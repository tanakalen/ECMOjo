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
import king.lib.script.model.Language;
import king.lib.script.model.Script;
import king.lib.script.model.java.JavaCompile;
import king.lib.script.model.pnuts.PnutsCompile;

/**
 * A script compiler.
 *
 * @author  noblemaster
 * @since  November 10, 2009
 */
public final class ScriptCompiler {

  /**
   * The constructor is private to prevent instantiation.
   */
  private ScriptCompiler() {
    // not used
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
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        String dir = "Java/com.noblemaster.lib.script.Java/" + random + "/"; //Windows???
        File scriptDir = new File(tmpDir, dir);
        scriptDir.mkdirs();
        File scriptFile = new File(scriptDir, name + ".java");
        DataOutputStream out = new DataOutputStream(new FileOutputStream(scriptFile));
        out.writeBytes(script.getCode());
        out.close();
        out = null;
  
        // compile Java | NOTE/TODO: Java 6 can compile in memory :)
        try {
          Process process = Runtime.getRuntime().exec("javac " + scriptFile.getName(), null, scriptDir);
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
            File file = new File(scriptDir, name + ".class");
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
          scriptFile.delete();
          scriptDir.delete();
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
}
