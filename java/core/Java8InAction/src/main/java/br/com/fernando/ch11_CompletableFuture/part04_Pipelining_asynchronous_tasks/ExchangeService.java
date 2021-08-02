package br.com.fernando.ch11_CompletableFuture.part04_Pipelining_asynchronous_tasks;

public class ExchangeService {

    public static double getRate(Money source, Money destination) {
	return getRateWithDelay(source, destination);
    }

    private static double getRateWithDelay(Money source, Money destination) {
	Util.delay();
	return destination.getRate() / source.getRate();
    }
}
