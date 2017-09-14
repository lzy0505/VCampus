package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import client.Library.ActionLis_RepaintHomeScreen;

public class LibManage {
	HomeScreen homeScreen=null;
	JTabbedPane tab_library;//tab面板
	JPanel  p_lib;//中间面板
	JButton return_uplib1;//返回按钮
	JButton return_uplib2;
	
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
	JPanel add_p[];
	JPanel p_add;
	JButton ok_add;//确认增加书籍的按钮
	
	
	//书籍删除界面
	boolean flag;
	JPanel p_delete;//删除书籍面板
	JPanel delete_p[];//用于删除的三个面板
	String[] columnNames_delete;//删除表表头
	String[][] rowData_Delete;//删除书的数据
	JLabel l_delete_book_name;//显示书名的标签
	JTextField t_keyWord;//关键词编辑框
	JButton b_query;//查询按钮
    JTable  table_delete;//删除书显示表格
    JButton b_delete_single;//删除书按钮（单本）
    JButton b_delete_all;//同一种书全部删除
	
	public LibManage(HomeScreen hs){
		homeScreen=hs;
	}
	
	//绘制管理图书馆的函数 
	 void init() {
		
			
			//加书标签界面
			return_uplib1=new JButton("return");//返回到上一层按钮
			p_add = new JPanel();
			ok_add = new JButton("Ensure");
			ok_add.setPreferredSize(new Dimension(70, 30));
			l_book_name =new JLabel("Book Name:");
			book_name = new JTextField();
			book_name.setColumns(25);
			l_author_name =new JLabel("Author Name:");
			author_name = new JTextField();
			author_name.setColumns(25);
			l_publish_name = new JLabel("Publish :");
			publish_name = new JTextField();
			publish_name.setColumns(25);
			l_add_quantity = new JLabel("Number :");
			add_quantity = new JTextField();
			add_quantity.setColumns(25);
			add_p = new JPanel[6];
			for(int i=0;i<6;i++) {
				add_p[i] = new JPanel();
			}
			add_p[0].setLayout(new FlowLayout());
			add_p[0].add(l_book_name);
			add_p[0].add(book_name);
			add_p[1].add(l_author_name);
			add_p[1].add(author_name);
			add_p[2].add(l_publish_name);
			add_p[2].add(publish_name);
			add_p[3].add(l_add_quantity);
			add_p[3].add(add_quantity);
			add_p[4].add(ok_add);
			add_p[5].add(return_uplib1);
			p_add.setLayout(new BoxLayout(p_add,BoxLayout.Y_AXIS));
			for(int i=0;i<6;i++) {
				p_add.add(add_p[i]);
			}
			
			//删除书籍界面
			flag = false;
			p_delete = new JPanel();
			p_delete.setLayout(new BoxLayout(p_delete, BoxLayout.Y_AXIS));
			delete_p = new JPanel[4];
			for(int i=0;i<3;i++) {
				delete_p[i]= new JPanel();
			}
			columnNames_delete =new String[4];
			columnNames_delete[0]="Book Name";
			columnNames_delete[1]="Author";
			columnNames_delete[2]="Publish";
			columnNames_delete[3]="Quantity";
			return_uplib2=new JButton("Return");//返回主界面的按钮
			t_keyWord=new JTextField();//关键词编辑框
			t_keyWord.setPreferredSize(new Dimension(170, 28));
			b_query = new JButton("Query");//查询按钮
			b_query.setPreferredSize(new Dimension(80, 32));
			b_delete_single = new JButton("Delete single");
			b_delete_all = new JButton("Delete all");
			b_delete_all.setVisible(false);
			b_delete_single.setVisible(false);
			
			delete_p[0].add(t_keyWord);
			delete_p[0].add(b_query);
			delete_p[3].add(b_delete_single);
			delete_p[3].add(b_delete_all);
			delete_p[4].add(return_uplib2);
			
			for(int i=0;i<4;i++) {
				p_delete.add(delete_p[i]);
			}
			
			//将删除和加书加到tabbed中
			tab_library = new JTabbedPane();
			tab_library.addTab("Add", p_add);
			tab_library.addTab("Delete",p_delete);
			tab_library.setPreferredSize(new Dimension(500,450));
			p_lib = new JPanel();//顶层面板
			addLis();
			
	 }
	 
	 
	 void addLis()
	 {
		 b_query.addActionListener(new ActionLis_Query());//查询按钮
		 ok_add.addActionListener(new ActionLis_Add_Ok());//确认增加书按钮
		 b_delete_single.addActionListener(new ActionLis_delete_single());//删除单个
		 b_delete_all.addActionListener(new ActionLis_b_delete_all);//都删啦
		 tab_library.addChangeListener(new ChangeLis_Tab());
		 return_uplib1.addActionListener(new ActionLis_RepaintHomeScreen());//返回主界面
		 return_uplib2.addActionListener(new ActionLis_RepaintHomeScreen());//返回主界面	 
	 }
	 
	/* void paint()
		{
			
			 homeScreen.G5.getContentPane().removeAll();
			 homeScreen.G5.setTitle("Library");
			 homeScreen.G5.getContentPane().add(p_library);
			 homeScreen.G5.getContentPane().repaint();
			 homeScreen.G5.getContentPane().revalidate();
			
			
		}
	 */
	 
	 
	//各种消息响应函数及中间函数
	 class ActionLis_Query implements ActionListener
	 {
		 public void actionPerformed(ActionEvent e) {
			 
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
	
	
		
	 
	 
	 
	 
	 
	 
}
