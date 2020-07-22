package com.openclassrooms.cardgame.model;

public class PlayingCard {
    Rank rank;
    Suit suit;
    boolean faceUp;
    public PlayingCard(Rank r, Suit s) {
        this.rank = r;
        this.suit = s;
        faceUp = false;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public boolean flip() {
        faceUp = !faceUp;
        return faceUp;
    }
}
