package com.msx7;

import android.widget.ImageView;

public final class ImageManager {
	public static final int CACHE_SDCARD = 0x0001;
	public static final int CACHE_TEMP = CACHE_SDCARD << CACHE_SDCARD;
	public static final int CACHE_NONE = CACHE_TEMP | CACHE_SDCARD;
	private static final ImageManager instance = new ImageManager();

	private ImageManager() {
	}

	public static final synchronized ImageManager getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * @param url
	 * @param imageView
	 * @param cacheMethod 
	 */
	public void loadImageView(String url, ImageView imageView, int cacheMethod) {
	}

}
