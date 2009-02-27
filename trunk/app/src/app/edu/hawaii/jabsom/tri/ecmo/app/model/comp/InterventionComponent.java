package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;

/**
 * The intervention component. 
 *
 * @author   king
 * @since    September 30, 2008
 */
public class InterventionComponent extends Component {
  
  /** The intervention location. */
  private InterventionLocation interventionLocation;
  

  /**
   * Returns the intervention location.
   *
   * @return  The intervention location.
   */
  public InterventionLocation getInterventionLocation() {
    return interventionLocation;
  }

  /**
   * Sets the intervention location.
   *
   * @param interventionLocation  The intervention location to set.
   */
  public void setInterventionLocation(InterventionLocation interventionLocation) {
    this.interventionLocation = interventionLocation;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Intervention";
  }
}
