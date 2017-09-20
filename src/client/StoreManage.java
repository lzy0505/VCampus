package client;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import com.sun.crypto.provider.HmacMD5;
public class StoreManage {

	//需要传进来父窗口的JFrame
	JFrame f;
	
	//顶层Panel
	JTabbedPane tab;
	
	
	//添加商品
	JPanel p_addGoods;//添加商品面板
	JPanel addGoods_p1,addGoods_p2,addGoods_p3,addGoods_p4;//布局面板
	JLabel l_add_goodsName;//商品名标签
	JLabel l_amount;//商品数量
	JLabel l_price;//商品价格 
	JLabel l_addPicture;//增加商品图片
	JTextField t_goodsName;//商品名输入框
	JTextField t_amount;//商品数量输入框
	JTextField t_price;//商品单价输入框
	JButton b_openFileManagement;//打开文件管理器按钮
	FileDialog fileDialog;//文件管理器
	ImageIcon imageIcon_display;//客户端显示的图片
	ImageIcon imageIcon_pass;//用来传输到服务器的图片
	JLabel l_showImage;//用来显示图片的Label
	String path;//保存文件地址
	JButton b_confirmAdd;//确认添加按钮
	JComboBox<String> cata; 
	

	
	//修改库存或修改价格
	JPanel p_revise;//修改库存面板
	JPanel revise_p1,revise_p2,revise_p3;//三个布局Panel
	JLabel l_amount_GoodsName;//商品名标签
	JTextField t_revise_GoodsName;//商品名输入框
	JComboBox<String> cb_chose;//复选框，选择是修改库存还是价格
	int index;//保存查询时的comboBox的索引
	//String[] item_id_revise;//保存商品唯一标识码
	JButton b_query_Revise;//查询按钮
	JTable table_revise;//表格
	String[] columnNames_revise;//表格的表头
	String[][] rowData_revise;//表格的数据
	JScrollPane sp_revise;
	JButton b_confirmRevise;//确认修改按钮
	
	
	//删除某件商品
    
    JPanel p_delete;
    JPanel delete_p1,delete_p2,delete_p3;//布局面板
    JLabel l_delete_GoodsName;//商品名标签
    JTextField t_delete_GoodsName;//商品名输入框
    String[] url_delete;//保存商品唯一标识码 
    JButton b_query_Delete;//查询按钮
    JTable table_delete;//表格
    JScrollPane sp_delete;
    String[]columnNames_delete;//表格的表头
    String[][]rowData_delete;//表格数据
    JButton b_confirmDelete;//确认修改按钮
	

