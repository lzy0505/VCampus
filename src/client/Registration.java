package client;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import client.Registration.DoubleClickModifyDelete;
import client.Registration.CancelLister;
import client.Registration.SubmitLister;
import client.StudentAffairs.ReturnActionListener;
import client.StudentAffairs.SelectCourseButtonEditor;
import table_component.ButtonEditor;
import table_component.ButtonRenderer;
import table_component.SpringUtilities;

import java.awt.Font;
import java.awt.GridLayout;

public class Registration {
	private JPanel mainPanel;
	private HomeScreen homeScreen;
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
	ScoreSubmitJTable importScoreTable=null;
	JRadioButton rdbtnFemale;
	JRadioButton rdbtnMale;
	JFormattedTextField textEnrollTime;
	JComboBox SpecialitySelection;
	
	JTextField textExamPlace=null;
	JTextField textExamTime=null;
	JComboBox chooseSubjectForScore =null;
	JComboBox chooseSubjectForExam =null;
	ArrayList<HashMap<String,String>> courseList=null;
	ArrayList<HashMap<String,String>> studentList=null;
	//Information table construction
	final String[] informationQueryTableHead= {"学号","姓名","性别","入学年份","专业","编辑","删除"};

	

	public Registration(String [][]data,HomeScreen hs) {
		homeScreen=hs;
		initialize(data);
		
	}

	private void initialize(String data[][]) {

		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(500,360));
		mainPanel=new JPanel();
		mainPanel.add(tabbedPane);
		JButton returnToHomeScreen = new JButton("返回");
 		returnToHomeScreen.addActionListener(new ReturnActionListener());
 		returnToHomeScreen.setPreferredSize(new Dimension(80,32));
		mainPanel.add(returnToHomeScreen);
		
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
		textStudentCardID.setColumns(10);
		lblStudentCardID.setLabelFor(textStudentCardID);
		informationImportLeftContentPanel.add(textStudentCardID);
		
		JLabel lblStudentNumber = new JLabel("学号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentNumber);
		textStudentNo = new JTextField();
		textStudentNo.setColumns(10);
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
		SpecialitySelection.setMaximumRowCount(10);
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

		SpringUtilities.makeCompactGrid(informationImportLeftContentPanel, 3, 2, 15, 40, 5, 26);
		SpringUtilities.makeCompactGrid(informationImportRightContentPanel, 3, 2, 15, 40, 5, 22);
		informationImportContentPanel.add(informationImportLeftContentPanel);
		informationImportContentPanel.add(informationImportRightContentPanel);

		
		
		
		JPanel informationImportActionBar = new JPanel();
		informationImportActionBar.setLayout(new FlowLayout());
		
		JButton btnButton = new JButton("提交");
		informationImportActionBar.add(btnButton);
		JButton btnCancel = new JButton("清空");
		informationImportActionBar.add(btnCancel);
		informationImportActionBar.setPreferredSize(new Dimension(200,100));
		
		
		informationImportPanel.add(informationImportContentPanel);
		informationImportPanel.add(informationImportActionBar);

		search.addActionListener(new SearchLister());
		btnButton.addActionListener(new SubmitLister());
		btnCancel.addActionListener(new CancelLister());
	
		
		//成绩录入
		
		JPanel ModifyBar = new JPanel();
		ModifyBar.setBounds(6, 32, 438, 440);
		tabbedPane.addTab("成绩录入", null, ModifyBar, null);
		ModifyBar.setLayout(new BoxLayout(ModifyBar,BoxLayout.Y_AXIS));
		
		
		
		JPanel coursePanel=new JPanel(new FlowLayout());
		JLabel lblSubject = new JLabel("课程:");
		coursePanel.add(lblSubject);

		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("op", "teacher_courselist");
		hm.put("card_id", ClientInfo.getCi());
		courseList=GUI.getList(hm);
		String[] courses=new String[courseList.size()];
		for(int i=0;i<courseList.size();i++) {
			courses[i]=courseList.get(i).get("course_name")+"-"+courseList.get(i).get("course_time");
		}
		chooseSubjectForScore = new JComboBox();
		chooseSubjectForScore.setModel(new DefaultComboBoxModel(courses));
		lblSubject.setLabelFor(chooseSubjectForScore);
		chooseSubjectForScore.setEditable(false);
		ChooseBoxActionListener cbalistener=new ChooseBoxActionListener();
		chooseSubjectForScore.addActionListener(cbalistener);

		coursePanel.add(chooseSubjectForScore);
		coursePanel.setPreferredSize(new Dimension(200,30));
		JButton submitScore=new JButton("上传分数");
		submitScore.addActionListener(new ScoreSubmitActionListener());
		coursePanel.add(submitScore);
				
		DefaultTableModel emptyTableModel=new DefaultTableModel();
		emptyTableModel.setDataVector(new Object[][] {}, new String[] {"学号","姓名","分数"});
		
		importScoreTable=new ScoreSubmitJTable(emptyTableModel);
		importScoreTable.setColumnSelectionAllowed(false);
		importScoreTable.setFillsViewportHeight(true);
		
		JScrollPane scrollPanel = new JScrollPane(importScoreTable);
		scrollPane.setBounds(0, 30, 600, 320);
		ModifyBar.add(coursePanel);
		ModifyBar.add(scrollPanel);
		cbalistener.actionPerformed(null);
		
		
		chooseSubjectForExam = new JComboBox();
		chooseSubjectForExam.setModel(new DefaultComboBoxModel(courses));
		chooseSubjectForExam.setEditable(false);
		ChooseBoxActionListener2 cbalistener2=new ChooseBoxActionListener2();
		chooseSubjectForExam.addActionListener(cbalistener2);
		
		JPanel arrangeExam=new JPanel(new SpringLayout());
		
		arrangeExam.add(lblSubject);
		lblSubject.setLabelFor(chooseSubjectForExam);
		arrangeExam.add(chooseSubjectForExam);
		
		JLabel lblExamTime = new JLabel("考试时间");
		arrangeExam.add(lblExamTime);
		textExamTime = new JTextField();
		lblExamTime.setLabelFor(textExamTime);
		arrangeExam.add(textExamTime);
		
		JLabel lblExamPlace = new JLabel("考试地点");
		arrangeExam.add(lblExamPlace);
		textExamPlace = new JTextField();
		lblExamTime.setLabelFor(textExamPlace);
		arrangeExam.add(textExamPlace);	
		
		SpringUtilities.makeCompactGrid(arrangeExam, 3, 2, 40, 50, 5, 16);
		arrangeExam.setPreferredSize(new Dimension(250,100));
		cbalistener2.actionPerformed(null);
		
		JButton submitArrangement=new JButton("提交");
		submitArrangement.addActionListener(new ExamSubmitActionListener());
		JPanel examArrangePanel = new JPanel();
		examArrangePanel.setLayout(new BoxLayout(examArrangePanel,BoxLayout.Y_AXIS));
		examArrangePanel.add(arrangeExam);
		examArrangePanel.add(submitArrangement);
		tabbedPane.addTab("考试安排", null, examArrangePanel, null);
	}
	
