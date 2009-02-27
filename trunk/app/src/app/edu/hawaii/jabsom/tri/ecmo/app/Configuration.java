package edu.hawaii.jabsom.tri.ecmo.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import king.lib.access.Access;
import king.lib.access.LocalHookup;

/**
 * The configuration. 
 *
 * @author   king
 * @since    March 7, 2008
 */
public class Configuration {
  
  /** Admin property. */
  private static final String ADMIN = "ADMIN";
    
  /** The configuration instance. */
  private static Configuration instance;
  
  /** True for admin. */
  private boolean admin = false;
  
  
  /**
   * Init function for configuration.
   */
  public static void init() {
    instance = new Configuration();
    
    // load from the config file
    String applicationConfigFile = "Application.config";
    try {
      // load properties
      String path = Access.getInstance().getScenarioDir() + "/" + applicationConfigFile;
      InputStream inputStream = LocalHookup.getInstance().getInputStream(path);
      Properties properties = new Properties();
      properties.load(inputStream);
      inputStream.close();
        
      // read all properties
      instance.admin = Boolean.parseBoolean(properties.getProperty(ADMIN));
    }
    catch (IOException e) {
      System.out.println("Cannot find the config file: " + applicationConfigFile);
    }
  }

  /**
   * Returns the instance.
   *
   * @return the instance
   */
  public static Configuration getInstance() {
    return instance;
  }

  /**
   * Returns true for admin.
   *
   * @return  True for admin.
   */
  public boolean isAdmin() {
    return admin;
  }
  
  /**
   * Returns the string for the given key.
   * 
   * @param key  The key.
   * @return  The string belonging to the key or the key if it wasn't found.
   */
  public String getString(String key) {
    // NOTE: this is obviously not implemented yet
    // -> use MessagesBundle.properties when needed...
    return key;
  }
}
