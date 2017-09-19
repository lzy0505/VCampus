/**
 * 
 */
package test;
import client.*;

import java.awt.Font;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

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
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;  
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
	                "黑体", Font.PLAIN, 17)));
	        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font(  
	                "黑体", Font.PLAIN, 17))); 
	       
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        UIManager.put("RootPane.setupButtonVisible", false); 
		ClientInfo.setCi("liumou");
		new HSAdmin().init("storeadmin");
		JOptionPane.showMessageDialog(null,"MESSAGE","ERROR",JOptionPane.WARNING_MESSAGE);  
		
	}

}

