package edu.hawaii.jabsom.tri.ecmo.app.state;

/**
 * The about state. 
 *
 * @author Christoph Aschwanden
 * @since August 19, 2008
 */
public class AboutState extends State {

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
   * Proceeds to the menu state.
   */
  public void menuState() {
    transition(new MenuState());
  }
}
