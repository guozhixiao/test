package main.java.com.netdevsdk.demo;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.com.netdevsdk.lib.NetDEVSDKLib;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_BATCH_OPERATOR_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_BATCH_OPERATOR_LIST_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_LIB_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_PLATE_ATTR_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_VEHICLE_DETAIL_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_VEHICLE_INFO_LIST_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_VEHICLE_MEMBER_ATTR_S;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VehicleOperateWindow extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField VehicleOperatePlateNumberTextField;
	JComboBox<Object> comboBoxVehicleOperateLicensePlateType = new JComboBox<Object>();
	JComboBox<Object> comboBoxLicenseVehicleOperatePlateColor = new JComboBox<Object>();
	JComboBox<Object> comboBoxlblVehicleOperateCarBodyColor = new JComboBox<Object>();
	
	NetDEVSDKLib netdevsdk = NetDEVSDKLib.NETDEVSDK_INSTANCE;
	
	public static class VEHICLE_OPERATE_WINDOW_EFFECT{
	    public static final int VEHICLE_OPERATE_WINDOW_ADDVEHICLE             = 1;        /* 添加车牌界面 */
	    public static final int VEHICLE_OPERATE_WINDOW_MODIFVEHICLE          = 2;        /* 修改车牌界面 */
	}
	
	public VehicleOperateWindow(int dwOperateType) {
		this.setSize(689,600);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize =toolkit.getScreenSize();
        int x=(screenSize.width-this.getWidth())/2;
        int y=(screenSize.height-this.getHeight())/2;
        this.setLocation(x,y);
        
        JPanel VehicleOperatePanel = new JPanel();
        getContentPane().add(VehicleOperatePanel, BorderLayout.CENTER);
        VehicleOperatePanel.setLayout(null);
        
        JLabel lblVehicleOperatePlateNumber = new JLabel("* License plate");
        lblVehicleOperatePlateNumber.setBounds(47, 48, 96, 15);
        VehicleOperatePanel.add(lblVehicleOperatePlateNumber);
        
        VehicleOperatePlateNumberTextField = new JTextField();
        VehicleOperatePlateNumberTextField.setBounds(169, 45, 435, 21);
        VehicleOperatePanel.add(VehicleOperatePlateNumberTextField);
        VehicleOperatePlateNumberTextField.setColumns(10);
        
        JLabel lblVehicleOperateLicensePlateType = new JLabel("License plate type");
        lblVehicleOperateLicensePlateType.setBounds(47, 99, 118, 15);
        VehicleOperatePanel.add(lblVehicleOperateLicensePlateType);
        
       
        comboBoxVehicleOperateLicensePlateType.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BIG_CAR", "MINI_CAR", "EMBASSY_CAR", "CONSULATE_CAR", "OVERSEAS_CAR", "FOREIGN_CAR", "COMMON_MOTORBIKE", "HANDINESS_MOTORBIKE", "EMBASSY_MOTORBIKE", "CONSULATE_MOTORBIKE", "OVERSEAS_MOTORBIKE", "FOREIGN_MOTORBIKE", "LOW_SPEED_CAR", "TRACTOR", "TRAILER", "COACH_CAR", "COACH_MOTORBIKE", "TEMPORARY_ENTRY_CAR", "TEMPORARY_ENTRY_MOTORBIKE", "TEMPORARY_DRIVING", "POLICE_CAR", "POLICE_MOTORBIKE", "AGRICULTURAL", "HONGKONG_ENTRY_EXIT", "MACAO_ENTRY_EXIT", "ARMED_POLICE", "ARMY"}));
        comboBoxVehicleOperateLicensePlateType.setBounds(169, 96, 435, 21);
        VehicleOperatePanel.add(comboBoxVehicleOperateLicensePlateType);
        
        JLabel lblVehicleOperateLicensePlateColor = new JLabel("License plate color");
        lblVehicleOperateLicensePlateColor.setBounds(47, 149, 118, 15);
        VehicleOperatePanel.add(lblVehicleOperateLicensePlateColor);
        comboBoxLicenseVehicleOperatePlateColor.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BLACK", "WHITE", "GRAY", "RED", "BLUE", "YELLOW", "ORANGE", "BROWN", "GREEN", "PURPLE", "CYAN", "PINK", "TRANSPARENT", "SILVERYWHITE", "DARK", "LIGHT", "COLOURLESS", "YELLOWGREEN", "GRADUALGREEN"}));
        
        
        comboBoxLicenseVehicleOperatePlateColor.setBounds(169, 146, 435, 21);
        VehicleOperatePanel.add(comboBoxLicenseVehicleOperatePlateColor);
        
        JButton btnVehicleOperateComplete = new JButton(" Complete ");
        btnVehicleOperateComplete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		boolean bRet = false;
        		NETDEV_LIB_INFO_S stVehicleLibInfo = NetDemo.mapVehicleLib.get(NetDemo.VehicleLibComboBox.getItemAt(NetDemo.VehicleLibComboBox.getSelectedIndex()));
                if(dwOperateType == VEHICLE_OPERATE_WINDOW_EFFECT.VEHICLE_OPERATE_WINDOW_ADDVEHICLE)
                {
                	NETDEV_VEHICLE_INFO_LIST_S stVehicleMemberList = new NETDEV_VEHICLE_INFO_LIST_S();
                	stVehicleMemberList.udwVehicleNum = 1;
                	NETDEV_VEHICLE_DETAIL_INFO_S stMemberInfoList = new NETDEV_VEHICLE_DETAIL_INFO_S();
                	stMemberInfoList.stPlateAttr = new NETDEV_PLATE_ATTR_INFO_S();
                	stMemberInfoList.stPlateAttr.udwColor = Common.StringConventToEnumNETDEV_PLATE_COLOR_E(comboBoxLicenseVehicleOperatePlateColor.getSelectedItem().toString());
                	stMemberInfoList.stPlateAttr.udwType = Common.StringConventToEnumNETDEV_PLATE_TYPE_E(comboBoxVehicleOperateLicensePlateType.getSelectedItem().toString());
                	Common.stringToByteArray(VehicleOperatePlateNumberTextField.getText(), stMemberInfoList.stPlateAttr.szPlateNo);
                	stMemberInfoList.stVehicleAttr = new NETDEV_VEHICLE_MEMBER_ATTR_S();
                	stMemberInfoList.stVehicleAttr.udwColor = Common.StringConventToEnumNETDEV_PLATE_COLOR_E(comboBoxlblVehicleOperateCarBodyColor.getSelectedItem().toString());
                	stMemberInfoList.write();
                	stVehicleMemberList.pstMemberInfoList = stMemberInfoList.getPointer();
                	
                	NETDEV_BATCH_OPERATOR_LIST_S stResultList = new NETDEV_BATCH_OPERATOR_LIST_S();
                	stResultList.udwNum = 1;
                	NETDEV_BATCH_OPERATOR_INFO_S stBatchList = new NETDEV_BATCH_OPERATOR_INFO_S();
                	stResultList.pstBatchList = stBatchList.getPointer();
                	
                	bRet = netdevsdk.NETDEV_AddVehicleMemberList(NetDemo.lpUserID, stVehicleLibInfo.udwID, stVehicleMemberList, stResultList);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_AddVehicleMemberList failed:%d\n", netdevsdk.NETDEV_GetLastError());
					}
                }
                else if(dwOperateType == VEHICLE_OPERATE_WINDOW_EFFECT.VEHICLE_OPERATE_WINDOW_MODIFVEHICLE)
                {
    				String strVehiclePlateName = (String) NetDemo.VehicleTable.getValueAt(NetDemo.VehicleTable.getSelectedRow(), 0);
    				NETDEV_VEHICLE_DETAIL_INFO_S stVehilceDetailInfo = NetDemo.mapVehicleInfo.get(strVehiclePlateName);
    				stVehilceDetailInfo.stPlateAttr.udwColor = Common.StringConventToEnumNETDEV_PLATE_COLOR_E(comboBoxLicenseVehicleOperatePlateColor.getSelectedItem().toString());
    				stVehilceDetailInfo.stPlateAttr.udwType = Common.StringConventToEnumNETDEV_PLATE_TYPE_E(comboBoxVehicleOperateLicensePlateType.getSelectedItem().toString());
    				stVehilceDetailInfo.stVehicleAttr.udwColor = Common.StringConventToEnumNETDEV_PLATE_COLOR_E(comboBoxlblVehicleOperateCarBodyColor.getSelectedItem().toString()); 
    				NETDEV_VEHICLE_INFO_LIST_S stVehicleMemberList = new NETDEV_VEHICLE_INFO_LIST_S();
    				stVehilceDetailInfo.write();
    				stVehicleMemberList.udwVehicleNum = 1;
    				stVehicleMemberList.pstMemberInfoList = stVehilceDetailInfo.getPointer();
    				
    				NETDEV_BATCH_OPERATOR_LIST_S stResutList = new NETDEV_BATCH_OPERATOR_LIST_S();
    				stResutList.udwNum = 1;
    				NETDEV_BATCH_OPERATOR_INFO_S stBatchList = new NETDEV_BATCH_OPERATOR_INFO_S();
    				stResutList.pstBatchList = stBatchList.getPointer();
    				
    				/* 参数2目前默认填0生效 */
    				bRet = netdevsdk.NETDEV_ModifyVehicleMemberInfo(NetDemo.lpUserID, stVehicleLibInfo.udwID, stVehicleMemberList, stResutList);
    				if(false == bRet)
    				{
    		    		System.out.printf("NETDEV_ModifyVehicleMemberInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
    		    		return;
    				}
                }
                dispose();
        	}
        });
        btnVehicleOperateComplete.setBounds(384, 528, 93, 23);
        VehicleOperatePanel.add(btnVehicleOperateComplete);
        
        JButton btnVehicleOperateCancel = new JButton("Cancel");
        btnVehicleOperateCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		dispose();
        	}
        });
        btnVehicleOperateCancel.setBounds(511, 528, 93, 23);
        VehicleOperatePanel.add(btnVehicleOperateCancel);
        
        JLabel lblVehicleOperateCarBodyColor = new JLabel("Car body color");
        lblVehicleOperateCarBodyColor.setBounds(47, 202, 96, 15);
        VehicleOperatePanel.add(lblVehicleOperateCarBodyColor);
        
        comboBoxlblVehicleOperateCarBodyColor.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BLACK", "WHITE", "GRAY", "RED", "BLUE", "YELLOW", "ORANGE", "BROWN", "GREEN", "PURPLE", "CYAN", "PINK", "TRANSPARENT", "SILVERYWHITE", "DARK", "LIGHT", "COLOURLESS", "YELLOWGREEN", "GRADUALGREEN"}));
        comboBoxlblVehicleOperateCarBodyColor.setBounds(169, 199, 435, 21);
        VehicleOperatePanel.add(comboBoxlblVehicleOperateCarBodyColor);
        
        if(dwOperateType == VEHICLE_OPERATE_WINDOW_EFFECT.VEHICLE_OPERATE_WINDOW_ADDVEHICLE)
        {
    		this.setTitle("Add vehicle info");
        }
        else if(dwOperateType == VEHICLE_OPERATE_WINDOW_EFFECT.VEHICLE_OPERATE_WINDOW_MODIFVEHICLE)
        {
        	this.setTitle("Modify vehicle info");
        	
			String strVehicleName = (String) NetDemo.VehicleTable.getValueAt(NetDemo.VehicleTable.getSelectedRow(), 0);
			NETDEV_VEHICLE_DETAIL_INFO_S stVehicleInfo = NetDemo.mapVehicleInfo.get(strVehicleName);
			VehicleOperatePlateNumberTextField.setText(Common.byteArrayToString(stVehicleInfo.stPlateAttr.szPlateNo));
			comboBoxVehicleOperateLicensePlateType.setSelectedItem(Common.EnumNETDEV_PLATE_TYPE_EConventToString(stVehicleInfo.stPlateAttr.udwType));
			comboBoxLicenseVehicleOperatePlateColor.setSelectedItem(Common.EnumNETDEV_PLATE_COLOR_EConventToString(stVehicleInfo.stPlateAttr.udwColor));
			comboBoxlblVehicleOperateCarBodyColor.setSelectedItem(Common.EnumNETDEV_PLATE_COLOR_EConventToString(stVehicleInfo.stVehicleAttr.udwColor));
        	
        	
        	VehicleOperatePlateNumberTextField.setEnabled(false);
        }
        
	}
}
