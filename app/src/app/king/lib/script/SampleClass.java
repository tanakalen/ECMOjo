package king.lib.script;

import king.lib.out.Error;
import king.lib.script.model.Executable;

/**
 * A sample class.
 * 
 * @author noblemaster
 * @since October 26, 2009
 */
public class SampleClass implements Executable {
    
  /**
   * Executes something.
   * 
   * @param object  The input.
   * @return  The output.
   */
  public Object execute(Object object) {
    System.out.println("Output Input: " + object);
    System.out.println("Randx: " + Math.random());
    System.out.println("Txxx: " + Thread.currentThread());
    try {
      System.out.println("ClassLoader " + getClass().getClassLoader().loadClass("java.io.File"));
    } 
    catch (ClassNotFoundException e) {
      System.out.println("Class not found 2 " + e);
    }
    Error.out("Test ERROR output");
    return 3.1415;
  }
}
