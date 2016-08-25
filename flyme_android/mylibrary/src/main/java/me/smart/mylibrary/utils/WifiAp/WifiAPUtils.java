package me.smart.mylibrary.utils.WifiAp;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * author：wypx on 2016/7/3 00:31
 * blog：smarting.me
 */
public class WifiAPUtils
{
		private boolean flag=false;
		private WifiManager wifiManager;
		//先获取到wifi的服务，再配置热点名称、密码等等，然后再通过反射打开它

		WifiAPUtils(Context context)
		{
				//获取wifi管理服务
				wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		}

		public void startSatAp()
		{
				//如果是打开状态就关闭，如果是关闭就打开
				flag=!flag;
				setWifiApEnabled(flag);
		}
		// wifi热点开关
		public boolean setWifiApEnabled(boolean enabled)
		{
				if (enabled)
				{ // disable WiFi in any case
						//wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
						wifiManager.setWifiEnabled(false);
				}
				try
				{
						//热点的配置类
						WifiConfiguration apConfig = new WifiConfiguration();
						apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
						//配置热点的名称(可以在名字后面加点随机数什么的)
						apConfig.SSID = "YRCCONNECTION";
						//配置热点的密码
						apConfig.preSharedKey="12122112";
						//通过反射调用设置热点
						Method method = wifiManager.getClass().getMethod(
										"setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
						//返回热点打开状态
						return (Boolean) method.invoke(wifiManager, apConfig, enabled);
				}
				catch (Exception e) {
						return false;
				}
		}


}
