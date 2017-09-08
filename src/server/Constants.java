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
			put("users",new String[]{"card_id","password","identity"});
			put("book_info",new String[]{"book_name","author","publisher","quantity","book_info_id"});
			put("book",new String[]{"book_info_id","book_id","reader","borrow_date","is_borrowed"});
			put("course_records",new String[] {"course_student","course_info_id","select_status","course_id","course_record_id","course_exam_status","course_score"});
			put("course_info",new String[] {"course_name","course_credits","course_info_id"});
			put("course_details",new String[] {"course_id","course_max_number","course_selected_number","course_is_full","course_teacher","course_time","course_info_id","course_exam_arrangement"});
		}
	};
	
}
