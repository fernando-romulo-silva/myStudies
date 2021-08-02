package br.com.fernando.ch08_refactoring_testing_and_debugging.part02_Refactoring_object_oriented_design_patterns_with_lambdas;

import java.util.ArrayList;
import java.util.List;

// Refactoring object-oriented design patterns with lambdas
public class Test2 {

    // Observer
    // The observer design pattern is a common solution when an object (called the subject) needs to
    // automatically notify a list of other objects (called observers) when some event happens (for example, a state change).
    public static void test3() {

	// It’s a pretty straightforward implementation: the feed keeps an internal list of observers that it
	// can then notify when a tweet arrives. You can now create a demo application to wire up the
	// subject and observers:
	final Feed feed = new Feed();

	feed.registerObserver(new NYTimes());
	feed.registerObserver(new Guardian());
	feed.registerObserver(new LeMonde());

	// notify
	feed.notifyObservers("The queen said her favourite book is Java 8 in Action!");

	//
	// Using lambda expressions
	final Feed feedLambda = new Feed();
	
	// By now you should recognize that ValidationStrategy is a functional interface (in addition, it has
	// the same function descriptor as Predicate<String>). This means that instead of declaring new
	// classes to implement different strategies, you can pass lambda expressions directly, which are
	// more concise:

	feedLambda.registerObserver((String tweet) -> {
	    if (tweet != null && tweet.contains("money")) {
		System.out.println("Breaking news in NY! " + tweet);
	    }
	});

	feedLambda.registerObserver((String tweet) -> {
	    if (tweet != null && tweet.contains("queen")) {
		System.out.println("Yet another news in London... " + tweet);
	    }
	});

	feedLambda.notifyObservers("Money money money, give me money!");
    }

    // First, you need an Observer interface that groups the different observers. It has just one method
    // called notify that will be called by the subject (Feed) when a new tweet is available:
    static interface Observer {

	void notify(String tweet);
    }

    // You can now declare different observers (here, the three newspapers) that produce a different
    // action for each different keyword contained in a tweet:
    static class NYTimes implements Observer {

	@Override
	public void notify(String tweet) {
	    if (tweet != null && tweet.contains("money")) {
		System.out.println("Breaking news in NY! " + tweet);
	    }
	}
    }

    static class Guardian implements Observer {

	@Override
	public void notify(String tweet) {
	    if (tweet != null && tweet.contains("queen")) {
		System.out.println("Yet another news in London... " + tweet);
	    }
	}
    }

    static class LeMonde implements Observer {

	@Override
	public void notify(String tweet) {
	    if (tweet != null && tweet.contains("wine")) {
		System.out.println("Today cheese, wine and news! " + tweet);
	    }
	}
    }

    // You’re still missing the crucial part: the subject! Let’s define an interface for him:
    static interface Subject {

	void registerObserver(Observer o);

	void notifyObservers(String tweet);
    }

    // The subject can register a new observer using the registerObserver method and notify his
    // observers of a tweet with the notifyObservers method. Let’s go ahead and implement the Feed class:
    static class Feed implements Subject {

	private final List<Observer> observers = new ArrayList<>();

	@Override
	public void registerObserver(Observer o) {
	    this.observers.add(o);
	}

	@Override
	public void notifyObservers(String tweet) {
	    observers.forEach(o -> o.notify(tweet));
	}
    }

    // =================================================================================================================
    public static void main(String[] args) {
	test3();
    }

}
