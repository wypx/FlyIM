package me.smart.flyme.adapter.computer;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.cybergarage.upnp.Device;

import java.util.ArrayList;
import java.util.List;

import me.smart.flyme.R;
import me.smart.flyme.model.BaseViewHolder;
import me.smart.mylibrary.utils.systerm.LogUtils;

/**
 * author：wypx on 2016/8/21 16:16
 * blog：smarting.me
 */
public class DeviceListAdapeter extends RecyclerView.Adapter<BaseViewHolder> {

		private String MEDIA = "urn:schemas-upnp-org:device:MediaServer:1";
		private String GATEWAY = "urn:schemas-upnp-org:device:InternetGatewayDevice:1";
		private String FILESERVER = "urn:schemas-upnp-org:device:fileserver:2";
		private Context mContext;
		public List<Device> friend_names = new ArrayList<Device>();
		private LayoutInflater mLayoutInflater;

		public DeviceListAdapeter(Context mContext)
		{
				this.mContext = mContext;
				mLayoutInflater = LayoutInflater.from(mContext);

		}
		@Override
		public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View mView = mLayoutInflater.inflate(R.layout.base_file_item, parent, false);
				BaseViewHolder mViewHolder = new BaseViewHolder(mView);

				return mViewHolder;
		}

		@Override
		public void onBindViewHolder(final  BaseViewHolder holder, final  int position) {
				if (mOnItemClickListener != null)
				{
						holder.itemView.setOnClickListener(new View.OnClickListener()
						{
								@Override
								public void onClick(View v)
								{
										mOnItemClickListener.onItemClick(holder.itemView, position);
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

				};
				Device dev = friend_names.get(position);
				LogUtils.e("type: "+dev.getDeviceType());
				if(dev.getDeviceType().contains(GATEWAY))
						GlideLoadImage(R.mipmap.device_details_router, holder.image);
				else if(dev.getDeviceType().contains(FILESERVER))
						GlideLoadImage(R.mipmap.no_record, holder.image);
				else if(dev.getDeviceType().contains(MEDIA))
						GlideLoadImage(R.mipmap.sys_window, holder.image);
				else
						GlideLoadImage(R.mipmap.sys_apple, holder.image);

				holder.title.setText(friend_names.get(position).getFriendlyName());
				holder.subtitle.setText("IP : " +  friend_names.get(position).getSSDPPacket().getRemoteAddress());
		}

		@Override
		public int getItemCount() {
				return friend_names.size();
		}

		public interface OnItemClickListener
		{
				void onItemClick(View view, int position);

				void onItemLongClick(View view, int position);
		}

		public OnItemClickListener mOnItemClickListener;

		public void setOnItemClickListener(OnItemClickListener listener)
		{
				this.mOnItemClickListener = listener;
		}
		public void GlideLoadImage(String resId, AppCompatImageView image)
		{
				Glide.with(mContext).
								load(resId).
								thumbnail(1/3).
								fitCenter().
								centerCrop().
								//.override(300,200)    //大小
								//.dontTransform()
								//.dontTransform()
								//.transform(new  )        //动画
								crossFade().  ////动画
								placeholder(R.mipmap.empty_photo).//占位符
								error(R.mipmap.empty_photo).
								//diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
												into(image);
		}
		public void GlideLoadImage(int resId, AppCompatImageView image)
		{
				Glide.with(mContext).
								load(resId).
								thumbnail(1/3).
								fitCenter().
								centerCrop().
								//.override(300,200)    //大小
								//.dontTransform()
								//.dontTransform()
								//.transform(new  )        //动画
												crossFade().  ////动画
								placeholder(R.mipmap.empty_photo).//占位符
								error(R.mipmap.ic_launcher).
								//diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
												into(image);
		}
}
