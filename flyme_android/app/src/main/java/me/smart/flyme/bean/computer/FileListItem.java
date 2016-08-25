package me.smart.flyme.bean.computer;

import java.io.Serializable;

/**
 * author：wypx on 2016/8/1 21:55
 * blog：smarting.me
 */
public class FileListItem implements Serializable
{
		private String fileName;
		public FileListItem()
		{
				/**
				 * 默认的构造方法必须不能省，不然不能解析
				 */
		}
		public FileListItem(String fileName)
		{
			this.fileName = fileName;
		}
		public String getFileName() {
				return fileName;
		}

		public void setFileName(String fileName) {
				this.fileName = fileName;
		}


}
