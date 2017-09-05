package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.IOException;
import java.lang.String;
import java.net.Socket;
import java.util.HashMap;

//implements ActionListener
public class GUI extends JFrame 
{
	private Client client;
	private String un=null;
	//the elements of G1
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
	
	//the elements of G4
	static JFrame G4;
	JPanel p14,p24;
	JLabel la4;
	static JButton return4;
	
	//the elements of G5
	static JFrame G5;
	JPanel p15,p25,p35;
	JLabel l15,l25,l35,l45,l55,l65;
	
	//the elements of G6
	static JFrame G6;
	JPanel p16,p26,p36;
	JLabel l16,l26,l36,l46,l56,l66;
	
	//the elements of G7
	static JFrame G7;
	JPanel p17,p27;
	JLabel la7;
	static JButton close7;
	
	//the elements of G8
	static JFrame G8;
 	JPanel p18,p28;
 	JLabel l18,l28;
 	JComboBox ways;
 	JTextField t18;
 	JButton b18,b28;
 	
 	//the elements of SearchBook9th
 	static JFrame SearchBook9th;
 	JPanel p19,p29,p39,p49,p59,p69;//mostly 5 messages at present
 	JLabel bookName1,bookName2,bookName3,bookName4,bookName5,press1,press2,press3,press4,press5;//press is publishing house
 	JLabel bookNumber1,bookNumber2,bookNumber3,bookNumber4,bookNumber5;
 	JCheckBox check1,check2,check3,check4,check5;//which book to choose
 	JButton borrow;
	
