package me.smart.mylibrary.net;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class gpsdata
{
		public int InfoType;		//数据类型
		public int Latitude;			//纬度
		public int Longitude;		//经度
		public double High;		//海拔
		public double Direct;		//方向
		public double Speed;		//速度
		public String GpsTime;	//GPS时间
		public gpsdata()
		{

		}
		private LocationManager lm;
		private Location loc;
		private Criteria ct;
		private String provider;




}
