package edu.hawaii.jabsom.tri.ecmo.app;

import java.io.InputStream;
import java.io.OutputStream;
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
    
  /** The selection. */
  private static final String SELECTION_SCENARIO_TAB = "SELECTION_SCENARIO_TAB";
  /** The selection. */
  private static final String SELECTION_VV_MODE_ECMO = "SELECTION_VV_MODE_ECMO";
  /** The selection. */
  private static final String SELECTION_SILICONE_OXYGENATOR = "SELECTION_SILICONE_OXYGENATOR";
  /** The selection. */
  private static final String SELECTION_ROLLER_PUMP = "SELECTION_ROLLER_PUMP";
  /** The selection. */
  private static final String SELECTION_CONVENTIONAL_VENT = "SELECTION_CONVENTIONAL_VENT";
  
  /** The configuration instance. */
  private static Configuration instance;
  
  /** The application type. */
  private AppType appType;
  
  /** True for admin. */
  private boolean admin;
  
  /** The selection. */
  private boolean selectionScenarioTab;
  /** The selection. */
  private boolean selectionVVModeECMO;
  /** The selection. */
  private boolean selectionSiliconeOxygenator;
  /** The selection. */
  private boolean selectionRollerPump;
  /** The selection. */
  private boolean selectionConventionalVent;
  
  
  /**
   * Init function for configuration.
   * 
   * @param appType  The application type.
   */
  public static void init(AppType appType) {
    instance = new Configuration();
    instance.appType = appType;
    
    // load from the config file
    instance.loadConfiguration();
  }

  /**
   * Loads the last configuration.
   */
  public void loadConfiguration() {
    String applicationConfigFile = "Application.config";
    String path = Access.getInstance().getScenarioDir() + "/" + applicationConfigFile;

    // Preload last configuration
    try {
      InputStream inputStream = LocalHookup.getInstance().getInputStream(path);
      Properties properties = new Properties();
      properties.load(inputStream);
   
      // read all properties
      admin = Boolean.parseBoolean(properties.getProperty(ADMIN, "false"));
      selectionScenarioTab = Boolean.parseBoolean(properties.getProperty(SELECTION_SCENARIO_TAB, "false"));
      selectionVVModeECMO = Boolean.parseBoolean(properties.getProperty(SELECTION_VV_MODE_ECMO, "true"));
      selectionSiliconeOxygenator = Boolean.parseBoolean(properties.getProperty(SELECTION_SILICONE_OXYGENATOR, "true"));
      selectionRollerPump = Boolean.parseBoolean(properties.getProperty(SELECTION_ROLLER_PUMP, "true"));
      selectionConventionalVent = Boolean.parseBoolean(properties.getProperty(SELECTION_CONVENTIONAL_VENT, "true"));
     
      // and close stream
      inputStream.close();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  /** 
   * Saves the current configuration to file.
   */
  public void saveConfiguration() {
    String applicationConfigFile = "Application.config";
    String path = Access.getInstance().getScenarioDir() + "/" + applicationConfigFile;

    // store data
    try {
      OutputStream outputStream = LocalHookup.getInstance().getOutputStream(path, false);
      Properties properties = new Properties();
      
      // set the properties
      properties.put(ADMIN, String.valueOf(admin));
      properties.put(SELECTION_SCENARIO_TAB, String.valueOf(selectionScenarioTab));
      properties.put(SELECTION_VV_MODE_ECMO, String.valueOf(selectionVVModeECMO));
      properties.put(SELECTION_SILICONE_OXYGENATOR, String.valueOf(selectionSiliconeOxygenator));
      properties.put(SELECTION_ROLLER_PUMP, String.valueOf(selectionRollerPump));
      properties.put(SELECTION_CONVENTIONAL_VENT, String.valueOf(selectionConventionalVent));
      
      // and close the stream
      properties.store(outputStream, "Default User Parameters");
      outputStream.close();
    }
    catch (Exception e) {
      System.out.println(e);
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
   * Returns the application type.
   * 
   * @return  The application type.
   */
  public AppType getAppType() {
    return appType;
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
   * Returns the selectionScenarioTab.
   *
   * @return  The selectionScenarioTab.
   */
  public boolean isSelectionScenarioTab() {
    return selectionScenarioTab;
  }

  /**
   * Sets the selectionScenarioTab.
   *
   * @param selectionScenarioTab  The selectionScenarioTab.
   */
  public void setSelectionScenarioTab(boolean selectionScenarioTab) {
    this.selectionScenarioTab = selectionScenarioTab;
  }

  /**
   * Returns the selectionVVModeECMO.
   *
   * @return  The selectionVVModeECMO.
   */
  public boolean isSelectionVVModeECMO() {
    return selectionVVModeECMO;
  }

  /**
   * Sets the selectionVVModeECMO.
   *
   * @param selectionVVModeECMO  The selectionVVModeECMO.
   */
  public void setSelectionVVModeECMO(boolean selectionVVModeECMO) {
    this.selectionVVModeECMO = selectionVVModeECMO;
  }

  /**
   * Returns the selectionSiliconeOxygenator.
   *
   * @return  The selectionSiliconeOxygenator.
   */
  public boolean isSelectionSiliconeOxygenator() {
    return selectionSiliconeOxygenator;
  }

  /**
   * Sets the selectionSiliconeOxygenator.
   *
   * @param selectionSiliconeOxygenator  The selectionSiliconeOxygenator.
   */
  public void setSelectionSiliconeOxygenator(boolean selectionSiliconeOxygenator) {
    this.selectionSiliconeOxygenator = selectionSiliconeOxygenator;
  }

  /**
   * Returns the selectionRollerPump.
   *
   * @return  The selectionRollerPump.
   */
  public boolean isSelectionRollerPump() {
    return selectionRollerPump;
  }

  /**
   * Sets the selectionRollerPump.
   *
   * @param selectionRollerPump  The selectionRollerPump.
   */
  public void setSelectionRollerPump(boolean selectionRollerPump) {
    this.selectionRollerPump = selectionRollerPump;
  }

  /**
   * Returns the selectionConventionalVentilator.
   *
   * @return  The selectionConventionalVentilator.
   */
  public boolean isSelectionConventionalVentilator() {
    return selectionConventionalVent;
  }

  /**
   * Sets the selectionConventionalVentilator.
   *
   * @param selectionConventionalVentilator  The selectionConventionalVentilator.
   */
  public void setSelectionConventionalVentilator(boolean selectionConventionalVentilator) {
    this.selectionConventionalVent = selectionConventionalVentilator;
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
