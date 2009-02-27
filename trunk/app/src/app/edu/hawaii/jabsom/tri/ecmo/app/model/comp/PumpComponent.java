package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

/**
 * The pump component. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class PumpComponent extends Component {
  
  /** The available pump types. */
  public enum PumpType { CENTRIFUGAL, ROLLER };
  
  /** The pump type. */
  private PumpType pumpType;
  
  /** True for on. False for on. */
  private boolean on;
  
  /** The flow in liters per minute [0.000, 5.000]. */
  private double flow;

  /** True if there is an alarm. */
  private boolean alarm;

  
  /**
   * Returns the pump type.
   *
   * @return  The pump type.
   */
  public PumpType getPumpType() {
    return pumpType;
  }

  /**
   * Sets the pump type.
   *
   * @param pumpType  The pump type.
   */
  public void setPumpType(PumpType pumpType) {
    this.pumpType = pumpType;
  }

  /**
   * Returns true for on.
   *
   * @return  True for on, false for off.
   */
  public boolean isOn() {
    return on;
  }

  /**
   * Set true for on.
   *
   * @param on  True for on, false for off.
   */
  public void setOn(boolean on) {
    this.on = on;
    notifyUpdate();
  }

  /**
   * Returns the flow in liters per minutes.
   *
   * @return the flow
   */
  public double getFlow() {
    return flow;
  }

  /**
   * Sets the flow in liters per minute.
   *
   * @param flow  The flow to set.
   */
  public void setFlow(double flow) {
    this.flow = flow;
    notifyUpdate();
  }
  
  /**
   * Returns true for alarm.
   *
   * @return  True for alarm.
   */
  public boolean isAlarm() {
    return alarm;
  }

  /**
   * Sets if there is an alarm.
   *
   * @param alarm  The alarm to set.
   */
  public void setAlarm(boolean alarm) {
    this.alarm = alarm;
    notifyUpdate();
  }
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    if (pumpType == PumpType.CENTRIFUGAL) {
      return "Centrifugal Pump";
    }
    else {
      return "Roller Pump";
    }
  }
}
