package br.com.fernando.ch11_CompletableFuture.part05_Reacting_to_a_CompletableFuture_completion;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

import br.com.fernando.ch11_CompletableFuture.part05_Reacting_to_a_CompletableFuture_completion.Test.Discount.Code;

// Reacting to a CompletableFuture completion
class Test {

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

        final long start = System.nanoTime();

        @SuppressWarnings("rawtypes")
	final CompletableFuture[] futures = findPricesStream("myPhone27S") //
            .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)"))) //
            .toArray(size -> new CompletableFuture[size]);

        // The allOf factory method takes as input an array of CompletableFutures and returns a
        // CompletableFuture<Void> that’s completed only when all the CompletableFutures passed have
        // completed. This means that invoking join on the CompletableFuture returned by the allOf
        // method provides an easy way to wait for the completion of all the CompletableFutures in the
        // original stream.
        CompletableFuture.allOf(futures).join();

        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream() //
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceDescription(product), EXECUTOR)) //
            .map(future -> future.thenApply(Quote::parse)) //
            .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), EXECUTOR))); //
    }

    // =====================================================================================================

    public static void main(String[] args) {
        test01();
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
            // Util.randomDelay();
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

        public static String applyDiscount(Quote quote) {
            return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
        }

        private static double apply(double price, Code code) {
            Util.randomDelay();
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
