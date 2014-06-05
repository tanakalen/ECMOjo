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

import javax.swing.JLabel;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.OxygenatorAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.KnobButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.gui.KnobButton.RotationListener;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class OxyDetailPanel extends DetailPanel implements Runnable {

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/Detail-Oxygenator.png");
  
  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedMedium.png");
  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackMedium.png");

  /** The top bar image. */
  private Image topBarImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Bar-Top.png");
  /** The middle bar image. */
  private Image middleBarImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Bar-Middle.png");
  /** The bottom bar image. */
  private Image bottomBarImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Bar-Bottom.png");
  
  /** The increase Co2 button. */
  private ImageButton incTotalSweepIntegerButton;
  /** The decrease Co2 button. */
  private ImageButton decTotalSweepIntegerButton;
  /** The increase O2 button. */
  private ImageButton incTotalSweepDecimalButton; 
  /** The decrease O2 button. */
  private ImageButton decTotalSweepDecimalButton;
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The component. */
  private OxygenatorComponent component;
  
  /** The updater thread. */
  private Thread thread;
  
  /** The total sweep tenth formatter. */
  private DecimalFormat totalSweepTenthFormatter = new DecimalFormat("#0.0");
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected OxyDetailPanel(final OxygenatorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);

    // load images
    Image incButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Up.png");
    Image incButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-UpRol.png");
    Image incButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-UpSel.png");
    Image decButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Down.png");    
    Image decButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-DownRol.png");
    Image decButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-DownSel.png");

    // add "+" button for total sweep integer
    incTotalSweepIntegerButton = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);
    incTotalSweepIntegerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        OxygenatorAction action = new OxygenatorAction();
        if ((component.getTotalSweepInteger() + 1) > 10) {
          action.setTotalSweepInteger(10);
        }
        else {
          action.setTotalSweepInteger(component.getTotalSweepInteger() + 1);
        }
        action.setTotalSweepDecimal(component.getTotalSweepDecimal());
        action.setFiO2(component.getFiO2());
        notifyActionListeners(action);
      }
    });
    incTotalSweepIntegerButton.setLocation(175, 187);
    incTotalSweepIntegerButton.setSize(32, 32);
    add(incTotalSweepIntegerButton); 

    // add "-" button for total sweep integer
    decTotalSweepIntegerButton = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);    
    decTotalSweepIntegerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        OxygenatorAction action = new OxygenatorAction();
        if ((component.getTotalSweepInteger() - 1) < 0) {
          action.setTotalSweepInteger(0);
        }
        else {
          action.setTotalSweepInteger(component.getTotalSweepInteger() - 1);
        }
        action.setTotalSweepDecimal(component.getTotalSweepDecimal());
        action.setFiO2(component.getFiO2());
        notifyActionListeners(action);
      }
    });
    decTotalSweepIntegerButton.setLocation(175, 224);
    decTotalSweepIntegerButton.setSize(32, 32);
    add(decTotalSweepIntegerButton);  
    
    // add "+" button for total sweep decimal
    incTotalSweepDecimalButton = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);  
    incTotalSweepDecimalButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        OxygenatorAction action = new OxygenatorAction();
        if ((component.getTotalSweepDecimal() + 1) > 9) {
          action.setTotalSweepDecimal(9);
        }
        else {
          action.setTotalSweepDecimal(component.getTotalSweepDecimal() + 1);
        }
        action.setTotalSweepInteger(component.getTotalSweepInteger());
        action.setFiO2(component.getFiO2());
        notifyActionListeners(action);
      }
    });
    incTotalSweepDecimalButton.setLocation(216, 187);
    incTotalSweepDecimalButton.setSize(32, 32);
    add(incTotalSweepDecimalButton); 

    // add "-" button for total sweep decimal
    decTotalSweepDecimalButton = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);    
    decTotalSweepDecimalButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        OxygenatorAction action = new OxygenatorAction();
        if ((component.getTotalSweepDecimal() - 1) < 0) {
          action.setTotalSweepDecimal(0);
        }
        else {
          action.setTotalSweepDecimal(component.getTotalSweepDecimal() - 1);
        }
        action.setTotalSweepInteger(component.getTotalSweepInteger());
        action.setFiO2(component.getFiO2());
        notifyActionListeners(action);
      }
    });
    decTotalSweepDecimalButton.setLocation(216, 224);
    decTotalSweepDecimalButton.setSize(32, 32);
    add(decTotalSweepDecimalButton);
    
    // add knob button
    Image knobImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-KnobBase.png");
    Image dialImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-KnobDial.png");
    Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-KnobRollover.png");
    KnobButton knobButton = new KnobButton(knobImage, dialImage, rolloverImage, 1, 18);
    knobButton.addRotationListener(new RotationListener() {
      public void handleRotation(double angle, boolean valueAdjusting) {
        double value = (angle - (Math.PI / 4)) / (Math.PI * 6 / 4);
        OxygenatorAction action = new OxygenatorAction();
        action.setTotalSweepInteger(component.getTotalSweepInteger());
        action.setTotalSweepDecimal(component.getTotalSweepDecimal());
        action.setFiO2(value);
        repaint();
        notifyActionListeners(action);
      }    
    });
    knobButton.setAngle((component.getFiO2() * (Math.PI * 6 / 4)) + (Math.PI / 4));
    knobButton.setMinAngle(Math.PI * 1 / 4);
    knobButton.setMaxAngle(Math.PI * 7 / 4);
    knobButton.setLocation(73, 162);
    knobButton.setSize(96, 96);
    add(knobButton);
    
    // add label for total sweep
    TextLabel lblToSweep = createTextLabels(
        Translator.getString("label.ToSweep[i18n]: Total Sweep"),
        8f, 182, 217);
    add(lblToSweep);
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
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
    label.setDrawShadow(true);
    label.setDrawBorder(false);
    label.setLocation(x, y);
    label.setSize(60, 25);
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
   
    // draw base
    g2.drawImage(detailImage, 0, 0, this);
    
    // total sweep integer bar
    g.drawImage(bottomBarImage, 188, 182, this);
    int totalSweepIntegerBarTopHeight = (int) component.getTotalSweep() * 15;
    for (int i = 0; i < (totalSweepIntegerBarTopHeight - 2); i++) {
      g.drawImage(middleBarImage, 188, 181 - i, this);
    }
    g.drawImage(topBarImage, 188, 180 - totalSweepIntegerBarTopHeight, this);
    
    // total sweep decimal bar
    g.drawImage(bottomBarImage, 228, 182, this);
    int totalSweepDecimalBarTopHeight = ((int) (component.getTotalSweep() * 10.0)
                                      - (int) component.getTotalSweep() * 10) * 15;
    for (int i = 0; i < (totalSweepDecimalBarTopHeight - 2); i++) {
      g.drawImage(middleBarImage, 228, 181 - i, this);    
    }
    g.drawImage(topBarImage, 228, 180 - totalSweepDecimalBarTopHeight, this);
    
    // draw blinking red light if there is an alarm
    if (component.isAlarm()){
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 136, 31, this);
      }
      else {
        g.drawImage(blackAlertImage, 136, 31, this);
      }
    }
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 40f));
    
    // draw text
    double value = component.getFiO2();
    String text = Math.round(value * 100) + "%";
    g.drawString(text, 54, 130);
    
    g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
    value = component.getTotalSweep();
    text = Translator.getString("text.Sweep[i18n]: Sweep") + ": "
      + totalSweepTenthFormatter.format(value);
    g.drawString(text, 54, 150);
  }
  
}
