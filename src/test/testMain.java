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
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("card_id", "\'213151245\'");
		hm.put("identity", "\'student\'");
		hm.put("password", "\'123456\'");
//		hm.put("user_info_id", " ");
		DataBase db = new DataBase();
		db.connectToDB();
		db.insert("users", hm);
	}

}

