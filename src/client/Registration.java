package client;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;


import java.awt.Color;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import client.Registration.DoubleClickModifyDelete;
import client.Registration.CancelLister;
import client.Registration.SubmitLister;
import client.StudentAffairs.SelectCourseButtonEditor;
import table_component.ButtonEditor;
import table_component.ButtonRenderer;
import table_component.SpringUtilities;

import java.awt.Font;
import java.awt.GridLayout;

public class Registration {

	private JFrame frame;
	private JTextField studentnumber;
	private JTextField studentname;
	private JTable informationQueryTable;
	private JTextField textStudentNo;
	private JTextField textStudentName;
	private JTextField textStudentCardID;
	private String information[][] = null;
	private String user_info_id[] =null;
	private String[] majors = {"建筑学院", "机械工程学院", "能源与环境学院", "信息科学与工程学院", 
	           				"土木工程学院", "电子科学与工程学院", "数学学院", "自动化学院", "cs", "物理系", "生物科学与医学工程学院", 
	        				"材料科学与工程学院", "人文学院", "经济管理学院", "电气工程学院", "外国语学院", "体育系", "化学化工学院", "交通学院", 
	        				"仪器科学与工程学院", "艺术学院", "法学院", "基础医学院", "公共卫生学院", "临床医学院", "吴健雄学院", "软件学院"};
	JTable searchTable =null;
	JRadioButton rdbtnFemale;
	JRadioButton rdbtnMale;
	JFormattedTextField textEnrollTime;
	JComboBox SpecialitySelection;
	
	//Information table construction
	final String[] informationQueryTableHead= {"学号","姓名","性别","入学年份","专业","编辑","删除"};

	

	public Registration(String [][]data) {
		initialize(data);
		
	}

