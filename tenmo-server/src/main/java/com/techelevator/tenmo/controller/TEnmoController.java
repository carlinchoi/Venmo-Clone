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

import javax.validation.Valid;
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
        if (userDtos.size() > 0) {
            return userDtos;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found. This should not be possible");
        }
    }


    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable("id") int userId){
        BigDecimal balance = userDao.getUserById(userId).getBalance();
        if(balance != null) {
            return balance;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No balance found. This should not be possible.");
        }
    }

    @RequestMapping(path = "/users/{id}/transfers", method = RequestMethod.GET)
    public List<Transaction> listTransactionsOfUser(@PathVariable("id") int userId){
        User currentUser = userDao.getUserById(userId);
        List<Transaction> transactions = transactionDao.listTransaction(currentUser.getId());
        if (transactions != null) {
            return transactions;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No transactions found.");
        }

    }

    @RequestMapping(path = "/users/transfers/{transferid}", method = RequestMethod.GET)
    public Transaction getTransactionDetails(@PathVariable("transferid") int transferId){
        Transaction transaction = transactionDao.getTransaction(transferId);
        if (transaction != null){
            return transaction;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such transaction found.");
        }
    }


    @RequestMapping(path = "/users/{id}", method = RequestMethod.POST)
    public Transaction transfer(@Valid @RequestBody Transaction transaction){
        BigDecimal amount = transaction.getAmount();
        Transaction returnedTransaction = null;
        User fromUser = userDao.getUserById(transaction.getFromUserId());
        User toUser = userDao.getUserById(transaction.getToUserId());
        if (toUser.getId() == fromUser.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't send to yourself.");
        }else if(amount.compareTo(new BigDecimal("0")) <=0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't send 0 or less.");
        } else if (toUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receiving account not found.");
        }else if(userDao.getUserById(transaction.getFromUserId()).getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance.");
        } else {
            BigDecimal newBalance = fromUser.getBalance().subtract(amount);
            updateBalance(transaction.getFromUserId(), newBalance);
            updateBalance(transaction.getToUserId(), toUser.getBalance().add(amount));
            returnedTransaction = transactionDao.createTransaction(transaction);
        }
        return returnedTransaction;
    }

    public void updateBalance(int userId, BigDecimal amount){
        User user = userDao.getUserById(userId);
        user.setBalance(amount);
        userDao.updateUser(user);

    }
}
