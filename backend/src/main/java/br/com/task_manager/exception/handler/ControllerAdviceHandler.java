package br.com.task_manager.exception.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.task_manager.exception.TaskManagerBadRequestException;
import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.exception.error.StandardError;
import br.com.task_manager.exception.error.ValidationError;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(TaskManagerBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> badRequestException(TaskManagerBadRequestException e,
            HttpServletRequest http) {
        StandardError error = new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), http.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TaskManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StandardError> notFoundException(TaskManagerNotFoundException e, HttpServletRequest http) {
        StandardError error = new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(),
                e.getMessage(), http.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationError> validationException(MethodArgumentNotValidException e,
            HttpServletRequest http) {
        ValidationError error = new ValidationError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                e.getTitleMessageCode(), http.getRequestURI());
        for (FieldError field : e.getFieldErrors()) {
            error.addListErrors(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
