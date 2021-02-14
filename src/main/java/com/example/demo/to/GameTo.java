package com.example.demo.to;

public class GameTo {

    private final String askedLetter;
    private final String playerName;

    public GameTo(String askedLetter, String playerName) {
        this.askedLetter = askedLetter;
        this.playerName = playerName;
    }

    public String getAskedLetter() {
        return askedLetter;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return "GameTo{" +
                "askedLetter='" + askedLetter + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
