package client;

/**
*<p>ClientInfo</p >
* <p>客户端信息类，该类保存用户的一卡通号以及客户端文件存储路径<br>
* </p>
* @author 李子厚
*/
public class ClientInfo {
	//!!!!card_id is used to identify a user 
	static String ci=null;
	//the elements of G1
	private static String dir_path = "clientpic/";
	/**
	* <p>获取card_id的方法<br>
	* </p>
	*/
	public static String getCi() {
		return ci;
	}
	/**
	* <p>设置card_id的方法<br>
	* </p>
	*/
	public static void setCi(String c) {
		ci = c;
	}
	/**
	* <p>获取文件路径的方法<br>
	* </p>
	*/
	public static String getDirPath(){
		return dir_path;
	}
}