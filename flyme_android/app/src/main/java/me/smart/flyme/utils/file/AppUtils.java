package me.smart.flyme.utils.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 *  APP工具类
 */
public class AppUtils
{
		public static boolean DEBUG = false;
		public static String TAG = "AppUtils";

		/**
		 *  获得APP的label名字
		 * @param context
		 * @return
		 */
		public static String getAppName(Context context)
		{
				if (null == context)
				{
						return null;
				}
				try
				{
						PackageManager packageManager = context.getPackageManager();
						PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
						int labelRes = packageInfo.applicationInfo.labelRes;
						String appName = context.getResources().getString(labelRes);
						Log.i("App名字:", appName);
						return appName;
				}
				catch (NameNotFoundException e)
				{
						e.printStackTrace();
				}
				return null;
		}

		/**
		 * 获取应用程序版本名称信息
		 */
		public static String getVersionName(Context context)
		{
				if (null == context)
				{
						return null;
				}
				try
				{
						PackageManager packageManager = context.getPackageManager();
						PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
						String versionCode = packageInfo.versionName;
						Log.i("App版本:", versionCode);
						return packageInfo.versionName;

				} catch (NameNotFoundException e)
				{
						e.printStackTrace();
				}
				return null;
		}

		/**
		 * 获取应用程序包名
		 */
		public static String getPackageName(Context context)
		{
				if (null == context)
				{
						return null;
				}
				String pkgName = context.getPackageName();
				Log.i("App包名:", pkgName);
				return pkgName;
		}

		@SuppressLint("NewApi")
		public static Drawable getAppIcon(Context context)
		{
				if (null == context)
				{
						return null;
				}
				try
				{
						PackageManager packageManager = context.getPackageManager();
						PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
						int icon = packageInfo.applicationInfo.icon;
						Drawable drawable = context.getResources().getDrawable(icon, null);
						Log.i("App图标:", drawable.toString());
						return drawable;
				}
				catch (NameNotFoundException e)
				{
						e.printStackTrace();
				}
				return null;
		}

		/**
		 *  参考资源:
		 *  http://code.google.com/p/android/issues/detail?id=9151
		 */
		public static Drawable getApkIcon(Context context, String apkPath)
		{
				PackageManager pm = context.getPackageManager();
				PackageInfo info = pm.getPackageArchiveInfo(apkPath,PackageManager.GET_ACTIVITIES);
				if (info != null)
				{
						ApplicationInfo appInfo = info.applicationInfo;
						appInfo.sourceDir = apkPath;
						appInfo.publicSourceDir = apkPath;
						try
						{
								return appInfo.loadIcon(pm);
						}
						catch (OutOfMemoryError e)
						{
								Log.e("ApkIconUtils", e.toString());
						}
				}
				return null;
		}
}
