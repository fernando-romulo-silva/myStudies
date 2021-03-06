package br.com.fernando.ch10_using_optional_as_a_better_alternative_to_null.part03_Patterns_for_adopting_Optional;

import java.util.Optional;

// Patterns for adopting Optional
public class Test {

    // Creating Optional objects
    public static void test01() {
	// The first step before working with Optional is to learn how to create optional objects!
	// There are several ways.
	//
	// Empty optional
	final Optional<Car> optCar1 = Optional.empty();
	//
	// Optional from a non-null value
	final Car car2 = new Car();
	final Optional<Car> optCar2 = Optional.of(car2);

	// Optional from null
	final Optional<Car> optCar = Optional.ofNullable(car2);

	// You might imagine we’ll continue by investigating “how to get a value out of an optional.” In
	// particular, there’s a get method that does precisely this, and we’ll talk more about it later. But
	// get raises an exception when the optional is empty, and so using it in an ill-disciplined manner
	// effectively re-creates all the maintenance problems caused by using null. So instead we start by
	// looking at ways of using optional values that avoid explicit tests; these are inspired by similar
	// operations on streams.
    }

    // Extracting and transforming values from optionals with map
    public static void test02() {

	final Insurance insurance = new Insurance();
	// Optional supports a map method for this pattern. It works as follows:
	final Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
	final Optional<String> name = optInsurance.map(Insurance::getName);

	// The map operation applies the provided function to each element of a stream. You could also think of an
	// Optional object as a particular collection of data, containing at most a single element. If the
	// Optional contains a value, then the function passed as argument to map transforms that value.
	// If the Optional is empty, then nothing happens.
    }

    // Chaining Optional objects with flatMap
    public static void test03() {

	// With streams, the flatMap method takes a function as an
	// argument, which returns another stream. This function is applied to each element of a stream,
	// which would result in a stream of streams. But flatMap has the effect of replacing each
	// generated stream by the contents of that stream. In other words, all the separate streams that
	// are generated by the function get amalgamated or flattened into a single stream. What you want
	// here is something similar, but you want to flatten a two-level optional into one.
    }

    public static String getCarInsuranceName(final Person person) {

	final Optional<Person> optPerson = Optional.of(person);
	// The variable optPeople is of type Optional<People>, so it’s perfectly fine to call the map method.
	// But getCar returns an object of type Optional<Car>.
	//
	// This means the result of the map operation is an object of type Optional<Optional<Car>>.
	// As a result, the call to getInsurance is invalid because the outermost optional contains as its
	// value another optional, which of course doesn’t support the getInsurance method
	//
	// With streams, the flatMap method takes a function as an argument, which returns another stream.
	// This function is applied to each element of a stream, which would result in a stream of streams.
	// But flatMap has the effect of replacing each generated stream by the contents of that stream.

	return optPerson //
	    .flatMap(Person::getCar) //
	    .flatMap(Car::getInsurance) //
	    .map(Insurance::getName) //
	    .orElse("Unknown"); // A default value if the resulting Optional is empty
    }

    // Default actions and unwrapping an optional
    public static void test04() {
	//  get() is the simplest but also the least safe of these methods. It returns the wrapped value if present
	// but throws a NoSuchElementException otherwise. For this reason, using this method is almost
	// always a bad idea unless you’re really sure the optional contains a value. In addition, it’s not much of
	// an improvement over nested null checks.
	//
	//  orElse(T other) is the method used in listing 10.5, and as we noted there, it allows you to provide a
	// default value for when the optional doesn’t contain a value.
	//
	//  orElseGet(Supplier<? extends T> other) is the lazy counterpart of the orElse method, because
	// the supplier is invoked only if the optional contains no value. You should use this method either when
	// the default value is time-consuming to create (to gain a little efficiency) or you want to be sure this is
	// done only if the optional is empty (in which case it’s strictly necessary).
	//
	//  orElseThrow(Supplier<? extends X> exceptionSupplier) is similar to the get method in that it
	// throws an exception when the optional is empty, but in this case it allows you to choose the type of
	// exception that you want to throw.
	//
	//  ifPresent(Consumer<? super T> consumer) lets you execute the action given as argument if a
	// value is present; otherwise no action is taken.
    }

