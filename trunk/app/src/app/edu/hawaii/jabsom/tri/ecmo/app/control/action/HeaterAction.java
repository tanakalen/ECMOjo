package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;

/**
 * The heater action. 
 *
 * @author   king
 * @since    September 12, 2008
 */
public class HeaterAction extends Action {

  /** The temperature. */
  private double temperature;
  
  
  /**
   * Returns the temperature.
   *
   * @return  The temperature.
   */
  public double getTemperature() {
    return temperature;
  }

  /**
   * Sets the temperature.
   *
   * @param temperature  The temperature to set.
   */
  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // sets the temperature
    HeaterComponent component = (HeaterComponent)game.getEquipment().getComponent(HeaterComponent.class);
    component.setTemperature(temperature);
  }
}
