package me.smart.flyme.mediastore;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;

public  class MediaStoreUtil
{
    //规定缩略图高度和宽度
    private static final int MINI_THUMB_WIDTH = 512;
    private static final int MINI_THUMB_HEIGHT = 384;

    private MediaStoreUtil()
    {
    }

		/**
     * 是否是媒体库数据
     * @param uri
     * @return
     */
    public static boolean isMediaStoreUri(Uri uri)
    {
        return uri != null && ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())
              && MediaStore.AUTHORITY.equals(uri.getAuthority());
    }

		/**
		 * 是否是视频库文件
		 */
    private static boolean isVideoUri(Uri uri)
    {
        return uri.getPathSegments().contains("video");
    }
    //判断是否是媒体视频文件
    public static boolean isMediaStoreVideoUri(Uri uri)
    {
		    return isMediaStoreUri(uri) && isVideoUri(uri);
    }
    //判断是否是媒体图片文件
    public static boolean isMediaStoreImageUri(Uri uri)
    {
        return isMediaStoreUri(uri) && !isVideoUri(uri);
    }

    public static boolean isThumbnailSize(int width, int height)
    {
         return width <= MINI_THUMB_WIDTH && height <= MINI_THUMB_HEIGHT;
    }
}
