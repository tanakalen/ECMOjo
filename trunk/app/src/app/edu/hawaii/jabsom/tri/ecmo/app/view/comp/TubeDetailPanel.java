package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.CircuitChangeAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

/**
 * The detail panel. 
 *
 * @author Christoph Aschwanden
 * @since October 28, 2009
 */
public class TubeDetailPanel extends DetailPanel {

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Tube.png");
  
  /** The normal bullet. */
  private Image bul = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCircuitBullet.png");
  /** The rollover bullet. */
  private Image bulRol = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCircuitBulletRol.png");
  /** The selected bullet. */
  private Image bulSel = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCircuitBulletSel.png");
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The component.
   */
  protected TubeDetailPanel(final TubeComponent component) {
    super(component);
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);

    Image changeCircuitNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ChangeCircuit.png");
    Image changeCircuitRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ChangeCircuitRol.png");
    Image changeCircuitSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ChangeCircuitSel.png");
    ImageButton changeCircuitButton 
      = new ImageButton(changeCircuitNormalImage, changeCircuitRolloverImage, changeCircuitSelectedImage);
    changeCircuitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // send the action
        CircuitChangeAction action = new CircuitChangeAction();
        notifyActionListeners(action);
            
        // output dialog
        StandardDialog.showDialog(TubeDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , "Circuit Changed"
            , "The circuit has been changed.");
      }
    });
    changeCircuitButton.setLocation(18, 222);
    changeCircuitButton.setSize(192, 32);
    add(changeCircuitButton);
    
    // add the bullets
    ImageButton bullet;
    ActionListener defaultBulletListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // output splash - nothing found
        
      }
    };
    
    bullet = addBullet(33, 149);    
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(63, 149);    
    bullet.addActionListener(defaultBulletListener);

  }
  
  /**
   * Adds a new bullet button.
   * 
   * @param x  The x position (center).
   * @param y  The y position (center).
   * @return  The button added.
   */
  private ImageButton addBullet(int x, int y) {
    ImageButton bulletButton = new ImageButton(bul, bulRol, bulSel);
    bulletButton.setLocation(x - 12, y - 12);
    bulletButton.setSize(24, 24);
    add(bulletButton);
    return bulletButton;
  }
  
  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draw base
    g2.drawImage(detailImage, 0, 0, this);
  }
}
