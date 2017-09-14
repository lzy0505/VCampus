package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class HSAdmin {
    //传进来的参数
	String ci;
	//部件
    JFrame f_admin;
    JTabbedPane tab;
	/*JPanel p_admin;
	JPanel admin_p1;
	JPanel admin_p2;
	JLabel l_library;
	JLabel l_fee;
	JLabel l_store;
	*/
	//缴费管理
	JPanel p_fee;
	JPanel fee_p1,fee_p2,fee_p3;	
	JLabel l_studentCi;
	JTextField t_studentCi;
	JComboBox<String> chosePay;//选择缴费的种类
	JButton b_fee_query;//查询相应缴费项目欠款金额的按钮
	JTable table_fee;
	String[]columnNames;
	String[][]rowData;
	String sign;
	JButton b_fee_modify;//确认更改按钮
	
	
	

	
	public void init()
	{
         f_admin=new JFrame("Admin Management");
         tab=new JTabbedPane();
        /* p_admin=new JPanel();
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
		 f_admin.add(p_admin);*/
		 f_admin.setSize(500, 500);
		 f_admin.setLocation(GUI.getWidth(f_admin.getWidth()),GUI.getHeight(f_admin.getHeight()));
		  
		 //费用管理界面
		 
		    p_fee =new JPanel();
		    p_fee.setLayout(new BoxLayout(p_fee,BoxLayout.Y_AXIS));
			fee_p1=new JPanel();
			fee_p2=new JPanel();
			fee_p3=new JPanel();
			l_studentCi=new JLabel("Stdudent_card ID : ");
			t_studentCi=new JTextField();
			t_studentCi.setPreferredSize(new Dimension(150,20));
			chosePay=new JComboBox<String>();//选择缴费的种类
			b_fee_query=new JButton("Query");//查询相应缴费项目欠款金额的按钮
			b_fee_query.setPreferredSize(new Dimension(70,30));
			//JTable t_fee;
			b_fee_modify=new JButton("Modify");//确认更改按钮
			b_fee_modify.setPreferredSize(new Dimension(70,30));
			fee_p1.add(l_studentCi);
			fee_p1.add(t_studentCi);
			fee_p1.add(b_fee_query);			
			fee_p3.add(b_fee_modify);
			p_fee.add(fee_p1);
			p_fee.add(fee_p2);
			p_fee.add(fee_p3);
			tab.addTab("Fee Manage",p_fee);
			tab.setPreferredSize(new Dimension(300,280));
		    
         addLis();//增加监听函数
        
	}
	
	void addLis()
	{
		
		//b_fee_query.addActionListener(new ActLis_fee_query());
		
		
		
	}
	
	void update(String card_id)
	{
		ci=card_id;
		
	}
	void paint()
	{
		f_admin.getContentPane().removeAll();
		f_admin.getContentPane().add(tab);
		f_admin.getContentPane().repaint();
		f_admin.getContentPane().revalidate();
	
	}
	//各种消息映射类
	/*
	class ActLis_fee_query implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("card_id", ci);
			
			columnNames=new String[3];
			//表头
			columnNames[0]="Semester";
			columnNames[1]="Cost";
			
			//columnNames[2]="Has paid?";	
			//初始化年级和学期方格的内容

			
		switch(chosePay.getSelectedIndex())
		{
			
		case 0:
			//查询学费表
			hm.put("op", "QueryPayment");	
			hm.put("type", "Tuition");
			System.out.println("now type:"+ hm.get("type"));
			sign="Tuition";
			columnNames[1]="Tution";
			ArrayList<HashMap<String,String>> TuitionList = GUI.getList(hm);
			//@反馈部分--------
			//...返回学费表的费用
			rowData=new String[TuitionList.size()][3];
			
			for(int i=0;i<TuitionList.size();i++)
			{
					rowData[i][0]=TuitionList.get(i).get("card_time");
					rowData[i][1]=TuitionList.get(i).get("card_cost");
					
				
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
			columnNames[1]="Water and electricty fee";
			//@反馈部分--------
			//...返回水电表的费用和是否缴清两列数据，显示
			//
			//-----------------
			ArrayList<HashMap<String,String>> WandEList = GUI.getList(hm);
			//@反馈部分--------
			//...返回学费表的费用和是否缴清两列数据，显示
			rowData=new String[WandEList.size()][3];
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
			columnNames[1]="Accommodation fee";
			//@反馈部分--------
			//...返回住宿表的费用和是否缴清两列数据，显示
			//
			//-----------------
			ArrayList<HashMap<String,String>> AfeeList = GUI.getList(hm);
			//@反馈部分--------
			//...返回学费表的费用和是否缴清两列数据，显示
			rowData=new String[AfeeList.size()][3];
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
	
*/
	
	
	
	
	
	
	
	

}
