package me.smart.flyme.activity.phone;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.smart.flyme.R;
import me.smart.flyme.adapter.phone.DocAdapter;
import me.smart.flyme.fragement.main.MainTab03;
import me.smart.flyme.utils.ScreenUtils;
import me.smart.flyme.utils.view.ToolbarTools;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

/**
 * author：wypx on 2015/12/23 01:47
 * blog：smarting.me
 */
public class DocActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener, View.OnClickListener
{

		private CoordinatorLayout mCoordinatorLayout;
		private AppBarLayout mAppBarLayout;
		private Toolbar mToolbar;
		private TabLayout mTabLayout;
		private ViewPager mViewPager;

		private String[] mTitles;//标题
		private List<Fragment> mFragments;
		private DocAdapter mViewPagerAdapter;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.base_file_tabs);
				ScreenUtils.setScreenTransParent(this);

				initViews();
				initData();
				configViews();



	  }
		private void initViews()
		{
				mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout_document);
				mAppBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout_document);
				mToolbar = (Toolbar) findViewById(R.id.toolbar_document);
				mTabLayout = (TabLayout) findViewById(R.id.tablayout_document);
				mViewPager = (ViewPager) findViewById(R.id.viewpager_document);
		}
		private void initData()
		{

				mTitles = getResources().getStringArray(R.array.tab_titles);
				//初始化填充到ViewPager中的Fragment集合
				mFragments = new ArrayList<>();
				for (int i = 0; i < mTitles.length; i++)
				{
						mTabLayout.addTab(mTabLayout.newTab().setText(mTitles[i]));
						Bundle mBundle = new Bundle();
						mBundle.putInt("flag", i);
						MainTab03 mFragment = new MainTab03();
						mFragment.setArguments(mBundle);
						mFragments.add(i, mFragment);
				}

		}
		private void configViews() {

				// 设置显示Toolbar
				setSupportActionBar(mToolbar);
				getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);


				// 初始化ViewPager的适配器，并设置给它
				mViewPagerAdapter = new DocAdapter(getSupportFragmentManager(), mTitles, mFragments);
				mViewPager.setAdapter(mViewPagerAdapter);
				// 设置ViewPager最大缓存的页面个数
				mViewPager.setOffscreenPageLimit(5);
				// 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
				mViewPager.addOnPageChangeListener(this);

				mTabLayout.setTabMode(MODE_SCROLLABLE);
				// 将TabLayout和ViewPager进行关联，让两者联动起来
				mTabLayout.setupWithViewPager(mViewPager);
				// 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
				mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
		}

		@Override
		public void onClick(View v)
		{

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
		{
				mToolbar.setTitle(mTitles[position]);
		}

		@Override
		public void onPageSelected(int position)
		{
				final int res = R.mipmap.ic_launcher;
				ToolbarTools.colorChange(DocActivity.this,position,res,mToolbar,mTabLayout);
				//mTabLayout.colorChange(DocActivity.this,position,res,mToolbar);
		}

		@Override
		public void onPageScrollStateChanged(int state)
		{

		}
}
