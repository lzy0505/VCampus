/**
 * 
 */
package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author storm
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			Socket socket = null;
			System.out.println("服务器启动，等待连接，端口："+ serverSocket.getLocalPort());;
			while (true) {
				//开始等待
				socket=serverSocket.accept();
				System.out.println("服务器开始监听");
				//创建一个工作线程
				Thread workThread = new Thread(new ServerThread(socket));
				//线程开始工作
				workThread.start();			
			}			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
