package com.bera.todo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.initDirectFieldAccess();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<Object> apiServiceHandleException(Exception ex, WebRequest request) {
    return handleExceptionInternal(
        ex,
        new CustomExceptionVO(ex.getMessage()),
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request);
  }
}
