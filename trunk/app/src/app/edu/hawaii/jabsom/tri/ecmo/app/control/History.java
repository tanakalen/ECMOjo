package edu.hawaii.jabsom.tri.ecmo.app.control;

/**
 * Holds historical values.
 *
 * @author   king
 * @since    August 13, 2008
 */
public class History {
  
  /** Holds time patient remains sedated in 20ms intervals. */
  private double patientSedatedTime = 0;
  /** Holds prior patient temperature information. */
  private double patientTemperature;
  /** Holds prior flow rate information. */
  private double flow;
  /** Holds prior sweep rate information. */
  private double sweep;
  /** Holds prior venous pressure information. */
  private double venousPressure;
  /** Holds starting time that a clamp is applied. */
  private long startClampTime = -1;
  /** Holds prior True if the bridge is open. */
  private boolean bridgeOpen;
  /** Holds prior True if arterial position A open. */
  private boolean arterialAOpen;
  /** Holds prior True if arterial position B open. B is the patient side/left */
  private boolean arterialBOpen;
  /** Holds prior True if venous position A open. */
  private boolean venousAOpen;
  /** Holds prior True if venous position B open. B is the patient side/left */
  private boolean venousBOpen;
  
  
  /**
   * Returns the starting time a clamp is applied.
   *
   * @return  The time when clamp is applied.
   */
  public long getStartClampTime() {
    return startClampTime;
  }

  /**
   * Sets the counter to when a clamp has been applied. ECMO circuit is not in
   * onPump state where A~BV and not in recirculating state where ~AB~V.
   *
   * @param time  The time when a clamp is applied not at end state.
   */
  public void setStartClampTime(long time) {
    this.startClampTime = time;
  }

  /**
   * Returns the bridgeOpen.
   *
   * @return The bridgeOpen.
   */
  public boolean isBridgeOpen() {
    return bridgeOpen;
  }

  /**
   * Sets the bridgeOpen.
   *
   * @param bridgeOpen The bridgeOpen to set.
   */
  public void setBridgeOpen(boolean bridgeOpen) {
    this.bridgeOpen = bridgeOpen;
  }

  /**
   * Returns the arterialAOpen.
   *
   * @return The arterialAOpen.
   */
  public boolean isArterialAOpen() {
    return arterialAOpen;
  }

  /**
   * Sets the arterialAOpen.
   *
   * @param arterialAOpen The arterialAOpen to set.
   */
  public void setArterialAOpen(boolean arterialAOpen) {
    this.arterialAOpen = arterialAOpen;
  }

  /**
   * Returns the arterialBOpen.
   *
   * @return The arterialBOpen.
   */
  public boolean isArterialBOpen() {
    return arterialBOpen;
  }

  /**
   * Sets the arterialBOpen.
   *
   * @param arterialBOpen The arterialBOpen to set.
   */
  public void setArterialBOpen(boolean arterialBOpen) {
    this.arterialBOpen = arterialBOpen;
  }

  /**
   * Returns the venousAOpen.
   *
   * @return The venousAOpen.
   */
  public boolean isVenousAOpen() {
    return venousAOpen;
  }

  /**
   * Sets the venousAOpen.
   *
   * @param venousAOpen The venousAOpen to set.
   */
  public void setVenousAOpen(boolean venousAOpen) {
    this.venousAOpen = venousAOpen;
  }

  /**
   * Returns the venousBOpen.
   *
   * @return The venousBOpen.
   */
  public boolean isVenousBOpen() {
    return venousBOpen;
  }

  /**
   * Sets the venousBOpen.
   *
   * @param venousBOpen The venousBOpen to set.
   */
  public void setVenousBOpen(boolean venousBOpen) {
    this.venousBOpen = venousBOpen;
  }

  /**
   * Returns the patient's time remains isSedated == TRUE.
   *
   * @return  The time in 20ms intervals patient isSedated.
   */
  public double getPatientSedatedTime() {
    return patientSedatedTime;
  }

  /**
   * Sets the variable tracking 20ms intervals patient.isSedated() == TRUE.
   *
   * @param patientIsSedated  The number of 20ms intervals patient isSedated.
   */
  public void setPatientSedatedTime(double patientIsSedated) {
    this.patientSedatedTime = patientIsSedated;
  }

  /**
   * Returns the patient temperature.
   *
   * @return  The patient temperature.
   */
  public double getPatientTemperature() {
    return patientTemperature;
  }

  /**
   * Sets the patient temperature.
   *
   * @param patientTemperature  The patient temperature.
   */
  public void setPatientTemperature(double patientTemperature) {
    this.patientTemperature = patientTemperature;
  }

  /**
   * Returns the flow.
   *
   * @return  The flow.
   */
  public double getFlow() {
    return flow;
  }
  
  /**
   * Sets the flow.
   *
   * @param flow  The flow.
   */
  public void setFlow(double flow) {
    this.flow = flow;
  }
  
  /**
   * Returns the sweep.
   *
   * @return  The sweep.
   */
  public double getSweep() {
    return sweep;
  }
  
  /**
   * Sets the sweep.
   *
   * @param sweep  The sweep.
   */
  public void setSweep(double sweep) {
    this.sweep = sweep;
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
   * @param venousPressure  The venous pressure.
   */
  public void setVenousPressure(double venousPressure) {
    this.venousPressure = venousPressure;
  }
}
