package com.example.zoomeye.ZoomEye;

import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *
 * ZoomEye Android SDK --version 1.0
 *
 * 使用需要在gradle添加引用  compile 'eu.the4thfloor.volley:com.android.volley:2015.05.28'
 *
 * Created by MR.SJ on 2016/4/25.
 * *****************************************************************************
 * example==
 *
 * first:long in                                                              *
 *           zoomEye=new ZoomEye(MainActivity.this,handler,user);               *
 *           zoomEye.login();                                                    *
 *                                                                               *
 * second:web search                                                             *
 *              zoomEye.searchWeb("dork",token);                                 *
 *                                                                               *
 * third: host search
 *              zoomEye.searchHost("app",token);
 *
 * ******************************************************************************
 */
public class ZoomEye {
    private User user;
    private Context context;
    private Handler handler;
    public static RequestQueue requestQueue;

    /**
     * 初始化ZoomEye
     *
     * @param context  当前Android组件的context
     * @param handler  用于处理多线程的信息传递
     * @param user     当前用户信息
     */
    public ZoomEye(Context context, Handler handler,User user){
        this.context=context;
        this.user=user;
        this.handler=handler;
        requestQueue= Volley.newRequestQueue(context);
    }

    /**
     * login()方法用于登录请求，获取得到token
     * 可以通过handler的handlerMessage方法  msg.getData().get("access_token");获取
     * 通过handler里message 的what值来判断请求是否成功，1为成功，2为错误
     */
    public void login(){
        String url="http://api.zoomeye.org/user/login";
        LoginZE loginZE=LoginZE.getInstance(context,handler,url,user);

    }

    /**
     * 用于web搜索
     * @return
     * 通过handler里message 的what值来判断请求是否成功，3为成功，4为错误
     */
    public void searchWeb(String dork,String token){
        String url="http://api.zoomeye.org/web/search?query="+dork;
        ZEWebSearch zeWebSearch=new ZEWebSearch(context,handler,url,token);
        zeWebSearch.postWebRequest();
    }

    public void searchWeb(String dork,String token,int page,int port){
        String url="http://api.zoomeye.org/web/search?query=port:"+port+" "+dork+"&page="+page+"&facet=";
        ZEWebSearch zeWebSearch=new ZEWebSearch(context,handler,url,token);
        zeWebSearch.postWebRequest();
        }



    /**
     * 用于主机搜索
     * @param dork  需要查询的dork
     * @param token  令牌
     * @return
     * 通过handler里message 的what值来判断请求是否成功，5为成功，6为错误
     */
    public void searchHost(String dork,String token){
        String url="http://api.zoomeye.org/host/search?query="+dork;
        ZEHostSearch zeHostSearch=new ZEHostSearch(context,handler,url,token);
        zeHostSearch.postHostRequest();

    }

    public void searchHost(String dork,String token,int page,int port){
            String url="http://api.zoomeye.org/host/search?query=\"port:"+port+" "+dork+"&page="+page+"&facet=";
            ZEHostSearch zeHostSearch=new ZEHostSearch(context,handler,url,token);
            zeHostSearch.postHostRequest();
    }
}
