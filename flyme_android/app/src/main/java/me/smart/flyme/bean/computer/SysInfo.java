package me.smart.flyme.bean.computer;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

import me.smart.flyme.RadarScan.utils.LogUtil;

/**
 * author：wypx on 2016/9/2 23:41
 * blog：smarting.me
 */
public class SysInfo implements Serializable {



		private int    cmdType;
		private String hostName;
		private String user;
		private int totalMem;
		private int cpuNum;
		private int cpuFre;

		public static SysInfo decode(String json)
		{
				LogUtil.e(json);
				if(null == json)
				{
						return null;
				}
				SysInfo sysinfo = JSON.parseObject(json, SysInfo.class);
				if(null == sysinfo)
				{
						LogUtil.e("sysinfo is null");
						return  null;
				}
				LogUtil.e(sysinfo.getCmdType());
				LogUtil.e(sysinfo.getCpuFre());
				LogUtil.e(sysinfo.getCpuNum());
				LogUtil.e(sysinfo.getUser());
				LogUtil.e(sysinfo.getHostName());
				LogUtil.e(sysinfo.getTotalMem());
				return sysinfo;
		}
		public SysInfo()
		{
				/**
				 * 默认的构造方法必须不能省，不然不能解析
				 */
		}
		public int getCmdType() {
				return cmdType;
		}

		public void setCmdType(int cmdType) {
				this.cmdType = cmdType;
		}
		public String getHostName() {
				return hostName;
		}

		public void setHostName(String hostName) {
				this.hostName = hostName;
		}

		public String getUser() {
				return user;
		}

		public void setUser(String user) {
				this.user = user;
		}

		public int getTotalMem() {
				return totalMem;
		}

		public void setTotalMem(int totalMem) {
				this.totalMem = totalMem;
		}

		public int getCpuNum() {
				return cpuNum;
		}

		public void setCpuNum(int cpuNum) {
				this.cpuNum = cpuNum;
		}

		public int getCpuFre() {
				return cpuFre;
		}

		public void setCpuFre(int cpuFre) {
				this.cpuFre = cpuFre;
		}


}
