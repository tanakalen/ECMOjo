package edu.hawaii.jabsom.tri.ecmo.app.state;

/**
 * The edit state. 
 *
 * @author Christoph Aschwanden
 * @since December 9, 2009
 */
public class EditState extends State {

  /** The path. */
  private String path;
  
  
  /**
   * The constructor.
   */
  public EditState() {
    this(null);
  }
  
  /**
   * The constructor.
   * 
   * @param path  The path.
   */
  public EditState(String path) {
    this.path = path;
  }

  /**
   * The path.
   * 
   * @return  The path or null for none.
   */
  public String getPath() {
    return path;
  }
  
  /**
   * Sets the path or null for none.
   * 
   * @param path  The path.
   */
  public void setPath(String path) {
    this.path = path;
  }
  
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
   * Proceeds to the test state.
   *
   * @param path  The path of the scenario to test.
   */
  public void evalState(String path) {
    transition(new EvalState(path));
  }
  
  /**
   * Proceeds to the menu state.
   */
  public void menuState() {
    transition(new MenuState());
  }
}
