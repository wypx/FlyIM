package me.smart.flyme.activity.systerm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.smart.flyme.R;
import me.smart.flyme.myapp.MyApp;
import me.smart.flyme.socket.Command;
import me.smart.flyme.socket.FileInfo;
import me.smart.flyme.utils.image.MulitPointTouchListener;
import me.smart.flyme.view.wiget.TitleCustomBarView;

/**
 * author：wypx on 2015/12/26 22:29
 * blog：smarting.me
 */
public class RemoteScreenActivity extends AppCompatActivity
{
		private ImageView tab4_img_screen ;
		private Bitmap bmp;
		private String SdPath;//SD卡的路径
		private byte imgdata[];
		private TitleCustomBarView titleCustomBarView_desk;

		private Handler handler=new Handler()
		{
				public void handleMessage(android.os.Message msg)
				{
						if(msg.what==1)
						{
								bmp = BitmapFactory.decodeByteArray(imgdata, 0, imgdata.length);
								tab4_img_screen.setImageBitmap(bmp);
						}
				};
		};
		@Override
		public void onCreate(Bundle savedInstanceState )
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.systerm_remote_screen);
				tab4_img_screen = (ImageView)findViewById(R.id.tab4_img_screen);
				titleCustomBarView_desk = (TitleCustomBarView)findViewById(R.id.toorbar_sys_screen);
				titleCustomBarView_desk.setTitleCustomClicker(titleCustomClicker);
				titleCustomBarView_desk.setDisplaySub1(0, true);
				titleCustomBarView_desk.setDisplaySub2(0, true);
				titleCustomBarView_desk.setTitle("远程桌面");
				titleCustomBarView_desk.setActivity(this);

				//图片缩放
				tab4_img_screen.setOnTouchListener(new MulitPointTouchListener());
				SdPath= Environment.getExternalStorageDirectory().getAbsolutePath();

		}
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
				if (keyCode == KeyEvent.KEYCODE_BACK)
				{


//	 		  try {
//	 			// MySocket.getInstance().close();
//	 		} catch (IOException e) {
//	 			// TODO Auto-generated catch block
//	 			e.printStackTrace();
//	 		}
						finish();
						return false;
//	 	   }
//	      else
//	        {

						//     return super.onKeyDown(keyCode, event);
				}
				return false;
		}


		private TitleCustomBarView.TitleCustomClicker titleCustomClicker = new TitleCustomBarView.TitleCustomClicker()
		{
				@Override
				public void doSub2Click()
				{
						try
						{
								String filename="Screen.jpg";
								File mfile=new File(SdPath+filename);//文件保存路径
								FileOutputStream fileout=new FileOutputStream(mfile);

								fileout.write(imgdata,0,imgdata.length);
								Log.i("保存完成--->>", "complete！");
						}
						catch (FileNotFoundException e)
						{
								e.printStackTrace();
						}
						catch (IOException e)
						{
								e.printStackTrace();
						}


				}
				@Override
				public void doSub1Click()
				{
						new Thread(new Runnable()
						{
								@SuppressWarnings("unused")
								@Override
								public void run()
								{
										try
										{
												// Thread.sleep(300);
												//截图功能  --发送命令
												Command commd=new Command(10,"GetFileLen");
												MyApp.getSockInstance().getOutputStream().write(commd.getByteArrayData());


												byte [] inbyte=new byte[1028];;
												byte [] filebyte=new byte[314];
												MyApp.getSockInstance().getInputStream().read(inbyte);

												Command cmd=new Command(inbyte);
												Log.i("ID-->>", "command ID:"+cmd.getID());

												System.arraycopy(inbyte,4, filebyte,0,314);
												FileInfo fileinfo=new FileInfo(filebyte);
												// 接收操作
												String filename=fileinfo.getFilename();//文件名
												int filelen=fileinfo.getFilelen();//文件长度
												//int filelen=212595;
												Log.i("filelen-->", "文件长度:"+filelen);
												Log.i("filename-->", "文件名:"+filename);
												Log.i("filetime-->", "文件时间:"+fileinfo.getTime());


//				File mfile=new File(SdPath+filename);//文件保存路径
//		    	FileOutputStream fileout=new FileOutputStream(mfile);
////				if( !mfile.exists()){	//若不存在
////					mfile.mkdir();
////				}
////				//小文件
//				byte data[]=new byte[filelen];
//				int nleft=filelen;
//				int idx=0;
//				int ret=0;
//				while(nleft>0)
//				{
//				ret=0;
//				ret=sock_desktop.getInputStream().read(data,idx,nleft);
//
//				nleft-=ret;
//				idx+=ret;
//				Log.i("接收多少-->", "比例:"+(idx/filelen));
//				}
//
//				fileout.write(data,0,idx);
//				Log.i("接收完成--->>", "complete！");
//
//				Message msg=Message.obtain();
//				msg.what=is_end;
//				msg.obj=data;
//				handler.sendMessage(msg);
												//
//				fileout.flush();
//				fileout.close();
												imgdata = new byte[filelen];
												int index=0;
												while (filelen > 0)
												{
														System.out.println("aaaa");
														byte[] buf = recvMsg(filelen);
														int currlen = buf.length;
														filelen = filelen - currlen;
														System.arraycopy(buf, 0, imgdata, filelen, buf.length);
														index += buf.length;
														System.out.println("curr len =" + currlen + "len = " + filelen);
														Log.i("Tag", "curr len =" + currlen +"  "+ "len = " + filelen);
												}
												Message msg=Message.obtain();
												msg.what=1;
												msg.obj=imgdata;
												handler.sendMessage(msg);
										}
										catch (IOException e)
										{
												e.printStackTrace();
										}

								}
						}).start();

				}

				@Override
				public void doMoreClick()
				{


				}

				@Override
				public void doBackClick()
				{
						finish();

				}
		};


		///////////////////////
		public byte[] recvMsg(int needrecvlen)
		{
				int len;
				int recvlen = needrecvlen;
				int index = 0;
				byte [] buf = new byte[needrecvlen];
				byte [] buff = new byte[needrecvlen];
				try
				{
						InputStream in = MyApp.getSockInstance().getInputStream();
						try
						{
								while(recvlen != 0)
								{
										len = in.read(buf, 0, recvlen);
										if(len > 0)
										{
												recvlen -= len;
												System.arraycopy(buf, 0, buff, index, len);
												index += len;
										}
								}
								return buff;
						}
						catch(IOException e)
						{
								e.printStackTrace();
						}

				}
				catch(IOException e)
				{
						e.printStackTrace();
				}

				return null;
		}


}