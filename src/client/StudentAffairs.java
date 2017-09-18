package client;
 
 import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
 
 import javax.swing.JFrame;
 import java.awt.FlowLayout;
 import java.awt.Toolkit;
 
 import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
 import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.sun.javafx.stage.EmbeddedWindow;

import table_component.ButtonEditor;
import table_component.ButtonRenderer;
import table_component.SpringUtilities;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

//import client.GUI.ChooseCourseLister;
 
 import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
 import javax.swing.JScrollPane;
 import javax.swing.JScrollBar;
 import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.net.Socket;
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class StudentAffairs {
 
 	private HomeScreen homeScreen=null;
 	private String ci;
 	private ArrayList<HashMap<String,String>> csList =null;
 	private ArrayList<HashMap<String,String>> coList =new ArrayList<HashMap<String,String>>();
 	
 	
	private JTextField textStudentNo;
	private JTextField textStudentName;
	private JTextField textStudentCardID;
	JRadioButton rdbtnFemale;
	JRadioButton rdbtnMale;
	JTextField textEnrollTime;
	JComboBox SpecialitySelection;
	private String[] majors = {"建筑学院", "机械工程学院", "能源与环境学院", "信息科学与工程学院", 
				"土木工程学院", "电子科学与工程学院", "数学学院", "自动化学院", "计算机科学与工程学院", "物理系", "生物科学与医学工程学院", 
			"材料科学与工程学院", "人文学院", "经济管理学院", "电气工程学院", "外国语学院", "体育系", "化学化工学院", "交通学院", 
			"仪器科学与工程学院", "艺术学院", "法学院", "基础医学院", "公共卫生学院", "临床医学院", "吴健雄学院", "软件学院"};
 	JFrame courseDetails=null;
 	//tab表中的内容
 	Object[][] selectCourseTableContent=null;
	Object[][] scoreQueryTableContent=null;
	Object[][] examArrangementTableContent=null;
	//tab表头
	final Object[] selectCourseTableHead=new Object[]{"课程名称","学分","选课信息","操作"};
	final Object[] scoreQueryTableHead=new Object[] {"课程名称","学分","考试分数"};
	final Object[] examArrangementTableHead=new Object[] {"课程名称","考试地点","考试时间"};
	//tab表
	CourseSelectJTable selectCourseTable=null;
	JTable examArrangementTable=null;
	JTable scoreQueryTable=null;
	//tab
	public JTabbedPane tabbedPane=null;
	//已选课的信息（用于disable button）
	HashMap<String,String> selectedCourse=null;
	HashMap<String,String>isCompleted=null;
	

 	
 	public int getWidth(int frameWidth)
 	{
 		return(Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / 2;
 	}
 	
 	public int getHeight(int frameHeight)
 	{
 		return(Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / 2;
 	}
 	
 	public StudentAffairs(HomeScreen homeScreen) {
 		this.homeScreen=homeScreen;
 		
 		
 		
  		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
 		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

 		
 		
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
		textStudentCardID = new JTextField(ClientInfo.getCi());
		textStudentCardID.setEnabled(false);
		lblStudentCardID.setLabelFor(textStudentCardID);
		informationImportLeftContentPanel.add(textStudentCardID);
		
		JLabel lblStudentNumber = new JLabel("学号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentNumber);
		textStudentNo = new JTextField();

		lblStudentNumber.setLabelFor(textStudentNo);
		informationImportLeftContentPanel.add(textStudentNo);
		
		JLabel lblStudentname = new JLabel("姓名",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentname);
		textStudentName = new JTextField();
		textStudentName.setColumns((int)(HomeScreen.width/100));
		lblStudentname.setLabelFor(textStudentName);
		informationImportLeftContentPanel.add(textStudentName);
		
		JLabel lblEnrollmenttime = new JLabel("入学年份",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblEnrollmenttime);
		textEnrollTime = new JTextField();
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

		SpringUtilities.makeCompactGrid(informationImportLeftContentPanel, 3, 2, (int)(HomeScreen.width/8), (int)(HomeScreen.height/8),  (int)(HomeScreen.width/80), (int)(HomeScreen.height/10));
		SpringUtilities.makeCompactGrid(informationImportRightContentPanel, 3, 2, (int)(HomeScreen.width/8), (int)(HomeScreen.height/8),  (int)(HomeScreen.width/80), (int)(HomeScreen.height/10));
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
		btnButton.addActionListener(new SubmitLister());
		btnCancel.addActionListener(new CancelLister());
 		
 		updateTableContent();
 		
 		DefaultTableModel selectCourseTableModel= new DefaultTableModel();
 		selectCourseTableModel.setDataVector(selectCourseTableContent,selectCourseTableHead);
 		selectCourseTable=new CourseSelectJTable(selectCourseTableModel);
 		selectCourseTable.setFillsViewportHeight(true);
 		selectCourseTable.setRowHeight(40);

        TableCellRenderer buttonRenderer = new ButtonRenderer();
        selectCourseTable.getColumn("操作").setCellRenderer(buttonRenderer);
        selectCourseTable.getColumn("操作").setCellEditor(
         new SelectCourseButtonEditor(new JCheckBox()));	
 		JScrollPane selectCoursePane = new JScrollPane(selectCourseTable);
 		JPanel test1=new JPanel();
 		selectCoursePane.setPreferredSize(new Dimension((int)(HomeScreen.width*5/7),(int)(HomeScreen.height*4/5)));
 		test1.add(selectCoursePane);
 		tabbedPane.addTab("选课", null, test1, null);
 		//考试安排模块初始化
 		DefaultTableModel examArrangementTableModel=new DefaultTableModel();
 		examArrangementTableModel.setDataVector(examArrangementTableContent, examArrangementTableHead);
 		examArrangementTable=new JTable(examArrangementTableModel);
 		examArrangementTable.setFillsViewportHeight(true); 
 		examArrangementTable.setRowHeight(40);
 		examArrangementTable.setEnabled(false);
 		
 		JScrollPane examArrangementPane = new JScrollPane(examArrangementTable);
 		JPanel test2=new JPanel();
 		test2.add(examArrangementPane);
 		examArrangementPane.setPreferredSize(new Dimension((int)(HomeScreen.width*5/7),(int)(HomeScreen.height*5/6)));				
 		tabbedPane.addTab("考试安排", null, test2, null);
 		//成绩查询模块初始化
 		DefaultTableModel scoreQueryTableModel=new DefaultTableModel();
 		scoreQueryTableModel.setDataVector(scoreQueryTableContent, scoreQueryTableHead);
 		scoreQueryTable=new JTable(scoreQueryTableModel);
 		scoreQueryTable.setFillsViewportHeight(true);
 		scoreQueryTable.setRowHeight(40);
 		scoreQueryTable.setEnabled(false);
 		
 		JScrollPane scoreQueryPane = new JScrollPane(scoreQueryTable);
 		JPanel test3=new JPanel();
 		scoreQueryPane.setPreferredSize(new Dimension((int)(HomeScreen.width*5/7),(int)(HomeScreen.height*5/6)));
 		test3.add(scoreQueryPane);
 		tabbedPane.addTab("成绩查询", null, test3, null); 		
  		

		if(isCompleted.get("result").equals("false")) {
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(2, false);
			tabbedPane.setEnabledAt(3, false);
		}else {
			tabbedPane.remove(0);
			tabbedPane.setEnabledAt(0, true);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(2, true);
		}
		tabbedPane.addChangeListener(new changeTabListener());

 	}
 	

 	 	
 	/**
 	 * Update the contents of the frame.
 	 */
 	private void updateStudentCourse() {
 		updateTableContent();
 		DefaultTableModel selectCourseTableModel= new DefaultTableModel();
 		selectCourseTableModel.setDataVector(selectCourseTableContent,selectCourseTableHead);
 		selectCourseTable.setModel(selectCourseTableModel);
 		
        TableCellRenderer buttonRenderer = new ButtonRenderer();
        selectCourseTable.getColumn("操作").setCellRenderer(buttonRenderer);
        selectCourseTable.getColumn("操作").setCellEditor(
        new SelectCourseButtonEditor(new JCheckBox()));	
 
       
 		DefaultTableModel examArrangementTableModel=new DefaultTableModel();
 		examArrangementTableModel.setDataVector(examArrangementTableContent, examArrangementTableHead);
 		examArrangementTable.setModel(examArrangementTableModel); 
 		
 		
 		DefaultTableModel scoreQueryTableModel=new DefaultTableModel();
 		scoreQueryTableModel.setDataVector(scoreQueryTableContent, scoreQueryTableHead);
 		scoreQueryTable.setModel(scoreQueryTableModel);
 	}
	
	public void updateTableContent() {
		isCompleted=new HashMap<String,String>();
		isCompleted.put("op", "isCompleted");
		isCompleted.put("card_id",ClientInfo.getCi());
		isCompleted=GUI.getOne(isCompleted);
 		HashMap<String,String> hm = new HashMap<String,String>();
	 	hm.put("card_id", ClientInfo.getCi());
	 	hm.put("op", "search_course");
 		csList=GUI.getList(hm);
		
		int csSize = csList.size();
 	 	int scoreQueryCounter = 0;
		int examArrangemetCounter = 0;
		int[] scoreQueryCourseIndex=new int[csSize];
		int[] examArrangemetIndex=new int[csSize];
		
		selectCourseTableContent=new Object[csSize][4];


 	 	for(int i=0;i<csSize;i++) {
 	 		//获取所有选课信息
 	 		selectCourseTableContent[i][0] = csList.get(i).get("course_name");
 	 		selectCourseTableContent[i][1] = csList.get(i).get("course_credits");
 	 		if(csList.get(i).get("select_status").equals("TRUE")) {
 	 			selectCourseTableContent[i][2] = "已选： " + csList.get(i).get("course_teacher");
 	 		}
 	 		else {
 	 			selectCourseTableContent[i][2] = "未选";
 	 		}
 	 		selectCourseTableContent[i][3]="选课";
 	 		
 	 		//获取所有已选课程的考试分数
 	 		if(csList.get(i).get("select_status").equals("TRUE")&&csList.get(i).get("course_exam_status").equals("TRUE")) {
 	 	 		//score inquery need selected and student has tooken a exam
 	 			scoreQueryCourseIndex[scoreQueryCounter]=i;
 	 			scoreQueryCounter++;
 	 	 	}
 	 		
 	 		//获取所有未考试课程的考试信息
 	 		if(csList.get(i).get("select_status").equals("TRUE")&&csList.get(i).get("course_exam_status").equals("FALSE")) {	 			
 	 			//exam need selected and student has'n tooken a exam
 	 			examArrangemetIndex[examArrangemetCounter]=i;
 	 			examArrangemetCounter++;
 	 		}
 	 	}
		scoreQueryTableContent=new Object[scoreQueryCounter][3];
		examArrangementTableContent=new Object[examArrangemetCounter][3];
 	 	
 	 	for(int j=0;j<scoreQueryCounter;j++) {
 	 		scoreQueryTableContent[j][0] = csList.get(scoreQueryCourseIndex[j]).get("course_name");
			scoreQueryTableContent[j][1] = csList.get(scoreQueryCourseIndex[j]).get("course_credits");
			scoreQueryTableContent[j][2] = csList.get(scoreQueryCourseIndex[j]).get("course_score");
 	 	}
		for(int j=0;j<examArrangemetCounter;j++) {
		 	examArrangementTableContent[j][0] = csList.get(examArrangemetIndex[j]).get("course_name");
		 	examArrangementTableContent[j][1] = csList.get(examArrangemetIndex[j]).get("course_exam_place");
		 	examArrangementTableContent[j][2] = csList.get(examArrangemetIndex[j]).get("course_exam_time");
		}
	}

 	public void courseSelect(Object[][] selectTeacherTableContent)
 	{
 		courseDetails = new JFrame();
 		courseDetails.setTitle("课程选择");
 		courseDetails.setIconImage(new ImageIcon("logo.png").getImage());
 		courseDetails.setSize(new Dimension((int)(HomeScreen.width/2),(int)(HomeScreen.height)));
 		courseDetails.setLayout(new FlowLayout());

 		Object[] selectTeacherTableHead=new Object[] {"教师姓名","课程时间","状态","操作"};

 		DefaultTableModel selectTeacherTableModel=new DefaultTableModel();
 		selectTeacherTableModel.setDataVector(selectTeacherTableContent, selectTeacherTableHead);
 		JTable selectTeacherTable= new JTable(selectTeacherTableModel);
 		selectTeacherTable.setFillsViewportHeight(true);
 		selectTeacherTable.setRowHeight(40);
        selectTeacherTable.getColumn("操作").setCellRenderer(new SelectTeacherButtonRenderer());
        selectTeacherTable.getColumn("操作").setCellEditor(
         new SelectTeacherButtonEditor(new JCheckBox()));	
 		JScrollPane selectTeacherPane = new JScrollPane(selectTeacherTable);
 		selectTeacherPane.setPreferredSize(new Dimension((int)(HomeScreen.width/2)-10,(int)(HomeScreen.height)-30));
 		courseDetails.add(selectTeacherPane);
 		courseDetails.setLocation(getWidth(courseDetails.getWidth()),getHeight(courseDetails.getHeight()));
 		courseDetails.setVisible(true);
 	
 	}

 	class SelectCourseButtonEditor extends ButtonEditor{
 		int row;		
		public SelectCourseButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			// TODO Auto-generated constructor stub
		}
		
		@Override public Component getTableCellEditorComponent(JTable table, Object value,
			      boolean isSelected, int row, int column) {
			this.row=row;
			return super.getTableCellEditorComponent(table, value, isSelected, row, column);
		}
		
		@Override public Object getCellEditorValue() {
			if (super.getIsPushed()) {
				selectedCourse=csList.get(row);
				HashMap<String,String> cchm =new HashMap<String,String>();
	 			cchm.put("course_info_id", csList.get(row).get("course_info_id"));
	 			cchm.put("op", "choose_course");
	 			cchm.put("course_name",csList.get(row).get("course_name"));
	 			cchm.put("course_record_id",csList.get(row).get("course_record_id"));
	 			coList = GUI.getList(cchm);
	 			int size = coList.size();
	 			Object[][] selectTeacherTableContent=new Object[size][4];
	 			for(int i = 0;i<size;i++) {
	 				selectTeacherTableContent[i][0]=coList.get(i).get("course_teacher");
	 				selectTeacherTableContent[i][1] = coList.get(i).get("course_time");
	 				if(coList.get(i).get("course_is_full").equals("TRUE")) {
	 					selectTeacherTableContent[i][2] = "已满";
	 				}
	 				else {
	 					selectTeacherTableContent[i][2]= "未满";
	 				}
	 				selectTeacherTableContent[i][3]= "选择";
	 			} 			
	 			//get information from database,and give value to teacherName[],time[],state[] and size
	 			
	 			courseSelect(selectTeacherTableContent);  
		    }
			return super.getCellEditorValue();
		  }
		 
 		
 	}
  
	class SelectTeacherButtonEditor extends ButtonEditor{
			int row;		
			public SelectTeacherButtonEditor(JCheckBox checkBox) {
				super(checkBox);
				// TODO Auto-generated constructor stub
			}
			
			@Override public Component getTableCellEditorComponent(JTable table, Object value,
				      boolean isSelected, int row, int column) {
				this.row=row;
				Component button=super.getTableCellEditorComponent(table, value, isSelected, row, column);
				if (table.getValueAt(row, column-1).equals("已满")||selectedCourse.get("select_status").equals("TRUE")&&selectedCourse.get("course_id").equals(coList.get(row).get("course_id"))) {
					button.setForeground(table.getSelectionForeground());
					button.setBackground(UIManager.getColor("Button.background"));
//					button.setEnabled(false);
					super.setIsPushed(false);
//					System.out.println("Editor-disable!!!!");
				}
				return button;
			}
			
			@Override public Object getCellEditorValue(){
				if (super.getIsPushed()) {
					HashMap<String,String> cohm =new HashMap<String,String>();
		 			cohm.put("course_id", coList.get(row).get("course_id"));
		 			cohm.put("course_record_id", coList.get(row).get("course_record_id"));
		 			cohm.put("op", "choose_ok");
		 			cohm = GUI.getOne(cohm);
		 			String result="选课"+cohm.get("result")+"！";
		 			courseDetails.dispose();
		 			updateStudentCourse();
		 			tabbedPane.repaint();
		 	        tabbedPane.revalidate();
					JOptionPane.showMessageDialog(null, result,
		 					"操作结果",JOptionPane.INFORMATION_MESSAGE);
			    }
				return super.getCellEditorValue();
			  }
	}
	
	class SelectTeacherButtonRenderer extends ButtonRenderer{
		public SelectTeacherButtonRenderer() {
			super();
		}
		@Override public Component getTableCellRendererComponent(JTable table, Object value,
			      boolean isSelected, boolean hasFocus, int row, int column) {
			if (table.getValueAt(row, column-1).equals("已满")||selectedCourse.get("select_status").equals("TRUE")&&selectedCourse.get("course_id").equals(coList.get(row).get("course_id"))) {  
				setForeground(table.getSelectionForeground());
			    setBackground(UIManager.getColor("Button.background"));
			    System.out.println("Render-disable!!!!");
			}else {
			      setForeground(table.getForeground());
			      setBackground(UIManager.getColor("Button.background"));
			}
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
	}
		
	class CourseSelectJTable extends JTable{
		public CourseSelectJTable(DefaultTableModel tableModel) {
			// TODO Auto-generated constructor stub
			super(tableModel);
		}
		@Override
		public boolean isCellEditable(int row, int col) {
		     switch (col) {
		         case 3:
		             return true;
		         default:
		             return false;
		      }
		}
	}
	
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
			
			isCompleted=new HashMap<String,String>();
			isCompleted.put("op", "isCompleted");
			isCompleted.put("card_id",ClientInfo.getCi());
			isCompleted=GUI.getOne(isCompleted);
	 		if(isCompleted.get("result").equals("false")) {
				tabbedPane.setEnabledAt(1, false);
				tabbedPane.setEnabledAt(2, false);
				tabbedPane.setEnabledAt(3, false);
			}else {
				tabbedPane.remove(0);
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setEnabledAt(2, true);
				tabbedPane.setEnabledAt(0, true);
			}
	 		
			
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
	class changeTabListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			updateStudentCourse();
		}
		
	}
	
}