package king.lib.util;

/**
 * Writes memory usage to the console output in 0.5 second intervals. Quik and dirty
 * solution to search for memory leaks.
 * <p>
 * Usage:
 * <pre>
 * MemoryTracker.getInstance().start();  // to start it
 * ...
 * MemoryTracker.getInstance().stop();   // to stop it
 * </pre>
 * 
 * @author  king
 * @since   December 8, 2004
 */
public final class MemoryTracker implements Runnable {

  /** The only instance of memory tracker. */
  private static MemoryTracker memoryTracker = new MemoryTracker();
  
  /** The thread that outputs memory usage. */
  private Thread thread = null;
  
  
  /**
   * The only constructor for memory tracker which disables to have several
   * instances of it. It's private!
   */
  private MemoryTracker() {
    // to disable instantiation of this object  
  }
  
  /**
   * Returns the only instance of memory tracker.
   * 
   * @return  The only existing instance of memory tracker.
   */
  public static MemoryTracker getInstance() {
    return MemoryTracker.memoryTracker;
  }
  
  /**
   * Starts the thread which outputs memory usage.
   */
  public void start() {
    this.thread = new Thread(this);
    this.thread.start();
  }

  /**
   * Main thread which outputs memory usage.
   */
  public void run() {
    Runtime runtime = Runtime.getRuntime();
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // output memory usage
      System.out.println("Memory: " + runtime.freeMemory() + "/" + runtime.totalMemory());
      try {
        // wait before next output
        Thread.sleep(500);
      }
      catch (InterruptedException e) {
        throw new IllegalThreadStateException();
      }
    }
  }
  
  /**
   * Stops the thread which outputs memory usage.
   */
  public void stop() {
    this.thread = null;
  }
} 
