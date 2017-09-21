package client;
import java.awt.*;
import java.awt.event.*;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import com.healthmarketscience.jackcess.Table;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.TableModel;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import com.sun.xml.internal.ws.api.message.saaj.SaajStaxWriter;

import table_component.SpringUtilities;

//import ShoppingCart.ColumnListener;
//import ShoppingCart.MyTableModel;
/**
 *<p>Store</p>
 * <p>商店类<br>
 * 绘制商店界面，有购买、购物车、商品分类功能
 * </p>
 * @author 叶鑫、赵千锋、李子厚、刘宗源
 */
public class Store {

	//主界面的组件
	public JTabbedPane mainPanel;
	JTextField searchText;
	JButton search;
	JLabel []product = new JLabel[4];//一开始随机放四个商品在主界面上
	private ArrayList<HashMap<String , String>> getDetial=null;//用于存储商品的名字啊什么乱七八糟的
	private ArrayList<HashMap<String , String>> getInitDetial=null;//用于装初始化商品的初始化信息
	private String[] file_path= null;//用于记录文件路径方便查询
	private ArrayList<String[]> shoppingCartArray=new ArrayList<String[]>();
	private String[] file_init_path= null;//用于记录文件路径方便查询
	calculatePriceLister calListener= null;
	
	//搜索结果主界面
	JPanel searchPanel;
	JLabel[] productResultLabel;
	JLabel salesVolumeLabel;
	JLabel inventoryLabel;
	String[] details;

	JLabel TotalPriceLabel;
	
	//一个索引
//	static int index = 0;
	
	//某商品详细信息主界面及有响应组件
	JTextField quantityText;//用于计算选中某种商品的数量
	
