package com.example.hoangtienmanh.imagelistview.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoangtienmanh.imagelistview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Hoang Tien Manh on 4/16/2018.
 */

public class VideoYoutubeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Video> list;

    public VideoYoutubeAdapter(Context context,int layout,ArrayList<Video> list){
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        Picasso.with(context)
                .load(list.get(position).getThumnail())
                .into(img);

        TextView txt = (TextView) convertView.findViewById(R.id.text);
        txt.setText(list.get(position).getTitle());


        return convertView;
    }
}
