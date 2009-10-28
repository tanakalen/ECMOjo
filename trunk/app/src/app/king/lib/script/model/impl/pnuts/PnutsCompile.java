package king.lib.script.model.impl.pnuts;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

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
    // create a context
    pnuts.lang.Package pnutsPackage = new pnuts.lang.Package();
    pnutsPackage.set("input".intern(), object);
    pnuts.lang.Context pnutsContext = new pnuts.lang.Context(pnutsPackage);
    
    // set time limit as needed    
    if (context.getMaxDuration() > 0) {
      pnutsContext = new MyContext(pnutsContext, System.currentTimeMillis() + context.getMaxDuration());
    }
    
    // make the pnuts context secure (no threads, file access, etc.)
    if (context.isRestricted()) {
      pnutsContext.setImplementation(new SecurePnutsImpl(pnutsContext.getImplementation()));    
      pnutsContext.setConfiguration(new LimitedClassesConfiguration() {
        @Override
        public Constructor[] getConstructors(Class cls) {
          return new Constructor[0];
        }
        public Method[] getMethods(Class cls) {
          return new Method[0];
        }
        protected String[] getDefaultImports() {
          return new String[0];
        }
        protected ClassLoader getInitialClassLoader() {
          return null;
        }
        public List createList() {
          return null;
        }
        public Object callConstructor(pnuts.lang.Context context, Class c, Object[] args, Class[] types) {
           System.out.println("c " + c.getName());
           return super.callConstructor(context, c, args, types);
//         throw new IllegalArgumentException("xxx");
        }
        public Object callMethod(pnuts.lang.Context context, Class c, String name, Object[] args, Class[] types,
            Object target) {
           System.out.println("a " + c.getName() + " " + name + " " + target);
           return super.callMethod(context, c, name, args, types, target);
//           throw new IllegalArgumentException("xxx");
        }
        @Override
        public Object getStaticField(pnuts.lang.Context context, Class clazz, String name) {
          System.out.println("b " + clazz);
          return super.getStaticField(context, clazz, name);
        }
        @Override
        public void putStaticField(pnuts.lang.Context context, Class clazz, String name, Object value) {
          System.out.println("c " + clazz);
          super.putStaticField(context, clazz, name, value);
        }
        @Override
        public Callable toCallable(Object obj) {
          System.out.println("d " + obj);
          return super.toCallable(obj);
        }
        @Override
        public void registerClass(Class cls) {
          System.out.println("e " + cls);
          super.registerClass(cls);
        }
        @Override
        public Object handleUndefinedSymbol(String symbol, pnuts.lang.Context context) {
          System.out.println("f " + symbol);
          return super.handleUndefinedSymbol(symbol, context);
        }
        @Override
        public void putField(pnuts.lang.Context context, Object target, String name, Object value) {
          System.out.println("g " + target);
          super.putField(context, target, name, value);
        }      
      });
    }
    
    // and run the script
    return pnuts.run(pnutsContext);
  }
}
