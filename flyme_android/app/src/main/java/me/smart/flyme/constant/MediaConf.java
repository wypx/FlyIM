package me.smart.flyme.constant;

import me.smart.flyme.mediastore.MediaStoreData;

/**
 * author：wypx on 2016/1/3 00:38
 * blog：smarting.me
 */
public class MediaConf
{
		private static MediaStoreData.MimeType CurrentMimeType = MediaStoreData.MimeType.IMAGE;
		public static MediaStoreData.MimeType getCurrentMimeType()
		{
				return CurrentMimeType;
		}

		public static void setCurrentMimeType(MediaStoreData.MimeType mcurrentMimeType)
		{
				CurrentMimeType = mcurrentMimeType;
		}


}
