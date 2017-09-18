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
	private ObjectInputStream sois = null;//对象输入流
	private ObjectOutputStream soos = null;
	private FileInputStream fis;
    private DataOutputStream dos;
	private DataBase db=null;
	private String dir_path = "serverpic/";//指定文件存放处
    int length = 0;  
    byte[] sendBytes = null;  
	HashMap<String,String> send =new HashMap<String,String>();//send is used to send message 
	ArrayList<HashMap<String,String>> sendList=null;//a HashMap array
	ArrayList<HashMap<String,String>> getList =new ArrayList<HashMap<String,String>>();
	HashMap<String,String> getOne =new HashMap<String,String>();//get is used to get message		
	//初始化
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	//run函数
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
						getOne.put("user_info_id", "null");
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
						ArrayList<HashMap<String, String>> hmList = db.selectWhere("card_info", "card_id =" + getOne.get("card_id"));
						//为新用户添加水电费用，暂时先设为0；
						String[] fee=new String[]{"\'Tuition\'","\'WandE\'","\'Afee\'"};
						String[] sem=new String[]{"\'第一学年上学期\'","\'第一学年下学期\'","\'第二学年上学期\'","\'第二学年下学期\'","\'第三学年上学期\'","\'第三学年下学期\'","\'第四学年上学期\'","\'第四学年下学期\'"};				
						for(int j=0;j<3;j++)
						{               
							for(int i=0;i<8;i++)
							{
				                    HashMap<String, String> cost = new HashMap<>();
									cost.put("card_info_id", hmList.get(0).get("card_info_id"));
									cost.put("card_cost", "0");
									cost.put("card_content", fee[j]);
									cost.put("card_is_paid", "FALSE");
									cost.put("card_time", sem[i]);
									db.insert("card_records", cost);
									}
						}
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
					send.put("result", "成功");
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
					//管理员加书	
				case "add_book":
					getOne.remove("op");					
					db.insert("book_info", getOne);
					ArrayList<HashMap<String,String>> book_info_list = db.selectWhere("book_info", "book_name ="+getOne.get("book_name"));
					for(int i = 0;i<Integer.parseInt( book_info_list.get(0).get("quantity")); i++) {
						send.put("book_info_id", book_info_list.get(0).get("book_info_id"));
						send.put("reader", null);
						send.put("borrow_date", null);
						send.put("is_borrowed", "FALSE");
						db.insert("book", send);
					} 
					break;
				//管理员删书,全部删除
				case "delete_all_book":
					ArrayList<HashMap<String,String>> book_info_delete = db.selectWhere("book", "book_info_id ="+getOne.get("book_info_id"));
					for(int i=0;i<book_info_delete.size();i++){
						if(book_info_delete.get(i).get("is_borrowed").equals("FALSE")){
							db.deleteWhere("book", "book_id =" +book_info_delete.get(i).get("book_id"));
							db.setWhere("book_info", "quantity =quantity -1", "book_info_id="+getOne.get("book_info_id"));
						}
					}
					ArrayList<HashMap<String,String>> book_delete = db.selectWhere("book", "book_info_id ="+getOne.get("book_info_id"));
					//如果没有外借的书
					if(book_delete.size()==0){
						db.deleteWhere("book_info",  "book_info_id ="+getOne.get("book_info_id"));		
						send.put("result", "success");
						soos.writeObject(send);
						break;
					}else{
						send.put("result", "unsuccess");
						soos.writeObject(send);
						break;
					}	
					
				//管理员删单个书
				case "delete_single_book":
					ArrayList<HashMap<String,String>> book_info_delete_single = db.selectWhere("book_info", "book_info_id ="+getOne.get("book_info_id"));
					System.out.println("图书馆里书的数量： "+book_info_delete_single.get(0).get("quantity"));
					ArrayList<HashMap<String,String>> book_id = db.selectWhere("book", "book_info_id ="+getOne.get("book_info_id"));
					boolean book_is_borrowed =false;
					for(int i=0;i<book_id.size();i++){
						if(book_id.get(i).get("is_borrowed").equals("TRUE"))
							book_is_borrowed=true;
					}//如果图书馆剩超过两本，就直接删
					System.out.println("是否外借 ："+book_is_borrowed);
					if(Integer.parseInt(book_info_delete_single.get(0).get("quantity"))>=2) {
						for(int i=0;i<book_id.size();i++){
							if(book_id.get(i).get("is_borrowed").equals("FALSE")){
								db.deleteWhere("book", "book_id="+book_id.get(i).get("book_id"));
								db.setWhere("book_info", "quantity =quantity -1", "book_info_id="+getOne.get("book_info_id"));
								break;
							}
						}
						send.put("result", "success");
						soos.writeObject(send);
						break;
						//如果剩一本了，但外面还有，就只改数量
					}else if((Integer.parseInt(book_info_delete_single.get(0).get("quantity"))==1)&&book_is_borrowed){
						for(int i=0;i<book_id.size();i++){
							if(book_id.get(i).get("is_borrowed").equals("FALSE")){
								db.deleteWhere("book", "book_id="+book_id.get(i).get("book_id"));
								db.setWhere("book_info", "quantity =0", "book_info_id="+getOne.get("book_info_id"));
								break;
							}
						}
						send.put("result", "success");
						soos.writeObject(send);
						break;
						//如果还剩一本，且外面没有了，就都删除
					}else if((Integer.parseInt(book_info_delete_single.get(0).get("quantity"))==1)&&!book_is_borrowed){
						db.deleteWhere("book_info", "book_info_id =" +getOne.get("book_info_id"));
						db.deleteWhere("book", "book_info_id =" +getOne.get("book_info_id"));
						send.put("result", "success");
						soos.writeObject(send);
						break;
					}
					//如果馆里没有了，就不能删除
					else{
						send.put("result", "unsuccess");
						soos.writeObject(send);
						break;
					}
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
					//费用修改
				case "fee_modify":
					 int count=Integer.parseInt(getOne.get("count"));
					 //ArrayList<HashMap<String,String>> list_card_info_id = db.selectWhere("card_info", "card_id=\'"+getOne.get("card_id")+"\'");
					 //String card_info_id=list_card_info_id.get(0).get("card_info_id");
					 String card_record_id;
					 String modifiedFee;
					 for(int i=0;i<count;i++)
					 {
					 card_record_id=getOne.get("card_record_id"+i);
					 modifiedFee=getOne.get("fee"+i);
	                 db.setWhere("card_records","card_cost="+modifiedFee,"card_record_id="+card_record_id);			 
					 }
					 send.put("result","success modified");
				     soos.writeObject(send);
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
				//新商品上传
				case "up_load":
					//接收到要传的指令后先检查是否已经存在该商品
					System.out.println("要上传的商品名字： "+getOne.get("item_name"));
					ArrayList<HashMap<String,String>> chackList = db.selectWhere("store_item_info", "item_name =\'" + getOne.get("item_name")+"\'");
					//如果已经存在
					if(chackList.size()!=0) {
						send.put("result", "false");
						send.put("reason", "该商品已经存在了！");
						soos.writeObject(send);
						break;
					}
					//先将一部分信息插入数据库
					
					getOne.remove("op");
					getOne.put("item_details", null);
					getOne.put("item_picture_url", null);
					getOne.put("item_purchased_number", "0");
					getOne.put("item_name","\'" +getOne.get("item_name")+"\'");
					db.insert("store_item_info", getOne);
					//先把名字存起来，怕后面接收到的getOne出事情
					String name = getOne.get("item_name");
					send.put("result", "success");
					soos.writeObject(send);
					//接收图片,从这里开始就接受不到杂流（HashMap）,所以在此处重新建立链接
				//	file_sois =new ObjectInputStream(socket.getInputStream());
					File getFile = (File)sois.readObject();
					File file = new File(dir_path+getFile.getName());
					if(!file.exists()) {
						System.out.println("开始接收图片了");
						FileInputStream fis = new FileInputStream(getFile);//文件输入流
						FileOutputStream fos = new FileOutputStream(new File(dir_path+getFile.getName()));//文件输出流
						byte[] buffer =new byte[1];
						while(fis.read(buffer)!=-1){
							fos.write(buffer);
						}
						fos.flush();
						if(fis!=null)fis.close();
						if(fos!=null)fos.close();
					}
					System.out.println("如果没看到开始就GG，如果有就完事儿了！");
					//最后一步，更新表中的url数据
					db.setWhere("store_item_info", "item_picture_url =\'"+dir_path+getFile.getName()+"\'", "item_name="+name);
					break;
				
				//商品删除确认
				case "DeleteGoods":
					System.out.println("文件路径： "+getOne.get("item_picture_url"));
					//删除图片和对应的商品
					File delete_file = new File(getOne.get("item_picture_url"));
					if(delete_file.exists())delete_file.delete();			
					db.deleteWhere("store_item_info", "item_name=\'" + getOne.get("item_name")+"\'");
					break;
				//修改货物查询数量
				case "QueryGoodsAmount":				
				//修改货物查询价格
				case "QueryGoodsPrice":
				//商品删除查询，这三种情况都一样
				case "GoodsInfoQuery":
					sendList=db.selectWhere("store_item_info", "item_name LIKE \'%"+getOne.get("item_name")+"%\'");	
					soos.writeObject(sendList);
					break;
				//商品库存修改
				case "GoodsAmountRevise":
					db.setWhere("store_item_info", "item_stock ="+getOne.get("item_stock"), "item_name=\'" + getOne.get("item_name")+"\'");
					break;
				//商品价格修改
				case "GoodsPriceRevise":
					db.setWhere("store_item_info", "item_price ="+getOne.get("item_price"), "item_name=\'" + getOne.get("item_name")+"\'");
					break;
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
						record.put("card_id", "\'"+getOne.get("card_id")+"\'");
						record.put("purchase_content", "\'网络商店购物\'");
						db.insert("store_purchase_records", record);
						//将购买的物品相应减少数量，销量加
						int quantity_now = Integer.parseInt(store_item_info_list.get(0).get("item_stock")) - Integer.parseInt(getOne.get("quantity"));
						int buy_quantity_now = Integer.parseInt(store_item_info_list.get(0).get("item_purchased_number")) + Integer.parseInt(getOne.get("quantity"));
						db.setWhere("store_item_info", "item_stock ="+quantity_now, "item_name =\'"+getOne.get("item_name")+"\'");
						db.setWhere("store_item_info", "item_purchased_number", "item_name =\'"+getOne.get("item_name")+"\'");
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
				if(sois!=null)sois.close();
				if(socket!=null)socket.close();
				db.finalize();	
				
				}
				catch (EOFException e) {
					// TODO: handle exception
				}
			catch (IOException e) {
				// TODO 自动生成的 catch 块
//				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
	
	}
	public void name() {
		
	}
	

}
