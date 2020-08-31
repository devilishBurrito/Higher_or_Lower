package com.example.higherorlower;

import android.content.Context;
import android.widget.Toast;

public class Card {

    public enum Suite{
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }

    public enum Rank{
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13)
        ;

        int value;
        Rank(int r){
            value = r;
        }
        public int getRankValue(){
            return this.value;
        }
    }

    private Suite mSuite;
    private Rank mRank;

    public Card(Suite suite, Rank rank){
        this.mSuite = suite;
        this.mRank = rank;
    }
    public Suite getSuite(){ return this.mSuite;}

    public Rank getRank(){ return this.mRank; }

    public int getValue(){
        return this.mRank.getRankValue();
    }

    public String suiteCardFinder( Context context) {

        if (this.getSuite().equals(Suite.SPADES)) {
            return "basecard_spades";
        } else if (this.getSuite().equals(Suite.HEARTS)) {
            return "basecard_hearts";
        } else if (this.getSuite().equals(Suite.DIAMONDS)) {
            return "basecard_diamonds";
        } else if (this.getSuite().equals(Suite.CLUBS)) {
            return "club_temp";
            // "
        } else {
            Toast.makeText(context, "no suite", Toast.LENGTH_SHORT).show();
            return "basecard_spades";
        }
    }

    public String suiteLogoFinder( Context context) {

        if (this.getSuite().equals(Suite.SPADES)) {
            return "emblem_spade";
        } else if (this.getSuite().equals(Suite.HEARTS)) {
            return "emblem_heart";
        } else if (this.getSuite().equals(Suite.DIAMONDS)) {
            return "emblem_diamond";
        } else if (this.getSuite().equals(Suite.CLUBS)) {
            return "emblem_club";
        } else {
            Toast.makeText(context, "no suite", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
