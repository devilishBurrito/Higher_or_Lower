package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;

import static com.example.higherorlower.AppliClass.bgMusicPlayer;


public class HighLowActivity extends Activity {

    MediaPlayer mplayer;

    Button playButton, highButton, lowButton;
    ImageView cardImage;
    TextView rankText, streakText, highScoreText;

    int cardValue, prevCardValue, streak, highScore;
    Deck mdeck;

    static String TAG = "Chartboost-SDK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

    }


    public void initialize(){

        playButton = findViewById(R.id.playButton);
        playButton.setVisibility(View.VISIBLE);
        highButton = findViewById(R.id.HIGHER);
        highButton.setVisibility(View.GONE);
        lowButton = findViewById(R.id.LOWER);
        lowButton.setVisibility(View.GONE);
        cardImage = findViewById(R.id.drawCard);
        cardImage.setImageResource(R.drawable.basecard_back);

        rankText = findViewById(R.id.rankTextView);
        rankText.setVisibility(View.GONE);

        streakText = findViewById(R.id.streakTextView);
        streakText.setVisibility(View.GONE);

        highScoreText = findViewById(R.id.highScore);
        highScoreText.setVisibility(View.GONE);

        streak = 0;
        highScore = 0;

        mdeck = new Deck();
        mdeck.shuffle();

    }

    // onClick method for playButton
    public void play(View v){
        Card c = mdeck.drawCard();
        prevCardValue = c.getValue();

        playButton.setVisibility(View.GONE);
        highButton.setVisibility(View.VISIBLE);
        lowButton.setVisibility(View.VISIBLE);

        int cardResID = getResources().getIdentifier(c.suiteCardFinder(getApplicationContext()),
                "drawable", getPackageName());
        cardImage.setImageResource(cardResID);

        rankText.setText(c.getRank().toString());
        rankText.setVisibility(View.VISIBLE);

        streakText.setText(getString(R.string.streak_counter, streak));
        streakText.setVisibility(View.VISIBLE);

        highScoreText.setText(getString(R.string.high_score_text, highScore));
        highScoreText.setVisibility(View.VISIBLE);
    }

    public void draw(View v) {

        if (mdeck.getSize() >= 1) {
            Card c = mdeck.drawCard();
            cardValue = c.getValue();

            int id = v.getId();
            String direction = v.getResources().getResourceEntryName(id);


            int cardResID = getResources().getIdentifier(c.suiteCardFinder(getApplicationContext()),
                    "drawable", getPackageName());
            cardImage.setImageResource(cardResID);

            rankText.setText(c.getRank().toString());
            rankText.setVisibility(View.VISIBLE);

            checkHighLow(prevCardValue, cardValue, direction);

        } else {
            Toast.makeText(this, "No remaining cards", Toast.LENGTH_LONG).show();
        }
    }

    public void checkHighLow(int prev, int cur, String dir){

        if (dir.equals("HIGHER")){
            if(cur > prev){
                win();
            } else if (cur <= prev){
                loss();
            }
        } else if (dir.equals("LOWER")){
            if(cur < prev){
                win();
            } else if (cur >= prev){
                loss();
                if (Chartboost.hasInterstitial(CBLocation.LOCATION_DEFAULT)) {
                    Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
                }
            }
        }
        prevCardValue = cur;
        streakText.setText(getString(R.string.streak_counter, streak));
        highScoreCheck(streak);

    }

    private void win(){
        mplayer = MediaPlayer.create(this, R.raw.ting);
        mplayer.start();
        streak++;
        Toast.makeText(this, "NICE!", Toast.LENGTH_SHORT).show();
    }

    private void loss(){
        mplayer = MediaPlayer.create(this, R.raw.buzzer);
        mplayer.start();
        streak = 0;
        Toast.makeText(this, "Oof... nice try", Toast.LENGTH_SHORT).show();
    }

    private void highScoreCheck(int streak) {
        if (streak>highScore){
            highScore = streak;
            highScoreText.setText(getString(R.string.high_score_text, highScore));
        }
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
