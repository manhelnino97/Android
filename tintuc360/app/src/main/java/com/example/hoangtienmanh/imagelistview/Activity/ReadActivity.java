package com.example.hoangtienmanh.imagelistview.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoangtienmanh.imagelistview.MyDatabaseHelper;
import com.example.hoangtienmanh.imagelistview.R;
import com.example.hoangtienmanh.imagelistview.Tintuc;
import com.example.hoangtienmanh.imagelistview.TintucAdapter;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class ReadActivity extends AppCompatActivity{
    ListView lv1,lv2,lv3;
    TextView tieude,noidungtieude,danhmuc,noidung1,noidung2;
    ImageView hinhanh;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    ReadActivity r;
    ShareButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        lv1 = (ListView) findViewById(R.id.listView1);
        lv2 = (ListView) findViewById(R.id.listView2);
        tieude = (TextView) findViewById(R.id.tieude);
        noidungtieude = (TextView) findViewById(R.id.noidungtieude);
        danhmuc = (TextView) findViewById(R.id.danhmuc);
        noidung1 = (TextView) findViewById(R.id.noidung1);
        noidung2 = (TextView) findViewById(R.id.noidung2);
        hinhanh = (ImageView) findViewById(R.id.hinhanh);

        Bundle bundle = getIntent().getExtras();
        final Tintuc tintuc = bundle.getParcelable("key");
        //Toast.makeText(this, tintuc.noidung2, Toast.LENGTH_SHORT).show();

        actionBar.setTitle(tintuc.danhmuc);

        tieude.setText(tintuc.tieude);
        danhmuc.setText(tintuc.danhmuc);
        noidungtieude.setText(tintuc.noidungtieude);
        noidung1.setText(tintuc.noidung1);
        Picasso.with(this)
                .load(tintuc.linkAnh)
                .into(hinhanh);
        noidung2.setText(tintuc.noidung2);


        ArrayList<Tintuc> ls1 = new ArrayList<Tintuc>();
        ArrayList<Tintuc> ls2 = new ArrayList<Tintuc>();
        Tintuc t1 = selectTintuc(db.getAllTintuc(),tintuc.danhmuc,"",tintuc.tieude);
        Tintuc t2 = selectTintuc(db.getAllTintuc(),tintuc.danhmuc,t1.tieude,tintuc.tieude);
        ls1.add(t1);
        ls2.add(t2);
        TintucAdapter adapter1 = new TintucAdapter(
                this,
                R.layout.tintuc_layout,
                ls1
        );
        lv1.setAdapter(adapter1);
        TintucAdapter adapter2 = new TintucAdapter(
                this,
                R.layout.tintuc_layout,
                ls2
        );
        lv2.setAdapter(adapter2);

        btn = (ShareButton) findViewById(R.id.btnshare);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Chia sẻ tin tức")
                .setImageUrl(Uri.parse(""))
                .setContentUrl(Uri.parse(tintuc.linkBaiviet))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#tintuc360")
                        .build())
                .build();
        btn.setShareContent(content);
        onClickItem(lv1);
        onClickItem(lv2);
    }

    //Chia sẻ link bài viết lên fb
    public void shareLinkFB(String title, String linkShare, String imgThumnal) {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setImageUrl(Uri.parse(imgThumnal))
                .setContentUrl(Uri.parse(linkShare))
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#tintuc360")
                        .build())
                .build();
        ShareDialog.show(ReadActivity.this, content);
    }

    //Bắt sự kiện click vào tin tức
    private void onClickItem(ListView lv){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = ((TintucAdapter)(parent.getAdapter())).getItem(position).tieude;
                Tintuc tin = db.gettintuc(s);
                Intent intent = new Intent(ReadActivity.this,ReadActivityTmp.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("key",tin);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(MainActivity.this,tin.noidung2 , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Tintuc selectTintuc(ArrayList<Tintuc> arrayList,String s,String s1,String s2){
        while(true) {
            Random rd=new Random();
            int x = arrayList.size()-1;
            int i = rd.nextInt((x-0+1)+0);
            if(s.equals(arrayList.get(i).danhmuc) && s1.equals(arrayList.get(i).tieude)==false && s2.equals(arrayList.get(i).tieude)==false){
                return arrayList.get(i);
            }
        }

    }
}
