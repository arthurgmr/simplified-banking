package com.simplifiedbanking.dtos;

import java.math.BigDecimal;

import com.simplifiedbanking.domain.user.UserType;

public record UserDTO(String firstName, String lastName, String document, String email, String password, BigDecimal balance, UserType type) {
  
}
