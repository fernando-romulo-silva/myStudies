package br.fernando.ch09_Streams_Objective.par08_Collecting_values_from_stream;

class Person {

    public final String name;

    public final Integer age;

    public Person(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return this.name + " is " + this.age + " years old";
    }
}
