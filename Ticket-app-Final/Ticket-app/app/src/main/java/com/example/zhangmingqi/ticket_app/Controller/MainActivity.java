package com.example.zhangmingqi.ticket_app.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.zhangmingqi.ticket_app.Controller.ViewPager.fragment;
import com.example.zhangmingqi.ticket_app.Model.MainViewModel;
import com.example.zhangmingqi.ticket_app.R;
import com.example.zhangmingqi.ticket_app.View.MainView;

import java.util.ArrayList;
import java.util.List;

//主页，利用ViewPager展示电影
public class MainActivity extends AppCompatActivity {
    public MainView mainView;
    public MainViewModel mainViewModel;
    public List<fragment> fragments;
    public FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainView = new MainView(this);
        addListenner();
        mainViewModel = new MainViewModel(this,mainView);
        mainViewModel.getCurrentLocation();
        fragments=new ArrayList<fragment>();
        fragmentManager = getSupportFragmentManager();
        fragments = mainViewModel.createFragment();
        //view层负责显示数据
        mainView.showFragment(fragments,fragmentManager);
        //停止刷新动画
        //  mainView.swipeRefresh.setRefreshing(false);
    }

    private void addListenner() {
        mainView.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUser.getInstance().isLogin)
                    Toast.makeText(MainActivity.this,"您已经登录过！",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
            }
        });
        //setReshing() true表示展示动画false不展示刷新动画
        mainView.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this,"正在刷新1",Toast.LENGTH_SHORT).show();
                //model处理fragment数据更新
                fragments = mainViewModel.createFragment();
                mainView.showFragment(fragments,fragmentManager);

            }
        });
        mainView.cycleViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mainView.swipeRefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mainView.swipeRefresh.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

}

