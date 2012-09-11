package edu.hawaii.jabsom.tri.ecmo.app.state;

import java.io.IOException;

import king.lib.access.LocalHookup;
import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioCreator;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioLoader;

/**
 * The test state. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class EvalState extends GameState {

  /** The path. */
  private String path;
  
  
  /** 
   * Constructor for test state.
   * 
   * @param path  The path to the scenario.
   */
  public EvalState(String path) {
    this.path = path;
  }
    
  /** 
   * Called during state init.
   */
  @Override
  public void init() {
    // setup the game
    try {
      // the scenario and user
      scenario = ScenarioLoader.load(LocalHookup.getInstance(), path);
      ScenarioCreator.setup(scenario);
      user = "N/A";
    }
    catch (IOException e) {
      Error.out(e);
    }
    
    // and init
    super.init();
  }

  /**
   * Proceeds to the result state.
   */
  @Override
  public void resultState() {
    transition(new EditState(path));
  }

  /**
   * Proceeds to the menu state.
   */
  @Override
  public void menuState() {
    transition(new EditState(path));
  }
}
