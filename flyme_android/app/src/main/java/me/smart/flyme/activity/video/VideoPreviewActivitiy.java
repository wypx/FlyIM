package me.smart.flyme.activity.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import me.smart.flyme.R;
import me.smart.flyme.view.wiget.CustomSurfaceView;

/**
 * author：wypx on 2015/12/27 17:43
 * blog：smarting.me
 */
public class VideoPreviewActivitiy extends AppCompatActivity
{

		private CustomSurfaceView customSurfaceView;


		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				//禁止屏幕休眠
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
								WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				// 设置全屏
//     	getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
//     	getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_video_preview);
				customSurfaceView = (CustomSurfaceView)findViewById(R.id.surface_priview);
		}

}
