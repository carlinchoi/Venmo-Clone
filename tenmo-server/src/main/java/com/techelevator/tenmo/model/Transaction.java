package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class Transaction {
    private int transactionId;

    @NotBlank(message = "The from user ID cannot be blank")
    private int fromUserId;

    @NotBlank(message = "The to user ID cannot be blank")
    private int toUserId;

    @Positive(message = "The amount must be greater than 0.")
    private BigDecimal amount;

    @NotBlank(message = "The transfer status cannot be blank")
    private String transferStatus;

    public Transaction () {
    }

    public Transaction(int fromUserId, int toUserId, BigDecimal amount, String transferStatus) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.transferStatus = transferStatus;
    }

    public Transaction(int transactionId, int fromUserId, int toUserId, BigDecimal amount, String transferStatus) {
        this.transactionId = transactionId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.transferStatus = transferStatus;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    @Override
        public String toString() {
            return "Auction{" + "FromUserId=" + fromUserId + "ToUserId = " + toUserId +", amount= " + amount + '\'' + ", TransferStatus= " + transferStatus + '}';
        }
}
