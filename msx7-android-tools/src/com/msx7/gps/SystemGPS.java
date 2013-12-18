package com.msx7.gps;

import static com.msx7.gps.LocationServices.GPS_SYSTEM;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * 1、在你启动{@link LocationServices}的intent中，<br/>
 * 传递系统定位相关参数：<br/>
 * key - value<br/>
 * {@link #PARAM_GPS} - {@link Criteria} <br/>
 * {@link #PARAM_PROVIDER} - {@link LocationManager#NETWORK_PROVIDER} or
 * {@link LocationManager#GPS_PROVIDER}<br/>
 * {@link #PARAM_MIN_TIME} - 最小间隔时间 long类型单位ms <br/>
 * {@link #PARAM_MIN_DISTANCE} - 最小距离 float类型，单位m <br/>
 * <br/>
 * 
 * @author Msx7
 */
public class SystemGPS extends BaseGPS {
	public static final String PARAM_GPS = "param_gps";
	public static final String PARAM_PROVIDER = "param_provider";
	public static final String PARAM_MIN_TIME = "param_min_time";
	public static final String PARAM_MIN_DISTANCE = "param_min_distance";
	public static final String ACTION_PROVIDER_DISABLE = "com.msx7.gps.systemgps.disable_provider";
	Criteria criteria;
	Context ctx;
	String provider;
	LocationManager locationManager;
	LocationListener listener;
	long minTime;
	float minDistance;

	@Override
	public boolean doFilter(Context ctx, Intent data, int type) {
		if ((type & GPS_SYSTEM) != GPS_SYSTEM)
			return false;
		criteria = data.getParcelableExtra(PARAM_GPS);
		provider = data.getStringExtra(PARAM_PROVIDER);
		minTime = data.getLongExtra(PARAM_MIN_TIME, 1000);
		minDistance = data.getFloatExtra(PARAM_MIN_DISTANCE, 10f);
		this.ctx = ctx;
		locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		return true;
	}

	@Override
	public void startGPS() {
		locationManager.getBestProvider(criteria, true);
		if (!locationManager.isProviderEnabled(provider)) {
			ctx.sendBroadcast(new Intent(ACTION_PROVIDER_DISABLE));
			return;
		}
		listener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}

			@Override
			public void onLocationChanged(Location location) {
				upadate.updateLocation(new Msx7Location(location, 3));
			}
		};
		locationManager.requestLocationUpdates(PARAM_PROVIDER, minTime,
				minDistance, listener);
	}

	@Override
	public void onFinish() {
		if (listener != null)
			locationManager.removeUpdates(listener);
	}

}
