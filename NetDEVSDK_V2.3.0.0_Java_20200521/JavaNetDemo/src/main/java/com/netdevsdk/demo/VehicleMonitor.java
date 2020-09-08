package main.java.com.netdevsdk.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import main.java.com.netdevsdk.lib.NetDEVSDKLib;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_LIB_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_MONITION_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_MONITION_RULE_INFO_S;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class VehicleMonitor extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NetDEVSDKLib netdevsdk = NetDEVSDKLib.NETDEVSDK_INSTANCE;
	private JTextField textFieldVehicleMonitorTaskName;
	private JTextField textFieldVehicleMonitorDescribe;
	private JComboBox<String> comboBoxVehicleMonitorLib = new JComboBox<String>();
	private JComboBox<String> comboBoxVehicleMonitorReason = new JComboBox<String>();
	
	public static class VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT{
	    public static final int VEHICLE_MONITOR_OPERATE_WINDOW_ADDVEHICLEMONITOR             = 1;        /* 添加人脸布控 */
	    public static final int VEHICLE_MONITOR_OPERATE_WINDOW_MODIFYVEHICLEMONITOR          = 2;        /* 修改人脸布控 */
	}
	
	public VehicleMonitor(int dwOperateType) {
		this.setSize(689,600);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize =toolkit.getScreenSize();
        int x=(screenSize.width-this.getWidth())/2;
        int y=(screenSize.height-this.getHeight())/2;
        this.setLocation(x,y);
        
        JPanel VehicleMonitorOperatePanel = new JPanel();
        getContentPane().add(VehicleMonitorOperatePanel, BorderLayout.CENTER);
        VehicleMonitorOperatePanel.setLayout(null);
        
        JLabel lblVehicleMonitorMissionName = new JLabel("* Task name");
        lblVehicleMonitorMissionName.setBounds(31, 27, 95, 15);
        VehicleMonitorOperatePanel.add(lblVehicleMonitorMissionName);
        
        textFieldVehicleMonitorTaskName = new JTextField();
        textFieldVehicleMonitorTaskName.setBounds(153, 24, 474, 21);
        VehicleMonitorOperatePanel.add(textFieldVehicleMonitorTaskName);
        textFieldVehicleMonitorTaskName.setColumns(10);
        
        JLabel lblVehicleMonitorType = new JLabel("* Monitor type");
        lblVehicleMonitorType.setBounds(31, 78, 95, 15);
        VehicleMonitorOperatePanel.add(lblVehicleMonitorType);
        
        JRadioButton rdbtnVehicleMonitorMatch = new JRadioButton("Match");
        rdbtnVehicleMonitorMatch.setBounds(153, 74, 121, 23);
        VehicleMonitorOperatePanel.add(rdbtnVehicleMonitorMatch);
        
        JRadioButton rdbtnVehicleMonitorNoMatch = new JRadioButton("Not match");
        rdbtnVehicleMonitorNoMatch.setBounds(386, 74, 121, 23);
        VehicleMonitorOperatePanel.add(rdbtnVehicleMonitorNoMatch);
        
        ButtonGroup MonitorTypeGroup = new ButtonGroup();
        MonitorTypeGroup.add(rdbtnVehicleMonitorMatch);
        MonitorTypeGroup.add(rdbtnVehicleMonitorNoMatch);
        
        JLabel lblVehicleMonitorReason = new JLabel("Monitor reason");
        lblVehicleMonitorReason.setBounds(31, 132, 95, 15);
        VehicleMonitorOperatePanel.add(lblVehicleMonitorReason);
        
        JLabel lblVehicleMonitorDescribe = new JLabel("Describe");
        lblVehicleMonitorDescribe.setBounds(31, 191, 77, 15);
        VehicleMonitorOperatePanel.add(lblVehicleMonitorDescribe);
        
        textFieldVehicleMonitorDescribe = new JTextField();
        textFieldVehicleMonitorDescribe.setBounds(153, 188, 474, 21);
        VehicleMonitorOperatePanel.add(textFieldVehicleMonitorDescribe);
        textFieldVehicleMonitorDescribe.setColumns(10);
        
        JPanel panelPersonMonitorLib = new JPanel();
        panelPersonMonitorLib.setBorder(new TitledBorder(null, "Vehicle lib", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelPersonMonitorLib.setBounds(31, 237, 605, 204);
        VehicleMonitorOperatePanel.add(panelPersonMonitorLib);
        panelPersonMonitorLib.setLayout(null);
        

        comboBoxVehicleMonitorLib.setBounds(30, 35, 172, 21);
        panelPersonMonitorLib.add(comboBoxVehicleMonitorLib);
        
        JButton btnVehicleMonitorComplete = new JButton("Complete");
        btnVehicleMonitorComplete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		boolean bRet = false;
        		
        		if(dwOperateType == VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT.VEHICLE_MONITOR_OPERATE_WINDOW_ADDVEHICLEMONITOR)
        		{
    				NETDEV_MONITION_INFO_S stMonitorInfo = new NETDEV_MONITION_INFO_S();
    				stMonitorInfo.stMonitorRuleInfo = new NETDEV_MONITION_RULE_INFO_S();
    				stMonitorInfo.stMonitorRuleInfo.bEnabled = 1;
    				Common.stringToByteArray(textFieldVehicleMonitorTaskName.getText(), stMonitorInfo.stMonitorRuleInfo.szName);
    				Common.stringToByteArray(textFieldVehicleMonitorDescribe.getText(), stMonitorInfo.stMonitorRuleInfo.szReason);
    				if(rdbtnVehicleMonitorMatch.isSelected() == true)
    				{
    					stMonitorInfo.stMonitorRuleInfo.udwMonitorType = 0;
    				}
    				else
    				{
    					stMonitorInfo.stMonitorRuleInfo.udwMonitorType = 1;
    				}
    				
    				
    				String strPersonLibString = (String) comboBoxVehicleMonitorReason.getItemAt(comboBoxVehicleMonitorReason.getSelectedIndex());
    				switch (strPersonLibString) {
					case "Robbed car":
						stMonitorInfo.stMonitorRuleInfo.udwMonitorReason = 0;
						break;
					case "Stolen car":
						stMonitorInfo.stMonitorRuleInfo.udwMonitorReason = 1;
						break;
					case "Suspected vehicle":
						stMonitorInfo.stMonitorRuleInfo.udwMonitorReason = 2;
						break;
					case "Illegal vehicles":
						stMonitorInfo.stMonitorRuleInfo.udwMonitorReason = 3;
						break;
					case "Emergency check and control vehicle":
						stMonitorInfo.stMonitorRuleInfo.udwMonitorReason = 4;
						break;

					default:
						break;
					}
    				
    				stMonitorInfo.stMonitorRuleInfo.udwLibNum = 1;		
    				NETDEV_LIB_INFO_S stVehicleLibInfo = NetDemo.mapVehicleLib.get(comboBoxVehicleMonitorLib.getItemAt(comboBoxVehicleMonitorLib.getSelectedIndex()));
    				stMonitorInfo.stMonitorRuleInfo.audwLibList[0] = stVehicleLibInfo.udwID;
    				
    				bRet = netdevsdk.NETDEV_AddVehicleMonitorInfo(NetDemo.lpUserID, stMonitorInfo);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_AddVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
        		}
        		else if(dwOperateType == VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT.VEHICLE_MONITOR_OPERATE_WINDOW_MODIFYVEHICLEMONITOR)
        		{
					String strVehicleMonitorName = (String) NetDemo.VehicleMonitorTable.getValueAt(NetDemo.VehicleMonitorTable.getSelectedRow(), 0);
					NETDEV_MONITION_INFO_S stVehicleMonitorInfo = NetDemo.mapVehicleMonitorMap.get(strVehicleMonitorName);
					
					
					NETDEV_MONITION_RULE_INFO_S stMonitorRuleInfo = new NETDEV_MONITION_RULE_INFO_S();
					bRet = netdevsdk.NETDEV_GetVehicleMonitorInfo(NetDemo.lpUserID, stVehicleMonitorInfo.udwID, stMonitorRuleInfo);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_SetVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
					
    				Common.stringToByteArray(textFieldVehicleMonitorTaskName.getText(), stMonitorRuleInfo.szName);
    				Common.stringToByteArray(textFieldVehicleMonitorDescribe.getText(), stMonitorRuleInfo.szReason);
    				if(rdbtnVehicleMonitorMatch.isSelected() == true)
    				{
    					stMonitorRuleInfo.udwMonitorType = 0;
    				}
    				else
    				{
    					stMonitorRuleInfo.udwMonitorType = 1;
    				}
    				
    				
    				String strPersonLibString = (String) comboBoxVehicleMonitorReason.getItemAt(comboBoxVehicleMonitorReason.getSelectedIndex());
    				switch (strPersonLibString) {
					case "Robbed car":
						stMonitorRuleInfo.udwMonitorReason = 0;
						break;
					case "Stolen car":
						stMonitorRuleInfo.udwMonitorReason = 1;
						break;
					case "Suspected vehicle":
						stMonitorRuleInfo.udwMonitorReason = 2;
						break;
					case "Illegal vehicles":
						stMonitorRuleInfo.udwMonitorReason = 3;
						break;
					case "Emergency check and control vehicle":
						stMonitorRuleInfo.udwMonitorReason = 4;
						break;

					default:
						break;
					}
    				
    				bRet = netdevsdk.NETDEV_SetVehicleMonitorInfo(NetDemo.lpUserID, stVehicleMonitorInfo.udwID, stMonitorRuleInfo);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_SetVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
        		}

        		dispose();
        	}
        });
        btnVehicleMonitorComplete.setBounds(406, 528, 93, 23);
        VehicleMonitorOperatePanel.add(btnVehicleMonitorComplete);
        
        JButton btnVehicleMonitorCancel = new JButton("Cancel");
        btnVehicleMonitorCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		dispose();
        	}
        });
        btnVehicleMonitorCancel.setBounds(534, 528, 93, 23);
        VehicleMonitorOperatePanel.add(btnVehicleMonitorCancel);
        
        comboBoxVehicleMonitorReason.setModel(new DefaultComboBoxModel<String>(new String[] {"Robbed car", "Stolen car", "Suspected vehicle", "Illegal vehicles", "Emergency check and control vehicle", ""}));
        comboBoxVehicleMonitorReason.setBounds(153, 129, 474, 21);
        VehicleMonitorOperatePanel.add(comboBoxVehicleMonitorReason);
        
        if(dwOperateType == VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT.VEHICLE_MONITOR_OPERATE_WINDOW_ADDVEHICLEMONITOR)
        {
        	this.setTitle("Add vehicle monitor");
        	AddPersonMonitorInit();
        }
        else if(dwOperateType == VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT.VEHICLE_MONITOR_OPERATE_WINDOW_MODIFYVEHICLEMONITOR)
        {
        	this.setTitle("Modify vehicle monitor");
        	
			String strVehicleMonitorName = (String) NetDemo.VehicleMonitorTable.getValueAt(NetDemo.VehicleMonitorTable.getSelectedRow(), 0);
			NETDEV_MONITION_INFO_S stMonitorInfo = NetDemo.mapVehicleMonitorMap.get(strVehicleMonitorName);
			
			textFieldVehicleMonitorTaskName.setText(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szName));
			if(stMonitorInfo.stMonitorRuleInfo.udwMonitorType == 0)
			{
				rdbtnVehicleMonitorMatch.setSelected(true);
			}
			else
			{
				rdbtnVehicleMonitorNoMatch.setSelected(true);
			}
			
			textFieldVehicleMonitorDescribe.setText(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szReason));

			switch (stMonitorInfo.stMonitorRuleInfo.udwMonitorReason) {
			case 0:
				comboBoxVehicleMonitorReason.addItem("Robbed car");
				break;
			case 1:
				comboBoxVehicleMonitorReason.addItem("Stolen car");
				break;
			case 2:
				comboBoxVehicleMonitorReason.addItem("Suspected vehicle");
				break;
			case 3:
				comboBoxVehicleMonitorReason.addItem("Illegal vehicles");
				break;
			case 4:
				comboBoxVehicleMonitorReason.addItem("Emergency check and control vehicle");
				break;
				
			default:
				break;
			}
				
			String strPersonLibString = (String) NetDemo.VehicleLibComboBox.getItemAt(NetDemo.VehicleLibComboBox.getSelectedIndex());
			comboBoxVehicleMonitorLib.addItem(strPersonLibString);
			comboBoxVehicleMonitorLib.setEnabled(false);
        }
        
	}
	
	void AddPersonMonitorInit()
	{
		for(Map.Entry<String, NETDEV_LIB_INFO_S> entry : NetDemo.mapVehicleLib.entrySet())
		{
			String strVehiclePersonLibNameString = entry.getKey();
			comboBoxVehicleMonitorLib.addItem(strVehiclePersonLibNameString);
		}
 
	}
}
