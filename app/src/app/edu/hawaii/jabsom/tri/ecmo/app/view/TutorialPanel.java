package edu.hawaii.jabsom.tri.ecmo.app.view;

import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal.Item;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal.TutorialListener;

/**
 * The tutorial overlay. 
 *
 * @author   Christoph Aschwanden
 * @since    Jan 12, 2009
 */
public class TutorialPanel extends JPanel implements TutorialListener {

  /** The goal. */
  private TutorialGoal goal;
  
  /** The tutorial box. */
  private TutorialBox box;
    
  /**
   * Constructor for panel. 
   * 
   * @param goal  The goal.
   */
  public TutorialPanel(final TutorialGoal goal) {
    this.goal = goal;
    
    // set look
    setOpaque(false);
    
    // no layout
    setLayout(null);

    // add the tutorial box
    box = new TutorialBox();
    box.addTutorialBoxListener(new TutorialBox.TutorialBoxListener() {
      public void handleAction() {
        // proceed to the next state
       goal.proceed();
      }
    });
    box.setLocation(293, 2);
    box.setSize(312, 76);
    add(box);
    box.setVisible(false);
    
    // update the box
    updateBox();
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    goal.addTutorialListener(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    goal.removeTutorialListener(this);
    
    super.removeNotify();
  }
  
  /**
   * Called when the tutorial changed.
   */
  public void handleTutorial() {
    updateBox();
  }
  
  /**
   * Updates the tutorial box.
   */
  private void updateBox() {
    Item item = goal.getItem();
    if (item != null) {
      box.setText(item.getText());
      box.setShowNext(item.getTrigger() == null);
      box.setVisible(true);
    }
    else {
      box.setVisible(false);
    }
    
    // let's update the display...
    repaint();
  }
}
