package edu.hawaii.jabsom.tri.ecmo.app.state;

import java.util.ArrayList;
import java.util.List;

/**
 * The state machine. 
 *
 * @author   king
 * @since    March 7, 2008
 */
public class StateMachine {

  /** The state machine listener. */
  public interface StateMachineListener {
    
    /**
     * Called when the state changed.
     */
    void stateChanged();
  }
  
  /** The listeners. */
  private List<StateMachineListener> listeners = new ArrayList<StateMachineListener>();
  
  /** The currently active state. */
  private State state;
  
  
  /**
   * Inits the state machine.
   * 
   * @param state  The state to init with.
   */
  public void init(State state) {
    setState(state);
  }
  
  /**
   * Returns the current state.
   * 
   * @return  The current state.
   */
  public State currentState() {
    return this.state;
  }
  
  /**
   * Sets a new state.
   * 
   * @param state  The new state.
   */
  void setState(State state) {
    if (this.state != null) {
      // stop previous state
      this.state.deinit();
    }
    
    if (this.state != state) {
      state.setStateMachine(this);
      this.state = state;
      this.state.init();
      
      // notify about change
      stateChangeNotification();
    }
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener.
   */
  public void addStateMachineListener(StateMachineListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener.
   */
  public void removeStateMachineListener(StateMachineListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * Called to notify about state change.
   */
  private void stateChangeNotification() {
    for (int i = 0; i < listeners.size(); i++) {
      listeners.get(i).stateChanged();
    }
  }
}
