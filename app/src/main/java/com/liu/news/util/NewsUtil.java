package com.liu.news.util;

import android.util.Xml;

import com.liu.news.bean.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2015/12/22 0022.
 */
public class NewsUtil {

    /**
     * 从输入流中获取新闻列表
     * @param is
     * @return
     */
    public static List<News> getNewsFromStream(InputStream is){
        List<News> newsList=null;
        News news=new News();
        //解析xml文件
        XmlPullParser xp= Xml.newPullParser();
        try {
            //设置文件格式为GBK
            xp.setInput(is,"GBK");
            int type=xp.getEventType();
            while(type!=XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if("channel".equals(xp.getName())){
                            newsList=new ArrayList<>();
                        }else if("item".equals(xp.getName())){
                            news=new News();
                        }else if("title".equals(xp.getName())){
                            news.setTitle(xp.nextText());
                        }else if("link".equals(xp.getName())){
                            news.setLink(xp.nextText());
                        }else if("author".equals(xp.getName())){
                            news.setAuthor(xp.nextText());
                        }else if("pubDate".equals(xp.getName())){
                            news.setPubDate(xp.nextText());
                        }else if("description".equals(xp.getName())){
                            news.setDescription(xp.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("item".equals(xp.getName())){
                            newsList.add(news);
                        }
                        break;
                }
                type=xp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
