package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TEnmoService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public TEnmoService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public BigDecimal viewCurrentBalance() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Transaction> viewTransferHistory() {
        // TODO Auto-generated method stub
        return null;
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        // TODO Auto-generated method stub

    }
}