	public void paint() {
		homeScreen.G5.getContentPane().removeAll();
 		homeScreen.G5.setTitle("学生信息管理");
 		homeScreen.G5.getContentPane().add(mainPanel);
 		homeScreen.G5.getContentPane().repaint();
 		homeScreen.G5.getContentPane().revalidate();
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
//			System.out.println("mouseClicked");
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
	
	class ReturnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			homeScreen.paint();
		}
		
	}
	
	
	class ChooseBoxActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			HashMap<String,String>hm=new HashMap<String,String>();
			hm.put("op", "teacher_query_studentlist");
			hm.put("course_id", courseList.get(chooseSubjectForScore.getSelectedIndex()).get("course_id"));
			studentList=GUI.getList(hm);
			String[][] data=new String[studentList.size()][3];
			for(int i=0;i<studentList.size();i++) {
				data[i][0]=studentList.get(i).get("student_id");
				data[i][1]=studentList.get(i).get("nname");
				data[i][2]=studentList.get(i).get("course_score");
			}
			importScoreTable.setModel(new DefaultTableModel(data,new String[] {"学号","姓名","分数"}));
		}
	}

	class ChooseBoxActionListener2 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			textExamTime.setText(courseList.get(chooseSubjectForExam.getSelectedIndex()).get("course_exam_time"));
			textExamPlace.setText(courseList.get(chooseSubjectForExam.getSelectedIndex()).get("course_exam_place"));		
		}
	}
	
	
	class ScoreSubmitActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 
			int result=JOptionPane.showConfirmDialog(null, "确认上传？");
			if(result == 0)
			{
				for(int row=0;row<studentList.size();row++)
				{	
				//修改学生信息
					HashMap<String,String> hm= new HashMap<>();
					hm.put("op", "modify_score");
					hm.put("course_id", courseList.get(chooseSubjectForScore.getSelectedIndex()).get("course_id"));
					hm.put("course_student", studentList.get(row).get("course_student"));
					hm.put("course_score",(String)importScoreTable.getValueAt(row, 2));
					GUI.send(hm);
				    //Modify the information of student[row] in the database
					//TODO
				}
				JOptionPane.showMessageDialog(null, "上传成功！","上传成功",JOptionPane.PLAIN_MESSAGE);	
			}	
		}
	}
	
	class ExamSubmitActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 
			int result=JOptionPane.showConfirmDialog(null, "确认上传？");
			if(result == 0)
			{	
				HashMap<String,String> hm= new HashMap<>();
				hm.put("op", "teacher_modify_ExamInfo");
				hm.put("course_id", courseList.get(chooseSubjectForExam.getSelectedIndex()).get("course_id"));
				courseList.get(chooseSubjectForExam.getSelectedIndex()).replace("course_exam_time", (String)textExamTime.getText());
				courseList.get(chooseSubjectForExam.getSelectedIndex()).replace("course_exam_place", (String)textExamPlace.getText());
				hm.put("course_exam_time", (String)textExamTime.getText());
				hm.put("course_exam_place",(String)textExamPlace.getText());
				GUI.send(hm);
			    //Modify the information of student[row] in the database
				//TODO
				JOptionPane.showMessageDialog(null, "上传成功！","上传成功",JOptionPane.PLAIN_MESSAGE);	
			}	
		}
	}
	
	
	class ScoreSubmitJTable extends JTable{
		public ScoreSubmitJTable(DefaultTableModel tableModel) {
			// TODO Auto-generated constructor stub
			super(tableModel);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
		     switch (col) {
		         case 2:
		             return true;
		         default:
		             return false;
		      }
		}
	}


}


