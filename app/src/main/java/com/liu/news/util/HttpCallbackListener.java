package com.liu.news.util;

import com.liu.news.bean.News;

import java.util.List;

/**
 * Created by liu on 2015/12/23 0023.
 */
public interface HttpCallbackListener {
    /**
     * 服务器响应成功时调用
     * @param newsList
     */
    void onFinish(List<News> newsList);

    /**
     * 服务器响应异常时调用
     * @param e
     */
    void onError(Exception e);
}
