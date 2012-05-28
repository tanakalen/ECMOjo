package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

/**
 * The blood gas lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class BloodGasLabTest extends LabTest {
  
  /** The Blood Gas Type enumeration. */
  public enum BloodGasType { 
    /** Baby blood gas. */
    BABY("Baby"), 
    /** Pump blood gas pre-oxygenator. */
    PRE("Pre"),
    /** Pump blood gas post-oxygenator. */
    POST("Post");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private BloodGasType(String name) {
      this.name = name;
    }
    
    /**
     * Returns the name.
     * 
     * @return  The name.
     */
    public String getName() {
      return name;
    }
    
    /**
     * Returns the blood gas type that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static BloodGasType parse(String name) {
      for (int i = 0; i < values().length; i++) {
        BloodGasType value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };
  
  /** The blood gas type [baby, pre, post]. */
  private BloodGasType bloodGasType;
  /** The pH [0.00, 14.00]. */
  private double pH;
  /** The pCO2 [0, 200]. */
  private double pCO2;
  /** The pO2 [0, 550]. */
  private double pO2;
  /** The HCO3 [0, 50]. */
  private double hCO3;
  /** The BE [-50, 50]. */
  private double bE;

  
  /**
   * Returns the blood gas type.
   * 
   * @return  The blood gas type.
   */
  public BloodGasType getBloodGasType() {
    return bloodGasType;
  }

  /**
   * Sets the blood gas type.
   * 
   * @param bloodGasType  The bloodGasType to set.
   */
  public void setBloodGasType(BloodGasType bloodGasType) {
    this.bloodGasType = bloodGasType;
  }
  
  /**
   * Returns the BE value.
   * 
   * @return the bE
   */
  public double getBE() {
    return bE;
  }
  
  /**
   * Sets the BE value.
   * 
   * @param bE the BE to set
   */
  public void setBE(double bE) {
    this.bE = bE;
  }
  
  /**
   * Returns the HCO3 value.
   * 
   * @return the hCO3
   */
  public double getHCO3() {
    return hCO3;
  }
  
  /**
   * Sets the HCO3 value.
   * 
   * @param hCO3 the HCO3 to set
   */
  public void setHCO3(double hCO3) {
    this.hCO3 = hCO3;
  }
    
  /**
   * Returns the pCO2 value.
   * 
   * @return the pCO2
   */
  public double getPCO2() {
    return pCO2;
  }
  
  /**
   * Gets the BE value.
   * 
   * @param pCO2 the pCO2 to set
   */
  public void setPCO2(double pCO2) {
    this.pCO2 = pCO2;
  }
  
  /**
   * Returns the pH value.
   * 
   * @return the pH
   */
  public double getPH() {
    return pH;
  }
  
  /**
   * Sets the pH value.
   * 
   * @param pH the pH to set
   */
  public void setPH(double pH) {
    this.pH = pH;
  }
  
  /**
   * Returns the pO2 value.
   * 
   * @return the pO2
   */
  public double getPO2() {
    return pO2;
  }
  
  /**
   * Sets the pO2 value.
   * 
   * @param pO2 the pO2 to set
   */
  public void setPO2(double pO2) {
    this.pO2 = pO2;
  }
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Blood Gas";
  }
}
