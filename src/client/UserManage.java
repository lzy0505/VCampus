/**
 * 
 */
package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	
		
		JLabel lblStudentNumber = new JLabel("学号",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentNumber);
		//TODO 这里是写学号 写成JTextField("xxxxxx")  没有填空 
		textStudentNo = new JTextField();
		lblStudentNumber.setLabelFor(textStudentNo);
		informationImportLeftContentPanel.add(textStudentNo);
		JLabel lblStudentname = new JLabel("姓名",JLabel.TRAILING);
		informationImportLeftContentPanel.add(lblStudentname);
		//TODO 这里是写姓名 写成JTextField("xxxxxx")  没有填空 
		textStudentName = new JTextField();
		textStudentName.setColumns((int)(HomeScreen.width/100));
		lblStudentname.setLabelFor(textStudentName);
		informationImportLeftContentPanel.add(textStudentName);
		JLabel lblEnrollmenttime = new JLabel("入学年份",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblEnrollmenttime);
		//TODO 这里是写入学时间 写成JTextField("xxxxxx")  没有填空 
		textEnrollTime = new JTextField();
		textEnrollTime.setText("2010");
		lblEnrollmenttime.setLabelFor(textEnrollTime);
		informationImportRightContentPanel.add(textEnrollTime);
		JLabel lblSpecialty = new JLabel("专业",JLabel.TRAILING);
		informationImportRightContentPanel.add(lblSpecialty);
		SpecialitySelection = new JComboBox();
		SpecialitySelection.setModel(new DefaultComboBoxModel(majors));
		//TODO 这里是专业   
		int index=0;
		if(true) {//有信息
			for(int i=0;i<majors.length;i++) {
				if(true) {//专业名称=majors[i]
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
		//TODO 这里是性别，没有不操作
		if(true) {//男
			rdbtnMale.setSelected(true);
		}
		else if(true) {//女
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
 		
		tabPanel.addTab("修改密码", null, pwdChangePanel, null);

		
	}
	
	
	
	class SubmitLister implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			//TODO 这里需要判断一下是不是合法内容，是否上传成功
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
