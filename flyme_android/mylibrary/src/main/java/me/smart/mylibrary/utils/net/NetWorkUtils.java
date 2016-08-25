package me.smart.mylibrary.utils.net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public class NetWorkUtils {

		public static final String NETWORK_TYPE_WIFI       = "wifi";
		public static final String NETWORK_TYPE_3G         = "eg";
		public static final String NETWORK_TYPE_2G         = "2g";
		public static final String NETWORK_TYPE_WAP        = "wap";
		public static final String NETWORK_TYPE_UNKNOWN    = "unknown";
		public static final String NETWORK_TYPE_DISCONNECT = "disconnect";


		public static boolean DEBUG = false;
		// 接受网络状态的广播Action
		public static final String NET_BROADCAST_ACTION = "com.network.state.action";

		public static final String  NET_STATE_NAME = "";
		public static int CURRENT_NETWORK_STATE = -1;



		//取得网络类型
		public static int getNetworkType(Context context)
		{
				ConnectivityManager connectivityManager = (ConnectivityManager)context
								.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
				return networkInfo == null ? -1 : networkInfo.getType();
		}
		//获取网络名称
		@SuppressWarnings({ "deprecation" })
		public static String getNetworkTypeName(Context context)
		{
				ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo;
				String type = NETWORK_TYPE_DISCONNECT;
				if (null == manager || (networkInfo = manager.getActiveNetworkInfo()) == null)
				{
						return type;
				};

				if (networkInfo.isConnected())
				{
						String typeName = networkInfo.getTypeName();
						if ("WIFI".equalsIgnoreCase(typeName))
						{
								type = NETWORK_TYPE_WIFI;
						}
						else if ("MOBILE".equalsIgnoreCase(typeName))
						{
								@SuppressWarnings({ })
								String proxyHost = android.net.Proxy.getDefaultHost();
								type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ?
												NETWORK_TYPE_3G : NETWORK_TYPE_2G) : NETWORK_TYPE_WAP;
						}
						else
						{
								type = NETWORK_TYPE_UNKNOWN;
						}
				}
				return type;
		}
		//是否是快速移动网络
		private static boolean isFastMobileNetwork(Context context)
		{
				TelephonyManager telephonyManager = (TelephonyManager)context.
								getSystemService(Context.TELEPHONY_SERVICE);
				if (null == telephonyManager)
				{
						return false;
				}

				switch (telephonyManager.getNetworkType())
				{
						case TelephonyManager.NETWORK_TYPE_1xRTT:
								return false;
						case TelephonyManager.NETWORK_TYPE_CDMA:
								return false;
						case TelephonyManager.NETWORK_TYPE_EDGE:
								return false;
						case TelephonyManager.NETWORK_TYPE_EVDO_0:
								return true;
						case TelephonyManager.NETWORK_TYPE_EVDO_A:
								return true;
						case TelephonyManager.NETWORK_TYPE_GPRS:
								return false;
						case TelephonyManager.NETWORK_TYPE_HSDPA:
								return true;
						case TelephonyManager.NETWORK_TYPE_HSPA:
								return true;
						case TelephonyManager.NETWORK_TYPE_HSUPA:
								return true;
						case TelephonyManager.NETWORK_TYPE_UMTS:
								return true;
						case TelephonyManager.NETWORK_TYPE_EHRPD:
								return true;
						case TelephonyManager.NETWORK_TYPE_EVDO_B:
								return true;
						case TelephonyManager.NETWORK_TYPE_HSPAP:
								return true;
						case TelephonyManager.NETWORK_TYPE_IDEN:
								return false;
						case TelephonyManager.NETWORK_TYPE_LTE:
								return true;
						case TelephonyManager.NETWORK_TYPE_UNKNOWN:
								return false;
						default:
								return false;
				}
		}
	/*
	 * 实时更新网络状态
	 * -1为网络无连接
	 * 1为WIFI
	 * 2为移动网络
	*/

		// 判断网络是否连接
		public static boolean isConnected(Context context) {

				ConnectivityManager connectivity = (ConnectivityManager)context
								.getSystemService(Context.CONNECTIVITY_SERVICE);

				if (null != connectivity) {

						NetworkInfo info = connectivity.getActiveNetworkInfo();
						if (null != info && info.isConnected()) {
								if (info.getState() == NetworkInfo.State.CONNECTED) {
										Log.i("NetWork", "当前网络可用");
										return true;
								}
						}
				}
				Log.i("NetWork", "当前网络不可用");
				return false;
		}

		//判断是否是wifi连接
		public static boolean isWifi(Context context) {
				ConnectivityManager cm = (ConnectivityManager) context
								.getSystemService(Context.CONNECTIVITY_SERVICE);

				if (cm == null) {
						Log.i("NetWork", "当前网络不可用");
						return false;
				}
				boolean isWifi = cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
				if (isWifi)
						Log.i("NetWork", "当前网络----->WIFI环境");
				else
						Log.i("NetWork", "当前网络----->非WIFI环境");

				return isWifi;

		}

		/**
		 * 打开网络设置界面
		 */
		public static void openSetting(Activity activity) {
				Intent intent = new Intent("/");
				ComponentName cm = new ComponentName("com.android.settings",
								"com.android.settings.WirelessSettings");
				intent.setComponent(cm);
				intent.setAction("android.intent.action.VIEW");
				activity.startActivityForResult(intent, 0);
		}

		/**
		 * 开启服务,实时监听网络变化
		 * 需要自己在清单文件中配置服务
		 * 然后把对应的Action传入
		 * 服务类:android.develop.utils.net.NetService
		 */
		public static void startNetService(Context context,String action){
				//注册广播
				IntentFilter intentFilter = new IntentFilter();
				intentFilter.addAction(NET_BROADCAST_ACTION);
				context.registerReceiver(mReceiver, intentFilter);
				//开启服务
				Intent intent = new Intent();
				Log.i("NetWork", "开启网络监听服务");

				intent.setAction(action);
				context.bindService(intent, new ServiceConnection() {

						@Override
						public void onServiceDisconnected(ComponentName name) {

						}

						@Override
						public void onServiceConnected(ComponentName name, IBinder service) {

						}
				}, Context.BIND_AUTO_CREATE);
		}
		//接受服务上发过来的广播
		private static BroadcastReceiver mReceiver = new BroadcastReceiver(){

				@Override
				public void onReceive(Context context, Intent intent) {
						if(intent!=null){
								Log.i("NetWork", "当前网络----->WIFI环境");

								CURRENT_NETWORK_STATE=Integer.parseInt((String)intent.getExtras().get(NET_STATE_NAME));
								switch (CURRENT_NETWORK_STATE) {
										case -1:
												Log.i("NetWork", "网络更改为 无网络  CURRENT_NETWORK_STATE ="+CURRENT_NETWORK_STATE);
												break;
										case 1:
												Log.i("NetWork", "网络更改为 WIFI网络  CURRENT_NETWORK_STATE="+CURRENT_NETWORK_STATE);
												break;
										case 2:
												Log.i("NetWork", "网络更改为 移动网络  CURRENT_NETWORK_STATE ="+CURRENT_NETWORK_STATE);
												break;

										default:
												break;
								}
						}
				}

		};
}
