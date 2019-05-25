package com.levirs.example.slidinguppanel;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {


    List<Fragment> fraglist=new ArrayList<>();
    List<String> fragTitleList=new ArrayList<>();



    public void addFragment(Fragment f,String t)
    {
        fraglist.add(f);
        fragTitleList.add(t);
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragTitleList.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fraglist.get(i);
    }

    @Override
    public int getCount() {
        return fraglist.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
