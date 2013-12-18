package com.msx7.gps;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;

import com.baidu.location.LocationClientOption;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;

public class GPSUtils {
	public static final int MIN_TIME = 3 * 60 * 1000;
	public static final float MIN_DISTANCE = 20;

	public static void StartGoogleNetLocation(Context ctx) {
		Intent intent=new Intent(LocationServices.ACTION_SERVICE);
		int flag=0;
		addGoogleNetLocation(flag, intent);
		addBaiduNetLocation(flag, intent);
		intent.putExtra(LocationServices.FLAG_GPS_TYPE, flag);
		ctx.startService(intent);
	}

	public static void addGoogleNetLocation(int flag, Intent intent) {
		if (flag == 0)
			flag = LocationServices.GPS_GOOGLE;
		else
			flag = flag | LocationServices.GPS_GOOGLE;
		LocationRequest request= LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		request.setInterval(MIN_TIME);
		request.setFastestInterval(MIN_TIME);
		intent.putExtra(GoogleGPS.PARAM_GOOGLE_GPS, request);
	}
	
	public static void addBaiduNetLocation(int flag,Intent intent){
		if (flag == 0)
			flag = LocationServices.GPS_BAIDU;
		else
			flag = flag | LocationServices.GPS_BAIDU;
		BaiduGPS.BaiduOption option=new BaiduGPS.BaiduOption();
		option.interval=MIN_TIME;
		option.isCache=true;
		option.isOpenGps=false;
		option.priority=LocationClientOption.NetWorkFirst;
		option.proName="baidu_location_test";
		intent.putExtra(BaiduGPS.PARAM_BAIDU, new Gson().toJson(option));
	}
	
	public static void addSystemNetLocation(int flag,Intent intent){
		if (flag == 0)
			flag = LocationServices.GPS_SYSTEM;
		else
			flag = flag | LocationServices.GPS_SYSTEM;
		Criteria criteria=new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		intent.putExtra(SystemGPS.PARAM_GPS, criteria);
		intent.putExtra(SystemGPS.PARAM_MIN_DISTANCE, MIN_DISTANCE);
		intent.putExtra(SystemGPS.PARAM_MIN_TIME, (long)MIN_TIME);
		intent.putExtra(SystemGPS.PARAM_PROVIDER, LocationManager.NETWORK_PROVIDER);
	}
}
