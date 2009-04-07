package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent;

/**
 * The oxigenator action. 
 *
 * @author   Christoph Aschwanden
 * @since    October 1, 2008
 */
public class OxigenatorAction extends Action {

  /** The total sweep integer. total sweep = integer + decimal / 10 [0.0, 10.0]. */
  private int totalSweepInteger = -1;
  /** The total sweep decimal. total sweep = integer + decimal / 10 [0.0, 10.0]. */
  private int totalSweepDecimal = -1;
  /** The oxygen concentration in percent / 100 [0.00, 1.00]. */
  private double fiO2;

  /**
   * Returns the oxygen concentration in percent.
   * 
   * @return  The fiO2 value.
   */
  public double getFiO2() {
    return fiO2;
  }

  /**
   * Sets the oxygen concentration in percent.
   * 
   * @param fiO2  The fiO2 value to set.
   */
  public void setFiO2(double fiO2) {
    this.fiO2 = fiO2;
  }

  /**
   * Returns the totalSweepInteger.
   *
   * @return The totalSweepInteger.
   */
  public double getTotalSweep() {
    return (double) totalSweepInteger + (double) totalSweepDecimal / 10;
  }
  
  /**
   * Sets the total sweep.
   *
   * @param totalSweep  The total sweep.
   */
  public void setTotalSweep(double totalSweep) {
    this.totalSweepInteger = (int) totalSweep;
    this.totalSweepDecimal = (int) (totalSweep - (int) totalSweep) * 10;
  }

  /**
   * Returns the totalSweepInteger.
   *
   * @return The totalSweepInteger.
   */
  public int getTotalSweepInteger() {
    return totalSweepInteger;
  }
  
  /**
   * Sets the totalSweepInteger.
   *
   * @param totalSweepInteger The totalSweepInteger to set.
   */
  public void setTotalSweepInteger(int totalSweepInteger) {
    this.totalSweepInteger = totalSweepInteger;
  }

  /**
   * Returns the totalSweepDecimal.
   *
   * @return The totalSweepDecimal.
   */
  public int getTotalSweepDecimal() {
    return totalSweepDecimal;
  }

  /**
   * Sets the totalSweepDecimal.
   *
   * @param totalSweepDecimal The totalSweepDecimal to set.
   */
  public void setTotalSweepDecimal(int totalSweepDecimal) {
    this.totalSweepDecimal = totalSweepDecimal;
  }
  
  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // sets the oxigenator
    OxigenatorComponent component = (OxigenatorComponent)game.getEquipment().getComponent(OxigenatorComponent.class);
    if (totalSweepInteger != -1) {
      component.setTotalSweepInteger(totalSweepInteger);
    }
    if (totalSweepDecimal != -1) {
      component.setTotalSweepDecimal(totalSweepDecimal);      
    }
    component.setFiO2(fiO2);
  }
}
