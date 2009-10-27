package king.lib.script.control;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import king.lib.util.StringSet;

/**
 * The sandbox class loader.
 * 
 * @author noblemaster
 * @since October 26, 2009
 * @access Public
 */
public class SandboxClassLoader extends URLClassLoader {
 
  /** The classes we have legal access to. */
  private StringSet legalClasses = new StringSet();
  
  /** The class data if any. */
  private Map classData = new HashMap();
  
  
  /**
   * The constructor.
   *
   * @param parent  The parent class loader.
   */
  public SandboxClassLoader(ClassLoader parent) {
    super(new URL[0], parent);
  }

  /**
   * Adds a legal class.
   * 
   * @param name  The name, e.g. "java.lang.Math" or "java.lang.Object".
   */
  public void addLegal(String name) {
    legalClasses.add(name);
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
   * @throws SandboxException  If there is a problem.
   */
  public void addClass(String name, byte[] b) throws SandboxException {
    if (!legalClasses.contains(name)) {
      throw new SandboxException("Class name is not allowed: " + name);
    }
    else {
      classData.put(name, defineClass(name, b, 0, b.length));
    }
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
    if (legalClasses.contains(name)) {
      // it's a legal class, let's load it...
      try {
        return super.loadClass(name, resolve); 
      }
      catch (ClassNotFoundException e) {
        // let's check if we have the class defined via byte array
        if (classData.containsKey(name)) {
          return (Class)classData.get(name);
        }
        else {
          throw e;
        }
      }
    }
    else {
      throw new IllegalArgumentException("No permission to load class: " + name);
    }
  }
}
