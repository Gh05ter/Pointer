package com.example.mrsj.zoombug.WorkSpace.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mrsj.zoombug.Utils.Util;

/**
 * Created by MR.SJ on 2016/4/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Util.isNetworkAvailable(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
        }

    }
    abstract public void initView();
    abstract public void initData();
}
