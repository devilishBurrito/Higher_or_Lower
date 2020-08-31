package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

// CHARTBOOST IMPORTS
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.CBImpressionActivity;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Privacy.model.CCPA;
import com.chartboost.sdk.Privacy.model.GDPR;


public class MainActivity extends Activity {


    String cb_app_id, cb_appsig;
    static String TAG = "Chartboost-SDK";
    public final String inter_loc = CBLocation.LOCATION_DEFAULT;
    public final String reward_loc = CBLocation.LOCATION_HOME_SCREEN;

    RelativeLayout bannerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cb_init();

        start();
    }

    public void cb_init(){
        //Chartboost SDK
        Chartboost.addDataUseConsent(this, new GDPR(GDPR.GDPR_CONSENT.BEHAVIORAL));
        Chartboost.addDataUseConsent(this, new CCPA(CCPA.CCPA_CONSENT.OPT_IN_SALE));

        cb_app_id = getString(R.string.cb_appid);
        cb_appsig = getString(R.string.cb_appsig);
        Chartboost.setDelegate(myDelegate);
        Chartboost.startWithAppId(getApplicationContext(), cb_app_id, cb_appsig);
        Chartboost.setLoggingLevel(CBLogging.Level.ALL);

        Chartboost.setAutoCacheAds(true);
        Chartboost.setShouldRequestInterstitialsInFirstSession(true);
    }

    private void start() {

        Button start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
            startActivity(new Intent(MainActivity.this, HighLowActivity.class));
        }
        });

        Button login = findViewById(R.id.loginButton);


        Button leaderboard = findViewById(R.id.leaderboardButton);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
            startActivity(new Intent(MainActivity.this, Leaderboard.class));
        }
        });

        Button showInterst = findViewById(R.id.extraButton1);
        Button showRewarded = findViewById(R.id.extraButton2);

    }

    public void login_onclick(View v) {
        startActivity(new Intent(MainActivity.this,CBActivity.class));

    }

    public void extraButton1_onClick(View view) {
        if (Chartboost.hasInterstitial(inter_loc)) {
            Toast.makeText(this, "Interstitial Playing", Toast.LENGTH_SHORT).show();
            Chartboost.showInterstitial(inter_loc); }
        else {
            Toast.makeText(this, "No Interstitial, " +
                    "Caching Again", Toast.LENGTH_SHORT).show();
        }
    }

    public void extraButton2_onClick(View view) {
        Chartboost.showRewardedVideo(reward_loc);
    }

    public ChartboostDelegate myDelegate = new ChartboostDelegate() {
        // Declare delegate methods here, see CBSample project for examples

        @Override
        public void didInitialize(){
            Log.i(TAG,"CB SDK successfully initialized");
        }

        @Override
        public void didCacheInterstitial(String location){
            Toast.makeText(MainActivity.this, "WTF", Toast.LENGTH_SHORT).show();
            Log.i(TAG,"Interstitial successfully cached at " + location );
        }

        @Override
        public void didDisplayInterstitial(String location){
            Log.i(TAG,"Interstitial successfully displayed at " + location );
        }

        @Override
        public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error){
            Toast.makeText(MainActivity.this, "WTF", Toast.LENGTH_SHORT).show();
            Log.e(TAG,"Interstitial failed to load at " + location + " with error: " + error.name());
        }

        @Override
        public void didCacheRewardedVideo(String location){
            Log.i(TAG,"Rewarded Video successfully cached at " + location );
        }

        @Override
        public void didDisplayRewardedVideo(String location){
            Log.i(TAG,"Rewarded Video successfully displayed at " + location );
        }

        @Override
        public void didFailToLoadRewardedVideo(String location,
                                               CBError.CBImpressionError error) {
            Log.e(TAG,"Rewarded Video failed to load at " + location + " with error: " + error.name());
        }
    };


}

