/**
 * 
 */
package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.HomeScreen;

/**
 * @author Mcdull
 *
 */

//----------------------------------------
//@银行类----------------------------------
class Bank
{
	HomeScreen homeScreen=null;
	String ci=null;
	JTabbedPane tab_bank;
	JPanel  p_bank;//中间面板
	JButton return_upbank;//返回按钮
   
	
	//充值界面
	JLabel l_money;//显示充值金额的标签
	JTextField money_recharge;//充值金额
	JButton ok_re;//确认按钮 	
	JPanel recharge_p1;//放置充值金额标签和显示框
	JPanel recharge_p2;//放置确实标签密码和确定按钮
	JPanel p_recharge;
	JLabel l_confirm1;
	JPasswordField t_password_confirm1;
	
	
	//缴费界面
	JLabel l_chosePay;
	JComboBox<String> chosePay;//选择缴费的种类
	JButton payment_query;//查询相应缴费项目欠款金额的按钮
	JLabel l_needtopay;
	JTextField t_needtopay;//需要交多少钱的文本框显示
	
	JButton ok_pay;//确认缴费
	JLabel l_confirm2;
	JPasswordField t_password_confirm2;
	JPanel payment_p1;
	JPanel payment_p2;
	JPanel payment_p3;
	JPanel p_payment;
	BoxLayout layout_payment;
	String[][] rowData;//学费表数据
	String[] columnNames;//学费表头
	String sign;//在缴费时用来标识交哪种费用
	JTable t;//学费表格
	JScrollPane sp;//学费滚动面板
		
	//查询界面
	JPanel p_balance;//余额面板
	JLabel l_ecard;//一卡通余额标签
	JTextField t_balance;//一卡通余额显示

	public Bank(HomeScreen hs,String username){
		homeScreen=hs;
		ci=username;
	}
	
