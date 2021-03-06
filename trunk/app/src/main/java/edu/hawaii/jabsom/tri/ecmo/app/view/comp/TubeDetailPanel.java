package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

import king.lib.access.ImageLoader;
import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.CircuitChangeAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
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
/**
 * @author tanaka
 *
 */
public class TubeDetailPanel extends DetailPanel implements Runnable {

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage(
       "conf/image/interface/game/Detail-Tube.png");
  
  /** The info label. */
  private JLabel infoLabel;
  
  /** The normal bullet. */
  private Image bul = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCircuitBullet.png");
  /** The rollover bullet. */
  private Image bulRol = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCircuitBulletRol.png");
  /** The selected bullet. */
  private Image bulSel = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCircuitBulletSel.png");
  
  /** The thread. */
  private Thread thread;
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The component.
   * @param oxygenator The oxygenator obtain status.
   */
  protected TubeDetailPanel(final TubeComponent component, final OxygenatorComponent oxygenator) {
    super(component);
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
    
    // Instruction label [i18n]
    JLabel instructionLabel = new JLabel();
    instructionLabel.setText(Translator.getString("label.TubeDetail[i18n]: "
        + "<html>Click on each of the<br>bullets to verify the<br>circuit."));
    instructionLabel.setFont(instructionLabel.getFont().deriveFont(Font.BOLD, 12f));
    instructionLabel.setForeground(Color.BLACK);
    instructionLabel.setLocation(30, 18);
    instructionLabel.setSize(160, 70);
    add(instructionLabel);
    
    Image changeCircuitNormalImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/BtnLong.png");
    Image changeCircuitRolloverImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/BtnLongRol.png");
    Image changeCircuitSelectedImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/BtnLongSel.png");
    ImageButton changeCircuitButton 
      = new ImageButton(changeCircuitNormalImage, changeCircuitRolloverImage, changeCircuitSelectedImage);
    changeCircuitButton.setText(
        Translator.getString("button.ChangeCircuit[i18n]: Change Circuit"));
    changeCircuitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // send the action
        CircuitChangeAction action = new CircuitChangeAction();
        notifyActionListeners(action);
            
        // output dialog
        StandardDialog.showDialog(TubeDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , Translator.getString("title.CircuitChangeDialog[i18n]: Circuit Changed")
            , Translator.getString("text.CircuitChangeDialog[i18n]: "
                + "The circuit has been changed. Please increase flow."));
      }
    });
    changeCircuitButton.setLocation(18, 222);
    changeCircuitButton.setSize(192, 32);
    add(changeCircuitButton);
    
    // add flash text
    infoLabel = new JLabel();
    infoLabel.setText(Translator.getString("label.LocOK[i18n]: Location OK!"));
    infoLabel.setFont(infoLabel.getFont().deriveFont(Font.BOLD, 14f));
    infoLabel.setForeground(new Color(0x00000000, true));
    infoLabel.setLocation(30, 90);
    infoLabel.setSize(140, 20);
    add(infoLabel);
    
    // add the bullets (top left -> top right -> bottom right -> bottom left)
    ImageButton bullet;
    ActionListener defaultBulletListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // output splash - nothing found
        infoLabel.setForeground(Color.RED);
        infoLabel.setText(Translator.getString("label.LocOK[i18n]: Location OK!"));
        // something wrong with circuit
        if (component.isCoagulopathy()) {
          infoLabel.setText(Translator.getString("label.Fibrin[i18n]: Fibrin!"));
        }
      }
    };
    ActionListener oxygenatorBulletListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // output splash - nothing found
        infoLabel.setForeground(Color.RED);
        infoLabel.setText(Translator.getString("label.LocOK[i18n]: Location OK!"));
        // oxygenator having issues
        if (oxygenator.getClotting() > 1) {
          infoLabel.setText(Translator.getString("label.Clots[i18n]: Clots!"));
        }
      }
    };
    
    bullet = addBullet(63, 149); // art bridge connector
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(100, 149); // art tube
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(144, 144); // bubble detector
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(185, 109); // intervention pt D 
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(215, 79); // oxygenator 
    bullet.addActionListener(oxygenatorBulletListener);
    bullet = addBullet(235, 59); // oxygenator
    bullet.addActionListener(oxygenatorBulletListener);
    bullet = addBullet(262, 50); // oxygenator
    bullet.addActionListener(oxygenatorBulletListener);
    bullet = addBullet(250, 79); // oxygenator
    bullet.addActionListener(oxygenatorBulletListener);
    bullet = addBullet(223, 108); // oxygenator
    bullet.addActionListener(oxygenatorBulletListener);
    bullet = addBullet(203, 149); // intervention pt C
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(222, 171); // pump
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(248, 197); // pump
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(215, 208); // pump
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(183, 208); // intervention pt B
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(141, 208); // venous tube
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(100, 208); // venous tube
    bullet.addActionListener(defaultBulletListener);
    bullet = addBullet(63, 208); //ven bridge connector
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
   * Rotates text for display.
   * 
   * @param g  The graphics environment.
   * @param x  The x position.
   * @param y  The y position.
   * @param theta  The angle of rotation in radians (pi/2 = 180 deg).
   * @param label  The text for the label.
   */
  private void rotateLabel(Graphics g, double x, double y, double theta, String label) {
    Graphics2D g2D = (Graphics2D)g;
    AffineTransform original = g2D.getTransform();
    FontRenderContext frc = g2D.getFontRenderContext();
    GlyphVector gv = g2D.getFont().createGlyphVector(frc, label);
    Rectangle bounds = gv.getPixelBounds(frc, (float)x, (float)y);
    AffineTransform at = new AffineTransform();
    at.rotate(theta, x+(bounds.width/2), y+(bounds.height/2));
    g2D.setTransform(at);
    // Render the rotated string, fragile custom hack
    if (Configuration.getInstance().getLang().equals("ja")) {
      g2D.drawString(label, (int)(x+15), (int)(y+15));
    }
    else {
      g2D.drawString(label, (int)x, (int)y);
    }
    g2D.setTransform(original);
}

  /**
   * Called when this panel is added.
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
   * Called when this panel is removed.
   */
  @Override
  public void removeNotify() {
    // stop the thread
    this.thread = null;
    
    super.removeNotify();
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }
  
  /**
   * The updater thread for graphics.
   */
  public void run() {
    Thread currentThread = Thread.currentThread();
    while(this.thread == currentThread) {
      // update flash label
      int alpha = infoLabel.getForeground().getAlpha();
      if (alpha > 0) {
        alpha -= 16;
        if (alpha < 0) {
          alpha = 0;
        }
        int rgb = (infoLabel.getForeground().getRGB() & 0x00ffffff) | (alpha << 24);
        infoLabel.setForeground(new Color(rgb, true));
      }
      repaint();
      
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        // ignore!
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
    
    // draw oxygenator [i18n] label
    g2.setFont(new Font("default", Font.BOLD, 12));
    rotateLabel(g, 203, 82, -(Math.PI/4), 
        Translator.getString("label.Oxygenator[i18n]: oxygenator"));
  }
}
