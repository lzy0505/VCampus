package client;
import java.net.*;

import utils.Message;

import java.io.*;
/*
 * <p>MyClient</p>
 * 客户端，用于与服务端连接并接发数据
 * @author
 */
public class Client{
	private ObjectInputStream cin;//对象输入流
	private ObjectOutputStream cout;//对象输出流
	
	public Client()throws IOException {
		Socket clientSocket=new Socket("192.168.1.12",8080);
		System.out.println("链接建立成功");
		OutputStream os=clientSocket.getOutputStream();
		//包装一个输出流
		cout=new ObjectOutputStream(os);
		
		InputStream is=clientSocket.getInputStream();
		cin=new ObjectInputStream(is);
	}
	
	
	//发送数据包
	public void SendMessage(Message sendmes) throws IOException 
	{	 
		cout.writeObject(sendmes);
	}
	//获取数据包
	public Message GetMessage()throws IOException 
	{
		
		Message getmes = null;
		try {
			getmes = (Message)cin.readObject();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return getmes;
	}
	
	
	
	
	
	
}
