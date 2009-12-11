package king.lib.sandbox.model;

/**
 * Defines a package sandbox with restricted access to a package.
 *
 * @author  noblemaster
 * @since  December 11, 2009
 */
public class PackageSandbox implements Sandbox {

  /** The package to allow access to. */
  private String accessiblePackage;
  

  /**
   * The constructor.
   *
   * @param accessiblePackage  The package that shall be accessible. E.g. "java.lang" or "java.math".
   */
  public PackageSandbox(String accessiblePackage) {
    this.accessiblePackage = accessiblePackage;
  }
  
  /**
   * Returns true if full access to the inputed class is given.
   * 
   * @param clazz  The class. E.g. "java.lang.Math" or "java.lang.Object".
   * @return  True for full unrestriced access.
   */
  public boolean hasAccess(String clazz) {
    return clazz.startsWith(accessiblePackage);
  }
}
