package main.java.com.netdevsdk.demo;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.jna.Memory;

import main.java.com.netdevsdk.lib.NetDEVSDKLib;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_FILE_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_GENDER_TYPE_E;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_IDENTIFICATION_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_ID_TYPE_E;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_IMAGE_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_LIB_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_PERSON_INFO_LIST_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_PERSON_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_PERSON_RESULT_LIST_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_REGION_INFO_S;

public class PersonOperateWindow extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField PersonOperateNameTextField;
	private JTextField AddPersonOperateDatetextField;
	private JTextField PersonOperateNationTextField;
	private JTextField PersonOperateProvinceTextField;
	private JTextField PersonOperateCertificateNumbertextField;
	private JTextField PersonOperateCityTextField;

	public String strAddPersonName = "";
	public String strAddPersonBirthDate = "";
	public int dwAddPersonGender = 0;
	public String strAddPersonNation = "";
	public int dwAddPersonIdentifyType = 0;
	public String strAddPersonProvince = "";
	public String strAddPersonIdentifyNumber = "";
	public String strAddPersonCity = "";
	
	NetDEVSDKLib netdevsdk = NetDEVSDKLib.NETDEVSDK_INSTANCE;
	
	public static class PERSON_OPERATE_WINDOW_EFFECT{
	    public static final int PERSON_OPERATE_WINDOW_ADDPERSON             = 1;        /* 添加人脸界面 */
	    public static final int PERSON_OPERATE_WINDOW_MODIFYPERSON          = 2;        /* 修改人脸界面 */
	}
	
	@SuppressWarnings("deprecation")
	public PersonOperateWindow(int dwOperateMode) {
        this.setSize(800,600);
        
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize =toolkit.getScreenSize();
        int x=(screenSize.width-this.getWidth())/2;
        int y=(screenSize.height-this.getHeight())/2;
        this.setLocation(x,y);
        
        getContentPane().setLayout(null);
        JLabel lblPersonOperateName = new JLabel("* Name");
        lblPersonOperateName.setBounds(248, 71, 57, 15);
        getContentPane().add(lblPersonOperateName);
        
        PersonOperateNameTextField = new JTextField();
        PersonOperateNameTextField.setBounds(303, 71, 188, 21);
        getContentPane().add(PersonOperateNameTextField);
        PersonOperateNameTextField.setColumns(10);
        
        JLabel lblPersonOperateBirthDate = new JLabel("Date of birth");
        lblPersonOperateBirthDate.setBounds(501, 71, 90, 15);
        getContentPane().add(lblPersonOperateBirthDate);
        
        AddPersonOperateDatetextField = new JTextField();
        AddPersonOperateDatetextField.setBounds(589, 68, 149, 21);
        getContentPane().add(AddPersonOperateDatetextField);
        AddPersonOperateDatetextField.setText("20000101");
        AddPersonOperateDatetextField.setColumns(10);
        
        JLabel lbPersonOperateGender = new JLabel("Gender");
        lbPersonOperateGender.setBounds(248, 110, 54, 15);
        getContentPane().add(lbPersonOperateGender);
        
        JRadioButton rdbtnPersonOperateMan = new JRadioButton("man");
        rdbtnPersonOperateMan.setBounds(303, 106, 57, 23);
        getContentPane().add(rdbtnPersonOperateMan);
        
        JRadioButton rdbtnPersonOperateFemale = new JRadioButton("female");
        rdbtnPersonOperateFemale.setBounds(359, 106, 66, 23);
        getContentPane().add(rdbtnPersonOperateFemale);
        
        JRadioButton rdbtnPersonOperateUnknow = new JRadioButton("unknow");
        rdbtnPersonOperateUnknow.setBounds(425, 106, 73, 23);
        getContentPane().add(rdbtnPersonOperateUnknow);
        this.setVisible(true);
        
        ButtonGroup GendierGroup = new ButtonGroup();
        GendierGroup.add(rdbtnPersonOperateMan);
        GendierGroup.add(rdbtnPersonOperateFemale);
        GendierGroup.add(rdbtnPersonOperateUnknow);
        
        JLabel lbPersonOperateNation = new JLabel("Nation");
        lbPersonOperateNation.setBounds(501, 110, 66, 15);
        getContentPane().add(lbPersonOperateNation);
        
        PersonOperateNationTextField = new JTextField();
        PersonOperateNationTextField.setBounds(589, 107, 149, 21);
        getContentPane().add(PersonOperateNationTextField);
        PersonOperateNationTextField.setColumns(10);
        
        JLabel lblPersonOperateDocumentType = new JLabel("* Document type");
        lblPersonOperateDocumentType.setBounds(248, 147, 100, 15);
        getContentPane().add(lblPersonOperateDocumentType);
        
        JComboBox<Object> PersonOperateDocumentTypeComboBox = new JComboBox<Object>();
        PersonOperateDocumentTypeComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"Identity card", "Passport", "Driving license", "Other"}));
        PersonOperateDocumentTypeComboBox.setToolTipText("");
        PersonOperateDocumentTypeComboBox.setBounds(358, 144, 133, 21);
        getContentPane().add(PersonOperateDocumentTypeComboBox);
        
        JLabel lblPersonOperateProvince = new JLabel("Province");
        lblPersonOperateProvince.setBounds(501, 147, 66, 15);
        getContentPane().add(lblPersonOperateProvince);
        
        PersonOperateProvinceTextField = new JTextField();
        PersonOperateProvinceTextField.setBounds(589, 144, 149, 21);
        getContentPane().add(PersonOperateProvinceTextField);
        PersonOperateProvinceTextField.setColumns(10);
        
        JLabel lblPersonOperateCertificateNumber = new JLabel("* Certificate number");
        lblPersonOperateCertificateNumber.setBounds(242, 187, 127, 15);
        getContentPane().add(lblPersonOperateCertificateNumber);
        
        PersonOperateCertificateNumbertextField = new JTextField();
        PersonOperateCertificateNumbertextField.setBounds(368, 184, 123, 21);
        getContentPane().add(PersonOperateCertificateNumbertextField);
        PersonOperateCertificateNumbertextField.setColumns(10);
        
        JLabel lblPersonOperateCity = new JLabel("City");
        lblPersonOperateCity.setBounds(501, 187, 54, 15);
        getContentPane().add(lblPersonOperateCity);
        
        PersonOperateCityTextField = new JTextField();
        PersonOperateCityTextField.setBounds(589, 184, 149, 21);
        getContentPane().add(PersonOperateCityTextField);
        PersonOperateCityTextField.setColumns(10);
        
        
        JButton btnAddPersonComplete = new JButton("Complete");
        btnAddPersonComplete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		strAddPersonName = PersonOperateNameTextField.getText();
        		strAddPersonBirthDate = AddPersonOperateDatetextField.getText();
        		
    			if(rdbtnPersonOperateMan.isSelected() == true)
    			{
    				dwAddPersonGender = NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_MAN;
    			}
    			else if(rdbtnPersonOperateFemale.isSelected() == true)
    			{
    				dwAddPersonGender = NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_WOMAN;
    			}
    			else
    			{
    				dwAddPersonGender = NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_WOMAN;
    			}
    			
        		
        		strAddPersonNation = PersonOperateNationTextField.getText();
        		dwAddPersonIdentifyType = 23;
        		switch (PersonOperateDocumentTypeComboBox.getSelectedItem().toString()) {
				case "Identity card":
					dwAddPersonIdentifyType = NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_ID;
					break;
				case "Passport":
					dwAddPersonIdentifyType = NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_PASSPORT;
					break;
				case "Driving license":
					dwAddPersonIdentifyType = NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_DRIVING_LICENSE;
					break;
				case "Other":
					dwAddPersonIdentifyType = NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_OTHER;
					break;

				default:
					break;
				}
        		
        		strAddPersonProvince = PersonOperateProvinceTextField.getText();
        		strAddPersonIdentifyNumber = PersonOperateCertificateNumbertextField.getText();
        		strAddPersonCity = PersonOperateCityTextField.getText();
        		
        		NETDEV_LIB_INFO_S stPersonLibInfo = NetDemo.mapPersonLib.get(NetDemo.PersonLibcomboBox.getItemAt(NetDemo.PersonLibcomboBox.getSelectedIndex()));
        		NETDEV_PERSON_INFO_LIST_S stPersonInfoList = new NETDEV_PERSON_INFO_LIST_S();
        		stPersonInfoList.udwNum = 1;
        		NETDEV_PERSON_INFO_S[] stPersonInfo = new NETDEV_PERSON_INFO_S[4];    //支持一次添加多个人员，demo中只添加一个
        		for(int i = 0; i< 4; i++)
        		{
        			stPersonInfo[i] = new NETDEV_PERSON_INFO_S();
        			stPersonInfo[i].write();
        		}
        		
        		Common.stringToByteArray(strAddPersonName, stPersonInfo[0].szPersonName);
        		stPersonInfo[0].udwGender = dwAddPersonGender;
        		Common.stringToByteArray(strAddPersonBirthDate, stPersonInfo[0].szBirthday);
        		
        		stPersonInfo[0].stRegionInfo = new NETDEV_REGION_INFO_S();
        		Common.stringToByteArray(strAddPersonNation, stPersonInfo[0].stRegionInfo.szNation);
        		Common.stringToByteArray(strAddPersonProvince, stPersonInfo[0].stRegionInfo.szProvince);
        		Common.stringToByteArray(strAddPersonCity, stPersonInfo[0].stRegionInfo.szCity);
        		
				stPersonInfo[0].udwTimeTemplateNum = 0;
				stPersonInfo[0].pstTimeTemplateList = null;
				
				stPersonInfo[0].udwIdentificationNum = 1;
				stPersonInfo[0].stIdentificationInfo[0] = new NETDEV_IDENTIFICATION_INFO_S();
				stPersonInfo[0].stIdentificationInfo[0].udwType = dwAddPersonIdentifyType;
				Common.stringToByteArray(strAddPersonIdentifyNumber, stPersonInfo[0].stIdentificationInfo[0].szNumber);
				
				stPersonInfo[0].udwImageNum = 1;
				stPersonInfo[0].stImageInfo[0] = new NETDEV_IMAGE_INFO_S();
				stPersonInfo[0].stImageInfo[0].stFileInfo = new NETDEV_FILE_INFO_S();
				
				if(dwOperateMode == PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_MODIFYPERSON)
				{
					String strPersonName = (String) NetDemo.PersonTable.getValueAt(NetDemo.PersonTable.getSelectedRow(), 0);
					NETDEV_PERSON_INFO_S stPersonInfoOrininal = NetDemo.mapPersonInfo.get(strPersonName);
					stPersonInfo[0].udwPersonID = stPersonInfoOrininal.udwPersonID;
					stPersonInfo[0].stImageInfo[0].stFileInfo.udwSize = stPersonInfoOrininal.stImageInfo[0].stFileInfo.udwSize;
					stPersonInfo[0].stImageInfo[0].stFileInfo.pcData = stPersonInfoOrininal.stImageInfo[0].stFileInfo.pcData;	
					stPersonInfo[0].stImageInfo[0].stFileInfo.dwFileType = stPersonInfoOrininal.stImageInfo[0].stFileInfo.dwFileType;
					stPersonInfo[0].stImageInfo[0].stFileInfo.szName = stPersonInfoOrininal.stImageInfo[0].stFileInfo.szName;
				}
				else if(dwOperateMode == PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_ADDPERSON)
				{
					stPersonInfo[0].stImageInfo[0].stFileInfo.udwSize = (int) Common.GetFileSize(NetDemo.strPersonChosePicurePath);
					stPersonInfo[0].stImageInfo[0].stFileInfo.pcData = Common.readPictureFile(NetDemo.strPersonChosePicurePath);
					stPersonInfo[0].stImageInfo[0].stFileInfo.dwFileType = 1;
					Common.stringToByteArray(new File(NetDemo.strPersonChosePicurePath.trim()).getName(), stPersonInfo[0].stImageInfo[0].stFileInfo.szName);
				}

				stPersonInfo[0].udwBelongLibNum = 0;
				stPersonInfo[0].pudwBelongLibList = null;
				
				
				stPersonInfo[0].write();
				
				stPersonInfoList.pstPersonInfo = new Memory(stPersonInfo[0].size() * stPersonInfo.length);
				Common.SetStructArrToPointerData(stPersonInfo, stPersonInfoList.pstPersonInfo);

				NETDEV_PERSON_RESULT_LIST_S stPersonResultList = new NETDEV_PERSON_RESULT_LIST_S();
        		
        		
				if(dwOperateMode == PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_ADDPERSON)
				{
	        		boolean bRet = netdevsdk.NETDEV_AddPersonInfo(NetDemo.lpUserID, stPersonLibInfo.udwID, stPersonInfoList, stPersonResultList);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_FindNextPersonLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
					}
				}
				else if(dwOperateMode == PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_MODIFYPERSON)
				{
					
					boolean bRet = netdevsdk.NETDEV_ModifyPersonInfo(NetDemo.lpUserID, stPersonLibInfo.udwID, stPersonInfoList, stPersonResultList);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_ModifyPersonInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
					}
				}
        		
        		dispose();
   
        	}
        });
        btnAddPersonComplete.setBounds(501, 505, 93, 23);
        getContentPane().add(btnAddPersonComplete);
        
        JButton btnAddPersonCancel = new JButton("Cancel");
        btnAddPersonCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btnAddPersonCancel.setBounds(624, 505, 93, 23);
        getContentPane().add(btnAddPersonCancel);
     
        
        JButton btnPersonOperatePicture = new JButton("");
        btnPersonOperatePicture.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				JFileChooser fcPictureChooser = new JFileChooser();
			    //设置一个文件筛选器
				FileNameExtensionFilter filter=new FileNameExtensionFilter("picture file(jpg)", "jpg"); 
				fcPictureChooser.setFileFilter(filter);  
			    //设置不允许多选
				fcPictureChooser.setMultiSelectionEnabled(false); 
			    int result=fcPictureChooser.showSaveDialog(null); 
			    if (result==JFileChooser.APPROVE_OPTION)
			    {
			    	NetDemo.strPersonChosePicurePath = fcPictureChooser.getSelectedFile().getAbsolutePath();
			    }
        	}
        });
        btnPersonOperatePicture.setBounds(25, 56, 194, 258);
        getContentPane().add(btnPersonOperatePicture);
        
        if(dwOperateMode == PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_ADDPERSON)
        {
            this.setTitle("Add Person");
            javax.swing.ImageIcon iconPersonImage = new javax.swing.ImageIcon(NetDemo.strPersonChosePicurePath);
            iconPersonImage.setImage(iconPersonImage.getImage().getScaledInstance(btnPersonOperatePicture.getWidth(), btnPersonOperatePicture.getHeight(),  Image.SCALE_DEFAULT));
            btnPersonOperatePicture.setIcon(iconPersonImage);
        }
        
		if(dwOperateMode == PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_MODIFYPERSON)
		{
	        this.setTitle("Modify Person");
			String strPersonName = (String) NetDemo.PersonTable.getValueAt(NetDemo.PersonTable.getSelectedRow(), 0);
			NETDEV_PERSON_INFO_S stPersonInfoOrininal = NetDemo.mapPersonInfo.get(strPersonName);
			PersonOperateNameTextField.setText(Common.byteArrayToString(stPersonInfoOrininal.szPersonName));
			AddPersonOperateDatetextField.setText(Common.byteArrayToString(stPersonInfoOrininal.szBirthday));
			if(stPersonInfoOrininal.udwGender == NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_MAN)
			{
				rdbtnPersonOperateMan.setSelected(true);
			}
			else if(stPersonInfoOrininal.udwGender == NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_WOMAN)
			{
				rdbtnPersonOperateFemale.setSelected(true);
			}
			else
			{
				rdbtnPersonOperateUnknow.setSelected(true);
			}
			PersonOperateNationTextField.setText(Common.byteArrayToString(stPersonInfoOrininal.stRegionInfo.szNation));
			PersonOperateProvinceTextField.setText(Common.byteArrayToString(stPersonInfoOrininal.stRegionInfo.szProvince));
			PersonOperateCityTextField.setText(Common.byteArrayToString(stPersonInfoOrininal.stRegionInfo.szCity));
			if(stPersonInfoOrininal.stIdentificationInfo[0].udwType == NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_ID)
			{
				PersonOperateDocumentTypeComboBox.setSelectedIndex(0);
			}
			else if(stPersonInfoOrininal.stIdentificationInfo[0].udwType == NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_PASSPORT)
			{
				PersonOperateDocumentTypeComboBox.setSelectedIndex(1);
			}
			else if(stPersonInfoOrininal.stIdentificationInfo[0].udwType == NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_DRIVING_LICENSE)
			{
				PersonOperateDocumentTypeComboBox.setSelectedIndex(2);
			}
			else
			{
				PersonOperateDocumentTypeComboBox.setSelectedIndex(3);
			}
			PersonOperateDocumentTypeComboBox.enable(false);
			
			PersonOperateCertificateNumbertextField.setText(Common.byteArrayToString(stPersonInfoOrininal.stIdentificationInfo[0].szNumber));
			PersonOperateCertificateNumbertextField.setEditable(false);
        	
            javax.swing.ImageIcon iconPersonImage = new javax.swing.ImageIcon(stPersonInfoOrininal.stImageInfo[0].stFileInfo.pcData.getByteArray(0, stPersonInfoOrininal.stImageInfo[0].stFileInfo.udwSize));
            iconPersonImage.setImage(iconPersonImage.getImage().getScaledInstance(btnPersonOperatePicture.getWidth(), btnPersonOperatePicture.getHeight(),  Image.SCALE_DEFAULT));
            btnPersonOperatePicture.setIcon(iconPersonImage);

		}
	}
	
}
