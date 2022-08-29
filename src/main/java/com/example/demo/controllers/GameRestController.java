package com.example.demo.controllers;

import com.example.demo.model.Game;
import com.example.demo.repository.InMemoryGameRepository;
import com.example.demo.to.GameTo;
import com.example.demo.util.ValidationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = GameRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "hello", description = "Sample hello world application")
public class GameRestController {
    static final String REST_URL = "/wheel/rest";
    private static final Logger log = LoggerFactory.getLogger(GameRestController.class);

    private final InMemoryGameRepository repository;

    public GameRestController(InMemoryGameRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Starts new game each time when get this endpoint")
    @GetMapping(value = "/start")
    public Game startGame() {
        log.info("startGame");
        return repository.startGame();
    }

    @ApiOperation(value = "Shows game status")
    @GetMapping("/info/{gameId}")
    public Game getGameInfo(@PathVariable @ApiParam(value = "Takes id of the game") int gameId) {
        log.info("getGameInfo of {}", gameId);
        return repository.getGameInfo(gameId);
    }

    @ApiOperation(value = "Restarts game with needed id")
    @GetMapping(value = "/restart/{gameId}")
    public Game restartGame(@PathVariable @ApiParam(value = "Takes id of the game") String gameId) {
        ValidationUtil.gameIdValidation(gameId, "Game id values is invalid");
        log.info("restartGame number {}", gameId);
        return repository.restartGame(gameId);
    }

    @ApiOperation(value = "Adds new player to the game with needed id")
    @PostMapping(value = "/user/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addNewPlayer(@RequestBody @ApiParam(value = "Adds player move") Map<String, String> body) {
        String gameId = body.get("gameId");
        String name = body.get("name");
        ValidationUtil.gameIdValidation(gameId, "Game id values is invalid");
        ValidationUtil.playerNameValidation(name, "Player name is invalid");
        log.info("addNewPlayer - New player {} added to the game number {}", name, gameId);
        return repository.addNewPlayer(gameId, name);
    }

    @ApiOperation(value = "Adds new player to the game with needed id")
    @DeleteMapping(value = "/user/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String removePlayer(@RequestBody @ApiParam(value = "Map with gameId and player name") Map<String, String> body) {
        String gameId = body.get("gameId");
        String name = body.get("name");
        ValidationUtil.gameIdValidation(gameId, "Game id values is invalid");
        ValidationUtil.playerNameValidation(name, "Player name is invalid");
        log.info("removePlayer - Player {} removed from the game number {}", name, gameId);
        return repository.removePlayer(gameId, name);
    }

    @ApiOperation(value = "Players action")
    @PutMapping(value = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Game move(@Validated @RequestBody @ApiParam(value = "Takes player's move") GameTo gameTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationUtil.jsonValidation(bindingResult, "Invalid JSON input! Player's move can't be execute!");
        }
        log.info("Player {} is asking the letter {}", gameTo.getPlayerName(), gameTo.getAskedLetter());
        return repository.move(gameTo);
    }
}
