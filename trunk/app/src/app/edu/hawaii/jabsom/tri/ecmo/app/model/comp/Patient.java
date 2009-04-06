package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

/**
 * The patient. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public class Patient  extends Component {

  /** The lung function enumeration. */
  public enum LungFunction { 
    /** Good function. */
    GOOD("Good"), 
    /** Bad function. */
    BAD("Bad");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private LungFunction(String name) {
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
     * Returns the lung function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static LungFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        LungFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };
  
  /** The heart function enumeration. */
  public enum HeartFunction { 
    /** Good function. */
    GOOD("Good"), 
    /** Bad function. */
    BAD("Bad");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private HeartFunction(String name) {
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
     * Returns the heart function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching heart function or null for none.
     */
    public static HeartFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        HeartFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };  
  
  
  /** True for alive. */
  private boolean alive;
  
  /** The weight in kilograms. */
  private double weight;
  /** The age in months. */
  private double age;
  
  /** The lung function. */
  private LungFunction lungFunction;
  /** The heart function. */
  private HeartFunction heartFunction;
  
  /** True if patient is bleeding. */
  private boolean bleeding;
  
  /** Patient's temperature in centigrade [32.0, 42.0]. */
  private double temperature;
  /** The heart rate [0, 300]. */
  private double heartRate;
  /** The respiratory rate [0, 100]. */
  private double respiratoryRate;
  /** The O2 saturation in percent / 100 [0.00, 1.00]. */
  private double o2Saturation;
  /** The systolic blood pressure in mm Hg [0, 200]. */
  private double systolicBloodPressure;
  /** The central venous pressure in cm water [0, 50]. */
  private double centralVenousPressure;
  /** The urine output in mL/kg/h [0, 10]. */
  private double urineOutput;
  /** The Hgb value [0.0, 50.0]. */
  private double hgb;
  /** The pH [0.00, 14.00]. */
  private double pH;
  /** The lactate value [0.5, 1.9]. */
  private double lactate;
  /** The pCO2 [0, 200]. */
  private double pCO2;
  /** The pO2 [0, 550]. */
  private double pO2;
  /** The ACT values [0, 999]. */
  private double act;

  
  /**
   * Returns true for alive.
   *
   * @return  True for alive.
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Set to true for alive.
   *
   * @param alive  True for alive.
   */
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Returns the age in months.
   *
   * @return  The age.
   */
  public double getAge() {
    return age;
  }
  
  /**
   * Sets the age in months.
   *
   * @param age  The age to set.
   */
  public void setAge(double age) {
    this.age = age;
  }
  
  /**
   * Returns the weight.
   *
   * @return  The weight.
   */
  public double getWeight() {
    return weight;
  }
  
  /**
   * Sets the weight.
   *
   * @param weight  The weight to set.
   */
  public void setWeight(double weight) {
    this.weight = weight;
  }
  
  /**
   * Returns the diastolic blood pressure.
   * 
   * @return  The diastolic blood pressure.
   */
  public double getDiastolicBloodPressure() {
    return 0.7 * getSystolicBloodPressure();
  }
  
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
   * Returns the heart rate.
   * 
   * @return  The heart rate.
   */
  public double getHeartRate() {
    return heartRate;
  }
  
  /**
   * Sets the heart rate.
   * 
   * @param heartRate  The heart rate to set.
   */
  public void setHeartRate(double heartRate) {
    this.heartRate = heartRate;
  }
  
  /**
   * Returns the respiratory rate.
   * 
   * @return respiratoryRate  The respiratory rate to get.
   */
  public double getRespiratoryRate() {
    return respiratoryRate;
  }

  /**
   * Sets the respiratory rate.
   * 
   * @param respiratoryRate  The respiratory rate to set.
   */
  public void setRespiratoryRate(double respiratoryRate) {
    this.respiratoryRate = respiratoryRate;
  }

  /**
   * Returns the O2 saturation.
   * 
   * @return The o2Saturation.
   */
  public double getO2Saturation() {
    return o2Saturation;
  }
  
  /**
   * Sets the O2 saturation.
   * 
   * @param saturation  The o2Saturation to set.
   */
  public void setO2Saturation(double saturation) {
    o2Saturation = saturation;
    notifyUpdate();
  }
  
  /**
   * Returns the systolic blood pressure.
   * 
   * @return the systolic blood pressure.
   */
  public double getSystolicBloodPressure() {
    return systolicBloodPressure;
  }
  
  /**
   * Sets the systolic blood pressure.
   * 
   * @param systolicBloodPressure  The systolic blood pressure to set.
   */
  public void setSystolicBloodPressure(double systolicBloodPressure) {
    this.systolicBloodPressure = systolicBloodPressure;
  }

  /**
   * Returns the central venous pressure.
   * 
   * @return centralVenousPressure  The central venous pressure to get.
   */
  public double getCentralVenousPressure() {
    return centralVenousPressure;
  }

  /**
   * Sets the central venous pressure.
   * 
   * @param centralVenousPressure  The central venous pressure to set.
   */
  public void setCentralVenousPressure(double centralVenousPressure) {
    this.centralVenousPressure = centralVenousPressure;
  }

  /**
   * Returns the urine output.
   * 
   * @return urineOutput  The urine output to get.
   */
  public double getUrineOutput() {
    return urineOutput;
  }

  /**
   * Sets the urine output.
   * 
   * @param urineOutput  The urine output to set.
   */
  public void setUrineOutput(double urineOutput) { //should we give absolute and convert?
    this.urineOutput = urineOutput;
  }

  /**
   * Returns the heart function.
   * 
   * @return  The heart function.
   */
  public HeartFunction getHeartFunction() {
    return heartFunction;
  }

  /**
   * Sets the heart function.
   * 
   * @param heartFunction  The heart function to set.
   */
  public void setHeartFunction(HeartFunction heartFunction) {
    this.heartFunction = heartFunction;
  }

  /**
   * Returns the lung function.
   * 
   * @return  The lung function.
   */
  public LungFunction getLungFunction() {
    return lungFunction;
  }

  /**
   * Sets the lung function.
   * 
   * @param lungFunction  The lung function to set.
   */
  public void setLungFunction(LungFunction lungFunction) {
    this.lungFunction = lungFunction;
  }
  
  /**
   * Returns true for bleeding.
   *
   * @return  True for bleeding.
   */
  public boolean isBleeding() {
    return bleeding;
  }

  /**
   * Set true for bleeding.
   *
   * @param bleeding  True for bleeding.
   */
  public void setBleeding(boolean bleeding) {
    this.bleeding = bleeding;
  }

  /**
   * Returns the Hct value.
   * 
   * @return The hct from [0.0, 100.0].
   */
  public double getHct() {
    double hct = 3 * getHgb();
    if (hct > 100) {
      hct = 100;
    }
    return hct;
  }
  
  /**
   * Returns the Hgb value.
   * 
   * @return the hgb
   */
  public double getHgb() {
    return hgb;
  }
  
  /**
   * Sets the Hgb value.
   * 
   * @param hgb the Hgb to set
   */
  public void setHgb(double hgb) {
    this.hgb = hgb;
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
   * Returns the lactate.
   *
   * @return  The lactate.
   */
  public double getLactate() {
    return lactate;
  }

  /**
   * Sets the lactate.
   *
   * @param lactate  The lactate.
   */
  public void setLactate(double lactate) {
    this.lactate = lactate;
  }

  /**
   * @return the act
   */
  public double getAct() {
    return act;
  }

  /**
   * @param act the act to set
   */
  public void setAct(double act) {
    this.act = act;
  }

  /**
   * Returns the BE value.
   * 
   * @return the bE
   */
  public double getBE() {
    return (getHCO3() - 24.4 + (2.3 * getHgb()) * (getPH() - 7.4)) * (1 - 0.023 * getHgb());
  }
  
  /**
   * Returns the HCO3 value.
   * 
   * @return the hCO3
   */
  public double getHCO3() {
    return 0.03 * getPCO2() * Math.pow(10, getPH() - 6.1);
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Patient";
  }
}
