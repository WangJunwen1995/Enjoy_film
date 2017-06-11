package com.example.zhangmingqi.ticket_app.Controller;

/**
 * Created by 39092 on 2017/5/4.
 */
//单例类，用于保存用户状态相关信息
public class CurrentUser {
    //用户名、地点、登录状态
    public String userName,location;
    public boolean isLogin;
    public int number;//请求的电影id
    private CurrentUser() {
        number = 1;
        location = "广州";
        isLogin = false;
    }
    private static volatile CurrentUser instance;
    public static CurrentUser getInstance() {
        if (instance == null) {
            //同步代码块
            synchronized(CurrentUser.class) {
                if (instance == null) {
                    instance = new CurrentUser();
                }
            }
        }
        return instance;
    }
}
