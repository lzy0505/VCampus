package client;

import javax.swing.*;

public class HSAdmin {
    //传进来的参数
	String ci;
	//部件
    JFrame f_admin;
	JPanel p_admin;
	JPanel admin_p1;
	JPanel admin_p2;
	JLabel l_library;
	JLabel l_fee;
	JLabel l_store;
	

	
	public void init()
	{
        f_admin=new JFrame("Admin Management");
        p_admin=new JPanel();
		l_library=new JLabel("Library Manage",new ImageIcon("library.png"),JLabel.LEFT);
		l_fee=new JLabel("Fee Manage",new ImageIcon("bank.png"),JLabel.LEFT);
		l_store=new JLabel("Store Manage",new ImageIcon("store.png"),JLabel.LEFT);		
		 l_library.setHorizontalTextPosition(JLabel.CENTER);
		 l_library.setVerticalTextPosition(JLabel.BOTTOM);
		 l_fee.setHorizontalTextPosition(JLabel.CENTER);
		 l_fee.setVerticalTextPosition(JLabel.BOTTOM);
		 l_store.setHorizontalTextPosition(JLabel.CENTER);
		 l_store.setVerticalTextPosition(JLabel.BOTTOM);
		admin_p1=new JPanel();
		admin_p2=new JPanel();
		admin_p1.add(l_library);
		admin_p1.add(l_fee);
		admin_p2.add(l_store);
		p_admin.add(admin_p1);
		p_admin.add(admin_p2);
		p_admin.setLayout(new BoxLayout(p_admin,BoxLayout.Y_AXIS));
		f_admin.add(p_admin);
		f_admin.setSize(500, 500);
		f_admin.setLocation(GUI.getWidth(f_admin.getWidth()),GUI.getHeight(f_admin.getHeight()));
        addLis();//增加监听函数
        
	}
	
	void addLis()
	{
		
		
		
		
		
	}
	
	void update(String card_id)
	{
		ci=card_id;
		
	}
	void paint()
	{
		f_admin.removeAll();
		f_admin.add(p_admin);
		f_admin.repaint();
		f_admin.revalidate();
	
	}
	
	
	
	
	
	
	
	
	
}
