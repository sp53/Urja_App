package com.example.deltasp.uidesign;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    JSONObject jsobject;
    JSONArray jsarray;
    String indata = "";
    String JSON_STRING;
    String jsdata;

    List<List<String>> x=new ArrayList<List<String>>();
    List<List<String>> y=new ArrayList<List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJSON();
    }



    private void initRecyclerView()
    {

        Toast.makeText(this,""+x.size(),Toast.LENGTH_SHORT).show();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, x, y);
        adapter.receiveContext(MainActivity.this);
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
            json_url = "http://dbms-com.stackstaging.com/graph_data.php";
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

            try {
                jsobject = new JSONObject(indata);
                jsarray = jsobject.getJSONArray("graph");
                int count = 0;

                while (count < jsarray.length()) {
                    int count2=0;
                    List<String> xx=new ArrayList<>();
                    List<String> yy=new ArrayList<>();

                    while(count2<10) {
                        JSONObject jo = jsarray.getJSONObject(count);
                        xx.add(jo.getString("year"));
                        yy.add(jo.getString("growth"));

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


