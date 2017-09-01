/**
 * 
 */
package server;

import java.awt.Choice;
import java.io.*;
import java.net.*;
import java.util.HashMap;

import utils.User;


/**
 * @author storm
 *
 */
public class ServerThread implements Runnable{
	private boolean flag;
	private Socket socket=null;
	private ObjectInputStream sois = null;
	private ObjectOutputStream soos = null;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
				
		
	}
	
	public void run() {
			try {
				sois = new ObjectInputStream(socket.getInputStream());
				soos = new ObjectOutputStream(socket.getOutputStream());
				HashMap<String,String> a =new HashMap<String,String>();
				HashMap<String,String> b =new HashMap<String,String>();
				b = (HashMap<String,String>) sois.readObject();
				
				if(b.get("username").equals("qwe")) {
					a.put("result", "success");
				}else {
					a.put("result", "fault");
				}
				soos.writeObject(a);
				
				
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

