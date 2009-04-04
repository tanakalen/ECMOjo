package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;

/**
 * The patient action. 
 *
 * @author   king
 * @since    Jan 5, 2009
 */
public class PatientAction extends Action {

  /** The checks. */
  public enum Check { CANULA_SITE, BLEEDING, BROKEN_ETT, SUCTION_ETT, DIAPER, STIMULATE };
  
  /** The check done. */
  private Check check;
  

  /**
   * Returns the check.
   *
   * @return  The check.
   */
  public Check getCheck() {
    return check;
  }

  /**
   * Sets the check.
   *
   * @param check  The check.
   */
  public void setCheck(Check check) {
    this.check = check;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // update patient
    Check check = getCheck();
    if (check == Check.CANULA_SITE) {
      TubeComponent tube = (TubeComponent)game.getEquipment().getComponent(TubeComponent.class);
      tube.setBrokenCannula(false);
    }
    else if (check == Check.BLEEDING) {
      Patient patient = game.getPatient();
      patient.setBleeding(false);
    }
    else if (check == Check.BROKEN_ETT) {
      TubeComponent tube = (TubeComponent)game.getEquipment().getComponent(TubeComponent.class);
      tube.setBrokenETT(false);
    }
    else if (check == Check.SUCTION_ETT) {
      TubeComponent tube = (TubeComponent)game.getEquipment().getComponent(TubeComponent.class);
      tube.setSuctionETT(false);
    }
    else if (check == Check.DIAPER) {
      // no action taken...
    }
    else if (check == Check.STIMULATE) {
      // no action taken...
    }
    else {
      Error.out("Check not mapped: " + check);
    }
  }
}