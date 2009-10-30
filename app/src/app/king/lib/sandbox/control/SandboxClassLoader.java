package king.lib.sandbox.control;

import king.lib.sandbox.model.Sandbox;

/**
 * The sandbox class loader.
 * 
 * @author noblemaster
 * @since October 26, 2009
 * @access Public
 */
public class SandboxClassLoader extends ExternalClassLoader {
 
  /** The sandbox. */
  private Sandbox sandbox;
  
  
  /**
   * The constructor.
   *
   * @param sandbox  The sandbox.
   * @param parent  The parent class loader.
   */
  public SandboxClassLoader(Sandbox sandbox, ClassLoader parent) {
    super(parent);
    this.sandbox = sandbox;
  }

  /**
   * Adds a class.
   * 
   * @param name  The name, e.g. "com.company.SomeClass".
   * @param b  The bytes.
   * @throws SandboxException  If there is a problem.
   */
  @Override
  public void addClass(String name, byte[] b) throws SandboxException {
    if (sandbox.hasAccess(name)) {
      super.addClass(name, b);
    }
    else {
      throw new SandboxException("Class name is not allowed: " + name);
    }
  }
  
  /**
   * Loads the actual classes. Throws an IllegalArgumentException if a class is not allowed to be used.
   * 
   * @param name  The name of the class to load.
   * @param resolve  True to resolve the class.
   * @throws ClassNotFoundException  If the class cannot be found.
   * @return The class loaded.
   */
  @Override
  protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
    if (sandbox.hasAccess(name)) {
      return super.loadClass(name, resolve);
    }
    else {
      throw new IllegalArgumentException("No permission to load class: " + name);
    }
  }
}
