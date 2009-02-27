package edu.hawaii.jabsom.tri.ecmo.app.state;

/**
 * The state. 
 *
 * @author   king
 * @since    January 11, 2007
 */
public abstract class State {

  /** The state machine. */
  private StateMachine machine;
  
  
  /**
   * Executes a transition to this new state.
   * 
   * @param state  The new state.
   */
  protected void transition(State state) {
    machine.setState(state);
  }
  
  /**
   * Sets the state machine.
   * 
   * @param machine  The state machine.
   */
  void setStateMachine(StateMachine machine) {
    this.machine = machine;
  }
  
  /** 
   * Called during state init.
   */
  abstract void init();
  
  /**
   * Called after state deinit.
   */
  abstract void deinit();
}
