/**
 * 
 */
package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import client.HomeScreen;
import sun.security.util.Password;

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
	public JTabbedPane tab_bank;
	JPanel  p_bank;//中间面板
   
	
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
    boolean clickflag;
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
	String[] time = new String[8];//记录缴水电学杂费栏学期
	String[] card_record_id = new String[8];//记录相应的唯一标识码
	String sign;//在缴费时用来标识交哪种费用
	JTable t;//学费表格
	JScrollPane sp;//学费滚动面板
		
	//查询界面
	JPanel p_balance;//余额面板
	JLabel l_ecard;//一卡通余额标签
	JTextField t_balance;//一卡通余额显示

	public Bank(HomeScreen hs){
		homeScreen=hs;
		ci=ClientInfo.getCi();
	}
//	void update(String card_id)
//	 {
//		 ci=card_id;
//	 }
	
	//绘制银行的函数 
	 void init()
	 {		
		
		 	//充值界面	
		    p_recharge = new JPanel();			    
		 	money_recharge=new JTextField();
		 	money_recharge.setColumns(20);
		 	ok_re=new JButton("确定");
		 	ok_re.setPreferredSize(new Dimension(70,30));
		 	l_money=new JLabel("充值金额");
		 	recharge_p1=new JPanel();
		 	recharge_p2=new JPanel();
		 	l_confirm1=new JLabel("确认密码");
		 	t_password_confirm1=new JPasswordField();
		 	t_password_confirm1.setColumns(17);
		 	recharge_p1.setLayout(new FlowLayout());
		 	recharge_p1.add(l_money);
		 	recharge_p1.add(money_recharge);
		 	recharge_p2.add(l_confirm1);
		 	recharge_p2.add(t_password_confirm1);
		 	recharge_p2.add(ok_re);
		 	p_recharge.setLayout(new BoxLayout(p_recharge,BoxLayout.Y_AXIS));
		 	
		 	
		 	
		 	//缴费界面
		 	clickflag=false;
		    sign="Tuition";//默认缴学费
		    p_payment=new JPanel();		  
		    p_payment.setLayout(new BoxLayout(p_payment,BoxLayout.Y_AXIS));
		 	l_chosePay=new JLabel("付款项目 ");
		 	chosePay=new JComboBox<String>();//选择缴费的种类
		 	chosePay.addItem("学费");//学费选项
		 	chosePay.addItem("水电费");//水电费
		 	chosePay.addItem("住宿费");//住宿费	 	
		 	//payment_query=new JButton("Query");//查询相应缴费项目欠款金额的按钮
		 	//payment_query.setPreferredSize(new Dimension(70,30));
		 	l_needtopay=new JLabel("需支付");//
		 	t_needtopay=new JTextField();//需要交多少钱的文本框显示
		 	t_needtopay.setColumns(10);
		 	t_needtopay.setEditable(false);
		 	l_confirm2=new JLabel("确认密码");
		 	l_confirm2.setVisible(false);
		 	t_password_confirm2=new JPasswordField();
		 	t_password_confirm2.setColumns(15);
		 	t_password_confirm2.setVisible(false);
		 	ok_pay=new JButton("缴费");//确认缴费
		 	ok_pay.setVisible(false);;//必须至少查询一次才可以出现，否则表没出来点击导致异常
		 	payment_p3=new JPanel();
		 	payment_p3.add(l_confirm2);
		 	payment_p3.add(t_password_confirm2);
		 	payment_p3.add(ok_pay);	 	
		 	payment_p1=new JPanel();
		 	payment_p2=new JPanel();
		 	payment_p1.add(l_chosePay);
		 	payment_p1.add(chosePay);
		 	//payment_p1.add(payment_query);		 	
		 	p_payment.add(payment_p1);
		 	p_payment.add(payment_p2);
		 	p_payment.add(payment_p3);
		 	
		 	
		 	
		 	
		 	//查询界面
		 	l_ecard=new JLabel("一卡通余额：");
		 	t_balance=new JTextField();
		 	t_balance.setColumns(10);
		 	t_balance.setEditable(false);//设置文本框不可编辑
		 	p_balance=new JPanel();		 	
		 	p_balance.setLayout(new FlowLayout());
		 	p_balance.add(l_ecard);
		 	p_balance.add(t_balance);
		 	
		 	p_recharge.add(p_balance);
		 	p_recharge.add(recharge_p1);
		 	p_recharge.add(recharge_p2);
		 	
		 	
		 	
		 	//银行tab
		 	//return_upbank.setPreferredSize(new Dimension(300,80));
		 	tab_bank=new JTabbedPane();			 	
		 	tab_bank.addTab("查询与充值",p_recharge);
		 	tab_bank.addTab("支付",p_payment);
		 	tab_bank.setPreferredSize(new Dimension(300,280));
	        p_bank=new JPanel();
	        p_bank.setLayout(new BoxLayout(p_bank,BoxLayout.Y_AXIS));
	        p_bank.add(tab_bank);
	        
		 	//启用监听函数
		 	addLis();
		 			 
	 }

	 //为银行类的相关控件增加监听函数
	 void addLis()
	 {   
		 ok_re.addActionListener(new ActionLis_recharge());	//确认充值按钮	
		 //payment_query.addActionListener(new ActionLis_Paymentquery());//缴费查询按钮
		 chosePay.addActionListener(new ActionLis_Paymentquery());
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
		    if(selectedIndex==1){
		    	new ActionLis_Paymentquery().actionPerformed(null);
		    }else if(selectedIndex==0)
		    {
		    	HashMap<String,String> hm=new HashMap<String,String>();
				hm.put("op", "QueryBalance");//查询一卡通余额操作
				hm.put("card_id", ci);
				hm=GUI.getOne(hm);//GetOne是GUI的static函数，调用时要前缀" GUI. "
				t_balance.setText(hm.get("card_balance"));
				System.out.println("card_balance :" + hm.get("card_balance"));
				
				//---反馈部分交给李某
				//...返回用户的一卡通余额，写进t_balance控件显示
				//--------------
				//哦，写完了
		    }
			
			
			
		}
		 
		 
		 
		 
		 
	 }

	 
    class ActionLis_Pay implements ActionListener
    {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t.getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null, "尚未选择付款项！","操作错误",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(rowData[t.getSelectedRow()][2].equals("Yes"))
					{JOptionPane.showMessageDialog(null, "已支付","操作结果",JOptionPane.WARNING_MESSAGE);
					return;
					}
				
				HashMap<String,String> hm=new HashMap<String,String>();
				hm.put("op", "Payment");	
				hm.put("card_id", ci);
				String password = t_password_confirm2.getText();
				hm.put("password", password);
				
				//hm.put("type", sign);//注：sign分别可能为 "Tuition" ,"WandE", "Afee"用来标识缴费的种类
				//String row=t.getSelectedRow()+"";//获取用户指向的行，表明要交哪一学期的钱,此处有一个int向String的转换			
				int row =t.getSelectedRow();
				hm.put("card_time", time[row]);
				System.out.println("card_time = " +time[row]);
				hm.put("card_record_id", card_record_id[row]);
				
				System.out.println("card_record_id = " +card_record_id[row]);
				hm=GUI.getOne(hm);//GetOne是GUI的static函数，调用时要前缀" GUI. "
				if(hm.get("result").equals("success")){
					JOptionPane.showMessageDialog(null, "缴费成功！");
					//t.setValueAt("Yes", row, 3);
					new ActionLis_Paymentquery().actionPerformed(null);
                    
				}
				else {
					JOptionPane.showMessageDialog(null,hm.get("reason"),"缴费失败", JOptionPane.ERROR_MESSAGE);
				}
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
			String password = t_password_confirm1.getText();//密码
			System.out.println(password +" e card mima ");
			hm.put("password", password);
			hm=GUI.getOne(hm);
			if(hm.get("result").equals("success")){
				JOptionPane.showMessageDialog(null, "缴费成功");
			}
			else {
				JOptionPane.showMessageDialog(null, hm.get("reason"),"缴费失败", JOptionPane.ERROR_MESSAGE);
			}
			//---反馈部分交给李某
			//...返回是否充值成功，建议弄一个小弹窗
			//--------------
		}
		 
	 
	}
	
	
	 	 
	





	  class ActionLis_Paymentquery implements ActionListener
	  {

		@Override
		public  void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("card_id", ci);
			
			columnNames=new String[3];
			//表头
			columnNames[0]="学期";
			columnNames[1]="费用";
			columnNames[2]="支付状态";	
			//初始化年级和学期方格的内容

			
		switch(chosePay.getSelectedIndex())
		{
			
		case 0:
			//查询学费表
			hm.put("op", "QueryPayment");	
			hm.put("type", "Tuition");
			System.out.println("now type:"+ hm.get("type"));
			sign="Tuition";//表示交学费
			ArrayList<HashMap<String,String>> TuitionList = GUI.getList(hm);
			//@反馈部分--------
			//...返回学费表的费用和是否缴清两列数据，显示
			rowData=new String[TuitionList.size()][3];
			
			if(TuitionList.size()==0)
				break;
			for(int i=0;i<TuitionList.size();i++)
			{
					rowData[i][0]=TuitionList.get(i).get("card_time");
					rowData[i][1]=TuitionList.get(i).get("card_cost");			
					if(TuitionList.get(i).get("card_is_paid").equals("TRUE")) {
						rowData[i][2]="已支付";
					}
					else {
						rowData[i][2]="未支付";
					}
					card_record_id[i] = TuitionList.get(i).get("card_record_id");
						
			}
			//
			//-----------------
			
			break;
			
		case 1:
			//查询水电费
			hm.put("op", "QueryPayment");
			hm.put("type", "WandE");
			System.out.println("now type:"+ hm.get("type"));
			sign="WandE";//表示交水电费
			//@反馈部分--------
			//...返回水电表的费用和是否缴清两列数据，显示
			//
			//-----------------
			ArrayList<HashMap<String,String>> WandEList = GUI.getList(hm);
			//@反馈部分--------
			//...返回学费表的费用和是否缴清两列数据，显示
			rowData=new String[WandEList.size()][3];
			if(WandEList.size()==0)
				break;
			for(int i=0;i<WandEList.size();i++)
			{
			
				rowData[i][0]=WandEList.get(i).get("card_time");
				rowData[i][1]=WandEList.get(i).get("card_cost");
				
				if(WandEList.get(i).get("card_is_paid").equals("TRUE")) {
					rowData[i][2]="Yes";
				}
				else {
					rowData[i][2]="No";
				}
				card_record_id[i] = WandEList.get(i).get("card_record_id");
			
			}		
			break;
		case 2:
			hm.put("op", "QueryPayment");
			hm.put("type", "Afee");
			System.out.println("now type:"+ hm.get("type"));
			sign="Afee";//表示交住宿费
			//@反馈部分--------
			//...返回住宿表的费用和是否缴清两列数据，显示
			//
			//-----------------
			ArrayList<HashMap<String,String>> AfeeList = GUI.getList(hm);
			//@反馈部分--------
			//...返回学费表的费用和是否缴清两列数据，显示
			rowData=new String[AfeeList.size()][3];
			if(AfeeList.size()==0)
				break;
			for(int i=0;i<AfeeList.size();i++)
			{
				rowData[i][0]=AfeeList.get(i).get("card_time");			
				rowData[i][1]=AfeeList.get(i).get("card_cost");		
				if(AfeeList.get(i).get("card_is_paid").equals("TRUE")) {
					rowData[i][2]="Yes";
				}
				else {
					rowData[i][2]="No";
				}
				card_record_id[i] = AfeeList.get(i).get("card_record_id");
		
			}		
			break;
			
		default:
				break;

		}
		//创建表格
		sp=new JScrollPane();
		t=new JTable(rowData,columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//bank.t.setPreferredSize(new Dimension(450,300));
		t.setRowHeight(40);
		sp.setViewportView(t);
		sp.setPreferredSize(new Dimension(600,300));
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



 