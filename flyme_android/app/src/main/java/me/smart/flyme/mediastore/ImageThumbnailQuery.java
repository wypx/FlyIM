package me.smart.flyme.mediastore;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * author：wypx on 2015/12/22 22:11
 * blog：smarting.me
 */
public class ImageThumbnailQuery
{
		private static final String[] PATH_PROJECTION =
						{
										MediaStore.Images.Thumbnails.DATA,
						};
		private static final String PATH_SELECTION =
						MediaStore.Images.Thumbnails.KIND + " = " + MediaStore.Images.Thumbnails.MINI_KIND
										+ " AND " + MediaStore.Images.Thumbnails.IMAGE_ID + " = ?";

		public static Cursor query(Context context, Uri uri)
		{
				String imageId = uri.getLastPathSegment();
				return context.getContentResolver().query(
								MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
								PATH_PROJECTION,
								PATH_SELECTION,
								new String[] { imageId },
								null /*sortOrder*/);
		}
		public static String getImageThumbnail(Context context, Uri uri)
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
				String filepath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				cursor.close();

				if (null == filepath)
				{
						return null;
				}
				return filepath;
		}
}

