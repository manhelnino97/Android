package com.example.hoangtienmanh.imagelistview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hoangtienmanh.imagelistview.BottomNavigationViewHelper;
import com.example.hoangtienmanh.imagelistview.R;

import java.util.ArrayList;

;

public class PersonalActivity extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> list2 = new ArrayList<String>();
    ListView lv,lv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cá nhân");

        lv = (ListView) findViewById(R.id.lv);
        list.add("Phiên bản ứng dụng: 1.0");
        list.add("Theo dõi Fanpage Tin tức 360");
        list.add("Góp ý cho nhà phát hành");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);


        lv2 = (ListView) findViewById(R.id.lv2);
        list2.add("Địa chỉ: 144 Xuân Thuỷ - Cầu Giấy - Hà Nội");
        list2.add("Email: Manhelnino97@gmail.com");
        list2.add("Số điện thoại: 01638668369");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list2);
        lv2.setAdapter(adapter2);




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_one:
                        Intent intent0 = new Intent(PersonalActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.action_two:
                        Intent intent1 = new Intent(PersonalActivity.this,VideoActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.action_three:

                        break;

                }


                return false;
            }
        });
    }
}
