package com.example.demo.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "GameTo - it's DTO model")
public class GameTo {

    @ApiModelProperty(notes = "Id of the game")
    @NotNull
    private final Integer gameId;

    @ApiModelProperty(notes = "Asked letter of playerName")
    @Size(min = 1, max = 1, message = "Should be only 1 letter")
    @Pattern(regexp = "[A-Za-z]", message = "Use only letters of any case")
    private final String askedLetter;

    @ApiModelProperty(notes = "Name of the player which asks the letter")
    @Pattern(regexp = "[A-Za-z]+\\s*", message = "Use only letters of any case and whitespace")
    private final String playerName;

    public GameTo(Integer gameId, String askedLetter, String playerName) {
        this.gameId = gameId;
        this.askedLetter = askedLetter.toLowerCase();
        this.playerName = playerName;
    }

    public Integer getId() {
        return gameId;
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
                "gameId=" + gameId +
                ", askedLetter='" + askedLetter + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
