package academy.devdojo.springboot.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import academy.devdojo.springboot.exception.BadRequestExceptiomDetails;
import academy.devdojo.springboot.exception.BadRequestException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptiomDetails> handleBadRequestException(BadRequestException badRequestException){
        return new ResponseEntity<>(
            BadRequestExceptiomDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request Exception, Check the Documentation")
            .details(badRequestException.getMessage())
            .developerMessage(badRequestException.getClass().getName())
            .build(), HttpStatus.BAD_REQUEST);
    }
}
