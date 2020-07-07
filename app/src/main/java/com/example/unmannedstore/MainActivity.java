package com.example.unmannedstore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {

    private User user;
    ImageView imageView;
    TextView textView;
    EditText emailText;
    EditText pwdText;
    private String email;
    private String pwd;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "37252f399f7dbf9a4608174c8a9457a4");

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        Button goRorSignUpBtn=(Button)findViewById(R.id.goRorSignUpBtn);
        Button loginBtn=(Button)findViewById(R.id.signIn);
        emailText=(EditText)findViewById(R.id.email);
        pwdText=(EditText)findViewById(R.id.password);

        user = new User();

        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {

            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });

        goRorSignUpBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {// 给bnt1添加点击响应事件
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        email = emailText.getText().toString();
        pwd = pwdText.getText().toString();
        final BmobQuery<User> queryEmail = new BmobQuery<>();
        BmobQuery<User> queryPwd = new BmobQuery<>();
        BmobQuery<User> and = new BmobQuery<>();
        BmobQuery<User> query;
        List<BmobQuery<User>> andQuery = new ArrayList<>();

        queryEmail.addWhereEqualTo("email", email);
        queryPwd.addWhereEqualTo("passWord", pwd);

        andQuery.add(queryEmail);
        andQuery.add(queryPwd);

        query = and.and(andQuery);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (null != e) {
                    show(e.getMessage());
                } else if (list.size() == 0) {
                    show("用户名或密码错误");
                } else {
                    //登陆成功跳转
                    queryEmail.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (null != e) {
                                show(e.getMessage());
                            } else if (list.size() == 0) {
                                show("查询错误");
                            } else {
                                user = list.get(0);
                                show("登录成功");
                                Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                                intent.putExtra("User", user);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

    }

    public void show(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}



