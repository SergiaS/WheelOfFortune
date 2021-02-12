package com.example.demo.controllers;

import com.example.demo.model.Game;
import com.example.demo.repository.InMemoryGameRepository;
import com.example.demo.to.GameTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {
    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    private final InMemoryGameRepository repository;

    public MainRestController(InMemoryGameRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/move")
    public GameTo checkRequestedLetter(@RequestBody Game game) {
        log.info("Check letter {} by player {} - ", game.getAskedLetter(), game.getPlayerName());
        return repository.checkRequestedLetter(game);
    }
}
