package king.lib.out;

import java.util.ArrayList;

/**
 * Outputs a message to the various output units.
 *
 * @author   king
 * @since    June 27, 2004
 */
public class MultiOutputUnit extends OutputUnit {

  /** Output units where to redirect messages comming in here. */
  private ArrayList<OutputUnit> outputUnits = new ArrayList<OutputUnit>();
  
  
  /**
   * Adds an output unit where to send messages to.
   * 
   * @param outputUnit  An output unit where to send message to.
   */
  public void add(OutputUnit outputUnit) {
    outputUnits.add(outputUnit);
  }
  
  /**
   * Removes an output unit which should not receive messages anymore.
   * 
   * @param outputUnit  Output unit to remove from the list of message receivers.
   */
  public void remove(OutputUnit outputUnit) {
    outputUnits.remove(outputUnit);
  }
  
  /**
   * Outputs a message to various outputs.
   *
   * @param message  The message to output.
   */
  protected synchronized void formattedOut(String message) {
    for (int i = 0; i < outputUnits.size(); i++) {
      ((OutputUnit)outputUnits.get(i)).formattedOut(message);
    }
  }
}
