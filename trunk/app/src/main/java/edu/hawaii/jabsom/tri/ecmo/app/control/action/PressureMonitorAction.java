package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;

/**
 * The pressure monitor action. 
 *
 * @author   king
 * @since    October 1, 2008
 */
public class PressureMonitorAction extends Action {

  /** The pre-membrane pressure minimum in torr [0, 900]. */
  private double preMembranePressureMin;
  /** The pre-membrane pressure minimum in torr [0, 900]. */
  private double preMembranePressureMax;
  
  /** The post-membrane pressure minimum in torr [0, 900]. */
  private double postMembranePressureMin;
  /** The post-membrane pressure maximum in torr [0, 900]. */
  private double postMembranePressureMax;
  
  /** The venous pressure minimum in torr [-50, 100]. */
  private double venousPressureMin;
  /** The venous pressure maximum in torr [-50, 100]. */
  private double venousPressureMax;


  /**
   * Returns the post-membrane pressure minimum.
   *
   * @return  The post-membrane pressure.
   */
  public double getPostMembranePressureMin() {
    return postMembranePressureMin;
  }

  /**
   * Sets the post-membrane pressure minimum.
   *
   * @param postMembranePressureMin  The post-membrane pressure to set.
   */
  public void setPostMembranePressureMin(double postMembranePressureMin) {
    this.postMembranePressureMin = postMembranePressureMin;
  }

  /**
   * Returns the post-membrane pressure maximum.
   *
   * @return  The post-membrane pressure.
   */
  public double getPostMembranePressureMax() {
    return postMembranePressureMax;
  }

  /**
   * Sets the post-membrane pressure maximum.
   *
   * @param postMembranePressureMax  The post-membrane pressure to set.
   */
  public void setPostMembranePressureMax(double postMembranePressureMax) {
    this.postMembranePressureMax = postMembranePressureMax;
  }

  /**
   * Returns the pre-membrane pressure minimum.
   *
   * @return  The pre-membrane pressure.
   */
  public double getPreMembranePressureMin() {
    return preMembranePressureMin;
  }

  /**
   * Sets the pre-membrane pressure minimum.
   *
   * @param preMembranePressureMin  The pre-membrane pressure to set.
   */
  public void setPreMembranePressureMin(double preMembranePressureMin) {
    this.preMembranePressureMin = preMembranePressureMin;
  }

  /**
   * Returns the pre-membrane pressure maximum.
   *
   * @return  The pre-membrane pressure.
   */
  public double getPreMembranePressureMax() {
    return preMembranePressureMax;
  }

  /**
   * Sets the pre-membrane pressure maximum.
   *
   * @param preMembranePressureMax  The pre-membrane pressure to set.
   */
  public void setPreMembranePressureMax(double preMembranePressureMax) {
    this.preMembranePressureMax = preMembranePressureMax;
  }

  /**
   * Returns the venous pressure minimum.
   *
   * @return  The venous pressure.
   */
  public double getVenousPressureMin() {
    return venousPressureMin;
  }

  /**
   * Sets the venous pressure minimum.
   *
   * @param venousPressureMin  The venous pressure to set.
   */
  public void setVenousPressureMin(double venousPressureMin) {
    this.venousPressureMin = venousPressureMin;
  }

  /**
   * Returns the venous pressure maximum.
   *
   * @return  The venous pressure.
   */
  public double getVenousPressureMax() {
    return venousPressureMax;
  }

  /**
   * Sets the venous pressure maximum.
   *
   * @param venousPressureMax  The venous pressure to set.
   */
  public void setVenousPressureMax(double venousPressureMax) {
    this.venousPressureMax = venousPressureMax;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  @Override
  public void execute(Game game) {
    // create bubbles in tubing
    PressureMonitorComponent component = (PressureMonitorComponent)game.getEquipment()
                                         .getComponent(PressureMonitorComponent.class);
    component.setPreMembranePressureMin(preMembranePressureMin);
    component.setPreMembranePressureMax(preMembranePressureMax);
    component.setPostMembranePressureMin(postMembranePressureMin);
    component.setPostMembranePressureMax(postMembranePressureMax);
    component.setVenousPressureMin(venousPressureMin);
    component.setVenousPressureMax(venousPressureMax);
  }
  
  /**
   * Returns the string representation.
   * 
   * @return  The string representation.
   */
  @Override
  public String toString() {
    return "Action:PressureMonitor:{" + preMembranePressureMin  + ", " + preMembranePressureMax 
                                      + postMembranePressureMin + ", " + postMembranePressureMax
                                      + venousPressureMin       + ", " + venousPressureMax + "}";    
  }
}
