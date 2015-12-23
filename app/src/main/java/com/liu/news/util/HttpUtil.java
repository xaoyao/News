package com.liu.news.util;

import com.liu.news.bean.News;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by liu on 2015/12/23 0023.
 */
public class HttpUtil {

    public static void getNewsFromServer(final String path, final HttpCallbackListener listener){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url=new URL(path);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    if(conn.getResponseCode()==200){
                        InputStream is=conn.getInputStream();
                        List<News> newsList=NewsUtil.getNewsFromStream(is);
                        if(listener!=null){
                            //将newsList传入回调函数，供程序处理
                            listener.onFinish(newsList);
                        }
                    }else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                }
            }
        }.start();

    }
}
