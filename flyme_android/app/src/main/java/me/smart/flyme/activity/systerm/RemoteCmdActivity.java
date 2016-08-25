package me.smart.flyme.activity.systerm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;

import me.smart.flyme.R;
import me.smart.flyme.myapp.MyApp;
import me.smart.flyme.socket.Command;
import me.smart.flyme.view.wiget.TitleCustomBarView;

/**
 * author：wypx on 2015/12/26 22:22
 * blog：smarting.me
 */
public class RemoteCmdActivity extends AppCompatActivity
{
		private AppCompatAutoCompleteTextView edit_cmd;
		private AppCompatButton btn_execute_cmd;
		private TextView tv_cmdresult;
		private Command mCommand;
		private ArrayAdapter<String> adapter;
		private TitleCustomBarView titleCustomBarView_cmd;
		private MyApp handerapp;
		private Handler mhHandler=new Handler()
		{
				@Override
				public void handleMessage(Message msg)
				{
						switch (msg.what)
						{
								case 1:
										tv_cmdresult.append("<-----------------------CMD回显消息----------------------->\n");
										tv_cmdresult.append("  "+mCommand.getLparam());
										break;

								default:
										break;
						}
				}
		};

		public void ShakeWindow(View v)
		{
				Animation shake = new TranslateAnimation(0, 5, 0, 0);
				shake.setInterpolator(new CycleInterpolator(5));
				shake.setDuration(300);
				v.startAnimation(shake);

		}
		@Override
		public void onCreate(Bundle savedInstanceState )
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.systerm_remote_cmd);

				edit_cmd=(AppCompatAutoCompleteTextView)findViewById(R.id.cmd);
				btn_execute_cmd=(AppCompatButton)findViewById(R.id.execute_cmd);

				titleCustomBarView_cmd = (TitleCustomBarView)findViewById(R.id.toolbar_cmd);
				titleCustomBarView_cmd.setTitleCustomClicker(titleCustomClicker);
				titleCustomBarView_cmd.setTitle("远程关机");
				titleCustomBarView_cmd.setActivity(this);


				handerapp=(MyApp)getApplication();
				handerapp.init();
				handerapp.addActivity(this);
				adapter = new ArrayAdapter<String>(this,
								android.R.layout.simple_dropdown_item_1line,Command.reomte_commnd);
				edit_cmd.setAdapter(adapter);

				tv_cmdresult=(TextView)this.findViewById(R.id.cmd_msg);

				btn_execute_cmd.setOnClickListener(new View.OnClickListener()
				{
						@Override
						public void onClick(View v)
						{
								new Thread(new Runnable()
								{
										@Override
										public void run()
										{
												//tv_cmdresult.setText(" ");

//				if(!TextUtils.isEmpty(edit_cmd.getText()))
												if(true)
												{

														//操作案例  右键单击
//
														String cmdline = edit_cmd.getText().toString().toLowerCase();
														if(cmdline!= null && !cmdline.isEmpty()){
																//String cmdline="ping www.amor.so";
																Command commd=new Command(31,cmdline);
																try {
																		MyApp.getSockInstance().getOutputStream().write(commd.getByteArrayData());
																} catch (IOException e) {
																		e.printStackTrace();

																}
														}
														else
														{
																//空的，抖动效果
																Animation anim = AnimationUtils.loadAnimation(
																				RemoteCmdActivity.this, R.anim.shake_anim);
																edit_cmd.startAnimation(anim);

														}




														//接收得到byte数组  byteArrayData[4+2048]
														byte []byteArrayData=new byte[1028];
														try {

																MyApp.getSockInstance().getInputStream().read(byteArrayData);
														} catch (IOException e1) {
																e1.printStackTrace();
														}
														//mCommand对象得到成员ID 和lparam的值
														mCommand = new Command(byteArrayData);
														Log.i("Cmd Msg-->", mCommand.getLparam());

														Message msg=Message.obtain();
														msg.what=1;
														mhHandler.sendMessage(msg);

												}}
								}).start();



						}

				});
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
