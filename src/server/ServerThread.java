/**
 * 
 */
package server;

import java.awt.Choice;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.ForegroundAction;

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
	private DataBase db=null;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
				
		
	}
	
	public void run() {
			try {
				db=new DataBase();
				db.connectToDB();
				sois = new ObjectInputStream(socket.getInputStream());
				soos = new ObjectOutputStream(socket.getOutputStream());
				//a is used to send message 
				HashMap<String,String> a =new HashMap<String,String>();
				//b is used to get message	
				HashMap<String,String> b =new HashMap<String,String>();
				b = (HashMap<String,String>) sois.readObject();
				
				//op is used to decide what user will do 
				String op = b.get("op");
				//a HashMap array
				ArrayList<HashMap<String,String>> aList=null;
				switch (op) {
				case "sign in":
					
					//get a array of HashMap whose username equal to b'username
					aList=db.selectWhere("users", "username = "+"\'"+b.get("username")+"\'");
					if(aList.size()==0) {
						a.put("result", "fail");
						a.put("reason", "Username or Password is false!");
						break;
					}
					else if (aList.get(0).get("password").equals(b.get("password"))&&aList.get(0).get("identity").equals(b.get("identity"))) {
						a.put("username",b.get("username") );
						a.put("result", "success");
						break;
					}
					else {
						a.put("result", "fail");
						break;
					}
					
				case "sign up":
					
					aList=db.selectWhere("users", "username = "+"\'"+b.get("username")+"\'");
					// if there's no username same as b'username,which means sing up is allowable;
					if(aList.size()==0) {
						b.remove("op");
						
						db.insert("users",b );
						a.put("result", "success");
						break;
					}
					else {
						a.put("result","fail");
						a.put("reason","Username has been used!");
						break;
					}
				case "searchbook":
					if(b.get("search_type").equals("author")) 
					{
						aList=db.selectWhere("book_info", "author LIKE \'%"+b.get("keyword")+"%\'");	
					}
					else 
					{
						aList=db.selectWhere("book_info", "book_name LIKE \'%"+b.get("keyword")+"%\'");					
					}
					
					if(aList.size()>=1) {
					a.put("book_name1", aList.get(0).get("book_name"));
					a.put("author1", aList.get(0).get("author"));
					a.put("quantity1", aList.get(0).get("quantity"));
					a.put("publish1", aList.get(0).get("publish"));
					}
					if(aList.size()>=2) {
					a.put("book_name2", aList.get(1).get("book_name"));
					a.put("author2", aList.get(1).get("author"));
					a.put("quantity2", aList.get(1).get("quantity"));
					a.put("publish2", aList.get(1).get("publish"));
					}
					if(aList.size()>=3) {
					a.put("book_name3", aList.get(2).get("book_name"));
					a.put("author3", aList.get(2).get("author"));
					a.put("quantity3", aList.get(2).get("quantity"));
					a.put("publish3", aList.get(2).get("publish"));
					}
					if(aList.size()>=4) {
					a.put("book_name4", aList.get(3).get("book_name"));
					a.put("author4", aList.get(3).get("author"));
					a.put("quantity4", aList.get(3).get("quantity"));
					a.put("publish4", aList.get(3).get("publish"));
					}
					if(aList.size()>=5) {
					a.put("book_name5", aList.get(4).get("book_name"));
					a.put("author5", aList.get(4).get("author"));
					a.put("quantity5", aList.get(4).get("quantity"));
					a.put("publish5", aList.get(4).get("publish"));
					}
				
					break;
				}
				soos.writeObject(a);
				
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	
	}
	

}

