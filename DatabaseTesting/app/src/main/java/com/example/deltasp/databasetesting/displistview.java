package com.example.deltasp.databasetesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class displistview extends AppCompatActivity {

    String indata;
    JSONObject jsobject;
    JSONArray jsarray;
    contactadapter conadap;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displistviewlayout);
        lv=(ListView) findViewById(R.id.list);

        conadap=new contactadapter(this,R.layout.row);
        lv.setAdapter(conadap);

        indata=getIntent().getExtras().getString("data");

        try {
            jsobject=new JSONObject(indata);
            jsarray=jsobject.getJSONArray("Array");
            int count=0;
            String title,description,img;

            while(count<jsarray.length())
            {
                JSONObject jo=jsarray.getJSONObject(count);
                title= jo.getString("title");
                description= jo.getString("description");
                img= jo.getString("img");
                contacts obj=new contacts(title,description,img);
                conadap.add(obj);
                count++;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
