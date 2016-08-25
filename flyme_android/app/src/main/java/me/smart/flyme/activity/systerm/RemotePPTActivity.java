package me.smart.flyme.activity.systerm;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import java.io.IOException;

import me.smart.flyme.R;
import me.smart.flyme.myapp.MyApp;
import me.smart.flyme.socket.Command;
import me.smart.flyme.utils.image.DrawableUtils;
import me.smart.flyme.view.wiget.TitleCustomBarView;

/**
 * author：wypx on 2015/12/26 22:39
 * blog：smarting.me
 */
public class RemotePPTActivity extends AppCompatActivity implements View.OnClickListener
{
		private AppCompatButton start_now;
		private AppCompatButton escape;
		private AppCompatButton forward;
		private AppCompatButton back;
		private AppCompatButton start_page1;
		private TitleCustomBarView toolbar_ppt;

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.systerm_remote_ppt);
				toolbar_ppt = (TitleCustomBarView)findViewById(R.id.toolbar_ppt);
				toolbar_ppt.setTitleCustomClicker(titleCustomClicker);
				toolbar_ppt.setTitle("遥控PPT");
				toolbar_ppt.setActivity(this);

				//ID获取
				start_now = (AppCompatButton) this.findViewById(R.id.start_now_page);
				start_page1=(AppCompatButton)this.findViewById(R.id.start_from_page1);
				escape = (AppCompatButton) this.findViewById(R.id.escape);
				forward = (AppCompatButton) this.findViewById(R.id.froward);
				back = (AppCompatButton) this.findViewById(R.id.back);

				start_now.setBackground(DrawableUtils.getBtBack(this,R.mipmap.ppt_pause,R.mipmap.ppt_pause_pressed));
				start_page1.setBackground(DrawableUtils.getBtBack(this,R.mipmap.ppt_play,R.mipmap.ppt_play_pressed));
				escape.setBackground(DrawableUtils.getBtBack(this,R.mipmap.ppt_exit,R.mipmap.ppt_exit_pressed));
				back.setBackground(DrawableUtils.getBtBack(this,R.mipmap.ppt_forwad,R.mipmap.ppt_forward_pressed));
				forward.setBackground(DrawableUtils.getBtBack(this,R.mipmap.ppt_next,R.mipmap.ppt_next_pressed));

				start_now.setOnClickListener(this);
				start_page1.setOnClickListener(this);
				escape.setOnClickListener(this);
				forward.setOnClickListener(this);
				back.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId())
				{
						case R.id.start_now_page:
								new Thread()
								{

										public void run()
										{
												//操作案例
												Command commd1=new Command(28,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd1.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;
						case R.id.start_from_page1:
								new Thread()
								{

										public void run()
										{
												//操作案例
												Command commd2=new Command(29,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd2.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;
						case R.id.escape:
								new Thread()
								{

										public void run()
										{
												//操作案例
												Command commd3=new Command(26,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd3.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}

										}
								}.start();

								break;
						case R.id.froward:
								new Thread()
								{
										public void run()
										{
												//操作案例
												Command commd4=new Command(25,"C:\0");
												try {
														MyApp.getSockInstance().getOutputStream().write(commd4.getByteArrayData());
												} catch (IOException e) {
														e.printStackTrace();
												}
										}

								}.start();

								break;
						case R.id.back:
								new Thread()
								{

										public void run()
										{
												//操作案例
												Command commd3=new Command(26,"C:\0");
												try
												{
														MyApp.getSockInstance().getOutputStream().write(commd3.getByteArrayData());
												}
												catch (IOException e)
												{
														e.printStackTrace();
												}

										}
								}.start();

								break;
						default:
								break;

				}
		}


		private TitleCustomBarView.TitleCustomClicker titleCustomClicker = new TitleCustomBarView.TitleCustomClicker()
		{

				@Override
				public void doSub2Click()
				{


				}

				@Override
				public void doSub1Click()
				{

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


}


