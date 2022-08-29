package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

@ApiModel(description = "Game - it's a basic model of the game")
public class Game {

    @ApiModelProperty(notes = "Id of the game")
    private int gameId;

    @ApiModelProperty(notes = "Target word of the game")
    private String targetWord;

    @ApiModelProperty(notes = "Shows what letters are guessed in target word")
    private String guessedWord;

    @ApiModelProperty(notes = "Shows what letters was asked")
    private Set<Character> askedLettersSet;

    @ApiModelProperty(notes = "Shows players list of current game")
    private Queue<String> players;

    @ApiModelProperty(notes = "Shows current player move")
    private String playerOnAir;

    @ApiModelProperty(notes = "Shows status of the game")
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

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    public String writeGuessedLettersInTheWord() {
        StringBuilder res = new StringBuilder();

        for (char c : targetWord.toCharArray()) {
            if (askedLettersSet.contains(c)) {
                res.append(c).append(" ");
            } else {
                res.append("_ ");
            }
        }
        isPlaying = res.toString().contains("_");
        return String.valueOf(res).trim();
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", targetWord='" + targetWord + '\'' +
                ", guessedWord='" + guessedWord + '\'' +
                ", askedLettersSet=" + askedLettersSet +
                ", players=" + players +
                ", playerOnAir='" + playerOnAir + '\'' +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
