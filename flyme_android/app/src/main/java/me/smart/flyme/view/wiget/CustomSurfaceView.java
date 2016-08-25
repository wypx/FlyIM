package me.smart.flyme.view.wiget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import me.smart.flyme.fragement.video.StreamCallback;

/**
 * author：wypx on 2015/12/27 23:13
 * blog：smarting.me
 */
public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener
{
		//	SurfaceView是视图(View)的继承类,这个视图里内嵌了一个专门用于绘制的Surface。
		//	你可以控制这个Surface的格式和尺寸.Surfaceview控制这个Surface的绘制位置。
		//	surface是纵深排序(Z-ordered)的，这表明它总在自己所在窗口的后面
		//	使用SurfaceView 有一个原则，所有的绘图工作必须得在Surface 被创建之后才能开始
		//this

		//	通过SurfaceHolder接口访问这个surface，getHolder()方法可以得到这个接口。
		private SurfaceHolder surfaceHolder;
		private Camera camera;                    // 定义系统所用的照相机

		//	声明屏幕宽高的变量
		private int screenWidth;
		private int screenHeight;

		private boolean isPreview = false;        //是否在浏览中

		private String ipaddr;  //ip地址

		public CustomSurfaceView(Context context)
		{
				super(context);
				screenWidth = 640;
				screenHeight = 480;
				surfaceHolder = this.getHolder();// 获得SurfaceView的SurfaceHolder
				surfaceHolder.addCallback(this);
				// 设置该SurfaceView自己不维护缓冲
				// 设置显示器类型，setType必须设置
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

				setOnTouchListener(this);

		}

		public CustomSurfaceView(Context context, AttributeSet attrs)
		{
				super(context, attrs);
				//视频参数
				screenWidth = 640;
				screenHeight = 480;
				surfaceHolder = this.getHolder();// 获得SurfaceView的SurfaceHolder
				surfaceHolder.addCallback(this);
				// 设置该SurfaceView自己不维护缓冲
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				// surfaceHolder.setFixedSize(176, 144); // 预览大小設置


				setOnTouchListener(this);
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder)
		{
				startCamera();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
		{
				//add
				if ( camera != null )
				{
						stopCamera();
						try
						{
								camera.setPreviewDisplay(holder);
						}
						catch ( Exception ex)
						{
								ex.printStackTrace();
						}
						startCamera();
				}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
		{
				stopCamera();
		}
		//打开摄像头
		private void startCamera()
		{
				//如果此时没有在浏览中，那么打开摄像头
				if (!isPreview)
				{
						camera = Camera.open();
				}
				//	当此时摄像头不为空且不在浏览中，那么进行设置摄像头参数
				if (camera != null && !isPreview)
				{
						try
						{

								Camera.Parameters parameters = camera.getParameters();
								// parameters.setFlashMode("off"); // 无闪光灯
								parameters.setPreviewSize(screenWidth, screenHeight);    // 设置预览照片的大小
								parameters.setPreviewFpsRange(20,30);                    // 每秒显示20~30帧
								parameters.setPictureFormat(ImageFormat.NV21);           // 设置图片格式
								parameters.setPictureSize(screenWidth, screenHeight);    // 设置照片的大小

								parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);

								//camera.setParameters(parameters);                      // android2.3.3以后不需要此行代码
								camera.setPreviewDisplay(surfaceHolder);                 // 通过SurfaceView显示取景画面
								camera.setPreviewCallback(new StreamCallback(ipaddr));   // 设置回调的类

								if (this.getResources().getConfiguration().orientation !=
												Configuration.ORIENTATION_LANDSCAPE)
								{
										//旋转角度,不然是偏的
										//这是一个众所周知但未文档化的特性
										parameters.set("orientation", "portrait");

										//对于Android 2.2及以上版本  镜头角度转90度（默认摄像头是横拍）
										camera.setDisplayOrientation(90);

										//对于Android 2.2及以上版本取消注释
										//parameters.setRotation(90);
								}
								else
								{
										//这是一个众所周知但未文档化的特性
										parameters.set("orientation", "landscape");

										//对于Android 2.2及以上版本
										//camera.setDisplayOrientation(0);

										//对于Android 2.2及以上版本取消注释
										//parameters.setRotation(0);
								}


								camera.startPreview();                                   // 开始预览
								camera.autoFocus(null);                                  // 自动对焦
						}
						catch (Exception e)
						{
								e.printStackTrace();
						}
						isPreview = true;
				}

		}
		private void stopCamera()
		{
				// 如果camera不为null ,释放摄像头
				if (camera != null)
				{
						if (isPreview)
						{
								camera.stopPreview();
						}
						camera.setPreviewCallback(null);
						camera.release();
						camera = null;
				}

		}
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
				return true;
		}
}
