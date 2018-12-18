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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String json_string;
    private ArrayList<String> mnames = new ArrayList<>();

    private ArrayList<String> mImageURLs = new ArrayList<>();
    int count = 0;

    public void callfn(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imgvw = (ImageView) findViewById(R.id.vibrance);
        imgvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count % 4 == 1) {
                    imgvw.setImageResource(R.mipmap.ic_launcher_foregroundvib2);
                } else if (count % 4 == 2) {
                    imgvw.setImageResource(R.mipmap.ic_launcher_foregroundvib);
                }
                else if (count % 4 == 3)
                {
                    imgvw.setImageResource(R.mipmap.ic_launcher_foregroundvib3);
                }
                else
                {
                    imgvw.setImageResource(R.mipmap.ic_launcher_foregroundvib4);
                }

            }
        });

        getImages();
    }

    private void getImages() {


        mImageURLs.add("https://i.imgur.com//hu3XwNF.jpg");
        mnames.add("Event 1");

        mImageURLs.add("https://i.imgur.com//w4WcoU2.png");
        mnames.add("Event 2");

        mImageURLs.add("https://i.imgur.com//7Nb3mKU.jpg");
        mnames.add("Event 3");

        mImageURLs.add("https://i.imgur.com//KF3SaJu.jpg");
        mnames.add("Event 4");


        mImageURLs.add("https://i.imgur.com//qRBPjQs.jpg");
        mnames.add("Event 5");

        mImageURLs.add("https://i.imgur.com//PBzvumx.jpg");
        mnames.add("Event 6");


        mImageURLs.add("https://i.imgur.com/GFtwsEs.png");
        mnames.add("Event 7");

        mImageURLs.add("https://i.imgur.com//IBLKZ1e.jpg");
        mnames.add("Event 8");

        mImageURLs.add("https://i.imgur.com//l6EYwyk.jpg");
        mnames.add("Event 9");

        initRecyclerView();
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mnames, mImageURLs);
        adapter.receiveContext(MainActivity.this);
        recyclerview.setAdapter(adapter);

    }
}