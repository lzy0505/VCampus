package client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.lang.String;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

//implements ActionListener
public class GUI extends JFrame 
{
	private Client client;
	//private String[] book_id = null;
	private String ci; //card_id
	static JFrame G1;
	JPanel p11,p21,p31,p41;
	JLabel profession1,id1,password1;
	JComboBox pro1;
	JTextField i1;
	JPasswordField pass1;
	static JButton reg1;
	static JButton sign1;
	
	//the elements of G2
	static JFrame G2;
	JPanel p12,p22,p32,p42;
	JLabel id2,password2,ipassword2,l1,l2,l3;
	static JTextField i2;
	static JPasswordField pass2;
	static JPasswordField ipass2;
	static JButton reg2;
	
	//the elements of G3
	static JFrame G3;
	JPanel p13,p23;
	JLabel la3;
	static JButton close3;
	
	/*
	//the elements of G5
	static JFrame G5;
	JPanel p15,p25,p35;
	JLabel l15,l25,l35,l45,l55,l65;
	*/
	//the elements of G6
	static JFrame G6;
	JPanel p16,p26,p36;
	JLabel l16,l26,l36,l46,l56,l66;
	

 	
 	//HomeScreen、Library等成员变量
 	Bank bank;
 	Library library;
// 	StudentAffairs studentAffairs;
 	HomeScreen homeScreen;
	
