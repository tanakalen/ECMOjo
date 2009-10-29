package king.lib.script.model.java;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import king.lib.sandbox.control.SandboxClassLoader;
import king.lib.sandbox.model.ClassSandbox;
import king.lib.sandbox.model.Sandbox;
import king.lib.util.StringSet;

import org.junit.Test;

/**
 * Tests the scripting functionality.
 * 
 * @author noblemaster
 * @since  October 28, 2009
 */
public class TestScriptJava {

/*
package king.lib.script.model.java;
import king.lib.out.Error;
public class SampleClass implements JavaExecutable {
  public Object execute(Object object) {
    System.out.println("Output Input: " + object);
    System.out.println("Randx: " + Math.random());
    System.out.println("Txxx: " + Thread.currentThread());
    try {
      System.out.println("ClassLoader " + getClass().getClassLoader().loadClass("java.io.File"));
    } 
    catch (ClassNotFoundException e) {
      System.out.println("Class not found 2 " + e);
    }
    Error.out("Test ERROR output");
    return 3.1415;
  }
}
 */

  /**
   * Try to pass and modify some parameters.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testJavaParameterPassing() throws Exception {

  }
  
  /**
   * Try to run potentially malicious Java scripts.
   *
   * @throws Exception  When things go wrong.
   */
  @Test
  public void testJavaMaliciousExecution() throws Exception {
    StringSet accessibleClasses = new StringSet();
    accessibleClasses.add("king.lib.script.SampleClass");
    accessibleClasses.add("king.lib.script.model.Executable");
    accessibleClasses.add("king.lib.out.Error");
    accessibleClasses.add("java.lang.Object");
    accessibleClasses.add("java.lang.Throwable");
    accessibleClasses.add("java.lang.Exception");
    accessibleClasses.add("java.lang.IllegalArgumentException");
    accessibleClasses.add("java.lang.ClassNotFoundException");
    accessibleClasses.add("java.lang.System");
    accessibleClasses.add("java.io.PrintStream");
    accessibleClasses.add("java.io.File");
    accessibleClasses.add("java.lang.Class");
    accessibleClasses.add("java.lang.ClassLoader");
    accessibleClasses.add("java.lang.Thread");
    accessibleClasses.add("java.lang.Math");
    accessibleClasses.add("java.lang.Boolean");
    accessibleClasses.add("java.lang.Byte");
    accessibleClasses.add("java.lang.Short");
    accessibleClasses.add("java.lang.Integer");
    accessibleClasses.add("java.lang.Long");
    accessibleClasses.add("java.lang.Float");
    accessibleClasses.add("java.lang.Double");
    accessibleClasses.add("java.lang.String");
    accessibleClasses.add("java.lang.StringBuilder");
    accessibleClasses.add("java.lang.StringBuffer"); 

    Sandbox sandbox = new ClassSandbox(accessibleClasses);
    SandboxClassLoader sandboxClassLoader = new SandboxClassLoader(sandbox, getClass().getClassLoader());
    File file = new File("C:\\_temp\\king\\lib\\script\\SampleClass.class");
    byte[] b = new byte[(int)file.length()];
    InputStream in = new FileInputStream(file);
    in.read(b, 0, b.length);
    sandboxClassLoader.addClass("king.lib.script.SampleClass", b);
    
    JavaExecutable executable = (JavaExecutable)(sandboxClassLoader.loadClass("king.lib.script.SampleClass"))
                                                                   .newInstance();
    System.out.println("Output: " + executable.execute("bN-1201"));
  }
  
  /**
   * Test stopping a Java thread.
   * 
   * @throws Exception  When things go wrong.
   */
  @Test
  @SuppressWarnings("deprecation")
  public void testJavaExecutionMaxDuration() throws Exception {
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
}
