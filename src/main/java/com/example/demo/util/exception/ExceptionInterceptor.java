package com.example.demo.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.UnexpectedTypeException;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @ExceptionHandler(value = GameException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorInfo gameValidationError(HttpServletRequest req, GameException gameException) {
        String message = gameException.getMessage();
        log.info(message);
        return new ErrorInfo(req.getRequestURL(), HttpStatus.FORBIDDEN.toString(), message);
    }

    @ExceptionHandler(value = WinnerException.class)
    public String winnerError(WinnerException winnerException) {
        String message = winnerException.getMessage();
        log.info(message);
        return winnerException.getMessage();
    }

    @ExceptionHandler(value = ModelValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageError modelValidationError(ModelValidationException e) {
        log.info(e.getMessage());
        return new MessageError(e.getMessage(), e.getErrorsFound());
    }

    @ExceptionHandler(value = UnexpectedTypeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo typeVariableError(HttpServletRequest req) {
        String message = "Unexpected type of variable!";
        log.info(message);
        return new ErrorInfo(req.getRequestURL(), HttpStatus.FORBIDDEN.toString(), message);
    }
}
