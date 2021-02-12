package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.to.GameTo;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class InMemoryGameRepository {

    private final GameTo gameTo;

    public InMemoryGameRepository() {
        gameTo = new GameTo();
    }

    public GameTo checkRequestedLetter(Game game) {
        String currentPlayerName = gameTo.getPlayers().peek();
        gameTo.setPlayerOnAir(currentPlayerName);

        if (!currentPlayerName.equals(game.getPlayerName())) {
            throw new IllegalArgumentException();
        }

        String askedLetter = game.getAskedLetter().toLowerCase();
        Set<Character> askedLettersSet = gameTo.getAskedLettersSet();

        if (!askedLettersSet.contains(askedLetter.charAt(0))) {
            askedLettersSet.add(askedLetter.charAt(0));
            gameTo.setGuessedWord(writeGuessedLettersInTheWord());
            if (!gameTo.getTargetWord().contains(askedLetter)) {
                gameTo.getPlayers().add(gameTo.getPlayers().poll());
                gameTo.setPlayerOnAir(gameTo.getPlayers().peek());
            }
        }
        return this.gameTo;
    }

    public String writeGuessedLettersInTheWord() {
        StringBuilder res = new StringBuilder();

        String targetWord = gameTo.getTargetWord();
        for (char c : targetWord.toCharArray()) {
            if (gameTo.getAskedLettersSet().contains(c)) {
                res.append(c).append(" ");
            } else {
                res.append("_ ");
            }
        }
        gameTo.setPlaying(res.toString().contains("_"));
        return String.valueOf(res).trim();
    }

}
