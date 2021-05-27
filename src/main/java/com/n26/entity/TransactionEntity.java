package com.n26.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class TransactionEntity {

	private BigDecimal amount = null;
	private ZonedDateTime timestamp = null; 

    public BigDecimal getAmount () {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

}