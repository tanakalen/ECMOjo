package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent.PumpType;

/**
 * The component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class PumpComponentPanel extends ComponentPanel implements Runnable {

  /** The panel image. */
  private Image bladderImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-RollerBladder.png");

  /** The panel image. */
  private Image pumpImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-RollerPump.png");

  /** The start time in nano second. */
  private long lastUpdate;

  /** The theta rotate angle. */
  private double angle = 0.0;
  
  /** The rollover image. */
  private Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-PumpRol.png");
  /** The selected image. */
  private Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-PumpSel.png");

  /** The detail image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-PumpRedAlert.png");

  /** The detail image. */
  private Image blackAlertImage
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-PumpBlackAlert.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  /** The flow formatter. */
  private final DecimalFormat flowFormatter = new DecimalFormat("0.000");
  
  /** The component. */
  protected PumpComponent component;
  
  /** The selection button. */
  private AbstractButton selectionButton;

  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected PumpComponentPanel(PumpComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(430, 495);
    setSize(365, 95);
    
    // set layout
    setLayout(null);
    
    // variable for rotation
    lastUpdate = System.nanoTime();
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(224, 0);
    selectionButton.setSize(103, 102);
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
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 14f));
    
    // draw text
    if (component.isOn()) {
      String text = flowFormatter.format(component.getFlow());
      g.drawString(text, 246, 40);
    }
    else if (component.isAlarm()) {
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 224, 0, this);
      }
      else {
        g.drawImage(blackAlertImage, 224, 0, this);        
      }
    }
    else {
      g.drawImage(redAlertImage, 224, 0, this);
    }    
    
    // roller pump
    if (component.getPumpType() == PumpType.ROLLER) {
      // draws the image
      g2.drawImage(bladderImage, 11, 18, this);

      // draws the image
      long currentUpdate = System.nanoTime();
      long delta = currentUpdate - lastUpdate;
      lastUpdate = currentUpdate;
  
      if (component.isOn()) {    
        double deltaAngle = delta * 0.0000003 * component.getFlow();
        angle += deltaAngle;
        if (angle > 360.0) {
          angle -= 360.0;
        }
      }
      g2.rotate(-angle * Math.PI / 180.0, 162, 41);
      g2.drawImage(pumpImage, 130, 9, this);
      g2.rotate(angle * Math.PI / 180.0, 162, 41);    
    }
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
