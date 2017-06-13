package com.example.zhangmingqi.ticket_app.View;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangmingqi.ticket_app.Controller.ViewPager.DepthPageTransformer;
import com.example.zhangmingqi.ticket_app.Controller.ViewPager.QuickFragmentPageAdapter;
import com.example.zhangmingqi.ticket_app.Controller.ViewPager.fragment;
import com.example.zhangmingqi.ticket_app.R;

import java.util.List;




//负责展示主页面除fragment以外的数据展示
public class MainView {
    public ViewPager cycleViewPager;
    public ImageView login;
    public SwipeRefreshLayout swipeRefresh;
    public TextView location_text;
    public MainView(Activity activity) {
        initView(activity);
    }
    private void initView(Activity activity) {
        login = (ImageView)activity.findViewById(R.id.user_img);
        swipeRefresh = (SwipeRefreshLayout)activity.findViewById(R.id.swipeRefresh);
        cycleViewPager = (ViewPager)activity.findViewById(R.id.viewpager);
        location_text = (TextView)activity.findViewById(R.id.location_text);
    }
    public void setLocationText(String text) {
        if (text == null)
            text = "广州";
      //  Log.d("sssss", "setLocationText: "+text);
        location_text.setText(text);
    }
    public void showFragment(List<fragment> fragments, FragmentManager fragmentManager) {
        QuickFragmentPageAdapter adapter = new QuickFragmentPageAdapter(fragmentManager,fragments);
        cycleViewPager.setAdapter(adapter);
        cycleViewPager.setPageTransformer(true,new DepthPageTransformer());
        swipeRefresh.setRefreshing(false);
    }
}
