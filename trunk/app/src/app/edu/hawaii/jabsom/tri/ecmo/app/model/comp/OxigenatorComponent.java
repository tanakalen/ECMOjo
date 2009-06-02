package edu.hawaii.jabsom.tri.ecmo.app.model.comp;


/**
 * The oxigenator component. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class OxigenatorComponent extends Component {
  
  /** The available types. */
  public enum OxiType { SCI_MED, QUADROX_D };
  
  /** The oxigenator type. */
  private OxiType oxiType;
  
  /** The total sweep integer. total sweep = integer + decimal / 10 [0.0, 10.0]. */
  private int totalSweepInteger;
  /** The total sweep decimal. total sweep = integer + decimal / 10 [0.0, 10.0]. */
  private int totalSweepDecimal;
  /** The oxygen conzentration in percent / 100 [0.00, 1.00]. */
  private double fiO2;

  /** Clotting (i.e. number of platelets at wrong place). */
  private double clotting;
  
  /** True if the component is broken. */
  private boolean broken;
  
  
  /**
   * Returns the oxi type.
   *
   * @return  The oxi type.
   */
  public OxiType getOxiType() {
    return oxiType;
  }

  /**
   * Sets the oxi type.
   *
   * @param oxiType  The oxi type.
   */
  public void setOxiType(OxiType oxiType) {
    this.oxiType = oxiType;
  }

  /**
   * Returns the total sweep.
   *
   * @return  The total sweep.
   */
  public double getTotalSweep() {
    return (double) totalSweepInteger + (double) totalSweepDecimal / 10.0;
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
   * Returns the oxygen conzentration in percent.
   * 
   * @return  The fiO2 value.
   */
  public double getFiO2() {
    return fiO2;
  }

  /**
   * Sets the oxygen conzentration in percent.
   * 
   * @param fiO2  The fiO2 value to set.
   */
  public void setFiO2(double fiO2) {
    this.fiO2 = fiO2;
    notifyUpdate();
  }
  
  /**
   * @return the clotting
   */
  public double getClotting() {
    return clotting;
  }

  /**
   * @param clotting the clotting to set
   */
  public void setClotting(double clotting) {
    this.clotting = clotting;
  }
  
  /**
   * Returns true for broken.
   *
   * @return  True for broken.
   */
  public boolean isBroken() {
    return broken;
  }

  /**
   * Set true for broken.
   *
   * @param broken  True for broken.
   */
  public void setBroken(boolean broken) {
    this.broken = broken;
  }

  /**
   * Returns true for alarm.
   *
   * @return  True for alarm.
   */
  public boolean isAlarm() {
    return false;
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
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    if (oxiType == OxiType.QUADROX_D) {
      return "Quadrox D Oxigenator";
    }
    else {
      return "SciMed Oxigenator";
    }
  }
}