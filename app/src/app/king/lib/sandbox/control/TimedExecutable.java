package king.lib.sandbox.control;

/**
 * The executable.
 * 
 * @author noblemaster
 * @since October 30, 2009
 */
public interface TimedExecutable {
 
  /**
   * The method that is executed.
   * 
   * @return  The result or null for none.
   * @throws Exception  For any exceptions that are occurring.
   */
  Object execute() throws Exception;
}
