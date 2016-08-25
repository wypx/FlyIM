package me.smart.flyme.utils.image;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;

/*
 * -----------------本地文件 缩略图方法总结------------------
 *
 * 在android中获取视频文件的缩略图有三种方法：
 *
 * 	1.  从媒体库中查询
	 	新视频增加后需要SDCard重新扫描才能给新增加的文件添加缩略图，
		灵活性差，而且不是很稳定，适合简单应用

	2.  android 2.2以后使用ThumbnailUtils类获取
	       实现简单，但2.2以前的版本不支持

	       其实用的还是 MediaMetadataRetriever
	        参考文章:http://my.oschina.net/kylinhuang/blog/472757

	3.  调用jni文件，实现MediaMetadataRetriever类
	       实现复杂，但比较灵活，推荐使用
	    http://www.2cto.com/kf/201210/161238.html

	    从API 10开始新增一类MediaMetadataRetriever可以用来获取媒体文件的信息
	   MediaMetadataRetriever可以获取视频任何一帧的缩略图。
 *
 */
public class ThumbTools
{
		/**
		 * 获取图片的缩略图
		 * 根据指定的图像路径和大小来获取缩略图
		 * 此方法有两点好处：
		 *   1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
		 *      第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
		 *   2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
		 *      用这个工具生成的图像不会被拉伸。
		 * @param imagePath 图像的路径
		 * @param width 指定输出图像的宽度
		 * @param height 指定输出图像的高度
		 * @return 生成的缩略图
		 */
		//方法2 ---获取图片缩略图 --ThumbnailUtils
		public static Bitmap getImageThumbnail(String imagePath, int width, int height)
		{
				Bitmap bitmap = null;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				// 获取这个图片的宽和高，注意此处的bitmap为null
				bitmap = BitmapFactory.decodeFile(imagePath, options);
				options.inJustDecodeBounds = false; // 设为 false
				// 计算缩放比
				int h = options.outHeight;
				int w = options.outWidth;
				int beWidth = w / width;
				int beHeight = h / height;
				int be = 1;
				if (beWidth < beHeight)
				{
						be = beWidth;
				}
				else
				{
						be = beHeight;
				}
				if (be <= 0)
				{
						be = 1;
				}
				options.inSampleSize = be;
				// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
				bitmap = BitmapFactory.decodeFile(imagePath, options);
				// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
				bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
								ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

				return bitmap;
		}

