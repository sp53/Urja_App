package com.levirs.example.slidinguppanel;

import android.util.Log;

public class assistdata {
    public static String s;
    public static String w;
    public static String t;
    public static String r;
    public static String d;
    public static String ans="";
    public static int suf;

    public static void calc()
    {
        Float is,iw,it,ir,id;
        is=Float.parseFloat(s.trim().substring(0,s.length()-3));
        iw=Float.parseFloat(w.trim().substring(0,w.length()-3));
        it=Float.parseFloat(t.trim().substring(0,t.length()-3));
        ir=Float.parseFloat(r.trim().substring(0,r.length()-3));
        id=Float.parseFloat(d.trim().substring(0,d.length()-3));
        //Log.d("================",""+is+" "+ir);

        if(is+iw+it>=id)
        {
            ans="The daily predicted power generation from Solar, Wind power plant including power generated " +
                    "from thermal and other electric plants is sufficient to meet the predicted demand of the population." ;
            suf=1;
        }
        if(is+iw+it<id && is+iw+it+ir>=id)
        {
            ans="The daily predicted power generation from Solar, Wind power plant and including power generated " +
                    "from thermal and other electric plants is NOT sufficient " + (id-(is+iw+it))+
            " can be extracted from reserve storage to meet the predicted demand of the population.";
            suf=2;
        }
        if(is+iw+it+ir<id)
        {
            ans="The daily predicted power generation from Solar, Wind power plant and including power generated " +
                    "from thermal and other electric plants plus the reserve storage is NOT sufficient " +
                    " to meet the predicted demand of the population. Power cut is Necessary.";
            suf=0;
        }
    }

}
