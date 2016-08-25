package me.smart.flyme.utils.view;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 *  视图 工具类
 */
public class ViewUtils
{
		/**
		 * 修改普通View的高<br>
		 * Adapter---getView方法中慎用
		 */
		public static void changeH(View v, int H)
		{
				LayoutParams params = (LayoutParams) v.getLayoutParams();
				params.height = H;
				v.setLayoutParams(params);
		}

		/**
		 * 修改普通View的宽
		 * Adapter---getView方法中慎用
		 */
		public static void changeW(View v, int W)
		{
				LayoutParams params = (LayoutParams) v.getLayoutParams();
				params.width = W;
				v.setLayoutParams(params);
		}

		/**
		 * 修改控件的宽高
		 * Adapter---getView方法中慎用
		 * @param v
		 * @param W
		 * @param H
		 */
		public static void changeWH(View v, int W, int H)
		{
				LayoutParams params = (LayoutParams) v.getLayoutParams();
				params.width = W;
				params.height = H;
				v.setLayoutParams(params);
		}

		/**
		 * 设置视图是否显示
		 * @param v
		 * @param isDisplay
		 */
		public static  void setViewDisplay(View v,boolean isDisplay)
		{
				//如果需要显示
				if(isDisplay)
				{
						//如果是不可见的,则设置,否则不需要.
						if(View.GONE == v.getVisibility())
						{
								v.setVisibility(View.VISIBLE);
						}
				}
				else
				{
						//不需要显示,如果是显示的话,则设置为不显示
						if(View.VISIBLE == v.getVisibility())
						{
								v.setVisibility(View.GONE);
						}
				}
		}
		/**
		 * 设置Text的值
		 * @param view
		 * @param id
		 * @param text
		 * @return
		 */
		public static boolean setText(View view, int id, String text)
		{
				AppCompatTextView textView = (AppCompatTextView) view.findViewById(id);
				if (null == textView)
				{
						return false;
				}
				textView.setText(text);
				return true;
		}

}	
