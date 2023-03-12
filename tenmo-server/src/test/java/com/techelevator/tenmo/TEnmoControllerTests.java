package com.techelevator.tenmo;

import com.techelevator.tenmo.controller.TEnmoController;
import com.techelevator.tenmo.dao.JdbcTransactionDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.UserDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TEnmoControllerTests extends BaseDaoTests{
    private final UserDto user1 = new UserDto(1, "user1");
    private final UserDto user2 = new UserDto(1, "user2");
    private final UserDto user3 = new UserDto(1, "user3");
    protected static final Transaction TRANSACTION_1 = new Transaction(3001,1001, 1002, new BigDecimal("100.00"), 1, 1);
    protected static final Transaction TRANSACTION_2 = new Transaction(3002, 1002, 1003, new BigDecimal("500.00"), 1, 1);
    protected static final Transaction TRANSACTION_3 = new Transaction(3003, 1003, 1001, new BigDecimal("100.00"), 1, 1);

    private List<UserDto> userList = new ArrayList<>();


    private TEnmoController sut;
    private UserDao userDao;
    private TransactionDao transactionDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDao = new JdbcUserDao(jdbcTemplate);
        transactionDao = new JdbcTransactionDao(jdbcTemplate);
        sut = new TEnmoController(userDao, transactionDao);
    }

    @Test
    public void listUsers_returns_correct_list(){
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        Assert.assertEquals(3, sut.listUsers().size());
        Assert.assertEquals("user2", sut.listUsers().get(1).getUsername());
    }


    @Test
    public void getBalance_returns_correct_balance(){
        Assert.assertEquals(new BigDecimal("1000.00"), sut.getBalance(1001));


    }

    @Test
    public void listTransactionsOfUser_returns_correct_transactions(){
      //  Assert.assertEquals();
    }

    @Test
    public void getTransactionDetails_returns_correct_transaction(){

    }

    @Test(expected = ResponseStatusException.class)
    public void getTransactionDetails_returns_exception_given_invalid_id(){
        sut.getTransactionDetails(-1);

    }

//    protected static final Transaction TRANSACTION_1 = new Transaction(3001,1001, 1002, new BigDecimal("100.00"), 1, 1);
//    protected static final Transaction TRANSACTION_2 = new Transaction(3002, 1002, 1003, new BigDecimal("500.00"), 1, 1);
//    protected static final Transaction TRANSACTION_3 = new Transaction(3003, 1003, 1001, new BigDecimal("100.00"), 1, 1);

    @Test
    public void transfer_transfers_correct_amount(){
        sut.transfer(TRANSACTION_1);
        Assert.assertEquals(new BigDecimal("900.00"), sut.getBalance(1001));

    }

}
