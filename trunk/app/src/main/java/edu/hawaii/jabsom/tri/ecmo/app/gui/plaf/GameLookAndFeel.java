package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import edu.hawaii.jabsom.tri.ecmo.app.gui.NarrowBevelBorder;

/**
 * The look and feel class! 
 * 
 * @author king
 * @since August 26, 2007
 */
public class GameLookAndFeel extends MetalLookAndFeel {

  /** The default background color. */
  public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xCDCDCD);
  /** The dark background color. */
  public static final Color DARK_BACKGROUND_COLOR = new Color(0xC0C1C5);
  /** The darker background color. */
  public static final Color DARKER_BACKGROUND_COLOR = new Color(0xB0B1B5);
  /** The darker background color. */
  public static final Color DARKEST_BACKGROUND_COLOR = new Color(0xA0A1A4);
  /** The bright background color. */
  public static final Color BRIGHT_BACKGROUND_COLOR = new Color(0xD7D7D7);
  /** The bright background color. */
  public static final Color BRIGHTER_BACKGROUND_COLOR = new Color(0xE7E7E7);
  /** The bright background color. */
  public static final Color BRIGHTEST_BACKGROUND_COLOR = new Color(0xF7F7F7);

  /** The default foreground color. */
  public static final Color DEFAULT_FOREGROUND_COLOR = new Color(0x101010);
  /** The dark foreground color. */
  public static final Color DARK_FOREGROUND_COLOR = new Color(0x000000);
  /** The bright foreground color. */
  public static final Color BRIGHT_FOREGROUND_COLOR = new Color(0x1c1c1c);

  /** The info background color. */
  public static final Color GAME_INFO_BACKGROUND_COLOR = new Color(0.6f, 0.6f, 0.6f, 0.9f);
  
  
  /**
   * Inits the class defaults.
   * 
   * @param table  The table.
   */
  protected void initClassDefaults(UIDefaults table) {
    super.initClassDefaults(table);

    // set default components
    Object[] uiDefaults = {
              "ButtonUI", GameButtonUI.class.getName(),
               "LabelUI", GameLabelUI.class.getName(),
            "TextAreaUI", GameTextAreaUI.class.getName(),
        "ToggleButtonUI", GameButtonUI.class.getName(),
         "RadioButtonUI", GameRadioButtonUI.class.getName(),
               "PanelUI", GamePanelUI.class.getName(),
          "ScrollPaneUI", GameScrollPaneUI.class.getName(),
           "ScrollBarUI", GameScrollBarUI.class.getName(),
            "ComboBoxUI", GameComboBoxUI.class.getName(),
             "SpinnerUI", GameSpinnerUI.class.getName(),
          "TabbedPaneUI", GameTabbedPaneUI.class.getName(),
                "MenuUI", GameMenuUI.class.getName(),
            "MenuItemUI", GameMenuItemUI.class.getName(),
             "ToolTipUI", GameToolTipUI.class.getName(),
    };
    table.putDefaults(uiDefaults);
    
    // set JButton + JToggleButton values
    UIManager.put("Button.font", ((Font)UIManager.get("Button.font")).deriveFont(Font.PLAIN));
    UIManager.put("Button.background", DARKEST_BACKGROUND_COLOR);
    UIManager.put("Button.foreground", DARK_FOREGROUND_COLOR.darker());
    UIManager.put("Button.disabledText", DARKER_BACKGROUND_COLOR.darker());
    UIManager.put("Button.rolloverBackground", DARKER_BACKGROUND_COLOR);
    UIManager.put("Button.pressedBackground", DEFAULT_BACKGROUND_COLOR);

    // set JLabel values
    UIManager.put("Label.font", ((Font)UIManager.get("Label.font")).deriveFont(Font.PLAIN));
  
    // set JPanel values
    UIManager.put("Panel.background", DEFAULT_BACKGROUND_COLOR);

    // set JTextField values
    UIManager.put("TextField.border", new NarrowBevelBorder());
    UIManager.put("TextField.background", BRIGHTEST_BACKGROUND_COLOR);

    // set JTextArea values
    UIManager.put("TextArea.font", ((Font)UIManager.get("TextArea.font")).deriveFont(Font.PLAIN, 11f));
    UIManager.put("TextArea.background", DARK_BACKGROUND_COLOR);

    // set JScrollPane values
    UIManager.put("ScrollPane.background", DARK_BACKGROUND_COLOR);
    UIManager.put("ScrollPane.border", new NarrowBevelBorder());

    // set JScrollBar values
    UIManager.put("ScrollBar.track", BRIGHT_BACKGROUND_COLOR);
    UIManager.put("ScrollBar.thumb", DARKER_BACKGROUND_COLOR);
    UIManager.put("ScrollBar.thumbShadow", DARKER_BACKGROUND_COLOR.darker());
    UIManager.put("ScrollBar.thumbHighlight", DARK_BACKGROUND_COLOR);
    
    // set JList values
    UIManager.put("List.selectionBackground", DARK_BACKGROUND_COLOR.darker());
    UIManager.put("List.selectionForeground", DARK_FOREGROUND_COLOR.darker());
    UIManager.put("List.background", DARK_BACKGROUND_COLOR);
    UIManager.put("List.foreground", DARK_FOREGROUND_COLOR);
    UIManager.put("List.font", ((Font)UIManager.get("List.font")).deriveFont(Font.PLAIN, 10.0f));
    
    // set JComboBox values (=dropdown list)
    UIManager.put("ComboBox.background", DARK_BACKGROUND_COLOR);
    UIManager.put("ComboBox.foreground", DARK_FOREGROUND_COLOR);
    UIManager.put("ComboBox.disabledBackground", DARK_BACKGROUND_COLOR.darker());
    UIManager.put("ComboBox.disabledForeground", DARK_FOREGROUND_COLOR.darker());
    UIManager.put("ComboBox.font", ((Font)UIManager.get("List.font")).deriveFont(Font.PLAIN));
    
    // set JSpinner values
    UIManager.put("Spinner.border", new NarrowBevelBorder());
    UIManager.put("Spinner.background", BRIGHTEST_BACKGROUND_COLOR);
    
    // set JTabbedPane values
    UIManager.put("TabbedPane.contentAreaColor", DARK_BACKGROUND_COLOR);
    UIManager.put("TabbedPane.font", ((Font)UIManager.get("TabbedPane.font")).deriveFont(Font.PLAIN));
    
    // set JMenuItem values
    UIManager.put("MenuItem.font", ((Font)UIManager.get("MenuItem.font")).deriveFont(Font.PLAIN));
    
    // set JOptionPane values
    UIManager.put("OptionPane.background", DEFAULT_BACKGROUND_COLOR);
    
    // set ToolTip values
    UIManager.put("ToolTip.background", new Color(0xf0fff7c8, true));
    UIManager.put("ToolTip.border", new LineBorder(new Color(0x4c4f53), 1, true));
  }
  
  /**
   * The name.
   * 
   * @return  The name.
   * @see javax.swing.plaf.metal.MetalLookAndFeel#getName()
   */
  public String getName() {
    return "CityGame";
  }
  
  /**
   * Returns the description.
   * 
   * @return  The description.
   * @see javax.swing.plaf.metal.MetalLookAndFeel#getDescription()
   */
  public String getDescription() {
    return "The City Game Look & Feel.";
  }
}
