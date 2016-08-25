package me.smart.flyme.bean.computer;

import java.io.Serializable;

/**
 * author：wypx on 2016/7/29 22:33
 * blog：smarting.me
 *
 *   FileInfo person = JSON.parseObject(jsonString, FileInfo.class);
 */
public class FileInfo implements Serializable
{
		private int    cmdType;
		private String fileName;
		// 文件的路径
		private String filePath;

		private String fileSize;
		private String fileTime;
		private int    fileType;

		// 父节点
		private String parent;

		// 父节点路径
		private String parentPath;

		public FileInfo()
		{
				/**
				 * 默认的构造方法必须不能省，不然不能解析
				 */
		}
		public String getFileName() {
				return fileName;
		}

		public void setFileName(String fileName) {
				this.fileName = fileName;
		}

		public String getFileSize() {
				return fileSize;
		}

		public void setFileSize(String fileSize) {
				this.fileSize = fileSize;
		}

		public String getFileTime() {
				return fileTime;
		}

		public void setFileTime(String fileTime) {
				this.fileTime = fileTime;
		}

		public int getFileType() {
				return fileType;
		}

		public void setFileType(int fileType) {
				this.fileType = fileType;
		}
}
