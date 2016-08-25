package me.smart.flyme.activity.systerm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.IOException;

import me.smart.flyme.R;
import me.smart.flyme.myapp.MyApp;
import me.smart.flyme.socket.Command;

/**
 * author：wypx on 2015/12/26 22:08
 * blog：smarting.me
 */
public class RemoteControlActivity extends AppCompatActivity implements View.OnClickListener
{

		private Button btn_left;
		private Button btn_right;
		private ImageButton windows;
		private Button esc;
		private Button delete;
		private LinearLayout MouseMove_field;

		// 初始值的x，y坐标
		private float initX, initY;
		// 移动后的x，y坐标
		private float disX, disY;
		// 抬起的x,y坐标
		private float upX, upY;

		private LinearLayout top_back;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.systerm_pc_control);
				//按钮等初始化
				InitView();

				MouseMove_field= (LinearLayout)this.findViewById(R.id.Move_field);

				MouseMove_field.setOnTouchListener(new View.OnTouchListener()
				{

						@SuppressWarnings("unused")
						@Override
						public boolean onTouch(View v, MotionEvent event)
						{
								//判断移动 方式
								int action = event.getAction();
								switch (action)
								{
										//屏幕按下
										case MotionEvent.ACTION_DOWN:
												initX = event.getX();
												initY = event.getY();


												// 抬起记录按下的x,y
												upX = event.getX();
												upY = event.getY();
												break;
										case MotionEvent.ACTION_MOVE:
												// 获取手移动后的坐标
												disX = event.getX() - initX;
												disY = event.getY() - initY;


												String X = Integer.toString((int)disX);
												String Y = Integer.toString((int)disY);
												final String point=X+","+Y;
												//判断 XY是否为空     if (disX != 0 || disY != 0)
												new Thread()
												{

														public void run()
														{
																//操作案例  移动距离问题
																Command commd2=new Command(21,point);
																try
																{
																		MyApp.getSockInstance().getOutputStream().write(commd2.getByteArrayData());
																}
																catch (IOException e)
																{
																		e.printStackTrace();
																}
														};

												}.start();


												// 实时更新开始坐标

												initX = event.getX();
												initY = event.getY();
												break;

										case MotionEvent.ACTION_UP:
												// 如果没有移动过
												if ((event.getX() - upX) == 0 && (event.getY() - upY) == 0)
												{

												}
												//测试
												//触屏压力
												float pressure=event.getPressure();
												//触点尺寸
												float size=event.getSize();
												//绝对坐标信息
												float RawX=event.getRawX();
												float RawY=event.getRawY();
												//获取弹起事件的坐标
												break;
								}

								return true;
						}
				});

		}


		private void InitView()
		{
				top_back=(LinearLayout)this.findViewById(R.id.top_back);
				windows=(ImageButton)this.findViewById(R.id.windows);
				esc=(Button)this.findViewById(R.id.esc);
				delete=(Button)this.findViewById(R.id.delete);
				btn_left=(Button)this.findViewById(R.id.btn_left);
				btn_right=(Button)this.findViewById(R.id.btn_right);
				btn_left.setOnClickListener(this);
				btn_right.setOnClickListener(this);
				windows.setOnClickListener(this);
				esc.setOnClickListener(this);
				delete.setOnClickListener(this);
				top_back.setOnClickListener(this);
		}

		@Override
		public void onClick(View v)
		{
				switch (v.getId()) {
						case R.id.btn_left:
								new Thread()
								{

										public void run()
										{
												//操作案例--左键双击或者单击
												Command commd=new Command(19,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd.getByteArrayData());
														Thread.sleep(300);
												} catch (IOException e) {
														e.printStackTrace();
												} catch (InterruptedException e) {

														e.printStackTrace();
												}

										}
								}.start();

								break;

						case R.id.btn_right:
								new Thread()
								{

										public void run()
										{
												//操作案例  右键单击
												Command commd1=new Command(20,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd1.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}
												//把sock传递给ThreadRead构造函数，用来读取命令和接收信息
												try {
														Thread.sleep(300);
												} catch (InterruptedException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;
						case R.id.windows:
								new Thread()
								{

										public void run()
										{
												//操作案例  右键单击
												Command commd7=new Command(22,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd7.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;
						case R.id.esc:
								new Thread()
								{

										public void run()
										{
												//操作案例
												Command commd8=new Command(23,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd8.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;
						case R.id.delete:
								new Thread()
								{

										public void run()
										{
												//操作案例
												Command commd9=new Command(24,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd9.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;

						case R.id.top_back:
								//返回
								finish();
								break;
						default:
								break;
				}

		}



//		@SuppressWarnings("unused")
//		public static String getDisplayMetrics(Context cx)
//		{
//				String str = "";
//				DisplayMetrics dm = new DisplayMetrics();
//				dm = cx.getApplicationContext().getResources().getDisplayMetrics();
//				int screenWidth = dm.widthPixels;
//				int screenHeight = dm.heightPixels;
//				float density = dm.density;
//				float xdpi = dm.xdpi;
//				float ydpi = dm.ydpi;
//
//				//String.valueOf(screenWidth)   int -->String
//				return null;
//
//
//		}

}
/*
 * 鼠标移动模块
     这个模块主要是控制电脑鼠标的移动，
     因为有了这个功能人们在配置鼠标的左右按键功能的时候可以更好的控制电脑。
     对于鼠标的控制主要是利用手机的触摸屏幕来实现的，对触摸屏幕有三种事件，
     即DOWN、MOVE和UP事件。可以充分利用MOVE事件来获取手指在手机屏幕的坐标，
     然后发送到服务端即可。控制电脑鼠标的移动过程有两种方式，
     一种是进行手机屏幕和电脑屏幕的静态映射，
     即由手机的位置乘上电脑屏幕相对手机屏幕的倍数就可进行映射，
     这种方式的缺点在于移动连续性不是很好，
     在完成一次移动之后重新移动则无法在原来的基础上移动
另一种映射方式是根据手机屏幕的范围和用户移动的习惯，
将手机屏幕映射到以当前位置为中心的一个矩形范围，
这样可以解决移动连续性的问题，具体实现是在第一种方式的基础上再形成一种坐标映射。
即首先使用第一种方式来获知实际的电脑鼠标相对电脑屏幕的位置，然后计算矩形范围进行第二次映射，最后转换到实际的坐标发送到电脑进行执行就可以了。
*/