	public void init(){
		
		try {
			client = new Client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	public static HashMap<String, String> getOne(HashMap<String,String> sendmes){
		HashMap<String, String> getmes=null;
		try {
			Client client = new Client();
			client.sendMessage(sendmes);
			getmes = client.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getmes;
	}
	public static ArrayList<HashMap<String,String>> getList(HashMap<String,String> sendmes){
		ArrayList<HashMap<String,String>> getmes=null;
		try {
			Client client = new Client();
			client.sendMessage(sendmes);
			getmes = client.getMessages();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getmes;
	}
	
	public static int getWidth(int frameWidth)
	{
		return(Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / 2;
	}
	
	public static int getHeight(int frameHeight)
	{
		return(Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / 2;
	}
	
	
	public GUI(String title1,String title2,String title3,String title4,String title5,String title6,
			String title7,String title8,String title10)
	{
		//the part of G1
		G1 = new JFrame(title1);
		Container c1 = G1.getContentPane();
		c1.setLayout(new BoxLayout(c1,BoxLayout.Y_AXIS));
		
		G1.setSize(360, 270);
		p11 = new JPanel();
		p11.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		profession1 = new JLabel("Identity");
		p11.add(profession1);
		
		pro1 = new JComboBox();
		pro1.addItem("Student");
		pro1.addItem("Teacher");
		p11.add(pro1);
		G1.add(p11);
		
		p21 = new JPanel();
		p21.setLayout(new FlowLayout(FlowLayout.CENTER));
		id1 = new JLabel("card_id");
		p21.add(id1);
		
		i1 = new JTextField("",8);
		p21.add(i1);
		G1.add(p21);
		
		p31 = new JPanel();
		p31.setLayout(new FlowLayout(FlowLayout.CENTER));
		password1 = new JLabel("Password");
		p31.add(password1);
		pass1 = new JPasswordField("",8);
		p31.add(pass1);
		G1.add(p31);
		
		p41 = new JPanel();
		p41.setLayout(new FlowLayout(FlowLayout.CENTER));
		reg1 = new JButton("Sign Up");
		sign1 = new JButton("Sign In");
		p41.add(reg1);
		p41.add(sign1);	
		G1.add(p41);
		
		G1.setLocation(getWidth(G1.getWidth()),getHeight(G1.getHeight()));
		//put this frame into the center of the screen
		
		G1.setVisible(true);
		//G1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//the part of G2
		G2 = new JFrame(title2);
		G2.setSize(360,270);
		Container c2 = G2.getContentPane();
		c2.setLayout(new BoxLayout(c2,BoxLayout.Y_AXIS));
		
		p12 = new JPanel();
		p12.setLayout(new FlowLayout(FlowLayout.CENTER));
		id2 = new JLabel("card_id");
		p12.add(id2);
		i2 = new JTextField("",8);
		p12.add(i2);
		l1 = new JLabel("(less than 10 characters)");
		p12.add(l1);
		G2.add(p12);
		
		p22 = new JPanel();
		p22.setLayout(new FlowLayout(FlowLayout.CENTER));
		password2 = new JLabel("Password");
		pass2 = new JPasswordField("",8);
		p22.add(password2);
		p22.add(pass2);
		l2 = new JLabel("(6 to 16 characters)");
		p22.add(l2);
		G2.add(p22);
		
		p32 = new JPanel();
		p32.setLayout(new FlowLayout(FlowLayout.CENTER));
		ipassword2 = new JLabel("Comfirm password");
		ipass2 = new JPasswordField("",8);
		p32.add(ipassword2);
		p32.add(ipass2);
		l3 = new JLabel("(6 to 16 characters)");
		p32.add(l3);
		G2.add(p32);
		
		p42 = new JPanel();
		p42.setLayout(new FlowLayout(FlowLayout.CENTER));
		reg2 = new JButton("Sign Up");
		p42.add(reg2);
		G2.add(p42);
		
		G2.setLocation(getWidth(G2.getWidth()),getHeight(G2.getHeight()));
		
		//the part of G3
		G3 = new JFrame(title3);
		G3.setSize(200,130);
		G3.setLayout(new GridLayout(2,1));
		la3 = new JLabel("Registered successfully!");
		p13 = new JPanel();
		p13.setLayout(new FlowLayout(FlowLayout.CENTER));
		p13.add(la3);
		G3.add(p13);
		
		close3 = new JButton("Close");
		p23 = new JPanel();
		p23.setLayout(new FlowLayout(FlowLayout.CENTER));
		p23.add(close3);
		G3.add(p23);
		
		G3.setLocation(getWidth(G3.getWidth()),getHeight(G3.getHeight()));

	/*	
		//the part of G5
		G5 = new JFrame(title5);
		G5.setSize(300,300);
		G5.setLayout(new FlowLayout());
		p15 = new JPanel();
		p15.setLayout(new GridLayout(1,2,150,0));
		l15 = new JLabel("Welcome!");
		p15.add(l15);
		l25 = new JLabel("Sign out");
		p15.add(l25);
		G5.add(p15);
		
		l35 = new JLabel("Library",new ImageIcon("library.png"),JLabel.LEFT);
		l35.setHorizontalTextPosition(JLabel.CENTER);
		l35.setVerticalTextPosition(JLabel.BOTTOM);
		p25 = new JPanel();
		p25.setLayout(new FlowLayout(FlowLayout.CENTER));
		p25.add(l35);
		
		l45 = new JLabel("Store",new ImageIcon("store.png"),JLabel.LEFT);
		l45.setHorizontalTextPosition(JLabel.CENTER);
		l45.setVerticalTextPosition(JLabel.BOTTOM);
		p25.add(l45);
		G5.add(p25);
		
		l55 = new JLabel("Courses",new ImageIcon("courses.png"),JLabel.LEFT);
		l55.setHorizontalTextPosition(JLabel.CENTER);
		l55.setVerticalTextPosition(JLabel.BOTTOM);
		p35 = new JPanel();
		p35.setLayout(new FlowLayout(FlowLayout.CENTER));
		p35.add(l55);
		
		l65 = new JLabel("Bank",new ImageIcon("bank.png"),JLabel.LEFT);
		l65.setHorizontalTextPosition(JLabel.CENTER);
		l65.setVerticalTextPosition(JLabel.BOTTOM);
		p35.add(l65);
		G5.add(p35);		
		G5.setLocation(getWidth(G5.getWidth()),getHeight(G5.getHeight()));
		
		*/
		
		
		//the part of G6
		G6 = new JFrame(title6);
		G6.setSize(300,300);
		G6.setLayout(new FlowLayout());
		p16 = new JPanel();
		p16.setLayout(new GridLayout(1,2,150,0));
		l16 = new JLabel("Welcome!");
		p16.add(l16);
		l26 = new JLabel("Sign out");
		p16.add(l26);
		G6.add(p16);
				
		l36 = new JLabel("Library",new ImageIcon("library.png"),JLabel.LEFT);
		l36.setHorizontalTextPosition(JLabel.CENTER);
		l36.setVerticalTextPosition(JLabel.BOTTOM);
		p26 = new JPanel();
		p26.setLayout(new FlowLayout(FlowLayout.CENTER));
		p26.add(l36);
				
		l46 = new JLabel("Store",new ImageIcon("store.png"),JLabel.LEFT);
		l46.setHorizontalTextPosition(JLabel.CENTER);
		l46.setVerticalTextPosition(JLabel.BOTTOM);
		p26.add(l46);
		G6.add(p26);
				
		
		l56 = new JLabel("Courses",new ImageIcon("studentMessage.png"),JLabel.LEFT);
		l56.setHorizontalTextPosition(JLabel.CENTER);
		l56.setVerticalTextPosition(JLabel.BOTTOM);
		p36 = new JPanel();
		p36.setLayout(new FlowLayout(FlowLayout.CENTER));
		p36.add(l56);
				
		l66 = new JLabel("Bank",new ImageIcon("bank.png"),JLabel.LEFT);
		l66.setHorizontalTextPosition(JLabel.CENTER);
		l66.setVerticalTextPosition(JLabel.BOTTOM);
		p36.add(l66);
		G6.add(p36);
		
		G6.setLocation(getWidth(G6.getWidth()),getHeight(G6.getHeight()));
		
		/*
		//the part of G8
		G8 = new JFrame(title8);
		G8.setSize(300, 150);
		G8.setLayout(new FlowLayout());
		
		ways = new JComboBox();
		ways.addItem("Author");
		ways.addItem("BookName");
		p18 = new JPanel();
		p18.setLayout(new FlowLayout(FlowLayout.CENTER));
		p18.add(ways);
		
		t18 = new JTextField("",8);
		p18.add(t18);
		G8.add(p18);
				
		b18 = new JButton("Search");
		b28 = new JButton("Return Book");
		p28 = new JPanel();
		p28.setLayout(new FlowLayout(FlowLayout.CENTER));
		p28.add(b18);
		p28.add(b28);
		G8.add(p28);		
		G8.setLocation(getWidth(G8.getWidth()),getHeight(G8.getHeight()));
		*/
		//调用Load类初始化所有类变量
		new Load();
		
		
		//events and reaction
		reg1.addActionListener(new MyActLister1());//sign up int the first GUI
		reg2.addActionListener(new MyActLister2());//the GUI of sign up
		//b28.addActionListener(new MyActLister3());//turn to the GUI ReturnBook
		close3.addActionListener(new MyActLister4());//close this GUI's window
		sign1.addActionListener(new MyActLister5());//if successful,sign in to the correct GUI
		//l35.addMouseListener(new MyMouLister1());//open library(student)
		//暂且注释l36.addMouseListener(new MyMouLister1());//open library(teacher)
		//l55.addMouseListener(new MyMouLister2());//open student affairs
		//b18.addActionListener(new SearchBookFromDB());//search book
		l56.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				new Registration(new String [][]{
				{"09015101","Mary","Female","201509","CSE","Modify","Delete"},//new integer test
				{"09015102","Kate","Female","201509","CSE","Modify","Delete"},
				{"09015103","Lili","Female","201509","CSE","Modify","Delete"},
				{"09015104","Amy","Female","201509","CSE","Modify","Delete"}
		});
			}
		});
		
		//return10.addActionListener(new MyActLister6());//if return10 is clicked,return to G8
		
		pro1.addItemListener(new MyItemLister1());
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException 
	{  try{//定义界面风格
		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
	  }catch(UnsupportedLookAndFeelException e)
	    {
		  
	    }
		GUI gui= new GUI("Sign In","Sign Up","Success","Fail","Student","Teacher",
				"Sign in failed","Library","Search unsuccessful");
		//gui.init();
	
	}

	//---------------------------------
	//@初始化类-------------------------
		 class Load{
			 
			 Load()
			 {  			    		 
				 //初始化时注意先后顺序
				loadHomeScreen();
				loadBank();
				loadLibrary();
//				loadStudentAffairs();
			 }
			 //初始化银行类
			 void loadBank()
			 {  
				 bank=new Bank(homeScreen);
				 bank.init();
			 }
			 //初始化主界面类
		     void loadHomeScreen()
		     {
		    	 homeScreen=new HomeScreen();	
		    	 homeScreen.init();		    	    	 
		     }
		     void loadLibrary()
		     {
		    	 library=new Library(homeScreen);
		    	 library.init();
	    	 
		     }
//		     void loadStudentAffairs()
//		     {
//		    	 studentAffairs=new StudentAffairs(homeScreen); 	 
//		     }
		    
		     //调用主界面的update函数将Library、Bank等变量传入
		
		     
	   
		     
		 }
		 //@初始化类结束--------------------------
		 //--------------------------------------
	

	class MyActLister1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			G2.setVisible(true);
			//G2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//*************
		}
	}
	
	class MyItemLister1 implements ItemListener
	{
		public void itemStateChanged(ItemEvent e) {
			//if the person is a teacher,then the sign up button is not able to be pressed
			if(e.getSource() == pro1 && pro1.getSelectedIndex()==1)
			{
				reg1.setEnabled(false);
			}
			if(e.getSource() == pro1 && pro1.getSelectedIndex()==0)
			{
				reg1.setEnabled(true);
			}
		}
		
	}
	//注册界面
	class MyActLister2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			
			if(i2.getText().length()<11 & (pass2.getText().length()>5) & (pass2.getText().length()<17)& (pass2.getText().equals(ipass2.getText())))
			{
				
				HashMap<String,String> hm=new HashMap<String,String>();
					
				hm.put("card_id",i2.getText());
				hm.put("password",pass2.getText());
				hm.put("op", "sign up");
				if(pro1.getSelectedIndex()==0){
					hm.put("identity","student");
				}else{
					hm.put("identity","teacher");
				}
			    
				System.out.println(hm.get("card_id"));
				System.out.println(hm.get("password"));	
				hm=getOne(hm);
				//System.out.println(hm.get("result"));
				if(hm.get("result").equals("success"))
				{
					G3.setVisible(true);
				}
				else if(hm.get("result").equals("fail"))
				{
					JOptionPane.showMessageDialog(G1,"User's name or password is unvalid!",
							"Sign up unsuccessfully",JOptionPane.ERROR_MESSAGE);

				}
			}
			else
			{
				JOptionPane.showMessageDialog(G1,"User's name or password is unvalid!",
						"Sign up unsuccessfully",JOptionPane.ERROR_MESSAGE);

			}
		}
		
	}

