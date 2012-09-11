package king.lib.script.control;

import king.lib.script.model.Compile;
import king.lib.script.model.Context;
import king.lib.script.model.Script;

/**
 * A script executer.
 *
 * @author  noblemaster
 * @since  October 21, 2009
 */
public class ScriptRunner {

  /** The default instance in sandboxed mode. */
  private static ScriptRunner instance;
  
  /** The context. */
  private Context context;
  
  
  /**
   * The default constructor which uses the default context in sandboxed mode.
   */
  public ScriptRunner() {
    this(new Context());
  }

  /**
   * The constructor.
   * 
   * @param context  The context.
   */
  public ScriptRunner(Context context) {
    this.context = context;
  }

  /**
   * Returns the default script runner.
   * 
   * @return  The default script runner in sandboxed mode.
   */
  public static ScriptRunner getDefault() {
    if (instance == null) {
      instance = new ScriptRunner();
    }
    return instance;
  }
    
  /**
   * Executes a script. Please execute a compile if a script is executed more than once to improve 
   * performance. The input is assumed to be null.
   * 
   * @param script  The script.
   * @return  The return value if any.
   * @throws CompileException  If there is a compiling problem.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public Object execute(Script script) throws CompileException, ScriptException {
    return execute(script, context, null);
  }
  
  /**
   * Executes a script. Please execute a compile if a script is executed more than once to improve 
   * performance.
   * 
   * @param script  The script.
   * @param object  The input.
   * @return  The return value if any.
   * @throws CompileException  If there is a compiling problem.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public Object execute(Script script, Object object) throws CompileException, ScriptException {
    return execute(script, context, object);
  }
  
  /**
   * Executes a script. Please execute a compile if a script is executed more than once to improve 
   * performance.
   * 
   * @param script  The script.
   * @param context  The context.
   * @param object  The input.
   * @return  The return value if any.
   * @throws CompileException  If there is a compiling problem.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public static Object execute(Script script, Context context, Object object) throws CompileException, ScriptException {
    return execute(ScriptCompiler.compile(script), context, object);
  }
  
  /**
   * Executes a compile. This is preferred over executing a script, if a script is going to be 
   * executed more than once. Or execute the compile directly by calling its execute method. The 
   * input is assumed to be null.
   * 
   * @param compile  The compile.
   * @return  The return value if any.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public Object execute(Compile compile) throws ScriptException {
    return execute(compile, context, null);
  }
  
  /**
   * Executes a compile. This is preferred over executing a script, if a script is going to be 
   * executed more than once. Or execute the compile directly by calling its execute method.
   * 
   * @param compile  The compile.
   * @param object  The input.
   * @return  The return value if any.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public Object execute(Compile compile, Object object) throws ScriptException {
    return execute(compile, context, object);
  }
  
  /**
   * Executes a compile. This is preferred over executing a script, if a script is going to be 
   * executed more than once. Or execute the compile directly by calling its execute method.
   * 
   * @param compile  The compile.
   * @param context  The context.
   * @param object  The input.
   * @return  The return value if any.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public static Object execute(Compile compile, Context context, Object object) throws ScriptException {
    return compile.execute(context, object);
  }
}
