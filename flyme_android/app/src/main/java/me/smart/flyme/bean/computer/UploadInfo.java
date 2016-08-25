package me.smart.flyme.bean.computer;

import java.io.Serializable;

/**
 * author：wypx on 2015/12/29 00:31
 * blog：smarting.me
 */
public class UploadInfo implements Serializable
{
		private long id;

		// 服务端 上传记录标识
		private String sourceid;

		private String uploadfilepath;

		public String getSourceid()
		{
				return sourceid;
		}

		public void setSourceid(String sourceid)
		{
				this.sourceid = sourceid;
		}

		public String getUploadfilepath()
		{
				return uploadfilepath;
		}

		public void setUploadfilepath(String uploadfilepath)
		{
				this.uploadfilepath = uploadfilepath;
		}

		public long getId()
		{
				return id;
		}

		public void setId(long id)
		{
				this.id = id;
		}
}
