package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import king.lib.util.Translator;


/**
 * The pressure monitor component for the patient. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class PressureMonitorComponent extends Component {
  
  /** The pre-membrane pressure in torr [0, 900]. */
  private double preMembranePressure;
  /** The pre-membrane pressure minimum in torr [0, 900]. */
  private double preMembranePressureMin;
  /** The pre-membrane pressure minimum in torr [0, 900]. */
  private double preMembranePressureMax;
  
  /** The post-membrane pressure in torr [0, 900]. */
  private double postMembranePressure;
  /** The post-membrane pressure minimum in torr [0, 900]. */
  private double postMembranePressureMin;
  /** The post-membrane pressure maximum in torr [0, 900]. */
  private double postMembranePressureMax;
  
  /** The venous pressure in torr [-50, 100]. */
  private double venousPressure;
  /** The venous pressure minimum in torr [-50, 100]. */
  private double venousPressureMin;
  /** The venous pressure maximum in torr [-50, 100]. */
  private double venousPressureMax;
  
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
   * Returns the post-membrane pressure minimum.
   *
   * @return  The post-membrane pressure.
   */
  public double getPostMembranePressureMin() {
    return postMembranePressureMin;
  }

  /**
   * Sets the post-membrane pressure minimum.
   *
   * @param postMembranePressureMin  The post-membrane pressure to set.
   */
  public void setPostMembranePressureMin(double postMembranePressureMin) {
    this.postMembranePressureMin = postMembranePressureMin;
    notifyUpdate();
  }

  /**
   * Returns the post-membrane pressure maximum.
   *
   * @return  The post-membrane pressure.
   */
  public double getPostMembranePressureMax() {
    return postMembranePressureMax;
  }

  /**
   * Sets the post-membrane pressure maximum.
   *
   * @param postMembranePressureMax  The post-membrane pressure to set.
   */
  public void setPostMembranePressureMax(double postMembranePressureMax) {
    this.postMembranePressureMax = postMembranePressureMax;
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
   * Returns the pre-membrane pressure minimum.
   *
   * @return  The pre-membrane pressure.
   */
  public double getPreMembranePressureMin() {
    return preMembranePressureMin;
  }

  /**
   * Sets the pre-membrane pressure minimum.
   *
   * @param preMembranePressureMin  The pre-membrane pressure to set.
   */
  public void setPreMembranePressureMin(double preMembranePressureMin) {
    this.preMembranePressureMin = preMembranePressureMin;
    notifyUpdate();
  }

  /**
   * Returns the pre-membrane pressure maximum.
   *
   * @return  The pre-membrane pressure.
   */
  public double getPreMembranePressureMax() {
    return preMembranePressureMax;
  }

  /**
   * Sets the pre-membrane pressure maximum.
   *
   * @param preMembranePressureMax  The pre-membrane pressure to set.
   */
  public void setPreMembranePressureMax(double preMembranePressureMax) {
    this.preMembranePressureMax = preMembranePressureMax;
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
   * Returns the venous pressure minimum.
   *
   * @return  The venous pressure.
   */
  public double getVenousPressureMin() {
    return venousPressureMin;
  }

  /**
   * Sets the venous pressure minimum.
   *
   * @param venousPressureMin  The venous pressure to set.
   */
  public void setVenousPressureMin(double venousPressureMin) {
    this.venousPressureMin = venousPressureMin;
    notifyUpdate();
  }

  /**
   * Returns the venous pressure maximum.
   *
   * @return  The venous pressure.
   */
  public double getVenousPressureMax() {
    return venousPressureMax;
  }

  /**
   * Sets the venous pressure maximum.
   *
   * @param venousPressureMax  The venous pressure to set.
   */
  public void setVenousPressureMax(double venousPressureMax) {
    this.venousPressureMax = venousPressureMax;
    notifyUpdate();
  }

  /**
   * Returns true if there is an alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isPreMembranePressureAlarm() {
    return preMembranePressure > preMembranePressureMax;
  }
  
  /**
   * Returns true if there is an warning.
   * 
   * @return  True for warning.
   */
  public boolean isPreMembranePressureWarning() {
    return preMembranePressure > (preMembranePressureMax - 50);
  }

  /**
   * Returns true if there is an alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isPostMembranePressureAlarm() {
    return postMembranePressure > postMembranePressureMax;
  }

  /**
   * Returns true if there is an warning.
   * 
   * @return  True for warning.
   */
  public boolean isPostMembranePressureWarning() {
    return postMembranePressure > (postMembranePressureMax - 50);
  }

  /**
   * Returns true if there is an alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isVenousPressureAlarm() {
    return (venousPressure < venousPressureMin) || (venousPressure > venousPressureMax);
  }

  /**
   * Returns true if there is a warning.
   * 
   * @return  True for warning.
   */
  public boolean isVenousPressureWarning() {
    return (venousPressure < (venousPressureMin + 7)) || (venousPressure > (venousPressureMax - 7));
  }

  /**
   * Returns true if there is an alarm.
   * 
   * @return  True for alarm.
   */
  public boolean isAlarm() {
    if (isPreMembranePressureAlarm()) {
      return true;
    }
    else if (isPostMembranePressureAlarm()) {
      return true;
    }
    else if (isVenousPressureAlarm())  {
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
    return Translator.getString("label.PressureMonitor[i18n]: Pressure Monitor");
  }
}
