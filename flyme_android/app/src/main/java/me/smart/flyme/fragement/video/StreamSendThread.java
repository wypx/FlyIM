package me.smart.flyme.fragement.video;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * author：wypx on 2015/12/27 23:54
 * blog：smarting.me
 */
public class StreamSendThread extends Thread
{
		private byte byteBuffer[] = new byte[1024];

		private OutputStream outsocket;

		private ByteArrayOutputStream myoutputstream;

		private String ipaddr;

		public StreamSendThread(ByteArrayOutputStream myoutputstream,String ipname)
		{
				this.myoutputstream = myoutputstream;
				this.ipaddr = ipname;
				try
				{
						//关流
						myoutputstream.close();
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}
		}

		public void run()
		{
				try
				{
						//将图像数据通过Socket发送出去
						Socket tempSocket = new Socket("192.168.1.104", 6000);
						//获取输出流
						outsocket = tempSocket.getOutputStream();
						//将自己数组输出流转化成字节数组后套接在字节数组输入流上
						ByteArrayInputStream inputstream = new ByteArrayInputStream(myoutputstream.toByteArray());
						int n;
						//将输出流读到自己数组中
						while ((n = inputstream.read(byteBuffer)) != -1)
						{
								//将字节数组的数据写到socket输出流上
								outsocket.write(byteBuffer, 0, n);
						}
						myoutputstream.flush();
						myoutputstream.close();
						tempSocket.close();
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}
		}


}

