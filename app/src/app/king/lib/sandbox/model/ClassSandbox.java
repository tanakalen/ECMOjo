package king.lib.sandbox.model;

import king.lib.util.StringSet;

/**
 * Defines a class sandbox with restricted access to classes.
 *
 * @author  noblemaster
 * @since  October 28, 2009
 */
public class ClassSandbox implements Sandbox {

  /** The classes that shall be accessible. */
  private StringSet accessibleClasses;
  

  /**
   * The default constructor with no classes being accessible.
   */
  public ClassSandbox() {
    this(new StringSet());
  }

  /**
   * The constructor.
   *
   * @param accessibleClass  The class that shall be accessible. E.g. "java.lang.Math" or "java.lang.Object".
   */
  public ClassSandbox(String accessibleClass) {
    this.accessibleClasses = new StringSet();
    this.accessibleClasses.add(accessibleClass);
  }

  /**
   * The constructor.
   *
   * @param accessibleClasses  The classes that shall be accessible. E.g. "java.lang.Math" or "java.lang.Object".
   */
  public ClassSandbox(StringSet accessibleClasses) {
    this.accessibleClasses = accessibleClasses;
  }
  
  /**
   * Returns true if full access to the inputed class is given.
   * 
   * @param clazz  The class. E.g. "java.lang.Math" or "java.lang.Object".
   * @return  True for full unrestriced access.
   */
  public boolean hasAccess(String clazz) {
    return accessibleClasses.contains(clazz);
  }
}
