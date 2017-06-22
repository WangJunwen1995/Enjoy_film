package com.example.zhangmingqi.ticket_app.Model;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.zhangmingqi.ticket_app.Controller.CurrentUser;
import com.example.zhangmingqi.ticket_app.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//负责电影详情展示页面，网络请求，数据处理
public class MovieDetailModel {
    public Activity activity;
    public ImageView imgView;
    public String movieName;
    //public static final String IP = "http://192.168.1.57:3000/";
    public MovieDetailModel(Activity _activity, ImageView _imgView,String _movieName) {
        activity = _activity;
        imgView = _imgView;
        movieName = _movieName;
    }
    public void getImage() {
        String imageURL = CurrentUser.getInstance().IP+"image/"+movieName+".jpg";
        Glide.with(activity).load(imageURL).placeholder(R.drawable.male).error(R.drawable.female).centerCrop().priority(Priority.HIGH).into(imgView);
    }
    public void getDetail(final TextView textView) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Toast.makeText(activity,"正在获取电影信息...",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(CurrentUser.getInstance().IP).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<MovieInfo> call = service.getMovieInfo(movieName);
        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                //    Log.d("电影信息：",response.body().introduction);
                if (response.body() != null) {
                    StringBuffer buffer = new StringBuffer(response.body().introduction);
                    buffer.insert(buffer.indexOf("简介"),"\n\n");
                    textView.setText(buffer);
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                Toast.makeText(activity,"获取电影信息出现异常...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}