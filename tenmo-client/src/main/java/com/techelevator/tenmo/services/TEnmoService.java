package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TenmoService {
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
//        try {
//            restTemplate.postForObject(API_BASE_URL + "?id=" + userId, transaction.getAmount(), makeTransactionEntity(transaction), Transaction.class);
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
    }

    public Transaction[] viewTransferHistory(int userId) {
        // TODO Auto-generated method stub
        Transaction[] transactions = null;
        try {
            transactions = restTemplate.getForObject(API_BASE_URL + userId + "/transfers", Transaction[].class );
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transactions;
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
