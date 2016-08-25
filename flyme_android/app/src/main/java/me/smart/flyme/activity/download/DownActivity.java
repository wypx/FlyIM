package me.smart.flyme.activity.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import me.smart.flyme.R;
import me.smart.flyme.view.wiget.ArrowDownloadButton;

/**
 * author：wypx on 2015/12/26 13:49
 * blog：smarting.me
 */
public class DownActivity  extends AppCompatActivity
{

		int count = 0;
		int progress = 0;
		ArrowDownloadButton button;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.base_file_download);
				button = (ArrowDownloadButton)this.findViewById(R.id.arrow_download_button);
				button.setOnClickListener(new View.OnClickListener()
				{
						@Override
						public void onClick(View v)
						{
								if ((count % 2) == 0)
								{
										button.startAnimating();
										Timer timer = new Timer();
										timer.schedule(new TimerTask()
										{
												@Override
												public void run()
												{
														runOnUiThread(new Runnable()
														{
																@Override
																public void run()
																{
																		progress = progress + 1;
																		button.setProgress(progress);
																}
														});
												}
										}, 800, 20);
								} else {
										button.reset();
								}
								count++;
						}
				});

		}
}
