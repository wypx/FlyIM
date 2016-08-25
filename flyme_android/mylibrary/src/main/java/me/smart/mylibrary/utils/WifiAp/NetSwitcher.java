package me.smart.mylibrary.utils.WifiAp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * author：wypx on 2016/7/3 00:56
 * blog：smarting.me
 */
public class NetSwitcher
{
		private Context context;
		NetSwitcher(Context cx)
		{
			this.context = cx;
		}
		public void ToggleWiFi(boolean status)
		{
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				if (status == true && !wifiManager.isWifiEnabled())
				{
						wifiManager.setWifiEnabled(true);
				}
				else if (status == false && wifiManager.isWifiEnabled())
				{
						wifiManager.setWifiEnabled(false);
				}
		}

		public void ToggleMobileData(Context context, boolean state)
		{
				ConnectivityManager connectivityManager = null;
				try
				{
						connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
						Method method = connectivityManager.getClass().getMethod(
										"setMobileDataEnabled", new Class[] { boolean.class });
						method.invoke(connectivityManager, state);
				} catch (Exception e)
				{
						e.printStackTrace();
				}
		}
}
