package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import king.lib.access.ImageLoader;
import king.lib.util.Translator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;

/**
 * The tutorial box. 
 *
 * @author   Christoph Aschwanden
 * @since    Jan 12, 2009
 */
public class TutorialBox extends JPanel {

  /** The tutorial background image. */
  private Image image = ImageLoader.getInstance().getImage("conf/gui/tutorialbox.png");
  
  /** Listener for changes. */
  public static interface TutorialBoxListener {
    
    /**
     * Called when the "next" button was pressed.
     */
    void handleAction();
  };
  
  /** Listeners for changes. */
  private List<TutorialBoxListener> listeners = new ArrayList<TutorialBoxListener>();


  /** The text. */
  private JTextArea textArea;
  
  /** The button. */
  private ImageButton button;
  
  
  /**
   * Constructor for panel. 
   */
  public TutorialBox() {
    // set look
    setOpaque(false);
    
    // no layout
    setLayout(new FormLayout("5px, fill:368px, 75px, 4px"  // 452px col: sp, text_w, button_w, sp
                           , "3px, fill:51px, 30px"  // 84px row: sp, text_h, button_h
      ));
    CellConstraints cc = new CellConstraints();
    
    // add the "next" button
    Image nextNormalImage = ImageLoader.getInstance().getImage(
        "conf/gui/Btn.png");
    Image nextRolloverImage = ImageLoader.getInstance().getImage(
        "conf/gui/Btn.png");
    Image nextSelectedImage = ImageLoader.getInstance().getImage(
        "conf/gui/BtnSel.png");
    button = new ImageButton(nextNormalImage, nextRolloverImage, nextSelectedImage);
    button.setText(
        Translator.getString("button.Next[i18n]: Next"));
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        for (TutorialBoxListener listener: listeners) {
          listener.handleAction();
        }
      }     
    });
    add(button, cc.xy(3, 3));
    
    // add the text area
    textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setEditable(false);
    textArea.setOpaque(false);
    textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN, 12f));
    textArea.setMargin(new Insets(5, 5, 7, 5));
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(null);
    add(scrollPane, cc.xywh(2, 2, 2, 1));
  }
  
  /**
   * Returns the text displayed.
   * 
   * @return  The text.
   */
  public String getText() {
    return textArea.getText();
  }
  
  /**
   * Updates the text.
   * 
   * @param text  The text.
   */
  public void setText(String text) {
    textArea.setText(text.replace("\\n", "\n"));
  }
  
  /**
   * Returns true if the next button is shown.
   * 
   * @return  True for shown.
   */
  public boolean isShowNext() {
    return button.isVisible();
  }
  
  /**
   * True to show the next button.
   * 
   * @param show  True to show.
   */
  public void setShowNext(boolean show) {
    button.setVisible(show);
  }
  
  /**
   * Sets the text (in)visible.
   * 
   * @param visible  True for visible.
   */
  public void setTextVisible(boolean visible) {
    textArea.setVisible(visible);
  }
  
  /**
   * Returns if the text is (in)visible.
   * 
   * @return  True for visible.
   */
  public boolean isTextVisible() {
    return textArea.isVisible();
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addTutorialBoxListener(TutorialBoxListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeTutorialBoxListener(TutorialBoxListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draw base
    g.drawImage(image, 0, 0, this);
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    // text properties
    g2.setFont(g.getFont().deriveFont(10f));
    g2.setColor(Color.DARK_GRAY);
    
    // add i18n text.tutorial "Press [Esc] to exit"
    String sTutorial = Translator.getString("text.tutorial[i18n]: Press [Esc] to exit");
    g2.drawString(sTutorial, 18, 66);
  }
}
