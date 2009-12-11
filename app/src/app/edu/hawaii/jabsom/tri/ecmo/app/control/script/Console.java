package edu.hawaii.jabsom.tri.ecmo.app.control.script;

import java.util.LinkedList;

/**
 * The debug console for scripting. 
 *
 * @author noblemaster
 * @since December 10, 2009
 */
public class Console {

  /** The messages. */
  private LinkedList<String> messages = new LinkedList<String>();
  
  
  /**
   * Outputs a message.
   * 
   * @param message  The message to output.
   */
  public void out(String message) {
    messages.add(message);
  }

  /**
   * Returns but does not remove the next message.
   * 
   * @return  The message or null for none.
   */
  public String peek() {
    return messages.peek();
  }
  
  /**
   * Returns and removes the next message.
   * 
   * @return  The message or null for none.
   */
  public String poll() {
    return messages.poll();
  }
  
  /**
   * Returns the number of messages.
   * 
   * @return  The number of messages.
   */
  public int size() {
    return messages.size();
  }
}
