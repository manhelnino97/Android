package com.example.hoangtienmanh.imagelistview.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hoangtienmanh.imagelistview.MainActivity;
import com.example.hoangtienmanh.imagelistview.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class VideoActivity extends AppCompatActivity {

    String API_URI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=";
    String KEY_BROWSE = "AIzaSyDDJuMZJ6SIJImXxCcZDk0xJ_HM6uCvt7o";
    String PLAYLIST_ID = "PLC07qFOFy9stBQw7HcsBu-uT6j49YcZOr";
    ListView lv;
    ArrayList<Video> listVideo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        lv = (ListView) findViewById(R.id.list_Video);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Video Hot");

        if (isConnected() == true )
        {
            new ParseVideoYoutube().execute();
        }
        else{
            showAlertDialog();
        }
        OnClick();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_one:
                        Intent intent0 = new Intent(VideoActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.action_two:

                        break;

                    case R.id.action_three:
                        Intent intent2 = new Intent(VideoActivity.this, ActivityTwo.class);
                        startActivity(intent2);
                        break;
                }


                return false;
            }
        });
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tintuc360");
        builder.setMessage("Vui lòng kiểm tra kết nôi Internet để xem video");
        builder.setNegativeButton("Ok",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void OnClick() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VideoActivity.this, PlayVideoActivity.class);
                intent.putExtra("idvideo", listVideo.get(position).getUrlVideo());
                startActivity(intent);
            }
        });
    }

    //AsyncTask parse Json
    private class ParseVideoYoutube extends AsyncTask<Void, Void, ArrayList<Video>> {
        @Override
        protected ArrayList<Video> doInBackground(Void... params) {
            URL jSonUrl;
            URLConnection jSonConnect;
            try {
                jSonUrl = new URL(API_URI + PLAYLIST_ID + "&key=" + KEY_BROWSE+"&maxResults=50");
                jSonConnect = jSonUrl.openConnection();
                InputStream inputstream = jSonConnect.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                inputstream.close();
                String jSontxt = stringBuilder.toString();

                JSONObject jsonobject = new JSONObject(jSontxt);
                JSONArray allItem = jsonobject.getJSONArray("items");
                for (int i = 0; i < allItem.length(); i++) {
                    JSONObject item = allItem.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("snippet");

                    String title = snippet.getString("title");              // Get Title Video

                    String decription = snippet.getString("description");   // Get Description

                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");    //Get Url Thumnail
                    JSONObject thumnailsIMG = thumbnails.getJSONObject("medium");
                    String thumnailurl = thumnailsIMG.getString("url");

                    JSONObject resourceId = snippet.getJSONObject("resourceId");    //Get ID Video
                    String videoId = resourceId.getString("videoId");

                    Video video = new Video();
                    video.setTitle(title);
                    video.setThumnail(thumnailurl);
                    video.setDecription(decription);
                    video.setUrlVideo(videoId);
                    //Add video to List
                    listVideo.add(video);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listVideo;
        }

        @Override
        protected void onPostExecute(ArrayList<Video> videos) {
            super.onPostExecute(videos);

            VideoYoutubeAdapter adapter = new VideoYoutubeAdapter(
                    VideoActivity.this,
                    R.layout.video,
                    videos);
            lv.setAdapter(adapter);

        }

    }
}