package edu.hawaii.jabsom.tri.ecmo.app;

/**
 * The application type. 
 *
 * @author noblemaster
 * @since May 27, 2010
 */
public enum AppType {
  
  /** ECMOjo Infant application. */
  INFANT("infant", "infant"), 
  /** ECMOjo Infant application. */
  INFANT_JA("infant_ja", "infant_ja"), 
  /** ECMOjo Adult application. */
  ADULT("adult", "adult");
  
  /** The name. */
  private String name;
  /** The path. */
  private String path;
  
  /**
   * The constructor.
   *
   * @param name  The name.
   * @param path  The path.
   */
  private AppType(String name, String path) {
    this.name = name;
    this.path = path;
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
   * Returns the path.
   * 
   * @return  The path without "/".
   */
  public String getPath() {
    return path;
  }
  
  /**
   * Returns the application type for the inputed name.
   * 
   * @param name  The name.
   * @return  The application type or null if not found.
   */
  public static AppType parse(String name) {
    for (int i = 0; i < values().length; i++) {
      if (values()[i].getName().equalsIgnoreCase(name)) {
        return values()[i];
      }
    }
    
    // not found
    return null;
  }
}
