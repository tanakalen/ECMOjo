package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.BloodIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.CatecholamineIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.HeparinBolusIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.FFPIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.AlbuminIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.Intervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.PlateletsIntervention;

/**
 * The intervention action. 
 *
 * @author   king
 * @since    August 29, 2008
 */
public class InterventionAction extends Action {

  /** The intervention. */
  private Intervention intervention;
  /** The intervention location. */
  private InterventionLocation location;
  
  
  /**
   * Returns the intervention.
   *
   * @return  The intervention.
   */
  public Intervention getIntervention() {
    return intervention;
  }

  /**
   * Sets the intervention.
   *
   * @param intervention  The intervention to set.
   */
  public void setIntervention(Intervention intervention) {
    this.intervention = intervention;
  }

  /**
   * Returns the location.
   *
   * @return  The location.
   */
  public InterventionLocation getLocation() {
    return location;
  }

  /**
   * Sets the location.
   *
   * @param location  The location to set.
   */
  public void setLocation(InterventionLocation location) {
    this.location = location;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    Patient patient = game.getPatient();
    Equipment equipment = game.getEquipment();
    
    if (intervention instanceof BloodIntervention) {
      // affect blood pressure
      patient.setSystolicBloodPressure(1.2f * patient.getSystolicBloodPressure());
      
      // goes down 5% and then 5 minutes later up 1-2% from what it originally was
      patient.setO2Saturation(0.95f * patient.getO2Saturation());
      
      // update HCT
      patient.setHgb(0.5f + patient.getHgb());
      
       // update pressure
      TubeComponent tube = (TubeComponent)equipment.getComponent(TubeComponent.class);
      tube.setVenousPressure(5.0f + tube.getVenousPressure());
    }
    else if (intervention instanceof PlateletsIntervention) {
      // affect blood pressure
      patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() + 5);

      // add clotting if entered at wrong location
      if ((location == InterventionLocation.BEFORE_OXIGENATOR)
       || (location == InterventionLocation.BEFORE_PUMP)) {
        OxigenatorComponent oxigenator = (OxigenatorComponent)equipment.getComponent(OxigenatorComponent.class);
        oxigenator.setClotting(oxigenator.getClotting() + 1);
      }
    }
    else if (intervention instanceof FFPIntervention) {
      // affect blood pressure
      patient.setAct(patient.getAct() * 1.02);      // +2% increase ACT
      if (patient.getHeartFunction() == HeartFunction.GOOD) { 
        patient.setCentralVenousPressure(patient.getCentralVenousPressure() + 4.0f);   // 4 mmHg increase
      }
      else if (patient.getHeartFunction() == HeartFunction.BAD) {
        patient.setCentralVenousPressure(patient.getCentralVenousPressure() + 4.5f);   // 4.5 mmHg increase
      }
    }
    else if (intervention instanceof AlbuminIntervention) {
      // affect ACT

      
    }
    else if (intervention instanceof CatecholamineIntervention) {
      // affect blood pressure and heart rate
      
      
    }
    else if (intervention instanceof HeparinBolusIntervention) {
      // affect ACT
      patient.setAct(patient.getAct() * 1.11);    // +11% 
    } 
    
    // track intervention
    game.getTracker().track(intervention.getClass());
  }
}
