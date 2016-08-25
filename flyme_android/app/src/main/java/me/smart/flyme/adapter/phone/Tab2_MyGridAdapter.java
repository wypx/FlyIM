package me.smart.flyme.adapter.phone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import me.smart.flyme.R;
import me.smart.flyme.bean.phone.BaseViewHolder;

public class Tab2_MyGridAdapter extends BaseAdapter
{
		private Context mContext;
		private TranslateAnimation taLeft, taRight, taTop, taBlow;

		public String[] img_text = {
						"图片", "音频", "视频",
						"文档", "压缩包", "应用",
						"最近访问", "收藏书签", "保险柜子" , };
		public int[] imgs =
						{
								R.mipmap.ic_photo_size_select_actual_black_48dp,
								R.mipmap.ic_music_note_black_48dp,
								R.mipmap.ic_movie_creation_black_48dp,

								R.mipmap.ic_print_black_48dp,
								R.mipmap.ic_high_quality_black_48dp,
								R.mipmap.ic_widgets_black_48dp,


								R.mipmap.ic_adb_black_48dp,
								R.mipmap.ic_star_black_48dp,
								R.mipmap.ic_party_mode_black_48dp,
						};

		public Tab2_MyGridAdapter(Context mContext)
		{
				super();
				this.mContext = mContext;
				InitAnima();
		}

		@Override
		public int getCount() {

				return img_text.length;
		}

		@Override
		public Object getItem(int position) {
				return position;
		}

		@Override
		public long getItemId(int position) {
				return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
						convertView = LayoutInflater.from(mContext).inflate(
										R.layout.tab2_classify_gridview_item, parent, false);
				}
				ImageView iv = BaseViewHolder.get(convertView, R.id.ItemImg);
				TextView tv = BaseViewHolder.get(convertView, R.id.ItemTV);

				//iv.setBackgroundResource(imgs[position]);
				iv.setImageResource(imgs[position]);
				tv.setText(img_text[position]);


				Random ran = new Random();
				int rand = ran.nextInt(4);
				switch (rand)
				{
						case 0:
								convertView.startAnimation(taLeft);
								break;
						case 1:
								convertView.startAnimation(taRight);
								break;
						case 2:
								convertView.startAnimation(taTop);
								break;
						case 3:
								convertView.startAnimation(taBlow);
								break;
				}

				return convertView;
		}
		//GridView的item从上下左右飞入
		private void InitAnima() {
				taLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f);
				taRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f);
				taTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 1.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f);
				taBlow = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f,
								Animation.RELATIVE_TO_PARENT, -1.0f,
								Animation.RELATIVE_TO_PARENT, 0.0f);
				taLeft.setDuration(1000);
				taRight.setDuration(1000);
				taTop.setDuration(1000);
				taBlow.setDuration(1000);
		}
}
