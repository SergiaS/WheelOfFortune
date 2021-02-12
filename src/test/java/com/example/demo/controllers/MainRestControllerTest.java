package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MainRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkRequestedLetter() throws Exception {
        mockMvc
                .perform(post("/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"askedLetter\":\"z\",\"playerName\":\"Bob\"}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.targetWord", containsString("a")))
                .andExpect(jsonPath("$.players").isArray())
                .andExpect(jsonPath("$.players", hasItem("Bob")))
                .andExpect(jsonPath("$.players", hasSize(3)));
    }
}

