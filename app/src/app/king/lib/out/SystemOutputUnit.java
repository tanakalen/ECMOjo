package king.lib.out;

/**
 * Outputs a message to the console window.
 *
 * @author   king
 * @since    August 1, 2002
 */
public class SystemOutputUnit extends OutputUnit {

  /**
   * Outputs a message to the console window.
   *
   * @param message  The message to output.
   */
  protected synchronized void formattedOut(String message) {
    System.out.print(message);
  }
}
