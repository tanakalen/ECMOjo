package king.lib.access;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Downloads a file from an URL to the local file system.
 * 
 * @author   king
 * @since    June 1, 2006
 */
public class FileDownloader implements Runnable {
  
  /** The remote URL. */
  private String remoteURL;
  /** The local file path to store the file at. */
  private String localPath;
  
  /** The downloader thread. */
  private Thread thread = null;
  /** True, if completed successfully. */
  private boolean completed = false;
  /** The error message if not completed successfully. */
  private String errorMessage = null;
  
  /** The total size of the download or -1 for unknown. */
  private int totalSize = 0;
  /** The current size of the download. */
  private int currentSize = 0;
  
  
  /**
   * File downloader constructor.
   * 
   * @param remoteURL  The URL to load the file from.
   * @param localPath  The path to save the file to.
   */
  public FileDownloader(String remoteURL, String localPath) {
    this.remoteURL = remoteURL;
    this.localPath = localPath;
  }
  
  /**
   * Starts the file download.
   */
  public void start() {
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  /**
   * Stops the file download.
   */
  public void stop() {
    this.thread = null;
  }
  
  /**
   * Returns true if successfully completed.
   * 
   * @return  True for successfully completed.
   */
  public boolean isCompleted() {
    return this.completed;
  }
  
  /**
   * Returns the error message if failure.
   * 
   * @return Returns the error message or null for no error.
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Returns the current size of the download.
   * 
   * @return Returns the current size.
   */
  public int getCurrentSize() {
    return currentSize;
  }

  /**
   * Returns the total size of the download.
   * 
   * @return Returns the total size or -1 for unknown.
   */
  public int getTotalSize() {
    return totalSize;
  }

  /**
   * True if downloading.
   * 
   * @return  True if downloading, false otherwise.
   */
  public boolean isRunning() {
    return this.thread != null;
  }
  
  /**
   * The downloader thread.
   */
  public void run() {
    // open URL and file first
    InputStream inputStream;
    OutputStream outputStream;
    
    try {
      // open input
      URL url = new URL(remoteURL);
      URLConnection urlConnection = url.openConnection();
      this.totalSize = urlConnection.getContentLength();
      inputStream = urlConnection.getInputStream();
      
      // open output
      File localFile = new File(localPath);
      localFile.getParentFile().mkdir();
      outputStream = LocalHookup.getInstance().getOutputStream(localPath);
    }
    catch (Exception e) {
      // error opening
      this.errorMessage = e.getMessage();
      this.completed = false;
      this.thread = null;
      return;
    }
    
    // downloader thread
    byte[] buffer = new byte[2048];
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      try {
        int bytesRead = inputStream.read(buffer);
        if (bytesRead == -1) {
          inputStream.close();
          outputStream.close();
          
          // successful
          this.errorMessage = null;
          this.completed = true;
          this.thread = null;
          return;
        }
        else {
          this.currentSize += bytesRead;
          outputStream.write(buffer, 0, bytesRead);
        }
      }
      catch (Exception e) {
        // failure
        this.errorMessage = e.getMessage();
        this.completed = false;
        this.thread = null;
        return;
      }
    }
    
    // delete file
    try {
      inputStream.close();
      outputStream.close();
      inputStream = null;
      outputStream = null;
      
      File file = new File(localPath);
      file.delete();
    }
    catch (Exception e) {
      // failure
      this.errorMessage = e.getMessage();
      this.completed = false;
      this.thread = null;
      return;
    }
  }
}
