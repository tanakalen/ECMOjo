package edu.hawaii.jabsom.tri.ecmo.app.state;

import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;

/**
 * The edit state. 
 *
 * @author Christoph Aschwanden
 * @since December 9, 2009
 */
public class EditState extends State {

  /** 
   * Called during state init.
   */
  void init() {
    // not used
  }
  
  /**
   * Called after state deinit.
   */
  void deinit() {
    // not used
  }
    
  /**
   * Proceeds to the game state.
   *
   * @param scenario  The scenario selected.
   */
  public void gameState(Scenario scenario) {
    transition(new GameState(scenario, "N/A", true));
  }
  
  /**
   * Proceeds to the menu state.
   */
  public void menuState() {
    transition(new MenuState());
  }
}
