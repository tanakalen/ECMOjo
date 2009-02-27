package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;

/**
 * The bubble action which creates bubbles in tubing. The bubble action
 * can be used as a "random" event in coordination with triggers. 
 *
 * @author   king
 * @since    October 1, 2008
 */
public class BubbleAction extends Action {

  /** True for arterial bubbles. */
  private boolean arterialBubbles;
  /** True for venus bubbles. */
  private boolean venusBubbles;

  
  /**
   * Returns true for arterial bubbles.
   *
   * @return  True for bubbles.
   */
  public boolean isArterialBubbles() {
    return arterialBubbles;
  }

  /**
   * Set if arterial bubbles.
   *
   * @param arterialBubbles  True for bubbles.
   */
  public void setArterialBubbles(boolean arterialBubbles) {
    this.arterialBubbles = arterialBubbles;
  }

  /**
   * Returns true for venus bubbles.
   *
   * @return  True for bubbles.
   */
  public boolean isVenusBubbles() {
    return venusBubbles;
  }

  /**
   * Set if venus bubbles.
   *
   * @param venusBubbles  True for bubbles.
   */
  public void setVenusBubbles(boolean venusBubbles) {
    this.venusBubbles = venusBubbles;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // create bubbles in tubing
    TubeComponent component = (TubeComponent)game.getEquipment().getComponent(TubeComponent.class);
    component.setArterialBubbles(arterialBubbles);
    component.setVenusBubbles(venusBubbles);
  }
}
