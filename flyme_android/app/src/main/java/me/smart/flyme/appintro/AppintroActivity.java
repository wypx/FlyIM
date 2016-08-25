package me.smart.flyme.appintro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

import me.smart.flyme.R;
import me.smart.flyme.activity.main.MainActivity;
import me.smart.flyme.utils.ScreenUtils;

public class AppintroActivity extends FragmentActivity
{
		private boolean isSliderAnimation = false;
		private EdgeEffectCompat leftEdge;//左边界
		private EdgeEffectCompat rightEdge;//右边界

		private static final int[] resource = new int[]
		{
						R.mipmap.guide_1, R.mipmap.guide_2,
						R.mipmap.guide_3, R.mipmap.guide_2
		};
		private static final String TAG = AppintroActivity.class.getSimpleName();

		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_appintro);
				ScreenUtils.setScreenTransParent(this);

				MyFragmentStatePager adpter = new MyFragmentStatePager(getSupportFragmentManager());
				ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
				ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
				viewPager.setAdapter(adpter);


//				CirclePageIndicator mIndicator  = (CirclePageIndicator) findViewById(R.id.indicator);
//				mIndicator.setViewPager(viewPager);
				/*
         * 拿到ViewPager的边界条EdgeEffectCompat,判断是否到了边界(获取EdgeEffectCompat通过反射)
         */
				try
				{
						Field leftEdgeField = viewPager.getClass().getDeclaredField("mLeftEdge");
						Field rightEdgeField = viewPager.getClass().getDeclaredField("mRightEdge");
						if (leftEdgeField != null && rightEdgeField != null)
						{
								leftEdgeField.setAccessible(true);
								rightEdgeField.setAccessible(true);
								leftEdge = (EdgeEffectCompat) leftEdgeField.get(viewPager);
								rightEdge = (EdgeEffectCompat) rightEdgeField.get(viewPager);
						}
				}
				catch (Exception e)
				{
						e.printStackTrace();
				}
				/**
				 *  首先，你必须在 设置 Viewpager的 adapter 之后在调用这个方法
				 *  第二点，setmViewPager(ViewPager mViewPager,Object obj, int count, int... colors)
				 *    第一个参数 是 你需要传人的 viewpager
				 *    第二个参数 是 一个实现了ColorAnimationView.OnPageChangeListener接口的Object,用来实现回调
				 *    第三个参数 是 viewpager 的 孩子数量
				 *    第四个参数 int... colors,你需要设置的颜色变化值~~ 如何你传空,那么触发默认设置的颜色动画
				 * */


				colorAnimationView.setmViewPager(viewPager, resource.length);
				colorAnimationView.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
				{
						@Override
						public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
						{
								//禁止显示黄色或者蓝色的渐变图片的解决方法
								if (leftEdge != null && rightEdge != null)
								{
										leftEdge.finish();
										// rightEdge.finish();
										leftEdge.setSize(0, 0);
										//rightEdge.setSize(0, 0);
								}
								//Log.e("TAG","onPageScrolled");
						}

						@Override
						public void onPageSelected(int position)
						{
						//		Log.e("TAG","onPageSelected");
						}

						@Override
						public void onPageScrollStateChanged(int state)
						{
								if(rightEdge!=null&&!rightEdge.isFinished())
								{
										//到了最后一张并且还继续拖动，出现蓝色限制边条了
										startActivity(new Intent(AppintroActivity.this, MainActivity.class));
										finish();
								}
							//	Log.e("TAG","onPageScrollStateChanged");
						}
				});
				// Four : Also ,you can call this method like this:
				// colorAnimationView.setmViewPager(viewPager,this,resource.length,0xffFF8080,0xff8080FF,0xffffffff,0xff80ff80);
		}


		public class MyFragmentStatePager extends FragmentStatePagerAdapter
		{

				public MyFragmentStatePager(FragmentManager fm)
				{
						super(fm);
				}

				@Override public Fragment getItem(int position)
				{
						return new MyFragment(position);
				}

				@Override public int getCount()
				{
						return resource.length;
				}
		}
		@SuppressLint("ValidFragment")
		public class MyFragment extends Fragment
		{
				private int position;

				public MyFragment(int position)
				{
						this.position = position;
				}

				@Override
				public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
				{
						ImageView imageView = new ImageView(getActivity());
						imageView.setImageResource(resource[position]);
						return imageView;
				}
		}
}

