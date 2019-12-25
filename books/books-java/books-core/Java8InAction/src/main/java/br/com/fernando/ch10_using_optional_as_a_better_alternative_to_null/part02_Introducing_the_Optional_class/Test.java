package br.com.fernando.ch10_using_optional_as_a_better_alternative_to_null.part02_Introducing_the_Optional_class;

import java.util.Optional;

// Introducing the Optional class
public class Test {

    public static void test01() {

        // You might wonder what the difference is between a null reference and Optional .empty().
        // Semantically, they could be seen as the same thing, but in practice the difference is huge:
        // trying to dereference a null will invariably cause a NullPointer-Exception, whereas Optional.empty()
        // is a valid, workable object of type Optional that can be invoked in useful ways

        getCarInsuranceName(null);
    }

    public static String getCarInsuranceName(final Person person) {

        if (person != null) {
            final Optional<Car> optionalCar = person.getCar();

            if (optionalCar.isPresent()) {

                final Car car = optionalCar.get();

                final Optional<Insurance> optional = car.getInsurance();

                if (optional.isPresent()) {
                    final Insurance insurance = optional.get();
                    return insurance.getName();
                }
            }
        }

        return "Unknow";
    }

    // ===========================================================
    static class Person {

        private Car car;

        // A person might or might not own a car, so you declare this get return a Optional
        public Optional<Car> getCar() {
            return Optional.ofNullable(car);
        }
    }

    static class Car {

        private Insurance insurance;

        // A car might or might not be insured, so you declare this get return a Optional
        public Optional<Insurance> getInsurance() {
            return Optional.ofNullable(insurance);
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
