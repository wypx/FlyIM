package me.smart.flyme.fragement.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.lang.reflect.Field;

import me.smart.flyme.R;

import me.smart.flyme.view.wiget.FlatTabGroup;

public class MainTab03 extends Fragment implements FlatTabGroup.OnItemClickListener
{
		private View main_fg;
		private FragmentManager fragmentManager;

		private FlatTabGroup tab3_flatgroup;




		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				if (null == main_fg)
				{
						main_fg = inflater.inflate(R.layout.main_tab_video, null);
				}
				ViewGroup parent = (ViewGroup) main_fg.getParent();
				if (parent != null)
				{
						parent.removeView(main_fg);
				}
				fragmentManager = getActivity().getSupportFragmentManager();
				tab3_flatgroup = (FlatTabGroup) main_fg.findViewById(R.id.tab3_flatgroup);
				tab3_flatgroup.setOnItemClickListener(this);

				showFragment(1);
				return main_fg;
		}

		@Override
		public void onItemClick(RadioButton item, int checkedItem) {
				//根据checkedItem显示哪个fragement
				showFragment(checkedItem + 1);
				Log.e("onItemClick","checkedItem: "+checkedItem);

		}
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
//								if (videoStatFragement != null)
//								{
//										ft.show(videoStatFragement);
//								}
//								else
//								{
//										videoStatFragement = new VideoStatFragement();
//										ft.add(R.id.tab3_fl_main, videoStatFragement);
//								}
								break;
						case 2:
//								if (previewFragment != null)
//								{
//										ft.show(previewFragment);
//								}
//								else
//								{
//										previewFragment = new PreviewFragment();
//										ft.add(R.id.tab3_fl_main, previewFragment);
//								}
								break;
						case 3:
//								if (fragment_Three != null)
//								{
//										ft.show(fragment_Three);
//								}
//								else
//								{
//										fragment_Three = new Tab2_Fragment_Three();
//										ft.add(R.id.tab3_fl_main, fragment_Three);
//								}
								break;
						default:

								break;

				}

				ft.commit();

		}
		// 当fragment已被实例化，相当于发生过切换，就隐藏起来
		public void hideFragments(FragmentTransaction ft)
		{
//				if (videoStatFragement != null)
//				{
//						ft.hide(videoStatFragement);
//				}
//
//				if (previewFragment!= null)
//				{
//						ft.hide(previewFragment);
//				}

//				if (fragment_Three != null)
//				{
//						ft.hide(fragment_Three);
//				}
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


