package king.lib.script.model;

/**
 * An executable.
 *
 * @author noblemaster
 * @since October 26, 2009
 * @access Public
 */
public interface Executable {

  /**
   * Takes an input, executes it an optionally returns an output.
   * 
   * @param object  The input.
   * @return  The output or null for none.
   */
  Object execute(Object object);
}
