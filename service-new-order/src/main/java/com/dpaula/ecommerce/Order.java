package com.dpaula.ecommerce;

import java.math.BigDecimal;

/**
 * @author Fernando de Lima
 */
public class Order {

    private final String userId;
    private final String orderId;
    private final BigDecimal amount;
    private final String email;

    public Order(String userId, String orderId, BigDecimal amount, String email) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }
}
