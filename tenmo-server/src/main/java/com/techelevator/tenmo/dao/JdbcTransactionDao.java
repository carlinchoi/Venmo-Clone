package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDao implements TransactionDao {
    
    private static List <Transaction> transactions = new ArrayList<>();
    @Override
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTransactionId(getMaxIdPlusOne());
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> listTransaction(int userId) {
        return transactions;
    }

    @Override
    public Transaction getTransaction(int transactionId) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == transactionId) {
                return transaction;
            }
        }
        return null;
    }
    
    private int getMaxId() {
        int maxId = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() > maxId) {
                maxId = transaction.getTransactionId();
            }
        }
        return maxId;
    }

    private int getMaxIdPlusOne() {
        return getMaxId() + 1;
    }
}
