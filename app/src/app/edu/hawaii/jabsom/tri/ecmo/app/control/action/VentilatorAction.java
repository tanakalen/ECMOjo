package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;

/**
 * The ventilator action. 
 *
 * @author   king
 * @since    Nov 26, 2008
 */
public class VentilatorAction extends Action {

  /** True for emergency function on. */
  private boolean emergencyFunction;
  
  
  /**
   * Returns true for emergency function.
   *
   * @return  True for emergency function.
   */
  public boolean isEmergencyFunction() {
    return emergencyFunction;
  }

  /**
   * Set true for emergency function.
   *
   * @param emergencyFunction  True for emergency function.
   */
  public void setEmergencyFunction(boolean emergencyFunction) {
    this.emergencyFunction = emergencyFunction;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // create bubbles in tubing
    VentilatorComponent component = (VentilatorComponent)game.getEquipment().getComponent(VentilatorComponent.class);
    component.setEmergencyFuction(emergencyFunction);
  }
}
