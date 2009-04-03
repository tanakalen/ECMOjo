package edu.hawaii.jabsom.tri.ecmo.app.model;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;

/**
 * The baseline values. 
 *
 * @author   king
 * @since    Jan 6, 2009
 */
public class Baseline {

  /** Baseline value for bleeding. */
  private boolean bleeding;
  /** Baseline value for min heart rate. */
  private double minHeartRate;
  /** Baseline value for max heart rate. */
  private double maxHeartRate;
  /** Baseline value for O2 Saturation min percent. */
  private double minO2Saturation;
  /** Baseline value for O2 Saturation max percent. */
  private double maxO2Saturation; 
  /** Baseline value for min HGB. */
  private double minHgb;
  /** Baseline value for max HGB. */
  private double maxHgb;
  /** Baseline value for min pH. */
  private double minPh;
  /** Baseline value for max pH. */
  private double maxPh;
  /** Baseline value for min pCO2. */
  private double minPco2;
  /** Baseline value for max pCO2. */
  private double maxPcO2;
  /** Baseline value for min ACT. */
  private double minAct;
  /** Baseline value for max ACT. */
  private double maxAct;
  /** Baseline value for min heater temperature. */
  private double minTemperature;
  /** Baseline value for max heater temperature. */
  private double maxTemperature; 
  
  /** The tube function enumeration. */
  public enum TubeFunction { 
    /** Open function. */
    OPEN("Open"), 
    /** Close function. */
    CLOSED("Closed");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private TubeFunction(String name) {
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
     * Returns the tube function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static TubeFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        TubeFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };
  
  /** Baseline value for arterial A. */
  private TubeFunction arterialA;
  /** Baseline value for arterial B. */
  private TubeFunction arterialB;  
  /** Baseline value for venous A. */
  private TubeFunction venousA;
  /** Baseline value for venous B. */
  private TubeFunction venousB;  
  /** Baseline value for bridge. */
  private TubeFunction bridge;   

  /** The cannula function enumeration. */
  public enum CannulaFunction { 
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
    private CannulaFunction(String name) {
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
     * Returns the cannula function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static CannulaFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        CannulaFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };  
  
  /** Baseline value for cannula. */
  private CannulaFunction cannula;
  
  /** The ETT function enumeration. */
  public enum EttFunction { 
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
    private EttFunction(String name) {
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
     * Returns the ETT function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static EttFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        EttFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };  
  
  /** Baseline value for ett. */
  private EttFunction ett;  
  
  /** The suction ETT function enumeration. */
  public enum SuctionEttFunction { 
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
    private SuctionEttFunction(String name) {
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
     * Returns the suction ETT function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static SuctionEttFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        SuctionEttFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };  
  
  /** Baseline value for ett. */
  private SuctionEttFunction suctionEtt;    
  
  /** Baseline value for min pre-membrane pressure. */
  private double minPreMembranePressure;
  /** Baseline value for max pre-membrane pressure. */
  private double maxPreMembranePressure; 

  /** Baseline value for min post-membrane pressure. */
  private double minPostMembranePressure;
  /** Baseline value for max post-membrane pressure. */
  private double maxPostMembranePressure; 

  /** Baseline value for min venous pressure. */
  private double minVenousPressure;
  /** Baseline value for max venous pressure. */
  private double maxVenousPressure; 
  
  /** Baseline value for arterial bubbles. */
  private boolean arterialBubbles;
  /** Baseline value for venous bubbles. */
  private boolean venousBubbles;
  
  /** Baseline value for min fiO2. */
  private double minFiO2;
  /** Baseline value for max fiO2. */
  private double maxFiO2;
  
  /** Baseline value for broken. */
  private boolean broken;
  
  /** The power function enumeration. */
  public enum PowerFunction { 
    /** On function. */
    ON("On"), 
    /** Off function. */
    OFF("Off");
    
    /** The name. */
    private String name;
    
    /**
     * The constructor.
     *
     * @param name  The name.
     */
    private PowerFunction(String name) {
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
     * Returns the power function that matches the name.
     * 
     * @param name  The name.
     * @return  The matching lung function or null for none.
     */
    public static PowerFunction parse(String name) {
      for (int i = 0; i < values().length; i++) {
        PowerFunction value = values()[i];
        if (name.equalsIgnoreCase(values()[i].getName())) {
          return value;
        }
      }
      return null;
    }
  };  
  
  /** Baseline value for power. */
  private PowerFunction power; 
  
