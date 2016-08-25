package me.smart.mylibrary.utils.systerm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

@SuppressLint("NewApi")
public class DeviceUtils {

		@SuppressWarnings("unused")
		private String getPhoneInfo(Context context) {

				String phoneInfo = "产品名称: " + android.os.Build.PRODUCT;
				//MODEL 机型
				phoneInfo += "\n用户组: " + android.os.Build.USER;
				phoneInfo += "\nTIME: " + android.os.Build.TIME;
				phoneInfo += "\nTYPE: " + android.os.Build.TYPE;
				phoneInfo += "\n标签: " + android.os.Build.TAGS;
				phoneInfo += "\nRADIO: " + android.os.Build.getRadioVersion();

				phoneInfo += "\n手机型号: " + android.os.Build.MODEL;
				//VERSION.SDK SDK版本
				phoneInfo += "\nSDK版本: " + android.os.Build.VERSION.SDK_INT;
				//VERSION.RELEASE 固件版本
				phoneInfo += "\n系统版本: " + android.os.Build.VERSION.RELEASE;
				phoneInfo += "\nVERSION.CODENAME: " + android.os.Build.VERSION.CODENAME;
				//VERSION.INCREMENTAL 基带版本
				phoneInfo += "\nVERSION.INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL;

				//DEVICE 驱动
				phoneInfo += "\n设备驱动: " + android.os.Build.DEVICE;
				//DISPLAY 显示
				phoneInfo += "\n显示: " + android.os.Build.DISPLAY;
				//BRAND 运营商
				phoneInfo += "\n品牌: " + android.os.Build.BRAND;
				phoneInfo += "\nCPU型号: " + android.os.Build.SUPPORTED_ABIS;
				//phoneInfo += "\nCPU型号: " + android.os.Build.CPU_ABI;
				//phoneInfo+="\nCPU型号: "+android.os.Build.CPU_ABI2;
				////BOARD 主板
				phoneInfo += "\n主板: " + android.os.Build.BOARD;
				phoneInfo += "\nBOOTLOADER: " + android.os.Build.BOOTLOADER;
				//指纹
				phoneInfo += "\n指纹: " + android.os.Build.FINGERPRINT;
				//HARDWARE 硬件
				phoneInfo += "\nHARDWARE: " + android.os.Build.HARDWARE;
				phoneInfo += "\nHOST: " + android.os.Build.HOST;
				phoneInfo += "\nID: " + android.os.Build.ID;
				//MANUFACTURER 生产厂家
				phoneInfo += "\n制造商: " + android.os.Build.MANUFACTURER;

				//获取应用程序的IMEI号
				TelephonyManager telecomManager = (TelephonyManager)context
								.getSystemService(Context.TELEPHONY_SERVICE);
				String imei = telecomManager.getDeviceId();
				phoneInfo += "\nIMEI标识: " + imei;

				return phoneInfo;
		}
}
