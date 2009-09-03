package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;

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
    
    // reset the tubing
    TubeComponent tubeComponent = (TubeComponent)equipment
                                  .getComponent(TubeComponent.class);
    tubeComponent.setVenousPressure(0);
    
    // reset the oxigenator
    OxygenatorComponent oxiComponent = (OxygenatorComponent)equipment
                                       .getComponent(OxygenatorComponent.class);
    oxiComponent.setClotting(0.0);
    oxiComponent.setBroken(false);

    // reset the pump
    PumpComponent pumpComponent = (PumpComponent)equipment
                                  .getComponent(PumpComponent.class);
    pumpComponent.setOn(false);
    pumpComponent.setFlow(0.6);
    
    // reset alarms
    AlarmIndicatorComponent alarmIndicatorComponent = (AlarmIndicatorComponent)equipment
                                                      .getComponent(AlarmIndicatorComponent.class);
    alarmIndicatorComponent.setAlarm(false);
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
