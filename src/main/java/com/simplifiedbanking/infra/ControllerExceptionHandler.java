package com.simplifiedbanking.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simplifiedbanking.dtos.ExceptionDTO;
import com.simplifiedbanking.infra.exceptions.UserNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
  
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity threatDuplicateEntry(DataIntegrityViolationException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO("User already exists", HttpStatus.BAD_REQUEST);
    return ResponseEntity.badRequest().body(exceptionDTO);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity threat404(EntityNotFoundException exception) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> threatGeneralException(UserNotFoundException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
  }
}
