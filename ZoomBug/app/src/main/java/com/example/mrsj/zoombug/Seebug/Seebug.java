package com.example.mrsj.zoombug.Seebug;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mrsj.zoombug.WorkSpace.Activity.WebViewActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by MR.SJ on 2016/4/27.
 */
//异步获取信息
public class Seebug extends AsyncTask<String, String, String>
{
        private Activity activity;
        private Fragment context;
        public Seebug(Fragment context, Activity activity, RecyclerView recyclerView){
            this.recyclerView=recyclerView;
            this.activity=activity;
            this.context=context;
        }
        ArrayList<String> urls=new ArrayList<>();
        ArrayList<String> text=new ArrayList<>();
        private RecyclerView recyclerView;

    /**
     * 对获取的数据进行解析
     * @param params
     * @return
     */
        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder=new StringBuilder();
            BufferedReader reader=null;
            try {
                URL url=new URL(params[0]);
                URLConnection urlConnection=url.openConnection();
                urlConnection.setConnectTimeout(5000);
               urlConnection.addRequestProperty("User-Agent"," Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");

               reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
                String line=null;
                Document document=null;
                while((line=reader.readLine())!=null){
                    builder.append(line+"\n");
                }
                document= Jsoup.parse(builder.toString());
                Elements elements=document.getElementsByClass("vul-title");
                for(Element element:elements){
                    urls.add("https://www.seebug.org"+element.attr("href"));
                    text.add( element.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(reader!=null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<RecycleItem> infoList=new ArrayList<>();
            for(int i=0;i<urls.size();i++){
                infoList.add(new RecycleItem(urls.get(i),text.get(i)));
            }

            final SgRecycleAdapter adapter=new SgRecycleAdapter(activity,infoList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new SgRecycleAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, String data) {
                    Intent intent =new Intent(activity, WebViewActivity.class);
                    intent.putExtra("url",data);
                    Log.e("CLICK","CLICK"+data);
                    activity.startActivity(intent);
                }
            });
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}

