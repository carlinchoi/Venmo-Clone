package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transaction createTransaction(Transaction newTransaction) {
        String sql = "INSERT INTO transfer(" +
                "transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, 1, 1,
                newTransaction.getFromUserId(), newTransaction.getToUserId(), newTransaction.getAmount());
        return getTransaction(newId);
    }

    @Override
    public List<Transaction> listTransaction(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer WHERE account_from = ? OR account_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            Transaction transaction = mapRowToTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public Transaction getTransaction(int transactionId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionId);
        if (results.next()) {
            return mapRowToTransaction(results);
        } else {
            return null;
        }
    }


    private Transaction mapRowToTransaction(SqlRowSet results) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(results.getInt("transfer_id"));
        transaction.setFromUserId(results.getInt("account_from"));
        transaction.setToUserId(results.getInt("account_to"));
        transaction.setAmount(results.getBigDecimal("amount"));
        transaction.setTransferStatus(results.getInt("transfer_status_id"));
        transaction.setTransferTypeId(results.getInt("transfer_type_id"));
        return transaction;
    }

}
