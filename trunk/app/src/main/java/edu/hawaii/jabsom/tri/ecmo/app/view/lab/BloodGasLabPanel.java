package edu.hawaii.jabsom.tri.ecmo.app.view.lab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import king.lib.access.ImageLoader;
import king.lib.util.Translator;

import org.jdesktop.swingx.JXTable;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.LabRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabList.LabTestListener;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.LabDetailPanel;

/**
 * The blood gas lab test panel. 
 *
 * @author   Christoph Aschwanden
 * @since    October 7, 2008
 */
public class BloodGasLabPanel extends LabDetailPanel implements LabTestListener {
  
  /** The lab component. */
  private LabComponent component;
  
  /** The table model. */
  private DefaultTableModel tableModel;
  
  /** The table. */
  private JXTable table;
  
  /** DEFAULT not applicable N/A i18n text string. **/
  private String notApplicable = Translator.getString("text.NotApplicable[i18n]: N/A");
  
  /**
   * Default constructor.
   * 
   * @param component  The component
   */
  public BloodGasLabPanel(final LabComponent component) {
    super(component);
    this.component = component;
    
    // add title
    JLabel titleLabel = new JLabel(Translator.getString("label.BloodGas[i18n]: Blood Gas"));
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
    titleLabel.setLocation(28, 34);
    titleLabel.setSize(150, 20);
    add(titleLabel);
    
    Image requestButtonImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnSmall.png");
    Image requestButtonRolloverImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnSmallRol.png");
    Image requestButtonSelectedImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnSmallSel.png");
    
    // add lab request button
    ImageButton requestButton
      = new ImageButton(requestButtonImage, requestButtonRolloverImage, requestButtonSelectedImage);
    requestButton.setText(
        Translator.getString("button.newLab[i18n]: Lab"));
    requestButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        LabRequestAction action = new LabRequestAction();
        action.setLabTest(BloodGasLab.class);
        notifyActionListeners(action);
      }    
    });
    requestButton.setLocation(173, 34);
    requestButton.setSize(54, 22);
    add(requestButton);
    
    // add table with values
    tableModel = new DefaultTableModel() {
      private DecimalFormat labHundredthsFormatter = new DecimalFormat("###.00");
      private DecimalFormat labFormatter = new DecimalFormat("###");
      public int getColumnCount() {
        int size = component.getResults().size();
        if (size < 10) {
          size = 10;
        }
        return size + 1;
      }
      public int getRowCount() {
        // blood gas has 5 values and 1 type (location)
        return 6;
      }
      public String getColumnName(int col) {
        if (col == 0) {
          return " ";
        }
        else {
          int size = component.getResults().size();
          if (col <= size) {
            return String.valueOf(size - (col - 1));
          }
          else {
            return "-";
          }
        }
      }
      public Object getValueAt(int row, int col) {
        if (col == 0) {
          switch (row) {
            case 0:
              return Translator.getString("text.Type[i18n]: Type");
            case 1:
              return "pH";
            case 2:
              return "PCO2";
            case 3:
              return "PO2";
            case 4:
              return "HCO3";
            case 5:
              return "BE";
            default:
              // error condition
              return null;
          }
        }
        else {
          int size = component.getResults().size();
          if (col <= size) {
            switch (row) {
              case 0:
                return ((BloodGasLab)component.getResults().get(size - col)).getBloodGasType().getName();
              case 1:
                return labHundredthsFormatter.format(((BloodGasLab)component.getResults().get(size - col)).getPH());
              case 2:
                return labFormatter.format(((BloodGasLab)component.getResults().get(size - col)).getPCO2());
              case 3:
                return labFormatter.format(((BloodGasLab)component.getResults().get(size - col)).getPO2());
              case 4:
                return labFormatter.format(((BloodGasLab)component.getResults().get(size - col)).getHCO3());
              case 5:
                return labFormatter.format(((BloodGasLab)component.getResults().get(size - col)).getBE());
              default:
                // error condition
                return null;
            }
          }
          else {
            switch (row) {
              case 0:
                return notApplicable;
              case 1:
                return notApplicable;
              case 2:
                return notApplicable;
              case 3:
                return notApplicable;
              case 4:
                return notApplicable;
              case 5:
                return notApplicable;
              default:
                // error condition
                return null;
            }
          }
        }
      }
      public boolean isCellEditable(int row, int col) { 
        return false;
      }
    };
    
    table = new JXTable(tableModel);
    updateTable();
    
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setLocation(28, 59);
    scrollPane.setSize(215, 185);
    add(scrollPane);
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    component.getResults().addLabTestListener(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    component.getResults().removeLabTestListener(this);
    
    super.removeNotify();
  }

  /**
   * Called when the lab tests have been updated.
   */
  public void handleLabTestUpdate() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        tableModel.fireTableStructureChanged();
        tableModel.fireTableDataChanged();
        updateTable();
      }
    });
  }
  
  /**
   * Update the table.
   */
  private void updateTable() {
    TableCellRenderer firstColumnStyle = new DefaultTableCellRenderer() {
      public Component getTableCellRendererComponent(JTable table,
          Object value, boolean isSelected, boolean hasFocus, int row,
          int column) {
        setHorizontalAlignment(JLabel.LEFT);
        setBackground(new Color(0xEEEEEE));
        setText((value == null) ? "" : value.toString());
        return this;
      }      
    };
    
    TableCellRenderer firstRowStyle = new DefaultTableCellRenderer() {
      public Component getTableCellRendererComponent(JTable table,
          Object value, boolean isSelected, boolean hasFocus, int row,
          int column) {
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(JLabel.CENTER);
        setFont(getFont().deriveFont(Font.BOLD));
        setText((value == null) ? "" : value.toString());
        return this;
      }      
    };
    
    TableCellRenderer otherRowStyle = new DefaultTableCellRenderer() {
      public Component getTableCellRendererComponent(JTable table,
          Object value, boolean isSelected, boolean hasFocus, int row,
          int column) {
        setHorizontalAlignment(JLabel.CENTER);
        setText((value == null) ? "" : value.toString());
        return this;
      }
    };
    
    table.getColumn(0).setCellRenderer(firstColumnStyle);
    for (int i = 1; i < table.getColumnCount(); i++) {
      table.getColumn(i).setHeaderRenderer(firstRowStyle);
      table.getColumn(i).setCellRenderer(otherRowStyle); 
    }
    table.setFillsViewportHeight(true);
    table.setHorizontalScrollEnabled(true);
  }
}
