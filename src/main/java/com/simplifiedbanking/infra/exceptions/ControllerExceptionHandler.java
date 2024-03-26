package com.simplifiedbanking.infra.exceptions;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simplifiedbanking.dtos.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
  
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> threatDuplicateEntry(DataIntegrityViolationException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(Optional.of("User already exists"), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> threatMessageNotReadable(HttpMessageNotReadableException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(Optional.of(exception.getMessage()), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> threatGeneralException(UserNotFoundException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(Optional.of(exception.getMessage()), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnauthorizedTransactionException.class)
  public ResponseEntity<Object> threatUnauthorizedTransactionException(UnauthorizedTransactionException exception) {
    ExceptionDTO exceptionDTO = new ExceptionDTO(Optional.of(exception.getMessage()), HttpStatus.FORBIDDEN);
    return new ResponseEntity<>(exceptionDTO, HttpStatus.FORBIDDEN);
  }
}
