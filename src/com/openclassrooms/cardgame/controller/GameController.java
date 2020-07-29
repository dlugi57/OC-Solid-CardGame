package com.openclassrooms.cardgame.controller;

import com.openclassrooms.cardgame.games.GameEvaluator;
import com.openclassrooms.cardgame.games.HighCardGameEvaluator;
import com.openclassrooms.cardgame.model.*;
import com.openclassrooms.cardgame.view.GameViewable;
import com.openclassrooms.cardgame.view.GameViewables;

import java.util.ArrayList;


public class GameController {

	enum GameState {
		AddingPlayers,
		CardsDealt,
		WinnerRevealed,
		AddingView
	}

    private Deck deck;
    private ArrayList<IPlayer> players;
    private IPlayer winner;
    private GameViewables views;
    private GameState gameState;
    private GameEvaluator evaluator;


    public GameController(GameViewable view, Deck deck, GameEvaluator evaluator) {
        views = new GameViewables();
        this.deck = deck;
        players = new ArrayList<IPlayer> ();
        gameState = GameState.AddingPlayers;
        //view.setController(this);
        addViewable(view);
        this.evaluator = evaluator;
        this.evaluator = new HighCardGameEvaluator();

    }

    public void addViewable(GameViewable newView) {
        GameState curState = gameState;
        gameState = GameState.AddingView;
        newView.setController(this);
        views.addViewable(newView);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameState = curState;
    }

    public void run() {
        while (true) {
            switch (gameState) {
                case AddingPlayers:
                    views.promptForPlayerName();
                    break;

                case CardsDealt:
                    views.promptForFlip();
                    break;

                case WinnerRevealed:
                    views.promptForNewGame();
                    break;

                case AddingView:
                    break;
            }
        }
    }

    public void addPlayer(String playerName) {
        if (gameState == GameState.AddingPlayers) {
            players.add(new Player(playerName));
            views.showPlayerName(players.size(), playerName);
        }
    }


    public void startGame() {
        if (gameState != GameState.CardsDealt) {
            deck.shuffle();
            int playerIndex = 1;
            for (IPlayer player : players) {
                player.addCardToHand(deck.removeTopCard());
                views.showFaceDownCardForPlayer(playerIndex++, player.getName());
            }
            gameState = GameState.CardsDealt;
        }
    }

    public void flipCards() {
        int playerIndex = 1;
        for (IPlayer player : players) {
            PlayingCard pc = player.getCard(0);
            pc.flip();
            views.showCardForPlayer(playerIndex++, player.getName(), pc.getRank().toString(), pc.getSuit().toString());
        }

        evaluateWinner();
        displayWinner();
        rebuildDeck();
        gameState = GameState.WinnerRevealed;
    }

    public void evaluateWinner() {
        winner = new WinningPlayer(evaluator.evaluateWinner(players));
    }

    public void displayWinner() {
        views.showWinner(winner.getName());
    }

    private void rebuildDeck() {
        for (IPlayer player : players) {
            deck.returnCardToDeck(player.removeCard());
        }
    }

    private void restartGame() {
        rebuildDeck();
        gameState = GameState.AddingPlayers;
    }

}

