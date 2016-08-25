package me.smart.flyme.utils.view;

import android.content.Context;
import android.util.TypedValue;
/**
 * 单位转换 工具类<br>
 * 内部已经封装了打印功能,只需要把DEBUG参数改为true即可<br>
 * 如果需要更换tag可以直接更改,默认为KEZHUANG
 *
 */
public class DensityUtils {

		public static boolean DEBUG = false;
		public static String TAG = "DensityUtils";


		// dp转px
		public static int dp2px(Context context, float dpVal) {
				int result = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());

				return result;
		}


		// sp转px
		public static int sp2px(Context context, float spVal) {
				int result = (int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
				return result;
		}


		//px转dp
		public static int px2dp(Context context, float pxVal) {
				final float scale = context.getResources().getDisplayMetrics().density;
				int result = (int) (pxVal / scale);
				return result;
		}


		// px转sp
		public static float px2sp(Context context, float pxVal) {
				int result = (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
				return result;
		}

}
