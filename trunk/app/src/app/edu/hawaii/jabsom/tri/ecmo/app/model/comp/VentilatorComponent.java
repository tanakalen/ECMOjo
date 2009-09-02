package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import java.io.Serializable;


/**
 * The ventilator component. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class VentilatorComponent extends Component {

  /** The ventilator subtype. */
  public abstract static class Subtype implements Serializable {
    
    /**
     * Returns the mean pressure (or MAP).
     *
     * @param ventilator  The ventilator. 
     * @return  The mean pressure.
     */
    abstract double getMeanPressure(VentilatorComponent ventilator);
    
    /**
     * Returns the name. 
     * 
     * @return  The name.
     */
    abstract String getName();
  }
  
  /** The conventional subtype. */
  public static class ConventionalSubtype extends Subtype {
    
    /** PIP: Peak inspiratory pressure [5, 50]. */ 
    private double pip = 25;
    /** PEEP: Peak end expiratory pressure [0, 20]. */
    private double peep = 10;
    /** The rate [0, 100]. */
    private double rate = 50;
    
    
    /**
     * Returns the mean pressure (or MAP).
     *
     * @param ventilator  The ventilator.
     * @return  The mean pressure.
     */
    @Override
    public double getMeanPressure(VentilatorComponent ventilator) {
      return (rate * ventilator.getITime() / 60 * (pip -peep)) + peep;
    }

    /**
     * Returns the PEEP value.
     * 
     * @return The PEEP value.
     */
    public double getPeep() {
      return peep;
    }

    /**
     * Sets the PEEP value.
     * 
     * @param peep  The PEEP to set.
     */
    public void setPeep(double peep) {
      this.peep = peep;
    }

    /**
     * Returns the PIP value.
     * 
     * @return  The PIP value.
     */
    public double getPip() {
      return pip;
    }

    /**
     * Sets the PIP value.
     * 
     * @param pip The PIP to set
     */
    public void setPip(double pip) {
      this.pip = pip;
    }

    /**
     * Returns the rate.
     * 
     * @return  The rate.
     */
    public double getRate() {
      return rate;
    }

    /**
     * Sets the rate.
     * 
     * @param rate  The rate to set.
     */
    public void setRate(double rate) {
      this.rate = rate;
    }

    /**
     * Returns the name of the component.
     * 
     * @return  The name.
     */
    public String getName() {
      return "Conventional Ventilator";
    }
  }
  
  /** The high frequency subtype. */
  public static class HighFrequencySubtype extends Subtype {
    
    /** The mean pressure [5.0, 50.0]. */
    private double meanPressure = 25;
    /** The hertz value [0, 100]. */
    private double hz = 10;
    /** The amplitude [0, 100]. */
    private double amp = 40;
    
    
    /**
     * Returns the mean pressure (or MAP).
     *
     * @param ventilator  The ventilator.
     * @return  The mean pressure.
     */
    @Override
    public double getMeanPressure(VentilatorComponent ventilator) {
      return meanPressure;
    }

    /**
     * Returns the mean pressure.
     *
     * @return  The mean pressure.
     */
    public double getMeanPressure() {
      return meanPressure;
    }

    /**
     * Sets the mean pressure.
     *
     * @param meanPressure  The mean pressure.
     */
    public void setMeanPressure(double meanPressure) {
      this.meanPressure = meanPressure;
    }

    /**
     * Returns the amplitude.
     *
     * @return  The amplitude.
     */
    public double getAmp() {
      return amp;
    }
    
    /**
     * Sets the amplitude.
     *
     * @param amp  The amplitude to set.
     */
    public void setAmp(double amp) {
      this.amp = amp;
    }
    
    /**
     * Returns the hz.
     *
     * @return  The hz.
     */
    public double getHz() {
      return hz;
    }
    
    /**
     * Sets the hz.
     *
     * @param hz  The hz to set.
     */
    public void setHz(double hz) {
      this.hz = hz;
    }  
    
    /**
     * Returns the name of the component.
     * 
     * @return  The name.
     */
    @Override
    public String getName() {
      return "High Frequency Ventilator";
    }
  }

  /** The ventilator type. */
  private Subtype subtype;
  
  /** True for emergency function, false otherwise. */
  private boolean emergencyFuction;
  
  /** iTime value [0.0, 2.0]. */
  private double iTime;
  /** The oxygen conzentration in percent / 100 [0.00, 1.00]. */
  private double fiO2;

  
  /**
   * Returns the subtype.
   *
   * @return  The subtype.
   */
  public Subtype getSubtype() {
    return subtype;
  }

  /**
   * Sets the subtype.
   *
   * @param subtype  The subtype.
   */
  public void setSubtype(Subtype subtype) {
    this.subtype = subtype;
  }

  /**
   * Returns the mean pressure (or MAP).
   *
   * @return  The mean pressure.
   */
  public double getMeanPressure() {
    return subtype.getMeanPressure(this);
  }
  
  /**
   * Returns the iTime.
   *
   * @return  The iTime.
   */
  public double getITime() {
    return iTime;
  }

  /**
   * Sets the iTime.
   *
   * @param iTime  The iTime to set.
   */
  public void setITime(double iTime) {
    this.iTime = iTime;
    notifyUpdate();
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
   * Returns true for emergency fuction.
   *
   * @return  True for emergency function.
   */
  public boolean isEmergencyFuction() {
    return emergencyFuction;
  }

  /**
   * Sets if emergency fuction.
   *
   * @param emergencyFuction  True for emergency function.
   */
  public void setEmergencyFuction(boolean emergencyFuction) {
    this.emergencyFuction = emergencyFuction;
  }
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return subtype.getName();
  }
}
