package com.example.zhangmingqi.ticket_app.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhangmingqi.ticket_app.R;

import java.util.List;



//选择电影院页面的ListView的adapter
public class CinemaAdapter extends BaseAdapter {
    public Context context;
    public List<CinemaInfo> list;
    public CinemaAdapter(Context _context, List<CinemaInfo> _list) {
        context = _context;
        list = _list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View theView;
        ViewHolder viewHolder;
        if (convertView == null) {
            theView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.cinemaname = (TextView)theView.findViewById(R.id.cinemaName);
            viewHolder.price = (TextView)theView.findViewById(R.id.price);
            viewHolder.ticketmargin = (TextView)theView.findViewById(R.id.tickets);
            viewHolder.time = (TextView)theView.findViewById(R.id.time);
            theView.setTag(viewHolder);
        } else {
            theView = convertView;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Log.d("adapter",list.get(position).cinemaname);
        viewHolder.ticketmargin.setText(list.get(position).ticketmargin);
        viewHolder.time.setText(list.get(position).time);
        viewHolder.price.setText(list.get(position).price);
        viewHolder.cinemaname.setText(list.get(position).cinemaname);
        return theView;
    }
    private class ViewHolder {
        public TextView cinemaname,price,ticketmargin,time;
    }
}
