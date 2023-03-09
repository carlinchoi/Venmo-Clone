package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.util.List;

public interface TransactionDao {
    void createTransaction(Transaction transaction);
    List<Transaction> listTransaction(int userId);
    Transaction getTransaction(int transactionId);
}
