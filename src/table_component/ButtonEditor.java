package table_component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor {
	  protected JButton button;

	  private String content;

	  private boolean isPushed;

	  public ButtonEditor(JCheckBox checkBox) {
	    super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
//	    if (isSelected) {
//	      button.setForeground(table.getSelectionForeground());
//	      button.setBackground(table.getSelectionBackground());
//	    } else {
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
//	    }
	    content=value.toString();
	    button.setText(content);
	    isPushed = true;
	    return button;
	  }

	  public Object getCellEditorValue() {
	    isPushed = false;
	    return new String(content);
	  }
	  public boolean getIsPushed() {
		  return isPushed;
	  }
	  public void setIsPushed(boolean b) {
		  isPushed=b;
	  }

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }
	}