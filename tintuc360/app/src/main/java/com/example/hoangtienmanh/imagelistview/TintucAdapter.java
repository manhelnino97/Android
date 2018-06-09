package com.example.hoangtienmanh.imagelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hoang Tien Manh on 3/28/2018.
 */

public class TintucAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Tintuc> listTintuc;

    public TintucAdapter(Context context,int layout,List<Tintuc> listTintuc){
        this.context = context;
        this.layout = layout;
        this.listTintuc = listTintuc;
    }

    @Override
    public int getCount() {
        return listTintuc.size();
    }

    @Override
    public Tintuc getItem(int position) {
        return listTintuc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        // Ánh xạ giá trị
        TextView txtName = (TextView) convertView.findViewById(R.id.tieude);//tieu de
        String tieude;
        if(listTintuc.get(position).tieude.length()<60){
            tieude = listTintuc.get(position).tieude;
        }
        else {
            tieude = listTintuc.get(position).tieude.substring(0,60)+"...";
        }
        txtName.setText(tieude);

        TextView txtDm = (TextView) convertView.findViewById(R.id.danhmuc);//danhmuc
        txtDm.setText(String.valueOf(">>"+listTintuc.get(position).danhmuc));

        ImageView img = (ImageView) convertView.findViewById(R.id.imageView);//hinh anh
        Picasso.with(context)
                .load(listTintuc.get(position).linkAnh)
                .into(img);


        return convertView;
    }
}
