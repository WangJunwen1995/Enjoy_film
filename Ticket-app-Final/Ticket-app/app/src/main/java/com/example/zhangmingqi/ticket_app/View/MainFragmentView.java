package com.example.zhangmingqi.ticket_app.View;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.example.zhangmingqi.ticket_app.Controller.ViewPager.DepthPageTransformer;
import com.example.zhangmingqi.ticket_app.Controller.ViewPager.QuickFragmentPageAdapter;
import com.example.zhangmingqi.ticket_app.Controller.ViewPager.fragment;
import com.example.zhangmingqi.ticket_app.R;

import java.util.List;




//负责处理fragment页面信息展示
public class MainFragmentView {
    public ImageView imgView;
    public SwipeRefreshLayout swipe;
    public ViewPager cycleViewPager;
    public MainFragmentView(View view, Activity activity) {
        initView(view,activity);
    }
    public void initView(View view,Activity activity) {
        imgView = (ImageView)view.findViewById(R.id.img);
        swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefresh);
        cycleViewPager = (ViewPager)activity.findViewById(R.id.viewpager);
    }
    public void showFragment(List<fragment> fragments, FragmentManager fragmentManager) {
        QuickFragmentPageAdapter adapter = new QuickFragmentPageAdapter(fragmentManager,fragments);
        cycleViewPager.setAdapter(adapter);
        cycleViewPager.setPageTransformer(true,new DepthPageTransformer());
        swipe.setRefreshing(false);
    }

}
