package me.smart.flyme.bean.computer;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * author：wypx on 2016/8/1 22:33
 * blog：smarting.me
 *
 * 构造请求JSON数据包
 */
public class Request implements Serializable
{
		private int    cmdID;
		private String cmdParam;

		public String encodeS() {
				Request request = new Request();
				request.setCmdID(cmdID);
				request.setCmdParam(cmdParam);

				String jsonString = JSON.toJSONString(request);

				return null != jsonString ? jsonString : null;
		}
		public byte[] encodeB() {
				Request request = new Request();
				request.setCmdID(cmdID);
				request.setCmdParam(cmdParam);

				String jsonString = JSON.toJSONString(request);
				if(null != jsonString)
				{
						return  jsonString.getBytes();
				}
				return null;
		}
		public Request()
		{
		}
		public Request(int cmdID,String cmdParam )
		{
				this.cmdID = cmdID;
				this.cmdParam = cmdParam;
		}
		public int getCmdID() {
				return cmdID;
		}
		public void setCmdID(int cmdID) {
				this.cmdID = cmdID;
		}
		public String getCmdParam() {
				return cmdParam;
		}
		public void setCmdParam(String cmdParam) {
				this.cmdParam = cmdParam;
		}
}
