package com.example.demo.util.exception;

import java.util.Map;

public class JsonInputException extends RuntimeException {

    private final Map<String, String> errorsFound;

    public JsonInputException(String message, Map<String, String> errorsFound) {
        super(message);
        this.errorsFound = errorsFound;
    }

    public Map<String, String> getErrorsFound() {
        return errorsFound;
    }
}
