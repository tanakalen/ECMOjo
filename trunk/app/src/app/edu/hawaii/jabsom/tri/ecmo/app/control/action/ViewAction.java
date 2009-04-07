package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;

/**
 * The view action. 
 *
 * @author   king
 * @since    Jan 12, 2009
 */
public class ViewAction extends Action {

  /** The component that was viewed. */
  private Component component;
  
  
  /**
   * The default constructor.
   */
  public ViewAction() {
    this(null);
  }
  
  /**
   * The constructor.
   *
   * @param component  The component viewed.
   */
  public ViewAction(Component component) {
    this.component = component;
  }
  
  /**
   * Returns the component.
   *
   * @return  The component.
   */
  public Component getComponent() {
    return component;
  }

  /**
   * Sets the component.
   *
   * @param component  The component to set.
   */
  public void setComponent(Component component) {
    this.component = component;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // there is nothing to execute, this is merely an indicator/user action tracker
  }
}
