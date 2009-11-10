package king.lib.script.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pnuts.lang.ParseException;
import pnuts.lang.Pnuts;
import king.lib.script.model.Compile;
import king.lib.script.model.Context;
import king.lib.script.model.Language;
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
   * Parses and compiles a script.
   * <p>
   * Limitations: 
   * <ul>
   *   <li><b>Pnuts</b>: no problems
   *   <li><b>Java</b>: can only compile with imports from the Java standard packages. Custom imports that
   *       are not available in the standard packages cannot be found and will result in a compiler error. Also,
   *       inner classes are not supported.
   *   <li><b>Rhino</b>: not supported yet.
   * </ul>
   * 
   * @param script  The script.
   * @return  The compiled script.
   * @throws CompileException  If there is a compiling problem.
   */
  public static Compile compile(Script script) throws CompileException {
    Language language = Language.find(script.getLang());
    if (language == Language.JAVA) {
      try {
        // write the file
        String name = JavaCompile.SCRIPT_NAME;
        String random = System.nanoTime() + "" + ((int)(Math.random() * 10000000));
        String dir = System.getProperty("java.io.tmpdir") + "/Java/com.noblemaster.lib.script.Java/" + random + "/";
        new File(dir).mkdirs();
        String path = dir + name + ".java";
        DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(path)));
        out.writeBytes(script.getCode());
        out.close();
        out = null;
  
        // compile Java | NOTE/TODO: Java 6 can compile in memory :)
        try {
          Process process = Runtime.getRuntime().exec("javac \"" + path + "\"");
          String error = "unknown error";
          try {
            // read err input
            BufferedReader errIn = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuffer err = new StringBuffer("");
            int ch;
            while ((ch = errIn.read()) != -1) {
              err.append((char)ch);
            }
            errIn.close();
            if (err.length() > 0) {
              error = err.toString();
            }
            
            // wait for it to end
            process.waitFor();
          } 
          catch(InterruptedException e) { 
            System.out.println(e); 
          }
          int ret = process.exitValue();            
          if (ret == 0) {
            // load the class bytes
            File file = new File(dir + name + ".class");
            byte[] b = new byte[(int)file.length()];
            InputStream in = new FileInputStream(file);
            in.read(b, 0, b.length);
            in.close();
            in = null;
            file.delete();
            
            // return the java compile
            return new JavaCompile(b);
          }
          else {
            throw new CompileException(error);
          }
        }
        finally {
          // cleanup temp directory
          new File(path).delete();
          new File(dir).delete();
        }
      }
      catch (IOException e) {
        throw new CompileException(e);
      }
    }
    else if (language == Language.PNUTS) {
      try {
        // parse script
        Pnuts pnuts = Pnuts.parse(script.getCode());
        
        // return the compile
        return new PnutsCompile(pnuts);
      }
      catch (ParseException e) {
        throw new CompileException(e.getMessage(), e.getErrorLine());
      }
    }
    else if (language == Language.RHINO) {
      throw new CompileException("error.RhinoNotSupported[i18n]: Rhino is not supported.");
    }
    else if (language == Language.BEANSHELL) {
      throw new CompileException("error.BeanShellNotSupported[i18n]: BeanShell is not supported.");
    }
    else {
      throw new CompileException("error.ScriptLanguageNotSupported[i18n]: The scripting language is not supported.");
    }
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
    return execute(compile(script), context, object);
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
