package com.msx7.core;

import android.app.Application;

public class Controller extends Application {
	public static final String CONFIG_NAME = "config.ini";
	public static final String MAX_PROCESS = "max_process";
	private static Controller instance;
	protected int max_progress = 3;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static final Controller getApplication() {
		return instance;
	}

	

	public void setMaxProcess(int max) {
		max_progress = max;
	}

}
