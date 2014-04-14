package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import king.lib.util.Translator;
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
  
  /** True for cracked pigtail. */
  private boolean crackedPigtail;
  
  
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
   * Returns true for cracked pigtail.
   *
   * @return  True for cracked pigtail.
   */
  public boolean isCrackedPigtail() {
    return crackedPigtail;
  }

  /**
   * Set true for cracked pigtail.
   *
   * @param crackedPigtail  True for cracked pigtail.
   */
  public void setCrackedPigtail(boolean crackedPigtail) {
    this.crackedPigtail = crackedPigtail;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("label.Intervention[i18n]: Intervention");
  }
}
