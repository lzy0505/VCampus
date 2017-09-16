package client;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//----------------------------------------
//@主界面类--------------------------------
class HomeScreen
{
	//the elements of G5
	//需要传参的变量
	    Bank bank;
	    JFrame G8;
	    Store store;
	    String ci;
	//init()变量    
	    JFrame G5;
		JPanel p15,p25,p35;
		Container p_HomeScreen; //主面板内容容器
		JLabel l15,l25,l35,l45,l55,l65;
		

	 	 
	 void init()
	 {
		  G5 = new  JFrame();
		  p_HomeScreen=new JPanel();
		  p_HomeScreen.setLayout(new BoxLayout(p_HomeScreen,BoxLayout.Y_AXIS));
		  p15 = new JPanel();
		  p15.setLayout(new BoxLayout(p15,BoxLayout.X_AXIS));
		  l15 = new JLabel("Welcome!");
		  l25 = new JLabel("Sign out");
		  p35 = new JPanel();
		  l35 = new JLabel("Library",new ImageIcon("library.png"),JLabel.LEFT);
		  l45 = new JLabel("Store",new ImageIcon("store.png"),JLabel.LEFT);
		  l55 = new JLabel("Courses",new ImageIcon("courses.png"),JLabel.LEFT);
		  p25 = new JPanel();
		  p25.setLayout(new BoxLayout(p25,BoxLayout.X_AXIS));
		  l65 = new JLabel("Bank",new ImageIcon("bank.png"),JLabel.LEFT);
		  G5.setSize(500,500);
		  //p15.setLayout(new GridLayout(1,2,150,0));			
		  p15.add(l15);
		  p15.add(l25);
		  p_HomeScreen.add(p15);		
		  l35.setHorizontalTextPosition(JLabel.CENTER);
		  l35.setVerticalTextPosition(JLabel.BOTTOM);
     	  //p25.setLayout(new FlowLayout(FlowLayout.CENTER));
		  p25.add(l35);
		  l45.setHorizontalTextPosition(JLabel.CENTER);
		  l45.setVerticalTextPosition(JLabel.BOTTOM);
		  p25.add(l45);
		  p_HomeScreen.add(p25);
		  l55.setHorizontalTextPosition(JLabel.CENTER);
		  l55.setVerticalTextPosition(JLabel.BOTTOM);				
		  //p35.setLayout(new FlowLayout(FlowLayout.CENTER));
		  p35.setLayout(new BoxLayout(p35,BoxLayout.X_AXIS));
		  p35.add(l55);	
		  l65.setHorizontalTextPosition(JLabel.CENTER);
		  l65.setVerticalTextPosition(JLabel.BOTTOM);
		  p35.add(l65);
		  p_HomeScreen.add(p35);			
		  G5.setLocation(GUI.getWidth(G5.getWidth()),GUI.getHeight(G5.getHeight()));
		  G5.add(p_HomeScreen);		  
		  
		  
	 }
	 //重绘函数
	 void paint()
	 {
		 
		 G5.getContentPane().removeAll();
		 G5.getContentPane().add(p_HomeScreen);
		 G5.getContentPane().repaint();
		 G5.getContentPane().revalidate();
	 }
	 //消息映射函数
	 void addLis() 
	 {
		 
		 l65.addMouseListener(new MyMouLister_bank());//open bank;(stduent)
		 l35.addMouseListener(new MyMouLister1());//open library(student)
		 l45.addMouseListener(new MyMouseLister_store());
		 l55.addMouseListener(new MyMouLister2());//open student affairs
		 
	 }
	//传参并且启用监听函数
	 void update(JFrame lb,Bank bk,String card_id,Store st)
	 {
		 G8=lb;	 
		 bank=bk;
		 ci=card_id;
		 store=st;
		 addLis();//HomeScreen的addLis()应写在update()里
		 
	 }
	 class MyMouseLister_store implements MouseListener
	 {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO 自动生成的方法存根
			
			store.homeScreen.setVisible(true);
			
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自动生成的方法存根
			
		}
		 
		 
		 
		 
		 
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 //点击图书馆按钮
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
	 
	 
	 //点击课程按钮(学生)
	 class MyMouLister2 implements MouseListener
	 	{
	 		public void mouseClicked(MouseEvent arg0) {
	 			
	 			
	 			HashMap<String,String> hm = new HashMap<>();
	 	 	 	hm.put("card_id", ci);
	 	 	 	hm.put("op", "search_course");
	 			//get information from database and give value to this above parameters
	  			StudentAffairs student= new StudentAffairs(hm);
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
	 			// TODO 自动生成的方法存根
	 			
	 		}
	 
	 		@Override
	 		public void mouseReleased(MouseEvent arg0) {
	 			// TODO 自动生成的方法存根
	 			
	 		}
	}
	 
	 
	 
	 
	 
	 
	 
	//点击银行按钮
	 class MyMouLister_bank implements MouseListener
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
	 			//System.out.println("BANKOK");
	 			bank.paint();
	 			//virtualBank.setVisible(true);
	 			
	 		}
	 
	 		@Override
	 		public void mouseReleased(MouseEvent arg0) {
	 			// TODO 自动生成的方法存根
	 			
	 		}
	 		
	 	}

	 
}
//@主界面类结束-----------------------------
//-----------------------------------------


