package me.smart.mylibrary.utils.systerm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;

import java.io.File;

/**
 * @ClassName: SystemInfoUtils 
 * @Description: TODO(系统信息--SD卡信息，手机内存信息等) 
 * @author amor smarting.me
 * @date 2015-10-30 下午10:14:02 
 *  
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@SuppressLint("NewApi")
public class SystemInfoUtils 
{
	//外部存储是否可用 (存在且具有读写权限) 
	public static boolean isExternalStorageAvailable() 
	{  
	    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}  

    // 获取手机内部可用空间大小 
    public static long getAvailableInternalMemorySize() 
    {  
        File path = Environment.getDataDirectory();  
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();  
        long availableBlocks = stat.getAvailableBlocksLong();  
        return availableBlocks * blockSize;  
    }  

    //获取手机内部空间总大小
    public static  long getTotalInternalMemorySize() 
    {
        File path = Environment.getDataDirectory();//Gets the Android data directory
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();      //每个block 占字节数
        long totalBlocks = stat.getBlockCountLong();   //block总数
        return totalBlocks * blockSize;
    }

    //获取sd卡可用空间大小 
    public static long getAvailableExternalMemorySize() 
    {  
        if (isExternalStorageAvailable()) 
        {  
            File path = Environment.getExternalStorageDirectory();//获取SDCard根目录  

            StatFs stat = new StatFs(path.getPath());  

            long blockSize = stat.getBlockSizeLong();  

            long availableBlocks = stat.getAvailableBlocksLong();  

            return availableBlocks * blockSize;  
        } 
        else 
        {  
            return -1;  
        }  

    }  

    //获取sd卡可用空间总大小 
    public static long getTotalExternalMemorySize() 
    {  
       if (isExternalStorageAvailable()) 
       {  

           File path = Environment.getExternalStorageDirectory(); //获取SDCard根目录  

           StatFs stat = new StatFs(path.getPath());  

           long blockSize = stat.getBlockSizeLong();  

           long totalBlocks = stat.getBlockCountLong(); 
           
           return totalBlocks * blockSize;  
       } else 
       {  
           return -1;  
       }  
   }  

    //震动功能 
    public static void Vibrate(final Context context)
    { 
       long milliseconds = 100;
       Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
       vib.vibrate(milliseconds);   
    } 
    //震动功能  long milliseconds : 震动的时长，单位是毫秒
    public static void Vibrate(final Context context, long milliseconds) 
    {   
       Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);   
       vib.vibrate(milliseconds);   
    } 
    //long[] pattern :自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长等]
    //boolean isRepeat : 是否反复震动
    public static void Vibrate(final Context context, long[] pattern,boolean isRepeat) 
    {   
       Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);   
       vib.vibrate(pattern, isRepeat ? 1 : -1);//-1为反复震动   
    }   
}


/*
 
	使用SDcard进行读写的时候 会用到Environment类下面的几个静态方法:
 
  	1:getDataDirectory()  				获取到Android中的data数据目录
	2:getDownloadCacheDirectory() 		获取到下载的缓存目录
	3:getExternalStorageDirectory()     获取到外部存储的目录 一般指SDcard
	4:getExternalStorageState()         获取外部设置的当前状态 一般指SDcard,

	android系统中对于外部设置的状态，比较常用的是 MEDIA_MOUNTED（SDcard存在且可以进行读写）
	MEDIA_MOUNTED_READ_ONLY (SDcard存在,只可以进行读操作) 当然还有其他的一些状态,可以在文档中进行查找到
	
	5:getRootDirectory()                获取到Android Root路径
	6:isExternalStorageEmulated()    	返回Boolean值判断外部设置是否有效
	7:isExternalStorageRemovable() 		返回Boolean值，判断外部设置是否可以移除
*/

