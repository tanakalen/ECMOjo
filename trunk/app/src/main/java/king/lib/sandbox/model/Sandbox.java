package king.lib.sandbox.model;

/**
 * Defines a sandbox.
 *
 * @author  noblemaster
 * @since  October 28, 2009
 */
public interface Sandbox {

  /**
   * Returns true if full access to the inputed class is given.
   * 
   * @param clazz  The class. E.g. "java.lang.Math" or "java.lang.Object".
   * @return  True for full unrestriced access.
   */
  boolean hasAccess(String clazz);
}
