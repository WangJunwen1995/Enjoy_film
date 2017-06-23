package com.example.zhangmingqi.ticket_app.Controller.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


//自定义的适用于ViewPager的FragmentAdapter
public class QuickFragmentPageAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> mList;


    /**
     * @param fm
     * @param list

     */
    public QuickFragmentPageAdapter(FragmentManager fm, List<T> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }


}