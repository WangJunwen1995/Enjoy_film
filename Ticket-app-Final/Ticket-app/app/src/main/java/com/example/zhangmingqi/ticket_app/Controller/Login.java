package com.example.zhangmingqi.ticket_app.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhangmingqi.ticket_app.Model.LoginModel;
import com.example.zhangmingqi.ticket_app.R;



//登录或注册页面相关
public class Login extends AppCompatActivity {
    public Button choose_login,choose_register,commit_button;

    public LoginModel loginModel;
    public EditText usernameInput,passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        loginModel = new LoginModel(Login.this);
        addListenner();
    }
    private void initView() {
        choose_login = (Button)findViewById(R.id.chooseLogin);
        choose_register = (Button)findViewById(R.id.chooseRegister);
        commit_button = (Button)findViewById(R.id.commit_Button);
        usernameInput = (EditText)findViewById(R.id.LOR_UserName);
        passwordInput = (EditText)findViewById(R.id.LOR_Password);
        choose_login.setTextScaleX(1.2f);
    }
    private void addListenner() {
        choose_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_login.setTextScaleX(1.2f);
                choose_register.setTextScaleX(1);
                commit_button.setText("登录");
            }
        });
        choose_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_register.setTextScaleX(1.2f);
                choose_login.setTextScaleX(1);
                commit_button.setText("注册");
            }
        });
        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("text:",usernameInput.getText());
                if (usernameInput.getText().toString().equals("") || passwordInput.getText().toString().equals(""))
                    Toast.makeText(Login.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                else if (commit_button.getText().equals("登录")) {
                loginModel.login(usernameInput.getText().toString(),passwordInput.getText().toString());
            } else if (commit_button.getText().equals("注册")) {
                    //注册
                loginModel.register(usernameInput.getText().toString(),passwordInput.getText().toString());
                }

            }
        });
    }
}
