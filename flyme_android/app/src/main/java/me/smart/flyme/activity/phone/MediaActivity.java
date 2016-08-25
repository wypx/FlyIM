package me.smart.flyme.activity.phone;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import me.smart.flyme.R;
import me.smart.flyme.activity.wifi.WiFiActivity;
import me.smart.flyme.adapter.phone.MediaStoreAdapter;
import me.smart.flyme.constant.MediaConf;
import me.smart.flyme.mediastore.MediaStoreData;
import me.smart.flyme.mediastore.MediaStoreDataLoader;
import me.smart.flyme.view.fab.FABToolbarLayout;
import me.smart.flyme.view.recyclerview.AutoLoadRecyclerView;
import me.smart.flyme.view.wiget.TitleCustomBarView;

/**
 * author：wypx on 2015/12/20 00:40
 * blog：smarting.me
 */
public class MediaActivity extends AppCompatActivity implements
				LoaderManager.LoaderCallbacks<List<MediaStoreData>>, MediaStoreAdapter.OnItemClickListener
{

		private FABToolbarLayout layout;
		private View one, two, three, four;


		private TitleCustomBarView toorbar_mediastore;
		private AutoLoadRecyclerView mRecyclerView;
		//布局管理器
		private RecyclerView.LayoutManager mLayoutManager;
		//适配器
		private MediaStoreAdapter mediaAdapter;

		private static final int SPAN_COUNT = 2;

		@Override
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.base_file_recycleview);

				layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar_base);
				one = findViewById(R.id.img_base_one);
				two = findViewById(R.id.img_base_two);
				three = findViewById(R.id.img_base_three);
				four = findViewById(R.id.img_base_four);

				toorbar_mediastore = (TitleCustomBarView)findViewById(R.id.toolbar_base_title);
				toorbar_mediastore.setTitleCustomClicker(titleCustomClicker);
				toorbar_mediastore.setTitle("媒体库文件");
				toorbar_mediastore.setActivity(this);

				//设置为视频
				//MediaConf.setCurrentMimeType(MediaStoreData.MimeType.VIDEO);

				mLayoutManager = new GridLayoutManager(this,SPAN_COUNT,GridLayoutManager.VERTICAL,false)
				{
						//RecyclerView 元素的预加载
						//getExtraLayoutSpace将返回GridManager应该预留的额外空间(显示范围之外,应该额外缓存的空间)
						@Override
						protected int getExtraLayoutSpace(RecyclerView.State state)
						{
								return 300;
						}
				};
				mRecyclerView = (AutoLoadRecyclerView)findViewById(R.id.recyclerview_base_file);
				//mLayoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
				mediaAdapter = new MediaStoreAdapter(this);
				mediaAdapter.setOnItemClickListener(this);


				//设置adapter
				AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mediaAdapter);
				alphaAdapter.setDuration(1000);
				alphaAdapter.setInterpolator(new OvershootInterpolator());
				alphaAdapter.setFirstOnly(true);

				// 设置是否滑动时候暂停加载
			//	mRecyclerView.setOnPauseListenerParams(Glide.get(this), false, true);
				mRecyclerView.setAdapter(alphaAdapter);
				mRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
				mRecyclerView.setItemAnimator(new DefaultItemAnimator());	//设置Item增加、移除动画

				//初始化LoaderManager启动
				getLoaderManager().initLoader(0, null, this);
		}

		//在创建activity时跟着onCreate会调用一次
		@Override
		public Loader<List<MediaStoreData>> onCreateLoader(int arg0, Bundle arg1)
		{
				return new MediaStoreDataLoader(this);
		}
		//每次改变和Loader相关的数据库记录后会调用一次
		@Override
		public void onLoadFinished(Loader<List<MediaStoreData>> loader,List<MediaStoreData> data)
		{
				mediaAdapter.setData(data);
		}

		//在关闭Activity时调用,释放资源
		@Override
		public void onLoaderReset(Loader<List<MediaStoreData>> loader)
		{

				mediaAdapter.setData(null);
		}

		@Override
		public void onItemClick(Uri filepath, int position)
		{
				layout.hide();
				Intent tostart = new Intent(Intent.ACTION_VIEW);
				if(MediaConf.getCurrentMimeType() == MediaStoreData.MimeType.IMAGE)
				{
						tostart.setDataAndType(filepath, "image/*");
				}
				else if(MediaConf.getCurrentMimeType() == MediaStoreData.MimeType.VIDEO)
				{
						tostart.setDataAndType(filepath, "video/*");
				}

				startActivity(tostart);

		}

		@Override
		public void onItemLongClick(View view, int position)
		{
				layout.show();
				startActivity(new Intent(MediaActivity.this, WiFiActivity.class));
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
