package king.lib.access;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import com.sun.media.sound.WaveFileReader;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Preloads sounds and makes them accessible. Errors are sent to all audio exception
 * listeners. The following jar libraries are needed to play mp3 files:
 * <ul>
 *   <li>jl1.0.jar</li>
 *   <li>mp3spi1.9.2.jar</li>
 *   <li>tritonus_share.jar</li>
 * </ul>
 * The following audio file formats are supported:
 * <ul>
 *   <li>wav
 *   <li>aiff
 *   <li>au
 *   <li>mp3
 * </ul>
 * 
 * @author    king 
 * @since     March 23, 2004
 */
public final class AudioLoader {

  /** Makes sure that there is only one audio loader available. */
  private static AudioLoader audioLoader = new AudioLoader();
  
  /** List of players that are currently instantiated. */
  private List<AudioPlayer> players = new ArrayList<AudioPlayer>();
  
  /** Listeners for audio events in case something goes wrong during audio play. */
  private List<AudioExceptionListener> audioListeners = new ArrayList<AudioExceptionListener>();

  /** True, if audio is enabled. Default is true. */
  private boolean audioEnabled = true;
  
  
  /**
   * Constructor for audio loader. Private to make sure nobody else
   * can instantiate it. Use getInstance() method instead.
   */
  private AudioLoader() {
    // does nothing, just makes sure nobody else can instantiate it.
  }
  
  /** 
   * The way to get an instance of audio loader.
   * 
   * @return  The one and only instance of audio loader.
   */
  public static AudioLoader getInstance() {
    return AudioLoader.audioLoader;
  }

  /**
   * Plays an audio file. ResourceHookup is where the file is expected to reside.
   * 
   * @param path  The path to the audio file to play.
   */
  public void play(String path) {
    play(path, ResourceHookup.getInstance());
  }

  /**
   * Plays an audio file.
   * 
   * @param path  The path to the audio file to play.
   * @param hookup  Where the file resides.
   */
  public void play(String path, Hookup hookup) {
    if (audioEnabled) {
      try {
        // open stream to sound file
        InputStream inputStream = hookup.getInputStream(path);
        AudioInputStream audioStream;
        
        // *** HACK START
        // *** Only audioStream = AudioSystem.getAudioInputStream(inputStream); 
        // *** doesn't work, WAV files are not loaded correctly because of mp3spi1.9.2.jar
        if (path.endsWith(".mp3")) {
          audioStream = AudioSystem.getAudioInputStream(inputStream);
        }
        else {
          WaveFileReader reader = new WaveFileReader();
          audioStream = reader.getAudioInputStream(inputStream);
        }
        // *** HACK END
  
        // get audio format
        AudioFormat audioFormat = audioStream.getFormat();
  
        // convert, so sound can be played
        if (path.endsWith(".mp3")) {
          if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            AudioFormat newFormat = new AudioFormat(
              AudioFormat.Encoding.PCM_SIGNED, 
              audioFormat.getSampleRate(),
              16,
              audioFormat.getChannels(),
              audioFormat.getChannels() * 2,
              audioFormat.getSampleRate(),
              false);
            AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, audioStream);
            audioFormat = newFormat;
            audioStream = newStream;
          }      
        }
       
        // Find player to be used
        AudioPlayer player = null;
        for (int i = 0; i < this.players.size(); i++) {
          if (!((AudioPlayer)this.players.get(i)).isPlaying()) {
            player = (AudioPlayer)this.players.get(i);
          }
        }
    
        // Create new player if none available.
        if (player == null) {
          player = new AudioPlayer();
          this.players.add(player);
        }
        player.play(this, audioFormat, audioStream);
      }
      catch (UnsupportedAudioFileException e) {
        notifyAudioException(new AudioException("Audio file " + path + " not supported: " + e));
      }
      catch (IOException e) {
        notifyAudioException(new AudioException("Audio file " + path + " IO problem: " + e));
      }
    }
  }

  /**
   * Stops all the songs currently playing.
   */
  public void stop() {
    for (int i = 0; i < this.players.size(); i++) {
      AudioPlayer player = (AudioPlayer)this.players.get(i);
      player.stop();
    }
  }
  
  /**
   * Returns true if audio is enabled.
   * 
   * @return Returns if audio is enabled.
   */
  public boolean isAudioEnabled() {
    return audioEnabled;
  }

  /**
   * Enables or disables audio.
   * 
   * @param audioEnabled  True to enable audio.
   */
  public void setAudioEnabled(boolean audioEnabled) {
    if (!audioEnabled) {
      stop();
    }
    this.audioEnabled = audioEnabled;
  }

  /**
   * Adds a listener for audio exceptions that occur.
   * 
   * @param audioExceptionListener  The listener for audio events to add.
   */
  public void addAudioExceptionListener(AudioExceptionListener audioExceptionListener) {
    this.audioListeners.add(audioExceptionListener);
  }
  
  /**
   * Removes a listener for audio exceptions that occur.
   * 
   * @param audioExceptionListener  The listener for audio events to remove.
   */
  public void removeAudioExceptionListener(AudioExceptionListener audioExceptionListener) {
    this.audioListeners.remove(audioExceptionListener);
  }
  
  /**
   * Returns all the listeners for audio exceptions that occur.
   * 
   * @return  All the listeners for problems during audio play.
   */
  public List<AudioExceptionListener> getAudioExceptionListeners() {
    return this.audioListeners;
  }
  
  /**
   * Handles exception from the audio player. Sends exceptions to
   * registered listeners.
   * 
   * @param audioException  The exception that occured at the audio player.
   */
  protected void notifyAudioException(AudioException audioException) {
    // notify all listener about exception
    for (AudioExceptionListener listener : this.audioListeners) {
      listener.audioExceptionThrown(audioException);
    }
  }
}
