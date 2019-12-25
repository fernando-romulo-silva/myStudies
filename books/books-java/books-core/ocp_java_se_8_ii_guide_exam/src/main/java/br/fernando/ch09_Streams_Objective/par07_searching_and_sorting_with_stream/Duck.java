package br.fernando.ch09_Streams_Objective.par07_searching_and_sorting_with_stream;

class Duck implements Comparable<Duck> {

    private final String name;

    private final String color;

    private final int age;

    public Duck(String name, String color, int age) {
        super();
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getAge() {
        return age;
    }

    public int compareTo(Duck otherDuck) {
        return this.getName().compareTo(otherDuck.getName());
    }

    @Override
    public String toString() {
        return name + " is " + color + " and is " + age + " years old.";
    }
}
