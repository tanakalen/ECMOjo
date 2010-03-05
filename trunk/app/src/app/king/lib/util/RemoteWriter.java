package king.lib.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import edu.hawaii.jabsom.tri.ecmo.app.ECMOAppRelease;

/**
 * The remote writer which POSTs data to a remote script.
 * <p>
 * Script code below (e.g. collector.php). Replace "email.com" with your domain information:
 * <code><pre>
      &lt;?php
          if ( $_POST['stacktrace'] == "" || $_POST['package_version'] == "" || $_POST['package_name'] == "" ) {
              die("This script is used to collect stacktraces caused by crashes. No personal information is " 
                . "transmitted, collected or stored. For more information, please contact &lt;a "
                . "href='mailto:contact@email.com'&gt;contact@email.com&lt;/a&gt;.");
          }
          $remote_src = $_POST['remote_src'];
          $remote_os = $_POST['remote_os'];
          $remote_uid = $_POST['remote_uid'];
          $remote_message = $_POST['remote_message'];
          $string_search = array(" ", "|", ",");
          $string_replace = array("-", "-", "-");
          $package = str_replace($string_search, $string_replace, strtolower($remote_src));
          $report = "SRC=" . $remote_src . ", OS=" . $remote_os . ", UID=" . $remote_uid . "&lt;br&gt;".$remote_message;
          $handle = fopen($package . "-log-" . time() . "-" . rand(1000,9999), "w+");
          fwrite($handle, $report);
          fclose($handle);
      
          // Uncomment and change the following line to have exceptions mailed to you
          // mail("contact@email.com", "Exception Received (" . $remote_src . ")", $report, "from:noreply@email.com");
      ?&gt;
 * </pre></code>
 * 
 * @author noblemaster
 * @since March 4, 2010
 */
public class RemoteWriter {

  /** Where to write to. Usually a remote script. */
  private String url;
  
  
  /**
   * The constructor.
   *
   * @param url  Where to write to.
   */
  public RemoteWriter(String url) {
    this.url = url;
  }
  
  /**
   * Writes a message.
   * 
   * @param message  The message to write.
   * @throws IOException  If there is a problem sending the message.
   */
  public void write(String message) throws IOException {
    // open the connection
    URLConnection connection = new URL(url).openConnection();
    connection.setDoOutput(true);  
    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());  
    
    // write the data
    String src = "ECMOjo " + ECMOAppRelease.getReleaseVersion();
    String os = System.getProperty("os.name") + " " + System.getProperty("os.version");
    String uid = System.getProperty("user.name");
    out.write(URLEncoder.encode("remote_src", "UTF-8") + "=" + URLEncoder.encode(src, "UTF-8")  
       +"&" + URLEncoder.encode("remote_os", "UTF-8") + "=" + URLEncoder.encode(os, "UTF-8")  
       +"&" + URLEncoder.encode("remote_uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8")
       +"&" + URLEncoder.encode("remote_message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8"));  
    
    // close the connection
    out.close();  
  }
}
