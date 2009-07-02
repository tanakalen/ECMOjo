package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;
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
    tubeComponent.setArterialAOpen(true);
    tubeComponent.setArterialBOpen(true);
    tubeComponent.setVenousAOpen(true);
    tubeComponent.setVenousBOpen(true);
    tubeComponent.setBridgeOpen(false);
    tubeComponent.setVenousPressure(0);
    tubeComponent.setArterialBubbles(false);
    tubeComponent.setVenousBubbles(false);

    // reset the pump
    PumpComponent pumpComponent = (PumpComponent)equipment
                                  .getComponent(PumpComponent.class);
    pumpComponent.setOn(true);
    pumpComponent.setFlow(0.6);

    // reset the pressure monitor
    PressureMonitorComponent pressureMonitorComponent = (PressureMonitorComponent)equipment
                                                        .getComponent(PressureMonitorComponent.class);
    pressureMonitorComponent.setPreMembranePressureMin(100);
    pressureMonitorComponent.setPreMembranePressureMax(500);
    pressureMonitorComponent.setPostMembranePressureMin(100);
    pressureMonitorComponent.setPostMembranePressureMax(400);
    pressureMonitorComponent.setVenousPressureMin(-50);
    pressureMonitorComponent.setVenousPressureMax(20);
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
