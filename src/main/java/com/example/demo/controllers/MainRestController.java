package com.example.demo.controllers;

import com.example.demo.model.Game;
import com.example.demo.repository.InMemoryGameRepository;
import com.example.demo.to.GameTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
        log.info("Get game info");
        return repository.getGameInfo();
    }

    @PostMapping(value = "/user/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNewPlayer(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        log.info("New player {} added to the game", name);
        repository.addNewPlayer(name);
    }

    @DeleteMapping(value = "/user/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removePlayer(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        log.info("Player {} removed from the game", name);
        repository.removePlayer(name);
    }

    @PutMapping(value = "/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Game move(@RequestBody GameTo gameTo) {
        log.info("Check letter {} by player {}", gameTo.getAskedLetter(), gameTo.getPlayerName());
        return repository.move(gameTo);
    }
}
