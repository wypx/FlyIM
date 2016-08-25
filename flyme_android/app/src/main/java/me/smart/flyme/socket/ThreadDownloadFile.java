package me.smart.flyme.socket;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

//用于接收文件，也就是从服务器下载文件
@SuppressLint("SdCardPath")
public class ThreadDownloadFile extends Thread {

	
	String filename;
	int filelen;
	FileOutputStream fileout;
	Socket sock;
	public ThreadDownloadFile(Socket sock, String filename, int filelen)
	{
		this.sock=sock;
		this.filename="/sdcard/"+filename;
		this.filelen=filelen;
	}
	
	
	@Override
	public void run() {
		
		try {
			
			//数据转化类
			@SuppressWarnings("unused")
			DataTransform mtransform=new DataTransform();
			File mfile=new File(filename);
			fileout=new FileOutputStream(mfile);
			
			//小文件
			byte data[]=new byte[filelen];
			int nleft=filelen;
			int idx=0;
			int ret=0;
			while(nleft>0)
			{
				ret=0;
				ret=sock.getInputStream().read(data,idx,nleft);
				nleft-=ret;
				idx+=ret;
			}
			fileout.write(data,0,idx);
			fileout.flush();
			Log.i("recevie count--", "---"+nleft);
			
		} catch (FileNotFoundException e) {
				//处理File相关的错误
			e.printStackTrace();
		} catch (IOException e) {
			// 处理getInputStream错误
			e.printStackTrace();
		}
			try {
			fileout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.run();
	}
}
