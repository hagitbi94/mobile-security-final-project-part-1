package com.hw2.elements;

import java.util.List;
import java.util.Stack;

public class Deck {

    private Stack<Card> cards = new Stack<>();


    public Deck(){
        this.cards = new Stack<>();
    }




    public int getCardsCount() {
        return this.cards.size();
    }

    public void addCard(List<Card> card) {
        this.cards.addAll(card);
    }



    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    public Card getCard(){
        return cards.pop();
    }

    public int deckIsEmpty(){
        if(cards.empty()){
            return 0;
        }
        return 1;
    }



    @Override
    public String toString() {
        return this.cards.toString();
    }
}
