package org.nandao.cap11.p05JavaBeanEnhancementsInJava7;

import java.beans.Expression;

// JavaBean is a way of building reusable components for Java applications. They are Java classes
// that follow certain naming conventions. There have been several JavaBean enhancements
// added in Java 7. Here we will focus on the java.beans.Expression class, which is useful
// in executing methods. The execute method has been added to facilitate this capability.
public class Test {

    public static class Person {

        private String name;

        public Person() {
            this("Jane", 23);
        }

        public Person(String name, int age) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        String arguments[] = { "Peter" };

        Expression expression = new Expression(null, person, "setName", arguments);
        System.out.println("Name: " + person.getName());
        expression.execute();

        System.out.println("Name: " + person.getName());
        System.out.println();

        expression = new Expression(null, person, "getName", null);
        System.out.println("Name: " + person.getName());

        expression.execute();
        System.out.println("getValue: " + expression.getValue());
    }

}
