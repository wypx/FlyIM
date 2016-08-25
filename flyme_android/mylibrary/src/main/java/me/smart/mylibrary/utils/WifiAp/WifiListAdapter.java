package me.smart.mylibrary.utils.WifiAp;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * author：wypx on 2016/7/3 01:08
 * blog：smarting.me
 */
public class WifiListAdapter extends BaseAdapter
{

		LayoutInflater inflater;
		List<ScanResult> list;
		public WifiListAdapter(Context context, List<ScanResult> list)
		{
				this.inflater = LayoutInflater.from(context);
				this.list = list;
		}
		@Override
		public int getCount() {
				return list.size();
		}

		@Override
		public Object getItem(int position) {
				return position;
		}

		@Override
		public long getItemId(int position) {
				return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
				StringBuffer mStringBuffer = null;
				mStringBuffer = new StringBuffer();
				/*
				       getBSSID() 获取BSSID
				       getDetailedStateOf() 获取客户端的连通性
				       getHiddenSSID() 获得SSID 是否被隐藏
				       getIpAddress() 获取IP 地址
				       getLinkSpeed() 获得连接的速度
				       getMacAddress() 获得Mac 地址
				       getRssi() 获得802.11n 网络的信号
				       getSSID() 获得SSID
				       getSupplicanState() 返回具体客户端状态的信息

        */
				View view = null;
			//	view = inflater.inflate(R.layout.item_wifi_list, null);
				ScanResult scanResult = list.get(position);
//				TextView textView = (TextView) view.findViewById(R.id.textView);
//				textView.setText(scanResult.SSID);
//				TextView signalStrenth = (TextView) view.findViewById(R.id.signal_strenth);
				//signalStrenth.setText(String.valueOf(Math.abs(scanResult.level)));

				//level 转换rssi 说明http://www.zhihu.com/question/21106590
				//RSSI = level - NoiceFloor
				//NoiceFloor一般取-96dBm
				//这样如果 level 是 -60dBm, RSSI 就是 36
				mStringBuffer = mStringBuffer.append("NO.").append(position + 1)
								.append(" SSID:").append(scanResult.SSID).append(" BSSID：")
								.append(scanResult.BSSID).append(" \n接入性能：")
								.append(scanResult.capabilities).append(" \n频率：")
								.append(scanResult.frequency).append("\nRSSI：")
								.append(scanResult.level+96).append(" \n连接描述：")
								.append(scanResult.describeContents()).append("\n\n");
//				signalStrenth.setText(mStringBuffer.toString());
				System.out.println(mStringBuffer.toString());
//				ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
				//判断信号强度，显示对应的指示图标
//				if (Math.abs(scanResult.level) > 100) {
//						imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s3));
//				} else if (Math.abs(scanResult.level) > 80) {
//						imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s2));
//				} else if (Math.abs(scanResult.level) > 70) {
//						imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s2));
//				} else if (Math.abs(scanResult.level) > 60) {
//						imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s2));
//				} else if (Math.abs(scanResult.level) > 50) {
//						imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s1));
//				} else {
//						imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s0));
//				}
				return view;

		}
}
