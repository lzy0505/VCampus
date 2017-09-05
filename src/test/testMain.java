/**
 * 
 */
package test;
import client.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import server.DataBase;

/**
 * @author lzy05
 *
 */
public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,String> a = new HashMap<String,String>();
		HashMap<String,String> b = new HashMap<String,String>();
		HashMap<String,String> c = new HashMap<String,String>();
		HashMap<String,String> d = new HashMap<String,String>();
		HashMap<String,String> i = new HashMap<String,String>();
		HashMap<String,String> f = new HashMap<String,String>();
		HashMap<String,String> g = new HashMap<String,String>();
		HashMap<String,String> h = new HashMap<String,String>();
		//模拟注册
		
		a.put("username","zihou11");
		a.put("password","123456");
		a.put("op", "sign up");
	//模拟重复注册
		
		f.put("username","zihou11");
		f.put("password","123456");
		f.put("op", "sign up");
		//模拟登陆失败
		
		b.put("username","zihou11");
		b.put("password","654321");
		b.put("op", "sign in");
		//模拟登陆成功
		
		i.put("username","zihou11");
		i.put("password","123456");
		i.put("op", "sign in");
		
		try {
			System.out.println("模拟注册:");
			Client client1 = new Client();
			client1.clientSocket = new Socket("localhost",8080);
			System.out.println("链接1建立成功");
			client1.sendMessage(a);
			
			c = client1.getMessage();
			
			String cs = c.get("result");
			System.out.println("c :" +cs);
			client1.clientSocket.close();
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {	
			System.out.println("模拟重复注册:");
			Client client2 = new Client();
			client2.clientSocket = new Socket("localhost",8080);
			System.out.println("链接2建立成功");
			client2.sendMessage(f);
			d = client2.getMessage();
			String ds = d.get("result");
			System.out.println("d :" +ds);
			String re = d.get("reason");
			System.out.println("d :" +re);
			client2.clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();// TODO: handle exception
		}
		try {
			
			System.out.println("模拟登录失败:");
			Client client3 = new Client();
			client3.clientSocket = new Socket("localhost",8080);
			System.out.println("链接3建立成功");
			client3.sendMessage(b);
			
			g = client3.getMessage();
			
			String cs = g.get("result");
			System.out.println("g :" +cs);
			String re = g.get("reason");
			System.out.println("reason :" +re);
			client3.clientSocket.close();
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			System.out.println("模拟登录成功:");
			Client client4 = new Client();
			client4.clientSocket = new Socket("localhost",8080);
			System.out.println("链接4建立成功");
			client4.sendMessage(i);
			
			h= client4.getMessage();
			
			String cs = h.get("result");
			System.out.println("h :" +cs);
			client4.clientSocket.close();
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

}