	//绘制银行的函数 
	 void init()
	 {		
		
		    //返回到上一层按钮
			return_upbank=new JButton("return");
			
			
		 	//充值界面	
		    p_recharge = new JPanel();			    
		 	money_recharge=new JTextField();
		 	money_recharge.setColumns(20);
		 	ok_re=new JButton("ensure");
		 	ok_re.setPreferredSize(new Dimension(70,30));
		 	l_money=new JLabel("Recharge amount:");
		 	recharge_p1=new JPanel();
		 	recharge_p2=new JPanel();
		 	l_confirm1=new JLabel("confirm password :");
		 	t_password_confirm1=new JPasswordField();
		 	t_password_confirm1.setColumns(17);
		 	recharge_p1.setLayout(new FlowLayout());
		 	recharge_p1.add(l_money);
		 	recharge_p1.add(money_recharge);
		 	recharge_p2.add(l_confirm1);
		 	recharge_p2.add(t_password_confirm1);
		 	recharge_p2.add(ok_re);
		 	p_recharge.setLayout(new BoxLayout(p_recharge,BoxLayout.Y_AXIS));
		 	p_recharge.add(recharge_p1);
		 	p_recharge.add(recharge_p2);
		 	
		 	
		 	//缴费界面
		    sign="Tuition";//默认缴学费
		    p_payment=new JPanel();		  
		    p_payment.setLayout(new BoxLayout(p_payment,BoxLayout.Y_AXIS));
		 	l_chosePay=new JLabel("Pay Type : ");
		 	chosePay=new JComboBox<String>();//选择缴费的种类
		 	chosePay.addItem("Tuition");//学费选项
		 	chosePay.addItem("Water and electricity");//水电费
		 	chosePay.addItem("Accommodation fee");//住宿费	 	
		 	payment_query=new JButton("Query");//查询相应缴费项目欠款金额的按钮
		 	payment_query.setPreferredSize(new Dimension(70,30));
		 	l_needtopay=new JLabel("need to pay : ");//
		 	t_needtopay=new JTextField();//需要交多少钱的文本框显示
		 	t_needtopay.setColumns(10);
		 	t_needtopay.setEditable(false);
		 	l_confirm2=new JLabel("confirm password : ");
		 	l_confirm2.setVisible(false);
		 	t_password_confirm2=new JPasswordField();
		 	t_password_confirm2.setColumns(15);
		 	t_password_confirm2.setVisible(false);
		 	ok_pay=new JButton("Ensure Payment");//确认缴费
		 	ok_pay.setVisible(false);;//必须至少查询一次才可以出现，否则表没出来点击导致异常
		 	payment_p3=new JPanel();
		 	payment_p3.add(l_confirm2);
		 	payment_p3.add(t_password_confirm2);
		 	payment_p3.add(ok_pay);	 	
		 	payment_p1=new JPanel();
		 	payment_p2=new JPanel();
		 	payment_p1.add(l_chosePay);
		 	payment_p1.add(chosePay);
		 	payment_p1.add(payment_query);		 	
		 	p_payment.add(payment_p1);
		 	p_payment.add(payment_p2);
		 	p_payment.add(payment_p3);
		 	
		 	
		 	//查询界面
		 	l_ecard=new JLabel("e-card balance : ");
		 	t_balance=new JTextField();
		 	t_balance.setColumns(10);
		 	t_balance.setEditable(false);//设置文本框不可编辑
		 	p_balance=new JPanel();		 	
		 	p_balance.setLayout(new FlowLayout());
		 	p_balance.add(l_ecard);
		 	p_balance.add(t_balance);
		 	
		 	
		 	//银行tab
		 	//return_upbank.setPreferredSize(new Dimension(300,80));
		 	tab_bank=new JTabbedPane();			 	
		 	tab_bank.addTab("Recharge",p_recharge);
		 	tab_bank.addTab("Payment",p_payment);
		 	tab_bank.addTab("Balance",p_balance);
		 	tab_bank.setPreferredSize(new Dimension(300,280));
	        p_bank=new JPanel();
	        p_bank.setLayout(new BoxLayout(p_bank,BoxLayout.Y_AXIS));
	        p_bank.add(tab_bank);
	        p_bank.add(return_upbank);
	        
		 	//启用监听函数
		 	addLis();
		 			 
	 }
	 //银行类的重绘函数 
	 void paint()
	 {
	 homeScreen.G5.getContentPane().removeAll();
	 homeScreen.G5.setTitle("Bank");
	 homeScreen.G5.getContentPane().add(p_bank);
	 homeScreen.G5.getContentPane().repaint();
	 homeScreen.G5.getContentPane().revalidate();
	 }
	 //为银行类的相关控件增加监听函数
	 void addLis()
	 {   
		 return_upbank.addActionListener(new ActionLis_RepaintHomeScreen());//返回重绘按钮（已完成）
		 ok_re.addActionListener(new ActionLis_recharge());	//确认充值按钮	
		 payment_query.addActionListener(new ActionLis_Paymentquery());//缴费查询按钮
		 ok_pay.addActionListener(new ActionLis_Pay());//确认缴费按钮
		 tab_bank.addChangeListener(new ChangeLis_tab());//Tab选项卡监听，当鼠标点击第三个选项卡（查询余额）,触发查询事件
	 }
		//-----------------------------------------------
		//@@各种消息映射类的实现
	 class ChangeLis_tab implements ChangeListener
	 {

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			
		    JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
		    int selectedIndex = tabbedPane.getSelectedIndex();
		    if(selectedIndex==2)
		    {
		    	HashMap<String,String> hm=new HashMap<String,String>();
				hm.put("op", "QueryBalance");//查询一卡通余额操作
				hm.put("card_id", ci);
				hm=GUI.getOne(hm);//GetOne是GUI的static函数，调用时要前缀" GUI. "
				
				//---反馈部分交给李某
				//...返回用户的一卡通余额，写进t_balance控件显示
				//--------------
	
		    }
			
			
			
		}
		 
		 
		 
		 
		 
	 }

	 
    class ActionLis_Pay implements ActionListener
    {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				HashMap<String,String> hm=new HashMap<String,String>();
				hm.put("op", "Payment");	
				hm.put("card_id", ci);
				hm.put("type", sign);//注：sign分别可能为 "Tuition" ,"WandE", "Afee"用来标识缴费的种类
				String row=t.getSelectedRow()+"";//获取用户指向的行，表明要交哪一学期的钱,此处有一个int向String的转换			
				hm.put("row", row);
				hm=GUI.getOne(hm);//GetOne是GUI的static函数，调用时要前缀" GUI. "
				
				//---反馈部分交给李某
				//...返回是否交钱成功，建议弄一个小弹窗
				//--------------
			
			}

    }
	 

	 
	class ActionLis_recharge implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//发送包含密码和充值金额的HashMap
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("op", "recharge");
			hm.put("card_id", ci);//传递用户名
			hm.put("amount",money_recharge.getText());//传递需要充值多少金额
			hm=GUI.getOne(hm);
			
			//---反馈部分交给李某
			//...返回是否充值成功，建议弄一个小弹窗
			//--------------
		}
		 
	 
	}
	
	
	 class ActionLis_RepaintHomeScreen implements ActionListener
	 {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			homeScreen.paint();
			
			
		}	 	 
	 }





	  class ActionLis_Paymentquery implements ActionListener
	  {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("card_id", ci);

			rowData=new String[8][4];
			columnNames=new String[4];
			//表头
			columnNames[0]="Grade";
			columnNames[1]="Semester";
			
			columnNames[3]="Has paid?";	
			//初始化年级和学期方格的内容
			rowData[0][0]=rowData[1][0]="1";
			rowData[2][0]=rowData[3][0]="2";
			rowData[4][0]=rowData[5][0]="3";
			rowData[6][0]=rowData[7][0]="4";
			for(int i=0;i<8;i++)
			{
				if(i%2==0)
				{					
					rowData[i][1]="First";					
				}
				if(i%2==1)
				{
					rowData[i][1]="Second";
				}

			}
			
		switch(chosePay.getSelectedIndex())
		{
			
		case 0:
			//查询学费表
			hm.put("op", "QueryPayment");	
			hm.put("type", "Tuition");
			sign="Tuition";//表示交学费
			columnNames[2]="Tution";
			//@反馈部分--------
			//...返回学费表的费用和是否缴清两列数据，显示
			
			//
			//-----------------
			
			break;
			
		case 1:
			//查询水电费
			hm.put("op", "QueryPayment");
			hm.put("type", "WandE");
			sign="WandE";//表示交水电费
			columnNames[2]="Water and electricty fee";
			//@反馈部分--------
			//...返回水电表的费用和是否缴清两列数据，显示
			//
			//-----------------
			
			
			
			
			break;
		case 2:
			hm.put("op", "QueryPayment");
			hm.put("type", "Afee");
			sign="Afee";//表示交住宿费
			columnNames[2]="Accommodation fee";
			//@反馈部分--------
			//...返回住宿表的费用和是否缴清两列数据，显示
			//
			//-----------------

			break;
			
		default:
				break;

		}
		//创建表格
		sp=new JScrollPane();
		t=new JTable(rowData,columnNames);
		//bank.t.setPreferredSize(new Dimension(450,300));
		t.setRowHeight(40);
		sp.setViewportView(t);
		sp.setPreferredSize(new Dimension(450,300));
		payment_p2.removeAll();
		payment_p2.add(sp);
		payment_p2.repaint();
		payment_p2.revalidate();
		//至少查询一次后将确认密码部分控件设置为可见
		l_confirm2.setVisible(true);
		t_password_confirm2.setVisible(true);
		ok_pay.setVisible(true);
		
		}		   	
	  }
	 
	 
}
//@银行类结束------------------------------
//-------------------------------------------



 