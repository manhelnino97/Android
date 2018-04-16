package com.example.hoangtienmanh.imagelistview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hoangtienmanh.imagelistview.MainActivity;
import com.example.hoangtienmanh.imagelistview.R;

;

public class ActivityTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);;

        TextView title = (TextView) findViewById(R.id.activityTitle1);
        title.setText("This is ActivityTwo");

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
                        Intent intent0 = new Intent(ActivityTwo.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.action_two:
                        Intent intent1 = new Intent(ActivityTwo.this,VideoActivity.class);
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
