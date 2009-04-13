package edu.hawaii.jabsom.tri.ecmo.app.model.comp;


/**
 * The tube component. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class TubeComponent extends Component {
  
  /** Available ECMO modes. */
  public enum Mode { VA, VV };
  
  /** The ECMO mode. */
  private Mode mode;
  
  /** True if the bridge is open. */
  private boolean bridgeOpen;
  /** True if arterial position A open. */
  private boolean arterialAOpen;
  /** True if arterial position B open. */
  private boolean arterialBOpen;
  /** True if venus position A open. */
  private boolean venusAOpen;
  /** True if venus position B open. */
  private boolean venusBOpen;
  
  /** True if cannula site is broken. */
  private boolean brokenCannula;
  /** True for broken ETT. */
  private boolean brokenETT;
  /** True for suction problem ETT. */
  private boolean suctionETT;

  /** The pre-membrane pressure in torr [0, 900]. */
  private double preMembranePressure;
  /** The post-membrane pressure in torr [0, 900]. */
  private double postMembranePressure;
  /** The venous pressure in torr [-50, 100]. */
  private double venousPressure;

  /** True for arterial bubbles. */
  private boolean arterialBubbles;
  /** True for venus bubbles. */
  private boolean venusBubbles;
  
  /** The arterial O2 saturation (red) in percent / 100 [0.00, 1.00]. */
  private double saO2;
  /** Post-oxygenator pH [0, 14]. */
  private double postPH;
  /** Post-oxygenator pCO2 [0, 200]. */
  private double postPCO2;

  /** The venous O2 saturation (blue) in percent / 100 [0.00, 1.00]. */
  private double svO2;
  /** Pre-oxygenator pH [0, 14]. */
  private double prePH;
  /** Pre-oxygenator pCO2 [0, 200]. */
  private double prePCO2;
  
  
  /**
   * Returns the mode.
   *
   * @return  The mode.
   */
  public Mode getMode() {
    return mode;
  }

  /**
   * Sets the mode.
   *
   * @param mode  The mode.
   */
  public void setMode(Mode mode) {
    this.mode = mode;
  }

  /**
   * Returns true if arterial A is open.
   *
   * @return  True if open.
   */
  public boolean isArterialAOpen() {
    return arterialAOpen;
  }
  
  /**
   * Set true if arterial A is open.
   *
   * @param arterialAOpen  True for open.
   */
  public void setArterialAOpen(boolean arterialAOpen) {
    this.arterialAOpen = arterialAOpen;
    notifyUpdate();
  }
  
  /**
   * Returns true if arterial B is open.
   *
   * @return  True if open.
   */
  public boolean isArterialBOpen() {
    return arterialBOpen;
  }
  
  /**
   * Sets true if arterial B is open.
   *
   * @param arterialBOpen  True for open.
   */
  public void setArterialBOpen(boolean arterialBOpen) {
    this.arterialBOpen = arterialBOpen;
    notifyUpdate();
  }
  
  /**
   * Returns true if bridge is open.
   *
   * @return  True if open.
   */
  public boolean isBridgeOpen() {
    return bridgeOpen;
  }
  
  /**
   * Set true if bridge is open.
   *
   * @param bridgeOpen  True for open.
   */
  public void setBridgeOpen(boolean bridgeOpen) {
    this.bridgeOpen = bridgeOpen;
    notifyUpdate();
  }
  
  /**
   * Returns true if venus A is open.
   *
   * @return  True if open.
   */
  public boolean isVenusAOpen() {
    return venusAOpen;
  }
  
  /**
   * Set true if venus A is open.
   *
   * @param venusAOpen  True for open.
   */
  public void setVenusAOpen(boolean venusAOpen) {
    this.venusAOpen = venusAOpen;
    notifyUpdate();
  }
  
  /**
   * Returns true if venus B is open.
   *
   * @return  True if open.
   */
  public boolean isVenusBOpen() {
    return venusBOpen;
  }
  
  /**
   * Set true if venus B is open.
   *
   * @param venusBOpen  True for open.
   */
  public void setVenusBOpen(boolean venusBOpen) {
    this.venusBOpen = venusBOpen;
    notifyUpdate();
  }
  
  /**
   * Returns true for broken cannula.
   *
   * @return  True for broken cannula.
   */
  public boolean isBrokenCannula() {
    return brokenCannula;
  }

  /**
   * Sets true for broken cannula.
   *
   * @param brokenCannula  True for broken cannula.
   */
  public void setBrokenCannula(boolean brokenCannula) {
    this.brokenCannula = brokenCannula;
  }

  /**
   * Returns true for broken ETT.
   *
   * @return  True for broken ETT.
   */
  public boolean isBrokenETT() {
    return brokenETT;
  }

  /**
   * Set true for broken ETT.
   *
   * @param brokenETT  True for broken ETT.
   */
  public void setBrokenETT(boolean brokenETT) {
    this.brokenETT = brokenETT;
  }

  /**
   * Returns true for problem suction ETT.
   *
   * @return  True for problem suction ETT.
   */
  public boolean isSuctionETT() {
    return suctionETT;
  }

  /**
   * Set true for problem suction ETT.
   *
   * @param suctionETT  True for problem suction ETT.
   */
  public void setSuctionETT(boolean suctionETT) {
    this.suctionETT = suctionETT;
  }

  /**
   * Returns the post-membrane pressure.
   *
   * @return  The post-membrane pressure.
   */
  public double getPostMembranePressure() {
    return postMembranePressure;
  }

  /**
   * Sets the post-membrane pressure.
   *
   * @param postMembranePressure  The post-membrane pressure to set.
   */
  public void setPostMembranePressure(double postMembranePressure) {
    this.postMembranePressure = postMembranePressure;
    notifyUpdate();
  }

  /**
   * Returns the pre-membrane pressure.
   *
   * @return  The pre-membrane pressure.
   */
  public double getPreMembranePressure() {
    return preMembranePressure;
  }

  /**
   * Sets the pre-membrane pressure.
   *
   * @param preMembranePressure  The pre-membrane pressure to set.
   */
  public void setPreMembranePressure(double preMembranePressure) {
    this.preMembranePressure = preMembranePressure;
    notifyUpdate();
  }

  /**
   * Returns the venous pressure.
   *
   * @return  The venous pressure.
   */
  public double getVenousPressure() {
    return venousPressure;
  }

  /**
   * Sets the venous pressure.
   *
   * @param venousPressure  The venous pressure to set.
   */
  public void setVenousPressure(double venousPressure) {
    this.venousPressure = venousPressure;
    notifyUpdate();
  }

  /**
   * Returns true for arterial bubbles.
   *
   * @return  True for bubbles.
   */
  public boolean isArterialBubbles() {
    return arterialBubbles;
  }

  /**
   * Set if arterial bubbles.
   *
   * @param arterialBubbles  True for bubbles.
   */
  public void setArterialBubbles(boolean arterialBubbles) {
    this.arterialBubbles = arterialBubbles;
  }

  /**
   * Returns true for venus bubbles.
   *
   * @return  True for bubbles.
   */
  public boolean isVenusBubbles() {
    return venusBubbles;
  }

  /**
   * Set if venus bubbles.
   *
   * @param venusBubbles  True for bubbles.
   */
  public void setVenusBubbles(boolean venusBubbles) {
    this.venusBubbles = venusBubbles;
  }

  /**
   * Returns the venous O2 saturation value.
   * 
   * @return  The O2 value.
   */
  public double getSvO2() {
    return svO2;
  }
  
  /**
   * Sets the venous O2 saturation value.
   * 
   * @param svO2  The O2 to set.
   */
  public void setSvO2(double svO2) {
    this.svO2 = svO2;
    notifyUpdate();
  }
  
  /**
   * Returns the venous pH (pre-oxygenator pH).
   * 
   * @return The pre-oxygenator pH.
   */
  public double getPrePH() {
    return prePH;
  }

  /**
   * Sets the venous pH (pre-oxygenator pH).
   * 
   * @param prePH The pre-oxygenator pH.
   */
  public void setPrePH(double prePH) {
    this.prePH = prePH;
    notifyUpdate();
  }

  /**
   * Returns the venous pCO2 (pre-oxygenator pCO2).
   * 
   * @return prePCO2 The pre-oxygenator pCO2.
   */
  public double getPrePCO2() {
    return prePCO2;
  }

  /**
   * Sets the venous pCO2 (pre-oxygenator pCO2).
   * 
   * @param prePCO2 The pre-oxygenator pCO2.
   */
  public void setPrePCO2(double prePCO2) {
    this.prePCO2 = prePCO2;
    notifyUpdate();
  }

  /**
   * Returns the venous (pre-oxygenator) BE value.
   * 
   * @param hgb Hemoglobin parameter.
   * @return The BE.
   */
  public double getPreBE(double hgb) {
    return (getPreHCO3() - 24.4 + (2.3 * hgb) * (getPrePH() - 7.4)) * (1 - 0.023 * hgb);
  }
  
  /**
   * Returns the venous (pre-oxygenator) HCO3 value.
   * 
   * @return The HCO3.
   */
  public double getPreHCO3() {
    return 0.03 * getPrePCO2() * Math.pow(10, getPrePH() - 6.1);
  }

  /**
   * Returns the arterial O2 saturation value.
   * 
   * @return  The O2 value.
   */
  public double getSaO2() {
    return saO2;
  }
  
  /**
   * Sets the arterial O2 saturation value.
   * 
   * @param saO2  The O2 to set.
   */
  public void setSaO2(double saO2) {
    this.saO2 = saO2;
    notifyUpdate();
  }
  
  /**
   * Returns the arterial pH (post-oxygenator pH).
   * 
   * @return The post-oxygenator pH.
   */
  public double getPostPH() {
    return postPH;
  }

  /**
   * Sets the arterial pH (post-oxygenator pH).
   * 
   * @param postPH The post-oxygenator pH to set.
   */
  public void setPostPH(double postPH) {
    this.postPH = postPH;
    notifyUpdate();
  }

  /**
   * Returns the arterial pCO2 (post-oxygenator pCO2).
   * 
   * @return The post-oxygenator pCO2.
   */
  public double getPostPCO2() {
    return postPCO2;
  }

  /**
   * Sets the arterial pCO2 (post-oxygenator pCO2).
   * 
   * @param postPCO2 The post-oxygenator pCO2 to set.
   */
  public void setPostPCO2(double postPCO2) {
    this.postPCO2 = postPCO2;
    notifyUpdate();
  }

  /**
   * Returns the arterial (post-oxygenator) BE value.
   * 
   * @param hgb Hemoglobin parameter.
   * @return The BE.
   */
  public double getPostBE(double hgb) {
    return (getPostHCO3() - 24.4 + (2.3 * hgb) * (getPostPH() - 7.4)) * (1 - 0.023 * hgb);
  }
  
  /**
   * Returns the arterial (post-oxygenator) HCO3 value.
   * 
   * @return The HCO3.
   */
  public double getPostHCO3() {
    return 0.03 * getPostPCO2() * Math.pow(10, getPostPH() - 6.1);
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    if (mode == Mode.VA) {
      return "VA ECMO";
    }
    else {
      return "VV ECMO";
    }
  }
}