	//购物车
	static int count[];
	JTable table=null;
	//分类
	JList typelist;
	String type[];
	//储存搜索结果
	String productName[];
	String productIcon[];
	String productPrice[];
	String[] headName; 
	JCheckBox checkAll;
	boolean flagAll;
	//存储搜索结果筛选前在product[]里面的位置
	int[] product_index;
	boolean b_filiterMode;//标记是否为筛选模式
	JLabel welcomePicture;
	boolean hasdeleteAdd;//判断广告标签是否删除
	public Store()
	/**
	 *<p>Store</p>
	 * <p>初始化<br>
	 * 绘制商店界面
	 * </p>
	 */
	{	b_filiterMode=false;
		flagAll=false;
		mainPanel=new JTabbedPane();
		JPanel p=new JPanel();
		p.setName("gouwuche");
		mainPanel.addTab("购物车",p);
		hasdeleteAdd=false;
		
		searchPanel=new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));
		
		
		JPanel searchInput=new JPanel(new FlowLayout(FlowLayout.CENTER));
		//添加入场广告
		//JPanel flashPanel
		welcomePicture=new JLabel();
		//图片缩放
		ImageIcon imageIcon_display=new ImageIcon("clientpic/广告2.jpeg");
		Image image=imageIcon_display.getImage();
		image=image.getScaledInstance((int)(HomeScreen.width*6/8), (int)(HomeScreen.height*5/6), Image.SCALE_DEFAULT);
		imageIcon_display=new ImageIcon(image);
		welcomePicture.setIcon(imageIcon_display);
		welcomePicture.setPreferredSize(new Dimension((int)(HomeScreen.width*5/6),(int)(HomeScreen.height*5/6)));
		
		//Search bar start
		searchText = new JTextField();
		searchText.setColumns(20);
		
		search = new JButton("搜索");
		
		searchInput.add(searchText);
		searchInput.add(search);	
		//searchPanel.add(p_classfiy);
		searchPanel.add(searchInput);
		searchPanel.add(welcomePicture);
		//设置定时器关闭广告
		
		
		
		
		//分类"饮料","日用品","电子产品","食品"
		type=new String[5];
		type[0]="全部";
		type[1]="饮料";
		type[2]="日用品";
		type[3]="电子产品";
		type[4]="食品";
		typelist=new JList(type);
		typelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//规定List列表只能单选
		headName=new String[4];
	    headName[0]="商品名称";
	    headName[1]="单价";
	    headName[2]="数量";
	    headName[3]="总价";
	    
		//Search bar end
		
		//为了强行初始化，加了一段，献丑了
		/*
		String[] productName = new String[4];
		String[] productIcon = new String[4];

		HashMap<String, String> load_pro = new HashMap<>();//取信息
		HashMap<String, String> load_pic = new HashMap<>();//取图片
		load_pro.put("op", "init_product");
		load_pic.put("op", "init_pic");
		getInitDetial = GUI.getList(load_pro);
		try {
			file_init_path=GUI.getImage(load_pic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0;i<4;i++) {
			productName[i] =getInitDetial.get(i).get("item_name"); 
		}
		productIcon=file_init_path;
		//绘制主界面的商品
		
		JPanel productPreviewPanel =new JPanel(new SpringLayout());
		
		for(int i = 0;i<4;i++) 
		{
			product[i] = new JLabel(productName[i]);
			product[i].setIcon(new ImageIcon(productIcon[i]));
			product[i].setHorizontalTextPosition(JLabel.CENTER);
			product[i].setVerticalTextPosition(JLabel.BOTTOM);
			product[i].addMouseListener(new InitDetailsListener(i));
			productPreviewPanel.add(product[i]);
		}
		
		SpringUtilities.makeCompactGrid(productPreviewPanel, 1, 4, 0, 0, 50, 0);
		searchPanel.add(productPreviewPanel);
		
		*/
		//响应如下
	   
		search.addActionListener(new SearchLister());
        //new SearchLister().actionPerformed(null);//手动调用搜索
		mainPanel.addTab("搜索", searchPanel);
		mainPanel.setSelectedIndex(1);
		typelist.addListSelectionListener(new ListListener());
		
	}
	
	
	
	void setTimer()
	{
		System.out.println("ok1");
		Timer t=new Timer();
		t.schedule(new MyTask(), 3000);
		
	}
	class MyTask extends TimerTask
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("ok2");
			if(hasdeleteAdd==false)
			{
				searchPanel.remove(welcomePicture);
				new SearchLister().actionPerformed(null);//手动调用搜索
				searchPanel.repaint();
				searchPanel.revalidate();
				hasdeleteAdd=true;
			}
			
		}
		
		
		

	}
	/**
	 * <p>点击商店响应<br>
	 * 绘制商店界面
	 * </p>
	 */
	
	class ListListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			int size=getDetial.size();
			int vaildSize=0;
			int index=0;
			int count=0;
			String theItemName[];
			String theItemPicture[];
			String theItemPrice[];
			if(size==0)
			{
				return;
			}
			String itemType[]={"饮料","日用品","电子产品","食品"};
			
			for(int k=0;k<5;k++)
			{
			//如果选择的是..的话
			if(typelist.getSelectedIndex()==k&&k==0)
			{
				b_filiterMode=false;
				flagAll=true;
				new SearchLister().actionPerformed(null);
				//显示全部
				
			}
		
			else if(typelist.getSelectedIndex()==k&&k!=0)
			{
				b_filiterMode=true;
				flagAll=false;
				for(int i=0;i<size;i++)
				{
					
					if(getDetial.get(i).get("item_type").equals(itemType[k-1]))
					{
					  
                       ++vaildSize;
					}				
				}
				product_index=new int[vaildSize];
				theItemName=new String[vaildSize];
				theItemPicture=new String[vaildSize];
				theItemPrice=new String[vaildSize];
				
				for(int i=0;i<size;i++)
				{
					if(getDetial.get(i).get("item_type").equals(itemType[k-1]))
					{
					  
					   product_index[count]=i;					   
                       theItemName[index]=productName[i];
                       theItemPicture[index]=productIcon[i];
                       theItemPrice[index]=productPrice[i];
                       ++index;
                       ++count;
					}				
				}
	
				searchResult(theItemName,theItemPicture,theItemPrice,vaildSize);
				return;
			}
			
			
			}
	
			
		}
			
		
	}
	
	
	/**
	 * <p>搜索响应<br>
	 * 绘制搜索结果的函数
	 * </p>
	 *@param productName 商品名字
	 *@param productIcon 商品图片路径
	 *@param productPrice 商品价格
	 *@param size 搜索到的商品数量
	 */
	public void searchResult(String productName[],String productIcon[],String productPrice[],int size)
	{
		JPanel p_buf=new JPanel();
		p_buf.setLayout(new BoxLayout(p_buf,BoxLayout.X_AXIS));
		
		//加入滚动面板
		JPanel searchResultPanel =new JPanel();
		searchResultPanel.setLayout(new BoxLayout(searchResultPanel,BoxLayout.Y_AXIS));
		JPanel panel = new JPanel(new SpringLayout());
		searchResultPanel.add(panel);
		panel.setPreferredSize(new Dimension((int)(HomeScreen.width*5/7),(int)(HomeScreen.height*5/6)));
		JScrollPane scrollPane = new JScrollPane(searchResultPanel);
		
		JPanel p_classfiy=new JPanel();
		typelist.setBackground(new Color(240,240,240));
		typelist.setFixedCellHeight((int)HomeScreen.width*1/22);
		typelist.setPreferredSize(new Dimension((int)(HomeScreen.width*1/8),(int)(HomeScreen.height*4/5)));
		p_classfiy.add(typelist);
		p_classfiy.setPreferredSize(new Dimension((int)(HomeScreen.width*1/7),(int)(HomeScreen.height*4/5)));
		
		
		p_buf.add(p_classfiy);
		p_buf.add(scrollPane);
		
		//绘制搜索结果页面
		if(size == 0)
		{
			JOptionPane.showMessageDialog(null, "无此类商品！","搜索失败",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			int insert=0;
			while(size%4!=0) {
				size++;//填满最后一行
				insert++;//空Label
			}
			productResultLabel = new JLabel[size];
			JPanel[] panelSet=new JPanel[size];
			JLabel[] productPriceLabel=new JLabel[size];
			
			for(int i = 0;i<size-insert;i++)
			{   panelSet[i]=new JPanel();
			    panelSet[i].setLayout(new BoxLayout(panelSet[i],BoxLayout.Y_AXIS));
				productPriceLabel[i]=new JLabel("          "+"¥ "+productPrice[i]);
				productPriceLabel[i].setForeground(Color.RED);
				productPriceLabel[i].setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
				
				productResultLabel[i] = new JLabel(productName[i]);
				productResultLabel[i].setIcon(new ImageIcon(productIcon[i]));
				System.out.println(productIcon[i]);
				productResultLabel[i].setHorizontalTextPosition(JLabel.CENTER);
				productResultLabel[i].setVerticalTextPosition(JLabel.BOTTOM);
				if(b_filiterMode==false)
				{
				productResultLabel[i].addMouseListener(new searchResultLister(i));
				}
				else if(b_filiterMode==true)
				{
					System.out.println(product_index[i]);
					productResultLabel[i].addMouseListener(new searchResultLister(product_index[i]));
					
				}
				panelSet[i].add(productResultLabel[i]);
				panelSet[i].add(productPriceLabel[i]);
				panel.add(panelSet[i]);
				
			}
			for(int i=0;i<insert;i++) {
				productResultLabel[size-insert+i]=new JLabel();
				panel.add(productResultLabel[size-insert+i]);
				
			}
			
			
		}
		SpringUtilities.makeCompactGrid(panel, size/4, 4, 0, 0, 50, 30);
		if(searchPanel.getComponentCount()>1)
		{
		searchPanel.remove(1);
		}
		searchPanel.add(p_buf);
		searchPanel.repaint();
		searchPanel.updateUI();
		searchPanel.revalidate();
		
	}
	/**
	 * <p>商品详情显示<br>
	 *	点击商品后显示具体信息
	 * </p>
	 * 
	 */
	//商品详情显示页面
	public void productDetail()
	{
//		productDetails = new JFrame();
//		productDetails.setBounds(100, 100, 750, 500);
//		productDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		productDetailPanel.setLayout(null);
		JPanel productDetailPanel;
		if(mainPanel.getTabCount()==3) {
			mainPanel.remove(2);
		}
		productDetailPanel=new JPanel(new FlowLayout());
		mainPanel.addTab("商品详情",productDetailPanel);
		
		JPanel info =new JPanel(new SpringLayout());
		
		
		//商品名
		JLabel productNameLabel = new JLabel(details[0]);
		productNameLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 30));
		info.add(productNameLabel);
		info.add(new JLabel());
		
		
		//销售量
		JLabel lblSalesVolume = new JLabel("销量");
		info.add(lblSalesVolume);
		salesVolumeLabel = new JLabel(details[3]);
		lblSalesVolume.setLabelFor(salesVolumeLabel);
		info.add(salesVolumeLabel);
		
				
		//库存
		JLabel lblInventory = new JLabel("库存");
		info.add(lblInventory);
		inventoryLabel = new JLabel(details[4]);
		lblInventory.setLabelFor(inventoryLabel);
		info.add(inventoryLabel);
		
		
		
		//价格
		JLabel lblPrice = new JLabel("单价");
		info.add(lblPrice);
		JLabel priceLabel = new JLabel(details[2]);
		priceLabel.setForeground(Color.RED);
		priceLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
		lblPrice.setLabelFor(priceLabel);
		info.add(priceLabel);


		//数量
		JLabel lblQuantity = new JLabel("购买数量");
		info.add(lblQuantity);
		quantityText = new JTextField("1",5);
		lblQuantity.setLabelFor(quantityText);
		info.add(quantityText);
		

		
		
		JButton btnBuyNow = new JButton("立即购买");
		info.add(btnBuyNow);
		
		JButton btnAddToShoppingCart = new JButton("加入购物车");
		info.add(btnAddToShoppingCart);
		
		SpringUtilities.makeCompactGrid(info, 6, 2, 0, 0, 20, 50);
		
		
		
		//商品图片
		JLabel productImageLabel = new JLabel("");
		productImageLabel.setIcon(new ImageIcon(details[1]));
		productImageLabel.setBounds(91, 42, 180, 240);
		productDetailPanel.add(new JLabel(""));
		productDetailPanel.add(new JLabel(""));
		productDetailPanel.add(info);
		productDetailPanel.add(productImageLabel);
		

		
		
		
