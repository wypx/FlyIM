
package me.smart.flyme.mediastore;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
 @SuppressLint("NewApi")

 public class CursorQuery
 {
		  //获取游标，代码封装
			public static Cursor getCursor(Context context, Uri uri, String[] projection,String selection,
			                               String[] selectionArgs,String order)
			{
			     ContentResolver cr = context.getContentResolver();
			     Cursor cursor = cr.query(uri, projection, selection, selectionArgs,order);
			     return cursor;
			}
 }
