package me.smart.flyme.view.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 设置GridBView不能滚动
 */
public class MyGridView extends GridView
{
    
			public MyGridView(Context context)
			{
				super(context);
			}
			public MyGridView(Context context, AttributeSet attrs)
			{
				super(context, attrs);
			}

			@Override
			public boolean dispatchTouchEvent(MotionEvent ev)
			{

					if (ev.getAction() == MotionEvent.ACTION_MOVE)
					{
					   return true;
					}
					return super.dispatchTouchEvent(ev);
			}

			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
			{
					int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
					super.onMeasure(widthMeasureSpec, expandSpec);
			}

			/**
			 * GridView显示不完成的原因是因为,他的外层也套用了一个滑动的控件,
			 * 我们开发中经常碰到,就是当ListView或者GridView被嵌套在ScrollView
			 * 中时，发现只会显示第一行的数据，后面的数据就不会显示了。产生这个
			 * 问题的原因，可能是因为Gridview和ListView都是根据子item的宽高来
			 * 显示大小的，但一旦嵌套到ScrollView中就可以上下滑动，于是系统就不
			 * 能确定到底该画多大，所以才会产生这样的问题。
			 * 解决的方法就是重写GridView,是控制GridView不能滚动,就是写一个类
			 * 继承GridView
			 */
}
