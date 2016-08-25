package me.smart.flyme.fragement.computer;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cybergarage.upnp.Device;

import java.util.ArrayList;
import java.util.List;

import me.smart.flyme.R;
import me.smart.flyme.adapter.computer.DeviceListAdapeter;
import me.smart.flyme.fragement.main.MainTab01;
import me.smart.flyme.upnp.ctlpt.RemoteCtrl;
import me.smart.flyme.view.recyclerview.AutoLoadRecyclerView;
import me.smart.flyme.view.recyclerview.LoadFinishCallBack;
import me.smart.mylibrary.FlycoDialog.animation.BounceEnter.BounceTopEnter;
import me.smart.mylibrary.FlycoDialog.animation.SlideExit.SlideBottomExit;
import me.smart.mylibrary.FlycoDialog.dialog.listener.OnBtnClickL;
import me.smart.mylibrary.FlycoDialog.dialog.widget.MaterialDialog;
import me.smart.mylibrary.utils.systerm.LogUtils;

/**
 * author：wypx on 2016/8/8 22:33
 * blog：smarting.me
 */
public class DevListFragement extends Fragment  implements DeviceListAdapeter.OnItemClickListener
{
		private View devlist_fg;// 缓存Fragment view
		private AutoLoadRecyclerView recyclerView_devlist;
		private SwipeRefreshLayout mSwipeRefreshLayout;
		private RecyclerView.LayoutManager mLayoutManager;
		private static final int SPAN_COUNT = 1;
		private DeviceListAdapeter deviceListAdapeter;
		private LoadFinishCallBack mLoadFinisCallBack;
		List<Device> friend_names = new ArrayList<Device>();
		RemoteCtrl remoteCtrl;

		Handler handler = new Handler()
		{
				@Override
				public void handleMessage(Message msg) {
						switch (msg.what)
						{
								case 1:
										deviceListAdapeter.friend_names = null;
										mSwipeRefreshLayout.setRefreshing(false);
										deviceListAdapeter.friend_names = (List<Device>) msg.obj;
										deviceListAdapeter.notifyDataSetChanged();
										break;
								default:
										break;
						}
				}
		};
		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
				if (null == devlist_fg)
				{
						devlist_fg = inflater.inflate(R.layout.activity_computer_fileserverlist, null);
				}

				ViewGroup parent = (ViewGroup) devlist_fg.getParent();
				if (parent != null)
				{
						parent.removeView(devlist_fg);
				}
				recyclerView_devlist = (AutoLoadRecyclerView) devlist_fg.findViewById(R.id.id_recyclerview);
				mSwipeRefreshLayout = (SwipeRefreshLayout) devlist_fg.findViewById(R.id.id_swiperefreshlayout);

				mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
				deviceListAdapeter = new DeviceListAdapeter(getActivity());
				deviceListAdapeter.setOnItemClickListener(this);

				//设置adapter
				recyclerView_devlist.setAdapter(deviceListAdapeter);
				//设置布局管理器
				recyclerView_devlist.setLayoutManager(mLayoutManager);
				//如果item的内容不改变view布局大小，那使用这个设置可以提高RecyclerView的效率
				//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
				recyclerView_devlist.setHasFixedSize(true);

				//设置Item增加、移除动画
				recyclerView_devlist.setItemAnimator(new DefaultItemAnimator());
				// 刷新时，指示器旋转后变化的颜色
				mSwipeRefreshLayout.setColorSchemeResources(
								android.R.color.holo_blue_bright,
								android.R.color.holo_green_light,
								android.R.color.holo_orange_light,
								android.R.color.holo_red_light);

				mLoadFinisCallBack = recyclerView_devlist;
				mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
						@Override
						public void onRefresh() {
								LogUtils.e("onRefresh.......");
								friend_names.clear();
//								friend_names.add("我的电脑");
//								friend_names.add("我的ubuntu");
//								friend_names.add("我的windows");
//								friend_names.add("我的debian");
//								friend_names.add("我的apple");
//								friend_names.add("我的android");

								friend_names = remoteCtrl.getDevices();
								Message message = new Message();
								message.what = 1;
								message.obj  = friend_names;
								handler.sendMessage(message);
						}
				});
				recyclerView_devlist.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {
						@Override
						public void loadMore() {
								//mAdapter.loadNextPage();
						}
				});
				remoteCtrl = new RemoteCtrl();
				new Thread(new Runnable() {
						@Override
						public void run() {
								remoteCtrl.start();
						}
				}).start();
				friend_names = remoteCtrl.getDevices();
				Message message = new Message();
				message.what = 1;
				message.obj  = friend_names;
				handler.sendMessage(message);
				return devlist_fg;
		}

		@Override
		public void onItemClick(View view, int position)
		{
				MainTab01.getInstance().showFragment(1);
				MainTab01.getInstance().getTab1_flatgroup().setButtonCheck(1);
		}

		@Override
		public void onItemLongClick(View view, int position) {


		}
		public void MaterialDialogDefault(String content)
		{
				final MaterialDialog dialog = new MaterialDialog(getActivity());
				dialog.content(content)//
								.btnText("取消", "确定")//
								.showAnim(new BounceTopEnter())//
								.dismissAnim(new SlideBottomExit())//
								.show();
				dialog.setOnBtnClickL(new OnBtnClickL() {
						@Override
						public void onBtnClick() {

						}
				});
//				dialog.setOnBtnLeftClickL(new OnBtnClickL() {
//						@Override
//						public void onBtnClick() {
//							dialog.dismiss();
//						}
//				});
		}

}
