package edu.hawaii.jabsom.tri.ecmo.app.view.lab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.LabRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTestList.LabTestListener;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.LabDetailPanel;

/**
 * The chemistry lab test panel. 
 *
 * @author   Christoph Aschwanden
 * @since    October 7, 2008
 */
public class ChemistryLabTestPanel extends LabDetailPanel implements LabTestListener {

  /** The lab component. */
  private LabComponent component;
  
  /** The table model. */
  private DefaultTableModel tableModel;
  
  /** The table. */
  private JXTable table;
  
  /**
   * Default constructor.
   * 
   * @param component  The component
   */
  public ChemistryLabTestPanel(final LabComponent component) {
    super(component);
    this.component = component;
    
    // add title
    JLabel titleLabel = new JLabel("Chemistry");
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
    titleLabel.setLocation(28, 34);
    titleLabel.setSize(150, 20);
    add(titleLabel);
    
    // add lab request button
    JButton requestButton = new JButton("Lab");
    requestButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        LabRequestAction action = new LabRequestAction();
        action.setLabTest(ChemistryLabTest.class);
        notifyActionListeners(action);
      }    
    });
    requestButton.setLocation(173, 34);
    requestButton.setSize(70, 20);
    add(requestButton);

    // add table with values
    tableModel = new DefaultTableModel() {
      public int getColumnCount() {
        int size = component.getResults().size();
        if (size < 10) {
          size = 10;
        }
        return size + 1;
      }
      public int getRowCount() {
        // chemistry has 5 values!
        return 5;
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
              return "Gluc";
            case 1:
              return "IonCa";
            case 2:
              return "K";
            case 3:
              return "Lactate";
            case 4:
              return "Na";
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
                return ((ChemistryLabTest)component.getResults().get(size - col)).getGluc();
              case 1:
                return ((ChemistryLabTest)component.getResults().get(size - col)).getIonCa();
              case 2:
                return ((ChemistryLabTest)component.getResults().get(size - col)).getK();
              case 3:
                return ((ChemistryLabTest)component.getResults().get(size - col)).getLactate();
              case 4:
                return ((ChemistryLabTest)component.getResults().get(size - col)).getNa();
              default:
                // error condition
                return null;
            }
          }
          else {
            switch (row) {
              case 0:
                return "N/A";
              case 1:
                return "N/A";
              case 2:
                return "N/A";
              case 3:
                return "N/A";
              case 4:
                return "N/A";
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
