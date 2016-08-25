package me.smart.flyme.fragement.video;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * author：wypx on 2015/12/27 23:49
 * blog：smarting.me
 */
@SuppressWarnings("deprecation")
public class StreamCallback implements Camera.PreviewCallback
{
		/**
		 * android摄像头模块不仅预览,拍照这么简单,而是需要在预览视频的时候，
		 * 能够做出一些检测,比如最常见的人脸检测。在未按下拍照按钮前，
		 * 就检测出人脸然后矩形框标示出来,再按拍照。那么如何获得预览帧视频么？
		 * 答案是: 只需要在Activity里继承PreviewCallback这个接口就行了
		 *
		 * @param data
		 * @param camera
		 */

		private String ipaddr;
		private int YUVIMGLEN;

		//声明一个构造函数
		public StreamCallback(String ipaddr)
		{
				this.ipaddr = ipaddr;
		}

		/**
		 * 会自动重载这个函数: public void onPreviewFrame(byte[] data, Camera camera) {}
		 * 这个函数的data就是实时预览帧视频。一旦程序调用PreviewCallback接口,就会自动调用onPreviewFrame这个函数
		 *
		 * @param data
		 * @param camera
		 */
		@Override
		public void onPreviewFrame(byte[] data, Camera camera)
		{
				//传递进来的data,默认是YUV420SP的
				// 获取原生的YUV420SP数据


				//获取摄像头的尺寸大小  传递进来的data,默认是YUV420SP的
				Camera.Size size = camera.getParameters().getPreviewSize();
				try
				{
						//调用image.compressToJpeg（）将YUV格式图像数据data转为jpg格式
						//首先将字节数组data转成YuvImage格式的图片
						YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
						if (image != null)
						{
								//声明并初始化字节数组输出流
								ByteArrayOutputStream outstream = new ByteArrayOutputStream();
								//将此图片压缩成JPEG格式的图片存放在输出流中
								//在此设置图片的尺寸和质量
								image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, outstream);
								outstream.flush();
								//启用线程将图像数据发送出去
								Thread th = new StreamSendThread(outstream, ipaddr);
								//开启线程
								th.start();
						}
				} catch (Exception ex)
				{
						Log.e("amor", "Error:" + ex.getMessage());
				}

		}
}

