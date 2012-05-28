package king.lib.out;


/**
 * Output class for info messages.
 *
 * @author   king
 * @since    September 5, 2006
 */
public final class Info {

  /** Output unit for info messages. */
  private static OutputUnit outputUnit = new ErrorOutputUnit();

  
  /**
   * Private constructor to disable instantiation.
   */
  private Info() {
    // not used.
  }
  
  /**
   * Outputs an info message.
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
    Info.outputUnit = outputUnit;
  }
  
  /**
   * Returns the output unit used to output error messages.
   * 
   * @return  The output unit for error messages.
   */
  public static OutputUnit getOutputUnit() {
    return Info.outputUnit;
  }
}
