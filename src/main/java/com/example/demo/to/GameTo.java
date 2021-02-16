package com.example.demo.to;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GameTo {

    @Size(min = 1, max = 1, message = "Should be only 1 letter")
    @Pattern(regexp = "[A-Za-z]", message = "Use only letters of any case")
    private final String askedLetter;

    @Pattern(regexp = "[A-Za-z]+\\s*", message = "Use only letters of any case and whitespace")
    private final String playerName;

    public GameTo(String askedLetter, String playerName) {
        this.askedLetter = askedLetter.toLowerCase();
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
