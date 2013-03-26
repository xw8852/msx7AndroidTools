package com.msx7.core;

import android.app.Application;
import android.os.Handler;
import android.util.DisplayMetrics;

public class Controller extends Application {
    private static Controller instance;
    private Handler mHandler;
    public static final boolean DEBUG = true;
    public static int WIDTH=0;
    public static int HEIGHT=0;
    DisplayMetrics metrics;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        metrics =getResources().getDisplayMetrics();
        WIDTH=metrics.widthPixels;
        HEIGHT= metrics.heightPixels;
    }

    public static final Controller getApplication() {
        return instance;
    }

    public Handler getHandler() {
        if (mHandler == null)
            mHandler = new Handler();
        return mHandler;
    }
}
