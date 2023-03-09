package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransactionDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcTransactionDaoTests extends BaseDaoTests {
    protected static final Transaction TRANSACTION_1 = new Transaction(3001,1001, 1002, new BigDecimal("100.00"), 1, 1);
    protected static final Transaction TRANSACTION_2 = new Transaction(3002, 1002, 1003, new BigDecimal("500.00"), 1, 1);
    protected static final Transaction TRANSACTION_3 = new Transaction(3003, 1003, 1001, new BigDecimal("100.00"), 1, 1);

    private JdbcTransactionDao sut;

    private Transaction testTransaction;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransactionDao(jdbcTemplate);
        testTransaction = new Transaction(1001, 1003, BigDecimal.valueOf(50), 1, 1);
    }

    @Test
    public void create_transaction_creates_a_transaction() {
        Transaction createdTransaction = sut.createTransaction(testTransaction);

        int newId = createdTransaction.getTransactionId();
        Transaction retrievedTransaction = sut.getTransaction(newId);

        assertTransactionsMatch(createdTransaction, retrievedTransaction);
    }

    @Test
    public void findAllTransactionsById() {
        List<Transaction> transactions = sut.listTransaction(1001);

        Assert.assertEquals(2, transactions.size());
        assertTransactionsMatch(TRANSACTION_1, transactions.get(0));
        assertTransactionsMatch(TRANSACTION_3, transactions.get(1));
    }

    @Test
    public void getTransactionByTransactionId() {
        Transaction actualTransaction = sut.getTransaction(TRANSACTION_1.getTransactionId());

        assertTransactionsMatch(TRANSACTION_1, actualTransaction);
    }


    private void assertTransactionsMatch(Transaction expected, Transaction actual) {
        Assert.assertEquals(expected.getTransactionId(), actual.getTransactionId());
        Assert.assertEquals(expected.getFromUserId(), actual.getFromUserId());
        Assert.assertEquals(expected.getToUserId(), actual.getToUserId());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
        Assert.assertEquals(expected.getTransferStatus(), actual.getTransferStatus());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
    }



}
