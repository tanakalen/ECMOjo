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
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.gui.VerticalLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PhysiologicMonitorComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class PhysiologicMonitorComponentPanel extends ComponentPanel implements Runnable {

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedSmall.png");
  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackSmall.png");

  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The Strings for monitor label values: 6 slots (3 left, 3 right). */
  private String[] labels;
  
  /** X-coordinate for left column. */
  private static final int LEFT_COLUMN_X = 23;
  
  /** X-coordinate for right column. */
  private static final int RIGHT_COLUMN_X = 148;
  
  /** Y-coordinate for top row. */
  private static final int TOP_ROW_Y = 20;
  
  /** Y-coordinate for middle row. */
  private static final int MIDDLE_ROW_Y = 45;
  
  /** Y-coordinate for bottom row. */
  private static final int BOTTOM_ROW_Y = 68;
  
  /** Array of x-coordinates for labels. */
  private int[] lblX = {
      LEFT_COLUMN_X,
      LEFT_COLUMN_X,
      LEFT_COLUMN_X,
      RIGHT_COLUMN_X,
      RIGHT_COLUMN_X,
      RIGHT_COLUMN_X
  };
  
  /** Array of y-coordinates for labels. */
  private int[] lblY = {
      TOP_ROW_Y,
      MIDDLE_ROW_Y,
      BOTTOM_ROW_Y,
      TOP_ROW_Y,
      MIDDLE_ROW_Y,
      BOTTOM_ROW_Y
  };
  
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
    // TODO: Render the rotated string, fragile custom hack
    if (Configuration.getInstance().getLang().equals("ja")) {
      VerticalLabel resultLabel = new VerticalLabel(result);
      resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 10f));
      resultLabel.setForeground(Color.WHITE);
      resultLabel.setHorizontalAlignment(JLabel.LEFT);
      resultLabel.setLocation(275, -7);
      resultLabel.setSize(13, 110);
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
    
    // add monitor labels
    labels = new String[] {
        "label.MonitorTemp[i18n]: Temp",
        "label.MonitorHR[i18n]: HR",
        "label.MonitorBP[i18n]: BP",
        "label.MonitorRR[i18n]: RR",
        "label.MonitorO2Sat[i18n]: O2 sat",
        "label.MonitorCVP[i18n]: CVP"
    };
    for (int i = 0; i < labels.length; i++) {
      TextLabel lblValue = createTextLabels(
          Translator.getString(labels[i]),
          14f, lblX[i], lblY[i]);
      lblValue.setGradientTopColor(new Color(204, 204, 204));
      lblValue.setGradientBottomColor(new Color(204, 204, 204));
      lblValue.setDrawBorder(true);
      add(lblValue);
    }
  }

    /**
     * Private method to simplify & consolidate rendering text labels.
     *
     * @param text  String for TextLabel.
     * @param fontSize  Size of font.
     * @param x  X-coordinate.
     * @param y  Y-coordinate.
     * @return TextLabel object.
     */
    private TextLabel createTextLabels(String text, float fontSize, int x, int y) {
      TextLabel label = new TextLabel(text);
      label.setHorizontalAlignment(JLabel.LEFT);
      label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
      label.setBorderColor(new Color(0.0f, 0.0f, 0.0f, 0.25f));
      label.setDrawBorder(false);
      label.setLocation(x, y);
      label.setSize(120, 45);
      return label;
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
      g.setFont(g.getFont().deriveFont(Font.PLAIN, 10f));
      text = Translator.getString("text.Mean[i18n]: Mean") + ": "
             + String.valueOf((int)component.getMeanBloodPressure());
      g.drawString(text, 88, 87);
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
