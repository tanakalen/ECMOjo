package king.lib.script.model.java;

import java.lang.reflect.Method;

import king.lib.sandbox.control.ExternalClassLoader;
import king.lib.sandbox.control.SandboxClassLoader;
import king.lib.sandbox.control.TimedExecutable;
import king.lib.sandbox.control.TimedRunner;
import king.lib.sandbox.model.ClassSandbox;
import king.lib.sandbox.model.CompositeSandbox;
import king.lib.sandbox.model.Sandbox;
import king.lib.sandbox.model.SandboxList;
import king.lib.script.control.ScriptException;
import king.lib.script.model.Compile;
import king.lib.script.model.Context;
import king.lib.util.StringSet;

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
    ExternalClassLoader classLoader;
    if (context.getSandbox() != null) {
      SandboxList sandboxes = new SandboxList();
      StringSet accessibleClasses = new StringSet();
      accessibleClasses.add(SCRIPT_NAME);
      accessibleClasses.add("java.lang.Object");
      sandboxes.add(new ClassSandbox(accessibleClasses));
      sandboxes.add(context.getSandbox());
      Sandbox sandbox = new CompositeSandbox(sandboxes);
      classLoader = new SandboxClassLoader(sandbox, getClass().getClassLoader());
    }
    else {
      classLoader = new ExternalClassLoader(getClass().getClassLoader());
    }
    
    // add class to class loader
    classLoader.addClass(SCRIPT_NAME, raw);
    
    // load the method
    Method method;
    try {
      Class clazz = classLoader.loadClass(SCRIPT_NAME);
      method = clazz.getMethod("execute", Object.class);
    }
    catch (Exception e) {
      throw new ScriptException(e);
    }
    
    // execute TIMED -OR- NON-TIMED
    if (context.getMaxDuration() > 0) {
      // create the executable that runs TIMED (limited time)
      final Method finalMethod = method;
      final Object finalObject = object;
      TimedExecutable executable = new TimedExecutable() {
        public Object execute() throws Exception {
          try {
            return finalMethod.invoke(null, finalObject);
          }
          catch (Exception e) {
            throw new ScriptException(e);
          }
        }
      };       
      try {
        // run with time limit
        return TimedRunner.execute(executable, context.getMaxDuration());
      }
      catch (ScriptException e) {
        throw e;
      }
      catch (Exception e) {
        throw new ScriptException(e);
      }
    }
    else {
      // run NON-TIMED
      try {
        return method.invoke(null, object);
      }
      catch (Exception e) {
        throw new ScriptException(e);
      }
    }
  }
}
