package br.com.fernando.ch08_refactoring_testing_and_debugging.part02_Refactoring_object_oriented_design_patterns_with_lambdas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// Refactoring object-oriented design patterns with lambdas
public class Test4 {

    // Factory
    public static void test4() {
	// The factory design pattern lets you create objects without exposing the instantiation logic to the
	// client. For example, let’s say you’re working for a bank and they need a way of creating different
	// financial products: loans, bonds, stocks, and so on.
	// Here, Loan, Stock, and Bond are all subtypes of Product
	//
	// Normal Way
	final Product p1 = ProductFactory.createProduct("loan");

	// You saw in chapter 3 that you can refer to constructors just like you refer to methods, by using
	// method references. For example, here’s how to refer to the Loan constructor:
	final Supplier<Product> loanSupplier = Loan::new;
	final Product p2 = loanSupplier.get();

	final Product p3 = ProductFactory.createProductLambda("loan");

    }

    static class ProductFactory {

	// Normal Factory Method
	//
	// The createProduct method could have additional logic to configure each created product.
	// But the benefit is that you can now create these objects without exposing the constructor and the configuration to the client
	public static Product createProduct(String name) {
	    switch (name) {
		case "loan":
		    return new Loan();
		case "stock":
		    return new Stock();
		case "bond":
		    return new Bond();
		default:
		    throw new RuntimeException("No such product " + name);
	    }
	}

	// Lambda Factory Method
	public static Product createProductLambda(String name) {
	    final Supplier<Product> p = map.get(name);

	    if (p != null) {
		return p.get();
	    }

	    throw new RuntimeException("No such product " + name);
	}
    }

    static interface Product {
    }

    static class Loan implements Product {
    }

    static class Stock implements Product {
    }

    static class Bond implements Product {
    }

    final static Map<String, Supplier<Product>> map = new HashMap<>();

    static {
	map.put("loan", Loan::new);
	map.put("stock", Stock::new);
	map.put("bond", Bond::new);
    }
}
