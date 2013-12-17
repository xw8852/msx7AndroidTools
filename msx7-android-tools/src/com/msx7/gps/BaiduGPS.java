package com.msx7.gps;

import static com.msx7.gps.LocationServices.GPS_BAIDU;
import android.content.Context;
import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;

public class BaiduGPS extends BaseGPS {
	public static final String PARAM_BAIDU = "param_baidu";
	LocationClient client;
	LocationClientOption option;
	BaiduOption _Option;
	Context ctx;

	@Override
	public boolean doFilter(Context ctx, Intent data, int type) {
		if ((type & GPS_BAIDU) != GPS_BAIDU)
			return false;
		_Option = new Gson().fromJson(data.getStringExtra(PARAM_BAIDU),
				BaiduOption.class);
		return true;
	}

	@Override
	public void startGPS() {
		option=new LocationClientOption();
		option.setOpenGps(_Option.isOpenGps);//是否打开GPS
		option.setPriority(_Option.priority);
		if(_Option!=null&&_Option.proName!=null&&!"".equals(_Option.proName.trim()))
			option.setProdName(_Option.proName);
		option.setScanSpan(_Option.interval);
		option.disableCache(_Option.isCache);
		client=new LocationClient(ctx, option);
		client.start();
		client.registerLocationListener(new BDLocationListener() {
			
			@Override
			public void onReceivePoi(BDLocation arg0) {
				
			}
			
			@Override
			public void onReceiveLocation(BDLocation arg0) {
				upadate.updateLocation(new Msx7Location(arg0));
			}
		});
		
	}

	@Override
	public void onFinish() {

	}

	public static class BaiduOption {
		/** 设置是否打开gps，使用gps前提是用户硬件打开gps。默认是不打开gps的。 */
		public boolean isOpenGps;
		/**
		 * 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
		 * */
		public String proName;
		/** 设置定时定位的时间间隔。单位ms SDK限制频率不会超过1秒一次 */
		public int interval;
		/** true表示禁用缓存定位，false表示启用缓存定位。 */
		public boolean isCache;
		/**
		 * 设置定位方式的优先级。目前定位SDK的定位方式有两类：一是使用GPS进行定位。优点是定位准确，精度在几十米，缺点是第一次定位速度较慢，
		 * 甚至需要2、3分钟。二是使用网络定位。优点是定位速度快，服务端只需8ms，考虑到网速的话，一般客户端3秒左右即可定位，缺点是没有gps准确
		 * ，精度在几十到几百米。为了方便用户，我们提供了有两个整型的项：LocationClientOption.GpsFirst 以及
		 * LocationClientOption.NetWorkFirst：
		 * 
		 * GpsFirst：当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。
		 * 如果gps不可用，再发起网络请求，进行定位。
		 * NetWorkFirst：即时有gps，而且可用，也仍旧会发起网络请求。这个选项适合对精确坐标不是特别敏感，但是希望得到位置描述的用户。
		 * 
		 * {@link LocationClientOption#NetWorkFirst}<br/>
		 * {@link LocationClientOption#GpsFirst}
		 */
		public int priority = LocationClientOption.GpsFirst;

	}



}
