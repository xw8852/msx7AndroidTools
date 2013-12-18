package com.msx7.gps;

import static com.msx7.gps.LocationServices.GPS_GOOGLE;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
/**
 * 1、在你启动{@link LocationServices}的intent中，<br/>
 * 参考如下方法传递你定义的{@link LocationRequest}<br/>
 * intent.putExtra(GoogleGPS.PARAM_GOOGLE_GPS, LocationRequest.create());
 * @author Msx7
 */
public class GoogleGPS extends BaseGPS {
	public static final String PARAM_GOOGLE_GPS="param_googleGps";

	Context ctx;
	AlarmManager mAlarmManager;
	LocationRequest request;
	LocationClient client;
	LocationListener listener;
	@Override
	public boolean doFilter(Context ctx,Intent data,int type) {
		if((type&GPS_GOOGLE)!=GPS_GOOGLE)
			return false;
		if(ctx==null)throw new IllegalAccessError("The Context must be not null!");
		if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx)!=ConnectionResult.SUCCESS)
			return false;
		request = data.getParcelableExtra(PARAM_GOOGLE_GPS);
		this.ctx = ctx;		
		return true;
	}

	@Override
	public void startGPS() {
		if(ctx==null) throw new IllegalAccessError("The First,you must be call doFilter(Context ctx,Intent data,int type)");
		mAlarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		if(request==null){		
			//TODO:默认设置
			LocationRequest mRequest = LocationRequest.create();
			mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		}
		client=new LocationClient(ctx, callbacks, failedListener);
		client.connect();
	}
	GooglePlayServicesClient.ConnectionCallbacks callbacks=new GooglePlayServicesClient.ConnectionCallbacks() {
		
		@Override
		public void onDisconnected() {
			
		}
		
		@Override
		public void onConnected(Bundle arg0) {
			listener=new LocationListener() {
				
				@Override
				public void onLocationChanged(Location arg0) {
					upadate.updateLocation(new Msx7Location(arg0, 1));
				}
			};
			client.requestLocationUpdates(request, listener);
		}
	};
	
	GooglePlayServicesClient.OnConnectionFailedListener failedListener=new GooglePlayServicesClient.OnConnectionFailedListener() {
		
		@Override
		public void onConnectionFailed(ConnectionResult arg0) {
			
		}
	};
	@Override
	public void onFinish() {
		if(client!=null){
			if(listener!=null)
				client.removeLocationUpdates(listener);
			client.disconnect();
		}
	}

	

}
