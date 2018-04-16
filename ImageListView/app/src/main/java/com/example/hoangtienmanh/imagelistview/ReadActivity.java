package com.example.hoangtienmanh.imagelistview;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReadActivity extends ActionBarActivity {
    ListView lv1,lv2,lv3;
    TextView tieude,noidungtieude,danhmuc,noidung1,noidung2;
    ImageView hinhanh;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    //@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
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
        Tintuc tintuc = bundle.getParcelable("key");
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
        ls1.add(db.getAllTintuc().get(0));
        ls2.add(db.getAllTintuc().get(1));
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

    }
    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

}
