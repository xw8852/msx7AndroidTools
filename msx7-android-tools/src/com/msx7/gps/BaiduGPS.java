package com.msx7.gps;

import android.content.Context;

public class BaiduGPS implements IGPS{

	@Override
	public boolean isSupport(Context ctx) {
		return true;
	}

	@Override
	public void startGPS() {
		
	}

	@Override
	public void onFinish() {
		
	}

}
