package me.smart.flyme.bean.phone;


import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.smart.flyme.R;

/**
 * author：wypx on 2015/12/20 00:49
 * blog：smarting.me
 */
public class VideoHolder extends RecyclerView.ViewHolder
{
		public CardView cardView;
		public AppCompatImageView img_thumb;
		public AppCompatTextView tv_tiltle;

		public VideoHolder(View itemView)
		{
				super(itemView);
				cardView = (CardView)itemView.findViewById(R.id.cardview_base_item);
				img_thumb = (AppCompatImageView)itemView.findViewById(R.id.base_item_thumb);
				tv_tiltle = (AppCompatTextView) itemView.findViewById(R.id.base_item_title);
		}
}
