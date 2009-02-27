package edu.hawaii.jabsom.tri.ecmo.app;

import java.awt.Component;
import java.awt.Dimension;

import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.gui.transition.RandomTransition;
import edu.hawaii.jabsom.tri.ecmo.app.gui.transition.TransitionPanel;
import edu.hawaii.jabsom.tri.ecmo.app.state.LoadState;
import edu.hawaii.jabsom.tri.ecmo.app.state.LoadStatePanel;
import edu.hawaii.jabsom.tri.ecmo.app.state.MenuState;
import edu.hawaii.jabsom.tri.ecmo.app.state.MenuStatePanel;
import edu.hawaii.jabsom.tri.ecmo.app.state.ResultState;
import edu.hawaii.jabsom.tri.ecmo.app.state.ResultStatePanel;
import edu.hawaii.jabsom.tri.ecmo.app.state.StateMachine;
import edu.hawaii.jabsom.tri.ecmo.app.state.State;
import edu.hawaii.jabsom.tri.ecmo.app.state.GameState;
import edu.hawaii.jabsom.tri.ecmo.app.state.GameStatePanel;
import edu.hawaii.jabsom.tri.ecmo.app.state.StateMachine.StateMachineListener;

/**
 * The main panel. 
 *
 * @author   king
 * @since    January 10, 2007
 */
public class ECMOPanel extends TransitionPanel implements StateMachineListener {

  /** The state machine of this panel. */
  private StateMachine stateMachine;

  
  /**
   * Constructor for panel. 
   * 
   * @param stateMachine  The state machine.
   */
  public ECMOPanel(StateMachine stateMachine) {
    this.stateMachine = stateMachine;
    
    // set transition
    setTransition(new RandomTransition());
    
    // add state change listener
    stateMachine.addStateMachineListener(this);
  }
  
  /**
   * Returns the preferred size.
   * 
   * @return  The preferred size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600);
  }
  
  /**
   * Called when the active state in the state machine changed.
   */
  public void stateChanged() {
    Component component = null;
    State state = stateMachine.currentState();
    if (state instanceof LoadState) {
      component = new LoadStatePanel((LoadState)stateMachine.currentState());
    }
    else if (state instanceof MenuState) {
      component = new MenuStatePanel((MenuState)stateMachine.currentState());
    }
    else if (state instanceof GameState) {
      component = new GameStatePanel((GameState)stateMachine.currentState());
    }
    else if (state instanceof ResultState) {
      component = new ResultStatePanel((ResultState)stateMachine.currentState());
    }
    else {
      Error.out("State not supported: " + state);
    }
    
    // and fade in the new component
    fadeIn(component);
  }
}
