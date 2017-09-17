package client;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.swing.internal.plaf.basic.resources.basic;

import utils.*;
import java.io.*;
import java.io.File;
/*
 * <p>MyClient</p >
 * 客户端，用于与服务端连接并接发数据
 * @author
 */
public class Client{
	private ObjectInputStream cin;//对象输入流
	private ObjectOutputStream cout;//对象输出流
	public static int port =8080;
	private String host= "127.0.0.1";
	public Socket clientSocket = null;
	public Client()throws IOException {
		clientSocket =new Socket(host, port);
	}
	
	//发送数据包
	public void sendMessage(HashMap<String,String> sendmes) throws IOException 
	{	 
		OutputStream os=clientSocket.getOutputStream();
		//包装一个输出流
		cout=new ObjectOutputStream(os);
		cout.writeObject(sendmes);
	}
	
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
		File file =new File(hm.get("item_picture_url"));
		if(!file.exists()) {
			getmes.put("result", "false");
			getmes.put("reason", "路径下文件不存在！");
		} 
		System.out.println("客户端文件路径： "+ hm.get("item_picture_url"));
		cout.writeObject(file);
		cout.flush();
		if(cout!=null)cout.close();
		System.out.println("客户端文件传输成功！");
		//第三步
		return getmes;
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