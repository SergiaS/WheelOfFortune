package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.to.GameTo;
import com.example.demo.util.exception.PlayerNameException;
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
            throw new PlayerNameException(
                    String.format("Expected player %s to move, but found %s", currentPlayerName, gameTo.getPlayerName()));
        }

        String askedLetter = gameTo.getAskedLetter();
        Set<Character> askedLettersSet = game.getAskedLettersSet();
        if (!askedLettersSet.contains(askedLetter.charAt(0))) {
            askedLettersSet.add(askedLetter.charAt(0));
            game.setGuessedWord(game.writeGuessedLettersInTheWord());
            if (!game.getTargetWord().contains(askedLetter)) {
                game.getPlayers().add(game.getPlayers().poll());
                game.setPlayerOnAir(game.getPlayers().peek());
            }
        }
        return this.game;
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
