package me.smart.flyme.activity.computer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;

import me.smart.flyme.R;
import me.smart.flyme.RadarScan.utils.LogUtil;
import me.smart.flyme.activity.download.DownActivity;
import me.smart.flyme.adapter.computer.FileListAdapter;
import me.smart.flyme.bean.computer.FileList;
import me.smart.flyme.bean.computer.FileListItem;
import me.smart.flyme.bean.computer.Request;
import me.smart.flyme.constant.COMMON;
import me.smart.flyme.myapp.MyApp;
import me.smart.flyme.view.fab.FABToolbarLayout;
import me.smart.flyme.view.recyclerview.AutoLoadRecyclerView;
import me.smart.flyme.view.recyclerview.LoadFinishCallBack;
import me.smart.mylibrary.utils.systerm.LogUtils;

/**
 * author：wypx on 2015/12/27 21:42
 * blog：smarting.me
 */
public class FileListActivity extends AppCompatActivity
				implements SwipeRefreshLayout.OnRefreshListener, FileListAdapter.OnItemClickListener
{
	//	private SwipeRefreshLayout mSwipeRefreshLayout;
		private AutoLoadRecyclerView mRecyclerView;
		private RecyclerView.LayoutManager mLayoutManager;

		private FileListAdapter mRecyclerViewAdapter;
		byte [] byteArrayData=new byte[2048];
		private FileList fileList;
		private static final int SPAN_COUNT = 1;
		private LoadFinishCallBack mLoadFinisCallBack;

		private String driver;

		Handler handler = new Handler()
		{
				@Override
				public void handleMessage(Message msg) {
						switch (msg.what)
						{
								case 1:
									//	mRecyclerViewAdapter.fileList = null;
									//	mSwipeRefreshLayout.setRefreshing(false);
										FileList list = (FileList) msg.obj;
										mRecyclerViewAdapter.fileList.addAll(list.getFiles());
										LogUtils.e("file size: "+ mRecyclerViewAdapter.fileList.size());
									//	mRecyclerViewAdapter.notifyDataSetChanged();
										break;
								case 0:

										LogUtils.e("file size: "+ mRecyclerViewAdapter.fileList.size());
										mRecyclerViewAdapter.notifyDataSetChanged();
										break;
								default:
										break;
						}
				}
		};
		private FABToolbarLayout layout;
		private View one, two, three, four;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.base_file_recycleview);

			//	mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.id_swiperefreshlayout);
				mRecyclerView = (AutoLoadRecyclerView) this.findViewById(R.id.recyclerview_base_file);


				mLayoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
				mRecyclerViewAdapter = new FileListAdapter(this);
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
//				mSwipeRefreshLayout.setColorSchemeResources(
//								android.R.color.holo_blue_bright,
//								android.R.color.holo_green_light,
//								android.R.color.holo_orange_light,
//								android.R.color.holo_red_light);

//				mSwipeRefreshLayout.setOnRefreshListener(this);

				mLoadFinisCallBack = mRecyclerView;
				mRecyclerView.setLoadMoreListener(new AutoLoadRecyclerView.onLoadMoreListener() {
						@Override
						public void loadMore() {
								//mAdapter.loadNextPage();

						}
				});

				//mRecyclerView.setOnPauseListenerParams(Glide.get(this), false, true);

				layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar_base);
				one = findViewById(R.id.img_base_one);
				two = findViewById(R.id.img_base_two);
				three = findViewById(R.id.img_base_three);
				four = findViewById(R.id.img_base_four);

				driver = getIntent().getStringExtra("driver");

				new Thread(new Runnable()
				{
						@Override
						public void run() {
								try
								{
										LogUtil.e("getStringExtra: "+ driver);
										Request request = new Request(COMMON.CMD_FILE_LIST, driver);
										MyApp.Send(request.encodeS());
								}
								catch (IOException e)
								{
										e.printStackTrace();
								}
								try
								{
										LogUtils.e("file size0: "+ mRecyclerViewAdapter.fileList.size());
										mRecyclerViewAdapter.fileList.clear();
										LogUtils.e("file size1: "+ mRecyclerViewAdapter.fileList.size());
										String buf = null;
										while((buf = MyApp.Recv()) != null)
										{
												fileList = null;
												fileList = FileList.decode(buf);
												if(null != fileList
																&& fileList.getCmdType() == COMMON.CMD_FILE_LIST)
												{
														Message message = new Message();
														message.what = 1;
														message.obj  = fileList;
														handler.sendMessage(message);
												}
										}

										Message message2 = new Message();
										message2.what = 0;
										handler.sendMessage(message2);
								}
								catch (IOException e1)
								{
										e1.printStackTrace();
								}
						}
				}).start();
			//	layout.hide();
			//	layout.show();
		}

		@Override
		public void onItemClick(View view, int position) {
				FileListItem item = mRecyclerViewAdapter.fileList.get(position);
				if(item.getFileType() == COMMON.TYPE_DIR)
				{
						new Thread(new Runnable()
						{
								@Override
								public void run() {
										try
										{
												//"F:\\BaiduYunDownload"
												String driver = getIntent().getStringExtra("driver");
												LogUtil.e("getStringExtra: "+ driver);
												Request request = new Request(COMMON.CMD_FILE_LIST, driver);
												System.out.print(request.encodeS());
												MyApp.Send(request.encodeS());
										}
										catch (IOException e)
										{
												e.printStackTrace();
										}
										try
										{
												fileList = FileList.decode(MyApp.Recv());
												if(null != fileList && fileList.getCmdType() == COMMON.CMD_FILE_LIST)
												{
														Message message = new Message();
														message.what = 1;
														message.obj  = fileList;
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
				else if(item.getFileType() == COMMON.TYPE_FILE )
				{
						startActivity(new Intent(this, DownActivity.class));
				}
		}

		@Override
		public void onItemLongClick(View view, int position) {

		}

		@Override
		public void onRefresh() {

		}
}
