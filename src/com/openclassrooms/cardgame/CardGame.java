package com.openclassrooms.cardgame;

import com.openclassrooms.cardgame.controller.GameController;
import com.openclassrooms.cardgame.model.Deck;
import com.openclassrooms.cardgame.view.CommandLineView;
import com.openclassrooms.cardgame.view.GameSwing;
import com.openclassrooms.cardgame.DeckFactory.DeckType;

public class CardGame {
    public static void main(String[] args) {
        GameSwing gs = new GameSwing();
        gs.createAndShowGUI();
        GameController gc = new GameController(
                gs,
                DeckFactory.makeDeck(DeckType.Normal),  // Factory design pattern, rather than new Deck()...
                null
        );
        gc.run();
    }
}
