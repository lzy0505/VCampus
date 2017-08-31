/**
 * 
 */
package test;

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
		DataBase db = new DataBase();
		db.connectToDB();
		db.initTable("user");
		HashMap<String,String> i = new HashMap<String,String>();
		i.put("username", "zihousb");
		i.put("password", "12345");
		i.put("identity","1");
		db.insert("user", i);
	}

}
