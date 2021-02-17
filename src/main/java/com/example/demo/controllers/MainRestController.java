package com.example.demo.controllers;

import com.example.demo.model.Game;
import com.example.demo.repository.InMemoryGameRepository;
import com.example.demo.to.GameTo;
import com.example.demo.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = MainRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MainRestController {
    static final String REST_URL = "/wheel/rest";
    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    private final InMemoryGameRepository repository;

    public MainRestController(InMemoryGameRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/info")
    public Game getGameInfo() {
        log.info("getGameInfo");
        return repository.getGameInfo();
    }

    @GetMapping(value = {"/start", "/restart"})
    public Game startOrRestartGame() {
        log.info("New game is started!");
        return repository.startOrRestartGame();
    }

    @PostMapping(value = "/user/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addNewPlayer(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        ValidationUtil.playerNameValidation(name, "Player name is invalid");
        log.info("addNewPlayer - New player {} added to the game", name);
        return repository.addNewPlayer(name);
    }

    @DeleteMapping(value = "/user/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String removePlayer(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        ValidationUtil.playerNameValidation(name, "Player name is invalid");
        log.info("removePlayer - Player {} removed from the game", name);
        return repository.removePlayer(name);
    }

    @PutMapping(value = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Game move(@Validated @RequestBody GameTo gameTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationUtil.jsonValidation(bindingResult, "Invalid JSON input! Player's move can't be execute!");
        }
        log.info("Player {} is asking the letter {}", gameTo.getPlayerName(), gameTo.getAskedLetter());
        return repository.move(gameTo);
    }
}
