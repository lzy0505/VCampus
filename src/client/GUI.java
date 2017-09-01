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
	

	static JFrame G1;
	JPanel p11,p21,p31,p41;
	JLabel profession1,id1,password1;
	JComboBox pro1;
	JTextField i1;
	JPasswordField pass1;
	static JButton reg1;
	static JButton sign1;
	

	static JFrame G2;
	JPanel p12,p22,p32,p42;
	JLabel id2,password2,ipassword2,l1,l2,l3;
	static JTextField i2;
	static JPasswordField pass2;
	static JPasswordField ipass2;
	static JButton reg2;
	
	
	static JFrame G3;
	JPanel p13,p23;
	JLabel la3;
	static JButton close3;
	

	static JFrame G4;
	JPanel p14,p24;
	JLabel la4;
	static JButton return4;
	
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
		HashMap<String, String> getmes=null;
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
	
	
	public GUI(String title1,String title2,String title3,String title4)
	{
		
		G1 = new JFrame(title1);
		Container c1 = G1.getContentPane();
		c1.setLayout(new BoxLayout(c1,BoxLayout.Y_AXIS));
		
		G1.setSize(250, 250);
		p11 = new JPanel();
		p11.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		profession1 = new JLabel("��ݣ�");
		p11.add(profession1);
		
		pro1 = new JComboBox();
		pro1.addItem("Student");
		pro1.addItem("Teacher");
		p11.add(pro1);
		G1.add(p11);
		
		p21 = new JPanel();
		p21.setLayout(new FlowLayout(FlowLayout.CENTER));
		id1 = new JLabel("�û���");
		p21.add(id1);
		
		i1 = new JTextField("",8);
		p21.add(i1);
		G1.add(p21);
		
		p31 = new JPanel();
		p31.setLayout(new FlowLayout(FlowLayout.CENTER));
		password1 = new JLabel("���룺");
		p31.add(password1);
		pass1 = new JPasswordField("",8);
		p31.add(pass1);
		G1.add(p31);
		
		p41 = new JPanel();
		p41.setLayout(new FlowLayout(FlowLayout.CENTER));
		reg1 = new JButton("ע��");
		sign1 = new JButton("��½");
		p41.add(reg1);
		p41.add(sign1);	
		G1.add(p41);
		
		G1.setVisible(true);
		G1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//G2����Ʋ���
		G2 = new JFrame(title2);
		G2.setSize(300,270);
		Container c2 = G2.getContentPane();
		c2.setLayout(new BoxLayout(c2,BoxLayout.Y_AXIS));
		
		//G2.add(p21);
		//G2.add(p31);
		
		p12 = new JPanel();
		p12.setLayout(new FlowLayout(FlowLayout.CENTER));
		id2 = new JLabel("�û���");
		p12.add(id2);
		i2 = new JTextField("",8);
		p12.add(i2);
		l1 = new JLabel("(һ��ͨ��)");
		p12.add(l1);
		G2.add(p12);
		
		p22 = new JPanel();
		p22.setLayout(new FlowLayout(FlowLayout.CENTER));
		password2 = new JLabel("���룺");
		pass2 = new JPasswordField("",8);
		p22.add(password2);
		p22.add(pass2);
		l2 = new JLabel("(6��16λ)");
		p22.add(l2);
		G2.add(p22);
		
		p32 = new JPanel();
		p32.setLayout(new FlowLayout(FlowLayout.CENTER));
		ipassword2 = new JLabel("����ȷ�ϣ�");
		ipass2 = new JPasswordField("",8);
		p32.add(ipassword2);
		p32.add(ipass2);
		l3 = new JLabel("(6��16λ)");
		p32.add(l3);
		G2.add(p32);
		
		p42 = new JPanel();
		p42.setLayout(new FlowLayout(FlowLayout.CENTER));
		reg2 = new JButton("ע��");
		p42.add(reg2);
		G2.add(p42);
		
		//G3����ɲ���
		G3 = new JFrame(title3);
		G3.setSize(200,130);
		G3.setLayout(new GridLayout(2,1));
		la3 = new JLabel("ע��ɹ���");
		p13 = new JPanel();
		p13.setLayout(new FlowLayout(FlowLayout.CENTER));
		p13.add(la3);
		G3.add(p13);
		
		close3 = new JButton("�ر�");
		p23 = new JPanel();
		p23.setLayout(new FlowLayout(FlowLayout.CENTER));
		p23.add(close3);
		G3.add(p23);
		
		//G4����ɲ���
		G4 = new JFrame(title4);
		G4.setSize(200,130);
		G4.setLayout(new GridLayout(2,1));
		la4 = new JLabel("�û���/���벻���Ҫ��");
		p14 = new JPanel();
		p14.setLayout(new FlowLayout(FlowLayout.CENTER));
		p14.add(la4);
		G4.add(p14);
		
		return4 = new JButton("����");
		p24 = new JPanel();
		p24.setLayout(new FlowLayout(FlowLayout.CENTER));
		p24.add(return4);
		G4.add(p24);
		
		reg1.addActionListener(new MyActLister1());
		reg2.addActionListener(new MyActLister2());
		return4.addActionListener(new MyActLister3());
		close3.addActionListener(new MyActLister4());
	}
	
	
		public static void main(String[] args) {
			GUI gui= new GUI("����У԰��½����","ע��","ע��ɹ�","ע��ʧ��");
			gui.init();
			//sign1.addActionListener(new MyActLister4());
	
		}


	class MyActLister1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			G2.setVisible(true);
			G2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			/*if()//�����½��ť��ȥ��ݿ������Ƿ��д��û����ڣ����У���򿪵�½��ķ������
			{}*/
			//TODO
			
		}
		
	}
	
	class MyActLister2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			//���û���С��ʮλ������ȷ�ϡ����볤�ȷ��Ҫ��ע��ɹ�
			if(i2.getText().length()<11 & (pass2.getText().length()>5) & (pass2.getText().length()<17)& (pass2.getText().equals(ipass2.getText())))
			{
				G3.setVisible(true);
				G3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				HashMap<String,String> hm=new HashMap<String,String>();
					
				hm.put("username",i2.getText());
				hm.put("password",pass2.getText());
				if(pro1.getSelectedIndex()==0){
					hm.put("identity","student");
				}else{
					hm.put("identity","teacher");
				}
			
				System.out.println(hm.get("username"));
				System.out.println(hm.get("password"));
				hm=get(hm);
				System.out.println(hm.get("result"));
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
		}
		
	}
	
	/*static class MyActLister4 implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//�ж���ݿ����Ƿ��д��ˣ����У���½�ɹ�����ʾ��½����
			//TODO
		}
		
	}*/
	

}


