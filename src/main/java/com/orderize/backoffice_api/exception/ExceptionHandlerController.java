package com.orderize.backoffice_api.exception;

import com.orderize.backoffice_api.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(Exception exception, HttpServletRequest request) {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationError(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errorMessage = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorMessage.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                errorMessage.toString(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException exception, HttpServletRequest request) {
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.name(),
                exception.getMessage(),
                request.getServletPath()

        );
    }

}
