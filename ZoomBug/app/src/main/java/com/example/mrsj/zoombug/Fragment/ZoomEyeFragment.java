package com.example.mrsj.zoombug.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrsj.zoombug.R;
import com.example.mrsj.zoombug.WorkSpace.Activity.IPInfoActivity;
import com.example.zoomeye.ZoomEye.User;
import com.example.zoomeye.ZoomEye.ZoomEye;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MR.SJ on 2016/4/26.
 */
public class ZoomEyeFragment extends Fragment {
    private View view;
    ProgressDialog bar;
    private ArrayList<String> ipList;
    private ArrayList<String> urlList;
    private ArrayList<String> infoList;
    private boolean flag=false;
     ZoomEye zoomEye;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result="";

            switch (msg.what){
                case 3:
                    flag=true;
                    result=msg.getData().getString("response");
                    try{
                        JSONArray array=new JSONObject(result).getJSONArray("matches");
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject=array.getJSONObject(i);
                            if(jsonObject.toString().isEmpty()){
                                flag=true;
                            }
                            Log.e("ip",jsonObject.getString("ip")+jsonObject.getString("site"));
                            ipList.add(jsonObject.getString("ip"));
                            urlList.add(jsonObject.getString("site"));
                            infoList.add(jsonObject.getString("headers"));
                        }
                    }catch (Exception e){

                    }
                    break;
                case 4:
                    Toast.makeText(getActivity(),"出现错误",Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    flag=true;
                    result=msg.getData().getString("response");
                    try{
                        JSONArray array=new JSONObject(result).getJSONArray("matches");
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject=array.getJSONObject(i);
                            Log.e("ip",jsonObject.getString("ip"));
                            ipList.add(jsonObject.getString("ip"));
                            urlList.add(jsonObject.getString("timestamp")+"");
                            JSONObject json=jsonObject.getJSONObject("portinfo");

                            infoList.add(json.getString("banner"));
                        }
                    }catch (Exception e){

                    }

                    break;
                case 6:
                    Toast.makeText(getActivity(),"出现错误",Toast.LENGTH_LONG).show();
                    break;
            }

            TextView textView=(TextView)view.findViewById(R.id.info_tv);
            textView.setText(result);
            bar.dismiss();

            if(flag){
                Intent intent=new Intent(getActivity(), IPInfoActivity.class);
                intent.putStringArrayListExtra("ip",ipList);
                intent.putStringArrayListExtra("url",urlList);
                intent.putStringArrayListExtra("info",infoList);
                startActivity(intent);
                getActivity().finish();
               ZoomEye.requestQueue.stop();
                flag=false;
            }

            return true;
        }
    });


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.zoomeye_fragment,container,false);
        final EditText searchView=(EditText) view.findViewById(R.id.search_view);
        ipList=new ArrayList<>();
        urlList=new ArrayList<>();
        infoList=new ArrayList<>();
        bar=new ProgressDialog(getActivity());
        Button webSearch=(Button)view.findViewById(R.id.web_search_bt);
        Button hostSearch=(Button)view.findViewById(R.id.host_search_bt);
        final SharedPreferences sharedPreferences=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        final User user=new User(sharedPreferences.getString("username",""),sharedPreferences.getString("password",""));
        /**
         *
         * 执行Web Search 的相关操作
         */
        webSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dork=searchView.getText().toString();
                if(!dork.isEmpty()){
                    bar = new ProgressDialog(getActivity());
                    bar.setMessage("正在加载");
                    bar.setIndeterminate(false);
                    bar.setCancelable(false);
                    bar.show();
                    zoomEye=new ZoomEye(getActivity(),handler,user);
                    zoomEye.searchWeb(dork,sharedPreferences.getString("token",""));

                }else{
                    Toast.makeText(getActivity(),"输入为空",Toast.LENGTH_SHORT).show();
                }

            }
        });
        /**
         * 执行 Host Search 的相关操作S
         */
        hostSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dork=searchView.getText().toString();
                if(!dork.isEmpty()){
                    bar = new ProgressDialog(getActivity());
                    bar.setMessage("正在加载");
                    bar.setIndeterminate(false);
                    bar.setCancelable(false);
                    bar.show();
                    zoomEye=new ZoomEye(getActivity(),handler,user);
                    zoomEye.searchHost(dork,sharedPreferences.getString("token",""));
                }else{
                    Toast.makeText(getActivity(),"输入为空",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
