package me.smart.flyme.bean.computer;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * author：wypx on 2016/7/31 15:21
 * blog：smarting.me
 */
public class Responce implements Serializable
{
		private int    cmdID;
		private int    responce;

		public static Responce decode(String json) {
				if (null == json)
						return null;
				Responce responce = JSON.parseObject(json, Responce.class);
				System.out.println(responce.getCmdID());
				System.out.println(responce.getResponce());
				return responce;
		}
		public  Responce(int cmdDriverList, String s)
		{

		}
		public int getResponce() {
				return responce;
		}

		public void setResponce(int reponce) {
				this.responce = reponce;
		}

		public int getCmdID() {
				return cmdID;
		}

		public void setCmdID(int cmdID) {
				this.cmdID = cmdID;
		}

}
