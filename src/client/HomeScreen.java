package client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.RootPaneUI;

//----------------------------------------
//@主界面类--------------------------------
class HomeScreen
{
	//the elements of G5
	//需要传参的变量
	    Bank bank;
	    Library library;//图书馆
	    StudentAffairs studentAffairs;
	    Registration registration;
	    HSAdmin hsAdmin;
	    String ci;
	   //init()变量    
	    JFrame G5;
//		JPanel p15,p25,p35;
		JTabbedPane p_HomeScreen; //主面板内容容器
//		JLabel l15,l25,l35,l45,l55,l56,l65;
		

	 	 
	
	 //重绘函数
	 void paint()
	 {
		 G5.getContentPane().removeAll();
		 G5.getContentPane().add(p_HomeScreen);
		 G5.getContentPane().repaint();
		 G5.getContentPane().revalidate();
	 }

	//传参并且启用监听函数
	 void update(String identity)
	 {
		 	G5 = new  JFrame();
		 	G5.setSize(new Dimension(1000,800));
		 	G5.setResizable(false);
		 	p_HomeScreen=new JTabbedPane();
		 	p_HomeScreen.setTabPlacement(JTabbedPane.LEFT);
		 	JPanel backGround=new JPanel();
		 	
		  G5.setLocation(GUI.getWidth(G5.getWidth()),GUI.getHeight(G5.getHeight()));
		  G5.add(p_HomeScreen);	
		 ci=ClientInfo.getCi();
		 if(identity.equals("admin")) {
			 hsAdmin=new HSAdmin();
	    	 hsAdmin.init();
	    	 p_HomeScreen.addTab("",new ImageIcon("bank.png"),hsAdmin.tab);
		 }else {
			 library=new Library(this);
	    	 library.init();
	    	 p_HomeScreen.addTab("",new ImageIcon("library.png"),library.tab_library);
	    	 bank=new Bank(this);
			 bank.init();
			 p_HomeScreen.addTab("",new ImageIcon("bank.png"),bank.tab_bank);
			 if(identity.equals("student")) {
				 studentAffairs=new StudentAffairs(this);
				 p_HomeScreen.addTab("",new ImageIcon("courses.png"),studentAffairs.tabbedPane);
			 }else {
				 registration=new Registration(new String[][] {},this);
				 p_HomeScreen.addTab("",new ImageIcon("studentMessage.png"),registration.tabbedPane);
			 }
		 }
		 backGround.add(p_HomeScreen);
		 G5.add(backGround);
		 G5.setVisible(true);
		
		 
	 }
	 
	 
}
//@主界面类结束-----------------------------
//-----------------------------------------


