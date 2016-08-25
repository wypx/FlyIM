package me.smart.flyme.activity.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import me.smart.flyme.R;
import me.smart.flyme.activity.menu.MyMenuFragment;
import me.smart.flyme.fragement.main.MainTab01;
import me.smart.flyme.fragement.main.MainTab02;
import me.smart.flyme.fragement.main.MainTab03;
import me.smart.flyme.fragement.main.MainTab04;
import me.smart.mylibrary.bottomnavigation.BottomNavigationItem;
import me.smart.mylibrary.bottomnavigation.BottomNavigationView;
import me.smart.mylibrary.bottomnavigation.OnBottomNavigationItemClickListener;
import me.smart.mylibrary.flowingdrawer.FlowingView;
import me.smart.mylibrary.flowingdrawer.LeftDrawerLayout;
import me.smart.mylibrary.utils.systerm.ToastUtils;

/**
 * 主界面的Activity,主要管理四个Tab界面
 */
public class MainActivity extends FragmentActivity
{
		private static LeftDrawerLayout mLeftDrawerLayout;

		protected static final String TAG = "MainActivity";
		private View currentButton;

		private MainTab01 fragment_one;
		private MainTab02 fragment_two;
		private MainTab03 fragment_three;
		private MainTab04 fragment_four;

		private ImageButton btn_four;
		private FragmentManager fm;

		public BottomNavigationView bottomNavigationView;

		@Override
		protected void onSaveInstanceState(Bundle bundle)
		{
				// Empty
				//重复的 Fragment 是像堆栈一样堆在底下，导致界面无限卡顿和重叠。
		}
		@Override
		protected void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation2);

				mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);

				FragmentManager fm2 = getSupportFragmentManager();
				MyMenuFragment mMenuFragment = (MyMenuFragment) fm2.findFragmentById(R.id.id_container_menu);
				FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
				if (mMenuFragment == null) {
						fm2.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
				}
				mLeftDrawerLayout.setFluidView(mFlowingView);
				mLeftDrawerLayout.setMenuFragment(mMenuFragment);

				fm = getSupportFragmentManager();

				int[] image =
								{
												R.drawable.main_mic_black_24dp,
												R.drawable.main_favorite_black_24dp,
												R.drawable.main_book_black_24dp,
												R.drawable.main_github_circle
								};
				int[] color =
								{
												ContextCompat.getColor(this, R.color.firstColor),
												ContextCompat.getColor(this, R.color.secondColor),
												ContextCompat.getColor(this, R.color.thirdColor),
												ContextCompat.getColor(this, R.color.fourthColor)
								};
				if (bottomNavigationView != null)
				{
						bottomNavigationView.isWithText(false);
						// bottomNavigationView.activateTabletMode();
						bottomNavigationView.isColoredBackground(false);
						bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.firstColor));
						bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/fzqtfw.ttf"));

						//bottomNavigationView.disableShadow();
				}
				else
				{
						Log.e("cc","bottomNavigationView is null");
				}

				BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
								("Computer", color[0], image[0]);
				BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
								("Phone", color[1], image[1]);
				BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
								("Video", color[2], image[2]);
				BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
								("System", color[3], image[3]);

				if(bottomNavigationItem != null)
						bottomNavigationView.addTab(bottomNavigationItem);
				if(bottomNavigationItem1 != null)
						bottomNavigationView.addTab(bottomNavigationItem1);
				if(bottomNavigationItem2 != null)
						bottomNavigationView.addTab(bottomNavigationItem2);
				if(bottomNavigationItem3 != null)
						bottomNavigationView.addTab(bottomNavigationItem3);

				bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener()
				{
						@Override
						public void onNavigationItemClick(int index)
						{
								switch (index)
								{
										case 0:
												showFragment(1);
												break;
										case 1:
												showFragment(2);
												break;
										case 2:
												showFragment(3);
												break;
										case 3:
												showFragment(4);
												break;
								}
						}
				});
				bottomNavigationView.selectTab(2);
				showFragment(2);//默认显示第二个

		}


		public void showFragment(int index)
		{
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out);
				// 想要显示一个fragment,先隐藏所有fragment，防止重叠
				hideFragments(ft);
				switch (index)
				{
						case 1:
								// 如果fragment1已经存在则将其显示出来
								if (fragment_one != null)
								{
										ft.show(fragment_one);
										// 否则添加fragment1，注意添加后是会显示出来的
										// replace方法也是先remove后add
								}
								else
								{
										fragment_one = MainTab01.getInstance();
										ft.add(R.id.fl_content, fragment_one);
								}
								break;
						case 2:
								if (fragment_two != null)
								{
										ft.show(fragment_two);
								}
								else
								{
										fragment_two = new MainTab02();
										ft.add(R.id.fl_content, fragment_two);
								}
								break;
						case 3:
								if (fragment_three != null)
								{
										ft.show(fragment_three);
								}
								else
								{
										fragment_three = new MainTab03();
										ft.add(R.id.fl_content, fragment_three);
								}
								break;
						case 4:
								if (fragment_four != null)
								{
										ft.show(fragment_four);
								}
								else
								{
										fragment_four = new MainTab04();
										ft.add(R.id.fl_content, fragment_four);
								}
								break;

						default:
								break;
				}

				ft.commitAllowingStateLoss();//允许状态丢失的提交

		}
		// 当fragment已被实例化，相当于发生过切换，就隐藏起来
		public void hideFragments(FragmentTransaction ft)
		{
				if (fragment_one != null)
						ft.hide(fragment_one);
				if (fragment_two!= null)
						ft.hide(fragment_two);
				if (fragment_three != null)
						ft.hide(fragment_three);
				if (fragment_four != null)
						ft.hide(fragment_four);

		}
	public static void menu_open()
	{
			mLeftDrawerLayout.toggle();
	}
		//用户体验--返回键
		private long lastclicktime = 0;
		@Override
		public void onBackPressed()
		{
				//菜单
				if (mLeftDrawerLayout.isShownMenu())
				{
						mLeftDrawerLayout.closeDrawer();
				}
				//super.onBackPressed();
				if(lastclicktime <= 0)
				{
						ToastUtils.showLong(MainActivity.this, "再按一次退出应用 !");
						lastclicktime=System.currentTimeMillis();
				}
				else
				{
						long currentime =System.currentTimeMillis();
						if(currentime - lastclicktime< 1000)
						{
//								MaterialDialogDefault();

						}
						else
						{
								ToastUtils.showLong(MainActivity.this, "再按一次退出应用 !");
								lastclicktime = currentime;
								finish();

						}

				}

		}
}

