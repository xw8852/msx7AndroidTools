package com.msx7.gps;

import android.content.Context;

public class SystemGPS implements IGPS {

	@Override
	public boolean isSupport(Context ctx) {
		return true;
	}

	@Override
	public void startGPS() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub

	}

}
