package king.lib.script.model.pnuts;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import pnuts.ext.LimitedClassesConfiguration;
import pnuts.lang.Callable;
import pnuts.lang.Jump;
import pnuts.lang.Pnuts;
import pnuts.security.SecurePnutsImpl;
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
    // create a Pnuts context
    pnuts.lang.Package pnutsPackage = new pnuts.lang.Package();
    pnutsPackage.set("input".intern(), object);
    pnuts.lang.Context pnutsContext = new pnuts.lang.Context(pnutsPackage);
    
    // set time limit as needed    
    if (context.getMaxDuration() > 0) {
      pnutsContext = new MyContext(pnutsContext, System.currentTimeMillis() + context.getMaxDuration());
    }
    
    // make the pnuts context secure (no threads, file access, etc.)
    if (context.getSandbox() != null) {
      // there is probably no security manager set, so this might not actually do anything???
      pnutsContext.setImplementation(new SecurePnutsImpl(pnutsContext.getImplementation())); 
      
      // we restrict access to classes with that beauty!
      pnutsContext.setConfiguration(new LimitedClassesConfiguration() {
        @Override
        public Constructor[] getConstructors(Class cls) {
          System.out.println("getConstructors: " + cls.getName());
          return super.getConstructors(cls);
        }
        @Override
        public Method[] getMethods(Class cls) {
          System.out.println("getMethods: " + cls.getName());
          return super.getMethods(cls);
        }
        @Override
        protected String[] getDefaultImports() {
          System.out.println("getDefaultImports");
          return super.getDefaultImports();
        }
        @Override
        protected ClassLoader getInitialClassLoader() {
          System.out.println("getInitialClassLoader");
          return super.getInitialClassLoader();
        }
        @Override
        public List createList() {
          System.out.println("createList");
          return super.createList();
        }
        @Override
        public Map createMap(int size, pnuts.lang.Context context) {
          System.out.println("createMap");
          return super.createMap(size, context);
        }
        @Override
        public Object callConstructor(pnuts.lang.Context context, Class c, Object[] args, Class[] types) {
          System.out.println("callConstructor: " + c.getName());
          return super.callConstructor(context, c, args, types);
        }
        @Override
        public Object callMethod(pnuts.lang.Context context, Class c, String name, Object[] args, Class[] types
                               , Object target) {
          System.out.println("callMethod: " + c.getName() + " " + name + " " + target);
          return super.callMethod(context, c, name, args, types, target);
        }
        @Override
        public Object getStaticField(pnuts.lang.Context context, Class clazz, String name) {
          System.out.println("getStaticField: " + clazz + " " + name);
          return super.getStaticField(context, clazz, name);
        }
        @Override
        public void putStaticField(pnuts.lang.Context context, Class clazz, String name, Object value) {
          System.out.println("putStaticField: " + clazz + " " + name);
          super.putStaticField(context, clazz, name, value);
        }
        @Override
        public Callable toCallable(Object obj) {
          System.out.println("toCallable: " + obj);
          return super.toCallable(obj);
        }
        @Override
        public void registerClass(Class cls) {
          System.out.println("registerClass: " + cls);
          super.registerClass(cls);
        }
        @Override
        public Object handleUndefinedSymbol(String symbol, pnuts.lang.Context context) {
          System.out.println("handleUndefinedSymbol: " + symbol);
          return super.handleUndefinedSymbol(symbol, context);
        }
        @Override
        public void putField(pnuts.lang.Context context, Object target, String name, Object value) {
          System.out.println("putField: " + target);
          super.putField(context, target, name, value);
        }      
      });
    }
    
    // and run the script
    return pnuts.run(pnutsContext);
  }
}
