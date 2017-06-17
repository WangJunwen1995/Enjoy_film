package com.example.zhangmingqi.ticket_app.Model;


//存储选择电影院并购票页面的信息，此页面的数据由多个该对象组成的数组构成
public class CinemaInfo {

    public String cinemaname;
    public String time;
    public String price;
    public String ticketmargin;
    public CinemaInfo(String _cinemaName, String _tickets, String _price, String _time) {
        cinemaname = _cinemaName;
        ticketmargin = _tickets;
        price = _price;
        time = _time;
    }
   /* public String toString() {
        return cinemaname+ticketmargin+price+time;
    }*/
}
