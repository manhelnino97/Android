package com.example.hoangtienmanh.imagelistview.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoangtienmanh.imagelistview.Activity.MainActivity;
import com.example.hoangtienmanh.imagelistview.Activity.ReadActivity;
import com.example.hoangtienmanh.imagelistview.MyDatabaseHelper;
import com.example.hoangtienmanh.imagelistview.R;
import com.example.hoangtienmanh.imagelistview.Tintuc;
import com.example.hoangtienmanh.imagelistview.TintucAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hoang Tien Manh on 4/10/2018.
 */

public class EighthFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View mRootView;
    public EighthFragment(){};
    ListView lv;
    ArrayList<Tintuc> t;
    MainActivity m;
    ArrayList<Tintuc> tintucList = new ArrayList<Tintuc>();
    private SwipeRefreshLayout swipe;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = (MainActivity) getActivity();
        Bundle bundle = m.onDataSelected();
        t = bundle.getParcelableArrayList("key");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_layout,container,false);
        // Toast.makeText(getActivity(), "cmm", Toast.LENGTH_SHORT).show();
        lv = (ListView) mRootView.findViewById(R.id.listView);
        TintucAdapter adapter = new TintucAdapter(
                m,
                R.layout.tintuc_layout,
                selectiveList(t)
        );
        lv.setAdapter(adapter);
        onClickItem();
        swipe = mRootView.findViewById(R.id.swipe);
        swipe.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.RED);
        swipe.setOnRefreshListener(this);
        return mRootView;
    }
    public ArrayList<Tintuc> selectiveList(ArrayList<Tintuc> t){
        ArrayList<Tintuc> list = new ArrayList<>();
        for(int i=0;i<t.size();i++){
            if(t.get(i).danhmuc.equals("Game")){
                list.add(t.get(i));
            }
        }
        return list;
    }
    private void onClickItem(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyDatabaseHelper db = new MyDatabaseHelper(m);
                String s = ((TintucAdapter)(parent.getAdapter())).getItem(position).tieude;
                Tintuc tin = db.gettintuc(s);

                Intent intent = new Intent(m,ReadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("key",tin);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(MainActivity.this,tin.noidung2 , Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRefresh() {



        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (isConnected() == true )
                {
                    new DownloadTask().execute("https://tintuc360cf.000webhostapp.com/admin/baiviet");

                }
                m.recreate();
                swipe.setRefreshing(false);
            }
        }.start();
    }

    //Kiểm tra kết nối internet
    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) m.getSystemService(Context.CONNECTIVITY_SERVICE);
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
            m.db.deleteTintuc();
            int i;
            for (i = 0; i < tintucs.size(); i++) {
                m.db.addTintuc(tintucs.get(i));
            }
        }
    }

}
