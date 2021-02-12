package com.example.demo.model;

public class Game {

    private final String askedLetter;
    private final String playerName;

    public Game(String askedLetter, String playerName) {
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
        return "Game{" +
                "askedLetter='" + askedLetter + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
