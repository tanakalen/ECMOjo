package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;

/**
 * The pump action. 
 *
 * @author   Kin Lik Wang
 * @since    September 23, 2008
 */
public class PumpAction extends Action {

  /** True for on. False for off. */
  private boolean on;
  
  /** The flow. */
  private double flow;
  
  
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
  }
  
  /**
   * Returns the flow.
   *
   * @return  The flow.
   */
  public double getFlow() {
    return flow;
  }

  /**
   * Sets the flow.
   *
   * @param flow  The flow to set.
   */
  public void setFlow(double flow) {
    this.flow = flow;
  }

  /**
   * Executes the action on the game.
   *  
   * @param game  The game.
   */
  @Override
  public void execute(Game game) {
    // the component we are manipulating
    PumpComponent component = (PumpComponent)game.getEquipment().getComponent(PumpComponent.class);
    if (isOn()) {
      component.setOn(on);
      component.setFlow(flow);
    }
    else {
      component.setOn(on);
    }
  }
  
  /**
   * Returns the string representation.
   * 
   * @return  The string representation.
   */
  @Override
  public String toString() {
    return "Action:Pump:{" + on + ", " + flow + "}";
  }
}
