package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The lab test list. 
 *
 * @author   king
 * @since    August 20, 2008
 */
public class LabList extends ArrayList<Lab> implements Serializable {
  
  /** Listener for changes. */
  public static interface LabTestListener {
    
    /**
     * Called when the list changed.
     */
    void handleLabTestUpdate();
  };
  
  /** Listeners for changes. */
  private List<LabTestListener> listeners = new ArrayList<LabTestListener>();

  /**
   * Inserts a new lab test.
   * 
   * @param index  The index.
   * @param labTest  The lab test.
   */
  @Override
  public void add(int index, Lab labTest) {
    super.add(index, labTest);
    notifyLabTestUpdate();
  }

  /**
   * Adds a new lab test.
   * 
   * @param labTest  The lab test.
   * @return  The result.
   */
  @Override
  public boolean add(Lab labTest) {
    boolean successful = super.add(labTest);
    notifyLabTestUpdate();
    return successful;
  }

  /**
   * Clears the list.
   */
  @Override
  public void clear() {
    super.clear();
    notifyLabTestUpdate();
  }

  /**
   * Removes a lab test.
   * 
   * @param index  The index.
   * @return  The result.
   */
  @Override
  public Lab remove(int index) {
    Lab labTest = super.remove(index);
    notifyLabTestUpdate();
    return labTest;
  }

  /**
   * Removes a lab test.
   * 
   * @param labTest  The lab test.
   * @return  The result.
   */
  @Override
  public boolean remove(Object labTest) {
    boolean successful =  super.remove(labTest);
    notifyLabTestUpdate();
    return successful;
  }

  /**
   * Sets a new lab test.
   * 
   * @param index  The index.
   * @param labTest  The lab test.
   * @return  The result.
   */
  @Override
  public Lab set(int index, Lab labTest) {
    labTest = super.set(index, labTest);
    notifyLabTestUpdate();
    return labTest;
  }

  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addLabTestListener(LabTestListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeLabTestListener(LabTestListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all the listeners.
   */
  protected void notifyLabTestUpdate() {
    for (LabTestListener listener: listeners) {
      listener.handleLabTestUpdate();
    }
  }
}
