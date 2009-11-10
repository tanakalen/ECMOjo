package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;

/**
 * The circuit change action. 
 *
 * @author   Christoph Aschwanden
 * @since    July 2, 2009
 */
public class CircuitChangeAction extends Action {

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  @Override
  public void execute(Game game) {
    // change circuit <=> "reset the circuit"   
    Equipment equipment = game.getEquipment();
    
    // reset the oxygenator
    OxygenatorComponent oxiComponent = (OxygenatorComponent)equipment
                                       .getComponent(OxygenatorComponent.class);
    oxiComponent.setClotting(0.0);
    oxiComponent.setBroken(false);

    // reset the tubing
    TubeComponent tubeComponent = (TubeComponent)equipment
                                  .getComponent(TubeComponent.class);
    tubeComponent.setVenousPressure(0);
    if (oxiComponent.getOxyType() == OxygenatorComponent.OxyType.QUADROX_D) { 
      // PMP
      tubeComponent.setPreMembranePressure(125);
      tubeComponent.setPostMembranePressure(120);
    }
    else { 
      // Silicon
      tubeComponent.setPreMembranePressure(140);
      tubeComponent.setPostMembranePressure(120);
    }

    // reset the pump
    PumpComponent pumpComponent = (PumpComponent)equipment
                                  .getComponent(PumpComponent.class);
//    pumpComponent.setOn(false);
    pumpComponent.setFlow(0.0);
    
    // reset alarms
    AlarmIndicatorComponent alarmIndicatorComponent = (AlarmIndicatorComponent)equipment
                                                      .getComponent(AlarmIndicatorComponent.class);
    alarmIndicatorComponent.setAlarm(false);
    
    // reset patient and labs
    Patient patient = game.getPatient();
    patient.setAct(Double.NaN);
    patient.setFibrinogen(Double.NaN);
    patient.setPlatelets(Double.NaN);
    patient.setPtt(Double.NaN);
  }
  
  /**
   * Returns the string representation.
   * 
   * @return  The string representation.
   */
  @Override
  public String toString() {
    return "Action:CircuitChange";
  }
}
