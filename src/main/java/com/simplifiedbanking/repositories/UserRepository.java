package com.simplifiedbanking.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifiedbanking.domain.user.User;
import java.util.Optional;
import java.util.List;



public interface UserRepository extends JpaRepository<User, UUID> {
  
  Optional<User> findByDocument(String document);

  Optional<User> findById(UUID id);

}
