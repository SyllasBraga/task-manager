package br.com.task_manager.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.task_manager.exception.TaskManagerBadRequestException;
import br.com.task_manager.exception.TaskManagerNotFoundException;
import br.com.task_manager.exception.error.StandardError;
import br.com.task_manager.exception.error.ValidationError;
import br.com.task_manager.exception.handler.ControllerAdviceHandler;

class ControllerAdviceHandlerTest {

    @InjectMocks
    private ControllerAdviceHandler controllerAdviceHandler;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenBadRequestExceptionThenReturnsBadRequestResponse() {
        String errorMessage = "Bad Request Error";
        when(httpServletRequest.getRequestURI()).thenReturn("/test-uri");

        TaskManagerBadRequestException exception = new TaskManagerBadRequestException(errorMessage);

        ResponseEntity<StandardError> response = controllerAdviceHandler.badRequestException(exception, httpServletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void whenNotFoundExceptionThenReturnsNotFoundResponse() {
        String errorMessage = "Not Found Error";
        when(httpServletRequest.getRequestURI()).thenReturn("/test-uri");

        TaskManagerNotFoundException exception = new TaskManagerNotFoundException(errorMessage);

        ResponseEntity<StandardError> response = controllerAdviceHandler.notFoundException(exception, httpServletRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void whenNoSuchElementExceptionThenReturnsNotFoundResponse() {
        String errorMessage = "Element Not Found";
        when(httpServletRequest.getRequestURI()).thenReturn("/test-uri");

        NoSuchElementException exception = new NoSuchElementException(errorMessage);

        ResponseEntity<StandardError> response = controllerAdviceHandler.noSuchElementExceptionnotFoundException(exception, httpServletRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void whenValidationExceptionThenReturnsValidationErrorResponse() {
        String errorMessage = "Validation Error";
        when(httpServletRequest.getRequestURI()).thenReturn("/test-uri");
        when(methodArgumentNotValidException.getFieldErrors()).thenReturn(List.of(new FieldError("object", "Validation Error", errorMessage)));

        ResponseEntity<ValidationError> response = controllerAdviceHandler.validationException(methodArgumentNotValidException, httpServletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().getListErrors().get(0).getFieldMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }
}

