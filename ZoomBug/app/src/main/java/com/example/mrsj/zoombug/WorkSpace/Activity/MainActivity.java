package com.example.mrsj.zoombug.WorkSpace.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrsj.zoombug.Adapter.FragmentAdapters;
import com.example.mrsj.zoombug.Fragment.SeebugFragment;
import com.example.mrsj.zoombug.Fragment.ZoomEyeFragment;
import com.example.mrsj.zoombug.R;
import com.example.mrsj.zoombug.Utils.Util;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
       sharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        if(!Util.isNetworkAvailable(this.getApplicationContext())){
            Toast.makeText(MainActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
        }
        initView();
        initData();
    }

    @Override
    public void initView() {

        drawerLayout=(DrawerLayout)findViewById(R.id.draw_layout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        TextView userName_tv=(TextView)findViewById(R.id.user_name);
        userName_tv.setText(sharedPreferences.getString("username","无名"));

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        final ViewPager viewPager=(ViewPager)findViewById(R.id.view_pager);
        String[] titles={"ZoomEye","Seebug"};
        ArrayList<Fragment> list=new ArrayList<>();
        list.add(new ZoomEyeFragment());
        list.add(new SeebugFragment());
        viewPager.setAdapter(new FragmentAdapters(getSupportFragmentManager(),list,titles));
        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        RadioGroup radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.lazy_rd:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.hunger_rd:
                        Toast.makeText(MainActivity.this,"此功能正在完善，敬请期待",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.big_rd:
                        Toast.makeText(MainActivity.this,"此功能正在完善，敬请期待",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {

        this.finish();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }
}
