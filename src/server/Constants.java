/**
 * 
 */
package server;

import java.util.HashMap;

/**
 *<p>Constants</p>
 * <p>数据库连接类<br>
 * 
 * </p>
 * @author 刘宗源
 */
class Constants {
	public final static HashMap<String,String[]> constructionOfTables=new HashMap<String,String[]>() {
		{
			put("users",new String[]{"card_id","password","identity","user_info_id"});
			put("book_info",new String[]{"book_name","author","publisher","quantity","book_info_id"});
			put("book",new String[]{"book_info_id","book_id","reader","borrow_date","is_borrowed","return_date"});
			put("course_records",new String[] {"course_student","course_info_id","select_status","course_id","course_record_id","course_exam_status","course_score"});
			put("course_info",new String[] {"course_name","course_credits","course_info_id"});
			put("course_details",new String[] {"course_id","course_max_number","course_selected_number","course_is_full","course_teacher","course_time","course_info_id","course_exam_time","course_exam_place"});
			put("card_info",new String[]{"card_balance","card_info_id","card_id","card_is_lost"} );
			put("card_records",new String[] {"card_cost","card_content","card_info_id","card_is_paid","card_time","card_record_id"});
			put("user_info",new String[] {"user_info_id","nname","gender","grade","major","student_id"});
			put("store_item_info",new String[] {"item_id","item_stock","item_details","item_picture_url","item_price","item_purchased_number","item_name","item_type"});
			put("store_purchase_records",new String[] {"purchase_records_id","card_id","purchase_time","purchase_cost","purchase_content"});
			}

	};
	
}
