package me.smart.flyme.fragement.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.lang.reflect.Field;

import me.smart.flyme.R;
import me.smart.flyme.RadarScan.utils.LogUtil;
import me.smart.flyme.activity.main.MainActivity;
import me.smart.flyme.fragement.computer.DevListFragement;
import me.smart.flyme.fragement.computer.FileServerFragement;
import me.smart.flyme.view.wiget.FlatTabGroup;

/*
 * 每次FragmentTabHost切换fragment时会调用onCreateView()重绘UI。
 * 解决方法，在fragment onCreateView 里缓存View:
 */
public class MainTab01 extends Fragment implements FlatTabGroup.OnItemClickListener
{

		public FlatTabGroup getTab1_flatgroup() {
				return tab1_flatgroup;
		}

		private FlatTabGroup tab1_flatgroup;

		private View main_fg1;// 缓存Fragment view

		private FragmentManager fragmentManager;
		private DevListFragement devListFragement;
		private FileServerFragement fileServerFragement;

		private  static MainTab01 mainTab01;
		public static  synchronized MainTab01 getInstance()
		{
				if(null == mainTab01)
				{
						mainTab01 = new MainTab01();
				}
				return  mainTab01;
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{

				if (null == main_fg1)
				{
						main_fg1 = inflater.inflate(R.layout.main_tab_computer, null);
				}
		 /*
		  *  缓存的rootView需要判断是否已经被加过parent
		  *  如果有parent需要从parent删除
		  *  要不然会发生这个rootview已经有parent的错误。
		  */

				ViewGroup parent = (ViewGroup) main_fg1.getParent();
				if (parent != null)
				{
						parent.removeView(main_fg1);
				}
				main_fg1.findViewById(R.id.main_tab_pc_toorbar).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								MainActivity.menu_open();
						}
				});
				tab1_flatgroup = (FlatTabGroup) main_fg1.findViewById(R.id.tab1_flatgroup);
				tab1_flatgroup.setOnItemClickListener(this);
				fragmentManager = getActivity().getSupportFragmentManager();

				showFragment(1);//显示第2个
				return main_fg1;
		}


		@Override
		public void onDestroy() {
				super.onDestroy();

		}

		@Override
		public void onItemClick(RadioButton item, int checkedItem)
		{
				LogUtil.e("checkedItem : "+checkedItem);
				showFragment(checkedItem );//显示第一个
		}

		public  void showFragment(int index )
		{
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.setCustomAnimations( R.anim.alpha_in, android.R.anim.fade_out);
				// 想要显示一个fragment,先隐藏所有fragment，防止重叠
				hideFragments(ft);
				switch (index)
				{
						case 0:
								// 如果fragment1已经存在则将其显示出来
								if (devListFragement != null)
								{
										ft.show(devListFragement);
								}
								// 否则添加fragment1，注意添加后是会显示出来的
								// replace方法也是先remove后add
								else
								{
										devListFragement = new DevListFragement();
										ft.add(R.id.tab1_fl_main, devListFragement);
								}
								break;
						case 1:
								if (fileServerFragement != null)
								{
										ft.show(fileServerFragement);
								}
								else
								{
										fileServerFragement = FileServerFragement.getInstance();
										ft.add(R.id.tab1_fl_main, fileServerFragement);
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
				if (devListFragement != null)
				{
						ft.hide(devListFragement);
				}

				if (fileServerFragement!= null)
				{
						ft.hide(fileServerFragement);
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
