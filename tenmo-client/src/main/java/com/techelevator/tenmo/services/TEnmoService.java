package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TEnmoService {
    public static String API_BASE_URL = "http://localhost:8080/api/users/";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

//    ResponseEntity<Auction> response =
//            restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), Auction.class);
//    auction = response.getBody();
    public Transaction viewTransaction(int transactionId){
        Transaction transaction = null;
        try {
            ResponseEntity<Transaction> response = restTemplate.exchange(API_BASE_URL + "transfers/" +
                            transactionId, HttpMethod.GET, makeAuthEntity(), Transaction.class);
            transaction = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transaction;
    }

    public BigDecimal viewCurrentBalance(int userId) {
        BigDecimal balance = null;
        try {
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_BASE_URL + userId, HttpMethod.GET,
                    makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }


    public Transaction sendBucks(Transaction transaction, int userId) {
        Transaction returnedTransaction = null;
//        @Valid @RequestBody int fromUserId, int toUserId, BigDecimal amount
        try {
            returnedTransaction = restTemplate.postForObject(API_BASE_URL + userId, makeTransactionEntity(transaction),
                     Transaction.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransaction;
    }

    public Transaction[] viewTransferHistory(int userId) {
        Transaction[] transactions = null;
        try {
            ResponseEntity<Transaction[]> response = restTemplate.exchange(API_BASE_URL + userId + "/transfers",
                    HttpMethod.GET, makeAuthEntity(), Transaction[].class);
            transactions = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transactions;
    }

    public UserDto[] listUsers(){
        UserDto[] users = null;
        try {
            ResponseEntity<UserDto[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(),
                    UserDto[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }


    private HttpEntity<Transaction> makeTransactionEntity(Transaction transaction) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<Transaction>(transaction, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
