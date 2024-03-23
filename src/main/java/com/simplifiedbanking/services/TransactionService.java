package com.simplifiedbanking.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.simplifiedbanking.domain.transaction.Transaction;
import com.simplifiedbanking.domain.user.User;
import com.simplifiedbanking.dtos.TransactionDTO;
import com.simplifiedbanking.repositories.TransactionRepository;

@Service
public class TransactionService {

  @Autowired
  private UserService userService;

  @Autowired
  private TransactionRepository repository;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${transaction.authorization.url}")
  private String transactionAuthorizationUrl;

  public Transaction createTransaction (TransactionDTO transaction) throws Exception {
    User sender = this.userService.findUserById(transaction.senderId());
    User receiver = this.userService.findUserById(transaction.receiverId());

    userService.validateTransaction(sender, transaction.value());

    boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
    if (!isAuthorized) {
      throw new Exception("Transaction is not authorized.");
    }

    Transaction newTransaction = new Transaction();
    newTransaction.setAmount(transaction.value());
    newTransaction.setSender(sender);
    newTransaction.setReceiver(receiver);
    newTransaction.setTimestamp(LocalDateTime.now());

    sender.setBalance(sender.getBalance().subtract(transaction.value()));
    receiver.setBalance(receiver.getBalance().add(transaction.value()));

    this.repository.save(newTransaction);
    this.userService.saveUser(sender);
    this.userService.saveUser(receiver);

    this.notificationService.sendNotification(sender, "Transaction made successfully");
    this.notificationService.sendNotification(receiver, "Transaction received");

    return newTransaction;

  }

  public boolean authorizeTransaction(User sender, BigDecimal value) {
    ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(this.transactionAuthorizationUrl, Map.class);

    if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
      String message = (String) authorizationResponse.getBody().get("message");
      return "Autorizado".equalsIgnoreCase(message);
    } else return false;
  }
  
}
