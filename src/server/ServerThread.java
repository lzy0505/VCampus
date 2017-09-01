/**
 * 
 */
package server;

import java.io.*;
import java.net.*;

import utils.User;


/**
 * @author storm
 *
 */
public class ServerThread implements Runnable{
	private Socket socket=null;
	private ObjectInputStream sois = null;
	private ObjectOutputStream soos = null;
	private UserService us=new UserService();
	private FileService fs=new FileService();
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		
		try {
			sois = new ObjectInputStream(socket.getInputStream());
			soos = new ObjectOutputStream(socket.getOutputStream());
			User b =new User();
			b = (User) sois.readObject();
			System.out.println("b.username: " + b.getUsername());
			/*
			 * 接受到指令后，做出响应操作这里有待完善！！！！！！！！
			 */
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		
	}
	

}
