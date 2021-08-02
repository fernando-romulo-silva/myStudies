package br.com.fernando.ch11_CompletableFuture.part02_Implementing_an_asynchronous_API;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Test {

    // Implementing an asynchronous API
    public static void test01() {

	// Using an asynchronous API
	final Shop shop = new Shop("BestShop");

	final long start = System.nanoTime();

	// Query the shop to retrieve the price of a product
	final Future<Double> futurePrice = shop.getPriceAsync("my favorite product");

	final long invocationTime = (System.nanoTime() - start) / 1_000_000;

	System.out.println("Invocation returned after:" + invocationTime + " msecs");

	// Do some more tasks, like querying ohter shops

	doSomethingElse();

	// while the pric of the product is being calculated

	try {
	    // It’s a good practice to always use a timeout to avoid similar situations elsewhere in your code.
	    // This way the client will at least avoid waiting indefinitely, but when the timeout expires,
	    // it will just be notified with a TimeoutException. As a consequence, it won’t have
	    // a chance to discover what really caused that failure inside the thread that was trying to calculate
	    // the product price.

	    final double price = futurePrice.get();

	    System.out.printf("Prisce is %.2f%n", price);

	} catch (final Exception ex) {
	    ex.printStackTrace();
	}

	final long retrievalTime = (System.nanoTime() - start) / 1_000_000;

	System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    // =====================================================================================================

    public static void main(String[] args) {
	test01();
    }

    private static void doSomethingElse() {
	System.out.println("Starts doSomethingElse");

	Util.delay(2000);

	System.out.println("Ends doSomethingElse");
    }

    static class Shop {

	private final String name;

	private final Random random;

	public Shop(String name) {
	    this.name = name;
	    random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	// synchronous getPrice API
	public double getPrice(final String product) {
	    return calculatePrice(product);
	}

	// asynchronous getPrice API
	public Future<Double> getPriceAsync(final String product) {

	    System.out.println("Starts getPriceAsync");

	    // Create the 'CompletableFuture' that will contain the result of the computation
	    final CompletableFuture<Double> futurePrice = new CompletableFuture<>();

	    new Thread(() -> { //

		try {

		    // Execute the computation asynchronously
		    final double price = calculatePrice(product);

		    // Set the value returned by the long computation on the 'Future' when it becomes available
		    futurePrice.complete(price); // if the price calculation completed normally, complete the 'Future' with the price
		} catch (final Exception ex) {
		    // Dealing with errors
		    // Otherwise, complete it exceptionally with 'Exception' that caused the failure.
		    futurePrice.completeExceptionally(ex);
		}

	    }).start();

	    System.out.println("Ends getPriceAsync");

	    // Return the 'Future' without waiting for the computation of the result it contains to be completed
	    return futurePrice;

	}

	// Creating a CompletableFuture with the supplyAsync factory method
	public Future<Double> getPriceAsyncWithFactoryMethod(final String product) {
	    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}

	private double calculatePrice(String product) {

	    // A method to simulate a 1-second delay
	    Util.delay(6000);

	    // Formatter the value
	    return Util.format(random.nextDouble() * product.charAt(0) + product.charAt(1));
	}

	public String getName() {
	    return name;
	}
    }
}
