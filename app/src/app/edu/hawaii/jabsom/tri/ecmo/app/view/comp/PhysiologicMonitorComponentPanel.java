package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PhysiologicMonitorComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class PhysiologicMonitorComponentPanel extends ComponentPanel implements Runnable {

  /** The panel image. */
  private Image image = ImageLoader.getInstance().getImage("conf/image/interface/game/Mtr-Physiologic.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedSmall.png");
  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackSmall.png");

  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The component. */
  private PhysiologicMonitorComponent component;
  
  /** The updater thread. */
  private Thread thread;

  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected PhysiologicMonitorComponentPanel(PhysiologicMonitorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(0, 152);
    setSize(288, 105);
    
    // set layout
    setLayout(null);
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

    // draws the image
    g.drawImage(image, 0, 0, this);
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    
    // true for alarm
    boolean alarm = component.isAlarm();
    
    // draw text
    String text = String.valueOf((int)component.getTemperature()) + "\u00B0C";
    g.drawString(text, 88, 34);
    text = String.valueOf((int)component.getHeartRate());
    g.drawString(text, 88, 60);

    g.setFont(g.getFont().deriveFont(Font.BOLD, 12f));
    text = String.valueOf((int)component.getSystolicBloodPressure()) + " / "
         + String.valueOf((int)component.getDiastolicBloodPressure());
    g.drawString(text, 88, 77);
    g.setFont(g.getFont().deriveFont(Font.PLAIN, 11f));
    text = "Mean: " + String.valueOf((int)component.getMeanBloodPressure());
    g.drawString(text, 88, 86);
   
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    text = String.valueOf((int)component.getRespiratoryRate());
    g.drawString(text, 212, 34);
    text = String.valueOf((int)(component.getO2Saturation() * 100)) + "%";
    g.drawString(text, 212, 60);
    text = String.valueOf((int)(component.getCentralVenousPressure()));
    g.drawString(text, 212, 84);
    
    // draw blinking red light if alerting
    if (alarm) {
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 1, 3, this);
      }
      else {
        g.drawImage(blackAlertImage, 1, 3, this);        
      }      
    } 
  }
  
  /**
   * Assigns the component to a group.
   * 
   * @param group  The group.
   */
  public void assign(ButtonGroup group) {
    // not needed for this component
  }
}
