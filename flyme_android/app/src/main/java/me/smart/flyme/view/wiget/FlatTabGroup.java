package me.smart.flyme.view.wiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import me.smart.flyme.R;

/**
 * @ClassName: FlatTabGroup
 * @Description: TODO(类似于IOS的分段Tab的控件)
 * @author amor smarting.me
 * @date 2015-10-25 下午2:45:07
 *
 */
@SuppressLint({ "Recycle", "NewApi" })
public class FlatTabGroup extends RadioGroup implements OnClickListener
{
		//按钮点击事件
		private OnItemClickListener listener;

		private int mRadius;
		private int mStroke;
		private int mStrokeColor;//高亮颜色
		private String[] mTabItems = null;
		private float mTextSize;


		//默认选中
		private int checkedItem = 1;

		public FlatTabGroup(Context context)
		{
				super(context);
				isInEditMode();
		}

		public FlatTabGroup(Context context, AttributeSet attrs)
		{

				super(context, attrs);
				isInEditMode();
				this.setOrientation(HORIZONTAL);//设置水平方向
				this.setGravity(Gravity.CENTER_VERTICAL);//居中

				TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlatTabGroup);
				//控件边界颜色  #03a9f4是默认值
				mStrokeColor = array.getColor(R.styleable.FlatTabGroup_tab_border_color, Color.parseColor("#03a9f4"));
				//控件边界线宽度
				mStroke = array.getDimensionPixelSize(R.styleable.FlatTabGroup_tab_border_width, 2);
				//边界的圆角弧度
				mRadius = array.getDimensionPixelOffset(R.styleable.FlatTabGroup_tab_radius, 5);
				//字体大小
				mTextSize = array.getDimensionPixelSize(R.styleable.FlatTabGroup_tab_textSize, 14);

				String contents = array.getString(R.styleable.FlatTabGroup_tab_contents);

				//判断输入内容的有效性
				if(null != contents && 0 != contents.trim().length())
				{
						//标题栏存入数组
						mTabItems = contents.split(",");
				}

				array.recycle();

        /*  从string-array中获取Tab Item的值
        	int id = array.getResourceId(R.styleable.FlatTabGroup_tab_items, 0);
        	mItemString = isInEditMode() ? new String[]{"猪婆", "小黑", "摸摸"} :
        	        				context.getResources().getStringArray(id);

        */

				generateTabView(context, attrs);

