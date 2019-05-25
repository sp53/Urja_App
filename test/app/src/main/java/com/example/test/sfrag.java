package com.levirs.example.slidinguppanel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class sfrag extends Fragment {



    JSONObject jsobject;
    JSONArray jsarray;
    contactadapter conadap;
    ListView lv;
    String indata="";
    TextView tvw;
    String JSON_STRING;
    String jsdata;
    View vhold;
    public sfrag() {

        getJSON();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        getJSON();
        View view = inflater.inflate(R.layout.solar_fragment, container, false);
        vhold=view;
        return view;    }


    // json data
    public void getJSON() {
        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://dbms-com.stackstaging.com/json_data.php";
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
            //setContentView(R.layout.solar_fragment);
            lv = (ListView)vhold.findViewById(R.id.list);

            conadap = new contactadapter(getActivity(), R.layout.row);
            lv.setAdapter(conadap);


            try {
                jsobject = new JSONObject(indata);
                jsarray = jsobject.getJSONArray("Solar");
                int count = 0;
                String t, h;

                while (count < jsarray.length()) {
                    JSONObject jo = jsarray.getJSONObject(count);
                    t = jo.getString("H");
                    h = jo.getString("T");

                    contacts obj = new contacts(t, h);
                    conadap.add(obj);
                    count++;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
