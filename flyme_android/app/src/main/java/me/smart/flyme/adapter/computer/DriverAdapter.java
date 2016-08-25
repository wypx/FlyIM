package me.smart.flyme.adapter.computer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;

import me.smart.flyme.R;
import me.smart.flyme.bean.computer.DiskInfo;
import me.smart.flyme.model.BaseViewHolder;


public class DriverAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    private Context mContext;
    public DiskInfo mDatas;
    private LayoutInflater mLayoutInflater;

    private int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

      //  holder.card.clearAnimation();

    }

    public DriverAdapter(Context mContext)
    {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    /**
     * 创建ViewHolder
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View mView = mLayoutInflater.inflate(R.layout.base_file_item, parent, false);
        BaseViewHolder mViewHolder = new BaseViewHolder(mView);

        return mViewHolder;
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position)
    {
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

        if(mDatas.getDrivers().get(position).getDiskName().contains("C:"))
        {
            Glide.with(mContext).
                    load(R.mipmap.icon_drivers_c).
                    thumbnail(1/3).
                    fitCenter().
                    centerCrop().
                    //.override(300,200)    //大小
                    //.dontTransform()
                    //.transform(new  )        //动画
                            crossFade().  ////动画
                    placeholder(R.mipmap.ic_folder_gray_48dp).//占位符
                    error(R.mipmap.ic_folder_gray_48dp).
                    //diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
                            into(holder.image);
        }
        else
        {
            Glide.with(mContext).
                    load(R.mipmap.icon_driver).
                    thumbnail(1/3).
                    fitCenter().
                    centerCrop().
                    //.override(300,200)    //大小
                    //.dontTransform()
                    //.transform(new  )        //动画
                            crossFade().  ////动画
                    placeholder(R.mipmap.ic_folder_gray_48dp).//占位符
                    error(R.mipmap.ic_folder_gray_48dp).
                    //diskCacheStrategy(DiskCacheStrategy.ALL).//让Glide既缓存全尺寸又缓存其他尺寸
                            into(holder.image);
        }
        if(mDatas.getDrivers().get(position).getDiskName().contains("Desktop"))
        {
            holder.title.setText("我的桌面");
        }
        else if (mDatas.getDiskName(position).contains("C:"))
        {
            holder.title.setText("系统"+ "   ("+mDatas.getDiskName(position)+ ")");
        }
        else if (mDatas.getDiskName2(position).isEmpty())
        {
            holder.title.setText("本地"+ "   ("+mDatas.getDiskName(position)+ ")");
        }
        else
        {
            holder.title.setText(mDatas.getDiskName2(position)+
                    "   ("+mDatas.getDiskName(position)+ ")");
        }
        holder.subtitle.setText("总空间: "+mDatas.getTotalSpace(position)+
                "      剩余空间: "+mDatas.getFreeSpace(position));

//		    holder.cardview.setCardBackgroundColor(ColorList.DriverColor[position % 8]);

        //setAnimation(holder.card, position);
    }

    @Override
    public int getItemCount()
    {
        if(mDatas != null)
            return mDatas.getDriversNum();
        return 0;
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

    /*public void loadFirst() {
            page = 1;
            loadDataByNetworkType();
        }

        public void loadNextPage() {
            page++;
            loadDataByNetworkType();
        }

        private void loadDataByNetworkType() {

            if (isNetWorkConnected(getActivity())) {

                executeRequest(new Request4FreshNews(FreshNews.getUrlFreshNews(page),
                        new Response.Listener<ArrayList<String>>() {
                            @Override
                            public void onResponse(ArrayList<String> response) {

                                mLoadFinisCallBack.loadFinish(null);//�������
                                if (mSwipeRefreshLayout.isRefreshing()) {//ȡ��ˢ��
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }

                                if (page == 1) {
                                    //�������
                                    mAdapter.mImageUrls.clear();
                                }
                                //������ʾ
                                mAdapter.mImageUrls.addAll(response);
                                notifyDataSetChanged();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "����ʧ��", 0).show();
                        mLoadFinisCallBack.loadFinish(null);//�������
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }));
            }
        }*/
}
