package com.example.demo.model;

import java.util.*;

public class Game {

    private String targetWord;
    private String guessedWord;

    private Set<Character> askedLettersSet;
    private Queue<String> players;
    private String playerOnAir;
    private boolean isPlaying;

    public Game() {
        targetWord = setupRandomTargetWord();
        guessedWord = targetWord.replaceAll("\\w", "_ ").trim();
        askedLettersSet = new HashSet<>();
        players = new ArrayDeque<>();
        {
            players.add("Bob");
            players.add("Carl");
            players.add("Tina");
        }
        playerOnAir = players.peek();
        isPlaying = true;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }

    public Set<Character> getAskedLettersSet() {
        return askedLettersSet;
    }

    public void setAskedLettersSet(Set<Character> askedLettersSet) {
        this.askedLettersSet = askedLettersSet;
    }

    public Queue<String> getPlayers() {
        return players;
    }

    public void setPlayers(Queue<String> players) {
        this.players = players;
    }

    public String getPlayerOnAir() {
        return playerOnAir;
    }

    public void setPlayerOnAir(String playerOnAir) {
        this.playerOnAir = playerOnAir;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String setupRandomTargetWord() {
        String[] words = {"banana", "cocaine", "badabum"};
        return words[new Random().nextInt(words.length)];
    }

    @Override
    public String toString() {
        return "GameTo{" +
                "targetWord='" + targetWord + '\'' +
                ", askedLettersSet=" + askedLettersSet +
                ", players=" + players +
                ", playerOnAir='" + playerOnAir + '\'' +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
