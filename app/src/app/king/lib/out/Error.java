package king.lib.out;


/**
 * Output class for error messages.
 *
 * @author   king
 * @since    August 1, 2002
 */
public final class Error {

  /** Output unit for error messages. */
  private static OutputUnit outputUnit = new ErrorOutputUnit();

  
  /**
   * Private constructor to disable instantiation.
   */
  private Error() {
    // not used.
  }
  
  /**
   * Outputs an error message.
   * 
   * @param message  The message to output.
   */
  public static void out(String message) {
    if (outputUnit != null) {
      outputUnit.out(message);
    }
  }
  
  /**
   * Outputs an exception.
   * 
   * @param e  The exception to output.
   */
  public static void out(Exception e) {
    if (outputUnit != null) {
      outputUnit.out(e);
    }
  }

  /**
   * Sets the output unit to write error messages.
   *
   * @param outputUnit  The new output unit.
   */
  public static void setOutputUnit(OutputUnit outputUnit) {
    Error.outputUnit = outputUnit;
  }
  
  /**
   * Returns the output unit used to output error messages.
   * 
   * @return  The output unit for error messages.
   */
  public static OutputUnit getOutputUnit() {
    return Error.outputUnit;
  }
}
