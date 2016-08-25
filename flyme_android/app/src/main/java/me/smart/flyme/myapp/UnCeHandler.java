package me.smart.flyme.myapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;

import me.smart.flyme.activity.main.MainActivity;

@SuppressWarnings("ResourceType")
public class UnCeHandler implements UncaughtExceptionHandler
{

		private Thread.UncaughtExceptionHandler mDefaultHandler;
		public final String TAG = "CatchExcep";
		MyApp HanderApp;

		public UnCeHandler(MyApp application)
		{
				// 获取系统默认的UncaughtException处理器
				mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
				this.HanderApp = application;
		}

		@Override
		public void uncaughtException(Thread thread, Throwable ex)
		{
				if (!handleException(ex) && mDefaultHandler != null)
				{
						// 如果用户没有处理则让系统默认的异常处理器来处理
						mDefaultHandler.uncaughtException(thread, ex);
				}
				else
				{
						try
						{
								Thread.sleep(2000);
						} catch (InterruptedException e)
						{
								Log.e(TAG, "error : ", e);
						}
						//默认重启的程序
						Intent intent = new Intent(HanderApp.getApplicationContext(),MainActivity.class);
						PendingIntent restartIntent = PendingIntent.getActivity(
										HanderApp.getApplicationContext(), 0, intent,
										Intent.FLAG_ACTIVITY_NEW_TASK);
						// 退出程序
						AlarmManager mgr = (AlarmManager) HanderApp.getSystemService(Context.ALARM_SERVICE);
						mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,restartIntent);
						// 1秒钟后重启应用
						HanderApp.finishActivity();
				}
		}

		/**
		 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
		 *
		 * @param ex
		 * @return true:如果处理了该异常信息;否则返回false.
		 */
		private boolean handleException(Throwable ex)
		{
				if (ex == null)
				{
						return false;
				}
				// 使用Toast来显示异常信息
				new Thread()
				{
						@Override
						public void run()
						{
								Looper.prepare();
								Toast.makeText(HanderApp.getApplicationContext(),"程序异常,即将退出...", Toast.LENGTH_SHORT).show();
								Looper.loop();
						}
				}.start();
				return true;
		}

}
