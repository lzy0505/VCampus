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
 /**
  *<p>StudentAffairs</p>
  * <p>学生课程管理类<br>
  * 学生查询成绩、选课
  * </p>
  * @author 叶鑫、刘宗源、李子厚
  */
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
	CourseSelectJTable selectCourseTable=null;
	JTable examArrangementTable=null;
	JTable scoreQueryTable=null;
	//tab
	public JTabbedPane tabbedPane=null;
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
  				
		tabbedPane.addChangeListener(new changeTabListener());

 	}
 	

 	 /**
 	  * <p>更新学生课程，绘制界面<br>
 	  * </p>
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
 	 /**
	  * <p>界面切换方法<br>
	  *	切换到学生的课程情况界面，从数据库获取信息
	  * </p>
	  */
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
	 /**
	  * <p>课程选择界面<br>
	  *	界面绘制
	  * </p>
	  */
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
	 /**
	  * <p>选择课程响应函数<br>
	  *	学生选课，修改数据库信息
	  * </p>
	  */
 	class SelectCourseButtonEditor extends ButtonEditor{
 		int row;		
		public SelectCourseButtonEditor(JCheckBox checkBox) {
			super(checkBox);
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
 	 /**
	  * <p>选择老师响应函数<br>
	  *	学生选具体老师的课，修改数据库信息
	  * </p>
	  */
	class SelectTeacherButtonEditor extends ButtonEditor{
			int row;		
			public SelectTeacherButtonEditor(JCheckBox checkBox) {
				super(checkBox);
	
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
	 /**
	  * <p>选择老师响应函数<br>
	  *	学生选具体老师的课，修改数据库信息
	  * </p>
	  */
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
	
	
	class changeTabListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent arg0) {
			updateStudentCourse();
		}
		
	}
	
}