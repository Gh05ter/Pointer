package com.example.zoomeye.ZoomEye;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类用于主机搜索
 * Created by MR.SJ on 2016/4/25.
 */
public class ZEHostSearch {
    private String url;
    private String auth;
    private Context context;
    private RequestQueue requestQueue;
    /**
     *handler 用于向UI 线程返回数据
     */
    private Handler handler;
   public  ZEHostSearch(Context context, Handler handler,String url, String auth){
        this.auth=auth;
       this.url=url;
       this.context=context;
       this.handler=handler;
       requestQueue=ZoomEye.requestQueue;
    }
    /**
     * json 用于接收返回的数据
     */
    public JSONObject json;
    /**
     * statusCode 用于接收错误码
     */
    public int statusCode;

    /**
     *用于进行主机搜索
     *
     */
    public void postHostRequest(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        json=response;
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString("response",response.toString());
                        message.what=5;
                        message.setData(bundle);
                        handler.sendMessage(message);
                        Log.e("host","success");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message message=new Message();

                        message.what=6;
                        message.arg1=error.networkResponse.statusCode;
                        handler.sendMessage(message);
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("Authorization",auth);
                        return map;
                    }
                    @Override
                    protected VolleyError parseNetworkError(VolleyError volleyError) {
                        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                            volleyError = error;
                        }
                        return volleyError;
                    }

                };

                requestQueue.add(jsonObjectRequest);
            }
        }).start();

    }

}
