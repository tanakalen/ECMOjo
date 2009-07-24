package edu.hawaii.jabsom.tri.ecmo.app.state;

/**
 * The result state. 
 *
 * @author   Christoph Aschwanden
 * @since    August 19, 2008
 */
public class HelpState extends State {

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
