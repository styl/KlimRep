package com.example.twousers.models;

import java.math.BigDecimal;
import java.util.UUID;

public class User {
    private final UUID accountId = UUID.randomUUID();
    private BigDecimal balance = BigDecimal.ZERO;

    public UUID getAccountId() {
        return accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void plusBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public void minusBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                '}';
    }
}
