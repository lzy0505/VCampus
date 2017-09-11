package client;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import client.Registration.CancelLister;
import client.Registration.SubmitLister;

import java.awt.Font;

public class Registration {

	private JFrame frame;
	private JTextField studentnumber;
	private JTextField studentname;
	private JTable table;
	private JTextField textStudentNo;
	private JTextField textStudentName;
	
	JRadioButton rdbtnFemale;
	JRadioButton rdbtnMale;
	JFormattedTextField textEnrollTime;
	JComboBox SpecialitySelection;

	public Registration() {
		initialize();
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(21, 17, 850, 540);
		frame.getContentPane().add(tabbedPane);
		
		JPanel InformationService = new JPanel();
		tabbedPane.addTab("Information Service", null, InformationService, null);
		InformationService.setLayout(null);
		
		/*JPanel DisplayBar1 = new JPanel();
		DisplayBar1.setBorder(null);
		DisplayBar1.setBounds(21, 5, 760, 33);
		
		
		DisplayBar1.setBackground(UIManager.getColor("MenuBar.selectionBackground"));
		InformationService.add(DisplayBar1);
		DisplayBar1.setLayout(new BorderLayout(0, 0));*/
		
		JLabel SInformationService = new JLabel("Student Information Service");
		SInformationService.setFont(new Font("Lucida Console", Font.PLAIN, 17));
		SInformationService.setBounds(21, 5, 700, 30);
		SInformationService.setForeground(Color.blue);
		//SInformationService.setForeground(UIManager.getColor("CheckBoxMenuItem.background"));
		//SInformationService.setBackground(SystemColor.inactiveCaption);
		//DisplayBar1.add(SInformationService);
		
		InformationService.add(SInformationService);
		
		JPanel ActionBar1 = new JPanel();
		ActionBar1.setBounds(21, 37, 800, 47);
		InformationService.add(ActionBar1);
		ActionBar1.setLayout(null);
		
		JLabel StudentNumber = new JLabel("Student Number:");
		StudentNumber.setBounds(40, 6, 118, 35);
		ActionBar1.add(StudentNumber);
		
		studentnumber = new JTextField();
		studentnumber.setBounds(155, 10, 130, 26);
		ActionBar1.add(studentnumber);
		studentnumber.setColumns(10);
		
		JLabel StudentName = new JLabel("Student Name:");
		StudentName.setBounds(307, 6, 118, 35);
		ActionBar1.add(StudentName);
		
		studentname = new JTextField();
		studentname.setColumns(10);
		studentname.setBounds(409, 10, 130, 26);
		ActionBar1.add(studentname);
		
		
		JButton search = new JButton("Search");
		search.setBounds(569, 10, 117, 29);
		ActionBar1.add(search);
		
		JPanel panel = new JPanel();
		panel.setBounds(21, 84, 750, 408);
		InformationService.add(panel);
		
		//Information table construction
		String[] headName= {"StudentNumber","StudentName","Sex","EnrollmentTime","Specialty","Operation",""};
		/*Try to implement "add buttons to the table",but failed
		JButton [] buttons=new JButton[3];
		buttons[0]=new JButton("Check");
		buttons[1]=new JButton("Modify");
		buttons[2]=new JButton("Delete");*/
		Object[] [] data= {
				{"09015101","Mary","Female",new Integer(5),"CSE","Modify","Delete"},//new integer test
				{"09015102","Kate","Female","201509","CSE","Modify","Delete"},
				{"09015103","Lili","Female","201509","CSE","Modify","Delete"},
				{"09015104","Amy","Female","201509","CSE","Modify","Delete"}
		};
		panel.setLayout(null);
		JTable table = new JTable(data,headName);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		
		
		
		JLabel test = new JLabel("Modifying");
		table.add(test,10);
		
		
		
		
		//Scroll implement
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 6, 750, 396);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane);
		
		//when having no scrollpane(only have a table), use the following method
		//scrollPane.setLayout(new BorderLayout());  
		//scrollPane.add(table.getTableHeader(), BorderLayout.PAGE_START);  
		//scrollPane.add(table, BorderLayout.CENTER);

		
		
		//The following code is about the "Information Import" bar.
		JPanel InformationImport_1 = new JPanel();
		tabbedPane.addTab("Information Import", null, InformationImport_1, null);
		InformationImport_1.setLayout(null);
		
		/*JPanel DisplayBar2 = new JPanel();
		DisplayBar2.setBounds(21, 5, 750, 34);
		DisplayBar2.setBorder(null);
		DisplayBar2.setBackground(UIManager.getColor("MenuBar.selectionBackground"));
		InformationImport_1.add(DisplayBar2);
		DisplayBar2.setLayout(new BorderLayout(0, 0));*/
		
		JLabel SInformationImport = new JLabel("Student Information Import");
		SInformationImport.setFont(new Font("Lucida Console", Font.PLAIN, 17));
		SInformationImport.setBounds(21,10,750,34);
		SInformationImport.setForeground(Color.blue);
		//SInformationImport.setBackground(SystemColor.inactiveCaption);
		//DisplayBar2.add(SInformationImport, BorderLayout.CENTER);
		InformationImport_1.add(SInformationImport);
		
		JPanel InfoImportActionBar = new JPanel();
		InfoImportActionBar.setBounds(21, 43, 750, 427);
		InformationImport_1.add(InfoImportActionBar);
		InfoImportActionBar.setLayout(null);
		
		JSeparator separatorVer = new JSeparator();
		separatorVer.setOrientation(SwingConstants.VERTICAL);
		separatorVer.setBounds(290, 20, 12, 239);
		InfoImportActionBar.add(separatorVer);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 61, 738, 12);
		InfoImportActionBar.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 103, 738, 12);
		InfoImportActionBar.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(6, 154, 738, 12);
		InfoImportActionBar.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(6, 202, 738, 12);
		InfoImportActionBar.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(6, 257, 738, 12);
		InfoImportActionBar.add(separator_5);
		
		JButton btnButton = new JButton("Summit");
		btnButton.setBounds(140, 310, 117, 29);
		InfoImportActionBar.add(btnButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(320, 310, 117, 29);
		InfoImportActionBar.add(btnCancel);
		
		JLabel lblStudentNumber2 = new JLabel("StudentNumber:");
		lblStudentNumber2.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblStudentNumber2.setBounds(123, 18, 142, 45);
		InfoImportActionBar.add(lblStudentNumber2);
		
		JLabel lblStudentname = new JLabel("StudentName:");
		lblStudentname.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblStudentname.setBounds(123, 62, 142, 45);
		InfoImportActionBar.add(lblStudentname);
		
		JLabel lblSex = new JLabel("Sex:");
		lblSex.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblSex.setBounds(123, 110, 142, 45);
		InfoImportActionBar.add(lblSex);
		
		JLabel lblEnrollmenttime = new JLabel("EnrollmentTime:");
		lblEnrollmenttime.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblEnrollmenttime.setBounds(123, 160, 142, 45);
		InfoImportActionBar.add(lblEnrollmenttime);
		
		JLabel lblSpecialty = new JLabel("Speciality:");
		lblSpecialty.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblSpecialty.setBounds(123, 216, 142, 45);
		InfoImportActionBar.add(lblSpecialty);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(321, 119, 81, 23);
		InfoImportActionBar.add(rdbtnFemale);
		
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setSelected(true);
		rdbtnMale.setBounds(411, 119, 75, 23);
		InfoImportActionBar.add(rdbtnMale);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnFemale);
		buttonGroup.add(rdbtnMale);
		
		
		textStudentNo = new JTextField();
		textStudentNo.setBounds(311, 29, 130, 26);
		InfoImportActionBar.add(textStudentNo);
		textStudentNo.setColumns(10);
		
		textStudentName = new JTextField();
		textStudentName.setBounds(311, 73, 130, 26);
		InfoImportActionBar.add(textStudentName);
		textStudentName.setColumns(10);
		
		SpecialitySelection = new JComboBox();
		SpecialitySelection.setModel(new DefaultComboBoxModel(new String[] {"建筑学院", "机械工程学院", "能源与环境学院", "信息科学与工程学院", 
				"土木工程学院", "电子科学与工程学院", "数学学院", "自动化学院", "计算机科学与工程学院", "物理系", "生物科学与医学工程学院", 
				"材料科学与工程学院", "人文学院", "经济管理学院", "电气工程学院", "外国语学院", "体育系", "化学化工学院", "交通学院", 
				"仪器科学与工程学院", "艺术学院", "法学院", "基础医学院", "公共卫生学院", "临床医学院", "吴健雄学院", "软件学院"}));
		SpecialitySelection.setMaximumRowCount(28);
		SpecialitySelection.setBounds(314, 226, 218, 27);
		InfoImportActionBar.add(SpecialitySelection);
		
		textEnrollTime = new JFormattedTextField();
		textEnrollTime.setText("2010-09");
		textEnrollTime.setBounds(321, 171, 81, 26);
		InfoImportActionBar.add(textEnrollTime);
		
		frame.setVisible(true);

		btnButton.addActionListener(new SubmitLister());
		btnCancel.addActionListener(new CancelLister());
		
		//if "delete" is chosen,turn to the method deleteConfirm()
		//TODO
	}
	
	//delete function in "student information"
	public void deleteConfirm()
	{
		Object[] options ={ "确定删除", "取消并返回" };
		JOptionPane.showOptionDialog(null, "Are you sure that you want to delete this student status information?","Delete Confirm",JOptionPane.YES_NO_OPTION,
				  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		int result = JOptionPane.showConfirmDialog(null, "Delete confirm?");
		if(result == 0)
		{
			//delete corresponding data from database
			//TODO
			JOptionPane.showMessageDialog(null, "Delete successfully!","Delete Successfully",JOptionPane.PLAIN_MESSAGE);
		}
	
	}

	//submit function in "information import"
	class SubmitLister implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			//put textStudentNo.getText(),textStudentName.getText(),sex,textEnrollTime.getText(),
			//SpecialitySelection.getSelectedItem() into the database
			//TODO
			
			//
			JOptionPane.showMessageDialog(null, "Submit successfully!","Submit Successfully",JOptionPane.PLAIN_MESSAGE);
			
		}	
	}
	
	class CancelLister implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {

			textStudentNo.setText(null);
			textStudentName.setText(null);
			textEnrollTime.setText(null);
		}
		
	}
}


