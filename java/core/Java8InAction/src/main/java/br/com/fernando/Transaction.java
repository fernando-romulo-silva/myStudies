package br.com.fernando;

public class Transaction {

    private final Trader trader;

    private final int year;

    private final double value;

    private final Currency currency;

    public Transaction(Currency currency, double value) {
        this(currency, null, 0, value);
    }

    public Transaction(Trader trader, int year, double value) {
        this(Currency.USD, null, 0, value);
    }

    public Transaction(Currency currency, Trader trader, int year, double value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
        this.currency = currency;
    }

    public Trader getTrader() {
        return this.trader;
    }

    public int getYear() {
        return this.year;
    }

    public double getValue() {
        return this.value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "{" + this.trader + ", " + "year: " + this.year + ", " + "value:" + this.value + "}";
    }
}
