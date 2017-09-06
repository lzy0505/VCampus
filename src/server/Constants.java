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
			put("book_info",new String[]{"book_name","author","publisher","quantity","book_info_id"});
			put("book",new String[]{"book_id","book_info_id","reader","borrow_date","is_borrowed"});
		}
	};
	
}
