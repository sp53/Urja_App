package com.example.deltasp.uidesign;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private static  final String TAG="RecyclerViewAdapter";
    private ArrayList<String> mnames=new ArrayList<>();
    private ArrayList<String> mImageURLs=new ArrayList<>();
    private Context mcontext;
    private Context maincontext;


    public RecyclerViewAdapter(Context context,ArrayList<String> names,ArrayList<String> imgurls)
    {
        mnames=names;
        mImageURLs=imgurls;
        mcontext=context;
    }

    public void receiveContext(Context con)
    {
        maincontext=con;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mcontext).asBitmap().load(mImageURLs.get(position)).into(((ViewHolder)holder).img);

        ((ViewHolder)holder).name.setText(mnames.get(position));
        ((ViewHolder)holder).img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intnt=new Intent(maincontext,Main2Activity.class);
                maincontext.startActivity(intnt);
            }
        });



    }


    @Override
    public int getItemCount() {
        return mnames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name;
        public ViewHolder(View itemview)
        {
            super(itemview);
            img=itemview.findViewById(R.id.image1);
            name=itemview.findViewById(R.id.name1);
        }

    }
}
