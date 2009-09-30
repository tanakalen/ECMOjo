package edu.hawaii.jabsom.tri.ecmo.app.model.comp;


/**
 * The physiologic monitor component for the patient. 
 *
 * @author   king
 * @since    August 28, 2008
 */
public class PhysiologicMonitorComponent extends Component {
  
  /** Patient's temperature in centigrade [32.0, 42.0]. */
  private double temperature;
  /** The heart rate [0, 300]. */
  private double heartRate;
  /** The respiratory rate [0, 100]. */
  private double respiratoryRate;
  /** The O2 saturation in percent / 10 [0.00, 1.00]. */
  private double o2Saturation;
  /** The systolic blood pressure in mm Hg [0, 200]. */
  private double systolicBloodPressure;
  /** The diastolic blood pressure in mm Hg [0, 100]. */
  private double diastolicBloodPressure;
  /** The central venous pressure in cm water [0, 50]. */
  private double centralVenousPressure;
  
  
  /**
   * Returns the mean blood pressure.
   * 
   * @return the mean blood pressure.
   */
  public double getMeanBloodPressure() {
    return ((systolicBloodPressure + 2*diastolicBloodPressure) / 3);
  }

  /**
   * Returns the diastolic blood pressure.
   * 
   * @return  The diastolic blood pressure.
   */
  public double getDiastolicBloodPressure() {
    return diastolicBloodPressure;
  }
  
  /**
   * Sets the diastolic blood pressure. 
   * @param diastolicBloodPressure  The diastolic blood pressure to set.
   */
  public void setDiastolicBloodPressure(double diastolicBloodPressure) {
    this.diastolicBloodPressure = diastolicBloodPressure;
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
    notifyUpdate();
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
    notifyUpdate();
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
    notifyUpdate();
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
    notifyUpdate();
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
    notifyUpdate();
  }

  /**
   * Returns true for temperature alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isTemperatureAlarm() {
    return ((temperature < 34) || (temperature > 39));
  }
  
  /**
   * Returns true for heart rate alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isHeartRateAlarm() {
    return ((heartRate < 95) || (heartRate > 160));
  }

  /**
   * Returns true for respiratory rate alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isRespiratoryRateAlarm() {
    return respiratoryRate < 5;
  }

  /** 
   * Returns true if there is an O2 saturation alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isO2SaturationAlarm() {
    return o2Saturation <= 0.53;
  }
  
  /**
   * Returns true for systolic blood pressure alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isSystolicBloodPressureAlarm() {
    return ((systolicBloodPressure < 70) || (systolicBloodPressure > 80));
  }

  /**
   * Returns true for diastolic blood pressure alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isDiastolicBloodPressureAlarm() {
    return ((diastolicBloodPressure < 0) || (diastolicBloodPressure > 100));  // TODO: values need adjustment...
  }

  /**
   * Returns true for central venous pressure alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isCentralVenousPressureAlarm() {
    return ((centralVenousPressure < 0) || (centralVenousPressure > 8));
  }

  /**
   * Returns true if there is an alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isAlarm() {
    if (isTemperatureAlarm()) {
      return true;
    }
    else if (isHeartRateAlarm()) {
      return true;
    }
    else if (isRespiratoryRateAlarm())  {
      return true;
    }
    else if (isO2SaturationAlarm()) {
      return true;
    }
    else if (isSystolicBloodPressureAlarm()) {
      return true;
    }
    else if (isDiastolicBloodPressureAlarm()) {
      return true;
    }
    else if (isCentralVenousPressureAlarm()) {
      return true;
    }

    // otherwise, no alarm
    return false;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Physiologic Monitor";
  }
}
