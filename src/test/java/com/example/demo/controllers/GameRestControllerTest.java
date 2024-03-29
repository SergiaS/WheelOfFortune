package com.example.demo.controllers;

import com.example.demo.repository.InMemoryGameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameRestControllerTest {
    private final String REST_URL = GameRestController.REST_URL;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryGameRepository repository;

    @BeforeEach
    void settings() {
        repository.startGame();
    }

    @AfterEach
    void finish() {
        repository.restartGame("1");
    }

    @Test
    void startGame() throws Exception {
        mockMvc
                .perform(get(REST_URL + "/start"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getGameInfo() throws Exception {
        mockMvc
                .perform(get(REST_URL + "/info/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void restartGame() throws Exception {
        mockMvc
                .perform(get(REST_URL + "/restart/" + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void addNewPlayer() throws Exception {
        mockMvc
                .perform(post(REST_URL + "/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gameId\":\"1\",\"name\":\"Marta\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void removePlayer() throws Exception {
        mockMvc
                .perform(delete(REST_URL + "/user/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gameId\":\"1\",\"name\":\"Bob\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void move() throws Exception {
        mockMvc
                .perform(put(REST_URL + "/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"gameId\":\"1\",\"askedLetter\":\"z\",\"playerName\":\"Bob\"}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gameId", notNullValue()))
                .andExpect(jsonPath("$.targetWord", containsString("a")))
                .andExpect(jsonPath("$.players").isArray())
                .andExpect(jsonPath("$.players", hasItem("Bob")));
    }

    @Test
    void pageNotFound() throws Exception {
        mockMvc
                .perform(get(REST_URL + "/nothing"))
                .andExpect(status().is4xxClientError());
    }
}

