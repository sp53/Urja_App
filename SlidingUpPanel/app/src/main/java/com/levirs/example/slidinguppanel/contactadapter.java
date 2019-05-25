package com.levirs.example.slidinguppanel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class contactadapter extends ArrayAdapter {

    List list=new ArrayList();
    public contactadapter(Context context, int resource) {
        super(context, resource);
    }



    public void add(contacts object) {
        super.add(object);
        list.add(object);
    }

    public void add(List l)
    {
        list=l;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {



        View row;
        row = convertView;
        contactholder chld;
        if(row==null)
        {
            LayoutInflater linf = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=linf.inflate(R.layout.row,parent,false);
            chld=new contactholder();
            chld.tx_h=row.findViewById(R.id.t1);
            chld.tx_t=row.findViewById(R.id.t2);
            chld.imgupdown=row.findViewById(R.id.imgud);

            row.setTag(chld);

        }
        else
        {
            chld=(contactholder) row.getTag();
        }


        contacts cobj=(contacts)this.getItem(position);
        chld.tx_h.setText(cobj.getH());
        chld.tx_t.setText(cobj.getT());
        chld.imgupdown.setImageResource(R.drawable.up);
        float hld=Float.parseFloat(cobj.getH());
        float prev;

        if(position>0) {
            contacts cobj2 = (contacts) this.getItem(position - 1);

            prev= Float.parseFloat(cobj2.getH());
        }
        else
        {
            prev=0;
        }

        if(hld>prev)
        {
            chld.imgupdown.setImageResource(R.drawable.up);
        }
        else
        {
            chld.imgupdown.setImageResource(R.drawable.down);
        }


        return row;
    }


    static class contactholder
    {
        TextView tx_t,tx_h;
        ImageView imgupdown;
    }
}
