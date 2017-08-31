package utils;
/**
 * 
 */

/**
 * @author C5627
 *
 */
public class Entity {
	private String id;
	private String pwd;
	private int type;
	
	public String getId(){
		return id;
	}
	public String getPwd(){
		return pwd;
	}
	public int getType(){
		return type;
	}
	public void setId(String Id){
		 this.id=Id;
	}
	public void setPwd(String Pwd){
		this.pwd = Pwd;
	}
	public void setType(int type){
		this.type=type;
	}
}
