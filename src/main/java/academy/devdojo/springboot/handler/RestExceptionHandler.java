package academy.devdojo.springboot.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import academy.devdojo.springboot.exception.BadRequestExceptiomDetails;
import academy.devdojo.springboot.exception.BadRequestException;
import academy.devdojo.springboot.exception.ValidationExceptionDetails;

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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValidException
    (MethodArgumentNotValidException exception){
        List<FieldError> fieldsErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldsErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldsErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(" "));
        
        return new ResponseEntity<>(
            ValidationExceptionDetails.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .title("Bad Request Exception, Invalid Fields")
            .details("Ckeck the field(s) error")
            .developerMessage(exception.getClass().getName())
            .fields(fields)
            .fieldsMessage(fieldsMessage)
            .build(), HttpStatus.BAD_REQUEST);
    }
}
