package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;

/**
 * The kill action. 
 *
 * @author   king
 * @since    October 24, 2008
 */
public class KillAction extends Action {

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // kill the patient...
    Patient patient = game.getPatient();
    patient.setAlive(false);
  }
}
