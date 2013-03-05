package com.example.listviewtemplate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ClipDrawable mClipDrawable = ((ClipDrawable) getResources()
        // .getDrawable(R.drawable.clip_roude));
        // mClipDrawable.setLevel(mClipDrawable.getLevel() + 500);
        final RoudShape shape = new RoudShape(getResources().getDrawable(R.drawable.roude),  2);
        shape.setLevel(0);
        final ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
        mImageView.setBackgroundDrawable(shape);
        page = new Page(10, 0);
        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                while (true) {
                    level += 100;
                    publishProgress(level);
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
                if (level > 10000) {
                    level = 0;
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
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView tv = (TextView) convertView;
            tv.setText(getItem(position));
            AnimationSet set = new AnimationSet(true);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(800);
            set.addAnimation(animation);
            if (position >= lastposition)
                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            else
                animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                        -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

            animation.setDuration(600);
            set.addAnimation(animation);

            tv.startAnimation(set);

            lastposition = position;
            return convertView;
        }
    }
}
