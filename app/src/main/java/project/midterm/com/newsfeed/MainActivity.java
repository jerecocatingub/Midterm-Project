package project.midterm.com.newsfeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://newsapi.org/v1/articles?source=cnn&apiKey=af3979871c3f4a9ebe2a2a20c10822c2";
    private NewsAsyncTask task;
    private ListView newsfeed;
    private NewsAdapter mAdapter;
    private ProgressDialog pd;
    private TextView defaultMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        setData(url);

    }

    public void setData(String link){
        newsfeed = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsfeed.setAdapter(mAdapter);

        newsfeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News current = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(current.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        task = new NewsAsyncTask();
        task.execute(link);
        checkWifiAvailability();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String network="";
        switch (item.getItemId()) {
            case R.id.ch_BBC:
                network = "bbc-news"; break;
            case R.id.ch_CNN:
                network = "cnn"; break;
            case R.id.ch_NatGeo:
                network = "national-geographic"; break;
            case R.id.ch_TIME:
                network = "time"; break;
        }
        String link = "https://newsapi.org/v1/articles?source="+network+"&apiKey=af3979871c3f4a9ebe2a2a20c10822c2";
        setData(link);
        return true;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {
        @Override
        protected List<News> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<News> result = QueryUtils.fetchNewsData(urls[0]);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            defaultMessage = (TextView) findViewById(R.id.textView);
            defaultMessage.setText("");

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected void onPostExecute(List<News> data) {
            mAdapter.clear();
            defaultMessage = (TextView) findViewById(R.id.textView);
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            } else {
                defaultMessage.setText("No Available Data");
            }

            if(pd.isShowing()){
                pd.dismiss();
            }
        }
    }
     public void checkWifiAvailability(){
         WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
         if(!wifi.isWifiEnabled()){
             Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
         }
     }
}
