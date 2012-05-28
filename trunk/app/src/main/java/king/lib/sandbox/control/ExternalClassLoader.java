package king.lib.sandbox.control;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads external classes from URLs and raw byte data.
 * 
 * @author noblemaster
 * @since October 30, 2009
 * @access Public
 */
public class ExternalClassLoader extends URLClassLoader {
 
  /** The class data if any. */
  private Map classData = new HashMap();
  
  
  /**
   * The constructor.
   *
   * @param parent  The parent class loader.
   */
  public ExternalClassLoader(ClassLoader parent) {
    super(new URL[0], parent);
  }

  /**
   * Adds an URL.
   * 
   * @param url  The class file URL. For example: "file:///C:\\temp\\".
   * @throws SandboxException  If there is a problem.
   */
  public void addURL(String url) throws SandboxException {
    try {
      addURL(new URL(url));
    }
    catch (MalformedURLException e) {
      throw new SandboxException(e);
    }
  }
  
  /**
   * Adds a class.
   * 
   * @param name  The name, e.g. "com.company.SomeClass".
   * @param b  The bytes.
   */
  public void addClass(String name, byte[] b) {
    classData.put(name, defineClass(name, b, 0, b.length));
  }
  
  /**
   * Loads the actual classes. Throws an IllegalArgumentException if a class is not allowed to be used.
   * 
   * @param name  The name of the class to load.
   * @param resolve  True to resolve the class.
   * @throws ClassNotFoundException  If the class cannot be found.
   * @return The class loaded.
   */
  @Override
  protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
    try {
      // 1. try to find in URL paths
      return super.findClass(name);
    }
    catch (ClassNotFoundException e) {
      // 2. let's check if we have the class defined via byte array
      if (classData.containsKey(name)) {
        return (Class)classData.get(name);
      }
      else {
        // 3. let's also check the standard class loader
        return super.loadClass(name, resolve); 
      }
    }
  }
}
