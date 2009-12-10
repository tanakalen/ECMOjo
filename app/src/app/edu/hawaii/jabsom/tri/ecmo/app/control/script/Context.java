package edu.hawaii.jabsom.tri.ecmo.app.control.script;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The context for scripting. 
 *
 * @author noblemaster
 * @since December 8, 2009
 */
public interface Context {

  /**
   * Returns the game.
   *
   * @return  The game.
   */
  Game getGame();
  
  /**
   * Returns the progress information.
   * 
   * @return  The progress.
   */
  Progress getProgress();
  
  /**
   * Returns the console for debug output.
   * 
   * @return  The console.
   */
  Console getConsole();
  
  /**
   * Returns the notepad to take notes.
   * 
   * @return  The notepad with the notes.
   */
  Notepad getNotepad();

  /**
   * Returns the recorder to record actions.
   * 
   * @return  The recorder.
   */
  Recorder getRecorder();
}
