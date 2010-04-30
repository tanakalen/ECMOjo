package edu.hawaii.jabsom.tri.ecmo.app.view.dialog;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * The panel dialog using a JPanel to display the dialog inside a JFrame.
 * 
 * @author noblemaster
 * @since April 21, 2010
 */
public abstract class PanelDialog {

  /** The dialog layer. */
  private static final Integer DIALOG_LAYER = new Integer(250);
  
  /** The owner. */
  private JFrame owner;
  /** True for modal. */
  private boolean modal;
  
  /** True to draw the stripe. */
  private boolean stripe = true;
  /** The stripe color. */
  private Color stripeColor = new Color(0x30ffffff, true);
  /** The stripe stroke. */
  private BasicStroke stripeStroke = new BasicStroke(30);
  
  /** The dialog. */
  private JPanel dialogPanel;
  
  /** The mouse listener if any. */
  private MouseListener mouseListener;
  /** The component listener. */
  private ComponentListener componentListener;
  
  
  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param modal  True for modal.
   */
  protected PanelDialog(Component parent, boolean modal) {
    this(parent, modal, null);
  }

  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param modal  True for modal.
   * @param panel  The panel.
   */
  protected PanelDialog(Component parent, boolean modal, Component panel) {
    this.modal = modal;
    
    // find the JFrame
    Window window = parent != null ? SwingUtilities.windowForComponent(parent) : null;
    if ((window != null) && (window instanceof JFrame)) {
      owner = (JFrame)window;
    }
    else {
      // TODO(9): add support even if the top window is not a JFrame
      throw new IllegalArgumentException("Window for component is not a JFrame: " + parent);
    }
    
    // default look
    dialogPanel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        // draw dark overlay if modal
        if (PanelDialog.this.modal) {
          // draw background
          g2.setColor(getBackground());
          g2.fillRect(0, 0, dialogPanel.getWidth(), dialogPanel.getHeight());
          
          // draw stroke
          if (stripe) {
            Stroke stroke = g2.getStroke();
            g2.setStroke(stripeStroke);
            g2.setColor(stripeColor);
            int distance = getWidth() > getHeight() ? getWidth() : getHeight();
            int maxX = getWidth() + getHeight();
            int xInc = (int)(stripeStroke.getLineWidth() * 3);
            for (int x = 0; x < maxX; x += xInc) {
              g2.drawLine(x, 0, x - distance, distance);
            }
            g2.setStroke(stroke);
          }
        }
      }
    };
    dialogPanel.setOpaque(false);
    dialogPanel.setBackground(new Color(0x40000000, true));
    dialogPanel.setLayout(null);
    
    // and set the panel
    setPanel(panel);
  }
  
  /**
   * Returns the panel.
   *
   * @return  The panel or null for none.
   */
  public Component getPanel() {
    if (dialogPanel.getComponentCount() > 0) {
      return dialogPanel.getComponent(0);
    }
    else {
      return null;
    }
  }

  /**
   * Sets the panel. Make sure getSize() does return a real value. The component is centered
   * based on the getSize() return value. If getSize() is (0, 0), the getPreferredSize() is used instead.
   *
   * @param panel  The panel or null for none. 
   */
  public void setPanel(Component panel) {
    dialogPanel.removeAll();
    if (panel != null) {
      dialogPanel.add(panel);

      // center
      center();
    }
  }
  
  /**
   * Returns the background.
   * 
   * @return  The background.
   */
  public Color getBackground() {
    return dialogPanel.getBackground();
  }
  
  /**
   * Sets the background.
   * 
   * @param background  The background.
   */
  public void setBackground(Color background) {
    dialogPanel.setBackground(background);
  }
  
  /**
   * Returns true to draw the stripe.
   *
   * @return  True to draw.
   */
  public boolean isStripe() {
    return stripe;
  }

  /**
   * Set true to draw the stripe.
   *
   * @param stripe  True to draw.
   */
  public void setStripe(boolean stripe) {
    this.stripe = stripe;
  }

  /**
   * Returns the stripe color.
   *
   * @return  The stripe color.
   */
  public Color getStripeColor() {
    return stripeColor;
  }

  /**
   * Sets the stripe color.
   *
   * @param stripeColor  The stripe color.
   */
  public void setStripeColor(Color stripeColor) {
    this.stripeColor = stripeColor;
  }

  /**
   * Returns the stripe width.
   *
   * @return  The stripe width.
   */
  public int getStripeWidth() {
    return (int)stripeStroke.getLineWidth();
  }

  /**
   * Sets the stripe width.
   *
   * @param width  The stripe width.
   */
  public void setStripeWidth(int width) {
    this.stripeStroke = new BasicStroke(width);
  }

  /**
   * Shows or hides this dialog.
   * 
   * @param visible  True to show the dialog.
   */
  public void setVisible(boolean visible) {
    if (visible) {
      final JLayeredPane layeredPane = owner.getRootPane().getLayeredPane();
  
      // center
      center();

      // intercept mouse clicks if modal
      if (modal) {
        // intercepts and blocks mouse clicks :)
        mouseListener = new MouseAdapter() { };
        dialogPanel.addMouseListener(mouseListener);
      }

      // add this dialog to the dialog layer
      layeredPane.add(dialogPanel, DIALOG_LAYER, 0);  // add on top
      
      // update size (keep up-to-date with component listener)
      componentListener = new ComponentAdapter() {
        public void componentResized(ComponentEvent event) {
          dialogPanel.setSize(layeredPane.getSize());
        }
      };
      layeredPane.addComponentListener(componentListener);
      dialogPanel.setSize(layeredPane.getSize());
      
      // and update
      layeredPane.revalidate();
      layeredPane.repaint();
    }
    else {
      JLayeredPane layeredPane = owner.getRootPane().getLayeredPane();

      // remove component listener
      layeredPane.removeComponentListener(componentListener);
      componentListener = null;
      
      // we remove ourself
      layeredPane.remove(dialogPanel);
      
      // we remove mouse listener if any
      if (mouseListener != null) {
        dialogPanel.removeMouseListener(mouseListener);
        mouseListener = null;
      }
      
      // and update
      layeredPane.revalidate();
      layeredPane.repaint();
    }
  }  

  /** 
   * Center the panel.
   */
  public void center() {
    Component panel = getPanel();
    if (panel != null) {
      JLayeredPane layeredPane = owner.getRootPane().getLayeredPane();
      int width = layeredPane.getWidth();
      int height = layeredPane.getHeight();
  
      int w = panel.getWidth();
      int h = panel.getHeight();
      if ((w == 0) || (h == 0)) {
        w = panel.getPreferredSize().width;
        h = panel.getPreferredSize().height;
        panel.setSize(w, h);
      }
      panel.setLocation((width - w) / 2, (height - h) / 2);
    }
  }
}
