package com.example.mrsj.zoombug.WorkSpace.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mrsj.zoombug.R;

/**
 * Created by MR.SJ on 2016/4/28.
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);

        initData();
        initView();
    }

    @Override
    public void initView() {
         webView=(WebView)findViewById(R.id.web_view);
        String url=this.getIntent().getStringExtra("url");
        final ProgressDialog bar=new ProgressDialog(WebViewActivity.this);
        WebSettings settings=webView.getSettings();
        settings.setUseWideViewPort(true);
       settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                bar.setMessage("正在加载。。。。");
                bar.setIndeterminate(false);
                bar.setCancelable(true);
                bar.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                bar.dismiss();
                super.onPageFinished(view, url);
            }
        });





    }

    @Override
    public void initData() {

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
