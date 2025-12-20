package br.com.alura.ecommerce;

import java.math.BigDecimal;

public class Order {

    private final String orderId, email;

    private final BigDecimal amount;

    public Order(String orderId, BigDecimal amount, String email) {
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", amount=" + amount + "]";
    }
}
