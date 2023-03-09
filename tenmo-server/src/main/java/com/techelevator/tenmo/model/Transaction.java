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
    private int transferStatus;

    @NotBlank(message = "The transfer type cannot be blank")
    private int transferTypeId;

    public Transaction () {
    }

    public Transaction(int fromUserId, int toUserId, BigDecimal amount, int transferStatus, int transferTypeId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.transferStatus = transferStatus;
        this.transferTypeId = transferTypeId;
    }

    public Transaction(int transactionId, int fromUserId, int toUserId, BigDecimal amount, int transferStatus, int transferTypeId) {
        this.transactionId = transactionId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.transferStatus = transferStatus;
        this.transferTypeId = transferTypeId;
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

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    @Override
        public String toString() {
            return "Transfer{" + "FromUserId=" + fromUserId + ", ToUserId = " + toUserId +", amount= " + amount + ", TransferType= " + transferTypeId + ", TransferStatus= " + transferStatus + '}';
        }
}
