package king.lib.sandbox.control;

/**
 * Runs an executable for a maximum amount of time. The executable is killed throwing an exception if time
 * runs out. 
 * <p>
 * Limitations: Threads started by the executable cannot be stopped (sandbox as needed).
 * 
 * @author noblemaster
 * @since October 30, 2009
 */
public final class TimedRunner {
 
  /** The timed thread. */
  private static final class TimedThread extends Thread {
    
    /** The executable to execute. */
    private TimedExecutable executable;
    
    /** True for successfully terminated. */
    private boolean finished = false;
    /** True if there was a return value, false for exception. */
    private boolean returnValue = false;
    /** The result returned. Can be null. */
    private Object result = null;
    /** The exception or null. */
    private Exception exception = null;
    
    /**
     * The main method.
     */
    public void run() {
      try {
        result = executable.execute();
        returnValue = true;
      }
      catch (Exception e) {
        exception = e;
      }
      finished = true;
    }
  }
  
  /**
   * The private constructor to prevent instantiation.
   */
  private TimedRunner() {
    // not used
  }
  
  /**
   * Executes an executable.
   * 
   * @param executable  The executable to execute.
   * @param maxDuration  The maximum duration in millisecond the executable is allowed to run before it is killed.
   * @return  The result or null for none.
   * @throws TimedException  If the thread took too long to execute.
   * @throws Exception  For any exceptions that are occurring in the executable.
   */
  @SuppressWarnings("deprecation")
  public static Object execute(TimedExecutable executable, long maxDuration) throws TimedException, Exception {
    // the thread to run the executable
    TimedThread thread = new TimedThread();
    thread.executable = executable;
    thread.start();
    
    // wait for completion
    thread.join(maxDuration);
    
    // kill thread as needed
    if (!thread.finished) {
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
      
      // tell about it
      throw new TimedException("The code took too long to execute.");
    }
    else {
      if (thread.returnValue) {
        return thread.result;
      }
      else {
        throw thread.exception;
      }
    }
  }
}
