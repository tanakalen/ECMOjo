package king.lib.script.model.java;

/**
 * A script interface for java.
 *
 * @author noblemaster
 * @since October 26, 2009
 * @access Public
 */
public interface JavaExecutable {

  /**
   * Takes an input, executes it and optionally returns an output.
   * 
   * @param object  The input. The input can be modified by this function.
   * @return  The output or null for none.
   */
  Object execute(Object object);
}
