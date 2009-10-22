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

  /** The context. */
  private Context context;
  
  
  /**
   * The default constructor.
   */
  public  ScriptRunner() {
    this(new Context());
  }

  /**
   * The constructor.
   * 
   * @param context  The context.
   */
  public  ScriptRunner(Context context) {
    this.context = context;
  }

  /**
   * Parses a script and makes sure it can be executed.
   * 
   * @param script  The script.
   * @throws ScriptException  If the script couldn't be parsed.
   */
  public static void parse(Script script) throws ScriptException {
    // let's try to compile it...
    compile(script);
  }
  
  /**
   * Parses and compiles a script.
   * 
   * @param script  The script.
   * @return  The compiled script.
   * @throws ScriptException  If there is a compiling problem.
   */
  public static Compile compile(Script script) throws ScriptException {
    String language = script.getLang();
    if (language.equalsIgnoreCase("java")) {
      // TODO: implement
      return null;
    }
    else if (language.equalsIgnoreCase("pnuts")) {
      // TODO: implement
      return null;
/*
      // execute pnuts script (test)
      String sourceCode = "System.out.println(\"pnuts script executed\");\n"
                        + "thread = new Thread(new Runnable() {\n"
                        + "  run() {\n"
                        + "    System.out.println(\"thread!\");\n"
                        + "  }\n"
                        + "});\n"
                        + "thread.start();\n";
      try {
        // parse script
        Pnuts pnuts = Pnuts.parse(sourceCode);
        
        // run script
        pnuts.lang.Package pkg = new pnuts.lang.Package();
        pkg.set("someVariable".intern(), "any java object");
        Context context = new Context(pkg);
        
        // make it secure (no threads, file access, etc.)
        context.setImplementation(new SecurePnutsImpl(context.getImplementation()));
        
        // run it...
        pnuts.run(context);
      }
      catch (ParseException e) {
        throw new ScriptException(e.getMessage());
      }
*/
    }
    else {
      throw new ScriptException("error.ScriptLanguageNotSupported[i18n]: The script language is not supported.");
    }
  }
  
  /**
   * Executes a script. Please execute a compile if a script is executed more than once to improve 
   * performance.
   * 
   * @param script  The script.
   * @param object  The input.
   * @return  The return value if any.
   * @throws ScriptException  If there is a problem executing the script.
   */
  public Object execute(Script script, Object object) throws ScriptException {
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
   * @throws ScriptException  If there is a problem executing the script.
   */
  public static Object execute(Script script, Context context, Object object) throws ScriptException {
    return execute(compile(script), context, object);
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
