package me.smart.flyme.activity.phone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.smart.flyme.R;
import me.smart.flyme.bean.view.WheelIndicatorItem;
import me.smart.flyme.view.wiget.TitleCustomBarView;
import me.smart.flyme.view.wiget.WheelIndicatorView;

/**
 * author：wypx on 2015/12/26 15:00
 * blog：smarting.me
 */
public class PhoneStorageActivity extends AppCompatActivity
{
		private TitleCustomBarView toorbar_sd_info;
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.phone_storage_info);

				toorbar_sd_info = (TitleCustomBarView)findViewById(R.id.toorbar_sd_info);
				toorbar_sd_info.setTitleCustomClicker(titleCustomClicker);
				toorbar_sd_info.setTitle("外置储存卡");
				toorbar_sd_info.setActivity(this);

				WheelIndicatorView wheelIndicatorView = (WheelIndicatorView) findViewById(R.id.wheel_indicator_view);


				float dailyKmsTarget = 4.0f;// 总的
				float totalKmsDone = 3.0f; // 占用
				int percentageOfExerciseDone = (int) (totalKmsDone/dailyKmsTarget * 100); //

				//设置下面元素所占的总比例75%
				wheelIndicatorView.setFilledPercent(percentageOfExerciseDone);

				WheelIndicatorItem bikeActivityIndicatorItem = new WheelIndicatorItem(1.8f, Color.parseColor("#ff9000"));
				WheelIndicatorItem walkingActivityIndicatorItem = new WheelIndicatorItem(0.9f, Color.argb(255, 194, 30, 92));
				WheelIndicatorItem runningActivityIndicatorItem = new WheelIndicatorItem(0.3f, Color.parseColor("#4cb5ab"));

				wheelIndicatorView.addWheelIndicatorItem(bikeActivityIndicatorItem);
				wheelIndicatorView.addWheelIndicatorItem(walkingActivityIndicatorItem);
				wheelIndicatorView.addWheelIndicatorItem(runningActivityIndicatorItem);

				// Or you can add it as
				//wheelIndicatorView.setWheelIndicatorItems(Arrays.asList(runningActivityIndicatorItem,walkingActivityIndicatorItem,bikeActivityIndicatorItem));

				wheelIndicatorView.startItemsAnimation(); // Animate!
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
