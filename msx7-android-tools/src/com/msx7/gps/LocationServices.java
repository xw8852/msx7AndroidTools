package com.msx7.gps;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 经度范围 -180°~180° 纬度范围 -90°~90°
 * 
 * @author Josn
 * 
 */
public class LocationServices extends Service {
	public static final String ACTION_SEND_LOCATION = "com.msx7.gps.send_location";
	public static final String PARAM_LOCATION = "param_location";
	public static final int GPS_SYSTEM = 0x0001;
	public static final int GPS_GOOGLE = GPS_SYSTEM << 1;
	public static final int GPS_BAIDU = GPS_SYSTEM << 2;
	public static final String FLAG_GPS_TYPE = "gpsType";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int type = intent.getIntExtra(FLAG_GPS_TYPE, GPS_SYSTEM);
		startGPS(type, intent);
		return super.onStartCommand(intent, flags, startId);
	}

	private IGPS startGPS(int type, Intent data) {
		IGPS gps = null;
		for (IGPS _gps : getIGPSList()) {
			if (_gps.doFilter(this, data, type)) {
				gps = _gps;
				gps.setLocationUpdate(upadate);
			}
		}
		return gps;
	}

	LocationUpadate upadate = new LocationUpadate() {

		@Override
		public void updateLocation(Msx7Location location) {
			Intent intent = new Intent(ACTION_SEND_LOCATION);
			intent.putExtra(PARAM_LOCATION, new Gson().toJson(location));
			sendBroadcast(intent);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	public List<IGPS> getIGPSList() {
		List<IGPS> gps = new ArrayList<IGPS>();
		gps.add(new GoogleGPS());
		gps.add(new BaiduGPS());
		gps.add(new SystemGPS());
		return gps;
	}

	public static interface LocationUpadate {
		public void updateLocation(Msx7Location location);
	}
}
