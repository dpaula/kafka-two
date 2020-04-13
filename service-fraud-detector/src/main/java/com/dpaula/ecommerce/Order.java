package com.dpaula.ecommerce;

import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Fernando de Lima
 */
@ToString
public class Order {

    private final String userId;
    private final String orderId;
    private final BigDecimal amount;

    public Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }
}
