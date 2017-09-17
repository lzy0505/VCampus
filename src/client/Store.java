package client;
import java.awt.*;
import java.awt.event.*;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.*;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.TableModel;

//import ShoppingCart.ColumnListener;
//import ShoppingCart.MyTableModel;

public class Store {

	//主界面的组件
	JFrame homeScreen;
	JTextField searchText;
	JButton search;
	JLabel []product = new JLabel[4];//一开始随机放四个商品在主界面上
	private ArrayList<HashMap<String , String>> getDetial=null;//用于存储商品的名字啊什么乱七八糟的
	private ArrayList<HashMap<String , String>> getInitDetial=null;//用于装初始化商品的初始化信息
	private String[] file_path= null;//用于记录文件路径方便查询
	private ArrayList<String[]> shoppingCartArray=new ArrayList<String[]>();

	
	//搜索结果主界面
	JFrame searchResult;
	JLabel[] productResultLabel;

	
	//一个索引
	static int index = 0;
	
	//某商品详细信息主界面及有响应组件
	JFrame productDetails;
	JTextField quantityText;//用于计算选中某种商品的数量
	
	//购物车
	static double totalPrice = 0;
	static int count[];
	
	
	public Store()
	{	
		homeScreen = new JFrame();
		homeScreen.setBounds(100, 100, 900, 600);
		homeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeScreen.getContentPane().setLayout(null);
		
		//Welcome bar start
		JLabel lblWelcome = new JLabel("Welcome!");
		lblWelcome.setForeground(Color.red);
		lblWelcome.setFont(new Font("Lucida Fax", Font.PLAIN, 15));
		lblWelcome.setBounds(18, 6, 109, 27);
		homeScreen.add(lblWelcome);
		//Welcome bar end
		
		//Search bar start
		searchText = new JTextField();
		searchText.setBounds(292, 55, 270, 32);
		homeScreen.getContentPane().add(searchText);
		searchText.setColumns(10);
		
		search = new JButton("Search");
		search.setBounds(574, 58, 117, 29);
		homeScreen.getContentPane().add(search);
		//Search bar end
		
		//为了强行初始化，加了一段，献丑了
		
		String[] productName = new String[4];
		String[] productIcon = new String[4];

		HashMap<String, String> load_pro = new HashMap<>();//取信息
		HashMap<String, String> load_pic = new HashMap<>();//取图片
		load_pro.put("op", "init_product");
		load_pic.put("op", "init_pic");
		getInitDetial = GUI.getList(load_pro);
		try {
			productIcon=GUI.getImage(load_pic);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for(int i=0;i<4;i++) {
			productName[i] =getInitDetial.get(i).get("item_name"); 
		}
		
		//绘制主界面的商品
		for(int i = 0;i<4;i++) 
		{
			product[i] = new JLabel(productName[i]);
			product[i].setIcon(new ImageIcon(productIcon[i]));
			product[i].setBounds(90+250*i%4, 110+(i/4)*220, 162, 217);
			product[i].setHorizontalTextPosition(JLabel.CENTER);
			product[i].setVerticalTextPosition(JLabel.BOTTOM);
			homeScreen.getContentPane().add(product[i]);
			
		}
		//homeScreen.setVisible(true);
		
		//响应如下
		search.addActionListener(new SearchLister());
		for(index=0;index<4;index++)
		{
			product[index].addMouseListener(new MouseListener()
					{
						
						@Override
						public void mouseClicked(MouseEvent arg0) {
							String details[] = new String[5];
							details[0] = getInitDetial.get(index).get("item_name");
							System.out.println("点击的商品名.." +getInitDetial.get(index).get("item_name")); 
							details[1] = file_path[index];
							details[2] = getInitDetial.get(index).get("item_price");
							System.out.println("点击的价格.." +getInitDetial.get(index).get("item_price"));
							details[3] = getInitDetial.get(index).get("item_purchased_number");
							details[4] = getInitDetial.get(index).get("item_stock");
							//商品详情字符串数组，里面的内容参照productDetails()
//							String details[] = null;
							//TODO
							//把详情传到details里，将details作为参数传递到商品详情界面
							productDetail(details);
							
						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							// TODO 自动生成的方法存根
							
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
							// TODO 自动生成的方法存根
							
						}

						@Override
						public void mousePressed(MouseEvent arg0) {
							// TODO 自动生成的方法存根
							
						}

						@Override
						public void mouseReleased(MouseEvent arg0) {
							// TODO 自动生成的方法存根
							
						}
				
					});
			break;
		}
		
	}
	
	public void searchResult(String productName[],String productIcon[],int size)
	{
		searchResult = new JFrame();
		searchResult.setBounds(100, 100,900, 600);
		searchResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		searchResult.getContentPane().setLayout(null);
		
		JPanel DisplayBarh = new JPanel();
		DisplayBarh.setBackground(UIManager.getColor("ComboBox.selectionBackground"));
		DisplayBarh.setBounds(16, 23, 831, 36);
		searchResult.getContentPane().add(DisplayBarh);
		DisplayBarh.setLayout(null);
		
		JLabel lblWelcome = new JLabel("Search Result");
		lblWelcome.setForeground(UIManager.getColor("ComboBox.buttonBackground"));
		lblWelcome.setFont(new Font("Lucida Fax", Font.PLAIN, 18));
		lblWelcome.setBounds(20, 5, 168, 27);
		DisplayBarh.add(lblWelcome);
		
		JPanel DisplayBar = new JPanel();
		DisplayBar.setBounds(6, 39, 888, 533);
		searchResult.getContentPane().add(DisplayBar);
		DisplayBar.setLayout(null);
		
		//加入滚动面板
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 870, 520);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(6, 6, 876, 521);
		DisplayBar.add(scrollPane);
		
		//绘制搜索结果页面
		if(size == 0)
		{
			JOptionPane.showMessageDialog(null, "无此类商品！","搜索失败",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			productResultLabel = new JLabel[size];
			for(int i = 0;i<size;i++)
			{
				productResultLabel[i] = new JLabel(productName[i]);
				productResultLabel[i].setIcon(new ImageIcon(productIcon[i]));
				productResultLabel[i].setBounds(90+250*i%4, 110+(i/4)*220, 162, 217);
				productResultLabel[i].setHorizontalTextPosition(JLabel.CENTER);
				productResultLabel[i].setVerticalTextPosition(JLabel.BOTTOM);
				panel.add(productResultLabel[i]);
				
			}
			
			searchResult.setVisible(true);
		}
		
		//搜索结果中点击某一个商品的响应
		for(index=0;index<size;index++)
		{
			productResultLabel[index].addMouseListener(new searchResultLister(index));
			break;
		}
		
	}
	
	//商品详情显示页面
	public void productDetail(final String details[])
	{
		productDetails = new JFrame();
		productDetails.setBounds(100, 100, 750, 500);
		productDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		productDetails.getContentPane().setLayout(null);
		//价格
		JLabel lblPrice = new JLabel("Price：");
		lblPrice.setBounds(325, 96, 61, 16);
		productDetails.getContentPane().add(lblPrice);
		//销售量
		JLabel lblSalesVolume = new JLabel("SalesVolume：");
		lblSalesVolume.setBounds(325, 136, 97, 16);
		productDetails.getContentPane().add(lblSalesVolume);
		//库存
		JLabel lblInventory = new JLabel("Inventory：");
		lblInventory.setBounds(325, 176, 97, 16);
		productDetails.getContentPane().add(lblInventory);
		//数量
		JLabel lblQuantity = new JLabel("Quantity：");
		lblQuantity.setBounds(325, 216, 85, 16);
		productDetails.getContentPane().add(lblQuantity);
		
		JButton btnBuyNow = new JButton("Buy Now!");
		btnBuyNow.setBounds(318, 256, 97, 29);
		productDetails.getContentPane().add(btnBuyNow);
		
		JButton btnAddToShoppingCart = new JButton("Add To Shopping Cart");
		btnAddToShoppingCart.setBounds(415, 256, 172, 29);
		productDetails.getContentPane().add(btnAddToShoppingCart);
		
		quantityText = new JTextField("1",5);
		quantityText.setBounds(401, 210, 33, 26);
		productDetails.getContentPane().add(quantityText);
		
		JLabel label = new JLabel("件");
		label.setBounds(441, 216, 61, 16);
		productDetails.getContentPane().add(label);
		
		//商品名
		JLabel productNameLabel = new JLabel(details[0]);
		productNameLabel.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		productNameLabel.setBounds(321, 42, 161, 33);
		productDetails.getContentPane().add(productNameLabel);
		
		//商品图片
		JLabel productImageLabel = new JLabel("");
		productImageLabel.setIcon(new ImageIcon(details[1]));
		productImageLabel.setBounds(91, 42, 180, 240);
		productDetails.getContentPane().add(productImageLabel);
		
		//商品单价
		JLabel priceLabel = new JLabel(details[2]);
		priceLabel.setForeground(Color.RED);
		priceLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		priceLabel.setBounds(364, 87, 104, 29);
		productDetails.getContentPane().add(priceLabel);
		
		//商品已售数量
		JLabel salesVolumeLabel = new JLabel(details[3]);
		salesVolumeLabel.setBounds(421, 136, 61, 16);
		productDetails.getContentPane().add(salesVolumeLabel);
		
		//商品库存量
		JLabel inventoryLabel = new JLabel(details[4]);
		inventoryLabel.setBounds(396, 176, 61, 16);
		productDetails.getContentPane().add(inventoryLabel);
		
		productDetails.setVisible(true);
		
		//立即购买，响应如下
		btnBuyNow.addActionListener(new ActionListener() {
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
					//TODO
					if(hm.get("result").equals("success"))
					{
						//库存量和银行卡余额相应的减少
						//TODO
						JOptionPane.showMessageDialog(null, "购买成功！");
					}
					else {
						JOptionPane.showMessageDialog(null, hm.get("item_name") + "购买失败！" + hm.get("reason"));
					}
				}
					
				
			}
		});
		//添加至购物车
		btnAddToShoppingCart.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
				int quantity = Integer.parseInt(quantityText.getText());
				double price = Double.parseDouble(details[2]);
				if(quantity > Integer.parseInt(details[4])) {
					JOptionPane.showMessageDialog(null,"数量超过了！","Warnning",JOptionPane.WARNING_MESSAGE);
					return;
				}
				String[] addProduct =new String[4];
				addProduct[0] =details[0];
				addProduct[1] = details[2];
				addProduct[2] = quantity+"";
				addProduct[3] = quantity*price + "";
				shoppingCartArray.add(addProduct);
				//把该类商品信息加入购物车
				//TODO
				Object[] options ={ "查看购物车", "返回页面" };
				 int result= JOptionPane.showOptionDialog(null, "Added to the shopping cart successfully!","Added Successfully",JOptionPane.YES_NO_OPTION,
						  JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				 if(result == 0)
				 {
					 
					 /*shoppingCartArray的格式如下
						//shoppingCartArray = {
						{new Boolean(false),"商品名1","单价1","数量1","总价1"},
						{new Boolean(false),"商品名2","单价2","数量2","总价2"},
						{new Boolean(false),"商品名3","单价3","数量3","总价3"},
						{new Boolean(false),"商品名4","单价4","数量4","总价4"}
						……
						};*/
					 shoppingCart(shoppingCartArray);
				 }
				 
			 }			
		});
	}
	
	public void shoppingCart(ArrayList<String[]> shoppingCartArray)
	{
		
		String [][] shopCart = new String[shoppingCartArray.size()][4];
		for(int i=0;i<shoppingCartArray.size();i++) {
			for(int j=0;j<4;j++) {
				shopCart[i][j] = shoppingCartArray.get(i)[j];
			}
		}
		/*shoppingCartArray的格式如下
		//shopCartArray = {
		{new Boolean(false),"商品名1","单价1","数量1","总价1"},
		{new Boolean(false),"商品名2","单价2","数量2","总价2"},
		{new Boolean(false),"商品名3","单价3","数量3","总价3"},
		{new Boolean(false),"商品名4","单价4","数量4","总价4"}
		……
		};*/
		
		JFrame shoppingCart = new JFrame("ShoppingCart");
		shoppingCart.setBounds(100, 100, 900, 600);
		shoppingCart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		shoppingCart.getContentPane().setLayout(null);
		
		JPanel DisplayBar = new JPanel();
		DisplayBar.setBackground(UIManager.getColor("ComboBox.selectionBackground"));
		DisplayBar.setBounds(34, 23, 831, 36);
		shoppingCart.getContentPane().add(DisplayBar);
		DisplayBar.setLayout(null);
		
		JLabel lblProductname = new JLabel("ShoppingCart");
		lblProductname.setForeground(UIManager.getColor("ComboBox.buttonBackground"));
		lblProductname.setFont(new Font("Lucida Console", Font.PLAIN, 18));
		lblProductname.setBounds(88, 14, 138, 16);
		DisplayBar.add(lblProductname);
		
		//This panel contains the scrollPane and the TotalBar which is to summit the things you choose to buy.
		JPanel panel = new JPanel();
		panel.setBounds(34, 84, 831, 435);
		shoppingCart.getContentPane().add(panel);
	
		//表头
		String[] headName = {"","Name","Unitprice","Quantity","Amout"};
		
		
		JTable table = new JTable(shopCart,headName);
		table.setPreferredScrollableViewportSize(new Dimension(831, 300));
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
       /* table.getColumnModel().getSelectionModel().
            addListSelectionListener(new ListSelectionListener()
            {
            	  public void valueChanged(ListSelectionEvent event)
            	  {
            		  if (event.getValueIsAdjusting()) {
                          return;
                      }
            	  }
            }
            );*/
		panel.setLayout(null);
		
		//Scroll implement
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(-2, 5, 835, 304);
		table.setFillsViewportHeight(true);
		panel.add(scrollPane);
		
		JPanel TotalBar = new JPanel();
		TotalBar.setBounds(8, 321, 817, 95);
		panel.add(TotalBar);
		TotalBar.setLayout(null);
		
		JButton btnSettle = new JButton("Settle");
		btnSettle.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		btnSettle.setBounds(676, 29, 117, 37);
		TotalBar.add(btnSettle);
		//全选和删除暂时不写哈，只有结算的功能
		/*JCheckBox chckbxSelectAll = new JCheckBox("Select All");
		chckbxSelectAll.setBounds(53, 34, 128, 23);
		TotalBar.add(chckbxSelectAll);*/
		
		/*JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(185, 30, 80, 30);
		TotalBar.add(btnDelete);*/
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		lblTotal.setBounds(490, 29, 88, 37);
		TotalBar.add(lblTotal);
		
		JLabel TotalPriceLabel = new JLabel("¥0.00");
		TotalPriceLabel.setForeground(Color.RED);
		TotalPriceLabel.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		TotalPriceLabel.setBounds(561, 30, 88, 37);
		TotalBar.add(TotalPriceLabel);
		
		shoppingCart.setVisible(true);
		
        count = new int[table.getRowCount()];
		
		table.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
				{
					for(int j=0;j<2;j++)
					{
					for(int i=0;i<table.getRowCount();i++)
					{
						if(table.isCellSelected(i, 3))
						{
							double amountPrice = Double.parseDouble((String)table.getValueAt(i,2))
									*Double.parseDouble((String)table.getValueAt(i,3));
							table.setValueAt(""+amountPrice,i,4);
						}
						
					}
					}
				}
				for(int i=0;i<table.getRowCount();i++)
				{
						double amountPrice = Double.parseDouble((String)table.getValueAt(i,2))
								*Double.parseDouble((String)table.getValueAt(i,3));
						table.setValueAt(""+amountPrice,i,4);
												
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				
			}
	
		});
		
		table.addMouseListener(new MouseListener()
				{

					@Override
					public void mouseClicked(MouseEvent e) {
						System.out.println("ok");
						System.out.println(""+count[0]);
							
						for(int i = 0;i<table.getRowCount();i++)
						{
							//if (table.isCellSelected(i, 0))
							if(table.getSelectedColumn() == 0&&table.getSelectedRow()==i)
							{
								count[i]+=1;
								if( count[i]%2==1)
								{
									
									totalPrice += Double.parseDouble((String)table.getValueAt(i,4));
									TotalPriceLabel.setText("¥"+totalPrice);
								}
								else
								{
									totalPrice -= Double.parseDouble((String)table.getValueAt(i,4));
									TotalPriceLabel.setText("¥"+totalPrice);
								}
							}
									
						}
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO 自动生成的方法存根
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						
						
					
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO 自动生成的方法存根
						
					}
			
				});
			//购物车一块购买
				btnSettle.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						
						int result = JOptionPane.showConfirmDialog(null, "总价为"+TotalPriceLabel.getText()+",确认购买？");
						//若银行卡余额充足并且确认购买，则显示购买成功
						//TODO
						if(result == 0) {						
							for(int i=0;i<shoppingCartArray.size();i++) {
								HashMap<String, String> hm=new HashMap<String, String>();
								hm.put("op", "buy");
								hm.put("card_id", ClientInfo.getCi());
								hm.put("cost", shoppingCartArray.get(i)[3]);
								hm.put("item_name", shoppingCartArray.get(i)[0]);
								hm.put("quantity", shoppingCartArray.get(i)[2]);
								hm=GUI.getOne(hm);
								if(hm.get("result").equals("success"))
								{
									//库存量和银行卡余额相应的减少
									//TODO
									JOptionPane.showMessageDialog(null, "购买成功！");
								}else {
									JOptionPane.showMessageDialog(null, hm.get("item_name") + "购买失败！" + hm.get("reason"));
									break;
								}
								//若银行卡余额不足，提醒他账户余额不够，请尽快充值
								//JOptionPane.showMessageDialog(null,"账户余额不足，请尽快充值！",
								//		"购买失败！",JOptionPane.WARNING_MESSAGE);
							}
						}					
					}
			
				});
	}
	
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			size = getDetial.size();
			String productName[] = new String[size];
			String productIcon[] = file_path;
			for(int i=0;i<size;i++) {
				productName[i] = getDetial.get(i).get("item_name");
			}
			
			
			//从数据库那里得到商品名字及商品图片的字符串数组及数组们的大小，将其作为参数
			//传到搜索结果界面的构造函数中
			//TODO
			searchResult(productName,productIcon,size);
			
		}
		
	}
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
			String details[] = new String[5];
			details[0] = getDetial.get(index).get("item_name");
			System.out.println("点击的商品名.." +getDetial.get(index).get("item_name")); 
			details[1] = file_path[index];
			details[2] = getDetial.get(index).get("item_price");
			System.out.println("点击的价格.." +getDetial.get(index).get("item_price"));
			details[3] = getDetial.get(index).get("item_purchased_number");
			details[4] = getDetial.get(index).get("item_stock");
			//TODO
			
			//把详情传到details里，将details作为参数传递到商品详情界面
			productDetail(details);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}
		
	}
	
}
