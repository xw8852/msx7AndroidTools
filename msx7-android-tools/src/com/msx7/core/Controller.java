package com.msx7.core;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.msx7.image.ImageFetcher;

public class Controller extends Application {
    private static Controller instance;
    private Handler mHandler;
    public static final boolean DEBUG = true;
    public static int WIDTH=0;
    public static int HEIGHT=0;
    protected  DisplayMetrics metrics;
    protected ImageFetcher mFetcher;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        metrics =getResources().getDisplayMetrics();
        WIDTH=metrics.widthPixels;
        HEIGHT= metrics.heightPixels;
        mFetcher= new ImageFetcher(this);
    }

    public static final Controller getApplication() {
        return instance;
    }

    public Handler getHandler() {
        if (mHandler == null)
            mHandler = new Handler();
        return mHandler;
    }
    
    public void loadThumbnailImage(String key, ImageView imageView, Bitmap loadingBitmap) {
        mFetcher.loadThumbnailImage(key, imageView, loadingBitmap);
    }

    public void loadThumbnailImage(String key, ImageView imageView, int resId) {
         mFetcher.loadThumbnailImage(key, imageView, resId);
    }

    public void loadThumbnailImage(String key, ImageView imageView) {
        mFetcher.loadThumbnailImage(key, imageView);
    }

    public void loadImage(String key, ImageView imageView, Bitmap loadingBitmap) {
        mFetcher.loadImage(key, imageView, loadingBitmap);
    }

    public void loadImage(String key, ImageView imageView, int resId) {
        mFetcher.loadImage(key, imageView, resId);
    }

    public void loadImage(String key, ImageView imageView) {
        mFetcher.loadImage(key, imageView);
    }
}
