package main.java.com.netdevsdk.demo;

import main.java.com.netdevsdk.lib.NetDEVSDKLib;
import main.java.com.netdevsdk.lib.NetDEVSDKLib.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class Common {
	
    public static final int NETDEMO_PICTURE_SIZE            = 1048576;            /* 图片内存建议分配大小 */
	
	public static boolean isIP(String addr)  
    {  
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))  
        {  
            return false;  
        }  
        /** 
         * 判断IP格式和范围 
         */  
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";  
          
        Pattern pat = Pattern.compile(rexp);    
          
        Matcher mat = pat.matcher(addr);    
          
        boolean ipAddress = mat.find();  

        return ipAddress;  
    }
	
	public static String utfToString(byte[] data) {
	    try {
	           int length = 0;
				for (int i = 0; i < data.length; ++i) {
					if (data[i] == 0) {
						length = i;
						break;
					}
				}
				return new String(data, 0, length, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	    	return "";
	    }   
	 }
	
	public static String DevideTypeToString(int nDevideType) {
		String strDevideType = "";
		switch(nDevideType){
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_UNKNOWN:
			strDevideType = "UNKNOWN";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_IPC:
			strDevideType = "IPC";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_IPC_FISHEYE:
			strDevideType = "IPC_FISHEYE";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_IPC_ECONOMIC_FISHEYE:
			strDevideType = "IPC_ECONOMIC_FISHEYE";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_NVR:
			strDevideType = "NVR";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_NVR_BACKUP:
			strDevideType = "NVR_BACKUP";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_HNVR:
			strDevideType = "HNVR";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_DC:
			strDevideType = "DC";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_DC_ADU:
			strDevideType = "ADU";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_EC:
			strDevideType = "EC";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_VMS:
			strDevideType = "VMS";
			break;
		case NetDEVSDKLib.NETDEV_DEVICE_TYPE_E.NETDEV_DTYPE_FG:
			strDevideType = "FG";
			break;
		default:
			strDevideType = "INVALID";
			break;
		}
	    return strDevideType;
	 }
	
	public static String DevideStatusToString(int nStatus) {
		String strStatus = "";
		switch(nStatus){
		case NetDEVSDKLib.NETDEV_CHANNEL_STATUS_E.NETDEV_CHL_STATUS_OFFLINE:
			strStatus = "offline";
			break;
		case NetDEVSDKLib.NETDEV_CHANNEL_STATUS_E.NETDEV_CHL_STATUS_ONLINE:
			strStatus = "online";
			break;
		case NetDEVSDKLib.NETDEV_CHANNEL_STATUS_E.NETDEV_CHL_STATUS_UNBIND:
			strStatus = "unbind";
			break;
		default:
			strStatus = "INVALID";
			break;
		}
	    return strStatus;
	 }
	
	public static String CHNStatusToString(int nStatus) {
		String strStatus = "";
		switch(nStatus){
		case NetDEVSDKLib.NETDEV_CHN_STATUS_E.NETDEV_CHN_STATUS_OFFLINE:
			strStatus = "offline";
			break;
		case NetDEVSDKLib.NETDEV_CHN_STATUS_E.NETDEV_CHN_STATUS_ONLINE:
			strStatus = "online";
			break;
		case NetDEVSDKLib.NETDEV_CHN_STATUS_E.NETDEV_CHN_STATUS_VIDEO_LOSE:
			strStatus = "video lose";
			break;
		default:
			strStatus = "INVALID";
			break;
		}
	    return strStatus;
	 }
	
	public static boolean isValidDate(String seconds,String format)
    {
		SimpleDateFormat dateFormat = null;
		dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
        try
        {
             dateFormat.parse(seconds);
             return true;
         }
        catch (Exception e)
        {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
	
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static long date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return sdf.parse(date_str).getTime()/1000;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return 0;  
    }  
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static long timeStamp(){  
        long time = System.currentTimeMillis();  
        return time/1000;  
    }
    
	public static String AlarmTypeToString(int nAlarmType) {
		String strAlarmInfo = "";
		switch (nAlarmType) {
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_MOVE_DETECT:
			strAlarmInfo = "move_detect";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_MOVE_DETECT_RECOVER:
			strAlarmInfo = "move_detect_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_VIDEO_LOST:
			strAlarmInfo = "video_lost";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_VIDEO_LOST_RECOVER:
			strAlarmInfo = "video_lost_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_VIDEO_TAMPER_DETECT:
			strAlarmInfo = "video_tamper_detect";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_VIDEO_TAMPER_RECOVER:
			strAlarmInfo = "video_tamper_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_INPUT_SWITCH:
			strAlarmInfo = "input_switch";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_INPUT_SWITCH_RECOVER:
			strAlarmInfo = "input_switch_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_TEMPERATURE_HIGH:
			strAlarmInfo = "temperature_high";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_TEMPERATURE_LOW:
			strAlarmInfo = "temperature_low";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_TEMPERATURE_RECOVER:
			strAlarmInfo = "temperature_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_AUDIO_DETECT:
			strAlarmInfo = "audio_detect";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_AUDIO_DETECT_RECOVER:
			strAlarmInfo = "audio_detect_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SERVER_FAULT:
			strAlarmInfo = "server_fault";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SERVER_NORMAL:
			strAlarmInfo = "server_normal";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_ONLINE:
			strAlarmInfo = "report_dev_online";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_OFFLINE:
			strAlarmInfo = "report_dev_offline";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_REBOOT:
			strAlarmInfo = "report_dev_reboot";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_SERVICE_REBOOT:
			strAlarmInfo = "report_dev_service_reboot";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_CHL_ONLINE:
			strAlarmInfo = "report_dev_chl_online";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_CHL_OFFLINE:
			strAlarmInfo = "report_dev_chl_offline";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REPORT_DEV_DELETE_CHL:
			strAlarmInfo = "report_dev_delete_chl";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_NET_FAILED:
			strAlarmInfo = "net_failed";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_NET_TIMEOUT:
			strAlarmInfo = "net_timeout";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SHAKE_FAILED:
			strAlarmInfo = "shake_failed";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STREAMNUM_FULL:
			strAlarmInfo = "streamnum_full";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STREAM_THIRDSTOP:
			strAlarmInfo = "stream_thirdstop";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_FILE_END:
			strAlarmInfo = "file_end";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_RTMP_CONNECT_FAIL:
			strAlarmInfo = "rtmp_connect_fail";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_RTMP_INIT_FAIL:
			strAlarmInfo = "rtmp_init_fail";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STREAM_DOWNLOAD_OVER:
			strAlarmInfo = "stream_download_over";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_ERROR:
			strAlarmInfo = "disk_error";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_DISK_ERROR:
			strAlarmInfo = "sys_disk_error";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_ONLINE:
			strAlarmInfo = "disk_online";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_DISK_ONLINE:
			strAlarmInfo = "sys_disk_online";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_OFFLINE:
			strAlarmInfo = "disk_offline";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_DISK_OFFLINE:
			strAlarmInfo = "sys_disk_offline";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_ABNORMAL:
			strAlarmInfo = "disk_abnormal";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_ABNORMAL_RECOVER:
			strAlarmInfo = "disk_abnormal_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_STORAGE_WILL_FULL:
			strAlarmInfo = "disk_storage_will_full";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_STORAGE_WILL_FULL_RECOVER:
			strAlarmInfo = "disk_storage_will_full_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_STORAGE_IS_FULL:
			strAlarmInfo = "disk_storage_is_full";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_DISK_STORAGE_IS_FULL:
			strAlarmInfo = "sys_disk_storage_is_full";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_STORAGE_IS_FULL_RECOVER:
			strAlarmInfo = "disk_storage_is_full_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_RAID_DISABLED_RECOVER:
			strAlarmInfo = "disk_raid_disabled_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_RAID_DEGRADED:
			strAlarmInfo = "disk_raid_degraded";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_DISK_RAID_DEGRADED:
			strAlarmInfo = "sys_disk_raid_degraded";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_RAID_DISABLED:
			strAlarmInfo = "disk_raid_disabled";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_DISK_RAID_DISABLED:
			strAlarmInfo = "sys_disk_raid_disabled";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_RAID_DEGRADED_RECOVER:
			strAlarmInfo = "disk_raid_degraded_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STOR_GO_FULL:
			strAlarmInfo = "stor_go_full";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_STOR_GO_FULL:
			strAlarmInfo = "sys_stor_go_full";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_ARRAY_NORMAL:
			strAlarmInfo = "array_normal";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_ARRAY_NORMAL:
			strAlarmInfo = "sys_array_normal";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DISK_RAID_RECOVERED:
			strAlarmInfo = "disk_raid_recovered";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STOR_ERR:
			strAlarmInfo = "stor_err";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_STOR_ERR:
			strAlarmInfo = "sys_stor_err";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STOR_ERR_RECOVER:
			strAlarmInfo = "stor_err_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STOR_DISOBEY_PLAN:
			strAlarmInfo = "stor_disobey_plan";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_STOR_DISOBEY_PLAN_RECOVER:
			strAlarmInfo = "stor_disobey_plan_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_BANDWITH_CHANGE:
			strAlarmInfo = "bandwith_change";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_VIDEOENCODER_CHANGE:
			strAlarmInfo = "videoencoder_change";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_IP_CONFLICT:
			strAlarmInfo = "ip_conflict";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_IP_CONFLICT_CLEARED:
			strAlarmInfo = "ip_conflict_cleared";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_NET_OFF:
			strAlarmInfo = "net_off";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_NET_RESUME_ON:
			strAlarmInfo = "net_resume_on";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_ILLEGAL_ACCESS:
			strAlarmInfo = "illegal_access";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SYS_ILLEGAL_ACCESS:
			strAlarmInfo = "sys_illegal_access";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_LINE_CROSS:
			strAlarmInfo = "line_cross";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_OBJECTS_INSIDE:
			strAlarmInfo = "objects_inside";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_FACE_RECOGNIZE:
			strAlarmInfo = "face_recognize";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_IMAGE_BLURRY:
			strAlarmInfo = "image_blurry";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SCENE_CHANGE:
			strAlarmInfo = "scene_change";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_TRACK:
			strAlarmInfo = "smart_track";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_LOITERING_DETECTOR:
			strAlarmInfo = "loitering_detector";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_BANDWIDTH_CHANGE:
			strAlarmInfo = "bandwidth_change";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_ALLTIME_FLAG_END:
			strAlarmInfo = "alltime_flag_end";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_MEDIA_CONFIG_CHANGE:
			strAlarmInfo = "media_config_change";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_REMAIN_ARTICLE:
			strAlarmInfo = "remain_article";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_PEOPLE_GATHER:
			strAlarmInfo = "people_gather";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_ENTER_AREA:
			strAlarmInfo = "enter_area";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_LEAVE_AREA:
			strAlarmInfo = "leave_area";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_ARTICLE_MOVE:
			strAlarmInfo = "article_move";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_FACE_MATCH_LIST:
			strAlarmInfo = "smart_face_match_list";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_FACE_MATCH_LIST_RECOVER:
			strAlarmInfo = "smart_face_match_list_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_FACE_MISMATCH_LIST:
			strAlarmInfo = "smart_face_mismatch_list";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_FACE_MISMATCH_LIST_RECOVER:
			strAlarmInfo = "smart_face_mismatch_list_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_VEHICLE_MATCH_LIST:
			strAlarmInfo = "smart_vehicle_match_list";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_VEHICLE_MATCH_LIST_RECOVER:
			strAlarmInfo = "smart_vehicle_match_list_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_VEHICLE_MISMATCH_LIST:
			strAlarmInfo = "smart_vehicle_mismatch_list";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_VEHICLE_MISMATCH_LIST_RECOVER:
			strAlarmInfo = "smart_vehicle_mismatch_list_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_IMAGE_BLURRY_RECOVER:
			strAlarmInfo = "image_blurry_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_TRACK_RECOVER:
			strAlarmInfo = "smart_track_recover";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_READ_ERROR_RATE:
			strAlarmInfo = "smart_read_error_rate";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_SPIN_UP_TIME:
			strAlarmInfo = "smart_spin_up_time";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_START_STOP_COUNT:
			strAlarmInfo = "smart_start_stop_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_REALLOCATED_SECTOR_COUNT:
			strAlarmInfo = "smart_reallocated_sector_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_SEEK_ERROR_RATE:
			strAlarmInfo = "smart_seek_error_rate";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_POWER_ON_HOURS:
			strAlarmInfo = "smart_power_on_hours";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_SPIN_RETRY_COUNT:
			strAlarmInfo = "smart_spin_retry_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_CALIBRATION_RETRY_COUNT:
			strAlarmInfo = "smart_calibration_retry_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_POWER_CYCLE_COUNT:
			strAlarmInfo = "smart_power_cycle_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_POWEROFF_RETRACT_COUNT:
			strAlarmInfo = "smart_poweroff_retract_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_LOAD_CYCLE_COUNT:
			strAlarmInfo = "smart_load_cycle_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_TEMPERATURE_CELSIUS:
			strAlarmInfo = "smart_temperature_celsius";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_REALLOCATED_EVENT_COUNT:
			strAlarmInfo = "smart_reallocated_event_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_CURRENT_PENDING_SECTOR:
			strAlarmInfo = "smart_current_pending_sector";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_OFFLINE_UNCORRECTABLE:
			strAlarmInfo = "smart_offline_uncorrectable";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_UDMA_CRC_ERROR_COUNT:
			strAlarmInfo = "smart_udma_crc_error_count";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_SMART_MULTI_ZONE_ERROR_RATE:
			strAlarmInfo = "smart_multi_zone_error_rate";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_RESOLUTION_CHANGE:
			strAlarmInfo = "resolution_change";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_MANUAL:
			strAlarmInfo = "manual";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_ALARMHOST_COMMON:
			strAlarmInfo = "alarmhost_common";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_DOORHOST_COMMON:
			strAlarmInfo = "doorhost_common";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_FACE_NOT_MATCH:
			strAlarmInfo = "face_not_match";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_FACE_MATCH_SUCCEED:
			strAlarmInfo = "face_match_succeed";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_VEHICLE_BLACK_LIST:
			strAlarmInfo = "vehicle_black_list";
			break;
		case NetDEVSDKLib.NETDEV_ALARM_TYPE_E.NETDEV_ALARM_HUMAN_SHAPE_DETECTION:
			strAlarmInfo = "human_shape_detection";
			break;
		default:
			strAlarmInfo = "unknown";
			break;
		}
		return strAlarmInfo;
	}
	
	public static String PlaybackSpeedToLong(int nSpeedValue) {
		String strInfo = "";
		switch (nSpeedValue) {
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_16_BACKWARD:
			strInfo = "-16X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_8_BACKWARD:
			strInfo = "-8X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_4_BACKWARD:
			strInfo = "-4X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_2_BACKWARD:
			strInfo = "-2X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_1_BACKWARD:
			strInfo = "-1X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_HALF_BACKWARD:
			strInfo = "-1/2X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_QUARTER_BACKWARD:
			strInfo = "-1/4X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_QUARTER_FORWARD:
			strInfo = "1/4X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_HALF_FORWARD:
			strInfo = "1/2X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_1_FORWARD:
			strInfo = "1X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_2_FORWARD:
			strInfo = "2X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_4_FORWARD:
			strInfo = "4X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_8_FORWARD:
			strInfo = "8X";
			break;
		case NetDEVSDKLib.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_16_FORWARD:
			strInfo = "16X";
			break;
		}
		return strInfo;
	}
	
	static public class NETDEMO_DEVICE_INFO_S extends Structure {
	    public int dwChnNum;                                                                                                 /* 通道数量 Number of channels */                                       
	    public int dwDevIndex;                                
	    public NETDEV_DEV_BASIC_INFO_S stDevBasicInfo = new NETDEV_DEV_BASIC_INFO_S();                                       /* 设备信息 Device information */          
	    public Vector<NETDEV_DEV_CHN_ENCODE_INFO_S> vecChanInfo = new Vector<NetDEVSDKLib.NETDEV_DEV_CHN_ENCODE_INFO_S>();   /* 通道信息 Channel information */
	    
		public NETDEMO_DEVICE_INFO_S() {
			dwChnNum = 0;
		}   
	}
	
	static public class NETDEMO_DEV_LOGININFO_S extends Structure {
		public Pointer    pHandle;    /* 设备登录句柄 Device login handle */
		public Pointer    pCloudHandle;
		public int        dwDevNum;                                        
		public Map<Integer, NETDEMO_DEVICE_INFO_S> stDevLoginInfo = new HashMap<Integer, Common.NETDEMO_DEVICE_INFO_S>();  /* 一体机下的设备信息 Device information under VMS */
	}
	
	public static void GetPointerDataToStruct(Pointer pNativeData, long OffsetOfpNativeData, Structure pJavaStu) {
		pJavaStu.write();
		Pointer pJavaMem = pJavaStu.getPointer();
		pJavaMem.write(0, pNativeData.getByteArray(OffsetOfpNativeData, pJavaStu.size()), 0, pJavaStu.size());
		pJavaStu.read();
	}
	
	public static void GetPointerData(Pointer pNativeData, Structure pJavaStu)
	{
		GetPointerDataToStruct(pNativeData, 0, pJavaStu);
	}
	
	public static void GetPointerDataToStructArr(Pointer pNativeData, Structure []pJavaStuArr) {
		long offset = 0;
		for (int i=0; i<pJavaStuArr.length; ++i)
		{
			GetPointerDataToStruct(pNativeData, offset, pJavaStuArr[i]);
			offset += pJavaStuArr[i].size();
		}
	}
	
	public static void SetStructDataToPointer(Structure pJavaStu, Pointer pNativeData, long OffsetOfpNativeData){
		pJavaStu.write();
		Pointer pJavaMem = pJavaStu.getPointer();
		pNativeData.write(OffsetOfpNativeData, pJavaMem.getByteArray(0, pJavaStu.size()), 0, pJavaStu.size());
	}
	
	public static void SetStructArrToPointerData(Structure []pJavaStuArr, Pointer pNativeData) {
		long offset = 0;
		for (int i = 0; i < pJavaStuArr.length; ++i) {
			SetStructDataToPointer(pJavaStuArr[i], pNativeData, offset);
			offset += pJavaStuArr[i].size();
		}
	}
	
	
	// 获取当前时间
	public static String getDate() {
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	    String date = simpleDate.format(new java.util.Date()).replace(" ", "_").replace(":", "-");
	    
	    return date;
	}
	
	//Point类型转byte数组写文件
	public static void savePicture(Pointer pBuf, int dwBufSize, String strFileName)
	{
        try
        {
          	FileOutputStream fos = new FileOutputStream(strFileName);
        	fos.write(pBuf.getByteArray(0, dwBufSize), 0, dwBufSize);
        	fos.close();
        } catch (Exception e){
        	e.printStackTrace();
        }
	}
	
	//byte数组写文件
	public static void savePicture(byte[] pBuf, int dwBufOffset, int dwBufSize, String sDstFile)
	{
        try
        {       	
        	FileOutputStream fos = new FileOutputStream(sDstFile);
        	fos.write(pBuf, dwBufOffset, dwBufSize);
        	fos.close();
        } catch (Exception e){
        	e.printStackTrace();
        }
	}

	public static Method updateStructureByReferenceMethod;
	
    @SuppressWarnings("unchecked")
	public  static <T extends Structure>  T invokeUpdateStructureByReferenceMethod(Class<T> type, T s, Pointer address) {

        try {
            return (T)updateStructureByReferenceMethod.invoke(null, type, s, address);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
    public  static <T extends Structure> T[] pointerToStructureArray(int size, Pointer pointer, Class<T> clazz) {
        if (size == 0) {
        	System.out.println("pointerToStructureArray size == 0 ");
        	return null;
        }

        try {
            @SuppressWarnings("unchecked")
			T[] array = (T[]) Array.newInstance(clazz, size);
            for (int i = 0; i < size; i++) {
                T t = clazz.newInstance();
                Pointer offsetPointer = pointer.share(t.size() * i);
                array[i] = invokeUpdateStructureByReferenceMethod(clazz, t, offsetPointer);
            }
            return array;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
	public static void stringToByteArray(String src, byte[] dst) {
		
		if (src == null || src.isEmpty()) {
			return;
		}
		
		for(int i = 0; i < dst.length; i++) {
			dst[i] = 0;
		}
		
		byte []szSrc;
		
		try {
			szSrc = src.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			szSrc = src.getBytes();
		} 
		
		if (szSrc != null) {
			int len = szSrc.length >= dst.length ? dst.length-1:szSrc.length;
			System.arraycopy(szSrc, 0, dst, 0, len);
		}
	}
	
	public static String byteArrayToString(byte[] buffer) {
		try {
			int length = 0;
			for (int i = 0; i < buffer.length; ++i) {
				if (buffer[i] == 0) {
					length = i;
					break;
				}
			}
			return new String(buffer, 0, length, "UTF-8");
		} catch (Exception e) {
			return "";
		}
	}
	
	
	/**
	 * 读取图片数据
	 * @param file 图片路径
	 * @param memory 图片数据缓存  
	 * @return
	 */
	public static boolean ReadAllFileToMemory(String file, Memory memory) {
		if (memory != Memory.NULL)
		{
			long fileLen = GetFileSize(file);
			if (fileLen <= 0)
			{
				return false;
			}
			
			try {
				File infile = new File(file);
				if (infile.canRead())
				{
					FileInputStream in = new FileInputStream(infile);
					int buffLen = 1024; 
					byte[] buffer = new byte[buffLen];
					long currFileLen = 0;
					int readLen = 0;
					while (currFileLen < fileLen)
					{
						readLen = in.read(buffer);
						memory.write(currFileLen, buffer, 0, readLen);
						currFileLen += readLen;
					}
					
					in.close();
					return true;
				}
		        else
		        {
		        	System.err.println("Failed to open file %s for read!!!\n");
		            return false;
		        }
			}catch (Exception e)
			{
				System.err.println("Failed to open file %s for read!!!\n");
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static long GetFileSize(String filePath) {
		File f = new File(filePath);
		if (f.exists() && f.isFile()) {
			return f.length();
		}
		else
		{
			return 0;
		}
	}
	
	public static Memory readPictureFile(String picPath) {
		long nPicBufLen = 0;
		Memory memory = null;
			
	    /*
	     * 读取本地图片大小
	     */
		nPicBufLen = (long)GetFileSize(picPath);   
		
		// 读取文件大小失败
		if (nPicBufLen <= 0) {
			System.err.println("读取图片大小失败，请重新选择！");
            return null;
		}

		/*
		 * 读取图片缓存
		 */
		memory = new Memory(nPicBufLen);   // 申请缓存
		memory.clear();
		
		if (!ReadAllFileToMemory(picPath,  memory)) {
			System.err.println("读取图片数据，请重新选择！");
            return null;
		}
	
        return memory;
	}

	public static String EnumNETDEV_PLATE_COLOR_EConventToString(int dwEnum)
	{
		String strTempString = "";
		switch (dwEnum) {
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_BLACK_E:
			strTempString = "BLACK";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_WHITE_E:
			strTempString = "WHITE";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_GRAY_E:
			strTempString = "GRAY";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_RED_E:
			strTempString = "RED";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_BLUE_E:
			strTempString = "BLUE";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_YELLOW_E:
			strTempString = "YELLOW";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_ORANGE_E:
			strTempString = "ORANGE";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_BROWN_E:
			strTempString = "BROWN";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_GREEN_E:
			strTempString = "GREEN";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_PURPLE_E:
			strTempString = "PURPLE";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_CYAN_E:
			strTempString = "CYAN";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_PINK_E:
			strTempString = "PINK";
			break;
		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_TRANSPARENT_E:
			strTempString = "TRANSPARENT";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_SILVERYWHITE_E:
			strTempString = "SILVERYWHITE";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_DARK_E:
			strTempString = "DARK";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_LIGHT_E:
			strTempString = "LIGHT";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_COLOURLESS:
			strTempString = "COLOURLESS";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_YELLOWGREEN:
			strTempString = "YELLOWGREEN";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_GRADUALGREEN:
			strTempString = "GRADUALGREEN";
			break;

		case NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_OTHER_E:
			strTempString = "OTHER";
			break;

		default:
			strTempString = "OTHER";
			break;
		}

	return strTempString;
	}
	
	public static int StringConventToEnumNETDEV_PLATE_COLOR_E(String strColor)
	{
		int dwTemp = 0;
		switch (strColor) {
		case "BLACK":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_BLACK_E;
			break;
		case "WHITE":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_WHITE_E;
			break;
		case "GRAY":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_GRAY_E;
			break;
		case "RED":		
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_RED_E;
			break;
		case "BLUE":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_BLUE_E;
			break;
		case "YELLOW":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_YELLOW_E;
			break;
		case "ORANGE":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_ORANGE_E;
			break;
		case "BROWN":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_BROWN_E;
			break;
		case "GREEN":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_GREEN_E;
			break;
		case "PURPLE":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_PURPLE_E;
			break;
		case "CYAN":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_CYAN_E;
			break;
		case "PINK":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_PINK_E;
			break;
		case "TRANSPARENT":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_TRANSPARENT_E;
			break;

		case "SILVERYWHITE":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_SILVERYWHITE_E;
			break;

		case "DARK":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_DARK_E;
			break;

		case "LIGHT":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_LIGHT_E;
			break;

		case "COLOURLESS":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_COLOURLESS;
			break;

		case "YELLOWGREEN":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_YELLOWGREEN;
			break;

		case "GRADUALGREEN":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_GRADUALGREEN;
			break;

		case "OTHER":
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_OTHER_E;
			break;

		default:
			dwTemp = NETDEV_PLATE_COLOR_E.NETDEV_PLATE_COLOR_OTHER_E;
			break;
		}

	return dwTemp;
	}
	
	public static String EnumNETDEV_PLATE_TYPE_EConventToString(int dwEnum)
	{
		String strTempString = "";
		switch (dwEnum) {
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_BIG_CAR_E:
			strTempString = "BIG_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_MINI_CAR_E:
			strTempString = "MINI_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_EMBASSY_CAR_E:
			strTempString = "EMBASSY_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_CONSULATE_CAR_E:
			strTempString = "CONSULATE_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_OVERSEAS_CAR_E:
			strTempString = "OVERSEAS_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_FOREIGN_CAR_E:
			strTempString = "FOREIGN_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_COMMON_MOTORBIKE_E:
			strTempString = "COMMON_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_HANDINESS_MOTORBIKE_E:
			strTempString = "HANDINESS_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_EMBASSY_MOTORBIKE_E:
			strTempString = "EMBASSY_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_CONSULATE_MOTORBIKE_E:
			strTempString = "CONSULATE_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_OVERSEAS_MOTORBIKE_E:
			strTempString = "OVERSEAS_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_FOREIGN_MOTORBIKE_E:
			strTempString = "FOREIGN_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_LOW_SPEED_CAR_E:
			strTempString = "LOW_SPEED_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TRACTOR_E:
			strTempString = "TYPE_TRACTOR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TRAILER_E:
			strTempString = "TRAILER";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_COACH_CAR_E:
			strTempString = "COACH_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_COACH_MOTORBIKE_E:
			strTempString = "COACH_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TEMPORARY_ENTRY_CAR_E:
			strTempString = "TEMPORARY_ENTRY_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TEMPORARY_ENTRY_MOTORBIKE_E:
			strTempString = "TEMPORARY_ENTRY_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TEMPORARY_DRIVING_E:
			strTempString = "TEMPORARY_DRIVING";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_POLICE_CAR_E:
			strTempString = "POLICE_CAR";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_POLICE_MOTORBIKE_E:
			strTempString = "POLICE_MOTORBIKE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_AGRICULTURAL_E:
			strTempString = "AGRICULTURAL";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_HONGKONG_ENTRY_EXIT_E:
			strTempString = "HONGKONG_ENTRY_EXIT";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_MACAO_ENTRY_EXIT_E:
			strTempString = "MACAO_ENTRY_EXIT";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_ARMED_POLICE_E:
			strTempString = "ARMED_POLICE";
			break;
		case NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_ARMY_E:
			strTempString = "ARMY";
			break;

		default:
			strTempString = "OTHER";
			break;
		}

	return strTempString;
	}
	
	public static int StringConventToEnumNETDEV_PLATE_TYPE_E(String strEnum)
	{
		int dwRet = 0;
		switch (strEnum) {
		case "BIG_CAR":
		    dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_BIG_CAR_E;
			break;
		case "MINI_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_MINI_CAR_E;
			break;
		case "EMBASSY_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_EMBASSY_CAR_E;
			break;
		case "CONSULATE_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_CONSULATE_CAR_E;
			break;
		case "OVERSEAS_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_OVERSEAS_CAR_E;
			break;
		case "FOREIGN_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_FOREIGN_CAR_E;
			break;
		case "COMMON_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_COMMON_MOTORBIKE_E;
			break;
		case "HANDINESS_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_HANDINESS_MOTORBIKE_E;
			break;
		case "EMBASSY_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_EMBASSY_MOTORBIKE_E;
			break;
		case "CONSULATE_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_CONSULATE_MOTORBIKE_E;
			break;
		case "OVERSEAS_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_OVERSEAS_MOTORBIKE_E;
			break;
		case "FOREIGN_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_FOREIGN_MOTORBIKE_E;
			break;
		case "LOW_SPEED_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_LOW_SPEED_CAR_E;
			break;
		case "TYPE_TRACTOR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TRACTOR_E;
			break;
		case "TRAILER":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TRAILER_E;
			break;
		case "COACH_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_COACH_CAR_E;
			break;
		case "COACH_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_COACH_MOTORBIKE_E;
			break;
		case "TEMPORARY_ENTRY_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TEMPORARY_ENTRY_CAR_E;
			break;
		case "TEMPORARY_ENTRY_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TEMPORARY_ENTRY_MOTORBIKE_E;
			break;
		case "TEMPORARY_DRIVING":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_TEMPORARY_DRIVING_E;
			break;
		case "POLICE_CAR":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_POLICE_CAR_E;
			break;
		case "POLICE_MOTORBIKE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_POLICE_MOTORBIKE_E;
			break;
		case "AGRICULTURAL":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_AGRICULTURAL_E;
			break;
		case "HONGKONG_ENTRY_EXIT":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_HONGKONG_ENTRY_EXIT_E;
			break;
		case "MACAO_ENTRY_EXIT":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_MACAO_ENTRY_EXIT_E;
			break;
		case "ARMED_POLICE":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_ARMED_POLICE_E;
			break;
		case "ARMY":
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_ARMY_E;
			break;

		default:
			dwRet = NETDEV_PLATE_TYPE_E.NETDEV_PLATE_TYPE_OTHER_E;
			break;
		}

	return dwRet;
	}
	
	public static int StringConventToEnumNETDEV_VEHICLE_MONITOR_TYPE_E(String strMonitorType)
	{
		int dwTemp = 0;
		switch (strMonitorType) {
		case "Robbed car":
			dwTemp = NETDEV_VEHICLE_MONITOR_TYPE_E.NETDEV_VEHICLE_MONITOR_TYPE_ROB_E;
			break;
		case "Stolen car":
			dwTemp = NETDEV_VEHICLE_MONITOR_TYPE_E.NETDEV_VEHICLE_MONITOR_TYPE_STEAL_E;
			break;
		case "Suspected vehicle":
			dwTemp = NETDEV_VEHICLE_MONITOR_TYPE_E.NETDEV_VEHICLE_MONITOR_TYPE_SUSPICION_E;
			break;
		case "Illegal vehicles":		
			dwTemp = NETDEV_VEHICLE_MONITOR_TYPE_E.NETDEV_VEHICLE_MONITOR_TYPE_ILLEGAL_E;
			break;
		case "Emergency check and control vehicle":
			dwTemp = NETDEV_VEHICLE_MONITOR_TYPE_E.NETDEV_VEHICLE_MONITOR_TYPE_SUREILLANCE_E;
			break;
		default:
			dwTemp = NETDEV_VEHICLE_MONITOR_TYPE_E.NETDEV_VEHICLE_MONITOR_TYPE_ALL_E;
			break;
		}

	return dwTemp;
	}
	
	public static String EnumNETDEV_DISK_WORK_STATUS_EConventToString(int dwEnum)
	{
		String strTempString = "";
		switch (dwEnum) {
		case NETDEV_DISK_WORK_STATUS_E.NETDEV_DISK_WORK_STATUS_EMPTY:
			strTempString = "EMPTY";
			break;
		case NETDEV_DISK_WORK_STATUS_E.NETDEV_DISK_WORK_STATUS_UNFORMAT:
			strTempString = "UNFORMAT";
			break;
		case NETDEV_DISK_WORK_STATUS_E.NETDEV_DISK_WORK_STATUS_FORMATING:
			strTempString = "FORMATING";
			break;
		case NETDEV_DISK_WORK_STATUS_E.NETDEV_DISK_WORK_STATUS_RUNNING:
			strTempString = "RUNNING";
			break;
		case NETDEV_DISK_WORK_STATUS_E.NETDEV_DISK_WORK_STATUS_HIBERNATE:
			strTempString = "HIBERNATE";
			break;
		case NETDEV_DISK_WORK_STATUS_E.NETDEV_DISK_WORK_STATUS_ABNORMAL:
			strTempString = "ABNORMAL";
			break;

		default:
			strTempString = "UNKNOWN";
			break;
		}

	return strTempString;
	}
	
	public static String EnumNETDEV_VIDEO_QUALITY_EConventToString(int dwEnum)
	{
		String strTempString = "";
		switch (dwEnum) {
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L0:
			strTempString = "L0";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L1:
			strTempString = "L1";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L2:
			strTempString = "L2";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L3:
			strTempString = "L3";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L4:
			strTempString = "L4";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L5:
			strTempString = "L5";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L6:
			strTempString = "L6";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L7:
			strTempString = "L7";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L8:
			strTempString = "L8";
			break;
		case NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L9:
			strTempString = "L9";
			break;

		default:
			strTempString = "";
			break;
		}

	return strTempString;
	}
	
	public static int StringConventToEnumNETDEV_VIDEO_QUALITY_E(String strVideoQuality)
	{
		int dwTemp = 0;
		switch (strVideoQuality) {
		case "L0":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L0;
			break;
		case "L1":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L1;
			break;
		case "L2":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L2;
			break;
		case "L3":		
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L3;
			break;
		case "L4":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L4;
			break;
		case "L5":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L5;
			break;
		case "L6":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L6;
			break;
		case "L7":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L7;
			break;
		case "L8":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L8;
			break;
		case "L9":
			dwTemp = NETDEV_VIDEO_QUALITY_E.NETDEV_VQ_L9;
			break;
		default:
			dwTemp = -1;
			break;
		}

	return dwTemp;
	}
	
}
