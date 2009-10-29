package king.lib.script.model.java;

import king.lib.script.control.ScriptException;
import king.lib.script.model.Compile;
import king.lib.script.model.Context;

/**
 * For compiled Java classes.
 *
 * @author  noblemaster
 * @since  October 28, 2009
 */
public class JavaCompile implements Compile {
  
  /** The bytes. */
  private byte[] raw;
  
  
  /**
   * The constructor.
   *
   * @param raw  The class bytes.
   */
  public JavaCompile(byte[] raw) {
    this.raw = raw;
  }
  
  /**
   * Executes the compiled script.
   * 
   * @param context  The context.
   * @param object  The input.
   * @return  The output.
   * @throws ScriptException  If there are errors executing the script.
   */
  public Object execute(Context context, Object object) throws ScriptException {   
    // TODO: implement!
    return null;
  }
}
