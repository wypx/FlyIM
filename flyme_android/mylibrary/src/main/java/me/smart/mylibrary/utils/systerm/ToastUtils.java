package me.smart.mylibrary.utils.systerm;

import android.content.Context;
import android.widget.Toast;

/* Toast 工具类 */
public class ToastUtils
{
		public  static boolean SHOW_DEBUG = true;
		/**
		 * 短时间显示Toast
		 */
		public static void showShort(Context context, CharSequence message)
		{
				if (SHOW_DEBUG)
						Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}

		/**
		 * 短时间显示Toast
		 */
		public static void showShort(Context context, int message)
		{
				if (SHOW_DEBUG)
						Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}

		/**
		 * 长时间显示Toast
		 */
		public static void showLong(Context context, CharSequence message)
		{
				if (SHOW_DEBUG)
						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}

		/**
		 * 长时间显示Toast
		 */
		public static void showLong(Context context, int message)
		{
				if (SHOW_DEBUG)
						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}


		// 自定义显示Toast时间
		public static void show(Context context, CharSequence message, int duration)
		{
				if (SHOW_DEBUG)
						Toast.makeText(context, message, duration).show();
		}

		//自定义显示Toast时间

		public static void show(Context context, int message, int duration)
		{
				if (SHOW_DEBUG)
						Toast.makeText(context, message, duration).show();
		}

}