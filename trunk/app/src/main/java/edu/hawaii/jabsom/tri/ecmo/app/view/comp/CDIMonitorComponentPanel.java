package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.gui.VerticalLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.CDIMonitorComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class CDIMonitorComponentPanel extends ComponentPanel {

  /** The monitor formatter. */
  private final DecimalFormat pHFormatter = new DecimalFormat("0.00");
  
  /** The monitor formatter. */
  private final DecimalFormat monitorFormatter = new DecimalFormat("0.0");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The Strings for CDI monitor label values: 10 slots (5 left, 5 right). */
  private String[] labels;
  
  /** X-coordinate for left column. */
  private static final int LEFT_COLUMN_X = 22;
  
  /** X-coordinate for right column. */
  private static final int RIGHT_COLUMN_X = 148;
  
  /** Y-coordinate for row 1. */
  private static final int ROW1_Y = 20;
  
  /** Y-coordinate for row 2. */
  private static final int ROW2_Y = 45;
  
  /** Y-coordinate for row 3. */
  private static final int ROW3_Y = 68;
  
  /** Y-coordinate for row 4. */
  private static final int ROW4_Y = 92;
  
  /** Y-coordinate for row 5. */
  private static final int ROW5_Y = 116;
  
  /** Array of x-coordinates for labels. */
  private int[] lblX = {
      LEFT_COLUMN_X,
      LEFT_COLUMN_X,
      LEFT_COLUMN_X,
      LEFT_COLUMN_X,
      LEFT_COLUMN_X,
      RIGHT_COLUMN_X,
      RIGHT_COLUMN_X,
      RIGHT_COLUMN_X,
      RIGHT_COLUMN_X,
      RIGHT_COLUMN_X
  };
  
  /** Array of y-coordinates for labels. */
  private int[] lblY = {
      ROW1_Y,
      ROW2_Y,
      ROW3_Y,
      ROW4_Y,
      ROW5_Y,
      ROW1_Y,
      ROW2_Y,
      ROW3_Y,
      ROW4_Y,
      ROW5_Y
  };
  
  /** The component. */
  private CDIMonitorComponent component;
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected CDIMonitorComponentPanel(CDIMonitorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(0, 0);
    setSize(288, 152);
    
    // set layout
    setLayout(null);
    
    // add label "CDI Monitor"
    String result = Translator.getString("label.CDIMonitor[i18n]: CDI Monitor");
    // Render the rotated string, fragile custom hack
    if (Configuration.getInstance().getLang().equals("ja")) {
      VerticalLabel resultLabel = new VerticalLabel(result);
      resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 12f));
      resultLabel.setForeground(Color.WHITE);
      resultLabel.setHorizontalAlignment(JLabel.LEFT);
      resultLabel.setLocation(274, 0);
      resultLabel.setSize(14, 152);
      add(resultLabel);
    }
    else {
      VerticalLabel resultLabel = new VerticalLabel(result, false);
      resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 12f));
      resultLabel.setForeground(Color.WHITE);
      resultLabel.setHorizontalAlignment(JLabel.CENTER);
      resultLabel.setLocation(273, 0);
      resultLabel.setSize(15, 152);
      add(resultLabel);
    }
    
    // add monitor labels
    labels = new String[] {
        "label.CDIpH[i18n]: pH",
        "label.CDIpCO2[i18n]: pCO2",
        "label.CDIpO2[i18n]: pO2",
        "label.CDIHCO3[i18n]: HCO3",
        "label.MonitorTemp[i18n]: Temp",
        "label.CDIHct[i18n]: HCT",
        "label.CDIHgb[i18n]: Hgb",
        "label.CDIsvO2[i18n]: svO2",
        "label.CDIBE[i18n]: BE",
        "label.CDIsaO2[i18n]: saO2"
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
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    
    // draw text
    String text = pHFormatter.format(component.getPH());
    g.drawString(text, 88, 34);
    text = String.valueOf(Math.round(component.getPCO2()));
    g.drawString(text, 88, 60);
    text = String.valueOf(Math.round(component.getPO2()));
    g.drawString(text, 88, 84);
    text = String.valueOf(Math.round(component.getHCO3()));
    g.drawString(text, 88, 108);
    text = monitorFormatter.format(component.getTemperature());
    g.drawString(text, 88, 131);
    
    text = monitorFormatter.format(component.getHct());
    g.drawString(text, 212, 34);
    text = monitorFormatter.format(component.getHgb());
    g.drawString(text, 212, 60);
    text = String.valueOf(Math.round(component.getSvO2() * 100)) + "%";
    g.drawString(text, 212, 84);
    text = monitorFormatter.format(component.getBE());
    g.drawString(text, 212, 108);
    text = String.valueOf(Math.round(component.getSaO2() * 100)) + "%";
    g.drawString(text, 212, 131);
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
