package com.example.demo.repository;

import com.example.demo.model.Game;
import com.example.demo.to.GameTo;
import com.example.demo.util.exception.GameException;
import com.example.demo.util.exception.WinnerException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class InMemoryGameRepository {

    private Game game;
    private int gameCount = 1;
    private final Map<Integer, Game> gameDb = new HashMap<>();

    public Game move(GameTo gameTo) {
        int requestedGameId = gameTo.getId();
        Game requestedGame = gameDb.get(requestedGameId);
        throwExceptionIfTheGameIsNotStarted(requestedGame, requestedGameId);
        if (requestedGame.isPlaying()) {
            String currentPlayerName = requestedGame.getPlayers().peek();
            requestedGame.setPlayerOnAir(currentPlayerName);

            if (!currentPlayerName.equals(gameTo.getPlayerName())) {
                throw new GameException(
                        String.format("Expected player %s to move, but found %s", currentPlayerName, gameTo.getPlayerName()));
            }

            String askedLetter = gameTo.getAskedLetter();
            Set<Character> askedLettersSet = requestedGame.getAskedLettersSet();
            if (!askedLettersSet.contains(askedLetter.charAt(0))) {
                askedLettersSet.add(askedLetter.charAt(0));
                requestedGame.setGuessedWord(requestedGame.writeGuessedLettersInTheWord());
                if (!requestedGame.getGuessedWord().contains("_")) {
                    throw new WinnerException(String.format("%s is winner! If you want more - restart the game.", currentPlayerName));
                }
                if (!requestedGame.getTargetWord().contains(askedLetter)) {
                    requestedGame.getPlayers().add(requestedGame.getPlayers().poll());
                    requestedGame.setPlayerOnAir(requestedGame.getPlayers().peek());
                }
            }
            return requestedGame;
        }
        throw new GameException("The game already ends. Please restart the game - use '/restart/" + requestedGameId + "'");
    }

    public Game startGame() {
        game = new Game();
        game.setGameId(gameCount);
        gameDb.put(gameCount, game);
        gameCount++;
        return game;
    }

    public Game restartGame(String gameId) {
        int requestedGameId = Integer.parseInt(gameId);
        Game game = gameDb.get(requestedGameId);
        throwExceptionIfTheGameIsNotStarted(game, requestedGameId);
        game = new Game();

        game.setGameId(requestedGameId);
        gameDb.put(requestedGameId, game);
        return game;
    }

    public Game getGameInfo(int gameId) {
        Game game = gameDb.get(gameId);
        throwExceptionIfTheGameIsNotStarted(game, gameId);
        return game;
    }

    public String addNewPlayer(String gameId, String name) {
        int requestedGameId = Integer.parseInt(gameId);
        Game game = gameDb.get(requestedGameId);
        throwExceptionIfTheGameIsNotStarted(game, requestedGameId);
        game.getPlayers().add(name);
        return "Player " + name + " is added to the game number " + gameId;
    }

    public String removePlayer(String gameId, String name) {
        int requestedGameId = Integer.parseInt(gameId);
        Game game = gameDb.get(requestedGameId);
        throwExceptionIfTheGameIsNotStarted(game, requestedGameId);
        game.getPlayers().remove(name);
        if (game.getPlayerOnAir().equals(name)) {
            game.setPlayerOnAir(game.getPlayers().peek());
        }
        return "Player " + name + " is removed from the game number " + gameId;
    }

    private void throwExceptionIfTheGameIsNotStarted(Game game, int gameId) {
        if (game == null) {
            throw new GameException("The game with id " + gameId + " doesn't exist!");
        }
    }
}
