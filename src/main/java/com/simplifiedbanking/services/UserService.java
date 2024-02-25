package com.simplifiedbanking.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplifiedbanking.domain.user.User;
import com.simplifiedbanking.domain.user.UserType;
import com.simplifiedbanking.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository repository;

  public void validateTransaction(User sender, BigDecimal amount) throws Exception {
    
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new Exception("Merchant user doesn't have authorization to make transactions.");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new Exception("Insufficient balance.");
    }
  }

  public User findUserById(UUID id) throws Exception {
    return this.repository.findById(id).orElseThrow(() -> new Exception("User not found."));
  }

  public void saveUser (User user) {
    this.repository.save(user);
  }
}
