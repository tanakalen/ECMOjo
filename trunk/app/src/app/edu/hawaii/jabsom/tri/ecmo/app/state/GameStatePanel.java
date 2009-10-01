package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.hawaii.jabsom.tri.ecmo.app.control.Manager.ManagerListener;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.SimulationGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal;
import edu.hawaii.jabsom.tri.ecmo.app.view.ManagerPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.TutorialPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import king.lib.access.ImageLoader;

/**
 * The game state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class GameStatePanel extends JPanel implements ManagerListener, KeyEventDispatcher {

  /** The game state. */
  private GameState state;
  
  
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public GameStatePanel(final GameState state) {
    this.state = state;
    
    // set look
    setOpaque(false);
    
    // set layout
    setLayout(new BorderLayout());
    
    // add game view panel
    ManagerPanel mainPanel = new ManagerPanel(state.getManager());
    add(mainPanel, BorderLayout.CENTER);
    
    // add tutorial panel or exit button as needed
    Goal goal = state.getManager().getGame().getGoal();
    if (goal instanceof TutorialGoal) {
      // add the tutorial panel
      TutorialPanel tutorialPanel = new TutorialPanel((TutorialGoal)goal);   
      mainPanel.add(tutorialPanel);
    }
    else {
      // add exit button
      Image exitNormalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Exit.png");
      Image exitRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ExitRol.png");
      Image exitSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ExitSel.png");
      ImageButton exitButton = new ImageButton(exitNormalImage, exitRolloverImage, exitSelectedImage);
      exitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
          exit();
        }      
      });
      exitButton.setLocation(308, 18);
      exitButton.setSize(120, 48);
      mainPanel.add(exitButton);
    }
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    state.getManager().addManagerListener(this);
    
    // listen to key presses
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {  
    // remove key listener
    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);

    // remove listener
    state.getManager().removeManagerListener(this);
    
    super.removeNotify();
  }
  
  /** 
   * Listen to key events. 
   * 
   * @param event  The event.
   * @return  True, if the event was handled.
   */
  public boolean dispatchKeyEvent(KeyEvent event) {
    // handle key event
    if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
      // [ESC] = exit
      exit();
    }

    // If the key should not be dispatched to the focused component, set discard event to true
    return false;
  }

  /**
   * Called when the state is exited.
   */
  public void exit() {
    Goal goal = state.getManager().getGame().getGoal();
    if (goal instanceof SimulationGoal) {
      // and exit to menu
      state.menuState();
    }
    else {
      // exit to result state
      state.resultState();
    }
  }
  
  /**
   * Called when the goal is reached.
   */
  public void goalReached() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Goal goal = state.getManager().getGame().getGoal();
        if (goal instanceof TutorialGoal) {
          // show completed dialog
          StandardDialog.showDialog(GameStatePanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Tutorial Completed"
              , "You successfully completed the tutorial.");

          // and exit to menu
          state.menuState();
        }
        else {
          // proceed to the result state
          state.resultState();
        }
      }
    });
  }
}
