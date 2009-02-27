package edu.hawaii.jabsom.tri.ecmo.app.model.comp;


/**
 * The heater component. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class HeaterComponent extends Component {
  
  /** The temperature in centigrade [0, 100]. */
  private double temperature;

  
  /**
   * Returns the temperature in centigrade.
   *
   * @return  The temperature.
   */
  public double getTemperature() {
    return temperature;
  }

  /**
   * Sets the temperature in centigrade.
   *
   * @param temperature  The temperature to set.
   */
  public void setTemperature(double temperature) {
    this.temperature = temperature;
    notifyUpdate();
  }

  /**
   * Returns true for alarm.
   *
   * @return  True for alarm.
   */
  public boolean isAlarm() {
    return ((temperature < 34) || (temperature > 39));
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Heater";
  }
}
