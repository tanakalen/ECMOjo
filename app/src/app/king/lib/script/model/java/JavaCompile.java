package king.lib.script.model.java;

import king.lib.sandbox.control.SandboxClassLoader;
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
  
  /** The script class name (does not have a package). */
  public static final String SCRIPT_NAME = "Script";
  
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
    // obtain class loader
    ClassLoader classLoader;
    if (context.getSandbox() != null) {
      classLoader = new SandboxClassLoader(context.getSandbox(), getClass().getClassLoader());
    }
    else {
      classLoader = getClass().getClassLoader();
    }
    
    // load the class
    return null; // todo
  }
}
