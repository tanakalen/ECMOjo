package king.lib.out;

/**
 * Outputs a message to the error console window.
 *
 * @author   king
 * @since    June 5, 2003
 */
public class ErrorOutputUnit extends OutputUnit {

  /**
   * Outputs a message to the error console window.
   *
   * @param message  The message to output.
   */
  protected synchronized void formattedOut(String message) {
    System.err.print(message);
  }
}
