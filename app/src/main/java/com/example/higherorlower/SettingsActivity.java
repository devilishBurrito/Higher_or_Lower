package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.os.Bundle;

import static com.example.higherorlower.AppliClass.bgMusicPlayer;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        super.onPause();
        bgMusicPlayer.pause();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        super.onResume();
        if (!bgMusicPlayer.isPlaying()){
            bgMusicPlayer.start();
        }
    }
}