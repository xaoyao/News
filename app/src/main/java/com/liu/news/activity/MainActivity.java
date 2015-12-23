package com.liu.news.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.liu.news.R;
import com.liu.news.adapter.NewsAdapter;
import com.liu.news.bean.News;
import com.liu.news.util.HttpCallbackListener;
import com.liu.news.util.HttpUtil;
import com.liu.news.util.NewsUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //请求成功
    private static final int SUCCESS=1;
    //请求失败
    private static final int FAIL=2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    final List<News> newsList= (List<News>) msg.obj;
                    NewsAdapter adapter=new NewsAdapter(MainActivity.this,R.layout.news_item,newsList);
                    ListView lv_news= (ListView) findViewById(R.id.lv_news);
                    lv_news.setAdapter(adapter);
                    //设置点击事件
                    lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news=newsList.get(position);
                            Intent intent=new Intent(MainActivity.this,NewsActivity.class);
                            //传递新闻链接
                            intent.putExtra("path",news.getLink());
                            startActivity(intent);
                        }
                    });
                    break;
                case FAIL:
                    Toast.makeText(MainActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showNews();
    }

    private void showNews(){

        //使用工具类来获取newsList
        HttpUtil.getNewsFromServer("http://news.qq.com/newsgn/rss_newsgn.xml", new HttpCallbackListener() {
            @Override
            public void onFinish(List<News> newsList) {
                Message msg=handler.obtainMessage();
                msg.obj=newsList;
                msg.what=SUCCESS;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                handler.sendEmptyMessage(FAIL);
                e.printStackTrace();
            }
        });

//        Thread t=new Thread(){
//            @Override
//            public void run() {
//                List<News> newsList;
//                //rss源地址
//                String path="http://news.qq.com/newsgn/rss_newsgn.xml";
//                try {
//                    URL url=new URL(path);
//                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//                    //设置请求方式
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(8000);
//                    conn.setReadTimeout(8000);
//                    //是否请求成功
//                    if(conn.getResponseCode()==200){
//                        InputStream is=conn.getInputStream();
//                        //通过工具类获取newsList
//                        newsList= NewsUtil.getNewsFromStream(is);
//
//                        Message msg=handler.obtainMessage();
//                        msg.obj=newsList;
//                        msg.what=SUCCESS;
//                        handler.sendMessage(msg);
//                    }else {
//                        handler.sendEmptyMessage(FAIL);
//                    }
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(FAIL);
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        t.start();
    }
}
