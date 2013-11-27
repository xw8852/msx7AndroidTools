package com.msx7.gps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocationServices extends Service {
	public static final int GPS_SYSTEM = 0x0001;
	public static final int GPS_GOOGLE = GPS_SYSTEM << 1;
	public static final int GPS_BAIDU = GPS_SYSTEM << 2;
	public static final String FLAG_GPS_TYPE = "gpsType";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int type = intent.getIntExtra(FLAG_GPS_TYPE, GPS_SYSTEM);
		startGPS(type);
		return super.onStartCommand(intent, flags, startId);
	}

	private IGPS startGPS(int type) {
		IGPS gps = null;
		if((type&GPS_GOOGLE)==GPS_GOOGLE){
			gps=new GoogleGPS();
		}
		if(!gps.isSupport(this)&&(type&GPS_BAIDU)==GPS_BAIDU){
			gps=new BaiduGPS();
		}
		if(!gps.isSupport(this)&&(type&GPS_SYSTEM)==GPS_SYSTEM){
			gps=new SystemGPS();
		}
		return gps!=null&&gps.isSupport(this)?gps:null;
	}

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

}
