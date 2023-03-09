package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@PreAuthorize("isAuthenticated()")
public class TEnmoController {
    private UserDao userDao;
    private TransactionDao transactionDao;

    public TEnmoController(UserDao userDao, TransactionDao transactionDao) {
        this.userDao = userDao;
        this.transactionDao = transactionDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<UserDto> listUsers(){
        List<User> users = userDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user.getId(), user.getUsername()));
        }
        return userDtos;
    }


    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@RequestParam String userName, @PathVariable("id") int userId){
        return userDao.findByUsername(userName).getBalance();
    }

    @RequestMapping(path = "/users/{id}/transfers", method = RequestMethod.GET)
    public List<Transaction> listTransactionsOfUser(@PathVariable("id") int userId){
        User currentUser = userDao.getUserById(userId);
        return transactionDao.listTransaction(currentUser.getId());

    }

    @RequestMapping(path = "/users/{userid}/transfers/{transferid}", method = RequestMethod.GET)
    public Transaction getTransactionDetails(@PathVariable("userid") int userId, @PathVariable("transferid") int transferId){
        User currentUser = userDao.getUserById(userId);
        return transactionDao.getTransaction(transferId);

    }


    @RequestMapping(path = "/users/{id}/", method = RequestMethod.POST)
    public void transfer(int fromUserId, int toUserId, BigDecimal amount){
        Transaction transaction = new Transaction(fromUserId, toUserId, amount, "Approved");
        User fromUser = userDao.getUserById(fromUserId);
        User toUser = userDao.getUserById(toUserId);
        if (userDao.getUserById(fromUserId).getBalance().compareTo(amount) >= 0) {
            BigDecimal newBalance = fromUser.getBalance().subtract(amount);
            updateBalance(fromUserId, newBalance);
            transactionDao.createTransaction(transaction);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient account balance.");
        }
    }

    public void updateBalance(int userId, BigDecimal amount){
        User user = userDao.getUserById(userId);
        user.setBalance(amount);
        userDao.updateUser(user);
    }
}
