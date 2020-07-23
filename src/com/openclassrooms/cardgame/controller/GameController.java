package com.openclassrooms.cardgame.controller;

import com.openclassrooms.cardgame.games.GameEvaluator;
import com.openclassrooms.cardgame.games.HighCardGameEvaluator;
import com.openclassrooms.cardgame.model.Deck;
import com.openclassrooms.cardgame.model.Player;
import com.openclassrooms.cardgame.model.PlayingCard;
import com.openclassrooms.cardgame.view.CommandLineView;
import com.openclassrooms.cardgame.view.GameViewable;

import java.util.ArrayList;


public class GameController {

    Deck deck;
    ArrayList<Player> players;
    Player winner;
    GameViewable view;
    GameState gameState;
    GameEvaluator evaluator;
    public GameController(GameViewable view, Deck deck, GameEvaluator _evaluator) {
        this.view = view;
        this.deck = deck;
        players = new ArrayList<Player>();
        gameState = GameState.AddingPlayers;
        view.setController(this);
        this.evaluator = _evaluator;
        this.evaluator = new HighCardGameEvaluator();
    }

    public void run() {
        while (true) {
            switch (gameState) {
                case AddingPlayers:
                    view.promptForPlayerName();
                    break;

                case CardsDealt:
                    view.promptForFlip();
                    break;

                case WinnerRevealed:
                    view.promptForNewGame();
                    break;
            }
        }
    }

    public void addPlayer(String playerName) {
        if (gameState == GameState.AddingPlayers) {
            players.add(new Player(playerName));
            view.showPlayerName(players.size(), playerName);
        }
    }

    public void startGame() {
        if (gameState != GameState.CardsDealt) {
            deck.shuffle();
            int playerIndex = 1;
            for (Player player : players) {
                player.addCardToHand(deck.removeTopCard());
                view.showFaceDownCardForPlayer(playerIndex++, player.getName());
            }
            gameState = GameState.CardsDealt;
        }
    }

    public void flipCards() {
        int playerIndex = 1;
        for (Player player : players) {
            PlayingCard pc = player.getCard(0);
            pc.flip();
            view.showCardForPlayer(playerIndex++, player.getName(), pc.getRank().toString(), pc.getSuit().toString());
        }

        evaluateWinner();
        displayWinner();
        rebuildDeck();
        gameState = GameState.WinnerRevealed;
    }
void restartGame(){
        rebuildDeck();
        gameState = GameState.AddingPlayers;
}
    void evaluateWinner() {

        winner = evaluator.evaluateWinner(players);
    }

    void displayWinner() {
        view.showWinner(winner.getName());
    }

    void rebuildDeck() {
        for (Player player : players) {
            deck.returnCardToDeck(player.removeCard());
        }
    }

    enum GameState {
        AddingPlayers,
        CardsDealt,
        WinnerRevealed
    }

}

