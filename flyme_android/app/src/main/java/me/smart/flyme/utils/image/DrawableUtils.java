package me.smart.flyme.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils
{
	//不同状态下设置不同的图片
		@SuppressWarnings({ "deprecation" })
		public static Drawable getBtBack(Context context, int nomalImgId, int pressedImgId)
		{
			Drawable nomalImg = context.getResources().getDrawable(nomalImgId);
			Drawable pressedImg = context.getResources().getDrawable(pressedImgId);
			
	    StateListDrawable stateListDrawable = new StateListDrawable();
			//选中状态
	    stateListDrawable.addState(
	        		new int[]{ android.R.attr.state_checked }, 
	        		pressedImg
	        		);
	    //按下状态
	    stateListDrawable.addState(
	        		new int[]{ android.R.attr.state_pressed,android.R.attr.state_enabled},
	        		pressedImg
	        		);
	    //正常状态
	    stateListDrawable.addState(
	        		new int[]{ android.R.attr.state_enabled},
	        		nomalImg
	        		);
	    //聚焦状态
	    stateListDrawable.addState(
	        		new int[]{ android.R.attr.state_focused,android.R.attr.state_enabled},
	        		nomalImg
	        		);
	    //Unable状态
	    stateListDrawable.addState(
	        		new int[]{ android.R.attr.state_window_focused },
	        		nomalImg
	        		);
	    //正常状态
	    stateListDrawable.addState(
	        		new int[]{ }, 
	        		nomalImg
	        		);
	    //木有选中 ---负值很重要 !
	    stateListDrawable.addState(
	        		new int[]{-android.R.attr.state_checked},
	        		nomalImg
	        		);
	        
	    return stateListDrawable;
	    }
}
