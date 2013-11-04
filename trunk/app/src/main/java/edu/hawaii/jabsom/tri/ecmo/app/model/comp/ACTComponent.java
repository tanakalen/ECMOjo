package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import java.util.ArrayList;
import java.util.List;

import king.lib.util.Translator;


/**
 * The ACT component. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class ACTComponent extends Component {
  
  /** Represents an ACT value. */
  public static class ACT {
    /** The ACT values [0, 999]. */
    private double value;
    /** The time in seconds relative to the start time. */
    private int time;
    
    /**
     * Returns the ACT value.
     *
     * @return  The ACT value.
     */
    public double getValue() {
      return value;
    }
    
    /**
     * Sets the ACT value.
     *
     * @param value  The ACT value.
     */
    public void setValue(double value) {
      this.value = value;
    }
    
    /**
     * Returns the time.
     *
     * @return  The time in seconds [s] relative to the start time..
     */
    public int getTime() {
      return time;
    }
    
    /**
     * Sets the time.
     *
     * @param time  The time in seconds [s] relative to the start time..
     */
    public void setTime(int time) {
      this.time = time;
    }
  }
  
  /** List of ACT values. */
  private List<ACT> acts = new ArrayList<ACT>();
  
  
  /**
   * Returns the latest ACT value.
   *
   * @return  The ACT value or null for none.
   */
  public ACT getACT() {
    if (acts.size() > 0) {
      return acts.get(acts.size() - 1);
    }
    else {
      // not ACT value yet
      return null;
    }
  }

  /**
   * Returns all the current ACT values.
   * 
   * @return  The ACT values.
   */
  public List<ACT> getACTs() {
    return acts;
  }

  /**
   * Adds an ACT value.
   * 
   * @param act  The value to add.
   */
  public void addACT(ACT act) {
    acts.add(act);
    notifyUpdate();
  }
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("text.ACT[i18n]: ACT");
  }
}
