package king.lib.script.model.pnuts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import king.lib.sandbox.model.ClassSandbox;
import king.lib.script.control.ScriptException;
import king.lib.script.control.ScriptRunner;
import king.lib.script.model.Context;
import king.lib.script.model.Language;
import king.lib.script.model.Script;
import king.lib.util.StringSet;

import org.junit.Test;

/**
 * Tests the scripting functionality.
 * 
 * @author noblemaster
 * @since  October 22, 2009
 */
public class TestScriptPnuts {
      
  /**
   * Try to pass and modify some parameters.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testPnutsParameterPassing() throws Exception {
    // context and script
    Context context = new Context();
    StringSet accessibleClasses = new StringSet();
    accessibleClasses.add("java.land.Object");
    accessibleClasses.add("java.util.ArrayList");
    context.setSandbox(new ClassSandbox(accessibleClasses));
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    
    // returns y = 22 * x
    script.setCode(
        "return context * 22;\n"
    );
    assertEquals("We should obtain 88.", 88, ScriptRunner.execute(script, context, 4));

    // can modify input
    script.setCode(
        "context.set(1, 3000000);\n"
    );
    List list = new ArrayList();
    list.add(1322);
    list.add(-524);
    list.add(6456);
    assertEquals("Item index 1.", -524, list.get(1));
    ScriptRunner.execute(script, context, list);
    assertEquals("Item index 1.", 3000000, list.get(1));
    
    // can modify custom input
    script.setCode(
          "sqrt = context.getSquareRoot(4.03);\n"
        + "context.writeSomething(\"sqrt(4.03)=\" + sqrt);\n"
        + "return sqrt;\n"
    );
    /** The test object. */
    class MyObject extends Object {
      /**
       * Test method one.
       * 
       * @param x  Input.
       * @return  Squared output.
       */
      public double getSquareRoot(double x) {
        return Math.sqrt(x);
      }
      /**
       * Writes out something.
       * 
       * @param text  The text to write.
       */
      public void writeSomething(String text) {
        System.out.println(text);
      }
    }
    MyObject myObject = new MyObject();
    Context myContext = new Context();
    StringSet myAccessibleClasses = new StringSet();
    myAccessibleClasses.add(MyObject.class.getName());
    myContext.setSandbox(new ClassSandbox(myAccessibleClasses));
    Double doubleReturn = (Double)ScriptRunner.execute(script, myContext, myObject);    
    assertEquals("Return value is the same.", myObject.getSquareRoot(4.03), doubleReturn);
    myObject.writeSomething("sqrt(4.03)=" + myObject.getSquareRoot(4.03));
  }

  /**
   * Try to run potentially malicious Pnuts scripts.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testPnutsMaliciousExecution() throws Exception {
    // context and script
    Context restrictedContext = new Context();
    restrictedContext.setSandbox(new ClassSandbox());
    Context unrestrictedContext = new Context();
    unrestrictedContext.setSandbox(null);
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    
    // thread execution
    script.setCode(
          "thread = new Thread(new Runnable() {\n"
        + "  run() {\n"
        + "    a = 1000000;\n"
        + "  }\n"
        + "});\n"
        + "thread.start();\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, null);
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }    
    script.setCode(
        "Thread.currentThread();\n"
    );
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }    

    // system access
    script.setCode(
          "System.out.println(\"sys out\");\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, null);
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {  
      // should come here
    }    
    script.setCode(
          "System.getProperty(\"user.home\");\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, null);
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {  
      // should come here
    }    
    script.setCode(
        "System.exit(0);\n"
    );
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }    

    // file I/O execution
    script.setCode(
          "outputStream = new java.io.FileOutputStream(new java.io.File(\"pnuts-test-file-3840124832490.txt\"));\n"
        + "outputStream.close();\n"
        + "outputStream = null;\n"
        + "(new java.io.File(\"pnuts-test-file-3840124832490.txt\")).delete();\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, null);
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }
    
    // class access
    script.setCode(
        "Class.forName(\"java.lang.String\");\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, null);
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }   
    
    // can access custom input and classloader through it
    script.setCode(
          "clazz = context.getClass();\n"
        + "loader = clazz.getClassLoader();\n"
        + "object = loader.loadClass(\"java.lang.Object\");\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, this);
    try {
      StringSet accessibleClasses = new StringSet();
      accessibleClasses.add(getClass().getName());
      Context myContext = new Context();
      myContext.setSandbox(new ClassSandbox(accessibleClasses));
      ScriptRunner.execute(script, myContext, this);
      fail("Potentially malicous script executed.");
    }  
    catch (Exception e) {
      // should come here
    }
    
    // can access pnuts modules (they should not be available!)
    script.setCode(
          "use(\"pnuts.lib\");\n"
        + "rsrc = getURL(\"http://www.noblemaster.com\");\n"
    );
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }   
    script.setCode(
          "use(\"pnuts.lib\");\n"
        + "testVector = vector();\n"
    );
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }   
  }
  
  /**
   * Try to setting a maximum execution time for Pnuts scripts works.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testPnutsExecutionMaxDuration() throws Exception {
    // context and script
    Context context = new Context();
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    script.setCode(
          "for (i = 0; i < 1000000; i++) {\n"
        + "  a = 3.1415;\n"
        + "}\n"
        + "return 3.1415;\n"
    );
    
    // should work
    context.setMaxDuration(0);
    assertEquals("We should obtain pi.", 3.1415, ScriptRunner.execute(script, context, null));

    // should file due to timeout (unless we have a super fast computer)
    context.setMaxDuration(1);
    try {
      ScriptRunner.execute(script, context, null);
      fail("Excecution should have stopped due to time limit.");
    }
    catch (ScriptException e) {
      // we should get an exception and come here due to the fact that the program took too long to execute
    }
  }
}
