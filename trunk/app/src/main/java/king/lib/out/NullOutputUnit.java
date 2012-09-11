package king.lib.out;

/**
 * Outputs no messages.
 *
 * @author   king
 * @since    June 10, 2003
 */
public class NullOutputUnit extends OutputUnit {

  /**
   * Doesn't output messages.
   *
   * @param message  The message to output. Which actually will not be outputted.
   */
  protected void formattedOut(String message) {
    // does nothing
  }
}
