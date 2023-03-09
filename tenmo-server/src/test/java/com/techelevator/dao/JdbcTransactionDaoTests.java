package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransactionDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcTransactionDaoTests extends BaseDaoTests {
    protected static final Transaction TRANSACTION_1 = new Transaction(1, 2, BigDecimal.valueOf(100), 1, 1);
    protected static final Transaction TRANSACTION_2 = new Transaction(2, 3, BigDecimal.valueOf(500), 1, 1);
    protected static final Transaction TRANSACTION_3 = new Transaction(3, 1, BigDecimal.valueOf(100), 1, 1);

    private JdbcTransactionDao sut;

    private Transaction testTransaction;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransactionDao(jdbcTemplate);
        testTransaction = new Transaction(0, 10, BigDecimal.valueOf(50), 1, 1);
    }

    @Test
    public void create_transaction_creates_a_transaction() {
        Transaction createdTransaction = sut.createTransaction(testTransaction);

        int newId = createdTransaction.getTransactionId();
        Transaction retrievedTransaction = sut.getTransaction(newId);

        assertTransactionsMatch(createdTransaction, retrievedTransaction);
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