		/**
		 * 获取视频的缩略图
		 * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
		 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
		 * @param videoPath 视频的路径
		 * @param width 指定输出视频缩略图的宽度
		 * @param height 指定输出视频缩略图的高度度
		 * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
		 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
		 * @return 指定大小的视频缩略图
		 */
		//方法2 ---获取视频缩略图 --ThumbnailUtils
		public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
		                                       int kind) {
				Bitmap bitmap = null;
				// 获取视频的缩略图
				bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
				Log.i("BMP的宽度", "W"+bitmap.getWidth());
				Log.i("BMP的高度", "H"+bitmap.getHeight());

				bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
								ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
				return bitmap;
		}

		//方法1 ---获取视频缩略图 --媒体库查询
		public static Bitmap getVideoThumbnail(Context context, String fileName)
		{
				ContentResolver cr = context.getContentResolver();
				Bitmap bitmap = null;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				String whereClause = MediaStore.Video.Media.DATA+"='"+fileName+"'";

				Cursor cursor = cr.query(
								MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
								new String[] { MediaStore.Video.Media._ID },
								whereClause,
								null,
								null
				);

				if (null == cursor || 0 == cursor.getCount())
				{
						return null;
				}
				cursor.moveToFirst();

				String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));

				if ( null == videoId )
				{
						return null;
				}
				cursor.close();
				long videoIdLong = Long.parseLong(videoId);

				bitmap = MediaStore.Video.Thumbnails.getThumbnail(
								cr,
								videoIdLong,
								MediaStore.Images.Thumbnails.MINI_KIND,
								options);

				return bitmap;
				//  MINI_KIND: 512 x 384
				//  MICRO_KIND: 96 x 96
		}
		public static Bitmap getImageThumbnail(Context context, String fileName)
		{
				ContentResolver cr = context.getContentResolver();
				Bitmap bitmap = null;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				String whereClause = MediaStore.Images.Media.DATA+"='"+fileName+"'";

				Cursor cursor = cr.query(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								new String[] { MediaStore.Images.Media._ID },
								whereClause,
								null,
								null
				);

				if (null == cursor || 0 == cursor.getCount())
				{
						return null;
				}
				cursor.moveToFirst();

				String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));

				if ( null == videoId )
				{
						return null;
				}
				cursor.close();
				long videoIdLong = Long.parseLong(videoId);

				bitmap = MediaStore.Images.Thumbnails.getThumbnail(
								cr,
								videoIdLong,
								MediaStore.Images.Thumbnails.MINI_KIND,
								options);

				return bitmap;
				//  MINI_KIND: 512 x 384
				//  MICRO_KIND: 96 x 96
		}

		//方法三 ---MediaMetadataRetriever
		//Android提供了MediaMetadataRetriever，由JNI(media_jni)实现。
    /*
     * 看得出MediaMetadataRetriever主要有两个功能：
     * MODE_GET_METADATA_ONLY和MODE_CAPTURE_FRAME_ONLY
	 * 这里设mode为MODE_CAPTURE_FRAME_ONLY，调用captureFrame取得一帧。
            另外还有两个方法可以用:
       extractMetadata 提取文件信息:
        ARTIST、DATE、YEAR、DURATION、RATING、FRAME_RATE、VIDEO_FORMAT
              和extractAlbumArt 提取专辑信息，这个下面的音乐文件可以用到。
     */
		//方法3 ---获取视频缩略图 --MediaMetadataRetriever
		public static Bitmap getVideoThumbnail(String filePath) {
				Bitmap bitmap = null;
				MediaMetadataRetriever retriever = new MediaMetadataRetriever();
				try {
						retriever.setDataSource(filePath);
						bitmap = retriever.getFrameAtTime();
	  /*
	   * 其中函数getFrameAtTime()有其他重载函数，该函数会随机选择一帧抓取，
	   * 如果想要指定具体时间的缩略图，可以用函数getFrameAtTime(long timeUs),
	   *  getFrameAtTime(long timeUs, int option)，具体如何使用可以查doc。
	   */
				}
				catch(IllegalArgumentException e) {
						e.printStackTrace();
				}
				catch (RuntimeException e) {
						e.printStackTrace();
				}
				finally {
						try
						{
								retriever.release();
						}
						catch (RuntimeException e) {
								e.printStackTrace();
						}
				}
				return bitmap;
		}
		/**
		 * 获得视频的缩略图
		 *
		 * @param
		 */
		public Bitmap getBitmap(String imgPath) {

				if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 8) {


						return ThumbTools.getVideoThumbnail(imgPath, 60, 60, MediaStore.Video.Thumbnails.MICRO_KIND);

				} else {
						return null;
				}

		}
		//方法3 ---获取音乐,取得AlbumImage缩略图 --MediaMetadataRetriever
		@SuppressWarnings("unused")
		private Bitmap createAlbumThumbnail(String filePath) {
				String TAG = "Music";
				Bitmap bitmap = null;
				MediaMetadataRetriever mmr = new MediaMetadataRetriever();
				try {
						//retriever.setMode(MediaMetadataRetriever.MODE_GET_METADATA_ONLY);
						mmr.setDataSource(filePath);
						// byte[] art =retriever.extractAlbumArt();???
						//mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
						//mmr.getEmbeddedPicture();
						//retriever.extractAlbumArt()得到的是byte数组，
						//还需要一步用BitmapFactory编码得到Bitmap对象
						// bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);


						String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
						Log.d(TAG, "album:" + album);

						Log.d(TAG, "METADATA_KEY_ALBUMARTIST:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST));

						String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
						Log.d(TAG, "artist:" + artist);

						Log.d(TAG, "METADATA_KEY_AUTHOR:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));

						String bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
						// 从api level 14才有，即从ICS4.0才有此功能
						Log.d(TAG, "bitrate:" +bitrate);

						Log.d(TAG, "METADATA_KEY_CD_TRACK_NUMBER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));

						Log.d(TAG, "METADATA_KEY_COMPILATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPILATION));

						Log.d(TAG, "METADATA_KEY_COMPOSER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER));

						Log.d(TAG, "METADATA_KEY_DATE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));

						Log.d(TAG, "METADATA_KEY_DISC_NUMBER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DISC_NUMBER));

						Log.d(TAG, "METADATA_KEY_DURATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

						Log.d(TAG, "METADATA_KEY_GENRE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));

						Log.d(TAG, "METADATA_KEY_HAS_AUDIO:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO));

						Log.d(TAG, "METADATA_KEY_HAS_VIDEO:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO));

						Log.d(TAG, "METADATA_KEY_LOCATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION));

						Log.d(TAG, "METADATA_KEY_MIMETYPE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE));



						Log.d(TAG, "METADATA_KEY_NUM_TRACKS:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS));

						Log.d(TAG, "METADATA_KEY_TITLE:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));

						Log.d(TAG, "METADATA_KEY_VIDEO_HEIGHT:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));

						Log.d(TAG, "METADATA_KEY_VIDEO_ROTATION:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));

						Log.d(TAG, "METADATA_KEY_VIDEO_WIDTH:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));

						Log.d(TAG, "METADATA_KEY_WRITER:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_WRITER));

						Log.d(TAG, "METADATA_KEY_YEAR:" + mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR));
				} catch(IllegalArgumentException ex) {
				} catch (RuntimeException ex) {
				} finally {
						try {
								mmr.release();
						} catch (RuntimeException ex) {
								// Ignore failures while cleaning up.
						}
				}
				return bitmap;
		}


		//知道了video和audio,还有image类型的文件呢,看看这个类ExifInterface
		//sdk官方解释: 这是一个读写JPEG文件中Exif标签的类.
		//方法3 ---获取图片缩略图 --MediaMetadataRetriever
		public  void getPicInfo()
		{
				String path = null;
				ExifInterface exifInterface = null;

				try
				{
						exifInterface = new ExifInterface(path);
				} catch (IOException e) {

				}
				exifInterface.getAttribute(ExifInterface.TAG_MODEL);
		}

}
/*
Bitmap extractThumbnail(Bitmap source, int width, int height, int options)

	创建所需尺寸居中缩放的位图。
	参数
	source： 需要被创造缩略图的源位图对象
	width：　生成目标的宽度
	height： 生成目标的高度
	options：在缩略图抽取时提供的选项
	options 如果options定义为OPTIONS_RECYCLE_INPUT,则回收

Bitmap extractThumbnail(Bitmap source, int width, int height)
	创建所需尺寸居中缩放的位图。
	参数：
	source： 需要被创造缩略图的源位图对象
	width：　生成目标的宽度
	height： 生成目标的高度
 */