    // Combining two optionals
    public static void test05() {

	final Car car = new Car();
	final Optional<Car> optCar = Optional.ofNullable(car);

	final Person person = new Person();
	final Optional<Person> optPerson = Optional.ofNullable(person);

	nullSafeFindCheapestInsurance2(optPerson, optCar);
    }

    public static Insurance findCheapestInsurance(final Person person, final Car car) {
	// queries services provided by the different insurance companies
	// compare all those data
	return null;// cheapestCompany;
    }

    public static Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
	if (person.isPresent() && car.isPresent()) {
	    return Optional.of(findCheapestInsurance(person.get(), car.get()));
	} else {
	    return Optional.empty();
	}
    }

    // Here you invoke a flatMap on the first optional, so if this is empty, the lambda expression
    // passed to it won’t be executed at all and this invocation will just return an empty optional.
    // Conversely, if the person is present, it uses it as the input of a Function returning an
    // Optional<Insurance> as required by the flatMap method. The body of this function invokes a
    // map on the second optional, so if it doesn’t contain any car, the Function will return an empty
    // optional and so will the whole nullSafeFindCheapestInsurance method. Finally, if both the
    // person and the car are present, the lambda expression passed as argument to the map method
    // can safely invoke the original findCheapestInsurance method with them.
    public static Optional<Insurance> nullSafeFindCheapestInsurance2(Optional<Person> person, Optional<Car> car) {

	return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    // Rejecting certain values with filter
    public static void test06() {

	final Car car = new Car();

	final Person person = new Person();
	person.setCar(car);

	// Often you need to call a method on an object and check some property
	final Optional<Insurance> optInsurance = Optional.ofNullable(person) //
	    .flatMap(Person::getCar).flatMap(Car::getInsurance);

	if (optInsurance.isPresent() && "CambridgeInsurance".equals(optInsurance.get().getName())) {
	    System.out.println("ok");
	} else {
	    System.out.println("Not ok");
	}

	// This pattern can be rewritten using the filter method on an Optional object, as follows:
	optInsurance //
	    .filter(insurance -> "CambridgeInsurance".equals(insurance.getName())) //
	    .ifPresent(x -> System.out.println("ok"));

	// Java 9
	// optInsurance
	// .filter(insurance -> "CambridgeInsurance".equals(insurance.getName())) //
	// .ifPresentOrElse(obj -> obj.setAvailable(true),
	// () -> logger.error("…"));

	//

    }

    public String getCarInsuranceName(Optional<Person> person, int minAge) {
	return person.filter(p -> p.getAge() >= minAge) //
	    .flatMap(Person::getCar) //
	    .flatMap(Car::getInsurance)//
	    .map(Insurance::getName) //
	    .orElse("Unknown");
    }

    // ===========================================================
    public static void main(String[] args) {
	test06();
    }

    static class Person {

	private Car car;

	private int age;

	// A person might or might not own a car, so you declare this get return a Optional
	public Optional<Car> getCar() {
	    return Optional.ofNullable(car);
	}

	public void setCar(final Car car) {
	    this.car = car;
	}

	public int getAge() {
	    return age;
	}

	public void setAge(int age) {
	    this.age = age;
	}
    }

    static class Car {

	private Insurance insurance;

	// A car might or might not be insured, so you declare this get return a Optional
	public Optional<Insurance> getInsurance() {
	    return Optional.ofNullable(insurance);
	}

	public void setInsurance(final Insurance insurance) {
	    this.insurance = insurance;
	}
    }

    static class Insurance {

	// An insurrance company must have a name, the fact that the name of the insurance company is declared of type String
	// instead of Optional<String> makes it evident that it’s mandatory for an insurance company tohave a name.
	private String name;

	public String getName() {
	    return name;
	}
    }
}
