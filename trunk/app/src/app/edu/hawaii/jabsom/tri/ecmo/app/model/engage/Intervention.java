package edu.hawaii.jabsom.tri.ecmo.app.model.engage;

import java.io.Serializable;

/**
 * The intervention. 
 *
 * @author   king
 * @since    August 29, 2008
 */
public abstract class Intervention implements Serializable {
  
  /**
   * Returns the name of the intervention.
   * 
   * @return  The name.
   */
  public abstract String getName();

  /**
   * Returns the textual representation of this intervention.
   * 
   * @return  The textual representation of this intervention.
   */
  public String toString() {
    return getName();
  }
}
