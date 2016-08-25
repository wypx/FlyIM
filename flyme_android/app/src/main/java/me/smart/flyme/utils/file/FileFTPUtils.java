package me.smart.flyme.utils.file;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * author：wypx on 2015/12/11 22:17
 * blog：smarting.me
 */
public class FileFTPUtils {
		public static int CATEGORY_TAB_INDEX = 0;
		public static int SDCARD_TAB_INDEX = 1;

		public static String sZipFileMimeType = "application/zip";

		/**
		 * 文档类型
		 */
		public static HashSet<String> sDocMimeTypesSet = new HashSet<String>() {
				{
						add("text/plain");
						add("text/plain");
						add("application/pdf");
						add("application/zip");
						add("application/msword");
						add("application/vnd.ms-excel");
						add("application/vnd.ms-excel");
				}
		};

		// 格式化时间
		public static String formatDateString(Context context, long time) {
				DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
				DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
				Date date = new Date(time);
				return dateFormat.format(date) + " " + timeFormat.format(date);
		}

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
		public static SDCardInfo getSDCardInfo()
		{
				String sDcString = android.os.Environment.getExternalStorageState();

				if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED))
				{
						File pathFile = android.os.Environment.getExternalStorageDirectory();

						try
						{
								android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

								long nTotalBlocks = statfs.getBlockCountLong();

								long nBlocSize = statfs.getBlockSizeLong();

								long nAvailaBlock = statfs.getAvailableBlocksLong();

								long nFreeBlock = statfs.getFreeBlocksLong();

								SDCardInfo info = new SDCardInfo();

								info.total = nTotalBlocks * nBlocSize;

								info.free = nAvailaBlock * nBlocSize;

								return info;
						}
						catch (IllegalArgumentException e)
						{
								Log.e("", e.toString());
						}
				}
				return null;
		}




		// 分离数字 comma separated number
		public static String convertNumber(long number)
		{
				return String.format("%,d", number);
		}

		// 字节转化大小storage, G M K B
		public static String convertStorage(long size)
		{
				long kb = 1024;
				long mb = kb * 1024;
				long gb = mb * 1024;

				if (size >= gb)
				{
						return String.format("%.1f GB", (float) size / gb);
				}
				else if (size >= mb)
				{
						float f = (float) size / mb;
						return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
				}
				else if (size >= kb)
				{
						float f = (float) size / kb;
						return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
				}
				else
				{
						return String.format("%d B", size);
				}
		}



}

