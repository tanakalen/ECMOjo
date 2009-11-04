package king.lib.script.model.java;

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
 * @since  October 28, 2009
 */
public class TestScriptJava {

  /**
   * Try to pass and modify some parameters.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testJavaParameterPassing() throws Exception {
    // context and script
    Context context = new Context();
    StringSet accessibleClasses = new StringSet();
    accessibleClasses.add("java.lang.Integer");
    accessibleClasses.add("java.util.List");
    context.setSandbox(new ClassSandbox(accessibleClasses));
    Script script = new Script();
    script.setLang(Language.JAVA.getName());
    
    // returns y = 22 * x
    script.setCode(
          "public class Script {\n"
        + "  public static Object execute(Object object) {\n"
        + "    int input = ((Integer)object).intValue();\n"
        + "    return input * 22;\n"
        + "  }\n"
        + "}\n"
    );
    assertEquals("We should obtain 88.", 88, ScriptRunner.execute(script, context, 4));
    
    // can modify input
    script.setCode(
          "import java.util.List;\n"
        + "public class Script {\n"
        + "  public static Object execute(Object object) {\n"
        + "    List input = (List)object;\n"
        + "    input.set(1, new Integer(383240));\n"
        + "    return null;\n"
        + "  }\n"
        + "}\n"
    );
    List list = new ArrayList();
    list.add(34);
    list.add(-1222);
    list.add(-6);
    assertEquals("Item index 1.", -1222, list.get(1));
    ScriptRunner.execute(script, context, list);
    assertEquals("Item index 1.", 383240, list.get(1));
  }
  
  /**
   * Try to run potentially malicious Java scripts.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testJavaMaliciousExecution() throws Exception {
    // context and script
    Context restrictedContext = new Context();
    restrictedContext.setSandbox(new ClassSandbox());
    Context unrestrictedContext = new Context();
    unrestrictedContext.setSandbox(null);
    Script script = new Script();
    script.setLang(Language.JAVA.getName());
    
    // thread execution
    script.setCode(
          "public class Script {\n"
        + "  public static Object execute(Object object) {\n"
        + "    Thread.currentThread();\n"
        + "    return null;\n"
        + "  }\n"
        + "}\n"
    );
    ScriptRunner.execute(script, unrestrictedContext, null);
    try {
      ScriptRunner.execute(script, restrictedContext, null);
      fail("Potentially malicous script executed.");
    }
    catch (Exception e) {
      // should come here
    }    
    
    // TODO: add other tests (see Pnuts test script for details)
  }
  
  /**
   * Test stopping a Java thread.
   * 
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testJavaExecutionMaxDuration() throws Exception {
    // context and script
    Context context = new Context();
    StringSet accessibleClasses = new StringSet();
    accessibleClasses.add("java.lang.Float");
    context.setSandbox(new ClassSandbox(accessibleClasses));
    Script script = new Script();
    script.setLang(Language.JAVA.getName());
    script.setCode(
          "public class Script {\n"
        + "  public static Object execute(Object object) {\n"
        + "    for (int i = 0; i < 100000000; i++) {\n"
        + "      float a = 3.1415f;\n"
        + "    }\n"
        + "    return 3.1415f;\n"
        + "  }\n"
        + "}\n"
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
