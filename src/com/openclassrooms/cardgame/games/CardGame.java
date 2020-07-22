package com.openclassrooms.cardgame.games;

import com.openclassrooms.cardgame.controller.GameController;
import com.openclassrooms.cardgame.model.Deck;
import com.openclassrooms.cardgame.view.View;

public class CardGame {
    public static void main(String[] args) {
        GameController gc = new GameController(new View(), new Deck());
        gc.run();
    }
}
