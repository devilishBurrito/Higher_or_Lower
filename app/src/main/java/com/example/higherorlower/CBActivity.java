package com.example.higherorlower;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;

public class CBActivity extends Activity {

    static String TAG = "Chartboost-SDK";
    public final String inter_loc = CBLocation.LOCATION_DEFAULT;
    public final String reward_loc = CBLocation.LOCATION_HOME_SCREEN;

    Button cache_inter,cache_reward,show_inter,show_reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cb);
        Chartboost.setDelegate(myDelegate);
        Chartboost.setLoggingLevel(CBLogging.Level.ALL);

        cache_inter = findViewById(R.id.cacheInterButton);
        cache_inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chartboost.cacheInterstitial(inter_loc);
            }
        });
        cache_reward = findViewById(R.id.cacheRewaredButton);
        cache_reward.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Chartboost.cacheRewardedVideo(reward_loc);
            }
        });

        show_inter = findViewById(R.id.showInterButton);
        show_inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Chartboost.hasInterstitial(inter_loc)) {
                    Chartboost.showInterstitial(inter_loc);
                } else {
                    Toast.makeText(CBActivity.this, "No Cached Interstial", Toast.LENGTH_SHORT).show();
                    Chartboost.cacheInterstitial(inter_loc);
                }
            }
        });
        show_reward = findViewById(R.id.showRewardButton);
        show_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Chartboost.hasRewardedVideo(reward_loc)) {
                    Chartboost.showRewardedVideo(reward_loc);
                } else {
                    Toast.makeText(CBActivity.this, "No Cached RewardedVideo", Toast.LENGTH_SHORT).show();
                    Chartboost.cacheRewardedVideo(reward_loc);
                }
            }
        });
    }

    public ChartboostDelegate myDelegate = new ChartboostDelegate() {
        // Declare delegate methods here, see CBSample project for examples

        @Override
        public void didInitialize(){
            Log.i(TAG,"CB SDK successfully initialized");
        }

        @Override
        public void didCacheInterstitial(String location){
            Toast.makeText(CBActivity.this, "WTF", Toast.LENGTH_SHORT).show();
            Log.i(TAG,"Interstitial successfully cached at " + location );
        }

        @Override
        public void didDisplayInterstitial(String location){
            Log.i(TAG,"Interstitial successfully displayed at " + location );
        }

        @Override
        public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error){
            Toast.makeText(CBActivity.this, "WTF", Toast.LENGTH_SHORT).show();
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
