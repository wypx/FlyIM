package me.smart.flyme.fragement.phone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import me.smart.flyme.R;
import me.smart.flyme.activity.download.DownActivity;
import me.smart.flyme.activity.phone.DocActivity;
import me.smart.flyme.activity.phone.MediaActivity;
import me.smart.flyme.activity.phone.PhoneStorageActivity;
import me.smart.flyme.adapter.phone.Tab2_MyGridAdapter;
import me.smart.flyme.constant.MediaConf;
import me.smart.flyme.mediastore.MediaStoreData;
import me.smart.flyme.view.wiget.LoginDialog;
import me.smart.flyme.view.wiget.MyGridView;
import me.smart.flyme.view.wiget.NumberProgressBar;
import me.smart.mylibrary.animator.waterwave.ActivityAnimationTool;
import me.smart.mylibrary.animator.waterwave.WaterEffect;


@SuppressLint("InflateParams")
public class Tab2_Fragment_Two extends Fragment implements OnClickListener,NumberProgressBar.OnProgressBarListener
{
		////
		private Timer timer;
		private NumberProgressBar bnp;
		///////
		private View contentView;// 缓存Fragment view
		private MyGridView mGridView;
		private Button bt_moreinfo;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
				if (null == contentView)
				{
						contentView = inflater.inflate(R.layout.tab2_fragment_two, null);
				}
				// 缓存的rootView需要判断是否已经被加过parent，
				//如果有parent需要从parent删除，
				//要不然会发生这个rootview已经有parent的错误。
				ViewGroup parent = (ViewGroup) contentView.getParent();
				if (parent != null)
				{
						parent.removeView(contentView);
				}
				mGridView=(MyGridView) contentView.findViewById(R.id.MyGridView2);
				mGridView.setAdapter(new Tab2_MyGridAdapter(getActivity()));
				//为mGridView添加点击事件监听器
				mGridView.setOnItemClickListener(new GridViewItemOnClick());
				////

				bnp = (NumberProgressBar)contentView.findViewById(R.id.numberbar);
				bnp.setOnProgressBarListener(this);
				timer = new Timer();
				timer.schedule(new TimerTask()
				{
						@Override
						public void run()
						{
								getActivity().runOnUiThread(new Runnable()
								{
										@Override
										public void run()
										{
												bnp.incrementProgressBy(1);
										}
								});
						}
				}, 1000, 100);

				//水波纹
				ActivityAnimationTool.init(new WaterEffect());
				return contentView;
		}
		@Override
		public void onClick(View v)
		{
				switch (v.getId())
				{
						default:
								break;
				}
		}
		@Override
		public void onProgressChange(int current, int max)
		{
				if(current == max)
				{
						Toast.makeText(getContext(), "完成", Toast.LENGTH_SHORT).show();
						bnp.setProgress(0);
				}
		}
		@Override
		public void onDestroy()
		{
				super.onDestroy();
				timer.cancel();
		}
		public class GridViewItemOnClick implements OnItemClickListener
		{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
						if(position==0)
						{
								//startActivity(new Intent(getActivity(),GalleryActivty.class));
								MediaConf.setCurrentMimeType(MediaStoreData.MimeType.IMAGE);
								startActivity(new Intent(getActivity(),MediaActivity.class));
						}
						else if(position==1)
						{
								//水波纹效果
							//	ActivityAnimationTool.startActivity(getActivity(), new Intent(getActivity(), AboutActivity.class));
								//下一个activity也需要设置

						}
						else if(position==2)
						{
								MediaConf.setCurrentMimeType(MediaStoreData.MimeType.VIDEO);
								startActivity(new Intent(getActivity(),MediaActivity.class));
						}
						else if(position == 3)
						{
								startActivity(new Intent(getActivity(),DocActivity.class));
						}

						else if(position == 4)
						{
								startActivity(new Intent(getActivity(),DownActivity.class));
						}
						else if(position == 5)
						{
								startActivity(new Intent(getActivity(),PhoneStorageActivity.class));
						}
						else if(position == 6)
						{
								LoginDialog loginDialog = new LoginDialog(getActivity(),R.layout.activity_fileserver_login);
								loginDialog.show();
					//			startActivity(new Intent(getActivity(),LeafLoadingActivity.class));

						}
						else if(position == 7)
						{

						}
						else if(position == 7)
						{
								//水波纹效果
								//ActivityAnimationTool.startActivity(getActivity(), new Intent(getActivity(), AboutActivity.class));
								//下一个activity也需要设置
						}
				}

		}


}

