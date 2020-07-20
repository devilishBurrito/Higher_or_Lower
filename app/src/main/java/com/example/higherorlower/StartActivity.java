package com.example.higherorlower;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vungle.warren.AdConfig;
import com.vungle.warren.Banners;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.VungleBanner;
import com.vungle.warren.error.VungleException;

public class StartActivity extends AppCompatActivity {

    static PlayAdCallback vunglePlayAdCallback;
    static LoadAdCallback vungleLoadAdCallback;

    String vungle_app_id;
    String vungle_banner_id;
    String vungle_interst_id;
    String error;
    static String TAG = "VUNGLE-SDK";
    RelativeLayout bannerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vungleInit();
        start();
    }

    public void vungleInit() {
        vungle_app_id = getString(R.string.vungle_app_id);
        vungle_banner_id = getString(R.string.vungle_banner_id);
        vungle_interst_id = getString(R.string.vungle_interst_id);


        Vungle.init(vungle_app_id, getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                // SDK has successfully initialized
                Log.i(TAG, "SDK Init Success");
            }

            @Override
            public void onError(VungleException exception) {
                error = exception.toString();
                Log.e(TAG, error);

            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {
                // Ad has become available to play for a cache optimized placement
            }
        });

        LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
            @Override
            public void onAdLoad(String id) {
               Log.i(TAG, "Successfully loaded Ad " + id);
            }

            @Override
            public void onError(String id, VungleException exception) {
                error = exception.toString();
                Log.e(TAG, error);
            }
        };
        vunglePlayAdCallback = new PlayAdCallback() {
            @Override
            public void onAdStart(String id) {
                // Ad experience started
            }

            @Override
            public void onAdEnd(String id, boolean completed, boolean isCTAClicked) {
            }

            @Override
            public void onAdEnd(String id) {
                // Ad experience ended
            }

            @Override
            public void onAdClick(String id) {
                // User clicked on ad
            }

            @Override
            public void onAdRewarded(String id) {
                // User earned reward for watching an ad
            }

            @Override
            public void onAdLeftApplication(String id) {
                // User has left app during an ad experience
            }

            @Override
            public void onError(String id, VungleException exception) {
                error = exception.toString();
                Log.e(TAG, error);
            }
        };

    }


    private void start() {

        if(!Vungle.isInitialized()){
                vungleInit();
        }

        Banners.loadBanner(vungle_banner_id, AdConfig.AdSize.BANNER, vungleLoadAdCallback);
        bannerContainer = findViewById(R.id.vungle_banner);


        Button start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
        });

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
//            startActivity(new Intent(StartActivity.this, Leaderboard.class));
        }
        });

        Button leaderboard = findViewById(R.id.leaderboardButton);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
            startActivity(new Intent(StartActivity.this, Leaderboard.class));
        }
        });

        Button sendEvent = findViewById(R.id.extraButton2);
        sendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
        }
        });

        Button newBanner = findViewById(R.id.extraButton1);
        newBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                // Check for banner ad availability and display
                if (Banners.canPlayAd(vungle_banner_id, AdConfig.AdSize.BANNER)) {
                    VungleBanner vungleBanner = Banners.getBanner(vungle_banner_id, AdConfig.AdSize.BANNER, vunglePlayAdCallback);
                    bannerContainer.addView(vungleBanner);
                } else if (Vungle.canPlayAd(vungle_interst_id)) {
                    Vungle.playAd(vungle_interst_id, null, StartActivity.vunglePlayAdCallback);
                } else {
                //unable to get Banner ads working at this time.
                    Toast.makeText(getApplicationContext(), getString(R.string.vungle_adload_error), Toast.LENGTH_SHORT).show();
            }
        }
        });
    }
}

