package com.example.zhangmingqi.ticket_app.Controller.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhangmingqi.ticket_app.Controller.MovieDetail;
import com.example.zhangmingqi.ticket_app.Model.MainViewModel;
import com.example.zhangmingqi.ticket_app.R;
import com.example.zhangmingqi.ticket_app.View.MainFragmentView;


//主页fragment
@SuppressLint("ValidFragment")
public class fragment extends Fragment {


    private MainFragmentView fragmentView;
   public MainViewModel mainViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment, container, false);
        fragmentView = new MainFragmentView(view,getActivity());
        mainViewModel = new MainViewModel(getActivity(),fragmentView.imgView);
        mainViewModel.setImage();
        fragmentView.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainViewModel.imageName.equals("尚未请求到电影信息！"))
                    Toast.makeText(getActivity(),"尚未请求到电影信息,请稍后再试！",Toast.LENGTH_SHORT).show();
                else {
                    String name = mainViewModel.imageName;
                    Bundle bundle = new Bundle();
                    bundle.putString("movieName",name);
                    Intent intent = new Intent(getContext(), MovieDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