	public void init(){
		
		try {
			client = new Client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(HashMap<String,String> sendmes){
		try {
			Client client = new Client();
			client.clientSocket = new Socket("localhost",8080);
			client.SendMessage(sendmes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<String, String> get(HashMap<String,String> sendmes){
		HashMap<String, String> getmes=new HashMap<String,String>();
		try {
			Client client = new Client();
			client.clientSocket = new Socket("localhost",8080);
			client.SendMessage(sendmes);
			getmes = client.GetMessage();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getmes;
	}
	
	
	public GUI(String title1,String title2,String title3,String title4,String title5,String title6,String title7,String title8)
	{
		//the part of G1
		G1 = new JFrame(title1);
		Container c1 = G1.getContentPane();
		c1.setLayout(new BoxLayout(c1,BoxLayout.Y_AXIS));
		
		G1.setSize(250, 250);
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
		id1 = new JLabel("Username");
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
		
		G1.setVisible(true);
		G1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//the part of G2
		G2 = new JFrame(title2);
		G2.setSize(360,270);
		Container c2 = G2.getContentPane();
		c2.setLayout(new BoxLayout(c2,BoxLayout.Y_AXIS));
		
		p12 = new JPanel();
		p12.setLayout(new FlowLayout(FlowLayout.CENTER));
		id2 = new JLabel("Username");
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
		
		//the part of G4
		G4 = new JFrame(title4);
		G4.setSize(200,130);
		G4.setLayout(new GridLayout(2,1));
		la4 = new JLabel("Register failed!");
		p14 = new JPanel();
		p14.setLayout(new FlowLayout(FlowLayout.CENTER));
		p14.add(la4);
		G4.add(p14);
		
		return4 = new JButton("Return");
		p24 = new JPanel();
		p24.setLayout(new FlowLayout(FlowLayout.CENTER));
		p24.add(return4);
		G4.add(p24);
		
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
		
		//the part of G7
		G7 = new JFrame(title7);
		G7.setSize(200,130);
		G7.setLayout(new GridLayout(2,1));
		la7 = new JLabel("Sign in failed!");
		p17 = new JPanel();
		p17.setLayout(new FlowLayout(FlowLayout.CENTER));
		p17.add(la7);
		G7.add(p17);
				
		close7 = new JButton("Close");
		p27 = new JPanel();
		p27.setLayout(new FlowLayout(FlowLayout.CENTER));
		p27.add(close7);
		G7.add(p27);
		
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
		
		
		//events and reaction
		reg1.addActionListener(new MyActLister1());
		reg2.addActionListener(new MyActLister2());
		return4.addActionListener(new MyActLister3());
		close3.addActionListener(new MyActLister4());
		close7.addActionListener(new MyActLister4());
		sign1.addActionListener(new MyActLister5());
		b18.addActionListener(new MyActLister6());
		l36.addMouseListener(new MyMouLister1());
		b18.addActionListener(new SearchBookFromDB());
		
		pro1.addItemListener(new MyItemLister1());
	}
	
	//the method of creating SearchBook9th
	public void searchBook9th(String bN1,String pre1,int n1,
			String bN2,String pre2,int n2,
			String bN3,String pre3,int n3,
			String bN4,String pre4,int n4,
			String bN5,String pre5,int n5)
	{
		SearchBook9th = new JFrame("Search book");
		SearchBook9th.setSize(200, 300);
		SearchBook9th.setLayout(new FlowLayout());
		
		p19 = new JPanel();
		p19.setLayout(new FlowLayout());
		bookName1 = new JLabel(bN1);
		press1 = new JLabel(pre1);	
		if(n1>0)
		{
			bookNumber1 = new JLabel(""+n1);
			p19.add(bookNumber1);
		}
		if(!bN1.equals(""))
		{
			check1 = new JCheckBox();
			p19.add(check1);
		}//if the bookName is not empty,then show the checkbox
		p19.add(bookName1);
		p19.add(press1);
		SearchBook9th.add(p19);
		
		
		p29 = new JPanel();
		p29.setLayout(new FlowLayout());
		bookName2 = new JLabel(bN2);
		press2 = new JLabel(pre2);
		if(n2>0)
		{
			bookNumber2 = new JLabel(""+n2);
			p29.add(bookNumber2);
		}
		if(!bN2.equals(""))
		{
			check2 = new JCheckBox();
			p29.add(check2);
		}
		p29.add(bookName2);
		p29.add(press2);
		SearchBook9th.add(p29);
				
		p39 = new JPanel();
		p39.setLayout(new FlowLayout());
		bookName3 = new JLabel(bN3);
		press3 = new JLabel(pre3);
		if(n3>0)
		{
			bookNumber3 = new JLabel(""+n3);
			p39.add(bookNumber3);
		}
		if(!bN3.equals(null))
		{
			check3 = new JCheckBox();
			p39.add(check3);
		}
		p39.add(bookName3);
		p39.add(press3);
		SearchBook9th.add(p39);
			
		p49 = new JPanel();
		p49.setLayout(new FlowLayout());
		bookName4 = new JLabel(bN4);
		press4 = new JLabel(pre4);
		if(n4>0)
		{
			bookNumber4 = new JLabel(""+n4);
			p49.add(bookNumber4);
		}
		if(!bN4.equals(null))
		{
			check4 = new JCheckBox();
			p49.add(check4);
		}
		p49.add(bookName4);
		p49.add(press4);
		SearchBook9th.add(p49);
		
		p59 = new JPanel();
		p59.setLayout(new FlowLayout());
		bookName5 = new JLabel(bN5);
		press5 = new JLabel(pre5);
		if(n5>0)
		{
			bookNumber5 = new JLabel(""+n5);
			p59.add(bookNumber5);
		}
		if(!bN5.equals(null))
		{
			check5 = new JCheckBox();
			p59.add(check5);
		}
		p59.add(bookName5);
		p59.add(press5);
		SearchBook9th.add(p59);
		
		
		p69 = new JPanel();
		p69.setLayout(new FlowLayout(FlowLayout.CENTER));
		borrow = new JButton("Borrow");
		p69.add(borrow);
		SearchBook9th.add(p69);
		
		SearchBook9th.setVisible(true);
		
	}
	
	public static void main(String[] args) 
	{
		GUI gui= new GUI("Sign In","Sign Up","Success","Fail","Student","Teacher","Sign in failed","Library");
		//gui.init();
	
	}

	
	class MyMouLister1 implements MouseListener
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
	 		public void mousePressed(MouseEvent arg0) {
	 			
	 			G8.setVisible(true);
	 			
	 		}
	 
	 		@Override
	 		public void mouseReleased(MouseEvent arg0) {
	 			// TODO 自动生成的方法存根
	 			
	 		}
	 		
	 	}

	class MyActLister1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			G2.setVisible(true);
			G2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
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
					
				hm.put("username",i2.getText());
				hm.put("password",pass2.getText());
				hm.put("op", "sign up");
				if(pro1.getSelectedIndex()==0){
					hm.put("identity","student");
				}else{
					hm.put("identity","teacher");
				}
			    
				System.out.println(hm.get("username"));
				System.out.println(hm.get("password"));	
				hm=get(hm);
				//System.out.println(hm.get("result"));
				if(hm.get("result").equals("success"))
				{
				G3.setVisible(true);
				G3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				else if(hm.get("result").equals("fail"))
				{
					G4.setVisible(true);
					G4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
			else
			{
				G4.setVisible(true);
				G4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
		
	}
	
	class MyActLister3 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			G2.setVisible(true);
			G2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
	}
	
	class MyActLister4 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			G3.setVisible(false);
			G2.setVisible(false);
			G7.setVisible(false);
		}
		
	}
	//点击登录按钮
	class MyActLister5 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			
			/*if() go to find whether there is a person(student/teacher) in our database,if 
			 * yes,it will turn to G5(student)/G6(teacher),or it will turn to G7(Failed sign in)
			
	*/
			HashMap<String,String> hm=new HashMap<String,String>();
			
			hm.put("username",i1.getText());
			hm.put("password",pass1.getText());
			hm.put("op", "sign in");
			if(pro1.getSelectedIndex()==0){
				hm.put("identity","student");
			}else{
				hm.put("identity","teacher");
			}
		    hm=get(hm);
		    if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==0)
		    {
		    	G5.setVisible(true);
		    	G5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	
		    }
		    else if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==1)
		    {
		    	G6.setVisible(true);
		    	G6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    		
		    }
		    else if(hm.get("result").equals("fail"))
		    {
		    	G7.setVisible(true);
		    	G7.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    		
		    	
		    }
			
			
	//TODO
			
		}
		
	}
	
