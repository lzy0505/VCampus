package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import client.Library.ActionLis_Query;
import client.Library.ChangeLis_Tab;
import table_component.SpringUtilities;

public class LibManage {

	
	//加书标签界面
	boolean flushFlag;//对表格是否要进行刷新的判断
	JLabel l_book_name;//显示书名的标签
	JTextField book_name;//书名
	JLabel l_author_name;//作者
	JTextField author_name;
	JLabel l_publish_name;//出版社
	JTextField publish_name;
	JLabel l_add_quantity;//数量
	JTextField add_quantity;
	public JPanel addMain;
	JButton ok_add;//确认增加书籍的按钮
	
	
	//书籍删除界面
	boolean flag;
	String[] book_info_id;
	public JPanel p_delete;//删除书籍面板
	JPanel delete_p[];//用于删除的三个面板
	String[] columnNames_delete;//删除表表头
	String[][] rowData_Delete;//删除书的数据
	JLabel l_delete_book_name;//显示书名的标签
	JTextField t_keyWord;//关键词编辑框
	JButton b_query;//查询按钮
    JTable  table_delete;//删除书显示表格
    JButton b_delete_single;//删除书按钮（单本）
    JButton b_delete_all;//同一种书全部删除
    JScrollPane sp_delete;
	/*public LibManage(HomeScreen hs){
		homeScreen=hs;
	}
	*/
	//绘制管理图书馆的函数 
	 void init() {
		
			
			//加书标签界面
		 	JPanel p_add = new JPanel();
			p_add.setLayout(new SpringLayout());
			ok_add = new JButton("确认");
			ok_add.setPreferredSize(new Dimension(100, 30));
			
			l_book_name =new JLabel("书名",JLabel.RIGHT);
			p_add.add(l_book_name);
			book_name = new JTextField();
			book_name.setPreferredSize(new Dimension(250,40));
			l_book_name.setLabelFor(book_name);
			p_add.add(book_name);
			
			l_author_name =new JLabel("作者",JLabel.RIGHT);
			p_add.add(l_author_name);
			author_name = new JTextField();
			author_name.setPreferredSize(new Dimension(250,40));
			l_author_name.setLabelFor(author_name);
			p_add.add(author_name);
			
			
			l_publish_name = new JLabel("出版社",JLabel.RIGHT);
			p_add.add(l_publish_name);
			publish_name = new JTextField();
			publish_name.setPreferredSize(new Dimension(250,40));
			l_publish_name.setLabelFor(publish_name);
			p_add.add(publish_name);
			
			l_add_quantity = new JLabel("数量",JLabel.RIGHT);
			p_add.add(l_add_quantity);
			add_quantity = new JTextField();
			add_quantity.setPreferredSize(new Dimension(250,40));
			l_add_quantity.setLabelFor(add_quantity);
			p_add.add(add_quantity);
			
			p_add.setPreferredSize(new Dimension(300,300));
			SpringUtilities.makeCompactGrid(p_add, 4, 2, 30, 30, 10, 40);
			
					
			addMain=new JPanel();
			addMain.setLayout(new BoxLayout(addMain,BoxLayout.Y_AXIS));
			addMain.add(p_add);
			addMain.add(ok_add);
			

			
			//删除书籍界面
			flag = false;
			p_delete = new JPanel();
			p_delete.setLayout(new BoxLayout(p_delete, BoxLayout.Y_AXIS));
			delete_p = new JPanel[4];
			for(int i=0;i<4;i++) {
				delete_p[i]= new JPanel();
			}
			columnNames_delete =new String[4];
			columnNames_delete[0]="书名";
			columnNames_delete[1]="作者";
			columnNames_delete[2]="出版社";
			columnNames_delete[3]="库存数量";
			t_keyWord=new JTextField();//关键词编辑框
			t_keyWord.setPreferredSize(new Dimension(170, 28));
			b_query = new JButton("查询");//查询按钮
			b_query.setPreferredSize(new Dimension(80, 32));
			b_delete_single = new JButton("删除单本");
			b_delete_all = new JButton("全部删除");
			b_delete_all.setVisible(false);
			b_delete_single.setVisible(false);
			
			delete_p[0].add(t_keyWord);
			delete_p[0].add(b_query);
			delete_p[2].add(b_delete_single);
			delete_p[2].add(b_delete_all);
			
			for(int i=0;i<4;i++) {
				p_delete.add(delete_p[i]);
			}
			

			addLis();
			
	 }
	 
	 
	 void addLis()
	 {
		 b_query.addActionListener(new ActionLis_Query());//查询按钮
		 ok_add.addActionListener(new ActionLis_Add_Ok());//确认增加书按钮
		 b_delete_single.addActionListener(new ActionLis_delete_single());//删除单个
		 b_delete_all.addActionListener(new ActionLis_delete_all());//都删啦
	 }
	 

	 
	//各种消息响应函数及中间函数
	 class ActionLis_Query implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e) {
				HashMap<String,String> hmlib=new HashMap<String,String>();
			
					//search book according to BookName
					hmlib.put("op", "searchbook");
					hmlib.put("search_type", "name");
					hmlib.put("keyword", t_keyWord.getText().replaceAll("'", "'''"));
									
				ArrayList<HashMap<String,String>> alist = GUI.getList(hmlib);
				int amount=alist.size();
				String[] bookName=new String[amount];
				String[] publisher=new String[amount];//bookName and press
				String[] quantity=new String[amount];
				String[] author=new String[amount];
				book_info_id = new String[amount];
				for(int i=0;i<amount;i++) {
					bookName[i]=alist.get(i).get("book_name");
					author[i]=alist.get(i).get("author");
					publisher[i]=alist.get(i).get("publisher");
					quantity[i]=alist.get(i).get("quantity");
					book_info_id[i] =alist.get(i).get("book_info_id"); 
				}
					
				rowData_Delete=new String[amount][4];
				if(alist.size()>=1)
				{ System.out.println("Query2 ok");
					for(int i=0;i<amount;i++)
					{
						rowData_Delete[i][0]=bookName[i];
						rowData_Delete[i][1]=author[i];
						rowData_Delete[i][2]=publisher[i];
						rowData_Delete[i][3]=quantity[i];
					}
					
					sp_delete=new JScrollPane();
					table_delete=new JTable(rowData_Delete,columnNames_delete) {
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					table_delete.setRowHeight(40);
					sp_delete.setViewportView(table_delete);
					sp_delete.setPreferredSize(new Dimension(450,270));
					delete_p[1].removeAll();
					delete_p[1].add(sp_delete);
					delete_p[1].repaint();
					delete_p[1].revalidate();
	                b_delete_all.setVisible(true);
	                b_delete_single.setVisible(true);
	                delete_p[2].repaint();
			
				}//if the first book is not a null,then show all the message of this book
				else 
				{
					table_delete.setModel(new DefaultTableModel(new String[][] {},new String[] {}));
					JOptionPane.showMessageDialog(null,"未找到该图书",
							"搜索失败",JOptionPane.ERROR_MESSAGE);							  
				}
				
		 }
	 }
	 
	 class ActionLis_Add_Ok implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e) {
			 System.out.println("Add ok");
			 if(!book_name.getText().equals("")&&!author_name.getText().equals("")&&!publish_name.getText().equals("")&&!add_quantity.getText().equals("")) {
				 HashMap<String,String> hmbook=new HashMap<String,String>();
				 hmbook.put("op", "add_book");
				 hmbook.put("book_name","\'"+book_name.getText()+"\'");
				 hmbook.put("author", "\'"+author_name.getText()+"\'");
				 hmbook.put("publisher","\'" +publish_name.getText()+"\'");
				 hmbook.put("quantity", add_quantity.getText());
				 GUI.send(hmbook);
				 JOptionPane.showMessageDialog(null, "添加图书成功！", "操作结果",JOptionPane.INFORMATION_MESSAGE);
				 book_name.setText("");
				 author_name.setText("");
				 publish_name.setText("");
				 add_quantity.setText("");
			 }
			 else {
				 JOptionPane.showMessageDialog(null,"输入不能为空","操作结果",JOptionPane.ERROR_MESSAGE);
			 }
		 }
	 }
	 
	 class ActionLis_delete_single implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e) {
			 HashMap<String,String> hmlib=null;
				boolean flag=false;//标识是否有书被勾选
				boolean flag_delete=false;//标识删除是否成功
				for (int i = 0;i < table_delete.getRowCount();i++) {
					 hmlib=new HashMap<String,String>();
					
					 if(has(table_delete.getSelectedRows(),table_delete.getSelectedRowCount(),i)) {				 
						flag=true;
					 	hmlib.put("book_name", rowData_Delete[i][0]);
					 	hmlib.put("op", "delete_single_book");
					 	hmlib.put("book_info_id", book_info_id[i]);
					 	hmlib=GUI.getOne(hmlib);
					 	if(hmlib.get("result").equals("success"))flag_delete = true;
					 	else flag_delete=false;
					 }
				}
				if(flag&&flag_delete)JOptionPane.showMessageDialog(null,"删除成功！","操作结果",JOptionPane.INFORMATION_MESSAGE);
				if(flag&&!flag_delete)JOptionPane.showMessageDialog(null,"删除失败，此书已经外借","操作结果",JOptionPane.ERROR_MESSAGE);
				//刷新操作,刷新两个表格。
				flushFlag=true;
				new ActionLis_Query().actionPerformed(null);
				flushFlag=false;
				
			 }
			  boolean has(int[] Array,int count,int a)
				{
					for(int i=0;i<count;i++)
					{
						if(Array[i]==a)
							return true;
					}
					return false;
					
					
				}
		 }
	
	 
	 class ActionLis_delete_all implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e) {
			HashMap<String,String> hmlib=null;
			boolean flag=false;//标识是否有书被勾选
			 boolean flag_delete=false;//标识删除是否成功
			for (int i = 0;i < table_delete.getRowCount();i++) {
				 hmlib=new HashMap<String,String>();
				 flag_delete=false;
				 if(has(table_delete.getSelectedRows(),table_delete.getSelectedRowCount(),i)) {				 
					flag=true;
				 	hmlib.put("book_name", rowData_Delete[i][0]);
				 	hmlib.put("op", "delete_all_book");
				 	hmlib.put("book_info_id", book_info_id[i]);
				 	hmlib=GUI.getOne(hmlib);
				 	if(hmlib.get("result").equals("success"))flag_delete = true;
				 }
			}
			if(flag&&flag_delete)JOptionPane.showMessageDialog(null,"删除成功！","操作结果",JOptionPane.INFORMATION_MESSAGE);
			if(flag&&!flag_delete)JOptionPane.showMessageDialog(null,"删除失败，此书已经外借!","操作结果",JOptionPane.ERROR_MESSAGE);
			
			//刷新操作,刷新两个表格。
			flushFlag=true;
			new ActionLis_Query().actionPerformed(null);
			flushFlag=false;
			
		 }
		  boolean has(int[] Array,int count,int a)
			{
				for(int i=0;i<count;i++)
				{
					if(Array[i]==a)
						return true;
				}
				return false;
				
				
			}
			
	 }


}
	 
	 
	 
	 
	 

