package com.example.videoplayer;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {
    public Fragment currentFragment;

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> listFragment;

    public PageAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.fragmentManager = fm;
        this.listFragment = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return listFragment.get(arg0); //返回第几个fragment
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listFragment.size(); //总共有多少个fragment
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.currentFragment= (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }
}