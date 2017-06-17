package com.example.zhangmingqi.ticket_app.Controller;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.zhangmingqi.ticket_app.Model.CinemaAdapter;
import com.example.zhangmingqi.ticket_app.Model.CinemaInfo;
import com.example.zhangmingqi.ticket_app.Model.CinemaModel;
import com.example.zhangmingqi.ticket_app.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



//负责展示可选择的电影院，以及票价、票余量、播放时间
public class SeclectCinema extends TabActivity {
    public Button sortByPrice_button,sortByTickets_button;
    public ListView listView;
    public TabHost tabHost;
    public Map<String,List<CinemaInfo>> allData;
    public CinemaModel cinemaModel;
    public String movieName;
    public String date;
    public CinemaAdapter adapter;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.select_cinema);
       initView();
      /* Calendar c = Calendar.getInstance();
       int day = c.get(Calendar.DAY_OF_MONTH);
       int month = c.get(Calendar.MONTH)+1;*///为何不准，加1才可以
       int day = 1;
       int month = 6;
       //Log.d("时间",month+"");
       addListenner();
       //改变button背景和颜色
       Bundle bundle = getIntent().getExtras();
       movieName = bundle.getString("movieName");
      // Log.d("moviename",movieName);
       allData = new HashMap<>();
       cinemaModel = new CinemaModel(SeclectCinema.this,listView,allData,adapter);
       sortByPrice_button.setBackgroundColor(Color.WHITE);
       sortByPrice_button.setTextColor(Color.RED);
       String tab1 = Integer.toString(month)+ "月" + Integer.toString(day) + "日";
       String tab2 = Integer.toString(month)+ "月" + Integer.toString(day+1) + "日";
       String tab3 = Integer.toString(month)+ "月" + Integer.toString(day+2) + "日";
       String tab4 = Integer.toString(month)+ "月" + Integer.toString(day+3) + "日";
       String tab5 = Integer.toString(month)+ "月" + Integer.toString(day+4) + "日";
       tabHost.addTab(tabHost.newTabSpec(tab1).setIndicator(tab1).setContent(R.id.sortLinearLayout));
       tabHost.addTab(tabHost.newTabSpec(tab2).setIndicator(tab2).setContent(R.id.sortLinearLayout));
       tabHost.addTab(tabHost.newTabSpec(tab3).setIndicator(tab3).setContent(R.id.sortLinearLayout));
       tabHost.addTab(tabHost.newTabSpec(tab3).setIndicator(tab3).setContent(R.id.sortLinearLayout));
       tabHost.addTab(tabHost.newTabSpec(tab4).setIndicator(tab4).setContent(R.id.sortLinearLayout));
       tabHost.setBackgroundColor(Color.RED);
       tabHost.setCurrentTab(0);//会调用一次TabChangedLisenner
       date = tabHost.getCurrentTabTag();
   }
   private void initView() {

       tabHost = getTabHost();
       sortByPrice_button = (Button)findViewById(R.id.sortByPrice_button);
       sortByTickets_button = (Button)findViewById(R.id.sortByTickets_button);
       listView = (ListView)findViewById(R.id.listView);

   }
   private void addListenner() {
       tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                updateTab(tabHost);

            }
        });
       sortByTickets_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               v.setBackgroundColor(Color.WHITE);
                sortByTickets_button.setTextColor(Color.RED);
               sortByPrice_button.setTextColor(Color.WHITE);
               sortByPrice_button.setBackgroundColor(Color.RED);

           }
       });
       sortByPrice_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               v.setBackgroundColor(Color.WHITE);
               sortByPrice_button.setTextColor(Color.RED);
               sortByTickets_button.setTextColor(Color.WHITE);
               sortByTickets_button.setBackgroundColor(Color.RED);
           }
       });
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView price = (TextView)view.findViewById(R.id.price);
               final TextView cinemaName = (TextView)view.findViewById(R.id.cinemaName);
               final TextView time = (TextView)view.findViewById(R.id.time);
                AlertDialog.Builder dialog = new AlertDialog.Builder(SeclectCinema.this);
                dialog.setTitle("购票确认").setMessage("是否花费"+price.getText()+"元购买"+movieName+"？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //请求购票
                        cinemaModel.buyTickets(movieName,cinemaName.getText().toString(),date,time.getText().toString());
                    }
                }).setNegativeButton("取消",null).show();
           }
       });
   }
   //更新每个Tab
    private void updateTab(final TabHost tabHost) {
        List<CinemaInfo> list;
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            TextPaint tp = tv.getPaint();
            tp.setFakeBoldText(true);
            tp.setTextSize(30f);
            if (tabHost.getCurrentTab() == i) {//选中
                view.setBackgroundColor(Color.WHITE);
                tv.setTextColor(Color.RED);

                if (!allData.containsKey(allData.get(date))) {
                    //不包含当前tab的数据
                    Log.d("allData大小",Integer.toString(allData.size()));
                    date = tabHost.getCurrentTabTag();
                    Log.d("Tag数据不包含",tabHost.getCurrentTabTag());
                    if (adapter == null)
                       Log.d("adapter","是空的");
                    cinemaModel.getData(movieName,date);
                } else {
                    assignValue(cinemaModel.list,allData.get(date));
                    Log.d("Tag数据包含",tabHost.getCurrentTabTag());
                    cinemaModel.showListView();
                }

            } else {//不选中

                view.setBackgroundColor(Color.RED);
                tv.setTextColor(Color.WHITE);
            }

        }
    }
    private void assignValue(List<CinemaInfo> d, List<CinemaInfo> s) {
        for (int i = 0; i < s.size();i++) {
            d.get(i).ticketmargin = s.get(i).ticketmargin;
            d.get(i).time = s.get(i).time;
            d.get(i).price = d.get(i).price;
            d.get(i).cinemaname = d.get(i).cinemaname;
        }
    }

}
