package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent;
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
    tubeComponent.setArterialBubbles(false);
    tubeComponent.setVenousBubbles(false);

    // reset the oxigenator
    OxigenatorComponent oxiComponent = (OxigenatorComponent)equipment
                                       .getComponent(OxigenatorComponent.class);
    oxiComponent.setClotting(0.0);
    oxiComponent.setBroken(false);

    // reset the pump
    PumpComponent pumpComponent = (PumpComponent)equipment
                                  .getComponent(PumpComponent.class);
    pumpComponent.setOn(true);
    pumpComponent.setFlow(0.6);
    
    // reset alarms
    AlarmIndicatorComponent alarmIndicatorComponent = (AlarmIndicatorComponent)equipment
                                                      .getComponent(AlarmIndicatorComponent.class);
    alarmIndicatorComponent.setAlarm(false);
    
    // reset bubble detector
    BubbleDetectorComponent bubbleDetectorComponent = (BubbleDetectorComponent)equipment
                                                      .getComponent(BubbleDetectorComponent.class);
    bubbleDetectorComponent.setAlarm(false);

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
