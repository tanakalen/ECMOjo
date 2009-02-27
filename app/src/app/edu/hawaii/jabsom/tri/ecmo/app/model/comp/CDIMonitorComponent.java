package edu.hawaii.jabsom.tri.ecmo.app.model.comp;


/**
 * The CDI monitor component for the patient. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class CDIMonitorComponent extends Component {
  
  /** The pH [0.00, 14.00]. */
  private double pH;
  /** The pCO2 [0, 200]. */
  private double pCO2;
  /** The pO2 [0, 550]. */
  private double pO2;
  /** The HCO3 [0, 50]. */
  private double hCO3;
  /** The temperature in centigrade [0.0, 100.0]. */
  private double temperature;
  /** The BE [-50, 50]. */
  private double bE;
  /** The arterial O2 saturation (red) in percent / 100 [0.00, 1.00]. */
  private double sa02;
  
  /** The HCT value [0.0, 100.0]. */
  private double hct;
  /** The Hgb value [0.0, 50.0]. */
  private double hgb;
  /** The venous O2 saturation (blue) in percent / 100 [0.00, 1.00]. */
  private double sv02;
  
  
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
    notifyUpdate();
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
    notifyUpdate();
  }
  
  /**
   * Returns the Hct value.
   * 
   * @return the hct
   */
  public double getHct() {
    return hct;
  }
  
  /**
   * Sets the Hct value.
   * 
   * @param hct the Hct to set
   */
  public void setHct(double hct) {
    this.hct = hct;
    notifyUpdate();
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
    notifyUpdate();
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
    notifyUpdate();
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
    notifyUpdate();
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
    notifyUpdate();
  }
  
  /**
   * Returns the venous O2 saturation value.
   * 
   * @return  The O2 value.
   */
  public double getSvO2() {
    return sv02;
  }
  
  /**
   * Sets the venous O2 saturation value.
   * 
   * @param sv02  The O2 to set.
   */
  public void setSvO2(double sv02) {
    this.sv02 = sv02;
    notifyUpdate();
  }
  
  /**
   * Returns the arterial O2 saturation value.
   * 
   * @return  The O2 value.
   */
  public double getSaO2() {
    return sa02;
  }
  
  /**
   * Sets the arterial O2 saturation value.
   * 
   * @param sa02  The O2 to set.
   */
  public void setSaO2(double sa02) {
    this.sa02 = sa02;
    notifyUpdate();
  }

  /**
   * Returns the temperature.
   * 
   * @return the temperature
   */
  public double getTemperature() {
    return temperature;
  }
  
  /**
   * Sets the temperature.
   * 
   * @param temperature the temperature to set
   */
  public void setTemperature(double temperature) {
    this.temperature = temperature;
    notifyUpdate();
  }  
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "CDI";
  }
}
