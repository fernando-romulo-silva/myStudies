package br.com.fernando.ch08_refactoring_testing_and_debugging.part03_Testing_lambdas;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

// Testing lambdas
//
// You’ve now sprinkled your code with lambda expressions, and it looks nice and concise. But in
// most developer jobs you’re not paid for writing nice code but for writing code that’s correct.
// Generally, good software engineering practice involves using unit testing to ensure that your
// program behaves as intended. You write test cases, which assert that small individual parts of
// your source code are producing the expected results.
public class Test {

    public static void test0() {

	// The following unit test checks whether the method moveRightBy behaves as expected:

	final Point p1 = new Point(5, 5);
	final Point p2 = p1.moveRightBy(10);

	if (15 != p2.getX()) {
	    System.out.println("Error X!");
	}

	if (5 != p2.getY()) {
	    System.out.println("Error Y!");
	}
    }

    // Testing the behavior of a visible lambda
    public static void test1() {

	// This works nicely because the method moveRightBy is public. Therefore, it can be tested inside
	// the test case. But lambdas don’t have a name (they’re anonymous functions, after all), so it’s
	// trickier to test them in your code because you can’t refer to them by a name!
	//
	// Sometime you may have access to a lambda via a field so you can reuse it, and you’d really like to
	// test the logic encapsulated in that lambda.
	// What can you do? You could test the lambda just like when calling methods.

	final Point p1 = new Point(10, 15);
	final Point p2 = new Point(10, 20);

	final int result = Point.compareByXAndThenY.compare(p1, p2);

	if (-1 != result) {
	    System.out.println("Error Comparing!");
	}
    }

    // Focusing on the behavior of the method using a lambda
    public static void test2() {

	// But the purpose of lambdas is to encapsulate a one-off piece of behavior to be used by another
	// method. In that case you shouldn’t make lambda expressions available publicly; they’re only an
	// implementation detail. Instead, we argue that you should test the behavior of the method that
	// uses a lambda expression.

	final List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));

	final List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));

	final List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);

	if (!expectedPoints.equals(newPoints)) {
	    System.out.println("Error  !");
	}
    }

    // Testing high-order functions
    public static void test4() {

	// Methods that take a function as argument or return another function (so-called higher-order
	// functions, explained more in chapter 14) are a little harder to deal with. One thing you can do if
	// a method takes a lambda as argument is test its behavior with different lambdas. For example,
	// you can test the filter method created in chapter 2 with different predicates:

	final List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

	final List<Integer> even = filter(numbers, i -> i % 2 == 0);

	final List<Integer> smallerThanThree = filter(numbers, i -> i < 3);

	if (!Arrays.asList(2, 4).equals(even)) {
	    System.out.println("Error List Even!");
	}

	if (!Arrays.asList(1, 2).equals(smallerThanThree)) {
	    System.out.println("Error List smallerThanThree!");
	}

	// What if the method that needs to be tested returns another function? You can test the behavior
	// of that function by treating it as an instance of a functional interface, as we showed earlier with a
	// Comparator.

    }

    public static <T> List<T> filter(final List<T> numbers, Predicate<T> predicate) {
	return numbers.stream() //
	    .filter(predicate) //
	    .collect(toList());
    }

    private static class Point {

	public final static Comparator<Point> compareByXAndThenY = comparing(Point::getX).thenComparing(Point::getY);

	private final int x;

	private final int y;

	private Point(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	public int getX() {
	    return x;
	}

	public int getY() {
	    return y;
	}

	public Point moveRightBy(int x) {
	    return new Point(this.x + x, this.y);
	}

	public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
	    return points.stream() //
	        .map(p -> new Point(p.getX() + x, p.getY())) //
	        .collect(toList());
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + x;
	    result = prime * result + y;
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    final Point other = (Point) obj;
	    if (x != other.x)
		return false;
	    if (y != other.y)
		return false;
	    return true;
	}
    }

    // ==========================================================
    public static void main(String[] args) {
	test1();
    }
}
