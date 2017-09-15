package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Library {
	

	    //需要通过传参进来的数据
	   String ci;//用户名
	   HomeScreen homeScreen;//主面板，用于重绘
	   
	    //the elements of Library 
	    JPanel p_library; //顶层面板
	    JButton return_uplib1;//返回主界面的按钮
	    JButton return_uplib2;
	    //借书部分
	    boolean flag;
	    JPanel p_borrow;//借书面板
	    JPanel borrow_p[];//用于布局的四个面板
	    JTabbedPane tab_library;//tab面板
	    String[] columnNames_Borrow;//借书表的表头
	    String[][] rowData_Borrow;//借书的数据
	    JLabel l_method;//“检索方式”的标签
	    JComboBox<String>  choseMethod;//检索方式的复选框
	    JTextField t_keyWord;//关键词编辑框
	    JButton b_query;//查询按钮
	    JTable  table_borrow;//借书显示表格
	    JScrollPane sp_borrow;
	    JButton b_borrow;//借书按钮
	    
	    
	    //还书部分
	    String[] book_id;//保存书本的唯一id
	    boolean flushFlag;//对表格是否要进行刷新的判断
	    JPanel p_return;//还书面板
	    JPanel return_p1,return_p2;//用于布局的面板
	    String[] columnNames_Return;//借书表的表头
	    String[][] rowData_Return;//借书的数据
	    JLabel l_return;//“你已借图书有”的按钮
	    JTable table_return;//显示需要归还的书的表格
	    JScrollPane sp_return;
	    JButton b_return;//还书按钮
	    
	public Library(HomeScreen hs)
	{
		homeScreen=hs;
		ci=ClientInfo.getCi();
	}
	void init()
	{  		
	   //借书部分
		flag=false;
	    p_borrow=new JPanel();
	    p_borrow.setLayout(new BoxLayout(p_borrow,BoxLayout.Y_AXIS));
	    borrow_p=new JPanel[4];  
	    for(int i=0;i<4;i++)
	    {
	    	borrow_p[i]=new JPanel();
	    }  
	    columnNames_Borrow=new String[4];
	    columnNames_Borrow[0]="Book Name";
	    columnNames_Borrow[1]="Author";
	    columnNames_Borrow[2]="Publish";
	    columnNames_Borrow[3]="Quantity";

        return_uplib1=new JButton("Return");//返回主界面的按钮
	    l_method=new JLabel("Retrieval method : ");//“检索方式”的标签
	    l_method.setFont(new Font ("Arial", Font.PLAIN , 15));
	    choseMethod=new JComboBox<String>();//检索方式的复选框
	    choseMethod.setPreferredSize(new Dimension(170,25));
	    choseMethod.addItem("Author");
	    choseMethod.addItem("Book Name");	    
	    t_keyWord=new JTextField();//关键词编辑框
	    t_keyWord.setPreferredSize(new Dimension(170,28));    
	    b_query=new JButton("Query");//查询按钮	  
	    b_query.setPreferredSize(new Dimension(80,32));
	    b_borrow=new JButton("Borrow");//借书按钮
	    b_borrow.setVisible(false);
	   
	    borrow_p[0].add(l_method);
	    borrow_p[0].add(choseMethod);
	    borrow_p[1].add(t_keyWord);
	    borrow_p[1].add(b_query);
	    borrow_p[3].add(b_borrow);
	    borrow_p[3].add(return_uplib1);
	    //将四个布局面板加到借书面板
	    p_borrow.add(borrow_p[0]);
	    p_borrow.add(borrow_p[1]);
	    p_borrow.add(borrow_p[2]);
	    p_borrow.add(borrow_p[3]);
    
	    //还书部分
	    flushFlag=false;
	    return_uplib2=new JButton("Return");//返回主界面的按钮
	    p_return=new JPanel();
	    p_return.setLayout(new BoxLayout(p_return,BoxLayout.Y_AXIS));
	    return_p1=new JPanel(); 
	    return_p2=new JPanel();
	    l_return=new JLabel("Borrowed books : ");//“你已借图书有”的按钮	   
	    b_return=new JButton("Return Book");//还书按钮
	    return_p1.add(b_return);
	    return_p1.add(return_uplib2);
	    p_return.add(l_return);
	    p_return.add(return_p2);
	    p_return.add(return_p1);
	    columnNames_Return=new String[3];//借书表的表头
	    columnNames_Return[0]="Book Name";
	    columnNames_Return[1]="Author";
	    columnNames_Return[2]="Publish";
	    
	    
	    //将借书和还书面板加入tabbed中
	    tab_library=new JTabbedPane();
	    tab_library.addTab("Borrow",p_borrow);
	    tab_library.addTab("Return",p_return);
	    tab_library.setPreferredSize(new Dimension(500,450));
	    p_library=new JPanel(); //顶层面板
	    p_library.add(tab_library);
	    addLis();
	    
     
	}

	void addLis()
	{  
		b_query.addActionListener(new ActionLis_Query());//查询按钮
		b_borrow.addActionListener(new ActionLis_Borrow());
		tab_library.addChangeListener(new ChangeLis_Tab());
		return_uplib1.addActionListener(new ActionLis_RepaintHomeScreen());//返回主界面
		return_uplib2.addActionListener(new ActionLis_RepaintHomeScreen());//返回主界面
		b_return.addActionListener(new ActionLis_Return());//还书按钮
	}	
	
	//重绘函数
	void paint()
	{
		
		 homeScreen.G5.getContentPane().removeAll();
		 homeScreen.G5.setTitle("Library");
		 homeScreen.G5.getContentPane().add(p_library);
		 homeScreen.G5.getContentPane().repaint();
		 homeScreen.G5.getContentPane().revalidate();
		
		
	}

	
//	void update(String card_id)
//	{
//		ci=card_id;
//	
//	}
//	
	

	//各种消息响应函数及中间函数
	class ActionLis_Return implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			HashMap<String,String> hmlib=null;
			boolean flag=false;
			HashMap<String,String> hmbook=new HashMap<String,String>();
			for (int i = 0;i < table_return.getRowCount();i++) {
				 hmlib=new HashMap<String,String>();
				 
				 if(has(table_return.getSelectedRows(),table_return.getSelectedRowCount(),i)) {
					flag=true;
				 	hmlib.put("book_name", rowData_Return[i][0]);
				 	hmlib.put("op", "return");
				 	hmlib.put("card_id", ci);
				 	hmlib.put("book_id", book_id[i]);
				 	hmlib=GUI.getOne(hmlib);
				 	hmbook.put(hmlib.get("book_name"),hmlib.get("result"));
				 }
			}
			String[] booknames=new String[hmbook.size()];
			booknames= hmbook.keySet().toArray(booknames);
			String result="";
			for(int i=0;i<hmbook.size();i++){
				result+=(booknames[i]+ " is returned "+hmbook.get(booknames[i])+"!\n");
			}
			if(flag)JOptionPane.showMessageDialog(null,result,"Results",JOptionPane.INFORMATION_MESSAGE);
			
			//刷新操作,刷新两个表格。
			flushFlag=true;
			new ChangeLis_Tab().stateChanged(null);
			new ActionLis_Query().actionPerformed(null);
			flushFlag=false;
			
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
	
	
	class ChangeLis_Tab implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			if(flushFlag==true)
				operation();
			else if(flushFlag==false){
				
			 JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
			 int selectedIndex = tabbedPane.getSelectedIndex();
			 //当点击第二个标签页时
			 if(selectedIndex==1)
			 {
				 operation();
			 }
			}

	}
		
		private void operation()
		{  
			HashMap<String,String> hmlib=new HashMap<String,String>();
			hmlib.put("op", "search_unreturn");
		 	hmlib.put("card_id", ci);
			//ReturnBook(bookNameR,authorR,publisherR,quantityR,alist.size)
		 	ArrayList<HashMap<String,String>> alist = GUI.getList(hmlib);	
			String[] bookNameR = new String[alist.size()];
			String[] publisherR = new String[alist.size()];
			String[] authorR = new String[alist.size()];//R means to return 
			book_id = new String[alist.size()];
			
			for(int i=0;i<alist.size();i++) {
				bookNameR[i]=alist.get(i).get("book_name");
				authorR[i]=alist.get(i).get("author");
				publisherR[i]=alist.get(i).get("publisher");	
				book_id[i] = alist.get(i).get("book_id");
			}
		 	
		
			   int amount=alist.size();
			  
			   
				if(alist.size()>=1)
				{   return_p2.setVisible(true);
				    b_return.setEnabled(true);
				   rowData_Return=new String[amount][3];
					for(int i=0;i<amount;i++)
					{
						rowData_Return[i][0]=bookNameR[i];
						rowData_Return[i][1]=authorR[i];
						rowData_Return[i][2]=publisherR[i];
						//rowData_Return[i][3]=b[i];
					}
					
					sp_return=new JScrollPane();
					table_return=new JTable(rowData_Return,columnNames_Return) {
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					table_return.setRowHeight(40);
					sp_return.setViewportView(table_return);
					sp_return.setPreferredSize(new Dimension(600,400));
					return_p2.removeAll();
					return_p2.add(sp_return);
					return_p2.repaint();
					return_p2.revalidate();	
				}	
			//if the first book is not a null,then show all the message of this book
			else 
			{  
				rowData_Return =new String[1][3];
			sp_return=new JScrollPane();
			table_return=new JTable(rowData_Return,columnNames_Return) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table_return.setRowHeight(40);
			sp_return.setViewportView(table_return);
			sp_return.setPreferredSize(new Dimension(600,400));
			return_p2.removeAll();
			return_p2.add(sp_return);
			return_p2.repaint();
			return_p2.revalidate();	
				
				
				
				      
			 b_return.setEnabled(false);
				 						  
			}
		}

	}
	
	class ActionLis_Query implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{   
		    
			
			System.out.println("Query1 ok");
			HashMap<String,String> hmlib=new HashMap<String,String>();
			if(choseMethod.getSelectedItem() == "Author")
			{
				//search book according to Author
				hmlib.put("op", "searchbook");
				hmlib.put("search_type", "author");
				hmlib.put("keyword", t_keyWord.getText());					
			}
			else if(choseMethod.getSelectedItem() == "Book Name")
			{
				//search book according to BookName
				hmlib.put("op", "searchbook");
				hmlib.put("search_type", "name");
				hmlib.put("keyword", t_keyWord.getText());
			}
			
			ArrayList<HashMap<String,String>> alist = GUI.getList(hmlib);
			int amount=alist.size();
			String[] bookName=new String[amount];
			String[] publisher=new String[amount];//bookName and press
			String[] quantity=new String[amount];
			String[] author=new String[amount];
			
			for(int i=0;i<amount;i++) {
				bookName[i]=alist.get(i).get("book_name");
				author[i]=alist.get(i).get("author");
				publisher[i]=alist.get(i).get("publisher");
				quantity[i]=alist.get(i).get("quantity");
			}
				
			rowData_Borrow=new String[amount][4];
			if(alist.size()>=1)
			{ System.out.println("Query2 ok");
				for(int i=0;i<amount;i++)
				{
					rowData_Borrow[i][0]=bookName[i];
					rowData_Borrow[i][1]=author[i];
					rowData_Borrow[i][2]=publisher[i];
					rowData_Borrow[i][3]=quantity[i];
				}
				
				sp_borrow=new JScrollPane();
				table_borrow=new JTable(rowData_Borrow,columnNames_Borrow) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				table_borrow.setRowHeight(40);
				sp_borrow.setViewportView(table_borrow);
				sp_borrow.setPreferredSize(new Dimension(600,400));
				borrow_p[2].removeAll();
				borrow_p[2].add(sp_borrow);
				borrow_p[2].repaint();
				borrow_p[2].revalidate();
                b_borrow.setVisible(true);
                borrow_p[3].repaint();
		
			}//if the first book is not a null,then show all the message of this book
			else 
			{
				//SearchUnSuccessful.setVisible(true);
				//show the GUI of searching unsuccessfully
				//TODO
				JOptionPane.showMessageDialog(null,"The book that you searched is not found!",
						"search unsuccessful",JOptionPane.WARNING_MESSAGE);							  
			}
			
		}
		
	}

		class ActionLis_Borrow implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0)
			{
				HashMap<String,String> hmlib=null;
				boolean flag=false;//标识是否有书被勾选

				HashMap<String,String> hmbook=new HashMap<String,String>();
				for (int i = 0;i < table_borrow.getRowCount();i++) {
					 hmlib=new HashMap<String,String>();
					 
					 if(has(table_borrow.getSelectedRows(),table_borrow.getSelectedRowCount(),i)) {
						 
						flag=true;
					 	hmlib.put("book_name", rowData_Borrow[i][0]);
					 	hmlib.put("op", "borrow");
					 	hmlib.put("card_id", ci);
					 	hmlib=GUI.getOne(hmlib);
					 	hmbook.put(hmlib.get("book_name"),hmlib.get("result"));
					 }
				}
				
				String[] booknames=new String[hmbook.size()];
				System.out.println(hmbook.size());
				booknames= hmbook.keySet().toArray(booknames);
				String result="";

				for(int i=0;i<hmbook.size();i++){
					result+=(booknames[i]+ " is borrowed "+hmbook.get(booknames[i])+"!\n");
				}
				if(flag)JOptionPane.showMessageDialog(null,result,"Results",JOptionPane.INFORMATION_MESSAGE);
				
				new ActionLis_Query().actionPerformed(null);
				
				
			}
			
		}
		
		//判断数组里是否包含数字a
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
