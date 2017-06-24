package com.example.zhangmingqi.ticket_app.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangmingqi.ticket_app.Model.MovieDetailModel;
import com.example.zhangmingqi.ticket_app.R;


//电影详情页面，包括电影封面显示，主演、简介等信息
public class MovieDetail extends AppCompatActivity {
    public Button buy_ticket;
    public Context context;
    public ImageView img_detail;
    public TextView description;
    public MovieDetailModel movieDetailModel;
    public static int MODE = MODE_PRIVATE;
    public static final String Preference_Name = "loginState";
    public String movieName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        context = MovieDetail.this;
        initView();
        addListenner();
        Bundle bundle = getIntent().getExtras();
        movieName = bundle.getString("movieName");
        description.setText(movieName);
        MovieDetailModel movieDetailModel = new MovieDetailModel(MovieDetail.this,img_detail,movieName);
        movieDetailModel.getDetail(description);
        movieDetailModel.getImage();
    }
    private void initView() {
        buy_ticket = (Button)findViewById(R.id.buy_button);
        img_detail = (ImageView)findViewById(R.id.img_detail);
        description = (TextView)findViewById(R.id.description);
    }
    private void addListenner() {
        buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Preference_Name,MODE);
                String isLogin = sharedPreferences.getString("isLogin","false");

                if (isLogin.equals("true")) {
                    CurrentUser.getInstance().isLogin = true;
                    Intent intent = new Intent(MovieDetail.this, SeclectCinema.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("movieName",movieName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Log.d("登录过","已经登录");
                } else {
                    Toast.makeText(MovieDetail.this,"请先登录！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MovieDetail.this, Login.class);
                    startActivity(intent);
                }
            }
        });
    }



}
