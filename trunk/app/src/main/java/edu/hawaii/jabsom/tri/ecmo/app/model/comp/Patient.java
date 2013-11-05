package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import king.lib.util.Translator;

/**
 * The patient. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public class Patient  extends Component {

  /** The species. */
  public enum Species { 
    /** Human. */
    HUMAN("human"), 
    /** Pig. */
    PIG("pig");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private Species(String name) {
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
    public static Species parse(String name) {
      for (int i = 0; i < values().length; i++) {
        Species value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };

  /** The gender. */
  public enum Gender { 
    /** Human. */
    MALE("male"), 
    /** Pig. */
    FEMALE("female");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private Gender(String name) {
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
    public static Gender parse(String name) {
      for (int i = 0; i < values().length; i++) {
        Gender value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };

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
  
  
  /** The species. */
  private Species species;
  /** The gender. */
  private Gender gender;
  
  /** 0.0 for dead, 1.0 for fully alive. */
  private double life;
  
  /** The weight in kilograms. */
  private double weight;
  /** The age in months. */
  private double age;
  
  /** The lung function. */
  private LungFunction lungFunction;
  /** The heart function. */
  private HeartFunction heartFunction;
  
  /** True for sedated. */
  private boolean sedated;
  
  /** Patient's blood volume in mL. */
  private double bloodVolume;
  
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
  /** The pCO2 [0, 200]. */
  private double pCO2;
  /** The pO2 [0, 550]. */
  private double pO2;
  /** The ACT values [0, 999]. */
  private double act;

  /** The platelets [0, 1000]. */
  private double platelets;
  /** The WBC [0.0, 100.0]. */
  private double wbc;
  /** The PT in seconds [0, 50]. */
  private double pt;
  /** The PTT in seconds [0, 300]. */
  private double ptt;
  /** The fibrinogen in mg/dl [0, 1000]. */
  private double fibrinogen;

  
  /**
   * Returns the species.
   *
   * @return  The species.
   */
  public Species getSpecies() {
    return species;
  }

  /**
   * Sets the species.
   *
   * @param species  The species.
   */
  public void setSpecies(Species species) {
    this.species = species;
  }
  
  /**
   * Returns the gender.
   *
   * @return  The gender.
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Sets the gender.
   *
   * @param gender  The gender.
   */
  public void setGender(Gender gender) {
    this.gender = gender;
  }

  /**
   * Returns true for alive.
   *
   * @return  True for alive.
   */
  public boolean isAlive() {
    return (this.life > 0.0) || (this.heartRate > 0) || (this.o2Saturation > 0) 
      || (this.systolicBloodPressure > 0);
  }
  
  /**
   * Returns the life.
   *
   * @return  The life where 0.0=dead and 1.0=fully alive.
   */
  public double getLife() {
    return life;
  }

  /**
   * Sets the life.
   *
   * @param life  The life where 0.0=dead and 1.0=fully alive.
   */
  public void setLife(double life) {
    if (life < 0.0) {
      this.life = 0.0;
    }
    else if (life > 1.0) {
      this.life = 1.0;
    }
    else {
      this.life = life;
    }
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
    if (age < 0) {
      this.age = 0;
    }
    else {
      this.age = age;
    }
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
    if (weight < 0) {
      this.weight = 0;
    }
    else {
      this.weight = weight;
    }
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
    if (Double.isNaN(temperature)) {
      this.temperature = 37;
    }
    else if (temperature < 32) {
      this.temperature = 32;
    }
    else if (temperature > 42) {
      this.temperature = 42;
    }
    else {
      this.temperature = temperature;
    }
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
   * Sets the O2 saturation in hundredths.
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
   * Returns the sedated.
   *
   * @return  The sedated.
   */
  public boolean isSedated() {
    return sedated;
  }

  /**
   * Sets the sedated.
   *
   * @param sedated  The sedated.
   */
  public void setSedated(boolean sedated) {
    this.sedated = sedated;
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
   * Returns patient's maximum blood volume usually 90mL/kg.
   *
   * @return  Blood volume in mL.
   */
  public double getMaxBloodVolume() {
    return 90 * weight;
  }

  /**
   * Returns patient's blood volume.
   *
   * @return  Blood volume in mL.
   */
  public double getBloodVolume() {
    return bloodVolume;
  }

  /**
   * Set patient's blood volume. Defaults to 8% of body weight or about
   * 90 mL/kg.
   *
   * @param volume  Volume of blood in patient in mL.
   */
  public void setBloodVolume(double volume) {
    if (Double.isNaN(volume)) {
      this.bloodVolume = getMaxBloodVolume();
    }
    else {
      this.bloodVolume = volume;
    }
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
    if (Double.isNaN(hgb)) {
      this.hgb = 15.0;
    }
    else {
      this.hgb = hgb;
    }
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
    if (Double.isNaN(pCO2)) {
      this.pCO2 = 40;
    }
    else {
      this.pCO2 = pCO2;
    }
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
    if (Double.isNaN(pO2)) {
      this.pO2 = 60;
    }
    else {
      this.pO2 = pO2;
    }
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
    if (Double.isNaN(pH)) {
      this.pH = 7.40;
    }
    else {
      this.pH = pH;
    }
  }
  
  /**
   * Returns the lactate.
   *
   * @return  The lactate.
   */
  public double getLactate() {
    return (-24.99 * pH) + 186.95;
  }

  /**
   * @return the act
   */
  public double getAct() {
    return act + (Math.random() * 5);
  }

  /**
   * @param act the act to set
   */
  public void setAct(double act) {
    if (Double.isNaN(act)) {
      this.act = 170;
    }
    else {
      this.act = act;      
    }
  }

  /**
   * @return the platelets
   */
  public double getPlatelets() {
    return platelets;
  }

  /**
   * @param platelets the platelets to set
   */
  public void setPlatelets(double platelets) {
    if (Double.isNaN(platelets)) {
      this.platelets = 100;
    }
    else {
      this.platelets = platelets;      
    }
  }

  /**
   * @return the wbc
   */
  public double getWbc() {
    return wbc;
  }

  /**
   * @param wbc the wbc to set
   */
  public void setWbc(double wbc) {
    if (Double.isNaN(wbc)) {
      if (weight < 5) {
        this.wbc = 9;
      }
      else if (weight >= 50) {
        this.wbc = 4.5;
      }
      else {
        this.wbc = 5;
      }
    }
    else {
      this.wbc = wbc;      
    }
  }

  /**
   * @return the pt
   */
  public double getPt() {
    return pt;
  }

  /**
   * @param pt the pt to set
   */
  public void setPt(double pt) {
    if (Double.isNaN(pt)) {
      if (weight < 5) {
        this.pt = 13;
      }
      else {
        this.pt = 11;
      }
    }
    else {
      this.pt = pt;      
    }
  }

  /**
   * @return the ptt
   */
  public double getPtt() {
    return ptt;
  }

  /**
   * @param ptt the ptt to set
   */
  public void setPtt(double ptt) {
    if (Double.isNaN(ptt)) {
      this.ptt = 80;
    }
    else {
      this.ptt = ptt;
    }
  }

  /**
   * @return the fibrinogen
   */
  public double getFibrinogen() {
    return fibrinogen;
  }

  /**
   * @param fibrinogen the fibrinogen to set
   */
  public void setFibrinogen(double fibrinogen) {
    if (Double.isNaN(fibrinogen)) {
      if (weight < 5) {
        this.fibrinogen = 125;
      }
      else {
        this.fibrinogen = 200;
      }
    }
    else {
      this.fibrinogen = fibrinogen;
    }
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
    return Translator.getString("text.Patient[i18n]: Patient");
  }
}
