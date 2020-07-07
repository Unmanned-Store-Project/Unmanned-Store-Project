package com.example.unmannedstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class HealthInfoActivity extends AppCompatActivity {

    private RadioButton rdBtnTempT, rdBtnTempF;
    private RadioButton rdBtnIdfT, rdBtnIdfF;
    private RadioButton rdBtnSigT, rdBtnSigF;
    private RadioButton rdBtnHcodeT, rdBtnHcodeF;
    private boolean isHealthy = false, health = false;
    private boolean isTemp = false, isIdf = false, isSig = false, isHcode = false;
    private Button btnSubInfo;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_info);
        Intent intent = getIntent();
        user = (User) intent.getExtras().get("User");
        Log.v("2", 2+user.toString());
        health = user.isHealth();

        rdBtnTempT = findViewById(R.id.temp_rbt);
        rdBtnTempF = findViewById(R.id.temp_rbf);
        rdBtnIdfT = findViewById(R.id.idf_rbt);
        rdBtnIdfF = findViewById(R.id.idf_rbf);
        rdBtnSigT = findViewById(R.id.sig_rbt);
        rdBtnSigF = findViewById(R.id.sig_rbf);
        rdBtnHcodeT = findViewById(R.id.healthcode_rbt);
        rdBtnHcodeF = findViewById(R.id.healthcode_rbf);
        setListeners();
        btnSubInfo = (Button)findViewById(R.id.btn_subinfo);
        btnSubInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Go to HeathInfoPage
                isHealthy = (!isTemp)&&(!isIdf)&&(!isSig)&&(isHcode);
                if (isHealthy != health) {
                    user.setHealth(isHealthy);
                    health();
                } else {
                    show("上传健康信息成功");
                    Intent intent = new Intent(HealthInfoActivity.this,HomeActivity.class);
                    intent.putExtra("User",user);
                    startActivity(intent);
                }
//                Toast toast = Toast.makeText(HealthInfoActivity.this,""+isHealthy, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
            }
        });
    }

    public void health() {
        String email = user.getEmail();
        BmobQuery<User> queryEmail = new BmobQuery<>();
        queryEmail.addWhereEqualTo("email", email);
        queryEmail.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (null != e) {
                    show(e.getMessage());
                } else if (list.size() == 0) {
                    show("查询错误");
                } else {
                    User tmpUser = list.get(0);
                    tmpUser.setHealth(true);
                    tmpUser.update(tmpUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                show("上传健康信息成功");
                                Intent intent = new Intent(HealthInfoActivity.this,HomeActivity.class);
                                intent.putExtra("User",user);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    public void show(String msg) {
        Toast.makeText(HealthInfoActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private void setListeners(){
        OnClick onClick = new OnClick();
        rdBtnTempT.setOnClickListener(onClick);
        rdBtnTempF.setOnClickListener(onClick);
        rdBtnIdfT.setOnClickListener(onClick);
        rdBtnIdfF.setOnClickListener(onClick);
        rdBtnSigT.setOnClickListener(onClick);
        rdBtnSigF.setOnClickListener(onClick);
        rdBtnHcodeT.setOnClickListener(onClick);
        rdBtnHcodeF.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // Intent intent = null;
            switch (view.getId()){
                case R.id.temp_rbt:
                    isTemp = true;
                    break;
                case R.id.temp_rbf:
                    isTemp = false;
                    break;
                case R.id.idf_rbt:
                    isIdf = true;
                    break;
                case R.id.idf_rbf:
                    isIdf = false;
                    break;
                case R.id.sig_rbt:
                    isSig = true;
                    break;
                case R.id.sig_rbf:
                    isSig = false;
                    break;
                case R.id.healthcode_rbt:
                    isHcode = true;
                    break;
                case R.id.healthcode_rbf:
                    isHcode = false;
                    break;
            }
        }
    }
}