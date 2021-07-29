package com.example.myapplication.elements;

public enum Suit {
    DIAMONDS("diamonds", "D"),
    SPADES("spades", "S"),
    CLUBS("clubs", "C"),
    HEARTS("hearts", "H");

    private String suitName;
    private String symbol;

    Suit(String suitName, String symbol){
        this.suitName = suitName;
        this.symbol = symbol;
    }

    /**
     *example HEARTS = H
     * @return shortHand of suit
     */
    public String getSymbol(){
        return this.symbol;
    }

    /**
     *example Hearts = Hearts
     * @return longHand of suit
     */
    public String getSuitName() {
        return this.suitName;
    }

    /**
     *example HEARTS = H
     * @return shortHand of suit
     */
    @Override
    public String toString() {
        return this.getSymbol();
    }
}
