/**
 * 
 */
package server;

import java.awt.Choice;
import java.awt.Image;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.crypto.provider.HmacMD5;

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
	private FileInputStream fis;
    private DataOutputStream dos;
	private DataBase db=null;
    int length = 0;  
    byte[] sendBytes = null;  
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
				dos = new DataOutputStream(socket.getOutputStream());  
				//op is used to decide what user will do 
				String op = getOne.get("op");
				String type = getOne.get("type");
				switch (op) {
				case "sign in":
					//get a array of HashMap whose card_id equal to b'card_id
					sendList=db.selectWhere("users", "card_id = "+"\'"+getOne.get("card_id")+"\'");
					if(sendList.size()==0) {
						send.put("result", "fail");
						send.put("reason", "card_id or Password is false!");
						soos.writeObject(send);
						break;
					}
					else if (sendList.get(0).get("password").equals(getOne.get("password"))&&sendList.get(0).get("identity").equals(getOne.get("identity"))) {
						send.put("card_id",getOne.get("card_id") );
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
					sendList=db.selectWhere("users", "card_id = "+"\'"+getOne.get("card_id")+"\'");
					// if there's no card_id same as b'card_id,which means sing up is allowable;
					if(sendList.size()==0) {
						
						getOne.remove("op");
						db.insert("users",getOne );
						sendList=db.selectWhere("users", "card_id = "+"\'"+getOne.get("card_id")+"\'");
						System.out.println("Sign up" + " card_id = "+sendList.get(0).get("card_id") + "password = " +sendList.get(0).get("password"));
						send.put("result", "success");
						soos.writeObject(send);
						break;
					}
					else {
						send.put("result","fail");
						send.put("reason","card_id has been used!");
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
							System.out.println(getOne.get("card_id"));
							db.setWhere("book", "reader=\'"+ getOne.get("card_id")+"\',"+"borrow_date=#"+ sdf +"#,"+"is_borrowed="+ "TRUE","book_id="+bList.get(i).get("book_id"));
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
					sendList=db.selectWhere("book", "reader = "+"\'"+getOne.get("card_id")+"\'");
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
				case "search_course":
					//return course_name,credits and course_info_id
					ArrayList<HashMap<String,String>> scList=db.selectWhere("course_records", "course_student =\'"+getOne.get("card_id")+"\'");
					sendList=new ArrayList<HashMap<String,String>>();
					for(int i=0;i<scList.size();i++){
						ArrayList<HashMap<String,String>> courseList = db.selectWhere("course_info", "course_info_id ="+ scList.get(i).get("course_info_id"));
						courseList.get(0).put("course_record_id", scList.get(i).get("course_record_id"));
						courseList.get(0).put("select_status", scList.get(i).get("select_status"));
						courseList.get(0).put("course_info_id", scList.get(i).get("course_info_id"));
						courseList.get(0).put("course_exam_status", scList.get(i).get("course_exam_status"));					
						if(scList.get(i).get("select_status").equals("TRUE")) {
							ArrayList<HashMap<String,String>> cdlList = db.selectWhere("course_details", "course_id ="+ scList.get(i).get("course_id"));
							courseList.get(0).put("course_teacher", cdlList.get(0).get("course_teacher"));
							courseList.get(0).put("course_id", scList.get(i).get("course_id"));
							courseList.get(0).put("course_score", scList.get(i).get("course_score"));
							courseList.get(0).put("course_exam_time", cdlList.get(0).get("course_exam_time"));
							courseList.get(0).put("course_exam_place", cdlList.get(0).get("course_exam_place"));
						}
						sendList.add(courseList.get(0));
					}
					soos.writeObject(sendList);
					break;
				case "choose_course":
					//return details to client
					sendList=db.selectWhere("course_details", "course_info_id= "+getOne.get("course_info_id"));
					for(int i = 0;i<sendList.size();i++){
						sendList.get(i).put("course_record_id", getOne.get("course_record_id"));
						sendList.get(i).put("coure_name", getOne.get("course_name"));
						sendList.get(i).put("select_status", getOne.get("select_status"));
					}
					soos.writeObject(sendList);
					break;
				case "choose_ok":
					//use course_record_id to choose course
					sendList=db.selectWhere("course_records", "course_record_id="+getOne.get("course_record_id"));
					if(sendList.get(0).get("select_status").equals("TRUE")) {
						db.setWhere("course_details", "course_selected_number=course_selected_number-1,course_is_full=FALSE", "course_id="+sendList.get(0).get("course_id"));
					}
					db.setWhere("course_records", "course_id="+getOne.get("course_id")+",select_status=" + "TRUE", "course_record_id=" + getOne.get("course_record_id"));
					sendList=db.selectWhere("course_details", "course_id= "+getOne.get("course_id"));
					db.setWhere("course_details", "course_selected_number = course_selected_number+1", "course_id=" + getOne.get("course_id"));
					if(sendList.get(0).get("course_selected_number").equals(sendList.get(0).get("course_max_number")))
						db.setWhere("course_details", "course_is_full ="+ "TRUE", "course_id=" + getOne.get("course_id"));
					send.put("result", " successfully");
					soos.writeObject(send);
					break;
				case "QueryBalance":
					sendList=db.selectWhere("card_info", "card_id="+getOne.get("card_id"));
					send.put("card_balance", sendList.get(0).get("card_balance"));
					soos.writeObject(send);
					break;
				case "QueryPayment":
					sendList=new ArrayList<HashMap<String,String>>();
					switch (type) {
					case "Tuition":
						ArrayList<HashMap<String,String>> cardInfoList=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						cardInfoList = db.selectWhere("card_records", "card_info_id="+cardInfoList.get(0).get("card_info_id"));
						for(int i= 0;i<cardInfoList.size();i++) {
							if(cardInfoList.get(i).get("card_content").equals(getOne.get("type")))sendList.add(cardInfoList.get(i));
						}
						soos.writeObject(sendList);
						break;
					case "WandE":
						ArrayList<HashMap<String,String>> cardInfoListWanE=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						cardInfoListWanE = db.selectWhere("card_records", "card_info_id="+cardInfoListWanE.get(0).get("card_info_id"));
						for(int i= 0;i<cardInfoListWanE.size();i++) {
							if(cardInfoListWanE.get(i).get("card_content").equals(getOne.get("type")))sendList.add(cardInfoListWanE.get(i));
						}
						soos.writeObject(sendList);
						break;
					case "Afee":
						ArrayList<HashMap<String,String>> cardInfoListAfee=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						cardInfoListAfee = db.selectWhere("card_records", "card_info_id="+cardInfoListAfee.get(0).get("card_info_id"));
						for(int i= 0;i<cardInfoListAfee.size();i++) {
							if(cardInfoListAfee.get(i).get("card_content").equals(getOne.get("type")))sendList.add(cardInfoListAfee.get(i));
						}
						soos.writeObject(sendList);
						break;
					}
					break;
				case "Payment":					
						ArrayList<HashMap<String,String>> cardRecordsListTuition=db.selectWhere("card_records", "card_record_id="+getOne.get("card_record_id"));
						ArrayList<HashMap<String,String>> cardInfoListTuition=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						ArrayList<HashMap<String,String>> users = db.selectWhere("users", "card_id=\'"+getOne.get("card_id")+"\'");
						System.out.println("balance :" +cardInfoListTuition.get(0).get("card_balance"));
						System.out.println("cost :" +cardRecordsListTuition.get(0).get("card_cost"));
						System.out.println("password :" +users.get(0).get("password"));
						System.out.println("password form client :" +getOne.get("password"));
						if(Float.parseFloat((cardInfoListTuition.get(0).get("card_balance")))<Float.parseFloat(cardRecordsListTuition.get(0).get("card_cost"))) {
							send.put("result", "false");
							send.put("reason", "Balance is unenough!");
							soos.writeObject(send);
							break;
						}
						else if(!users.get(0).get("password").equals(getOne.get("password"))){
							send.put("result", "false");
							send.put("reason", "Password is incorrect!");
							soos.writeObject(send);
							break;
						}
						else {
							String balance = (Float.parseFloat((cardInfoListTuition.get(0).get("card_balance")))-Float.parseFloat(cardRecordsListTuition.get(0).get("card_cost")))+"";
							System.out.println("balance now :" + balance);
							db.setWhere("card_info", "card_balance ="+balance, "card_id=\'" + getOne.get("card_id")+"\'");
							db.setWhere("card_records", "card_is_paid ="+ "TRUE", "card_record_id=" + getOne.get("card_record_id"));
							send.put("result", "success");
							soos.writeObject(send);
							break;
						}
				case "recharge":
					ArrayList<HashMap<String,String>> usersRecharge = db.selectWhere("users", "card_id=\'"+getOne.get("card_id")+"\'");
					ArrayList<HashMap<String,String>> cardInfoBalance=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
					if(!usersRecharge.get(0).get("password").equals(getOne.get("password"))) {
						send.put("result", "false");
						send.put("reason", "Password is incorrect!");
						soos.writeObject(send);
						break;
					}
					else {
						String balance = (Float.parseFloat(cardInfoBalance.get(0).get("card_balance"))+Float.parseFloat(getOne.get("amount"))) + "";
						db.setWhere("card_info", "card_balance="+ balance, "card_id=\'" + getOne.get("card_id")+"\'");
						send.put("result", "success");	
						soos.writeObject(send);
						break;
					}
				//初始化商品信息细节
				case "init_product":
					sendList = new ArrayList<HashMap<String, String>>();
					ArrayList<HashMap<String, String>> goood = db.selectWhere("store_item_info", "item_name =\'可乐\'");
					sendList.add(goood.get(0));
					ArrayList<HashMap<String, String>> goood1 = db.selectWhere("store_item_info", "item_name =\'牙刷\'");
					sendList.add(goood1.get(0));
					ArrayList<HashMap<String, String>> goood2 = db.selectWhere("store_item_info", "item_name =\'抽纸\'");
					sendList.add(goood2.get(0));
					ArrayList<HashMap<String, String>> goood3 = db.selectWhere("store_item_info", "item_name =\'薯片\'");
					sendList.add(goood3.get(0));
					soos.writeObject(sendList);
					break;
				//初始化商品图片
				case "init_pic":
					File[] files_init = new File[4];
					files_init[0] =new File("serverpic/可乐.jpg");
					files_init[1] =new File("serverpic/牙刷.jpg");
					files_init[2] =new File("serverpic/抽纸.jpg");
					files_init[3] =new File("serverpic/薯片.jpg");
					soos.writeObject(files_init);
					soos.flush();
					if(soos!=null)soos.close();
					System.out.println("传完了.");  
					if(socket!=null) socket.close();
					break;
					
				//查询商品的细节
				case "search_product":
					sendList=db.selectWhere("store_item_info", "item_name LIKE \'%"+getOne.get("key")+"%\'");
					soos.writeObject(sendList);
					break;
				//查询商品的图片
				case "search_picture":
					sendList=db.selectWhere("store_item_info", "item_name LIKE \'%"+getOne.get("key")+"%\'");
					File[] files = new File[sendList.size()];
					//把搜索到的文件都装到数组里面
					for(int i=0;i<sendList.size();i++) {
						files[i] = new File(sendList.get(i).get("item_picture_url"));
					}
					System.out.println("开始传..");  
					soos.writeObject(files);
					soos.flush();
					if(soos!=null)soos.close();
					System.out.println("传完了.");  
					if(socket!=null) socket.close();
					break;
				//购买确认
				case "buy":
					//获取当前时间
					Date date =new Date();
					SimpleDateFormat df= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					String sdf = df.format(date);
					//先查完余额再去比较决定是否购买成功
					sendList = db.selectWhere("card_info", "card_id=\'" + getOne.get("card_id")+"\'");
					ArrayList<HashMap<String, String>> store_item_info_list = db.selectWhere("store_item_info", "item_name =\'"+getOne.get("item_name")+"\'");     
					if(Integer.parseInt(store_item_info_list.get(0).get("item_stock"))<Integer.parseInt(getOne.get("quantity"))) {
						send.put("result", "false");
						send.put("item_name", getOne.get("item_name"));
						send.put("reason", "库存不足");
						soos.writeObject(send);
						break;
					}
					if(Float.parseFloat(sendList.get(0).get("card_balance"))>=Float.parseFloat(getOne.get("cost"))) {
						float balance_now = Float.parseFloat(sendList.get(0).get("card_balance"))-Float.parseFloat(getOne.get("cost"));
						db.setWhere("card_info", "card_balance =" + balance_now, "card_id=\'" + getOne.get("card_id")+"\'");
						HashMap<String, String> record = new HashMap<>();
						//将购买记录打包查到记录表里
						record.put("purchase_time","#"+ sdf +"#");
						record.put("purchase_cost", getOne.get("cost"));
						record.put("card_id", getOne.get("card_id"));
						record.put("purchase_content", "网络商店购物");
						db.insert("store_purchase_records", record);
						//将购买的物品相应减少数量
						db.setWhere("store_item_info", "item_stock ="+getOne.get("quantity_now"), "item_name =\'"+getOne.get("item_name")+"\'");
						//返回结果
						send.put("result", "success");
						send.put("item_name", getOne.get("item_name"));
						soos.writeObject(send);
						break;
					}else {
						send.put("result", "false");
						send.put("reason", "余额不足");
						soos.writeObject(send);
					}
					
				default:
					send.put("result","No such operation!");
					soos.writeObject(send);
					break;
					
					
					
					
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
