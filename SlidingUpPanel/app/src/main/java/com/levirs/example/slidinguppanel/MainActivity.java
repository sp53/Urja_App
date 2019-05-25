package com.levirs.example.slidinguppanel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SectionPageAdapter spa;
    private ViewPager mviewpager;
    String JSON_STRING;
    String jsdata;
    JSONObject jsobject;
    JSONArray jsarray;
    contactadapter conadap;
    ListView lv;
    String indata="";
    TextView tvw;
    TextView cty;
    View vhold;

    SectionPageAdapter adapter;
    //SwipeRefreshLayout swipeLayout;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cty=findViewById(R.id.textView5);
        cty.setText(city.name);
        //swipeLayout = findViewById(R.id.srl2);



        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {

            getJSON();
            /*swipeLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            getJSON();
                            swipeLayout.setRefreshing(false);
                        }
                    }
            );*/


        adapter= new SectionPageAdapter(getSupportFragmentManager());

        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.slidelayout);
        layout.setAnchorPoint(0.9f);


        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd");
        String dateString = sdf.format(date);
        TextView d=findViewById(R.id.date);
        d.setText(dateString);




        spa=new SectionPageAdapter(getSupportFragmentManager());
        mviewpager=(ViewPager) findViewById(R.id.container);

        mviewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                FragmentSwipeInterface fragment = (FragmentSwipeInterface) adapter.instantiateItem(mviewpager, i);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });

        setupViewPager(mviewpager);
        TabLayout tbl=(TabLayout) findViewById(R.id.tabs);
        tbl.setupWithViewPager(mviewpager);



        // no need for these lines .... working fine without
        ConstraintLayout cl=findViewById(R.id.dragview);
        cl.setClipToOutline(true);
        // .....


        ImageButton imgbtn=findViewById(R.id.ibtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent myIntent = new Intent(getApplicationContext(), graph.class);
                startActivity(myIntent);
            }
        });





        }
        else
        {


            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.settings",
                    "com.android.settings.Settings$DataUsageSummaryActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


    private void setupViewPager(ViewPager vp)
    {

        adapter.addFragment(new wfrag(),"Wind");
        adapter.addFragment(new sfrag(),"Solar");
        adapter.addFragment(new dfrag(),"Demand");
        vp.setAdapter(adapter);
    }


    // json data
    public void getJSON()
    {

        new BackgroundTask().execute();

    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected void onPreExecute() {

            String u="http://dbms-com.stackstaging.com/json_data.php?city="+city.name;
            json_url=u;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url= new URL(json_url);
                HttpURLConnection httpURLConnection;
                httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br =new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb=new StringBuilder();
                while((JSON_STRING=br.readLine())!=null)
                {
                    sb.append(JSON_STRING+"\n");
                }
                br.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            indata=result;


            try {
                jsobject = new JSONObject(indata);
                String Daily="";
                JSONObject jo;


                jsarray = jsobject.getJSONArray("MAIN_ACTIVITY");
                jo = jsarray.getJSONObject(0);
                Daily = jo.getString("solar");
                assistdata.s=Daily;
                tvw=findViewById(R.id.solar);
                tvw.setText(Daily);

                jsarray = jsobject.getJSONArray("MAIN_ACTIVITY");
                jo = jsarray.getJSONObject(0);
                Daily = jo.getString("wind");
                assistdata.w=Daily;
                tvw=findViewById(R.id.wind);
                tvw.setText(Daily);

                jsarray = jsobject.getJSONArray("MAIN_ACTIVITY");
                jo = jsarray.getJSONObject(0);
                Daily = jo.getString("demand");
                assistdata.d=Daily;
                tvw=findViewById(R.id.demand);
                tvw.setText(Daily);

                jsarray = jsobject.getJSONArray("MAIN_ACTIVITY");
                jo = jsarray.getJSONObject(0);
                Daily = jo.getString("reserved");
                assistdata.r=Daily;
                tvw=findViewById(R.id.reserve);
                tvw.setText(Daily);

                jsarray = jsobject.getJSONArray("MAIN_ACTIVITY");
                jo = jsarray.getJSONObject(0);
                Daily = jo.getString("others");
                assistdata.t=Daily;
                tvw=findViewById(R.id.total);
                tvw.setText(Daily);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        finish();
    }


}
