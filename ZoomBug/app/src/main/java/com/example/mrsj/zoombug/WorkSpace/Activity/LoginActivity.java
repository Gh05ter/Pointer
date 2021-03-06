package com.example.mrsj.zoombug.WorkSpace.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrsj.zoombug.R;
import com.example.mrsj.zoombug.Utils.Util;
import com.example.zoomeye.ZoomEye.User;
import com.example.zoomeye.ZoomEye.ZoomEye;

import java.util.Calendar;

/**
 * Created by MR.SJ on 2016/4/21.
 */
public class LoginActivity extends BaseActivity {
    private EditText user_et;
    private EditText passWord_et;
    private Button login;
    private CheckBox autoLogin;
    private CheckBox remPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        sharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);

        initView();
        initData();
    }

    @Override
    public void initView() {
        remPassword = (CheckBox) findViewById(R.id.rem_password);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);
        user_et = (EditText) findViewById(R.id.user_et);
        passWord_et = (EditText) findViewById(R.id.pw_et);
        login = (Button) findViewById(R.id.login_bt);

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        remPassword.setChecked(sharedPreferences.getBoolean("rem_password", false));
        autoLogin.setChecked(sharedPreferences.getBoolean("auto_login", false));
        if (remPassword.isChecked()) {
            user_et.setText(sharedPreferences.getString("username", ""));
            passWord_et.setText(sharedPreferences.getString("password", ""));
        }
        remPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("rem_password", isChecked);
                editor.commit();
                buttonView.setChecked(isChecked);
            }
        });
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("auto_login", isChecked);
                Util.logInfo("check", isChecked + "==++++++");
                editor.commit();
                buttonView.setChecked(isChecked);
            }
        });

    }

    @Override
    public void initData() {
        if (autoLogin.isChecked() && ( getTime()- sharedPreferences.getInt("time", 0)) <12) {
            Log.e("time",(getTime()+"     "+sharedPreferences.getInt("time", 0))+"");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setListening();
    }

    private User user;

    /**
     *
     * 进行login button 的监听
     */

    private ProgressDialog bar;
    public void setListening() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar = new ProgressDialog(LoginActivity.this);
                bar.setMessage("正在登录");
                bar.setIndeterminate(false);
                bar.setCancelable(true);
                bar.show();
                String userName = user_et.getText().toString().trim();
                String pwValue = passWord_et.getText().toString().trim();
                user = new User(userName, pwValue);
                if(Util.is3rd(getApplicationContext())||Util.isWifiEnabled(getApplicationContext())){
                    ZoomEye zoomEye = new ZoomEye(LoginActivity.this, handler, user);
                    zoomEye.login();

                }else{
                    bar.dismiss();
                    Toast.makeText(LoginActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    /**
     *  handler用于出口网络请求数据
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 1) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", msg.getData().get("access_token").toString());
                editor.putString("username", user.getUsername());
                editor.putString("password", user.getPassword());

                editor.putInt("time", getTime());
                editor.commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                bar.dismiss();
                if(Util.isNetworkAvailable(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this, "你的密码或账户有误", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_LONG).show();
                }

            }
            return true;
        }
    });


    public int getTime(){
        Calendar calendar=Calendar.getInstance();
        StringBuffer buffer=new StringBuffer();
        String hour=calendar.get(Calendar.HOUR_OF_DAY)+"";
        String day=calendar.get(Calendar.DAY_OF_MONTH)+"";
        buffer.append(day);
        Log.e("day",day+"");
        buffer.append(hour);
        Log.e("hour",hour+"");

        return Integer.valueOf(buffer.toString()).intValue();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}

