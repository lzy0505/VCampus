/**
 * 
 */
package server;

import java.io.*;
import java.net.*;
/*
 * @author storm
 *
 */
/**
 *<p>Server</p>
 * <p>服务器类<br>
 * 该类提供了与客户端连接的端口，多线程循环监听
 * </p>
 * @author 李子厚
 */
public class Server{
	private int port = 8080;   //端口号
	private ServerSocket serverSocket;
	private Socket socket=null;
	private int count=1;
	
	public void startServer() throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println("Listen to " + serverSocket.getLocalPort());
		while(true) {
			socket = null;
			socket = serverSocket.accept();
			System.out.println("accept port numbers:" + count);
			Thread thread = new Thread(new ServerThread(socket));
			thread.start();
			count++;
		}
	}

	
	public static void main(String args[])throws IOException
	{
		new Server().startServer();
	}
	
}

