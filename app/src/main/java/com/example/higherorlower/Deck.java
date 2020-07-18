package com.example.higherorlower;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    ArrayList<Card> mDeck;

    public Deck() {

        mDeck = new ArrayList<>(Card.Suite.values().length * Card.Rank.values().length);

        for (Card.Suite s : Card.Suite.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                Card mCard = new Card(s, r);
                mDeck.add(mCard);
            }
        }
    }

    public Card drawCard() {
        //removes the top card
        Card c = mDeck.get(0);
        mDeck.remove(0);

        return c;
    }

    public void shuffle() {
        // triple shuffle
        for (int i = 0; i <= 3; i++) {
            Collections.shuffle(this.mDeck);
        }
    }

    public Card.Suite getSuite() {
        return this.getSuite();
    }

    public Card.Rank getRank() {
        return this.getRank();
    }

    public int getValue() {
        return this.getValue();
    }


    public int getSize() {
        return mDeck.size();
    }
}