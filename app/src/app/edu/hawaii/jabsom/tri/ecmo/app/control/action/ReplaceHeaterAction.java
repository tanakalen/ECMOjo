package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;

/**
 * The replace heater action. 
 *
 * @author   Christoph Aschwanden
 * @since    September 29, 2009
 */
public class ReplaceHeaterAction extends Action {

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  @Override
  public void execute(Game game) {
    // fix the heater   
    Equipment equipment = game.getEquipment();
    HeaterComponent heater = (HeaterComponent)equipment.getComponent(HeaterComponent.class);
    heater.setBroken(false);
    heater.setTemperature(37);
  }
  
  /**
   * Returns the string representation.
   * 
   * @return  The string representation.
   */
  @Override
  public String toString() {
    return "Action:ReplaceHeater";
  }
}
