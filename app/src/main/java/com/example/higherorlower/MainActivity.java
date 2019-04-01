package com.example.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button playButton;
    Button highButton;
    Button lowButton;
    ImageView cardImage;
    ImageView suiteImage;
    TextView rankText;
    TextView streakText;

    int cardValue;
    int prevCardValue;
    int streak;
    int highScore;
    Deck mdeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }



    public void init(){
        playButton = findViewById(R.id.playButton);
        playButton.setVisibility(View.VISIBLE);
        highButton = findViewById(R.id.HIGHER);
        highButton.setVisibility(View.GONE);
        lowButton = findViewById(R.id.LOWER);
        lowButton.setVisibility(View.GONE);
        cardImage = findViewById(R.id.drawCard);
        cardImage.setImageResource(R.drawable.basecard_back);

        suiteImage = findViewById(R.id.suiteImageView);
        suiteImage.setVisibility(View.GONE);
        rankText = findViewById(R.id.rankTextView);
        rankText.setVisibility(View.GONE);

        streakText = findViewById(R.id.streakTextView);
        streakText.setVisibility(View.GONE);

        streak = 0;
        highScore = 0;

        mdeck = new Deck();
        mdeck.shuffle();
        mdeck.shuffle();
    }

    public void play(View v){
        Card c = mdeck.drawCard();
        prevCardValue = c.getValue();
        // this.players.get(1).drawCard(c);

        playButton.setVisibility(View.GONE);
        highButton.setVisibility(View.VISIBLE);
        lowButton.setVisibility(View.VISIBLE);

        Log.i("CARD", c.getSuite().toString());
        int cardResID = getResources().getIdentifier(c.suiteCardFinder(getApplicationContext()),
                "drawable", getPackageName());
        cardImage.setImageResource(cardResID);

        int suiteResID = getResources().getIdentifier(c.suiteLogoFinder(getApplicationContext()),
                "drawable", getPackageName());
        suiteImage.setImageResource(suiteResID);
        suiteImage.setVisibility(View.VISIBLE);

        rankText.setText(c.getRank().toString());
        rankText.setVisibility(View.VISIBLE);

        streakText.setText("STREAK: " + Integer.toString(streak));
        streakText.setVisibility(View.VISIBLE);
    }

    public void draw(View v) {

        if (mdeck.getSize() >= 1) {
            Card c = mdeck.drawCard();
            cardValue = c.getValue();

            int id = v.getId();
            String direction = v.getResources().getResourceEntryName(id);

            Log.i("CARD", c.getSuite().toString());
            int cardResID = getResources().getIdentifier(c.suiteCardFinder(getApplicationContext()),
                    "drawable", getPackageName());
            cardImage.setImageResource(cardResID);

            int suiteResID = getResources().getIdentifier(c.suiteLogoFinder(getApplicationContext()),
                    "drawable", getPackageName());
            suiteImage.setImageResource(suiteResID);
            suiteImage.setVisibility(View.VISIBLE);

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
            }
        }

        prevCardValue = cur;
        streakText.setText("STREAK: " + Integer.toString(streak));
    }


}
