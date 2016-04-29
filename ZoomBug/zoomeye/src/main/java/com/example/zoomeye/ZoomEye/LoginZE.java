package com.example.zoomeye.ZoomEye;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MR.SJ on 2016/4/25.
 */
public class LoginZE {
        RequestQueue requestQueue;
        Context context;
        Handler handler;
       private  LoginZE(Context context,Handler handler){
            this.context=context;
            this.handler=handler;
            requestQueue = ZoomEye.requestQueue;
        }
        public static LoginZE getInstance(Context context, Handler handler, String url,User user){
            LoginZE loginZE=new LoginZE(context,handler);
            loginZE.postJsonRequest(url,user);
            return loginZE;
        }

    public  void postJsonRequest(final String url,final User user){
                JSONObject jsonObject=new JSONObject();
                try{
                    jsonObject.put("username",user.getUsername());
                    jsonObject.put("password",user.getPassword());
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            String token=response.getString("access_token").toString();
                            String m_token="JWT "+token;
                            Message message=new Message();
                            message.what=1;
                            Bundle bundle=new Bundle();
                            bundle.putString("access_token",m_token);
                            message.setData(bundle);
                            handler.sendMessage(message);
                            Log.e("login","login success"+m_token);
                        }catch (JSONException e){
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                           handler.sendEmptyMessage(2);
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }

    }



