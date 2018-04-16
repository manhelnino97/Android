package com.example.hoangtienmanh.imagelistview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hoangtienmanh.imagelistview.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    String url="";
    private YouTubePlayerView ytpv;
    private final String API_KEY_YT = "AIzaSyDDJuMZJ6SIJImXxCcZDk0xJ_HM6uCvt7o";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        ytpv = (YouTubePlayerView) findViewById(R.id.ytpv);

        Intent intent = getIntent();
        url = intent.getStringExtra("idvideo");

        ytpv.initialize(API_KEY_YT, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer ytp, boolean b) {
        // https://www.youtube.com/watch?v=jpLQYuh2U4U
        if (!b) {
            ytp.cueVideo(url);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, 1).show();
        } else {
            String error = String.format("Error initializing YouTube player", result.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
}

