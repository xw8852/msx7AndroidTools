package com.msx7.core;

import android.app.Application;
import android.os.Handler;

public class Controller extends Application {
	private static Controller instance;
	private Handler mHandler;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static final Controller getApplication() {
		return instance;
	}

	public Handler getHandler(){
		if(mHandler==null) mHandler=new Handler();
		return mHandler;
	}
}
