package king.lib.access;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

/**
 * Allows to run a webstart application.
 *
 * @author  Christoph Aschwanden
 * @since  March 30, 2006
 */
public final class WebstartRunner {

  
  /**
   * Private to prevent instantiation of this class.
   */
  private WebstartRunner() {
    // does nothing.
  }
  
  /**
   * Runs the inputted jnlp file. 
   * 
   * @param jnlp  The location of the online jnlp to run.
   * @param localVersion  The version of the installer we are running to start the jnlp.
   */
  public static void run(String jnlp, int localVersion) {
    // find if OS is windows
    String osName = System.getProperty("os.name").toLowerCase();
    boolean windowsOS = osName.indexOf("windows") >= 0;
    boolean macOSX = osName.indexOf("mac os x") >= 0;

    // find Java home directory
    String javaHome = System.getProperty("java.home");
    String fileSeparator = System.getProperty("file.separator");
    
    // set quoting style (windows only can use quotes)
    String quote = windowsOS ? "\"" : "";
    
    // check if version is still good
    String jnlpPath = jnlp.substring(0, jnlp.lastIndexOf("/") + 1);
    String requiredVersionFile = jnlpPath + "RequiredWebstartRunner.version";
    try {
      // find remote version
      URL url = new URL(requiredVersionFile);
      URLConnection connection = url.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line = reader.readLine();
      int remoteVersion = Integer.parseInt(line);
      
      // check if still valid
      if (remoteVersion > localVersion) {
        // tell newer version available
        JOptionPane.showMessageDialog(null
            , "A new version of the Aevum Obscurum game client is available!\n"
            + "Please go to http://www.aevumobscurum.com for information and download.\n"
            + "\n"
            + "Your Installed Version: V" + localVersion + "\n"
            + "Required Version: V" + remoteVersion + "\n"
            + "\n"
            , "Aevum Obscurum: New Version Available."
            , JOptionPane.ERROR_MESSAGE);

        // need to redownload!
        System.out.println("Redownload of installer required. New version available");
      }
      else {
        // build list of commands
        String[] cmd = new String[2];
        if (macOSX) {
          cmd[0] = "javaws"; 
        }
        else {
          cmd[0] = quote + javaHome + fileSeparator + "bin" + fileSeparator + "javaws" + quote;
        }
        cmd[1] = jnlp;
        
        // set property!
        System.setProperty("runtime.start.method", "install4j");
        System.setProperty("runtime.start.version", "" + localVersion);
        
        // and execute command!
        try {
          Runtime.getRuntime().exec(cmd);
        }
        catch (IOException e) {
          System.err.println("Cannot start the webstart file (" + jnlp + "): " + e);
        }
      }
    }
    catch (IOException e) {
      // server down or file not found - connection cannot be established
      JOptionPane.showMessageDialog(null
          , "The connection to the Aevum Obscurum server could not be established.\n"
          + "Please try again later or go to http://www.aevumobscurum.com for further "
          + "information.\n"
          + "\n"
          + "We apologize for any inconvenience this might cause you.\n"
          + "\n"
          + "\n"
          , "Error Connecting to Aevum Obscurum Server"
          , JOptionPane.ERROR_MESSAGE);
      
      // output error to console
      System.err.println("IO exception (" + requiredVersionFile + "): " + e);
    }
  }
  
  /** 
   * The main method which will be called.
   *
   * @param args  The arguments for the webstart runner. 
   *              args[0] should be the location of the .jnlp file to run. 
   *              args[1] the version of the installer.
   */
  public static void main(String[] args) {
    run(args[0], Integer.parseInt(args[1]));
  }
}
