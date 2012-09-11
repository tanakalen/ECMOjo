package edu.hawaii.jabsom.tri.ecmo.app.model.engage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The tracker for intervention and other stuff. 
 *
 * @author   king
 * @since    October 24, 2008
 */
public class Tracker implements Serializable {
  
  /** The counter for interventions. */
  private Map<Class<? extends Intervention>, Integer> counter = new HashMap<Class<? extends Intervention>, Integer>();
  
  /**
   * Tracks the intervention.
   * 
   * @param intervention  The intervention.
   */
  public void track(Class<? extends Intervention> intervention) {
    counter.put(intervention, 1 + getCount(intervention));
  }
  
  /**
   * Returns the count for the given intervention.
   * 
   * @param intervention  The intervention.
   * @return  The number of times used.
   */
  public int getCount(Class<? extends Intervention> intervention) {
    Integer count = counter.get(intervention);
    if (count == null) {
      return 0;
    }
    else {
      return count.intValue();
    }
  }
}
