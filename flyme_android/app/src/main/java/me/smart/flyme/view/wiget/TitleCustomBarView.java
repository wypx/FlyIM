package me.smart.flyme.view.wiget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.smart.flyme.R;
import me.smart.flyme.utils.view.ViewUtils;

/**
 * @ClassName: TitleCustomBarView
 * @Description: TODO(模拟ToolBar的类,更方便的使用改变颜色)
 * @author amor smarting.me
 * @date 2015-11-28 下午4:55:02
 *
 */
public class TitleCustomBarView extends RelativeLayout
{
		private TitleCustomClicker  titleCustomClicker;

		private Context mContext;
		private Activity activity;
		private ImageView img_back;
		private ImageView img_more;
		private ImageView img_sub1;
		private ImageView img_sub2;
		private CustomTextView tv_centertltle;

		private RelativeLayout title_background;
		private TitleBarPopWd titleBarPopWd;



		public TitleCustomBarView(Context context)
		{
				super(context);
				mContext = context;
				initView();

		}
		public TitleCustomBarView(Context context, AttributeSet attrs)
		{
				super(context, attrs);
				mContext = context;
				initView();

		}
		//初始化控件各个部分
		private void initView()
		{
				LayoutInflater.from(mContext).inflate(R.layout.title_custom_bar_view, this);
				img_back =(ImageView)findViewById(R.id.title_img_back);
				img_more =(ImageView)findViewById(R.id.title_img_more);
				img_sub1 =(ImageView)findViewById(R.id.title_img_sub1);
				img_sub2 =(ImageView)findViewById(R.id.title_img_sub2);
				tv_centertltle = (CustomTextView)findViewById(R.id.title_tv_centertltle);
				title_background=(RelativeLayout)findViewById(R.id.title_background);


		}
		/*
		 * 改变TitleBar的背景色
		 */
		public void setBackColor(String color)
		{

				title_background.setBackgroundColor(Color.parseColor(color));
		}
		/**
		 * 设置返回键是否可用,设置返回键是否可用,
		 */
		public void setDisplayHomeBack(int resId, boolean isDisplay)
		{
				ViewUtils.setViewDisplay(img_back,isDisplay);
				if(isDisplay)
				{
						if(0 != resId)
						{
								img_back.setImageResource(resId);
						}
				}
		}


		/**
		 * 设置标题
		 */
		public void setTitle(String title)
		{
				if(title != null && !title.equals(""))
				{
						tv_centertltle.setText(title);
				}
		}
		/*
		 * 设置附1的图片
		 */
		public void setDisplaySub1(int resId,boolean isDisplay)
		{
				ViewUtils.setViewDisplay(img_sub1,isDisplay);
				if(isDisplay)
				{
						if(0 != resId)
						{
								img_sub1.setImageResource(resId);
						}
				}
		}
		/*
		 * 设置附2的图片
		 */
		public void setDisplaySub2(int resId,boolean isDisplay)
		{
				ViewUtils.setViewDisplay(img_sub2,isDisplay);
				if(isDisplay )
				{
						if(0 != resId)
						{
								img_sub2.setImageResource(resId);
						}
				}

		}
		/*
		 * 设置菜单键的可见度
		 */
		public void setDisplayMore (int resId,boolean isDisplay)
		{
				ViewUtils.setViewDisplay(img_more,isDisplay);
		}

		public void setTitleCustomClicker(TitleCustomClicker titleCustomClicker)
		{

				AlphaAnimation aa=new AlphaAnimation(0, 1);
				aa.setDuration(1000);
				this.setAnimation(aa);
				this.titleCustomClicker = titleCustomClicker;
		}
		/*
		 * 点击事件回调
		 */
		public interface TitleCustomClicker
		{
				public void doBackClick();
				public void doSub1Click();
				public void doSub2Click();
				public void doMoreClick();

		}
		/*
		 * 设置当前的Activity,PopWindow需要使用
		 */
		public void setActivity(Activity activity)
		{
				this.activity = activity;
				//不能用实现OnClickListener接口的方式,本类本来就是一个View.
				setBaseClicker(img_back, titleCustomClicker);
				setBaseClicker(img_more, titleCustomClicker);
				setBaseClicker(img_sub1, titleCustomClicker);
				setBaseClicker(img_sub2, titleCustomClicker);
		}

		public void setBaseClicker(final View view, final TitleCustomClicker mtitleCustomClicker)
		{

				view.setOnClickListener(new View.OnClickListener()
				{
						@Override
						public void onClick(View v)
						{
								if(view == img_back)
								{
										titleCustomClicker.doBackClick();
								}
								else if(view == img_more)
								{
										titleCustomClicker.doMoreClick();
										titleBarPopWd =new TitleBarPopWd(activity);
										titleBarPopWd.setAnimationStyle(R.anim.window_show_anim);
										//performShowAnimation(titleBarPopWd);
										titleBarPopWd.showPopupWindow(img_more);
								}
								else if (view == img_sub1)
								{
										titleCustomClicker.doSub1Click();
								}
								else
								{
										titleCustomClicker.doSub2Click();
								}
						}
				});
		}
		//弹出效果
		private void performShowAnimation(View v)
		{
				v.setPivotX(v.getWidth() / 2);
				v.setPivotY(v.getHeight());
				v.setScaleX(0.1f);
				v.setScaleY(0.1f);
				v.animate()
								.scaleX(1f).scaleY(1f)
								.setDuration(150)
								.setInterpolator(new OvershootInterpolator())
								.setListener(new AnimatorListenerAdapter()
								{
										@Override
										public void onAnimationEnd(Animator animation)
										{
												final boolean isContextMenuShowing = false;
										}
								});
		}

		private void performDismissAnimation(final View v)
		{
				v.setPivotX(v.getWidth() / 2);
				v.setPivotY(v.getHeight());
				v.animate()
								.scaleX(0.1f).scaleY(0.1f)
								.setDuration(150)
								.setInterpolator(new AccelerateInterpolator())
								.setStartDelay(100)
								.setListener(
												new AnimatorListenerAdapter()
												{
														@Override
														public void onAnimationEnd(Animator animation)
														{
																if (v != null)
																{
																	//	contextMenuView.dismiss();
																}
																//isContextMenuDismissing = false;
														}
												});
		}
}