	//初始化函数
	void init()
	{
		
		//添加新商品
		
		p_addGoods=new JPanel();//添加商品面板
		p_addGoods.setLayout(new BoxLayout(p_addGoods,BoxLayout.Y_AXIS));
		addGoods_p1=new JPanel();
		addGoods_p2=new JPanel();
		addGoods_p3=new JPanel();
		addGoods_p4=new JPanel();
		l_add_goodsName=new JLabel("商品名称");//商品名标签
		l_amount=new JLabel("数量");//商品数量
		l_price=new JLabel("单价");//商品价格
		l_addPicture=new JLabel("添加图片");//增加商品图片
		t_goodsName=new JTextField();
		t_goodsName.setPreferredSize(new Dimension(270,30));
		t_amount=new JTextField();
		t_amount.setPreferredSize(new Dimension(170,30));
		t_price=new JTextField();
		t_price.setPreferredSize(new Dimension(170,30));
		b_openFileManagement=new JButton("浏览文件管理器..");//打开文件管理器按钮
		fileDialog=new FileDialog(f,"添加图片",FileDialog.LOAD);//初始化文件管理器	
		b_confirmAdd=new JButton("确认添加商品");//确认添加按钮
	    addGoods_p1.add(l_add_goodsName);
	    addGoods_p1.add(t_goodsName);
	    addGoods_p1.add(l_amount);
	    addGoods_p1.add(t_amount);
	    
	    
	    
	    JPanel addGoods_p5=new JPanel();
	    addGoods_p5.add(new JLabel("商品类别"));
	    cata=new JComboBox<String>(new String[] {"食品","电子产品","日用品","饮料"});
	    cata.setPreferredSize(new Dimension(270,30));
	    addGoods_p5.add(cata);
	    
	    addGoods_p5.add(l_price);
	    addGoods_p5.add(t_price);
	    

	    Component[] cArray=addGoods_p1.getComponents();
		for(int j=0;j<addGoods_p1.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
	    addGoods_p2.add(l_addPicture);
	    addGoods_p2.add(b_openFileManagement);
	    cArray=addGoods_p2.getComponents();
		for(int j=0;j<addGoods_p2.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
		cArray=addGoods_p5.getComponents();
		for(int j=0;j<addGoods_p5.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
		addGoods_p4.add(b_confirmAdd);
		cArray=addGoods_p4.getComponents();
		for(int j=0;j<addGoods_p4.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
		p_addGoods.add(new JLabel(" "));
		p_addGoods.add(new JLabel(" "));
		p_addGoods.add(new JLabel(" "));
		p_addGoods.add(addGoods_p1);
		p_addGoods.add(addGoods_p5);
		p_addGoods.add(addGoods_p2);
		p_addGoods.add(addGoods_p3);
		p_addGoods.add(addGoods_p4);
		
		
		//修改库存或价格
		p_revise=new JPanel();//修改库存面板
		p_revise.setLayout(new BoxLayout(p_revise,BoxLayout.Y_AXIS));
	    l_amount_GoodsName=new JLabel("商品名称");//商品名标签
		t_revise_GoodsName=new JTextField();//商品名输入框
		t_revise_GoodsName.setPreferredSize(new Dimension(270,30));
		cb_chose=new JComboBox<String>();
		cb_chose.addItem("库存数量");
		cb_chose.addItem("商品价格");
		b_query_Revise=new JButton("查询");//查询按钮		
		columnNames_revise=new String[3];
		columnNames_revise[0]="商品名称";	
		index=0;
		b_confirmRevise=new JButton("确认修改");//确认修改按钮	
		revise_p1=new JPanel();
		revise_p1.add(l_amount_GoodsName);
		revise_p1.add(t_revise_GoodsName);
		revise_p1.add(cb_chose);
		revise_p1.add(b_query_Revise);
		cArray=revise_p1.getComponents();
		for(int j=0;j<revise_p1.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
		revise_p2=new JPanel();
		revise_p3=new JPanel();
		revise_p3.add(b_confirmRevise);
		cArray=revise_p3.getComponents();
		for(int j=0;j<revise_p3.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
		p_revise.add(new JLabel(" "));
		p_revise.add(revise_p1);
        p_revise.add(revise_p2);
        p_revise.add(revise_p3);
        
        
        
        //删除商品
        p_delete=new JPanel();
        p_delete.setLayout(new BoxLayout(p_delete,BoxLayout.Y_AXIS));
        delete_p1=new JPanel();
        delete_p2=new JPanel();
        delete_p3=new JPanel();
        l_delete_GoodsName=new JLabel("商品名称");
        t_delete_GoodsName=new JTextField();
        t_delete_GoodsName.setPreferredSize(new Dimension(270,30));
        b_query_Delete=new JButton("查询");    
        columnNames_delete=new String[3];
        columnNames_delete[0]="商品名称";
        columnNames_delete[1]="商品价格";
        columnNames_delete[2]="商品库存";
        b_confirmDelete=new JButton("确认删除");//确认修改
        delete_p1.add(l_delete_GoodsName);
        delete_p1.add(t_delete_GoodsName);
        delete_p1.add(b_query_Delete);
        cArray=delete_p1.getComponents();
		for(int j=0;j<delete_p1.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
        delete_p3.add(b_confirmDelete);
        cArray=delete_p3.getComponents();
		for(int j=0;j<delete_p3.getComponentCount();j++){
			cArray[j].setFont(new Font("黑体", Font.PLAIN, HSAdmin.fontsize));
		}
		p_delete.add(new JLabel(" "));
        p_delete.add(delete_p1);
        p_delete.add(delete_p2);
        p_delete.add(delete_p3);
        

		addLis();//增加消息响应
			
	}
	//传主界面的frame,作为文件资源管理器初始化时的父窗口参数
	void update(JFrame frame)
	{
		f=frame;
	}
	//添加消息映射函数
	void addLis()
	{
		
		b_openFileManagement.addActionListener(new ActionLis_openFileManagement());//点击打开文件资源管理器（已完成）
		b_confirmAdd.addActionListener(new ActionLis_confirmAdd());//确认添加新商品按钮
		b_query_Revise.addActionListener(new ActionLis_queryRevise());//修改库存或价格界面的“查询”按钮
		b_confirmRevise.addActionListener(new ActionLis_confirmRevise());//确认修改按钮
		b_query_Delete.addActionListener(new ActionLis_queryDelete());//删除商品界面的“查询”按钮
		b_confirmDelete.addActionListener(new ActionLis_confirmDelete());//确认删除按钮
		
		
	}	
	//各种消息映射函数
	
	//确认删除按钮的响应
	class ActionLis_confirmDelete implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(t_delete_GoodsName.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"未填入商品名称","操作结果",JOptionPane.WARNING_MESSAGE);
				return;
			}
			int selectedRow=table_delete.getSelectedRow();//得到用户所选的行索引
			//如果什么都没选
			if(selectedRow==-1)
			{			
				JOptionPane.showMessageDialog(null,"未选择商品","操作结果",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("op", "DeleteGoods");
			hm.put("item_picture_url",url_delete[selectedRow]);	//传递商品的图片路径
			hm.put("item_name", rowData_delete[selectedRow][0]);
			GUI.send(hm);
			//刷新表格
			if(hm.get("result")=="success")
			{
				//如果成功修改，刷新表格
				new ActionLis_queryDelete().actionPerformed(null);
			}
			
		}
	
	}
	
	
	
	
	
	//删除商品界面的查询按钮
	class ActionLis_queryDelete implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("op", "GoodsInfoQuery");
			hm.put("item_name", t_delete_GoodsName.getText());//传递商品名

			//要求返回满足条件（名称相符）的商品数量、商品单价、商品唯一标识码
			ArrayList<HashMap<String,String>> list = GUI.getList(hm);
			int validRowCount=list.size();//需要显示的商品种类数量
			rowData_delete=new String[validRowCount][3];
			url_delete=new String[validRowCount];
			for(int i=0;i<validRowCount;i++)
			{
				rowData_delete[i][0]=list.get(i).get("item_name");
				rowData_delete[i][1]=list.get(i).get("item_price");
				rowData_delete[i][2]=list.get(i).get("item_stock");
				url_delete[i]=list.get(i).get("item_picture_url");		
			}
			
			table_delete=new JTable(rowData_delete,columnNames_delete) {
    			public boolean isCellEditable(int row, int column) {
    				
    					return false;
    					
    			}
    		};
    		table_delete.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//只可以单选
    		table_delete.setRowHeight(40);
    		sp_delete=new JScrollPane();
    		sp_delete.setViewportView(table_delete);
    		sp_delete.setPreferredSize(new Dimension(HSAdmin.width*5/6,HSAdmin.height*2/3));
    		delete_p2.removeAll();
			delete_p2.add(sp_delete);
			delete_p2.repaint();
			delete_p2.revalidate();
	
		}
	}
	
	
	
	
	class ActionLis_confirmAdd implements ActionListener
	
	{
     	@Override
		public void actionPerformed(ActionEvent e) {
			//传递商品名 t_goodsName.getText()，数量t_amount.getText()，单价t_price.getText()
     		//和图片,图片就是ImageIcon型变量imageIcon_pass，大小96*96
     		HashMap<String, String> up_load = new HashMap<>();
     		up_load.put("item_name",t_goodsName.getText());
     		up_load.put("item_stock", t_amount.getText());
     		up_load.put("item_price", t_price.getText());
     		//TODO 这里加一个商品类别  cata.getItemAt(cata.getSelectedIndex());
     		 
     		up_load.put("item_picture_url", path);
     		up_load = GUI.upLoad(up_load);
			if(up_load.get("result").equals("success")) {
				JOptionPane.showMessageDialog(null, "新商品上传成功！");
			}else {
				JOptionPane.showMessageDialog(null,"新商品上传失败，原因：" + up_load.get("reason"));
			}
			
		}
	
	}
	
	
      //修改界面的确认修改按钮
	class ActionLis_confirmRevise implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(t_revise_GoodsName.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"未填入商品名称","操作结果",JOptionPane.WARNING_MESSAGE);
				return;
			}
			int selectedRow=table_revise.getSelectedRow();
			//如果啥都没选，报警
			if(selectedRow==-1)
			{
				JOptionPane.showMessageDialog(null,"请选择需要修改的商品","操作提示",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			HashMap<String,String> hm=new HashMap<String,String>();
			hm.put("item_name", rowData_revise[selectedRow][0]);//传递修改商品的item_name
			//如果修改库存
			if(index==0)
			{
			hm.put("op","GoodsAmountRevise");			
			double amount_afterRevise=Double.parseDouble(rowData_revise[selectedRow][1])+Double.parseDouble(rowData_revise[selectedRow][2]);
			hm.put("item_stock",String.valueOf(amount_afterRevise));//传递修改后的数量
			}
			//如果修改单价
			else if(index==1)
			{
				hm.put("op","GoodsPriceRevise");			
				hm.put("item_price", rowData_revise[selectedRow][2]);
			}
			
			GUI.send(hm);
			if(hm.get("Result")=="success")
			{
				//如果成功修改，刷新表格
				new ActionLis_queryRevise().actionPerformed(null);
				
				
			}
			
	
			
		}

		
	}
	
	

	//修改界面的查询按钮
	class ActionLis_queryRevise implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			HashMap<String,String> hm=new HashMap<String,String>();
			//如果查询库存数量
			if(cb_chose.getSelectedIndex()==0)
			{
			hm.put("op", "QueryGoodsAmount");
			columnNames_revise[1]="现有库存数量";
			columnNames_revise[2]="增加数量";	
			}
			//如果查询价格
			else if(cb_chose.getSelectedIndex()==1)
			{
				hm.put("op", "QueryGoodsPrice");
				columnNames_revise[1]="现在单价";
				columnNames_revise[2]="修改单价为";	
			}
			hm.put("item_name", t_revise_GoodsName.getText());//商品名传递过去（商品名可能不全，涉及模糊查找）
			//服务器返回满足条件的商品List,要求返回商品的名称，现有库存或价格，唯一标识item_id
			ArrayList<HashMap<String,String>> list = GUI.getList(hm);
			int validRowCount=list.size();//list返回的条数
			rowData_revise=new String[validRowCount][3];//根据list.size()初始化rowData
			//item_id_revise=new String[validRowCount];//初始化item_id_revise数组用于查询后保存商品唯一标识符
			
			for(int i=0;i<validRowCount;i++)
			{			
				rowData_revise[i][0]=list.get(i).get("item_name");
				//如果选择了查询库存,第二列要get quantity
				if(cb_chose.getSelectedIndex()==0)
				{	
				rowData_revise[i][1]=list.get(i).get("item_stock");
				index=0;
				}
				//如果选择查询价格,第二列 get price
				else if(cb_chose.getSelectedIndex()==1)
				{
					
				rowData_revise[i][1]=list.get(i).get("item_price");	
				index=1;
				}
				
				rowData_revise[i][2]="0";//第三列初始化为0
				//item_id_revise[i]=list.get(i).get("item_id");//得到唯一的item_id
			}
            table_revise=new JTable(rowData_revise,columnNames_revise) {
    			public boolean isCellEditable(int row, int column) {
    				//只有第三列能修改
    				if(column==2)
    				{
    					return true;
    				}
    				else 
    					return false;
    					
    			}
    		};
    		table_revise.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
    		table_revise.setRowHeight(40);
    		sp_revise=new JScrollPane();
    		sp_revise.setViewportView(table_revise);
    		sp_revise.setPreferredSize(new Dimension(HSAdmin.width*5/6,HSAdmin.height*2/3));
    		revise_p2.removeAll();
			revise_p2.add(sp_revise);
			revise_p2.repaint();
			revise_p2.revalidate();
				
			
		}
		
		

	}
	
	
	
	
	class ActionLis_openFileManagement implements ActionListener
	
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("okFile");
			fileDialog.setVisible(true);
			path=fileDialog.getDirectory()+fileDialog.getFile();//获得路径
			System.out.println(path);
			imageIcon_display=new ImageIcon(path);
			Image image=imageIcon_display.getImage();
			image=image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			imageIcon_display=new ImageIcon(image);
			l_showImage=new JLabel(imageIcon_display);
			l_showImage.setPreferredSize(new Dimension(200,200));
			
			//这里规格化要传送的图片大小
			image=image.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
			imageIcon_pass=new ImageIcon(image);
					
			addGoods_p3.removeAll();
			addGoods_p3.add(l_showImage);
			addGoods_p3.repaint();
			addGoods_p3.revalidate();
	
			
		}
		
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}