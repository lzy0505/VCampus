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
			put("user",new String[]{"username","password","identity"});
		}
	};
	
}
