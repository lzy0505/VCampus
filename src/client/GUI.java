package client;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
<<<<<<< HEAD
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import table_component.SpringUtilities;
=======

import java.io.File;
>>>>>>> refs/remotes/origin/database

import java.io.IOException;
import java.lang.String;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

//implements ActionListener
public class GUI extends JFrame 
{
	final static private double RATIO=0.33;
	
	private Client client;
	private String ci; //card_id
	static JFrame G1;
	JPanel p1,p41;
	JLabel profession1,id1,password1;
	JComboBox pro1;
	JTextField i1;
	JPasswordField pass1;
	static JButton reg1;
	static JButton sign1;
	
	//the elements of G2
	static JFrame G2;
	JPanel p2,p42;
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
	

	static JFrame G6;
	JPanel p16,p26,p36;
	JLabel l16,l26,l36,l46,l56,l66;
	

 	
 	//HomeScreen、Library等成员变量
 	Bank bank;
<<<<<<< HEAD
 	Library library;
=======
 	Store store;
>>>>>>> refs/remotes/origin/database
 	HomeScreen homeScreen;
 	HSAdmin hsAdmin;
	
	public void init(){
		
		try {
			client = new Client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//send message 可乐
	public static void send(HashMap<String,String> sendmes){
		try {
			Client client = new Client();
			client.clientSocket = new Socket("localhost",8080);
			client.sendMessage(sendmes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"无法连接到服务器",
					"致命的错误",JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null,"无法连接到服务器",
					"致命的错误",JOptionPane.ERROR_MESSAGE);
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
<<<<<<< HEAD
	//给新商品的名字、数量、路径、价格。返回一个HashMap，如果成功返回success，失败返回false和相应的原因。
	public static HashMap<String, String> upLoad(HashMap<String, String> hm) {
		HashMap<String, String> getmes=null;
		try {
			Client client = new Client();
			getmes = client.upLoad(hm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"无法连接到服务器",
					"致命的错误",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return getmes;
=======
	//给这个函数一个HashMap，里面装有操作和key，能给你返回服务器传到客户端的文件的路径数组。明明白白
	public static String[] getImage(HashMap<String,String> sendmes)throws IOException{
	
			Client client = new Client();
			String[] filename =client.getIcon(sendmes);
			 return filename;
>>>>>>> refs/remotes/origin/database
	}
	
	
	public static int getWidth(int frameWidth)
	{
		return(Toolkit.getDefaultToolkit().getScreenSize().width - frameWidth) / 2;
	}
	
	public static int getHeight(int frameHeight)
	{
		return(Toolkit.getDefaultToolkit().getScreenSize().height - frameHeight) / 2;
	}
	
	
	public GUI(String title1,String title2,String title3)
	{
		//the part of G1
		double width=Toolkit.getDefaultToolkit().getScreenSize().getWidth()*RATIO;
		double height=Toolkit.getDefaultToolkit().getScreenSize().getHeight()*RATIO;
		G1 = new JFrame(title1);
		G1.setSize((int)(width), (int)(height));
		G1.setResizable(false);
		G1.setUndecorated(true);
		G1.getLayeredPane().getComponent(1).setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		G1.setIconImage(new ImageIcon("logo.png").getImage());
		Container c1 = G1.getContentPane();
		c1.setLayout(new BoxLayout(c1,BoxLayout.Y_AXIS));
		
		
		p1 = new JPanel();
		JPanel bg=new JPanel();
		p1.setLayout(new SpringLayout());
		
		profession1 = new JLabel("用户身份",JLabel.RIGHT);
		p1.add(profession1);
		

		pro1 = new JComboBox();
		pro1.addItem("学生");
		pro1.addItem("教师");
		pro1.addItem("管理员");
		profession1.setLabelFor(pro1);
		p1.add(pro1);
		
		

		id1 = new JLabel("一卡通",JLabel.RIGHT);
		p1.add(id1);
		i1 = new JTextField("");
		id1.setLabelFor(i1);
		p1.add(i1);


		password1 = new JLabel("密码",JLabel.RIGHT);
		p1.add(password1);
		pass1 = new JPasswordField("");
		password1.setLabelFor(pass1);
		p1.add(pass1);
		SpringUtilities.makeCompactGrid(p1, 3, 2, 0, (int)(height/11), 10, (int)(height/11));
		
		p1.setPreferredSize(new Dimension((int)(width*3/5),(int)(height*7/11)));
		
		bg.add(p1);
		G1.add(bg);
		
		
		p41 = new JPanel();
		p41.setLayout(new FlowLayout(FlowLayout.CENTER));
		reg1 = new JButton("注册");
		sign1 = new JButton("登陆");
		p41.add(reg1);
		p41.add(sign1);	
		G1.add(p41);
		
		G1.setLocation(getWidth(G1.getWidth()),getHeight(G1.getHeight()));
		//put this frame into the center of the screen
		
		G1.setVisible(true);
		//G1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//the part of G2
		G2 = new JFrame(title2);
		G2.setIconImage(new ImageIcon("logo.png").getImage());
		G2.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*RATIO), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*RATIO));
		G2.getLayeredPane().getComponent(1).setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		Container c2 = G2.getContentPane();
		c2.setLayout(new BoxLayout(c2,BoxLayout.Y_AXIS));
		
		JPanel bg2=new JPanel();
		p2 = new JPanel();
		p2.setLayout(new SpringLayout());
		p2.setPreferredSize(new Dimension((int)(width*5/7),(int)(height*7/11)));
		id2 = new JLabel("一卡通",JLabel.RIGHT);
		p2.add(id2);
		i2 = new JTextField("");
		id2.setLabelFor(i2);
		p2.add(i2);
		l1 = new JLabel("(少于10个字符)");
		l1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		p2.add(l1);

		

		password2 = new JLabel("密码",JLabel.RIGHT);
		pass2 = new JPasswordField("");
		p2.add(password2);
		password2.setLabelFor(pass2);
		p2.add(pass2);
		l2 = new JLabel("(6-16个字符)");
		l2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		p2.add(l2);
		

		ipassword2 = new JLabel("确认密码",JLabel.RIGHT);
		ipass2 = new JPasswordField("");
		p2.add(ipassword2);
		ipassword2.setLabelFor(ipass2);
		p2.add(ipass2);
		l3 = new JLabel("(6-16个字符)");
		l3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		p2.add(l3);
		
		
		
		p42 = new JPanel();
		p42.setLayout(new FlowLayout(FlowLayout.CENTER));
		reg2 = new JButton("注册");
		p42.add(reg2);
		SpringUtilities.makeCompactGrid(p2, 3, 3, 0,(int)(height/13), 5, (int)(height/12));
		bg2.add(p2);
		G2.add(bg2);
		G2.add(p42);
		G2.setResizable(false);
		G2.setLocation(getWidth(G2.getWidth()),getHeight(G2.getHeight()));
		
		
		
		
		
		//the part of G3
		G3 = new JFrame(title3);
		G3.setSize(200,130);
		G3.setLayout(new GridLayout(2,1));
		la3 = new JLabel("注册成功");
		p13 = new JPanel();
		p13.setLayout(new FlowLayout(FlowLayout.CENTER));
		p13.add(la3);
		G3.add(p13);
		
		close3 = new JButton("关闭");
		p23 = new JPanel();
		p23.setLayout(new FlowLayout(FlowLayout.CENTER));
		p23.add(close3);
		G3.add(p23);
		
		G3.setLocation(getWidth(G3.getWidth()),getHeight(G3.getHeight()));


		


		//初始化HomeScreeen类变量
		homeScreen=new HomeScreen();	
		
		//events and reaction
		reg1.addActionListener(new MyActLister1());//sign up int the first GUI
		reg2.addActionListener(new MyActLister2());//the GUI of sign up
		close3.addActionListener(new MyActLister4());//close this GUI's window
		sign1.addActionListener(new MyActLister5());//if successful,sign in to the correct GUI
<<<<<<< HEAD

=======
		//l35.addMouseListener(new MyMouLister1());//open library(student)
		//暂且注释l36.addMouseListener(new MyMouLister1());//open library(teacher)
		//l55.addMouseListener(new MyMouLister2());//open student affairs
		b18.addActionListener(new SearchBookFromDB());//search book
		
		//TODO
		//点击商店的出现商店主界面的响应
		
		//return10.addActionListener(new MyActLister6());//if return10 is clicked,return to G8
>>>>>>> refs/remotes/origin/database
		
		pro1.addItemListener(new MyItemLister1());
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException 
	{  try{//定义界面风格
//		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();  
        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;  
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();  
        UIManager.put("RootPane.setupButtonVisible", false); 
        //更换所有字体
        FontUIResource fontRes = new FontUIResource(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
        fontRes.canDisplay(ABORT);
        
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {  
            Object key = keys.nextElement(); 
            Object value = UIManager.get(key);  
            if (value instanceof FontUIResource) {
//            	FontUIResource f=(FontUIResource)value;
//            	System.out.println(f.getAvailableAttributes().toString());
                UIManager.put(key, fontRes);  
            }  
        }
  	  }catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		GUI gui= new GUI("登陆","注册","成功");
		//gui.init();
	
	}

<<<<<<< HEAD
=======
	//---------------------------------
	//@初始化类-------------------------
		 class Load{
			 
			 Load()
			 {  			    		 
				 //初始化时注意先后顺序
				loadHomeScreen();
				loadBank();		
				loadStore();
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
		     void loadStore()
		     { 
		    	 store=new Store();
		    	 
		     }
		     //调用主界面的update函数将Library、Bank等变量传入
		
		     
	   
		     
		 }
		 //@初始化类结束--------------------------
		 //--------------------------------------
	

>>>>>>> refs/remotes/origin/database
	class MyActLister1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			G2.setVisible(true);
			//G2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	class MyItemLister1 implements ItemListener
	{
		public void itemStateChanged(ItemEvent e) {
			//if the person is a teacher,then the sign up button is not able to be pressed
			if(e.getSource() == pro1 && pro1.getSelectedIndex()!=0)
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
					JOptionPane.showMessageDialog(G1,"用户名或密码无效",
							"注册失败",JOptionPane.ERROR_MESSAGE);

				}
			}
			else
			{
				JOptionPane.showMessageDialog(G1,"用户名或密码无效",
						"注册失败",JOptionPane.ERROR_MESSAGE);

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
			}else if(pro1.getSelectedIndex()==1){
				hm.put("identity","teacher");
			}else{
				hm.put("identity", "admin");
			}
			//send hm to server and get it back, check the result 
			
		    hm=getOne(hm);
		    if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==0)
		    {
		    	ClientInfo.setCi(hm.get("card_id"));
		    	ci =ClientInfo.getCi() ;//un is used to identify user,a global variable
		    	//G5.setVisible(true);
<<<<<<< HEAD
		    	homeScreen.update("student");	    	
=======
		    	homeScreen.G5.setVisible(true);
		    	homeScreen.update(G8, bank, ci,store);
>>>>>>> refs/remotes/origin/database
		    	System.out.println("update card_id to : homeScreen!");
		    	System.out.println("update card_id to : bank!");
		    	G1.setVisible(false);
		    	//G5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	
		    }
		    else if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==1)
		    {
		    	ClientInfo.setCi(hm.get("card_id"));
		    	ci =ClientInfo.getCi();//ci is used to identify user,a global variable
		    	homeScreen.update("teacher");
		    	G1.setVisible(false);
		    	//G6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    		
		    }
		    else if(hm.get("result").equals("success")&&pro1.getSelectedIndex()==2)
		    {
		    	System.out.println("Adminok");
		    	ClientInfo.setCi(hm.get("card_id"));
		    	ci =ClientInfo.getCi();//ci is used to identify user,a global variable
		    	homeScreen.update("admin");
		    	G1.setVisible(false);
		    	
		    }
<<<<<<< HEAD
		    		    
		    else if(hm.get("result").equals("fail"))
		    {
		    	JOptionPane.showMessageDialog(null,"用户名或密码错误",
						"登陆失败",JOptionPane.ERROR_MESSAGE);
		    }
		    i1.setText("");
		    pass1.setText("");
		    //TODO
=======
	
			
		
			
	//TODO
>>>>>>> refs/remotes/origin/database
			
		}
		
	}

}
	



