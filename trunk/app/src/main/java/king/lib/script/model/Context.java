package king.lib.script.model;

import king.lib.sandbox.model.ClassSandbox;
import king.lib.sandbox.model.Sandbox;

/**
 * The context under which to execute.
 *
 * @author  noblemaster
 * @since  October 21, 2009
 */
public class Context {

  /** The sandbox or null for unrestricted access. */
  private Sandbox sandbox;
  /** The maximum execution time in milliseconds for execution. 0 for unlimited. */
  private long maxDuration;
  
  
  /**
   * The default constructor which sandboxes by default. Use setSandbox(null) to remove the sandbox.
   */
  public Context() {
    sandbox = new ClassSandbox();
    maxDuration = 0;
  }

  /**
   * Returns the sandbox.
   *
   * @return  The sandbox or null for unrestricted access.
   */
  public Sandbox getSandbox() {
    return sandbox;
  }

  /**
   * Sets the sandbox.
   *
   * @param sandbox  The sandbox or null for unrestricted access.
   */
  public void setSandbox(Sandbox sandbox) {
    this.sandbox = sandbox;
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
