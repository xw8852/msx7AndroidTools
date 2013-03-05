package com.example.listviewtemplate;

import java.util.Timer;
import java.util.TimerTask;

import android.R.mipmap;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.msx7.core.command.IResponseListener;
import com.msx7.core.command.model.Response;
import com.msx7.widget.PageFooter;
import com.msx7.widget.PageFooter.Page;
import com.msx7.widget.PageFooter.PageLoader;
import com.msx7.widget.PushHeader;
import com.msx7.widget.PushHeader.OnRefreshListener;
import com.msx7.widget.RoudShape;

@TargetApi(3)
public class MainActivity extends Activity {
	public static final String URL_GUOLI = "http://api.guoli.com/doaction.php?type=json";

	PushHeader header;
	Page page;
	PageFooter footer;
	int level=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		ClipDrawable mClipDrawable = ((ClipDrawable) getResources()
//				.getDrawable(R.drawable.clip_roude));
//		mClipDrawable.setLevel(mClipDrawable.getLevel() + 500);
	final	RoudShape shape=new RoudShape(getResources().getDrawable(R.drawable.roude), Gravity.TOP,2 );
		shape.setLevel(0);
		final	ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageView.setBackgroundDrawable(shape);
		page = new Page(10, 0);
		new AsyncTask<Void, Integer, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				while(true){
					level+=100;
					publishProgress(level);
					System.out.println("level:"+level);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				super.onProgressUpdate(values);
				if(level>4500){
					level=0;
				}
				shape.setLevel(level);
				mImageView.setBackgroundDrawable(shape);
			}
			
		}.execute();
		
		ListView listview = (ListView) findViewById(R.id.listView1);

		header = new PushHeader(listview);

		header.onRefreshComplete();
		header.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new AysncHeader().execute();
			}
		});
		footer = new PageFooter(listview, pageLoader, page);

		listview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.datasource)));
	}

	PageLoader pageLoader = new PageLoader() {

		@Override
		public void loadPage(int pageNumber) {
			new AysncFooter().execute();
		}
	};

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

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

	class AysncFooter extends AsyncTask<Void, Void, Void> {

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
			footer.onPageLoaderFinish();
			// footer.showComplete();
		}
	}
}
