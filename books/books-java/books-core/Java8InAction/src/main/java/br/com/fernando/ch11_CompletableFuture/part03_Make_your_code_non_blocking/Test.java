package br.com.fernando.ch11_CompletableFuture.part03_Make_your_code_non_blocking;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// Make your code non-blocking
public class Test {

    public static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), //
                                                   new Shop("LetsSaveBig"), //
                                                   new Shop("MyFavoriteShop"), //
                                                   new Shop("BuyItAll")); //

    // Create a thread pool with a numbers of threads equal to the minimun between 100 and the number of shops
    private static final Executor EXECUTOR1 = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {

	@Override
	public Thread newThread(Runnable r) {
	    final Thread t = new Thread(r);
	    t.setDaemon(true);
	    return t;
	}

    });

    // Create a thread pool with a numbers of threads equal to the number of shops
    private static final Executor EXECUTOR2 = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {

	@Override
	public Thread newThread(Runnable r) {
	    final Thread t = new Thread(r);
	    // Use daemon threads - they don't prevent the termination of the program
	    t.setDaemon(true);
	    return t;
	}
    });

    public static void test01() {

	// Our advice for using these APIs is as follows:
	//
	//  If you’re doing computation-heavy operations with no I/O, then the Stream interface gives the
	// simplest implementation and one likely to be the most efficient (if all threads are compute-bound,
	// then there’s no point in having more threads than processor cores).
	//
	//  On the other hand, if your parallel units of work involve waiting for I/O (including network
	// connections), then CompletableFutures give more flexibility and the ability to match the number of
	// threads to the wait/computer, or W/C, ratio as discussed previously. Another reason to avoid using
	// parallel streams when I/O waits are involved in the stream-processing pipeline is that the laziness of
	// streams can make it harder to reason about when the waits actually happen.

	execute("sequential", () -> findPricesSequential("myPhone27S"));
	System.out.println("===============================================================================================");
	execute("parallel", () -> findPricesParallel("myPhone27S"));
	System.out.println("===============================================================================================");
	execute("composed CompletableFuture", () -> findPricesFuture("myPhone27S"));
	System.out.println("===============================================================================================");
	execute("composed CompletableFuture with Custom Executor1", () -> findPricesFutureWithCustomExecutor1("myPhone27S"));
	System.out.println("===============================================================================================");
	execute("composed CompletableFuture with Custom Executor2", () -> findPricesFutureWithCustomExecutor2("myPhone27S"));
	System.out.println("===============================================================================================");
    }

    // A findPrices implementation sequentially querying all the shops
    public static List<String> findPricesSequential(String product) {
	return shops.stream() //
	    .map(shop -> shop.getName() + " price is " + shop.getPrice(product)) //
	    .collect(Collectors.toList());
    }

    // Parallelizing the findPrices method
    public static List<String> findPricesParallel(String product) {
	return shops.parallelStream() // Use a parallel Stream to reieve the prices from the different shops in parallel.
	    .map(shop -> shop.getName() + " price is " + shop.getPrice(product)) //
	    .collect(Collectors.toList());
    }

    // Implementing the findPrices method with CompletableFutures
    public static List<String> findPricesFuture(String product) {

	// Using this approach, you obtain a List<CompletableFuture<String>>, where each
	// CompletableFuture in the List will contain the String name of a shop when its
	// computation is completed.
	final List<CompletableFuture<String>> priceFutures = shops.stream()//
	    .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product))) //
	    .collect(Collectors.toList());

	// To achieve this result, you can apply a second map operation to the original
	// List<CompletableFuture<String>>, invoking a join on all the futures in the List and then
	// waiting for their completion one by one
	final List<String> prices = priceFutures.stream() //
	    .map(CompletableFuture::join) //
	    .collect(Collectors.toList());

	return prices;
    }

    // Implementing the findPrices method with CompletableFutures with custom Executor1
    public static List<String> findPricesFutureWithCustomExecutor1(String product) {

	// In particular, Goetz suggests that the right pool size to approximate a desired
	// CPU utilization rate can be calculated with the following formula:
	//
	// Nthreads = NCPU * UCPU * (1 + W/C)
	//
	// where
	//  NCPU is the number of cores, available through Runtime.getRuntime().availableProcessors()
	//  UCPU is the target CPU utilization (between 0 and 1), and
	//  W/C is the ratio of wait time to compute time

	final List<CompletableFuture<String>> priceFutures = shops.stream()//
	    .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), EXECUTOR1)) //
	    .collect(Collectors.toList());

	final List<String> prices = priceFutures.stream() //
	    .map(CompletableFuture::join) //
	    .collect(Collectors.toList());

	return prices;
    }

    // Implementing the findPrices method with CompletableFutures with custom Executor2
    public static List<String> findPricesFutureWithCustomExecutor2(String product) {

	// This executor only use numbers of threads equal to the number of shops
	final List<CompletableFuture<String>> priceFutures = shops.stream()//
	    .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), EXECUTOR2)) //
	    .collect(Collectors.toList());

	final List<String> prices = priceFutures.stream() //
	    .map(CompletableFuture::join) //
	    .collect(Collectors.toList());

	return prices;
    }

    // =====================================================================================================

    public static void main(String[] args) {
	test01();
    }

    private static void execute(String msg, Supplier<List<String>> s) {
	final long start = System.nanoTime();
	System.out.println(s.get());
	final long duration = (System.nanoTime() - start) / 1_000_000;
	System.out.println(msg + " done in " + duration + " msecs");
    }

    static class Shop {

	private final String name;

	private final Random random;

	public Shop(String name) {
	    this.name = name;
	    random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	public double getPrice(String product) {
	    System.out.println("Shop " + name);

	    return calculatePrice(product);
	}

	private double calculatePrice(String product) {
	    Util.delay();
	    return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}

	public String getName() {
	    return name;
	}
    }
}
