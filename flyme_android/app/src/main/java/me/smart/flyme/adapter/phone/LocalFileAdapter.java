package me.smart.flyme.adapter.phone;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import me.smart.flyme.R;
import me.smart.flyme.constant.ColorList;
import me.smart.flyme.model.BaseViewHolder;

/**
 * author：wypx on 2016/8/10 22:29
 * blog：smarting.me
 */
public class LocalFileAdapter extends RecyclerView.Adapter<BaseViewHolder>
{

		private Context mContext;
		private LayoutInflater mLayoutInflater;

		public List<String> items=null;//文件名
		public List<String> Itempaths=null;//文件路径

		public LocalFileAdapter(Context mContext,List<String> Itembean,List<String> paths)
		{
				this.items = Itembean;
				this.Itempaths=paths;
				this.mContext = mContext;
				mLayoutInflater = LayoutInflater.from(mContext);

		}
		@Override
		public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View mView = mLayoutInflater.inflate(R.layout.base_file_item_baseview, parent, false);
				BaseViewHolder mViewHolder = new BaseViewHolder(mView);

				return mViewHolder;
		}

		@Override
		public void onBindViewHolder(final BaseViewHolder holder, final int position) {
				if (mOnItemClickListener != null) {
						holder.itemView.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
										mOnItemClickListener.onItemClick(holder.itemView, position);
								}
						});

						holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
								@Override
								public boolean onLongClick(View v) {
										mOnItemClickListener.onItemLongClick(holder.itemView, position);
										return true;
								}
						});

				}
				;


				//得到路径
				File file = new File(Itempaths.get(position).toString());

				//不是根目录，返回上一级
				//根据Item的标题判断
				if (items.get(position).equals("up"))
				{
						GlideLoadImage(ColorList.FileType[11], holder.image);
						holder.title.setText("返回上一级");
						//	viewholder.cbCheckBox.setVisibility(View.GONE);
				} else
				{
						//设置文件名
						holder.title.setText(items.get(position));
						holder.subtitle.setText(" ");
					/* 取得扩展名 */
						String end = file.getName().substring(file.getName().lastIndexOf(".")
										+ 1, file.getName().length()).toLowerCase();
						if (file.isDirectory())
						{
								if (file.getName().equals("emulated"))
								{
										GlideLoadImage(R.mipmap.icon_sdcard_system,holder.image);
								}
								else if (file.getName().equals("sdcard0"))
								{
										GlideLoadImage(R.mipmap.icon_sdcard_sdcard,holder.image);
								}
								else if (file.getName().equals("sdcard1"))
								{
										GlideLoadImage(R.mipmap.icon_sdcard_otg,holder.image);
								}
								else
								{
										//文件夹图标
										GlideLoadImage(ColorList.FileType[1],holder.image);
								}
						}
						/* 依扩展名的类型决定MimeType */
						else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
										end.equals("acc") || end.equals("wma") || end.equals("wav"))
						{
								GlideLoadImage(ColorList.FileType[4],holder.image);
						}
						else if (end.equals("3gp") || end.equals("mp4") || end.equals("wmv") || end.equals("rmvb")
										|| end.equals("flv") || end.equals("mkv"))
						{
								GlideLoadImage(Itempaths.get(position),holder.image);
						}
						else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp"))
						{
								GlideLoadImage(Itempaths.get(position), holder.image);
						}
						else if (end.equals("apk"))
						{
								GlideLoadImage(ColorList.FileType[0],holder.image);
						}
						else if (end.equals("ppt"))
						{
								GlideLoadImage(ColorList.FileType[18],holder.image);
						}
						else if (end.equals("xls"))
						{
								GlideLoadImage(ColorList.FileType[2],holder.image);
						}
						else if (end.equals("excel"))
						{
								GlideLoadImage(ColorList.FileType[16],holder.image);
						}
						else if (end.equals("doc"))
						{
								GlideLoadImage(ColorList.FileType[17],holder.image);
						}
						else if (end.equals("pdf"))
						{
								GlideLoadImage(ColorList.FileType[10],holder.image);
						}
						else if (end.equals("txt"))
						{
								GlideLoadImage(ColorList.FileType[6],holder.image);
						}
						else if (end.equals("html"))
						{
								GlideLoadImage(ColorList.FileType[7],holder.image);
						}
						else if (end.equals("exe"))
						{
								GlideLoadImage(ColorList.FileType[12],holder.image);
						}
						else if (end.equals("zip") || end.equals("rar") || end.equals("7z") || end.equals("cab")
										|| end.equals("iso") || end.equals("tar") || end.equals("gzip") || end.equals("jar"))
						{
								GlideLoadImage(ColorList.FileType[9],holder.image);
						}
						else
						{
								//其他的未知的
								GlideLoadImage(ColorList.FileType[5], holder.image);
						}

				}
		}

		@Override
		public int getItemCount() {
				return items.size();
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
								error(R.mipmap.ic_launcher).
								//diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
												into(image);
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
}
