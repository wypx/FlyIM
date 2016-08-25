package me.smart.flyme.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation
{
		//文件夹复制，包括文件夹里面的文件复制
		public static void copyDir(File file, File plasPath)
		{
				if(null == plasPath)
				{
						plasPath.mkdir();
				}
				File[] f=file.listFiles();
				//递归实现
				for(File newFile:f)
				{
						if(newFile.isDirectory())
						{
								File files=new File(file.getPath()+"/"+newFile.getName()) ;
								File plasPaths=new File(plasPath.getPath()+"/"+newFile.getName());
								copyDir(files, plasPaths);
						}
						else
						{
								String newPath=plasPath.getPath()+"/"+newFile.getName();
								File newPlasFile=new File(newPath);
								copyFile(newFile, newPlasFile);
						}
				}

		}

		// 复制文件
		public static void copyFile(File file, File plasPath)
		{
				try
				{
						FileInputStream fileInput = new FileInputStream(file);
						BufferedInputStream inBuff=new BufferedInputStream(fileInput);
						FileOutputStream fileOutput=new FileOutputStream(plasPath);
						BufferedOutputStream outBuff=new BufferedOutputStream(fileOutput);
						byte[] b=new byte[1025 * 5];
						int len;
						while((len=inBuff.read(b))!= -1)
						{
								outBuff.write(b, 0, len);
						}
						outBuff.flush();
						inBuff.close();
						outBuff.close();
						fileOutput.close();
						fileInput.close();
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}
		}

		//删除文件夹的方法（删除该文件夹下的所有文件）
		public static void deleteFolder(File folder)
		{
				File[] fileArray = folder.listFiles();
				//文件空并且文件的父目录是/sdcard
				if(fileArray.length == 0 && folder.getParent().equals("/sdcard") )
				{
						//空文件夹则直接删除
						folder.delete();
				}
				else
				{
						for(File currentFile:fileArray)
						{
								//遍历该目录
								if(currentFile.exists()&&currentFile.isFile())
								{
										//文件则直接删除
										currentFile.delete();
								}
								else
								{
										deleteFolder(currentFile);//回调
								}
						}
						folder.delete();
				}
		}
}









