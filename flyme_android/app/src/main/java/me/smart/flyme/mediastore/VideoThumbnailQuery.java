package me.smart.flyme.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * author：wypx on 2015/12/22 22:09
 * blog：smarting.me
 */
public class VideoThumbnailQuery
{
		private static final String[] PATH_PROJECTION =
		{
				MediaStore.Video.Thumbnails.DATA
		};
		private static final String PATH_SELECTION =
						MediaStore.Video.Thumbnails.KIND + " = " + MediaStore.Video.Thumbnails.MINI_KIND
										+ " AND " + MediaStore.Video.Thumbnails.VIDEO_ID + " = ?";


		public static Cursor query(Context context, Uri uri)
		{
				String videoId = uri.getLastPathSegment();
				return context.getContentResolver().query(
								MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
								PATH_PROJECTION,
								PATH_SELECTION,
								new String[]{videoId},
								null /*sortOrder*/);
		}

		public static String getVideoThumbnail(Context context, Uri uri)
		{
//				Bitmap bitmap = null;
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inDither = false;
//				options.inPreferredConfig = Bitmap.Config.ARGB_8888;

				ContentResolver contentResolver = context.getContentResolver();
				Cursor cursor = query(context,uri);

				if (null == cursor || 0 == cursor.getCount())
				{
						return null;
				}
				cursor.moveToFirst();
				String filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				cursor.close();

				if (null == filepath)
				{
						return null;
				}
				return filepath;
		}



}
