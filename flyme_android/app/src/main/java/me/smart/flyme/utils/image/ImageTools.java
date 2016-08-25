package me.smart.flyme.utils.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

public class ImageTools {
	// 缩放/裁剪图片
	 	public static Bitmap zoomImg(Bitmap bm, int newWidth , int newHeight)
	 	{ 
	 		// 获得图片的宽高
	  	   int width = bm.getWidth();
	  	   int height = bm.getHeight();
	  	   // 计算缩放比例
	  	   float scaleWidth = ((float) newWidth) / width;
	  	   float scaleHeight = ((float) newHeight) / height;
	  	   // 取得想要缩放的matrix参数
	  	   Matrix matrix = new Matrix();
	  	   matrix.postScale(scaleWidth, scaleHeight);
	  	   // 得到新的图片
	  	   Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	  	   return newbm;
	 	}
	 	
	 	//通过文件路径获取到bitmap
	 	public static Bitmap getBitmapFromPath(String path, int w, int h) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 设置为ture只获取图片大小
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// 返回为空
			BitmapFactory.decodeFile(path, opts);
			int width = opts.outWidth;
			int height = opts.outHeight;
			float scaleWidth = 0.f, scaleHeight = 0.f;
			if (width > w || height > h) {
				// 缩放
				scaleWidth = ((float) width) / w;
				scaleHeight = ((float) height) / h;
			}
			opts.inJustDecodeBounds = false;
			float scale = Math.max(scaleWidth, scaleHeight);
			opts.inSampleSize = (int)scale;
			WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
			return Bitmap.createScaledBitmap(weak.get(), w, h, true);
		}
	 	
	 	//把bitmap转换成base64
	 	public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality)
	 	{
	 		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
	 		bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
	 		byte[] bytes = bStream.toByteArray();
	 		return Base64.encodeToString(bytes, Base64.DEFAULT);
	 	}
	 	
	 	//把base64转换成bitmap
	 	public static Bitmap getBitmapFromBase64(String string)
	 	{
	 		byte[] bitmapArray = null;
	 		try {
	 		bitmapArray = Base64.decode(string, Base64.DEFAULT);
	 		} catch (Exception e) {
	 		e.printStackTrace();
	 		}
	 		return BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
	 	}
	 	
	 	//把Stream转换成String
	 	public static String convertStreamToString(InputStream is) {
	 		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;

				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "/n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return sb.toString();	
	 	}	
	 	
	 	// 修改整个界面所有控件的字体
	 	public static void changeFonts(ViewGroup root,String path, Activity act) {
			  //path是字体路径
	 		Typeface tf = Typeface.createFromAsset(act.getAssets(),path);
	        for (int i = 0; i < root.getChildCount(); i++) {  
	            View v = root.getChildAt(i); 
	            if (v instanceof TextView) {
	               ((TextView) v).setTypeface(tf);  
	            } else if (v instanceof Button) {  
	               ((Button) v).setTypeface(tf);
	            } else if (v instanceof EditText) {  
	               ((EditText) v).setTypeface(tf);
	            } else if (v instanceof ViewGroup) {
	               changeFonts((ViewGroup) v, path,act);  
	            } 
	        }  
	     }
	 	
	 	// 修改整个界面所有控件的字体大小
	  	public static void changeTextSize(ViewGroup root,int size, Activity act) {
	         for (int i = 0; i < root.getChildCount(); i++) {  
	             View v = root.getChildAt(i);
	             if (v instanceof TextView) {  
	                ((TextView) v).setTextSize(size);
	             } else if (v instanceof Button) {  
	            	((Button) v).setTextSize(size);
	             } else if (v instanceof EditText) {  
	            	((EditText) v).setTextSize(size);  
	             } else if (v instanceof ViewGroup) {  
	                changeTextSize((ViewGroup) v,size,act);  
	             }  
	         }  
	      }
	  	
	  	// 不改变控件位置，修改控件大小
		public static void changeWH(View v,int W,int H)
		{
			ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)v.getLayoutParams();
		    params.width = W;
		    params.height = H;
		    v.setLayoutParams(params);
		}
		
		// 修改控件的高
		public static void changeH(View v,int H)
		{
			 ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)v.getLayoutParams();
		    params.height = H;
		    v.setLayoutParams(params);
		}


	 	
		//绘制圆角矩形图片   
		/**
	     *
	     * @param x 图像的宽度
	     * @param y 图像的高度
	     * @param image 源图片
	     * @param outerRadiusRat 圆角的大小
	     * @return 圆角图片
	     */
	    @SuppressWarnings("deprecation")
		Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
	        //根据源文件新建一个darwable对象
	        Drawable imageDrawable = new BitmapDrawable(image);

	        // 新建一个新的输出图片
	        Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);

	        // 新建一个矩形
	        RectF outerRect = new RectF(0, 0, x, y);

	        // 产生一个红色的圆角矩形
	        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        paint.setColor(Color.RED);
	        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

	        // 将源图片绘制到这个圆角矩形上
	        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	        imageDrawable.setBounds(0, 0, x, y);
	        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
	        imageDrawable.draw(canvas);
	        canvas.restore();

	        return output;
	    }
}
