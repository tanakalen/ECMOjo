package edu.hawaii.jabsom.tri.ecmo.app.view.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import king.lib.access.ImageLoader;

/**
 * The default dialog. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public abstract class DefaultDialog extends AbstractDialog {
   
/**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param title  The dialog title.
   */
  public DefaultDialog(Component parent, String title) {
    super(JOptionPane.getFrameForComponent(parent), title);
   
    // set look
    setUndecorated(true);
    //setBackground(new Color(0, 0, 0, 0)); // lt_add: Mac OS X specific setting for transparency

    // set content pane
    final Image backgroundImage = ImageLoader.getInstance().getImage("conf/gui/dialog.png");
    JPanel contentPane = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        // set antialiased text
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // draw background
        g.drawImage(backgroundImage, 0, 0, null);
        
        // draw title
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 20f));
        g.drawString(getTitle(), 35, 27);
        
        super.repaint(); // lt_add: displays dialog for mac jdk 1.5
      }
    };
    setContentPane(contentPane);
  }
}
