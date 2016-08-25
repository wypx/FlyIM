
package me.smart.mylibrary.utils.activty;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

// activity堆栈管理
public class ActivitiesManager
{
		public static final String ACTIVITY_FILE_VIEW = "FileView";

		public static final String ACTIVITY_FILE_CATEGORY = "FileCategory";

		public static final String ACTIVITY_TAB = "FileExplorerTab";

		private static Stack<Activity> mActivityStack;

		private static ActivitiesManager  instance;

		private static HashMap<String, Activity> activities ;

		private static Context context;

		private ActivitiesManager(Context context)
		{
				this.context = context;
		}
		//LIFO模式的大小
		public int stackSize()
		{
				return mActivityStack.size();
		}
		//HashMap的大小
		public int mapSize()
		{
				return activities.size();
		}
		public void registerActivity(String name, Activity a)
		{
				activities.put(name, a);
		}

		public Activity getActivity(String name)
		{
				return activities.get(name);
		}

		public static ActivitiesManager getInstance()
		{
				if (null == instance )
				{
						instance = new ActivitiesManager(context);
				}
				//确定一个方式
				if(null == activities)
				{
						activities = new HashMap<String, Activity>();

				}
				if (null == mActivityStack)
				{
						mActivityStack = new Stack<Activity>();
				}
				return instance;
		}
		//获取当前的activity
		public Activity getCurrentActivity()
		{
				Activity activity = null;

				try
				{
						activity = mActivityStack.lastElement();
				}
				catch (Exception e)
				{
						return null;
				}

				return activity;
		}
		//弹出一个activity
		public void popActivity()
		{
				Activity activity = mActivityStack.lastElement();
				if (null != activity)
				{
						Log.i("TAG","popActivity-->"+ activity.getClass().getSimpleName());
						activity.finish();
						mActivityStack.remove(activity);
						activity = null;
				}
		}
		//
		public void popActivity(Activity activity)
		{
				if (null != activity)
				{
						Log.i("TAG", "popActivity-->"+ activity.getClass().getSimpleName());
						// activity.finish();
						mActivityStack.remove(activity);
						activity = null;
				}
		}

		public void pushActivity(Activity activity)
		{
				mActivityStack.add(activity);
				Log.i("TAG", "pushActivity-->" + activity.getClass().getSimpleName());
		}


		// 开启activity
		public static void launchActivity(Context context, Class<?> activity)
		{
				Intent intent = new Intent(context, activity);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				context.startActivity(intent);
		}
		public void popAllActivities()
		{
				while (!mActivityStack.isEmpty())
				{
						Activity activity = getCurrentActivity();
						if (null == activity)
						{
								break;
						}
						activity.finish();
						popActivity(activity);
				}
		}
		public void popSpecialActivity(Class<?> cls)
		{
				try
				{
						Iterator<Activity> iterator = mActivityStack.iterator();
						Activity activity = null;
						while (iterator.hasNext())
						{
								activity = iterator.next();
								if (activity.getClass().equals(cls))
								{
										activity.finish();
										iterator.remove();
										activity = null;
								}
						}
				} catch (Exception e) {

				}
		}


		public void peekActivity()
		{
				for (Activity activity : mActivityStack)
				{
						if (null == activity)
						{
								break;
						}
						Log.i("TAG", "peekActivity()-->"+ activity.getClass().getSimpleName());
				}
		}
		public static void launchActivityForResult(Activity context,Class<?> activity, int requestCode)
		{
				Intent intent = new Intent(context, activity);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				context.startActivityForResult(intent, requestCode);
		}

		//Android 退出Activity

		@SuppressWarnings("deprecation")
		public void DestroyActivities()
		{
				/*1.Dalvik VM的本地方法*/
				android.os.Process.killProcess(android.os.Process.myPid());//获取PID
				System.exit(0); //常规java、c#的标准退出法，返回值为0代表正常退出
				/*2. 任务管理器方法*/
				ActivityManager am= (ActivityManager)context.getSystemService (Context.ACTIVITY_SERVICE);
				am.restartPackage(context.getPackageName());
		}



}
