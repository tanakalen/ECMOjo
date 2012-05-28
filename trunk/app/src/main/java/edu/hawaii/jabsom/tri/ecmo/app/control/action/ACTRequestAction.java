package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent.ACT;

/**
 * The ACT request action. 
 *
 * @author   king
 * @since    October 13, 2008
 */
public class ACTRequestAction extends Action {

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  @Override
  public void execute(Game game) {
    // update ACT
    ACTComponent component = (ACTComponent)game.getEquipment().getComponent(ACTComponent.class);
    ACT act = new ACT();
    act.setValue(game.getPatient().getAct());
    act.setTime((int)(game.getElapsedTime() / 1000));
    component.addACT(act);
  }
  
  /**
   * Returns the string representation.
   * 
   * @return  The string representation.
   */
  @Override
  public String toString() {
    return "Action:ACTRequest";
  }
}
