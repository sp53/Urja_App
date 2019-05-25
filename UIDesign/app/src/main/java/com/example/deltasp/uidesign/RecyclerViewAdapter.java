package com.example.deltasp.uidesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private static  final String TAG="RecyclerViewAdapter";
    private List<List<String>> m=new ArrayList<List<String>>();
    private List<List<String>> n=new ArrayList<List<String>>();
    private Context mcontext;
    private Context maincontext;


    public RecyclerViewAdapter(Context context,List<List<String>> x,List<List<String>> y)
    {
        m=x;
        n=y;
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

        List<BarEntry> barlist = new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            barlist.add(new BarEntry(Integer.parseInt(m.get(position).get(i)),Integer.parseInt(n.get(position).get(i))));
        }

        BarDataSet brdataset=new BarDataSet(barlist,"Just Checking");
        brdataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData brdata=new BarData(brdataset);
        brdata.setBarWidth(0.9f);

        ((ViewHolder)holder).bc.setVisibility(View.VISIBLE);
        ((ViewHolder)holder).bc.animateY(2000);
        ((ViewHolder)holder).bc.setData(brdata);
        ((ViewHolder)holder).bc.setFitBars(true);
        ((ViewHolder)holder).bc.invalidate();


    }


    @Override
    public int getItemCount() {
        return m.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        BarChart bc;
        public ViewHolder(View itemview)
        {
            super(itemview);
            bc=itemview.findViewById(R.id.chrt);
        }

    }


}
