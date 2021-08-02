package br.com.fernando.ch14_Functional_programming_techniques.part03_LazyEvaluationWithStreams;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Lazy evaluation with streams
public class Test {

    // Self-defining stream
    public static void test01() {
    }

    // This solution is somewhat awkward: you have to iterate through every number every time to
    // see if it can be exactly divided by a candidate number.
    public static Stream<Integer> primes(int n) {
	return Stream.iterate(2, i -> i + 1) //
	    .filter(Test::isPrime) //
	    .limit(n);
    }

    public static boolean isPrime(int candidate) {
	final int candidateRoot = (int) Math.sqrt(candidate);

	return IntStream.rangeClosed(2, candidateRoot) //
	    .noneMatch(i -> candidate % i == 0);
    }

    // Create a better solution
    public static IntStream primes2(int n) {
	return primes(numbers());
    }

    // Step 1: Get a stream of numbers - You need a stream of numbers from which you’ll select prime numbers.
    static IntStream numbers() {
	return IntStream.iterate(2, n -> n + 1);
    }

    // Step 2: Take the head - From that stream you take the first number (the head of the stream), which will be a prime
    // number (at the initial step this will be 2).
    static int head(IntStream numbers) {
	return numbers.findFirst().getAsInt();
    }

    // Step 3: Filter the tail - You then filter all the numbers divisible by that number from the tail of the stream
    static IntStream tail(IntStream numbers) {
	return numbers.skip(1);
    }

    // Step 4: Recursively create a stream of primes - The resulting tail is the new stream of numbers that you can use
    // to find prime numbers. Essentially you go back to step 1, so this algorithm is recursive.
    static IntStream primes(IntStream numbers) {

	final int head = head(numbers);

	return IntStream.concat( //
	                         IntStream.of(head), //
	                         primes(tail(numbers).filter(n -> n % head != 0)));
    }

    // Bad news
    // Unfortunately, if you run the code in step 4, you’ll get the following error: “java.lang.IllegalStateException: stream has already been operated upon or closed.”
    // Indeed, you’re using two terminal operations to split the stream into its head and tail: findFirst and skip. Remember from chapter 4 that once you call a terminal
    // operation on a stream, it’s consumed forever!

    // ===================================================================================================
    // Your own lazy list
    public static void test02() {
	// Java 8 streams are often described as lazy. They’re lazy in one particular aspect: a stream
	// behaves like a black box that can generate values on request. When you apply a sequence of
	// operations to a stream, these are merely saved up. Only when you apply a terminal operation to
	// a stream is anything actually computed

	final MyList<Integer> l = new MyLinkedList<>(5, new MyLinkedList<>(10, new Empty<Integer>()));

	System.out.println(l.head());

	LazyList<Integer> numbers = from(2);
	final int two = numbers.head();
	final int three = numbers.tail().head();
	final int four = numbers.tail().tail().head();
	System.out.println(two + " " + three + " " + four);

	numbers = from(2);
	final int prime_two = primes(numbers).head();
	final int prime_three = primes(numbers).tail().head();
	final int prime_five = primes(numbers).tail().tail().head();
	System.out.println(prime_two + " " + prime_three + " " + prime_five);

	// this will run until a stackoverflow occur because Java does not
	// support tail call elimination
	// printAll(primes(from(2)));

	// Our guideline is to remember that lazy data structures can be a useful weapon in your
	// programming armory. Use them when they make an application easier to program. Rewrite
	// them in more traditional style only if they cause unacceptable inefficiency

    }

    static LazyList<Integer> from(int n) {
	return new LazyList<>(n, () -> from(n + 1));
    }

    // See if you can use what you’ve done so far to generate a self-defining lazy list of prime numbers
    // (something you were unable to do with the Streams API). If you were to translate the code that
    // was using the Streams API earlier using our new LazyList, it would look like something like this:
    public static MyList<Integer> primes(MyList<Integer> numbers) {
	return new LazyList<>( //
	                       numbers.head(), //
	                       () -> primes( //
	                                     numbers.tail() //
	                                         .filter(n -> n % numbers.head() != 0) //
			       ) //
	);
    }

    static <T> void printAll(MyList<T> numbers) {
	if (numbers.isEmpty()) {
	    return;
	}

	System.out.println(numbers.head());
	printAll(numbers.tail());
    }

    static interface MyList<T> {

	T head();

	MyList<T> tail();

	default boolean isEmpty() {
	    return true;
	}

	MyList<T> filter(Predicate<T> p);
    }

    static class MyLinkedList<T> implements MyList<T> {

	final T head;

	final MyList<T> tail;

	public MyLinkedList(T head, MyList<T> tail) {
	    this.head = head;
	    this.tail = tail;
	}

	@Override
	public T head() {
	    return head;
	}

	@Override
	public MyList<T> tail() {
	    return tail;
	}

	@Override
	public boolean isEmpty() {
	    return false;
	}

	@Override
	public MyList<T> filter(Predicate<T> p) {
	    return isEmpty() ? //
	                     this : //
	                     p.test(head()) ? //
	                                    new MyLinkedList<>(head(), tail().filter(p)) : //
	                                    tail().filter(p);
	}
    }

    static class Empty<T> implements MyList<T> {

	@Override
	public T head() {
	    throw new UnsupportedOperationException();
	}

	@Override
	public MyList<T> tail() {
	    throw new UnsupportedOperationException();
	}

	@Override
	public MyList<T> filter(Predicate<T> p) {
	    return this;
	}
    }

    static class LazyList<T> implements MyList<T> {

	final T head;

	final Supplier<MyList<T>> tail;

	public LazyList(T head, Supplier<MyList<T>> tail) {
	    this.head = head;
	    this.tail = tail;
	}

	@Override
	public T head() {
	    return head;
	}

	@Override
	public MyList<T> tail() {
	    // Note how tail using a Supplier encodes laziness, comparared to head above
	    return tail.get();
	}

	@Override
	public boolean isEmpty() {
	    return false;
	}

	@Override
	public MyList<T> filter(Predicate<T> p) {
	    return isEmpty() ? //
	                     this : // You could return new Empty<>() but this is just as good and empty.
	                     p.test(head()) ? //
	                                    new LazyList<>(head(), () -> tail().filter(p)) : //
	                                    tail().filter(p);
	}
    }

    // =============================================================================================================
    public static void main(String[] args) {
	test02();
    }

}