	private void initialize(String data[][]) {
		frame = new JFrame();
		frame.setBounds(0, 0, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 0, 600, 363);
		frame.getContentPane().add(tabbedPane);
		
		JPanel informationService = new JPanel();
		tabbedPane.addTab("信息管理", null, informationService, null);
		informationService.setLayout(new BoxLayout(informationService,BoxLayout.Y_AXIS));
		

		JPanel inputPanel = new JPanel();
		informationService.add(inputPanel);
		inputPanel.setLayout(new FlowLayout());
		
		JLabel StudentNumber = new JLabel("学号");
		inputPanel.add(StudentNumber);
		
		studentnumber = new JTextField();
		inputPanel.add(studentnumber);
		studentnumber.setColumns(10);
		
		JLabel StudentName = new JLabel("学生姓名");
		inputPanel.add(StudentName);
		
		studentname = new JTextField();
		studentname.setColumns(10);
		inputPanel.add(studentname);
		
		
		JButton search = new JButton("搜索");
		inputPanel.add(search);
		
		
		DefaultTableModel informationQueryTableModel=new DefaultTableModel(data,informationQueryTableHead);
		informationQueryTable=new JTable(informationQueryTableModel);
		informationQueryTable.setFillsViewportHeight(true);
		informationQueryTable.setRowHeight(35);

        TableCellRenderer buttonRenderer = new ButtonRenderer();
        informationQueryTable.getColumn("编辑").setCellRenderer(buttonRenderer);
        informationQueryTable.getColumn("编辑").setCellEditor(
         new InformationQueryButtonEditor(new JCheckBox()));
        informationQueryTable.getColumn("删除").setCellRenderer(buttonRenderer);
        informationQueryTable.getColumn("删除").setCellEditor(
         new InformationQueryButtonEditor(new JCheckBox()));
        
		informationQueryTable.addMouseListener(new DoubleClickModifyDelete());
		informationQueryTable.setColumnSelectionAllowed(false);

		
		//Scroll implement
		JScrollPane scrollPane = new JScrollPane(informationQueryTable);
		scrollPane.setBounds(0, 0, 600, 350);

		informationService.add(scrollPane);
		
    	//The following code is about the "Information Import" bar.
		JPanel informationImportPanel = new JPanel();
		informationImportPanel.setLayout(new BoxLayout(informationImportPanel,BoxLayout.Y_AXIS));
		tabbedPane.addTab("信息录入", null, informationImportPanel, null);
		
		

		

		JPanel informationImportContentPanel =new JPanel();
		informationImportContentPanel.setLayout(new FlowLayout(50));
		
		JPanel informationImportLeftContentPanel =new JPanel(new SpringLayout());
		JPanel informationImportRightContentPanel =new JPanel(new SpringLayout());
		
		JLabel lblStudentCardID = new JLabel("一卡通号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentCardID);
		textStudentCardID = new JTextField();
		textStudentCardID.setColumns(15);
		lblStudentCardID.setLabelFor(textStudentCardID);
		informationImportLeftContentPanel.add(textStudentCardID);
		
		JLabel lblStudentNumber = new JLabel("学号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentNumber);
		textStudentNo = new JTextField();
		textStudentNo.setColumns(15);
		lblStudentNumber.setLabelFor(textStudentNo);
		informationImportLeftContentPanel.add(textStudentNo);
		
		JLabel lblStudentname = new JLabel("学生姓名",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentname);
		textStudentName = new JTextField();
		textStudentName.setColumns(10);
		lblStudentname.setLabelFor(textStudentName);
		informationImportLeftContentPanel.add(textStudentName);
		
		JLabel lblEnrollmenttime = new JLabel("入学年份",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblEnrollmenttime);
		textEnrollTime = new JFormattedTextField();
		textEnrollTime.setText("2010");
		lblEnrollmenttime.setLabelFor(textEnrollTime);
		informationImportRightContentPanel.add(textEnrollTime);
		
		JLabel lblSpecialty = new JLabel("专业",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblSpecialty);
		SpecialitySelection = new JComboBox();
		SpecialitySelection.setModel(new DefaultComboBoxModel(majors));
		SpecialitySelection.setMaximumRowCount(15);
		lblSpecialty.setLabelFor(SpecialitySelection);
		informationImportRightContentPanel.add(SpecialitySelection);
		
		JLabel lblSex = new JLabel("性别",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblSex);
		rdbtnFemale = new JRadioButton("女");
		rdbtnMale = new JRadioButton("男");
		ButtonGroup bg=new ButtonGroup();
		bg.add(rdbtnFemale);
		bg.add(rdbtnMale);
		JPanel genderPanel=new JPanel(new FlowLayout());
		genderPanel.add(rdbtnFemale);
		genderPanel.add(rdbtnMale);
		lblSex.setLabelFor(genderPanel);
		informationImportRightContentPanel.add(genderPanel);

		SpringUtilities.makeCompactGrid(informationImportLeftContentPanel, 3, 2, 30, 20, 6, 26);
		SpringUtilities.makeCompactGrid(informationImportRightContentPanel, 3, 2, 30, 20, 6, 22);
		informationImportContentPanel.add(informationImportLeftContentPanel);
		informationImportContentPanel.add(informationImportRightContentPanel);

		
		
		
		JPanel informationImportActionBar = new JPanel();
		informationImportActionBar.setLayout(new FlowLayout());
		
		JButton btnButton = new JButton("提交");
		informationImportActionBar.add(btnButton);
		JButton btnCancel = new JButton("清空");
		informationImportActionBar.add(btnCancel);
		informationImportActionBar.setBounds(200, 0, 100, 50);
		
		
		informationImportPanel.add(informationImportContentPanel);
		informationImportPanel.add(informationImportActionBar);
		frame.setVisible(true);

		search.addActionListener(new SearchLister());
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
			if(hmList!=null)
			{
				String information[][]=new String[hmList.size()][7];
				user_info_id=new String[hmList.size()];
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

				informationQueryTable.setModel(new DefaultTableModel(information,informationQueryTableHead));
			
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
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");
			// TODO 自动生成的方法存根
			for(int row=0,colModify=5,colDelete=6;row<informationQueryTable.getRowCount();row++)
			{	
				if(informationQueryTable.getSelectedRow() == row&&informationQueryTable.getSelectedColumn()==colModify)
				{
					//修改学生信息
					int result=JOptionPane.showConfirmDialog(null, "Modify confirmed?");
					if(result == 0)
					{
						HashMap<String,String> hm= new HashMap<>();
						ArrayList<HashMap<String, String>> hmList = new ArrayList<HashMap<String, String>>();
						hm.put("op", "modify_student");
						System.out.println((String)informationQueryTable.getValueAt(row, 0));
						hm.put("student_id",(String)informationQueryTable.getValueAt(row, 0));
						System.out.println((String)informationQueryTable.getValueAt(row, 1));
						hm.put("nname", (String)informationQueryTable.getValueAt(row, 1));
						System.out.println((String)informationQueryTable.getValueAt(row, 2));
						hm.put("gender",(String)informationQueryTable.getValueAt(row, 2));
						System.out.println((String)informationQueryTable.getValueAt(row, 3));
						hm.put("grade", (String)informationQueryTable.getValueAt(row, 3));
						System.out.println((String)informationQueryTable.getValueAt(row, 4));
						hm.put("major", (String)informationQueryTable.getValueAt(row, 4));
						hm.put("user_info_id", user_info_id[row]);
						GUI.send(hm);
					    //Modify the information of student[row] in the database
						//TODO
						JOptionPane.showMessageDialog(null, "Modify successfully!","Modify Successfully",JOptionPane.PLAIN_MESSAGE);
					}
				}
				//删除学生信息
				else if(informationQueryTable.getSelectedRow() == row&&informationQueryTable.getSelectedColumn()==colDelete)
				{
					int result = JOptionPane.showConfirmDialog(null, "Delete confirmed?");
					if(result == 0)
					{					
						//delete the information of student[row] from database
						//TODO
						HashMap<String,String> hm= new HashMap<>();
						hm.put("student_id",(String)informationQueryTable.getValueAt(row, 0));
						hm.put("user_info_id", user_info_id[row]);
						hm.put("op", "delete_student");
						GUI.send(hm);
						JOptionPane.showMessageDialog(null, "Delete successfully!","Delete Successfully",JOptionPane.PLAIN_MESSAGE);
						DefaultTableModel dtm=(DefaultTableModel)informationQueryTable.getModel();
						dtm.removeRow(row);	
						dtm.fireTableDataChanged();
					}
				}
//				}	
			}
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
	class InformationQueryButtonEditor extends ButtonEditor{

		public InformationQueryButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			// TODO Auto-generated constructor stub
		}
		
	}
}


