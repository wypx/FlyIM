package me.smart.flyme.utils.view;

/**
 * author：wypx on 2015/12/24 21:49
 * blog：smarting.me
 */
//Palette从图像中提取突出的颜色,这样可以把色值赋给ActionBar、或者其他,可以让界面整个色调统一
//Palette类可以分析一张图片,取出这张图片的特征色,然后为View中的文字,背景等设置颜色,看上去更和谐更美观。

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

/**
 *   Palette这个类中提取以下突出的颜色：
 *   Vibrant  （有活力）
 *   Vibrant dark（有活力 暗色）
 *   Vibrant light（有活力 亮色）
 *   Muted  （柔和）
 *   Muted dark（柔和 暗色）
 *   Muted light（柔和 亮色）
 */
public class ToolbarTools
{
		public static void colorChange(final Activity context, int position,
		                               int res, final Toolbar mToolbar,final View v)
		{
				//Palette p = Palette.generate(bitmap);//默认
				//Palette p = Palette.generate(bitmap, 24);
				// 用来提取颜色的Bitmap
				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),res);
				// Palette的部分
				Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener()
				{
						//提取完之后的回调方法
						@Override
						public void onGenerated(Palette palette)
						{
								//创建完实例后,我们还需要得到一种采集的样本（swatch),有6中样本（swatch）
								Palette.Swatch vibrant = palette.getVibrantSwatch();
								Palette.Swatch dark = palette.getDarkVibrantSwatch();
								Palette.Swatch light = palette.getLightVibrantSwatch();

								//使用样本（swatch）
                /* 界面颜色UI统一性处理,看起来更Material一些 */
								if(null != vibrant)
								{
										v.setBackgroundColor(vibrant.getRgb());
										mToolbar.setBackgroundColor(vibrant.getRgb());
										if (android.os.Build.VERSION.SDK_INT >= 21)
										{
												final Window window = context.getWindow();
												// 很明显，这两货是新API才有的。
												window.setStatusBarColor(colorBurn(vibrant.getRgb()));
												window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
										}

								}

						}
				});
		}

		/**
		 * 颜色加深处理
		 *
		 * @param RGBValues
		 *    RGB的值,由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
		 *     Android中我们一般使用它的16进制，
		 *     例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
		 *     red,green,blue.每种颜色值占一个字节(8位),值域0~255
		 *     所以下面使用移位的方法可以得到每种颜色的值,然后每种颜色值减小一下，
		 *     在合成RGB颜色,颜色就会看起来深一些了
		 * @return
		 */
		private static int colorBurn(int RGBValues)
		{
				int alpha = RGBValues >> 24;
				int red = RGBValues >> 16 & 0xFF;
				int green = RGBValues >> 8 & 0xFF;
				int blue = RGBValues & 0xFF;
				red = (int) Math.floor(red * (1 - 0.1));
				green = (int) Math.floor(green * (1 - 0.1));
				blue = (int) Math.floor(blue * (1 - 0.1));
				return Color.rgb(red, green, blue);
		}
}
