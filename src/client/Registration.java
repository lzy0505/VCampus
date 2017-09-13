package client;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import client.Registration.DoubleClickModifyDelete;
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
	private JTextField textStudentCardID;
	private String information[][] = null;
	private String user_info_id[] =null;
	private String[] majors = {"建筑学院", "机械工程学院", "能源与环境学院", "信息科学与工程学院", 
	           				"土木工程学院", "电子科学与工程学院", "数学学院", "自动化学院", "计算机科学与工程学院", "物理系", "生物科学与医学工程学院", 
	        				"材料科学与工程学院", "人文学院", "经济管理学院", "电气工程学院", "外国语学院", "体育系", "化学化工学院", "交通学院", 
	        				"仪器科学与工程学院", "艺术学院", "法学院", "基础医学院", "公共卫生学院", "临床医学院", "吴健雄学院", "软件学院"};
	JTable searchTable =null;
	JRadioButton rdbtnFemale;
	JRadioButton rdbtnMale;
	JFormattedTextField textEnrollTime;
	JComboBox SpecialitySelection;
	
	//Information table construction
	String[] headName= {"StudentNumber","StudentName","Sex","EnrollmentTime","Specialty","",""};

	

	public Registration(String [][]data) {
		initialize(data);
		
	}

	private void initialize(String data[][]) {
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
		
		JLabel SInformationService = new JLabel("Student Information Service");
		SInformationService.setFont(new Font("Lucida Console", Font.PLAIN, 17));
		SInformationService.setBounds(21, 5, 700, 30);
		SInformationService.setForeground(Color.blue);
		
		
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
		
		
		/*String [][]data= {
				{"09015101","Mary","Female","201509","CSE","Modify","Delete"},//new integer test
				{"09015102","Kate","Female","201509","CSE","Modify","Delete"},
				{"09015103","Lili","Female","201509","CSE","Modify","Delete"},
				{"09015104","Amy","Female","201509","CSE","Modify","Delete"}
		};*/
		
		
		panel.setLayout(null);
		table = new JTable(data,headName);
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		
		//Scroll implement
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 6, 750, 396);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane);
		
		//The following code is about the "Information Import" bar.
		JPanel InformationImport_1 = new JPanel();
		tabbedPane.addTab("Information Import", null, InformationImport_1, null);
		InformationImport_1.setLayout(null);
		
		JLabel SInformationImport = new JLabel("Student Information Import");
		SInformationImport.setFont(new Font("Lucida Console", Font.PLAIN, 17));
		SInformationImport.setBounds(21,10,750,34);
		SInformationImport.setForeground(Color.blue);
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
		lblStudentNumber2.setBounds(123, 20, 142, 45);
		InfoImportActionBar.add(lblStudentNumber2);
		
		JLabel lblStudentCardID = new JLabel("StudentCardID:");
		lblStudentCardID.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblStudentCardID.setBounds(123, 60, 142, 45);
		InfoImportActionBar.add(lblStudentCardID);
		
		JLabel lblStudentname = new JLabel("StudentName:");
		lblStudentname.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblStudentname.setBounds(123, 100, 142, 45);
		InfoImportActionBar.add(lblStudentname);
		
		JLabel lblSex = new JLabel("Gender:");
		lblSex.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblSex.setBounds(123, 140, 142, 45);
		InfoImportActionBar.add(lblSex);
		
		JLabel lblEnrollmenttime = new JLabel("Grade:");
		lblEnrollmenttime.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblEnrollmenttime.setBounds(123, 180, 142, 45);
		InfoImportActionBar.add(lblEnrollmenttime);
		
		JLabel lblSpecialty = new JLabel("Major:");
		lblSpecialty.setFont(new Font("Lucida Fax", Font.ITALIC, 17));
		lblSpecialty.setBounds(123, 220, 142, 45);
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
		textStudentNo.setBounds(311, 30, 130, 26);
		InfoImportActionBar.add(textStudentNo);
		textStudentNo.setColumns(10);
		
		textStudentCardID = new JTextField();
		textStudentCardID.setBounds(311, 55, 130, 26);
		InfoImportActionBar.add(textStudentCardID);
		textStudentCardID.setColumns(10);
		
		textStudentName = new JTextField();
		textStudentName.setBounds(311, 80, 130, 26);
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

		search.addActionListener(new SearchLister());
		table.addMouseListener(new DoubleClickModifyDelete());
		btnButton.addActionListener(new SubmitLister());
		btnCancel.addActionListener(new CancelLister());

	}
	//查找学生功能
	class SearchLister implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			
			//find whether there is a person searched in the database,if yes,
			//give value to the array information[][]
			////if search successfully,show the searchFrame
			//TODO
			HashMap<String,String> hm= new HashMap<>();
			ArrayList<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();
			hm.put("student_id", studentnumber.getText());
			hm.put("nname",studentname.getText());
			hm.put("op", "search_student");
			hmList = GUI.getList(hm);
			if(hmList.size()!=0)
			{
				String information[][]=new String[hmList.size()][7];
				for(int i = 0;i<hmList.size();i++) {	
					information[i][0] = hmList.get(i).get("student_id");
					System.out.println("hmList.get(i).get(student_id) :" + hmList.get(i).get("student_id"));
					information[i][1] = hmList.get(i).get("nname");
					System.out.println("student name :" + hmList.get(i).get("nname"));
					information[i][2] = hmList.get(i).get("gender");
					System.out.println("gender) :" + hmList.get(i).get("gender"));
					information[i][3] = hmList.get(i).get("grade");
					System.out.println("grade :" + hmList.get(i).get("grade"));
					information[i][4] = hmList.get(i).get("major");
					System.out.println("major:" + hmList.get(i).get("major"));
					information[i][5] = "Modify";
					information[i][6]= "Delete";
					user_info_id[i] = hmList.get(i).get("user_info_id");
				}
			//create a window to show the searching student
			JFrame searchFrame = new JFrame("Search");
			searchFrame.setSize(800, 150);
			searchFrame.setLayout(null);
			JTable searchTable = new JTable(information,headName);
			searchTable.setBounds(5, 5, 740, 90);
			searchFrame.add(searchTable);
			searchFrame.setLocation(300, 250);

			searchFrame.setVisible(true);
			searchTable.addMouseListener(new DoubleClickModifyDelete());
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Search failed!","Search Failed",JOptionPane.ERROR_MESSAGE);
			}

		}
		
	}
	
	//Modify and delete function
	class DoubleClickModifyDelete implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			for(int row=0,colModify=5,colDelete=6;row<table.getRowCount();row++)
			{
				if(e.getClickCount() == 2)
				{
					if(table.getSelectedRow() == row&&table.getSelectedColumn()==colModify)
					{
						//修改学生信息
						int result=JOptionPane.showConfirmDialog(null, "Modify confirmed?");
						if(result == 0)
						{
							HashMap<String,String> hm= new HashMap<>();
							ArrayList<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();
							hm.put("op", "modify_student");
							System.out.println((String)table.getValueAt(row, 0));
							hm.put("student_id",(String)table.getValueAt(row, 0));
							System.out.println((String)table.getValueAt(row, 1));
							hm.put("nname", (String)table.getValueAt(row, 1));
							System.out.println((String)table.getValueAt(row, 2));
							hm.put("gender",(String)table.getValueAt(row, 2));
							System.out.println((String)table.getValueAt(row, 3));
							hm.put("grade", (String)table.getValueAt(row, 3));
							System.out.println((String)table.getValueAt(row, 4));
							hm.put("major", (String)table.getValueAt(row, 4));
							hm.put("user_info_id", user_info_id[row]);
							GUI.send(hm);
						    //Modify the information of student[row] in the database
							//TODO
							JOptionPane.showMessageDialog(null, "Modify successfully!","Modify Successfully",JOptionPane.PLAIN_MESSAGE);

						}
					}
					//删除学生信息
					else if(table.getSelectedRow() == row&&table.getSelectedColumn()==colDelete)
					{
						int result = JOptionPane.showConfirmDialog(null, "Delete confirmed?");
						if(result == 0)
						{					
							//delete the information of student[row] from database
							//TODO
							HashMap<String,String> hm= new HashMap<>();
							hm.put("student_id",(String)table.getValueAt(row, 0));
							GUI.send(hm);
							JOptionPane.showMessageDialog(null, "Delete successfully!","Delete Successfully",JOptionPane.PLAIN_MESSAGE);
							
						}
					}
				}	
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}
		
	}
	//导入学生信息
	//submit function in "information import"
	class SubmitLister implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			//put textStudentNo.getText(),textStudentName.getText(),sex,textEnrollTime.getText(),
			//SpecialitySelection.getSelectedItem() into the database
			//TODO
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("op","import_student");
			hm.put("nname", "\'"+textStudentName.getText()+"\'");
			hm.put("student_id","\'"+ textStudentNo.getText()+"\'");
			if(rdbtnMale.isSelected())hm.put("gender", "\'male\'");
			else if(rdbtnFemale.isSelected()) hm.put("gender", "\'female\'");
			else {
				JOptionPane.showMessageDialog(null, "ERROR!","Choose the gender",JOptionPane.ERROR_MESSAGE);
				return;
			}
			hm.put("grade", textEnrollTime.getText());
			hm.put("card_id", textStudentCardID.getText());
			hm.put("major", "\'"+majors[SpecialitySelection.getSelectedIndex()]+"\'");
			GUI.send(hm);
			JOptionPane.showMessageDialog(null, "Submit successfully!","Submit Successfully",JOptionPane.PLAIN_MESSAGE);
			
		}	
	}
	
	class CancelLister implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {

			textStudentNo.setText(null);
			textStudentName.setText(null);
			textStudentCardID.setText(null);
			textEnrollTime.setText(null);
		}
		
	}
}


