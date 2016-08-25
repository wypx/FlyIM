package me.smart.mylibrary.utils.net;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IPTools {


		//其中dhcpInfo属性的值为int型，要转换成通常见到的32位地址则需要转换方法：
		//把int型的IP专程byte字节型的
		public  byte[] intToByteArray(int value)
		{
				byte[] b = new byte[4];
				for (int i = 0; i < 4; i++)
				{
						int offset = (b.length - 1 - i) * 8;
						b[i] = (byte) ((value >>> offset) & 0xFF);
				}
				return b;
		}
		//转话int的Ip地址为String类型的
		public static InetAddress intToInetAddress(int hostAddress)
		{
				byte[] addressBytes =
								{
												(byte)(0xff & hostAddress),
												(byte)(0xff & (hostAddress >> 8)),
												(byte)(0xff & (hostAddress >> 16)),
												(byte)(0xff & (hostAddress >> 24))
								};

				try
				{
						return InetAddress.getByAddress(addressBytes);
				}
				catch (UnknownHostException e)
				{
						throw new AssertionError();
				}
        /*
         *  方法2
         * return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "."
            + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
         */
		}

		//获取本机设备名称
		public static String getLocDeviceName()
		{
				return android.os.Build.MODEL;
		}

		//获取本机MAC
		public String getLocalMacAddress(Context context)
		{
				WifiManager wifi = (WifiManager)context. getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				return info.getMacAddress();
		}
		//获取本地ip地址---------获取本地内网IP
		//判断wifi下局域网IP
		public static String getIpAddress(Context context)
		{
				WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
				{
						WifiInfo info = wm.getConnectionInfo();
						int  hostip = info.getIpAddress();
						String ip = (hostip & 0xFF)+"."+((hostip>>8)&0xFF)+ "."
										+ ((hostip >> 16 ) & 0xFF) +"."+((hostip >> 24 ) & 0xFF);
						return ip;
				}
				return null;
		}

		//开启wifi  用Switch组建
		public void ToggleWiFi(Context context,boolean status)
		{
				WifiManager wifiManager = (WifiManager) context
								.getSystemService(Context.WIFI_SERVICE);
				if (status == true && !wifiManager.isWifiEnabled())
				{
						wifiManager.setWifiEnabled(true);
				}
				else if (status == false && wifiManager.isWifiEnabled())
				{
						wifiManager.setWifiEnabled(false);
				}
		}
		//开启手机数据
//		public void ToggleMobileData(Context context, boolean state)
//		{
//				ConnectivityManager connectivityManager = null;
//				try
//				{
//						connectivityManager = (ConnectivityManager) context
//										.getSystemService("connectivity");
//						Method method = connectivityManager.getClass().getMethod(
//										"setMobileDataEnabled", new Class[] { boolean.class });
//						method.invoke(connectivityManager, state);
//				}
//				catch (Exception e)
//				{
//						e.printStackTrace();
//				}}


		/**
		 * 使用Wifi时获取IP 设置用户权限
		 *
		 * <uses-permission
		 * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
		 *
		 * <uses-permission
		 * android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
		 *
		 * <uses-permission
		 * android:name="android.permission.WAKE_LOCK"></uses-permission>
		 *
		 * @return
		 */
		public static String getWifiIp(Context context)
		{
				// 获取wifi服务
				WifiManager wifiManager = (WifiManager) context
								.getSystemService(Context.WIFI_SERVICE);
				// 判断wifi是否开启
				if (!wifiManager.isWifiEnabled())
				{
						wifiManager.setWifiEnabled(true);
				}
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				int ipAddress = wifiInfo.getIpAddress();
				return intToIp(ipAddress);
		}

		private static String intToIp(int i)
		{
				return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
								+ "." + (i >> 24 & 0xFF);

		}

		/**
		 * 使用GPRS上网，时获取ip地址，设置用户上网权限
		 *
		 * <uses-permission
		 * android:name="android.permission.INTERNET"></uses-permission>
		 *
		 * @return
		 */
		public static String getGPRSIp()
		{
				try
				{
						for (Enumeration<NetworkInterface> en = NetworkInterface
										.getNetworkInterfaces(); en.hasMoreElements();)
						{
								NetworkInterface intf = en.nextElement();
								for (Enumeration<InetAddress> enumIpAddr = intf
												.getInetAddresses(); enumIpAddr.hasMoreElements();)
								{
										InetAddress inetAddress = enumIpAddr.nextElement();
										if (!inetAddress.isLoopbackAddress())
										{
												return inetAddress.getHostAddress().toString();
										}
								}
						}
				} catch (SocketException ex)
				{

				}
				return "";
		}
}
