package com.example.zhangmingqi.ticket_app.Model;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.zhangmingqi.ticket_app.Controller.CurrentUser;
import com.example.zhangmingqi.ticket_app.Controller.MainActivity;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//负责登录页面的网络请求，数据处理
public class LoginModel {
    public static final String IP = "http://192.168.1.57:3000/";
    public String isLogin = "false";
    public Activity activity;
    public static int MODE;
    public Boolean isSuccess;
    public static final String Preference_Name = "loginState";
    public LoginModel(Activity _activity) {
        activity = _activity;
        MODE = activity.MODE_PRIVATE;

    }
    public String login(String username,String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Toast.makeText(activity,"正在登录...",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<Boolean> call = service.login(username,password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response == null)
                    Log.d("response", "空的");
                else {
                    Log.d("有数据", response.body().toString());
                    if (response.body().toString().equals("true")) {
                        isLogin = "true";
                        CurrentUser.getInstance().isLogin = true;
                        SharedPreferences sharedPreferences = activity.getSharedPreferences(Preference_Name,MODE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("isLogin","true");
                        editor.commit();
                        Toast.makeText(activity,"登录成功！",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,MainActivity.class);
                        activity.startActivity(intent);

                    }
                    else {
                        isLogin = "false";
                        Toast.makeText(activity,"登录失败！",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("异常出现",t.getMessage());
                isLogin = "false";
                Toast.makeText(activity,"出现异常，登录失败！",Toast.LENGTH_SHORT).show();
            }
        });
        return isLogin;
    }
    public Boolean register(String username,String password) {
        isSuccess = false;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Toast.makeText(activity,"正在登录...",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<Boolean> call = service.register(username,password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response == null)
                    Log.d("response", "空的");
                else {
                    Log.d("有数据", response.body().toString());
                    if (response.body()) {
                        isSuccess = true;
                        Toast.makeText(activity,"注册成功，请登录！",Toast.LENGTH_SHORT).show();


                    }
                    else {
                        isSuccess = false;
                        Toast.makeText(activity,"注册失败！",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("异常出现",t.getMessage());
                isLogin = "false";
                Toast.makeText(activity,"出现异常，登录失败！",Toast.LENGTH_SHORT).show();
            }
        });
        return isSuccess;
    }
}