//		productDetails.setVisible(true);
		
		//立即购买，响应如下
		btnBuyNow.addActionListener(new BuyNowListener());
		//添加至购物车
		/**
		 * <p>添加至购物车响应<br>
		 * 将选择的商品添加至购物车，可以在购物车中结算或更改商品数量
		 * </p>
		 */
		btnAddToShoppingCart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				boolean sameflag =false;//判断购物车里是否已经有相同的商品
				int quantity = Integer.parseInt(quantityText.getText());
				double price = Double.parseDouble(details[2]);
				if(quantity > Integer.parseInt(details[4])) {
					JOptionPane.showMessageDialog(null,"数量超过了！","Warnning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				for(int i=0;i<shoppingCartArray.size();i++) {
					if(details[0].equals(shoppingCartArray.get(i)[0])) {
						shoppingCartArray.get(i)[2] = quantity +Integer.parseInt(shoppingCartArray.get(i)[2])+"";
						shoppingCartArray.get(i)[3] = quantity*price + Double.parseDouble(shoppingCartArray.get(i)[3])+"";
						sameflag =true;
						break;
					}			
					
				}
				if(!sameflag) {
					String[] addProduct =new String[4];
					addProduct[0] =details[0];//mingzi
					addProduct[1] = details[2];//danjia
					addProduct[2] = quantity+"";
					addProduct[3] = quantity*price + "";
					shoppingCartArray.add(addProduct);
				}
			
				//把该类商品信息加入购物车
				Object[] options ={ "查看购物车", "返回页面" };
				 int result= JOptionPane.showOptionDialog(null, "成功加入购物车","操作结果",JOptionPane.YES_NO_OPTION,
						  JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				 if(result == 0)
				 {
					 shoppingCart();
				 }else{
					 shoppingCart();
					 
					 mainPanel.setSelectedIndex(1);
				 }
			 }			
		});
		mainPanel.setSelectedIndex(2);
	}
	/**
	 * <p>购物车页面<br>
	 * 显示物品的数量、总价、名称
	 * </p>
	 */
	public void shoppingCart()
	{
		JPanel shoppingCartPanel=(JPanel)mainPanel.getComponentAt(0);
		shoppingCartPanel.removeAll();
		
		String [][] shopCart = new String[shoppingCartArray.size()][4];	
		for(int i=0;i<shoppingCartArray.size();i++) {
			for(int j=0;j<4;j++) {
				shopCart[i][j] = shoppingCartArray.get(i)[j];
			}
		}
		
		
	
//		
//		JLabel lblProductname = new JLabel("ShoppingCart");
//		lblProductname.setForeground(UIManager.getColor("ComboBox.buttonBackground"));
//		lblProductname.setFont(new Font("Lucida Console", Font.PLAIN, 18));
//		lblProductname.setBounds(88, 14, 138, 16);
//		shoppingCartPanel.add(lblProductname);
		
		//This panel contains the scrollPane and the TotalBar which is to summit the things you choose to buy.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
//		panel.setBounds(34, 84, 831, 435);
		shoppingCartPanel.add(panel);
	
		//表头
		

		table = new JTable(shopCart,headName);
		table.setPreferredScrollableViewportSize(new Dimension(831, 300));
//		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));

		//Scroll implement
		JScrollPane scrollPane = new JScrollPane(table);

		table.setFillsViewportHeight(true);
		panel.add(scrollPane);
		
		JPanel TotalBar = new JPanel(new FlowLayout());
//		TotalBar.setBounds(8, 321, 817, 95);
		
//		TotalBar.setLayout(null);
		checkAll=new JCheckBox("全部选择");
		JButton btnbuy=new JButton("购买选中商品");
		//JButton btnSettle = new JButton("购买全部");
		JButton btnDelete=new JButton("删除选中商品");
		TotalBar.add(checkAll);
		TotalBar.add(btnbuy);
		//TotalBar.add(btnSettle);
		TotalBar.add(btnDelete);
		//全选和删除暂时不写哈，只有结算的功能
		/*JCheckBox chckbxSelectAll = new JCheckBox("Select All");
		chckbxSelectAll.setBounds(53, 34, 128, 23);
		TotalBar.add(chckbxSelectAll);*/
		
		/*JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(185, 30, 80, 30);
		TotalBar.add(btnDelete);*/
		
		JLabel lblTotal = new JLabel("选中商品总价：");
//		lblTotal.setBounds(490, 29, 88, 37);
		TotalBar.add(lblTotal);
		
		TotalPriceLabel = new JLabel("¥0.00");
		TotalPriceLabel.setPreferredSize(new Dimension(70,30));
		TotalPriceLabel.setForeground(Color.RED);
		TotalPriceLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
//		TotalPriceLabel.setBounds(561, 30, 88, 37);
		TotalBar.add(TotalPriceLabel);

		
        count = new int[table.getRowCount()];
		
		table.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e) {
				
				JTable t=(JTable)e.getComponent();
				if(e.getClickCount()==2)
				{
					for(int j=0;j<2;j++)
					{
						for(int i=0;i<t.getRowCount();i++)
						{
							if(t.isCellSelected(i, 2))
							{
								double amountPrice = Double.parseDouble((String)t.getValueAt(i,1))
										*Double.parseDouble((String)t.getValueAt(i,2));
								t.setValueAt(""+amountPrice,i,3);
							}
						}
					}
				}
				for(int i=0;i<t.getRowCount();i++)
				{
						double amountPrice = Double.parseDouble((String)t.getValueAt(i,1))
								*Double.parseDouble((String)t.getValueAt(i,2));
						t.setValueAt(""+amountPrice,i,3);						
				}
				
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		panel.add(TotalBar);
		calListener= new calculatePriceLister();
		table.addMouseListener(calListener);
		calListener.mouseClicked(null);	
		//购物车一块购买
		checkAll.addChangeListener(new CheckListener());
	    //btnSettle.addActionListener(new BuyAllLister());
		//选择性支付
		btnbuy.addActionListener(new BuySelectedLister());
		btnDelete.addActionListener(new DeleteSltListener());
		mainPanel.setSelectedIndex(0);
	}
	
	
   class CheckListener implements ChangeListener
   {

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
		if(checkAll.isSelected())
		{
			table.selectAll();
					
		}
		else if(!checkAll.isSelected())
		{
						
			table.clearSelection();		
		}
		
		new calculatePriceLister().mouseClicked(null);
	}
  
	   
   }

	
	
	/**
	 * <p>查询商品响应<br>
	 * 通过商品名字查找商品细节
	 * </p>
	 */
	class SearchLister implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			int size = 0;
			//先找到商品名和商品的细节
			HashMap<String , String> sendkeyfordetial = new HashMap<String , String>();
			sendkeyfordetial.put("op", "search_product");
			sendkeyfordetial.put("key", searchText.getText());
			getDetial = GUI.getList(sendkeyfordetial);
			//找到商品的图片,接收到的图片文件存在clientpng文件夹内
			HashMap<String, String> sendkeyforpicture = new HashMap<String,String>();
			sendkeyforpicture.put("op", "search_picture");
			sendkeyforpicture.put("key",searchText.getText());
			try {
				file_path=GUI.getImage(sendkeyforpicture);
			} catch (IOException e) {
				e.printStackTrace();
			}
			size = getDetial.size();
			productName = new String[size];
			productPrice=new String[size];
			productIcon = file_path;
			for(int i=0;i<size;i++) {
				productName[i] = getDetial.get(i).get("item_name");
				productPrice[i]=getDetial.get(i).get("item_price");
			}	
			//从数据库那里得到商品名字及商品图片的字符串数组及数组们的大小，将其作为参数
			//传到搜索结果界面的构造函数中
			searchResult(productName,productIcon,productPrice,size);
			
			if(flagAll==false)
			{
			typelist.clearSelection();
			}
			typelist.setSelectedIndex(0);
			
		}
		
	}
	/**
	 * <p>查询结果响应<br>
	 * 显示详细商品信息
	 * </p>
	 */
	class searchResultLister implements MouseListener
	{
		int index=0;
		public searchResultLister(int i) {
			super();
			index=i;
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			//商品详情字符串数组，里面的内容参照productDetails()
			details= new String[6];
			details[0] = getDetial.get(index).get("item_name");
			System.out.println("点击的商品名.." +getDetial.get(index).get("item_name")); 
			details[1] = file_path[index];
			details[2] = getDetial.get(index).get("item_price");
			System.out.println("点击的价格.." +getDetial.get(index).get("item_price"));
			details[3] = getDetial.get(index).get("item_purchased_number");
			System.out.println("点击的销售量.." + getDetial.get(index).get("item_purchased_number"));
			details[4] = getDetial.get(index).get("item_stock");
			System.out.println("点击的库存数.." +getDetial.get(index).get("item_stock"));
			
			
			//把详情传到details里，将details作为参数传递到商品详情界面
			productDetail();
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	/**
	 * <p>计算总价<br>
	 * 计算购物车商品总价
	 * </p>
	 */

	class calculatePriceLister implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			Double totalPrice=0.0;
			int[] index=table.getSelectedRows();
			int indexCount=table.getSelectedRowCount();
			for(int i = 0;i<indexCount;i++)
			{
				totalPrice += Double.parseDouble((String)table.getValueAt(index[i],3));
				
			}
			TotalPriceLabel.setText("¥"+totalPrice);
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	
	
	/**
	 * <p>全部购买响应<br>
	 * 点击后数据库扣钱或者返回购买失败原因（余额不足或者卡已挂失）
	 * </p>
	 */
	class BuyAllLister implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag =true;
			int result = JOptionPane.showConfirmDialog(null, "总价为"+TotalPriceLabel.getText()+",确认购买？");
			//若银行卡余额充足并且确认购买，则显示购买成功
			int i=0;
			if(result == 0) {						
				for(;i<table.getRowCount();i++) {
					HashMap<String, String> hm=new HashMap<String, String>();
					hm.put("op", "buy");
					hm.put("card_id", ClientInfo.getCi());
					hm.put("cost",(String) table.getValueAt(i, 3));
					hm.put("item_name", (String) table.getValueAt(i, 0));
					hm.put("quantity", (String) table.getValueAt(i, 2));
					hm=GUI.getOne(hm);
					if(hm.get("result").equals("success"))
					{
						shoppingCartArray.remove(0);					
						//库存量和银行卡余额相应的减少
						//table.removeAll();
					}else {
						
						flag =false;
						JOptionPane.showMessageDialog(null, hm.get("item_name") + "购买失败！" + hm.get("reason"));
						break;
					}
				
					//若银行卡余额不足，提醒他账户余额不够，请尽快充值
					//JOptionPane.showMessageDialog(null,"账户余额不足，请尽快充值！",
					//		"购买失败！",JOptionPane.WARNING_MESSAGE);
				}
				if(flag) {
					JOptionPane.showMessageDialog(null,"购买成功");	
					//把跳转到购买页面注释了
					//mainPanel.setSelectedIndex(1);
					shoppingCart();
				}else {
					
					shoppingCart();
				}			
	
			}					
		}
	}
	/**
	 * <p>购买选择物品响应<br>
	 * 点击后数据库扣钱或者返回购买失败原因（余额不足或者卡已挂失）
	 * </p>
	 */
	 class BuySelectedLister implements ActionListener
	 {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			boolean flag =true;
			int result = JOptionPane.showConfirmDialog(null, "总价为"+TotalPriceLabel.getText()+",确认购买？");
			int index[]=table.getSelectedRows();
			int index_remove[]=table.getSelectedRows();
			int indexCount=table.getSelectedRowCount();
			//若银行卡余额充足并且确认购买，则显示购买成功
			
			if(result == 0) {						
				for(int i=0;i<indexCount;i++) {
					System.out.println(index[i]);
					HashMap<String, String> hm=new HashMap<String, String>();
					hm.put("op", "buy");
					hm.put("card_id", ClientInfo.getCi());
					hm.put("cost",(String) table.getValueAt(index[i], 3));
					hm.put("item_name", (String) table.getValueAt(index[i], 0));
					hm.put("quantity", (String) table.getValueAt(index[i], 2));
					hm=GUI.getOne(hm);
					if(hm.get("result").equals("success"))
					{
						for(int j=0;j<indexCount;j++)
						{
							if(index_remove[j]>index_remove[i])
							{
								index_remove[j]=index_remove[j]-1;
							}
						}
						
						
						shoppingCartArray.remove(index_remove[i]);	
						
						//库存量和银行卡余额相应的减少
						//table.removeAll();
					}else {
						flag =false;
						JOptionPane.showMessageDialog(null, hm.get("item_name") + "购买失败！" + hm.get("reason"));
						break;
					}
				
					//若银行卡余额不足，提醒他账户余额不够，请尽快充值
					//JOptionPane.showMessageDialog(null,"账户余额不足，请尽快充值！",
					//		"购买失败！",JOptionPane.WARNING_MESSAGE);
				}
				if(flag) {
					JOptionPane.showMessageDialog(null,"购买成功");	
					//把跳转到购买页面注释了
					//mainPanel.setSelectedIndex(1);
					shoppingCart();
				}else {
					
					shoppingCart();
				}			
	
			}					
			
			
			
		}

	 }
	
	 
	 class DeleteSltListener implements ActionListener
	 {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			int index[]=table.getSelectedRows();
			int indexCount=table.getSelectedRowCount();
			
			for(int i=0;i<indexCount;i++) {
						
					for(int j=0;j<indexCount;j++)
					{
						if(index[j]>index[i])
						{
							index[j]=index[j]-1;
						}
					}
					
					
			    shoppingCartArray.remove(index[i]);	
						
			
		}
			shoppingCart();

		 
	 }
	 }
		/**
		 * <p>初始化详细信息响应<br>
		 * 将商品详细信息传入productDetails
		 * </p>
		 */
	class InitDetailsListener implements MouseListener{
		int i;
		public InitDetailsListener(int i) {
			this.i=i;
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			details= new String[5];
			details[0] = getInitDetial.get(i).get("item_name");
			System.out.println("点击的商品名.." +getInitDetial.get(i).get("item_name")); 
			details[1] = file_init_path[i];
			details[2] = getInitDetial.get(i).get("item_price");
			System.out.println("点击的价格.." +getInitDetial.get(i).get("item_price"));
			details[3] = getInitDetial.get(i).get("item_purchased_number");
			details[4] = getInitDetial.get(i).get("item_stock");
			//商品详情字符串数组，里面的内容参照productDetails()
			//把详情传到details里，将details作为参数传递到商品详情界面
			productDetail();
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}
	/**
	 * <p>立即购买响应<br>
	 * 成功则扣钱，失败则返回原因
	 * </p>
	 */
	class BuyNowListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			double price = Double.parseDouble(details[2]);
			int quantity = Integer.parseInt(quantityText.getText());
			if(quantity > Integer.parseInt(details[4])) {
				JOptionPane.showMessageDialog(null,"数量超过了！","Warnning",JOptionPane.WARNING_MESSAGE);
				return;
			}
			double total = price*quantity;
			int result = JOptionPane.showConfirmDialog(null, "总价为"+total+",确认购买？");
			if (result == 0) {
				HashMap<String, String> hm= new HashMap<>();
				hm.put("op", "buy");
				hm.put("item_name", details[0]);
				hm.put("card_id", ClientInfo.getCi());
				hm.put("quantity", quantityText.getText());
				hm.put("cost", total+"");
				hm=GUI.getOne(hm);
				//若银行卡余额充足并且确认购买，则显示购买成功
				if(hm.get("result").equals("success"))
				{
					//库存量和银行卡余额相应的减少
					int sale_now =Integer.parseInt(salesVolumeLabel.getText())+Integer.parseInt(quantityText.getText());
					int stock_now = Integer.parseInt(inventoryLabel.getText()) - Integer.parseInt(quantityText.getText());
					salesVolumeLabel.setText(sale_now+"");
					inventoryLabel.setText(stock_now+"");
					JOptionPane.showMessageDialog(null, "购买成功！");
				}
				else {
					JOptionPane.showMessageDialog(null, hm.get("item_name") + "购买失败！" + hm.get("reason"));
				}
			}		
		}
	}
	
}
