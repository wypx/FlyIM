package me.smart.flyme.utils.file;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*

		获取手机中外置内存卡、内置内存卡、手机内存路径。
		思路是：先用 Environment.getExternalStorageDirectory()获得外部存储卡路径
		（某些机型也表现为内部存储卡路径），如没有获 取到有效sd卡插入，
		则使用安卓的配置文件system/etc/vold.fstab读取全部挂载信息，
		假如也没有可写入的sd卡，则使用 getFilesDir()方式获得当前应用所在存储路径。
		为适应不同手机的内存情况，先分三种情况获得可存储路径phonePicsPath，
 */
public class SDCardUtils
{
	public static boolean DEBUG = false;

	public static String TAG = "sdcard";

	/**
	 * 判断SDCard是否可用
	 */
	public static boolean isSDCardEnable()
	{
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡路径
	 */
	public static String getSDCardPath()
	{
				String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
				if(path !=null )
				{
						return path;
				}
				return null;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static long getSDCardAllSize()
	{
				if (isSDCardEnable())
				{
							StatFs stat = new StatFs(getSDCardPath());

							// 获取SDCard上BLOCK总数
							long nTotalBlocks = stat.getBlockCountLong();
						// 获取SDCard上每个block的SIZE

						// 获取可供程序使用的Block的数量
							long nBlocSize = stat.getBlockSizeLong();

						// 计算SDCard 总容量大小MB
						long TotalSize = nTotalBlocks * nBlocSize;

						// 获取可供程序使用的Block的数量
							long freeBlocks = stat.getAvailableBlocksLong();
						// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
							long nFreeBlock = stat.getFreeBlocksLong();

							long result = freeBlocks * freeBlocks;

							// 计算 SDCard 剩余大小MB


						Log.i("当前内存卡的容量:", ""+result);
						return result;
				}
				return 0;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
				String path = Environment.getRootDirectory().getAbsolutePath();
				Log.i("当前存储路径:", path);
				return path;
	}

		// storage, G M K B
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

		/**
		 * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
		 *
		 * @return
		 */
		public static ArrayList<String> getDevMountList() {
				String[] toSearch = FileUtils.readFile("/system/etc/vold.fstab").split(" ");
				ArrayList<String> out = new ArrayList<String>();
				for (int i = 0; i < toSearch.length; i++) {
						if (toSearch[i].contains("dev_mount")) {
								if (new File(toSearch[i + 2]).exists()) {
										out.add(toSearch[i + 2]);
								}
						}
				}
				return out;
		}


		/**
		 * 获取扩展SD卡存储目录
		 *
		 * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
		 * 否则：返回内置SD卡目录
		 *
		 * @return
		 */
		public static String getExternalSdCardPath() {

//				if (SDCardUtils.isMounted()) {
//						File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//						return sdCardFile.getAbsolutePath();
//				}

				String path = null;

				File sdCardFile = null;

				ArrayList<String> devMountList = getDevMountList();

				for (String devMount : devMountList) {
						File file = new File(devMount);

						if (file.isDirectory() && file.canWrite()) {
								path = file.getAbsolutePath();

								String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
								File testWritable = new File(path, "test_" + timeStamp);

								if (testWritable.mkdirs()) {
										testWritable.delete();
								} else {
										path = null;
								}
						}
				}

				if (path != null) {
						sdCardFile = new File(path);
						return sdCardFile.getAbsolutePath();
				}

				return null;
		}

}
