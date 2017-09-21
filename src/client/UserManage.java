/**
 * 
 */
package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import table_component.SpringUtilities;

/**
 * @author lzy05
 *
 */
public class UserManage {

	HomeScreen hs;
	
	public JTabbedPane tabPanel;
	JPanel infoChangePanel;
	JPanel pwdChangePanel;
	
	private JTextField textStudentNo;
	private JTextField textStudentName;
	private JTextField textStudentCardID;
	JPasswordField confirmOldPwdText;
	JPasswordField newPwdText ;
	JPasswordField confirmNewPwdText;
	
	
	JRadioButton rdbtnFemale;
	JRadioButton rdbtnMale;
	JTextField textEnrollTime;
	JComboBox SpecialitySelection;
	private String[] majors = {"建筑学院", "机械工程学院", "能源与环境学院", "信息科学与工程学院", 
				"土木工程学院", "电子科学与工程学院", "数学学院", "自动化学院", "计算机科学与工程学院", "物理系", "生物科学与医学工程学院", 
			"材料科学与工程学院", "人文学院", "经济管理学院", "电气工程学院", "外国语学院", "体育系", "化学化工学院", "交通学院", 
			"仪器科学与工程学院", "艺术学院", "法学院", "基础医学院", "公共卫生学院", "临床医学院", "吴健雄学院", "软件学院"};
	
	
	public UserManage(HomeScreen hs) {
		this.hs=hs;
		tabPanel=new JTabbedPane();
		infoChangePanel=new JPanel();
		pwdChangePanel=new JPanel();

		infoChangePanel.setLayout(new BoxLayout(infoChangePanel,BoxLayout.Y_AXIS));
		tabPanel.addTab("个人信息修改", null, infoChangePanel, null);
		JPanel informationImportContentPanel =new JPanel();
		informationImportContentPanel.setLayout(new FlowLayout(50));
		JPanel informationImportLeftContentPanel =new JPanel(new SpringLayout());
		JPanel informationImportRightContentPanel =new JPanel(new SpringLayout());
		JLabel lblStudentCardID = new JLabel("一卡通号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentCardID);
		textStudentCardID = new JTextField(ClientInfo.getCi());
		textStudentCardID.setEnabled(false);
		lblStudentCardID.setLabelFor(textStudentCardID);
		informationImportLeftContentPanel.add(textStudentCardID);
		
		//TODO 这里写一个获取该学生信息的操作，用ClientInfo.getCi()查找
		HashMap card_for_search = new HashMap<String,String>();
		card_for_search.put("card_id", ClientInfo.getCi());
		card_for_search.put("op", "card_search_student");
		ArrayList<HashMap<String,String>> studentInfo =GUI.getList(card_for_search);
		if(studentInfo.size() !=0) {		
			textStudentNo = new JTextField(studentInfo.get(0).get("student_id"));		
			textStudentName = new JTextField(studentInfo.get(0).get("nname"));		
			textEnrollTime = new JTextField(studentInfo.get(0).get("grade"));
		}else {
			textStudentNo = new JTextField();			
			textStudentName = new JTextField();			
			textEnrollTime = new JTextField();
		}
		JLabel lblStudentNumber = new JLabel("学号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentNumber);
		
		lblStudentNumber.setLabelFor(textStudentNo);
		informationImportLeftContentPanel.add(textStudentNo);
		JLabel lblStudentname = new JLabel("姓名",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentname);
		
		textStudentName.setColumns((int)(HomeScreen.width/100));
		lblStudentname.setLabelFor(textStudentName);
		informationImportLeftContentPanel.add(textStudentName);
		JLabel lblEnrollmenttime = new JLabel("入学年份",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblEnrollmenttime);
		
		
		lblEnrollmenttime.setLabelFor(textEnrollTime);
		informationImportRightContentPanel.add(textEnrollTime);
		JLabel lblSpecialty = new JLabel("专业",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblSpecialty);
		SpecialitySelection = new JComboBox();
		SpecialitySelection.setModel(new DefaultComboBoxModel(majors));
	
		  
		int index=0;
		if(studentInfo.size() !=0) {//有信息
			for(int i=0;i<majors.length;i++) {
				if(studentInfo.get(0).get("major").equals(majors[i])) {					
					index=i;
				}
			}
		}	
		SpecialitySelection.setSelectedIndex(index);
		SpecialitySelection.setMaximumRowCount(10);
		lblSpecialty.setLabelFor(SpecialitySelection);
		informationImportRightContentPanel.add(SpecialitySelection);
		
		JLabel lblSex = new JLabel("性别",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblSex);
		rdbtnFemale = new JRadioButton("女");
		rdbtnMale = new JRadioButton("男");
		ButtonGroup bg=new ButtonGroup();
		bg.add(rdbtnFemale);
		bg.add(rdbtnMale);
		if(studentInfo.size() !=0&&studentInfo.get(0).get("gender").equals("male")) {//男
			rdbtnMale.setSelected(true);
		}
		else if(studentInfo.size() !=0&&studentInfo.get(0).get("gender").equals("female")) {//女
			rdbtnFemale.setSelected(true);
		}
		JPanel genderPanel=new JPanel(new FlowLayout());
		genderPanel.add(rdbtnFemale);
		genderPanel.add(rdbtnMale);
		lblSex.setLabelFor(genderPanel);
		informationImportRightContentPanel.add(genderPanel);

		SpringUtilities.makeCompactGrid(informationImportLeftContentPanel, 3, 2, (int)(HomeScreen.width/8), (int)(HomeScreen.height/8),  (int)(HomeScreen.width/80), (int)(HomeScreen.height/10));
		SpringUtilities.makeCompactGrid(informationImportRightContentPanel, 3, 2, (int)(HomeScreen.width/8), (int)(HomeScreen.height/8),  (int)(HomeScreen.width/80), (int)(HomeScreen.height/10));
		informationImportContentPanel.add(informationImportLeftContentPanel);
		informationImportContentPanel.add(informationImportRightContentPanel);
		
		JPanel informationImportActionBar = new JPanel();
		informationImportActionBar.setLayout(new FlowLayout());
		
		JButton btnButton = new JButton("提交");
		informationImportActionBar.add(btnButton);
		JButton btnCancel = new JButton("清空");
		informationImportActionBar.add(btnCancel);
		informationImportActionBar.setPreferredSize(new Dimension(200,100));
		
		
		infoChangePanel.add(informationImportContentPanel);
		infoChangePanel.add(informationImportActionBar);
		btnButton.addActionListener(new SubmitLister());
		btnCancel.addActionListener(new CancelLister());
 		
		pwdChangePanel.setLayout(new SpringLayout());
		
		JLabel confirmOldPwdLabel = new JLabel("确认当前密码",JLabel.RIGHT);
		pwdChangePanel.add(confirmOldPwdLabel);

		confirmOldPwdText = new JPasswordField();
		confirmOldPwdText.setColumns(20);
		confirmOldPwdLabel.setLabelFor(confirmOldPwdText);
		pwdChangePanel.add(confirmOldPwdText);
		JLabel newPwdLabel = new JLabel("新密码",JLabel.RIGHT);
		pwdChangePanel.add(newPwdLabel);
		newPwdText = new JPasswordField();
		newPwdLabel.setLabelFor(newPwdText);
		pwdChangePanel.add(newPwdText);
		confirmNewPwdText = new JPasswordField();
		JLabel confirmNewPwdLabel = new JLabel("确认新密码",JLabel.RIGHT);
		pwdChangePanel.add(confirmNewPwdLabel);
		confirmNewPwdLabel.setLabelFor(confirmNewPwdText);
		pwdChangePanel.add(confirmNewPwdText);
		JButton changePwdButton=new JButton("修改密码");
		
		
		pwdChangePanel.add(new JLabel());
		pwdChangePanel.add(changePwdButton);
		changePwdButton.addActionListener(new ChangeListener());
		
		SpringUtilities.makeCompactGrid(pwdChangePanel, 4, 2, 0, 50, 10, 50);
		JPanel middle = new JPanel();
		middle.setMinimumSize(new Dimension((int)HomeScreen.width*2/3,(int)HomeScreen.height*2/3));
		middle.add(pwdChangePanel);
		tabPanel.addTab("修改密码", null, middle, null);
		
		
	}
	
	
	class ChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//判断输入的数量是否合法
			String str = newPwdText.getText();			
			for(int i =str.length();--i>=0;){
				if(!(Character.isDigit(str.charAt(i))||Character.isAlphabetic(str.charAt(i)))){
					JOptionPane.showMessageDialog(null,"密码应为数字和字母！",
							"修改密码失败",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
				 
			if(!newPwdText.getText().equals(confirmNewPwdText.getText())) {
				JOptionPane.showMessageDialog(null, "新密码不一致","操作错误",JOptionPane.ERROR_MESSAGE);
				return;
			}else {
				HashMap<String, String> changePassword = new HashMap<>();
				changePassword.put("op", "modify_password");
				changePassword.put("card_id", ClientInfo.getCi());
				changePassword.put("newPassword", newPwdText.getText());
				changePassword = GUI.getOne(changePassword);
				if(changePassword.get("result").equals("success")) {
					JOptionPane.showMessageDialog(null,"修改成功！");
				}else {
					JOptionPane.showMessageDialog(null,changePassword.get("reason"),"修改失败！",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		
	}

	
	class SubmitLister implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			String str = textStudentNo.getText();		
			for(int i =str.length();--i>=0;){
				if(!(Character.isDigit(str.charAt(i)))){
					JOptionPane.showMessageDialog(null,"学号应为数字！",
							"信息录入失败",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			if(str.length()!=8) {
				JOptionPane.showMessageDialog(null,"请输入8位学号！",
						"信息录入失败",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String gradestr =  textEnrollTime.getText();		
			for(int i =gradestr.length();--i>=0;){
				if(!(Character.isDigit(gradestr.charAt(i)))){
					JOptionPane.showMessageDialog(null,"入学时间应为数字！",
							"信息录入失败",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			if(str.length()!=4) {
				JOptionPane.showMessageDialog(null,"请输入4位入学日期！",
						"信息录入失败",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(Integer.parseInt(gradestr)<2000||Integer.parseInt(gradestr)>2017) {
				JOptionPane.showMessageDialog(null,"不合法的入学日期！（应为2000-2017）",
						"信息录入失败",JOptionPane.ERROR_MESSAGE);
				return;
			}
			HashMap<String,String> hm = new HashMap<String,String>();
			hm.put("op","import_student");
			hm.put("nname", "\'"+textStudentName.getText()+"\'");
			hm.put("student_id","\'"+ textStudentNo.getText()+"\'");
			if(rdbtnMale.isSelected())hm.put("gender", "\'male\'");
			else if(rdbtnFemale.isSelected()) hm.put("gender", "\'female\'");
			else {
				JOptionPane.showMessageDialog(null, "信息不完整","操作错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			hm.put("grade", textEnrollTime.getText());
			hm.put("card_id", textStudentCardID.getText());
			hm.put("major", "\'"+majors[SpecialitySelection.getSelectedIndex()]+"\'");
			GUI.send(hm);
			JOptionPane.showMessageDialog(null, "提交成功","操作结果",JOptionPane.PLAIN_MESSAGE);
			hs.p_HomeScreen.setEnabledAt(3, true);
		}	
	}
	
	class CancelLister implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			textStudentNo.setText(null);
			textStudentName.setText(null);
			textEnrollTime.setText(null);
		}
		
	}

}
