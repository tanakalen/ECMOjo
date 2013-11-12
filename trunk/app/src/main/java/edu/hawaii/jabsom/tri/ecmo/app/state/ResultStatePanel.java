package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.gui.VerticalLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;
import edu.hawaii.jabsom.tri.ecmo.app.report.ResultReporter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.out.InternetTextPane;
import king.lib.util.Translator;

/**
 * The result state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 19, 2008
 */
public class ResultStatePanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/Background.png");
    
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public ResultStatePanel(final ResultState state) {
    Game game = state.getGame();
    boolean success = game.getGoal().isWon(game);

    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel(Translator.getString("title.ECMOResults[i18n]: ECMO Results"));
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
    InternetTextPane infoArea = new InternetTextPane();
    infoArea.setDefaultFont(InternetTextPane.FONT_TYPE_SANS_SERIF);
    String html = ResultReporter.getHTMLOverview(game);
    if (game.getGoal() instanceof BaselineGoal) {
      BaselineGoal goal = (BaselineGoal)game.getGoal();
      if (success) {
        html = goal.getInfoSuccess().replace("\\n", "<br>") + "<br><br><br>" + html;
      }
      else {
        html = goal.getInfoFailure().replace("\\n", "<br>") + "<br><br><br>" + html;
      }
    }
    infoArea.addHTML(html);
    infoArea.setOpaque(false);
    infoArea.setEditable(false);
    infoArea.setFont(titleLabel.getFont().deriveFont(Font.PLAIN, 14f));
    infoArea.setLocation(30, 160);
    infoArea.setSize(580, 360);
    add(infoArea);
    
    // output overall result
    String result = success ? Translator.getString("label.Success[i18n]: Success")
                            : Translator.getString("label.Failure[i18n]: Failure");
    //TODO: i18n vertical versus horizontal orientation
    //if lang.locale isEastern: JLabel resultLabel = new TextLabel()
    //else: // lang.local isWestern
    JLabel resultLabel = new VerticalLabel();
    resultLabel.setText(result);
    resultLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 72f));
    resultLabel.setForeground(Color.WHITE);
    resultLabel.setHorizontalAlignment(JLabel.CENTER);
    resultLabel.setLocation(600, 100);
    resultLabel.setSize(200, 400);
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
    okButton.setLocation(30, 530);
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
