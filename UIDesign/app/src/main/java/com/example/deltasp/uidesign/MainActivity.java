package com.example.deltasp.uidesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mnames=new ArrayList<>();

    private ArrayList<String> mImageURLs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getImages();
    }

    private void getImages() {


        mImageURLs.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mnames.add("Havasu Falls");

        mImageURLs.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mnames.add("Trondheim");

        mImageURLs.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mnames.add("Portugal");

        mImageURLs.add("https://i.redd.it/j6myfqglup501.jpg");
        mnames.add("Rocky Mountain National Park");


        mImageURLs.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mnames.add("Mahahual");

        mImageURLs.add("https://i.redd.it/k98uzl68eh501.jpg");
        mnames.add("Frozen Lake");


        mImageURLs.add("https://i.redd.it/glin0nwndo501.jpg");
        mnames.add("White Sands Desert");

        mImageURLs.add("https://i.redd.it/obx4zydshg601.jpg");
        mnames.add("Austrailia");

        mImageURLs.add("https://i.imgur.com/ZcLLrkY.jpg");
        mnames.add("Washington");

        initRecyclerView();
    }

    private void initRecyclerView()
    {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mnames,mImageURLs);
        recyclerview.setAdapter(adapter);

    }

}
