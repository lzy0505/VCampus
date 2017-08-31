package server;
//filename:MyServer.java
/*
 * <p>MyServer</p>
 * 服务端，用于监听端口与客户端作连接，接收并响应客户端的信息
 * @author
 */
import java.net.*;

import utils.Message;

import java.io.*;

public class Server{
	
	private ServerSocket server=null; //监听端口
	private ObjectInputStream sin; //对象输入流
	private ObjectOutputStream sout;//对象输出流
	
	//构造函数，用于与端口绑定
	public Server() throws IOException
	{
		try{
			//与一个窗口绑定
			server =new ServerSocket(8080,10);
			System.out.println("绑定端口为："+server.getLocalPort());	
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	

	public static void main(String args[])throws IOException{	
		new Server().serviceThread();
	}
	
	
	public void serviceThread() {
		while(true) 
		{
			Socket socket=null;
			try {
				//开始等待
				socket=server.accept();
				System.out.println("服务器开始监听");
				//创建一个工作线程
				Thread workThread = new Thread(new Hanlder(socket));
				//线程开始工作
				workThread.start();
				
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}		
		}
		
	}
	//Handler实现接口Runnable，负责与单个客户进行通信
	class Hanlder implements Runnable{
		private Socket socket;
		public Hanlder(Socket socket){
			this.socket=socket;
		}
		//重写run
		public void run() {
			/*有待完善*/////////////////////////

			InputStream is;
			try {
				is = socket.getInputStream();
				sin=new ObjectInputStream(is);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			//创建一个输入流对象
			//创建一个Message对象aLine,接受客户端发送的对象
			Message aLine;
			try {
				aLine = (Message)sin.readObject();
				System.out.println("如果没问题，对象传送这一块安排上了。");
				System.out.println("aLine.getOpType=?? \n" + aLine.getOpType());
				
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		
	}
	

	public void finalize()
	{
		try{
			if(server!=null)server.close();
		}catch(IOException e)
		{
			System.out.println(e);
		}
		
		
	}






}
