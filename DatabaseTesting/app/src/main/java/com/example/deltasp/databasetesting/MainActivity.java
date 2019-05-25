
package com.example.deltasp.databasetesting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String JSON_STRING;
    String jsdata ;
    private static final String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getJSON();


    }
    public void getJSON()
    {
        new BackgroundTask().execute();

    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected void onPreExecute() {
            json_url="http://dbms-com.stackstaging.com/json_data.php";
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
            TextView tv=findViewById(R.id.disp);
            tv.setText(result);
            jsdata=result;
        }
    }
    public void parseJSON(View view)
    {

        Intent i=new Intent(this,displistview.class);
        i.putExtra("data", jsdata);
        startActivity(i);

    }




}
