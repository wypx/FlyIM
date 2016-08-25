package me.smart.flyme.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImgUtil {
	@SuppressWarnings("unused")
	private static final String TAG = "LoadImageUtil";
	private static ImgUtil instance;
	private static HashMap<String, SoftReference<Bitmap>> imgCaches;
	private static ExecutorService executorThreadPool = Executors
			.newFixedThreadPool(1);
	static {
		instance = new ImgUtil();
		imgCaches = new HashMap<String, SoftReference<Bitmap>>();
	}

	public static ImgUtil getInstance() {
		if (instance != null) {
			return instance;
		}
		return null;
	}

	public void loadBitmap(final String path,
			final OnLoadBitmapListener listener) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bitmap bitmap = (Bitmap) msg.obj;
				listener.loadImage(bitmap, path);
			}
		};
		new Thread() {

			@Override
			public void run() {
				executorThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						Bitmap bitmap = loadBitmapFromCache(path);
						if (bitmap != null) {
							Message msg = handler.obtainMessage();
							msg.obj = bitmap;
							handler.sendMessage(msg);
						}

					}
				});
			}

		}.start();
	}

	private Bitmap loadBitmapFromCache(String path) {
		if (imgCaches == null) {
			imgCaches = new HashMap<String, SoftReference<Bitmap>>();
		}
		Bitmap bitmap = null;
		if (imgCaches.containsKey(path)) {
			bitmap = imgCaches.get(path).get();
		}
		if (bitmap == null) {
			bitmap = loadBitmapFromLocal(path);
		}
		return bitmap;
	}

	private Bitmap loadBitmapFromLocal(String path) {
		if (path == null) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		float height = 800f;
		float width = 480f;
		float scale = 1;
		if (options.outWidth > width && options.outWidth > options.outHeight) {
			scale = options.outWidth / width;
		} else if (options.outHeight > height
				&& options.outHeight > options.outWidth) {
			scale = options.outHeight / height;
		} else {
			scale = 1;
		}
		options.inSampleSize = (int) scale;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, options);
		bitmap = decodeBitmap(bitmap);
		if (!imgCaches.containsKey(path)) {
			//imgCaches.put(path, new SoftReference<Bitmap>(bitmap));
			addCache(path, bitmap);
		}
		return bitmap;
	}

	private Bitmap decodeBitmap(Bitmap bitmap) {
		int scale = 100;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
		while ((bos.toByteArray().length / 1024) > 30) {
			bos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, scale, bos);
			scale -= 10;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		bitmap = BitmapFactory.decodeStream(bis);
		return bitmap;
	}
	
	public void addCache(String path,Bitmap bitmap){
		imgCaches.put(path, new SoftReference<Bitmap>(bitmap));
	}
	
	public void reomoveCache(String path){
		imgCaches.remove(path);
	}

	public interface OnLoadBitmapListener {
		void loadImage(Bitmap bitmap, String path);
	}
	
	
	//////////////////////////

	/**
	 * 把图片转成圆角
	 * @param bitmap
	 * @param angle 图角角度 建议0~90
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float angle) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		//final float roundPx = 90;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, angle, angle, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	/**
	 * 处理图片 放大、缩小到合适位置
     * 
     * @param newWidth
     * @param newHeight
     * @param bitmap
     * @return
     */
    public static Bitmap resizeBitmap(float newWidth, float newHeight, Bitmap bitmap) {
    	Matrix matrix = new Matrix();
    	matrix.postScale(newWidth / bitmap.getWidth(), newHeight / bitmap.getHeight());
    	Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    	return newBitmap;
    }
    /**旋转图片
	 * @param source
	 * @return
	 *
	 * @version:v1.0
	 * @author:lanyj
	 * @date:2014-7-8 上午11:58:22
	 */
	public static Bitmap changeRoate(Bitmap source,boolean isHeadCamera) {
		int orientation=90;
		Bitmap bMapRotate=null;
        if(source.getHeight() < source.getWidth()){
            	orientation = 90;
            if(isHeadCamera)
            	orientation=-90;
        } else {
            orientation = 0;
        }
        if (orientation != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            bMapRotate = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
            		source.getHeight(), matrix, true);
        } else {
            return source;
        }
        return bMapRotate;
	}
	/**
	 * 读取本地的图片得到缩略图，如图片需要旋转则旋转。
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static  Bitmap getLocalThumbImg(String path,float width,float height,String imageType){
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / width);
		} else if (w < h && h > height) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / height);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(path, newOpts);
		bitmap = compressImage(bitmap,100,imageType);//压缩好比例大小后再进行质量压缩
		int degree = readPictureDegree(path);
		bitmap = rotaingImageView(degree, bitmap);
		return bitmap;
	}
	/**
	 * 图片质量压缩
	 * @param image
	 * @size 图片大小（kb）
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image, int size,String imageType) {
		try{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(imageType.equalsIgnoreCase("png")) {
			image.compress(Bitmap.CompressFormat.PNG, 100, baos);
		}else {
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		}
		int options = 100;
		while ( baos.toByteArray().length / 1024 > size) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩		
			baos.reset();//重置baos即清空baos
			if(imageType.equalsIgnoreCase("png")) {
				image.compress(Bitmap.CompressFormat.PNG, options, baos);
			}else {
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			}
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	/**
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		if(bitmap == null)
			return null;
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	 /**
     * 获取适应屏幕大小的图 
     */
    public static Bitmap sacleBitmap(Context context, Bitmap bitmap) {
        // 适配屏幕大小
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        float aspectRatio = (float) screenWidth / (float) width;
        int scaledHeight = (int) (height * aspectRatio);
        Bitmap scaledBitmap = null;
        try {
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, scaledHeight, false);
        } catch (OutOfMemoryError e) {
        }
        return scaledBitmap;
    }

		/**
		 * 获得网络图片Bitmap
		 * @param
		 * @return
		 */
		public static Bitmap loadBitmapFromNet(String imageUrlStr) {
				Bitmap bitmap = null;
				URL imageUrl = null;

				if (imageUrlStr == null || imageUrlStr.length() == 0) {
						return null;
				}

				try {
						imageUrl = new URL(imageUrlStr);
						URLConnection conn = imageUrl.openConnection();
						conn.setDoInput(true);
						conn.connect();
						InputStream is = conn.getInputStream();
						int length = conn.getContentLength();
						if (length != -1) {
								byte[] imgData = new byte[length];
								byte[] temp = new byte[512];
								int readLen = 0;
								int destPos = 0;
								while ((readLen = is.read(temp)) != -1) {
										System.arraycopy(temp, 0, imgData, destPos, readLen);
										destPos += readLen;
								}
								bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
						}
				} catch (IOException e) {
						Log.e("TAG", e.toString());
						return null;
				}

				return bitmap;
		}

		/**
		 * 得到压缩图片

		 * @return
		 */
		public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
		                                                     int reqWidth, int reqHeight)
		{
				// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeResource(res, resId, options);
				// 调用上面定义的方法计算inSampleSize值
				options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
				// 使用获取到的inSampleSize值再次解析图片
				options.inJustDecodeBounds = false;
				return BitmapFactory.decodeResource(res, resId, options);
		}

		/**
		 * 计算缩略图压缩的比列，因为每张图片长宽不一样，压缩比列也不一样
		 * @param options
		 * @param reqWidth
		 * @param reqHeight
		 * @return
		 */
		public static int calculateInSampleSize(BitmapFactory.Options options,
		                                        int reqWidth, int reqHeight) {
				// 源图片的高度和宽度
				final int height = options.outHeight;
				final int width = options.outWidth;
				int inSampleSize = 1;
				if (height > reqHeight || width > reqWidth) {
						// 计算出实际宽高和目标宽高的比率
						final int heightRatio = Math.round((float) height / (float) reqHeight);
						final int widthRatio = Math.round((float) width / (float) reqWidth);
						// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
						// 一定都会大于等于目标的宽和高。
						inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
				}
				return inSampleSize;
		}

}
