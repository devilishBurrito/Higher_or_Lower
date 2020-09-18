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
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Privacy.model.CCPA;
import com.chartboost.sdk.Privacy.model.GDPR;


// TODO: 9/16/20 Create "Pause" function for when user backgrounds / closes app 
// TODO: 9/16/20 create "resume" function allowing user to resume game where left off
// TODO: 9/16/20 add background music (royalty free) that pauses when app is backgrounded and resumes when foregrounded
// TODO: 9/16/20 start blackjack game 
// TODO: 9/16/20 figure out how to migrate git data to a new app (i want to change the android package name)
// TODO: 9/16/20 continue scoreboard activities
// TODO: 9/17/20 add user account creation feature (just username, leaderboard initials and password)
// TODO: 9/17/20 add user login feature (similar to netflix select account) that asks for password
// TODO: 9/17/20 add user profile page


public class MainActivity extends Activity {


    String cb_app_id, cb_appsig;
    static String TAG = "Integration-SDK";
    public final String inter_loc = CBLocation.LOCATION_DEFAULT;
    public final String reward_loc = CBLocation.LOCATION_HOME_SCREEN;

    RelativeLayout bannerContainer;

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

        Button extraButton1 = findViewById(R.id.extraButton1);
        extraButton1.setText("Iterable");
        extraButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, IterActivity.class));
            }
        });
        Button extraButton2 = findViewById(R.id.extraButton2);

    }

    public void login_onclick(View v) {
        startActivity(new Intent(MainActivity.this,CBActivity.class));

    }

    public ChartboostDelegate myDelegate = new ChartboostDelegate() {
        // Declare delegate methods here, see CBSample project for examples

        @Override
        public void didInitialize(){
            Log.i(TAG,"CB SDK successfully initialized");
        }
    };


}

