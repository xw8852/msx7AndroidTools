package com.example.listviewtemplate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.msx7.core.command.IResponseListener;
import com.msx7.core.command.model.Response;
import com.msx7.widget.PageFooter;
import com.msx7.widget.PageFooter.Page;
import com.msx7.widget.PageFooter.PageLoader;
import com.msx7.widget.PushHeader;
import com.msx7.widget.PushHeader.OnRefreshListener;

@TargetApi(3)
public class MainActivity extends Activity {
	public static final String URL_GUOLI = "http://api.guoli.com/doaction.php?type=json";

    PushHeader header;
    Page page;
    PageFooter footer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page = new Page(10, 0);

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

        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.datasource)));
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
