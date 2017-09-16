package client;

public class ClientInfo {
	//!!!!card_id is used to identify a user 
	static String ci=null;
	//the elements of G1
	private static String dir_path = "clientpic/";
	public static String getCi() {
		return ci;
	}
	public static void setCi(String c) {
		ci = c;
	}
	public static String getDirPath(){
		return dir_path;
	}
}
