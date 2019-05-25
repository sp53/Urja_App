package com.levirs.example.slidinguppanel;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

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

public class Api_act extends AppCompatActivity {


    String JSON_STRING;
    String jsdata;
    JSONObject jsobject;
    JSONArray jsarray;
    contactadapter conadap;
    ListView lv;
    String indata="";
    TextView tvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_act);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd");
        String dateString = sdf.format(date);
        TextView d=findViewById(R.id.date);
        d.setText(dateString);
        getJSON();
    }

    @Override
    public void onBackPressed() {
        Intent i =new Intent(getApplicationContext(),graph.class);
        startActivity(i);
        finish();
    }

    // json data
    public void getJSON()
    {

        new Api_act.BackgroundTask().execute();

    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected void onPreExecute() {

            String u="http://dbms-com.stackstaging.com/weather.php?city=mumbai";
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


                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(0);
                Daily = jo.getString("temp");
                assistdata.s=Daily;
                tvw=findViewById(R.id.temp);
                tvw.setText("Temperature : "+Daily);

                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(1);
                Daily = jo.getString("pressure");
                assistdata.w=Daily;
                tvw=findViewById(R.id.pres);
                tvw.setText("Pressure : "+Daily);

                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(2);
                Daily = jo.getString("humidity");
                assistdata.d=Daily;
                tvw=findViewById(R.id.hum);
                tvw.setText("Humidity : "+Daily);

                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(3);
                Daily = jo.getString("wind");
                assistdata.r=Daily;
                tvw=findViewById(R.id.winsp);
                tvw.setText("Wind Speed : "+Daily);

                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(4);
                Daily = jo.getString("winddirection");
                assistdata.t=Daily;
                tvw=findViewById(R.id.winddr);
                tvw.setText("Wind Direction : "+Daily);


                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(5);
                Daily = jo.getString("visibility");
                assistdata.t=Daily;
                tvw=findViewById(R.id.vis);
                tvw.setText("Visibility : "+Daily);

                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(6);
                Daily = jo.getString("solard");
                assistdata.t=Daily;
                tvw=findViewById(R.id.solard);
                tvw.setText("Solar Accuracy : "+Daily);

                jsarray = jsobject.getJSONArray("weather");
                jo = jsarray.getJSONObject(7);
                Daily = jo.getString("windd");
                assistdata.t=Daily;
                tvw=findViewById(R.id.windd);
                tvw.setText("Wind Accuracy : "+Daily);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
