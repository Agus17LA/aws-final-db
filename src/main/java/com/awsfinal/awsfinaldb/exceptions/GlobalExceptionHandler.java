package com.awsfinal.awsfinaldb.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParamException.class)
    public ResponseEntity<Object> handleParamException(ParamException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();
        ApiError apiError;
        if (rootCause instanceof SQLException) {
            String message = "Error de integridad de datos: " + rootCause.getMessage();
            apiError = new ApiError(HttpStatus.BAD_REQUEST,message);
        }else{
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error de integridad de datos.");
        }
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex) {
        Throwable rootCause = ex.getCause();
        ApiError apiError;
        if (rootCause instanceof SQLException) {
            String message = "Error de datos: " + rootCause.getMessage();
            apiError = new ApiError( HttpStatus.BAD_REQUEST,message);
        }else{
            apiError = new ApiError( HttpStatus.BAD_REQUEST,"Error de datos");
        }
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException ex) {
        Throwable rootCause = ex.getCause();
        ApiError apiError;

        if (rootCause instanceof SQLException) {
            String message = "Dato no encontrado: " + rootCause.getMessage();
            apiError = new ApiError(HttpStatus.NOT_FOUND, message);
        } else {
            apiError = new ApiError(HttpStatus.NOT_FOUND, "Dato no encontrado.");
        }

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}