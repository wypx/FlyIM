package me.smart.flyme.fragement.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import me.smart.flyme.R;
import me.smart.flyme.fragement.phone.LocalFileFragement;
import me.smart.flyme.fragement.phone.Tab2_Fragment_Three;
import me.smart.flyme.fragement.phone.Tab2_Fragment_Two;
import me.smart.mylibrary.livingtabs.LivingTabsLayout;
import me.smart.mylibrary.spinkit.SpinKitView;
import me.smart.mylibrary.spinkit.SpriteFactory;
import me.smart.mylibrary.spinkit.Style;
import me.smart.mylibrary.spinkit.sprite.Sprite;

@SuppressLint("InflateParams")
public class MainTab02 extends Fragment
{
		private LivingTabsLayout  tab2Layout;
		private View main_fg2;// 缓存Fragment view

		private boolean isInit; // 是否可以开始加载数据

		private LocalFileFragement fragment_One;
		private Tab2_Fragment_Two fragment_Two;
//		private Tab2_Fragment_Three fragment_Three;
		private Tab2_Fragment_Three fragment_Three;

		private FragmentManager fragmentManager;
		private LinearLayout linearLayout;

		SpinKitView spinKitView;

		private Handler handler = new Handler();
		@Override
		public void onSaveInstanceState(Bundle outState)
		{
				// Empty
				//重复的 Fragment 是像堆栈一样堆在底下，导致界面无限卡顿和重叠。
		}
		@Override
		public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState)
		{

				if (null == main_fg2)
				{
						main_fg2 = inflater.inflate(R.layout.main_tab_phone, null);
				}

				ViewGroup parent = (ViewGroup) main_fg2.getParent();
				if (parent != null)
				{
						parent.removeView(main_fg2);
				}
				fragmentManager = getActivity().getSupportFragmentManager();
				linearLayout = (LinearLayout)main_fg2.findViewById(R.id.layout_loading);


				//延时加载
				//2.启动任务，这里设置500毫秒后开始加载数据
				handler.postDelayed(Load_Data, 1500);

				tab2Layout= (LivingTabsLayout) main_fg2.findViewById(R.id.living_tabs);

//				//设置文本在选中和为选中时候的颜色 01bcd4 Color.parseColor("#4185F1")
				tab2Layout.setTabTextColors( Color.WHITE,Color.parseColor("#757575"));
				tab2Layout.addTab(tab2Layout.newTab().setText("本地"), true);//添加 Tab,默认选中
				tab2Layout.addTab(tab2Layout.newTab().setText("分类"),false);//添加 Tab,默认不选中
				tab2Layout.addTab(tab2Layout.newTab().setText("传输"),false);//添加 Tab,默认不选中

				tab2Layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
				{
						@Override
						public void onTabSelected(TabLayout.Tab tab)
						{
								showFragment(tab.getPosition()+1);
						}

						@Override
						public void onTabUnselected(TabLayout.Tab tab)
						{

						}

						@Override
						public void onTabReselected(TabLayout.Tab tab)
						{

						}
				});
				spinKitView = (SpinKitView) main_fg2.findViewById(R.id.spin_kit);
				Style style = Style.values()[2];
				Sprite drawable = SpriteFactory.create(style);
				spinKitView.setIndeterminateDrawable(drawable);


				return main_fg2;
		}


		//延时加载
		//3.若用户切换到其他Fragment则取消任务
		//每次调用的方法。注意-----每次加载数据就可以了
		@Override
		public void setUserVisibleHint(boolean isVisibleToUser)
		{
				super.setUserVisibleHint(isVisibleToUser);
				// 每次切换fragment时调用的方法
				if (isVisibleToUser)
				{
						//加载数据
						showData();
				}
		}

		//初始化数据
		private void showData()
		{
				if (isInit)
				{
						isInit = false;//加载数据完成
						// 加载各种数据
				}
		}
		//延时加载
		//1.首先，设置延迟任务
		private Runnable Load_Data=new Runnable()
		{

				@Override
				public void run()
				{
						//在这里讲数据内容加载到Fragment上
						linearLayout.setVisibility(View.GONE);
						showFragment(1);//显示第一个
				}
		};

		public void showFragment(int index)
		{
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.setCustomAnimations( R.anim.alpha_in, android.R.anim.fade_out);
				// 想要显示一个fragment,先隐藏所有fragment，防止重叠
				hideFragments(ft);
				switch (index)
				{
						case 1:
								// 如果fragment1已经存在则将其显示出来
								if (fragment_One != null)
								{
										ft.show(fragment_One);
								}
								// 否则添加fragment1，注意添加后是会显示出来的
								// replace方法也是先remove后add
								else
								{
										fragment_One = new LocalFileFragement();
										ft.add(R.id.tab2_fl_main, fragment_One);
								}
								break;
						case 2:
								if (fragment_Two != null)
								{
										ft.show(fragment_Two);
								}
								else
								{
										fragment_Two = new Tab2_Fragment_Two();
										ft.add(R.id.tab2_fl_main, fragment_Two);
								}
								break;
						case 3:
								if (fragment_Three != null)
								{
										ft.show(fragment_Three);
								}
								else
								{
										fragment_Three = new Tab2_Fragment_Three();
										ft.add(R.id.tab2_fl_main, fragment_Three);
								}
								break;
						default:

								break;

				}

				ft.commit();

		}
		// 当fragment已被实例化，相当于发生过切换，就隐藏起来
		public void hideFragments(FragmentTransaction ft)
		{
				if (fragment_One != null)
				{
						ft.hide(fragment_One);
				}

				if (fragment_Two!= null)
				{
						ft.hide(fragment_Two);
				}

				if (fragment_Three != null)
				{
						ft.hide(fragment_Three);
				}
		}
		@Override
		public void onDetach() {
				super.onDetach();
				try
				{
						Field childFragmentManager = Fragment.class
										.getDeclaredField("mChildFragmentManager");
						childFragmentManager.setAccessible(true);
						childFragmentManager.set(this, null);

				}
				catch (NoSuchFieldException e)
				{
						throw new RuntimeException(e);
				}
				catch (IllegalAccessException e)
				{
						throw new RuntimeException(e);
				}
		}

}
