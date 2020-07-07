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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private User user;
    private String phone;
    private String code;
    private String email;
    private String cardId;
    private String pwd;
    private String name;
    EditText phoneText;
    EditText codeText;
    EditText emailText;
    EditText cardText;
    EditText pwdText;
    EditText nameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button registerBtn=(Button)findViewById(R.id.signUp);
        Button sendCodeBtn=(Button)findViewById(R.id.sendCode);
        phoneText=(EditText)findViewById(R.id.phone);
        codeText=(EditText)findViewById(R.id.code);
        emailText=(EditText)findViewById(R.id.email);
        cardText=(EditText)findViewById(R.id.card);
        pwdText=(EditText)findViewById(R.id.password);
        nameText=(EditText)findViewById(R.id.name);

        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        sendCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }

    public void register() {
        phone=phoneText.getText().toString();
        code=codeText.getText().toString();
        email=emailText.getText().toString();
        cardId=cardText.getText().toString();
        pwd=pwdText.getText().toString();
        name=nameText.getText().toString();
        user = new User();
        user.setCardId(cardId);
        user.setEmail(email);
        user.setHealth(false);
        user.setName(name);
        user.setPassWord(pwd);
        user.setPhone(phone);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("email", email);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e != null) {
                    show(e.getMessage());
                } else if (list.size() != 0) {
                    show("用户名已存在");
                    return;
                } else {
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (null != e) {
                                show(e.getMessage());
                            } else {
                                Card card = new Card();
                                card.setBalance(1000.00);
                                card.setCardId(cardId);
                                card.setPassWord(pwd);
                                card.setUserName(email);
                                card.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (null != e) {
                                            show(2+e.getMessage());
                                        } else {
                                            BmobUser.signOrLoginByMobilePhone(phone, code, new LogInListener<BmobUser>() {
                                                @Override
                                                public void done(BmobUser bmobUser, BmobException e) {
                                                    if (e == null) {
                                                        //注册成功
                                                        show("注册成功");
                                                        Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                                                        startActivity(intent);
                                                    } else {
                                                        show("验证码错误");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void sendMsg() {
        phone = phoneText.getText().toString();
        BmobSMS.requestSMSCode(phone, "myTemplate", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    show("发送验证码成功");
                } else {
                    show("发送验证码失败，请重新发送");
                }
            }
        });
    }

    public void show(String msg) {
        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}