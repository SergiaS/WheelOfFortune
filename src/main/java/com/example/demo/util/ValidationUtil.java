package com.example.demo.util;

import com.example.demo.util.exception.JsonInputException;
import com.example.demo.util.exception.PlayerNameException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ValidationUtil {

    public static void jsonValidation(BindingResult result, String msg) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : result.getFieldErrors()) {
            errorMap.computeIfPresent(fieldError.getField(), (k, v) -> v + "; " + fieldError.getDefaultMessage());
            errorMap.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        if (!errorMap.isEmpty()) {
            throw new JsonInputException(msg, errorMap);
        }
    }

    public static void playerNameValidation(String name, String msg) {
        if (name == null || name.equals("null") || name.isBlank()) {
            throw new PlayerNameException(msg);
        }
    }
}
