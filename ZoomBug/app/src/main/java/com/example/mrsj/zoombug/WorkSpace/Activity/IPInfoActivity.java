package com.example.mrsj.zoombug.WorkSpace.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mrsj.zoombug.R;

import java.util.ArrayList;

/**
 * Created by MR.SJ on 2016/4/28.
 */
public class IPInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_activity);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IPInfoActivity.this,MainActivity.class));
                finish();
            }
        });
        ListView listView=(ListView)findViewById(R.id.list_view);
        final ArrayList<String> arrayList=this.getIntent().getStringArrayListExtra("ip");
        final ArrayList<String> urlList=this.getIntent().getStringArrayListExtra("url");
        BaseAdapter adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return arrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
               View view=getLayoutInflater().from(IPInfoActivity.this).inflate(R.layout.ip_list_item,parent,false);
                TextView ip=(TextView)view.findViewById(R.id.ip_tv);
                TextView url=(TextView)view.findViewById(R.id.url_tv);
                url.setText(urlList.get(position));
                ip.setText(arrayList.get(position));
                return view;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(IPInfoActivity.this,WebViewActivity.class);
                intent.putExtra("url","httP://www."+urlList.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(IPInfoActivity.this,MainActivity.class));
    }
}
