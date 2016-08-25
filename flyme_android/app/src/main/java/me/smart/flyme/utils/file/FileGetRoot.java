package me.smart.flyme.utils.file;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.DataOutputStream;
/**
 * 获取文件的Root权限
 */
public class FileGetRoot extends ContextWrapper
{
			public FileGetRoot(Context base)
			{
				 super(base);
			}

		  //应用程序的包文件获取权限
			public boolean getRoot()
			{
					//添加apk的权限，777 表示可读可写可操作。
					 String apkRoot = "chmod -R 777 " + getPackageCodePath();
					 return RootCommand(apkRoot);
			}

			//修改Root权限的方法
			public static boolean RootCommand (String command)
			{
		      Process process = null;
		      DataOutputStream os = null;
		      try
		      {
		          process = Runtime.getRuntime().exec("su");
		          os = new DataOutputStream(process.getOutputStream());
		          os.writeBytes(command + "\n");
		          os.writeBytes("exit\n");
		          os.flush();
		          process.waitFor();
		      }
		      catch (Exception e)
          {
              return false;
          }
          finally
		      {
		          try
		          {
		              if (os != null)
		              {
		                  os.close();
		              }
		              process.destroy();
		          }
		          catch (Exception e)
		          {
		              e.printStackTrace();
		          }
      }
      return true;
  }
	
}
	