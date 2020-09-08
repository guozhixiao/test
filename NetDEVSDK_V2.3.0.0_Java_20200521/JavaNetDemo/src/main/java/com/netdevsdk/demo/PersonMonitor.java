package main.java.com.netdevsdk.demo;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.sun.jna.Memory;
import main.java.com.netdevsdk.demo.Common.NETDEMO_DEVICE_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_LIB_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_MONITION_CHL_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_MONITION_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_MONITION_RULE_INFO_S;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.NETDEV_MONITOR_RESULT_INFO_S;

import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PersonMonitor extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel PersonMonitorPanel = new JPanel();
	JLabel lblPersonMonitorTaskName = new JLabel("* Task name");
	JTextField textFieldPersonMonitorTaskName = new JTextField();
	JLabel lblPersonMonitorType = new JLabel("* Monitor type");
	JRadioButton rdbtnPersonMonitorType = new JRadioButton("Match alarm");
	JRadioButton rdbtnPersonMonitorNoMatch = new JRadioButton("No match alarm");
	JLabel lblPersonMonitorDescribe = new JLabel("Describe");
	JTextField textFieldPersonMonitorDescribe = new JTextField();
	JLabel lblPersonAlarmObject = new JLabel("* Monitor object");
	JRadioButton rdbtnPersonAlarmObjectPersonLib = new JRadioButton("Person lib");
	JRadioButton rdbtnPersonAlarmObjectPersonFace = new JRadioButton("Person face");
	JPanel panelPersonMonitorPersonLib = new JPanel();
	JComboBox<String> comboBoxPersonMonitorPersonLib = new JComboBox<String>();
	JButton btnPersonMonitorComplate = new JButton("complate");
	JButton btnPersonMonitorCancle = new JButton("Cancel");
	
	NetDEVSDKLib netdevsdk = NetDEVSDKLib.NETDEVSDK_INSTANCE;
	
	public static class PERSON_MONITOR_OPERATE_WINDOW_EFFECT{
	    public static final int PERSON_MONITOR_OPERATE_WINDOW_ADDPERSONMONITOR             = 1;        /* 添加人脸布控 */
	    public static final int PERSON_MONITOR_OPERATE_WINDOW_MODIFYPERSONMONITOR          = 2;        /* 修改人脸布控 */
	}

	public PersonMonitor(int dwOperateType) {
		this.setSize(689,600);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setTitle("Add Person monitor");
		this.setVisible(true);
		
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension screenSize =toolkit.getScreenSize();
        int x=(screenSize.width-this.getWidth())/2;
        int y=(screenSize.height-this.getHeight())/2;
        this.setLocation(x,y);
		
		getContentPane().add(PersonMonitorPanel, BorderLayout.CENTER);
		PersonMonitorPanel.setLayout(null);
		
		JLabel lblPersonMonitorTaskName = new JLabel("* Task name");
		lblPersonMonitorTaskName.setBounds(24, 10, 90, 15);
		PersonMonitorPanel.add(lblPersonMonitorTaskName);
		
		
		textFieldPersonMonitorTaskName.setBounds(126, 7, 483, 21);
		PersonMonitorPanel.add(textFieldPersonMonitorTaskName);
		textFieldPersonMonitorTaskName.setColumns(10);
		
		
		lblPersonMonitorType.setBounds(24, 46, 90, 15);
		PersonMonitorPanel.add(lblPersonMonitorType);
		
		
		rdbtnPersonMonitorType.setBounds(126, 42, 121, 23);
		PersonMonitorPanel.add(rdbtnPersonMonitorType);
		
		
		rdbtnPersonMonitorNoMatch.setBounds(312, 42, 121, 23);
		PersonMonitorPanel.add(rdbtnPersonMonitorNoMatch);
		
		ButtonGroup MonitorTypeGroup = new ButtonGroup();
		MonitorTypeGroup.add(rdbtnPersonMonitorType);
		MonitorTypeGroup.add(rdbtnPersonMonitorNoMatch);
		
		
		lblPersonMonitorDescribe.setBounds(37, 83, 54, 15);
		PersonMonitorPanel.add(lblPersonMonitorDescribe);
		
		
		textFieldPersonMonitorDescribe.setBounds(126, 80, 483, 21);
		PersonMonitorPanel.add(textFieldPersonMonitorDescribe);
		textFieldPersonMonitorDescribe.setColumns(10);
		
		
		lblPersonAlarmObject.setBounds(24, 120, 96, 15);
		PersonMonitorPanel.add(lblPersonAlarmObject);
		
		
		rdbtnPersonAlarmObjectPersonLib.setBounds(126, 116, 121, 23);
		PersonMonitorPanel.add(rdbtnPersonAlarmObjectPersonLib);
		
		
		rdbtnPersonAlarmObjectPersonFace.setBounds(312, 116, 121, 23);
		PersonMonitorPanel.add(rdbtnPersonAlarmObjectPersonFace);
		
		ButtonGroup MonitorObjectGroup = new ButtonGroup();
		MonitorObjectGroup.add(rdbtnPersonAlarmObjectPersonLib);
		MonitorObjectGroup.add(rdbtnPersonAlarmObjectPersonFace);
		
		
		panelPersonMonitorPersonLib.setBorder(new TitledBorder(null, "Person Lib", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPersonMonitorPersonLib.setBounds(24, 156, 618, 174);
		PersonMonitorPanel.add(panelPersonMonitorPersonLib);
		panelPersonMonitorPersonLib.setLayout(null);
		
		
		comboBoxPersonMonitorPersonLib.setBounds(10, 26, 170, 21);
		panelPersonMonitorPersonLib.add(comboBoxPersonMonitorPersonLib);
		
		
		if(dwOperateType ==PERSON_MONITOR_OPERATE_WINDOW_EFFECT.PERSON_MONITOR_OPERATE_WINDOW_ADDPERSONMONITOR)
		{
			this.setTitle("Add Person monitor");
			AddPersonMonitorInit();
		}
		else if(dwOperateType ==PERSON_MONITOR_OPERATE_WINDOW_EFFECT.PERSON_MONITOR_OPERATE_WINDOW_MODIFYPERSONMONITOR)
		{
			this.setTitle("Modify Person monitor");
			
			String strPersonMonitorName = (String) NetDemo.PersonMonitorTable.getValueAt(NetDemo.PersonMonitorTable.getSelectedRow(), 0);
			NETDEV_MONITION_INFO_S stPersonMonitorInfo = NetDemo.mapPersonMonitorMap.get(strPersonMonitorName);
			
			textFieldPersonMonitorTaskName.setText(Common.byteArrayToString(stPersonMonitorInfo.stMonitorRuleInfo.szName));
			if(stPersonMonitorInfo.stMonitorRuleInfo.udwMonitorType == 0)
			{
				rdbtnPersonMonitorType.setSelected(true);
			}
			else
			{
				rdbtnPersonMonitorNoMatch.setSelected(true);
			}
			
			textFieldPersonMonitorDescribe.setText(Common.byteArrayToString(stPersonMonitorInfo.stMonitorRuleInfo.szReason));
			rdbtnPersonAlarmObjectPersonLib.setSelected(true);
			
			String strPersonLibString = NetDemo.PersonLibcomboBox.getItemAt(NetDemo.PersonLibcomboBox.getSelectedIndex());
			comboBoxPersonMonitorPersonLib.addItem(strPersonLibString);
			
			rdbtnPersonAlarmObjectPersonLib.setEnabled(false);
			rdbtnPersonAlarmObjectPersonFace.setEnabled(false);
			comboBoxPersonMonitorPersonLib.setEnabled(false);
			rdbtnPersonMonitorType.setEnabled(false);
			rdbtnPersonMonitorNoMatch.setEnabled(false);
		}
		
		btnPersonMonitorComplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dwOperateType == PERSON_MONITOR_OPERATE_WINDOW_EFFECT.PERSON_MONITOR_OPERATE_WINDOW_ADDPERSONMONITOR)
				{
					NETDEV_MONITION_INFO_S stMonitorInfo = new NETDEV_MONITION_INFO_S();
					stMonitorInfo.stMonitorRuleInfo = new NETDEV_MONITION_RULE_INFO_S();
					stMonitorInfo.stMonitorRuleInfo.bEnabled = 1;
					Common.stringToByteArray(textFieldPersonMonitorTaskName.getText(), stMonitorInfo.stMonitorRuleInfo.szName);
					Common.stringToByteArray(textFieldPersonMonitorDescribe.getText(), stMonitorInfo.stMonitorRuleInfo.szReason);
					if(rdbtnPersonMonitorType.isSelected() == true)
					{
						stMonitorInfo.stMonitorRuleInfo.udwMonitorType = 0;
					}
					else
					{
						stMonitorInfo.stMonitorRuleInfo.udwMonitorType = 1;
					}
					
					stMonitorInfo.stMonitorRuleInfo.udwLibNum = 1;		
					NETDEV_LIB_INFO_S stPersonLibInfo = NetDemo.mapPersonLib.get(comboBoxPersonMonitorPersonLib.getItemAt(comboBoxPersonMonitorPersonLib.getSelectedIndex()));
					stMonitorInfo.stMonitorRuleInfo.audwLibList[0] = stPersonLibInfo.udwID;
					
					if(NetDemo.DeviceTypeComboBox.getSelectedIndex() == 1)
					{
						stMonitorInfo.stMonitorRuleInfo.udwDevNum = 1;
						//根据通道号查找设备ID
						for(int i =0; i< NetDemo.gastLoginDeviceInfo.dwDevNum; i++)
						{
							NETDEMO_DEVICE_INFO_S stDemoDeviceInfo = NetDemo.gastLoginDeviceInfo.stDevLoginInfo.get(i);
							for(int j = 0; j< stDemoDeviceInfo.vecChanInfo.size(); j++)
							{
								if(NetDemo.ChannelID == stDemoDeviceInfo.vecChanInfo.get(j).stChnBaseInfo.dwChannelID)
								{
									stMonitorInfo.stMonitorRuleInfo.audwMonitorDevIDList[0] = stDemoDeviceInfo.stDevBasicInfo.dwDevID;
									break;
								}
							}
						}
					}
					else if(NetDemo.DeviceTypeComboBox.getSelectedIndex() == 0)
					{
						stMonitorInfo.stMonitorRuleInfo.udwChannelNum = 1;
						stMonitorInfo.stMonitorRuleInfo.pudwMonitorChlIDList = new Memory(4);
						stMonitorInfo.stMonitorRuleInfo.pudwMonitorChlIDList.setInt(0, NetDemo.ChannelID);
					}

					
					NETDEV_MONITOR_RESULT_INFO_S stMonitorResultInfo = new NETDEV_MONITOR_RESULT_INFO_S();
					stMonitorResultInfo.udwChannelNum = 1;
					NETDEV_MONITION_CHL_INFO_S stMonitorChlInfos = new NETDEV_MONITION_CHL_INFO_S(); 
					stMonitorChlInfos.write();
					stMonitorResultInfo.pstMonitorChlInfos = stMonitorChlInfos.getPointer();
					
					
					boolean bRet = netdevsdk.NETDEV_AddPersonMonitorInfo(NetDemo.lpUserID, stMonitorInfo, stMonitorResultInfo);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_AddPersonMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
				}
				else if(dwOperateType == PERSON_MONITOR_OPERATE_WINDOW_EFFECT.PERSON_MONITOR_OPERATE_WINDOW_MODIFYPERSONMONITOR) 
				{
					String strPersonMonitorName = (String) NetDemo.PersonMonitorTable.getValueAt(NetDemo.PersonMonitorTable.getSelectedRow(), 0);
					NETDEV_MONITION_INFO_S stPersonMonitorInfo = NetDemo.mapPersonMonitorMap.get(strPersonMonitorName);
					
					boolean bRet = false;
					
					if(NetDemo.DeviceTypeComboBox.getSelectedIndex() == 1)
					{
						bRet = netdevsdk.NETDEV_GetPersonMonitorRuleInfo(NetDemo.lpUserID, stPersonMonitorInfo);
						if(false == bRet)
						{
				    		System.out.printf("NETDEV_GetPersonMonitorRuleInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
				    		return;
						}
					}
					else if(NetDemo.DeviceTypeComboBox.getSelectedIndex() == 0)
					{
						stPersonMonitorInfo.udwLinkStrategyNum = 0;
					}
					
					Common.stringToByteArray(textFieldPersonMonitorTaskName.getText(), stPersonMonitorInfo.stMonitorRuleInfo.szName);
					Common.stringToByteArray(textFieldPersonMonitorDescribe.getText(), stPersonMonitorInfo.stMonitorRuleInfo.szReason);
					stPersonMonitorInfo.write();
					
					bRet = netdevsdk.NETDEV_SetPersonMonitorRuleInfo(NetDemo.lpUserID, stPersonMonitorInfo);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_SetPersonMonitorRuleInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
				}
				
				dispose();
			}
		});
		
		
		btnPersonMonitorComplate.setBounds(363, 414, 93, 23);
		PersonMonitorPanel.add(btnPersonMonitorComplate);
		btnPersonMonitorCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		
		btnPersonMonitorCancle.setBounds(549, 414, 93, 23);
		PersonMonitorPanel.add(btnPersonMonitorCancle);
		
		

	}
	
	void AddPersonMonitorInit()
	{
		for(Map.Entry<String, NETDEV_LIB_INFO_S> entry : NetDemo.mapPersonLib.entrySet())
		{
			String strPersonLibNameString = entry.getKey();
			comboBoxPersonMonitorPersonLib.addItem(strPersonLibNameString);
		}
 
	}
}
