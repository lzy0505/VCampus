/**
 * 
 */
package utils;

import java.io.*;
/**
 * @author storm
 *
 */
public class User implements Serializable {
	private int type;//这里是用户的类型，1为学生，2为教师；
	private String username;
	private String password;
	
	public User() {
		
	}
	public User(String username, String password){
		super();
		this.password = password;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