				updateChildBackground();

		}
		//生成TAB 布局视图
		private void generateTabView(Context context, AttributeSet attrs)
		{
				if ( null == mTabItems )
				{
						return ;
				}
				//循环读取TAB的值
				for (String text : mTabItems)
				{
						//为每个button设置属性:字体颜色,大小和背景,以及字符值
						RadioButton button = new RadioButton(context, attrs);
						button.setGravity(Gravity.CENTER);
						button.setButtonDrawable(android.R.color.transparent); //android.R.color.transparent
						button.setText(text);
						button.setPadding(10, 10, 10, 10);
						//#10B9F4         #03a9f4
						button.setTextColor(setTextColorList("#10B9F4"));//设置不同状态下的颜色变化
						button.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
						button.setTypeface(null, Typeface.NORMAL);
						button.setOnClickListener(this);

						LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,2);

						params.weight = 2.0f;
						params.width = 2;

						//第一个按钮不能设置左边距，不然会变形的
						if(!text.equals( mTabItems[0] ))
						{
								//可以设置中间的间隔,不然看起来觉得中间太粗的
								params.setMargins(-1, 0, 0, 0);
								button.setChecked(false);
						}
						else
						{
								//默认第一个选中
								button.setChecked(true);
						}
						button.setLayoutParams(params);
						addView(button, params);
				}
		}

		@Override
		protected void onFinishInflate()
		{
				super.onFinishInflate();
				updateChildBackground();
		}
		//设置不同状态下的背景颜色
		public void updateChildBackground()
		{
				for (int position = 0; position < getChildCount(); position++)
				{
						View child = getChildAt(position);
						if (child instanceof RadioButton)
						{
								child.setBackground(SetRadioButtonBackground(position,
												Color.parseColor("#10B9F4"), Color.WHITE));
						}
				}
		}
		//设置Text的不同状态的颜色
		private ColorStateList setTextColorList(String colorString)
		{
				int pressed = Color.parseColor(colorString);
				int focused = Color.parseColor(colorString);
				int checked = Color.parseColor(colorString);
				int normal = Color.WHITE;
				int unable = Color.WHITE;


				ColorStateList colorList = new ColorStateList(
								new int[][]
												{
														{	-android.R.attr.state_checked,android.R.attr.state_enabled	},
														{	android.R.attr.state_checked	},
														{ android.R.attr.state_focused	},
														{ android.R.attr.state_window_focused	},//unable
														{ android.R.attr.state_enabled 	},
														{ android.R.attr.state_pressed 	}
												},
								new int[]
												{
														normal,
														checked,
														focused,
														unable,
														normal,
														pressed
												}
				);

				return colorList;
		}
		//根据不同的位置来设置背景颜色和Shape属性:圆角弧度边线
		private Drawable SetRadioButtonBackground(int position, int TxPressedColor, int TxNormalColor)
		{
				StateListDrawable stateListDrawable = new StateListDrawable();
				//选中状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_checked },
								GetDrawable( position, TxPressedColor,TxNormalColor,0)
				);
				//按下状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_pressed,android.R.attr.state_enabled},
								GetDrawable( position, TxPressedColor,TxNormalColor,0)//white
				);
				//正常状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_enabled},
								GetDrawable( position, TxPressedColor,TxNormalColor, 1)
				);
				//聚焦状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_focused,android.R.attr.state_enabled},
								GetDrawable( position, TxPressedColor,TxNormalColor,0)
				);
				//Unable状态
				stateListDrawable.addState(
								new int[]{android.R.attr.state_window_focused},
								GetDrawable( position, TxPressedColor, TxNormalColor,1)
				);
				//正常状态
				stateListDrawable.addState(
								new int[]{ },
								GetDrawable( position, TxPressedColor,TxNormalColor,1)
				);
				//木有选中 ---负值很重要 !
				stateListDrawable.addState(
								new int[]{-android.R.attr.state_checked},
								GetDrawable( position, TxPressedColor,TxNormalColor,1)
				);

				return stateListDrawable;
		}
		/*
	 *
	 * 设置RadioButton的背景颜色  和 圆角弧度
	 * 根据位置判断   弧度角度  边界宽度和边界颜色
	 *  mRadius  5dp
	 */
		private Drawable GetDrawable(int position, int TxPressedColor, int TxNormalColor,int mode)
		{
				float[] radius;
				//充分考虑只有一个按钮的情况
				if(1 == mTabItems.length)
				{
						radius=new float[]
										{
												mRadius,mRadius,
												mRadius,mRadius,
												mRadius,mRadius,
												mRadius,mRadius,
										};

				}
				else
				{
						//多个按钮的情况
						//第一个位置
						if (0 == position)
						{
								//左边两个角
								radius = new float[]
												{
														mRadius, mRadius,
														0, 0,
														0, 0,
														mRadius, mRadius
												};
						}
						//最后一个位置
						else if (position == getChildCount() - 1)
						{
								//右边两个角
								radius = new float[]
												{
														0, 0,
														mRadius, mRadius,
														mRadius, mRadius,
														0, 0
												};
						}
						//其他位置
						else
						{
								//中间的其他保持矩形
								radius = new float[]
												{
														0, 0,
														0, 0,
														0, 0,
														0, 0
												};
						}
				}

				//设置圆角和颜色属性
				GradientDrawable shape = new GradientDrawable();

				//setCornerRadius是设置shape中的圆角半径
				//如果要设置单个角的弧度，可以用setCornerRadii（）设置每个角的弧度
				shape.setCornerRadii(radius);
				//setColor等于shape中的填充色
				if(mode == 1)
					shape.setColor(TxPressedColor);
				else
						shape.setColor(TxNormalColor);

				// shape.setGradientCenter(x, y);  渐变

				//setStroke（）是描边，需要填写描边的宽和边的颜色
				// shape.setStroke(width, colorStateList)  描边
				 shape.setStroke(mStroke, TxNormalColor);//WHITE

				//边线
				//shape.setStroke(mStroke, mStrokeColor);
				return shape;
		}

		/**
		 * @Title: setOnItemClickListener
		 * @Description: TODO( 按钮点击回调接口)
		 * @param @param onItemClickListener  设置回掉接口
		 * @return void    返回类型
		 * @throws
		 */
		public void setOnItemClickListener(OnItemClickListener onItemClickListener)
		{
				this.listener = onItemClickListener;
		}

		//Item点击接口
		public interface OnItemClickListener
		{
				void onItemClick(RadioButton item,int checkedItem);
		}


		@Override
		public void onClick(View v)
		{
				//int count  = getChildCount();
				for(int i = 0;i < mTabItems.length; i++)
				{
						RadioButton  child = (RadioButton) getChildAt(i);
						if(v.equals(child)) //v instanceof child
						{
								checkedItem = i;
								child.setChecked(true);
						}
						else
						{
								child.setChecked(false);
						}

						child.postInvalidate();
				}
				if(listener != null)
				{
						listener.onItemClick((RadioButton)v, checkedItem);
				}
		}

		public void  setButtonCheck(int index)
		{
				for(int i = 0;i < mTabItems.length; i++)
				{
						RadioButton  child = (RadioButton) getChildAt(i);
						if(index == i)
						{
								checkedItem = i;
								child.setChecked(true);
						}
						else
						{
								child.setChecked(false);
						}

						child.postInvalidate();
				}
		}
}
