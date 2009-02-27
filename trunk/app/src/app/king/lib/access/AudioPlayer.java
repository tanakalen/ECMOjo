package king.lib.access;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

/**
 * Plays a sound. Internally used by AudioLoader.
 *
 * @author    king 
 * @since     March 23, 2004
 */
class AudioPlayer implements Runnable {

  /** The thread which plays the file. */
  private Thread thread;
  
  /** The audio loader which is interested in Exceptions. */
  private AudioLoader audioLoader;
  /** The format of the audio file to play. */
  private AudioFormat audioFormat;
  /** The samples to play. */
  private AudioInputStream audioStream;
  
  /** Temp array for data to play. */
  private byte[] playData = new byte[4096];
  
  /** Returns true if playing, false otherwise (false = available). */
  private boolean playing = false;

  
  /**
   * Plays a song. Starts the playing and leaves.
   * 
   * @param audioLoader   The audio loader which is interested in the status. I.e. if
   *                      exceptions occured.
   * @param audioFormat   The format of the audio file to play.
   * @param audioStream   Where the audio data resides.
   */
  void play(AudioLoader audioLoader, AudioFormat audioFormat, AudioInputStream audioStream) {
    this.audioLoader = audioLoader;
    this.audioFormat = audioFormat;
    this.audioStream = audioStream;
    
    // Thread which plays the file.
    this.thread = new Thread(this);
    this.thread.start();
    this.playing = true;
  }

  /**
   * True, if song is playing.
   * 
   * @return  True if song is playing.
   */
  boolean isPlaying() {
    return this.playing;
  }
  
  /**
   * Stops playing a song.
   */
  void stop() {
    this.thread = null;
  }
  
  /**
   * Main thread to play an audio file.
   */
  public void run() {
    try {
      // open line to play to
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
      line.open(audioFormat);
      
      // write data to line and wait until finished
      line.start();

      // write data piece for piece
      Thread currentThread = Thread.currentThread();
      int bytesRead = 0;
      while ((this.thread == currentThread) && (bytesRead != -1)) {
        bytesRead = audioStream.read(playData, 0, playData.length);
        if (bytesRead != -1) {
          line.write(playData, 0, bytesRead);
        }
      }
      // end sound stream
      line.drain();
      line.close();
    }
    catch (LineUnavailableException e) {
      this.audioLoader.notifyAudioException(new AudioException("Error playing audio: " + e));
    }
    catch (IOException e) {
      this.audioLoader.notifyAudioException(new AudioException("Error reading audio: " + e));
    }
    
    // close player
    try {
      this.audioStream.close();
    }
    catch (IOException e) {
      this.audioLoader.notifyAudioException(new AudioException("Error closing audio: " + e));
    }
    this.audioStream = null;
    this.audioFormat = null;
    this.thread = null;
    this.playing = false;
  }  
}
