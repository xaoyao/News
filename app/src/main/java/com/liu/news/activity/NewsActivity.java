package com.liu.news.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liu.news.R;

public class NewsActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent=getIntent();
        //获取新闻链接
        String path=intent.getStringExtra("path");

        webView= (WebView) findViewById(R.id.wv_news);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //加载网页
        webView.loadUrl(path);
    }
}
