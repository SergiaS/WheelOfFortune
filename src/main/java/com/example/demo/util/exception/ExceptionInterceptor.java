package com.example.demo.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @ExceptionHandler(value = PlayerNameException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo playerNameValidationError(HttpServletRequest req, PlayerNameException ce) {
        String message = ce.getMessage();
        log.info(message);
        return new ErrorInfo(req.getRequestURL(), HttpStatus.BAD_REQUEST.toString(), message);
    }

    @ExceptionHandler(value = JsonInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageError jsonValidationError(JsonInputException e) {
        log.info(e.getMessage());
        return new MessageError(e.getMessage(), e.getErrorsFound());
    }
}
