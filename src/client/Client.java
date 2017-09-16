package client;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import java.io.File;

import com.sun.swing.internal.plaf.basic.resources.basic;

import utils.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
/*
 * <p>MyClient</p >
 * 客户端，用于与服务端连接并接发数据
 * @author
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
	            	File[] files;
					try {
						files = (File[])cin.readObject();
					
	            	file_path= new String[files.length];
	            	System.out.println("开始接收数据...");  
		            for(int i=0;i<files.length;i++) {	
		            	//getDirPath 是ClientInfo的静态方法
		            	System.out.println(" path:"+files[i].getName());
		            	file_path[i] =ClientInfo.getDirPath() + files[i].getName();
		            	File file = new File(ClientInfo.getDirPath()+files[i].getName());
		            	if(!file.exists()) {
		            		fos = new FileOutputStream(new File(ClientInfo.getDirPath()+files[i].getName())); 
			              	FileInputStream fis = new FileInputStream(files[i]);
			                byte[] buffer = new byte[1];
			    			while (fis.read(buffer) != -1) {
			    				fos.write(buffer);
			    			}    			
			    			fos.flush();
			    			if (fis != null) 
			    			fis.close();
			    			if (fos != null)  
			    			fos.close();
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