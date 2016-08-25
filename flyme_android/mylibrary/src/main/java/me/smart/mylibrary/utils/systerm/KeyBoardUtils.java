package me.smart.mylibrary.utils.systerm;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/* 软键盘工具类*/
public class KeyBoardUtils {
		/**
		 * 打卡软键盘
		 */
		public static void openKeybord(EditText mEditText, Context mContext) {
				InputMethodManager imm = (InputMethodManager) mContext
								.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
								InputMethodManager.HIDE_IMPLICIT_ONLY);
		}

		/**
		 * 关闭软键盘
		 */
		public static void closeKeybord(EditText mEditText, Context mContext)
		{
				InputMethodManager imm = (InputMethodManager) mContext
								.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		}
		/**
		 * 隐藏软键盘
		 */
		public static void hideSoftKeybord(Activity activity)
		{

				if (null == activity)
				{
						return;
				}
				try
				{
						final View v = activity.getWindow().peekDecorView();
						if (v != null && v.getWindowToken() != null)
						{
								InputMethodManager imm = (InputMethodManager) activity
												.getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						}
				}
				catch (Exception e)
				{

				}
		}
}
