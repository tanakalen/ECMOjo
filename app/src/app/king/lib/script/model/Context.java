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
}
