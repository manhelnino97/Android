package com.example.hoangtienmanh.imagelistview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hoangtienmanh.imagelistview.Activity.ActivityTwo;
import com.example.hoangtienmanh.imagelistview.Activity.BottomNavigationViewHelper;
import com.example.hoangtienmanh.imagelistview.Activity.VideoActivity;
import com.example.hoangtienmanh.imagelistview.Fragment.FragmentAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private ViewPager viewPager;
    MyDatabaseHelper db = new MyDatabaseHelper(this);
    ArrayList<Tintuc> tintucList = new ArrayList<Tintuc>();
    int x=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initView();
        bottomNav();
        if (isConnected() == true )
        {
            new DownloadTask().execute("https://tintuc360cf.000webhostapp.com/admin/baiviet/");
        }
        else{

        }
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tintuc360");
        builder.setMessage("Bạn đang đọc tin ở chế độ offline");
        builder.setNegativeButton("Ok",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


//Kiểm tra kết nối internet
    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

//Load tintưc
    class DownloadTask extends AsyncTask<String, Tintuc, ArrayList<Tintuc>> {
        @Override
        protected ArrayList<Tintuc> doInBackground(String... strings) {
            Document document = null;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                if (document != null) {
                    Elements subjectElements = document.select("tr.123");
                    if (subjectElements != null && subjectElements.size() > 0) {
                        for (Element element : subjectElements) {
                            Element titleSubject1 = element.getElementById("2");
                            Element titleSubject2 = element.getElementById("3");
                            Element category = element.getElementById("4");
                            Element content1 = element.getElementById("5");
                            Element content2 = element.getElementById("6");
                            Element link = element.getElementById("7");
                            Element imgSubject = element.getElementById("8");
                            if (titleSubject1 != null && titleSubject2 != null && imgSubject != null && category != null && link != null && content1!=null && content2!=null) {
                                String title1 = titleSubject1.text();
                                String title2 = titleSubject2.text();
                                String category_t = category.text();
                                String ct1 = content1.text();
                                String ct2 = content2.text();
                                String link_t = link.text();
                                String src = imgSubject.text();
                                Tintuc tintuc = new Tintuc(title1, title2,ct1,ct2, category_t, src, link_t);
                                tintucList.add(tintuc);
                            }
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return tintucList;
        }

        @Override
        protected void onPostExecute(ArrayList<Tintuc> tintucs) {
            super.onPostExecute(tintucs);
                db.deleteTintuc();
                int i;
                for (i = 0; i < tintucs.size(); i++) {
                    db.addTintuc(tintucs.get(i));
                }
        }
    }

    private void bottomNav() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_one:

                        break;

                    case R.id.action_two:
                        Intent intent1 = new Intent(MainActivity.this, VideoActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.action_three:
                        Intent intent2 = new Intent(MainActivity.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;
                }


                return false;

            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        TabLayout tabLayout =  (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public Bundle onDataSelected() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("key", (ArrayList<Tintuc>) db.getAllTintuc());
        return bundle;
    }
}

