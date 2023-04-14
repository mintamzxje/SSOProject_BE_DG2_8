package com.sso.exception;

import com.sso.payload.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDTO handlerNotFoundException(NotFoundException ex, WebRequest req) {
        return new ResponseDTO(false,HttpStatus.NOT_FOUND, ex.getMessage(),null);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerDuplicateRecordException(DuplicateRecordException ex, WebRequest req) {
        return new ResponseDTO(false,HttpStatus.BAD_REQUEST, ex.getMessage(),null);
    }
}
