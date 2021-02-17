package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.to.GameTo;
import com.example.demo.util.exception.GameException;
import com.example.demo.util.exception.WinnerException;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class InMemoryGameRepository {

    private Game game;

    public InMemoryGameRepository() {
        game = new Game();
    }

    public Game move(GameTo gameTo) {
        if (game.isPlaying()) {
            String currentPlayerName = game.getPlayers().peek();
            game.setPlayerOnAir(currentPlayerName);

            if (!currentPlayerName.equals(gameTo.getPlayerName())) {
                throw new GameException(
                        String.format("Expected player %s to move, but found %s", currentPlayerName, gameTo.getPlayerName()));
            }

            String askedLetter = gameTo.getAskedLetter();
            Set<Character> askedLettersSet = game.getAskedLettersSet();
            if (!askedLettersSet.contains(askedLetter.charAt(0))) {
                askedLettersSet.add(askedLetter.charAt(0));
                game.setGuessedWord(game.writeGuessedLettersInTheWord());
                if (!game.getGuessedWord().contains("_")) {
                    throw new WinnerException(String.format("%s is winner! If you want more - restart the game.", currentPlayerName));
                }
                if (!game.getTargetWord().contains(askedLetter)) {
                    game.getPlayers().add(game.getPlayers().poll());
                    game.setPlayerOnAir(game.getPlayers().peek());
                }
            }
            return game;
        }
        throw new GameException("The game already ends. Please restart the game.");
    }

    public Game restartGame() {
        return game = new Game();
    }

    public Game getGameInfo() {
        return game;
    }

    public String addNewPlayer(String name) {
        game.getPlayers().add(name);
        return "Player " + name + " is added to the game!";
    }

    public String removePlayer(String name) {
        game.getPlayers().remove(name);
        if (game.getPlayerOnAir().equals(name)) {
            game.setPlayerOnAir(game.getPlayers().peek());
        }
        return "Player " + name + " is removed from the game!";
    }
}
