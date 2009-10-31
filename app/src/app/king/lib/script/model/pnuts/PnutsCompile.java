package king.lib.script.model.pnuts;

import pnuts.ext.ConfigurationAdapter;
import pnuts.lang.Jump;
import pnuts.lang.Pnuts;
import pnuts.security.SecurePnutsImpl;
import king.lib.sandbox.model.Sandbox;
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
    private long endTime;
    /** True for expired. */
    private boolean expired = false;
    
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
        expired = true;
        throw new Jump(null); 
      }
    }
  }
  
  /** The configuration which defines access. */
  private static final class MyConfiguration extends ConfigurationAdapter {
    
    /** The sandbox. */
    private Sandbox sandbox;
    
    /**
     * The constructor.
     *
     * @param sandbox  The sandbox.
     */
    private MyConfiguration(Sandbox sandbox) {
      this.sandbox = sandbox; 
    }
    
    /**
     * Intercepts constructor calls. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param clazz  The class.
     * @param args  The arguments.
     * @param types  The types.
     * @return  The return value.
     */
    @Override
    public Object callConstructor(pnuts.lang.Context context, Class clazz, Object[] args, Class[] types) {
      verifyAccess(clazz);
      return super.callConstructor(context, clazz, args, types);
    }
    
    /**
     * Intercepts method calls. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param clazz  The class.
     * @param name  The name.
     * @param args  The arguments.
     * @param types  The types.
     * @param target  The target.
     * @return  The return value.
     */
    @Override
    public Object callMethod(pnuts.lang.Context context, Class clazz, String name, Object[] args, Class[] types
                           , Object target) {
      verifyAccess(target);
      return super.callMethod(context, clazz, name, args, types, target);
    }
    
    /**
     * Intercepts static field access. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param clazz  The class.
     * @param name  The name.
     * @return  The return value.
     */
    @Override
    public Object getStaticField(pnuts.lang.Context context, Class clazz, String name) {
      verifyAccess(clazz.getName());
      return super.getStaticField(context, clazz, name);
    }
    
    /**
     * Intercepts static field access. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param clazz  The class.
     * @param name  The name.
     * @param value  The value.
     */
    @Override
    public void putStaticField(pnuts.lang.Context context, Class clazz, String name, Object value) {
      verifyAccess(clazz);
      super.putStaticField(context, clazz, name, value);
    }
    
    /**
     * Intercepts field access. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param target  The target.
     * @param name  The name.
     * @return  The return value.
     */
    @Override
    public Object getField(pnuts.lang.Context context, Object target, String name) {
      verifyAccess(target);
      return super.getField(context, target, name);
    }
    
    /**
     * Intercepts field access. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param target  The target.
     * @param name  The name.
     * @param value  The value.
     */
    @Override
    public void putField(pnuts.lang.Context context, Object target, String name, Object value) {
      verifyAccess(target);
      super.putField(context, target, name, value);
    }
    
    /**
     * Intercepts element access. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param target  The target.
     * @param key  The key.
     * @return  The return value.
     */
    @Override
    public Object getElement(pnuts.lang.Context context, Object target, Object key) {
      verifyAccess(target);
      return super.getElement(context, target, key);
    }
    
    /**
     * Intercepts element access. Throws exceptions for no access.
     * 
     * @param context  The context.
     * @param target  The target.
     * @param key  The key.
     * @param value  The value.
     */
    @Override
    public void setElement(pnuts.lang.Context context, Object target, Object key, Object value) {
      verifyAccess(target);
      super.setElement(context, target, key, value);
    }
    
    /**
     * Verify the inputed class has access. Throws an illegal argument exception for no access.
     * 
     * @param clazz  The class to check.
     */
    private void verifyAccess(Class clazz) {
      verifyAccess(clazz.getName());
    }
    
    /**
     * Verify the inputed class has access. Throws an illegal argument exception for no access.
     * 
     * @param target  The target to check.
     */
    private void verifyAccess(Object target) {
      if (target instanceof Class) {
        verifyAccess(((Class)target).getName());
      }
      else {
        verifyAccess(target.getClass().getName());
      }
    }
    
    /**
     * Verify the inputed class has access. Throws an illegal argument exception for no access.
     * 
     * @param clazz  The class to check.
     */
    private void verifyAccess(String clazz) {
      if (!sandbox.hasAccess(clazz)) {
        throw new IllegalArgumentException("Access denied for: " + clazz);
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
    // create a Pnuts context
    pnuts.lang.Package pnutsPackage = new pnuts.lang.Package();
    pnutsPackage.set("input".intern(), object);
    pnuts.lang.Context pnutsContext = new pnuts.lang.Context(pnutsPackage);
    
    // set time limit as needed    
    MyContext timedContext = null;
    if (context.getMaxDuration() > 0) {
      timedContext = new MyContext(pnutsContext, System.currentTimeMillis() + context.getMaxDuration());
      pnutsContext = timedContext;
    }
    
    // make the pnuts context secure (no threads, file access, etc.)
    if (context.getSandbox() != null) {
      // there is probably no security manager set, so this might not actually do anything???
      pnutsContext.setImplementation(new SecurePnutsImpl(pnutsContext.getImplementation())); 
      
      // we restrict access to classes with that beauty!
      pnutsContext.setConfiguration(new MyConfiguration(context.getSandbox()));
    }
    
    // and run the script
    Object result = pnuts.run(pnutsContext);
    if ((timedContext != null) && (timedContext.expired == true)) {
      throw new ScriptException("Script took too long to execute.");
    }
    else {
      return result;
    }
  }
}
