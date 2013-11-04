package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import king.lib.util.Translator;


/**
 * The alarm indicator component. 
 *
 * @author   king
 * @since    August 29, 2008
 */
public class AlarmIndicatorComponent extends Component {
  
  /** True if there is an alarm. */
  private boolean alarm;
  
  
  /**
   * Returns true for alarm.
   *
   * @return  True for alarm.
   */
  public boolean isAlarm() {
    return alarm;
  }

  /**
   * Sets if there is an alarm.
   *
   * @param alarm  The alarm to set.
   */
  public void setAlarm(boolean alarm) {
    this.alarm = alarm;
    notifyUpdate();
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("text.AlarmInd[i18n]: Alarm Indicator");
  }
}
