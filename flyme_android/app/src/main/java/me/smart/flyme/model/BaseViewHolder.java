package me.smart.flyme.model;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.smart.flyme.R;

/**
 * author：wypx on 2016/8/9 00:48
 * blog：smarting.me
 */
public class BaseViewHolder  extends RecyclerView.ViewHolder {

		public CardView           cardview;
		public AppCompatImageView image;
		public AppCompatTextView  title;
		public AppCompatTextView  subtitle;
		//public AppCompatCheckBox  check;  //opt

		public BaseViewHolder(View itemView)
		{
				super(itemView);
//				cardview = (CardView)itemView.findViewById(R.id.cardview_list_item);
				image = (AppCompatImageView)itemView.findViewById(R.id.base_item_file_image);
				title = (AppCompatTextView) itemView.findViewById(R.id.base_item_file_title);
				subtitle = (AppCompatTextView)itemView.findViewById(R.id.base_item_file_subtitle);
		//		check = (AppCompatCheckBox)itemView.findViewById(R.id.base_item_cbCheckBox);
		}
}
