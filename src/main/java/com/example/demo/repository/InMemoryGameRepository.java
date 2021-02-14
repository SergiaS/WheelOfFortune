package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.to.GameTo;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class InMemoryGameRepository {

    private final Game game;

    public InMemoryGameRepository() {
        game = new Game();
    }

    public Game move(GameTo gameTo) {
        String currentPlayerName = game.getPlayers().peek();
        game.setPlayerOnAir(currentPlayerName);

        if (!currentPlayerName.equals(gameTo.getPlayerName())) {
            throw new IllegalArgumentException();
        }

        String askedLetter = gameTo.getAskedLetter().toLowerCase();
        Set<Character> askedLettersSet = game.getAskedLettersSet();

        if (!askedLettersSet.contains(askedLetter.charAt(0))) {
            askedLettersSet.add(askedLetter.charAt(0));
            game.setGuessedWord(writeGuessedLettersInTheWord());
            if (!game.getTargetWord().contains(askedLetter)) {
                game.getPlayers().add(game.getPlayers().poll());
                game.setPlayerOnAir(game.getPlayers().peek());
            }
        }
        return this.game;
    }

    private String writeGuessedLettersInTheWord() {
        StringBuilder res = new StringBuilder();

        String targetWord = game.getTargetWord();
        for (char c : targetWord.toCharArray()) {
            if (game.getAskedLettersSet().contains(c)) {
                res.append(c).append(" ");
            } else {
                res.append("_ ");
            }
        }
        game.setPlaying(res.toString().contains("_"));
        return String.valueOf(res).trim();
    }

    public Game getGameInfo() {
        return game;
    }

    public void addNewPlayer(String name) {
        game.getPlayers().add(name);
    }

    public void removePlayer(String name) {
        game.getPlayers().remove(name);
    }
}
