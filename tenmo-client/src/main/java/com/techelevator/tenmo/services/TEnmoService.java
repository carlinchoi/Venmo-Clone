package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDto;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TEnmoService {
    public static String API_BASE_URL = "http//localhost:8080/api/users/";
    private RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }


    public BigDecimal viewCurrentBalance() {
        // TODO Auto-generated method stub
        return null;
    }

    private void sendBucks(Transaction transaction, User userId) {
        // TODO Auto-generated method stub
    }

    public Transaction[] viewTransferHistory(int userId) {
        // TODO Auto-generated method stub
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

    public Transaction viewTransaction(int transactionId){
        return null;
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
