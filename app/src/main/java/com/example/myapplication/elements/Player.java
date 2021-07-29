package com.example.myapplication.elements;

import java.util.List;

public class Player {
    public static enum Players {player1,player2}
    private Players players;
    private int score = 0;
    private Deck playerDeck;


    public Player(){

    }

    public Player(Players players){
        this.players = players;
        this.playerDeck = new Deck();
        score=0;
    }

    public int getScore(){
        return score;

    }


    public void addScore(){
        this.score++;
    }

    public Players getPlayers(){
        return players;
    }

    public Deck getPlayerDeck(){
        return playerDeck;
    }


    public void addCardsToPlayer(List<Card> card){
        this.getPlayerDeck().addCard(card);
    }

    @Override
    public String toString() {
        return "Player{" +
                "players=" + players +
                ", score=" + score +
                ", playerDeck=" + playerDeck +
                '}';
    }

}
