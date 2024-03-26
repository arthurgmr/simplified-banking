package com.simplifiedbanking.dtos;

import java.util.Optional;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(Optional<String> message, HttpStatus statusCode) {
  
}
