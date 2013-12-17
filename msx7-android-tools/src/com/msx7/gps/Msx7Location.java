package com.msx7.gps;

import android.location.Location;

import com.baidu.location.BDLocation;

public class Msx7Location {
	/**
	 * 1、GOOGLE—<br/>
	 * 2、BAIDU-<br/>
	 * 3、system-<br/>
	 */
	public int type;
	public double mLatitude = 0.0;
	public double mLongitude = 0.0;
	public Object original;

	public Msx7Location(BDLocation location) {
		type = 2;
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
	}

	public Msx7Location(Location location, int type) {
		this.type = type;
		mLongitude = location.getLongitude();
		mLatitude = location.getLatitude();
	}
}
