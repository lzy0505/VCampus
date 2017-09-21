package client;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;

import java.io.File;

import com.sun.swing.internal.plaf.basic.resources.basic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
/**
 *
 *<p>Client</p >
 * <p>客户端类，该类与服务器建立连接<br>
 * 同时提供接发信息、传送图片等方法供其他类使用
 * </p>
 * @author 李子厚
 * @see java.awt;
 * @since 1.8
 */
public class Client{
	private ObjectInputStream cin;//对象输入流
	private ObjectOutputStream cout;//对象输出流
	private DataInputStream dis;//数据流
    private FileOutputStream fos;//文件流
	public static int port =8080;
	private String host= "127.0.0.1";
	public Socket clientSocket = null;
	public Client()throws IOException {
		clientSocket =new Socket(host, port);
	}
	/**
	 * <p>发送信息函数<br>
	 * 将HashMap发送给服务器
	 * </p>
	 * @return 没有返回值
	 * @param sendmes 需要发送给服务端的信息
	 */
	//发送数据包
	public void sendMessage(HashMap<String,String> sendmes) throws IOException 
	{	 
		OutputStream os=clientSocket.getOutputStream();
		//包装一个输出流
		cout=new ObjectOutputStream(os);
		cout.writeObject(sendmes);
	}
	/**
	 * <p>接受单个HashMap函数<br>
	 * 接受服务器发送的信息
	 * </p>
	 * @return getmes 是一个HashMap
	 * 
	 */
	//获取数据包
	public HashMap<String,String> getMessage()throws IOException 
	{		
		InputStream is=clientSocket.getInputStream();
		cin=new ObjectInputStream(is);
		HashMap<String,String> getmes = new HashMap<String,String>();
		try {
			getmes = (HashMap<String,String>)cin.readObject();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return getmes;
	}
	/**
	 * <p>接受多个HashMap函数<br>
	 * 接受服务器发送的信息
	 * </p>
	 * @return getmes 是一个HashMap的ArrayList
	 * 
	 */
	public ArrayList<HashMap<String,String>> getMessages()throws IOException 
	{		
		InputStream is=clientSocket.getInputStream();
		cin=new ObjectInputStream(is);
		ArrayList<HashMap<String,String>> getmes = null;
		try {
			getmes = (ArrayList<HashMap<String,String>>)cin.readObject();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return getmes;
	}
	/**
	 * 上传图片和发送商品详情的方法
	 * <p>上传图片和相关信息的类<br>
	 * </p>
	 * @param hm 给一个HashMap装商品的图片路径、名字、数量、单价、类型
	 * @return getmes 是一个HashMap，返回是否成功和失败的原因
	 * 
	 */
	//给一个HashMap装商品的路径、名字、数量、单价。
		public HashMap<String, String> upLoad(HashMap<String, String> hm)throws IOException {
			/*分三步，第一步：传给服务器要传的新商品的名字，如果已经有了该商品就返回失败。否则先录入信息，
			 * 注：先将路径设为空；hm里面的路径是客户端处的
			 第二步：接受到可以传输的指令后传送图片，服务端写入指定文件夹并将文件名加上路径修改数据库。
			 第三步：返回是否成功。
			*/
			//第一步
			hm.put("op", "up_load");
			cout=new ObjectOutputStream(clientSocket.getOutputStream());
			cin=new ObjectInputStream(clientSocket.getInputStream());
			cout.writeObject(hm);
			HashMap<String,String> getmes =null;
			try {
				 getmes =(HashMap<String, String>) cin.readObject();			
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println("上传时第一次接受情况："+getmes.get("result").equals("success"));
			if(!getmes.get("result").equals("success")) {
				return getmes;
			}
			//第二步
			File file=new File(hm.get("item_picture_url"));
			if(!file.exists()) {
				getmes.put("result", "false");
				getmes.put("reason", "路径下文件不存在！");
			} 
			FileImageInputStream input = new FileImageInputStream(file);
		      ByteArrayOutputStream output = new ByteArrayOutputStream();
		      byte[] buf = new byte[1024];
		      int numBytesRead = 0;
		      while ((numBytesRead = input.read(buf)) != -1) {
		      output.write(buf, 0, numBytesRead);
		      }
		      byte[] data = output.toByteArray();
		      HashMap<String,byte[]> hmd=new HashMap<String,byte[]>();
		      hmd.put(hm.get("item_picture_url"), data);
		      output.close();
		      input.close();
			
			
			
			System.out.println("客户端文件路径： "+ hm.get("item_picture_url"));
			cout.writeObject(hmd);
			cout.flush();
			if(cout!=null)cout.close();
			System.out.println("客户端文件传输成功！");
			//第三步
			return getmes;
		}

	
	public HashMap<String, HashMap> getHashMap() throws IOException{
		InputStream is=clientSocket.getInputStream();
		cin=new ObjectInputStream(is);
		HashMap<String, HashMap> getfile = null;
		try {
			getfile = (HashMap<String, HashMap>)cin.readObject();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return getfile;
	}
	/**
	 * 接受服务器发送的图片和商品详情
	 * 给这个函数一个HashMap，里面装有操作和key，能给你返回服务器传到客户端的文件的路径数组。明明白白
	 * <p>接受服务器发送的图片和商品详情的类<br>
	 * </p>
	 * @param sendmes 含有发送操作以及商品名称
	 * @return getmes 是一个HashMap，返回服务器传到客户端的文件的路径数组
	 * 
	 */
	//给这个函数一个HashMap，里面装有操作和key，能给你返回服务器传到客户端的文件的路径数组。明明白白
		public String[] getIcon(HashMap<String,String> sendmes)throws IOException{
				byte[] inputByte = null;  
		        int length = 0;  
		        DataInputStream dis = null;  
		        FileOutputStream fos = null;  
		        String file_path[] =null;
		            	OutputStream os=clientSocket.getOutputStream();
		            	cout=new ObjectOutputStream(os);
		        		cout.writeObject(sendmes);
		            	System.out.println("开始进入getIcon...");    
		                cin = new ObjectInputStream(clientSocket.getInputStream());
//		            	File[] files;
		                ArrayList<HashMap<String,byte[]>> dataList;
						try {
							dataList = (ArrayList<HashMap<String,byte[]>>)cin.readObject();
		            	System.out.println("开始接收数据..."); 
		            	file_path=new String[dataList.size()];
			            for(int i=0;i<dataList.size();i++) {	
			            	String filePath=dataList.get(i).keySet().iterator().next();
			            	//getDirPath 是ClientInfo的静态方法
			            	System.out.println(" path:"+ClientInfo.getDirPath() + filePath);
			            	
			            	file_path[i] =ClientInfo.getDirPath() + filePath;
			            	File file = new File(ClientInfo.getDirPath()+filePath);
			            	if(!file.exists()) {
			            		byte[] data=dataList.get(i).get(filePath);
			            		
			            		FileImageOutputStream imageOutput = new FileImageOutputStream(file);
			            	    imageOutput.write(data, 0, data.length);
			            	    imageOutput.close();
			            	}      	
			            }              	             
		                System.out.println("完成接收");                 
		                if (dis != null)  
		                    dis.close();  
		                if (clientSocket != null)  
		                	clientSocket.close();  
		                
						} catch (ClassNotFoundException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}	
		                return file_path;


		    }  

	
	
	
	protected void finalize()
	{
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}