package edu.hawaii.jabsom.tri.ecmo.app.state;

/**
 * The contact state. 
 *
 * @author Christoph Aschwanden
 * @since February 22, 2010
 */
public class ContactState extends State {

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
