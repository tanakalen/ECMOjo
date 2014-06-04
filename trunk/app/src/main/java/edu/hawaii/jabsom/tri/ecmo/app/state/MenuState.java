package edu.hawaii.jabsom.tri.ecmo.app.state;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import king.lib.access.Access;
import king.lib.access.LocalHookup;
import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioLoader;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;

/**
 * The menu state. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class MenuState extends State {

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
   * Returns a list of scenarios.
   * 
   * @return  The scenarios.
   */
  public List<Scenario> getScenarios() {
    return scenarios("Scenario");
  }

  /**
   * Returns a list of simulations.
   * 
   * @return  The simulations.
   */
  public List<Scenario> getSimulations() {
    return scenarios("Simulation");
  }
 
  /**
   * Returns a list of scenarios that start with the given prefix.
   * 
   * @param prefix  The prefix.
   * @return  The scenarios.
   */
  private List<Scenario> scenarios(String prefix) {
    // create scenario list
    List<Scenario> scenarios = new ArrayList<Scenario>();
    
    // parse scenario files with given prefix
    LocalHookup hookup = LocalHookup.getInstance();
    String appName = Configuration.getInstance().getAppType().getName();
    String langCode = Configuration.getInstance().getLang();
    String path = "";
    if (langCode.equals("")) {
      path = "en" + File.separator + appName;
    }
    else {
      path = langCode + File.separator + appName;
    }
    String[] files = hookup.getFiles(Access.getInstance().getScenarioDir() + File.separator + path);
    for (String file: files) {
      String fileName = file.substring(file.lastIndexOf(File.separatorChar) + 1);
      if (fileName.startsWith(prefix)) {
        try {
          Scenario scenario = ScenarioLoader.load(hookup, file);
          scenarios.add(scenario);
        }
        catch (Exception e) {
          Error.out("Error loading file: " + fileName);
          Error.out(e);
        }
      }
    }
    
    // and return the scenarios
    return scenarios;
  }
  
  /**
   * Proceeds to the game state.
   *
   * @param scenario  The scenario selected.
   * @param user  The user that is executing the game.
   */
  public void gameState(Scenario scenario, String user) {
    transition(new GameState(scenario, user));
  }
  
  /**
   * Proceeds to the editor state.
   */
  public void editState() {
    transition(new EditState());
  }

  /**
   * Proceeds to the about state.
   */
  public void aboutState() {
    transition(new AboutState());
  }

  /**
   * Proceeds to the contact state.
   */
  public void contactState() {
    transition(new ContactState());
  }
  /**
   * Reloads the menu state.
   */
  public void menuState() {
    transition(new MenuState());
  }
}
