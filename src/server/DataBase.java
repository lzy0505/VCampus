package server;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 *<p>DataBase</p>
 * <p>数据库类<br>
 * 该类提供了管理数据库的方法，查找、修改、删除、插入功能
 * </p>
 * @author 刘宗源
 */

public class DataBase {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	/**
	 * <p>构造函数<br>
	 * 加载数据库驱动
	 * </p>
	 */
	public DataBase() {
		System.out.println("DataBase -Load DBDriver- "+"Loading...");
		try {
	        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	        System.out.println("DataBase -Load DBDriver- "+"Successfull!");
	    } catch (ClassNotFoundException e) {
	        System.out.println("DataBase -Load DBDriver- "+"Driver Error!");
	        e.printStackTrace();
	    }
	}
	/**
	 * <p>与数据库连接<br>
	 * </p>
	 */
	public void connectToDB() {
		String filePath=System.getProperty("user.dir");
		System.out.println("DataBase -Connect to DB- "+"File Path:"+filePath);
	    String url="jdbc:ucanaccess://"+filePath.replace('\\', '/')+"/Database.accdb;memory=true";
	    System.out.println("DataBase -Connect to DB- "+"Connecting...");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	stmt = conn.createStatement();
	    	System.out.println("DataBase -Connect to DB- "+"Successfull!");
	    }catch(SQLException e) {
	    	System.out.println("DataBase -Connect to DB- "+"Connection Error!");
	    	e.printStackTrace();
	    }
	}
	
//	public void initTable(String tableName) {
//		try {
//			System.out.println("DataBase -Initalize Table- "+"Drop table "+tableName);
//			stmt.execute("DROP TABLE "+tableName+";");
//		}catch(Exception e) {
//			System.out.println("DataBase -Initalize Table- "+"Error:");
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("DataBase -Initalize Table- "+"Creating table "+tableName);
//			stmt.execute(Constants.constructionCommands.get(tableName));
//			System.out.println("DataBase -Initalize Table- "+"Successfull!");
//		}catch(SQLException e) {
//			System.out.println("DataBase -Initalize Table- "+"Error:");
//			e.printStackTrace();
//		}
//	}
	/**
	 * <p>查找数据库方法<br>
	 * </p>
	 * @param tableName 表名
	 * @param condition 情况
	 */
	public ArrayList<HashMap<String,String>> selectWhere(String tableName,String condition){
		try {
			System.out.println("DataBase -Excute select- "+"Excuting:"+"SELECT * FROM "+ tableName+" WHERE "+condition+";" );
			rs=stmt.executeQuery("SELECT * FROM "+ tableName+" WHERE "+condition);
			conn.commit();
			ArrayList<HashMap<String,String>> result=new ArrayList<HashMap<String,String>>();
			String[] keywords=Constants.constructionOfTables.get(tableName);
			while(rs.next()) {
				HashMap<String,String> inst=new HashMap<String,String>();
				for(int i=0;i<keywords.length;i++) {
					System.out.println("DataBase -Excute select- "+keywords[i]+":"+ rs.getString(i+1));
					inst.put(keywords[i], rs.getString(i+1));
					//System.out.println(rs.getString(i+1));
				}
				result.add(inst);
			}
			System.out.println("DataBase -Excute select- "+"Successfull!");
			return result;
		} catch (SQLException e) {
			System.out.println("DataBase -Excute select- "+"Error:");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * <p>插入<br>
	 * </p>
	 * @param tableName 表名
	 * @param condition 条件
	 */
	public void insert(String tableName,HashMap<String,String> content) {
		try {
			Iterator<String> it =content.keySet().iterator();
			String command="INSERT INTO " + tableName +" (";
			while(it.hasNext()) {
				command+=it.next()+",";
			}
			command=command.substring(0, command.length()-1);		
			command+=") VALUES(";
			it=content.keySet().iterator();
			while(it.hasNext()) {
				command+=content.get(it.next())+",";
			}
			command=command.substring(0, command.length()-1);
			command+=");";
			System.out.println("DataBase -Excute insert- "+"Excuting:"+command);
			stmt.execute(command);
			conn.commit();
			System.out.println("DataBase -Excute insert- "+"Successfull!");
			
		} catch (SQLException e) {
			System.out.println("DataBase -Excute insert- "+"Error:");
			e.printStackTrace();
		}
	}
	/**
	 * <p>选取<br>
	 * </p>
	 * @param tableName 表名
	 * @param setValue 设定值
	 * @param condition 条件
	 * @return 返回一个HashMap的ArrayList
	 */
	public void setWhere(String tableName,String setValue,String condition) {
		try {
			String command="UPDATE " + tableName +" SET "+setValue+" WHERE "+condition+";";
			System.out.println("DataBase -Excute update- "+"Excuting:"+command);
			stmt.executeUpdate(command);
			conn.commit(); 
			System.out.println("DataBase -Excute update- "+"Successfull!");
		} catch (SQLException e) {
			System.out.println("DataBase -Excute update- "+"Error:");
			e.printStackTrace();
		}
	}
	/**
	 * <p>删除<br>
	 * </p>
	 * @param tableName 表名
	 * @param condition 条件
	 * @return 无返回值
	 */
	public void deleteWhere(String tableName, String condition) {
		try {
			String command="DELETE FROM "+tableName+" WHERE "+condition+";";
			System.out.println("DataBase -Excute delete- "+"Excuting:"+command);
			stmt.executeUpdate(command);
			conn.commit();
			System.out.println("DataBase -Excute delete- "+"Successfull!");
		}catch(SQLException e){
			System.out.println("DataBase -Excute delete "+"Error:");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <p>解构函数<br>
	 * </p>
	 */
	protected void finalize(){
		System.out.println("DataBase -Disconnect to DB- "+"Disconnecting...");
		try {
			stmt.close();
			conn.close();
			System.out.println("DataBase -Disconnect DB- "+"Successfull!");
		} catch (SQLException e) {
			System.out.println("DataBase -Disconnect DB- "+"Error!");
			e.printStackTrace();
		} 
	}

}
