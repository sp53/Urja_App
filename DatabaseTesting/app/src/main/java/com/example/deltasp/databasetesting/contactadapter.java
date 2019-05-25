package com.example.deltasp.databasetesting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            chld.tx_email=row.findViewById(R.id.t1);
            chld.tx_name=row.findViewById(R.id.t2);
            chld.tx_mobile=row.findViewById(R.id.t3);

            row.setTag(chld);

        }
        else
        {
            chld=(contactholder) row.getTag();
        }

        contacts cobj=(contacts)this.getItem(position);
        chld.tx_name.setText(cobj.getTitle());
        chld.tx_email.setText(cobj.getDescription());
        chld.tx_mobile.setText(cobj.getImg());

        return row;
    }


    static class contactholder
    {
        TextView tx_name,tx_email,tx_mobile;
    }
}