  /** Baseline value for bubble detector alarming. */
  private boolean alarming;
  
  /**
   * Returns true if the baseline is reached.
   * 
   * @param game  The game.
   * @return  True, if baseline is reached.
   */
  public boolean isReached(Game game) {
    Patient patient = game.getPatient();
    return (bleeding == patient.isBleeding())
        && (patient.getHeartRate() >= minHeartRate) 
        && (patient.getHeartRate() <= maxHeartRate);   // TODO: add other conditions
  }
  
  /**
   * Returns the alarming.
   *
   * @return The alarming.
   */
  public boolean isAlarming() {
    return alarming;
  }


  /**
   * Sets the alarming.
   *
   * @param alarming The alarming to set.
   */
  public void setAlarming(boolean alarming) {
    this.alarming = alarming;
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
   * Returns the min heart rate.
   *
   * @return  The min heart rate.
   */
  public double getMinHeartRate() {
    return minHeartRate;
  }

  /**
   * Sets the min heart rate.
   *
   * @param minHeartRate  The min heart rate.
   */
  public void setMinHeartRate(double minHeartRate) {
    this.minHeartRate = minHeartRate;
  }

  /**
   * Returns the max heart rate.
   *
   * @return  The max heart rate.
   */
  public double getMaxHeartRate() {
    return maxHeartRate;
  }

  /**
   * Sets the max heart rate.
   *
   * @param maxHeartRate  The max heart rate.
   */
  public void setMaxHeartRate(double maxHeartRate) {
    this.maxHeartRate = maxHeartRate;
  }

  /**
   * Gets the arterialA.
   *
   * @return The arterialA.
   */
  public TubeFunction getArterialA() {
    return arterialA;
  }

  /**
   * Sets the arterialA.
   *
   * @param arterialA The arterialA to set.
   */
  public void setArterialA(TubeFunction arterialA) {
    this.arterialA = arterialA;
  }

  /**
   * Gets the arterialB.
   *
   * @return The arterialB.
   */
  public TubeFunction getArterialB() {
    return arterialB;
  }

  /**
   * Sets the arterialB.
   *
   * @param arterialB The arterialB to set.
   */
  public void setArterialB(TubeFunction arterialB) {
    this.arterialB = arterialB;
  }

  /**
   * Gets the arterialBubbles.
   *
   * @return The arterialBubbles.
   */
  public boolean isArterialBubbles() {
    return arterialBubbles;
  }

  /**
   * Sets the arterialBubbles.
   *
   * @param arterialBubbles The arterialBubbles to set.
   */
  public void setArterialBubbles(boolean arterialBubbles) {
    this.arterialBubbles = arterialBubbles;
  }

  /**
   * Gets the bridge.
   *
   * @return The bridge.
   */
  public TubeFunction getBridge() {
    return bridge;
  }

  /**
   * Sets the bridge.
   *
   * @param bridge The bridge to set.
   */
  public void setBridge(TubeFunction bridge) {
    this.bridge = bridge;
  }

  /**
   * Gets the broken.
   *
   * @return The broken.
   */
  public boolean isBroken() {
    return broken;
  }

  /**
   * Sets the broken.
   *
   * @param broken The broken to set.
   */
  public void setBroken(boolean broken) {
    this.broken = broken;
  }
  
  /**
   * Gets the cannula.
   *
   * @return The cannula.
   */
  public CannulaFunction getCannula() {
    return cannula;
  }

  /**
   * Sets the cannula.
   *
   * @param cannula The cannula to set.
   */
  public void setCannula(CannulaFunction cannula) {
    this.cannula = cannula;
  }

  /**
   * Gets the ett.
   *
   * @return The ett.
   */
  public EttFunction getEtt() {
    return ett;
  }

  /**
   * Sets the ett.
   *
   * @param ett The ett to set.
   */
  public void setEtt(EttFunction ett) {
    this.ett = ett;
  }

  /**
   * Gets the maxAct.
   *
   * @return The maxAct.
   */
  public double getMaxAct() {
    return maxAct;
  }

  /**
   * Sets the maxAct.
   *
   * @param maxAct The maxAct to set.
   */
  public void setMaxAct(double maxAct) {
    this.maxAct = maxAct;
  }

  /**
   * Gets the maxFiO2.
   *
   * @return The maxFiO2.
   */
  public double getMaxFiO2() {
    return maxFiO2;
  }

  /**
   * Sets the maxFiO2.
   *
   * @param maxFiO2 The maxFiO2 to set.
   */
  public void setMaxFiO2(double maxFiO2) {
    this.maxFiO2 = maxFiO2;
  }

  /**
   * Gets the maxHgb.
   *
   * @return The maxHgb.
   */
  public double getMaxHgb() {
    return maxHgb;
  }

  /**
   * Sets the maxHgb.
   *
   * @param maxHgb The maxHgb to set.
   */
  public void setMaxHgb(double maxHgb) {
    this.maxHgb = maxHgb;
  }

  /**
   * Gets the maxO2Saturation.
   *
   * @return The maxO2Saturation.
   */
  public double getMaxO2Saturation() {
    return maxO2Saturation;
  }

  /**
   * Sets the maxO2Saturation.
   *
   * @param maxO2Saturation The maxO2Saturation to set.
   */
  public void setMaxO2Saturation(double maxO2Saturation) {
    this.maxO2Saturation = maxO2Saturation;
  }

  /**
   * Gets the maxPcO2.
   *
   * @return The maxPcO2.
   */
  public double getMaxPcO2() {
    return maxPcO2;
  }

  /**
   * Sets the maxPcO2.
   *
   * @param maxPcO2 The maxPcO2 to set.
   */
  public void setMaxPcO2(double maxPcO2) {
    this.maxPcO2 = maxPcO2;
  }

  /**
   * Gets the maxPh.
   *
   * @return The maxPh.
   */
  public double getMaxPh() {
    return maxPh;
  }

  /**
   * Sets the maxPh.
   *
   * @param maxPh The maxPh to set.
   */
  public void setMaxPh(double maxPh) {
    this.maxPh = maxPh;
  }

  /**
   * Gets the maxPostMembranePressure.
   *
   * @return The maxPostMembranePressure.
   */
  public double getMaxPostMembranePressure() {
    return maxPostMembranePressure;
  }

  /**
   * Sets the maxPostMembranePressure.
   *
   * @param maxPostMembranePressure The maxPostMembranePressure to set.
   */
  public void setMaxPostMembranePressure(double maxPostMembranePressure) {
    this.maxPostMembranePressure = maxPostMembranePressure;
  }

  /**
   * Gets the maxPreMembranePressure.
   *
   * @return The maxPreMembranePressure.
   */
  public double getMaxPreMembranePressure() {
    return maxPreMembranePressure;
  }

  /**
   * Sets the maxPreMembranePressure.
   *
   * @param maxPreMembranePressure The maxPreMembranePressure to set.
   */
  public void setMaxPreMembranePressure(double maxPreMembranePressure) {
    this.maxPreMembranePressure = maxPreMembranePressure;
  }

  /**
   * Gets the maxTemperature.
   *
   * @return The maxTemperature.
   */
  public double getMaxTemperature() {
    return maxTemperature;
  }

  /**
   * Sets the maxTemperature.
   *
   * @param maxTemperature The maxTemperature to set.
   */
  public void setMaxTemperature(double maxTemperature) {
    this.maxTemperature = maxTemperature;
  }

  /**
   * Gets the maxVenousPressure.
   *
   * @return The maxVenousPressure.
   */
  public double getMaxVenousPressure() {
    return maxVenousPressure;
  }

  /**
   * Sets the maxVenousPressure.
   *
   * @param maxVenousPressure The maxVenousPressure to set.
   */
  public void setMaxVenousPressure(double maxVenousPressure) {
    this.maxVenousPressure = maxVenousPressure;
  }

  /**
   * Gets the minAct.
   *
   * @return The minAct.
   */
  public double getMinAct() {
    return minAct;
  }

  /**
   * Sets the minAct.
   *
   * @param minAct The minAct to set.
   */
  public void setMinAct(double minAct) {
    this.minAct = minAct;
  }

  /**
   * Gets the minFiO2.
   *
   * @return The minFiO2.
   */
  public double getMinFiO2() {
    return minFiO2;
  }

  /**
   * Sets the minFiO2.
   *
   * @param minFiO2 The minFiO2 to set.
   */
  public void setMinFiO2(double minFiO2) {
    this.minFiO2 = minFiO2;
  }

  /**
   * Gets the minHgb.
   *
   * @return The minHgb.
   */
  public double getMinHgb() {
    return minHgb;
  }

  /**
   * Sets the minHgb.
   *
   * @param minHgb The minHgb to set.
   */
  public void setMinHgb(double minHgb) {
    this.minHgb = minHgb;
  }

  /**
   * Gets the minO2Saturation.
   *
   * @return The minO2Saturation.
   */
  public double getMinO2Saturation() {
    return minO2Saturation;
  }

  /**
   * Sets the minO2Saturation.
   *
   * @param minO2Saturation The minO2Saturation to set.
   */
  public void setMinO2Saturation(double minO2Saturation) {
    this.minO2Saturation = minO2Saturation;
  }

  /**
   * Gets the minPco2.
   *
   * @return The minPco2.
   */
  public double getMinPco2() {
    return minPco2;
  }

  /**
   * Sets the minPco2.
   *
   * @param minPco2 The minPco2 to set.
   */
  public void setMinPco2(double minPco2) {
    this.minPco2 = minPco2;
  }

  /**
   * Gets the minPh.
   *
   * @return The minPh.
   */
  public double getMinPh() {
    return minPh;
  }

  /**
   * Sets the minPh.
   *
   * @param minPh The minPh to set.
   */
  public void setMinPh(double minPh) {
    this.minPh = minPh;
  }

  /**
   * Gets the minPostMembranePressure.
   *
   * @return The minPostMembranePressure.
   */
  public double getMinPostMembranePressure() {
    return minPostMembranePressure;
  }

  /**
   * Sets the minPostMembranePressure.
   *
   * @param minPostMembranePressure The minPostMembranePressure to set.
   */
  public void setMinPostMembranePressure(double minPostMembranePressure) {
    this.minPostMembranePressure = minPostMembranePressure;
  }

  /**
   * Gets the minPreMembranePressure.
   *
   * @return The minPreMembranePressure.
   */
  public double getMinPreMembranePressure() {
    return minPreMembranePressure;
  }

  /**
   * Sets the minPreMembranePressure.
   *
   * @param minPreMembranePressure The minPreMembranePressure to set.
   */
  public void setMinPreMembranePressure(double minPreMembranePressure) {
    this.minPreMembranePressure = minPreMembranePressure;
  }

  /**
   * Gets the minTemperature.
   *
   * @return The minTemperature.
   */
  public double getMinTemperature() {
    return minTemperature;
  }

  /**
   * Sets the minTemperature.
   *
   * @param minTemperature The minTemperature to set.
   */
  public void setMinTemperature(double minTemperature) {
    this.minTemperature = minTemperature;
  }

  /**
   * Gets the minVenousPressure.
   *
   * @return The minVenousPressure.
   */
  public double getMinVenousPressure() {
    return minVenousPressure;
  }

  /**
   * Sets the minVenousPressure.
   *
   * @param minVenousPressure The minVenousPressure to set.
   */
  public void setMinVenousPressure(double minVenousPressure) {
    this.minVenousPressure = minVenousPressure;
  }

  /**
   * Gets the power.
   *
   * @return The power.
   */
  public PowerFunction getPower() {
    return power;
  }

  /**
   * Sets the power.
   *
   * @param power The power to set.
   */
  public void setPower(PowerFunction power) {
    this.power = power;
  }

  /**
   * Gets the suctionEtt.
   *
   * @return The suctionEtt.
   */
  public SuctionEttFunction getSuctionEtt() {
    return suctionEtt;
  }

  /**
   * Sets the suctionEtt.
   *
   * @param suctionEtt The suctionEtt to set.
   */
  public void setSuctionEtt(SuctionEttFunction suctionEtt) {
    this.suctionEtt = suctionEtt;
  }

  /**
   * Gets the venousA.
   *
   * @return The venousA.
   */
  public TubeFunction getVenousA() {
    return venousA;
  }

  /**
   * Sets the venousA.
   *
   * @param venousA The venousA to set.
   */
  public void setVenousA(TubeFunction venousA) {
    this.venousA = venousA;
  }

  /**
   * Gets the venousB.
   *
   * @return The venousB.
   */
  public TubeFunction getVenousB() {
    return venousB;
  }

  /**
   * Sets the venousB.
   *
   * @param venousB The venousB to set.
   */
  public void setVenousB(TubeFunction venousB) {
    this.venousB = venousB;
  }

  /**
   * Gets the venousBubbles.
   *
   * @return The venousBubbles.
   */
  public boolean isVenousBubbles() {
    return venousBubbles;
  }

  /**
   * Sets the venousBubbles.
   *
   * @param venousBubbles The venousBubbles to set.
   */
  public void setVenousBubbles(boolean venousBubbles) {
    this.venousBubbles = venousBubbles;
  }
  
}
