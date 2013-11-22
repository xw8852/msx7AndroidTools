package com.msx7.gps;

import android.content.Context;

public interface IGPS {
	public boolean isSupport(Context ctx);
	
	public void startGPS();
	
	public void onFinish();
	
}
