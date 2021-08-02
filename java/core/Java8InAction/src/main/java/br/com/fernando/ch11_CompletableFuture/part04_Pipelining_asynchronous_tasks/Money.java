package br.com.fernando.ch11_CompletableFuture.part04_Pipelining_asynchronous_tasks;

public enum Money {
    USD(1.0), //

    EUR(1.35387), //

    GBP(1.69715), //

    CAD(.92106), //

    MXN(.07683);

    private final double rate;

    Money(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
