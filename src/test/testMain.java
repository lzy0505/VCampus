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
		a.put("zihou","SB");
		a.put("op", "login");
		b.put("zongyuan","sb");
		b.put("op", "reg");
		try {
			Client client1 = new Client();
			client1.clientSocket = new Socket("localhost",8080);
			System.out.println("链接1建立成功");
			client1.SendMessage(a);
			c = client1.GetMessage();
			String cs = c.get("result");
			System.out.println("c :" +cs);
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {	
			Client client2 = new Client();
			client2.clientSocket = new Socket("localhost",8080);
			System.out.println("链接2建立成功");
			client2.SendMessage(b);
			d = client2.GetMessage();
			String ds = d.get("result");
			System.out.println("d :" +ds);
		} catch (IOException e) {
			e.printStackTrace();// TODO: handle exception
		}
		
	}

}

