package com.simplifiedbanking.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.simplifiedbanking.domain.user.User;
import com.simplifiedbanking.domain.user.UserType;
import com.simplifiedbanking.dtos.UserDTO;
import com.simplifiedbanking.infra.exceptions.UnauthorizedTransactionException;
import com.simplifiedbanking.infra.exceptions.UserNotFoundException;
import com.simplifiedbanking.repositories.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository repository;

  public void validateTransaction(User sender, BigDecimal amount) throws UnauthorizedTransactionException {
    
    if (sender.getUserType() == UserType.MERCHANT) {
      throw new UnauthorizedTransactionException("Merchant user doesn't have authorization to make transactions.");
    }

    if (sender.getBalance().compareTo(amount) < 0) {
      throw new UnauthorizedTransactionException("Insufficient balance.");
    }
  }

  public User findUserById(UUID id) throws Exception {
    return this.repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found."));
  }

  public User createUser(UserDTO data) {
    User newUser = new User(data);
    this.saveUser(newUser);
    return newUser;
  }

  public void saveUser (User user) {
    this.repository.save(user);
  }

  public List<User> getAllUsers() {
   return this.repository.findAll();
  }
}
