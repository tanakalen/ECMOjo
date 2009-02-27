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

  /** The total sweep [0.0, 10.0]. */
  private double totalSweep;
  /** The oxygen concentration in percent / 100 [0.00, 1.00]. */
  private double fiO2;

  
  /**
   * Returns the total sweep.
   *
   * @return  The total sweep.
   */
  public double getTotalSweep() {
    return totalSweep;
  }

  /**
   * Sets the total sweep.
   *
   * @param totalSweep  The total sweep.
   */
  public void setTotalSweep(double totalSweep) {
    this.totalSweep = totalSweep;
  }

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
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // sets the oxigenator
    OxigenatorComponent component = (OxigenatorComponent)game.getEquipment().getComponent(OxigenatorComponent.class);
    component.setTotalSweep(totalSweep);
    component.setFiO2(fiO2);
  }
}
