package com.levirs.example.slidinguppanel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String JSON_STRING;
    String jsdata;
    JSONObject jsobject;
    JSONArray jsarray;
    contactadapter conadap;
    String indata="";
    String status="";
    EditText et;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Context c=this;



            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// clear FLAG_TRANSLUCENT_STATUS flag:
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.setStatusBarColor(ContextCompat.getColor(login.this, R.color.login_statusbar));// finally change the color

            Spinner sp = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityarray, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(this);

            pb=findViewById(R.id.pbr);

            ImageButton b = findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!CheckNetwork.isInternetAvailable(c)) //returns true if internet available
                    {

                        Toast.makeText(c, "No Internet Connection", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName("com.android.settings",
                                "com.android.settings.Settings$DataUsageSummaryActivity"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                    else {
                        pb.setVisibility(View.VISIBLE);

                           et=findViewById(R.id.code);
                            city.opcode=et.getText().toString();
                            getJSON();
                    }
                }
            });







    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String c=parent.getItemAtPosition(position).toString();
        city.name=c;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // json data
    public void getJSON()
    {

        new login.BackgroundTask().execute();

    }
    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected void onPreExecute() {


            String u="http://dbms-com.stackstaging.com/login.php?city="+city.name+"&code="+city.opcode;
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
                JSONObject jo;


                jsarray = jsobject.getJSONArray("login");
                jo = jsarray.getJSONObject(0);

                status = jo.getString("status");

                if(status.equals("yes"))
                {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Operator Code", Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.INVISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
