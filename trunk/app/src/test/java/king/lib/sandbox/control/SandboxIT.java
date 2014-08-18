package king.lib.sandbox.control;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import javax.swing.JFrame;
import org.junit.Test;

/**
 * Tests the sandbox functionality.
 * 
 * @author noblemaster
 * @since  October 28, 2009
 */
public class SandboxIT {
      
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
    file.canRead();
    SandboxSecurity.sandbox();
    try {
      file = new File("testfile.txt");
      file.canRead();
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
}
