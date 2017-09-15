package client;
 
 import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
 
 import javax.swing.JFrame;
 import java.awt.FlowLayout;
 import java.awt.Toolkit;
 
 import javax.swing.JPanel;
 import javax.swing.JLabel;
 import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.sun.javafx.stage.EmbeddedWindow;

import table_component.ButtonEditor;
import table_component.ButtonRenderer;

import javax.swing.BoxLayout;

//import client.GUI.ChooseCourseLister;
 
 import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
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
	JTable selectCourseTable=null;
	JTable examArrangementTable=null;
	JTable scoreQueryTable=null;
	//tab
	JTabbedPane tabbedPane=null;
	//已选课的信息（用于disable button）
	HashMap<String,String> selectedCourse=null;
	

 	
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
 		tabbedPane.setBounds(7, 21, 437, 251);
 		
 		updateTableContent();
 		
 		DefaultTableModel selectCourseTableModel= new DefaultTableModel();
 		selectCourseTableModel.setDataVector(selectCourseTableContent,selectCourseTableHead);
 		selectCourseTable=new JTable(selectCourseTableModel);
 		selectCourseTable.setFillsViewportHeight(true);
 		selectCourseTable.setRowHeight(40);

        TableCellRenderer buttonRenderer = new ButtonRenderer();
        selectCourseTable.getColumn("操作").setCellRenderer(buttonRenderer);
        selectCourseTable.getColumn("操作").setCellEditor(
         new SelectCourseButtonEditor(new JCheckBox()));	
	
 		JScrollPane selectCoursePane = new JScrollPane(selectCourseTable);
 		tabbedPane.addTab("选课", null, selectCoursePane, null);
 		//考试安排模块初始化
 		DefaultTableModel examArrangementTableModel=new DefaultTableModel();
 		examArrangementTableModel.setDataVector(examArrangementTableContent, examArrangementTableHead);
 		examArrangementTable=new JTable(examArrangementTableModel);
 		examArrangementTable.setFillsViewportHeight(true); 
 		examArrangementTable.setRowHeight(40);
 		
 		JScrollPane examArrangementPane = new JScrollPane(examArrangementTable);
 		tabbedPane.addTab("考试安排", null, examArrangementPane, null);
 		//成绩查询模块初始化
 		DefaultTableModel scoreQueryTableModel=new DefaultTableModel();
 		scoreQueryTableModel.setDataVector(scoreQueryTableContent, scoreQueryTableHead);
 		scoreQueryTable=new JTable(scoreQueryTableModel);
 		scoreQueryTable.setFillsViewportHeight(true);
 		scoreQueryTable.setRowHeight(40);
 		
 		JScrollPane scoreQueryPane = new JScrollPane(scoreQueryTable);
 		tabbedPane.addTab("成绩查询", null, scoreQueryPane, null); 		
  		

 		

 	}
 	
 	public void paint() {

 		JButton returnToHomeScreen = new JButton("返回");
 		returnToHomeScreen.addActionListener(new ReturnActionListener());
 		returnToHomeScreen.setPreferredSize(new Dimension(80,32));
 		JPanel mainPanel=new JPanel();
 		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
 		mainPanel.add(tabbedPane);
 		mainPanel.add(returnToHomeScreen);
 		homeScreen.G5.getContentPane().removeAll();
 		homeScreen.G5.setTitle("学生教务");
 		homeScreen.G5.getContentPane().add(mainPanel);
 		homeScreen.G5.getContentPane().repaint();
 		homeScreen.G5.getContentPane().revalidate();
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
 	 			selectCourseTableContent[i][2] = "Selected: " + csList.get(i).get("course_teacher");
 	 		}
 	 		else {
 	 			selectCourseTableContent[i][2] = "Unselected";
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

 	//tow
 	public void courseSelect(Object[][] selectTeacherTableContent)
 	{
 		courseDetails = new JFrame();
 		courseDetails.setSize(480,300);
 		courseDetails.setLayout(new FlowLayout());

 		Object[] selectTeacherTableHead=new Object[] {"教师姓名","课程时间","状态","操作"};

 		DefaultTableModel selectTeacherTableModel=new DefaultTableModel();
 		selectTeacherTableModel.setDataVector(selectTeacherTableContent, selectTeacherTableHead);
 		JTable selectTeacherTable= new JTable(selectTeacherTableModel);
 		selectTeacherTable.setFillsViewportHeight(true); 
        selectTeacherTable.getColumn("操作").setCellRenderer(new SelectTeacherButtonRenderer());
        selectTeacherTable.getColumn("操作").setCellEditor(
         new SelectTeacherButtonEditor(new JCheckBox()));	
 		JScrollPane selectTeacherPane = new JScrollPane(selectTeacherTable);
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
					button.setForeground(table.getBackground());
					button.setBackground(table.getBackground());
//					button.setEnabled(false);
					super.setIsPushed(false);
					System.out.println("Editor-disable!!!!");
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
		 			String result="Choosing course"+cohm.get("result")+"!";
		 			courseDetails.dispose();
		 			updateStudentCourse();
		 			tabbedPane.repaint();
		 	        tabbedPane.revalidate();
					JOptionPane.showMessageDialog(null, result,
		 					"Results",JOptionPane.INFORMATION_MESSAGE);
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
				setForeground(table.getSelectionBackground());
			    setBackground(UIManager.getColor("Button.background"));
			    System.out.println("Render-disable!!!!");
			}else {
				if (isSelected) {
			      setForeground(table.getSelectionForeground());
			      setBackground(table.getSelectionBackground());
			    } else {
			      setForeground(table.getForeground());
			      setBackground(UIManager.getColor("Button.background"));
			    }
			}
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
	}
	
	class ReturnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			homeScreen.paint();
		}
		
	}
}