package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.report.ResultReporter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;

/**
 * The result state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 19, 2008
 */
public class ResultStatePanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/result/Base.jpg");
    
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public ResultStatePanel(final ResultState state) {
    Game game = state.getGame();
    
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel("ECMO Results");
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(100, 16);
    textLabel.setSize(600, 40);
    add(textLabel);
    
    // add title label
    JLabel titleLabel = new JLabel(game.getName());
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
    titleLabel.setLocation(30, 120);
    titleLabel.setSize(720, 30);
    add(titleLabel);
    
    // add result overview
    JTextArea infoArea = new JTextArea(ResultReporter.getOverview(game));
    infoArea.setOpaque(false);
    infoArea.setEditable(false);
    infoArea.setLineWrap(true);
    infoArea.setWrapStyleWord(true);
    infoArea.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 14f));
    infoArea.setLocation(30, 160);
    infoArea.setSize(720, 360);
    add(infoArea);
    
    // output overall result
    boolean success = game.getGoal().isWon(game);
    Image result = success ? ImageLoader.getInstance().getImage("conf/image/interface/result/success.png") 
                           : ImageLoader.getInstance().getImage("conf/image/interface/result/failure.png");
    JLabel resultLabel = new JLabel();
    resultLabel.setVerticalAlignment(JLabel.CENTER);
    resultLabel.setLocation(600, 100);
    resultLabel.setSize(200, 400);
    resultLabel.setIcon(new ImageIcon(result));
    add(resultLabel);
    
    // add done button
    Image okButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-Ok.png");    
    Image okButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-OkRol.png");
    Image okButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-OkSel.png");
    ImageButton okButton = new ImageButton(okButtonImage, okButtonRolloverImage, okButtonSelectedImage);
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // proceed to the next state
        state.menuState();
      }
    });
    okButton.setSize(75, 30);
    okButton.setLocation(665, 530);
    add(okButton);
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draws the image as background
    g.drawImage(background, 0, 0, this);
  }
}
