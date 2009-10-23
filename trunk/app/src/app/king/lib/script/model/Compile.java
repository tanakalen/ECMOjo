package king.lib.script.model;

import king.lib.script.control.ScriptException;

/**
 * A compiled script.
 *
 * @author  noblemaster
 * @since  October 21, 2009
 */
public interface Compile {

  /**
   * Executes the compiled script.
   * 
   * @param context  The context.
   * @param object  The input.
   * @return  The output.
   * @throws ScriptException  If there are errors executing the script.
   */
  Object execute(Context context, Object object) throws ScriptException;
}
