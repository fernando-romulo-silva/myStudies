package br.com.nandao.cap11ModeloPagamentos.part01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Payment {
    private final List<Product> products;
    private final LocalDateTime date;
    private final Customer customer;

    public Payment(List<Product> products, LocalDateTime date, Customer customer) {
        this.products = Collections.unmodifiableList(products);
        this.date = date;
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public String toString() {
        return "[Payment: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " + customer + " " + products + "]";
    }
}
