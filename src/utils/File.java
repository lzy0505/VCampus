/**
 * 
 */
package utils;

import java.io.*;
/**
 * @author storm
 *
 */
public class File implements Serializable{
	private int fid;
	private String fname;
	private byte[] fcontent;
	
	public File() {
		
	}
	
	public File(String fname,byte[] fcontent) {
		super();
		this.fname = fname;
		this.fcontent = fcontent;
	}
	
	
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public byte[] getFcontent() {
		return fcontent;
	}
	public void setFocntent(byte[] fcontent) {
		this.fcontent = fcontent;
	}
	
}
