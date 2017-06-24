package com.example.zhangmingqi.ticket_app.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//Retrofit框架接口
public interface RetrofitInterface {

        @FormUrlEncoded
        @POST("login")
        Call<Boolean> login(@Field("username") String username, @Field("password") String password);

        @FormUrlEncoded
        @POST("signup")
        Call<Boolean> register(@Field("username") String username, @Field("password") String password);

        @FormUrlEncoded
        @POST("image")
        Call<ImageName> getImageName(@Field("newid") String id);

        @FormUrlEncoded
        @POST("movie")
        Call<MovieInfo> getMovieInfo(@Field("moviename") String moviename);

        @FormUrlEncoded
        @POST("cinema")
        Call<ArrayList<CinemaRetro>> getCinemaInfo(@Field("date") String date, @Field("moviename") String moviename);

        @FormUrlEncoded
        @POST("ticket")
        Call<Boolean> postBuyTickets(@Field("cinemaname") String cinemaname, @Field("date") String date, @Field("moviename") String moviename, @Field("time") String time);

}
