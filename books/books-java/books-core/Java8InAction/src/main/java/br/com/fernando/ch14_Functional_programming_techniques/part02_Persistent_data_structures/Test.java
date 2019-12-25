package br.com.fernando.ch14_Functional_programming_techniques.part02_Persistent_data_structures;

import java.util.function.Consumer;

// Persistent data structures
public class Test {

    // The first thing to note is that a functional-style method isn’t allowed to update any global data
    // structure or any structure passed as a parameter. Why? Because calling it twice is likely to
    // produce different answers—violating referential transparency and the ability to understand the
    // method as a simple mapping from arguments to results.
    //
    // Destructive updates vs. functional
    public static void test01() {

	// The functional-style approach to this problem is to ban such side-effecting methods. If you need
	// a data structure to represent the result of a computation, you should make a new one and not
	// mutate an existing data structure as done previously. This is often best practice in standard
	// object-oriented programming too.

	final TrainJourney tj1 = new TrainJourney(40, new TrainJourney(30, "nowhere"), "Red");
	final TrainJourney tj2 = new TrainJourney(20, new TrainJourney(50, "nowhere"), "Yellow");

	final TrainJourney appended = append(tj1, tj2);
	visit(appended, tj -> {
	    System.out.print(tj.name + " - " + tj.price + " | ");
	});
	System.out.println();

	// A new TrainJourney is created without altering tj1 and tj2.
	final TrainJourney appended2 = append(tj1, tj2);
	visit(appended2, tj -> {
	    System.out.print(tj.name + " - " + tj.price + " | ");
	});
	System.out.println();

	// tj1 is altered but it's still not visible in the results.
	final TrainJourney linked = link(tj1, tj2);
	visit(linked, tj -> {
	    System.out.print(tj.name + " - " + tj.price + " | ");
	});
	System.out.println();

	// ... but here, if this code is uncommented, tj2 will be appended
	// at the end of the already altered tj1. This will cause a
	// StackOverflowError from the endless visit() recursive calls on
	// the tj2 part of the twice altered tj1.

	// TrainJourney linked2 = link(tj1, tj2);
	//
	// visit(linked2, tj -> {
	// System.out.print(tj.price + " - ");
	// });
	//
	// System.out.println();
	//
	//
    }

    public static void test02() {
	final Tree t = new Tree("Mary", 22, //
	                        new Tree("Emily", 20, //
	                                 new Tree("Alan", 50, null, null), //
	                                 new Tree("Georgie", 23, null, null)), //
	                        new Tree("Tian", 29, //
	                                 new Tree("Raoul", 23, null, null), //
	                                 null //
				));

	// found = 23
	System.out.println(lookup("Raoul", -1, t));
	// not found = -1
	System.out.println(lookup("Jeff", -1, t));

	final Tree f = functionalUpdate("Jeff", 80, t);
	// found = 80
	System.out.println(lookup("Jeff", -1, f));

	final Tree u = update("Jim", 40, t);
	// t was not altered by fupdate, so Jeff is not found = -1
	System.out.println(lookup("Jeff", -1, u));
	// found = 40
	System.out.println(lookup("Jim", -1, u));

	final Tree f2 = functionalUpdate("Jeff", 80, t);
	// found = 80
	System.out.println(lookup("Jeff", -1, f2));
	// f2 built from t altered by update() above, so Jim is still present = 40
	System.out.println(lookup("Jim", -1, f2));
    }

    public static void main(String[] args) {
	test02();
    }

    // =====================================================================================

    static class TrainJourney {

	private final int price;

	private TrainJourney onward;

	private final String name;

	public TrainJourney(int p, String n) {
	    price = p;
	    name = n;
	}

	public TrainJourney(int p, TrainJourney t, String n) {
	    price = p;
	    onward = t;
	    name = n;
	}

    }

    static TrainJourney link(TrainJourney a, TrainJourney b) {

	if (a == null) {
	    return b;
	}

	TrainJourney t = a;

	while (t.onward != null) {
	    t = t.onward;
	}

	t.onward = b;

	return a;
    }

    // This code is clearly functional style (it uses no mutation at all, even locally) and doesn’t modify
    // any existing data structures. Note, however, that the code does not create an entirely new TrainJourney
    static TrainJourney append(TrainJourney a, TrainJourney b) {
	return a == null ? b : new TrainJourney(a.price, append(a.onward, b), a.name + " " + b.name);
    }

    static void visit(TrainJourney journey, Consumer<TrainJourney> c) {
	if (journey != null) {
	    c.accept(journey);
	    visit(journey.onward, c);
	}
    }

    // ========================================================================================================

    static class Tree {

	private final String key;

	private int val;

	private Tree left, right;

	public Tree(String k, int v, Tree l, Tree r) {
	    key = k;
	    val = v;
	    left = l;
	    right = r;
	}
    }

    public static int lookup(String k, int defaultval, Tree t) {

	if (t == null) {
	    return defaultval;
	}

	if (k.equals(t.key)) {
	    return t.val;
	}

	return lookup(k, defaultval, k.compareTo(t.key) < 0 ? t.left : t.right);
    }

    public static Tree update(String k, int newval, Tree t) {

	if (t == null) { // 1º
	    t = new Tree(k, newval, null, null);
	} else if (k.equals(t.key)) { // 2º
	    t.val = newval;
	} else if (k.compareTo(t.key) < 0) { // 3º
	    t.left = update(k, newval, t.left);
	} else { // 4º
	    t.right = update(k, newval, t.right);
	}

	return t;
    }

    public static Tree functionalUpdate(String k, int newval, Tree t) {
	return (t == null) ? // 1º
	                   new Tree(k, newval, null, null) : //
	                   k.equals(t.key) ? // 2 º
	                                   new Tree(k, newval, t.left, t.right) : //
	                                   k.compareTo(t.key) < 0 ? //  3º
	                                                          new Tree(t.key, t.val, functionalUpdate(k, newval, t.left), t.right) : //
	                                                          new Tree(t.key, t.val, t.left, functionalUpdate(k, newval, t.right)); // 4º
    }
}
