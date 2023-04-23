package com.example.ecovelo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnAuthorizeHandler {
	@ExceptionHandler(UnAuthorizeException.class)
	public ResponseEntity<Object> handleUnAuthorizeException(UnAuthorizeException ex){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	}
}
