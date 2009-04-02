package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class HeaterComponentPanel extends ComponentPanel implements Runnable {

  /** The rollover image. */
  private Image rolloverImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-HeaterRol.png");
  /** The selected image. */
  private Image selectedImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-HeaterSel.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedSmall.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackSmall.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The component. */
  private HeaterComponent component;

  /** The selection button. */
  private AbstractButton selectionButton;  
  
  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected HeaterComponentPanel(HeaterComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(667, 249);
    setSize(96, 78);
    
    // set layout
    setLayout(null);

    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(96, 78);
    add(selectionButton);
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }

  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // start thread
    this.thread = new Thread(this);
    this.thread.setPriority(Thread.MIN_PRIORITY);
    this.thread.start();
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // stop thread
    this.thread = null;
    
    super.removeNotify();
  }
  
  /**
   * The time updater thread.
   */
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // update
      repaint();
      
      // wait for next update
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }
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

    if (component.isAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 10, 11, this);
      }
      else {
        g.drawImage(blackAlertImage, 10, 11, this);        
      }      
    } 
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
    
    // draw text
    String text = String.valueOf((int)component.getTemperature()) + "\u00B0C";
    g.drawString(text, 34, 38);
  }
  
  /**
   * Assigns the component to a group.
   * 
   * @param group  The group.
   */
  public void assign(ButtonGroup group) {
    group.add(selectionButton);
  }
}
