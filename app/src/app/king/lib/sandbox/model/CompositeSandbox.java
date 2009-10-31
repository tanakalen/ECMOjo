package king.lib.sandbox.model;

/**
 * Defines a class sandbox made up of several sandboxes. If at least one of the underlying sandboxes
 * allows access, this composite allows access.
 *
 * @author  noblemaster
 * @since  October 28, 2009
 */
public class CompositeSandbox implements Sandbox {

  /** The sandboxes. */
  private SandboxList sandboxes;
  

  /**
   * The default constructor with no access.
   */
  public CompositeSandbox() {
    this(new SandboxList());
  }

  /**
   * The constructor.
   *
   * @param sandboxes  The underlying sandboxes.
   */
  public CompositeSandbox(SandboxList sandboxes) {
    this.sandboxes = sandboxes;
  }
  
  /**
   * Returns true if full access to the inputed class is given.
   * 
   * @param clazz  The class. E.g. "java.lang.Math" or "java.lang.Object".
   * @return  True for full unrestriced access.
   */
  public boolean hasAccess(String clazz) {
    for (int i = 0; i < sandboxes.size(); i++) {
      if (sandboxes.get(i).hasAccess(clazz)) {
        return true;
      }
    }
    
    // no access
    return false;
  }
}
