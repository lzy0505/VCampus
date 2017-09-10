package client;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.swing.internal.plaf.basic.resources.basic;

import utils.*;
import java.io.*;
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