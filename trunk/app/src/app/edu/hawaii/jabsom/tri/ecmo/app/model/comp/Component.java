package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The component. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public abstract class Component implements Serializable {
  
  /** Listener for changes. */
  public static interface UpdateListener {
    
    /**
     * Called when the component changed.
     */
    void handleUpdate();
  };
  
  /** Listeners for changes. */
  private List<UpdateListener> listeners = new ArrayList<UpdateListener>();

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public abstract String getName();

  /**
   * Returns the textual representation of this component.
   * 
   * @return  The textual representation of this component.
   */
  @Override
  public String toString() {
    return getName();
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addUpdateListener(UpdateListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeUpdateListener(UpdateListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all the listeners.
   */
  protected void notifyUpdate() {
    for (UpdateListener listener: listeners) {
      listener.handleUpdate();
    }
  }
}
