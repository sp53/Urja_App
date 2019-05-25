package com.levirs.example.slidinguppanel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class wfrag extends Fragment implements FragmentSwipeInterface{

    JSONObject jsobject;
    JSONArray jsarray;
    contactadapter conadap;
    ListView lv;
    String indata="";
    TextView tvw;
    String JSON_STRING;
    String jsdata;
    View vhold;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.wind_fragment,container,false);
        vhold=view;

        getJSON();
        return view;

    }


    @Override
    public void fragmentBecameVisible() {
        getJSON();
    }



    // json data
    public void getJSON() {
        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            String u="http://dbms-com.stackstaging.com/wind_data.php?city="+city.name+"&code="+city.opcode;
            json_url=u;
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


            List<contacts> l=new ArrayList<contacts>();

            indata = result;
            //setContentView(R.layout.solar_fragment);
            lv = (ListView) vhold.findViewById(R.id.wlist);

            conadap = new contactadapter(getActivity(), R.layout.row);
            lv.setAdapter(conadap);


            try {
                jsobject = new JSONObject(indata);
                jsarray = jsobject.getJSONArray("Wind");
                int count = 0;
                String t, h;

                while (count < jsarray.length()) {
                    JSONObject jo = jsarray.getJSONObject(count);
                    t = jo.getString("H");
                    h = jo.getString("T");


                    contacts obj = new contacts(t, h);
                    l.add(obj);
                    count++;
                }
                conadap.add(l);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}

