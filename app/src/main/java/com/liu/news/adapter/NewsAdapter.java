package com.liu.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.liu.news.R;
import com.liu.news.bean.News;

import java.util.List;

/**
 * Created by liu on 2015/12/22 0022.
 */
public class NewsAdapter extends ArrayAdapter<News>{
    private int resourceId;
    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=getItem(position);
        View view;
        ViewHolder viewHolder;
        //是否纯在缓存
        if(convertView==null){
            viewHolder=new ViewHolder();
            view=View.inflate(getContext(),resourceId,null);
            viewHolder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_description= (TextView) view.findViewById(R.id.tv_description);
            viewHolder.tv_pubDate= (TextView) view.findViewById(R.id.tv_pubDate);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        //设置文本内容
        viewHolder.tv_title.setText(news.getTitle());
        viewHolder.tv_description.setText(news.getDescription());
        viewHolder.tv_pubDate.setText(news.getPubDate());
        return view;
    }

    class ViewHolder{
        TextView tv_title;
        TextView tv_description;
        TextView tv_pubDate;
    }
}
