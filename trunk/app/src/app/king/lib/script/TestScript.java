package king.lib.script;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import king.lib.script.control.ScriptRunner;
import king.lib.script.model.Context;
import king.lib.script.model.Script;

import org.junit.Test;

/**
 * Tests the scripting functionality.
 * 
 * @author noblemaster
 * @since  October 22, 2009
 */
public class TestScript {

  /**
   * Try to pass and modify some parameters.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testPnutsParameterPassing() throws Exception {
    // context and script
    Context context = new Context();
    Script script = new Script();
    script.setLang("pnuts");
    
    // returns y = 22 * x
    script.setCode(
        "return input * 22;\n"
    );
    assertEquals("We should obtain 88.", 88, ScriptRunner.execute(script, context, 4));

    // can modify input
    script.setCode(
        "input.set(1, 3000000);\n"
    );
    List list = new ArrayList();
    list.add(1322);
    list.add(-524);
    list.add(6456);
    assertEquals("Item index 1.", -524, list.get(1));
    ScriptRunner.execute(script, context, list);
    assertEquals("Item index 1.", 3000000, list.get(1));
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
    restrictedContext.setRestricted(true);
    Context unrestrictedContext = new Context();
    unrestrictedContext.setRestricted(false);   
    Script script = new Script();
    script.setLang("pnuts");
    
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
    script.setLang("pnuts");
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
    assertNull("Script execution should fail due to time limit.", ScriptRunner.execute(script, context, null));
  }
}
