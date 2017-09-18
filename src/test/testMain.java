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
		ClientInfo.setCi("liumou");
		new Store();
	}

}

