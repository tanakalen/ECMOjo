package edu.hawaii.jabsom.tri.ecmo.app.model.engage;


/**
 * The intervention location. 
 *
 * @author   king
 * @since    September 30, 2008
 */
public enum InterventionLocation { 
  
  /** Before the pump. */
  BEFORE_PUMP("1"), 
  /** Before the oxigenator. */
  BEFORE_OXYGENATOR("2"), 
  /** After the oxigenator. */
  AFTER_OXYGENATOR("3"), 
  /** By the patient. */
  PATIENT("neck");
  
  /** The name. */
  private String name;
  
  /**
   * The constructor.
   *
   * @param name  The name.
   */
  private InterventionLocation(String name) {
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
}
