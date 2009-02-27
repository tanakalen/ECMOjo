package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.hawaii.jabsom.tri.ecmo.app.control.Manager.ManagerListener;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal;
import edu.hawaii.jabsom.tri.ecmo.app.view.ManagerPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.TutorialPanel;

import java.awt.BorderLayout;
import java.awt.Component;

/**
 * The game state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class GameStatePanel extends JPanel implements ManagerListener {

  /** The game state. */
  private GameState state;
  
  /** The old glass pane. */
  private Component glassPane;
  
  
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public GameStatePanel(GameState state) {
    this.state = state;
    
    // set look
    setOpaque(false);
    
    // set layout
    setLayout(new BorderLayout());
    
    // add game view panel
    ManagerPanel mainPanel = new ManagerPanel(state.getManager());
    add(mainPanel, BorderLayout.CENTER);
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add tutorial panel
    Goal goal = state.getManager().getGame().getGoal();
    if (goal instanceof TutorialGoal) {
      glassPane = getRootPane().getGlassPane();
      getRootPane().setGlassPane(new TutorialPanel((TutorialGoal)goal));    
      getRootPane().getGlassPane().setVisible(true);
    }
    
    // add listener
    state.getManager().addManagerListener(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    state.getManager().removeManagerListener(this);
    
    // remove tutorial panel
    if (glassPane != null) {
      getRootPane().setGlassPane(glassPane);
      getRootPane().getGlassPane().setVisible(false);
      glassPane = null;
    }
    
    super.removeNotify();
  }
  
  /**
   * Called when the goal is reached.
   */
  public void goalReached() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        // proceed to the result state
        state.resultState();
      }
    });
  }
}
