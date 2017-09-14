/**
 * 
 */
package test;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


import table_component.ButtonRenderer;
import table_component.ButtonEditor;

/**
 * @author lzy05
 *
 */
public class TestTable {

	/**
	 * 
	 */
	public static void main(String args[]) {
		JFrame frame=new JFrame();
		DefaultTableModel tableModel=new DefaultTableModel();
		tableModel.setDataVector( new Object[][] { { "s11","s12","button 11", "button12" },
	        { "s21","s22","button 21", "button22" } 
		}, new Object[] {"String1","String2","Button1","Button2"});
		JTable table = new JTable(tableModel); 
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true); 

        TableCellRenderer buttonRenderer = new ButtonRenderer();
        table.getColumn("Button1").setCellRenderer(buttonRenderer);
        table.getColumn("Button2").setCellRenderer(buttonRenderer);
        table.getColumn("Button1").setCellEditor(
                new ButtonEditor(new JCheckBox()));
        frame.add(table);
        frame.setSize(400,400);
        frame.setVisible(true);
	}

}
