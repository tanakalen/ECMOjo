package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Polygon;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.RenderingHints;

/**
 * The tabbed pane UI.
 * 
 * @author    king 
 * @since     May 3, 2006
 */
public class GameTabbedPaneUI extends MetalTabbedPaneUI {
  
  /** The width of the last tab. */
  private final int lastWidth = 20;
  
  
  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GameTabbedPaneUI();
  }

  /**
   * Installs defaults. 
   */
  protected void installDefaults() {
    super.installDefaults();
    tabAreaInsets.left = 4;
    selectedTabPadInsets = new Insets(0, 0, 0, 0);
    tabInsets = selectedTabPadInsets;
  }

  /**
   * Tab run count.
   * 
   * @param pane  The tabbed pane.
   * @return  The tab run count.
   */
  public int getTabRunCount(JTabbedPane pane) {
    return 1;
  }

  /**
   * Returns the insets.
   * 
   * @param tabPlacement  The tab placement.
   * @return  The insets.
   */
  protected Insets getContentBorderInsets(int tabPlacement) {
    return new Insets(1, 1, 1, 1);
  }

  /**
   * Returns the tab height.
   * 
   * @param tabPlacement  The tab placement.
   * @param tabIndex  The tab index.
   * @param fontHeight  The font height.
   * @return  Tab height.
   */
  protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
    int vHeight = fontHeight;
    if (vHeight % 2 > 0) {
      vHeight += 1;
    }
    return vHeight;
  }

  /**
   * Calculates tab width.
   * 
   * @param tabPlacement  The tab placement.
   * @param tabIndex  Tab index.
   * @param metrics  Font metrics.
   * @return Tab width.
   */
  protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
    int width = super.calculateTabWidth(tabPlacement, tabIndex, metrics) + metrics.getHeight();
    if (tabIndex == (rects.length - 1)) {
      width += lastWidth;
    }
    return width;
  }

  /**
   * Paints the border edge.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param tabIndex  The tab index.
   * @param x  X position.
   * @param y  Y position.
   * @param w  Width.
   * @param h  Height.
   * @param isSelected  If selected.
   */
  protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex
                                  , int x, int y, int w, int h, boolean isSelected) {
    if (tabIndex == (rects.length - 1)) {
      w -= lastWidth;
    }
    
    Polygon shape = new Polygon();
    
    shape.addPoint(x, y + h);
    shape.addPoint(x, y);
    shape.addPoint(x + w - (h / 2), y);

    if (isSelected) {
      shape.addPoint(x + w + (h / 2), y + h);
      g.setColor(GameLookAndFeel.DARK_BACKGROUND_COLOR);
    }
    else if (tabIndex == (rects.length - 1)) {
      shape.addPoint(x + w + (h / 2), y + h);
      g.setColor(GameLookAndFeel.BRIGHT_BACKGROUND_COLOR);
    }
    else {
      shape.addPoint(x + w, y + (h / 2));
      shape.addPoint(x + w, y + h);
      g.setColor(GameLookAndFeel.BRIGHT_BACKGROUND_COLOR);
    }

    g.fillPolygon(shape);
  }

  /**
   * Paints the border edge.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param tabIndex  The tab index.
   * @param x  X position.
   * @param y  Y position.
   * @param w  Width.
   * @param h  Height.
   * @param isSelected  If selected.
   */
  protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex
                              , int x, int y, int w, int h, boolean isSelected) {
    if (tabIndex == (rects.length - 1)) {
      w -= lastWidth;
    }

    g.setColor(GameLookAndFeel.DARKEST_BACKGROUND_COLOR.darker());
    g.drawLine(x, y, x, y + h);
    g.drawLine(x, y, x + w - (h / 2), y);
    g.drawLine(x + w - (h / 2), y, x + w + (h / 2), y + h);

    if (isSelected) {
      g.setColor(GameLookAndFeel.DARKEST_BACKGROUND_COLOR);
      g.drawLine(x + 1, y + 1, x + 1, y + h);
      g.drawLine(x + 1, y + 1, x + w - (h / 2), y + 1);

      g.setColor(Color.BLACK);
      g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2) - 1, y + h);
    }
  }

  /**
   * Paints the border edge.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param selectedIndex  The selected index.
   * @param x  X position.
   * @param y  Y position.
   * @param w  Width.
   * @param h  Height.
   */
  protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex
                                         , int x, int y, int w, int h) {
    int offset = 0;
    if (selectedIndex == (rects.length - 1)) {
      offset = lastWidth;
    }
    
    Rectangle selectedRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);

    selectedRect.width = selectedRect.width + (selectedRect.height / 2) - 1;

    g.setColor(GameLookAndFeel.DARKEST_BACKGROUND_COLOR);
    g.drawLine(x, y, selectedRect.x, y);
    g.drawLine(selectedRect.x + selectedRect.width - offset + 1, y, x + w, y);

    g.setColor(GameLookAndFeel.DARK_BACKGROUND_COLOR);
    g.drawLine(selectedRect.x + 2, y, selectedRect.x + selectedRect.width - offset - 1, y);
    
    g.setColor(Color.BLACK);
    g.drawLine(selectedRect.x + selectedRect.width - offset, y, selectedRect.x + selectedRect.width - offset, y);
  }

  /**
   * Paints the border edge.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param selectedIndex  The selected index.
   * @param x  X position.
   * @param y  Y position.
   * @param w  Width.
   * @param h  Height.
   */
  protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex
                                           , int x, int y, int w, int h) {
    g.setColor(GameLookAndFeel.DARKEST_BACKGROUND_COLOR);
    g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
  }

  /**
   * Paints the border edge.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param selectedIndex  The selected index.
   * @param x  X position.
   * @param y  Y position.
   * @param w  Width.
   * @param h  Height.
   */
  protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex
                                          , int x, int y, int w, int h) {
    g.setColor(GameLookAndFeel.DARKEST_BACKGROUND_COLOR);
    g.drawLine(x, y, x, y + h - 1);
  }

  /**
   * Paints the border edge.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param selectedIndex  The selected index.
   * @param x  X position.
   * @param y  Y position.
   * @param w  Width.
   * @param h  Height.
   */
  protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex
                                            , int x, int y, int w, int h) {
    g.setColor(GameLookAndFeel.DARKEST_BACKGROUND_COLOR);
    g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
  }

  /**
   * Paints focus indicator.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param rects  Rectangles.
   * @param tabIndex  The tab index.
   * @param iconRect  The icon rectangle.
   * @param textRect  The text rectancle.
   * @param isSelected  If selected.
   */
  protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex
                                   , Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    // Do nothing
  }

  /**
   * Paints the tab area.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param selectedIndex  The selected index.
   */
  protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
    super.paintTabArea(g, tabPlacement, selectedIndex);
  }

  /**
   * Paints the text.
   * 
   * @param g  Where to draw to.
   * @param tabPlacement  The tab placement.
   * @param font  The font.
   * @param metrics  The font metrics.
   * @param tabIndex  The tab index.
   * @param title  The title.
   * @param textRect  The text rectancle.
   * @param isSelected  If selected.
   */
  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics
                        , int tabIndex, String title, Rectangle textRect, boolean isSelected) {
    int offset = 0;
    if (tabIndex == (rects.length - 1)) {
      offset = lastWidth / 2;
    }
    
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    if (isSelected) {
      g.setColor(GameLookAndFeel.DARK_FOREGROUND_COLOR);
      g.setFont(g.getFont().deriveFont(Font.BOLD));
    }
    else {
      g.setColor(GameLookAndFeel.DEFAULT_FOREGROUND_COLOR);
    }  
    g.drawString(title, textRect.x - offset, textRect.y + metrics.getAscent());
  }

  /**
   * The tab label y shift.
   * 
   * @param tabPlacement  The tab placement.
   * @param tabIndex  The tab index.
   * @param isSelected  If selected.
   * @return  The y shift.
   */
  protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
    return 0;
  }
}
