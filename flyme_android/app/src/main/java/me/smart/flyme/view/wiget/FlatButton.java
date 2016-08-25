package me.smart.flyme.view.wiget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import me.smart.flyme.R;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FlatButton extends AppCompatButton
{

//	private int normalbgcolor;   //没有按下的背景颜色
//
//	private int pressedbgcolor;  //按下的背景颜色
//
//	private int mRadius; //角的弧度

		private ColorStateList mTextColor;
		private int mTextSize;
		private String mText;

		public FlatButton(Context context)
		{
				super(context);

		}
		public FlatButton(Context context, AttributeSet attrs)
		{
				super(context, attrs);


				//自定义控件的属性输入
				TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlatButton);

//    //36D4A6
//    normalbgcolor = array.getColor(R.styleable.RectangleButton_button_normalbgcolor, Color.rgb(54, 228, 166));
//    //FFCC99
//    pressedbgcolor = array.getColor(R.styleable.RectangleButton_button_pressedbgcolor, Color.rgb(255, 204, 153));

//
//    //边界的圆角弧度
//    mRadius = array.getDimensionPixelOffset(R.styleable.RectangleButton_button_radius, 5);
				//字体颜色
				mTextColor = array.getColorStateList(R.styleable.FlatButton_button_textColor);
				//字体大小
				mTextSize = array.getDimensionPixelSize(R.styleable.FlatButton_button_textSize, 14);

				mText = array.getString(R.styleable.FlatButton_button_text);

				array.recycle();

				SetButtonView(context,attrs);

		}





		private void SetButtonView(Context context, AttributeSet attrs)
		{

				this.setText(mText);
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		}

		/**
		 *  动态设置Selector,灵活减少xml的编写
		 *
		 *  设置不同状态下的背景颜色 和圆角弧度的大小
		 *
		 *  圆形按钮
		 *
		 */
		public static Drawable SetCircleBackground( int color,int mRadius)
		{

				StateListDrawable stateListDrawable = new StateListDrawable();
				//选中状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_checked},
								SetDrawable(Color.rgb(255, 204, 153),85)
				);
				//按下状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_pressed,android.R.attr.state_enabled},
								SetDrawable(Color.rgb(255, 204, 153),85)
				);
				//正常状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_enabled},
								SetDrawable( color,mRadius)
				);
				//聚焦状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_focused,android.R.attr.state_enabled},
								SetDrawable( color,mRadius)
				);
				//Unable状态
				stateListDrawable.addState(
								new int[]{android.R.attr.state_window_focused},
								SetDrawable(Color.rgb(255, 204, 153),mRadius)
				);
				//正常状态
				stateListDrawable.addState(
								new int[]{},
								SetDrawable(color ,mRadius)
				);


				return stateListDrawable;
		}

		/**
		 * 设置FlatButton按钮的样式
		 * @param normalcolor
		 * @param mRadius  9dp
		 * @return
		 */
		public static Drawable setRetangeBackground(int normalcolor,int pressedcolor,int mRadius)
		{

				StateListDrawable stateListDrawable = new StateListDrawable();
				//选中状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_checked},
								SetDrawable(pressedcolor, mRadius)
				);
				//按下状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_pressed,android.R.attr.state_enabled},
								SetDrawable(pressedcolor, mRadius)
				);
				//正常状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_enabled},
								SetDrawable( normalcolor,mRadius)
				);
				//聚焦状态
				stateListDrawable.addState(
								new int[]{ android.R.attr.state_focused,android.R.attr.state_enabled},
								SetDrawable( normalcolor,mRadius)
				);
				//Unable状态
				stateListDrawable.addState(
								new int[]{android.R.attr.state_window_focused},
								SetDrawable(Color.parseColor("#57b1a5"),mRadius)
				);
				//正常状态
				stateListDrawable.addState(
								new int[]{},
								SetDrawable(normalcolor ,mRadius)
				);
				//木有选中 ---负值很重要 !
				stateListDrawable.addState(
								new int[]{-android.R.attr.state_checked},
								SetDrawable( normalcolor, mRadius)
				);

				return stateListDrawable;
		}
		/*
		 *
		 * 设置Button的背景颜色  和 圆角弧度
		 *  mRadius  25dp
		 */
		private static Drawable SetDrawable(int color ,int mRadius)
		{
				float[] radius;
				radius = new float[]
								{
												mRadius, mRadius,
												mRadius, mRadius,
												mRadius, mRadius,
												mRadius, mRadius
								};

				//设置圆角和颜色属性
				GradientDrawable shape = new GradientDrawable();
				shape.setCornerRadii(radius);
				shape.setColor(color);
				//设置边线的宽度和颜色
				// shape.setStroke(1, normalbgcolor);

				return shape;
		}


}
