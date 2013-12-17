package com.msx7.gps;

import com.msx7.gps.LocationServices.LocationUpadate;

import android.content.Context;
import android.content.Intent;

public interface IGPS {
	public boolean doFilter(Context ctx,Intent data,int type);
	
	public void startGPS();
	
	public void onFinish();
	
	public void setLocationUpdate(LocationUpadate upadate);
	
}
