package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;


public class HighLowActivity extends Activity {

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
                streak++;
                Toast.makeText(this, "NICE!", Toast.LENGTH_SHORT).show();
            } else if (cur <= prev){
                streak = 0;
                Toast.makeText(this, "Oof... nice try", Toast.LENGTH_SHORT).show();
            }
        } else if (dir.equals("LOWER")){
            if(cur < prev){
                streak++;
                Toast.makeText(this, "NICE!", Toast.LENGTH_SHORT).show();
            } else if (cur >= prev){
                streak = 0;
                Toast.makeText(this, "Oof... nice try", Toast.LENGTH_SHORT).show();
                if (Chartboost.hasInterstitial(CBLocation.LOCATION_DEFAULT)) {
                    Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
                }
            }
        }

        prevCardValue = cur;
        streakText.setText(getString(R.string.streak_counter, streak));
        highScoreCheck(streak);

    }


    private void highScoreCheck(int streak) {
        if (streak>highScore){
            highScore = streak;
            highScoreText.setText(getString(R.string.high_score_text, highScore));
        }
    }


}
