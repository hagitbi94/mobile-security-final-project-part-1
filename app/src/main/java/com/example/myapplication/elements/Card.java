package com.example.myapplication.elements;

public class Card implements Comparable<Card>{
    private Rank rank;
    private Suit suit;
    private String card;


    public Card(){}


    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.card = String.format("%s%s", rank.toString(), suit.getSymbol());
    }

    public Suit getSuit() {
        return this.suit;
    }

    public String getSuitLetter() {
        return this.suit.getSymbol();
    }

    public Rank getRank() {
        return this.rank;
    }


    public String getRankLetter() {
        return this.rank.toString();
    }


    public Integer getCardPoints() {
        return this.rank.getPointValue();
    }


    public String getCard() {
        return this.card;
    }


    public int compareTo(Card other) {

        return this.getCardPoints().compareTo(other.getCardPoints());
    }


    public boolean isSameSuit(Card other) {
        return this.suit.equals(other.suit);
    }


    public boolean isSameRank(Card other) {
        return this.rank.equals(other.rank);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Card.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Card other = (Card) obj;
        return this.suit == other.suit && this.rank == other.rank;
    }

    @Override
    public String toString() {
        return this.rank.getRankName() + " of " + this.suit.getSuitName();
    }

}
