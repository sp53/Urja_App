package com.levirs.example.slidinguppanel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

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
import java.util.ArrayList;
import java.util.List;

public class graph extends AppCompatActivity {


    JSONObject jsobject;
    JSONArray jsarray;
    String indata = "";
    String JSON_STRING;
    String jsdata;
    TextView cty;


    BarChart wind;
    List<BarEntry> barlist;


    List<List<String>> x = new ArrayList<List<String>>();
    List<List<String>> y = new ArrayList<List<String>>();

    SwipeRefreshLayout swipeLayout;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);




        cty=findViewById(R.id.textView5);
        cty.setText(city.name);

        swipeLayout = findViewById(R.id.srl);

        RecyclerView rv = findViewById(R.id.recyclerview);


        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeLayout.setEnabled(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        swipeLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });

        swipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getJSON();
                        swipeLayout.setRefreshing(false);
                    }
                }
        );


        ImageButton imgbtn = findViewById(R.id.ibtn2);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton imgbtn2=findViewById(R.id.assist);
        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assistdialog obj=new assistdialog();
                obj.show(getSupportFragmentManager(),"assist dialog");

            }
        });

        ImageButton imgbtn3=findViewById(R.id.apibtn);
        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Api_act.class);
                startActivity(i);
                finish();

            }
        });




        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd");
        String dateString = sdf.format(date);
        TextView d = findViewById(R.id.date);
        d.setText(dateString);

        getJSON();


    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, x, y);
        adapter.receiveContext(graph.this);
        recyclerview.setAdapter(adapter);

    }

    // json data
    public void getJSON() {
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            String u="http://dbms-com.stackstaging.com/graph_data.php?city="+city.name+"&code="+city.opcode;
            json_url = u;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection;
                httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while ((JSON_STRING = br.readLine()) != null) {
                    sb.append(JSON_STRING + "\n");
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


            indata = result;
            x.clear();
            y.clear();

            try {
                jsobject = new JSONObject(indata);
                jsarray = jsobject.getJSONArray("graph");
                int count = 0;

                while (count < jsarray.length()) {
                    int count2 = 0;
                    List<String> xx = new ArrayList<>();
                    List<String> yy = new ArrayList<>();

                    while (count2 < 10) {
                        JSONObject jo = jsarray.getJSONObject(count);
                        xx.add(jo.getString("x"));
                        yy.add(jo.getString("y"));

                        count++;
                        count2++;
                    }
                    x.add(xx);
                    y.add(yy);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            initRecyclerView();

        }
    }

}
