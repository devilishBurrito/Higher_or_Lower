package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

// CHARTBOOST IMPORTS
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Privacy.model.CCPA;
import com.chartboost.sdk.Privacy.model.GDPR;

import static com.example.higherorlower.AppliClass.bgMusicPlayer;


// TODO: 9/16/20 Create "Pause" function for when user backgrounds / closes app 
// TODO: 9/16/20 create "resume" function allowing user to resume game where left off
// TODO: 9/16/20 start blackjack game 
// TODO: 9/16/20 figure out how to migrate git data to a new app (want to change the android package name)
// TODO: 9/17/20 add user account creation feature (just username, leaderboard initials and password)
// TODO: 9/17/20 add user login feature (similar to netflix select account) that asks for password
// TODO: 9/17/20 add user profile page
// TODO: 9/21/20 create settings menu (change current song, music volume/mute, sound effects volume / mute)
// TODO: 9/21/20 scoreboard actiivty delayed. first need to implement sharedPreferences for scores, profiles and settings


public class MainActivity extends Activity {


    String cb_app_id, cb_appsig;
    static String TAG = "Integration-SDK";
    public final String inter_loc = CBLocation.LOCATION_DEFAULT;
    public final String reward_loc = CBLocation.LOCATION_HOME_SCREEN;

    Button start,login,leaderboard,extraButton1, extraButton2;
    ImageView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sdktest_init();

        start();
    }

    public void sdktest_init(){
        //Chartboost SDK
        Chartboost.addDataUseConsent(this, new GDPR(GDPR.GDPR_CONSENT.BEHAVIORAL));
        Chartboost.addDataUseConsent(this, new CCPA(CCPA.CCPA_CONSENT.OPT_IN_SALE));

        cb_app_id = getString(R.string.cb_appid);
        cb_appsig = getString(R.string.cb_appsig);
        Chartboost.setDelegate(myDelegate);
        Chartboost.startWithAppId(getApplicationContext(), cb_app_id, cb_appsig);
//        Chartboost.setLoggingLevel(CBLogging.Level.ALL);

        Chartboost.setAutoCacheAds(true);
        Chartboost.setShouldRequestInterstitialsInFirstSession(true);
    }

    private void start() {

        settingsButton = findViewById(R.id.settingsImageView);
        settingsButton.setImageResource(R.drawable.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
            startActivity(new Intent(MainActivity.this, HighLowActivity.class));
        }
        });

        login = findViewById(R.id.loginButton);


        leaderboard = findViewById(R.id.leaderboardButton);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getApplicationContext(),"Sorry, nothing here", Toast.LENGTH_SHORT).show();
        }
        });

        extraButton1 = findViewById(R.id.extraButton1);
        extraButton1.setText("IT SDK");
        extraButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, IterActivity.class));
            }
        });

        extraButton2 = findViewById(R.id.extraButton2);
        extraButton2.setText("CB SDK");
        extraButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CBActivity.class));
            }
        });

    }

    public void login_onclick(View v) {
        Toast.makeText(getApplicationContext(),"Sorry, nothing here", Toast.LENGTH_SHORT).show();
    }

    public ChartboostDelegate myDelegate = new ChartboostDelegate() {
        // Declare delegate methods here, see CBSample project for examples

        @Override
        public void didInitialize(){
            Log.i(TAG,"CB SDK successfully initialized");
        }
    };

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

