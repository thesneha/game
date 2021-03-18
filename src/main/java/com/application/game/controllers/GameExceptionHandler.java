package com.application.game.controllers;


import com.application.game.Response.EntityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<EntityResponse>  exceptionHandler(Exception e) {
//		return new ResponseEntity<>(new EntityResponse("4000", e.getMessage()), HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.OK)

				.body(new EntityResponse(false,e.getMessage()));


	}

}
