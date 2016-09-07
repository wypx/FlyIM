package me.smart.flyme.fragement.computer;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.IOException;

import me.smart.flyme.R;
import me.smart.flyme.activity.computer.FileListActivity;
import me.smart.flyme.adapter.computer.DriverAdapter;
import me.smart.flyme.bean.computer.DiskInfo;
import me.smart.flyme.bean.computer.Request;
import me.smart.flyme.constant.COMMON;
import me.smart.flyme.myapp.MyApp;
import me.smart.flyme.view.recyclerview.AutoLoadRecyclerView;
import me.smart.flyme.view.recyclerview.LoadFinishCallBack;

/**
 * author：wypx on 2016/8/21 16:38
 * blog：smarting.me
 */
public class DriverListFragement extends Fragment
				implements SwipeRefreshLayout.OnRefreshListener, DriverAdapter.OnItemClickListener
{

		private SwipeRefreshLayout mSwipeRefreshLayout;
		private AutoLoadRecyclerView mRecyclerView;
		private RecyclerView.LayoutManager mLayoutManager;

		private DriverAdapter mRecyclerViewAdapter;

		private static final int SPAN_COUNT = 1;

		private  View fg_fileserver;

		private  DiskInfo diskInfo;
		private LoadFinishCallBack mLoadFinisCallBack;
		private  static DriverListFragement fileServerFragement;
		public static  synchronized DriverListFragement getInstance()
		{
				if(null == fileServerFragement)
				{
						fileServerFragement = new DriverListFragement();
				}
				return  fileServerFragement;
		}

		Handler handler = new Handler()
		{
				@Override
				public void handleMessage(Message msg) {
						switch (msg.what)
						{
								case 1:
										mRecyclerViewAdapter.mDatas = null;
										mSwipeRefreshLayout.setRefreshing(false);
										mRecyclerViewAdapter.mDatas = (DiskInfo)msg.obj;
										mRecyclerViewAdapter.notifyDataSetChanged();
										break;
								default:
										break;
						}
				}
		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{

				if (null == fg_fileserver)
				{
						fg_fileserver = inflater.inflate(R.layout.activity_computer_fileserverlist, null);
				}
		 /*
		  *  缓存的rootView需要判断是否已经被加过parent
		  *  如果有parent需要从parent删除
		  *  要不然会发生这个rootview已经有parent的错误。
		  */

				ViewGroup parent = (ViewGroup) fg_fileserver.getParent();
				if (parent != null)
				{
						parent.removeView(fg_fileserver);
				}

				mSwipeRefreshLayout = (SwipeRefreshLayout) fg_fileserver.findViewById(R.id.id_swiperefreshlayout);
				mRecyclerView = (AutoLoadRecyclerView) fg_fileserver.findViewById(R.id.id_recyclerview);


				mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
				mRecyclerViewAdapter = new DriverAdapter(getActivity());
				mRecyclerViewAdapter.setOnItemClickListener(this);

				//设置adapter
				mRecyclerView.setAdapter(mRecyclerViewAdapter);
				//设置布局管理器
				mRecyclerView.setLayoutManager(mLayoutManager);
				//如果item的内容不改变view布局大小，那使用这个设置可以提高RecyclerView的效率
				//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
				mRecyclerView.setHasFixedSize(true);

				//设置Item增加、移除动画
				mRecyclerView.setItemAnimator(new DefaultItemAnimator());
				// 刷新时，指示器旋转后变化的颜色
				mSwipeRefreshLayout.setColorSchemeResources(
								android.R.color.holo_blue_bright,
								android.R.color.holo_green_light,
								android.R.color.holo_orange_light,
								android.R.color.holo_red_light);

				mSwipeRefreshLayout.setOnRefreshListener(this);

				mLoadFinisCallBack = mRecyclerView;
				mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {
						@Override
						public void loadMore() {
								//mAdapter.loadNextPage();

						}
				});


				return fg_fileserver;
		}
		@Override
		public void onActivityCreated(@Nullable Bundle savedInstanceState) {
				super.onActivityCreated(savedInstanceState);
				mRecyclerView.setOnPauseListenerParams(Glide.get(getActivity()), false, true);

				mRecyclerView.setAdapter(mRecyclerViewAdapter);
		}
		@Override
		public void onItemClick(View view, int position)
		{
				Intent intent = new Intent(getActivity(),FileListActivity.class);
				intent.putExtra("driver", mRecyclerViewAdapter.mDatas.getDiskName(position));
				startActivity(intent);

		}

		@Override
		public void onItemLongClick(View view, int position) {

		}


		@Override
		public void onRefresh() {
				if (mSwipeRefreshLayout.isRefreshing())
				{
						mSwipeRefreshLayout.setRefreshing(false);
				}
				new Thread(new Runnable()
				{
						@Override
						public void run() {

								try
								{
										Request request = new Request(COMMON.CMD_DRIVER_LIST,"22");
										System.out.print(request.encodeS());
										MyApp.Send(request.encodeS());
								}
								catch (IOException e)
								{
										e.printStackTrace();
								}
								try
								{
										diskInfo = DiskInfo.decode(MyApp.Recv());
										if(null != diskInfo && diskInfo.getCmdType() == COMMON.CMD_DRIVER_LIST)
										{
												Message message = new Message();
												message.what = 1;
												message.obj  = diskInfo;
												handler.sendMessage(message);
										}
								}
								catch (IOException e1)
								{
										e1.printStackTrace();
								}
						}
				}).start();
		}

		/**
		 * 各种不同的显示方式
		 */
		private void configRecyclerView()
		{

				// VERTICAL_LIST:
				mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
				// HORIZONTAL_LIST:
				mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
				// VERTICAL_GRID:
				mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
				// HORIZONTAL_GRID:
				mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
				//STAGGERED_GRID:
				mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);

				//STAGGERED_GRID
				{
						mRecyclerViewAdapter = new DriverAdapter(getActivity());
						mRecyclerViewAdapter.setOnItemClickListener(this);
						mRecyclerView.setAdapter(mRecyclerViewAdapter);

//						mStaggeredAdapter = new MyStaggeredViewAdapter(getActivity());
//						mStaggeredAdapter.setOnItemClickListener(this);
//						mRecyclerView.setAdapter(mStaggeredAdapter);

						mRecyclerView.setLayoutManager(mLayoutManager);

				}
		}
}
