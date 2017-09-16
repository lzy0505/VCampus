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
					sendList=db.selectWhere("users", "card_id = \'"+getOne.get("card_id")+"\'");
					// if there's no card_id same as b'card_id,which means sing up is allowable;
					if(sendList.size()==0) {
						getOne.remove("op");
//						getOne.put("user_info_id", "null");
						getOne.put("card_id", "\'"+getOne.get("card_id")+"\'");
						getOne.put("identity", "\'"+getOne.get("identity")+"\'");
						getOne.put("password", "\'"+getOne.get("password")+"\'");
						db.insert("users",getOne );
//						sendList=db.selectWhere("users", "card_id = "+getOne.get("card_id"));
//						System.out.println("Sign up" + " card_id = "+sendList.get(0).get("card_id") + "password = " +sendList.get(0).get("password"));
						HashMap hm = new HashMap<String,String>();
						hm.put("card_id", getOne.get("card_id"));
						hm.put("card_balance", "0");
						db.insert("card_info",hm);
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
							idList.get(0).put("borrow_date", sendList.get(i).get("borrow_date"));
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
							courseList.get(0).put("course_teacher", db.selectWhere("user_info","user_info_id="+db.selectWhere("users","card_id=\'"+cdlList.get(0).get("course_teacher")+"\'").get(0).get("user_info_id")).get(0).get("nname"));
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
						sendList.get(i).replace("course_teacher", db.selectWhere("user_info","user_info_id="+db.selectWhere("users","card_id=\'"+sendList.get(i).get("course_teacher")+"\'").get(0).get("user_info_id")).get(0).get("nname"));
					}
					soos.writeObject(sendList);
					break;
				case "choose_ok":
					//use course_record_id to choose course
					sendList=db.selectWhere("course_records", "course_record_id="+getOne.get("course_record_id"));
					if(sendList.get(0).get("select_status").equals("TRUE")) {
						db.setWhere("course_details", "course_selected_number=course_selected_number-1,course_is_full=FALSE", "course_id="+sendList.get(0).get("course_id"));
					}
					db.setWhere("course_records", "course_id="+getOne.get("course_id")+",select_status=TRUE,course_exam_status=FALSE", "course_record_id=" + getOne.get("course_record_id"));
					sendList=db.selectWhere("course_details", "course_id= "+getOne.get("course_id"));
					db.setWhere("course_details", "course_selected_number = course_selected_number+1", "course_id=" + getOne.get("course_id"));
					if(sendList.get(0).get("course_selected_number").equals(sendList.get(0).get("course_max_number")))
						db.setWhere("course_details", "course_is_full ="+ "TRUE", "course_id=" + getOne.get("course_id"));
					send.put("result", " successfully");
					soos.writeObject(send);
					break;
				case "QueryBalance":				
					sendList=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
					if(sendList.size()==0){
						send.put("card_balance", "0");	
						soos.writeObject(send);
						break;
					}
					else{
						send.put("card_balance", sendList.get(0).get("card_balance"));				
						soos.writeObject(send);
						break;
					}
					
				case "QueryPayment":
					sendList=new ArrayList<HashMap<String,String>>();
					switch (type) {
					case "Tuition":
						ArrayList<HashMap<String,String>> cardInfoList=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						if(cardInfoList.isEmpty()) {
							HashMap<String,String> re=new HashMap<String,String>();	
							re.put("result","错误的一卡通号");
							sendList.add(re);				
						}else {
							cardInfoList = db.selectWhere("card_records", "card_info_id="+cardInfoList.get(0).get("card_info_id"));
							for(int i= 0;i<cardInfoList.size();i++) {
								if(cardInfoList.get(i).get("card_content").equals(getOne.get("type")))sendList.add(cardInfoList.get(i));
							}
						}
						soos.writeObject(sendList);
						break;
					case "WandE":					
						ArrayList<HashMap<String,String>> cardInfoListWanE=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						if(cardInfoListWanE.isEmpty()) {
							HashMap<String,String> re=new HashMap<String,String>();	
							re.put("result","错误的一卡通号");
							sendList.add(re);
						}else {
							cardInfoListWanE = db.selectWhere("card_records", "card_info_id="+cardInfoListWanE.get(0).get("card_info_id"));
							for(int i= 0;i<cardInfoListWanE.size();i++) {
								if(cardInfoListWanE.get(i).get("card_content").equals(getOne.get("type")))sendList.add(cardInfoListWanE.get(i));
							}
						}
						soos.writeObject(sendList);
						break;
					case "Afee":
						ArrayList<HashMap<String,String>> cardInfoListAfee=db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
						if(cardInfoListAfee.isEmpty()) {
							HashMap<String,String> re=new HashMap<String,String>();	
							re.put("result","错误的一卡通号");
							sendList.add(re);
						}else {
							cardInfoListAfee = db.selectWhere("card_records", "card_info_id="+cardInfoListAfee.get(0).get("card_info_id"));
							for(int i= 0;i<cardInfoListAfee.size();i++) {
								if(cardInfoListAfee.get(i).get("card_content").equals(getOne.get("type")))sendList.add(cardInfoListAfee.get(i));
							}
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
				case "search_student":
					//通过学号或者姓名查找学生，找不到就给个空数组
					System.out.println("name :" +getOne.get("nname")+ "student_id :"+getOne.get("student_id"));
					ArrayList<HashMap<String,String>> use_name_to_find = db.selectWhere("user_info", "nname =\'" + getOne.get("nname")+"\'");
					if(use_name_to_find.size()==0) {
						ArrayList<HashMap<String,String>> use_student_id_to_find = db.selectWhere("user_info", "student_id =\'" + getOne.get("student_id")+"\'");
						if(use_student_id_to_find.size()==0){
							soos.writeObject(sendList);
							break;
						}else {
							soos.writeObject(use_student_id_to_find);
							break;
						}
					}else {
						soos.writeObject(use_name_to_find);
						break;
					}
				case "modify_student":
					//修改学生信息
					int grade = Integer.parseInt(getOne.get("grade"));
					db.setWhere("user_info", "nname =\'"+getOne.get("nname")+"\'," +"gender =\'"+getOne.get("gender")+"\',"+ "grade ="+grade +","+"major =\'"+ getOne.get("major")+"\',"+ "student_id =\'" + getOne.get("student_id")+"\'" , "user_info_id =" + getOne.get("user_info_id"));
					break;
				case "delete_student":
					db.setWhere("users", "user_info_id =null", "user_info_id ="+ getOne.get("user_info_id"));
					db.deleteWhere("user_info", "student_id =\'"+ getOne.get("student_id")+"\'");				
					break;
				case "import_student":
					String card_id= getOne.get("card_id");
					String student_id = getOne.get("student_id");
					System.out.println("card_id :"+getOne.get("card_id"));
					getOne.remove("op");
					getOne.remove("card_id");
					db.insert("user_info", getOne);
					System.out.println("student_id :"+getOne.get("student_id"));
					ArrayList<HashMap<String,String>> user_info_list = db.selectWhere("user_info", "student_id="+student_id);
					System.out.println("user_info_id :"+user_info_list.get(0).get("user_info_id"));
					db.setWhere("users", "user_info_id =" + user_info_list.get(0).get("user_info_id"), "card_id =\'" +card_id +"\'");
					break;
				case "add_book":
					getOne.remove("op");
					db.insert("book_info", getOne);
					ArrayList<HashMap<String,String>> book_info_list = db.selectWhere("book_info", "book_name =\'"+getOne.get("book_name")+"\'");
					for(int i = 0;i< book_info_list.size(); i++) {
						send.put("book_info_id", book_info_list.get(i).get("book_info_id"));
						send.put("reader", null);
						send.put("borrow_date", null);
						send.put("is_borrowed", "FALSE");
						db.insert("book", send);
					} 
					break;
				case "delete_book":
					db.deleteWhere("book_info", "book_info_id =" +getOne.get("book_info_id"));
					db.deleteWhere("book", "book_info_id =" +getOne.get("book_info_id"));
				case "teacher_courselist":
					getOne.remove("op");
					ArrayList<HashMap<String,String>> list=db.selectWhere("course_details","course_teacher=\'"+getOne.get("card_id")+"\'");
					ArrayList<HashMap<String,String>> result=new ArrayList<HashMap<String,String>>();
					HashMap<String,String> courseInfo=null;
					for(int i=0;i<list.size();i++) {
						courseInfo=new HashMap<String,String>();
						courseInfo.put("course_time",list.get(i).get("course_time"));
						courseInfo.put("course_id", list.get(i).get("course_id"));
						courseInfo.put("course_exam_time", list.get(i).get("course_exam_time"));
						courseInfo.put("course_exam_place", list.get(i).get("course_exam_place"));
						HashMap<String,String> tempHM=db.selectWhere("course_info", "course_info_id="+list.get(i).get("course_info_id")).get(0);
						courseInfo.put("course_name",tempHM.get("course_name"));
						courseInfo.put("course_credits",tempHM.get("course_credits"));
						result.add(courseInfo);
					}
					soos.writeObject(result);
					break;
				case"teacher_query_studentlist":
					getOne.remove("op");
					ArrayList<HashMap<String,String>> sList=db.selectWhere("course_records", "course_id="+getOne.get("course_id"));
					sendList=new ArrayList<HashMap<String,String>>();
					HashMap<String,String> hm=null;
					for(int i=0;i<sList.size();i++) {
						if(sList.get(i).get("select_status").equals("TRUE")){
							hm=new HashMap<String,String>();
							ArrayList<HashMap<String,String>> sInfo=db.selectWhere("users","card_id=\'"+sList.get(i).get("course_student")+"\'");
							hm.put("course_student",sList.get(i).get("course_student"));
							hm.put("course_score",sList.get(i).get("course_score"));
							sInfo=db.selectWhere("user_info","user_info_id="+sInfo.get(0).get("user_info_id"));
							hm.put("nname",sInfo.get(0).get("nname"));
							hm.put("student_id",sInfo.get(0).get("student_id"));
							sendList.add(hm);
						}
					}
					soos.writeObject(sendList);
					break;
				case "modify_score":
					getOne.remove("op");
					db.setWhere("course_records", "course_exam_status=TRUE,course_score="+getOne.get("course_score"), "course_id="+getOne.get("course_id")+" AND course_student=\'"+getOne.get("course_student")+"\'");
					break;
				case "teacher_modify_ExamInfo":
					getOne.remove("op");
					db.setWhere("course_details", "course_exam_time=\'"+getOne.get("course_exam_time")+"\' ,course_exam_place=\'"+getOne.get("course_exam_place")+"\'", "course_id="+getOne.get("course_id"));
					break;
				case "isCompleted":
					getOne.remove("op");
					String infoId=db.selectWhere("users","card_id=\'"+getOne.get("card_id")+"\'").get(0).get("user_info_id");
					if(infoId.equals("")||infoId.equals("0")) {
						getOne.put("result", "false");
					}else {
						getOne.put("result", "true");
					}
					soos.writeObject(getOne);
					break;
				case "QueryRecords":
					getOne.remove("op");
					sendList=db.selectWhere("store_purchase_records", "card_id=\'"+getOne.get("card_id")+"\'");
					soos.writeObject(sendList);
					break;
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
