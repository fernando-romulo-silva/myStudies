package br.com.nandao.cap11ModeloPagamentos.part01;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Subscription {

    private final BigDecimal monthlyFee;
    private final LocalDateTime begin;
    private final Optional<LocalDateTime> end;
    private final Customer customer;

    public Subscription(BigDecimal monthlyFee, LocalDateTime begin, Customer customer) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.empty();
        this.customer = customer;
    }

    public Subscription(BigDecimal monthlyFee, LocalDateTime begin, LocalDateTime end, Customer customer) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.of(end);
        this.customer = customer;
    }

    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public Optional<LocalDateTime> getEnd() {
        return end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getTotalPaid() {
        return getMonthlyFee().multiply(new BigDecimal(ChronoUnit.MONTHS.between(getBegin(), getEnd().orElse(LocalDateTime.now()))));
    }

}
