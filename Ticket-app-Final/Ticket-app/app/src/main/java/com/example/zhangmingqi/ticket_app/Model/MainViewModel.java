package com.example.zhangmingqi.ticket_app.Model;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.zhangmingqi.ticket_app.Controller.CurrentUser;
import com.example.zhangmingqi.ticket_app.Controller.ViewPager.fragment;
import com.example.zhangmingqi.ticket_app.R;
import com.example.zhangmingqi.ticket_app.View.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//负责主页面即电影展示页面，网络请求，数据处理
public class MainViewModel {
    public GetLocation getLocation;
    public MainView mainView;
    public Activity activity;
    public ImageView imgView;
    //public static final String IP = "http://172.20.10.10:3000/";
    public String imageName,imageURL;
    public MainViewModel(Activity _activity, MainView _mainView) {
        mainView = _mainView;
        activity = _activity;
    }
    public MainViewModel(Activity _activity, ImageView _imgView) {
        activity = _activity;
        imgView = _imgView;
        imageName = "尚未请求到电影信息！";
    }
    //获取定位
    public void getCurrentLocation() {
        getLocation = new GetLocation(activity);
        getLocation.initLocation();
        mainView.setLocationText(CurrentUser.getInstance().location);
    }
    //创建新的fragment
    public List<fragment> createFragment() {
        List<fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            fragments.add(new fragment());
        return fragments;
    }
    //加载网络图片
    public void setImage() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Toast.makeText(activity,"正在获取最新电影...",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(CurrentUser.getInstance().IP).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        int num = CurrentUser.getInstance().number;
        if (num > 15) {
            CurrentUser.getInstance().number = 1;
            num = 1;
        }
        Call<ImageName> call = service.getImageName(Integer.toString(num));
        CurrentUser.getInstance().number++;
        call.enqueue(new Callback<ImageName>() {
            @Override
            public void onResponse(Call<ImageName> call, Response<ImageName> response) {
                if (response.body() != null) {
                    imageName = response.body().image;

                    imageURL = CurrentUser.getInstance().IP+"image/"+imageName;
                    //  Log.d("名字",imageURL);
                    imageName = imageName.substring(0,imageName.indexOf("."));
                    Glide.with(activity).load(imageURL).placeholder(R.drawable.male).error(R.drawable.female).centerCrop().priority(Priority.HIGH).into(imgView);
                    //   Log.d("请求到名字",imageName);
                } else {
                    imageName = "尚未请求到电影信息！";
                    Log.d("response主页面", "空的");
                }
            }

            @Override
            public void onFailure(Call<ImageName> call, Throwable t) {
                Log.d("异常出现主页面",t.getMessage());
                Toast.makeText(activity,"出现异常，请求失败！",Toast.LENGTH_SHORT).show();
            }
        });

        //请求道图片以后setReshing(false)
    }
}