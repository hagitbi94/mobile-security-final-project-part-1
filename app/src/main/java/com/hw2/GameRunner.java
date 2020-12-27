package com.hw2;

import com.hw2.elements.Card;
import com.hw2.elements.Player;
import com.hw2.elements.Rank;
import com.hw2.elements.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRunner {
    List<Card> cards;
    public List<Player> players;




    public GameRunner(){
        createPlayers();

        createDeckForPlayer();

    }

    public void createPlayers(){
        players = new ArrayList<>();
        for(Player.Players player: Player.Players.values()){
            players.add(new Player(player));
        }
    }


    private void createDeck() {
        cards = new ArrayList<>();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                cards.add(new Card(r, s));
            }
        }
    }

    public void createDeckForPlayer() {
        createDeck();
        Collections.shuffle(cards);
        int sizeOfPlayerDeck = 52 / 2;
        List<Card> playerCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            playerCards.addAll(cards.subList(i * sizeOfPlayerDeck, (i + 1) * sizeOfPlayerDeck));
            players.get(i).addCardsToPlayer(playerCards);
            playerCards.clear();
        }
    }

    public void showCards(){
        for(Card card : cards){
        }
    }


    public void showPlayers(){
        for(Player player : players){
        }
    }


}
