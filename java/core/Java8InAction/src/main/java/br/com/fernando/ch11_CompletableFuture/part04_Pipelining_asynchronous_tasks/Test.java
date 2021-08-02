package br.com.fernando.ch11_CompletableFuture.part04_Pipelining_asynchronous_tasks;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import br.com.fernando.ch11_CompletableFuture.part04_Pipelining_asynchronous_tasks.Test.Discount.Code;

// Pipelining asynchronous tasks
public class Test {

    public static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), //
                                                   new Shop("LetsSaveBig"), //
                                                   new Shop("MyFavoriteShop"), //
                                                   new Shop("BuyItAll")); //

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            final Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public static void test01() {
        execute("findPricesSimplest", () -> findPricesSimplest("myPhone27S"));
        System.out.println("===============================================================================================");
        execute("findPricesParallel", () -> findPricesParallel("myPhone27S"));
        System.out.println("===============================================================================================");
        execute("findPricesCompletableFuture", () -> findPricesCompletableFuture("myPhone27S"));
        System.out.println("===============================================================================================");
        execute("findPricesInUSD", () -> findPricesInUSD("myPhone27S"));
        System.out.println("===============================================================================================");
        execute("findPricesInUSDJava7", () -> findPricesInUSDJava7("myPhone27S"));
    }

    // Simplest findPrices implementation that uses the Discount service
    public static List<String> findPricesSimplest(final String product) {

        // The desired result is obtained by pipelining three map operations on the stream of shops:
        //
        //  The first operation transforms each shop into a String that encodes the price and discount code of the
        // requested product for that shop.
        //
        //  The second operation parses those Strings, converting each of them in a Quote object.
        //
        //  Finally, the third one contacts the remote Discount service that will calculate the final discounted
        // price and return another String containing the name of the shop with that price.

        return shops.stream() //
            .map(shop -> shop.getPriceDescription(product)) // Retrieve the nondiscounted price from each shop.
            .map(Quote::parse) // Treansform the Strings returned by the shops in Quote objecs.
            .map(Discount::applyDiscount) // Contact the Discount service to apply the discount on each Quote.
            .collect(toList());
    }

    // Parallel version's findPrices implementation that uses the Discount service
    public static List<String> findPricesParallel(final String product) {

        return shops.parallelStream() //
            .map(shop -> shop.getPriceDescription(product)) //
            .map(Quote::parse) //
            .map(Discount::applyDiscount) //
            .collect(toList());
    }

    // Composing sunchronous and asunchronous operations
    public static List<String> findPricesCompletableFuture(final String product) {

        final List<CompletableFuture<String>> priceFutures = shops.stream() //
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceDescription(product), EXECUTOR)) // Asynchronously retrieve the nondiscounted price from each shop.
            .map(future -> future.thenApply(Quote::parse)) // Transform the String returned by a shop into a Quote object when it becomes available.
            .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), EXECUTOR))) // Compose the resulting Future with another asysnchronous task, applying the discount code.
            .collect(toList());

        // Note that using the thenApply method doesn’t block your code until the Completable-Future on
        // which you’re invoking it is completed.
        //
        // The thenCompose method specifically for this purpose, allowing you to pipeline two asynchronous
        // operations, passing the result of the first operation to the second operation when it becomes available
        // At this point you have two asynchronous operations, modeled with two distinct CompletableFutures, that you want to perform in a cascade:
        //  Retrieve the price from a shop and then transform it into a Quote
        //  Take this Quote and pass it to the Discount service to obtain the final discounted price
        //
        // In general, a method without the Async suffix in its name executes its task in the same thread as
        // the previous task, whereas a method terminating with Async always submits the succeeding task
        // to the thread pool, so each of the tasks can be handled by a different thread. In this case, the
        // result of the second CompletableFuture depends on the first, so it makes no difference to the
        // final result or to its broad-brush timing whether you compose the two CompletableFutures with
        // one or the other variant of this method. We chose to use the one with thenCompose only
        // because it’s slightly more efficient due to less thread-switching overhead.

        return priceFutures.stream() //
            .map(CompletableFuture::join) // wait for all the Futures in the stream to be completed and extrac their respective results.
            .collect(toList());
        
        
        // @formatter:off
        // Your Thread   | Executor Thread1             
        //               |                              
        //        Shop   |                              
        //               | supplyAsync()  task1          
        //               | shop.getPrice                
        //               | thenApply                  
        //               |                              
        //               |       |
        //               |       V
        //               |                              
        //               | new Quote(price)
        //               |                              
        //               | thenCompose
        //               |                              
        //               | applyDiscount(quote)  task2
        //               |
        //               | join                           
        //               |                                    
        //        price  |                              
        // @formatter:on         
    }
    
    // Combining two CompletableFutures—dependent and independent
    public static List<String> findPricesInUSD(String product) {
        
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
        
        for (Shop shop : shops) {
            // Start of Listing 10.20.
            // Only the type of futurePriceInUSD has been changed to
            // CompletableFuture so that it is compatible with the
            // CompletableFuture::join operation below.
            CompletableFuture<Double> futurePriceInUSD = // Create a first taks querying the shop to obtain the price of a product.
                CompletableFuture.supplyAsync(() -> shop.getPrice(product)) //
                .thenCombine( //
                    CompletableFuture.supplyAsync(() ->  ExchangeService.getRate(Money.EUR, Money.USD)), // Create a second independent task to retrieve the conversion rate between USD and EUR
                    (price, rate) -> price * rate // Combine the price and exchange rate by multiplying them.
                );
            
            priceFutures.add(futurePriceInUSD);
        }
        
        // Drawback: The shop is not accessible anymore outside the loop,
        // so the getName() call below has been commented out.
        List<String> prices = priceFutures.stream() //
                .map(CompletableFuture::join) //
                .map(price -> /*shop.getName() +*/ " price is " + price)
                .collect(toList());
        
        // @formatter:off
        // Your Thread   | Executor Thread1              | Executor Thread2
        //               |                               | 
        //        Shop   |                               |
        //               |                               |         
        //               | supplyAsync (task1)           | 
        //               | shop.getPrice                 | 
        //               | thenCombine                   | supplyAsync (task2)               
        //               |                               | ExchangeService.getRate(EUR, USD) 
        //               |                               | thenCombine                       
        //               |                               | 
        //               | (price, rate) -> price * rate |
        //               |                               |
        //               | join                          |
        //               |                               |
        //        price  |                               |
        // @formatter:on 
        return prices;
    } 
    
    // Reflecting on Future vs. CompletableFuture
    // CompletableFutures use lambda expressions to provide a declarative API that offers the possibility of easily defining
    // a recipe that combines and composes different synchronous and asynchronous tasks to perform a complex operation 
    // in the most effective way.
    //
    //
    // Combining two Futures in Java 7
    public static List<String> findPricesInUSDJava7(String product) {

        // Create an ExecutorService allowing you to submit tasks to a thread pool.
        final ExecutorService executor = Executors.newCachedThreadPool();

        final List<Future<Double>> priceFutures = new ArrayList<>();

        for (final Shop shop : shops) {

            final Future<Double> futureRate = executor.submit(new Callable<Double>() {

                @Override
                public Double call() {
                    // Create a 'Future' retrieving the exchange rate between EUR and USD.
                    return ExchangeService.getRate(Money.EUR, Money.USD);
                }
            });

            final Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {

                @Override
                public Double call() {
                    try {
                        // Find the price of the requested product for a viven shop in a second 'Future'
                        final double priceInEUR = shop.getPrice(product);
                        // Multiply the price and exchange rate in the same 'Future' used to find the price
                        return priceInEUR * futureRate.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            });

            priceFutures.add(futurePriceInUSD);
        }

        final List<String> prices = new ArrayList<>();
        for (final Future<Double> priceFuture : priceFutures) {
            try {
                prices.add(/* shop.getName() + */ " price is " + priceFuture.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
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

        public String getPriceDescription(String product) {
            final double price = calculatePrice(product);
            final Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
            return String.format("%s:%s:%s", name, Util.format(price), code);
        }
        
        public Double getPrice(String product) {
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

    static class Discount {

        static enum Code {
            NONE(0), //

            SILVER(5), //

            GOLD(10), //

            PLATINUM(15), //

            DIAMOND(20); //

            private final int percentage;

            Code(int percentage) {
                this.percentage = percentage;
            }
        }

        // Now it return a string
        public static String applyDiscount(Quote quote) {

            // Apply the discount code to the original price
            return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
        }

        private static double apply(double price, Code code) {
            Util.delay(); // Simulate a delay in the Discount service response
            return Util.format(price * (100 - code.percentage) / 100);
        }
    }

    static class Quote {

        private final String shopName;

        private final double price;

        private final Code discountCode;

        public Quote(String shopName, double price, Code discountCode) {
            this.shopName = shopName;
            this.price = price;
            this.discountCode = discountCode;
        }

        public static Quote parse(String product) {
            final String[] split = product.split(":");
            final String shopName = split[0];
            final double price = Double.parseDouble(split[1]);
            final Code discountCode = Code.valueOf(split[2]);
            return new Quote(shopName, price, discountCode);
        }

        public String getShopName() {
            return shopName;
        }

        public double getPrice() {
            return price;
        }

        public Code getDiscountCode() {
            return discountCode;
        }
    }
}
