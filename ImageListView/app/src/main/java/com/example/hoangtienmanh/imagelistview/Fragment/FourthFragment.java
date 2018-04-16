package com.example.hoangtienmanh.imagelistview.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoangtienmanh.imagelistview.MainActivity;
import com.example.hoangtienmanh.imagelistview.MyDatabaseHelper;
import com.example.hoangtienmanh.imagelistview.R;
import com.example.hoangtienmanh.imagelistview.ReadActivity;
import com.example.hoangtienmanh.imagelistview.Tintuc;
import com.example.hoangtienmanh.imagelistview.TintucAdapter;

import java.util.ArrayList;


/**
 * Created by Hoang Tien Manh on 3/24/2018.
 */

public class FourthFragment extends Fragment{
    private View mRootView;
    ListView lv;
    ArrayList<Tintuc> t;
    MainActivity m;
    public FourthFragment(){};

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = (MainActivity) getActivity();
        Bundle bundle = m.onDataSelected();
        t = bundle.getParcelableArrayList("key");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_first,container,false);
        // Toast.makeText(getActivity(), "cmm", Toast.LENGTH_SHORT).show();
        lv = (ListView) mRootView.findViewById(R.id.listView);
        TintucAdapter adapter = new TintucAdapter(
                m,
                R.layout.tintuc_layout,
                selectiveList(t)
        );
        lv.setAdapter(adapter);
        onClickItem();
        return mRootView;
    }
    public ArrayList<Tintuc> selectiveList(ArrayList<Tintuc> t){
        ArrayList<Tintuc> list = new ArrayList<>();
        for(int i=0;i<t.size();i++){
            if(t.get(i).danhmuc.equals("Giải trí")){
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
}
