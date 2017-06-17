package com.example.zhangmingqi.ticket_app.Model;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//负责选择电影院并购票页面的数据处理、网络访问以及调用View的函数显示在用户界面
public class CinemaModel {
    public static final String IP = "http://192.168.1.57:3000/";
    public List<CinemaInfo> list;
    public Activity activity;
    public ListView listView;
    public String date;
    public Map<String,List<CinemaInfo>> allData;
    public CinemaAdapter adapter;
    public CinemaModel(Activity _activity, ListView _listView, Map<String,List<CinemaInfo>> _allData, CinemaAdapter _adapter) {
        activity = _activity;
        listView = _listView;
        allData = _allData;
        adapter = _adapter;
    }
    public void getData(String movieName, String _date) {
        date = _date;
        list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Toast.makeText(activity,"正在获取...",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<ArrayList<CinemaRetro>> call = service.getCinemaInfo(date,movieName);
        call.enqueue(new Callback<ArrayList<CinemaRetro>>() {
            @Override
            public void onResponse(Call<ArrayList<CinemaRetro>> call, Response<ArrayList<CinemaRetro>> response) {
                Log.d("进来了","进来了");
                if (response != null) {
                    List<CinemaRetro> listResponse = response.body();
                    for (int i = 0; i < listResponse.size();i++) {
                        CinemaInfo cinemaInfo = new CinemaInfo(listResponse.get(i).cinemaname,listResponse.get(i).ticketmargin,listResponse.get(i).price,listResponse.get(i).time);
                        list.add(cinemaInfo);
                        Log.d("cinemaInfo",cinemaInfo.toString());
                    }
                    showListView();
                    allData.put(date,list);
                   Log.d("有回复",list.get(0).cinemaname);
                }

                else {
                    Log.d("空的response","kongde");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CinemaRetro>> call, Throwable t) {
                Log.d("异常",t.getMessage());
                Toast.makeText(activity,"出现异常，获取失败！",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void buyTickets(String movieName,String cinemaName,String date,String time) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Toast.makeText(activity,"正在获取...",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<Boolean> call = service.postBuyTickets(cinemaName,date,movieName,time);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {
                    Toast.makeText(activity,"购票成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity,"购票失败！",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(activity,"服务器异常，稍后重试！",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void showListView() {
        if (allData.size() == 0) {
            Log.d("第一次创建adapter",Integer.toString(allData.size()));
            adapter = new CinemaAdapter(activity, list);
            listView.setAdapter(adapter);
        } else {
            Log.d("第二次创建adapter",list.get(0).cinemaname);
            adapter.notifyDataSetChanged();
        }
    }
}
