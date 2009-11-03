package king.lib.script.model;

import java.io.Serializable;

/**
 * The script type. 
 *
 * @author  noblemaster
 * @since  November 2, 2009
 */
public enum ScriptType implements Serializable {
  
  /** Java. */
  JAVA("Java"), 
  /** Pnuts. */
  PNUTS("Pnuts"), 
  /** Rhino (for JavaScript). */
  RHINO("Rhino");
   
  /** The name of the type. */
  private String name;
    
  /** 
   * The constructor.
   * 
   * @param name  The name.
   */
  private ScriptType(String name) {
    this.name = name;
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
   * Returns the name.
   * 
   * @return  The name.
   */
  public String toString() {
    return this.name;
  }
}
