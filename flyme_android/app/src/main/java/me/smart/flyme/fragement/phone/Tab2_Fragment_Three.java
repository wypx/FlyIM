package me.smart.flyme.fragement.phone;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;

import me.smart.flyme.R;
import me.smart.mylibrary.FlycoDialog.animation.BaseAnimatorSet;
import me.smart.mylibrary.FlycoDialog.animation.BounceEnter.BounceTopEnter;
import me.smart.mylibrary.FlycoDialog.animation.SlideExit.SlideBottomExit;
import me.smart.mylibrary.FlycoDialog.dialog.entity.DialogMenuItem;
import me.smart.mylibrary.FlycoDialog.dialog.listener.OnOperItemClickL;
import me.smart.mylibrary.FlycoDialog.dialog.widget.NormalListDialog;
import me.smart.mylibrary.goodview.GoodView;


@SuppressLint("InflateParams")
public class Tab2_Fragment_Three extends Fragment {

		ExpandableListView mElv;
		private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();
		private String[] mStringItems = {"收藏", "下载", "分享", "删除", "歌手", "专辑"};
		private BaseAnimatorSet mBasIn;
		private BaseAnimatorSet mBasOut;

		public Tab2_Fragment_Three() {
		}

		public void setBasIn(BaseAnimatorSet bas_in) {
				this.mBasIn = bas_in;
		}

		public void setBasOut(BaseAnimatorSet bas_out) {
				this.mBasOut = bas_out;
		}

		private View contentView;// 缓存Fragment view

		GoodView mGoodView;
		public NormalListDialog dialog2;
		private Button btnSend;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				if (contentView == null)
				{
						contentView = inflater.inflate(R.layout.tab2_fragment_three, null);
				}
				// 缓存的rootView需要判断是否已经被加过parent，
				//如果有parent需要从parent删除，
				//要不然会发生这个rootview已经有parent的错误。
				ViewGroup parent = (ViewGroup) contentView.getParent();
				if (parent != null)
				{
						parent.removeView(contentView);
				}
				mGoodView = new GoodView(getActivity());
				ImageView image = (ImageView)contentView.findViewById(R.id.good2);
				image.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
								good(v);
						}
				});


				mMenuItems.add(new DialogMenuItem("收藏", R.mipmap.ic_winstyle_favor));
				mMenuItems.add(new DialogMenuItem("下载", R.mipmap.ic_winstyle_download));
				mMenuItems.add(new DialogMenuItem("分享", R.mipmap.ic_winstyle_share));
				mMenuItems.add(new DialogMenuItem("删除", R.mipmap.ic_winstyle_delete));
				mMenuItems.add(new DialogMenuItem("歌手", R.mipmap.ic_winstyle_artist));
				mMenuItems.add(new DialogMenuItem("专辑", R.mipmap.ic_winstyle_album));
				dialog2 = new NormalListDialog(getActivity(), mMenuItems);

				mBasIn = new BounceTopEnter();
				mBasOut = new SlideBottomExit();


				btnSend = (Button)contentView.findViewById(R.id.btnSend);
				btnSend.setOnClickListener(new OnClickListener()
				{
						@Override
						public void onClick(View v)
						{

								dialog2.title("请选择")//
												.titleTextSize_SP(18)//
												.titleBgColor(Color.parseColor("#409ED7"))//
												.itemPressColor(Color.parseColor("#85D3EF"))//
												.itemTextColor(Color.parseColor("#303030"))//
												.itemTextSize(14)//
												.cornerRadius(0)//
												.widthScale(0.8f)//
												.show(R.style.myDialogAnim);

								dialog2.setOnOperItemClickL(new OnOperItemClickL()
								{
										@Override
										public void onOperItemClick(AdapterView<?> parent, View view,
										                            int position, long id)
										{
//						T.showShort(mContext, mMenuItems.get(position).mOperName);
												dialog2.dismiss();
										}
								});
						}
				});




				return contentView;
		}


		public void good(View view) {
				((ImageView) view).setImageResource(R.mipmap.good_checked);
				mGoodView.setText("+1");
				mGoodView.show(view);
		}

		public void good2(View view) {
				((ImageView) view).setImageResource(R.mipmap.good_checked);
				mGoodView.setImage(getResources().getDrawable(R.mipmap.good_checked));
				mGoodView.show(view);
		}




}
