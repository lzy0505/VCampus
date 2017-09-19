package client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.RootPaneUI;

//----------------------------------------
//@主界面类--------------------------------
class HomeScreen
{
	final static private double RATIO=0.66;
	public static double width=Toolkit.getDefaultToolkit().getScreenSize().getWidth()*RATIO;
	public static double height=Toolkit.getDefaultToolkit().getScreenSize().getHeight()*RATIO;
	//the elements of G5
	//需要传参的变量
	    Bank bank;
	    Library library;//图书馆

	    Registration registration;
	    HSAdmin hsAdmin;
	    Store store;
	    String ci;
	    JFrame G5;
		JTabbedPane p_HomeScreen; //主面板内容容器

		int preSecTab=0;
		
		HashMap<String,String> isCompleted=null;

	 	 
	//传参并且启用监听函数

	 void update(String identity)
	 {	
		 	G5 = new  JFrame();
		 	G5.setTitle("虚拟校园系统");
		 	G5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	G5.setSize((int) (width),  (int) (height));
		 	G5.setIconImage(new ImageIcon("logo.png").getImage());
			G5.getLayeredPane().getComponent(1).setFont(new Font("Microsoft YaHei UI", Font.BOLD, 15));
		 	G5.setResizable(false);
		 	p_HomeScreen=new JTabbedPane();
		 	p_HomeScreen.addChangeListener(new ChangeListener(){

				@Override
				public void stateChanged(ChangeEvent arg0) {
					if(p_HomeScreen.getSelectedIndex()==5) {
						int r=JOptionPane.showConfirmDialog(G5, "是否确认登出", "操作确认",JOptionPane.WARNING_MESSAGE );
						if(r==0) {
							ClientInfo.setCi("");
							G5.setVisible(false);
							GUI.G1.setVisible(true);
						}else {
							p_HomeScreen.setSelectedIndex(preSecTab);
						}
					}else {
						preSecTab=p_HomeScreen.getSelectedIndex();
					}
				}
		 		
		 	});
		 	p_HomeScreen.setTabPlacement(JTabbedPane.LEFT);
		 	p_HomeScreen.setPreferredSize(new Dimension((int) (width*0.97),  (int) (height)-50));
		 	JPanel backGround=new JPanel();
		 	
		 	
			  G5.setLocation(GUI.getWidth(G5.getWidth()),GUI.getHeight(G5.getHeight()));
			  G5.add(p_HomeScreen);	
			  ci=ClientInfo.getCi();
			 library=new Library(this);
	    	 library.init();
	    	 p_HomeScreen.addTab("图书馆",new ImageIcon("library.png"),library.tab_library);
	    	 
	    	 bank=new Bank(this);
			 bank.init();
			 p_HomeScreen.addTab("银   行",new ImageIcon("bank.png"),bank.tab_bank);
			 store=new Store();
			 p_HomeScreen.addTab("商   店",new ImageIcon("store.png"),store.mainPanel);
			 if(identity.equals("student")) {
				StudentAffairs studentAffairs=new StudentAffairs(this);
				UserManage userManage=new UserManage(this);
				p_HomeScreen.addTab("教   务",new ImageIcon("courses.png"),studentAffairs.tabbedPane);
				p_HomeScreen.addTab("信息管理",userManage.tabPanel);
				isCompleted=new HashMap<String,String>();
				isCompleted.put("op", "isCompleted");
				isCompleted.put("card_id",ClientInfo.getCi());
				isCompleted=GUI.getOne(isCompleted);
				p_HomeScreen.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						isCompleted=new HashMap<String,String>();
						isCompleted.put("op", "isCompleted");
						isCompleted.put("card_id",ClientInfo.getCi());
						isCompleted=GUI.getOne(isCompleted);
						if(isCompleted.get("result").equals("false")) {
							p_HomeScreen.setEnabledAt(3, false);
						}else {
							p_HomeScreen.setEnabledAt(3, true);
						}
					}
				});
				if(isCompleted.get("result").equals("false")) {
					p_HomeScreen.setEnabledAt(3, false);
					JOptionPane.showMessageDialog(null, "请先录入个人信息","操作提示",JOptionPane.ERROR_MESSAGE);
				}else {
					p_HomeScreen.setEnabledAt(3, true);
				}
			 }else {
				 registration=new Registration(new String[][] {},this);
				 p_HomeScreen.addTab("教   务",new ImageIcon("studentMessage.png"),registration.tabbedPane);
			 }
			 
			 p_HomeScreen.addTab("登    出",null);
			 backGround.add(p_HomeScreen);
			 G5.add(backGround);
			 G5.setVisible(true);	 
	 }
	 
	 
}
//@主界面类结束-----------------------------
//-----------------------------------------


