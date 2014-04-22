package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.gui.VerticalLabel;
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
    
    setLayout(null);
    
    // add label "Phy Monitor"
    String result = Translator.getString("label.PhysMonitor[i18n]: Phy Monitor");
    // Render the rotated string, fragile custom hack
    if (Configuration.getInstance().getLang().equals("ja")) {
      VerticalLabel resultLabel = new VerticalLabel(result);
      resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 10f));
      resultLabel.setForeground(Color.WHITE);
      resultLabel.setHorizontalAlignment(JLabel.LEFT);
      resultLabel.setLocation(275, 0);
      resultLabel.setSize(13, 105);
      add(resultLabel);
    }
    else {
      VerticalLabel resultLabel = new VerticalLabel(result, false);
      resultLabel.setText(result);
      resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 12f));
      resultLabel.setForeground(Color.WHITE);
      resultLabel.setHorizontalAlignment(JLabel.CENTER);
      resultLabel.setLocation(273, 0);
      resultLabel.setSize(15, 105);
      add(resultLabel);
    }
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
    
    // true for blinking
    boolean blink = (((System.nanoTime()) / 500000000) % 2) == 0;
    
    // draw text
    if (blink || (!component.isTemperatureAlarm())) {
      String text = String.valueOf((int)component.getTemperature()) + "\u00B0C";
      g.drawString(text, 88, 34);
    }
    
    if (blink || (!component.isHeartRateAlarm())) {
      String text = String.valueOf((int)component.getHeartRate());
      g.drawString(text, 88, 60);
    }
    
    if (blink || ((!component.isDiastolicBloodPressureAlarm()) 
                  && (!component.isSystolicBloodPressureAlarm()))) {
      g.setFont(g.getFont().deriveFont(Font.BOLD, 12f));
      String text = String.valueOf((int)component.getSystolicBloodPressure()) + " / "
                  + String.valueOf((int)component.getDiastolicBloodPressure());
      g.drawString(text, 88, 77);
      g.setFont(g.getFont().deriveFont(Font.PLAIN, 11f));
      text = "Mean: " + String.valueOf((int)component.getMeanBloodPressure());
      g.drawString(text, 88, 86);
    }
  
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    if (blink || (!component.isRespiratoryRateAlarm())) {
      String text = String.valueOf((int)component.getRespiratoryRate());
      g.drawString(text, 212, 34);
    }
    
    if (blink || (!component.isO2SaturationAlarm())) {
      String text = String.valueOf((int)(component.getO2Saturation() * 100)) + "%";
      g.drawString(text, 212, 60);
    }
    
    if (blink || (!component.isCentralVenousPressureAlarm())) {
      String text = String.valueOf((int)(component.getCentralVenousPressure()));
      g.drawString(text, 212, 84);
    }
    
    // draw blinking red light if alerting
    if (component.isAlarm()) {
      if (blink) {
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
