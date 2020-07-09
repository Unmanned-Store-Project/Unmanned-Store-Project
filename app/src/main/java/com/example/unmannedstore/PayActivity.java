package com.example.unmannedstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends AppCompatActivity {

    private TextView tvTotalPay;
    private Button btnPay;
    private EditText etPassword;
    private String marketID, password;
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent receiveIntent = getIntent();
        marketID = receiveIntent.getStringExtra("marketID"); //商家标识码
        sum = receiveIntent.getIntExtra("sum",0);

        tvTotalPay = findViewById(R.id.tv_totalpay);
        btnPay = findViewById(R.id.btn_pay);
        etPassword = findViewById(R.id.et_password);

        tvTotalPay.setText("您需要支付的总价为：" + sum);
        password = etPassword.getText().toString(); //支付密码

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //和数据库进行交互

                //退回到主页
                Intent goBackHome = new Intent(PayActivity.this, HomeActivity.class);
                startActivity(goBackHome);
            }
        });
        //支付模块
    }
}