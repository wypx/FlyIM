package me.smart.flyme.myapp;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import me.smart.flyme.socket.DataTransform;

//使用application来保存全局变量
//全局变量，socket的状态等等
//<application android:name="com.example.data.Myapp"
public class MyApp extends Application
{
		public final static String TAG = "FlyMeApp";
		private static int Port=9999;
		private static String ServerIP = "192.168.31.120";

		private static Socket socket_instance = null ;

		private static final String CANARO_EXTRA_BOLD_PATH = "fonts/mini.ttf";
		public static Typeface canaroExtraBold;//字体

		//是否开启Debug模式
		public static boolean DEBUG = true;

		public static DataTransform dataTransform;

		private static MyApp sApplication;

		//初始化函数
		@Override
		public void onCreate()
		{
				sApplication = this;
				super.onCreate();
				initTypeface();
		}
		//单例模式中获取唯一的MyApplication实例
		public MyApp() throws UnknownHostException, IOException
		{
				if(null == dataTransform )
				{
						dataTransform = new DataTransform();
				}
		}
		public static MyApp getInstance()
		{
				return sApplication;
		}

		public static synchronized Socket getSockInstance()throws UnknownHostException, IOException
		{
				if(null == socket_instance )
				{
						Log.e("IP",ServerIP + "  " +  Port);
						socket_instance = new Socket(ServerIP, Port);
				}
				return socket_instance;
		}
		public static void Send(String buf) throws IOException
		{
				getSockInstance().getOutputStream().write(buf.getBytes());
		}
		public static String Recv() throws IOException
		{
				byte buf[] = new byte[2048];;
				getSockInstance().getInputStream().read(buf);

				return dataTransform.ByteArraytoString(buf,2048);
		}
		//搜索得到IP
		public static void setServerIP(String serverIP)
		{
				ServerIP = serverIP;
		}
		//得到IP
		public static String getServerIP()
		{
				return ServerIP;
		}
		////////////////////////////////////////////////////////

		ArrayList<Activity> list = new ArrayList<Activity>();

		public void init()
		{
				//设置该CrashHandler为程序的默认处理器
				UnCeHandler catchExcep = new UnCeHandler(this);
				Thread.setDefaultUncaughtExceptionHandler(catchExcep);
		}

		/**
		 * Activity关闭时，删除Activity列表中的Activity对象
		 */
		public void removeActivity(Activity a)
		{
				list.remove(a);
		}

		/**
		 * 向Activity列表中添加Activity对象
		 */
		public void addActivity(Activity a)
		{
				list.add(a);
		}

		/**
		 * 关闭Activity列表中的所有Activity*/
		public void finishActivity()
		{
				for (Activity activity : list)
				{
						if (null != activity)
						{
								activity.finish();
						}
				}
				//杀死该应用进程
				android.os.Process.killProcess(android.os.Process.myPid());
		}

		/**
		 *初始化字体的使用
		 */
		private void initTypeface()
		{
				canaroExtraBold = Typeface.createFromAsset(getAssets(),CANARO_EXTRA_BOLD_PATH);
		}
}
