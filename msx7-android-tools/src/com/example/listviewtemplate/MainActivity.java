package com.example.listviewtemplate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.msx7.core.command.IResponseListener;
import com.msx7.core.command.model.Response;
import com.msx7.widget.PageFooter;
import com.msx7.widget.PageFooter.Page;
import com.msx7.widget.PushHeader;
import com.msx7.widget.PushHeader.OnRefreshListener;
import com.msx7.widget.RoudShape;

@TargetApi(3)
public class MainActivity extends Activity {
	public static final String URL_GUOLI = "http://api.guoli.com/doaction.php?type=json";

	PushHeader header;
	Page page;
	PageFooter footer;
	int level = 0;
	ProgressBar mBar;
	Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ClipDrawable mClipDrawable = ((ClipDrawable) getResources()
		// .getDrawable(R.drawable.clip_roude));
		// mClipDrawable.setLevel(mClipDrawable.getLevel() + 500);
		final RoudShape shape = new RoudShape(getResources().getDrawable(
				R.drawable.roude), 2);
		shape.setLevel(10000);
		final ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
		Animation mAnimation = AnimationUtils.loadAnimation(this,
				R.anim.scalein);
		mImageView.startAnimation(mAnimation);
		mAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mImageView.startAnimation(AnimationUtils.loadAnimation(
						MainActivity.this, R.anim.scalein2));
			}
		});
		mBar = (ProgressBar) findViewById(R.id.progressBar1);
		mBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mBar.setProgressDrawable(new RoudShape(getResources().getDrawable(
				R.drawable.roude), 2));
		mBar.setIndeterminateDrawable(new RoudShape(getResources().getDrawable(
				R.drawable.roude), 2));
		mBar.setMax(10000);
		try {
			Field mField = mBar.getClass().getDeclaredField("mIndeterminate");
			mField.setAccessible(true);
			mField.set(mBar, false);
			Method method = mBar.getClass().getDeclaredMethod("stopAnimation");
			method.setAccessible(true);
			method.invoke(mBar);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// mBar.setIndeterminate(true);
		// System.out.println("bar:"+mBar.isIndeterminate()+",");
		// mBar.setIndeterminate(false);
		mBar.setProgress(level);

		System.out.println("bar:" + mBar.isIndeterminate() + ",");
		// mBar.setMinimumHeight(shape.getBounds().height());
		// mBar.setMinimumWidth(shape.getBounds().width());
		page = new Page(10, 0);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						level += 100;
						if (level > 10000) {
							level = 0;
						}
						mBar.setProgress(level);
						mImageView.setBackgroundDrawable(shape);
					}
				});
			}
		}, 100, 100);
		ListView listview = (ListView) findViewById(R.id.listView1);

		header = new PushHeader(listview);

		header.onRefreshComplete();
		header.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new AysncHeader().execute();
			}
		});

		listview.setAdapter(new SimpleArrayAdapter());
	}

	IResponseListener listener = new IResponseListener() {

		@Override
		public void onSuccess(Response arg0) {
			Log.d("MSG", "onSuccess:" + arg0.getData().toString());
		}

		@Override
		public void onError(Response arg0) {
			if (!(arg0.result instanceof Exception)) {
				Log.d("MSG", "onError:" + arg0.getData().toString());
			}
		}
	};

	class AysncHeader extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			header.onRefreshComplete();
			footer.reset();
		}
	}

	class SimpleArrayAdapter extends BaseAdapter {
		String[] array = null;
		private int lastposition;

		public SimpleArrayAdapter() {
			super();
			array = getResources().getStringArray(R.array.datasource);
		}

		@Override
		public int getCount() {
			return array.length;
		}

		@Override
		public String getItem(int position) {
			return array[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						android.R.layout.simple_list_item_1, null);
			}
			TextView tv = (TextView) convertView;
			tv.setText(getItem(position));
			AnimationSet set = new AnimationSet(true);
			Animation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(800);
			set.addAnimation(animation);
			if (position >= lastposition)
				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 1.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
			else
				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, -1.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);

			animation.setDuration(600);
			set.addAnimation(animation);

			tv.startAnimation(set);

			lastposition = position;
			return convertView;
		}
	}
}
