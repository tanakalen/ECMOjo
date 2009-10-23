package king.lib.script.model;

/**
 * The context under which to execute.
 *
 * @author  noblemaster
 * @since  October 21, 2009
 */
public class Context {

  /** True for a restricted context without access to threads, files etc. */
  private boolean restricted;
  /** The maximum execution time in milliseconds for execution. 0 for unlimited. */
  private long maxDuration;
  
  
  /**
   * The default constructor.
   */
  public Context() {
    this.restricted = true;
  }
  
  /**
   * Returns true for restricted.
   *
   * @return  True for a restricted context without access to threads, files etc.
   */
  public boolean isRestricted() {
    return restricted;
  }

  /**
   * Set true for restricted.
   *
   * @param restricted  True for a restricted context without access to threads, files etc.
   */
  public void setRestricted(boolean restricted) {
    this.restricted = restricted;
  }

  /**
   * Returns the max duration for execution.
   *
   * @return  The max duration for execution. 0 for unlimited.
   */
  public long getMaxDuration() {
    return maxDuration;
  }

  /**
   * Sets the max duration for execution.
   *
   * @param maxDuration  The max duration for execution. 0 for unlimited.
   */
  public void setMaxDuration(long maxDuration) {
    this.maxDuration = maxDuration;
  }
}
