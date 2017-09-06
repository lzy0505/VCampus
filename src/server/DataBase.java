package server;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
  

public class DataBase {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
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

	public ArrayList<HashMap<String,String>> selectWhere(String tableName,String condition){
		try {
			System.out.println("DataBase -Excute select- "+"Excuting:"+"SELECT * FROM "+ tableName+" WHERE "+condition );
			rs=stmt.executeQuery("SELECT * FROM "+ tableName+" WHERE "+condition);
			ArrayList<HashMap<String,String>> result=new ArrayList<HashMap<String,String>>();
			String[] keywords=Constants.constructionOfTables.get(tableName);
			while(rs.next()) {
				HashMap<String,String> inst=new HashMap<String,String>();
				for(int i=0;i<keywords.length;i++) {
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
	
	public void insert(String tableName,HashMap<String,String> content) {
		try {
			String[] keywords=Constants.constructionOfTables.get(tableName);
			String command="INSERT INTO " + tableName +" (";
			for(int i=0;i<keywords.length;i++) {
				command+=keywords[i]+",";
			}
			command=command.substring(0, command.length()-1);		
			command+=") VALUES(\'";
			for(int i=0;i<keywords.length;i++) {
				command+=content.get(keywords[i])+"\',\'";
			}
			command=command.substring(0, command.length()-2);
			command+=");";
			System.out.println("DataBase -Excute insert- "+"Excuting:"+command);
			stmt.execute(command);
			System.out.println("DataBase -Excute insert- "+"Successfull!");
			
		} catch (SQLException e) {
			System.out.println("DataBase -Excute insert- "+"Error:");
			e.printStackTrace();
		}
	}
	
	public void setWhere(String tableName,String setValue,String condition) {
		try {
			String command="UPDATE " + tableName +" SET "+setValue+" WHERE "+condition+";";
			System.out.println("DataBase -Excute update- "+"Excuting:"+command);
			stmt.execute(command);
			System.out.println("DataBase -Excute update- "+"Successfull!");
		} catch (SQLException e) {
			System.out.println("DataBase -Excute update- "+"Error:");
			e.printStackTrace();
		}
	}
	
	
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
