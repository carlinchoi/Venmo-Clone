package com.techelevator.dao;

import com.techelevator.tenmo.controller.TEnmoController;
import com.techelevator.tenmo.dao.JdbcTransactionDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class TEnmoControllerTests extends BaseDaoTests{
    private final UserDto user1 = new UserDto(1, "user1");
    private final UserDto user2 = new UserDto(1, "user2");
    private final UserDto user3 = new UserDto(1, "user3");
    private List<UserDto> userList = new ArrayList<>();


    private TEnmoController sut;
    private UserDao userDao;
    private TransactionDao transactionDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDao = new JdbcUserDao(jdbcTemplate);
        transactionDao = new JdbcTransactionDao();
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

}
