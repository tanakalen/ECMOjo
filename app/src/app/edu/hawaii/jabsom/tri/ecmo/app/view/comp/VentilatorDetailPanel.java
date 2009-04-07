package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import king.lib.access.ImageLoader;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class VentilatorDetailPanel extends DetailPanel {
  
  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Lab.png");

  
  /**
   * Constructor for panel.
   *
   * @param component  The associated component.
   */
  protected VentilatorDetailPanel(final VentilatorComponent component) {
    super(component);
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);

    // set layout and look
    setLayout(null);
    setOpaque(false);
    
    // add title
    JLabel titleLabel = new JLabel("Ventilator Settings");
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
    titleLabel.setLocation(28, 34);
    titleLabel.setSize(150, 20);
    add(titleLabel);

    // show text
    JTextArea infoLabel = new JTextArea();
    infoLabel.setText("You are advised not to change the Ventilator Settings.");
    infoLabel.setOpaque(false);
    infoLabel.setFont(infoLabel.getFont().deriveFont(Font.PLAIN, 14f));
    infoLabel.setEditable(false);
    infoLabel.setWrapStyleWord(true);
    infoLabel.setLineWrap(true);
    infoLabel.setLocation(28, 59);
    infoLabel.setSize(215, 185);
    add(infoLabel);
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    // not used
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
