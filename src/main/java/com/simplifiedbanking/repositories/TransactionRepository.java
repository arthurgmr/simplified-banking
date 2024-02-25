package com.simplifiedbanking.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplifiedbanking.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>{
  
}
