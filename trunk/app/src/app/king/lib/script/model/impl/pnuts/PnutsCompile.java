package king.lib.script.model.impl.pnuts;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

import pnuts.lang.Jump;
import pnuts.lang.Pnuts;
import pnuts.security.SecurePnutsImpl;
import king.lib.script.control.SandboxException;
import king.lib.script.control.SandboxSecurity;
import king.lib.script.control.ScriptException;
import king.lib.script.model.Compile;
import king.lib.script.model.Context;

/**
 * A compiled Pnuts script.
 *
 * @author  noblemaster
 * @since  October 22, 2009
 */
public class PnutsCompile implements Compile {

  /** Pnuts context with limited execution time. */
  private static final class MyContext extends pnuts.lang.Context {
    
    /** The end time. */
    long endTime;
    
    /** 
     * The constructor.
     *
     * @param context  The Pnuts context.
     * @param endTime  The end time.
     */
    private MyContext(pnuts.lang.Context context, long endTime) {
      super(context);
      this.endTime = endTime;
    }
    
    /**
     * Called when script is executed for each line.
     * 
     * @param line  The line number.
     */
    protected void updateLine(int line) {
      if (System.currentTimeMillis() > endTime) {
        throw new Jump(null); 
      }
    }
  }
  
  /** The Pnuts script. */
  private Pnuts pnuts;
  
  
  /**
   * The constructor.
   *
   * @param pnuts  The compiled script.
   */
  public PnutsCompile(Pnuts pnuts) {
    this.pnuts = pnuts;
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
    // create a context
    pnuts.lang.Package pnutsPackage = new pnuts.lang.Package();
    pnutsPackage.set("input".intern(), object);
    pnuts.lang.Context pnutsContext = new pnuts.lang.Context(pnutsPackage);
    
    // set time limit as needed    
    if (context.getMaxDuration() > 0) {
      pnutsContext = new MyContext(pnutsContext, System.currentTimeMillis() + context.getMaxDuration());
    }
    
    // make the pnuts context secure (no threads, file access, etc.)
context.setRestricted(false);
    if (context.isRestricted()) {
      CodeSource codeSource = pnuts.getClass().getProtectionDomain().getCodeSource();
      SecurePnutsImpl securePnutsImpl = new SecurePnutsImpl(pnutsContext.getImplementation(), codeSource);
      pnutsContext.setImplementation(securePnutsImpl);    
    }
    try {
System.out.println("AAA");
SandboxSecurity.install();
      SandboxSecurity.sandbox();
      Object result = pnuts.run(pnutsContext);
      SandboxSecurity.unsandbox();
SandboxSecurity.uninstall();
      return result;
    }
    catch (Exception e) {
    
    }
    return null;
    
    
    // run it...
    /*
    ProtectionDomain protectionDomain = pnuts.getClass().getProtectionDomain();
    AccessControlContext accessContext = new AccessControlContext(new ProtectionDomain[] {protectionDomain});
    final pnuts.lang.Context finalPnutsContext = pnutsContext;
    try {
      return AccessController.doPrivileged(new PrivilegedExceptionAction() {
        public Object run() {
          return pnuts.run(finalPnutsContext);
        }
      }, accessContext);    
    }
    catch (PrivilegedActionException e) {
      throw new ScriptException(e);
    }   
    */
  }
}
