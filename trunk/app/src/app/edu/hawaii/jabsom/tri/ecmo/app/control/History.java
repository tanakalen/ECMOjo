package edu.hawaii.jabsom.tri.ecmo.app.control;

/**
 * Holds historical values.
 *
 * @author   king
 * @since    August 13, 2008
 */
public class History {
  
  /** Holds prior patient temperature information. */
  private double patientTemperature;
  /** Holds prior flow rate information. */
  private double flow;
  /** Holds prior sweep rate information. */
  private double sweep;
  /** Holds prior venous pressure information. */
  private double venousPressure;
  
  
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