	class MyActLister6 implements ActionListener
	{
	 
	 	public void actionPerformed(ActionEvent arg0) 
	 	{
	 		/*if() find whether there is a book that client searching for in the database
	 			*if yes,G9.setVisable(true),or G10.setVisable(true)*/
	 			
	 	}
	 		
	 }
	
	class SearchBookFromDB implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String bN1 = "",bN2 = "",bN3 = "",bN4 = "",bN5 = "",
					pre1 = "",pre2 = "",pre3 = "",pre4 = "",pre5 = "";//bookName and press
			
			int n1 = 0,n2 = 0,n3 = 0,n4 = 0,n5 = 0;//n is bookNumber
			
			/*String bN1 = "bookTest1",bN2 = "bookTest2",bN3 = "bookTest3",bN4 = "bookTest4",
					bN5 = "bookTest5",pre1 = "pressTest1",pre2 = "pressTest2",pre3 = "pressTest3",
					pre4 = "pressTest4",pre5 = "pressTest5";
			
			int n1 = 10,n2 = 2,n3 = 3,n4 = 4,n5 = 5;*/
			//just test bookName and press,IF YOU WANT TO see this GUI's effect,undefined this
			//part of code
			
			/*search book from database,if there is a book in the database,return the book's message,which contains bN,pre and n
			 * attention: for there are at most 5 messages, if the messages can't be fullfiled,retun empty string or integer
			 *///TODO
			
			if(!(bN1.equals("")))
			{
				searchBook9th(bN1,pre1,n1,bN2,pre2,n2,bN3,pre3,n3,bN4,pre4,n4,bN5,pre5,n5);
			}//if the first book is not a null,then show all the message of this book
			else 
			{
				//show the GUI of searching unsuccessfully
				//TODO
			}
			
		}
		
	}
	

}


