package me.smart.flyme.utils.image;

import android.graphics.Bitmap;

import java.util.HashMap;

public class ImageCache {

	private static HashMap<String, Bitmap> imageCache = new HashMap<String, Bitmap>();
	
	public static void put(String key, Bitmap bmp) {
		imageCache.put(key, bmp);
	}
	
	public static Bitmap get(String key) {
		return imageCache.get(key);
	}
}
