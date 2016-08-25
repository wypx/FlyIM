package me.smart.flyme.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssertUtils
{
		public void getAssertFiles(Context context)
		{
				//is=context.getClass().getClassLoader().getAssets();

		}
		/*
		* 把Asserts目录的文件复制整个文件夹到SD卡下面
		*/
		public static void copyBigDataToSD(String strOutFileName,String srcstr,Context context) throws IOException
		{
				InputStream myInput;
				OutputStream myOutput = new FileOutputStream(strOutFileName);
				myInput = context.getAssets().open(srcstr);
				byte[] buffer = new byte[1024];
				int length = myInput.read(buffer);
				while(length > 0)
				{
						myOutput.write(buffer, 0, length);
						length = myInput.read(buffer);
				}

				myOutput.flush();
				myInput.close();
				myOutput.close();
		}

		/**
		 *  从assets目录中复制整个文件夹内容
		 *  @param  context  Context 使用CopyFiles类的Activity
		 *  @param  oldPath  String  原文件路径  如：/aa
		 *  @param  newPath  String  复制后路径  如：xx:/bb/cc
		 */
		public static void copyFilesFassets(Context context,String oldPath,String newPath)
		{
				Log.i("copy", "复制文件开始");
				try
				{
						//获取assets目录下的所有文件及目录名
						String fileNames[] = context.getAssets().list(oldPath);
						if (fileNames.length > 0)
						{
								//如果是目录
								File file = new File(newPath);
								file.mkdirs();//如果文件夹不存在，则递归
								for (String fileName : fileNames)
								{
										copyFilesFassets(context,oldPath + "/" + fileName,newPath+"/"+fileName);
								}
						}
						else
						{
								//如果是文件
								InputStream is = context.getAssets().open(oldPath);
								FileOutputStream fos = new FileOutputStream(new File(newPath));
								byte[] buffer = new byte[1024];
								int byteCount=0;
								while((byteCount = is.read(buffer)) != -1)
								{
										//循环从输入流读取 buffer字节
										fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
								}
								fos.flush();//刷新缓冲区
								is.close();
								fos.close();
						}
				}
				catch (Exception e)
				{
						e.printStackTrace();
						//如果捕捉到错误则通知UI线程
						//  MainActivity.handler.sendEmptyMessage(COPY_FALSE);
				}
		}


}