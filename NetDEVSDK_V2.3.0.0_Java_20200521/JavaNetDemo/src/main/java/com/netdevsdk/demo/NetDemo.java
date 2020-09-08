package main.java.com.netdevsdk.demo;

import main.java.com.netdevsdk.demo.AccessControlPermissionWindow.PERMISSION_OPERATE_WINDOW_EFFECT;
import main.java.com.netdevsdk.demo.Common.NETDEMO_DEVICE_INFO_S;
import main.java.com.netdevsdk.demo.Common.NETDEMO_DEV_LOGININFO_S;
import main.java.com.netdevsdk.demo.PersonMonitor.PERSON_MONITOR_OPERATE_WINDOW_EFFECT;
import main.java.com.netdevsdk.demo.PersonOperateWindow.PERSON_OPERATE_WINDOW_EFFECT;
import main.java.com.netdevsdk.demo.VehicleMonitor.VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT;
import main.java.com.netdevsdk.demo.VehicleOperateWindow.VEHICLE_OPERATE_WINDOW_EFFECT;
import main.java.com.netdevsdk.lib.*;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import java.awt.Panel;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFileChooser;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.UIManager;

public class NetDemo {

	private JFrame frame;
	private JTextField textIP;
	private JTextField textPort;
	private JTextField textUserName;
	private JTextField textDevicePassword;
	private JTextField textCloudURL;
	private JTextField textCloudUserName;
	private JTextField textCloudPassword;
	private JTable CloudDevListTable;
	private JTable DeviceTable;
	private JTable AlarmTable;
	private JTable DiscoveryTable;
	private JTextField textStartIP;
	private JTextField textEndIP;
	private JTable RecordTable;
	private JTextField textFieldCloudDevUserName;
	static JComboBox<String> PersonLibcomboBox;
	static JComboBox<String> VehicleLibComboBox = new JComboBox<String>();
	private JComboBox<Object> VehiclePassPlateColorComboBox = new JComboBox<Object>();
	private JComboBox<Object> VehiclePassRecordCarColorComboBox = new JComboBox<Object>();
	static JComboBox<String> DeviceTypeComboBox = new JComboBox<String>();

	NetDEVSDKLib netdevsdk = NetDEVSDKLib.NETDEVSDK_INSTANCE;
	static Pointer lpUserID = null; 
	private Pointer lpCloudUserID = null; 
	private Pointer lpPlayHandle = null; 
	private Pointer lpTalkHandle = null; 
	private Pointer lpDownloadHandle = null; 
	static  int ChannelID		= 0;
	private int PlayBackControlCmd = 0;
	private int PersonStructMointerID = -1;
	private int dwVehicleStructMointerID = -1;
	private int dwPersonRecognizeMointerID = -1;
	private int dwVehicleRecognizeMointerID = -1;
	private int dwAccessControlFGMointerID = -1;
	private String strPicturePath = "./Picture/";
	
	static Map<String, NETDEV_LIB_INFO_S> mapPersonLib = new HashMap<String, NETDEV_LIB_INFO_S>();
	static Map<String, NETDEV_PERSON_INFO_S> mapPersonInfo = new HashMap<String, NETDEV_PERSON_INFO_S>();
	static Map<String, NETDEV_MONITION_INFO_S> mapPersonMonitorMap = new HashMap<String, NETDEV_MONITION_INFO_S>();
	static NETDEMO_DEV_LOGININFO_S gastLoginDeviceInfo;
	
	static Map<String, NETDEV_LIB_INFO_S> mapVehicleLib = new HashMap<String, NETDEV_LIB_INFO_S>();
	static Map<String, NETDEV_VEHICLE_DETAIL_INFO_S> mapVehicleInfo = new HashMap<String, NETDEV_VEHICLE_DETAIL_INFO_S>();
	static Map<String, NETDEV_MONITION_INFO_S> mapVehicleMonitorMap = new HashMap<String, NETDEV_MONITION_INFO_S>();

	public static final int NETDEMO_FIND_ACS_PERSON_COUNT = 10;
	private int dwFindACSPersonOffset = 0;
	static Map<Integer,NETDEV_ORG_INFO_S> ACPersonDeptMap = new HashMap<Integer,NETDEV_ORG_INFO_S>();
	static Map<Integer, NETDEV_ACS_PERSON_BASE_INFO_S> mapACPersonInfo = new HashMap<Integer, NETDEV_ACS_PERSON_BASE_INFO_S>();
	
	private boolean bStartRecord = false;
	private String strRecordPath = "";
	
	private String DateFormat = "yyyy-MM-dd HH:mm:ss";
	public static String strPersonChosePicurePath;
	
	private DefaultTableModel DiscoveryTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Type", "IP", "Port", "MAC", "Serial Num", "Manufacturer"
			}
		);
	
	private DefaultTableModel AlarmTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Time", "ChannelID", "Info"
			}
		);
	
	private DefaultTableModel PersonTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"name", "gender", "data birth", "nationlity", "privince", "city", "ID type", "id"
			}
		);
	
	private DefaultTableModel PersonMonitorTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"mission name", "description", "status"
			}
		);
	
	private DefaultTableModel VehicleTableModle = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"plate number", "plate color", "plate type", "car color", "status"
			}
		);
	
	private DefaultTableModel VehicleMonitorTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"name", "description", "alarm type", "monitor type"
			}
		);
	
	private DefaultTableModel ConfigBasicHardDiskTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No.", "Total Capacity(M)", "Used Capacity(M)", "Status", "Manufacture"
			}
		);
	
	private DefaultTableModel ConfigPrivacyMaskTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No.", "Left Top(x)", "Left Top(y)", "Right Bottom(x)", "Right Bottom(y)"
			}
		);
	
	private DefaultTableModel ConfigIOAlarmInputTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Alarm Name", "Alarm Type", "Alarm Input"
			}
		);
	

	private DefaultTableModel AccessControlPersonTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Person ID", "Name", "Gender", "Department", "Card Type", "ID No.", "Phone"
			}
		);
	
	private DefaultTableModel VCAPeopleCountingTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Channel ID", "Report Time", "Interval Time", "Enter Number", "Exit Number", "Total Enter Number", "Total Exit Number"
			}
		);
	
	private DefaultTableModel PeopleCountingVMSNVRStatistalTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ChannelID", "Time", "EnterNum", "ExitNum"
			}
		);
	
	private DefaultTableModel PeopleCountingVMSNVRRealTimeTableModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ChannelID", "EnterCount", "ExitCount"
			}
		);
	
	private DefaultTableModel UserListTableModel = new DefaultTableModel(
			new Object[][] {
	        },
	        new String[] {
		        "No.", "User Name", "User Type"
	        }
        );
	
	private DefaultTableModel tablePTZExtendPresetPatrolsModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Cruise Point", "Stay Time", "Speed"
			}
		);
	
	private DefaultTableModel PTZBasePresetModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Preset ID(0~255)", "Preset Name"
			}
		);

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetDemo window = new NetDemo();
					window.frame.setResizable(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NetDemo() {
		initialize();
		init();
	}
	
	public void init() {
    	String strLogPath = "./sdklog/";
    	boolean bRet = netdevsdk.NETDEV_SetLogPath(strLogPath);
    	if(false == bRet){
    		System.out.printf("NETDEV_SetLogPath failed:%d", netdevsdk.NETDEV_GetLastError());
    	}
		
        bRet = netdevsdk.NETDEV_Init();
    	if(false == bRet){
    		System.out.printf("Initialize failed:%d", netdevsdk.NETDEV_GetLastError());
    		return;
    	}
    	
        try {
            Common.updateStructureByReferenceMethod = Structure.class.getDeclaredMethod("updateStructureByReference", Class.class, Structure.class, Pointer.class);
            Common.updateStructureByReferenceMethod.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    	
		File file=new File(strPicturePath);
		if(!file.exists()){
			file.mkdir();
		}
		
    	return;
    }
	
	private NETDEV_DISCOVERY_CALLBACK_PF cbDiscoveryCallBack = new NETDEV_DISCOVERY_CALLBACK_PF();
	public class NETDEV_DISCOVERY_CALLBACK_PF implements NetDEVSDKLib.NETDEV_DISCOVERY_CALLBACK_PF {
		@Override
		public void invoke(NETDEV_DISCOVERY_DEVINFO_S pstDevInfo, Pointer lpUserData){
			Vector<String> vector = new Vector<String>();
			vector.add(Common.DevideTypeToString((int)pstDevInfo.enDevType));
			vector.add(Common.utfToString(pstDevInfo.szDevAddr));
			vector.add(String.valueOf(pstDevInfo.dwDevPort));
			vector.add(Common.utfToString(pstDevInfo.szDevMac));
			vector.add(Common.utfToString(pstDevInfo.szDevSerailNum));
			vector.add(Common.utfToString(pstDevInfo.szManuFacturer));
	        DiscoveryTableModel.insertRow(0,vector);
		}
    }
	
	private NETDEV_SOURCE_DATA_CALLBACK_PF fPlayDataCallBack = new NETDEV_SOURCE_DATA_CALLBACK_PF();
    private class NETDEV_SOURCE_DATA_CALLBACK_PF implements NetDEVSDKLib.NETDEV_SOURCE_DATA_CALLBACK_PF{
    	@Override
    	public void invoke(Pointer lpPlayHandle, Pointer pucBuffer, int dwBufSize, int dwMediaDataType, Pointer lpUserParam){
    		byte[] szDataBuf = pucBuffer.getByteArray(0, dwBufSize);
    		
    		System.out.println(dwBufSize + ":" + Common.utfToString(szDataBuf));
    	}
    }
	
    private NETDEV_DECODE_AUDIO_DATA_CALLBACK_PF fDecodeAudioDataCallBack = new NETDEV_DECODE_AUDIO_DATA_CALLBACK_PF();
    private class NETDEV_DECODE_AUDIO_DATA_CALLBACK_PF implements NetDEVSDKLib.NETDEV_DECODE_AUDIO_DATA_CALLBACK_PF{
    	@Override
    	public void invoke(Pointer lpPlayHandle, NETDEV_WAVE_DATA_S pstWaveData, Pointer lpUserParam){
    		byte[] szDataBuf = pstWaveData.pcData.getByteArray(0, pstWaveData.dwDataLen);
    		
    		System.out.println(pstWaveData.dwWaveFormat + ":" + Common.utfToString(szDataBuf));
    	}
    }
    
	private NETDEV_DECODE_VIDEO_DATA_CALLBACK fPlayDecodeVideoCALLBACK = new NETDEV_DECODE_VIDEO_DATA_CALLBACK();
    private class NETDEV_DECODE_VIDEO_DATA_CALLBACK implements NetDEVSDKLib.NETDEV_DECODE_VIDEO_DATA_CALLBACK_PF{
		@Override
		public void invoke(Pointer lpPlayHandle, NETDEV_PICTURE_DATA_S pstPictureData, Pointer lpUserParam){
			System.out.println(pstPictureData.dwPicHeight + "," + pstPictureData.dwPicWidth + ":" + pstPictureData.dwLineSize[0]+":" + pstPictureData.dwLineSize[1]+":" + pstPictureData.dwLineSize[2]);
			
			if (0 == pstPictureData.dwPicHeight || 0 == pstPictureData.dwPicWidth)
		    {
		        return ;
		    }

			@SuppressWarnings("unused")
			byte[] szUBuf = pstPictureData.pucData[1].getByteArray(0,pstPictureData.dwLineSize[1]);
			@SuppressWarnings("unused")
			byte[] szVBuf = pstPictureData.pucData[2].getByteArray(0,pstPictureData.dwLineSize[2]);
			
			byte[] szDataBuf = new byte[pstPictureData.dwPicHeight * pstPictureData.dwPicWidth * 3 / 2];

		    int dwHeight = pstPictureData.dwPicHeight;
		    int dwWidth = pstPictureData.dwPicWidth;

		    int dwOffset = 0;
		    int dwIndex = 0;

		    for (dwIndex = 0; dwIndex < dwHeight; dwIndex++)
		    {
		    	byte[] szBuf = pstPictureData.pucData[0].getByteArray(dwIndex * pstPictureData.dwLineSize[0],dwWidth);
		    	for(int j=0; j<szBuf.length; j++)
		    	{
		    		szDataBuf[dwOffset+j] = szBuf[j];
		    	}
		        dwOffset += dwWidth;
		    }

		    for (dwIndex = 0; dwIndex < dwHeight / 2; dwIndex++)
		    {
		    	byte[] szBuf = pstPictureData.pucData[1].getByteArray(dwIndex * pstPictureData.dwLineSize[1],dwWidth / 2);
		    	for(int j=0; j<dwWidth / 2; j++)
		    	{
		    		szDataBuf[dwOffset+j] = szBuf[j];
		    	}
		        dwOffset += dwWidth / 2;
		    }

		    for (dwIndex = 0; dwIndex < dwHeight / 2; dwIndex++)
		    {
		    	byte[] szBuf = pstPictureData.pucData[2].getByteArray(dwIndex * pstPictureData.dwLineSize[2],dwWidth / 2);
		    	for(int j=0; j<dwWidth / 2; j++)
		    	{
		    		szDataBuf[dwOffset+j] = szBuf[j];
		    	}
		        dwOffset += dwWidth / 2;
		    }
		    

		    FileOutputStream fop = null;
		    File file;
		    try {
		    	file = new File("./videofile.yuv");
		    	fop = new FileOutputStream(file);
		    	if (!file.exists()) {
		    	    file.createNewFile();
		    	}
		    	fop.write(szDataBuf);
		    	fop.flush();
		    	fop.close();

		    	System.out.println("write Done");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
    	
    }
	
    private NETDEV_FaceSnapshotCallBack_PF cbFaceSnapshotCallBack = new NETDEV_FaceSnapshotCallBack_PF();
    private class NETDEV_FaceSnapshotCallBack_PF implements NetDEVSDKLib.NETDEV_FaceSnapshotCallBack_PF{
		@Override
		public void invoke(Pointer lpPlayHandle, NETDEV_TMS_FACE_SNAPSHOT_PIC_INFO_S pstFaceSnapShotData, Pointer lpUserParam){
			System.out.println(pstFaceSnapShotData.udwPicBuffLen);
			
			byte[] szDataBuf = new byte[pstFaceSnapShotData.udwPicBuffLen];
			szDataBuf = pstFaceSnapShotData.pcPicBuff.getByteArray(0, pstFaceSnapShotData.udwPicBuffLen);
			
		    FileOutputStream fop = null;
		    File file;
		    try {
		    	file = new File("./face.jpg");
		    	fop = new FileOutputStream(file);
		    	if (!file.exists()) {
		    	    file.createNewFile();
		    	}
		    	fop.write(szDataBuf);
		    	fop.flush();
		    	fop.close();

		    	System.out.println("write Done");
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
    	
    }
    
    private NETDEV_CarPlateCallBack_PF cbCarPlateCallBack = new NETDEV_CarPlateCallBack_PF();
    private class NETDEV_CarPlateCallBack_PF implements NetDEVSDKLib.NETDEV_CarPlateCallBack_PF{
		@Override
		public void invoke(Pointer lpPlayHandle, NETDEV_TMS_CAR_PLATE_INFO_S pstCarPlateData, Pointer lpUserParam){
			System.out.println("PicNum:" + pstCarPlateData.udwPicNum);
			
			for(int i=0; i<pstCarPlateData.udwPicNum; i++){
				byte[] szDataBuf = new byte[pstCarPlateData.stTmsPicInfo[0].udwPicSize];
				szDataBuf = pstCarPlateData.stTmsPicInfo[0].pcPicData.getByteArray(0, pstCarPlateData.stTmsPicInfo[0].udwPicSize);
				
			    FileOutputStream fop = null;
			    File file;
			    String strPath = "./" +  Integer.toString(i) + "Car.jpg";
			    try {
			    	file = new File(strPath);
			    	fop = new FileOutputStream(file);
			    	if (!file.exists()) {
			    	    file.createNewFile();
			    	}
			    	fop.write(szDataBuf);
			    	fop.flush();
			    	fop.close();

			    	System.out.println("write Done");
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
    	
    }
    
    
    private NETDEV_PassengerFlowStatisticCallBack_PF cbPassengerFlowStatisticCallBack = new NETDEV_PassengerFlowStatisticCallBack_PF();
    private class NETDEV_PassengerFlowStatisticCallBack_PF implements NetDEVSDKLib.NETDEV_PassengerFlowStatisticCallBack_PF{
		@Override
		public void invoke(Pointer lpPlayHandle, NETDEV_PASSENGER_FLOW_STATISTIC_DATA_S pstPassengerFlowData, Pointer lpUserParam){ 
			String strTime = Common.timeStamp2Date(Long.toString(pstPassengerFlowData.tReportTime), DateFormat);
			System.out.println(strTime + ":" + pstPassengerFlowData.dwTotalExitNum+ "," + pstPassengerFlowData.dwTotalEnterNum);
			
			
			
			
			Vector<String> PassengerFlowStatisticVector = new Vector<String>();
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.dwChannelID));
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.tReportTime));
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.tInterval));
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.dwEnterNum));
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.dwExitNum));
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.dwTotalEnterNum));
			PassengerFlowStatisticVector.add(String.valueOf(pstPassengerFlowData.dwTotalExitNum));
			
			VCAPeopleCountingTableModel.addRow(PassengerFlowStatisticVector);
			
		}
    	
    }
    
    private NETDEV_AlarmMessCallBack_PF_V30 cbAlarmMessCallBack = new NETDEV_AlarmMessCallBack_PF_V30();
    private JTextField textRecordBeginTime;
    private JTextField textRecordEndTime;
    private JTabbedPane tabSmartList;
    private class NETDEV_AlarmMessCallBack_PF_V30 implements NetDEVSDKLib.NETDEV_AlarmMessCallBack_PF_V30{
		@Override
		public void invoke(Pointer lpUserID, NETDEV_REPORT_INFO_S pstReportInfo, Pointer lpBuf, int dwBufLen,
				Pointer lpUserParam) {
			if (NETDEV_REPORT_TYPE_E.NETDEV_REPORT_TYPE_ALARM == pstReportInfo.dwReportType) {
				String strTime = Common.timeStamp2Date(Long.toString(pstReportInfo.stAlarmInfo.tAlarmTimeStamp),DateFormat);

				Vector<String> vector = new Vector<String>();
				vector.add(strTime);
				String ChannelInfo = Integer.toString(pstReportInfo.stAlarmInfo.dwChannelID);
				String AlarmSrc = Common.utfToString(pstReportInfo.stAlarmInfo.szAlarmSrc);
				if (!AlarmSrc.isEmpty()) {
					ChannelInfo += "(";
					ChannelInfo += AlarmSrc;
					ChannelInfo += ")";
				}
				vector.add(ChannelInfo);
				vector.add(Common.AlarmTypeToString(pstReportInfo.stAlarmInfo.dwAlarmType));
				/*if (pstReportInfo.stAlarmInfo.dwAlarmType == NETDEV_ALARM_TYPE_E.NETDEV_ALARM_INPUT_SWITCH
						|| pstReportInfo.stAlarmInfo.dwAlarmType == NETDEV_ALARM_TYPE_E.NETDEV_ALARM_INPUT_SWITCH_RECOVER
						|| pstReportInfo.stAlarmInfo.dwAlarmSrcID == NETDEV_ALARM_SRC_TYPE_E.NETDEV_ALARM_SRC_ALARM_INPUT_CHANNEL) {
					vector.add(Integer.toString(pstReportInfo.stAlarmInfo.wIndex));
				}*/

				AlarmTableModel.insertRow(0, vector);

				System.out.println(pstReportInfo.stAlarmInfo.dwAlarmID + ":" + pstReportInfo.stAlarmInfo.dwChannelID
						+ ":" + pstReportInfo.stAlarmInfo.dwAlarmLevel);

			}
			else{
				Vector<String> vector = new Vector<String>();
				vector.add("");
				vector.add("");
				vector.add("event_info");

				AlarmTableModel.insertRow(0, vector);
			}
		}
    	
    }
    
    private NETDEV_StructAlarmMessCallBack_PF cbStructAlarmCallBack = new NETDEV_StructAlarmMessCallBack_PF();
    private class NETDEV_StructAlarmMessCallBack_PF implements NetDEVSDKLib.NETDEV_StructAlarmMessCallBack_PF
    {

		@Override
		public void invoke(Pointer lpUserID, NETDEV_STRUCT_ALARM_INFO_S pstAlarmInfo,
				NETDEV_STRUCT_DATA_INFO_S pstAlarmData, Pointer lpUserData) {
			// TODO Auto-generated method stub
			System.out.println("NETDEV_StructAlarmMessCallBack_PF, UseId is:"+ lpUserID);
			
			String strTime = Common.getDate();
			
			NETDEV_STRUCT_IMAGE_INFO_S[] pstImageInfo = Common.pointerToStructureArray(pstAlarmData.udwImageNum, pstAlarmData.pstImageInfo, NETDEV_STRUCT_IMAGE_INFO_S.class);
			
			//人脸信息
			if(null != pstAlarmData.stObjectInfo.pstFaceInfo && 0 <pstAlarmData.stObjectInfo.udwFaceNum && null != pstAlarmData.pstImageInfo && 0 < pstAlarmData.udwImageNum)
			{
				NETDEV_FACE_STRUCT_INFO_S[] pstFaceInfo = Common.pointerToStructureArray(pstAlarmData.stObjectInfo.udwFaceNum, pstAlarmData.stObjectInfo.pstFaceInfo, NETDEV_FACE_STRUCT_INFO_S.class);
				for(int i = 0; i < pstAlarmData.stObjectInfo.udwFaceNum; i++)
				{
					for(int j = 0;j < pstAlarmData.udwImageNum; j++)
					{
						 if(pstFaceInfo[i].udwLargePicAttachIndex == pstImageInfo[j].udwIndex)
						 {
							 String strFileName = strPicturePath + strTime + "Face_large.jpg";
							 int iCmpRet = pstImageInfo[j].szUrl.toString().compareTo("");
							 if(iCmpRet != 0)
							 {
								 Pointer pszUrl = new Memory(260);
								 pszUrl.write(0, pstImageInfo[j].szUrl, 0, 260);
								 Pointer pszData = new Memory(pstImageInfo[j].udwSize);
								 netdevsdk.NETDEV_GetSystemPicture(lpUserID, pszUrl, pstImageInfo[j].udwSize, pszData);
								 
								 Common.savePicture(pszData, pstImageInfo[j].udwSize, strFileName);
							 }
							 else
							 {
								 Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							 }
							 continue;
						 }
						else if(pstFaceInfo[i].udwSmallPicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime + "Face_large.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
					}
				}
			}
			
			//人员信息
			if(null != pstAlarmData.stObjectInfo.pstPersonInfo && 0 <pstAlarmData.stObjectInfo.udwPersonNum &&  null != pstAlarmData.pstImageInfo && 0 < pstAlarmData.udwImageNum)
			{
				NETDEV_PERSON_STRUCT_INFO_S[] pstPersonInfo = Common.pointerToStructureArray(pstAlarmData.stObjectInfo.udwPersonNum, pstAlarmData.stObjectInfo.pstPersonInfo, NETDEV_PERSON_STRUCT_INFO_S.class);
				for(int i = 0; i < pstAlarmData.stObjectInfo.udwPersonNum; i++)
				{
					for(int j = 0;j < pstAlarmData.udwImageNum; j++)
					{
						if(pstPersonInfo[i].udwLargePicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_Person_Big.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
						else if(pstPersonInfo[i].udwSmallPicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_Person_Small.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
					}

				}
			}
			
			//非机动车信息
			if(null != pstAlarmData.stObjectInfo.pstNonMotorVehInfo && 0 <pstAlarmData.stObjectInfo.udwNonMotorVehNum &&  null != pstAlarmData.pstImageInfo && 0 < pstAlarmData.udwImageNum)
			{
				NETDEV_NON_MOTOR_VEH_INFO_S[] pstNonMotorVehInfo = Common.pointerToStructureArray(pstAlarmData.stObjectInfo.udwNonMotorVehNum, pstAlarmData.stObjectInfo.pstNonMotorVehInfo, NETDEV_NON_MOTOR_VEH_INFO_S.class);
				for(int i = 0; i < pstAlarmData.stObjectInfo.udwNonMotorVehNum; i++)
				{
					for(int j = 0;j < pstAlarmData.udwImageNum; j++)
					{
						if(pstNonMotorVehInfo[i].udwLargePicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_NonMotorVeh_Big.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
						else if(pstNonMotorVehInfo[i].udwSmallPicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_NonMotorVeh_Small.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
					}
				}
				
			}
			
			//车辆信息
			if(null != pstAlarmData.stObjectInfo.pstVehInfo && 0 <pstAlarmData.stObjectInfo.udwVehicleNum &&  null != pstAlarmData.pstImageInfo && 0 < pstAlarmData.udwImageNum)
			{
				NETDEV_VEH_INFO_S[] pstVehInfo = Common.pointerToStructureArray(pstAlarmData.stObjectInfo.udwVehicleNum, pstAlarmData.stObjectInfo.pstVehInfo, NETDEV_VEH_INFO_S.class);
				for(int i = 0; i < pstAlarmData.stObjectInfo.udwVehicleNum; i++)
				{
					for(int j = 0;j < pstAlarmData.udwImageNum; j++)
					{
						if(pstVehInfo[i].udwLargePicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_MotorVeh_Big.jpg";
							if(0 == pstImageInfo[j].szUrl.toString().compareTo(""))
							{
								Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
								continue;
							}
							else
							{
								 Pointer pszUrl = new Memory(260);
								 pszUrl.write(0, pstImageInfo[j].szUrl, 0, 260);
								 Pointer pszData = new Memory(pstImageInfo[j].udwSize);
								 netdevsdk.NETDEV_GetSystemPicture(lpUserID, pszUrl, pstImageInfo[j].udwSize, pszData);
								 Common.savePicture(pszData, pstImageInfo[j].udwSize, strFileName);
							}

						}
						else if(pstVehInfo[i].udwSmallPicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_MotorVeh_Small.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
						else if(pstVehInfo[i].udwPlatePicAttachIndex == pstImageInfo[j].udwIndex)
						{
							String strFileName = strPicturePath + strTime +"Struct_Plate_Small.jpg";
							Common.savePicture(pstImageInfo[j].pszData, pstImageInfo[j].udwSize, strFileName);
							continue;
						}
					}
				}
			}
				
		}
	}
    
    private NETDEV_PersonAlarmMessCallBack_PF personAlarmMessCB =new NETDEV_PersonAlarmMessCallBack_PF();
    private class NETDEV_PersonAlarmMessCallBack_PF implements NetDEVSDKLib.NETDEV_PersonAlarmMessCallBack_PF
    {

		@Override
		public void invoke(Pointer lpUserID, NETDEV_PERSON_EVENT_INFO_S pstAlarmData, Pointer lpUserData) {
			// TODO Auto-generated method stub
			String strTime = Common.getDate();
			System.out.println("Person AlarmTime:"+ pstAlarmData.udwTimestamp);
			for(int i = 0; i < pstAlarmData.udwFaceInfoNum; i++)
			{
				System.out.println("Person udwSimilarity:"+ pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.udwSimilarity);
				
				//布控的图片
				if(pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPersonInfo.udwImageNum > 0)
				{
					String strFileName = strPicturePath + strTime +"bukong.jpg";
					Common.savePicture(pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPersonInfo.stImageInfo[0].stFileInfo.pcData, pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPersonInfo.stImageInfo[0].stFileInfo.udwSize, strFileName);
				}
				
				// 大图，一体机获取的方法，ipc/NVR直接写数据 */
				{
					if(DeviceTypeComboBox.getSelectedIndex() == 1)
					{
						String strFileName = strPicturePath + strTime +"Person_Big.jpg";
						int iCmpRet = pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.szUrl.toString().compareTo("");
						if(iCmpRet != 0)
						{
							 Pointer pszUrl = new Memory(260);
							 pszUrl.write(0, pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.szUrl, 0, 260);
							 Pointer pszData = new Memory(pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.udwSize);
							 netdevsdk.NETDEV_GetSystemPicture(lpUserID, pszUrl, pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.udwSize, pszData);
							 
							 Common.savePicture(pszData, pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.udwSize, strFileName);
						}
						else
						{
							Common.savePicture(pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.pcData, pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stPanoImage.udwSize, strFileName);
						}
					}
				}
				
				//小图
				{
					String strFileName = strPicturePath + strTime +"Person_Small.jpg";
					Common.savePicture(pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stFaceImage.pcData, pstAlarmData.stCtrlFaceInfo[i].stCompareInfo.stFaceImage.udwSize, strFileName);
				}
			}
		}
    	
    }
    
    private NETDEV_VehicleAlarmMessCallBack_PF pfVehicleAlarmMessCB =new NETDEV_VehicleAlarmMessCallBack_PF();
    static JTable PersonTable;
    static JTable PersonMonitorTable;
    private JTextField textPersonAlarmBeginTime;
    private JTextField textPersonAlarmEndTime;
    private JTextField textPersonPassBegionTime;
    private JTextField textPersonPassEndTime;
    private JTextField textPersonPassAlarmSource;
    static JTable VehicleTable;
    static JTable VehicleMonitorTable;
    private JTextField VehiclePassRecordBeginTimeTextField;
    private JTextField VehiclePassRecordEndTimeTextField;
    private JTextField VehicleRecordVayonetNameTextField;
    private JTextField VehicleRecordPlateNumberTextField;
    private JTextField VehicleAlarmFindBeginTimeTextField;
    private JTextField VehicleAlarmEndTimetextField;
    private JTextField VehicleAlarmBayonetName;
    private JTextField textFieldVehicleAlarmPlateNumber;
    private JTextField textFieldBasicTime;
    private JTextField textFieldConfigDeviceName;
    private JTable tableConfigBasicHardDisk;
    private JTextField textFieldConfigNetWorkIPAddr;
    private JTextField textFieldConfigNetWorkSubMask;
    private JTextField textFieldConfigNetWorkGateway;
    private JTextField textFieldConfigNetWorkMTU;
    private JTextField textFieldNetworkPortHTTP;
    private JTextField textFieldNetworkPortHTTPS;
    private JTextField textFieldNetworkPortRTSP;
    private JTextField textFieldConfigNetWorkNTPServerIP;
    private JTextField textFieldConfigNetWorkNTPDomainName;
    private JTextField textFieldConfigNetWorkNTPPort;
    private JTextField textFieldConfigNetWorkNTPSynchronizeInterval;
    private JTextField textFieldConfigVideoStreamResolutionWidth;
    private JTextField textFieldConfigVideoStreamResolutionHigth;
    private JTextField textFieldConfigVideoStreamBitRate;
    private JTextField textFieldConfigVideoStreamFrameRate;
    private JTextField textFieldConfigVideoStreamIFremeInterval;
    private JTextField textFieldConfigImageInfoBrightness;
    private JTextField textFieldConfigImageInfoSaturation;
    private JTextField textFieldConfigImageInfoContrast;
    private JTextField textFieldConfigImageInfoSharpness;
    private JTextField textFieldConfigImageExposureMode;
    private JTextField textFieldConfigImageExposureCompensation;
    private JTextField textFieldConfigImageExposureMinShutter;
    private JTextField textFieldConfigImageExposureShutter;
    private JTextField textFieldConfigImageExposureDayNightSence;
    private JTextField textFieldlConfigImageExposureMaxShutter;
    private JTextField textFieldConfigImageExposureGain;
    private JTextField textFieldConfigImageExposureDayNightMode;
    private JTextField textFieldConfigImageExposureMinGain;
    private JTextField textFieldConfigImageExposureIsEnableSlowSh;
    private JTextField textFieldConfigImageExposureDayNightSwitch;
    private JTextField textFieldConfigImageExposureMaxGain;
    private JTextField textFieldConfigImageExposureSioweShutter;
    private JTextField textFieldConfigImageExposureWDR;
    private JTextField textFieldConfigImageExposureWDRLevel;
    private JTextField textFieldConfigOSDName;
    private JTextField textFieldConfigOSDText1;
    private JTextField textFieldConfigOSDText2;
    private JTextField textFieldConfigOSDText3;
    private JTextField textFieldConfigOSDText4;
    private JTextField textFieldConfigOSDText5;
    private JTextField textFieldConfigOSDText6;
    private JTextField textFieldConfigOSDTimeX;
    private JTextField textFieldConfigOSDNameX;
    private JTextField textFieldConfigOSDText1X;
    private JTextField textFieldConfigOSDText2X;
    private JTextField textFieldConfigOSDText3X;
    private JTextField textFieldConfigOSDText4X;
    private JTextField textFieldConfigOSDText5X;
    private JTextField textFieldConfigOSDText6X;
    private JTextField textFieldConfigOSDTimeY;
    private JTextField textFieldConfigOSDNameY;
    private JTextField textFieldConfigOSDText1Y;
    private JTextField textFieldConfigOSDText2Y;
    private JTextField textFieldConfigOSDText3Y;
    private JTextField textFieldConfigOSDText4Y;
    private JTextField textFieldConfigOSDText5Y;
    private JTextField textFieldConfigOSDText6Y;
    private JTextField textFieldConfigTemperSensitivity;
    private JTextField textFieldConfigMotionSensitivity;
    private JTextField textFieldConfigMotionObjectSize;
    private JTextField textFieldConfigMotionHistory;
    private JTable tableConfigPrivacyMask;
    private JTable tableConfigIOAlarmInput;
    private JTextField textFieldlblConfigIOAlarmOutputName;
    private JTextField textFieldConfigIOAlarmOutputChannelID;
    private JTextField textFieldlblConfigIOAlarmOutputDelay;
    private JTextField textFieldConfigIOAlarmOutputChannelNo;
    private JTextField textFieldConfigIOAlarmOutputChannelDelay;
    private JTextField textFieldConfigIOAlarmOutputChannelDefaultStatus;
    private class NETDEV_VehicleAlarmMessCallBack_PF implements NetDEVSDKLib.NETDEV_VehicleAlarmMessCallBack_PF
    {
		@Override
		public void invoke(Pointer lpUserID, NETDEV_VEH_RECOGNITION_EVENT_S pstVehicleAlarmInfo, Pointer lpBuf,
				int dwBufLen, Pointer lpUserData) {
			// TODO Auto-generated method stub
			System.out.println("NETDEV_VehicleAlarmMessCallBack_PF");	
			String strTime = Common.getDate();
			NETDEV_VEHICLE_RECORD_INFO_S[] pstVehicleRecordInfo = Common.pointerToStructureArray(pstVehicleAlarmInfo.stVehicleEventInfo.udwVehicleInfoNum, pstVehicleAlarmInfo.stVehicleEventInfo.pstVehicleRecordInfo, NETDEV_VEHICLE_RECORD_INFO_S.class);
			for(int i = 0; i < pstVehicleAlarmInfo.stVehicleEventInfo.udwVehicleInfoNum; i++)
			{
	            /* 车牌抓拍图片 */
	            {
	                if(pstVehicleRecordInfo[i].stPlateImage.pcData != null)
	                {
	                	String strFileName = strPicturePath + strTime +"Plate.jpg";
	                	Common.savePicture(pstVehicleRecordInfo[i].stPlateImage.pcData, pstVehicleRecordInfo[i].stPlateImage.udwSize, strFileName);
	                }
	            }
	            
	            /* 车辆抓拍图片 */
	            {
	                if(pstVehicleRecordInfo[i].stVehicleImage.pcData != null)
	                {
	                	String strFileName = strPicturePath + strTime +"Vehicle.jpg";
	                	Common.savePicture(pstVehicleRecordInfo[i].stVehicleImage.pcData, pstVehicleRecordInfo[i].stVehicleImage.udwSize, strFileName);
	                }
	            }
	            
	            /* 全景图 */
	            {
	            	NETDEV_FILE_INFO_S stFileInfo = new NETDEV_FILE_INFO_S();
	                stFileInfo.udwSize = 1048576;
	                stFileInfo.pcData = new Memory(stFileInfo.udwSize);
	                boolean iRet= netdevsdk.NETDEV_GetVehicleRecordImageInfo(lpUserID, pstVehicleRecordInfo[i].udwRecordID, stFileInfo);
	                if(iRet == true)
	                {
		            	String strFileName = strPicturePath + strTime +"VehiclePanoImage.jpg";
		            	Common.savePicture(stFileInfo.pcData, stFileInfo.udwSize, strFileName);
	                }
	            }
			}
		}
    	
    }
    
    private NETDEV_AlarmMessFGCallBack_PF pfAlarmMessFGCB =new NETDEV_AlarmMessFGCallBack_PF();

    private JTextField textFieldConfigIOAlarmOutputIndex;
    private JTable AccessControlPersonTable;
    private JTextField deptNameTextField;
    private JTextField addDeptNameTextField;
    private JTable AccessControlPermissionTable;
    private JTable tablePeopleCountingIPC;
    private JTable tablePeopleCountingVMSNVRRealTime;
    private JTextField textFieldPeopleCountingVMSNVRStatisticsBeginTime;
    private JTextField textFieldPeopleCountingVMSNVRStattisticsEndTime;
    private JTable tablePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType;
    private JTextField textFieldMaintenanceManualConfigurationImportConfiguration;
    private JTextField textFieldMaintenanceUpgradeBrowse;
    private JTable tableUserList;
    private JTextField textFieldAddUserUserName;
    private JTextField textFieldlAddUserPasswd;
    private JTextField textFieldModifyUserUserName;
    private JTextField textFieldModifyUserOldPassword;
    private JTextField textFieldlblModifyUserNewPasswd;
    private JTextField textFieldPTZExtendRoutePatrolsName;
    private JTextField textFieldPTZExtendPresetPatrolsName;
    private JTable tablePTZExtendPresetPatrols;
    private JTextField textFieldExtendPTZStayTime;
    private JTextField textFieldPTZExtendPresetPatrolsSpeed;
    private JTable tablePTZBasePreset;

    private class NETDEV_AlarmMessFGCallBack_PF implements NetDEVSDKLib.NETDEV_AlarmMessFGCallBack_PF
    {
		@Override
		public void invoke(Pointer lpUserID, NETDEV_PERSON_VERIFICATION_S pstAlarmData, Pointer lpUserData) {
			// TODO Auto-generated method stub
			System.out.println("NETDEV_AlarmMessFGCallBack_PF");
			
			/* 人脸信息 */
			NETDEV_CTRL_FACE_INFO_S[] pstFaceInfo = Common.pointerToStructureArray(pstAlarmData.udwFaceInfoNum, pstAlarmData.pstCtrlFaceInfo, NETDEV_CTRL_FACE_INFO_S.class);
			for(int i = 0; i < pstAlarmData.udwFaceInfoNum; i++)
		    {
				NETDEV_FEATURE_INFO_S[] pstFeatureInfo = Common.pointerToStructureArray(pstFaceInfo[i].udwFeatureNum, pstFaceInfo[i].pstFeatureInfo, NETDEV_FEATURE_INFO_S.class);
				for(int j = 0; j < pstFaceInfo[i].udwFeatureNum; j++)
			    {
					 System.out.println("FeatureVersion:" + pstFeatureInfo[j].szFeatureVersion + " Feature:" + pstFeatureInfo[j].szFeature);
			    }
				
				if(pstFaceInfo[i].stPanoImage.pcData != null && pstFaceInfo[i].stPanoImage.udwSize > 0)
				{
					String strFileName = strPicturePath + pstFaceInfo[i].udwTimestamp + "_" + pstFaceInfo[i].udwID + "_Pano.jpg";
					Common.savePicture(pstFaceInfo[i].stPanoImage.pcData, pstFaceInfo[i].stPanoImage.udwSize, strFileName);
					
					System.out.println(strFileName);	
				}
				if(pstFaceInfo[i].stFaceImage.pcData != null && pstFaceInfo[i].stFaceImage.udwSize > 0)
				{
					String strFileName = strPicturePath + pstFaceInfo[i].udwTimestamp + "_" + pstFaceInfo[i].udwID + "_face.jpg";
					Common.savePicture(pstFaceInfo[i].stFaceImage.pcData, pstFaceInfo[i].stFaceImage.udwSize, strFileName);
					
					System.out.println(strFileName);
				}
		    }
			
			/* 卡信息 */
			NETDEV_CTRL_CARD_INFO_S[] pstCardInfo = Common.pointerToStructureArray(pstAlarmData.udwCardInfoNum, pstAlarmData.pstCtrlCardInfo, NETDEV_CTRL_CARD_INFO_S.class);
			
			for(int i = 0; i < pstAlarmData.udwCardInfoNum; i++)
		    {
				
				System.out.println("cardInfo:" + pstCardInfo[i].udwTimestamp + pstCardInfo[i].udwID + "_" + pstCardInfo[i].udwCardType);
				if(pstCardInfo[i].stIDImage.pcData != null && pstCardInfo[i].stIDImage.udwSize > 0)
				{
					String strFileName = strPicturePath + pstCardInfo[i].udwTimestamp + "_" + pstCardInfo[i].udwID + "_Card.jpg";
					Common.savePicture(pstCardInfo[i].stIDImage.pcData, pstCardInfo[i].stIDImage.udwSize, strFileName);
					
					System.out.println(strFileName);
				}
		    }
			
			/* 闸机信息 */
			NETDEV_CTRL_GATE_INFO_S[] pstGateInfo = Common.pointerToStructureArray(pstAlarmData.udwGateInfoNum, pstAlarmData.pstCtrlGateInfo, NETDEV_CTRL_GATE_INFO_S.class);
			
			for(int i = 0; i < pstAlarmData.udwGateInfoNum; i++)
		    {
				String strTime = Common.timeStamp2Date(String.valueOf(pstGateInfo[i].udwTimestamp), null);
				System.out.println("GateInfo:" + strTime + pstGateInfo[i].udwID + "_" + pstGateInfo[i].udwInPersonCnt + "_" + pstGateInfo[i].udwOutPersonCnt);
		    }
			
			/* 库比对信息 */
			NETDEV_CTRL_LIB_MATCH_INFO_S[] pstLibMatchInfo = Common.pointerToStructureArray(pstAlarmData.udwLibMatInfoNum, pstAlarmData.pstLibMatchInfo, NETDEV_CTRL_LIB_MATCH_INFO_S.class);
			
			for(int i = 0; i < pstAlarmData.udwLibMatInfoNum; i++)
		    {
				System.out.println("LibMatchInfo: udwID:" + pstLibMatchInfo[i].udwID + "_LibID:" + 
		                            pstLibMatchInfo[i].udwLibID + "_MatchStatus:" + pstLibMatchInfo[i].udwMatchStatus + "_MatchPersonID:" +
		                            pstLibMatchInfo[i].udwMatchPersonID + "_MatchFaceID:" + pstLibMatchInfo[i].udwMatchFaceID );
		    }
		}
    }
    
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1354, 835);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabFunList = new JTabbedPane(JTabbedPane.TOP);
		tabFunList.setBounds(0, 279, 623, 510);
		frame.getContentPane().add(tabFunList);
		
		JPanel LiveViewPanel = new JPanel();
		tabFunList.addTab("LiveView", null, LiveViewPanel, "LiveView");
		LiveViewPanel.setLayout(null);
		
		JPanel LivePanel = new JPanel();
		LivePanel.setBorder(BorderFactory.createTitledBorder("Live"));
		LivePanel.setBounds(32, 10, 240, 66);
		LiveViewPanel.add(LivePanel);
		LivePanel.setLayout(null);
		
		DeviceTypeComboBox.setBounds(78, 201, 80, 21);
		frame.getContentPane().add(DeviceTypeComboBox);
		DeviceTypeComboBox.addItem("IPC/NVR");
		DeviceTypeComboBox.addItem("VMS");
		
		JButton btnStartLive = new JButton("Start");
		btnStartLive.setBounds(21, 22, 90, 23);
		LivePanel.add(btnStartLive);
		
		JButton btnStopLive = new JButton("Stop");
		btnStopLive.setEnabled(false);
		btnStopLive.setBounds(130, 22, 90, 23);
		LivePanel.add(btnStopLive);
		
		JPanel RecordPanel = new JPanel();
		RecordPanel.setBorder(BorderFactory.createTitledBorder("Record"));
		RecordPanel.setBounds(32, 110, 240, 66);
		LiveViewPanel.add(RecordPanel);
		RecordPanel.setLayout(null);
		
		JButton btnStartRecord = new JButton("Start");
		btnStartRecord.setBounds(21, 22, 90, 23);
		RecordPanel.add(btnStartRecord);
		
		JButton btnStopRecord = new JButton("Stop");
		btnStopRecord.setEnabled(false);
		btnStopRecord.setBounds(131, 22, 90, 23);
		RecordPanel.add(btnStopRecord);
		
		JPanel TalkPanel = new JPanel();
		TalkPanel.setBorder(BorderFactory.createTitledBorder("Talk"));
		TalkPanel.setBounds(330, 10, 250, 66);
		LiveViewPanel.add(TalkPanel);
		TalkPanel.setLayout(null);
		
		JButton btnStopTalk = new JButton("Stop");
		btnStopTalk.setEnabled(false);
		btnStopTalk.setBounds(131, 25, 90, 23);
		TalkPanel.add(btnStopTalk);
		
		JButton btnStartTalk = new JButton("Start");
		btnStartTalk.setBounds(21, 25, 90, 23);
		TalkPanel.add(btnStartTalk);
		
		JPanel OtherPanel = new JPanel();
		OtherPanel.setBorder(BorderFactory.createTitledBorder("Other"));
		OtherPanel.setBounds(330, 110, 250, 66);
		LiveViewPanel.add(OtherPanel);
		OtherPanel.setLayout(null);
		
		JButton btnCapture = new JButton("Capture");
		btnCapture.setBounds(21, 22, 88, 23);
		OtherPanel.add(btnCapture);
		
		JButton btnMakeI = new JButton("Make I");
		btnMakeI.setBounds(130, 22, 93, 23);
		OtherPanel.add(btnMakeI);
		
		JPanel SourceDataPanel = new JPanel();
		SourceDataPanel.setBorder(BorderFactory.createTitledBorder("RTP+ES"));
		SourceDataPanel.setBounds(32, 230, 240, 66);
		LiveViewPanel.add(SourceDataPanel);
		SourceDataPanel.setLayout(null);
		
		JButton btnStartSourceData = new JButton("Start");
		btnStartSourceData.setBounds(21, 22, 90, 23);
		SourceDataPanel.add(btnStartSourceData);
		
		JButton btnStopSourceData = new JButton("Stop");
		btnStopSourceData.setEnabled(false);
		btnStopSourceData.setBounds(128, 22, 90, 23);
		SourceDataPanel.add(btnStopSourceData);
		
		JPanel DecodeDataPanel = new JPanel();
		DecodeDataPanel.setBorder(BorderFactory.createTitledBorder("YUV"));
		DecodeDataPanel.setBounds(330, 230, 250, 66);
		LiveViewPanel.add(DecodeDataPanel);
		DecodeDataPanel.setLayout(null);
		
		JButton btnStartDecodeData = new JButton("Start");
		btnStartDecodeData.setBounds(24, 21, 90, 23);
		DecodeDataPanel.add(btnStartDecodeData);
		
		JButton btnStopDecodeData = new JButton("Stop");
		btnStopDecodeData.setEnabled(false);
		btnStopDecodeData.setBounds(134, 21, 90, 23);
		DecodeDataPanel.add(btnStopDecodeData);
		
		JPanel PTZPanel = new JPanel();
		tabFunList.addTab("PTZ", null, PTZPanel, null);
		PTZPanel.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 618, 492);
		PTZPanel.add(tabbedPane_1);
		
		JPanel PTZBasePanel = new JPanel();
		tabbedPane_1.addTab("BasicPTZ", null, PTZBasePanel, null);
		PTZBasePanel.setBorder(BorderFactory.createTitledBorder(""));
		PTZBasePanel.setLayout(null);
		
		JPanel panelPTZBaseControl = new JPanel();
		panelPTZBaseControl.setBorder(new TitledBorder(null, "Base PTZ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPTZBaseControl.setBounds(10, 10, 593, 146);
		PTZBasePanel.add(panelPTZBaseControl);
		panelPTZBaseControl.setLayout(null);
		
		JComboBox<String> PTZSpeedComboBox = new JComboBox<String>();
		PTZSpeedComboBox.setBounds(228, 24, 79, 21);
		panelPTZBaseControl.add(PTZSpeedComboBox);
		PTZSpeedComboBox.addItem("Speed:1");
		PTZSpeedComboBox.addItem("Speed:2");
		PTZSpeedComboBox.addItem("Speed:3");
		PTZSpeedComboBox.addItem("Speed:4");
		PTZSpeedComboBox.addItem("Speed:5");
		PTZSpeedComboBox.addItem("Speed:6");
		PTZSpeedComboBox.addItem("Speed:7");
		PTZSpeedComboBox.addItem("Speed:8");
		PTZSpeedComboBox.addItem("Speed:9");
		PTZSpeedComboBox.setSelectedIndex(4);
		
		JButton btnUp = new JButton("Up");
		btnUp.setBounds(58, 23, 79, 23);
		panelPTZBaseControl.add(btnUp);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.setBounds(10, 64, 79, 23);
		panelPTZBaseControl.add(btnLeft);
		
		JButton btnRight = new JButton("Right");
		btnRight.setBounds(114, 64, 79, 23);
		panelPTZBaseControl.add(btnRight);
		
		JButton btnDown = new JButton("Down");
		btnDown.setBounds(58, 100, 79, 23);
		panelPTZBaseControl.add(btnDown);
		
		JButton btnZoomNear = new JButton("Zoom+");
		btnZoomNear.setBounds(359, 23, 93, 23);
		panelPTZBaseControl.add(btnZoomNear);
		
		JButton btnZoomFar = new JButton("Zoom-");
		btnZoomFar.setBounds(359, 100, 93, 23);
		panelPTZBaseControl.add(btnZoomFar);
		
		JButton btnFocusFar = new JButton("Focus+");
		btnFocusFar.setBounds(500, 23, 93, 23);
		panelPTZBaseControl.add(btnFocusFar);
		
		JButton btnFocusNear = new JButton("Focus-");
		btnFocusNear.setBounds(500, 100, 93, 23);
		panelPTZBaseControl.add(btnFocusNear);
		
		JPanel panelPTZBasePreset = new JPanel();
		panelPTZBasePreset.setBorder(new TitledBorder(null, "Preset", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPTZBasePreset.setBounds(10, 166, 593, 186);
		PTZBasePanel.add(panelPTZBasePreset);
		panelPTZBasePreset.setLayout(null);
		
		JButton btnNewButtonBasePTZPresetGet = new JButton("Get");
		btnNewButtonBasePTZPresetGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				PTZBasePresetModel.setRowCount(0);
				
				NETDEV_PTZ_ALLPRESETS_S stPtzPresets = new NETDEV_PTZ_ALLPRESETS_S();
				
				boolean bRet = netdevsdk.NETDEV_GetPTZPresetList(lpUserID, ChannelID, stPtzPresets);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetPTZPresetList failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                else
                {	
                	for(int i = 0; i < stPtzPresets.dwSize; i++)
                	{
						Vector<String> vector = new Vector<String>();
				        vector.add(String.valueOf(stPtzPresets.astPreset[i].dwPresetID));
				        vector.add(Common.byteArrayToString(stPtzPresets.astPreset[i].szPresetName));
						
				        PTZBasePresetModel.insertRow(i,vector);
                	}
				}
			}
		});
		btnNewButtonBasePTZPresetGet.setBounds(20, 150, 68, 23);
		panelPTZBasePreset.add(btnNewButtonBasePTZPresetGet);
		
		JButton btnNewButtonBasePTZPresetGoTo = new JButton("Go to");
		btnNewButtonBasePTZPresetGoTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				if(0 == PTZBasePresetModel.getRowCount()  || tablePTZBasePreset.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find Preset or seletc Preset first.");
					return;
				}
				
				String strPresetID = (String) tablePTZBasePreset.getValueAt(tablePTZBasePreset.getSelectedRow(), 0);
				
				
				String strPresetNameString = "";
				boolean bRet = netdevsdk.NETDEV_PTZPreset_Other(lpUserID, ChannelID, NETDEV_PTZ_PRESETCMD_E.NETDEV_PTZ_GOTO_PRESET, strPresetNameString, Integer.valueOf(strPresetID));
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZPreset_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnNewButtonBasePTZPresetGoTo.setBounds(114, 150, 68, 23);
		panelPTZBasePreset.add(btnNewButtonBasePTZPresetGoTo);
		
		JButton btnNewButtonBasePTZPresetSet = new JButton("Set");
		btnNewButtonBasePTZPresetSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
			   
				new Preset();
			}
		});
		btnNewButtonBasePTZPresetSet.setBounds(208, 150, 75, 23);
		panelPTZBasePreset.add(btnNewButtonBasePTZPresetSet);
		
		JButton btnNewButtonBasePTZPresetDelete = new JButton("Delete");
		btnNewButtonBasePTZPresetDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				if(0 == PTZBasePresetModel.getRowCount()  || tablePTZBasePreset.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find Preset or seletc Preset first.");
					return;
				}
				
				String strPresetID = (String) tablePTZBasePreset.getValueAt(tablePTZBasePreset.getSelectedRow(), 0);
				
				String strPresetNameString = "";
				boolean bRet = netdevsdk.NETDEV_PTZPreset_Other(lpUserID, ChannelID, NETDEV_PTZ_PRESETCMD_E.NETDEV_PTZ_CLE_PRESET, strPresetNameString, Integer.valueOf(strPresetID));
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZPreset_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnNewButtonBasePTZPresetDelete.setBounds(302, 150, 75, 23);
		panelPTZBasePreset.add(btnNewButtonBasePTZPresetDelete);
		
		JScrollPane scrollPanePTZBasePreset = new JScrollPane();
		scrollPanePTZBasePreset.setBounds(20, 21, 563, 119);
		panelPTZBasePreset.add(scrollPanePTZBasePreset);
		
		tablePTZBasePreset = new JTable();
		tablePTZBasePreset.setModel(PTZBasePresetModel);
		scrollPanePTZBasePreset.setViewportView(tablePTZBasePreset);
		
		btnFocusNear.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_FOCUSNEAR, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_FOCUSNEAR, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		btnFocusFar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnFocusFar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_FOCUSFAR, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_FOCUSFAR, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		
		btnZoomFar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ZOOMWIDE, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ZOOMWIDE, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		btnZoomNear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnZoomNear.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ZOOMTELE, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ZOOMTELE, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TILTDOWN, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_TILTDOWN, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		
		btnRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_PANRIGHT, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_PANRIGHT, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		
		btnLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_PANLEFT, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_PANLEFT, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TILTUP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_TILTUP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nSpeed = PTZSpeedComboBox.getSelectedIndex()+1;
				if(null == lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}else{
					boolean bRet = netdevsdk.NETDEV_PTZControl(lpPlayHandle, NETDEV_PTZ_E.NETDEV_PTZ_ALLSTOP, nSpeed);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Check if the corresponding device supports PTZ or if the selected channel is correct.");
					}
				}
			}
		});
		
		JPanel PTZExtendPanel = new JPanel();
		tabbedPane_1.addTab("PTZExtend", null, PTZExtendPanel, null);
		PTZExtendPanel.setLayout(null);
		
		JPanel PTZExtend1panel = new JPanel();
		PTZExtend1panel.setBorder(new LineBorder(new Color(0, 153, 255)));
		PTZExtend1panel.setBounds(10, 10, 593, 77);
		PTZExtendPanel.add(PTZExtend1panel);
		PTZExtend1panel.setLayout(null);
		
		JButton btnPTZExtendWiperON = new JButton("ON");
		btnPTZExtendWiperON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_BRUSHON, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendWiperON.setBounds(74, 5, 93, 23);
		PTZExtend1panel.add(btnPTZExtendWiperON);
		
		JLabel lblPTZExtendWiper = new JLabel("Wiper");
		lblPTZExtendWiper.setBounds(10, 9, 54, 15);
		PTZExtend1panel.add(lblPTZExtendWiper);
		
		JLabel lblPTZExtendHeater = new JLabel("Heater");
		lblPTZExtendHeater.setBounds(304, 9, 54, 15);
		PTZExtend1panel.add(lblPTZExtendHeater);
		
		JButton btnPTZExtendHeaterON = new JButton("ON");
		btnPTZExtendHeaterON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_HEATON, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendHeaterON.setBounds(387, 5, 93, 23);
		PTZExtend1panel.add(btnPTZExtendHeaterON);
		
		JButton btnPTZExtendHeaterOFF = new JButton("OFF");
		btnPTZExtendHeaterOFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_HEATOFF, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendHeaterOFF.setBounds(490, 5, 93, 23);
		PTZExtend1panel.add(btnPTZExtendHeaterOFF);
		
		JLabel lblPTZExtendLight = new JLabel("Light");
		lblPTZExtendLight.setBounds(10, 45, 54, 15);
		PTZExtend1panel.add(lblPTZExtendLight);
		
		JButton btnPTZExtendLightON = new JButton("ON");
		btnPTZExtendLightON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_LIGHTON, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendLightON.setBounds(74, 41, 93, 23);
		PTZExtend1panel.add(btnPTZExtendLightON);
		
		JButton btnPTZExtendWiperOFF = new JButton("OFF");
		btnPTZExtendWiperOFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_BRUSHOFF, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendWiperOFF.setBounds(188, 5, 93, 23);
		PTZExtend1panel.add(btnPTZExtendWiperOFF);
		
		JButton btnPTZExtendLightOFF = new JButton("OFF");
		btnPTZExtendLightOFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_LIGHTOFF, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendLightOFF.setBounds(188, 41, 93, 23);
		PTZExtend1panel.add(btnPTZExtendLightOFF);
		
		JLabel lblPTZExtendSnowRemoval = new JLabel("Snow Removal");
		lblPTZExtendSnowRemoval.setBounds(304, 45, 79, 15);
		PTZExtend1panel.add(lblPTZExtendSnowRemoval);
		
		JButton btnPTZExtendSnowRemovalON = new JButton("ON");
		btnPTZExtendSnowRemovalON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_PTZControl_Other(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_SNOWREMOINGON, PTZSpeedComboBox.getSelectedIndex()+1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZControl_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendSnowRemovalON.setBounds(387, 38, 93, 23);
		PTZExtend1panel.add(btnPTZExtendSnowRemovalON);
		
		JButton btnPTZExtendSnowRemovalOFF = new JButton("OFF");
		btnPTZExtendSnowRemovalOFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PTZ_TRACK_INFO_S stTrackCruiseInfo = new NETDEV_PTZ_TRACK_INFO_S();
				
				boolean bRet = netdevsdk.NETDEV_PTZGetTrackCruise(lpUserID, ChannelID, stTrackCruiseInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZGetTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
			}
		});
		btnPTZExtendSnowRemovalOFF.setBounds(490, 38, 93, 23);
		PTZExtend1panel.add(btnPTZExtendSnowRemovalOFF);
		
		JPanel PTZExtendRoutePatrolsPanel = new JPanel();
		PTZExtendRoutePatrolsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Route Patrols", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		PTZExtendRoutePatrolsPanel.setBounds(10, 97, 593, 77);
		PTZExtendPanel.add(PTZExtendRoutePatrolsPanel);
		PTZExtendRoutePatrolsPanel.setLayout(null);
		
		JLabel lblPTZExtendRoutePatrolsRecord = new JLabel("Record");
		lblPTZExtendRoutePatrolsRecord.setBounds(10, 47, 54, 15);
		PTZExtendRoutePatrolsPanel.add(lblPTZExtendRoutePatrolsRecord);
		
		JLabel lblPTZExtendRoutePatrolsName = new JLabel("Nane");
		lblPTZExtendRoutePatrolsName.setBounds(10, 22, 54, 15);
		PTZExtendRoutePatrolsPanel.add(lblPTZExtendRoutePatrolsName);
		
		textFieldPTZExtendRoutePatrolsName = new JTextField();
		textFieldPTZExtendRoutePatrolsName.setBounds(74, 19, 96, 21);
		PTZExtendRoutePatrolsPanel.add(textFieldPTZExtendRoutePatrolsName);
		textFieldPTZExtendRoutePatrolsName.setColumns(10);
		
		JButton btnPTZExtendRoutePatrolsGet = new JButton("Get");
		btnPTZExtendRoutePatrolsGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PTZ_TRACK_INFO_S stTrackCruiseInfo = new NETDEV_PTZ_TRACK_INFO_S();
				
				boolean bRet = netdevsdk.NETDEV_PTZGetTrackCruise(lpUserID, ChannelID, stTrackCruiseInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZGetTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
                textFieldPTZExtendRoutePatrolsName.setText(Common.byteArrayToString(stTrackCruiseInfo.aszTrackName));
			}
		});
		btnPTZExtendRoutePatrolsGet.setBounds(223, 18, 93, 23);
		PTZExtendRoutePatrolsPanel.add(btnPTZExtendRoutePatrolsGet);
		
		JButton btnPTZExtendRoutePatrolsRun = new JButton("Run");
		btnPTZExtendRoutePatrolsRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PTZ_TRACK_INFO_S stTrackCruiseInfo = new NETDEV_PTZ_TRACK_INFO_S();
				
				boolean bRet = netdevsdk.NETDEV_PTZGetTrackCruise(lpUserID, ChannelID, stTrackCruiseInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZGetTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
                bRet = netdevsdk.NETDEV_PTZTrackCruise(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TRACKCRUISE, Common.byteArrayToString(stTrackCruiseInfo.aszTrackName));
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }

			}
		});
		btnPTZExtendRoutePatrolsRun.setBounds(339, 18, 93, 23);
		PTZExtendRoutePatrolsPanel.add(btnPTZExtendRoutePatrolsRun);
		
		JButton btnPTZExtendRoutePatrolsStop = new JButton("Stop");
		btnPTZExtendRoutePatrolsStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PTZ_TRACK_INFO_S stTrackCruiseInfo = new NETDEV_PTZ_TRACK_INFO_S();
				
				boolean bRet = netdevsdk.NETDEV_PTZGetTrackCruise(lpUserID, ChannelID, stTrackCruiseInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZGetTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
                bRet = netdevsdk.NETDEV_PTZTrackCruise(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TRACKCRUISESTOP, Common.byteArrayToString(stTrackCruiseInfo.aszTrackName));
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendRoutePatrolsStop.setBounds(456, 18, 93, 23);
		PTZExtendRoutePatrolsPanel.add(btnPTZExtendRoutePatrolsStop);
		
		JButton btnPTZExtendRoutePatrolsStart = new JButton("Start");
		btnPTZExtendRoutePatrolsStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PTZ_TRACK_INFO_S stTrackCruiseInfo = new NETDEV_PTZ_TRACK_INFO_S();
				
				boolean bRet = netdevsdk.NETDEV_PTZGetTrackCruise(lpUserID, ChannelID, stTrackCruiseInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZGetTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
                if(stTrackCruiseInfo.dwTrackNum == 0)
                {
                	/* Use PTZ to record route */
                	bRet = netdevsdk.NETDEV_PTZTrackCruise(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TRACKCRUISEADD, Common.byteArrayToString(stTrackCruiseInfo.aszTrackName));
                    if(bRet != true)
                    {
                    	System.out.printf("NETDEV_PTZTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                        return;
                    }

                    stTrackCruiseInfo.dwTrackNum++;
                }
                
                
                bRet = netdevsdk.NETDEV_PTZTrackCruise(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TRACKCRUISEREC, Common.byteArrayToString(stTrackCruiseInfo.aszTrackName));
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendRoutePatrolsStart.setBounds(74, 43, 93, 23);
		PTZExtendRoutePatrolsPanel.add(btnPTZExtendRoutePatrolsStart);
		
		JButton btnbtnPTZExtendRoutePatrolsStartStop = new JButton("Stop");
		btnbtnPTZExtendRoutePatrolsStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PTZ_TRACK_INFO_S stTrackCruiseInfo = new NETDEV_PTZ_TRACK_INFO_S();

			    boolean bRet = netdevsdk.NETDEV_PTZGetTrackCruise(lpUserID, ChannelID, stTrackCruiseInfo);
			    if(false == bRet)
			    {
                	System.out.printf("NETDEV_PTZGetTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
			    }

			    bRet = netdevsdk.NETDEV_PTZTrackCruise(lpUserID, ChannelID, NETDEV_PTZ_E.NETDEV_PTZ_TRACKCRUISERECSTOP, Common.byteArrayToString(stTrackCruiseInfo.aszTrackName));
			    if(false == bRet)
			    {
                	System.out.printf("NETDEV_PTZTrackCruise failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
			    }
			}
		});
		btnbtnPTZExtendRoutePatrolsStartStop.setBounds(177, 43, 93, 23);
		PTZExtendRoutePatrolsPanel.add(btnbtnPTZExtendRoutePatrolsStartStop);
		
		JPanel PTZExtendPresetPatrolspanel = new JPanel();
		PTZExtendPresetPatrolspanel.setBorder(new TitledBorder(null, "Preset Patrols", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PTZExtendPresetPatrolspanel.setBounds(10, 184, 593, 269);
		PTZExtendPanel.add(PTZExtendPresetPatrolspanel);
		PTZExtendPresetPatrolspanel.setLayout(null);
		
		JLabel lblPTZExtendPresetPatrolsID = new JLabel("ID");
		lblPTZExtendPresetPatrolsID.setBounds(10, 24, 35, 15);
		PTZExtendPresetPatrolspanel.add(lblPTZExtendPresetPatrolsID);
		
		JComboBox<String> comboBoxPTZExtendPresetPatrolsID = new JComboBox<String>();
		comboBoxPTZExtendPresetPatrolsID.setBounds(39, 21, 54, 21);
		PTZExtendPresetPatrolspanel.add(comboBoxPTZExtendPresetPatrolsID);
		
		JLabel lblPTZExtendPresetPatrolsName = new JLabel("Name");
		lblPTZExtendPresetPatrolsName.setBounds(114, 24, 42, 15);
		PTZExtendPresetPatrolspanel.add(lblPTZExtendPresetPatrolsName);
		
		textFieldPTZExtendPresetPatrolsName = new JTextField();
		textFieldPTZExtendPresetPatrolsName.setBounds(150, 21, 80, 21);
		PTZExtendPresetPatrolspanel.add(textFieldPTZExtendPresetPatrolsName);
		textFieldPTZExtendPresetPatrolsName.setColumns(10);
		
		JButton btnPTZExtendPresetPatrolsStart = new JButton("Start");
		btnPTZExtendPresetPatrolsStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_CRUISE_INFO_S strCruiseInfo = new NETDEV_CRUISE_INFO_S();
				strCruiseInfo.dwCuriseID = Integer.valueOf((String) comboBoxPTZExtendPresetPatrolsID.getSelectedItem());
				
				boolean bRet = netdevsdk.NETDEV_PTZCruise_Other(lpUserID, ChannelID, NETDEV_PTZ_CRUISECMD_E.NETDEV_PTZ_RUN_CRUISE,strCruiseInfo);
			    if(false == bRet)
			    {
                	System.out.printf("NETDEV_PTZCruise_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
			    }
			}
		});
		btnPTZExtendPresetPatrolsStart.setBounds(259, 20, 65, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsStart);
		
		JButton btnPTZExtendPresetPatrolsStop = new JButton("Stop");
		btnPTZExtendPresetPatrolsStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_CRUISE_INFO_S strCruiseInfo = new NETDEV_CRUISE_INFO_S();
				strCruiseInfo.dwCuriseID = Integer.valueOf((String) comboBoxPTZExtendPresetPatrolsID.getSelectedItem());
				
				boolean bRet = netdevsdk.NETDEV_PTZCruise_Other(lpUserID, ChannelID, NETDEV_PTZ_CRUISECMD_E.NETDEV_PTZ_STOP_CRUISE,strCruiseInfo);
			    if(false == bRet)
			    {
                	System.out.printf("NETDEV_PTZCruise_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
			    }
			}
		});
		btnPTZExtendPresetPatrolsStop.setBounds(334, 20, 65, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsStop);
		
		JButton btnPTZExtendPresetPatrolsSave = new JButton("Save");
		btnPTZExtendPresetPatrolsSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				boolean bRet = false;
				
				/* 获取设备是否有预置位巡航，如果没有就是添加，如果有就是修改 */
				NETDEV_CRUISE_LIST_S stCuriseList = new NETDEV_CRUISE_LIST_S();
			    netdevsdk.NETDEV_PTZGetCruise(lpUserID, ChannelID, stCuriseList);
      
                int dwPresetPatrolsTableSize = tablePTZExtendPresetPatrolsModel.getRowCount();
                if(dwPresetPatrolsTableSize < 2)
                {
                	JOptionPane.showMessageDialog(null, "Please add more Patrols");
                	return;
                }
                
                NETDEV_CRUISE_INFO_S strCruiseInfo = new NETDEV_CRUISE_INFO_S();
                if(comboBoxPTZExtendPresetPatrolsID.getSelectedIndex() >= 0)
                {
                	strCruiseInfo.dwCuriseID = Integer.valueOf((String) comboBoxPTZExtendPresetPatrolsID.getSelectedItem());
                }
                else
                {
                	strCruiseInfo.dwCuriseID = 1;
				}
                
                Common.stringToByteArray(textFieldPTZExtendPresetPatrolsName.getText(), strCruiseInfo.szCuriseName);
                
                strCruiseInfo.dwSize = dwPresetPatrolsTableSize;
                for(int i = 0; i < dwPresetPatrolsTableSize; i++)
                {
                	strCruiseInfo.astCruisePoint[i] = new NETDEV_CRUISE_POINT_S();
                	strCruiseInfo.astCruisePoint[i].dwPresetID = Integer.valueOf((String) tablePTZExtendPresetPatrols.getValueAt(i, 0));
                	strCruiseInfo.astCruisePoint[i].dwStayTime = Integer.valueOf((String) tablePTZExtendPresetPatrols.getValueAt(i, 1));
                	strCruiseInfo.astCruisePoint[i].dwSpeed = Integer.valueOf((String) tablePTZExtendPresetPatrols.getValueAt(i, 2));
                }
                strCruiseInfo.write();

                if(stCuriseList.dwSize > 0)
                {
    				bRet = netdevsdk.NETDEV_PTZCruise_Other(lpUserID, ChannelID, NETDEV_PTZ_CRUISECMD_E.NETDEV_PTZ_MODIFY_CRUISE, strCruiseInfo);
                    if(bRet != true)
                    {
                    	System.out.printf("NETDEV_PTZCruise_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                        return;
                    }
                }
                else
                {
    				bRet = netdevsdk.NETDEV_PTZCruise_Other(lpUserID, ChannelID, NETDEV_PTZ_CRUISECMD_E.NETDEV_PTZ_ADD_CRUISE, strCruiseInfo);
                    if(bRet != true)
                    {
                    	System.out.printf("NETDEV_PTZCruise_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                        return;
                    }
				}
                
                
			}
		});
		btnPTZExtendPresetPatrolsSave.setBounds(409, 20, 64, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsSave);
		
		JButton btnPTZExtendPresetPatrolsDelete = new JButton("Delete");
		btnPTZExtendPresetPatrolsDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				NETDEV_CRUISE_INFO_S strCruiseInfo = new NETDEV_CRUISE_INFO_S();
				strCruiseInfo.dwCuriseID = Integer.valueOf((String) comboBoxPTZExtendPresetPatrolsID.getSelectedItem());
				
  				boolean bRet = netdevsdk.NETDEV_PTZCruise_Other(lpUserID, ChannelID, NETDEV_PTZ_CRUISECMD_E.NETDEV_PTZ_DEL_CRUISE, strCruiseInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_PTZCruise_Other failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnPTZExtendPresetPatrolsDelete.setBounds(483, 20, 69, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsDelete);
		
		JScrollPane scrollPanePTZExtendPresetPatrols = new JScrollPane();
		scrollPanePTZExtendPresetPatrols.setBounds(10, 55, 573, 132);
		PTZExtendPresetPatrolspanel.add(scrollPanePTZExtendPresetPatrols);
		
		tablePTZExtendPresetPatrols = new JTable();
		tablePTZExtendPresetPatrols.setModel(tablePTZExtendPresetPatrolsModel);
		scrollPanePTZExtendPresetPatrols.setViewportView(tablePTZExtendPresetPatrols);
		
		JLabel lblPTZExtendPresetPatrolsPresetID = new JLabel("Preset ID");
		lblPTZExtendPresetPatrolsPresetID.setBounds(10, 197, 62, 15);
		PTZExtendPresetPatrolspanel.add(lblPTZExtendPresetPatrolsPresetID);
		
		JComboBox<String> comboBoxExtendPTZPresetID = new JComboBox<String>();
		comboBoxExtendPTZPresetID.setBounds(82, 197, 65, 21);
		PTZExtendPresetPatrolspanel.add(comboBoxExtendPTZPresetID);
		
		JLabel lblPTZExtendPresetPatrolsStayTime = new JLabel("StayTime(s)");
		lblPTZExtendPresetPatrolsStayTime.setBounds(164, 197, 80, 15);
		PTZExtendPresetPatrolspanel.add(lblPTZExtendPresetPatrolsStayTime);
		
		textFieldExtendPTZStayTime = new JTextField();
		textFieldExtendPTZStayTime.setBounds(241, 194, 66, 21);
		PTZExtendPresetPatrolspanel.add(textFieldExtendPTZStayTime);
		textFieldExtendPTZStayTime.setColumns(10);
		
		JLabel lblPTZExtendPresetPatrolsSpeed = new JLabel("Speed(1-10)");
		lblPTZExtendPresetPatrolsSpeed.setBounds(11, 241, 69, 15);
		PTZExtendPresetPatrolspanel.add(lblPTZExtendPresetPatrolsSpeed);
		
		textFieldPTZExtendPresetPatrolsSpeed = new JTextField();
		textFieldPTZExtendPresetPatrolsSpeed.setBounds(81, 240, 66, 21);
		PTZExtendPresetPatrolspanel.add(textFieldPTZExtendPresetPatrolsSpeed);
		textFieldPTZExtendPresetPatrolsSpeed.setColumns(10);
		
		JButton btnPTZExtendPresetPatrolsAdd = new JButton("Add");
		btnPTZExtendPresetPatrolsAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<String> PresetPointVector = new Vector<String>();
				PresetPointVector.add((String)comboBoxExtendPTZPresetID.getSelectedItem());
				PresetPointVector.add(textFieldExtendPTZStayTime.getText());
				PresetPointVector.add(textFieldPTZExtendPresetPatrolsSpeed.getText());
				tablePTZExtendPresetPatrolsModel.addRow(PresetPointVector);
			}
		});
		btnPTZExtendPresetPatrolsAdd.setBounds(334, 193, 93, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsAdd);
		
		JButton btnPTZExtendPresetPatrolsDelete2 = new JButton("Delete");
		btnPTZExtendPresetPatrolsDelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(0 == tablePTZExtendPresetPatrolsModel.getRowCount()  || tablePTZExtendPresetPatrols.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "no row to delete");
					return;
				}
				tablePTZExtendPresetPatrolsModel.removeRow(tablePTZExtendPresetPatrols.getSelectedRow());
			}
		});
		btnPTZExtendPresetPatrolsDelete2.setBounds(334, 240, 93, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsDelete2);
		
		JButton btnPTZExtendPresetPatrolsRefresh = new JButton("Refresh");
		btnPTZExtendPresetPatrolsRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}

				comboBoxPTZExtendPresetPatrolsID.removeAllItems();
				textFieldPTZExtendPresetPatrolsName.setText("");
				
				NETDEV_CRUISE_LIST_S stCuriseList = new NETDEV_CRUISE_LIST_S();
			    boolean bRet = netdevsdk.NETDEV_PTZGetCruise(lpUserID, ChannelID, stCuriseList);
                if(bRet == true)
                {
                    /* 预置位巡航信息 */
                    comboBoxPTZExtendPresetPatrolsID.addItem(String.valueOf(stCuriseList.astCruiseInfo[0].dwCuriseID));
                    textFieldPTZExtendPresetPatrolsName.setText(Common.byteArrayToString(stCuriseList.astCruiseInfo[0].szCuriseName));
                }

                /* 获取预置位信息，供添加预置位巡航使用  */
        		comboBoxExtendPTZPresetID.removeAllItems();
        		NETDEV_PTZ_ALLPRESETS_S stPtzPresets = new NETDEV_PTZ_ALLPRESETS_S();
        	    bRet = netdevsdk.NETDEV_GetPTZPresetList(lpUserID, ChannelID, stPtzPresets);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetPTZPresetList failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                else 
                {
                    for(int i =0; i < stPtzPresets.dwSize; i++)
                    {
                    	comboBoxExtendPTZPresetID.addItem(String.valueOf(stPtzPresets.astPreset[i].dwPresetID));
                    }
				}
                
			}
		});
		
		btnPTZExtendPresetPatrolsRefresh.setBounds(472, 240, 93, 23);
		PTZExtendPresetPatrolspanel.add(btnPTZExtendPresetPatrolsRefresh);
		
		JPanel PlayBackPanel = new JPanel();
		tabFunList.addTab("PlayBack", null, PlayBackPanel, null);
		PlayBackPanel.setLayout(null);
		
		JScrollPane RecordScrollPane = new JScrollPane();
		RecordScrollPane.setEnabled(false);
		RecordScrollPane.setBounds(29, 48, 554, 105);
		PlayBackPanel.add(RecordScrollPane);
		
		DefaultTableModel RecordTableModel= new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Begin Time", "End Time", "File Name"
				}
			);
		RecordTable = new JTable();
		RecordTable.setModel(RecordTableModel);
		RecordScrollPane.setViewportView(RecordTable);
		
		JButton btnQueryrecord = new JButton("QueryRecord");
		btnQueryrecord.setBounds(462, 15, 121, 23);
		PlayBackPanel.add(btnQueryrecord);
		
		textRecordBeginTime = new JTextField();
		textRecordBeginTime.setBounds(102, 16, 126, 21);
		PlayBackPanel.add(textRecordBeginTime);
		textRecordBeginTime.setColumns(10);
		textRecordBeginTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()-24*3600), DateFormat));
		
		textRecordEndTime = new JTextField();
		textRecordEndTime.setBounds(302, 17, 131, 21);
		PlayBackPanel.add(textRecordEndTime);
		textRecordEndTime.setColumns(10);
		textRecordEndTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()), DateFormat));
		
		JLabel lblBegintime = new JLabel("BeginTime:");
		lblBegintime.setBounds(29, 19, 67, 15);
		PlayBackPanel.add(lblBegintime);
		
		JLabel lblEndtime = new JLabel("EndTime:");
		lblEndtime.setBounds(238, 19, 54, 15);
		PlayBackPanel.add(lblEndtime);
		
		JSlider PlayBackSlider = new JSlider();
		PlayBackSlider.setBounds(29, 169, 554, 8);
		PlayBackPanel.add(PlayBackSlider);
		PlayBackSlider.setValue(0);
		
		JLabel lblProgressLabel = new JLabel("");
		lblProgressLabel.setBounds(211, 187, 184, 15);
		PlayBackPanel.add(lblProgressLabel);
		
		JButton btnRecordPlay = new JButton("Play");
		btnRecordPlay.setBounds(29, 215, 93, 23);
		PlayBackPanel.add(btnRecordPlay);
		
		JButton btnRecordStop = new JButton("Stop");
		btnRecordStop.setBounds(234, 215, 93, 23);
		PlayBackPanel.add(btnRecordStop);
		
		JButton btnRecordFast = new JButton(">>");
		btnRecordFast.setBounds(440, 215, 93, 23);
		PlayBackPanel.add(btnRecordFast);
		
		JButton btnRecordSlow = new JButton("<<");
		btnRecordSlow.setBounds(337, 215, 93, 23);
		PlayBackPanel.add(btnRecordSlow);
		
		JButton btnRecordPause = new JButton("Pause");
		btnRecordPause.setBounds(132, 215, 93, 23);
		PlayBackPanel.add(btnRecordPause);
		
		JLabel lblPlaybackSpeed = new JLabel("1X");
		lblPlaybackSpeed.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlaybackSpeed.setBounds(543, 217, 40, 18);
		PlayBackPanel.add(lblPlaybackSpeed);
		
		JButton btnStartdownload = new JButton("StartDownload");
		btnStartdownload.setBounds(29, 327, 126, 30);
		PlayBackPanel.add(btnStartdownload);
		
		JButton btnStopdownload = new JButton("StopDownload");
		btnStopdownload.setBounds(165, 327, 127, 30);
		PlayBackPanel.add(btnStopdownload);
		
		JProgressBar DownloadProgress = new JProgressBar();
		DownloadProgress.setBounds(29, 293, 543, 14);
		PlayBackPanel.add(DownloadProgress);
		
		JLabel lblRecordLabel = new JLabel("");
		lblRecordLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblRecordLabel.setForeground(Color.RED);
		lblRecordLabel.setBounds(29, 367, 554, 82);
		lblRecordLabel.setText("<html>Note: The Start and end times of playback or download are the same as the start and end times in the input box.</html>");
		PlayBackPanel.add(lblRecordLabel);
		
		JPanel VCAPanel = new JPanel();
		tabFunList.addTab("VCA", null, VCAPanel, null);
		VCAPanel.setLayout(null);
		
		tabSmartList = new JTabbedPane(JTabbedPane.TOP);
		tabSmartList.setBounds(0, 0, 620, 485);
		VCAPanel.add(tabSmartList);
		
		JPanel FacePanel = new JPanel();
		tabSmartList.addTab("Face", null, FacePanel, null);
		FacePanel.setLayout(null);
		
		JTabbedPane tabFaceList = new JTabbedPane(JTabbedPane.TOP);
		tabFaceList.setBounds(0, 0, 615, 455);
		FacePanel.add(tabFaceList);
		
		JPanel PersonLibPanel = new JPanel();
		PersonLibPanel.setToolTipText("");
		tabFaceList.addTab("PersonLib", null, PersonLibPanel, null);
		PersonLibPanel.setLayout(null);
		
		JPanel PersonLibManageePanel = new JPanel();
		PersonLibManageePanel.setBorder(new TitledBorder(null, "Person lib manage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PersonLibManageePanel.setBounds(0, 0, 134, 416);
		PersonLibPanel.add(PersonLibManageePanel);
		PersonLibManageePanel.setLayout(null);
		
		PersonLibcomboBox = new JComboBox<String>();
		PersonLibcomboBox.setBounds(10, 22, 114, 32);
		PersonLibManageePanel.add(PersonLibcomboBox);
		
		JButton btnAddPersonLib = new JButton("Add");
		btnAddPersonLib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				//弹出界面显示输入需要添加的库名字	
				String strPersoLibName= JOptionPane.showInputDialog("Please input person lib name:：");
	
				//调用sdk接口添加库
				NETDEV_LIB_INFO_S stPersonLibInfo = new NETDEV_LIB_INFO_S();
				Common.stringToByteArray(strPersoLibName, stPersonLibInfo.szName);
				IntByReference dwLibID = new IntByReference();
				boolean bRet = netdevsdk.NETDEV_CreatePersonLibInfo(lpUserID, stPersonLibInfo, dwLibID);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_CreatePersonLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}

		    	stPersonLibInfo.udwID = dwLibID.getValue();
		    	mapPersonLib.put(Common.byteArrayToString(stPersonLibInfo.szName), stPersonLibInfo);
		    	
		    	PersonLibcomboBox.addItem(Common.byteArrayToString(stPersonLibInfo.szName));
		    	
			}
		});
		btnAddPersonLib.setBounds(20, 64, 93, 23);
		PersonLibManageePanel.add(btnAddPersonLib);
		
		JButton btnDeletePaersonLib = new JButton("Delete");
		btnDeletePaersonLib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == PersonLibcomboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please add person lib first.");
				}
				
				String strPersonLibString = PersonLibcomboBox.getItemAt(PersonLibcomboBox.getSelectedIndex());
				NETDEV_LIB_INFO_S stPersonLibInfo = mapPersonLib.get(strPersonLibString);
				NETDEV_DELETE_DB_FLAG_INFO_S stFlagInfo = new NETDEV_DELETE_DB_FLAG_INFO_S();
				stFlagInfo.bIsDeleteMember = 1;
				boolean bRet = netdevsdk.NETDEV_DeletePersonLibInfo(lpUserID, stPersonLibInfo.udwID, stFlagInfo);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_DeletePersonLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}

		    	PersonLibcomboBox.removeItemAt(PersonLibcomboBox.getSelectedIndex());
		    	mapPersonLib.remove(strPersonLibString);
			}
		});
		btnDeletePaersonLib.setBounds(20, 110, 93, 23);
		PersonLibManageePanel.add(btnDeletePaersonLib);
		
		JButton btnModifyPersonLib = new JButton("Modify");
		btnModifyPersonLib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				String strPersoLibName = JOptionPane.showInputDialog("Please input new person lib name:");
				NETDEV_LIB_INFO_S stPersonLibInfo = mapPersonLib.get(PersonLibcomboBox.getItemAt(PersonLibcomboBox.getSelectedIndex()));
				Common.stringToByteArray(strPersoLibName, stPersonLibInfo.szName);
				stPersonLibInfo.write();
				NETDEV_PERSON_LIB_LIST_S stPersonLibList = new NETDEV_PERSON_LIB_LIST_S();
				stPersonLibList.udwNum = 1;
				stPersonLibList.pstLibInfo = stPersonLibInfo.getPointer();
				boolean bRet = netdevsdk.NETDEV_ModifyPersonLibInfo(lpUserID, stPersonLibList);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_ModifyPersonLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
		    	mapPersonLib.remove(PersonLibcomboBox.getItemAt(PersonLibcomboBox.getSelectedIndex()));
		    	mapPersonLib.put(strPersoLibName, stPersonLibInfo);
		    	int dwCurrentindex = PersonLibcomboBox.getSelectedIndex();
		    	PersonLibcomboBox.removeItemAt(dwCurrentindex);
		    	PersonLibcomboBox.insertItemAt(strPersoLibName, dwCurrentindex);
			}
		});
		btnModifyPersonLib.setBounds(20, 157, 93, 23);
		PersonLibManageePanel.add(btnModifyPersonLib);
		
		JButton btnFindPersonLib = new JButton("Find");
		btnFindPersonLib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				boolean bRet = false;
				
				PersonLibcomboBox.removeAllItems();
				mapPersonLib.clear();
				
				Pointer pPersonLibHandle = netdevsdk.NETDEV_FindPersonLibList(lpUserID);
				if(null == pPersonLibHandle)
				{
		    		System.out.printf("NETDEV_FindPersonLibList failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
				else 
				{
					while(true)
					{
						NETDEV_LIB_INFO_S stPersonLibInfo = new NETDEV_LIB_INFO_S();
						bRet = netdevsdk.NETDEV_FindNextPersonLibInfo(pPersonLibHandle, stPersonLibInfo);
						if(false == bRet)
						{
				    		System.out.printf("NETDEV_FindNextPersonLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
				    		break;
						}
						mapPersonLib.put(Common.byteArrayToString(stPersonLibInfo.szName), stPersonLibInfo);
						PersonLibcomboBox.addItem(Common.byteArrayToString(stPersonLibInfo.szName));
					}
					bRet = netdevsdk.NETDEV_FindClosePersonLibList(pPersonLibHandle);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_FindClosePersonLibList failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
				}
			}
		});
		btnFindPersonLib.setBounds(20, 208, 93, 23);
		PersonLibManageePanel.add(btnFindPersonLib);
		
		JPanel MersonManagePanel = new JPanel();
		MersonManagePanel.setBorder(new TitledBorder(null, "Person manage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		MersonManagePanel.setBounds(144, 0, 466, 426);
		PersonLibPanel.add(MersonManagePanel);
		MersonManagePanel.setLayout(null);
		
		JScrollPane PersonScrollPane = new JScrollPane();
		PersonScrollPane.setBounds(10, 22, 456, 208);
		MersonManagePanel.add(PersonScrollPane);
		
		PersonTable = new JTable();
		PersonTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		PersonTable.setModel(PersonTableModel);
		PersonTable.getColumnModel().getColumn(0).setPreferredWidth(85);
		PersonScrollPane.setViewportView(PersonTable);
		
		JButton btnModifyPerson = new JButton("Modify");
		btnModifyPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(0 == PersonTableModel.getRowCount()  || PersonTable.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find person or seletc person first.");
					return;
				}
				
				new PersonOperateWindow(PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_MODIFYPERSON);  
			}
		});
		btnModifyPerson.setBounds(253, 255, 93, 23);
		MersonManagePanel.add(btnModifyPerson);
		
		JButton btnDeletePerson = new JButton("Delete");
		btnDeletePerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(0 == PersonTableModel.getRowCount()  || PersonTable.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find person or seletc person first.");
					return;
				}
				
				String strPersonName = (String) PersonTable.getValueAt(PersonTable.getSelectedRow(), 0);
				NETDEV_PERSON_INFO_S stPersonInfo = mapPersonInfo.get(strPersonName);
				String strPersonLibString = PersonLibcomboBox.getItemAt(PersonLibcomboBox.getSelectedIndex());
				NETDEV_LIB_INFO_S stPersonLibInfo = mapPersonLib.get(strPersonLibString);
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0)
				{
					int dwLastChange = 0;
					boolean bRet = netdevsdk.NETDEV_DeletePersonInfo(lpUserID, stPersonLibInfo.udwID, stPersonInfo.udwPersonID, dwLastChange);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_DeletePersonInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
				}
				else
				{
					NETDEV_BATCH_OPERATE_MEMBER_LIST_S stIDList = new NETDEV_BATCH_OPERATE_MEMBER_LIST_S();
					stIDList.udwMemberNum = 1;
					stIDList.pstMemberIDList = new Memory(4);
					stIDList.pstMemberIDList.setInt(0, stPersonInfo.udwPersonID);
					
					NETDEV_BATCH_OPERATOR_LIST_S stResutList = new NETDEV_BATCH_OPERATOR_LIST_S();
					
					boolean bRet = netdevsdk.NETDEV_DeletePersonInfoList(lpUserID, stPersonLibInfo.udwID, stIDList, stResutList);
					if(false == bRet)
					{
			    		System.out.printf("NETDEV_DeletePersonInfoList failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
					}
				}		
			}
		});
		btnDeletePerson.setBounds(134, 255, 93, 23);
		MersonManagePanel.add(btnDeletePerson);
		
		JButton btnAddPerson = new JButton("Add");
		btnAddPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == PersonLibcomboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find person lib first.");
					return;
				}
				
				JFileChooser fcPictureChooser = new JFileChooser();
			    //设置一个文件筛选器
				FileNameExtensionFilter filter=new FileNameExtensionFilter("picture file(jpg)", "jpg"); 
				fcPictureChooser.setFileFilter(filter);  
			    //设置不允许多选
				fcPictureChooser.setMultiSelectionEnabled(false); 
			    int result=fcPictureChooser.showSaveDialog(null); 
			    if (result==JFileChooser.APPROVE_OPTION)
			    {
			    	strPersonChosePicurePath = fcPictureChooser.getSelectedFile().getAbsolutePath();
			    	
			    	new PersonOperateWindow(PERSON_OPERATE_WINDOW_EFFECT.PERSON_OPERATE_WINDOW_ADDPERSON);  
			    }
	    
			}
		});
		btnAddPerson.setBounds(10, 255, 93, 23);
		MersonManagePanel.add(btnAddPerson);
		
		JButton btnFindPerson = new JButton("Find");
		btnFindPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				if(0 == PersonLibcomboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find person lib first.");
					return;
				}
				
				NETDEV_LIB_INFO_S stPersonLibInfo = mapPersonLib.get(PersonLibcomboBox.getItemAt(PersonLibcomboBox.getSelectedIndex()));
				NETDEV_PERSON_QUERY_INFO_S stQueryInfo = new NETDEV_PERSON_QUERY_INFO_S();
		        stQueryInfo.udwNum = 0;
		        stQueryInfo.udwOffset =  0;
		        stQueryInfo.udwLimit = 16;
	
//		        NETDEV_QUERY_INFO_S stQueryInfos = new NETDEV_QUERY_INFO_S();
//		        stQueryInfos.dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_DATABASE_ID;
//		        stQueryInfos.dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
//		        Common.stringToByteArray(Integer.toString(stPersonLibInfo.udwID), stQueryInfos.szConditionData);
//		        stQueryInfos.write();
//		        stQueryInfo.pstQueryInfos = stQueryInfos.getPointer(); 
		        
				NETDEV_BATCH_OPERATE_BASIC_S stQueryResultInfo = new NETDEV_BATCH_OPERATE_BASIC_S();
				boolean bRet = false;
				
				PersonTableModel.setRowCount(0);
				mapPersonInfo.clear();
				
				while(true)
				{
					Pointer lpPersonFindHandle = netdevsdk.NETDEV_FindPersonInfoList(lpUserID, stPersonLibInfo.udwID, stQueryInfo, stQueryResultInfo);
		            if(lpPersonFindHandle == null || stQueryResultInfo.udwTotal == 0)
		            {
		            	System.out.printf("NETDEV_FindPersonInfoList failed:%d\n", netdevsdk.NETDEV_GetLastError());
		                break;
		            }
		            else 
		            {
		            	stQueryInfo.udwOffset += stQueryInfo.udwLimit;
		            	while(true)
		            	{
		            		NETDEV_PERSON_INFO_S stPersonInfo = new NETDEV_PERSON_INFO_S();
		            		stPersonInfo.stRegionInfo = new NETDEV_REGION_INFO_S();
		            		
		            		stPersonInfo.stImageInfo[0] = new NETDEV_IMAGE_INFO_S();
		            		stPersonInfo.stImageInfo[0].stFileInfo = new NETDEV_FILE_INFO_S();
		            		stPersonInfo.stImageInfo[0].stFileInfo.udwSize = 1048576;
		            		stPersonInfo.stImageInfo[0].stFileInfo.pcData = new Memory(stPersonInfo.stImageInfo[0].stFileInfo.udwSize);
		            		stPersonInfo.write();
		            		
		            		bRet = netdevsdk.NETDEV_FindNextPersonInfo(lpPersonFindHandle, stPersonInfo);
		            		if(bRet != true)
		            		{
		            			break;
		            		}
		            		else 
		            		{
		            			mapPersonInfo.put(Common.byteArrayToString(stPersonInfo.szPersonName), stPersonInfo);
		            			
		            			Vector<String> PersonLibVector = new Vector<String>();
		            			PersonLibVector.add(Common.byteArrayToString(stPersonInfo.szPersonName));
		            			
		            			if(stPersonInfo.udwGender == NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_MAN)
		            			{
		            				PersonLibVector.add("man");
		            			}
		            			else if(stPersonInfo.udwGender == NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_WOMAN)
		            			{
		            				PersonLibVector.add("woman");
		            			}
		            			else
		            			{
		            				PersonLibVector.add("unkoow");
								}
		            			
		            			PersonLibVector.add(Common.byteArrayToString(stPersonInfo.szBirthday));
		            			
		            			PersonLibVector.add(Common.byteArrayToString(stPersonInfo.stRegionInfo.szNation));
		            			PersonLibVector.add(Common.byteArrayToString(stPersonInfo.stRegionInfo.szProvince));
		            			PersonLibVector.add(Common.byteArrayToString(stPersonInfo.stRegionInfo.szCity));
		            			
		            			if(stPersonInfo.udwIdentificationNum > 0)
		            			{
		            				if(stPersonInfo.stIdentificationInfo[0].udwType == NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_ID)
		            				{
		            					PersonLibVector.add("ID");
		            				}
		            				else if(stPersonInfo.stIdentificationInfo[0].udwType == NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_PASSPORT)
		               				{
		            					PersonLibVector.add("Passport");
		            				}
		             				else if(stPersonInfo.stIdentificationInfo[0].udwType == NETDEV_ID_TYPE_E.NETDEV_CERTIFICATE_TYPE_DRIVING_LICENSE)
		               				{
		            					PersonLibVector.add("Driving license");
		            				}
		             				else
		             				{
		             					PersonLibVector.add("other");	
									}
		            				PersonLibVector.add(Common.byteArrayToString(stPersonInfo.stIdentificationInfo[0].szNumber));
		            			}
		            			else
		            			{
		            				PersonLibVector.add("-");
		            				PersonLibVector.add("-");
								}

		            			PersonTableModel.addRow(PersonLibVector);
		            			
							}
		            	}
		            	bRet = netdevsdk.NETDEV_FindClosePersonInfoList(lpPersonFindHandle);
			            if(bRet == false)
			            {
			            	System.out.printf("NETDEV_FindClosePersonInfoList failed:%d", netdevsdk.NETDEV_GetLastError());
			            }
					}
		            if(stQueryInfo.udwOffset >= stQueryResultInfo.udwTotal)
		            {
		            	break;
		            }
				}

			}
		});
		btnFindPerson.setBounds(363, 255, 93, 23);
		MersonManagePanel.add(btnFindPerson);
		
		JPanel PersonMonitorPane = new JPanel();
		tabFaceList.addTab("PersonMonitor", null, PersonMonitorPane, null);
		PersonMonitorPane.setLayout(null);
		
		JScrollPane PersionMonitorScrollPane = new JScrollPane();
		PersionMonitorScrollPane.setBounds(0, 0, 610, 249);
		PersonMonitorPane.add(PersionMonitorScrollPane);
		
		PersonMonitorTable = new JTable();
		PersonMonitorTable.setModel(PersonMonitorTableModel);
		PersionMonitorScrollPane.setViewportView(PersonMonitorTable);
		
		JButton btnAddPersonMonitor = new JButton("Add");
		btnAddPersonMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please log on to the device first.");
					return;
				}
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				new PersonMonitor(PERSON_MONITOR_OPERATE_WINDOW_EFFECT.PERSON_MONITOR_OPERATE_WINDOW_ADDPERSONMONITOR);
			}
		});
		btnAddPersonMonitor.setBounds(10, 269, 93, 23);
		PersonMonitorPane.add(btnAddPersonMonitor);
		
		JButton btnDeletePersonMonitor = new JButton("Delete");
		btnDeletePersonMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID || 0 == PersonMonitorTable.getRowCount() || PersonMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first or find monitor ");
					return;
				}
				
				String strPersonMonitorName = (String) PersonMonitorTable.getValueAt(PersonMonitorTable.getSelectedRow(), 0);
				NETDEV_MONITION_INFO_S stPersonMonitorInfo = mapPersonMonitorMap.get(strPersonMonitorName);
				
				NETDEV_BATCH_OPERATOR_LIST_S stResultList = new NETDEV_BATCH_OPERATOR_LIST_S();
				stResultList.udwNum = 1;
				NETDEV_BATCH_OPERATOR_INFO_S stBatchList = new NETDEV_BATCH_OPERATOR_INFO_S();
				stBatchList.udwID = stPersonMonitorInfo.udwID;
				stBatchList.write();
				stResultList.pstBatchList = stBatchList.getPointer();
				
				
				boolean bRet = netdevsdk.NETDEV_BatchDeletePersonMonitorInfo(lpUserID, stResultList);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_BatchDeletePersonMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}

			}
		});
		btnDeletePersonMonitor.setBounds(126, 269, 93, 23);
		PersonMonitorPane.add(btnDeletePersonMonitor);
		
		JButton btnModifyPersonMonitor = new JButton("Modify");
		btnModifyPersonMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == PersonMonitorTable.getRowCount() || PersonMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first or find monitor ");
					return;
				}

				new PersonMonitor(PERSON_MONITOR_OPERATE_WINDOW_EFFECT.PERSON_MONITOR_OPERATE_WINDOW_MODIFYPERSONMONITOR);

			}
		});
		btnModifyPersonMonitor.setBounds(244, 269, 93, 23);
		PersonMonitorPane.add(btnModifyPersonMonitor);
		
		JButton btnEnablePersonMonitor = new JButton("Enable");
		if(DeviceTypeComboBox.getSelectedIndex() == 0)
		{
			btnEnablePersonMonitor.setEnabled(false);
		}
		
		btnEnablePersonMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == PersonMonitorTable.getRowCount() || PersonMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first or find monitor ");
					return;
				}
				
				String strPersonMonitorName = (String) PersonMonitorTable.getValueAt(PersonMonitorTable.getSelectedRow(), 0);
				NETDEV_MONITION_INFO_S stPersonMonitorInfo = mapPersonMonitorMap.get(strPersonMonitorName);
				
				
				boolean bRet = netdevsdk.NETDEV_GetPersonMonitorRuleInfo(lpUserID, stPersonMonitorInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_GetPersonMonitorRuleInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
				
				stPersonMonitorInfo.stMonitorRuleInfo.bEnabled = 1;
				
				
				bRet = netdevsdk.NETDEV_SetPersonMonitorRuleInfo(lpUserID, stPersonMonitorInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_SetPersonMonitorRuleInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
			}
		});
		btnEnablePersonMonitor.setBounds(10, 318, 93, 23);
		PersonMonitorPane.add(btnEnablePersonMonitor);
		
		JButton btnShutDownPersonMonitor = new JButton("Disable");
		if(DeviceTypeComboBox.getSelectedIndex() == 0)
		{
			btnShutDownPersonMonitor.setEnabled(false);
		}
		btnShutDownPersonMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == PersonMonitorTable.getRowCount() || PersonMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first or find monitor ");
					return;
				}
				
				String strPersonMonitorName = (String) PersonMonitorTable.getValueAt(PersonMonitorTable.getSelectedRow(), 0);
				NETDEV_MONITION_INFO_S stPersonMonitorInfo = mapPersonMonitorMap.get(strPersonMonitorName);
				
				
				boolean bRet = netdevsdk.NETDEV_GetPersonMonitorRuleInfo(lpUserID, stPersonMonitorInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_GetPersonMonitorRuleInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
				
				stPersonMonitorInfo.stMonitorRuleInfo.bEnabled = 0;
				
				
				bRet = netdevsdk.NETDEV_SetPersonMonitorRuleInfo(lpUserID, stPersonMonitorInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_SetPersonMonitorRuleInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
				
			}
		});
		btnShutDownPersonMonitor.setBounds(126, 318, 93, 23);
		PersonMonitorPane.add(btnShutDownPersonMonitor);
		
		JButton btnFindPersonMonitor = new JButton("Find");
		btnFindPersonMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				mapPersonMonitorMap.clear();
				PersonMonitorTableModel.setRowCount(0);
				
				boolean bRet = false;
				NETDEV_MONITOR_QUERY_INFO_S stQueryInfo = new NETDEV_MONITOR_QUERY_INFO_S();
				stQueryInfo.udwLimit = 20;
				Pointer lpPersonMonitorHandle = netdevsdk.NETDEV_FindPersonMonitorList(lpUserID, ChannelID, stQueryInfo);
		        if(lpPersonMonitorHandle == null)
		        {
		    		System.out.printf("NETDEV_FindPersonMonitorList failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		        }
		        else 
		        {
					while(true)
					{
						NETDEV_MONITION_INFO_S stMonitorInfo = new NETDEV_MONITION_INFO_S();
						stMonitorInfo.stMonitorRuleInfo = new NETDEV_MONITION_RULE_INFO_S();
						
						stMonitorInfo.stMonitorRuleInfo.udwChannelNum = 1;
						stMonitorInfo.stMonitorRuleInfo.pudwMonitorChlIDList = new Memory(4 * stMonitorInfo.stMonitorRuleInfo.udwChannelNum);
						stMonitorInfo.stMonitorRuleInfo.pudwMonitorChlIDList.setInt(0, 0);
						
						stMonitorInfo.udwLinkStrategyNum = 10;
						NETDEV_LINKAGE_STRATEGY_S stLinkStrategyList = new NETDEV_LINKAGE_STRATEGY_S();
						
						stMonitorInfo.pstLinkStrategyList = new Memory(stLinkStrategyList.size() * 10);

						
						
						stMonitorInfo.stWeekPlan = new NETDEV_VIDEO_WEEK_PLAN_S();
						for(int i =0; i < NetDEVSDKLib.NETDEV_MAX_DAY_NUM; i++)
						{
							stMonitorInfo.stWeekPlan.astDayPlan[i] = new NETDEV_VIDEO_DAY_PLAN_S();
						}
						stMonitorInfo.write();
						
						bRet = netdevsdk.NETDEV_FindNextPersonMonitorInfo(lpPersonMonitorHandle, stMonitorInfo);
		                if(bRet != true)
		                {
		                	System.out.printf("NETDEV_FindNextPersonMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		                    break;
		                }
		                else 
		                {
		                	//显示查询数据
	            			Vector<String> PersonMonitorVector = new Vector<String>();
	            			PersonMonitorVector.add(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szName));
	            			PersonMonitorVector.add(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szReason));
	            			if(stMonitorInfo.stMonitorRuleInfo.bEnabled == 1)
	            			{
	            				PersonMonitorVector.add("enable");
	            			}
	            			else
	            			{
	            				PersonMonitorVector.add("disable");
							}
	            			PersonMonitorTableModel.addRow(PersonMonitorVector);
	            			mapPersonMonitorMap.put(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szName), stMonitorInfo);
						}
					}
					bRet = netdevsdk.NETDEV_FindClosePersonMonitorList(lpPersonMonitorHandle);
	                if(bRet != true)
	                {
	                	System.out.printf("NETDEV_FindClosePersonMonitorList failed:%d\n", netdevsdk.NETDEV_GetLastError());
	                    return;
	                }
				}
			}
		});
		btnFindPersonMonitor.setBounds(360, 269, 93, 23);
		PersonMonitorPane.add(btnFindPersonMonitor);
		
		JPanel PersonDataReportPanel = new JPanel();
		tabFaceList.addTab("DataReport", null, PersonDataReportPanel, null);
		PersonDataReportPanel.setLayout(null);
		
		JPanel PersonMatchPanel = new JPanel();
		PersonMatchPanel.setBorder(new TitledBorder(null, "Person match and not match", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		PersonMatchPanel.setBounds(10, 10, 280, 410);
		PersonDataReportPanel.add(PersonMatchPanel);
		PersonMatchPanel.setLayout(null);
		
		JButton btnRegisterPersonMatchAndNotMatchAlarm = new JButton("Register");
		btnRegisterPersonMatchAndNotMatchAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please login the device first.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_SetPersonAlarmCallBack(lpUserID, personAlarmMessCB, lpUserID);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_SetPersonAlarmCallBack failed:%d", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					NETDEV_LAPI_SUB_INFO_S stSubInfo = new NETDEV_LAPI_SUB_INFO_S();
					stSubInfo.udwType = 16;
					stSubInfo.udwLibIDNum = 0xffff;
					
					NETDEV_SUBSCRIBE_SUCC_INFO_S stSubSuccInfo = new NETDEV_SUBSCRIBE_SUCC_INFO_S();
					bRet = netdevsdk.NETDEV_SubscibeLapiAlarm(lpUserID, stSubInfo, stSubSuccInfo);
					if(false == bRet){
						System.out.printf("NETDEV_SubscibeLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
						return;
					}
				
					dwPersonRecognizeMointerID = stSubSuccInfo.udwID;
				}
				else
				{
					bRet = netdevsdk.NETDEV_SetAlarmCallBack_V30(lpUserID, cbAlarmMessCallBack, null);
					if(false == bRet){
						System.out.printf("NETDEV_SetAlarmCallBack_V30 failed:%d\n", netdevsdk.NETDEV_GetLastError());
					}
				}
			}
		});
		btnRegisterPersonMatchAndNotMatchAlarm.setBounds(20, 20, 93, 23);
		PersonMatchPanel.add(btnRegisterPersonMatchAndNotMatchAlarm);
		
		JButton btnCancelRegisterPersonMatchAndNotMatchAlarm = new JButton("Cancel");
		btnCancelRegisterPersonMatchAndNotMatchAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please login the device first.");
					return;
				}
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					boolean bRet = netdevsdk.NETDEV_UnSubLapiAlarm(lpUserID, dwPersonRecognizeMointerID);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_UnSubLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	dwPersonRecognizeMointerID = -1;
				}
				else
				{
					boolean bRet = netdevsdk.NETDEV_SetPersonAlarmCallBack(lpUserID, null, lpUserID);	
			    	if(false == bRet){
			    		System.out.printf("NETDEV_SetPersonAlarmCallBack failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
				}
			}
		});
		btnCancelRegisterPersonMatchAndNotMatchAlarm.setBounds(165, 20, 93, 23);
		PersonMatchPanel.add(btnCancelRegisterPersonMatchAndNotMatchAlarm);
		
		JPanel PersonRecognizePanel = new JPanel();
		PersonRecognizePanel.setBorder(new TitledBorder(null, "Person recognize", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PersonRecognizePanel.setBounds(300, 10, 280, 410);
		PersonDataReportPanel.add(PersonRecognizePanel);
		PersonRecognizePanel.setLayout(null);
		
		JButton btnRegistPersonRecognize = new JButton("Register");
		btnRegistPersonRecognize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == ChannelID){
					JOptionPane.showMessageDialog(null, "Please select the device channel after successful login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_SetStructAlarmCallBack(lpUserID, cbStructAlarmCallBack, lpUserID);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_SetStructAlarmCallBack failed:%d", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
				
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					NETDEV_LAPI_SUB_INFO_S stSubInfo = new NETDEV_LAPI_SUB_INFO_S();
					stSubInfo.udwType = 32;
					stSubInfo.udwLibIDNum = 0xffff;
					
					NETDEV_SUBSCRIBE_SUCC_INFO_S stSubSuccInfo = new NETDEV_SUBSCRIBE_SUCC_INFO_S();
					bRet = netdevsdk.NETDEV_SubscibeLapiAlarm(lpUserID, stSubInfo, stSubSuccInfo);
					if(false == bRet){
						System.out.printf("NETDEV_SubscibeLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
						return;
					}
				
					PersonStructMointerID = stSubSuccInfo.udwID;
				}
				else
				{
					NETDEV_SUBSCRIBE_SMART_INFO_S stSubscribeInfo = new NETDEV_SUBSCRIBE_SMART_INFO_S();
					stSubscribeInfo.udwNum = 1;
					stSubscribeInfo.pudwSmartType = new Memory(4 * stSubscribeInfo.udwNum);
					stSubscribeInfo.pudwSmartType.setInt(0, 0);
					
					NETDEV_SMART_INFO_S stSmartInfo = new NETDEV_SMART_INFO_S();
					stSmartInfo.dwChannelID = ChannelID;
					bRet = netdevsdk.NETDEV_SubscribeSmart(lpUserID, stSubscribeInfo, stSmartInfo);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_SubscribeSmart failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	PersonStructMointerID = stSmartInfo.udwSubscribeID;
				}
			}
		});
		btnRegistPersonRecognize.setBounds(20, 20, 93, 23);
		PersonRecognizePanel.add(btnRegistPersonRecognize);
		
		JButton btnCancelPersonRecognize = new JButton("Cancel");
		btnCancelPersonRecognize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == ChannelID || 0 == PersonStructMointerID){
					JOptionPane.showMessageDialog(null, "Please select the device channel after successful login.");
					return;
				}
					
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					boolean bRet = netdevsdk.NETDEV_UnSubLapiAlarm(lpUserID, PersonStructMointerID);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_UnSubLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	PersonStructMointerID = -1;
				}
				else
				{
					NETDEV_SMART_INFO_S stSmartInfo = new NETDEV_SMART_INFO_S();
					stSmartInfo.dwChannelID = ChannelID;
					stSmartInfo.udwSubscribeID = PersonStructMointerID;
					boolean bRet = netdevsdk.NETDEV_UnsubscribeSmart(lpUserID, stSmartInfo);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_UnsubscribeSmart failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
				}
			}
		});
		btnCancelPersonRecognize.setBounds(165, 20, 93, 23);
		PersonRecognizePanel.add(btnCancelPersonRecognize);
		
		JPanel PersonAlarmRecordPanel = new JPanel();
		tabFaceList.addTab("AlarmRecord", null, PersonAlarmRecordPanel, null);
		PersonAlarmRecordPanel.setLayout(null);
		
		JLabel lblPersonMonitorType = new JLabel("MonitorType");
		lblPersonMonitorType.setBounds(10, 10, 75, 28);
		PersonAlarmRecordPanel.add(lblPersonMonitorType);
		
		JComboBox<Object> PersonMonitorTypeComboBox = new JComboBox<Object>();
		PersonMonitorTypeComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"match", "not match"}));
		PersonMonitorTypeComboBox.setBounds(86, 14, 66, 24);
		PersonAlarmRecordPanel.add(PersonMonitorTypeComboBox);
		
		JLabel lblPersonAlarmTime = new JLabel("AlarmTime");
		lblPersonAlarmTime.setBounds(177, 17, 66, 15);
		PersonAlarmRecordPanel.add(lblPersonAlarmTime);
		
		textPersonAlarmBeginTime = new JTextField();
		textPersonAlarmBeginTime.setBounds(241, 14, 123, 21);
		PersonAlarmRecordPanel.add(textPersonAlarmBeginTime);
		textPersonAlarmBeginTime.setColumns(10);
		textPersonAlarmBeginTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()-24*3600), DateFormat));
		
		JLabel lblaaaa = new JLabel("-");
		lblaaaa.setBounds(374, 17, 17, 15);
		PersonAlarmRecordPanel.add(lblaaaa);
		
		textPersonAlarmEndTime = new JTextField();
		textPersonAlarmEndTime.setBounds(392, 14, 123, 21);
		PersonAlarmRecordPanel.add(textPersonAlarmEndTime);
		textPersonAlarmEndTime.setColumns(10);
		textPersonAlarmEndTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()), DateFormat));
		
		JButton btnPersonAlarmFind = new JButton("Queue");
		btnPersonAlarmFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread(new Runnable()
				{
					@Override
					public void run() {
						//数据量较大，建议采用分页形式展示
						String strBeginTime = textPersonAlarmBeginTime.getText();
						String strEndTime = textPersonAlarmEndTime.getText();
						NETDEV_ALARM_LOG_COND_LIST_S stFindCond = new NETDEV_ALARM_LOG_COND_LIST_S();
						stFindCond.dwPageRow = 16;
						stFindCond.dwFirstRow = 0;
						stFindCond.dwCondSize = 4;
						
						stFindCond.astCondition[0] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[1] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[2] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[3] = new NETDEV_QUERY_INFO_S();
						
						stFindCond.astCondition[0].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_ALARMTYPE;
						stFindCond.astCondition[0].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
						if(PersonMonitorTypeComboBox.getSelectedIndex() == 0)
						{
							Common.stringToByteArray("1018", stFindCond.astCondition[0].szConditionData);   //NETDEV_ALARM_SMART_FACE_MATCH_LIST
						}
						else if(PersonMonitorTypeComboBox.getSelectedIndex() == 1)
						{
							Common.stringToByteArray("1020", stFindCond.astCondition[0].szConditionData);   //NETDEV_ALARM_SMART_FACE_MISMATCH_LIST
						}
						
						
						stFindCond.astCondition[1].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[1].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_LESS;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(strBeginTime, DateFormat)), stFindCond.astCondition[1].szConditionData);   
						
						stFindCond.astCondition[2].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[2].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_GREATER;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(strEndTime, DateFormat)), stFindCond.astCondition[2].szConditionData);  
						
						stFindCond.astCondition[3].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[3].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DESC_ORDER;
						Common.stringToByteArray("", stFindCond.astCondition[3].szConditionData);   //NETDEV_ALARM_SMART_FACE_MATCH_LIST
						
						NETDEV_SMART_ALARM_LOG_RESULT_INFO_S  stResultInfo = new NETDEV_SMART_ALARM_LOG_RESULT_INFO_S();
						boolean bRet = false;
						
						while(true)
						{
							Pointer lpFindRecordHandlePointer = netdevsdk.NETDEV_FindFaceRecordDetailList(lpUserID, stFindCond, stResultInfo);
							if(lpFindRecordHandlePointer != null)
							{
								while(true)
								{
									NETDEV_FACE_RECORD_SNAPSHOT_INFO_S stRecordInfo = new NETDEV_FACE_RECORD_SNAPSHOT_INFO_S();
									stRecordInfo.stCompareInfo = new NETDEV_FACE_ALARM_CMP_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo = new NETDEV_FACE_MEMBER_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberRegionInfo = new NETDEV_FACE_MEMBER_REGION_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberIDInfo = new NETDEV_FACE_MEMBER_ID_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberImageInfo = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberImageInfo.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberImageInfo.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberSemiInfo = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberSemiInfo.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberSemiInfo.udwSize = Common.NETDEMO_PICTURE_SIZE;
											
									stRecordInfo.stCompareInfo.stSnapshotImage = new NETDEV_FACE_ALARM_SNAP_IMAGE_S();
									stRecordInfo.stCompareInfo.stSnapshotImage.stBigImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stSnapshotImage.stBigImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stSnapshotImage.stBigImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stCompareInfo.stSnapshotImage.stArea = new NETDEV_FACE_ALARM_IMAGE_AREA_S();
									
									bRet = netdevsdk.NETDEV_FindNextFaceRecordDetail(lpFindRecordHandlePointer, stRecordInfo);
									if(bRet == true)
									{
										String strTime = Common.getDate();
										if(DeviceTypeComboBox.getSelectedIndex() == 1)//VMS设备，大图需要单独查询
										{								
											String strFileNameBig = strPicturePath + strTime +"BigImage.jpg";
											NETDEV_FILE_INFO_S stFileInfo = new NETDEV_FILE_INFO_S();
											stFileInfo.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
											stFileInfo.udwSize = Common.NETDEMO_PICTURE_SIZE;
							
											 bRet = netdevsdk.NETDEV_GetFaceRecordImageInfo(lpUserID, stRecordInfo.udwRecordID, 0, stFileInfo);
							                if(bRet == true)
							                {
							                	Common.savePicture(stFileInfo.pcData, stFileInfo.udwSize, strFileNameBig);
							                }
										}

										
										String strFileNameSmall = strPicturePath + strTime +"SmallImage.jpg";
										Common.savePicture(stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.pcData, stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.udwSize, strFileNameSmall);
										
									}
									else 
									{
										break;
									}
								}
								bRet = netdevsdk.NETDEV_FindCloseFaceRecordDetail(lpFindRecordHandlePointer);
				                if(bRet != true)
				                {
				                	System.out.printf("NETDEV_FindCloseFaceRecordDetail failed:%d\n", netdevsdk.NETDEV_GetLastError());
				                    return;
				                }
				                if(stFindCond.dwFirstRow >= stResultInfo.udwTotal)
				                {
				                	break;
				                }
				                stFindCond.dwFirstRow += stFindCond.dwPageRow;
							}
							else 
							{
								break;
							}
						}
						JOptionPane.showMessageDialog(null, "find picture han been saved in local dir.");
					}
				}).start();
			}
		});
		btnPersonAlarmFind.setBounds(525, 13, 75, 23);
		PersonAlarmRecordPanel.add(btnPersonAlarmFind);
		
		JPanel PersonPassRecordPanel = new JPanel();
		tabFaceList.addTab("PassRecord", null, PersonPassRecordPanel, null);
		PersonPassRecordPanel.setLayout(null);
		
		JLabel lblPersonPassTime = new JLabel("Alarm time");
		lblPersonPassTime.setBounds(10, 10, 66, 15);
		PersonPassRecordPanel.add(lblPersonPassTime);
		
		textPersonPassBegionTime = new JTextField();
		textPersonPassBegionTime.setBounds(75, 7, 123, 21);
		PersonPassRecordPanel.add(textPersonPassBegionTime);
		textPersonPassBegionTime.setColumns(10);
		textPersonPassBegionTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()-24*3600), DateFormat));
		
		JLabel lblzhi = new JLabel("-");
		lblzhi.setBounds(208, 10, 6, 15);
		PersonPassRecordPanel.add(lblzhi);
		
		textPersonPassEndTime = new JTextField();
		textPersonPassEndTime.setBounds(224, 7, 123, 21);
		PersonPassRecordPanel.add(textPersonPassEndTime);
		textPersonPassEndTime.setColumns(10);
		textPersonPassEndTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()), DateFormat));
		
		JLabel lblPersonPassAlarmSource = new JLabel("Alarm source");
		lblPersonPassAlarmSource.setBounds(374, 10, 78, 15);
		PersonPassRecordPanel.add(lblPersonPassAlarmSource);
		
		textPersonPassAlarmSource = new JTextField();
		textPersonPassAlarmSource.setBounds(449, 7, 151, 21);
		PersonPassRecordPanel.add(textPersonPassAlarmSource);
		textPersonPassAlarmSource.setColumns(10);
		
		JButton btnFindPersonPassAlarmRecode = new JButton("Query");
		btnFindPersonPassAlarmRecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable()
				{
					@Override
					public void run() {
						//数据量较大，建议采用分页形式展示
						String strBeginTime = textPersonPassBegionTime.getText();
						String strEndTime = textPersonPassEndTime.getText();
						NETDEV_ALARM_LOG_COND_LIST_S stFindCond = new NETDEV_ALARM_LOG_COND_LIST_S();
						stFindCond.dwPageRow = 16;
						stFindCond.dwFirstRow = 0;
						stFindCond.dwCondSize = 3;
						
						stFindCond.astCondition[0] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[1] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[2] = new NETDEV_QUERY_INFO_S();		
						
						stFindCond.astCondition[0].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[0].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_LESS;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(strBeginTime, DateFormat)), stFindCond.astCondition[0].szConditionData);   //NETDEV_ALARM_SMART_FACE_MATCH_LIST
						
						stFindCond.astCondition[1].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[1].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_GREATER;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(strEndTime, DateFormat)), stFindCond.astCondition[1].szConditionData);   //NETDEV_ALARM_SMART_FACE_MATCH_LIST
						
						stFindCond.astCondition[2].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[2].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DESC_ORDER;
						Common.stringToByteArray("", stFindCond.astCondition[2].szConditionData);   
						
						NETDEV_SMART_ALARM_LOG_RESULT_INFO_S  stResultInfo = new NETDEV_SMART_ALARM_LOG_RESULT_INFO_S();
						boolean bRet = false;
						
						while(true)
						{
							Pointer lpFindRecordHandlePointer = netdevsdk.NETDEV_FindFaceRecordDetailList(lpUserID, stFindCond, stResultInfo);
							if(lpFindRecordHandlePointer != null)
							{
								while(true)
								{
									NETDEV_FACE_RECORD_SNAPSHOT_INFO_S stRecordInfo = new NETDEV_FACE_RECORD_SNAPSHOT_INFO_S();
									stRecordInfo.stCompareInfo = new NETDEV_FACE_ALARM_CMP_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo = new NETDEV_FACE_MEMBER_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberRegionInfo = new NETDEV_FACE_MEMBER_REGION_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberIDInfo = new NETDEV_FACE_MEMBER_ID_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberImageInfo = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberImageInfo.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberImageInfo.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberSemiInfo = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberSemiInfo.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stMemberInfo.stMemberSemiInfo.udwSize = Common.NETDEMO_PICTURE_SIZE;
									
									
									stRecordInfo.stCompareInfo.stSnapshotImage = new NETDEV_FACE_ALARM_SNAP_IMAGE_S();
									stRecordInfo.stCompareInfo.stSnapshotImage.stBigImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stSnapshotImage.stBigImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stSnapshotImage.stBigImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stCompareInfo.stSnapshotImage.stArea = new NETDEV_FACE_ALARM_IMAGE_AREA_S();
									
									bRet = netdevsdk.NETDEV_FindNextFaceRecordDetail(lpFindRecordHandlePointer, stRecordInfo);
									if(bRet == true)
									{
										//写文件
										String strTime = Common.getDate();
										String strFileNameBig = strPicturePath  + "_" + "PassTime_"+ stRecordInfo.udwPassTime + "_" + "_" + strTime +"BigImage.jpg";
										if(DeviceTypeComboBox.getSelectedIndex() == 1)//VMS设备，大图需要单独查询
										{
											NETDEV_FILE_INFO_S stFileInfo = new NETDEV_FILE_INFO_S();
											stFileInfo.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
											stFileInfo.udwSize = Common.NETDEMO_PICTURE_SIZE;
							
											 bRet = netdevsdk.NETDEV_GetFaceRecordImageInfo(lpUserID, stRecordInfo.udwRecordID, 0, stFileInfo);
							                if(bRet == true)
							                {
							                	Common.savePicture(stFileInfo.pcData, stFileInfo.udwSize, strFileNameBig);
							                }
										}
										
										String strFileNameSmall = strPicturePath + "_" + "PassTime_"+ stRecordInfo.udwPassTime + "_" + strTime +"SmallImage.jpg";
										Common.savePicture(stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.pcData, stRecordInfo.stCompareInfo.stSnapshotImage.stSmallImage.udwSize, strFileNameSmall);
										
									}
									else 
									{
										break;
									}
								}
								bRet = netdevsdk.NETDEV_FindCloseFaceRecordDetail(lpFindRecordHandlePointer);
				                if(bRet != true)
				                {
				                	System.out.printf("NETDEV_FindCloseFaceRecordDetail failed:%d\n", netdevsdk.NETDEV_GetLastError());
				                    return;
				                }
				                if(stResultInfo.udwTotal <= stFindCond.dwFirstRow)
				                {
				                	break;
				                }
				                stFindCond.dwFirstRow += stFindCond.dwPageRow;
							}
							else 
							{
								break;
							}
						}
					
					}
				}).start();
			
				JOptionPane.showMessageDialog(null, "find picture han been saved in local dir.");
			}
		});
		btnFindPersonPassAlarmRecode.setBounds(507, 38, 93, 23);
		PersonPassRecordPanel.add(btnFindPersonPassAlarmRecode);
		
		JPanel VehiclePanel = new JPanel();
		tabSmartList.addTab("Vehicle", null, VehiclePanel, null);
		VehiclePanel.setLayout(null);
		
		JTabbedPane tabVehicleList = new JTabbedPane(JTabbedPane.TOP);
		tabVehicleList.setBounds(0, 0, 615, 460);
		VehiclePanel.add(tabVehicleList);
		
		JPanel VehicleLibPanel = new JPanel();
		tabVehicleList.addTab("VehicleLib", null, VehicleLibPanel, null);
		VehicleLibPanel.setLayout(null);
		
		JPanel VehicleLibManagePanel = new JPanel();
		VehicleLibManagePanel.setBorder(new TitledBorder(null, "Vehicle lib manage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		VehicleLibManagePanel.setBounds(0, 0, 130, 431);
		VehicleLibPanel.add(VehicleLibManagePanel);
		VehicleLibManagePanel.setLayout(null);
		
		VehicleLibComboBox.setBounds(10, 30, 110, 21);
		VehicleLibManagePanel.add(VehicleLibComboBox);
		
		JButton btnVehicleLibAdd = new JButton("Add");
		btnVehicleLibAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				//弹出界面显示输入需要添加的库名字	
				String strVehicleLibName= JOptionPane.showInputDialog("Please input vehicle lib name:：");
	
				//调用sdk接口添加库
				NETDEV_LIB_INFO_S stVehicleLibInfo = new NETDEV_LIB_INFO_S();
				Common.stringToByteArray(strVehicleLibName, stVehicleLibInfo.szName);
				boolean bRet = netdevsdk.NETDEV_AddVehicleLibInfo(lpUserID, stVehicleLibInfo);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_AddVehicleLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}

		    	mapVehicleLib.put(Common.byteArrayToString(stVehicleLibInfo.szName), stVehicleLibInfo);
		    	
		    	VehicleLibComboBox.addItem(Common.byteArrayToString(stVehicleLibInfo.szName));
			}
		});
		btnVehicleLibAdd.setBounds(23, 76, 81, 23);
		VehicleLibManagePanel.add(btnVehicleLibAdd);
		
		JButton btnVehicleLibDelete = new JButton("Delete");
		btnVehicleLibDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == VehicleLibComboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please add vehicle lib first.");
				}
				
				String strVehicleLibString = (String) VehicleLibComboBox.getItemAt(VehicleLibComboBox.getSelectedIndex());
				NETDEV_LIB_INFO_S stVehicleLibInfo = mapVehicleLib.get(strVehicleLibString);
				NETDEV_DELETE_DB_FLAG_INFO_S stFlagInfo = new NETDEV_DELETE_DB_FLAG_INFO_S();
				stFlagInfo.bIsDeleteMember = 1;
				boolean bRet = netdevsdk.NETDEV_DeleteVehicleLibInfo(lpUserID, stVehicleLibInfo.udwID, stFlagInfo);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_DeleteVehicleLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}

		    	VehicleLibComboBox.removeItemAt(VehicleLibComboBox.getSelectedIndex());
		    	mapVehicleLib.remove(strVehicleLibString);
			}
		});
		btnVehicleLibDelete.setBounds(23, 119, 81, 23);
		VehicleLibManagePanel.add(btnVehicleLibDelete);
		
		JButton btnVehicleLibModify = new JButton("Modify");
		btnVehicleLibModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				String strVehicleLibName = JOptionPane.showInputDialog("Please input new vehicle lib name:");
				NETDEV_LIB_INFO_S stVehicleLibInfo = mapVehicleLib.get(VehicleLibComboBox.getItemAt(VehicleLibComboBox.getSelectedIndex()));
				Common.stringToByteArray(strVehicleLibName, stVehicleLibInfo.szName);
				stVehicleLibInfo.write();
				NETDEV_PERSON_LIB_LIST_S stVehicleLibList = new NETDEV_PERSON_LIB_LIST_S();
				stVehicleLibList.udwNum = 1;
				stVehicleLibList.pstLibInfo = stVehicleLibInfo.getPointer();
				boolean bRet = netdevsdk.NETDEV_ModifyVehicleLibInfo(lpUserID, stVehicleLibList);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_ModifyVehicleLibInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
		    	mapVehicleLib.remove(VehicleLibComboBox.getItemAt(VehicleLibComboBox.getSelectedIndex()));
		    	mapVehicleLib.put(strVehicleLibName, stVehicleLibInfo);
		    	int dwCurrentindex = VehicleLibComboBox.getSelectedIndex();
		    	VehicleLibComboBox.removeItemAt(dwCurrentindex);
		    	VehicleLibComboBox.insertItemAt(strVehicleLibName, dwCurrentindex);
			}
		});
		btnVehicleLibModify.setBounds(23, 165, 81, 23);
		VehicleLibManagePanel.add(btnVehicleLibModify);
		
		JButton btnVehicleLibFind = new JButton("Find");
		btnVehicleLibFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				VehicleLibComboBox.removeAllItems();
				mapVehicleLib.clear();
				
				boolean bRet = false;
				Pointer lpVehiclelibHandle = netdevsdk.NETDEV_FindVehicleLibList(lpUserID);
				if(lpVehiclelibHandle != null)
				{
					while(true)
					{
						NETDEV_LIB_INFO_S stVehicleLibInfo = new NETDEV_LIB_INFO_S();
						bRet = netdevsdk.NETDEV_FindNextVehicleLibInfo(lpVehiclelibHandle, stVehicleLibInfo);
						if(bRet == true)
						{
							mapVehicleLib.put(Common.byteArrayToString(stVehicleLibInfo.szName), stVehicleLibInfo);
							VehicleLibComboBox.addItem(Common.byteArrayToString(stVehicleLibInfo.szName));
						}
						else
						{
							break;
						}	
					}
					bRet = netdevsdk.NETDEV_FindCloseVehicleLibList(lpVehiclelibHandle);
	                if(bRet != true)
	                {
	                	System.out.printf("NETDEV_FindCloseVehicleLibList failed:%d\n", netdevsdk.NETDEV_GetLastError());
	                    return;
	                }
				}
			}
		});
		btnVehicleLibFind.setBounds(23, 216, 81, 23);
		VehicleLibManagePanel.add(btnVehicleLibFind);
		
		JPanel VehicleManagePanel = new JPanel();
		VehicleManagePanel.setBorder(new TitledBorder(null, "Vehicle manage", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		VehicleManagePanel.setBounds(140, 0, 470, 431);
		VehicleLibPanel.add(VehicleManagePanel);
		VehicleManagePanel.setLayout(null);
		
		JScrollPane VehicleScrollPane = new JScrollPane();
		VehicleScrollPane.setBounds(0, 20, 470, 252);
		VehicleManagePanel.add(VehicleScrollPane);
		
		VehicleTable = new JTable();
		VehicleTable.setModel(VehicleTableModle);
		VehicleScrollPane.setViewportView(VehicleTable);
		
		JButton btnAddVehicle = new JButton("Add");
		btnAddVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == VehicleLibComboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find person lib first.");
					return;
				}
				new VehicleOperateWindow(VEHICLE_OPERATE_WINDOW_EFFECT.VEHICLE_OPERATE_WINDOW_ADDVEHICLE);
			}
		});
		btnAddVehicle.setBounds(10, 287, 93, 23);
		VehicleManagePanel.add(btnAddVehicle);
		
		JButton btnDeleteVehicle = new JButton("Delete");
		btnDeleteVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(0 == VehicleTableModle.getRowCount()  || VehicleTable.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find vehicle or seletc vehicle first.");
					return;
				}
				
				
				String strVehiclePlateName = (String) VehicleTable.getValueAt(VehicleTable.getSelectedRow(), 0);
				NETDEV_VEHICLE_DETAIL_INFO_S stVehilceDetailInfo = mapVehicleInfo.get(strVehiclePlateName);
				NETDEV_VEHICLE_INFO_LIST_S stVehicleMemberList = new NETDEV_VEHICLE_INFO_LIST_S();
				stVehicleMemberList.udwVehicleNum = 1;
				stVehicleMemberList.pstMemberInfoList = stVehilceDetailInfo.getPointer();
				
				NETDEV_BATCH_OPERATOR_LIST_S stResutList = new NETDEV_BATCH_OPERATOR_LIST_S();
				stResutList.udwNum = 1;
				NETDEV_BATCH_OPERATOR_INFO_S stBatchList = new NETDEV_BATCH_OPERATOR_INFO_S();
				stResutList.pstBatchList = stBatchList.getPointer();
				
				/* 参数2目前默认填0生效 */
				boolean bRet = netdevsdk.NETDEV_DelVehicleMemberList(lpUserID, 0, stVehicleMemberList, stResutList);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_DeletePersonInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
			}
		});
		btnDeleteVehicle.setBounds(115, 287, 93, 23);
		VehicleManagePanel.add(btnDeleteVehicle);
		
		JButton btnVehicleModify = new JButton("Modify");
		btnVehicleModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == VehicleTableModle.getRowCount()  || VehicleTable.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find person or seletc person first.");
					return;
				}
				
				new VehicleOperateWindow(VEHICLE_OPERATE_WINDOW_EFFECT.VEHICLE_OPERATE_WINDOW_MODIFVEHICLE);
			}
		});
		btnVehicleModify.setBounds(223, 287, 93, 23);
		VehicleManagePanel.add(btnVehicleModify);
		
		JButton btnVehicleFind = new JButton("Find");
		btnVehicleFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				if(0 == VehicleLibComboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find vehicle lib first.");
					return;
				}
				
				VehicleTableModle.setRowCount(0);
				mapVehicleInfo.clear();
				
				
				NETDEV_LIB_INFO_S stVehicleLibInfo = mapVehicleLib.get(VehicleLibComboBox.getItemAt(VehicleLibComboBox.getSelectedIndex()));
				NETDEV_PERSON_QUERY_INFO_S stQueryInfo = new NETDEV_PERSON_QUERY_INFO_S();
		        stQueryInfo.udwNum = 0;
		        stQueryInfo.udwOffset =  0;
		        stQueryInfo.udwLimit = 16;
	
		        
				NETDEV_BATCH_OPERATE_BASIC_S stQueryResultInfo = new NETDEV_BATCH_OPERATE_BASIC_S();
				boolean bRet = false;
				
				
				while(true)
				{
					Pointer lpFindVehicleHandle = netdevsdk.NETDEV_FindVehicleMemberDetailList(lpUserID, stVehicleLibInfo.udwID, stQueryInfo, stQueryResultInfo);
		            if(lpFindVehicleHandle == null || stQueryResultInfo.udwTotal == 0)
		            {
		            	System.out.printf("NETDEV_FindVehicleMemberDetailList failed:%d\n", netdevsdk.NETDEV_GetLastError());
		                break;
		            }
		            else 
		            {
		            	stQueryInfo.udwOffset += stQueryInfo.udwLimit;
		            	while(true)
		            	{
		            		NETDEV_VEHICLE_DETAIL_INFO_S stVehicleMemberInfo = new NETDEV_VEHICLE_DETAIL_INFO_S();
		            		stVehicleMemberInfo.stPlateAttr = new NETDEV_PLATE_ATTR_INFO_S();
		            		stVehicleMemberInfo.stVehicleAttr = new NETDEV_VEHICLE_MEMBER_ATTR_S();
		            		stVehicleMemberInfo.stVehicleAttr.stVehicleImage = new NETDEV_FILE_INFO_S();
		            		stVehicleMemberInfo.stVehicleAttr.stVehicleImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
		            		stVehicleMemberInfo.stVehicleAttr.stVehicleImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
		            		
		            		bRet = netdevsdk.NETDEV_FindNextVehicleMemberDetail(lpFindVehicleHandle, stVehicleMemberInfo);
		            		if(bRet != true)
		            		{
		            			break;
		            		}
		            		else 
		            		{
		            			mapVehicleInfo.put(Common.byteArrayToString(stVehicleMemberInfo.stPlateAttr.szPlateNo), stVehicleMemberInfo);
		            			
		            			Vector<String> VehicleVector = new Vector<String>();
		            			VehicleVector.add(Common.byteArrayToString(stVehicleMemberInfo.stPlateAttr.szPlateNo));
		            			
		            			VehicleVector.add(Common.EnumNETDEV_PLATE_COLOR_EConventToString(stVehicleMemberInfo.stPlateAttr.udwColor));
		            			VehicleVector.add(Common.EnumNETDEV_PLATE_TYPE_EConventToString(stVehicleMemberInfo.stPlateAttr.udwType));
		            			VehicleVector.add(Common.EnumNETDEV_PLATE_COLOR_EConventToString(stVehicleMemberInfo.stVehicleAttr.udwColor));
		            			
		            			if(stVehicleMemberInfo.bIsMonitored == 0)
		            			{
		            				VehicleVector.add("Disable");
		            			}
		            			else
		            			{
		            				VehicleVector.add("Enable");
								}
		          
		            			VehicleTableModle.addRow(VehicleVector);	
							}
		            	}
		            	bRet = netdevsdk.NETDEV_FindCloseVehicleMemberDetail(lpFindVehicleHandle);
			            if(bRet == false)
			            {
			            	System.out.printf("NETDEV_FindCloseVehicleMemberDetail failed:%d", netdevsdk.NETDEV_GetLastError());
			            }
					}
				}
			}
		});
		btnVehicleFind.setBounds(340, 287, 93, 23);
		VehicleManagePanel.add(btnVehicleFind);
		
		JPanel VehicleMonitorPanel = new JPanel();
		tabVehicleList.addTab("VehicleMonitor", null, VehicleMonitorPanel, null);
		VehicleMonitorPanel.setLayout(null);
		
		JScrollPane VehicleMonitorScrollPane = new JScrollPane();
		VehicleMonitorScrollPane.setBounds(0, 0, 610, 205);
		VehicleMonitorPanel.add(VehicleMonitorScrollPane);
		
		VehicleMonitorTable = new JTable();
		VehicleMonitorTable.setModel(VehicleMonitorTableModel);
		VehicleMonitorScrollPane.setViewportView(VehicleMonitorTable);
		
		JButton btnAddVehicleMonitor = new JButton("Add");
		btnAddVehicleMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please log on to the device first.");
					return;
				}
				
				new VehicleMonitor(VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT.VEHICLE_MONITOR_OPERATE_WINDOW_ADDVEHICLEMONITOR);
			}
		});
		btnAddVehicleMonitor.setBounds(10, 223, 93, 23);
		VehicleMonitorPanel.add(btnAddVehicleMonitor);
		
		JButton btnDeleteVehicleMonitor = new JButton("Delete");
		btnDeleteVehicleMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == VehicleMonitorTable.getRowCount() || VehicleMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first、  or find monitor and select monitor!");
					return;
				}
				
				String strVehicleMonitorName = (String) VehicleMonitorTable.getValueAt(VehicleMonitorTable.getSelectedRow(), 0);
				NETDEV_MONITION_INFO_S stVehicleMonitorInfo = mapVehicleMonitorMap.get(strVehicleMonitorName);
				
				NETDEV_BATCH_OPERATOR_LIST_S stResultList = new NETDEV_BATCH_OPERATOR_LIST_S();
				stResultList.udwNum = 1;
				NETDEV_BATCH_OPERATOR_INFO_S stBatchList = new NETDEV_BATCH_OPERATOR_INFO_S();
				stBatchList.udwID = stVehicleMonitorInfo.udwID;
				stBatchList.write();
				stResultList.pstBatchList = stBatchList.getPointer();
				
				
				boolean bRet = netdevsdk.NETDEV_DeleteVehicleMonitorInfo(lpUserID, stResultList);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_DeleteVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
			}
		});
		btnDeleteVehicleMonitor.setBounds(124, 223, 93, 23);
		VehicleMonitorPanel.add(btnDeleteVehicleMonitor);
		
		JButton btnModifyVehicleMonitor = new JButton("Modify");
		btnModifyVehicleMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == VehicleMonitorTable.getRowCount() || VehicleMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first、find monitor and select monitor!");
					return;
				}
				
				new VehicleMonitor(VEHICLE_MONITOR_OPERATE_WINDOW_EFFECT.VEHICLE_MONITOR_OPERATE_WINDOW_MODIFYVEHICLEMONITOR);
			}
		});
		btnModifyVehicleMonitor.setBounds(241, 223, 93, 23);
		VehicleMonitorPanel.add(btnModifyVehicleMonitor);
		
		JButton btnFindVehicleMonitor = new JButton("Find");
		btnFindVehicleMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				mapVehicleMonitorMap.clear();
				VehicleMonitorTableModel.setRowCount(0);
				
				boolean bRet = false;
				NETDEV_MONITOR_QUERY_INFO_S stQueryInfo = new NETDEV_MONITOR_QUERY_INFO_S();
				stQueryInfo.udwLimit = 20;
				Pointer lpVehicleMonitorHandle = netdevsdk.NETDEV_FindVehicleMonitorList(lpUserID);
		        if(lpVehicleMonitorHandle == null)
		        {
		    		System.out.printf("NETDEV_FindVehicleMonitorList failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		        }
		        else 
		        {
					while(true)
					{
						NETDEV_MONITION_INFO_S stMonitorInfo = new NETDEV_MONITION_INFO_S();
						bRet = netdevsdk.NETDEV_FindNextVehicleMonitorInfo(lpVehicleMonitorHandle, stMonitorInfo);
		                if(bRet != true)
		                {
		                	System.out.printf("NETDEV_FindNextVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		                    break;
		                }
		                else 
		                {
		                	//显示查询数据
	            			Vector<String> VehicleMonitorVector = new Vector<String>();
	            			VehicleMonitorVector.add(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szName));
	            			VehicleMonitorVector.add(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szReason));
	            			if(stMonitorInfo.stMonitorRuleInfo.udwMonitorType == 0)
	            			{
	            				VehicleMonitorVector.add("match");
	            			}
	            			else
	            			{
	            				VehicleMonitorVector.add("not match");
							}
	            			if(stMonitorInfo.stMonitorRuleInfo.bEnabled == 1)
	            			{
	            				VehicleMonitorVector.add("enable");
	            			}
	            			else
	            			{
	            				VehicleMonitorVector.add("disable");
							}
	            			VehicleMonitorTableModel.addRow(VehicleMonitorVector);
	            			mapVehicleMonitorMap.put(Common.byteArrayToString(stMonitorInfo.stMonitorRuleInfo.szName), stMonitorInfo);
						}
					}
					bRet = netdevsdk.NETDEV_FindCloseVehicleMonitorList(lpVehicleMonitorHandle);
	                if(bRet != true)
	                {
	                	System.out.printf("NETDEV_FindCloseVehicleMonitorList failed:%d\n", netdevsdk.NETDEV_GetLastError());
	                    return;
	                }
				}
			}
		});
		btnFindVehicleMonitor.setBounds(364, 223, 93, 23);
		VehicleMonitorPanel.add(btnFindVehicleMonitor);
		
		JButton btnEnableVehicleMonitor = new JButton("Enable");
		btnEnableVehicleMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == VehicleMonitorTable.getRowCount() || VehicleMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first or find monitor ");
					return;
				}
				
				String strVehiecleMonitorName = (String) VehicleMonitorTable.getValueAt(VehicleMonitorTable.getSelectedRow(), 0);
				NETDEV_MONITION_INFO_S stVehicleMonitorInfo = mapVehicleMonitorMap.get(strVehiecleMonitorName);
				
				NETDEV_MONITION_RULE_INFO_S stMonitorRuleInfo = new NETDEV_MONITION_RULE_INFO_S();
				boolean bRet = netdevsdk.NETDEV_GetVehicleMonitorInfo(NetDemo.lpUserID, stVehicleMonitorInfo.udwID, stMonitorRuleInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_SetVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
				
				stMonitorRuleInfo.bEnabled = 1;
				
				
				bRet = netdevsdk.NETDEV_SetVehicleMonitorInfo(lpUserID, stVehicleMonitorInfo.udwID, stMonitorRuleInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_SetVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
			}
		});
		btnEnableVehicleMonitor.setBounds(10, 267, 93, 23);
		VehicleMonitorPanel.add(btnEnableVehicleMonitor);
		
		JButton btnNewButton = new JButton("Disable");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == VehicleMonitorTable.getRowCount() || VehicleMonitorTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Please Login device first or find monitor ");
					return;
				}
				
				String strVehiecleMonitorName = (String) VehicleMonitorTable.getValueAt(VehicleMonitorTable.getSelectedRow(), 0);
				NETDEV_MONITION_INFO_S stVehicleMonitorInfo = mapVehicleMonitorMap.get(strVehiecleMonitorName);
				
				NETDEV_MONITION_RULE_INFO_S stMonitorRuleInfo = new NETDEV_MONITION_RULE_INFO_S();
				boolean bRet = netdevsdk.NETDEV_GetVehicleMonitorInfo(NetDemo.lpUserID, stVehicleMonitorInfo.udwID, stMonitorRuleInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_SetVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
				
				stMonitorRuleInfo.bEnabled = 0;
				
				
			    bRet = netdevsdk.NETDEV_SetVehicleMonitorInfo(lpUserID, stVehicleMonitorInfo.udwID, stMonitorRuleInfo);
				if(false == bRet)
				{
		    		System.out.printf("NETDEV_SetVehicleMonitorInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
				}
			}
		});
		btnNewButton.setBounds(124, 267, 93, 23);
		VehicleMonitorPanel.add(btnNewButton);
		
		JPanel VehicleAlarmReportPanel = new JPanel();
		tabVehicleList.addTab("VehicleAlarm", null, VehicleAlarmReportPanel, null);
		VehicleAlarmReportPanel.setLayout(null);
		
		JPanel VehicleMatchAndrNotMatchPanel = new JPanel();
		VehicleMatchAndrNotMatchPanel.setBorder(new TitledBorder(null, "Vehicle match and not match", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		VehicleMatchAndrNotMatchPanel.setBounds(10, 10, 280, 410);
		VehicleAlarmReportPanel.add(VehicleMatchAndrNotMatchPanel);
		VehicleMatchAndrNotMatchPanel.setLayout(null);
		
		JButton btnVehicleMatchAndNotMatchRegister = new JButton("Register");
		btnVehicleMatchAndNotMatchRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please login the device first.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_SetVehicleAlarmCallBack(lpUserID, pfVehicleAlarmMessCB, lpUserID);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_SetVehicleAlarmCallBack failed:%d", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					NETDEV_LAPI_SUB_INFO_S stSubInfo = new NETDEV_LAPI_SUB_INFO_S();
					stSubInfo.udwType = 64;
					stSubInfo.udwLibIDNum = 0xffff;
					
					NETDEV_SUBSCRIBE_SUCC_INFO_S stSubSuccInfo = new NETDEV_SUBSCRIBE_SUCC_INFO_S();
					bRet = netdevsdk.NETDEV_SubscibeLapiAlarm(lpUserID, stSubInfo, stSubSuccInfo);
					if(false == bRet){
						System.out.printf("NETDEV_SubscibeLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
						return;
					}
				
					dwVehicleRecognizeMointerID = stSubSuccInfo.udwID;
					
				}
				else
				{
					bRet = netdevsdk.NETDEV_SetAlarmCallBack_V30(lpUserID, cbAlarmMessCallBack, null);
					if(false == bRet){
						System.out.printf("NETDEV_SetAlarmCallBack_V30 failed:%d", netdevsdk.NETDEV_GetLastError());
					}
				}
			}
		});
		btnVehicleMatchAndNotMatchRegister.setBounds(20, 20, 93, 23);
		VehicleMatchAndrNotMatchPanel.add(btnVehicleMatchAndNotMatchRegister);
		
		JButton btnCancelVehicleMatchAndNotMatchRegister = new JButton("Cancel");
		btnCancelVehicleMatchAndNotMatchRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please login the device first.");
					return;
				}
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					boolean bRet = netdevsdk.NETDEV_UnSubLapiAlarm(lpUserID, dwVehicleRecognizeMointerID);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_UnSubLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	dwVehicleRecognizeMointerID = -1;
				}
				else {
					boolean bRet = netdevsdk.NETDEV_SetVehicleAlarmCallBack(lpUserID, null, lpUserID);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_SetVehicleAlarmCallBack failed:%d", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
				}
			}
		});
		btnCancelVehicleMatchAndNotMatchRegister.setBounds(165, 20, 93, 23);
		VehicleMatchAndrNotMatchPanel.add(btnCancelVehicleMatchAndNotMatchRegister);
		
		JPanel VehicleStructAlarmPanel = new JPanel();
		VehicleStructAlarmPanel.setBorder(new TitledBorder(null, "Vehicle alarm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		VehicleStructAlarmPanel.setBounds(300, 10, 300, 410);
		VehicleAlarmReportPanel.add(VehicleStructAlarmPanel);
		VehicleStructAlarmPanel.setLayout(null);
		
		JButton btnVehicleAlarmRegister = new JButton("Register");
		btnVehicleAlarmRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID || 0 == ChannelID){
					JOptionPane.showMessageDialog(null, "Please select the device channel after successful login.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_SetStructAlarmCallBack(lpUserID, cbStructAlarmCallBack, lpUserID);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_SetStructAlarmCallBack failed:%d", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
				
				
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					NETDEV_LAPI_SUB_INFO_S stSubInfo = new NETDEV_LAPI_SUB_INFO_S();
					stSubInfo.udwType = 32;
					stSubInfo.udwLibIDNum = 0xffff;
					
					NETDEV_SUBSCRIBE_SUCC_INFO_S stSubSuccInfo = new NETDEV_SUBSCRIBE_SUCC_INFO_S();
					bRet = netdevsdk.NETDEV_SubscibeLapiAlarm(lpUserID, stSubInfo, stSubSuccInfo);
					if(false == bRet){
						System.out.printf("NETDEV_SubscibeLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
						return;
					}
				
					dwVehicleStructMointerID = stSubSuccInfo.udwID;
				}
				else
				{
					NETDEV_SUBSCRIBE_SMART_INFO_S stSubscribeInfo = new NETDEV_SUBSCRIBE_SMART_INFO_S();
					stSubscribeInfo.udwNum = 1;
					stSubscribeInfo.pudwSmartType = new Memory(4 * stSubscribeInfo.udwNum);
					stSubscribeInfo.pudwSmartType.setInt(0, NETDEV_SMART_ALARM_TYPE_E.NETDEV_SMART_ALARM_TYPE_VEHICLE_SNAP);
					
					NETDEV_SMART_INFO_S stSmartInfo = new NETDEV_SMART_INFO_S();
					stSmartInfo.dwChannelID = ChannelID;
					bRet = netdevsdk.NETDEV_SubscribeSmart(lpUserID, stSubscribeInfo, stSmartInfo);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_SubscribeSmart failed:%d", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	dwVehicleStructMointerID = stSmartInfo.udwSubscribeID;
				}
			}
		});
		btnVehicleAlarmRegister.setBounds(20, 20, 93, 23);
		VehicleStructAlarmPanel.add(btnVehicleAlarmRegister);
		
		JButton btnCancelVehicleAlarmRegister = new JButton("Cancel");
		btnCancelVehicleAlarmRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(DeviceTypeComboBox.getSelectedIndex() == 0){
					boolean bRet = netdevsdk.NETDEV_UnSubLapiAlarm(lpUserID, dwVehicleStructMointerID);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_UnSubLapiAlarm failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	dwVehicleStructMointerID = -1;
				}
				else
				{
					NETDEV_SMART_INFO_S stSmartInfo = new NETDEV_SMART_INFO_S();
					stSmartInfo.dwChannelID = ChannelID;
					stSmartInfo.udwSubscribeID = dwVehicleStructMointerID;
					boolean bRet = netdevsdk.NETDEV_UnsubscribeSmart(lpUserID, stSmartInfo);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_UnsubscribeSmart failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
				}
			}
		});
		btnCancelVehicleAlarmRegister.setBounds(175, 20, 93, 23);
		VehicleStructAlarmPanel.add(btnCancelVehicleAlarmRegister);
		
		JPanel VehicleAlarmRecord = new JPanel();
		tabVehicleList.addTab("VehicleAlarmRecord", null, VehicleAlarmRecord, null);
		VehicleAlarmRecord.setLayout(null);
		
		JLabel lblVehicleAlarmMonitorType = new JLabel("MinitorType");
		lblVehicleAlarmMonitorType.setBounds(10, 10, 66, 15);
		VehicleAlarmRecord.add(lblVehicleAlarmMonitorType);
		
		JComboBox<Object> VehicleAlarmMonitorTypeComboBox = new JComboBox<Object>();
		VehicleAlarmMonitorTypeComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"match", "not match"}));
		VehicleAlarmMonitorTypeComboBox.setBounds(86, 7, 90, 21);
		VehicleAlarmRecord.add(VehicleAlarmMonitorTypeComboBox);
		
		JLabel lblVehicleAlarmFindTime = new JLabel("Time");
		lblVehicleAlarmFindTime.setBounds(209, 10, 31, 15);
		VehicleAlarmRecord.add(lblVehicleAlarmFindTime);
		
		VehicleAlarmFindBeginTimeTextField = new JTextField();
		VehicleAlarmFindBeginTimeTextField.setBounds(304, 7, 126, 21);
		VehicleAlarmRecord.add(VehicleAlarmFindBeginTimeTextField);
		VehicleAlarmFindBeginTimeTextField.setColumns(10);
		VehicleAlarmFindBeginTimeTextField.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()-24*3600), DateFormat));
		
		JLabel lblzhi3 = new JLabel("-");
		lblzhi3.setBounds(440, 10, 16, 15);
		VehicleAlarmRecord.add(lblzhi3);
		
		VehicleAlarmEndTimetextField = new JTextField();
		VehicleAlarmEndTimetextField.setBounds(459, 7, 126, 21);
		VehicleAlarmRecord.add(VehicleAlarmEndTimetextField);
		VehicleAlarmEndTimetextField.setColumns(10);
		VehicleAlarmEndTimetextField.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()), DateFormat));
		
		JLabel lblVehicleAlarmBayonetName = new JLabel("BayonetName");
		lblVehicleAlarmBayonetName.setBounds(10, 40, 66, 15);
		VehicleAlarmRecord.add(lblVehicleAlarmBayonetName);
		
		VehicleAlarmBayonetName = new JTextField();
		VehicleAlarmBayonetName.setBounds(86, 37, 90, 21);
		VehicleAlarmRecord.add(VehicleAlarmBayonetName);
		VehicleAlarmBayonetName.setColumns(10);
		
		JLabel lblVehicleAlarmPlateNumber = new JLabel("PlateNumber");
		lblVehicleAlarmPlateNumber.setBounds(209, 40, 72, 15);
		VehicleAlarmRecord.add(lblVehicleAlarmPlateNumber);
		
		textFieldVehicleAlarmPlateNumber = new JTextField();
		textFieldVehicleAlarmPlateNumber.setBounds(304, 37, 85, 21);
		VehicleAlarmRecord.add(textFieldVehicleAlarmPlateNumber);
		textFieldVehicleAlarmPlateNumber.setColumns(10);
		
		JLabel lbllVehicleAlarmPlateColor = new JLabel("PlateColor");
		lbllVehicleAlarmPlateColor.setBounds(399, 40, 66, 15);
		VehicleAlarmRecord.add(lbllVehicleAlarmPlateColor);
		
		JComboBox<Object> comboBoxVehicleAlarmPlateColor = new JComboBox<Object>();
		comboBoxVehicleAlarmPlateColor.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BLACK", "WHITE", "GRAY", "RED", "BLUE", "YELLOW", "ORANGE", "BROWN", "GREEN", "PURPLE", "CYAN", "PINK", "TRANSPARENT", "SILVERYWHITE", "DARK", "LIGHT", "COLOURLESS", "YELLOWGREEN", "GRADUALGREEN"}));
		comboBoxVehicleAlarmPlateColor.setBounds(469, 37, 90, 21);
		VehicleAlarmRecord.add(comboBoxVehicleAlarmPlateColor);
		
		JLabel lblVehicleAlarmCarColor = new JLabel("CarColor");
		lblVehicleAlarmCarColor.setBounds(10, 74, 54, 15);
		VehicleAlarmRecord.add(lblVehicleAlarmCarColor);
		
		JComboBox<Object> VehicleAlarmCarColorcomboBox = new JComboBox<Object>();
		VehicleAlarmCarColorcomboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BLACK", "WHITE", "GRAY", "RED", "BLUE", "YELLOW", "ORANGE", "BROWN", "GREEN", "PURPLE", "CYAN", "PINK", "TRANSPARENT", "SILVERYWHITE", "DARK", "LIGHT", "COLOURLESS", "YELLOWGREEN", "GRADUALGREEN"}));
		VehicleAlarmCarColorcomboBox.setBounds(86, 71, 90, 21);
		VehicleAlarmRecord.add(VehicleAlarmCarColorcomboBox);
		
		JLabel lblVehicleAlarmMonitorReason = new JLabel("MonitorReason");
		lblVehicleAlarmMonitorReason.setBounds(209, 74, 85, 15);
		VehicleAlarmRecord.add(lblVehicleAlarmMonitorReason);
		
		JComboBox<Object> VehicleAlarmMonitorReasonComboBox = new JComboBox<Object>();
		VehicleAlarmMonitorReasonComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "Robbed car", "Stolen car", "Suspected vehicle", "Illegal vehicles", "Emergency check and control vehicle"}));
		VehicleAlarmMonitorReasonComboBox.setBounds(304, 68, 85, 21);
		VehicleAlarmRecord.add(VehicleAlarmMonitorReasonComboBox);
		
		JButton btnVehicleAlarmFind = new JButton("Query");
		btnVehicleAlarmFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable()
				{
					@Override
					public void run() {
						boolean bRet = false;
						NETDEV_ALARM_LOG_COND_LIST_S stFindCond = new NETDEV_ALARM_LOG_COND_LIST_S();
						NETDEV_SMART_ALARM_LOG_RESULT_INFO_S  stResultInfo = new NETDEV_SMART_ALARM_LOG_RESULT_INFO_S();
						
						stFindCond.dwFirstRow = 0;
						stFindCond.dwPageRow = 20;
						stFindCond.dwCondSize = 5;
						
						stFindCond.astCondition[0] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[1] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[2] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[3] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[4] = new NETDEV_QUERY_INFO_S();
						
						
						stFindCond.astCondition[0].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_VEH_DATA_TYPE;
						stFindCond.astCondition[0].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DIM_QUERY;
						Common.stringToByteArray("0", stFindCond.astCondition[0].szConditionData);
						
						stFindCond.astCondition[1].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_ALARMTYPE;
						stFindCond.astCondition[1].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
						if(VehicleAlarmMonitorTypeComboBox.getSelectedIndex() == 0)
						{
							Common.stringToByteArray("1022", stFindCond.astCondition[1].szConditionData);   //NETDEV_ALARM_SMART_VEHICLE_MATCH_LIST
						}
						else if(VehicleAlarmMonitorTypeComboBox.getSelectedIndex() == 1)
						{
							Common.stringToByteArray("1024", stFindCond.astCondition[1].szConditionData);   //NETDEV_ALARM_SMART_VEHICLE_MISMATCH_LIST
						}
						
						stFindCond.astCondition[2].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[2].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_LESS;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(VehicleAlarmFindBeginTimeTextField.getText(), DateFormat)), stFindCond.astCondition[2].szConditionData);
						
						stFindCond.astCondition[3].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[3].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_GREATER;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(VehicleAlarmEndTimetextField.getText(), DateFormat)), stFindCond.astCondition[3].szConditionData);
						
						stFindCond.astCondition[4].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[4].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DESC_ORDER;
						
						if(!VehicleAlarmBayonetName.getText().trim().equals(""))
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_CHNNAME;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DIM_QUERY;
							Common.stringToByteArray(VehicleAlarmBayonetName.getText(), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						if(!textFieldVehicleAlarmPlateNumber.getText().trim().equals(""))
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_PLATE_NUM;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DIM_QUERY;
							Common.stringToByteArray(textFieldVehicleAlarmPlateNumber.getText(), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						if(comboBoxVehicleAlarmPlateColor.getSelectedIndex() != 0)
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_PLATE_COLOR;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
							Common.stringToByteArray(String.valueOf(Common.StringConventToEnumNETDEV_PLATE_COLOR_E(comboBoxVehicleAlarmPlateColor.getSelectedItem().toString())), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						if(VehicleAlarmCarColorcomboBox.getSelectedIndex() != 0)
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_VEHICLE_COLOR;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
							Common.stringToByteArray(String.valueOf(Common.StringConventToEnumNETDEV_PLATE_COLOR_E(VehicleAlarmCarColorcomboBox.getSelectedItem().toString())), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
											
						if(VehicleAlarmMonitorReasonComboBox.getSelectedIndex() != 0)
						{
							
							
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_MONITOY_REASON;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
							Common.stringToByteArray(String.valueOf(Common.StringConventToEnumNETDEV_VEHICLE_MONITOR_TYPE_E(VehicleAlarmMonitorReasonComboBox.getSelectedItem().toString())), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						
						while(true)
						{
							Pointer lpFindVehicleRecordHandlePointer = netdevsdk.NETDEV_FindVehicleRecordInfoList(lpUserID, stFindCond, stResultInfo);
							if(lpFindVehicleRecordHandlePointer != null)
							{
								while(true)
								{
									NETDEV_VEHICLE_RECORD_INFO_S stRecordInfo = new NETDEV_VEHICLE_RECORD_INFO_S();
									stRecordInfo.stPlateAttr = new NETDEV_PLATE_ATTR_INFO_S();
									stRecordInfo.stVehAttr = new NETDEV_VEH_ATTR_S();
									stRecordInfo.stPlateImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stPlateImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stPlateImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									
									stRecordInfo.stVehicleImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stVehicleImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stVehicleImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									
									stRecordInfo.stPanoImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stPanoImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stPanoImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									
									stRecordInfo.stMonitorAlarmInfo = new NETDEV_MONITOR_ALARM_INFO_S();
									
									bRet = netdevsdk.NETDEV_FindNextVehicleRecordInfo(lpFindVehicleRecordHandlePointer, stRecordInfo);
									if(bRet == true)
									{
										//写文件
										String strTime = Common.getDate();
									    /* 车牌抓拍图片 */
										String strFileName = strPicturePath + strTime +"Plate.jpg";
										Common.savePicture(stRecordInfo.stPlateImage.pcData, stRecordInfo.stPlateImage.udwSize, strFileName);
										
//										 /* 车辆抓拍图片 */
//					                	strFileName = strPicturePath + strTime +"Vehicle.jpg";
//					                	Common.savePicture(stRecordInfo.stVehicleImage.pcData, stRecordInfo.stVehicleImage.udwSize, strFileName);
					                	
					                	/* 全景图 */
					                	NETDEV_FILE_INFO_S stFileInfo = new NETDEV_FILE_INFO_S();
						                stFileInfo.udwSize = 1048576;
						                stFileInfo.pcData = new Memory(stFileInfo.udwSize);
						                boolean iRet= netdevsdk.NETDEV_GetVehicleRecordImageInfo(lpUserID, stRecordInfo.udwRecordID, stFileInfo);
						                if(iRet == true)
						                {
							            	strFileName = strPicturePath + strTime +"VehiclePanoImage.jpg";
							            	Common.savePicture(stFileInfo.pcData, stFileInfo.udwSize, strFileName);
						                }
									}
									else 
									{
										break;
									}
								}
								bRet = netdevsdk.NETDEV_FindCloseVehicleRecordList(lpFindVehicleRecordHandlePointer);
						    	if(false == bRet){
						    		System.out.printf("NETDEV_FindCloseVehicleRecordList failed:%d\n", netdevsdk.NETDEV_GetLastError());
						    		return;
						    	}
							}
							if(stFindCond.dwFirstRow >= stResultInfo.udwTotal)
							{
								break;
							}
							stFindCond.dwFirstRow += stFindCond.dwPageRow;
						}
					}

				}).start();	
				
				JOptionPane.showMessageDialog(null, "find picture han been saved in local dir.");
			}
		});
		btnVehicleAlarmFind.setBounds(466, 70, 93, 23);
		VehicleAlarmRecord.add(btnVehicleAlarmFind);
		
		JPanel VehiclePassRecordPanel = new JPanel();
		tabVehicleList.addTab("VehiclePassRecord", null, VehiclePassRecordPanel, null);
		VehiclePassRecordPanel.setLayout(null);
		
		JLabel lblVehicleRecordPassTime = new JLabel("Queue time");
		lblVehicleRecordPassTime.setBounds(10, 10, 65, 15);
		VehiclePassRecordPanel.add(lblVehicleRecordPassTime);
		
		VehiclePassRecordBeginTimeTextField = new JTextField();
		VehiclePassRecordBeginTimeTextField.setBounds(99, 7, 122, 21);
		VehiclePassRecordPanel.add(VehiclePassRecordBeginTimeTextField);
		VehiclePassRecordBeginTimeTextField.setColumns(10);
		VehiclePassRecordBeginTimeTextField.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()-24*3600), DateFormat));
		
		JLabel lblzhi2 = new JLabel("-");
		lblzhi2.setBounds(227, 10, 13, 15);
		VehiclePassRecordPanel.add(lblzhi2);
		
		VehiclePassRecordEndTimeTextField = new JTextField();
		VehiclePassRecordEndTimeTextField.setBounds(239, 7, 122, 21);
		VehiclePassRecordPanel.add(VehiclePassRecordEndTimeTextField);
		VehiclePassRecordEndTimeTextField.setColumns(10);
		VehiclePassRecordEndTimeTextField.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()), DateFormat));
		
		JButton btnQueryVehiclePassRecord = new JButton("Query");
		btnQueryVehiclePassRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread(new Runnable()
				{
					@Override
					public void run() {
						boolean bRet = false;
						NETDEV_ALARM_LOG_COND_LIST_S stFindCond = new NETDEV_ALARM_LOG_COND_LIST_S();
						NETDEV_SMART_ALARM_LOG_RESULT_INFO_S  stResultInfo = new NETDEV_SMART_ALARM_LOG_RESULT_INFO_S();
						
						stFindCond.dwFirstRow = 0;
						stFindCond.dwPageRow = 16;
						stFindCond.dwCondSize = 4;
						
						stFindCond.astCondition[0] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[1] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[2] = new NETDEV_QUERY_INFO_S();
						stFindCond.astCondition[3] = new NETDEV_QUERY_INFO_S();

						
						
						stFindCond.astCondition[0].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_VEH_DATA_TYPE;
						stFindCond.astCondition[0].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DIM_QUERY;
						
						stFindCond.astCondition[1].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[1].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_LESS;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(VehiclePassRecordBeginTimeTextField.getText(), DateFormat)), stFindCond.astCondition[1].szConditionData);
			
						stFindCond.astCondition[2].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[2].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_NO_GREATER;
						Common.stringToByteArray(String.valueOf(Common.date2TimeStamp(VehiclePassRecordEndTimeTextField.getText(), DateFormat)), stFindCond.astCondition[2].szConditionData);
						
						stFindCond.astCondition[3].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_TIME;
						stFindCond.astCondition[3].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DESC_ORDER;
						
						if(!VehicleRecordVayonetNameTextField.getText().trim().equals(""))
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_CHNNAME;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DIM_QUERY;
							Common.stringToByteArray(VehicleRecordVayonetNameTextField.getText(), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						if(!VehicleRecordPlateNumberTextField.getText().trim().equals(""))
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_PLATE_NUM;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_DIM_QUERY;
							Common.stringToByteArray(VehicleRecordPlateNumberTextField.getText(), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						if(VehiclePassPlateColorComboBox.getSelectedIndex() != 0)
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_PLATE_COLOR;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
							Common.stringToByteArray(String.valueOf(Common.StringConventToEnumNETDEV_PLATE_COLOR_E(VehiclePassPlateColorComboBox.getSelectedItem().toString())), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						if(VehiclePassRecordCarColorComboBox.getSelectedIndex() != 0)
						{
							stFindCond.astCondition[stFindCond.dwCondSize] = new NETDEV_QUERY_INFO_S();
							stFindCond.astCondition[stFindCond.dwCondSize].dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_VEHICLE_COLOR;
							stFindCond.astCondition[stFindCond.dwCondSize].dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
							Common.stringToByteArray(String.valueOf(Common.StringConventToEnumNETDEV_PLATE_COLOR_E(VehiclePassRecordCarColorComboBox.getSelectedItem().toString())), stFindCond.astCondition[stFindCond.dwCondSize].szConditionData);
							
							stFindCond.dwCondSize++;
						}
						
						
						
						while(true)
						{
							Pointer lpFindVehicleRecordHandlePointer = netdevsdk.NETDEV_FindVehicleRecordInfoList(lpUserID, stFindCond, stResultInfo);
							if(lpFindVehicleRecordHandlePointer != null)
							{
								while(true)
								{
									NETDEV_VEHICLE_RECORD_INFO_S stRecordInfo = new NETDEV_VEHICLE_RECORD_INFO_S();
									stRecordInfo.stPlateAttr = new NETDEV_PLATE_ATTR_INFO_S();
									stRecordInfo.stVehAttr = new NETDEV_VEH_ATTR_S();
									stRecordInfo.stPlateImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stPlateImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stPlateImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									
									stRecordInfo.stVehicleImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stVehicleImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stVehicleImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									
									stRecordInfo.stPanoImage = new NETDEV_FILE_INFO_S();
									stRecordInfo.stPanoImage.udwSize = Common.NETDEMO_PICTURE_SIZE;
									stRecordInfo.stPanoImage.pcData = new Memory(Common.NETDEMO_PICTURE_SIZE);
									
									stRecordInfo.stMonitorAlarmInfo = new NETDEV_MONITOR_ALARM_INFO_S();
									
									bRet = netdevsdk.NETDEV_FindNextVehicleRecordInfo(lpFindVehicleRecordHandlePointer, stRecordInfo);
									if(bRet == true)
									{
										//写文件
										String strTime = Common.getDate();
									    /* 车牌抓拍图片 */
										String strFileName = strPicturePath + strTime +"Plate.jpg";
										Common.savePicture(stRecordInfo.stPlateImage.pcData, stRecordInfo.stPlateImage.udwSize, strFileName);
										
										 /* 车辆抓拍图片 */
//					                	strFileName = strPicturePath + strTime +"Vehicle.jpg";
//					                	Common.savePicture(stRecordInfo.stVehicleImage.pcData, stRecordInfo.stVehicleImage.udwSize, strFileName);
					                	
					                	/* 全景图 */
					                	NETDEV_FILE_INFO_S stFileInfo = new NETDEV_FILE_INFO_S();
						                stFileInfo.udwSize = 1048576;
						                stFileInfo.pcData = new Memory(stFileInfo.udwSize);
						                boolean iRet= netdevsdk.NETDEV_GetVehicleRecordImageInfo(lpUserID, stRecordInfo.udwRecordID, stFileInfo);
						                if(iRet == true)
						                {
							            	strFileName = strPicturePath + strTime +"VehiclePanoImage.jpg";
							            	Common.savePicture(stFileInfo.pcData, stFileInfo.udwSize, strFileName);
						                }
									}
									else 
									{
										break;
									}
								}
								bRet = netdevsdk.NETDEV_FindCloseVehicleRecordList(lpFindVehicleRecordHandlePointer);
						    	if(false == bRet){
						    		System.out.printf("NETDEV_FindCloseVehicleRecordList failed:%d\n", netdevsdk.NETDEV_GetLastError());
						    		return;
						    	}
							}
							if(stFindCond.dwFirstRow >= stResultInfo.udwTotal)
							{
								break;
							}
							stFindCond.dwFirstRow += stFindCond.dwPageRow;
						}
					}
			
			    }).start();	
				
				JOptionPane.showMessageDialog(null, "find picture han been saved in local dir.");
			}
		});
		btnQueryVehiclePassRecord.setBounds(458, 72, 93, 23);
		VehiclePassRecordPanel.add(btnQueryVehiclePassRecord);
		
		JLabel lblVehicleRecordVayonetName = new JLabel("VayonetName");
		lblVehicleRecordVayonetName.setBounds(366, 10, 83, 15);
		VehiclePassRecordPanel.add(lblVehicleRecordVayonetName);
		
		VehicleRecordVayonetNameTextField = new JTextField();
		VehicleRecordVayonetNameTextField.setBounds(451, 7, 149, 21);
		VehiclePassRecordPanel.add(VehicleRecordVayonetNameTextField);
		VehicleRecordVayonetNameTextField.setColumns(10);
		
		JLabel lblVehicleRecordPlateNumber = new JLabel("PlateNumber");
		lblVehicleRecordPlateNumber.setBounds(10, 39, 76, 15);
		VehiclePassRecordPanel.add(lblVehicleRecordPlateNumber);
		
		VehicleRecordPlateNumberTextField = new JTextField();
		VehicleRecordPlateNumberTextField.setBounds(97, 38, 124, 21);
		VehiclePassRecordPanel.add(VehicleRecordPlateNumberTextField);
		VehicleRecordPlateNumberTextField.setColumns(10);
		
		JLabel lblVehiclePassPlateColor = new JLabel("PlateColor");
		lblVehiclePassPlateColor.setBounds(237, 39, 65, 15);
		VehiclePassRecordPanel.add(lblVehiclePassPlateColor);
		

		VehiclePassPlateColorComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BLACK", "WHITE", "GRAY", "RED", "BLUE", "YELLOW", "ORANGE", "BROWN", "GREEN", "PURPLE", "CYAN", "PINK", "TRANSPARENT", "SILVERYWHITE", "DARK", "LIGHT", "COLOURLESS", "YELLOWGREEN", "GRADUALGREEN"}));
		VehiclePassPlateColorComboBox.setBounds(307, 38, 83, 21);
		VehiclePassRecordPanel.add(VehiclePassPlateColorComboBox);
		
		JLabel lblVehiclePassRecordCarColor = new JLabel("CarColor");
		lblVehiclePassRecordCarColor.setBounds(400, 39, 54, 15);
		VehiclePassRecordPanel.add(lblVehiclePassRecordCarColor);
		
		VehiclePassRecordCarColorComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"OTHER", "BLACK", "WHITE", "GRAY", "RED", "BLUE", "YELLOW", "ORANGE", "BROWN", "GREEN", "PURPLE", "CYAN", "PINK", "TRANSPARENT", "SILVERYWHITE", "DARK", "LIGHT", "COLOURLESS", "YELLOWGREEN", "GRADUALGREEN"}));
		VehiclePassRecordCarColorComboBox.setBounds(458, 38, 83, 21);
		VehiclePassRecordPanel.add(VehiclePassRecordCarColorComboBox);
		
		JPanel AccessControlPanel = new JPanel();
		AccessControlPanel.setToolTipText("");
		tabSmartList.addTab("AccessControl", null, AccessControlPanel, null);
		AccessControlPanel.setLayout(null);
		
		JTabbedPane AccessControlList = new JTabbedPane(JTabbedPane.TOP);
		AccessControlList.setToolTipText("");
		AccessControlList.setBounds(0, 0, 615, 456);
		AccessControlPanel.add(AccessControlList);
		
		JPanel AccessControlDataReportPanel = new JPanel();
		AccessControlList.addTab("DataReport", null, AccessControlDataReportPanel, null);
		
		JButton SubscibeAlarmBtn = new JButton("Subscibe Alarm");
		SubscibeAlarmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean bRet = netdevsdk.NETDEV_SetAlarmFGCallBack(lpUserID, pfAlarmMessFGCB, lpUserID);
			    if(true != bRet)
			    {
			        System.out.println("NETDEV_SetAlarmFGCallBack fail:" + netdevsdk.NETDEV_GetLastError());
			        return;
			    }
			    else
			    {
			    	System.out.println("NETDEV_SetAlarmFGCallBack success");
			    }

			    if(DeviceTypeComboBox.getSelectedIndex() == 0){/* IPC/NVR */
			    	NETDEV_LAPI_SUB_INFO_S stSubInfo = new NETDEV_LAPI_SUB_INFO_S();
				    NETDEV_SUBSCRIBE_SUCC_INFO_S stSubSuccInfo = new NETDEV_SUBSCRIBE_SUCC_INFO_S();

				    stSubInfo.udwType = 1024;
				    bRet = netdevsdk.NETDEV_SubscibeLapiAlarm(lpUserID, stSubInfo, stSubSuccInfo);
				    if(true != bRet)
				    {
				    	System.out.println("NETDEV_SubscibeLapiAlarm fail:" + netdevsdk.NETDEV_GetLastError());
				    }
				    else
				    {
				    	System.out.println("NETDEV_SubscibeLapiAlarm success");
				        dwAccessControlFGMointerID = stSubSuccInfo.udwID;
				    }
				}
				else
				{
					bRet = netdevsdk.NETDEV_SetAlarmCallBack_V30(lpUserID, cbAlarmMessCallBack, null);
					if(false == bRet){
						System.out.printf("NETDEV_SetAlarmCallBack_V30 failed:%d\n", netdevsdk.NETDEV_GetLastError());
					}
					
					System.out.println("NETDEV_SetAlarmCallBack_V30 success");
				}
			    
			}
		});
		AccessControlDataReportPanel.add(SubscibeAlarmBtn);
		
		JButton unSubscibeAlarmBtn = new JButton("unSubscibe Alarm");
		unSubscibeAlarmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(DeviceTypeComboBox.getSelectedIndex() == 0){/* IPC/NVR */
					boolean bRet = netdevsdk.NETDEV_UnSubLapiAlarm(lpUserID, dwAccessControlFGMointerID);
			    	if(false == bRet){
			    		System.out.println("NETDEV_UnSubLapiAlarm failed:" + netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
			    	
			    	System.out.println("NETDEV_UnSubLapiAlarm success");
			    	dwAccessControlFGMointerID = -1;
				}
				else
				{
					/* 一体机仅取消回调即可 */
				}
				
		    	netdevsdk.NETDEV_SetAlarmFGCallBack(lpUserID, null, lpUserID);
		    	System.out.println("NETDEV_SetAlarmFGCallBack null success");
			}
		});
		AccessControlDataReportPanel.add(unSubscibeAlarmBtn);
		
		JPanel AccessControlPersonPanel = new JPanel();
		AccessControlList.addTab("Person", null, AccessControlPersonPanel, null);
		AccessControlPersonPanel.setLayout(null);
		
		JPanel AccessControlDeptPanel = new JPanel();
		AccessControlDeptPanel.setBorder(new TitledBorder(null, "dept", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		AccessControlDeptPanel.setBounds(10, 10, 590, 76);
		AccessControlPersonPanel.add(AccessControlDeptPanel);
		AccessControlDeptPanel.setLayout(null);
		
		JComboBox<String> deptIDComboBox = new JComboBox<String>();
		deptIDComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					NETDEV_ORG_INFO_S orgInfo = ACPersonDeptMap.get(Integer.valueOf((String)deptIDComboBox.getSelectedItem()));
					deptNameTextField.setText(Common.byteArrayToString(orgInfo.szNodeName));
				  }
			}
		});
		deptIDComboBox.setBounds(113, 16, 87, 21);
		AccessControlDeptPanel.add(deptIDComboBox);
		
		JButton addDeptBtn = new JButton("Add");
		addDeptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(0 == deptIDComboBox.getItemCount())
				{
					return;
				}
				NETDEV_ORG_INFO_S pstOrgInfo = new NETDEV_ORG_INFO_S();
				pstOrgInfo.dwParentID = Integer.valueOf((String)deptIDComboBox.getSelectedItem());
				pstOrgInfo.dwType = NETDEV_ORG_TYPE_E.NETDEV_ORG_TYPE_DEPT;
				Common.stringToByteArray(addDeptNameTextField.getText(), pstOrgInfo.szNodeName);
				IntByReference pdwOrgID = new IntByReference();
				boolean bRet = netdevsdk.NETDEV_AddOrgInfo(lpUserID, pstOrgInfo, pdwOrgID);
				if(false == bRet)
				{
					System.out.println("NETDEV_AddOrgInfo fail, erron:" + netdevsdk.NETDEV_GetLastError());
					return;
				}
				pstOrgInfo.dwOrgID = pdwOrgID.getValue();
				ACPersonDeptMap.put(pstOrgInfo.dwOrgID, pstOrgInfo);
				deptIDComboBox.addItem(String.valueOf(pstOrgInfo.dwOrgID));
			}
		});
		addDeptBtn.setBounds(204, 43, 82, 23);
		AccessControlDeptPanel.add(addDeptBtn);
		
		JButton modifyDeptBtn = new JButton("Modify");
		modifyDeptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(0 == deptIDComboBox.getItemCount())
				{
					return;
				}
				 NETDEV_ORG_INFO_S pstOrgInfo = ACPersonDeptMap.get(Integer.valueOf((String)deptIDComboBox.getSelectedItem()));
				 if(null == pstOrgInfo)
				 {
					 return;
				 }
				 
				 Common.stringToByteArray(deptNameTextField.getText(), pstOrgInfo.szNodeName);
				boolean bRet = netdevsdk.NETDEV_ModifyOrgInfo(lpUserID, pstOrgInfo);
				if(false == bRet)
				{
					System.out.println("NETDEV_ModifyOrgInfo fail, erron:" + netdevsdk.NETDEV_GetLastError());
				}
			}
		});
		modifyDeptBtn.setBounds(396, 15, 87, 23);
		AccessControlDeptPanel.add(modifyDeptBtn);
		
		JButton deleteDeptBtn = new JButton("Delete");
		deleteDeptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(0 == deptIDComboBox.getItemCount())
				{
					return;
				}
				 NETDEV_ORG_INFO_S pstOrgInfo = ACPersonDeptMap.get(Integer.valueOf((String)deptIDComboBox.getSelectedItem()));
				 if(null == pstOrgInfo)
				 {
					 return;
				 }
				 
				 NETDEV_DEL_ORG_INFO_S pstOrgDelInfo = new NETDEV_DEL_ORG_INFO_S();
				 pstOrgDelInfo.dwOrgNum = 1;
				 pstOrgDelInfo.pdwOrgIDs = new Memory(pstOrgDelInfo.dwOrgNum * 4);
				 pstOrgDelInfo.pdwOrgIDs.setInt(0, pstOrgInfo.dwOrgID);
				 NETDEV_ORG_BATCH_DEL_INFO_S pstOrgDelResultInfo = new NETDEV_ORG_BATCH_DEL_INFO_S();
				 NETDEV_OPERATE_INFO_S operateInfo = new NETDEV_OPERATE_INFO_S();
				 operateInfo.write();
				 pstOrgDelResultInfo.dwNum = 1;
				 pstOrgDelResultInfo.pstResultInfo = operateInfo.getPointer();
				 
				 Common.stringToByteArray(deptNameTextField.getText(), pstOrgInfo.szNodeName);
				 boolean bRet = netdevsdk.NETDEV_BatchDeleteOrgInfo(lpUserID, pstOrgDelInfo, pstOrgDelResultInfo);
				if(false == bRet)
				{
					System.out.println("NETDEV_BatchDeleteOrgInfo fail, erron:" + netdevsdk.NETDEV_GetLastError());
					return;
				}
				
				deptIDComboBox.removeItem(deptIDComboBox.getSelectedItem());
			}
		});
		deleteDeptBtn.setBounds(493, 16, 87, 23);
		AccessControlDeptPanel.add(deleteDeptBtn);
		
		deptNameTextField = new JTextField();
		deptNameTextField.setBounds(205, 16, 181, 21);
		AccessControlDeptPanel.add(deptNameTextField);
		deptNameTextField.setColumns(10);
		
		JButton findDeptInfoBtn = new JButton("Find");
		findDeptInfoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				deptIDComboBox.removeAllItems();
				deptNameTextField.setText("");
				ACPersonDeptMap.clear();
				
				NETDEV_ORG_FIND_COND_S pstFindCond = new NETDEV_ORG_FIND_COND_S();
				pstFindCond.udwOrgType = NETDEV_ORG_TYPE_E.NETDEV_ORG_TYPE_DEPT;
				pstFindCond.udwFindType = NETDEV_ORG_FIND_MODE_E.NETDEV_ORG_FIND_MODE_TYPE;
				Pointer lpFindHandle = netdevsdk.NETDEV_FindOrgInfoList(lpUserID, pstFindCond);
				boolean bGetNextIsSuccess = true;
				while(bGetNextIsSuccess)
				{
					NETDEV_ORG_INFO_S pstOrgInfo = new NETDEV_ORG_INFO_S();
					bGetNextIsSuccess = netdevsdk.NETDEV_FindNextOrgInfo(lpFindHandle, pstOrgInfo);
					if(false == bGetNextIsSuccess)
					{
						break;
					}
					
					ACPersonDeptMap.put(pstOrgInfo.dwOrgID, pstOrgInfo);
					deptIDComboBox.addItem(String.valueOf(pstOrgInfo.dwOrgID));
				}
				
				netdevsdk.NETDEV_FindCloseOrgInfo(lpFindHandle);
			}
		});
		findDeptInfoBtn.setBounds(21, 15, 82, 23);
		AccessControlDeptPanel.add(findDeptInfoBtn);
		
		addDeptNameTextField = new JTextField();
		addDeptNameTextField.setBounds(21, 44, 173, 21);
		AccessControlDeptPanel.add(addDeptNameTextField);
		addDeptNameTextField.setColumns(10);
		
		JPanel AccessControlPersonInfoPanel = new JPanel();
		AccessControlPersonInfoPanel.setBorder(new TitledBorder(null, "Person Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		AccessControlPersonInfoPanel.setBounds(10, 111, 590, 306);
		AccessControlPersonPanel.add(AccessControlPersonInfoPanel);
		AccessControlPersonInfoPanel.setLayout(null);
		
		JScrollPane AccessControlPersonScrollPane = new JScrollPane();
		AccessControlPersonScrollPane.setBounds(10, 21, 570, 243);
		AccessControlPersonInfoPanel.add(AccessControlPersonScrollPane);
		
		AccessControlPersonTable = new JTable();
		AccessControlPersonTable.setModel(AccessControlPersonTableModel);
		AccessControlPersonScrollPane.setViewportView(AccessControlPersonTable);
		
		JButton FindACSPersonBtn = new JButton("Find");
		FindACSPersonBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				if(0 == deptIDComboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find ACS Person first.");
					return;
				}
				
				NETDEV_ORG_INFO_S pstOrgInfo = ACPersonDeptMap.get(Integer.valueOf((String)deptIDComboBox.getSelectedItem()));
				if(null == pstOrgInfo)
				{
					return;
				}
				
				dwFindACSPersonOffset = 0;
				findACSPersonInfo(pstOrgInfo.dwOrgID, dwFindACSPersonOffset);
			}
		});
		FindACSPersonBtn.setBounds(10, 274, 77, 23);
		AccessControlPersonInfoPanel.add(FindACSPersonBtn);
		
		JButton btnAddACSPerson = new JButton("Add");
		btnAddACSPerson.setBounds(97, 274, 77, 23);
		AccessControlPersonInfoPanel.add(btnAddACSPerson);
		
		JButton btnModifyACSPerson = new JButton("Modify");
		btnModifyACSPerson.setBounds(184, 274, 77, 23);
		AccessControlPersonInfoPanel.add(btnModifyACSPerson);
		
		JButton btnDeleteACSPerson = new JButton("Delete");
		btnDeleteACSPerson.setBounds(271, 274, 77, 23);
		AccessControlPersonInfoPanel.add(btnDeleteACSPerson);
		
		JButton btnNextACSPerson = new JButton("Next");
		btnNextACSPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				if(0 == deptIDComboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find ACS Person first.");
					return;
				}
				
				NETDEV_ORG_INFO_S pstOrgInfo = ACPersonDeptMap.get(Integer.valueOf((String)deptIDComboBox.getSelectedItem()));
				if(null == pstOrgInfo)
				{
					return;
				}
				
				dwFindACSPersonOffset += NETDEMO_FIND_ACS_PERSON_COUNT;
				findACSPersonInfo(pstOrgInfo.dwOrgID, dwFindACSPersonOffset);
			}
		});
		btnNextACSPerson.setBounds(503, 274, 77, 23);
		AccessControlPersonInfoPanel.add(btnNextACSPerson);
		
		JButton btnPreviousACSPerson = new JButton("Previous");
		btnPreviousACSPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				if(0 == deptIDComboBox.getItemCount())
				{
					JOptionPane.showMessageDialog(null, "Please find ACS Person first.");
					return;
				}
				
				NETDEV_ORG_INFO_S pstOrgInfo = ACPersonDeptMap.get(Integer.valueOf((String)deptIDComboBox.getSelectedItem()));
				if(null == pstOrgInfo)
				{
					return;
				}
				
				if(dwFindACSPersonOffset > NETDEMO_FIND_ACS_PERSON_COUNT)
			    {
					dwFindACSPersonOffset -= NETDEMO_FIND_ACS_PERSON_COUNT;
			    }
			    else {
			    	dwFindACSPersonOffset = 0;
			    }
				findACSPersonInfo(pstOrgInfo.dwOrgID, dwFindACSPersonOffset);
			}
		});
		btnPreviousACSPerson.setBounds(407, 274, 93, 23);
		AccessControlPersonInfoPanel.add(btnPreviousACSPerson);
		
		JPanel AccessControlPermissionsPanel = new JPanel();
		AccessControlList.addTab("Permissions", null, AccessControlPermissionsPanel, null);
		AccessControlPermissionsPanel.setLayout(null);
		
		JTabbedPane AccessControlPermissionsList = new JTabbedPane(JTabbedPane.TOP);
		AccessControlPermissionsList.setBounds(0, 0, 610, 427);
		AccessControlPermissionsPanel.add(AccessControlPermissionsList);
		
		JPanel AccessPermissionPanel = new JPanel();
		AccessControlPermissionsList.addTab("Access Permission", null, AccessPermissionPanel, null);
		AccessPermissionPanel.setLayout(null);
		
		JScrollPane AccessControlPermissionScrollPane = new JScrollPane();
		AccessControlPermissionScrollPane.setBounds(10, 10, 585, 238);
		AccessPermissionPanel.add(AccessControlPermissionScrollPane);
		
		AccessControlPermissionTable = new JTable();
		AccessControlPermissionTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Permission Name", "Access Period"
			}
		));
		AccessControlPermissionTable.getColumnModel().getColumn(0).setPreferredWidth(127);
		AccessControlPermissionTable.getColumnModel().getColumn(1).setPreferredWidth(112);
		AccessControlPermissionScrollPane.setViewportView(AccessControlPermissionTable);
		
		JButton FindPermissionBtn = new JButton("Find");
		FindPermissionBtn.setBounds(10, 259, 93, 23);
		AccessPermissionPanel.add(FindPermissionBtn);
		
		JButton AddPermissionBtn = new JButton("Add");
		AddPermissionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new AccessControlPermissionWindow(PERMISSION_OPERATE_WINDOW_EFFECT.PERMISSION_OPERATE_WINDOW_ADDPERSON, 0);
			}
		});
		AddPermissionBtn.setBounds(115, 259, 93, 23);
		AccessPermissionPanel.add(AddPermissionBtn);
		
		JButton ModifyPermissionBtn = new JButton("Modify");
		ModifyPermissionBtn.setBounds(218, 258, 93, 23);
		AccessPermissionPanel.add(ModifyPermissionBtn);
		
		JButton DeletePermissionBtn = new JButton("Delete");
		DeletePermissionBtn.setBounds(321, 258, 93, 23);
		AccessPermissionPanel.add(DeletePermissionBtn);
		
		JPanel PeopleCountingPanel = new JPanel();
		tabSmartList.addTab("People Counting", null, PeopleCountingPanel, null);
		PeopleCountingPanel.setLayout(null);
		
		JTabbedPane tabbedPanePerpleCounting = new JTabbedPane(JTabbedPane.TOP);
		tabbedPanePerpleCounting.setBounds(0, 0, 615, 466);
		PeopleCountingPanel.add(tabbedPanePerpleCounting);
		
		JPanel panelPeopleCountingIPC = new JPanel();
		tabbedPanePerpleCounting.addTab("IPC", null, panelPeopleCountingIPC, null);
		panelPeopleCountingIPC.setLayout(null);
		
		JButton btnPeopleCountingIPCRegister = new JButton("Register");
		btnPeopleCountingIPCRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				netdevsdk.NETDEV_SetPassengerFlowStatisticCallBack(lpUserID, cbPassengerFlowStatisticCallBack, null);
			}
		});
		btnPeopleCountingIPCRegister.setBounds(47, 324, 93, 23);
		panelPeopleCountingIPC.add(btnPeopleCountingIPCRegister);
		
		JButton btnPeopleCountingIPCCancel = new JButton("Cancel");
		btnPeopleCountingIPCCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				netdevsdk.NETDEV_SetPassengerFlowStatisticCallBack(lpUserID, null, null);
			}
		});
		btnPeopleCountingIPCCancel.setBounds(355, 324, 93, 23);
		panelPeopleCountingIPC.add(btnPeopleCountingIPCCancel);
		
		JScrollPane scrollPanePeopleCountingIPC = new JScrollPane();
		scrollPanePeopleCountingIPC.setBounds(10, 10, 590, 298);
		panelPeopleCountingIPC.add(scrollPanePeopleCountingIPC);
		
		tablePeopleCountingIPC = new JTable();
		tablePeopleCountingIPC.setModel(VCAPeopleCountingTableModel);
		scrollPanePeopleCountingIPC.setViewportView(tablePeopleCountingIPC);
		
		JPanel panelPeopleCountingVMSNVR = new JPanel();
		tabbedPanePerpleCounting.addTab("NVR/VMS", null, panelPeopleCountingVMSNVR, null);
		panelPeopleCountingVMSNVR.setLayout(null);
		
		JTabbedPane tabbedPeopleCountingVMSNVR = new JTabbedPane(JTabbedPane.TOP);
		tabbedPeopleCountingVMSNVR.setBounds(0, 0, 610, 437);
		panelPeopleCountingVMSNVR.add(tabbedPeopleCountingVMSNVR);
		
		JPanel panelPeopleCountingVMSNVRRealTime = new JPanel();
		tabbedPeopleCountingVMSNVR.addTab("RealTime", null, panelPeopleCountingVMSNVRRealTime, null);
		panelPeopleCountingVMSNVRRealTime.setLayout(null);
		
		JScrollPane scrollPanePeopleCountingVMSNVRRealTime = new JScrollPane();
		scrollPanePeopleCountingVMSNVRRealTime.setBounds(10, 10, 585, 317);
		panelPeopleCountingVMSNVRRealTime.add(scrollPanePeopleCountingVMSNVRRealTime);
		
		tablePeopleCountingVMSNVRRealTime = new JTable();
		tablePeopleCountingVMSNVRRealTime.setModel(PeopleCountingVMSNVRRealTimeTableModel);
		scrollPanePeopleCountingVMSNVRRealTime.setViewportView(tablePeopleCountingVMSNVRRealTime);
		
		JButton btnPeopleCountingVMSNVRRealTimeBegin = new JButton("Begin");
		btnPeopleCountingVMSNVRRealTimeBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				new Thread(new Runnable()
				{
					@Override
					public void run() {
						while(true)
						{
							boolean bRet = false;
							NETDEV_MULTI_TRAFFIC_STATISTICS_COND_S stStatisticCond = new NETDEV_MULTI_TRAFFIC_STATISTICS_COND_S();
							stStatisticCond.stChannelIDs = new NETDEV_OPERATE_LIST_S();
							stStatisticCond.stChannelIDs.pstOperateInfo = new Memory(4);
							stStatisticCond.stChannelIDs.pstOperateInfo.setInt(0, ChannelID);
							stStatisticCond.stChannelIDs.dwSize = 1;
							
							stStatisticCond.udwFormType = NETDEV_TRAFFIC_STATIC_FORM_TYPE_E.NETDEV_TRAFFIC_STAT_FORM_BY_DAY;
							stStatisticCond.udwStatisticsType = NETDEV_TRAFFIC_STATISTICS_TYPE_E.NETDEV_TRAFFIC_STATISTICS_TYPE_ALL;
							
							Calendar calendar = Calendar.getInstance();
							calendar.set(Calendar.HOUR_OF_DAY, 0);
							calendar.set(Calendar.HOUR, 0);
							calendar.set(Calendar.MINUTE, 0);
							calendar.set(Calendar.SECOND, 0);
							
							long now = System.currentTimeMillis() / 1000l;
							long daySecond = 60 * 60 * 24;
							stStatisticCond.tBeginTime = now - (now + 8 * 3600) % daySecond;;
							stStatisticCond.tEndTime = stStatisticCond.tBeginTime+24*3600;
							
							IntByReference udwSearchID = new IntByReference();		
						    bRet = netdevsdk.NETDEV_StartMultiTrafficStatistic(lpUserID, stStatisticCond, udwSearchID);
					    	if(false == bRet){
					    		System.out.printf("NETDEV_StartMultiTrafficStatistic failed:%d\n", netdevsdk.NETDEV_GetLastError());
					    		return;
					    	}
					    	
					    	/*查询统计进度 Query statistics progress */
					    	while(true)
					    	{
					    		IntByReference dwProgress = new IntByReference();	
					    		bRet = netdevsdk.NETDEV_GetTrafficStatisticProgress(lpUserID, udwSearchID.getValue(), dwProgress);
								if(dwProgress.getValue() == 100)
								{
									break;
								}
								else
								{
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
								}
					    	}
					    	
					    	Pointer lpStaticHandle = netdevsdk.NETDEV_FindTrafficStatisticInfoList(lpUserID, udwSearchID.getValue());
					    	if(lpStaticHandle != null)
					    	{
					    		while(true)
					    		{
					    			NETDEV_TRAFFIC_STATISTICS_INFO_S stStatisticInfo = new NETDEV_TRAFFIC_STATISTICS_INFO_S();
					    			bRet = netdevsdk.NETDEV_FindNextTrafficStatisticInfo(lpStaticHandle, stStatisticInfo);
					    			if(bRet != true)
					    			{
					    				break;
					    			}
					    			else
					    			{
				            			Vector<String> StatisticInfoVector = new Vector<String>();
				            			
				            			StatisticInfoVector.add(String.valueOf(stStatisticInfo.dwChannelID));
				            			StatisticInfoVector.add(String.valueOf(stStatisticInfo.audwEnterCount[0]));
				            			StatisticInfoVector.add(String.valueOf(stStatisticInfo.audwExitCount[0]));
				            			PeopleCountingVMSNVRRealTimeTableModel.addRow(StatisticInfoVector);
									}
					    		}
					    		bRet = netdevsdk.NETDEV_FindCloseTrafficStatisticInfo(lpStaticHandle);
						    	if(false == bRet){
						    		System.out.printf("NETDEV_FindCloseTrafficStatisticInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
						    		return;
						    	}
					    	}
							
							try {
								Thread.sleep(1000*20);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}).start();	
			}
		});
		btnPeopleCountingVMSNVRRealTimeBegin.setBounds(10, 349, 93, 23);
		panelPeopleCountingVMSNVRRealTime.add(btnPeopleCountingVMSNVRRealTimeBegin);
		
		JPanel panelPeopleCountingVMSNVRStatistical = new JPanel();
		tabbedPeopleCountingVMSNVR.addTab("Statistical", null, panelPeopleCountingVMSNVRStatistical, null);
		panelPeopleCountingVMSNVRStatistical.setLayout(null);
		
		JLabel lblPeopleCountingVMSNVRStatisticalReportType = new JLabel("ReportType");
		lblPeopleCountingVMSNVRStatisticalReportType.setBounds(10, 10, 72, 15);
		panelPeopleCountingVMSNVRStatistical.add(lblPeopleCountingVMSNVRStatisticalReportType);
		
		JComboBox<Object> comboBoxPeopleCountingVMSNVRStatisticalReportType = new JComboBox<Object>();
		comboBoxPeopleCountingVMSNVRStatisticalReportType.setModel(new DefaultComboBoxModel<Object>(new String[] {"Minute", "Hour", "Day", "Month"}));
		comboBoxPeopleCountingVMSNVRStatisticalReportType.setBounds(92, 7, 77, 21);
		panelPeopleCountingVMSNVRStatistical.add(comboBoxPeopleCountingVMSNVRStatisticalReportType);
		
		textFieldPeopleCountingVMSNVRStatisticsBeginTime = new JTextField();
		textFieldPeopleCountingVMSNVRStatisticsBeginTime.setBounds(179, 7, 123, 21);
		panelPeopleCountingVMSNVRStatistical.add(textFieldPeopleCountingVMSNVRStatisticsBeginTime);
		textFieldPeopleCountingVMSNVRStatisticsBeginTime.setColumns(10);
		textFieldPeopleCountingVMSNVRStatisticsBeginTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()-24*3600), DateFormat));
		
		textFieldPeopleCountingVMSNVRStattisticsEndTime = new JTextField();
		textFieldPeopleCountingVMSNVRStattisticsEndTime.setBounds(325, 7, 123, 21);
		panelPeopleCountingVMSNVRStatistical.add(textFieldPeopleCountingVMSNVRStattisticsEndTime);
		textFieldPeopleCountingVMSNVRStattisticsEndTime.setColumns(10);
		textFieldPeopleCountingVMSNVRStattisticsEndTime.setText(Common.timeStamp2Date(String.valueOf(Common.timeStamp()), DateFormat));
		
		JLabel lblPeopleCountingVMSNVRZhi = new JLabel("-");
		lblPeopleCountingVMSNVRZhi.setBounds(309, 10, 14, 15);
		panelPeopleCountingVMSNVRStatistical.add(lblPeopleCountingVMSNVRZhi);
		
		JButton btnPeopleCountingVMSNVRStatistics = new JButton("Statistics");
		btnPeopleCountingVMSNVRStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				PeopleCountingVMSNVRStatistalTableModel.setRowCount(0);
				
				boolean bRet = false;
				NETDEV_MULTI_TRAFFIC_STATISTICS_COND_S stStatisticCond = new NETDEV_MULTI_TRAFFIC_STATISTICS_COND_S();
				stStatisticCond.stChannelIDs = new NETDEV_OPERATE_LIST_S();
				stStatisticCond.stChannelIDs.pstOperateInfo = new Memory(4);
				stStatisticCond.stChannelIDs.pstOperateInfo.setInt(0, ChannelID);
				stStatisticCond.stChannelIDs.dwSize = 1;
				
				stStatisticCond.udwFormType = comboBoxPeopleCountingVMSNVRStatisticalReportType.getSelectedIndex();
				stStatisticCond.udwStatisticsType = NETDEV_TRAFFIC_STATISTICS_TYPE_E.NETDEV_TRAFFIC_STATISTICS_TYPE_ALL;
				
				stStatisticCond.tBeginTime = Common.date2TimeStamp(textFieldPeopleCountingVMSNVRStatisticsBeginTime.getText(), DateFormat);
				stStatisticCond.tEndTime = Common.date2TimeStamp(textFieldPeopleCountingVMSNVRStattisticsEndTime.getText(), DateFormat);
				
				IntByReference udwSearchID = new IntByReference();		
			    bRet = netdevsdk.NETDEV_StartMultiTrafficStatistic(lpUserID, stStatisticCond, udwSearchID);
		    	if(false == bRet){
		    		System.out.printf("NETDEV_StartMultiTrafficStatistic failed:%d\n", netdevsdk.NETDEV_GetLastError());
		    		return;
		    	}
		    	
		    	/*查询统计进度 Query statistics progress */
		    	while(true)
		    	{
		    		IntByReference dwProgress = new IntByReference();	
		    		bRet = netdevsdk.NETDEV_GetTrafficStatisticProgress(lpUserID, udwSearchID.getValue(), dwProgress);
					if(dwProgress.getValue() == 100)
					{
						break;
					}
					else
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
		    	}
		    	
		    	Pointer lpStaticHandle = netdevsdk.NETDEV_FindTrafficStatisticInfoList(lpUserID, udwSearchID.getValue());
		    	if(lpStaticHandle != null)
		    	{
		    		while(true)
		    		{
		    			NETDEV_TRAFFIC_STATISTICS_INFO_S stStatisticInfo = new NETDEV_TRAFFIC_STATISTICS_INFO_S();
		    			bRet = netdevsdk.NETDEV_FindNextTrafficStatisticInfo(lpStaticHandle, stStatisticInfo);
		    			if(bRet != true)
		    			{
		    				break;
		    			}
		    			else
		    			{
		    				for(int i = 0; i < stStatisticInfo.udwSize; i++)
		    				{
		            			Vector<String> StatisticInfoVector = new Vector<String>();
		            			
		            			StatisticInfoVector.add(String.valueOf(stStatisticInfo.dwChannelID));
		            			StatisticInfoVector.add(String.valueOf(i));
		            			StatisticInfoVector.add(String.valueOf(stStatisticInfo.audwEnterCount[i]));
		            			StatisticInfoVector.add(String.valueOf(stStatisticInfo.audwExitCount[i]));
		            			PeopleCountingVMSNVRStatistalTableModel.addRow(StatisticInfoVector);
		    				}
						}
		    		}
		    		bRet = netdevsdk.NETDEV_FindCloseTrafficStatisticInfo(lpStaticHandle);
			    	if(false == bRet){
			    		System.out.printf("NETDEV_FindCloseTrafficStatisticInfo failed:%d\n", netdevsdk.NETDEV_GetLastError());
			    		return;
			    	}
		    	}
			}
		});
		btnPeopleCountingVMSNVRStatistics.setBounds(473, 6, 93, 23);
		panelPeopleCountingVMSNVRStatistical.add(btnPeopleCountingVMSNVRStatistics);
		
		JScrollPane scrollPanePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType = new JScrollPane();
		scrollPanePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType.setBounds(10, 57, 585, 292);
		panelPeopleCountingVMSNVRStatistical.add(scrollPanePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType);
		
		tablePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType = new JTable();
		tablePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType.setModel(PeopleCountingVMSNVRStatistalTableModel);
		scrollPanePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType.setViewportView(tablePeopleCountingVMSNVRPeopleCountingVMSNVRStatisticalReportType);
		
		JPanel AlarmPanel = new JPanel();
		tabFunList.addTab("Alarm", null, AlarmPanel, null);
		AlarmPanel.setLayout(null);
		
		JScrollPane AlarmScrollPane = new JScrollPane();
		AlarmScrollPane.setEnabled(false);
		AlarmScrollPane.setBounds(10, 5, 598, 428);
		AlarmPanel.add(AlarmScrollPane);
		
		AlarmTable = new JTable();
		AlarmTable.setModel(AlarmTableModel);
		AlarmTable.setEnabled(false);
		AlarmScrollPane.setViewportView(AlarmTable);
		
		JButton btnSubscibeAlarm = new JButton("Subscibe");
		btnSubscibeAlarm.setBounds(20, 443, 131, 28);
		AlarmPanel.add(btnSubscibeAlarm);
		
		JButton btnUnSubAlarm = new JButton("UnSubscribe");
		btnUnSubAlarm.setBounds(173, 443, 131, 28);
		AlarmPanel.add(btnUnSubAlarm);
		
		JButton btnClearAlarm = new JButton("Clear");
		btnClearAlarm.setBounds(465, 443, 131, 28);
		AlarmPanel.add(btnClearAlarm);
		
		JPanel ConfigPanel = new JPanel();
		ConfigPanel.setToolTipText("");
		tabFunList.addTab("Configure", null, ConfigPanel, null);
		ConfigPanel.setLayout(null);
		
		JTabbedPane tabConfigList = new JTabbedPane(JTabbedPane.TOP);
		tabConfigList.setBounds(0, 0, 618, 481);
		ConfigPanel.add(tabConfigList);
		
		JPanel panelConfigBasic = new JPanel();
		tabConfigList.addTab("Basic", null, panelConfigBasic, null);
		panelConfigBasic.setLayout(null);
		
		JPanel panelConfigBasicSystemTime = new JPanel();
		panelConfigBasicSystemTime.setBorder(new TitledBorder(null, "System Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigBasicSystemTime.setBounds(10, 10, 593, 62);
		panelConfigBasic.add(panelConfigBasicSystemTime);
		panelConfigBasicSystemTime.setLayout(null);
		
		JComboBox<String> comboBoxBasicTimeZone = new JComboBox<String>();
		comboBoxBasicTimeZone.setBounds(10, 20, 247, 21);
		panelConfigBasicSystemTime.add(comboBoxBasicTimeZone);
		comboBoxBasicTimeZone.insertItemAt("GMT-12:00 International Date Line West", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W1200);
		comboBoxBasicTimeZone.insertItemAt("GMT-11:00 Midway Island, Samoa", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W1100);
		comboBoxBasicTimeZone.insertItemAt("GMT-10:00 Hawaii", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W1000);
		comboBoxBasicTimeZone.insertItemAt("GMT-09:00 Alaska", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0900);
		comboBoxBasicTimeZone.insertItemAt("GMT-08:00 Pacific Time (U.S. & Canada)", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0800);
		comboBoxBasicTimeZone.insertItemAt("GMT-07:00 Mountain Time (U.S. & Canada)", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0700);
		comboBoxBasicTimeZone.insertItemAt("GMT-06:00 Central Time (U.S. & Canada)", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0600);
		comboBoxBasicTimeZone.insertItemAt("GMT-05:00 Eastern Time (U.S. & Canada)", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0500);
		comboBoxBasicTimeZone.insertItemAt("GMT-04:30 Caracas", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0430);
		comboBoxBasicTimeZone.insertItemAt("GMT-04:00 Atlantic Time (Canada)", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0400);
		comboBoxBasicTimeZone.insertItemAt("GMT-03:30 Newfoundland", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0330);
		comboBoxBasicTimeZone.insertItemAt("GMT-03:00 Georgetown, Brasilia", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0300);
		comboBoxBasicTimeZone.insertItemAt("GMT-02:00 Mid-Atlantic", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0200);
		comboBoxBasicTimeZone.insertItemAt("GMT-01:00 Cape verde Islands, Azores", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_W0100);
		comboBoxBasicTimeZone.insertItemAt("GMT+00:00 Dublin, Edinburgh, London", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_0000);
		comboBoxBasicTimeZone.insertItemAt("GMT+01:00 Amsterdam, Berlin, Rome, Paris", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0100);
		comboBoxBasicTimeZone.insertItemAt("GMT+02:00 Athens, Jerusalem, Istanbul", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0200);
		comboBoxBasicTimeZone.insertItemAt("GMT+03:00 Baghdad, Kuwait, Moscow", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0300);
		comboBoxBasicTimeZone.insertItemAt("GMT+03:30 Tehran", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0330);
		comboBoxBasicTimeZone.insertItemAt("GMT+04:00 Caucasus Standard Time", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0400);
		comboBoxBasicTimeZone.insertItemAt("GMT+04:30 Kabul", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0430);
		comboBoxBasicTimeZone.insertItemAt("GMT+05:00 Islamabad, Karachi, Tashkent", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0500);
		comboBoxBasicTimeZone.insertItemAt("GMT+05:30 Madras, Bombay, New Delhi", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0530);
		comboBoxBasicTimeZone.insertItemAt("GMT+05:45 Kathmandu", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0545);
		comboBoxBasicTimeZone.insertItemAt("GMT+06:00 Almaty, Novosibirsk, Dhaka", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0600);
		comboBoxBasicTimeZone.insertItemAt("GMT+06:30 Yangon", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0630);
		comboBoxBasicTimeZone.insertItemAt("GMT+07:00 Bangkok, Hanoi, Jakarta", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0700);
		comboBoxBasicTimeZone.insertItemAt("GMT+08:00 Beijing, Hong Kong, Urumqi, Singapore, Taipei", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0800);
		comboBoxBasicTimeZone.insertItemAt("GMT+09:00 Seoul, Tokyo, Osaka, Sapporo", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0900);
		comboBoxBasicTimeZone.insertItemAt("GMT+09:30 Adelaide, Darwin", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E0930);
		comboBoxBasicTimeZone.insertItemAt("GMT+10:00 Melbourne, Sydney, Canberra", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E1000);
		comboBoxBasicTimeZone.insertItemAt("GMT+11:00 Magadan, Solomon Islands", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E1100);
		comboBoxBasicTimeZone.insertItemAt("GMT+12:00 Auckland, Wellington", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E1200);
		comboBoxBasicTimeZone.insertItemAt("GMT+13:00 Nuku'alofa", NETDEV_TIME_ZONE_E.NETDEV_TIME_ZONE_E1300);
		
		textFieldBasicTime = new JTextField();
		textFieldBasicTime.setBounds(267, 20, 155, 21);
		panelConfigBasicSystemTime.add(textFieldBasicTime);
		textFieldBasicTime.setColumns(10);
		
		JButton btnBasicGetSystemTime = new JButton("Get");
		btnBasicGetSystemTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				NETDEV_TIME_CFG_S stTimeCfg = new NETDEV_TIME_CFG_S();
				boolean bRet = netdevsdk.NETDEV_GetSystemTimeCfg(lpUserID, stTimeCfg);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetSystemTimeCfg failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                comboBoxBasicTimeZone.setSelectedIndex(stTimeCfg.dwTimeZone);
                String strTime = "" + stTimeCfg.stTime.dwYear+"-";
                if(stTimeCfg.stTime.dwMonth < 10)
                {
                	strTime+= "0";
                }
                strTime = strTime + stTimeCfg.stTime.dwMonth + "-";
                
                if(stTimeCfg.stTime.dwDay < 10)
                {
                	strTime+= "0";
                }
                strTime = strTime + stTimeCfg.stTime.dwDay + " ";
                
                if(stTimeCfg.stTime.dwHour < 10)
                {
                	strTime+= "0";
                }
                strTime = strTime + stTimeCfg.stTime.dwHour + ":";
                
                if(stTimeCfg.stTime.dwMinute < 10)
                {
                	strTime+= "0";
                }
                strTime = strTime + stTimeCfg.stTime.dwMinute + ":";
                
                if(stTimeCfg.stTime.dwSecond < 10)
                {
                	strTime+= "0";
                }
                strTime = strTime + stTimeCfg.stTime.dwSecond;
                
                textFieldBasicTime.setText(strTime);
                
				
			}
		});
		btnBasicGetSystemTime.setBounds(432, 19, 62, 23);
		panelConfigBasicSystemTime.add(btnBasicGetSystemTime);
		
		JButton btnBasicSaveSystemTime = new JButton("Save");
		btnBasicSaveSystemTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null != lpPlayHandle){
					JOptionPane.showMessageDialog(null, "Please stop playing first.");
					return;
				}
				
				NETDEV_TIME_CFG_S stTimeCfg = new NETDEV_TIME_CFG_S();
				
				String strTime = textFieldBasicTime.getText();

				stTimeCfg.stTime.dwYear = Integer.parseInt(strTime.substring(0, 4));
				stTimeCfg.stTime.dwMonth = Integer.parseInt(strTime.substring(5, 7));
				stTimeCfg.stTime.dwDay = Integer.parseInt(strTime.substring(8, 10));
				stTimeCfg.stTime.dwHour = Integer.parseInt(strTime.substring(11, 13));
				stTimeCfg.stTime.dwMinute = Integer.parseInt(strTime.substring(14, 16));
				stTimeCfg.stTime.dwSecond = Integer.parseInt(strTime.substring(17, 19));
				
				stTimeCfg.dwTimeZone = comboBoxBasicTimeZone.getSelectedIndex();
				
				boolean bRet = netdevsdk.NETDEV_SetSystemTimeCfg(lpUserID, stTimeCfg);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetSystemTimeCfg failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }

				
			}
		});
		btnBasicSaveSystemTime.setBounds(521, 19, 62, 23);
		panelConfigBasicSystemTime.add(btnBasicSaveSystemTime);
		
		JPanel panelConfigBasicDeviceName = new JPanel();
		panelConfigBasicDeviceName.setBorder(new TitledBorder(null, "Device Name", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigBasicDeviceName.setBounds(10, 96, 593, 56);
		panelConfigBasic.add(panelConfigBasicDeviceName);
		panelConfigBasicDeviceName.setLayout(null);
		
		textFieldConfigDeviceName = new JTextField();
		textFieldConfigDeviceName.setBounds(10, 25, 408, 21);
		panelConfigBasicDeviceName.add(textFieldConfigDeviceName);
		textFieldConfigDeviceName.setColumns(10);
		
		JButton btnConfigGetDeviceName = new JButton("Get");
		btnConfigGetDeviceName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}

				NETDEV_DEVICE_BASICINFO_S stDeviceInfo = new NETDEV_DEVICE_BASICINFO_S();
				IntByReference dwBytesReturned = new IntByReference();

				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_DEVICECFG, stDeviceInfo.getPointer(), 800, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stDeviceInfo.read();
                textFieldConfigDeviceName.setText(Common.byteArrayToString(stDeviceInfo.szDeviceName));
			}
		});
		btnConfigGetDeviceName.setBounds(428, 24, 60, 23);
		panelConfigBasicDeviceName.add(btnConfigGetDeviceName);
		
		JButton btnConfigDeviceNameSet = new JButton("Save");
		btnConfigDeviceNameSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				String strDeviceNameString = textFieldConfigDeviceName.getText();
				
				boolean bRet = netdevsdk.NETDEV_ModifyDeviceName(lpUserID, strDeviceNameString);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_ModifyDeviceName failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigDeviceNameSet.setBounds(509, 24, 60, 23);
		panelConfigBasicDeviceName.add(btnConfigDeviceNameSet);
		
		JPanel panelConfigBasicHardDisk = new JPanel();
		panelConfigBasicHardDisk.setBorder(new TitledBorder(null, "Hard Disk", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigBasicHardDisk.setBounds(10, 162, 593, 204);
		panelConfigBasic.add(panelConfigBasicHardDisk);
		panelConfigBasicHardDisk.setLayout(null);
		
		JScrollPane scrollPaneConfigBasicHardDisk = new JScrollPane();
		scrollPaneConfigBasicHardDisk.setBounds(10, 21, 573, 125);
		panelConfigBasicHardDisk.add(scrollPaneConfigBasicHardDisk);
		
		tableConfigBasicHardDisk = new JTable();
		tableConfigBasicHardDisk.setModel(ConfigBasicHardDiskTableModel);
		scrollPaneConfigBasicHardDisk.setViewportView(tableConfigBasicHardDisk);
		
		JButton btnConfigBasicHardDiskRefresh = new JButton("Refresh");
		btnConfigBasicHardDiskRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_DISK_INFO_LIST_S stDiskInfoList = new NETDEV_DISK_INFO_LIST_S();
				for(int i = 0; i < NetDEVSDKLib.NETDEV_DISK_MAX_NUM; i++)
				{
					stDiskInfoList.astDisksInfo[i] = new NETDEV_DISK_INFO_S();
				}
				stDiskInfoList.write();
				
				IntByReference dwBytesReturned = new IntByReference();

				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_DISKSINFO, stDiskInfoList.getPointer(), 13316, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stDiskInfoList.read();
                
				for(int i=0; i<stDiskInfoList.dwSize; i++)
				{
					Vector<String> vector = new Vector<String>();
			        vector.add(String.valueOf(i+1));
			        vector.add(String.valueOf(stDiskInfoList.astDisksInfo[i].dwTotalCapacity));
			        vector.add(String.valueOf(stDiskInfoList.astDisksInfo[i].dwUsedCapacity));
			        vector.add(Common.EnumNETDEV_DISK_WORK_STATUS_EConventToString(stDiskInfoList.astDisksInfo[i].enStatus));
			        vector.add(Common.byteArrayToString(stDiskInfoList.astDisksInfo[i].szManufacturer));

			        
			        ConfigBasicHardDiskTableModel.insertRow(i,vector);
			        
				}
				

			}
		});
		btnConfigBasicHardDiskRefresh.setBounds(496, 171, 75, 23);
		panelConfigBasicHardDisk.add(btnConfigBasicHardDiskRefresh);
		
		JPanel panelConfigNetwork = new JPanel();
		tabConfigList.addTab("Network", null, panelConfigNetwork, null);
		panelConfigNetwork.setLayout(null);
		
		JPanel panelConfigNetWorkNet = new JPanel();
		panelConfigNetWorkNet.setBorder(new TitledBorder(null, "Net", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigNetWorkNet.setBounds(10, 10, 283, 228);
		panelConfigNetwork.add(panelConfigNetWorkNet);
		panelConfigNetWorkNet.setLayout(null);
		
		JLabel lblConfigNetWorkDHCP = new JLabel("DHCP");
		lblConfigNetWorkDHCP.setBounds(10, 19, 34, 15);
		panelConfigNetWorkNet.add(lblConfigNetWorkDHCP);
		
		JCheckBox chckbxConfigNetWorkDHCPEnable = new JCheckBox("Enable");
		chckbxConfigNetWorkDHCPEnable.setBounds(93, 15, 66, 23);
		panelConfigNetWorkNet.add(chckbxConfigNetWorkDHCPEnable);
		
		JLabel lblConfigNetWorkIPAddr = new JLabel("IP Address");
		lblConfigNetWorkIPAddr.setBounds(10, 44, 73, 15);
		panelConfigNetWorkNet.add(lblConfigNetWorkIPAddr);
		
		textFieldConfigNetWorkIPAddr = new JTextField();
		textFieldConfigNetWorkIPAddr.setBounds(93, 41, 135, 21);
		panelConfigNetWorkNet.add(textFieldConfigNetWorkIPAddr);
		textFieldConfigNetWorkIPAddr.setColumns(10);
		
		JLabel lblConfigNetWorkSubMask = new JLabel("Subnet Mask");
		lblConfigNetWorkSubMask.setBounds(10, 77, 73, 15);
		panelConfigNetWorkNet.add(lblConfigNetWorkSubMask);
		
		textFieldConfigNetWorkSubMask = new JTextField();
		textFieldConfigNetWorkSubMask.setBounds(93, 74, 135, 21);
		panelConfigNetWorkNet.add(textFieldConfigNetWorkSubMask);
		textFieldConfigNetWorkSubMask.setColumns(10);
		
		JLabel lblConfigNetWorkGateway = new JLabel("Gateway");
		lblConfigNetWorkGateway.setBounds(10, 111, 48, 15);
		panelConfigNetWorkNet.add(lblConfigNetWorkGateway);
		
		textFieldConfigNetWorkGateway = new JTextField();
		textFieldConfigNetWorkGateway.setBounds(93, 108, 135, 21);
		panelConfigNetWorkNet.add(textFieldConfigNetWorkGateway);
		textFieldConfigNetWorkGateway.setColumns(10);
		
		JLabel lblConfigNetWorkMTU = new JLabel("MTU");
		lblConfigNetWorkMTU.setBounds(10, 147, 54, 15);
		panelConfigNetWorkNet.add(lblConfigNetWorkMTU);
		
		textFieldConfigNetWorkMTU = new JTextField();
		textFieldConfigNetWorkMTU.setBounds(93, 144, 135, 21);
		panelConfigNetWorkNet.add(textFieldConfigNetWorkMTU);
		textFieldConfigNetWorkMTU.setColumns(10);
		
		JButton btnConfigNetWorkNetGet = new JButton("Get");
		btnConfigNetWorkNetGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_NETWORKCFG_S stNetworkcfg = new NETDEV_NETWORKCFG_S();
				IntByReference dwBytesReturned = new IntByReference();

				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_NETWORKCFG, stNetworkcfg.getPointer(), 584, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stNetworkcfg.read();
                
                textFieldConfigNetWorkMTU.setText(String.valueOf(stNetworkcfg.dwMTU));
                
                if(stNetworkcfg.bIPv4DHCP == 1)
                {
                	chckbxConfigNetWorkDHCPEnable.setSelected(true);
                }
                
                textFieldConfigNetWorkIPAddr.setText(Common.byteArrayToString(stNetworkcfg.szIpv4Address));
                textFieldConfigNetWorkSubMask.setText(Common.byteArrayToString(stNetworkcfg.szIPv4SubnetMask));
                textFieldConfigNetWorkGateway.setText(Common.byteArrayToString(stNetworkcfg.szIPv4GateWay));
			}
		});
		btnConfigNetWorkNetGet.setBounds(10, 183, 93, 23);
		panelConfigNetWorkNet.add(btnConfigNetWorkNetGet);
		
		JButton btnConfigNetWorkNetSet = new JButton("Set");
		btnConfigNetWorkNetSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_NETWORKCFG_S stNetworkcfg = new NETDEV_NETWORKCFG_S();
				if(chckbxConfigNetWorkDHCPEnable.isSelected() == true)
				{
					stNetworkcfg.bIPv4DHCP = 1;
				}
				
				System.arraycopy(textFieldConfigNetWorkIPAddr.getText().getBytes(), 0, stNetworkcfg.szIpv4Address, 0, textFieldConfigNetWorkIPAddr.getText().getBytes().length);
				System.arraycopy(textFieldConfigNetWorkSubMask.getText().getBytes(), 0, stNetworkcfg.szIPv4SubnetMask, 0, textFieldConfigNetWorkSubMask.getText().getBytes().length);
				System.arraycopy(textFieldConfigNetWorkGateway.getText().getBytes(), 0, stNetworkcfg.szIPv4GateWay, 0, textFieldConfigNetWorkGateway.getText().getBytes().length);
				stNetworkcfg.dwMTU = Integer.valueOf(textFieldConfigNetWorkMTU.getText());
				stNetworkcfg.write();

				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_NETWORKCFG, stNetworkcfg.getPointer(), 584);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }

			}
		});
		btnConfigNetWorkNetSet.setBounds(135, 183, 93, 23);
		panelConfigNetWorkNet.add(btnConfigNetWorkNetSet);
		
		JPanel panelConfigNetWorkPort = new JPanel();
		panelConfigNetWorkPort.setBorder(new TitledBorder(null, "Port", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigNetWorkPort.setBounds(303, 10, 300, 157);
		panelConfigNetwork.add(panelConfigNetWorkPort);
		panelConfigNetWorkPort.setLayout(null);
		
		JLabel lblNetworkPortHTTP = new JLabel("HTTP");
		lblNetworkPortHTTP.setBounds(10, 24, 49, 15);
		panelConfigNetWorkPort.add(lblNetworkPortHTTP);
		
		textFieldNetworkPortHTTP = new JTextField();
		textFieldNetworkPortHTTP.setBounds(69, 21, 66, 21);
		panelConfigNetWorkPort.add(textFieldNetworkPortHTTP);
		textFieldNetworkPortHTTP.setColumns(10);
		
		JComboBox<Object> comboBoxNetworkPortHTTP = new JComboBox<Object>();
		comboBoxNetworkPortHTTP.setEnabled(false);
		comboBoxNetworkPortHTTP.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "Enable", "Disable"}));
		comboBoxNetworkPortHTTP.setBounds(145, 21, 66, 21);
		panelConfigNetWorkPort.add(comboBoxNetworkPortHTTP);
		
		JLabel lblNetworkPortHTTPS = new JLabel("HTTPS");
		lblNetworkPortHTTPS.setBounds(10, 63, 49, 15);
		panelConfigNetWorkPort.add(lblNetworkPortHTTPS);
		
		textFieldNetworkPortHTTPS = new JTextField();
		textFieldNetworkPortHTTPS.setBounds(69, 60, 66, 21);
		panelConfigNetWorkPort.add(textFieldNetworkPortHTTPS);
		textFieldNetworkPortHTTPS.setColumns(10);
		
		JComboBox<Object> comboBoxNetworkPortHTTPS = new JComboBox<Object>();
		comboBoxNetworkPortHTTPS.setEnabled(false);
		comboBoxNetworkPortHTTPS.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "Enable", "Disable"}));
		comboBoxNetworkPortHTTPS.setBounds(145, 60, 66, 21);
		panelConfigNetWorkPort.add(comboBoxNetworkPortHTTPS);
		
		JLabel lblNetworkPortRTSP = new JLabel("RTSP");
		lblNetworkPortRTSP.setBounds(10, 99, 49, 15);
		panelConfigNetWorkPort.add(lblNetworkPortRTSP);
		
		textFieldNetworkPortRTSP = new JTextField();
		textFieldNetworkPortRTSP.setBounds(69, 96, 66, 21);
		panelConfigNetWorkPort.add(textFieldNetworkPortRTSP);
		textFieldNetworkPortRTSP.setColumns(10);
		
		JComboBox<Object> comboBoxNetworkPortRTSP = new JComboBox<Object>();
		comboBoxNetworkPortRTSP.setEnabled(false);
		comboBoxNetworkPortRTSP.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "Enable", "Disable"}));
		comboBoxNetworkPortRTSP.setBounds(145, 96, 66, 21);
		panelConfigNetWorkPort.add(comboBoxNetworkPortRTSP);
		
		JButton btnNetworkPortGet = new JButton("Get");
		btnNetworkPortGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				NETDEV_UPNP_NAT_STATE_S stNatState = new NETDEV_UPNP_NAT_STATE_S();
				boolean bRet = netdevsdk.NETDEV_GetUpnpNatState(lpUserID, stNatState);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetUpnpNatState failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
                stNatState.read();
                
                for(int i =0; i < stNatState.dwSize; i++)
                {
                	switch (stNatState.astUpnpPort[i].eType) {
					case NETDEV_PROTOCOL_TYPE_E.NETDEV_PROTOCOL_TYPE_HTTP:
						textFieldNetworkPortHTTP.setText(String.valueOf(stNatState.astUpnpPort[i].dwPort));
						if(stNatState.astUpnpPort[i].bEnbale == 1)
						{
							comboBoxNetworkPortHTTP.setSelectedIndex(1);
						}
						else
						{
							comboBoxNetworkPortHTTP.setSelectedIndex(2);
						}
						break;
					case NETDEV_PROTOCOL_TYPE_E.NETDEV_PROTOCOL_TYPE_HTTPS:
						textFieldNetworkPortHTTPS.setText(String.valueOf(stNatState.astUpnpPort[i].dwPort));
						if(stNatState.astUpnpPort[i].bEnbale == 1)
						{
							comboBoxNetworkPortHTTPS.setSelectedIndex(1);
						}
						else
						{
							comboBoxNetworkPortHTTPS.setSelectedIndex(2);
						}
						break;
					case NETDEV_PROTOCOL_TYPE_E.NETDEV_PROTOCOL_TYPE_RTSP:
						textFieldNetworkPortRTSP.setText(String.valueOf(stNatState.astUpnpPort[i].dwPort));
						if(stNatState.astUpnpPort[i].bEnbale == 1)
						{
							comboBoxNetworkPortRTSP.setSelectedIndex(1);
						}
						else
						{
							comboBoxNetworkPortRTSP.setSelectedIndex(2);
						}
						break;

					default:
						break;
					}
                }
			}
		});
		btnNetworkPortGet.setBounds(10, 129, 93, 23);
		panelConfigNetWorkPort.add(btnNetworkPortGet);
		
		JButton btnNetworkPortSet = new JButton("Set");
		btnNetworkPortSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				NETDEV_UPNP_NAT_STATE_S stNatState = new NETDEV_UPNP_NAT_STATE_S();
				stNatState.dwSize = 3;
				
				for(int i = 0; i < 3; i++)
				{
					stNatState.astUpnpPort[i] = new NETDEV_UPNP_PORT_STATE_S();
				}
				
				stNatState.astUpnpPort[0].eType = NETDEV_PROTOCOL_TYPE_E.NETDEV_PROTOCOL_TYPE_HTTP;
				stNatState.astUpnpPort[0].dwPort = Integer.valueOf(textFieldNetworkPortHTTP.getText());
				if(comboBoxNetworkPortHTTP.getSelectedIndex() == 1)
				{
					stNatState.astUpnpPort[0].bEnbale = 1;
				}
				else
				{
					stNatState.astUpnpPort[0].bEnbale = 0;
				}
				
				stNatState.astUpnpPort[1].eType = NETDEV_PROTOCOL_TYPE_E.NETDEV_PROTOCOL_TYPE_HTTPS;
				stNatState.astUpnpPort[1].dwPort = Integer.valueOf(textFieldNetworkPortHTTPS.getText());
				if(comboBoxNetworkPortHTTPS.getSelectedIndex() == 1)
				{
					stNatState.astUpnpPort[1].bEnbale = 1;
				}
				else
				{
					stNatState.astUpnpPort[1].bEnbale = 0;
				}
				
				stNatState.astUpnpPort[2].eType = NETDEV_PROTOCOL_TYPE_E.NETDEV_PROTOCOL_TYPE_RTSP;
				stNatState.astUpnpPort[2].dwPort = Integer.valueOf(textFieldNetworkPortRTSP.getText());
				if(comboBoxNetworkPortRTSP.getSelectedIndex() == 1)
				{
					stNatState.astUpnpPort[2].bEnbale = 1;
				}
				else
				{
					stNatState.astUpnpPort[2].bEnbale = 0;
				}
				stNatState.write();
				
				boolean bRet = netdevsdk.NETDEV_SetUpnpNatState(lpUserID, stNatState);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetUpnpNatState failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnNetworkPortSet.setBounds(145, 127, 93, 23);
		panelConfigNetWorkPort.add(btnNetworkPortSet);
		
		JPanel panelConfigNetWorkTelnet = new JPanel();
		panelConfigNetWorkTelnet.setBorder(new TitledBorder(null, "Telnet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigNetWorkTelnet.setBounds(303, 177, 300, 59);
		panelConfigNetwork.add(panelConfigNetWorkTelnet);
		panelConfigNetWorkTelnet.setLayout(null);
		
		JButton btnConfigNetWorkTelnetOpen = new JButton("Open");
		btnConfigNetWorkTelnetOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_EnableTelnet(lpUserID, 1);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_EnableTelnet failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigNetWorkTelnetOpen.setBounds(21, 26, 93, 23);
		panelConfigNetWorkTelnet.add(btnConfigNetWorkTelnetOpen);
		
		JButton btnConfigNetWorkTelnetClose = new JButton("Close");
		btnConfigNetWorkTelnetClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_EnableTelnet(lpUserID, 0);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_EnableTelnet failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigNetWorkTelnetClose.setBounds(150, 26, 93, 23);
		panelConfigNetWorkTelnet.add(btnConfigNetWorkTelnetClose);
		
		JPanel panelConfigNetWorkNTP = new JPanel();
		panelConfigNetWorkNTP.setBorder(new TitledBorder(null, "NTP", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigNetWorkNTP.setBounds(10, 242, 593, 210);
		panelConfigNetwork.add(panelConfigNetWorkNTP);
		panelConfigNetWorkNTP.setLayout(null);
		
		JCheckBox chckbxConfigNetWorkNTPEnable = new JCheckBox("Enable");
		chckbxConfigNetWorkNTPEnable.setBounds(20, 21, 103, 23);
		panelConfigNetWorkNTP.add(chckbxConfigNetWorkNTPEnable);
		
		JLabel lblConfigNetWorkNTPIPType = new JLabel("IP Type");
		lblConfigNetWorkNTPIPType.setBounds(20, 60, 54, 15);
		panelConfigNetWorkNTP.add(lblConfigNetWorkNTPIPType);
		
		JLabel lblConfigNetWorkNTPServerIP = new JLabel("Server IP");
		lblConfigNetWorkNTPServerIP.setBounds(20, 95, 62, 15);
		panelConfigNetWorkNTP.add(lblConfigNetWorkNTPServerIP);
		
		textFieldConfigNetWorkNTPServerIP = new JTextField();
		textFieldConfigNetWorkNTPServerIP.setBounds(91, 92, 131, 21);
		panelConfigNetWorkNTP.add(textFieldConfigNetWorkNTPServerIP);
		textFieldConfigNetWorkNTPServerIP.setColumns(10);
		
		JComboBox<Object> comboBoxConfigNetWorkNTPIPType = new JComboBox<Object>();
		comboBoxConfigNetWorkNTPIPType.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "IPv4", "IPv6", "Domain name"}));
		comboBoxConfigNetWorkNTPIPType.setBounds(91, 57, 131, 21);
		panelConfigNetWorkNTP.add(comboBoxConfigNetWorkNTPIPType);
		
		JLabel lblConfigNetWorkNTPDomainName = new JLabel("DomainName");
		lblConfigNetWorkNTPDomainName.setBounds(258, 25, 88, 15);
		panelConfigNetWorkNTP.add(lblConfigNetWorkNTPDomainName);
		
		JLabel lblConfigNetWorkNTPPort = new JLabel("Port");
		lblConfigNetWorkNTPPort.setBounds(258, 60, 54, 15);
		panelConfigNetWorkNTP.add(lblConfigNetWorkNTPPort);
		
		JLabel lblConfigNetWorkNTPSynchronizeInterval = new JLabel("SynchronizeInterval");
		lblConfigNetWorkNTPSynchronizeInterval.setBounds(258, 95, 120, 15);
		panelConfigNetWorkNTP.add(lblConfigNetWorkNTPSynchronizeInterval);
		
		textFieldConfigNetWorkNTPDomainName = new JTextField();
		textFieldConfigNetWorkNTPDomainName.setBounds(393, 22, 94, 21);
		panelConfigNetWorkNTP.add(textFieldConfigNetWorkNTPDomainName);
		textFieldConfigNetWorkNTPDomainName.setColumns(10);
		
		textFieldConfigNetWorkNTPPort = new JTextField();
		textFieldConfigNetWorkNTPPort.setBounds(393, 57, 94, 21);
		panelConfigNetWorkNTP.add(textFieldConfigNetWorkNTPPort);
		textFieldConfigNetWorkNTPPort.setColumns(10);
		
		textFieldConfigNetWorkNTPSynchronizeInterval = new JTextField();
		textFieldConfigNetWorkNTPSynchronizeInterval.setBounds(393, 92, 54, 21);
		panelConfigNetWorkNTP.add(textFieldConfigNetWorkNTPSynchronizeInterval);
		textFieldConfigNetWorkNTPSynchronizeInterval.setColumns(10);
		
		JLabel lblConfigNetWorkNTPSynchronizeIntervalTip = new JLabel("(IPC:s NVR:min)");
		lblConfigNetWorkNTPSynchronizeIntervalTip.setBounds(461, 95, 103, 15);
		panelConfigNetWorkNTP.add(lblConfigNetWorkNTPSynchronizeIntervalTip);
		
		JButton btnConfigNetWorkNTPGet = new JButton("Get");
		btnConfigNetWorkNTPGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_SYSTEM_NTP_INFO_LIST_S stNTPInfo = new NETDEV_SYSTEM_NTP_INFO_LIST_S();
				for(int i =0; i<NetDEVSDKLib.NETDEV_NTP_SERVER_LIST_NUM; i++)
				{
					stNTPInfo.astNTPServerInfoList[i] = new NETDEV_SYSTEM_IPADDR_INFO_S();
				}
				stNTPInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();

				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_NTPCFG_EX, stNTPInfo.getPointer(), 1576, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stNTPInfo.read();
                
                for(int i= 0; i< stNTPInfo.ulNum; i++)
                {
                	if(stNTPInfo.astNTPServerInfoList[i].bEnabled ==1)
                	{
                		chckbxConfigNetWorkNTPEnable.setSelected(true);
                	}
                	textFieldConfigNetWorkNTPDomainName.setText(Common.byteArrayToString(stNTPInfo.astNTPServerInfoList[i].szDomainName));
                	
                	if(stNTPInfo.astNTPServerInfoList[i].ulAddressType == 0)
                	{
                		comboBoxConfigNetWorkNTPIPType.setSelectedIndex(1);
                	}
                	else if(stNTPInfo.astNTPServerInfoList[i].ulAddressType == 1)
                	{
                		comboBoxConfigNetWorkNTPIPType.setSelectedIndex(2);
					}
                	else if(stNTPInfo.astNTPServerInfoList[i].ulAddressType == 2)
                	{
                		comboBoxConfigNetWorkNTPIPType.setSelectedIndex(3);
                	}
      	
                	textFieldConfigNetWorkNTPServerIP.setText(Common.byteArrayToString(stNTPInfo.astNTPServerInfoList[i].szIPAddress));
                	textFieldConfigNetWorkNTPPort.setText(String.valueOf(stNTPInfo.astNTPServerInfoList[i].ulPort));
                	textFieldConfigNetWorkNTPSynchronizeInterval.setText(String.valueOf(stNTPInfo.astNTPServerInfoList[i].ulSynchronizeInterval));
                }
                
			}
		});
		btnConfigNetWorkNTPGet.setBounds(393, 177, 93, 23);
		panelConfigNetWorkNTP.add(btnConfigNetWorkNTPGet);
		
		JButton btnConfigNetWorkNTPSet = new JButton("Set");
		btnConfigNetWorkNTPSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_SYSTEM_NTP_INFO_LIST_S stNTPInfo = new NETDEV_SYSTEM_NTP_INFO_LIST_S();
				stNTPInfo.ulNum = 1;
				stNTPInfo.astNTPServerInfoList[0] = new NETDEV_SYSTEM_IPADDR_INFO_S();

				if(chckbxConfigNetWorkNTPEnable.isSelected() == true)
				{
					stNTPInfo.astNTPServerInfoList[0].bEnabled = 1;
					
					stNTPInfo.astNTPServerInfoList[0].ulAddressType = comboBoxConfigNetWorkNTPIPType.getSelectedIndex()-1;
					System.arraycopy(textFieldConfigNetWorkNTPServerIP.getText().getBytes(), 0, stNTPInfo.astNTPServerInfoList[0].szIPAddress, 0, textFieldConfigNetWorkNTPServerIP.getText().getBytes().length);
					System.arraycopy(textFieldConfigNetWorkNTPDomainName.getText().getBytes(), 0, stNTPInfo.astNTPServerInfoList[0].szDomainName, 0, textFieldConfigNetWorkNTPDomainName.getText().getBytes().length);
					stNTPInfo.astNTPServerInfoList[0].ulPort = Integer.valueOf(textFieldConfigNetWorkNTPPort.getText());
					stNTPInfo.astNTPServerInfoList[0].ulSynchronizeInterval = Integer.valueOf(textFieldConfigNetWorkNTPSynchronizeInterval.getText());
					
					stNTPInfo.write();
					
					boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_NTPCFG_EX, stNTPInfo.getPointer(), 1576);
	                if(bRet != true)
	                {
	                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
	                    return;
	                }
				}

			}
		});
		btnConfigNetWorkNTPSet.setBounds(496, 177, 93, 23);
		panelConfigNetWorkNTP.add(btnConfigNetWorkNTPSet);
		
		JPanel panelConfigVideo = new JPanel();
		tabConfigList.addTab("Video", null, panelConfigVideo, null);
		panelConfigVideo.setLayout(null);
		
		JPanel panelConfigVideoStream = new JPanel();
		panelConfigVideoStream.setBorder(new TitledBorder(null, "Stream Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigVideoStream.setBounds(10, 10, 593, 180);
		panelConfigVideo.add(panelConfigVideoStream);
		panelConfigVideoStream.setLayout(null);
		
		JLabel lblConfigVideoStreamIndex = new JLabel("Stream Index");
		lblConfigVideoStreamIndex.setBounds(10, 24, 80, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamIndex);
		
		JComboBox<Object> comboBoxConfigVideoStreamIndex = new JComboBox<Object>();
		comboBoxConfigVideoStreamIndex.setModel(new DefaultComboBoxModel<Object>(new String[] {"MAIN", "AUX", "THIRD"}));
		comboBoxConfigVideoStreamIndex.setBounds(100, 21, 114, 21);
		panelConfigVideoStream.add(comboBoxConfigVideoStreamIndex);
		
		JLabel lblConfigVideoStreamEncodeFormat = new JLabel("Encode Format");
		lblConfigVideoStreamEncodeFormat.setBounds(258, 24, 89, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamEncodeFormat);
		
		JComboBox<Object> comboBoxConfigVideoStreamEncodeFormat = new JComboBox<Object>();
		comboBoxConfigVideoStreamEncodeFormat.setModel(new DefaultComboBoxModel<Object>(new String[] {"MJPEG", "H.264", "H.265"}));
		comboBoxConfigVideoStreamEncodeFormat.setBounds(365, 21, 97, 21);
		panelConfigVideoStream.add(comboBoxConfigVideoStreamEncodeFormat);
		
		JLabel lbllblConfigVideoStreamResolution = new JLabel("Resolution");
		lbllblConfigVideoStreamResolution.setBounds(10, 59, 72, 15);
		panelConfigVideoStream.add(lbllblConfigVideoStreamResolution);
		
		textFieldConfigVideoStreamResolutionWidth = new JTextField();
		textFieldConfigVideoStreamResolutionWidth.setBounds(100, 56, 47, 21);
		panelConfigVideoStream.add(textFieldConfigVideoStreamResolutionWidth);
		textFieldConfigVideoStreamResolutionWidth.setColumns(10);
		
		JLabel lblConfigVideoStreamResolutionX = new JLabel("x");
		lblConfigVideoStreamResolutionX.setBounds(157, 59, 14, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamResolutionX);
		
		textFieldConfigVideoStreamResolutionHigth = new JTextField();
		textFieldConfigVideoStreamResolutionHigth.setBounds(167, 56, 47, 21);
		panelConfigVideoStream.add(textFieldConfigVideoStreamResolutionHigth);
		textFieldConfigVideoStreamResolutionHigth.setColumns(10);
		
		JLabel lblConfigVideoStreamBitRate = new JLabel("Bit Rate(fps)");
		lblConfigVideoStreamBitRate.setBounds(258, 59, 89, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamBitRate);
		
		textFieldConfigVideoStreamBitRate = new JTextField();
		textFieldConfigVideoStreamBitRate.setBounds(365, 56, 97, 21);
		panelConfigVideoStream.add(textFieldConfigVideoStreamBitRate);
		textFieldConfigVideoStreamBitRate.setColumns(10);
		
		JLabel lblConfigVideoStreamQuality = new JLabel("Quality");
		lblConfigVideoStreamQuality.setBounds(10, 95, 54, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamQuality);
		
		JComboBox<Object> comboBoxConfigVideoStreamQuality = new JComboBox<Object>();
		comboBoxConfigVideoStreamQuality.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "L0", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9"}));
		comboBoxConfigVideoStreamQuality.setBounds(100, 92, 114, 21);
		panelConfigVideoStream.add(comboBoxConfigVideoStreamQuality);
		
		JLabel lblConfigVideoStreamFrameRate = new JLabel("Frame Rate(fps)");
		lblConfigVideoStreamFrameRate.setBounds(258, 95, 102, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamFrameRate);
		
		textFieldConfigVideoStreamFrameRate = new JTextField();
		textFieldConfigVideoStreamFrameRate.setBounds(365, 92, 97, 21);
		panelConfigVideoStream.add(textFieldConfigVideoStreamFrameRate);
		textFieldConfigVideoStreamFrameRate.setColumns(10);
		
		JLabel lblConfigVideoStreamIFremeInterval = new JLabel("I Frame Interval");
		lblConfigVideoStreamIFremeInterval.setBounds(10, 134, 102, 15);
		panelConfigVideoStream.add(lblConfigVideoStreamIFremeInterval);
		
		textFieldConfigVideoStreamIFremeInterval = new JTextField();
		textFieldConfigVideoStreamIFremeInterval.setBounds(122, 131, 92, 21);
		panelConfigVideoStream.add(textFieldConfigVideoStreamIFremeInterval);
		textFieldConfigVideoStreamIFremeInterval.setColumns(10);
		
		JButton btnConfigVideoStreamGet = new JButton("Get");
		btnConfigVideoStreamGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_VIDEO_STREAM_INFO_S stStreamInfo = new NETDEV_VIDEO_STREAM_INFO_S();
				stStreamInfo.enStreamType = comboBoxConfigVideoStreamIndex.getSelectedIndex();
				stStreamInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_STREAMCFG, stStreamInfo.getPointer(), 1576, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stStreamInfo.read();
                
                if(stStreamInfo.enCodeType == NETDEV_VIDEO_CODE_TYPE_E.NETDEV_VIDEO_CODE_MJPEG)
                {
                	comboBoxConfigVideoStreamEncodeFormat.setSelectedIndex(0);
                }
                else if(stStreamInfo.enCodeType == NETDEV_VIDEO_CODE_TYPE_E.NETDEV_VIDEO_CODE_H264)
                {
                	comboBoxConfigVideoStreamEncodeFormat.setSelectedIndex(1);
                }
                else if(stStreamInfo.enCodeType == NETDEV_VIDEO_CODE_TYPE_E.NETDEV_VIDEO_CODE_H265)
                {
                	comboBoxConfigVideoStreamEncodeFormat.setSelectedIndex(2);
                }
					
                textFieldConfigVideoStreamResolutionWidth.setText(String.valueOf(stStreamInfo.dwWidth));
                textFieldConfigVideoStreamResolutionHigth.setText(String.valueOf(stStreamInfo.dwHeight));
                textFieldConfigVideoStreamBitRate.setText(String.valueOf(stStreamInfo.dwBitRate));
                comboBoxConfigVideoStreamQuality.setSelectedItem(Common.EnumNETDEV_VIDEO_QUALITY_EConventToString(stStreamInfo.enQuality));
                textFieldConfigVideoStreamFrameRate.setText(String.valueOf(stStreamInfo.dwFrameRate));
                textFieldConfigVideoStreamIFremeInterval.setText(String.valueOf(stStreamInfo.dwGop));
			}
		});
		btnConfigVideoStreamGet.setBounds(254, 130, 93, 23);
		panelConfigVideoStream.add(btnConfigVideoStreamGet);
		
		JButton btnConfigVideoStreamSet = new JButton("Set");
		btnConfigVideoStreamSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_VIDEO_STREAM_INFO_S stStreamInfo = new NETDEV_VIDEO_STREAM_INFO_S();
				stStreamInfo.enStreamType = comboBoxConfigVideoStreamIndex.getSelectedIndex();
				stStreamInfo.dwBitRate = Integer.valueOf(textFieldConfigVideoStreamBitRate.getText());
				stStreamInfo.dwFrameRate = Integer.valueOf(textFieldConfigVideoStreamFrameRate.getText());
				stStreamInfo.dwGop = Integer.valueOf(textFieldConfigVideoStreamIFremeInterval.getText());
				stStreamInfo.enQuality = Common.StringConventToEnumNETDEV_VIDEO_QUALITY_E((String) comboBoxConfigVideoStreamQuality.getSelectedItem());
				stStreamInfo.dwHeight = Integer.valueOf(textFieldConfigVideoStreamResolutionHigth.getText());
				stStreamInfo.dwWidth = Integer.valueOf(textFieldConfigVideoStreamResolutionWidth.getText());
				stStreamInfo.enCodeType = comboBoxConfigVideoStreamEncodeFormat.getSelectedIndex();
				stStreamInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_STREAMCFG, stStreamInfo.getPointer(), 1576 );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigVideoStreamSet.setBounds(393, 130, 93, 23);
		panelConfigVideoStream.add(btnConfigVideoStreamSet);
		
		JPanel panelConfigImage = new JPanel();
		tabConfigList.addTab("Image", null, panelConfigImage, null);
		panelConfigImage.setLayout(null);
		
		JPanel panelConfigImageInfo = new JPanel();
		panelConfigImageInfo.setBorder(new TitledBorder(null, "Image Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigImageInfo.setBounds(10, 10, 593, 87);
		panelConfigImage.add(panelConfigImageInfo);
		panelConfigImageInfo.setLayout(null);
		
		JLabel lblConfigImageInfoBrightness = new JLabel("Brightness(0~255)");
		lblConfigImageInfoBrightness.setBounds(10, 23, 113, 15);
		panelConfigImageInfo.add(lblConfigImageInfoBrightness);
		
		textFieldConfigImageInfoBrightness = new JTextField();
		textFieldConfigImageInfoBrightness.setBounds(122, 20, 66, 21);
		panelConfigImageInfo.add(textFieldConfigImageInfoBrightness);
		textFieldConfigImageInfoBrightness.setColumns(10);
		
		JLabel lblConfigImageInfoSaturation = new JLabel("Saturation(0~255)");
		lblConfigImageInfoSaturation.setBounds(223, 23, 113, 15);
		panelConfigImageInfo.add(lblConfigImageInfoSaturation);
		
		textFieldConfigImageInfoSaturation = new JTextField();
		textFieldConfigImageInfoSaturation.setBounds(333, 20, 66, 21);
		panelConfigImageInfo.add(textFieldConfigImageInfoSaturation);
		textFieldConfigImageInfoSaturation.setColumns(10);
		
		JLabel lblConfigImageInfoContrast = new JLabel("Contrast(0~255)");
		lblConfigImageInfoContrast.setBounds(10, 58, 101, 15);
		panelConfigImageInfo.add(lblConfigImageInfoContrast);
		
		textFieldConfigImageInfoContrast = new JTextField();
		textFieldConfigImageInfoContrast.setBounds(122, 55, 66, 21);
		panelConfigImageInfo.add(textFieldConfigImageInfoContrast);
		textFieldConfigImageInfoContrast.setColumns(10);
		
		JLabel lblConfigImageInfoSharpness = new JLabel("Sharpness(0~255)");
		lblConfigImageInfoSharpness.setBounds(223, 58, 113, 15);
		panelConfigImageInfo.add(lblConfigImageInfoSharpness);
		
		textFieldConfigImageInfoSharpness = new JTextField();
		textFieldConfigImageInfoSharpness.setBounds(333, 55, 66, 21);
		panelConfigImageInfo.add(textFieldConfigImageInfoSharpness);
		textFieldConfigImageInfoSharpness.setColumns(10);
		
		JButton btnConfigImageInfoGet = new JButton("Get");
		btnConfigImageInfoGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_IMAGE_SETTING_S stImageInfo = new NETDEV_IMAGE_SETTING_S();
				stImageInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_IMAGECFG, stImageInfo.getPointer(), 268, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stImageInfo.read();
                
                textFieldConfigImageInfoBrightness.setText(String.valueOf(stImageInfo.dwBrightness));
                textFieldConfigImageInfoSaturation.setText(String.valueOf(stImageInfo.dwSaturation));
                textFieldConfigImageInfoContrast.setText(String.valueOf(stImageInfo.dwContrast));
                textFieldConfigImageInfoSharpness.setText(String.valueOf(stImageInfo.dwSharpness));
			}
		});
		btnConfigImageInfoGet.setBounds(448, 19, 93, 23);
		panelConfigImageInfo.add(btnConfigImageInfoGet);
		
		JButton btnConfigImageInfoSet = new JButton("Set");
		btnConfigImageInfoSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_IMAGE_SETTING_S stImageInfo = new NETDEV_IMAGE_SETTING_S();
				stImageInfo.dwBrightness = Integer.valueOf(textFieldConfigImageInfoBrightness.getText());
				stImageInfo.dwSaturation = Integer.valueOf(textFieldConfigImageInfoSaturation.getText());
				stImageInfo.dwContrast = Integer.valueOf(textFieldConfigImageInfoContrast.getText());
				stImageInfo.dwSharpness = Integer.valueOf(textFieldConfigImageInfoSharpness.getText());
				stImageInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_IMAGECFG, stImageInfo.getPointer(), 268);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
			}
		});
		btnConfigImageInfoSet.setBounds(448, 54, 93, 23);
		panelConfigImageInfo.add(btnConfigImageInfoSet);
		
		JPanel panelConfigImageExposure = new JPanel();
		panelConfigImageExposure.setBorder(new TitledBorder(null, "Image Exposure Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigImageExposure.setBounds(10, 107, 593, 231);
		panelConfigImage.add(panelConfigImageExposure);
		panelConfigImageExposure.setLayout(null);
		
		JLabel lblConfigImageExposureMode = new JLabel("ExposureMode");
		lblConfigImageExposureMode.setBounds(10, 29, 83, 15);
		panelConfigImageExposure.add(lblConfigImageExposureMode);
		
		textFieldConfigImageExposureMode = new JTextField();
		textFieldConfigImageExposureMode.setBounds(103, 26, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureMode);
		textFieldConfigImageExposureMode.setColumns(10);
		
		JLabel lblConfigImageExposureCompensation = new JLabel("Compensation");
		lblConfigImageExposureCompensation.setBounds(211, 29, 75, 15);
		panelConfigImageExposure.add(lblConfigImageExposureCompensation);
		
		textFieldConfigImageExposureCompensation = new JTextField();
		textFieldConfigImageExposureCompensation.setBounds(296, 26, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureCompensation);
		textFieldConfigImageExposureCompensation.setColumns(10);
		
		JLabel lblConfigImageExposureMinShutter = new JLabel("MinShutter");
		lblConfigImageExposureMinShutter.setBounds(394, 29, 66, 15);
		panelConfigImageExposure.add(lblConfigImageExposureMinShutter);
		
		textFieldConfigImageExposureMinShutter = new JTextField();
		textFieldConfigImageExposureMinShutter.setBounds(472, 26, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureMinShutter);
		textFieldConfigImageExposureMinShutter.setColumns(10);
		
		JLabel lblConfigImageExposureShutter = new JLabel("Shutter(s)");
		lblConfigImageExposureShutter.setBounds(10, 61, 75, 15);
		panelConfigImageExposure.add(lblConfigImageExposureShutter);
		
		textFieldConfigImageExposureShutter = new JTextField();
		textFieldConfigImageExposureShutter.setBounds(103, 58, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureShutter);
		textFieldConfigImageExposureShutter.setColumns(10);
		
		JLabel lblConfigImageExposureDayNightSence = new JLabel("DayNightSence");
		lblConfigImageExposureDayNightSence.setBounds(211, 61, 83, 15);
		panelConfigImageExposure.add(lblConfigImageExposureDayNightSence);
		
		textFieldConfigImageExposureDayNightSence = new JTextField();
		textFieldConfigImageExposureDayNightSence.setBounds(296, 58, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureDayNightSence);
		textFieldConfigImageExposureDayNightSence.setColumns(10);
		
		JLabel lblConfigImageExposureMaxShutter = new JLabel("MaxShutter");
		lblConfigImageExposureMaxShutter.setBounds(394, 61, 66, 15);
		panelConfigImageExposure.add(lblConfigImageExposureMaxShutter);
		
		textFieldlConfigImageExposureMaxShutter = new JTextField();
		textFieldlConfigImageExposureMaxShutter.setBounds(472, 58, 66, 21);
		panelConfigImageExposure.add(textFieldlConfigImageExposureMaxShutter);
		textFieldlConfigImageExposureMaxShutter.setColumns(10);
		
		JLabel lblConfigImageExposureGain = new JLabel("Gain");
		lblConfigImageExposureGain.setBounds(10, 92, 54, 15);
		panelConfigImageExposure.add(lblConfigImageExposureGain);
		
		textFieldConfigImageExposureGain = new JTextField();
		textFieldConfigImageExposureGain.setBounds(103, 89, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureGain);
		textFieldConfigImageExposureGain.setColumns(10);
		
		JLabel lblConfigImageExposureDayNightMode = new JLabel("DayNightMode");
		lblConfigImageExposureDayNightMode.setBounds(211, 92, 83, 15);
		panelConfigImageExposure.add(lblConfigImageExposureDayNightMode);
		
		textFieldConfigImageExposureDayNightMode = new JTextField();
		textFieldConfigImageExposureDayNightMode.setBounds(296, 89, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureDayNightMode);
		textFieldConfigImageExposureDayNightMode.setColumns(10);
		
		JLabel lblConfigImageExposureMinGain = new JLabel("MinGain");
		lblConfigImageExposureMinGain.setBounds(394, 92, 54, 15);
		panelConfigImageExposure.add(lblConfigImageExposureMinGain);
		
		textFieldConfigImageExposureMinGain = new JTextField();
		textFieldConfigImageExposureMinGain.setBounds(472, 89, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureMinGain);
		textFieldConfigImageExposureMinGain.setColumns(10);
		
		JLabel lblConfigImageExposureIsEnableSlowSh = new JLabel("IsEnableSlowSh");
		lblConfigImageExposureIsEnableSlowSh.setBounds(10, 127, 93, 15);
		panelConfigImageExposure.add(lblConfigImageExposureIsEnableSlowSh);
		
		textFieldConfigImageExposureIsEnableSlowSh = new JTextField();
		textFieldConfigImageExposureIsEnableSlowSh.setBounds(103, 124, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureIsEnableSlowSh);
		textFieldConfigImageExposureIsEnableSlowSh.setColumns(10);
		
		JLabel lblConfigImageExposureDayNightSwitch = new JLabel("NightSwitch");
		lblConfigImageExposureDayNightSwitch.setBounds(211, 127, 75, 15);
		panelConfigImageExposure.add(lblConfigImageExposureDayNightSwitch);
		
		textFieldConfigImageExposureDayNightSwitch = new JTextField();
		textFieldConfigImageExposureDayNightSwitch.setBounds(296, 124, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureDayNightSwitch);
		textFieldConfigImageExposureDayNightSwitch.setColumns(10);
		
		JLabel lblConfigImageExposureMaxGain = new JLabel("MaxGain");
		lblConfigImageExposureMaxGain.setBounds(394, 127, 54, 15);
		panelConfigImageExposure.add(lblConfigImageExposureMaxGain);
		
		textFieldConfigImageExposureMaxGain = new JTextField();
		textFieldConfigImageExposureMaxGain.setBounds(472, 124, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureMaxGain);
		textFieldConfigImageExposureMaxGain.setColumns(10);
		
		JLabel lblConfigImageExposureSloweShutter = new JLabel("SloweShutter");
		lblConfigImageExposureSloweShutter.setBounds(10, 162, 83, 15);
		panelConfigImageExposure.add(lblConfigImageExposureSloweShutter);
		
		textFieldConfigImageExposureSioweShutter = new JTextField();
		textFieldConfigImageExposureSioweShutter.setBounds(103, 159, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureSioweShutter);
		textFieldConfigImageExposureSioweShutter.setColumns(10);
		
		JLabel lblConfigImageExposureWDR = new JLabel("WDR");
		lblConfigImageExposureWDR.setBounds(211, 162, 54, 15);
		panelConfigImageExposure.add(lblConfigImageExposureWDR);
		
		textFieldConfigImageExposureWDR = new JTextField();
		textFieldConfigImageExposureWDR.setBounds(296, 159, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureWDR);
		textFieldConfigImageExposureWDR.setColumns(10);
		
		JLabel lblConfigImageExposureWDRLevel = new JLabel("WDRLevel");
		lblConfigImageExposureWDRLevel.setBounds(394, 162, 66, 15);
		panelConfigImageExposure.add(lblConfigImageExposureWDRLevel);
		
		textFieldConfigImageExposureWDRLevel = new JTextField();
		textFieldConfigImageExposureWDRLevel.setBounds(472, 159, 66, 21);
		panelConfigImageExposure.add(textFieldConfigImageExposureWDRLevel);
		textFieldConfigImageExposureWDRLevel.setColumns(10);
		
		JButton btnConfigImageExposureGet = new JButton("Get");
		btnConfigImageExposureGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_IMAGE_EXPOSURE_S stImageExposure = new NETDEV_IMAGE_EXPOSURE_S();
				stImageExposure.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_IMAGE_EXPOSURE, stImageExposure.getPointer(), 1524, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stImageExposure.read();
                
                textFieldConfigImageExposureMode.setText(String.valueOf(stImageExposure.udwMode));
                textFieldConfigImageExposureShutter.setText(String.valueOf(stImageExposure.stShutterInfo.udwShutterTime));
                textFieldConfigImageExposureGain.setText(String.valueOf(stImageExposure.stGainInfo.udwGain));
                textFieldConfigImageExposureIsEnableSlowSh.setText(String.valueOf(stImageExposure.stShutterInfo.udwIsEnableSlowShutter));
                textFieldConfigImageExposureSioweShutter.setText(String.valueOf(stImageExposure.stShutterInfo.udwSlowestShutter));
                textFieldConfigImageExposureCompensation.setText(String.valueOf(stImageExposure.dwCompensationLevel));
                textFieldConfigImageExposureDayNightSence.setText(String.valueOf(stImageExposure.stDayNightInfo.udwDayNightSensitivity));
                textFieldConfigImageExposureDayNightMode.setText(String.valueOf(stImageExposure.stDayNightInfo.udwDayNightMode));
                textFieldConfigImageExposureDayNightSwitch.setText(String.valueOf(stImageExposure.stDayNightInfo.udwDayNightTime));
                textFieldConfigImageExposureWDR.setText(String.valueOf(stImageExposure.stWideDynamicInfo.udwWideDynamicMode));
                textFieldConfigImageExposureMinShutter.setText(String.valueOf(stImageExposure.stShutterInfo.udwMinShutterTime));
                textFieldlConfigImageExposureMaxShutter.setText(String.valueOf(stImageExposure.stShutterInfo.udwMaxShutterTime));
                textFieldConfigImageExposureMinGain.setText(String.valueOf(stImageExposure.stGainInfo.udwMinGain));
                textFieldConfigImageExposureMaxGain.setText(String.valueOf(stImageExposure.stGainInfo.udwMaxGain));
                textFieldConfigImageExposureWDRLevel.setText(String.valueOf(stImageExposure.stWideDynamicInfo.udwWideDynamicLevel));
			}
		});
		btnConfigImageExposureGet.setBounds(296, 192, 93, 23);
		panelConfigImageExposure.add(btnConfigImageExposureGet);
		
		JButton btnConfigImageExposureSet = new JButton("Set");
		btnConfigImageExposureSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_IMAGE_EXPOSURE_S stImageExposure = new NETDEV_IMAGE_EXPOSURE_S();
				
				stImageExposure.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_IMAGE_EXPOSURE, stImageExposure.getPointer(), 1524, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stImageExposure.read();
					
				stImageExposure.udwMode = Integer.valueOf(textFieldConfigImageExposureMode.getText());
				stImageExposure.stShutterInfo.udwShutterTime = Integer.valueOf(textFieldConfigImageExposureShutter.getText());
				stImageExposure.stGainInfo.udwGain = Integer.valueOf(textFieldConfigImageExposureGain.getText());
				stImageExposure.stShutterInfo.udwIsEnableSlowShutter = Integer.valueOf(textFieldConfigImageExposureIsEnableSlowSh.getText());
				stImageExposure.stShutterInfo.udwSlowestShutter = Integer.valueOf(textFieldConfigImageExposureSioweShutter.getText());
				stImageExposure.dwCompensationLevel = Integer.valueOf(textFieldConfigImageExposureCompensation.getText());
				stImageExposure.stDayNightInfo.udwDayNightSensitivity = Integer.valueOf(textFieldConfigImageExposureDayNightSence.getText());
				stImageExposure.stDayNightInfo.udwDayNightMode = Integer.valueOf(textFieldConfigImageExposureDayNightMode.getText());
				stImageExposure.stDayNightInfo.udwDayNightTime = Integer.valueOf(textFieldConfigImageExposureDayNightSwitch.getText());
				stImageExposure.stWideDynamicInfo.udwWideDynamicMode = Integer.valueOf(textFieldConfigImageExposureWDR.getText());
				stImageExposure.stShutterInfo.udwMinShutterTime = Integer.valueOf(textFieldConfigImageExposureMinShutter.getText());
				stImageExposure.stShutterInfo.udwMaxShutterTime = Integer.valueOf(textFieldlConfigImageExposureMaxShutter.getText());
				stImageExposure.stGainInfo.udwMinGain = Integer.valueOf(textFieldConfigImageExposureMinGain.getText());
				stImageExposure.stGainInfo.udwMaxGain = Integer.valueOf(textFieldConfigImageExposureMaxGain.getText());
				stImageExposure.stWideDynamicInfo.udwWideDynamicLevel = Integer.valueOf(textFieldConfigImageExposureWDRLevel.getText());
				
				stImageExposure.write();
				
			    bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_IMAGE_EXPOSURE, stImageExposure.getPointer(), 1524);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
           
			}
		});
		btnConfigImageExposureSet.setBounds(445, 192, 93, 23);
		panelConfigImageExposure.add(btnConfigImageExposureSet);
		
		JPanel panelConfigOSD = new JPanel();
		tabConfigList.addTab("OSD", null, panelConfigOSD, null);
		panelConfigOSD.setLayout(null);
		
		JPanel panelConfigOSD2 = new JPanel();
		panelConfigOSD2.setBorder(new TitledBorder(null, "OSD", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigOSD2.setBounds(10, 10, 593, 275);
		panelConfigOSD.add(panelConfigOSD2);
		panelConfigOSD2.setLayout(null);
		
		JLabel lblConfigOSDTime = new JLabel("Time");
		lblConfigOSDTime.setBounds(10, 22, 54, 15);
		panelConfigOSD2.add(lblConfigOSDTime);
		
		JLabel lblConfigOSDName = new JLabel("Name");
		lblConfigOSDName.setBounds(10, 47, 54, 15);
		panelConfigOSD2.add(lblConfigOSDName);
		
		JLabel lblConfigOSDText1 = new JLabel("Text 1");
		lblConfigOSDText1.setBounds(10, 72, 54, 15);
		panelConfigOSD2.add(lblConfigOSDText1);
		
		JLabel lblConfigOSDText2 = new JLabel("Text 2");
		lblConfigOSDText2.setBounds(10, 97, 54, 15);
		panelConfigOSD2.add(lblConfigOSDText2);
		
		JLabel lblConfigOSDText3 = new JLabel("Text 3");
		lblConfigOSDText3.setBounds(10, 122, 54, 15);
		panelConfigOSD2.add(lblConfigOSDText3);
		
		JLabel lblConfigOSDText4 = new JLabel("Text 4");
		lblConfigOSDText4.setBounds(10, 147, 54, 15);
		panelConfigOSD2.add(lblConfigOSDText4);
		
		JLabel lblConfigOSDText5 = new JLabel("Text 5");
		lblConfigOSDText5.setBounds(10, 172, 54, 15);
		panelConfigOSD2.add(lblConfigOSDText5);
		
		JLabel lblConfigOSDText6 = new JLabel("Text 6");
		lblConfigOSDText6.setBounds(10, 197, 54, 15);
		panelConfigOSD2.add(lblConfigOSDText6);
		
		JComboBox<Object> comboBoxConfigOSDTime1 = new JComboBox<Object>();
		comboBoxConfigOSDTime1.setModel(new DefaultComboBoxModel<Object>(new String[] {"M/d/yyyy", "MM/dd/yyyy", "dd/MM/yyyy", "yyyy/MM/dd", "yyyy-MM-dd", "dddd, MMMM dd, yyyy", "MMMM dd, yyyy", "dd MMMM, yyyy"}));
		comboBoxConfigOSDTime1.setBounds(94, 19, 107, 21);
		panelConfigOSD2.add(comboBoxConfigOSDTime1);
		
		JComboBox<Object> comboBoxConfigOSDTim2 = new JComboBox<Object>();
		comboBoxConfigOSDTim2.setModel(new DefaultComboBoxModel<Object>(new String[] {"HH:mm:ss", "hh:mm:ss tt"}));
		comboBoxConfigOSDTim2.setBounds(211, 19, 98, 21);
		panelConfigOSD2.add(comboBoxConfigOSDTim2);
		
		textFieldConfigOSDName = new JTextField();
		textFieldConfigOSDName.setBounds(94, 44, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDName);
		textFieldConfigOSDName.setColumns(10);
		
		textFieldConfigOSDText1 = new JTextField();
		textFieldConfigOSDText1.setBounds(94, 69, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDText1);
		textFieldConfigOSDText1.setColumns(10);
		
		textFieldConfigOSDText2 = new JTextField();
		textFieldConfigOSDText2.setBounds(94, 94, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDText2);
		textFieldConfigOSDText2.setColumns(10);
		
		textFieldConfigOSDText3 = new JTextField();
		textFieldConfigOSDText3.setBounds(94, 119, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDText3);
		textFieldConfigOSDText3.setColumns(10);
		
		textFieldConfigOSDText4 = new JTextField();
		textFieldConfigOSDText4.setBounds(94, 144, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDText4);
		textFieldConfigOSDText4.setColumns(10);
		
		textFieldConfigOSDText5 = new JTextField();
		textFieldConfigOSDText5.setBounds(94, 169, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDText5);
		textFieldConfigOSDText5.setColumns(10);
		
		textFieldConfigOSDText6 = new JTextField();
		textFieldConfigOSDText6.setBounds(94, 194, 215, 21);
		panelConfigOSD2.add(textFieldConfigOSDText6);
		textFieldConfigOSDText6.setColumns(10);
		
		JLabel lblConfigOSDX1 = new JLabel("x");
		lblConfigOSDX1.setBounds(315, 22, 20, 15);
		panelConfigOSD2.add(lblConfigOSDX1);
		
		JLabel lblConfigOSDX2 = new JLabel("x");
		lblConfigOSDX2.setBounds(315, 47, 20, 15);
		panelConfigOSD2.add(lblConfigOSDX2);
		
		JLabel lblConfigOSDX3 = new JLabel("x");
		lblConfigOSDX3.setBounds(315, 72, 20, 15);
		panelConfigOSD2.add(lblConfigOSDX3);
		
		JLabel lblConfigOSDX4 = new JLabel("x");
		lblConfigOSDX4.setBounds(315, 97, 20, 15);
		panelConfigOSD2.add(lblConfigOSDX4);
		
		JLabel lblConfigOSDX5 = new JLabel("x");
		lblConfigOSDX5.setBounds(315, 122, 14, 15);
		panelConfigOSD2.add(lblConfigOSDX5);
		
		JLabel lblConfigOSDX6 = new JLabel("x");
		lblConfigOSDX6.setBounds(315, 147, 20, 15);
		panelConfigOSD2.add(lblConfigOSDX6);
		
		JLabel lblConfigOSDX7 = new JLabel("x");
		lblConfigOSDX7.setBounds(315, 172, 20, 15);
		panelConfigOSD2.add(lblConfigOSDX7);
		
		JLabel lblConfigOSDX8 = new JLabel("x");
		lblConfigOSDX8.setBounds(315, 197, 14, 15);
		panelConfigOSD2.add(lblConfigOSDX8);
		
		JCheckBox chckbxConfigOSDTime = new JCheckBox("Enable");
		chckbxConfigOSDTime.setBounds(484, 18, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDTime);
		
		JCheckBox chckbxConfigOSDName = new JCheckBox("Enable");
		chckbxConfigOSDName.setBounds(483, 43, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDName);
		
		JCheckBox chckbxConfigOSDText1 = new JCheckBox("Enable");
		chckbxConfigOSDText1.setBounds(483, 68, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDText1);
		
		JCheckBox chckbxConfigOSDText2 = new JCheckBox("Enable");
		chckbxConfigOSDText2.setBounds(483, 93, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDText2);
		
		JCheckBox chckbxConfigOSDText3 = new JCheckBox("Enable");
		chckbxConfigOSDText3.setBounds(484, 118, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDText3);
		
		JCheckBox chckbxConfigOSDText4 = new JCheckBox("Enable");
		chckbxConfigOSDText4.setBounds(484, 143, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDText4);
		
		JCheckBox chckbxConfigOSDText5 = new JCheckBox("Enable");
		chckbxConfigOSDText5.setBounds(483, 168, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDText5);
		
		JCheckBox chckbxConfigOSDText6 = new JCheckBox("Enable");
		chckbxConfigOSDText6.setBounds(483, 193, 66, 23);
		panelConfigOSD2.add(chckbxConfigOSDText6);
		
		textFieldConfigOSDTimeX = new JTextField();
		textFieldConfigOSDTimeX.setBounds(325, 19, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDTimeX);
		textFieldConfigOSDTimeX.setColumns(10);
		
		textFieldConfigOSDNameX = new JTextField();
		textFieldConfigOSDNameX.setBounds(325, 44, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDNameX);
		textFieldConfigOSDNameX.setColumns(10);
		
		textFieldConfigOSDText1X = new JTextField();
		textFieldConfigOSDText1X.setBounds(325, 69, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText1X);
		textFieldConfigOSDText1X.setColumns(10);
		
		textFieldConfigOSDText2X = new JTextField();
		textFieldConfigOSDText2X.setBounds(325, 94, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText2X);
		textFieldConfigOSDText2X.setColumns(10);
		
		textFieldConfigOSDText3X = new JTextField();
		textFieldConfigOSDText3X.setBounds(325, 119, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText3X);
		textFieldConfigOSDText3X.setColumns(10);
		
		textFieldConfigOSDText4X = new JTextField();
		textFieldConfigOSDText4X.setBounds(325, 144, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText4X);
		textFieldConfigOSDText4X.setColumns(10);
		
		textFieldConfigOSDText5X = new JTextField();
		textFieldConfigOSDText5X.setBounds(325, 169, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText5X);
		textFieldConfigOSDText5X.setColumns(10);
		
		textFieldConfigOSDText6X = new JTextField();
		textFieldConfigOSDText6X.setBounds(325, 194, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText6X);
		textFieldConfigOSDText6X.setColumns(10);
		
		JLabel lblConfigOSDY1 = new JLabel("y");
		lblConfigOSDY1.setBounds(401, 22, 20, 15);
		panelConfigOSD2.add(lblConfigOSDY1);
		
		JLabel lblConfigOSDY2 = new JLabel("y");
		lblConfigOSDY2.setBounds(401, 47, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY2);
		
		JLabel lblConfigOSDY3 = new JLabel("y");
		lblConfigOSDY3.setBounds(401, 72, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY3);
		
		JLabel lblConfigOSDY4 = new JLabel("y");
		lblConfigOSDY4.setBounds(401, 97, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY4);
		
		JLabel lblConfigOSDY5 = new JLabel("y");
		lblConfigOSDY5.setBounds(401, 122, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY5);
		
		JLabel lblConfigOSDY6 = new JLabel("y");
		lblConfigOSDY6.setBounds(401, 147, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY6);
		
		JLabel lblConfigOSDY7 = new JLabel("y");
		lblConfigOSDY7.setBounds(401, 172, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY7);
		
		JLabel lblConfigOSDY8 = new JLabel("y");
		lblConfigOSDY8.setBounds(401, 197, 14, 15);
		panelConfigOSD2.add(lblConfigOSDY8);
		
		textFieldConfigOSDTimeY = new JTextField();
		textFieldConfigOSDTimeY.setBounds(411, 19, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDTimeY);
		textFieldConfigOSDTimeY.setColumns(10);
		
		textFieldConfigOSDNameY = new JTextField();
		textFieldConfigOSDNameY.setBounds(411, 44, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDNameY);
		textFieldConfigOSDNameY.setColumns(10);
		
		textFieldConfigOSDText1Y = new JTextField();
		textFieldConfigOSDText1Y.setBounds(411, 69, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText1Y);
		textFieldConfigOSDText1Y.setColumns(10);
		
		textFieldConfigOSDText2Y = new JTextField();
		textFieldConfigOSDText2Y.setBounds(411, 94, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText2Y);
		textFieldConfigOSDText2Y.setColumns(10);
		
		textFieldConfigOSDText3Y = new JTextField();
		textFieldConfigOSDText3Y.setBounds(411, 119, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText3Y);
		textFieldConfigOSDText3Y.setColumns(10);
		
		textFieldConfigOSDText4Y = new JTextField();
		textFieldConfigOSDText4Y.setBounds(411, 144, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText4Y);
		textFieldConfigOSDText4Y.setColumns(10);
		
		textFieldConfigOSDText5Y = new JTextField();
		textFieldConfigOSDText5Y.setBounds(411, 169, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText5Y);
		textFieldConfigOSDText5Y.setColumns(10);
		
		textFieldConfigOSDText6Y = new JTextField();
		textFieldConfigOSDText6Y.setBounds(411, 194, 66, 21);
		panelConfigOSD2.add(textFieldConfigOSDText6Y);
		textFieldConfigOSDText6Y.setColumns(10);
		
		JButton btnConfigOSDGet = new JButton("Get");
		btnConfigOSDGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_VIDEO_OSD_CFG_S stOSDInfo = new NETDEV_VIDEO_OSD_CFG_S();
				stOSDInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_OSDCFG, stOSDInfo.getPointer(), 616, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stOSDInfo.read();
                
                comboBoxConfigOSDTime1.setSelectedIndex(stOSDInfo.stTimeOSD.udwDateFormat); 
                comboBoxConfigOSDTim2.setSelectedIndex(stOSDInfo.stTimeOSD.udwTimeFormat);
                textFieldConfigOSDTimeX.setText(String.valueOf(stOSDInfo.stTimeOSD.stAreaScope.dwLocateX));
                textFieldConfigOSDTimeY.setText(String.valueOf(stOSDInfo.stTimeOSD.stAreaScope.dwLocateY));
                if(stOSDInfo.stTimeOSD.bEnableFlag == 1)
                {
                	chckbxConfigOSDTime.setSelected(true);
                }
                
                textFieldConfigOSDName.setText(Common.byteArrayToString(stOSDInfo.stNameOSD.szOSDText));
                textFieldConfigOSDNameX.setText(String.valueOf(stOSDInfo.stNameOSD.stAreaScope.dwLocateX));
                textFieldConfigOSDNameY.setText(String.valueOf(stOSDInfo.stNameOSD.stAreaScope.dwLocateY));
                if(stOSDInfo.stNameOSD.bEnableFlag == 1)
                {
                	chckbxConfigOSDName.setSelected(true);
                }
                
                textFieldConfigOSDText1.setText(Common.byteArrayToString(stOSDInfo.astTextOverlay[0].szOSDText));
                textFieldConfigOSDText1X.setText(String.valueOf(stOSDInfo.astTextOverlay[0].stAreaScope.dwLocateX));
                textFieldConfigOSDText1Y.setText(String.valueOf(stOSDInfo.astTextOverlay[0].stAreaScope.dwLocateY));
                if(stOSDInfo.astTextOverlay[0].bEnableFlag == 1)
                {
                	chckbxConfigOSDText1.setSelected(true);
                }
                
                textFieldConfigOSDText2.setText(Common.byteArrayToString(stOSDInfo.astTextOverlay[1].szOSDText));
                textFieldConfigOSDText2X.setText(String.valueOf(stOSDInfo.astTextOverlay[1].stAreaScope.dwLocateX));
                textFieldConfigOSDText2Y.setText(String.valueOf(stOSDInfo.astTextOverlay[1].stAreaScope.dwLocateY));
                if(stOSDInfo.astTextOverlay[1].bEnableFlag == 1)
                {
                	chckbxConfigOSDText2.setSelected(true);
                }
                
                textFieldConfigOSDText3.setText(Common.byteArrayToString(stOSDInfo.astTextOverlay[2].szOSDText));
                textFieldConfigOSDText3X.setText(String.valueOf(stOSDInfo.astTextOverlay[2].stAreaScope.dwLocateX));
                textFieldConfigOSDText3Y.setText(String.valueOf(stOSDInfo.astTextOverlay[2].stAreaScope.dwLocateY));
                if(stOSDInfo.astTextOverlay[2].bEnableFlag == 1)
                {
                	chckbxConfigOSDText3.setSelected(true);
                }
                
                textFieldConfigOSDText4.setText(Common.byteArrayToString(stOSDInfo.astTextOverlay[3].szOSDText));
                textFieldConfigOSDText4X.setText(String.valueOf(stOSDInfo.astTextOverlay[3].stAreaScope.dwLocateX));
                textFieldConfigOSDText4Y.setText(String.valueOf(stOSDInfo.astTextOverlay[3].stAreaScope.dwLocateY));
                if(stOSDInfo.astTextOverlay[3].bEnableFlag == 1)
                {
                	chckbxConfigOSDText4.setSelected(true);
                }
                
                textFieldConfigOSDText5.setText(Common.byteArrayToString(stOSDInfo.astTextOverlay[4].szOSDText));
                textFieldConfigOSDText5X.setText(String.valueOf(stOSDInfo.astTextOverlay[4].stAreaScope.dwLocateX));
                textFieldConfigOSDText5Y.setText(String.valueOf(stOSDInfo.astTextOverlay[4].stAreaScope.dwLocateY));
                if(stOSDInfo.astTextOverlay[4].bEnableFlag == 1)
                {
                	chckbxConfigOSDText5.setSelected(true);
                }
                
                textFieldConfigOSDText6.setText(Common.byteArrayToString(stOSDInfo.astTextOverlay[5].szOSDText));
                textFieldConfigOSDText6X.setText(String.valueOf(stOSDInfo.astTextOverlay[5].stAreaScope.dwLocateX));
                textFieldConfigOSDText6Y.setText(String.valueOf(stOSDInfo.astTextOverlay[5].stAreaScope.dwLocateY));
                if(stOSDInfo.astTextOverlay[5].bEnableFlag == 1)
                {
                	chckbxConfigOSDText6.setSelected(true);
                }
			}
		});
		btnConfigOSDGet.setBounds(298, 236, 93, 23);
		panelConfigOSD2.add(btnConfigOSDGet);
		
		JButton btnConfigOSDSet = new JButton("Set");
		btnConfigOSDSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_VIDEO_OSD_CFG_S stOSDInfo = new NETDEV_VIDEO_OSD_CFG_S();
				for(int i =0 ;i < NetDEVSDKLib.NETDEV_OSD_TEXTOVERLAY_NUM; i++)
				{
					stOSDInfo.astTextOverlay[i] = new NETDEV_OSD_TEXT_OVERLAY_S();
				}
				
				/* Time */
				if(chckbxConfigOSDTime.isSelected() == true)
				{
					stOSDInfo.stTimeOSD.bEnableFlag = 1;
				}
				stOSDInfo.stTimeOSD.stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDTimeX.getText());
				stOSDInfo.stTimeOSD.stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDTimeY.getText());
				stOSDInfo.stTimeOSD.udwDateFormat = comboBoxConfigOSDTime1.getSelectedIndex();
				stOSDInfo.stTimeOSD.udwTimeFormat = comboBoxConfigOSDTim2.getSelectedIndex();
				
				/* NAME */
				if(!textFieldConfigOSDName.getText().trim().equals(""))
				{
					if(chckbxConfigOSDName.isSelected() == true)
					{
						stOSDInfo.stNameOSD.bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDName.getText(), stOSDInfo.stNameOSD.szOSDText);
					stOSDInfo.stNameOSD.stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDNameX.getText());
					stOSDInfo.stNameOSD.stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDNameY.getText());
				}
				
				stOSDInfo.wTextNum = (short) NetDEVSDKLib.NETDEV_OSD_TEXTOVERLAY_NUM;
				
				 /* Text */
				if(!textFieldConfigOSDText1.getText().trim().equals(""))
				{
					if(chckbxConfigOSDText1.isSelected() == true)
					{
						stOSDInfo.astTextOverlay[0].bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDText1.getText(), stOSDInfo.astTextOverlay[0].szOSDText);
					stOSDInfo.astTextOverlay[0].stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDText1X.getText());
					stOSDInfo.astTextOverlay[0].stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDText1Y.getText());
				}
				
				if(!textFieldConfigOSDText2.getText().trim().equals(""))
				{
					if(chckbxConfigOSDText2.isSelected() == true)
					{
						stOSDInfo.astTextOverlay[1].bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDText2.getText(), stOSDInfo.astTextOverlay[1].szOSDText);
					stOSDInfo.astTextOverlay[1].stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDText2X.getText());
					stOSDInfo.astTextOverlay[1].stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDText2Y.getText());
				}
				
				if(!textFieldConfigOSDText3.getText().trim().equals(""))
				{
					if(chckbxConfigOSDText3.isSelected() == true)
					{
						stOSDInfo.astTextOverlay[2].bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDText3.getText(), stOSDInfo.astTextOverlay[2].szOSDText);
					stOSDInfo.astTextOverlay[2].stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDText3X.getText());
					stOSDInfo.astTextOverlay[2].stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDText3Y.getText());
				}
				
				if(!textFieldConfigOSDText4.getText().trim().equals(""))
				{
					if(chckbxConfigOSDText4.isSelected() == true)
					{
						stOSDInfo.astTextOverlay[3].bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDText4.getText(), stOSDInfo.astTextOverlay[3].szOSDText);
					stOSDInfo.astTextOverlay[3].stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDText4X.getText());
					stOSDInfo.astTextOverlay[3].stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDText4Y.getText());
				}
				
				if(!textFieldConfigOSDText5.getText().trim().equals(""))
				{
					if(chckbxConfigOSDText5.isSelected() == true)
					{
						stOSDInfo.astTextOverlay[4].bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDText5.getText(), stOSDInfo.astTextOverlay[4].szOSDText);
					stOSDInfo.astTextOverlay[4].stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDText5X.getText());
					stOSDInfo.astTextOverlay[4].stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDText5Y.getText());
				}
				
				if(!textFieldConfigOSDText6.getText().trim().equals(""))
				{
					if(chckbxConfigOSDText6.isSelected() == true)
					{
						stOSDInfo.astTextOverlay[5].bEnableFlag = 1;
					}
					
					Common.stringToByteArray(textFieldConfigOSDText6.getText(), stOSDInfo.astTextOverlay[5].szOSDText);
					stOSDInfo.astTextOverlay[5].stAreaScope.dwLocateX = Integer.valueOf(textFieldConfigOSDText6X.getText());
					stOSDInfo.astTextOverlay[5].stAreaScope.dwLocateY = Integer.valueOf(textFieldConfigOSDText6Y.getText());
				}

				stOSDInfo.write();
						
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_OSDCFG, stOSDInfo.getPointer(), 616);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigOSDSet.setBounds(431, 236, 93, 23);
		panelConfigOSD2.add(btnConfigOSDSet);
		
		JPanel panelConfigIO = new JPanel();
		tabConfigList.addTab("IO", null, panelConfigIO, null);
		panelConfigIO.setLayout(null);
		
		JPanel panelConfigIOAlarmInput = new JPanel();
		panelConfigIOAlarmInput.setBorder(new TitledBorder(null, "Alarm Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigIOAlarmInput.setBounds(10, 10, 593, 222);
		panelConfigIO.add(panelConfigIOAlarmInput);
		panelConfigIOAlarmInput.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 573, 156);
		panelConfigIOAlarmInput.add(scrollPane);
		
		tableConfigIOAlarmInput = new JTable();
		tableConfigIOAlarmInput.setModel(ConfigIOAlarmInputTableModel);
		scrollPane.setViewportView(tableConfigIOAlarmInput);
		
		JButton btnConfigIOAlarmInputGet = new JButton("Get");
		btnConfigIOAlarmInputGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_INPUT_SWITCH_INFO_LIST_S stInputSwitchInfoList = new NETDEV_INPUT_SWITCH_INFO_LIST_S();
				stInputSwitchInfoList.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_INPUTSWITCH_LIST_CFG, stInputSwitchInfoList.getPointer(), 245764, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stInputSwitchInfoList.read();
                
                ConfigIOAlarmInputTableModel.setRowCount(0);
                
                for(int i = 0; i < stInputSwitchInfoList.udwNum; i++)
                {
                	Vector<String> InputSwitchInfoVector = new Vector<String>();
                	InputSwitchInfoVector.add(String.valueOf(stInputSwitchInfoList.astInputSwitchesInfo[i].dwInputSwitchIndex));
                	InputSwitchInfoVector.add(String.valueOf(stInputSwitchInfoList.astInputSwitchesInfo[i].dwRunMode));
                	InputSwitchInfoVector.add(String.valueOf(stInputSwitchInfoList.astInputSwitchesInfo[i].dwEnabled));
                	
                	ConfigIOAlarmInputTableModel.addRow(InputSwitchInfoVector);
                }
			}
		});
		btnConfigIOAlarmInputGet.setBounds(410, 189, 93, 23);
		panelConfigIOAlarmInput.add(btnConfigIOAlarmInputGet);
		
		JPanel panelConfigIOAlarmOutput = new JPanel();
		panelConfigIOAlarmOutput.setBorder(new TitledBorder(null, "Alarm Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigIOAlarmOutput.setBounds(10, 239, 593, 97);
		panelConfigIO.add(panelConfigIOAlarmOutput);
		panelConfigIOAlarmOutput.setLayout(null);
		
		JLabel lblConfigIOAlarmOutputIndex = new JLabel("Index");
		lblConfigIOAlarmOutputIndex.setBounds(10, 23, 54, 15);
		panelConfigIOAlarmOutput.add(lblConfigIOAlarmOutputIndex);
		
		JLabel lblConfigIOAlarmOutputName = new JLabel("Name");
		lblConfigIOAlarmOutputName.setBounds(167, 23, 54, 15);
		panelConfigIOAlarmOutput.add(lblConfigIOAlarmOutputName);
		
		textFieldlblConfigIOAlarmOutputName = new JTextField();
		textFieldlblConfigIOAlarmOutputName.setBounds(231, 20, 66, 21);
		panelConfigIOAlarmOutput.add(textFieldlblConfigIOAlarmOutputName);
		textFieldlblConfigIOAlarmOutputName.setColumns(10);
		
		JComboBox<Object> comboBoxlblConfigIOAlarmOutputStatus = new JComboBox<Object>();
		comboBoxlblConfigIOAlarmOutputStatus.setModel(new DefaultComboBoxModel<Object>(new String[] {"", "OPEN", "CLOSE"}));
		comboBoxlblConfigIOAlarmOutputStatus.setBounds(360, 20, 103, 21);
		panelConfigIOAlarmOutput.add(comboBoxlblConfigIOAlarmOutputStatus);
		
		JLabel lblConfigIOAlarmOutputStatus = new JLabel("Status");
		lblConfigIOAlarmOutputStatus.setBounds(317, 23, 54, 15);
		panelConfigIOAlarmOutput.add(lblConfigIOAlarmOutputStatus);
		
		JLabel lblConfigIOAlarmOutputChannelID = new JLabel("Channel ID");
		lblConfigIOAlarmOutputChannelID.setBounds(10, 58, 66, 15);
		panelConfigIOAlarmOutput.add(lblConfigIOAlarmOutputChannelID);
		
		textFieldConfigIOAlarmOutputChannelID = new JTextField();
		textFieldConfigIOAlarmOutputChannelID.setBounds(77, 55, 66, 21);
		panelConfigIOAlarmOutput.add(textFieldConfigIOAlarmOutputChannelID);
		textFieldConfigIOAlarmOutputChannelID.setColumns(10);
		
		JLabel lblConfigIOAlarmOutputDelay = new JLabel("Delay(s)");
		lblConfigIOAlarmOutputDelay.setBounds(167, 58, 54, 15);
		panelConfigIOAlarmOutput.add(lblConfigIOAlarmOutputDelay);
		
		textFieldlblConfigIOAlarmOutputDelay = new JTextField();
		textFieldlblConfigIOAlarmOutputDelay.setBounds(231, 55, 66, 21);
		panelConfigIOAlarmOutput.add(textFieldlblConfigIOAlarmOutputDelay);
		textFieldlblConfigIOAlarmOutputDelay.setColumns(10);
		
		JButton btnlConfigIOAlarmOutputTrigger = new JButton("Trigger");
		btnlConfigIOAlarmOutputTrigger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_TRIGGER_ALARM_OUTPUT_S stTriggerAlarmOutput = new NETDEV_TRIGGER_ALARM_OUTPUT_S();
				Common.stringToByteArray(textFieldlblConfigIOAlarmOutputName.getText(), stTriggerAlarmOutput.szName);
				stTriggerAlarmOutput.enOutputState = NETDEV_RELAYOUTPUT_STATE_E.NETDEV_BOOLEAN_STATUS_ACTIVE;
				stTriggerAlarmOutput.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_TRIGGER_ALARM_OUTPUT, stTriggerAlarmOutput.getPointer(), 260);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnlConfigIOAlarmOutputTrigger.setBounds(379, 54, 75, 23);
		panelConfigIOAlarmOutput.add(btnlConfigIOAlarmOutputTrigger);
		
		JButton btnlConfigIOAlarmOutputSet = new JButton("Set");
		btnlConfigIOAlarmOutputSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_ALARM_OUTPUT_INFO_S stAlarmOutputInfo = new NETDEV_ALARM_OUTPUT_INFO_S();
				Common.stringToByteArray(textFieldlblConfigIOAlarmOutputName.getText(), stAlarmOutputInfo.szName);
				stAlarmOutputInfo.enDefaultStatus = comboBoxlblConfigIOAlarmOutputStatus.getSelectedIndex();
				stAlarmOutputInfo.dwChancelId = Integer.valueOf(textFieldConfigIOAlarmOutputChannelID.getText());
				stAlarmOutputInfo.dwDurationSec = Integer.valueOf(textFieldlblConfigIOAlarmOutputDelay.getText());
				stAlarmOutputInfo.dwOutputNum = Integer.valueOf(textFieldConfigIOAlarmOutputIndex.getText());

				stAlarmOutputInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_ALARM_OUTPUTCFG, stAlarmOutputInfo.getPointer(), 5124);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnlConfigIOAlarmOutputSet.setBounds(490, 54, 75, 23);
		panelConfigIOAlarmOutput.add(btnlConfigIOAlarmOutputSet);
		
		JButton btnlConfigIOAlarmOutputGet = new JButton("Get");
		btnlConfigIOAlarmOutputGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_ALARM_OUTPUT_LIST_S stAlarmOutputList = new NETDEV_ALARM_OUTPUT_LIST_S();
				stAlarmOutputList.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_ALARM_OUTPUTCFG, stAlarmOutputList.getPointer(), 5124, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stAlarmOutputList.read();
                
                for(int i =0; i< stAlarmOutputList.dwSize; i++)
                {
                	textFieldConfigIOAlarmOutputIndex.setText(String.valueOf(stAlarmOutputList.astAlarmOutputInfo[i].dwOutputNum));
                	textFieldlblConfigIOAlarmOutputName.setText(Common.byteArrayToString(stAlarmOutputList.astAlarmOutputInfo[i].szName));
                	comboBoxlblConfigIOAlarmOutputStatus.setSelectedIndex(stAlarmOutputList.astAlarmOutputInfo[i].enDefaultStatus);
                	textFieldConfigIOAlarmOutputChannelID.setText(String.valueOf(stAlarmOutputList.astAlarmOutputInfo[i].dwChancelId));
                	textFieldlblConfigIOAlarmOutputDelay.setText(String.valueOf(stAlarmOutputList.astAlarmOutputInfo[i].dwDurationSec));
                }
                
			}
		});
		btnlConfigIOAlarmOutputGet.setBounds(490, 19, 75, 23);
		panelConfigIOAlarmOutput.add(btnlConfigIOAlarmOutputGet);
		
		textFieldConfigIOAlarmOutputIndex = new JTextField();
		textFieldConfigIOAlarmOutputIndex.setBounds(77, 20, 66, 21);
		panelConfigIOAlarmOutput.add(textFieldConfigIOAlarmOutputIndex);
		textFieldConfigIOAlarmOutputIndex.setColumns(10);
		
		JPanel panelConfigIOAlarmOutputChannelInfo = new JPanel();
		panelConfigIOAlarmOutputChannelInfo.setBorder(new TitledBorder(null, "Alarm Output Channel Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigIOAlarmOutputChannelInfo.setBounds(10, 350, 593, 92);
		panelConfigIO.add(panelConfigIOAlarmOutputChannelInfo);
		panelConfigIOAlarmOutputChannelInfo.setLayout(null);
		
		JLabel lblConfigIOAlarmOutputChannelDelay = new JLabel("Delay(ms)");
		lblConfigIOAlarmOutputChannelDelay.setBounds(194, 22, 81, 15);
		panelConfigIOAlarmOutputChannelInfo.add(lblConfigIOAlarmOutputChannelDelay);
		
		JLabel lblConfigIOAlarmOutputChannelNo = new JLabel("AlarmOutputNo.");
		lblConfigIOAlarmOutputChannelNo.setBounds(10, 22, 92, 15);
		panelConfigIOAlarmOutputChannelInfo.add(lblConfigIOAlarmOutputChannelNo);
		
		textFieldConfigIOAlarmOutputChannelNo = new JTextField();
		textFieldConfigIOAlarmOutputChannelNo.setBounds(100, 19, 66, 21);
		panelConfigIOAlarmOutputChannelInfo.add(textFieldConfigIOAlarmOutputChannelNo);
		textFieldConfigIOAlarmOutputChannelNo.setColumns(10);
		
		textFieldConfigIOAlarmOutputChannelDelay = new JTextField();
		textFieldConfigIOAlarmOutputChannelDelay.setBounds(256, 19, 66, 21);
		panelConfigIOAlarmOutputChannelInfo.add(textFieldConfigIOAlarmOutputChannelDelay);
		textFieldConfigIOAlarmOutputChannelDelay.setColumns(10);
		
		JLabel lblConfigIOAlarmOutputChannelDefaultStatus = new JLabel("defaultStatus");
		lblConfigIOAlarmOutputChannelDefaultStatus.setBounds(350, 22, 81, 15);
		panelConfigIOAlarmOutputChannelInfo.add(lblConfigIOAlarmOutputChannelDefaultStatus);
		
		textFieldConfigIOAlarmOutputChannelDefaultStatus = new JTextField();
		textFieldConfigIOAlarmOutputChannelDefaultStatus.setBounds(435, 19, 66, 21);
		panelConfigIOAlarmOutputChannelInfo.add(textFieldConfigIOAlarmOutputChannelDefaultStatus);
		textFieldConfigIOAlarmOutputChannelDefaultStatus.setColumns(10);
		
		JButton btnConfigIOAlarmOutputChannelGet = new JButton("Get");
		btnConfigIOAlarmOutputChannelGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_DEV_CHN_ALARMOUT_INFO_S stAlarmOutInfo = new NETDEV_DEV_CHN_ALARMOUT_INFO_S();
				stAlarmOutInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetChnDetailByChnType(lpUserID, ChannelID, NETDEV_CHN_TYPE_E.NETDEV_CHN_TYPE_ALARMOUT, stAlarmOutInfo.getPointer(), 680, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetChnDetailByChnType failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stAlarmOutInfo.read();
                
                textFieldConfigIOAlarmOutputChannelNo.setText(String.valueOf(stAlarmOutInfo.udwAlarmOutputNum));
                textFieldConfigIOAlarmOutputChannelDelay.setText(String.valueOf(stAlarmOutInfo.dwDuration));
                textFieldConfigIOAlarmOutputChannelDefaultStatus.setText(String.valueOf(stAlarmOutInfo.dwRunMode));
			}
		});
		btnConfigIOAlarmOutputChannelGet.setBounds(326, 59, 93, 23);
		panelConfigIOAlarmOutputChannelInfo.add(btnConfigIOAlarmOutputChannelGet);
		
		JButton btnConfigIOAlarmOutputChannelSet = new JButton("Set");
		btnConfigIOAlarmOutputChannelSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_DEV_CHN_ALARMOUT_INFO_S stAlarmOutInfo = new NETDEV_DEV_CHN_ALARMOUT_INFO_S();
				stAlarmOutInfo.udwAlarmOutputNum = Integer.valueOf(textFieldConfigIOAlarmOutputChannelNo.getText());
				stAlarmOutInfo.dwDuration = Integer.valueOf(textFieldConfigIOAlarmOutputChannelDelay.getText());
				stAlarmOutInfo.dwRunMode = Integer.valueOf(textFieldConfigIOAlarmOutputChannelDefaultStatus.getText());
				stAlarmOutInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetChnDetailByChnType(lpUserID, ChannelID, NETDEV_CHN_TYPE_E.NETDEV_CHN_TYPE_ALARMOUT, stAlarmOutInfo.getPointer(), 260 );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetChnDetailByChnType failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stAlarmOutInfo.read();
			}
		});
		btnConfigIOAlarmOutputChannelSet.setBounds(445, 59, 93, 23);
		panelConfigIOAlarmOutputChannelInfo.add(btnConfigIOAlarmOutputChannelSet);
		
		JPanel panelConfigPrivacyMask = new JPanel();
		tabConfigList.addTab("Privacy Mask", null, panelConfigPrivacyMask, null);
		panelConfigPrivacyMask.setLayout(null);
		
		JPanel panelConfigPrivacyMask2 = new JPanel();
		panelConfigPrivacyMask2.setBorder(new TitledBorder(null, "Privacy Mask", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigPrivacyMask2.setBounds(10, 10, 593, 253);
		panelConfigPrivacyMask.add(panelConfigPrivacyMask2);
		panelConfigPrivacyMask2.setLayout(null);
		
		JScrollPane scrollPaneConfigPrivacyMask = new JScrollPane();
		scrollPaneConfigPrivacyMask.setBounds(10, 21, 573, 182);
		panelConfigPrivacyMask2.add(scrollPaneConfigPrivacyMask);
		
		tableConfigPrivacyMask = new JTable();
		tableConfigPrivacyMask.setModel(ConfigPrivacyMaskTableModel);
		scrollPaneConfigPrivacyMask.setViewportView(tableConfigPrivacyMask);
		
		JButton btnConfigPrivacyMaskAdd = new JButton("Add");
		btnConfigPrivacyMaskAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				Vector<String> PrivacyMaskVector = new Vector<String>();
				int dwInsertIndex = 0;
				for(int i = 0; i < NetDEVSDKLib.NETDEV_MAX_PRIVACY_MASK_AREA_NUM; i++)
				{
					dwInsertIndex = i + 1;
					if(i < ConfigPrivacyMaskTableModel.getRowCount())
					{
						int dwCurrentIndex = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 0));
			            if (dwInsertIndex >= dwCurrentIndex)
			            {
			                continue;
			            }
					}
					
	            	PrivacyMaskVector.add(String.valueOf(dwInsertIndex));
	            	PrivacyMaskVector.add("0");
	            	PrivacyMaskVector.add("0");
	            	PrivacyMaskVector.add("1000");
	            	PrivacyMaskVector.add("1000");
	            	ConfigPrivacyMaskTableModel.insertRow(i, PrivacyMaskVector);
	            	break;
				}
				
				NETDEV_PRIVACY_MASK_CFG_S stPrivacyMaskInfo = new NETDEV_PRIVACY_MASK_CFG_S();
				for(int i =0; i < NetDEVSDKLib.NETDEV_MAX_PRIVACY_MASK_AREA_NUM; i++)
				{
					stPrivacyMaskInfo.astArea[i] = new NETDEV_PRIVACY_MASK_AREA_INFO_S();
				}
				
				for(int i =0; i< ConfigPrivacyMaskTableModel.getRowCount(); i++)
				{
					stPrivacyMaskInfo.astArea[i].dwIndex = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 0));
					stPrivacyMaskInfo.astArea[i].dwTopLeftX = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 1));
					stPrivacyMaskInfo.astArea[i].dwTopLeftY = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 2));
					stPrivacyMaskInfo.astArea[i].dwBottomRightX = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 3));
					stPrivacyMaskInfo.astArea[i].dwBottomRightY = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 4));
					stPrivacyMaskInfo.astArea[i].bIsEanbled = 1;
				}
				stPrivacyMaskInfo.dwSize = ConfigPrivacyMaskTableModel.getRowCount();
				
				stPrivacyMaskInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_PRIVACYMASKCFG, stPrivacyMaskInfo.getPointer(), 196);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigPrivacyMaskAdd.setBounds(126, 220, 93, 23);
		panelConfigPrivacyMask2.add(btnConfigPrivacyMaskAdd);
		
		JButton btnConfigPrivacyMaskDel = new JButton("Delete");
		btnConfigPrivacyMaskDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				if(0 == tableConfigPrivacyMask.getRowCount()  || tableConfigPrivacyMask.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find privacy mask or seletc first.");
					return;
				}
				
				String strIndex = (String) tableConfigPrivacyMask.getValueAt(tableConfigPrivacyMask.getSelectedRow(), 0);
				IntByReference dwIndex = new IntByReference(Integer.valueOf(strIndex));
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_DELETE_PRIVACYMASKCFG, dwIndex.getPointer(), 4);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
               
			}
		});
		btnConfigPrivacyMaskDel.setBounds(229, 220, 93, 23);
		panelConfigPrivacyMask2.add(btnConfigPrivacyMaskDel);
		
		JButton btnConfigPrivacyMaskSave = new JButton("Save");
		btnConfigPrivacyMaskSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_PRIVACY_MASK_CFG_S stPrivacyMaskInfo = new NETDEV_PRIVACY_MASK_CFG_S();
				for(int i =0; i < NetDEVSDKLib.NETDEV_MAX_PRIVACY_MASK_AREA_NUM; i++)
				{
					stPrivacyMaskInfo.astArea[i] = new NETDEV_PRIVACY_MASK_AREA_INFO_S();
				}
				
				for(int i =0; i< ConfigPrivacyMaskTableModel.getRowCount(); i++)
				{
					stPrivacyMaskInfo.astArea[i].dwIndex = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 0));
					stPrivacyMaskInfo.astArea[i].dwTopLeftX = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 1));
					stPrivacyMaskInfo.astArea[i].dwTopLeftY = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 2));
					stPrivacyMaskInfo.astArea[i].dwBottomRightX = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 3));
					stPrivacyMaskInfo.astArea[i].dwBottomRightY = Integer.valueOf((String) ConfigPrivacyMaskTableModel.getValueAt(i, 4));
					stPrivacyMaskInfo.astArea[i].bIsEanbled = 1;
				}
				stPrivacyMaskInfo.dwSize = ConfigPrivacyMaskTableModel.getRowCount();
				
				stPrivacyMaskInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_PRIVACYMASKCFG, stPrivacyMaskInfo.getPointer(), 196);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigPrivacyMaskSave.setBounds(441, 220, 93, 23);
		panelConfigPrivacyMask2.add(btnConfigPrivacyMaskSave);
		
		JButton btnConfigPrivacyMaskGet = new JButton("Get");
		btnConfigPrivacyMaskGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				ConfigPrivacyMaskTableModel.setRowCount(0);
				
				NETDEV_PRIVACY_MASK_CFG_S stPrivacyMaskInfo = new NETDEV_PRIVACY_MASK_CFG_S();
				stPrivacyMaskInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_PRIVACYMASKCFG, stPrivacyMaskInfo.getPointer(), 196, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stPrivacyMaskInfo.read();
                
                for(int i = 0; i < stPrivacyMaskInfo.dwSize; i++)
                {
                	Vector<String> PrivacyMaskVector = new Vector<String>();
                	PrivacyMaskVector.add(String.valueOf(stPrivacyMaskInfo.astArea[i].dwIndex));
                	PrivacyMaskVector.add(String.valueOf(stPrivacyMaskInfo.astArea[i].dwTopLeftX));
                	PrivacyMaskVector.add(String.valueOf(stPrivacyMaskInfo.astArea[i].dwTopLeftY));
                	PrivacyMaskVector.add(String.valueOf(stPrivacyMaskInfo.astArea[i].dwBottomRightX));
                	PrivacyMaskVector.add(String.valueOf(stPrivacyMaskInfo.astArea[i].dwBottomRightY));
                	ConfigPrivacyMaskTableModel.addRow(PrivacyMaskVector);
                }
			}
		});
		btnConfigPrivacyMaskGet.setBounds(338, 220, 93, 23);
		panelConfigPrivacyMask2.add(btnConfigPrivacyMaskGet);
		
		JPanel panelConfigMotion = new JPanel();
		tabConfigList.addTab("Motion", null, panelConfigMotion, null);
		panelConfigMotion.setLayout(null);
		
		JPanel panelConfigMotion2 = new JPanel();
		panelConfigMotion2.setBorder(new TitledBorder(null, "Motion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigMotion2.setBounds(10, 10, 593, 150);
		panelConfigMotion.add(panelConfigMotion2);
		panelConfigMotion2.setLayout(null);
		
		JLabel lblConfigMotionSensitivity = new JLabel("Sensitivity(1~100)");
		lblConfigMotionSensitivity.setBounds(10, 22, 127, 15);
		panelConfigMotion2.add(lblConfigMotionSensitivity);
		
		textFieldConfigMotionSensitivity = new JTextField();
		textFieldConfigMotionSensitivity.setBounds(147, 19, 66, 21);
		panelConfigMotion2.add(textFieldConfigMotionSensitivity);
		textFieldConfigMotionSensitivity.setColumns(10);
		
		JLabel lblConfigMotionObjectSize = new JLabel("Object Size(1~100)");
		lblConfigMotionObjectSize.setBounds(279, 22, 117, 15);
		panelConfigMotion2.add(lblConfigMotionObjectSize);
		
		textFieldConfigMotionObjectSize = new JTextField();
		textFieldConfigMotionObjectSize.setBounds(418, 19, 66, 21);
		panelConfigMotion2.add(textFieldConfigMotionObjectSize);
		textFieldConfigMotionObjectSize.setColumns(10);
		
		JLabel lblConfigMotionHistory = new JLabel("History(1~100)");
		lblConfigMotionHistory.setBounds(10, 53, 108, 15);
		panelConfigMotion2.add(lblConfigMotionHistory);
		
		textFieldConfigMotionHistory = new JTextField();
		textFieldConfigMotionHistory.setBounds(147, 50, 66, 21);
		panelConfigMotion2.add(textFieldConfigMotionHistory);
		textFieldConfigMotionHistory.setColumns(10);
		
		JButton btnConfigMotionGet = new JButton("Get");
		btnConfigMotionGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_MOTION_ALARM_INFO_S stMotionAlarmInfo = new NETDEV_MOTION_ALARM_INFO_S();
				
				
				
				//stMotionAlarmInfo.awScreenInfo = new Memory(netdevsdk.NETDEV_SCREEN_INFO_ROW * netdevsdk.NETDEV_SCREEN_INFO_COLUMN * 2);


				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_MOTIONALARM, stMotionAlarmInfo.getPointer(), 868, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stMotionAlarmInfo.read();
                
                textFieldConfigMotionSensitivity.setText(String.valueOf(stMotionAlarmInfo.dwSensitivity));
                textFieldConfigMotionObjectSize.setText(String.valueOf(stMotionAlarmInfo.dwObjectSize));
                textFieldConfigMotionHistory.setText(String.valueOf(stMotionAlarmInfo.dwHistory));      
			}
		});
		btnConfigMotionGet.setBounds(279, 96, 93, 23);
		panelConfigMotion2.add(btnConfigMotionGet);
		
		JButton btnConfigMotionSet = new JButton("Set");
		btnConfigMotionSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_MOTION_ALARM_INFO_S stMotionAlarmInfo = new NETDEV_MOTION_ALARM_INFO_S();
				//stMotionAlarmInfo.awScreenInfo = new Memory(netdevsdk.NETDEV_SCREEN_INFO_ROW * netdevsdk.NETDEV_SCREEN_INFO_COLUMN * 2);
				stMotionAlarmInfo.write();
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_MOTIONALARM, stMotionAlarmInfo.getPointer(), 868, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stMotionAlarmInfo.read();
				

				stMotionAlarmInfo.dwSensitivity = Integer.valueOf(textFieldConfigMotionSensitivity.getText());
				stMotionAlarmInfo.dwObjectSize = Integer.valueOf(textFieldConfigMotionObjectSize.getText());
				stMotionAlarmInfo.dwHistory = Integer.valueOf(textFieldConfigMotionHistory.getText());
				stMotionAlarmInfo.write();


				 bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_MOTIONALARM, stMotionAlarmInfo.getPointer(), 868 );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigMotionSet.setBounds(418, 96, 93, 23);
		panelConfigMotion2.add(btnConfigMotionSet);
		
		JPanel panelConfigTemper = new JPanel();
		tabConfigList.addTab("Temper", null, panelConfigTemper, null);
		panelConfigTemper.setLayout(null);
		
		JPanel panelConfigTemper2 = new JPanel();
		panelConfigTemper2.setBorder(new TitledBorder(null, "Temper", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelConfigTemper2.setBounds(10, 10, 593, 56);
		panelConfigTemper.add(panelConfigTemper2);
		panelConfigTemper2.setLayout(null);
		
		JLabel lblConfigTemperSensitivity = new JLabel("Sensitivity(1~100)");
		lblConfigTemperSensitivity.setBounds(10, 22, 119, 15);
		panelConfigTemper2.add(lblConfigTemperSensitivity);
		
		JButton btnConfigTemperGet = new JButton("Get");
		btnConfigTemperGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_TAMPER_ALARM_INFO_S stTamperAlarmInfo = new NETDEV_TAMPER_ALARM_INFO_S();
				stTamperAlarmInfo.write();
				
				IntByReference dwBytesReturned = new IntByReference();
				
				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_TAMPERALARM, stTamperAlarmInfo.getPointer(), 260, dwBytesReturned );
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                stTamperAlarmInfo.read();
                
                textFieldConfigTemperSensitivity.setText(String.valueOf(stTamperAlarmInfo.dwSensitivity));
			}
		});
		btnConfigTemperGet.setBounds(322, 18, 93, 23);
		panelConfigTemper2.add(btnConfigTemperGet);
		
		JButton btnConfigTemperSet = new JButton("Set");
		btnConfigTemperSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_TAMPER_ALARM_INFO_S stTamperAlarmInfo = new NETDEV_TAMPER_ALARM_INFO_S();
				stTamperAlarmInfo.dwSensitivity = Integer.valueOf(textFieldConfigTemperSensitivity.getText());
				stTamperAlarmInfo.write();
				
				boolean bRet = netdevsdk.NETDEV_SetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_SET_TAMPERALARM, stTamperAlarmInfo.getPointer(), 260);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_SetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnConfigTemperSet.setBounds(441, 18, 93, 23);
		panelConfigTemper2.add(btnConfigTemperSet);
		
		textFieldConfigTemperSensitivity = new JTextField();
		textFieldConfigTemperSensitivity.setBounds(139, 19, 66, 21);
		panelConfigTemper2.add(textFieldConfigTemperSensitivity);
		textFieldConfigTemperSensitivity.setColumns(10);
		
		JPanel MaintenancePanel = new JPanel();
		tabFunList.addTab("Maintenance", null, MaintenancePanel, null);
		MaintenancePanel.setLayout(null);
		
		JPanel Maintenance2Panel = new JPanel();
		Maintenance2Panel.setBorder(new TitledBorder(null, "Maintenance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Maintenance2Panel.setBounds(10, 10, 598, 59);
		MaintenancePanel.add(Maintenance2Panel);
		Maintenance2Panel.setLayout(null);
		
		JButton btnMaintenanceReboot = new JButton("Reboot");
		btnMaintenanceReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_Reboot(lpUserID);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_Reboot failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnMaintenanceReboot.setBounds(10, 21, 93, 23);
		Maintenance2Panel.add(btnMaintenanceReboot);
		
		JButton btnMaintenanceFactoryDefault = new JButton("Factory Default");
		btnMaintenanceFactoryDefault.setEnabled(false);
		btnMaintenanceFactoryDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				boolean bRet = netdevsdk.NETDEV_RestoreConfig(lpUserID);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_RestoreConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnMaintenanceFactoryDefault.setBounds(155, 21, 123, 23);
		Maintenance2Panel.add(btnMaintenanceFactoryDefault);
		
		JPanel MaintenanceManualRecordPanel = new JPanel();
		MaintenanceManualRecordPanel.setBorder(new TitledBorder(null, "Manual Record", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		MaintenanceManualRecordPanel.setBounds(10, 79, 598, 59);
		MaintenancePanel.add(MaintenanceManualRecordPanel);
		MaintenanceManualRecordPanel.setLayout(null);
		
		JButton btnMaintenanceManualRecordStart = new JButton("Start");
		btnMaintenanceManualRecordStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_MANUAL_RECORD_CFG_S stManualRecordCfg = new NETDEV_MANUAL_RECORD_CFG_S();
				stManualRecordCfg.dwChannelID = ChannelID;
				stManualRecordCfg.enRecordType = NETDEV_RECORD_TYPE_E.NETDEV_RECORD_TYPE_MANUAL;
				
				boolean bRet = netdevsdk.NETDEV_StartManualRecord(lpUserID, stManualRecordCfg);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_StartManualRecord failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnMaintenanceManualRecordStart.setBounds(10, 21, 93, 23);
		MaintenanceManualRecordPanel.add(btnMaintenanceManualRecordStart);
		
		JButton btnMaintenanceManualRecordStop = new JButton("Stop");
		btnMaintenanceManualRecordStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				
				NETDEV_MANUAL_RECORD_CFG_S stManualRecordCfg = new NETDEV_MANUAL_RECORD_CFG_S();
				stManualRecordCfg.dwChannelID = ChannelID;
				stManualRecordCfg.enRecordType = NETDEV_RECORD_TYPE_E.NETDEV_RECORD_TYPE_MANUAL;
				
				boolean bRet = netdevsdk.NETDEV_StopManualRecord(lpUserID, stManualRecordCfg);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_StopManualRecord failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnMaintenanceManualRecordStop.setBounds(158, 21, 93, 23);
		MaintenanceManualRecordPanel.add(btnMaintenanceManualRecordStop);
		
		JPanel MaintenanceManualConfigurationPanel = new JPanel();
		MaintenanceManualConfigurationPanel.setBorder(new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		MaintenanceManualConfigurationPanel.setBounds(10, 148, 598, 65);
		MaintenancePanel.add(MaintenanceManualConfigurationPanel);
		MaintenanceManualConfigurationPanel.setLayout(null);
		
		JButton btnMaintenanceManualConfigurationExport = new JButton("Export");
		btnMaintenanceManualConfigurationExport.setEnabled(false);
		btnMaintenanceManualConfigurationExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(null == lpUserID){
//					JOptionPane.showMessageDialog(null, "Please Login device first.");
//					return;
//				}
//				
//				if(ChannelID == 0){
//					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
//					return;
//				}
//				
//				NETDEV_DEVICE_BASICINFO_S stDeviceInfo = new NETDEV_DEVICE_BASICINFO_S();
//				IntByReference dwBytesReturned = new IntByReference();
//				boolean bRet = netdevsdk.NETDEV_GetDevConfig(lpUserID, ChannelID, NETDEV_CONFIG_COMMAND_E.NETDEV_GET_DEVICECFG, stDeviceInfo.getPointer(), stDeviceInfo.size(), dwBytesReturned);
//                if(bRet != true)
//                {
//                	System.out.printf("NETDEV_GetDevConfig failed:%d\n", netdevsdk.NETDEV_GetLastError());
//                    return;
//                }
//                
//                String strFileNameString = Common.byteArrayToString(stDeviceInfo.szDevModel) + "_" + Common.byteArrayToString(stDeviceInfo.szMacAddress) + "_" + "config.tgz";
//                String strCurrentpathString = strPicturePath +  strFileNameString;
//				
//				
//				bRet = netdevsdk.NETDEV_GetConfigFile(lpUserID, strCurrentpathString);
//                if(bRet != true)
//                {
//                	System.out.printf("NETDEV_GetConfigFile failed:%d\n", netdevsdk.NETDEV_GetLastError());
//                    return;
//                }
			}
		});
		btnMaintenanceManualConfigurationExport.setBounds(10, 24, 77, 23);
		MaintenanceManualConfigurationPanel.add(btnMaintenanceManualConfigurationExport);
		
		JLabel lblMaintenanceManualConfigurationImportConfiguration = new JLabel("Import Configuration");
		lblMaintenanceManualConfigurationImportConfiguration.setBounds(97, 28, 129, 15);
		MaintenanceManualConfigurationPanel.add(lblMaintenanceManualConfigurationImportConfiguration);
		lblMaintenanceManualConfigurationImportConfiguration.setEnabled(false);
		
		textFieldMaintenanceManualConfigurationImportConfiguration = new JTextField();
		textFieldMaintenanceManualConfigurationImportConfiguration.setBounds(221, 25, 170, 21);
		MaintenanceManualConfigurationPanel.add(textFieldMaintenanceManualConfigurationImportConfiguration);
		textFieldMaintenanceManualConfigurationImportConfiguration.setColumns(10);
		textFieldMaintenanceManualConfigurationImportConfiguration.setEnabled(false);
		
		JButton btnMaintenanceManualConfigurationBrowse = new JButton("Browse");
		btnMaintenanceManualConfigurationBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fcFileChooser = new JFileChooser();
			    
			    //设置不允许多选
				fcFileChooser.setMultiSelectionEnabled(false); 
			    int result=fcFileChooser.showSaveDialog(null); 
			    if (result==JFileChooser.APPROVE_OPTION)
			    {
			    	fcFileChooser.getSelectedFile();
			    	String strFilePath = fcFileChooser.getSelectedFile().getAbsolutePath();
			    	
			    	textFieldMaintenanceManualConfigurationImportConfiguration.setText(strFilePath);
			    }
			}
		});
		btnMaintenanceManualConfigurationBrowse.setBounds(410, 24, 83, 23);
		MaintenanceManualConfigurationPanel.add(btnMaintenanceManualConfigurationBrowse);
		btnMaintenanceManualConfigurationBrowse.setEnabled(false);
		
		JButton btnMaintenanceManualConfigurationImport = new JButton("Import");
		btnMaintenanceManualConfigurationImport.setEnabled(false);
		btnMaintenanceManualConfigurationImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				if(null == lpUserID){
//					JOptionPane.showMessageDialog(null, "Please Login device first.");
//					return;
//				}
//				
//				boolean bRet = netdevsdk.NETDEV_SetConfigFile(lpUserID, textFieldMaintenanceManualConfigurationImportConfiguration.getText());
//                if(bRet != true)
//                {
//                	System.out.printf("NETDEV_SetConfigFile failed:%d\n", netdevsdk.NETDEV_GetLastError());
//                    return;
//                }
			}
		});
		btnMaintenanceManualConfigurationImport.setBounds(503, 24, 74, 23);
		MaintenanceManualConfigurationPanel.add(btnMaintenanceManualConfigurationImport);
		
		JPanel MaintenanceUpgradePanel = new JPanel();
		MaintenanceUpgradePanel.setBorder(new TitledBorder(null, "Upgrade", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		MaintenanceUpgradePanel.setBounds(10, 223, 598, 65);
		MaintenancePanel.add(MaintenanceUpgradePanel);
		MaintenanceUpgradePanel.setLayout(null);
		
		JButton btnMaintenanceUpgradeBrowse = new JButton("Browse");
		btnMaintenanceUpgradeBrowse.setEnabled(false);
		btnMaintenanceUpgradeBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fcFileChooser = new JFileChooser();
			    
			    //设置不允许多选
				fcFileChooser.setMultiSelectionEnabled(false); 
			    int result=fcFileChooser.showSaveDialog(null); 
			    if (result==JFileChooser.APPROVE_OPTION)
			    {
			    	fcFileChooser.getSelectedFile();
			    	String strFilePath = fcFileChooser.getSelectedFile().getAbsolutePath();
			    	
			    	textFieldMaintenanceUpgradeBrowse.setText(strFilePath);
			    }
			}
		});
		btnMaintenanceUpgradeBrowse.setBounds(10, 21, 93, 23);
		MaintenanceUpgradePanel.add(btnMaintenanceUpgradeBrowse);
		
		textFieldMaintenanceUpgradeBrowse = new JTextField();
		textFieldMaintenanceUpgradeBrowse.setEnabled(false);
		textFieldMaintenanceUpgradeBrowse.setBounds(116, 22, 368, 21);
		MaintenanceUpgradePanel.add(textFieldMaintenanceUpgradeBrowse);
		textFieldMaintenanceUpgradeBrowse.setColumns(10);
		
		JButton btnMaintenanceUpgradeUpgrade = new JButton("Upgrade");
		btnMaintenanceUpgradeUpgrade.setEnabled(false);
		btnMaintenanceUpgradeUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(null == lpUserID){
//					JOptionPane.showMessageDialog(null, "Please Login device first.");
//					return;
//				}
//				
//				String pszTaskNo = "";
//				int dwUpgradeType = NETDEV_UPGRADE_TYPE_E.NETDEV_UPGRADE_TYPE_LOCAL;
//				boolean bRet = netdevsdk.NETDEV_UpgradeDevice(lpUserID, dwUpgradeType, pszTaskNo);
//                if(bRet != true)
//                {
//                	System.out.printf("NETDEV_UpgradeDevice failed:%d\n", netdevsdk.NETDEV_GetLastError());
//                    return;
//                }
//                
//                if(dwUpgradeType == NETDEV_UPGRADE_TYPE_E.NETDEV_UPGRADE_TYPE_LOCAL)
//                {
//                	try {
//						Thread.sleep(10000);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//                	
//                	bRet = netdevsdk.NETDEV_UploadDeviceFirmware(lpUserID, textFieldMaintenanceUpgradeBrowse.getText(), pszTaskNo);
//                    if(bRet != true)
//                    {
//                    	System.out.printf("NETDEV_UploadDeviceFirmware failed:%d\n", netdevsdk.NETDEV_GetLastError());
//                        return;
//                    }
//                }
			}
		});
		btnMaintenanceUpgradeUpgrade.setBounds(499, 21, 89, 23);
		MaintenanceUpgradePanel.add(btnMaintenanceUpgradeUpgrade);
		
		JPanel UserPanel = new JPanel();
		tabFunList.addTab("User", null, UserPanel, null);
		UserPanel.setLayout(null);
		
		JPanel UserListpanel = new JPanel();
		UserListpanel.setBorder(new TitledBorder(null, "User List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		UserListpanel.setBounds(10, 10, 598, 161);
		UserPanel.add(UserListpanel);
		UserListpanel.setLayout(null);
		
		JScrollPane scrollPaneUserList = new JScrollPane();
		scrollPaneUserList.setBounds(10, 20, 578, 100);
		UserListpanel.add(scrollPaneUserList);
		
		tableUserList = new JTable();
		tableUserList.setModel(UserListTableModel);
		scrollPaneUserList.setViewportView(tableUserList);
		
		JButton btnUserListGetUserList = new JButton("GetUserList");
		btnUserListGetUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				UserListTableModel.setRowCount(0);
				
				NETDEV_USER_DETAIL_LIST_S stUserDetailList = new NETDEV_USER_DETAIL_LIST_S();
				for(int i = 0; i<NetDEVSDKLib.NETDEV_LEN_64; i++)
				{
					stUserDetailList.astUserInfo[i] = new NETDEV_USER_DETAIL_INFO_S();
				}
				stUserDetailList.write();
				
				boolean bRet = netdevsdk.NETDEV_GetUserDetailList(lpUserID, stUserDetailList);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_GetUserDetailList failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
                
                for(int i = 0; i < stUserDetailList.udwNum; i++)
                {
                	Vector<String> UserListVector= new Vector<String>();
                	
                	UserListVector.add(String.valueOf(i+1));
                	UserListVector.add(Common.byteArrayToString(stUserDetailList.astUserInfo[i].szUserName));
                	
                	if(stUserDetailList.astUserInfo[i].udwLevel == NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_ADMINISTRATOR)
                	{
                		UserListVector.add("ADMINISTRATOR");
                	}
                	else if(stUserDetailList.astUserInfo[i].udwLevel == NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_OPERATOR)
                	{
                		UserListVector.add("OPERATOR");
                	}
                	else if(stUserDetailList.astUserInfo[i].udwLevel == NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_USER)
                	{
                		UserListVector.add("USER");
                	}
                	else if(stUserDetailList.astUserInfo[i].udwLevel == NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_Default)
                	{
                		UserListVector.add("Default");
                	}
                	UserListTableModel.addRow(UserListVector);
                }
			}
		});
		btnUserListGetUserList.setBounds(324, 130, 106, 23);
		UserListpanel.add(btnUserListGetUserList);
		
		JButton btnUserListDeleteUser = new JButton("DeleteUser");
		btnUserListDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == UserListTableModel.getRowCount()  || tableUserList.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find user or seletc user first.");
					return;
				}
				
				String strUserName = (String) tableUserList.getValueAt(tableUserList.getSelectedRow(), 1);
				
				boolean bRet = netdevsdk.NETDEV_DeleteUser(lpUserID, strUserName);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_DeleteUser failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnUserListDeleteUser.setBounds(476, 130, 106, 23);
		UserListpanel.add(btnUserListDeleteUser);
		
		JPanel AddUserPanel = new JPanel();
		AddUserPanel.setBorder(new TitledBorder(null, "Add User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		AddUserPanel.setBounds(10, 181, 598, 55);
		UserPanel.add(AddUserPanel);
		AddUserPanel.setLayout(null);
		
		JLabel lblAddUserUserName = new JLabel("UserName");
		lblAddUserUserName.setBounds(10, 21, 66, 15);
		AddUserPanel.add(lblAddUserUserName);
		
		textFieldAddUserUserName = new JTextField();
		textFieldAddUserUserName.setBounds(94, 18, 66, 21);
		AddUserPanel.add(textFieldAddUserUserName);
		textFieldAddUserUserName.setColumns(10);
		
		JLabel lblAddUserPasswd = new JLabel("Password");
		lblAddUserPasswd.setBounds(183, 21, 61, 15);
		AddUserPanel.add(lblAddUserPasswd);
		
		textFieldlAddUserPasswd = new JTextField();
		textFieldlAddUserPasswd.setBounds(254, 18, 66, 21);
		AddUserPanel.add(textFieldlAddUserPasswd);
		textFieldlAddUserPasswd.setColumns(10);
		
		JLabel lbllAddUserUserType = new JLabel("User Type");
		lbllAddUserUserType.setBounds(340, 21, 65, 15);
		AddUserPanel.add(lbllAddUserUserType);
		
		JComboBox<Object> comboBoxAddUserUserType = new JComboBox<Object>();
		comboBoxAddUserUserType.setModel(new DefaultComboBoxModel<Object>(new String[] {"OPERATOR", "USER"}));
		comboBoxAddUserUserType.setBounds(415, 18, 78, 21);
		AddUserPanel.add(comboBoxAddUserUserType);
		
		JButton btnAddUserAdd = new JButton("Add");
		btnAddUserAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				NETDEV_USER_DETAIL_INFO_S stUserInfo = new NETDEV_USER_DETAIL_INFO_S();
				Common.stringToByteArray(textFieldAddUserUserName.getText(), stUserInfo.szUserName);
				Common.stringToByteArray(textFieldlAddUserPasswd.getText(), stUserInfo.szPassword);
				if(comboBoxAddUserUserType.getSelectedIndex() == 0)
				{
					stUserInfo.udwLevel = NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_OPERATOR;
				}
				else if(comboBoxAddUserUserType.getSelectedIndex() == 1)
				{
					stUserInfo.udwLevel = NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_USER;
				}

				boolean bRet = netdevsdk.NETDEV_CreateUser(lpUserID, stUserInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_CreateUser failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnAddUserAdd.setBounds(503, 17, 93, 23);
		AddUserPanel.add(btnAddUserAdd);
		
		JPanel ModifyUserPanel = new JPanel();
		ModifyUserPanel.setBorder(new TitledBorder(null, "Modify User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ModifyUserPanel.setBounds(10, 246, 598, 99);
		UserPanel.add(ModifyUserPanel);
		ModifyUserPanel.setLayout(null);
		
		JLabel lblModifyUserUserName = new JLabel("User Name");
		lblModifyUserUserName.setBounds(10, 25, 72, 15);
		ModifyUserPanel.add(lblModifyUserUserName);
		
		textFieldModifyUserUserName = new JTextField();
		textFieldModifyUserUserName.setBounds(92, 22, 66, 21);
		ModifyUserPanel.add(textFieldModifyUserUserName);
		textFieldModifyUserUserName.setColumns(10);
		
		JLabel lblModifyUserOldPassword = new JLabel("Old Password");
		lblModifyUserOldPassword.setBounds(172, 25, 95, 15);
		ModifyUserPanel.add(lblModifyUserOldPassword);
		
		textFieldModifyUserOldPassword = new JTextField();
		textFieldModifyUserOldPassword.setBounds(277, 22, 66, 21);
		ModifyUserPanel.add(textFieldModifyUserOldPassword);
		textFieldModifyUserOldPassword.setColumns(10);
		
		JLabel lblModifyUserUserType = new JLabel("User Type");
		lblModifyUserUserType.setBounds(10, 61, 68, 15);
		ModifyUserPanel.add(lblModifyUserUserType);
		
		JComboBox<Object> comboBoxModifyUserUserType = new JComboBox<Object>();
		comboBoxModifyUserUserType.setModel(new DefaultComboBoxModel<Object>(new String[] {"OPERATOR", "USER"}));
		comboBoxModifyUserUserType.setBounds(92, 59, 83, 21);
		ModifyUserPanel.add(comboBoxModifyUserUserType);
		
		JButton btnModifyUserModify = new JButton("Modify");
		btnModifyUserModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please Login device first.");
					return;
				}
				
				if(0 == UserListTableModel.getRowCount()  || tableUserList.getSelectedRow() < 0)
				{
					JOptionPane.showMessageDialog(null, "Please find user or seletc user first.");
					return;
				}
				
				//textFieldModifyUserUserName    textFieldModifyUserOldPassword    textFieldlblModifyUserNewPasswd     comboBoxModifyUserUserType
				
				NETDEV_USER_MODIFY_DETAIL_INFO_S stUserInfo = new NETDEV_USER_MODIFY_DETAIL_INFO_S();
				Common.stringToByteArray(textFieldModifyUserUserName.getText(), stUserInfo.stUserInfo.szUserName);
				Common.stringToByteArray(textFieldModifyUserOldPassword.getText(), stUserInfo.szCurrentPassword);
				Common.stringToByteArray(textFieldlblModifyUserNewPasswd.getText(), stUserInfo.szNewPassword);
				
				if(comboBoxModifyUserUserType.getSelectedIndex() == 0)
				{
					stUserInfo.stUserInfo.udwLevel = NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_OPERATOR;
				}
				else if(comboBoxModifyUserUserType.getSelectedIndex() == 1)
				{
					stUserInfo.stUserInfo.udwLevel = NETDEV_USER_LEVEL_E.NETDEV_USER_LEVEL_USER;
				}
				
				/* 管理员修改自己的信息 */
				if(textFieldModifyUserUserName.getText().compareTo("Admin") == 0)
				{
					stUserInfo.bIsModifyOther = 0;
				}
				else 
				{
					stUserInfo.bIsModifyOther = 1;
				}
				
				/* 如果旧密码和新密码一样 */
				if(textFieldModifyUserOldPassword.getText().compareTo(textFieldlblModifyUserNewPasswd.getText()) == 0)
				{
					stUserInfo.bIsModifyPassword = 0;
				}
				else 
				{
					stUserInfo.bIsModifyPassword = 1;
				}
				
				boolean bRet = netdevsdk.NETDEV_ModifyUser(lpUserID, stUserInfo);
                if(bRet != true)
                {
                	System.out.printf("NETDEV_ModifyUser failed:%d\n", netdevsdk.NETDEV_GetLastError());
                    return;
                }
			}
		});
		btnModifyUserModify.setBounds(198, 57, 93, 23);
		ModifyUserPanel.add(btnModifyUserModify);
		
		JLabel lblModifyUserNote = new JLabel("Note: This function is available for NVR only");
		lblModifyUserNote.setBounds(310, 61, 278, 15);
		ModifyUserPanel.add(lblModifyUserNote);
		
		JLabel lblModifyUserNewPasswd = new JLabel("New Password");
		lblModifyUserNewPasswd.setBounds(368, 25, 93, 15);
		ModifyUserPanel.add(lblModifyUserNewPasswd);
		
		textFieldlblModifyUserNewPasswd = new JTextField();
		textFieldlblModifyUserNewPasswd.setBounds(471, 22, 66, 21);
		ModifyUserPanel.add(textFieldlblModifyUserNewPasswd);
		textFieldlblModifyUserNewPasswd.setColumns(10);
		
		Panel PlayLabel = new Panel();
		PlayLabel.setBounds(643, 279, 687, 510);
		frame.getContentPane().add(PlayLabel);
		PlayLabel.setBackground(Color.LIGHT_GRAY);
		
		JTabbedPane tabDevLoginList = new JTabbedPane(JTabbedPane.TOP);
		tabDevLoginList.setBounds(10, 10, 354, 184);
		frame.getContentPane().add(tabDevLoginList);
		
		JPanel LocalLoginPanel = new JPanel();
		tabDevLoginList.addTab("Local Login", null, LocalLoginPanel, null);
		LocalLoginPanel.setLayout(null);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(35, 10, 47, 15);
		LocalLoginPanel.add(lblIp);
		
		textIP = new JTextField();
		textIP.setBounds(92, 7, 177, 21);
		LocalLoginPanel.add(textIP);
		textIP.setColumns(10);
		
		textPort = new JTextField();
		textPort.setBounds(92, 35, 177, 21);
		LocalLoginPanel.add(textPort);
		textPort.setColumns(10);
		
		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(10, 66, 67, 15);
		LocalLoginPanel.add(lblUsername);
		
		textUserName = new JTextField();
		textUserName.setBounds(92, 63, 177, 21);
		LocalLoginPanel.add(textUserName);
		textUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 94, 67, 15);
		LocalLoginPanel.add(lblPassword);
		
		textDevicePassword = new JTextField();
		textDevicePassword.setBounds(92, 91, 177, 21);
		LocalLoginPanel.add(textDevicePassword);
		textDevicePassword.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(23, 38, 54, 15);
		LocalLoginPanel.add(lblPort);
		
		JButton btnLogin = new JButton("Login");
		
		btnLogin.setBounds(35, 122, 76, 23);
		LocalLoginPanel.add(btnLogin);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setEnabled(false);
		btnLogout.setBounds(165, 122, 76, 23);
		LocalLoginPanel.add(btnLogout);
		DefaultTableModel DeviceTableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ChannelID", "Status", "Name"
				}
			);
		
		JPanel CloudLoginPanel = new JPanel();
		tabDevLoginList.addTab("Cloud Login", null, CloudLoginPanel, null);
		CloudLoginPanel.setLayout(null);
		
		JLabel lblCloudUrl = new JLabel("Cloud URL:");
		lblCloudUrl.setBounds(12, 22, 68, 15);
		CloudLoginPanel.add(lblCloudUrl);
		
		textCloudURL = new JTextField();
		textCloudURL.setBounds(119, 19, 171, 21);
		CloudLoginPanel.add(textCloudURL);
		textCloudURL.setColumns(10);
		
		JLabel lblCloudUserNameLabel = new JLabel("User Name/Mobile:");
		lblCloudUserNameLabel.setBounds(12, 50, 109, 15);
		CloudLoginPanel.add(lblCloudUserNameLabel);
		
		textCloudUserName = new JTextField();
		textCloudUserName.setBounds(119, 47, 171, 21);
		CloudLoginPanel.add(textCloudUserName);
		textCloudUserName.setColumns(10);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(12, 78, 54, 15);
		CloudLoginPanel.add(lblPassword_1);
		
		textCloudPassword = new JTextField();
		textCloudPassword.setBounds(119, 75, 171, 21);
		CloudLoginPanel.add(textCloudPassword);
		textCloudPassword.setColumns(10);
		
		JButton btnLogoutCloud = new JButton("LogoutCloud");
		btnLogoutCloud.setEnabled(false);
		btnLogoutCloud.setBounds(166, 106, 109, 23);
		CloudLoginPanel.add(btnLogoutCloud);
		
		JButton btnLoginCloud = new JButton("LoginCloud");
		btnLoginCloud.setBounds(22, 106, 109, 23);
		CloudLoginPanel.add(btnLoginCloud);
		
		JPanel CloudDevPanel = new JPanel();
		tabDevLoginList.addTab("Cloud Device Login", null, CloudDevPanel, null);
		CloudDevPanel.setLayout(null);
		
		JScrollPane CloudDeviceScrollPane = new JScrollPane();
		CloudDeviceScrollPane.setBounds(0, 0, 349, 115);
		CloudDevPanel.add(CloudDeviceScrollPane);
		
		DefaultTableModel CloudDevTableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"UserName", "Name", "Model", "Serial", "Status"
				}
			);
		CloudDevListTable = new JTable();
		CloudDeviceScrollPane.setViewportView(CloudDevListTable);
		CloudDevListTable.setModel(CloudDevTableModel);
		
		textFieldCloudDevUserName = new JTextField();
		textFieldCloudDevUserName.setEnabled(false);
		textFieldCloudDevUserName.setBounds(75, 125, 83, 21);
		CloudDevPanel.add(textFieldCloudDevUserName);
		textFieldCloudDevUserName.setColumns(10);
		
		JLabel lblCloudUsername = new JLabel("UserName:");
		lblCloudUsername.setBounds(10, 128, 64, 15);
		CloudDevPanel.add(lblCloudUsername);
		
		JButton btnLoginCloudDevice = new JButton("Login");
		btnLoginCloudDevice.setEnabled(false);
		btnLoginCloudDevice.setBounds(174, 125, 72, 23);
		CloudDevPanel.add(btnLoginCloudDevice);
		
		JButton btnLogoutCloudDevice = new JButton("Logout");
		btnLogoutCloudDevice.setEnabled(false);
		btnLogoutCloudDevice.setBounds(263, 125, 76, 23);
		CloudDevPanel.add(btnLogoutCloudDevice);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(643, 10, 687, 259);
		frame.getContentPane().add(tabbedPane);
		
		JPanel DiscoveryPanel = new JPanel();
		tabbedPane.addTab("Discovery", null, DiscoveryPanel, null);
		DiscoveryPanel.setLayout(null);
		
		JScrollPane DiscoveryScrollPane = new JScrollPane();
		DiscoveryScrollPane.setEnabled(false);
		DiscoveryScrollPane.setBounds(10, 10, 662, 168);
		DiscoveryPanel.add(DiscoveryScrollPane);
		
		DiscoveryTable = new JTable();
		DiscoveryTable.setModel(DiscoveryTableModel);
		DiscoveryScrollPane.setViewportView(DiscoveryTable);
		
		JButton btnDiscovery = new JButton("Discovery");
		btnDiscovery.setBounds(385, 187, 94, 23);
		DiscoveryPanel.add(btnDiscovery);
		
		textStartIP = new JTextField();
		textStartIP.setBounds(83, 189, 114, 21);
		DiscoveryPanel.add(textStartIP);
		textStartIP.setColumns(10);
		
		JLabel lblStartip = new JLabel("StartIP:");
		lblStartip.setBounds(20, 192, 54, 15);
		DiscoveryPanel.add(lblStartip);
		
		textEndIP = new JTextField();
		textEndIP.setBounds(251, 189, 114, 21);
		DiscoveryPanel.add(textEndIP);
		textEndIP.setColumns(10);
		
		JLabel lblEndip = new JLabel("EndIP:");
		lblEndip.setBounds(207, 188, 46, 22);
		DiscoveryPanel.add(lblEndip);

		textIP.setText("192.168.2.85");
		textUserName.setText("admin");
		textDevicePassword.setText("Admin123");
		textPort.setText("80");
		textCloudURL.setText("192.168.2.233");
		textCloudUserName.setText("zhao");
		textCloudPassword.setText("A123456");
		
		JScrollPane DeviceScrollPane = new JScrollPane();
		DeviceScrollPane.setBounds(374, 10, 255, 184);
		frame.getContentPane().add(DeviceScrollPane);
		
		DeviceTable = new JTable();
		DeviceTable.setModel(DeviceTableModel);
		DeviceScrollPane.setViewportView(DeviceTable);
		
		JLabel lblDeviceType = new JLabel("DeviceType:");
		lblDeviceType.setForeground(Color.BLACK);
		lblDeviceType.setBounds(10, 204, 72, 15);
		frame.getContentPane().add(lblDeviceType);
		
		JComboBox<String> LoginProtoComboBox = new JComboBox<String>();
		LoginProtoComboBox.setBounds(240, 201, 80, 21);
		frame.getContentPane().add(LoginProtoComboBox);
		LoginProtoComboBox.addItem("Onvif");
		LoginProtoComboBox.addItem("Private");
		
		JLabel LoginProtoLabel = new JLabel("LoginProto:");
		LoginProtoLabel.setForeground(Color.BLACK);
		LoginProtoLabel.setBounds(168, 204, 67, 15);
		frame.getContentPane().add(LoginProtoLabel);
		
		JLabel lblLoginDevice = new JLabel("Note: Select device type and protocol before login and VMS only supports private protocols.");
		lblLoginDevice.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblLoginDevice.setForeground(Color.RED);
		lblLoginDevice.setHorizontalAlignment(SwingConstants.LEFT);
		lblLoginDevice.setVerticalAlignment(SwingConstants.TOP);
		lblLoginDevice.setBounds(10, 232, 619, 37);
		frame.getContentPane().add(lblLoginDevice);
		btnDiscovery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strBeginIP = textStartIP.getText();
				String strEndIP = textEndIP.getText();
				DiscoveryTableModel.setRowCount(0);
				if(strBeginIP.isEmpty() && strEndIP.isEmpty()){
					netdevsdk.NETDEV_SetDiscoveryCallBack(cbDiscoveryCallBack, null);
					netdevsdk.NETDEV_Discovery("0.0.0.0", "0.0.0.0");
				}
				else if(Common.isIP(strBeginIP) && Common.isIP(strEndIP)){
					netdevsdk.NETDEV_SetDiscoveryCallBack(cbDiscoveryCallBack, null);
					boolean bRet = netdevsdk.NETDEV_Discovery(strBeginIP, strEndIP);
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Discovery failed. Please check the entered IP address.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please enter the correct start and end addresses, or do not enter.");
				}
			}
		});
		
		btnLoginCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strCloudURL = textCloudURL.getText();
				String strCloudUserName = textCloudUserName.getText();
				String strCloudPassword = textCloudPassword.getText();
				if(strCloudURL.isEmpty() || strCloudUserName.isEmpty() || strCloudPassword.isEmpty()){
					JOptionPane.showMessageDialog(null, "Please enter the cloud account information first.");
					return;	
				}
				CloudDevTableModel.setRowCount(0);
				lpCloudUserID = netdevsdk.NETDEV_LoginCloud(strCloudURL, strCloudUserName, strCloudPassword);
				if(null ==lpCloudUserID){
					JOptionPane.showMessageDialog(null, "Please check that the cloud account information is correct.");
				}
				else{
					new Thread(new Runnable() {
						@Override
						public void run() {
							Pointer lpFindHandle = netdevsdk.NETDEV_FindCloudDevListEx(lpCloudUserID);
							if(null != lpFindHandle){
								NETDEV_CLOUD_DEV_BASIC_INFO_S pstDevInfo = new NETDEV_CLOUD_DEV_BASIC_INFO_S();
								boolean bRet = netdevsdk.NETDEV_FindNextCloudDevInfoEx(lpFindHandle, pstDevInfo);
								while(bRet){
									Vector<String> vector = new Vector<String>();
									vector.add(Common.utfToString(pstDevInfo.szDevUserName));
								    vector.add(Common.utfToString(pstDevInfo.szDevName));
								    vector.add(Common.utfToString(pstDevInfo.szDevModel));
								    vector.add(Common.utfToString(pstDevInfo.szDevSerialNum));
								    if(1 == pstDevInfo.bKeepLiveStatus){
								    	vector.add("online");
								    }
								    else{
								    	vector.add("offline");
								    }
									CloudDevTableModel.insertRow(0,vector);
									bRet = netdevsdk.NETDEV_FindNextCloudDevInfoEx(lpFindHandle, pstDevInfo);
								}
								netdevsdk.NETDEV_FindCloseCloudDevListEx(lpFindHandle);
							}
						}
						
						{
							btnLogin.setEnabled(false);
							btnLogout.setEnabled(false);
							btnLoginCloud.setEnabled(false);
							btnLogoutCloud.setEnabled(true);
							btnLoginCloudDevice.setEnabled(true);
							btnLogoutCloudDevice.setEnabled(false);
						}
					}).start();
					JOptionPane.showMessageDialog(null, "Cloud login successful, please select device login on cloud device login page.");
				}
			}
		});
		
		btnLogoutCloud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null !=lpUserID){
					JOptionPane.showMessageDialog(null, "Please log out the device first.");
					return ;
				}
				if(null !=lpCloudUserID){
					netdevsdk.NETDEV_Logout(lpCloudUserID);
					lpCloudUserID = null;
				}
				CloudDevTableModel.setRowCount(0);
				
				{
					btnLogin.setEnabled(true);
					btnLogout.setEnabled(false);
					btnLoginCloud.setEnabled(true);
					btnLogoutCloud.setEnabled(false);
					btnLoginCloudDevice.setEnabled(false);
					btnLogoutCloudDevice.setEnabled(false);
				}
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strUserName = textUserName.getText();
				if(strUserName.isEmpty()){
					JOptionPane.showMessageDialog(null, "Enter UserName first.");
					return;
				}
				String strPassword = textDevicePassword.getText();
				String strIPAddr = textIP.getText();
				if(strIPAddr.isEmpty()){
					JOptionPane.showMessageDialog(null, "Enter IP first.");
					return;
				}
				String strPort = textPort.getText();
				if(strPort.isEmpty()){
					JOptionPane.showMessageDialog(null, "Enter Port first.");
					return;
				}
				
				NetDEVSDKLib.NETDEV_DEVICE_LOGIN_INFO_S stDevLoginInfo = new NetDEVSDKLib.NETDEV_DEVICE_LOGIN_INFO_S();
				NetDEVSDKLib.NETDEV_SELOG_INFO_S stSELogInfo = new NetDEVSDKLib.NETDEV_SELOG_INFO_S();
				
				System.arraycopy(strUserName.getBytes(), 0, stDevLoginInfo.szUserName, 0, strUserName.getBytes().length);
				System.arraycopy(strPassword.getBytes(), 0, stDevLoginInfo.szPassword, 0, strPassword.getBytes().length);
				System.arraycopy(strIPAddr.getBytes(), 0, stDevLoginInfo.szIPAddr, 0, strIPAddr.getBytes().length);
				
				stDevLoginInfo.dwPort = Integer.parseInt(strPort);
				stDevLoginInfo.dwLoginProto = LoginProtoComboBox.getSelectedIndex();
				lpUserID = netdevsdk.NETDEV_Login_V30(stDevLoginInfo, stSELogInfo);
				if(null != lpUserID){
					new Thread(new Runnable() {
						@Override
						public void run() {
							if(DeviceTypeComboBox.getSelectedIndex() == 0){
								int nMaxChlCount = 256;
								IntByReference dwChlCount = new IntByReference(nMaxChlCount);
								NetDEVSDKLib.NETDEV_VIDEO_CHL_DETAIL_INFO_EX_S[] stVideoChlList = (NetDEVSDKLib.NETDEV_VIDEO_CHL_DETAIL_INFO_EX_S[])new NetDEVSDKLib.NETDEV_VIDEO_CHL_DETAIL_INFO_EX_S().toArray(nMaxChlCount);
								boolean bRet = netdevsdk.NETDEV_QueryVideoChlDetailListEx(lpUserID, dwChlCount, stVideoChlList);
								DeviceTableModel.setRowCount(0);
								if(bRet)
								{
									for(int i=0; i<dwChlCount.getValue(); i++)
									{
										Vector<String> vector = new Vector<String>();
								        vector.add(String.valueOf(stVideoChlList[i].dwChannelID));
								        vector.add(Common.DevideStatusToString(stVideoChlList[i].enStatus));
								        vector.add(Common.utfToString(stVideoChlList[i].szChnName));
										
								        DeviceTableModel.insertRow(i,vector);
								        
									}
								}
								
								netdevsdk.NETDEV_SetFaceSnapshotCallBack(lpUserID, cbFaceSnapshotCallBack, null);
								netdevsdk.NETDEV_SetCarPlateCallBack(lpUserID, cbCarPlateCallBack, null);
								netdevsdk.NETDEV_SetPassengerFlowStatisticCallBack(lpUserID, cbPassengerFlowStatisticCallBack, null);
								
							}
							else{
								
								gastLoginDeviceInfo = new NETDEMO_DEV_LOGININFO_S();
								gastLoginDeviceInfo.pHandle = lpUserID;
							    int devIndex= 0;
								int dwDeviceType = NETDEV_DEVICE_MAIN_TYPE_E.NETDEV_DTYPE_MAIN_ENCODE;
								for(int i =0; i < 2; i++)
								{
									Pointer lpDevFindHandle = netdevsdk.NETDEV_FindDevList(lpUserID, dwDeviceType);
									if(lpDevFindHandle != null)
									{
									    while(true)
									    {
										    NETDEV_DEV_BASIC_INFO_S stDevInfo = new NETDEV_DEV_BASIC_INFO_S();
										    stDevInfo.stDevAddr = new NETDEV_IPADDR_INFO_S();
										    stDevInfo.stDevUserInfo = new NETDEV_USER_SIMPLE_INFO_S();
									    	if(true == netdevsdk.NETDEV_FindNextDevInfo(lpDevFindHandle, stDevInfo))
									    	{
									    		NETDEMO_DEVICE_INFO_S stDemoDeviceInfo = new NETDEMO_DEVICE_INFO_S();
									    		stDemoDeviceInfo.stDevBasicInfo = stDevInfo;
									    		stDemoDeviceInfo.dwDevIndex = devIndex;
									    		gastLoginDeviceInfo.stDevLoginInfo.put(devIndex, stDemoDeviceInfo);
									    		gastLoginDeviceInfo.dwDevNum++;
									    		
									    		Pointer lpChnFindHandle = netdevsdk.NETDEV_FindDevChnList(gastLoginDeviceInfo.pHandle, stDevInfo.dwDevID, NETDEV_CHN_TYPE_E.NETDEV_CHN_TYPE_ENCODE);
									    		if(null != lpChnFindHandle)
									    		{

									                while(true)
									                {
										    			NETDEV_DEV_CHN_ENCODE_INFO_S stDevChnEncodeInfo = new NETDEV_DEV_CHN_ENCODE_INFO_S();
										    			stDevChnEncodeInfo.write();
										    			IntByReference dwBytesReturned = new IntByReference(0);
									                	if(true == netdevsdk.NETDEV_FindNextDevChn(lpChnFindHandle, stDevChnEncodeInfo.getPointer(),stDevChnEncodeInfo.size(), dwBytesReturned))
									                	{
									                		stDevChnEncodeInfo.read(); //将指针内存中字段写入到结构体中
															Vector<String> vector = new Vector<String>();
													        vector.add(String.valueOf(stDevChnEncodeInfo.stChnBaseInfo.dwChannelID));
													        vector.add(Common.CHNStatusToString(stDevChnEncodeInfo.stChnBaseInfo.dwChnStatus));
													        vector.add(Common.utfToString(stDevChnEncodeInfo.stChnBaseInfo.szChnName));
													        DeviceTableModel.addRow(vector);
									                		
									                		gastLoginDeviceInfo.stDevLoginInfo.get(devIndex).vecChanInfo.add(stDevChnEncodeInfo);
									                		gastLoginDeviceInfo.stDevLoginInfo.get(devIndex).dwChnNum++;
									                	}
									                	else 
									                	{
														    break;	
														}
									                }
									                netdevsdk.NETDEV_FindCloseDevChn(lpChnFindHandle);
									    		}
									    	}
									    	else
									    	{
												break;
											}
									    	devIndex++;
									    }
									    netdevsdk.NETDEV_FindCloseDevInfo(lpDevFindHandle);
									}
									dwDeviceType = NETDEV_DEVICE_MAIN_TYPE_E.NETDEV_DTYPE_MAIN_BAYONET;
								}
							}
						}
					}).start();
					
					{
						btnLogin.setEnabled(false);
						btnLogout.setEnabled(true);
						btnLoginCloud.setEnabled(false);
						btnLogoutCloud.setEnabled(false);
						btnLoginCloudDevice.setEnabled(false);
						btnLogoutCloudDevice.setEnabled(false);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "login failed, Please check device type and protocol.");
				}
			}
		});
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpTalkHandle){
					netdevsdk.NETDEV_StopVoiceCom(lpTalkHandle);
					lpTalkHandle = null;
				}
				if(null != lpPlayHandle){
					netdevsdk.NETDEV_StopRealPlay(lpPlayHandle);
					lpPlayHandle = null;
					PlayLabel.repaint();  
				}
				if(null != lpUserID){
					netdevsdk.NETDEV_SetPassengerFlowStatisticCallBack(lpUserID, null, null);
					netdevsdk.NETDEV_Logout(lpUserID);
					lpUserID = null;
				}
				DeviceTableModel.setRowCount(0);
				
				{
					btnLogin.setEnabled(true);
					btnLogout.setEnabled(false);
					btnLoginCloud.setEnabled(true);
					btnLogoutCloud.setEnabled(false);
					btnLoginCloudDevice.setEnabled(false);
					btnLogoutCloudDevice.setEnabled(false);
				}

			}
		});
		btnStartLive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null != lpPlayHandle){
					JOptionPane.showMessageDialog(null, "Please stop playing first.");
					return;
				}
				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select an online channel after login.");
					return;
				}
				NetDEVSDKLib.NETDEV_PREVIEWINFO_S stPreviewInfo = new NetDEVSDKLib.NETDEV_PREVIEWINFO_S();
				stPreviewInfo.dwChannelID = ChannelID;
				stPreviewInfo.dwStreamType = 0;
				stPreviewInfo.dwLinkMode = 1;
				stPreviewInfo.hPlayWnd = Native.getComponentPointer(PlayLabel);
				stPreviewInfo.dwFluency = 0;
				stPreviewInfo.dwStreamMode = 0;
				stPreviewInfo.dwLiveMode = 0;
				stPreviewInfo.dwDisTributeCloud = 0;
				stPreviewInfo.dwallowDistribution = 0;
				stPreviewInfo.dwFluency = 0;
    
				lpPlayHandle = netdevsdk.NETDEV_RealPlay(lpUserID, stPreviewInfo, null, null);
				if(null == lpPlayHandle){
					JOptionPane.showMessageDialog(null, "RealPlay failed");
				}
				else{
					btnStartLive.setEnabled(false);
					btnStopLive.setEnabled(true);
					
					//开启随流音频声音，可通过接口NETDEV_OpenSound、NETDEV_SoundVolumeControl、NETDEV_GetSoundVolume、NETDEV_CloseSound控制音量
					netdevsdk.NETDEV_OpenSound(lpPlayHandle);
				}
				
			}
		});
		
		btnStopLive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpPlayHandle){
					netdevsdk.NETDEV_StopRealPlay(lpPlayHandle);
					lpPlayHandle = null;
					PlayLabel.repaint();
				}
				btnStartLive.setEnabled(true);
				btnStopLive.setEnabled(false);
			}
		});
		
		btnStartSourceData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null != lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_SetPlayDataCallBack(lpPlayHandle, fPlayDataCallBack, 1, null);				
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Set Play Source Data failed.");
					}
					else{
						btnStartSourceData.setEnabled(false);
						btnStopSourceData.setEnabled(true);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Start live, please.");
				}
			}
		});
		
		btnStopSourceData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpPlayHandle){
					netdevsdk.NETDEV_SetPlayDataCallBack(lpPlayHandle, null, 1, null);
					btnStartSourceData.setEnabled(true);
					btnStopSourceData.setEnabled(false);
				}
			}
		});
		
		btnStartDecodeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_SetPlayDecodeVideoCB(lpPlayHandle, fPlayDecodeVideoCALLBACK, 1, null);				
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Set Play Decode Video failed.");
					}
					else{
						btnStopDecodeData.setEnabled(true);
						btnStartDecodeData.setEnabled(false);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Start live, please.");
				}
			}
		});
		
		btnStopDecodeData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpPlayHandle){
					netdevsdk.NETDEV_SetPlayDecodeVideoCB(lpPlayHandle, null, 1, null);
					btnStartDecodeData.setEnabled(true);
					btnStopDecodeData.setEnabled(false);
				}
			}
		});
		
		btnStartTalk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please log on to the device first.");
					return;
				}

				lpTalkHandle = netdevsdk.NETDEV_StartVoiceCom(lpUserID, ChannelID, fDecodeAudioDataCallBack,null);
				if(null == lpTalkHandle){
					JOptionPane.showMessageDialog(null, "Start Talk failed.Please check if the control device supports voice talk.");
				}
				else{
					btnStartTalk.setEnabled(false);
					btnStopTalk.setEnabled(true);
				}
			}
		});
		
		btnStopTalk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpTalkHandle){
					netdevsdk.NETDEV_StopVoiceCom(lpTalkHandle);
					lpTalkHandle = null;	
					btnStartTalk.setEnabled(true);
					btnStopTalk.setEnabled(false);
				}
			}
		});
		
		btnStartRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpPlayHandle){
					strRecordPath = System.getProperty("user.dir").replaceFirst("/","").replaceAll("%20"," ") + "\\Record\\test" + String.valueOf(Common.timeStamp())+ "_D"+ChannelID;
					boolean bRet = netdevsdk.NETDEV_SaveRealData(lpPlayHandle, strRecordPath, 0);				
					if(!bRet){
						JOptionPane.showMessageDialog(null, "Start Record failed.Please check the path.");
					}
					else{
						bStartRecord = true;
						
						btnStopRecord.setEnabled(true);
						btnStartRecord.setEnabled(false);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Start live, please.");
				}
			}
		});
		
		btnStopRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(true == bStartRecord){
					boolean bRet = netdevsdk.NETDEV_StopSaveRealData(lpPlayHandle);
					bStartRecord = false;
					if(!bRet){
						JOptionPane.showMessageDialog(null, "StopRecord failed.Please check the path.");
					}
					else{
						btnStopRecord.setEnabled(false);
						btnStartRecord.setEnabled(true);
						JOptionPane.showMessageDialog(null, strRecordPath);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Start StartRecord, please.");
				}
			}
		});
		
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String strPicPath = System.getProperty("user.dir").replaceFirst("/","").replaceAll("%20"," ") + "\\Pic\\" + String.valueOf(Common.timeStamp())+ "_D"+ChannelID;
				if(null != lpPlayHandle){
					boolean bRet = netdevsdk.NETDEV_CapturePicture(lpPlayHandle, strPicPath, 1);
					if(bRet){
						JOptionPane.showMessageDialog(null, "Preview capture OK,Path:" + strPicPath);
						return;
					}
				}
				if(null != lpUserID){
					if(ChannelID == 0){
						JOptionPane.showMessageDialog(null, "Please select the channel first.");
						return;
					}
					boolean bRet = netdevsdk.NETDEV_CaptureNoPreview(lpUserID, ChannelID, 0, strPicPath, 1);
					if(bRet){
						JOptionPane.showMessageDialog(null, "Non-preview capture OK,Path:" + strPicPath);
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "Make sure the device supports non-preview capture.");
			}
		});
		
		btnMakeI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpUserID){
					JOptionPane.showMessageDialog(null, "Please log on to the device first.");
					return;
				}

				if(ChannelID == 0){
					JOptionPane.showMessageDialog(null, "Please select the channel first.");
					return;
				}
				boolean bRet = netdevsdk.NETDEV_MakeKeyFrame(lpUserID, ChannelID, 0);
				if(bRet){
					JOptionPane.showMessageDialog(null, "Make Key Frame OK");
				}
				else{
					JOptionPane.showMessageDialog(null, "Make Key Frame failed,  Please check device status.");
				}
				return;
			}
		});
		
		CloudDevListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = CloudDevListTable.rowAtPoint(arg0.getPoint());
				textFieldCloudDevUserName.setText(CloudDevListTable.getValueAt(row, 0).toString());
				System.out.println(CloudDevListTable.getValueAt(row, 0).toString());
			}
		});
		
		DeviceTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = DeviceTable.rowAtPoint(arg0.getPoint());
				ChannelID = Integer.parseInt(DeviceTable.getValueAt(row, 0).toString());
				System.out.println("ChannelID:" + ChannelID);
			}
		});
		
		btnLoginCloudDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null == lpCloudUserID){
					JOptionPane.showMessageDialog(null, "Please log in to the cloud account first.");
					return;
				}
				String strUserName = textFieldCloudDevUserName.getText();
				if(strUserName.isEmpty()){
					JOptionPane.showMessageDialog(null, "Please select an online cloud device.");
					return;
				}
				NETDEV_CLOUD_DEV_LOGIN_INFO_S pstCloudInfo = new NETDEV_CLOUD_DEV_LOGIN_INFO_S();

				System.arraycopy(strUserName.getBytes(), 0, pstCloudInfo.szDeviceName, 0, strUserName.getBytes().length);
				pstCloudInfo.dwLoginProto = LoginProtoComboBox.getSelectedIndex();
				lpUserID = netdevsdk.NETDEV_LoginCloudDevice_V30(lpCloudUserID, pstCloudInfo);
				if(null != lpUserID){
					new Thread(new Runnable() {
						@Override
						public void run() {
							if(DeviceTypeComboBox.getSelectedIndex() == 0){
								int nMaxChlCount = 256;
								IntByReference dwChlCount = new IntByReference(nMaxChlCount);
								NetDEVSDKLib.NETDEV_VIDEO_CHL_DETAIL_INFO_EX_S[] stVideoChlList = (NetDEVSDKLib.NETDEV_VIDEO_CHL_DETAIL_INFO_EX_S[])new NetDEVSDKLib.NETDEV_VIDEO_CHL_DETAIL_INFO_EX_S().toArray(nMaxChlCount);
								boolean bRet = netdevsdk.NETDEV_QueryVideoChlDetailListEx(lpUserID, dwChlCount, stVideoChlList);
								DeviceTableModel.setRowCount(0);
								if(bRet)
								{
									for(int i=0; i<dwChlCount.getValue(); i++)
									{
										Vector<String> vector = new Vector<String>();
								        vector.add(String.valueOf(stVideoChlList[i].dwChannelID));
								        vector.add(Common.DevideStatusToString(stVideoChlList[i].enStatus));
										
								        DeviceTableModel.insertRow(i,vector);
								        
									}
								}
								else{
									JOptionPane.showMessageDialog(null, "Channel query failed, please check if the device channel information is normal");
								}

							}
							else{
								DeviceTableModel.setRowCount(0);
								Pointer lpFindHandle = netdevsdk.NETDEV_FindDevChnList(lpUserID, 0, 0);
								if(null != lpFindHandle){
									NETDEV_DEV_CHN_ENCODE_INFO_S pstDevChnEncodeInfo = new NETDEV_DEV_CHN_ENCODE_INFO_S();
									pstDevChnEncodeInfo.write();
									//pstDevChnEncodeInfo.stChnBaseInfo = new NETDEV_DEV_CHN_BASE_INFO_S();
									int dwOutBufferSize = pstDevChnEncodeInfo.size();
									IntByReference pdwBytesReturned = new IntByReference(0);
									boolean bRet = netdevsdk.NETDEV_FindNextDevChn(lpFindHandle, pstDevChnEncodeInfo.getPointer(), dwOutBufferSize, pdwBytesReturned);
									int nCount = 0;
									while(bRet){
										pstDevChnEncodeInfo.read();
										Vector<String> vector = new Vector<String>();
								        vector.add(String.valueOf(pstDevChnEncodeInfo.stChnBaseInfo.dwChannelID));
								        vector.add(Common.CHNStatusToString(pstDevChnEncodeInfo.stChnBaseInfo.dwChnStatus));
								        vector.add(Common.utfToString(pstDevChnEncodeInfo.stChnBaseInfo.szChnName));
										
								        DeviceTableModel.insertRow(nCount,vector);
								        nCount++;
										bRet = netdevsdk.NETDEV_FindNextDevChn(lpFindHandle, pstDevChnEncodeInfo.getPointer(), dwOutBufferSize, pdwBytesReturned);
									}
									bRet = netdevsdk.NETDEV_FindCloseDevInfo(lpFindHandle);
								}
							}
						}
						{
							btnLogin.setEnabled(false);
							btnLogout.setEnabled(false);
							btnLoginCloud.setEnabled(false);
							btnLogoutCloud.setEnabled(true);
							btnLoginCloudDevice.setEnabled(false);
							btnLogoutCloudDevice.setEnabled(true);
						}
					}).start();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "login failed,Please check that the device is online or logged in to the device protocol.");
				}
			}
		});
		
		btnLogoutCloudDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpTalkHandle){
					netdevsdk.NETDEV_StopVoiceCom(lpTalkHandle);
					lpTalkHandle = null;
				}
				if(null != lpPlayHandle){
					netdevsdk.NETDEV_StopRealPlay(lpPlayHandle);
					lpPlayHandle = null;
					PlayLabel.repaint();  
				}
				if(null != lpUserID){
					netdevsdk.NETDEV_Logout(lpUserID);
					lpUserID = null;
				}
				DeviceTableModel.setRowCount(0);
				
				{
					btnLogin.setEnabled(false);
					btnLogout.setEnabled(false);
					btnLoginCloud.setEnabled(false);
					btnLogoutCloud.setEnabled(true);
					btnLoginCloudDevice.setEnabled(true);
					btnLogoutCloudDevice.setEnabled(false);
				}
			}
		});
		
		btnSubscibeAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean bRet = netdevsdk.NETDEV_SetAlarmCallBack_V30(lpUserID, cbAlarmMessCallBack, null);
				if(bRet){
					JOptionPane.showMessageDialog(null, "Set Alarm CallBack ok");
				}
				else{
					JOptionPane.showMessageDialog(null, "Set Alarm CallBack failed");
				}
			}
		});
		
		btnUnSubAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				netdevsdk.NETDEV_SetAlarmCallBack_V30(lpUserID, null, null);
			}
		});
		
		btnClearAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AlarmTableModel.setRowCount(0);
			}
		});
		
		btnQueryrecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null == lpUserID || 0 == ChannelID){
					JOptionPane.showMessageDialog(null, "Please select the device channel after successful login.");
					return;
				}
				String BeginTime = textRecordBeginTime.getText();
				String EndTime = textRecordEndTime.getText();
				if(false == Common.isValidDate(BeginTime, DateFormat) ||false == Common.isValidDate(EndTime, DateFormat)){
					JOptionPane.showMessageDialog(null, "Please check that the time format is correct.");
					return;
				}
				
				NETDEV_FILECOND_S pstFindCond = new NETDEV_FILECOND_S();
				pstFindCond.dwChannelID = ChannelID;
				pstFindCond.tBeginTime = Common.date2TimeStamp(BeginTime, DateFormat);
				pstFindCond.tEndTime = Common.date2TimeStamp(EndTime, DateFormat);
				//pstFindCond.dwFileType = 0x00000080;
				
				RecordTableModel.setRowCount(0);
				Pointer lpFindHandle = netdevsdk.NETDEV_FindFile(lpUserID, pstFindCond);
				if(null != lpFindHandle){
					NETDEV_FINDDATA_S pstFindData = new NETDEV_FINDDATA_S();
					boolean bRet = netdevsdk.NETDEV_FindNextFile(lpFindHandle, pstFindData);
					while(bRet){
						Vector<String> vector = new Vector<String>();
				        vector.add(Common.timeStamp2Date(String.valueOf(pstFindData.tBeginTime), DateFormat));
				        vector.add(Common.timeStamp2Date(String.valueOf(pstFindData.tEndTime), DateFormat));
				        vector.add(Common.utfToString(pstFindData.szFileName));
						
				        RecordTableModel.insertRow(0,vector);
						bRet = netdevsdk.NETDEV_FindNextFile(lpFindHandle, pstFindData);
					}
					netdevsdk.NETDEV_FindClose(lpFindHandle);
				}
				else{
					JOptionPane.showMessageDialog(null, "Check if the device software version is supported.");
					return;
				}
			}
		});
		
		btnStartdownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String BeginTime = textRecordBeginTime.getText();
				String EndTime = textRecordEndTime.getText();
				if(false == Common.isValidDate(BeginTime, DateFormat) ||false == Common.isValidDate(EndTime, DateFormat)){
					JOptionPane.showMessageDialog(null, "Please check that the time format is correct.");
					return;
				}
				
				strRecordPath = System.getProperty("user.dir").replaceFirst("/","").replaceAll("%20"," ") + "\\Record\\DownloadFile" + String.valueOf(Common.timeStamp())+ "_D"+ChannelID;
				
				NETDEV_PLAYBACKCOND_S pstPlayBackCond = new NETDEV_PLAYBACKCOND_S();
				pstPlayBackCond.dwChannelID = ChannelID;
				pstPlayBackCond.dwDownloadSpeed = NETDEV_E_DOWNLOAD_SPEED_E.NETDEV_DOWNLOAD_SPEED_EIGHT;
				pstPlayBackCond.tBeginTime = Common.date2TimeStamp(BeginTime, DateFormat);
				pstPlayBackCond.tEndTime = Common.date2TimeStamp(EndTime, DateFormat);
				lpDownloadHandle = netdevsdk.NETDEV_GetFileByTime(lpUserID, pstPlayBackCond, strRecordPath, NETDEV_MEDIA_FILE_FORMAT_E.NETDEV_MEDIA_FILE_MP4);
				if(null != lpDownloadHandle){
					DownloadProgress.setMaximum((int)pstPlayBackCond.tEndTime);
					DownloadProgress.setMinimum((int)pstPlayBackCond.tBeginTime);
					new Thread(new Runnable() {
						@Override
						public void run() {
							int nCount = 0;
							while(true)
							{
								LongByReference PlayTime = new LongByReference();
								boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpDownloadHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_GETPLAYTIME, PlayTime);
								if(bRet){
									long lTime = PlayTime.getValue();
									//System.out.println(lTime+ ":" + (long)PlayBackSlider.getMaximum() + "," + (long)PlayBackSlider.getMinimum()); 
									DownloadProgress.setValue((int)lTime);
								}
								else{
									nCount++;
									System.out.println("Get playback time failed"); 
								}
								if((long)DownloadProgress.getMaximum() == PlayTime.getValue() || PlayTime.getValue() > (long)DownloadProgress.getMaximum()
										|| nCount>3){
									netdevsdk.NETDEV_StopGetFile(lpDownloadHandle);
									DownloadProgress.setValue(DownloadProgress.getMaximum());
									lpDownloadHandle = null;
									break;
								}
								try{
									Thread.sleep(700); 
								}catch(InterruptedException e){
									e.printStackTrace();
								}
							}
							
						}
					}).start();
				}
			}
		});
		
		btnStopdownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpDownloadHandle){
					netdevsdk.NETDEV_StopGetFile(lpDownloadHandle);
					lpDownloadHandle = null;
					DownloadProgress.setValue(DownloadProgress.getMaximum());
				}
			}
		});
		
		btnRecordPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(null != lpPlayHandle){
					JOptionPane.showMessageDialog(null, "Please stop playing first.");
					return;
				}
				
				String BeginTime = textRecordBeginTime.getText();
				String EndTime = textRecordEndTime.getText();
				if(false == Common.isValidDate(BeginTime, DateFormat) ||false == Common.isValidDate(EndTime, DateFormat)){
					JOptionPane.showMessageDialog(null, "Please check that the time format is correct.");
					return;
				}
				
				NETDEV_PLAYBACKCOND_S pstPlayBackInfo = new NETDEV_PLAYBACKCOND_S();
				pstPlayBackInfo.dwChannelID = ChannelID;
				pstPlayBackInfo.hPlayWnd = Native.getComponentPointer(PlayLabel);
				pstPlayBackInfo.tBeginTime = Common.date2TimeStamp(BeginTime, DateFormat);
				pstPlayBackInfo.tEndTime = Common.date2TimeStamp(EndTime, DateFormat);
				lpPlayHandle = netdevsdk.NETDEV_PlayBackByTime(lpUserID, pstPlayBackInfo);
				if(null != lpPlayHandle){
					PlayBackSlider.setMaximum((int)pstPlayBackInfo.tEndTime);
					PlayBackSlider.setMinimum((int)pstPlayBackInfo.tBeginTime);
					PlayBackSlider.setValue(50);
					lblPlaybackSpeed.setText("1X");
					new Thread(new Runnable() {
						@Override
						public void run() {
							int nCount = 0;
							while(true)
							{
								LongByReference PlayTime = new LongByReference();
								boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_GETPLAYTIME, PlayTime);
								if(bRet){
									long lTime = PlayTime.getValue();
									//System.out.println(lTime+ ":" + (long)PlayBackSlider.getMaximum() + "," + (long)PlayBackSlider.getMinimum()); 
									PlayBackSlider.setValue((int)lTime);
									
									long lRemainTime = (long)PlayBackSlider.getMaximum() - lTime;
									
									String strRemainTime = String.valueOf(lRemainTime/60/60) + ":" + String.valueOf((lRemainTime/60)%60) + ":"+ String.valueOf(lRemainTime%60);
									
									String strInfo = Common.timeStamp2Date(String.valueOf(lTime), DateFormat);
									strInfo += " / ";
									strInfo += strRemainTime;
									lblProgressLabel.setText(strInfo);
									nCount = 0;
								}
								else{
									nCount++;
									System.out.println("Get playback time failed"); 
								}
								if((long)PlayBackSlider.getMaximum() == PlayTime.getValue() || PlayTime.getValue() > (long)PlayBackSlider.getMaximum()
										|| nCount>3){
									netdevsdk.NETDEV_StopPlayBack(lpPlayHandle);
									lpPlayHandle = null;
									break;
								}
								try{
									Thread.sleep(700); 
								}catch(InterruptedException e){
									e.printStackTrace();
								}
							}
							
						}
					}).start();
				}
			}
		});
		
		btnRecordPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_PAUSE == PlayBackControlCmd){
					boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_RESUME, null);
					if(bRet){
						PlayBackControlCmd = NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_RESUME;
						btnRecordPause.setText("Pause");
					}
				}
				else{
					boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_PAUSE, null);
					if(bRet){
						PlayBackControlCmd = NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_PAUSE;
						btnRecordPause.setText("Resume");						
					}
				}
			}
		});
		
		btnRecordStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null != lpPlayHandle){
					netdevsdk.NETDEV_StopPlayBack(lpPlayHandle);
					lpPlayHandle = null;
					PlayLabel.repaint();
				}
			}
		});
		
		btnRecordSlow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LongByReference PlaySpeed = new LongByReference();
				boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_GETPLAYSPEED, PlaySpeed);
				if(!bRet){
					System.out.println("Get playback speed failed");
					return;
				}
				long value = PlaySpeed.getValue();
				if(value <= NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_16_BACKWARD){
					JOptionPane.showMessageDialog(null, "It's already minimal playback speed.");
					return;
				}
				value -= 1;
				PlaySpeed.setValue(value);;
				 bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_SETPLAYSPEED, PlaySpeed);
				if(!bRet){
					System.out.println("Set playback speed failed:" + value); 
				}
				else{
					lblPlaybackSpeed.setText(Common.PlaybackSpeedToLong((int)value));
				}
			}
		});
		
		btnRecordFast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LongByReference PlaySpeed = new LongByReference();
				boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_GETPLAYSPEED, PlaySpeed);
				if(!bRet){
					System.out.println("Get playback speed failed");
					return;
				}
				long value = PlaySpeed.getValue();
				if(value >= NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_16_FORWARD){
					JOptionPane.showMessageDialog(null, "It's already maximum playback speed.");
					return;
				}
				value += 1;
				PlaySpeed.setValue(value);;
				 bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_SETPLAYSPEED, PlaySpeed);
				if(!bRet){
					System.out.println("Set playback speed failed:" + value); 
				}
				else{
					lblPlaybackSpeed.setText(Common.PlaybackSpeedToLong((int)value));
				}
			}
		});
		
		PlayBackSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				LongByReference PlayTime = new LongByReference();
				PlayTime.setValue((long)PlayBackSlider.getValue());
				boolean bRet = netdevsdk.NETDEV_PlayBackControl(lpPlayHandle, NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_SETPLAYTIME, PlayTime);
				if(!bRet){
					System.out.println("Set playback time failed:" + Common.timeStamp2Date(Long.toString(PlayTime.getValue()), DateFormat)); 
				}
			}
		});
	}
	
	private void findACSPersonInfo(int dwOrgID, int offset)
	{
		NETDEV_PERSON_QUERY_INFO_S pstQueryCond = new NETDEV_PERSON_QUERY_INFO_S();
		pstQueryCond.udwNum = 1;
		pstQueryCond.udwOffset =  offset;
		pstQueryCond.udwLimit = NETDEMO_FIND_ACS_PERSON_COUNT;
		
		NETDEV_QUERY_INFO_S stQueryInfos = new NETDEV_QUERY_INFO_S();
        stQueryInfos.dwQueryType = NETDEV_QUERYCOND_TYPE_E.NETDEV_QUERYCOND_ORGID;
        stQueryInfos.dwLogicFlag = NETDEV_QUERYCOND_LOGICTYPE_E.NETDEV_QUERYCOND_LOGIC_EQUAL;
        Common.stringToByteArray(Integer.toString(dwOrgID), stQueryInfos.szConditionData);
        stQueryInfos.write();
        pstQueryCond.pstQueryInfos = stQueryInfos.getPointer();
        
		NETDEV_BATCH_OPERATE_BASIC_S pstResultInfo = new NETDEV_BATCH_OPERATE_BASIC_S();
		
		Pointer lpFindHandle = netdevsdk.NETDEV_FindACSPersonList(lpUserID, pstQueryCond, pstResultInfo);
		if(null == lpFindHandle)
		{
			System.out.println("NETDEV_FindACSPersonList fail, erron:" + netdevsdk.NETDEV_GetLastError());
			return;
		}
		
		System.out.printf("NETDEV_FindACSPersonList,Total:%d,Offset:%d,Num:%d\n",pstResultInfo.udwTotal,pstResultInfo.udwOffset,pstResultInfo.udwNum);
		AccessControlPersonTableModel.setRowCount(0);
		mapACPersonInfo.clear();
		
		boolean bGetNextIsSuccess = true;
		while(bGetNextIsSuccess)
		{
			NETDEV_ACS_PERSON_BASE_INFO_S pstACSPersonInfo = new NETDEV_ACS_PERSON_BASE_INFO_S();
			pstACSPersonInfo.stMemberIDInfo = new NETDEV_FACE_MEMBER_ID_INFO_S();
			pstACSPersonInfo.stStaffInfo = new NETDEV_ACS_STAFF_INFO_S();
			pstACSPersonInfo.stVisitor = new NETDEV_ACS_VISITOR_INFO_S();
			pstACSPersonInfo.stVisitor.tScheduleTime = new NETDEV_ACS_TIME_SECTION_S();
			pstACSPersonInfo.stVisitor.tRealTime = new NETDEV_ACS_TIME_SECTION_S();
			bGetNextIsSuccess = netdevsdk.NETDEV_FindNextACSPersonInfo(lpFindHandle,pstACSPersonInfo);
			if(false == bGetNextIsSuccess)
			{
				return;
			}
			
			mapACPersonInfo.put(new Integer(pstACSPersonInfo.udwPersonID), pstACSPersonInfo);
			
			Vector<String> PersonVector = new Vector<String>();
			PersonVector.add(String.valueOf(pstACSPersonInfo.udwPersonID));
			PersonVector.add(Common.byteArrayToString(pstACSPersonInfo.stStaffInfo.szNumber));
			PersonVector.add(Common.byteArrayToString(pstACSPersonInfo.szName));
			
			if(pstACSPersonInfo.udwGender == NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_MAN)
			{
				PersonVector.add("man");
			}
			else if(pstACSPersonInfo.udwGender == NETDEV_GENDER_TYPE_E.NETDEV_GENDER_TYPE_WOMAN)
			{
				PersonVector.add("woman");
			}
			else
			{
				PersonVector.add("unknown");
			}
			
			PersonVector.add(Common.byteArrayToString(pstACSPersonInfo.stStaffInfo.szDeptName));
			
			
			if(pstACSPersonInfo.stMemberIDInfo.udwType == NETDEV_FACE_MEMBER_ID_TYPE_E.NETDEV_FACE_MEMBER_ID_TYPE_ID_CARD)
			{
				PersonVector.add("ID");
			}
			else if(pstACSPersonInfo.stMemberIDInfo.udwType == NETDEV_FACE_MEMBER_ID_TYPE_E.NETDEV_FACE_MEMBER_ID_TYPE_PASSPORT)
			{
				PersonVector.add("Passport");
			}
			else if(pstACSPersonInfo.stMemberIDInfo.udwType == NETDEV_FACE_MEMBER_ID_TYPE_E.NETDEV_FACE_MEMBER_ID_TYPE_DRIVING)
			{
				PersonVector.add("Driving license");
			}
			else
			{
				PersonVector.add("other");	
			}
			PersonVector.add(Common.byteArrayToString(pstACSPersonInfo.stMemberIDInfo.szNumber));
			
			PersonVector.add(Common.byteArrayToString(pstACSPersonInfo.szTelephone));
			

			AccessControlPersonTableModel.addRow(PersonVector);
		}
		netdevsdk.NETDEV_FindCloseACSPersonInfo(lpFindHandle);
	}
}
