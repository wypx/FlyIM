package me.smart.flyme.adapter.phone;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.smart.flyme.R;
import me.smart.flyme.bean.phone.VideoHolder;
import me.smart.flyme.mediastore.MediaStoreData;
import me.smart.mylibrary.utils.systerm.LogUtils;
/**
 * public static Uri getMediaScannerUri ()
    获取扫描视频的Uri。
 public static Uri getVolumeUri ()
    获取正在扫描的SD卡的Uri。

 */

/**
 * author：wypx on 2015/12/20 00:11
 * blog：smarting.me
 */
public class MediaStoreAdapter  extends RecyclerView.Adapter<VideoHolder>
{
		private List<MediaStoreData> mediaStoreDatas = new ArrayList<MediaStoreData>();
		private LayoutInflater mInflater;
		private  Context mContext;

		public MediaStoreAdapter(Context mContext)
		{
				this.mContext = mContext;
				mInflater = LayoutInflater.from(mContext);

		}
		// 创建ViewHolder
		@Override
		public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
				View mView = mInflater.inflate(R.layout.base_file_video_item, parent, false);
				VideoHolder mViewHolder = new VideoHolder(mView);
				return mViewHolder;


		}

		//绑定ViewHoler，给item中的控件设置数据
		@Override
		public void onBindViewHolder(final VideoHolder holder, final int position)
		{
				final MediaStoreData data = mediaStoreDatas.get(position);

				LogUtils.e("缩略图1:"+data.thumbpath);
				if(null != data.uri)
				{
						Glide.with(mContext).
										load(data.uri).
										thumbnail(1/3).
										fitCenter().
										centerCrop().
										//.override(300,200)    //大小
										//.dontTransform()
										//.transform(new  )        //动画
										crossFade().  ////动画
										placeholder(R.mipmap.empty_photo).//占位符
										error(R.mipmap.ic_launcher).
										//diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
										into(holder.img_thumb);
				}
				holder.tv_tiltle.setText(data.title);
				if (mOnItemClickListener != null)
				{
						holder.itemView.setOnClickListener(new View.OnClickListener()
						{
								@Override
								public void onClick(View v)
								{
										mOnItemClickListener.onItemClick(data.uri, position);
								}
						});

						holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
						{
								@Override
								public boolean onLongClick(View v)
								{
										mOnItemClickListener.onItemLongClick(holder.itemView, position);
										return true;
								}
						});

				}

		}

		@Override
		public int getItemCount()
		{
				return mediaStoreDatas.size();
		}
		@Override
		public long getItemId(int position)
		{
				return position;
		}
		//每个子项目点击菜单接口
		public interface OnItemClickListener
		{
				void onItemClick(Uri filepath, int position);

				void onItemLongClick(View view, int position);
		}

		public OnItemClickListener mOnItemClickListener;

		public void setOnItemClickListener(OnItemClickListener listener)
		{
				this.mOnItemClickListener = listener;
		}
		public void setData(List<MediaStoreData> mediaStoreDatas)
		{
				Log.i("MediaAdapter", "setData");
				this.mediaStoreDatas.clear();
				if (mediaStoreDatas != null)
				{
						for (int i = 0; i < mediaStoreDatas.size(); i++)
						{
								this.mediaStoreDatas.add(mediaStoreDatas.get(i));
						}
				}
				notifyDataSetChanged();
		}
		//在给item加上动画之后，慢速滑动的时候是没有问题的，但要快速滑动就会出现卡屏现象。
		@Override
		public void onViewDetachedFromWindow(VideoHolder holder)
		{
				super.onViewDetachedFromWindow(holder);
				holder.itemView.clearAnimation();
		}

}
