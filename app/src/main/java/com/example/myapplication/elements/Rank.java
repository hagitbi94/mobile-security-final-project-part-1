package com.example.myapplication.elements;

public enum Rank {
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9),
    TEN("ten", 10),
    JACK("jack", 11),
    QUEEN("queen", 12),
    KING("king", 13),
    ACE("ace",14);

    private String rankName;
    private int pointValue;

    Rank (String rankName, int pointValue) {
        this.pointValue = pointValue;
        this.rankName = rankName;
    }

    /**
     * example ACE = Ace
     * @return longHand of rank
     */
    public String getRankName() {
        return this.rankName;
    }

    /**
     * example ACE = 14
     * @return int rank from 2 to 14
     */
    public int getPointValue() {
        return this.pointValue;
    }

    /**
     * example ACE = A
     * @return shortHand of rank
     */
    @Override
    public String toString() {
        if(this.getPointValue() > 10){
            return this.getRankName().substring(0,1);
        }
        return String.valueOf(this.getPointValue());
    }

}
