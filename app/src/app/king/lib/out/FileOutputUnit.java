package king.lib.out;

import king.lib.access.LocalHookup;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Outputs a message to a file. 
 *
 * @author   king
 * @since    June 27, 2004
 */
public class FileOutputUnit extends OutputUnit {

  /** The output stream where to send data to. */
  private DataOutputStream outputStream;
  
  
  /**
   * Constructor for file output.
   * 
   * @param path  The file path where to append data to.
   * @param append  True, for appending data to file.
   * @throws IOException  If something goes wrong opening the output stream.
   */
  public FileOutputUnit(String path, boolean append) throws IOException {
    // get output stream
    this.outputStream = new DataOutputStream(LocalHookup.getInstance().getOutputStream(path, append));
  }
  
  /**
   * Outputs a message to a file.
   *
   * @param message  The message to output.
   */
  protected synchronized void formattedOut(String message) {
    try {
      this.outputStream.writeBytes(message);
    }
    catch (IOException e) {
      Error.out("FileOutputUnit: Error writing to file: " + e);
    }
  }
}
