package king.lib.sandbox.control;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Java thread stopping.
 * 
 * @author noblemaster
 * @since  October 28, 2009
 */
public class JavaThreadStoppingIT {
  
  /**
   * Test stopping a Java thread.
   * 
   * @throws Exception  When things go wrong.
   */
  @Test
  @SuppressWarnings("deprecation")
  public void testStopThread() throws Exception {
    // the thread to stop
    final Object lock = new Object();
    Thread thread = new Thread() {
      public void run() {
        // endless loop
        synchronized(lock) {
          int i = 0;
          while (true) {
            System.out.print(i++);
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
