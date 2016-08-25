package me.smart.flyme.bean.computer;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.smart.flyme.RadarScan.utils.LogUtil;

/**
 * author：wypx on 2016/7/24 22:35
 * blog：smarting.me
 */
public class DiskInfo implements Serializable
{
		private int     cmdType;
		private List<DiskItem> drivers = new ArrayList<DiskItem>();


		public List<DiskItem> getDrivers() {
				return drivers;
		}

		public void setDrivers(List<DiskItem> drivers) {
				this.drivers = drivers;
		}


		public int getCmdType() {
				return cmdType;
		}

		public void setCmdType(int cmdType) {
				this.cmdType = cmdType;
		}

		/**
		 * 默认的构造方法必须不能省，不然不能解析
		 */
		public DiskInfo()
		{

		}
		public static DiskInfo decode(String json)
		{
				if(null == json)
				{
						return null;
				}
				DiskInfo diskInfo = JSON.parseObject(json, DiskInfo.class);
				if(null == diskInfo)
				{
						LogUtil.e("diskInfo is null");
						return  null;
				}
				return diskInfo;
		}
		public String getDiskName(int position)
		{
				return  drivers.get(position).getDiskName();
		}
		public String getTotalSpace(int position)
		{
				return  drivers.get(position).getTotalSpace();
		}
		public String getFreeSpace(int position)
		{
				return  drivers.get(position).getFreeSpace();
		}
		public String getDiskName2(int position)
		{
				return  drivers.get(position).getDiskSerial();
		}
		public int getDriversNum()
		{
				return  drivers.size();
		}
}
