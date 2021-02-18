package com.example.demo.util;

import com.example.demo.util.exception.GameException;
import com.example.demo.util.exception.ModelValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {
    private static final Map<String, String> errorMap = new HashMap<>();

    public static void jsonValidation(BindingResult result, String msg) {
        for (FieldError fieldError : result.getFieldErrors()) {
            errorMap.computeIfPresent(fieldError.getField(), (k, v) -> v + "; " + fieldError.getDefaultMessage());
            errorMap.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }
        if (!errorMap.isEmpty()) {
            throw new ModelValidationException(msg, errorMap);
        }
    }

    public static void gameIdValidation(String gameId, String msg) {
        if (gameId == null || gameId.equals("null") || gameId.isBlank()) {
            throw new GameException(msg);
        }

        for (char c : gameId.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new GameException(msg);
            }
        }
    }

    public static void playerNameValidation(String name, String msg) {
        if (name == null || name.equals("null") || name.isBlank()) {
            throw new GameException(msg);
        }
    }
}
