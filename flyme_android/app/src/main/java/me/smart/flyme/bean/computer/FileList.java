package me.smart.flyme.bean.computer;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author：wypx on 2016/8/1 21:59
 * blog：smarting.me
 */
public class FileList implements Serializable
{
		private int  cmdType;
		private List<FileListItem> fileList = new ArrayList<FileListItem>();

		public int getCmdType() {
				return cmdType;
		}
		public void setCmdType(int cmdType) {
				this.cmdType = cmdType;
		}
		public List<FileListItem> getFileList() {
				return fileList;
		}

		public void setFileList(List<FileListItem> fileList) {
				this.fileList = fileList;
		}
		public static FileList decode(String json)
		{
				if(null == json)
						return null;
				FileList fileList = JSON.parseObject(json, FileList.class);
				System.out.println("cmdType: " + fileList.getCmdType());
				System.out.println("size: " + fileList.getFileList().size());


				if(fileList.getFileList().size() == 0 || null == fileList)
						return fileList;

				for(int i = 0;i < fileList.getFileList().size() ; i++)
				{
						System.out.println("fileName: "+ fileList.getFileList().get(i).getFileName());
				}
				return fileList;
		}
		/**
		 * 默认的构造方法必须不能省，不然不能解析
		 */
		public FileList()
		{

		}
}
