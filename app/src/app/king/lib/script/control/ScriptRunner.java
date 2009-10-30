package king.lib.script.control;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import pnuts.lang.ParseException;
import pnuts.lang.Pnuts;
import king.lib.script.model.Compile;
import king.lib.script.model.Context;
import king.lib.script.model.Script;
import king.lib.script.model.java.JavaCompile;
import king.lib.script.model.pnuts.PnutsCompile;

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
   * The default constructor which uses the default context in sandboxed mode.
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
      try {
        // write the file
        // TODO: needs rework (make sure another file does not exist already etc.)
        String name = "ScriptJava";
        String path = System.getProperty("java.io.tmpdir") + "/" + name + ".java";
        DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(path)));
        out.writeUTF(script.getCode());
        out.close();
        
        // compile Java
        Process process = Runtime.getRuntime().exec("javac " + path);
        try {
          process.waitFor();
        } 
        catch(InterruptedException e) { 
          System.out.println(e); 
        }
        int ret = process.exitValue();
        if (ret == 0) {
          // load the class bytes
          File file = new File(System.getProperty("java.io.tmpdir") + "/" + name + ".class");
          byte[] b = new byte[(int)file.length()];
          InputStream in = new FileInputStream(file);
          in.read(b, 0, b.length);
          in.close();
          
          // return the java compile
          return new JavaCompile(b);
        }
        else {
          throw new ScriptException("error.JavaCompileError[i18n]: The Java code could not be compiled.");
        }
      }
      catch (IOException e) {
        throw new ScriptException(e);
      }
    }
    else if (language.equalsIgnoreCase("pnuts")) {
      try {
        // parse script
        Pnuts pnuts = Pnuts.parse(script.getCode());
        
        // return the compile
        return new PnutsCompile(pnuts);
      }
      catch (ParseException e) {
        throw new ScriptException(e);
      }
    }
    else if (language.equalsIgnoreCase("rhino")) {
      throw new ScriptException("error.RhinoNotSupported[i18n]: Rhino is not supported.");
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
