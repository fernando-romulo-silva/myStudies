package br.com.fernando.ch15_Blending_OOP_and_FP.part01_Introduction_to_Scala;

import java.util.Optional;

class Person {

    private Car car;

    private Integer age;

    // A person might or might not own a car, so you declare this get return a Optional
    public Optional<Car> getCar() {
        return Optional.ofNullable(car);
    }

    public void setCar(final Car car) {
        this.car = car;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
