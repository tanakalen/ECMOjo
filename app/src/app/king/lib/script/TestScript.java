package king.lib.script;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import king.lib.script.control.SandboxSecurity;
import king.lib.script.control.SandboxClassLoader;
import king.lib.script.control.ScriptRunner;
import king.lib.script.model.Context;
import king.lib.script.model.Executable;
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
   * Tests compiling and executing a Pnuts script.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testPnutsCompileAndExecute() throws Exception {
    // context and script
    Context context = new Context();
    Script script = new Script();
    script.setLang("pnuts");
    
    // thread execution
    script.setCode(
          "thread = new Thread(new Runnable() {\n"
        + "  run() {\n"
        + "    a = 1000000;\n"
        + "    System.out.println(\"test\");\n"
        + "  }\n"
        + "});\n"
        + "thread.start();\n"
    );
    ScriptRunner.execute(script, context, null);

  }

  /**
   * Test the sandboxing class loader.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testSandboxByteArrayClassLoader() throws Exception {
    SandboxClassLoader sandboxClassLoader = new SandboxClassLoader(getClass().getClassLoader());
    sandboxClassLoader.addLegal("king.lib.script.SampleClass");
    sandboxClassLoader.addLegal("king.lib.script.model.Executable");
    sandboxClassLoader.addLegal("king.lib.out.Error");
    sandboxClassLoader.addLegal("java.lang.Object");
    sandboxClassLoader.addLegal("java.lang.Throwable");
    sandboxClassLoader.addLegal("java.lang.Exception");
    sandboxClassLoader.addLegal("java.lang.IllegalArgumentException");
    sandboxClassLoader.addLegal("java.lang.ClassNotFoundException");
    sandboxClassLoader.addLegal("java.lang.System");
    sandboxClassLoader.addLegal("java.io.PrintStream");
    sandboxClassLoader.addLegal("java.io.File");
    sandboxClassLoader.addLegal("java.lang.Class");
    sandboxClassLoader.addLegal("java.lang.ClassLoader");
    sandboxClassLoader.addLegal("java.lang.Thread");
    sandboxClassLoader.addLegal("java.lang.Math");
    sandboxClassLoader.addLegal("java.lang.Boolean");
    sandboxClassLoader.addLegal("java.lang.Byte");
    sandboxClassLoader.addLegal("java.lang.Short");
    sandboxClassLoader.addLegal("java.lang.Integer");
    sandboxClassLoader.addLegal("java.lang.Long");
    sandboxClassLoader.addLegal("java.lang.Float");
    sandboxClassLoader.addLegal("java.lang.Double");
    sandboxClassLoader.addLegal("java.lang.String");
    sandboxClassLoader.addLegal("java.lang.StringBuilder");
    sandboxClassLoader.addLegal("java.lang.StringBuffer"); 
    File file = new File("C:\\_temp\\king\\lib\\script\\SampleClass.class");
    byte[] b = new byte[(int)file.length()];
    InputStream in = new FileInputStream(file);
    in.read(b, 0, b.length);
    sandboxClassLoader.addClass("king.lib.script.SampleClass", b);
    
    Executable executable = (Executable)(sandboxClassLoader.loadClass("king.lib.script.SampleClass")).newInstance();
    System.out.println("Output: " + executable.execute("bN-1201"));
  }

  /**
   * Test the sandboxing class loader.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testSandboxURLClassLoader() throws Exception {
    SandboxClassLoader sandboxClassLoader = new SandboxClassLoader(getClass().getClassLoader());
    sandboxClassLoader.addLegal("king.lib.script.SampleClass");
    sandboxClassLoader.addLegal("king.lib.script.model.Executable");
    sandboxClassLoader.addLegal("king.lib.out.Error");
    sandboxClassLoader.addLegal("java.lang.Object");
    sandboxClassLoader.addLegal("java.lang.Throwable");
    sandboxClassLoader.addLegal("java.lang.Exception");
    sandboxClassLoader.addLegal("java.lang.IllegalArgumentException");
    sandboxClassLoader.addLegal("java.lang.ClassNotFoundException");
    sandboxClassLoader.addLegal("java.lang.System");
    sandboxClassLoader.addLegal("java.io.PrintStream");
    sandboxClassLoader.addLegal("java.io.File");
    sandboxClassLoader.addLegal("java.lang.Class");
    sandboxClassLoader.addLegal("java.lang.ClassLoader");
    sandboxClassLoader.addLegal("java.lang.Thread");
    sandboxClassLoader.addLegal("java.lang.Math");
    sandboxClassLoader.addLegal("java.lang.Boolean");
    sandboxClassLoader.addLegal("java.lang.Byte");
    sandboxClassLoader.addLegal("java.lang.Short");
    sandboxClassLoader.addLegal("java.lang.Integer");
    sandboxClassLoader.addLegal("java.lang.Long");
    sandboxClassLoader.addLegal("java.lang.Float");
    sandboxClassLoader.addLegal("java.lang.Double");
    sandboxClassLoader.addLegal("java.lang.String");
    sandboxClassLoader.addLegal("java.lang.StringBuilder");
    sandboxClassLoader.addLegal("java.lang.StringBuffer"); 
    sandboxClassLoader.addURL("file:///C:\\_temp\\");
    
    Executable executable = (Executable)(sandboxClassLoader.loadClass("king.lib.script.SampleClass")).newInstance();
    System.out.println("Output: " + executable.execute("bN-1201"));
  }
  
  /**
   * Test stopping a Java thread.
   * 
   * @throws Exception  When things go wrong.
   */
  @Test
  @SuppressWarnings("deprecation")
  public void testThreadStopping() throws Exception {
    // the thread to stop
    final Object lock = new Object();
    Thread thread = new Thread() {
      public void run() {
        // endless loop
        synchronized(lock) {
          int i = 0;
          while (true) {
            System.out.println(i++);            
          }
        }
      }
    };
    
    // thread running
    thread.start();
    assertTrue("Thread is running.", thread.isAlive());
    
    // wait 1 second
    Thread.sleep(1000);
    assertTrue("Thread is still running.", thread.isAlive());
    
    // stop thread
    // ---------------------------------------------------------------------------------------------------------------
    // IMPORTANT NOTE:
    // The only way to stop the thread is with thread.stop(), which is deprecated for good reasons. 
    // We execute thread.stop nonetheless with the following assumptions:
    // 1. The thread execution must not create or mutate any state (i.e. Java objects, class variables,
    //    external resources) that might be visible to other threads in the event that the thread is stopped. 
    // 2. The thread execution must not use notify to any other thread during its normal execution.
    // 3. The thread must not start or join other threads, or interact with then using stop, suspend or resume.
    // ---------------------------------------------------------------------------------------------------------------
    thread.stop();
    
    // wait 1 second
    Thread.sleep(1000);
    assertFalse("Thread is stopped.", thread.isAlive());

    // make sure the lock is gone
    synchronized(lock) {
      System.out.println("Lock is gone.");
    }
  }
  
  /**
   * Test the sandboxing system.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testSandboxSystem() throws Exception {
    // install sandbox security
    SandboxSecurity.install();
    
    // try reading system property
    System.getProperty("user.home");
    SandboxSecurity.sandbox(Thread.currentThread());
    try {
      System.getProperty("user.home");
      fail("We should get a security exception.");
    }
    catch (SecurityException e) {
      // show come here
    }
    SandboxSecurity.unsandbox(Thread.currentThread());
    
    // try spawning a thread
    Runnable runnable = new Runnable() {
      public void run() {
        // does nothing
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
    while (thread.isAlive()) {
      Thread.sleep(10);
    }
    SandboxSecurity.sandbox(Thread.currentThread());
    try {
      thread = new Thread(runnable);
      fail("We should get a security exception.");
    }
    catch (SecurityException e) {
      // show come here
    }
    SandboxSecurity.unsandbox(Thread.currentThread());

    // file access
    File file = new File("testfile.txt");
    file.canExecute();
    SandboxSecurity.sandbox();
    try {
      file = new File("testfile.txt");
      file.canExecute();
      fail("We should get a security exception.");
    }
    catch (SecurityException e) {
      // show come here
    }
    SandboxSecurity.unsandbox();

    // URL access
    URL url1 = new URL("http://www.noblemaster.com");
    url1.openConnection();
    SandboxSecurity.sandbox();
    try {
      URL url2 = new URL("http://www.noblemaster.com");
      url2.openConnection();
      fail("We should get a security exception.");
    }
    catch (SecurityException e) {
      // show come here
    }
    SandboxSecurity.unsandbox();

    // try opening a window
    new JFrame();
    SandboxSecurity.sandbox();
    try {
      new JFrame();
      fail("We should get a security exception.");
    }
    catch (SecurityException e) {
      // show come here
    }
    SandboxSecurity.unsandbox();

    // sandbox another thread
    /** Our malicious thread. */
    abstract class BadThread extends Thread {
      /** True if we have illegally executed code. */
      public boolean illegalExecution = true;     
      /** Our run method that needs to be implemented. */
      public abstract void run();
    }
    BadThread badThread = new BadThread() {
      public void run() {
        try {
          illegalExecution = false;
          System.getProperty("user.home");
          illegalExecution = true;
        }
        catch (SecurityException e) {
          // show come here
        }
      }
    };
    SandboxSecurity.sandbox(badThread);
    badThread.start();
    while (badThread.isAlive()) {
      Thread.sleep(10);
    }
    if (badThread.illegalExecution) {
      fail("We should have gotten security exception.");
    }
    SandboxSecurity.unsandbox(badThread);

    // common operations should work inside sandbox
    SandboxSecurity.sandbox();
    try {
      int a = 0;
      a *= 30;
      String test = "abc";   
      char ch = test.charAt(1);
      Integer integer = new Integer(-30808);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(ch);
      stringBuilder.append(integer);
      BigDecimal decimal = new BigDecimal(234234);
      decimal.negate();
    }
    catch (SecurityException e) {
      fail("We should NOT get a security exception.");
    }
    SandboxSecurity.unsandbox();
    
    // remove sandbox security
    SandboxSecurity.uninstall();
    
    // sandbox is inactive, so this should have no effect now
    SandboxSecurity.sandbox(Thread.currentThread());
    System.getProperty("user.home");
    SandboxSecurity.unsandbox(Thread.currentThread());
  }
  
  /**
   * Test the sandboxed thread interruption.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testSandboxThreadInterruption() throws Exception {
    // TODO: implement...
  }
  
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
