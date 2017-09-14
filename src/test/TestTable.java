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

import client.Registration;
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
		String [][]data= {
		{"09015101","Mary","Female","201509","CSE","Modify","Delete"},//new integer test
		{"09015102","Kate","Female","201509","CSE","Modify","Delete"},
		{"09015103","Lili","Female","201509","CSE","Modify","Delete"},
		{"09015104","Amy","Female","201509","CSE","Modify","Delete"}};
		Registration r=new Registration(data);
	}

}
