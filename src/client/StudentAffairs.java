package client;
 
 import java.awt.EventQueue;
 
 import javax.swing.JFrame;
 import java.awt.FlowLayout;
 import java.awt.Toolkit;
 
 import javax.swing.JPanel;
 import javax.swing.JLabel;
 import javax.swing.SwingConstants;

import com.sun.javafx.stage.EmbeddedWindow;

//import client.GUI.ChooseCourseLister;
 
 import javax.swing.JButton;
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
 import java.net.Socket;
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class StudentAffairs {
 
 	private JFrame Welcome;
 	private ClientInfo clientInfo = new ClientInfo();
 	private String ci = clientInfo.getCi();
 	private String[] courseInfoId =null;
 	private ArrayList<HashMap<String,String>> csList =new ArrayList<HashMap<String,String>>();
 	private ArrayList<HashMap<String,String>> coList =new ArrayList<HashMap<String,String>>();
 	JLabel courseNameLabel[] = null;
 	/*public static void main(String[] args) {
 		EventQueue.invokeLater(new Runnable() {
 			public void run() {
 				try {
 					CourseSelecting window = new CourseSelecting();
 					window.Welcome.setVisible(true);
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 		});
 	}*/
 	//send message
 	
	 public void send(HashMap<String,String> sendmes){
		try {
			Client client = new Client();
			client.clientSocket = new Socket("localhost",8080);
		client.sendMessage(sendmes);
	} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//send and get message
	public HashMap<String, String> getOne(HashMap<String,String> sendmes){
		HashMap<String, String> getmes=null;
		try {
			Client client = new Client();
			client.clientSocket = new Socket("localhost",8080);
		client.sendMessage(sendmes);
		getmes = client.getMessage();
	} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getmes;
	}
	public ArrayList<HashMap<String,String>> getList(HashMap<String,String> sendmes){
		ArrayList<HashMap<String,String>> getmes=null;
		try {
			Client client = new Client();
			client.clientSocket = new Socket("localhost",8080);
		client.sendMessage(sendmes);
		getmes = client.getMessages();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getmes;
	}

 	
 	JButton choose[] = null;
 	
 	//For test*********************
 	/*public static void main(String[] args)
 	{
 		String c[]={"Physics"};
 		String cr[]={"3"};
 		String d[]={"hahahhahahah"};
 		int n=1;
 		
 		String c2[]={"Biology"};
 		String cr2[]={"4"};
 		String s[]={"90"};
 		
 		String c3[]={"Physics"};
 		String p[]={"j2_102"};
 		String t[]={"8:00"};
 		
 		new StudentAffairs(c,cr,d,1,c2,cr2,s,n,c3,p,t,n);
 	}*/
 	
 	public int getWidth(int frameWidth)
 	{
 		return(Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / 2;
 	}
 	
 	public int getHeight(int frameHeight)
 	{
 		return(Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / 2;
 	}
 	
 	
 	
 	public StudentAffairs(String courseName[],String credit[],String details[],int csSize,
 			String siCourseName[],String siCredit[],String score[],int siSize,
 			String eaCourseName[],String place[],String examTime[],int eaSize,String[] courseInfoId,ArrayList<HashMap<String,String>> csList) {
 		
 		
 		initialize(courseName,credit,details,csSize,
 				siCourseName,siCredit,score,siSize,
 				eaCourseName,place,examTime,eaSize,courseInfoId,csList);
 	}
 
 	/**
 	 * Initialize the contents of the frame.
 	 */
 	private void initialize(String courseName[],String credit[],String details[],int csSize,
 			String siCourseName[],String siCredit[],String score[],int siSize,
 			String eaCourseName[],String place[],String examTime[],int eaSize,String[] courseInfoId,ArrayList<HashMap<String,String>> csList) {
 		//the elements of CourseSelecting
 		this.courseInfoId = courseInfoId;
 		this.csList = csList;
 		JLabel courseNameLabel[] = new JLabel[csSize];
 		JLabel creditLabel[] = new JLabel[csSize];
 		JLabel detailsLabel[] = new JLabel[csSize];
 		JButton chooseButton[] = new JButton[csSize]; 
 		
 		//the elements of CourseSelecting
 		JLabel courseNameSILabel[] = new JLabel[siSize];
 		JLabel creditSILabel[] = new JLabel[siSize];
 		JLabel scoreLabel[] = new JLabel[siSize];
 		
 		//the elements of ExamArrangement
 		JLabel courseNameEALabel[] = new JLabel[eaSize];
 		JLabel placeLabel[] = new JLabel[eaSize];
 		JLabel examTimeLabel[] = new JLabel[eaSize];
 		
 		Welcome = new JFrame();
 		Welcome.setBounds(100, 100, 470, 300);
 		Welcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		Welcome.getContentPane().setLayout(null);
 		
 		JLabel Welcome_1 = new JLabel("Welcome!");
 		Welcome_1.setBounds(7, 6, 60, 16);
 		//Welcome.getContentPane().add(Welcome_1);
 		Welcome.add(Welcome_1);
 		
 		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
 		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
 		tabbedPane.setBounds(7, 21, 437, 251);
 		//Welcome.getContentPane().add(tabbedPane);
 		Welcome.add(tabbedPane);
 
 		JScrollPane CourseSelectPane = new JScrollPane();
 		tabbedPane.addTab("CourseSelect", null, CourseSelectPane, null);
 		CourseSelectPane.setLayout(null);
 		
 		JLabel CourseName = new JLabel("Course Name");
 		CourseName.setBounds(26, 6, 410, 16);
 		CourseSelectPane.add(CourseName);
 		
 		JLabel Credit = new JLabel("Credit");
 		Credit.setBounds(138, 6, 278, 16);
 		CourseSelectPane.add(Credit);
 		
 		JLabel Details = new JLabel("Details");
 		Details.setBounds(220, 6, 210, 16);
 		CourseSelectPane.add(Details);
 		
 		JLabel Operation = new JLabel("Operation");
 		Operation.setBounds(335, 6, 86, 16);
 		CourseSelectPane.add(Operation);
 
 		//Separator layout
 		JSeparator separator = new JSeparator();
 		separator.setOrientation(SwingConstants.VERTICAL);
 		separator.setBounds(310, 6,1,300);
 		CourseSelectPane.add(separator);
 		
 		JSeparator separator_1 = new JSeparator();
 		separator_1.setOrientation(SwingConstants.VERTICAL);
 		separator_1.setBounds(180, 6, 1, 300);
 		CourseSelectPane.add(separator_1);
 		
 		JSeparator separator_2 = new JSeparator();
 		separator_2.setOrientation(SwingConstants.VERTICAL);
 		separator_2.setBounds(126, 6, 1, 300);
 		CourseSelectPane.add(separator_2);
 		
 		JSeparator separator_3 = new JSeparator();
 		separator_3.setBounds(1, 22, 400, 1);
 		CourseSelectPane.add(separator_3);
 		
 		//Score Inquery part
 		JScrollPane ScoreInqueryPane = new JScrollPane();
 		tabbedPane.addTab("Score Inquery", null, ScoreInqueryPane, null);
 		ScoreInqueryPane.setLayout(null);
 		
 		JLabel courseNameSI = new JLabel("Course Name");
 		courseNameSI.setBounds(44, 6, 120, 16);
 		ScoreInqueryPane.add(courseNameSI);
 		
 		JLabel creditSI = new JLabel("Credit");
 		creditSI.setBounds(176, 6, 38, 16);
 		ScoreInqueryPane.add(creditSI);
 		
 		JLabel scoreSI = new JLabel("Score");
 		scoreSI.setBounds(244, 6, 71, 16);
 		ScoreInqueryPane.add(scoreSI);
 		
 		JSeparator separator_4 = new JSeparator();
 		separator_4.setBounds(15, 20, 330, 1);
 		ScoreInqueryPane.add(separator_4);
 		
 		JSeparator separator_5 = new JSeparator();
 		separator_5.setOrientation(SwingConstants.VERTICAL);
 		separator_5.setBounds(164, 6, 18, 205);
 		ScoreInqueryPane.add(separator_5);
 		
 		JSeparator separator_6 = new JSeparator();
 		separator_6.setOrientation(SwingConstants.VERTICAL);
 		separator_6.setBounds(218, 6, 18, 214);
 		ScoreInqueryPane.add(separator_6);
 		
 		JScrollBar scrollBar_1 = new JScrollBar();
 		scrollBar_1.setBounds(401, 0, 15, 199);
 		ScoreInqueryPane.add(scrollBar_1);
 		
 		//Exam Arrangement
 		JPanel ExamArrangementPane = new JPanel();
 		tabbedPane.addTab("Exam\tArrangement", null, ExamArrangementPane, null);
 		ExamArrangementPane.setLayout(null);
 		
 		JLabel courseNameEA = new JLabel("Course Name");
 		courseNameEA.setBounds(44, 6, 120, 16);
 		ExamArrangementPane.add(courseNameEA);
 		
 		JLabel placeEA = new JLabel("Place");
 		placeEA.setBounds(166, 6, 60, 16);
 		ExamArrangementPane.add(placeEA);
 		
 		JLabel lblExaminationTime = new JLabel("Examination time");
 		lblExaminationTime.setBounds(245, 6, 135, 16);
 		ExamArrangementPane.add(lblExaminationTime);
 		
 		
 		JSeparator separator_7 = new JSeparator();
 		separator_7.setBounds(30, 20, 330, 12);
 		ExamArrangementPane.add(separator_7);
 		
 		JSeparator separator_8 = new JSeparator();
 		separator_8.setOrientation(SwingConstants.VERTICAL);
 		separator_8.setBounds(140, 6, 18, 205);
 		ExamArrangementPane.add(separator_8);
 		
 		JSeparator separator_9 = new JSeparator();
 		separator_9.setOrientation(SwingConstants.VERTICAL);
 		separator_9.setBounds(230, 6, 18, 214);
 		ExamArrangementPane.add(separator_9);
 		
 		JScrollBar scrollBar_2 = new JScrollBar();
 		scrollBar_2.setBounds(401, 0, 15, 199);
 		ExamArrangementPane.add(scrollBar_2);
 		
 		JPanel panel_3 = new JPanel();
 		tabbedPane.addTab("New tab", null, panel_3, null);
 		
 		//add information into CourseSelectPane
 		for(int i=0;i<csSize;i++)
 		{
 			courseNameLabel[i] = new JLabel(courseName[i]);
 			creditLabel[i] = new JLabel(credit[i]);
 			detailsLabel[i] = new JLabel(details[i]);
 			chooseButton[i] = new JButton("choose");
 			
 			courseNameLabel[i].setBounds(26, 33+33*i, 135, 16);
 			creditLabel[i].setBounds(148, 33+33*i, 50, 16);
 			detailsLabel[i].setBounds(192, 33+33*i, 220, 16);
 			chooseButton[i].setBounds(320, 28+33*i, 90, 25);
 			
 			CourseSelectPane.add(courseNameLabel[i]);
 			CourseSelectPane.add(creditLabel[i]);
 			CourseSelectPane.add(detailsLabel[i]);
 			CourseSelectPane.add(chooseButton[i]);
 			
 			chooseButton[i].setName(i+"");
 			chooseButton[i].addActionListener(new SelectCourse());
 
 		}
 		
 		//add information into ScoreInqueryPane
 		for(int i=0;i<siSize;i++)
 		{
 			courseNameSILabel[i] = new JLabel(siCourseName[i]);
 			creditSILabel[i] = new JLabel(siCredit[i]);
 			scoreLabel[i] = new JLabel(score[i]);
 			
 			courseNameSILabel[i].setBounds(50, 33+33*i, 135, 16);
 			creditSILabel[i].setBounds(185, 33+33*i, 50, 16);
 			scoreLabel[i].setBounds(250,33+33*i, 100, 16);
 			
 			ScoreInqueryPane.add(courseNameSILabel[i]);
 			ScoreInqueryPane.add(creditSILabel[i]);
 			ScoreInqueryPane.add(scoreLabel[i]);
 		}
 		
 		//add information into ExamArrangement
 		for(int i=0;i<eaSize;i++)
 		{
 			courseNameEALabel[i] = new JLabel(eaCourseName[i]);
 			placeLabel[i] = new JLabel(place[i]);
 			examTimeLabel[i] = new JLabel(examTime[i]);
 			
 			courseNameEALabel[i].setBounds(50, 33+33*i, 135, 16);
 			placeLabel[i].setBounds(160, 33+33*i, 60, 16);
 			examTimeLabel[i].setBounds(270, 33+33*i, 100, 16);
 			
 			ExamArrangementPane.add(courseNameEALabel[i]);
 			ExamArrangementPane.add(placeLabel[i]);
 			ExamArrangementPane.add(examTimeLabel[i]);
 		}
 		
 		Welcome.setVisible(true);

 
 		//different teachers
 		//the elements of CourseDetails
 		JFrame CourseDetailsF;
 	 	JPanel[] coursePanel = null;
 	 	JLabel[] teacherNameLabel = null;
 	 	JLabel[] timetableLabel = null;
 	 	JLabel[] stateLabel = null;
 	 	JButton[] choose = null;
 		
 	 	HashMap<String,String> hm = new HashMap<>();
 	 	hm.put("Card_id", ci);
 	 	
 	 	
 	 	
 	 	
 	 	
 	 	
 	}
 	//tow
 	public void courseSelect(String teacherName[],String time[],String state[],int size)
 	{
 		JFrame CourseDetails = new JFrame();
 		CourseDetails.setSize(360,360);
 		CourseDetails.setLayout(new FlowLayout());
 		
 		JPanel coursePanel[] = new JPanel[size+1];
 		JLabel teacherNameLabel[] = new JLabel[size+1];
 		JLabel timetableLabel[] = new JLabel[size+1];
 		JLabel stateLabel[] = new JLabel[size+1];
 		choose = new JButton[size];
 		
 		//coursePanel[0].setLayout(null);
 		teacherNameLabel[0] = new JLabel("Teacher");
 		timetableLabel[0] = new JLabel("Time");
 		stateLabel[0] = new JLabel("State");
 		coursePanel[0]=new JPanel();
 		JLabel operation = new JLabel("Operation");
 		
 		coursePanel[0].add(teacherNameLabel[0]);
 		coursePanel[0].add(timetableLabel[0]);
 		coursePanel[0].add(stateLabel[0]);
 		coursePanel[0].add(operation);
 		CourseDetails.add(coursePanel[0]);
 		
 		for(int i=1;i<size+1;i++)
 		{	
 			coursePanel[i]=new JPanel();
 			coursePanel[i].setLayout(new FlowLayout());
 			teacherNameLabel[i] = new JLabel(teacherName[i-1]);
 			timetableLabel[i] = new JLabel(time[i-1]);
 			stateLabel[i] = new JLabel(state[i-1]);
 			choose[i-1] = new JButton("Choose");
 			choose[i-1].setName((i-1)+"");
 			coursePanel[i].add(teacherNameLabel[i]);
 			coursePanel[i].add(timetableLabel[i]);
 			coursePanel[i].add(stateLabel[i]);
 			coursePanel[i].add(choose[i-1]);
 			CourseDetails.add(coursePanel[i]);
 			
 			choose[i-1].addActionListener(new ChooseCourseLister());
 			if(stateLabel[i].getText().equals("full"))
 			{
 				choose[i-1].setEnabled(false);
 			}//if the course is full,the choose button can't be pressed
 			
 		}
 		CourseDetails.setLocation(getWidth(CourseDetails.getWidth()),getHeight(CourseDetails.getHeight()));
 		
 		CourseDetails.setVisible(true);
 		
 	}
 	
 	class SelectCourse implements ActionListener
 	{
 
 		@Override
 		public void actionPerformed(ActionEvent arg0) {
 			HashMap<String,String> cchm =new HashMap<String,String>();
 			JButton choosenBuntton=(JButton)arg0.getSource();
 			int index=Integer.parseInt(choosenBuntton.getName());
 			cchm.put("course_info_id", csList.get(index).get("course_info_id"));
 			cchm.put("op", "choose_course");
 			cchm.put("course_name",csList.get(index).get("course_name"));
 			cchm.put("course_record_id",csList.get(index).get("course_record_id"));
 			coList = getList(cchm);
 			int size = coList.size();
 			String teacherName[] = new String[size];
 			String time[] = new String[size];
 			String state[] = new String[size];
 			for(int i = 0;i<size;i++) {
 				teacherName[i]=coList.get(i).get("course_teacher");
 				time[i] = coList.get(i).get("course_time");
 				if(coList.get(i).get("course_is_full").equals("TRUE")) {
 					state[i] = "full";
 				}
 				else {
 					state[i] = "notfull";
 				}
 			} 			
 			//get information from database,and give value to teacherName[],time[],state[] and size
 			courseSelect(teacherName,time,state,size);
 		}
 		
 	}
 	
 	class ChooseCourseLister implements ActionListener
 	{
 		public void actionPerformed(ActionEvent arg0) {
 			
 			HashMap<String,String> cohm =new HashMap<String,String>();
 			JButton selectedButton=(JButton)arg0.getSource();
 			int i=Integer.parseInt(selectedButton.getName());
 			cohm.put("course_id", coList.get(i).get("course_id"));
 			cohm.put("course_record_id", coList.get(i).get("course_record_id"));
 			cohm.put("op", "choose_ok");
 			cohm = getOne(cohm);
 			String result=cohm.get("course_name")+" is chosen "+cohm.get("result")+"!";
			JOptionPane.showMessageDialog(null, result,
 					"Results",JOptionPane.INFORMATION_MESSAGE);
			
 		}
 		
 	}
 }