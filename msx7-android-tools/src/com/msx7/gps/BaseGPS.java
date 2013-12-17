package com.msx7.gps;

import com.msx7.gps.LocationServices.LocationUpadate;

public abstract class BaseGPS implements IGPS {

	LocationUpadate upadate;

	@Override
	public void setLocationUpdate(LocationUpadate upadate) {
		this.upadate = upadate;
	}

}
