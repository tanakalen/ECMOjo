package edu.hawaii.jabsom.tri.ecmo.app.control.script;

import java.util.LinkedList;

/**
 * The debug console for scripting. 
 *
 * @author noblemaster
 * @since December 10, 2009
 */
public class Console {

  /** The outputs. */
  private LinkedList<Output> outputs = new LinkedList<Output>();
  
  
  /**
   * Outputs a standard message.
   * 
   * @param message  The message to output.
   */
  public void out(String message) {
    outputs.add(new Output(message, false));
  }

  /**
   * Outputs an error message.
   * 
   * @param message  The message to output.
   */
  public void err(String message) {
    outputs.add(new Output(message, true));
  }

  /**
   * Returns but does not remove the next output.
   * 
   * @return  The message or null for none.
   */
  public Output peek() {
    return outputs.peek();
  }
  
  /**
   * Returns and removes the next output.
   * 
   * @return  The message or null for none.
   */
  public Output poll() {
    return outputs.poll();
  }
  
  /**
   * Returns the number of outputs.
   * 
   * @return  The number of outputs.
   */
  public int size() {
    return outputs.size();
  }
}
