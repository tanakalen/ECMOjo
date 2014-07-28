package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

import java.io.Serializable;

/**
 * The lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public abstract class Lab implements Serializable {
  
  /**
   * Returns the name of the test.
   * 
   * @return  The name.
   */
  public abstract String getName();

  /**
   * Returns the textual representation of this component.
   * 
   * @return  The textual representation of this component.
   */
  public String toString() {
    return getName();
  }
}
