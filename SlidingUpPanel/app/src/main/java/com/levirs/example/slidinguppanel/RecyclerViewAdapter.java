package com.levirs.example.slidinguppanel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<List<String>> m = new ArrayList<List<String>>();
    private List<List<String>> n = new ArrayList<List<String>>();
    private Context mcontext;
    private Context maincontext;


    public RecyclerViewAdapter(Context context, List<List<String>> x, List<List<String>> y) {
        m = x;
        n = y;
        mcontext = context;
    }

    public void receiveContext(Context con) {
        maincontext = con;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View view;

        if(viewType==0)
        {

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.li, parent, false);
            return new ViewHolder(view);
        }
        else
        {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.li2, parent, false);
            return new ViewHolder2(view);

        }



    }

    @Override
    public int getItemViewType(int position) {

        if(position>=3)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");


        if(position==0) {

            List<BarEntry> barlist = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                barlist.add(new BarEntry(Integer.parseInt(m.get(position).get(i)), Float.parseFloat(n.get(position).get(i))));
            }

            BarDataSet brdataset = new BarDataSet(barlist, "Time vs Wind Power Graph");
            brdataset.setColors(ColorTemplate.rgb("23349C"));

            BarData brdata = new BarData(brdataset);
            brdata.setBarWidth(0.9f);

            Description d = new Description();
            d.setPosition(280, 100);
            d.setText("");

            ((ViewHolder) holder).bc.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).bc.setDescription(d);
            ((ViewHolder) holder).bc.animateY(2000);
            ((ViewHolder) holder).bc.setData(brdata);
            ((ViewHolder) holder).bc.setFitBars(true);
            ((ViewHolder) holder).bc.invalidate();
        }
        else if(position==1) {

            List<BarEntry> barlist = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                barlist.add(new BarEntry(Integer.parseInt(m.get(position).get(i)), Float.parseFloat(n.get(position).get(i))));
            }

            BarDataSet brdataset = new BarDataSet(barlist, "Time vs Solar Power Graph");
            brdataset.setColors(ColorTemplate.rgb("23349C"));

            BarData brdata = new BarData(brdataset);
            brdata.setBarWidth(0.9f);

            Description d = new Description();
            d.setPosition(280, 100);
            d.setText("");

            ((ViewHolder) holder).bc.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).bc.setDescription(d);
            ((ViewHolder) holder).bc.animateY(2000);
            ((ViewHolder) holder).bc.setData(brdata);
            ((ViewHolder) holder).bc.setFitBars(true);
            ((ViewHolder) holder).bc.invalidate();
        }
        else if(position==2) {

            List<BarEntry> barlist = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                barlist.add(new BarEntry(Integer.parseInt(m.get(position).get(i)), Float.parseFloat(n.get(position).get(i))));
            }

            BarDataSet brdataset = new BarDataSet(barlist, "Time vs Demand Graph");
            brdataset.setColors(ColorTemplate.rgb("23349C"));

            BarData brdata = new BarData(brdataset);
            brdata.setBarWidth(0.9f);

            Description d = new Description();
            d.setPosition(280, 100);
            d.setText("");

            ((ViewHolder) holder).bc.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).bc.setDescription(d);
            ((ViewHolder) holder).bc.animateY(2000);
            ((ViewHolder) holder).bc.setData(brdata);
            ((ViewHolder) holder).bc.setFitBars(true);
            ((ViewHolder) holder).bc.invalidate();
        }
        else
        {

            List<PieEntry> pielist = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                pielist.add(new PieEntry(Integer.parseInt(m.get(position).get(i)), Float.parseFloat(n.get(position).get(i))));
            }

            PieDataSet pdataset = new PieDataSet(pielist, "Wind/Solar Graph");
            pdataset.setColors(ColorTemplate.COLORFUL_COLORS);

            PieData pdata = new PieData(pdataset);

            Description d = new Description();
            d.setPosition(280, 100);
            d.setText("");

            ((ViewHolder2) holder).pc.setVisibility(View.VISIBLE);
            ((ViewHolder2) holder).pc.setDescription(d);
            ((ViewHolder2) holder).pc.animateY(2000);
            ((ViewHolder2) holder).pc.setData(pdata);
            ((ViewHolder2) holder).pc.invalidate();

        }


    }


    @Override
    public int getItemCount() {
        return m.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BarChart bc;

        public ViewHolder(View itemview) {
            super(itemview);
            bc = itemview.findViewById(R.id.chrt);
        }

    }
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        PieChart pc;

        public ViewHolder2(View itemview) {
            super(itemview);
            pc = itemview.findViewById(R.id.pchrt);
        }

    }


}
