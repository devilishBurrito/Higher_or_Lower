package com.example.higherorlower;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import com.iterable.iterableapi.IterableApi;
import com.iterable.iterableapi.IterableConfig;
import com.iterable.iterableapi.IterableInAppHandler;
import com.iterable.iterableapi.IterableInAppMessage;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;


public class AppliClass extends Application {

    static MediaPlayer bgMusicPlayer;

    @Override
    public void onCreate(){
        super.onCreate();

        bgMusicPlayer = MediaPlayer.create(this,R.raw.dreams);
        bgMusicPlayer.start();

        class MyInAppHandler implements IterableInAppHandler {
            @Override
            public InAppResponse onNewInApp(IterableInAppMessage message) {
                    return InAppResponse.SHOW;
            }
        }

        IterableConfig config = new IterableConfig.Builder()
                .setInAppHandler(new MyInAppHandler())
                .build();
        IterableApi.initialize(getApplicationContext(), getString(R.string.iter_apikey), config);
        IterableApi.getInstance().setEmail(getString(R.string.iter_email));
        IterableApi.getInstance().registerForPush();
    }

}
