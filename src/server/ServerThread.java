/**
 * 
 */
package server;

import java.awt.Choice;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

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
	
	HashMap<String,String> send =new HashMap<String,String>();//send is used to send message 
	ArrayList<HashMap<String,String>> sendList=null;//a HashMap array
	ArrayList<HashMap<String,String>> getList =new ArrayList<HashMap<String,String>>();
	HashMap<String,String> getOne =new HashMap<String,String>();//get is used to get message	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	public void run() {
			try {
				db=new DataBase();
				db.connectToDB();
				sois = new ObjectInputStream(socket.getInputStream());
				soos = new ObjectOutputStream(socket.getOutputStream());				
				getOne = (HashMap<String,String>) sois.readObject();	
				
				//op is used to decide what user will do 
				String op = getOne.get("op");	
				switch (op) {
				case "sign in":
					//get a array of HashMap whose username equal to b'username
					sendList=db.selectWhere("users", "username = "+"\'"+getOne.get("username")+"\'");
					if(sendList.size()==0) {
						send.put("result", "fail");
						send.put("reason", "Username or Password is false!");
						soos.writeObject(send);
						break;
					}
					else if (sendList.get(0).get("password").equals(getOne.get("password"))&&sendList.get(0).get("identity").equals(getOne.get("identity"))) {
						send.put("username",getOne.get("username") );
						send.put("result", "success");
						soos.writeObject(send);
						break;
					}
					else {
						send.put("result", "fail");
						soos.writeObject(send);
						break;
					}
					
				case "sign up":
					
					sendList=db.selectWhere("users", "username = "+"\'"+getOne.get("username")+"\'");
					// if there's no username same as b'username,which means sing up is allowable;
					if(sendList.size()==0) {
						getOne.remove("op");
						
						db.insert("users",getOne );
						send.put("result", "success");
						soos.writeObject(send);
						break;
					}
					else {
						send.put("result","fail");
						send.put("reason","Username has been used!");
						soos.writeObject(send);
						break;
					}
				case "searchbook":
					if(getOne.get("search_type").equals("author")) 
					{
						sendList=db.selectWhere("book_info", "author LIKE \'%"+getOne.get("keyword")+"%\'");	
					}
					else 
					{
						sendList=db.selectWhere("book_info", "book_name LIKE \'%"+getOne.get("keyword")+"%\'");
					}
					soos.writeObject(sendList);				
					break;
				case "borrow":
					String bookname=getOne.get("book_name");
					bookname=bookname.replaceAll("[']", "\'\'");
					System.out.println(bookname);
					sendList=db.selectWhere("book_info", "book_name = "+"\'"+bookname+"\'");
					ArrayList<HashMap<String,String>> bList=null;
					bList = db.selectWhere("book", "book_info_id ="+sendList.get(0).get("book_info_id"));
					System.out.println(bList.get(0).get("book_id"));
					System.out.println(bList.get(0).get("book_info_id"));
					for(int i =0;i<bList.size();i++)
					{
						if(bList.get(i).get("is_borrowed").equals("FALSE"))
						{
							Date date =new Date();
							SimpleDateFormat df= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
							String sdf = df.format(date);
							System.out.println(getOne.get("user_name"));
							db.setWhere("book", "reader=\'"+ getOne.get("user_name")+"\',"+"borrow_date=#"+ sdf +"#,"+"is_borrowed="+ "TRUE","book_id="+bList.get(i).get("book_id"));
							db.setWhere("book_info", "quantity=quantity-1","book_info_id="+bList.get(i).get("book_info_id"));
							send.put("result", "successfully");
							send.put("book_name",getOne.get("book_name"));
							break;
						}
						if(i==(bList.size()-1))
						{
							send.put("result","unsuccessfully");
							send.put("book_name",getOne.get("book_name"));
						}
					}
					soos.writeObject(send);
					break;
				case "search_unreturn":
					sendList=db.selectWhere("book", "reader = "+"\'"+getOne.get("user_name")+"\'");
					if(sendList.size()==0){
						soos.writeObject(sendList);
						break;
					}
					else{
						ArrayList<HashMap<String,String>> cList=new ArrayList<HashMap<String,String>>();
						for(int i =0;i<sendList.size();i++)
						{
							ArrayList<HashMap<String,String>> idList = db.selectWhere("book_info", "book_info_id ="+sendList.get(i).get("book_info_id"));
							idList.get(0).put("book_id", sendList.get(i).get("book_id"));
							cList.add(idList.get(0));						
						}
						soos.writeObject(cList);
						break;
					}
				case "return":
					sendList=db.selectWhere("book", "book_id = "+getOne.get("book_id"));
					db.setWhere("book", "reader=null,"+"is_borrowed="+ "FALSE","book_id="+getOne.get("book_id"));
					db.setWhere("book_info", "quantity=quantity+1","book_info_id="+sendList.get(0).get("book_info_id"));
					sendList=db.selectWhere("book", "book_id = "+getOne.get("book_id"));
					if(sendList.get(0).get("is_borrowed").equals("FALSE")) {
						send.put("result", "successfully");
						send.put("book_name", getOne.get("book_name"));
						soos.writeObject(send);
						break;
					}
					else {
						send.put("result", "unsuccessfully");
						send.put("book_name", getOne.get("book_name"));
						soos.writeObject(send);
						break;
					}	
				}
				db.finalize();	
				
					
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	
	}
	public void name() {
		
	}
	

}
