package me.smart.flyme.utils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕 工具类
 * 内部已经封装了打印功能,只需要把DEBUG参数改为true即可
 * 如果需要更换tag可以直接更改,默认为KEZHUANG
 *
 * @author amor
 *
 */
public class ScreenUtils
{

		public static boolean DEBUG =false;
		// Log 输出标签
		public static String TAG = "sv=screenutils";
		// 获得屏幕高度
		public static int getScreenWidth(Context context)
		{
				WindowManager wm = (WindowManager) context
								.getSystemService(Context.WINDOW_SERVICE);
				DisplayMetrics outMetrics = new DisplayMetrics();
				wm.getDefaultDisplay().getMetrics(outMetrics);
				int width = outMetrics.widthPixels;
				Log.i("当前屏幕宽度:", ""+width);

				return width;
		}
		//获得屏幕宽度

		public static int getScreenHeight(Context context)
		{
				WindowManager wm = (WindowManager) context
								.getSystemService(Context.WINDOW_SERVICE);
				DisplayMetrics outMetrics = new DisplayMetrics();
				wm.getDefaultDisplay().getMetrics(outMetrics);
				int height = outMetrics.heightPixels;
				Log.i("当前屏幕高度:", ""+height);

				return height;
		}

		/**
		 * 获得状态栏的高度
		 */
		public static int getStatusHeight(Context context)
		{

				int statusHeight = -1;
				try
				{
						Class<?> clazz = Class.forName("com.android.internal.R$dimen");
						Object object = clazz.newInstance();
						int height = Integer.parseInt(clazz.getField("status_bar_height")
										.get(object).toString());
						statusHeight = context.getResources().getDimensionPixelSize(height);
				}
				catch (Exception e)
				{
						e.printStackTrace();
				}
				Log.i("当前状态栏高度", ""+statusHeight);

				return statusHeight;
		}

		/**
		 * 获取当前屏幕截图，包含状态栏
		 */
		public static Bitmap snapShotWithStatusBar(Activity activity)
		{
				View view = activity.getWindow().getDecorView();
				view.setDrawingCacheEnabled(true);
				view.buildDrawingCache();
				Bitmap bmp = view.getDrawingCache();
				int width = getScreenWidth(activity);
				int height = getScreenHeight(activity);
				Bitmap bp = null;
				bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
				view.destroyDrawingCache();
				return bp;

		}

		/**
		 * 获取当前屏幕截图，不包含状态栏
		 */
		public static Bitmap snapShotWithoutStatusBar(Activity activity)
		{
				View view = activity.getWindow().getDecorView();
				view.setDrawingCacheEnabled(true);
				view.buildDrawingCache();
				Bitmap bmp = view.getDrawingCache();
				Rect frame = new Rect();
				activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
				int statusBarHeight = frame.top;

				int width = getScreenWidth(activity);
				int height = getScreenHeight(activity);
				Bitmap bp = null;
				bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
				view.destroyDrawingCache();
				return bp;
		}

		/*
		 * 把屏幕设置成透明,包括状态栏
		 */
		@TargetApi(Build.VERSION_CODES.KITKAT)
		public static void setScreenTransParent(Activity activity)
		{
				Window window = activity.getWindow();
				window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
}