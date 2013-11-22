package com.msx7.gps;

import android.app.AlarmManager;
import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;

public class GoogleGPS implements IGPS {
	Context ctx;
	AlarmManager mAlarmManager;
	@Override
	public boolean isSupport(Context ctx) {
		if(ctx==null)throw new IllegalAccessError("The Context must be not null!");
		this.ctx = ctx;
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx)==ConnectionResult.SUCCESS;
	}

	@Override
	public void startGPS() {
		if(ctx==null) throw new IllegalAccessError("First,you must be call isSupport(Context ctx)");
		mAlarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		LocationRequest mRequest = LocationRequest.create();
		mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//		mRequest.setFastestInterval(F)
	}

	@Override
	public void onFinish() {

	}

}
