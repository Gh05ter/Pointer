package com.example.mrsj.zoombug.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by MR.SJ on 2016/4/26.
 */
public class FragmentAdapters extends FragmentPagerAdapter {
    ArrayList<Fragment>  list;
    String[] titles;
    public FragmentAdapters(FragmentManager fm,ArrayList<Fragment> list,String[] titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}
