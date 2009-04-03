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
  public void execute(Game game) {
    // update ACT
    ACTComponent component = (ACTComponent)game.getEquipment().getComponent(ACTComponent.class);
    ACT act = new ACT();
    act.setValue(game.getPatient().getAct());
    act.setTime((int)(game.getElapsedTime() / 1000));
    component.addACT(act);
  }
}