	class MyActLister4 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			G3.setVisible(false);
			G2.setVisible(false);
			//G7.setVisible(false);
		}
		
	}
	//点击登录按钮, sign in
	class MyActLister5 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			
			/*if() go to find whether there is a person(student/teacher) in our database,if 
			 * yes,it will turn to G5(student)/G6(teacher),or it will turn to G7(Failed sign in)
			
	*/
			HashMap<String,String> hm=new HashMap<String,String>();
			
			hm.put("card_id",i1.getText());
			hm.put("password",pass1.getText());
			hm.put("op", "sign in");
			if(pro1.getSelectedIndex()==0){
				hm.put("identity","student");
			}else{
				hm.put("identity","teacher");
			}
			//send hm to server and get it back, check the result 
		    hm=getOne(hm);
		    if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==0)
		    {
		    	ClientInfo.setCi(hm.get("card_id"));
		    	ci =ClientInfo.getCi() ;//un is used to identify user,a global variable
		    	//G5.setVisible(true);
		    	homeScreen.G5.setVisible(true);
		    	homeScreen.update(library, bank, ci);
		    	System.out.println("update card_id to : homeScreen!");
		    	bank.update(ci);
		    	library.update(ci);
		    	ClientInfo.setCi(ci);
//		    	studentAffairs.update(ci);
		    	System.out.println("update card_id to : bank!");
		    	G1.setVisible(false);
		    	//G5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	
		    }
		    else if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==1)
		    {
		    	ClientInfo.setCi(hm.get("card_id"));
		    	ci =ClientInfo.getCi();//ci is used to identify user,a global variable
		    	G6.setVisible(true);
		    	G1.setVisible(false);
		    	//G6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    		
		    }
		    else if(hm.get("result").equals("fail"))
		    {
		    	JOptionPane.showMessageDialog(null,"User's name or password is incorrect!",
						"Sign in unsuccessfully",JOptionPane.ERROR_MESSAGE);
		    	
		    }
			
			
	//TODO
			
		}
		
	}

}

