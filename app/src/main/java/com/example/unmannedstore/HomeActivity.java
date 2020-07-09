package com.example.unmannedstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.Constant;
import com.wefour.UnmannedStore.Utils.QRCodeUtil;

public class HomeActivity extends AppCompatActivity {

    private User user;
    private String SellerCardId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        user = (User)intent.getExtras().get("User");
        Log.v("2", user.toString());
        Button balanceBtn=(Button) findViewById(R.id.search_button);//关联查询按钮
        Button toHealthBtn=(Button) findViewById(R.id.report_button);//关联上报健康信息按钮
        Button shopBtn=(Button) findViewById(R.id.shop_button);//关联购物按钮
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ShoppingActivity.class);
                // intent.putExtra("marketID", "1000"); // 商家ID
                startActivity(intent);
            }
        });
        ImageButton scanBtn = (ImageButton) findViewById(R.id.scan_button);//关联扫码按钮
        ImageView mImageView = (ImageView) findViewById(R.id.code_image);
        final boolean health = user.isHealth();
        String color;
        if(health == true){
            color = "#71C671";
        }else{
            color = "#EE0000";
        }
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("@"+user.getEmail(),221, Color.parseColor(color) ,Color.parseColor("#FFFFFF"));
        mImageView.setImageBitmap(mBitmap);
        //查询按钮绑定监听事件
		balanceBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 给查询按钮添加点击响应事件
				Intent intent=new Intent(HomeActivity.this,BalanceTranscationActivity.class);
				//启动
				startActivity(intent);
			}
		});
        toHealthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给上报健康信息按钮添加监听事件
                Intent intent=new Intent(HomeActivity.this,HealthInfoActivity.class);
                intent.putExtra("User",user);
                //启动
                startActivity(intent);
            }

        });
//		shopBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 给购物按钮添加监听事件
//				Intent intent=new Intent(MainActivity.this,ShoppingActivity.class);
//				//启动
//				startActivity(intent);
//			}
//		});
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 给扫码按钮添加监听事件
                Intent intent=new Intent(HomeActivity.this, CaptureActivity.class);
                //启动
                startActivityForResult(intent, Constant.REQ_QR_CODE);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isChange=false;
        if(requestCode==1){
            isChange=data.getBooleanExtra("isChange",false);
        }else if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            //扫码结果获取商家银行卡号
            Bundle bundle = data.getExtras();
            SellerCardId = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
        }

        //此处的requestCode就是startActivityForResult里面传递的requestCode
        // 可以用来区分是哪个按钮请求的
        // 如果健康状况改变则重新生成二维码
        if(isChange == true){
            User user = new User();
            boolean health = user.isHealth();
            ImageView mImageView = (ImageView) findViewById(R.id.code_image);
            String color;
            if(health == true){
                color = "#71C671";
            }else{
                color = "#EE0000";
            }
//            Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("@"+user.getEmail(),221, Color.parseColor(color) ,Color.parseColor("#FFFFFF"));
//            mImageView.setImageBitmap(mBitmap);
        }
    }

}
