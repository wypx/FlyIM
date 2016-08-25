package me.smart.flyme.utils.file;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;

import me.smart.flyme.R;

public class FileAttributeInfo
{
		private String spaces = null;
		private double space  = 0;
		private double count  = 0;

		//弹出文件属性对话框
		public void attribute(Context context, File file) throws IOException
		{
				LayoutInflater inflater=LayoutInflater.from(context);
				View atView=(View) inflater.inflate(R.layout.base_file_attribute_dilog, null);
				AppCompatTextView atText1=(AppCompatTextView) atView.findViewById(R.id.attribute_name);
				AppCompatTextView atText2=(AppCompatTextView) atView.findViewById(R.id.attribute_space);
				AppCompatTextView atText3=(AppCompatTextView) atView.findViewById(R.id.attribute_date);
				String Sum=null;
				if(file.isFile())
				{
						attFile(file);
						Sum=spaces;
				}
				else
				{
						attDir(file);
						if(count>1048576)
						{
								Sum=String.valueOf(count/1048576).substring(0,
												String.valueOf(count/1048576).lastIndexOf(".")+4)+"MB";
						}
						else
						{
								Sum=String.valueOf(count/1024).substring(0,
												String.valueOf(count/1024).lastIndexOf(".")+2)+"KB";
						}
				}
				//设置文件名
				atText1.setText(file.getName().toString());
				//设置文件大小
				atText2.setText(Sum);

				String date=String.valueOf(new Timestamp(file.lastModified()));
				String mydate=date.substring(0, date.lastIndexOf(":"));
				atText3.setText(mydate);
				new AlertDialog.Builder(context)
								.setTitle("                文件属性")
								.setView(atView)
								.setPositiveButton("确定", new DialogInterface.OnClickListener()
								{

										public void onClick(DialogInterface arg0, int arg1)
										{


										}
								}).show();
		}
		//获取文件夹
		private void attDir(File file) throws IOException
		{
				File[] liFile=file.listFiles();
				for(File nFile:liFile)
				{
						if(nFile.isFile())
						{
								attFile(nFile);
								count+=space;
						}
						else
						{
								attDir(nFile);
						}
				}
		}
		//获取文件的大小
		private void attFile(File file) throws IOException
		{
				FileInputStream fi=new FileInputStream(file);
				space= fi.available();
				if(space > 1048576)
				{
						spaces=String.valueOf(space/1048576).substring(0,
										String.valueOf(space/1048576).lastIndexOf(".")+4)+"MB";
				}
				else
				{
						spaces=String.valueOf(space/1024).substring(0,
										String.valueOf(space/1024).lastIndexOf(".")+2)+"KB";
				}
		}


		//转化大小 storage, G M K B
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
