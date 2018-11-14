package com.n26.transaction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Transaction {

    private BigDecimal amount;

    private ZonedDateTime timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
