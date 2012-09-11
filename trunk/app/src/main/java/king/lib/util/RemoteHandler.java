package king.lib.util;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * The remote handler which handles java.util.logging data and writes the data to a remote location.
 * 
 * @author noblemaster
 * @since March 4, 2010
 */
public class RemoteHandler extends Handler {

  /** The writer. */
  private RemoteWriter writer;
  
  
  /**
   * The constructor.
   *
   * @param writer  Where to write the data to.
   */
  public RemoteHandler(RemoteWriter writer) {
    this.writer = writer;
  }
  
  /**
   * Publishes a log record.
   * 
   * @param record  The log record.
   */
  @Override
  public void publish(LogRecord record) {
    // and write the record in full
    try {
      String message = getFormatter().format(record);
      writer.write(message);
    }
    catch (IOException e) {
      System.out.println("Error logging: " + e);
    }
  }
  
  /**
   * Flushes out the data.
   */
  @Override
  public void flush() {
    // not used
  }
  
  /**
   * Closes the output stream.
   * 
   * @throws SecurityException  For security problems.
   */
  @Override
  public void close() throws SecurityException {
    // not used
  }
}
