/**
 * 
 */
package server;

import java.util.HashMap;

/**
 * @author lzy05
 *
 */
class Constants {
	public final static HashMap<String,String[]> constructionOfTables=new HashMap<String,String[]>() {
		{
			put("users",new String[]{"username","password","identity"});
		}
	};
	public final static HashMap<String,String> constructionCommands= new HashMap<String,String >(){
		{
			put("users","CREATE TABLE users (username VARCHAR(10) NOT NULL," + 
					"password VARCHAR(20) NOT NULL," + 
					"identity VARCHAR(10) NOT NULL," + 
					");");
		}
	};
	
}
