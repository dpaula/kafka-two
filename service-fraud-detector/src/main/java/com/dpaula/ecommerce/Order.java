package com.dpaula.ecommerce;

import java.math.BigDecimal;

/**
 * @author Fernando de Lima
 */
public class Order {

    private final String userId;
    private final String orderId;
    private final BigDecimal amount;

    public Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
