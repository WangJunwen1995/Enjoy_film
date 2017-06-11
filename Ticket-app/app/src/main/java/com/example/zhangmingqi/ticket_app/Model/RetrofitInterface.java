package com.example.zhangmingqi.ticket_app.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 39092 on 2017/5/19.
 */
//Retrofit框架接口
public interface RetrofitInterface {

        @FormUrlEncoded
        @POST("login")
        Call<Boolean> login(@Field("username") String username, @Field("password") String password);

        @FormUrlEncoded
        @POST("signup")
        Call<Boolean> register(@Field("username") String username, @Field("password") String password);


}
