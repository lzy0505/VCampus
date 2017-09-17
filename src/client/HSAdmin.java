package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class HSAdmin {
    //传进来的参数
	String ci;
	LibManage lm;
	StoreManage sManage=null;
	//部件
    JFrame f_admin;
    JTabbedPane tab;

	//缴费管理
	JPanel p_fee;
	JPanel fee_p1,fee_p2,fee_p3;	
	JLabel l_studentCi;
	int rowCount;//保存行数
	JTextField t_studentCi;
	String[] card_record_id;
	JComboBox<String> chosePay;//选择缴费的种类
	JButton b_fee_query;//查询相应缴费项目欠款金额的按钮
	JTable table_fee;
	JScrollPane sp1;
	String[]columnNames;
	String[][]rowData;
	String sign;
	JButton b_fee_modify;//确认更改按钮
	
	
	public HSAdmin()
	{
	}

	
	public void init()
	{
		ci=ClientInfo.getCi();
         f_admin=new JFrame("管理员界面");
         JPanel bg=new JPanel();
         tab=new JTabbedPane();
      
		 f_admin.setSize(500, 500);
		 f_admin.setLocation(GUI.getWidth(f_admin.getWidth()),GUI.getHeight(f_admin.getHeight()));
		 f_admin.getLayeredPane().getComponent(1).setFont(new Font("Microsoft YaHei UI", Font.BOLD, 15));
		 //费用管理界面
		 lm=new LibManage();
		 lm.init();
		 sManage=new StoreManage();
		 sManage.init();
		    p_fee =new JPanel();
		    p_fee.setLayout(new BoxLayout(p_fee,BoxLayout.Y_AXIS));
			fee_p1=new JPanel();
			fee_p2=new JPanel();
			fee_p3=new JPanel();
			l_studentCi=new JLabel("一卡通号");
			t_studentCi=new JTextField();
			t_studentCi.setPreferredSize(new Dimension(150,30));
			card_record_id=new String[8];
			chosePay=new JComboBox<String>();//选择缴费的种类
			chosePay.addItem("学费");//学费选项
		 	chosePay.addItem("水电费");//水电费
		 	chosePay.addItem("住宿费");//住宿费	 	
			b_fee_query=new JButton("查询");//查询相应缴费项目欠款金额的按钮
			b_fee_query.setPreferredSize(new Dimension(70,30));
			//JTable t_fee;
			b_fee_modify=new JButton("更改");//确认更改按钮
			b_fee_modify.setPreferredSize(new Dimension(70,30));
			fee_p1.add(l_studentCi);
			fee_p1.add(t_studentCi);
			fee_p1.add(chosePay);
			fee_p1.add(b_fee_query);			
			fee_p3.add(b_fee_modify);
			p_fee.add(fee_p1);
			p_fee.add(fee_p2);
			p_fee.add(fee_p3);
			tab.addTab("费用管理",p_fee);
			tab.addTab("添加图书",lm.addMain);
			tab.addTab("删除图书",lm.p_delete);
			tab.addTab("商店管理", sManage.tab);
//			tab.setPreferredSize(new Dimension(300,280));
			bg.add(tab);
			f_admin.add(bg);
         addLis();//增加监听函数
         f_admin.setVisible(true);
        
	}
	
	void addLis()
	{
		
		b_fee_query.addActionListener(new ActLis_fee_query());
		b_fee_modify.addActionListener(new ActLis_fee_modify());
		
		
	}




	//各种消息映射类
	class ActLis_fee_modify implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("op","fee_modify");
			//hm.put("card_id", t_studentCi.getText());
			hm.put("count", String.valueOf(rowCount));//表示要传入几个数据
			for(int i=0;i<rowCount;i++)
			{			
			hm.put("card_record_id"+i, card_record_id[i]);//将唯一标识码存入
			hm.put("fee"+i, rowData[i][1]);//将修改后的金额放入			
			}
			hm=GUI.getOne(hm);
			
		 		String result=hm.get("result"); 
		 		System.out.println(result);
		    	JOptionPane.showMessageDialog(null,result,"操作结果",JOptionPane.INFORMATION_MESSAGE);

			
		}
		

		
	}
	class ActLis_fee_query implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			HashMap<String,String> hm=new HashMap<String,String>();
			if(t_studentCi.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"非法的一卡通号","操作错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			hm.put("card_id", t_studentCi.getText());
			
			columnNames=new String[2];
			//表头
			columnNames[0]="学期";
			columnNames[1]="费用";
				
			//初始化年级和学期方格的内容

			
		switch(chosePay.getSelectedIndex())
		{
			
		case 0:
			//查询学费表
			hm.put("op", "QueryPayment");	
			hm.put("type", "Tuition");
			System.out.println("now type:"+ hm.get("type"));
			sign="Tuition";
			ArrayList<HashMap<String,String>> TuitionList = GUI.getList(hm);
			if(!TuitionList.isEmpty()&&TuitionList.get(0).containsKey("result")) {
				JOptionPane.showMessageDialog(null, TuitionList.get(0).get("result"),"操作结果", JOptionPane.ERROR_MESSAGE);
				break;
			}else {
			//@反馈部分--------
			//...返回学费表的费用
				rowData=new String[TuitionList.size()][2];
				rowCount=TuitionList.size();
				System.out.println(TuitionList.size());
				for(int i=0;i<TuitionList.size();i++)
				{
						rowData[i][0]=TuitionList.get(i).get("card_time");
						rowData[i][1]=TuitionList.get(i).get("card_cost");			
						card_record_id[i] = TuitionList.get(i).get("card_record_id");
							
				}
			}
			//
			//-----------------
			
			break;
			
		case 1:
			//查询水电费
			hm.put("op", "QueryPayment");
			hm.put("type", "WandE");
			System.out.println("now type:"+ hm.get("type"));
			sign="WandE";//表示操作类型是水电费
			//@反馈部分--------
			//...返回水电表的费用
			//
			//-----------------
			ArrayList<HashMap<String,String>> WandEList = GUI.getList(hm);
			if(!WandEList.isEmpty()&&WandEList.get(0).containsKey("result")) {
				JOptionPane.showMessageDialog(null, WandEList.get(0).get("result"),"操作结果", JOptionPane.ERROR_MESSAGE);
				break;
			}else {
				rowData=new String[WandEList.size()][2];
				rowCount=WandEList.size();
				for(int i=0;i<WandEList.size();i++)
				{
				
					rowData[i][0]=WandEList.get(i).get("card_time");
					rowData[i][1]=WandEList.get(i).get("card_cost");
		
					card_record_id[i] = WandEList.get(i).get("card_record_id");
				
				}	
			}
			break;
		case 2:
			hm.put("op", "QueryPayment");
			hm.put("type", "Afee");
			System.out.println("now type:"+ hm.get("type"));
			sign="Afee";//表示操作类型是住宿费
			//@反馈部分--------
			//...返回住宿表的费用
			//-----------------
			ArrayList<HashMap<String,String>> AfeeList = GUI.getList(hm);
			if(!AfeeList.isEmpty()&&AfeeList.get(0).containsKey("result")) {
				JOptionPane.showMessageDialog(null, AfeeList.get(0).get("result"),"操作结果", JOptionPane.ERROR_MESSAGE);
				break;
			}else {
				rowData=new String[AfeeList.size()][2];
				rowCount=AfeeList.size();
				for(int i=0;i<AfeeList.size();i++)
				{
					rowData[i][0]=AfeeList.get(i).get("card_time");			
					rowData[i][1]=AfeeList.get(i).get("card_cost");		
					card_record_id[i] = AfeeList.get(i).get("card_record_id");
			
				}
			}
			break;
			
		default:
				break;

		}
		//创建表格
		sp1=new JScrollPane();
		if(rowData==null) return;
		table_fee=new JTable(rowData,columnNames) {
			public boolean isCellEditable(int row, int column) {
				if(column==1)
					return true;
				if(column==0)
					return false;
				else
					return false;
			}
		};
		table_fee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//bank.t.setPreferredSize(new Dimension(450,300));
		table_fee.setRowHeight(40);
		sp1.setViewportView(table_fee);
		sp1.setPreferredSize(new Dimension(450,300));
		fee_p2.removeAll();
		fee_p2.add(sp1);
		fee_p2.repaint();
		fee_p2.revalidate();
		//至少查询一次后将确认密码部分控件设置为可见
	
		b_fee_modify.setVisible(true);
		
		}		
			
			
		}
		

		
	
	
	
	
	
	
	
	
	

}
