package me.smart.flyme.bean.computer;

import java.io.Serializable;

/**
 * author：wypx on 2016/7/28 00:42
 * blog：smarting.me
 */
public class DiskItem implements Serializable
{
		private String diskName;
		private String totalSpace;
		private String freeSpace;
		private String diskSerial;
		public DiskItem()
		{
				/**
				 * 默认的构造方法必须不能省，不然不能解析
				 */
		}
		public String getDiskName() {
				return diskName;
		}

		public void setDiskName(String diskName) {
				this.diskName = diskName;
		}
		public String getFreeSpace() {
				return freeSpace;
		}

		public void setFreeSpace(String freeSpace) {
				this.freeSpace = freeSpace;
		}

		public String getTotalSpace() {
				return totalSpace;
		}

		public void setTotalSpace(String totalSpace) {
				this.totalSpace = totalSpace;
		}

		public String getDiskSerial() {
				return diskSerial;
		}

		public void setDiskSerial(String diskSerial) {
				this.diskSerial = diskSerial;
		}

		@Override
		public String toString()
		{
				return "driver: "+ diskName + "-" + totalSpace + "-" + freeSpace ;
		}
}
