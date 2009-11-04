package king.lib.script.model;

/**
 * The language enumeration.
 *         
 * @author Christoph Aschwanden
 * @since November 4, 2009
 * @access Public
 */
public final class Language {
  
  /** Java. */
  public static final Language JAVA = new Language("Java");
  /** Pnuts. */
  public static final Language PNUTS = new Language("Pnuts");
  /** Rhino. */
  public static final Language RHINO = new Language("Rhino");
  /** BeanShell. */
  public static final Language BEANSHELL = new Language("BeanShell");
  
  /** The values. */
  private static Language[] values = new Language[] {
    JAVA, PNUTS, RHINO, BEANSHELL
  };

  /** The name. */
  private String name;
  
  
  /** 
   * The constructor is private to prevent instantiation.
   * 
   * @param name  The name.
   */
  private Language(String name) {
    this.name = name;
  }
  
  /**
   * Returns the language for the given name.
   * 
   * @param name  The name.
   * @return  The language or null for not found.
   */
  public static Language find(String name) {
    for (int i = 0; i < values.length; i++) {
      if (values[i].getName().equalsIgnoreCase(name)) {
        return values[i];
      }
    }
    
    // not found
    return null;
  }
  
  /**
   * Returns the name.
   * 
   * @return  The name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Returns the ordinal number. 
   *
   * @return  The ordinal.
   */
  public int ordinal() {
    for (int i = 0; i < values.length; i++) {
      if (this == values[i]) {
        return i;
      }
    }
    
    // there is a problem with the code
    throw new RuntimeException("Item not found in array.");
  }
  
  /**
   * Returns all the values.
   *
   * @return  The values.
   */
  public static Language[] values() {
    return values;
  }
  
  /**
   * Returns the textual representation.
   * 
   * @return  The name.
   */
  public String toString() {
    return name;
  }
}