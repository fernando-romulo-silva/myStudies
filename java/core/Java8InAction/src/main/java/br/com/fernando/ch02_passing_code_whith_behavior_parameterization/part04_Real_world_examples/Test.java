package br.com.fernando.ch02_passing_code_whith_behavior_parameterization.part04_Real_world_examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.com.fernando.Apple;

// Real World
public class Test {

    // // Sorting with a Comparator
    public static void test1() {
        final List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        inventory.sort(new Comparator<Apple>() {

            public int compare(Apple a1, Apple a2) {
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        // If the farmer changes his mind about how to sort apples, you can create an ad hoc Comparator
        // to match the new requirement and pass it to the sort method! The internal details of how to sort
        // are abstracted away. With a lambda expression it would look like this:

        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
    }

    // Executing a block of code with Runnable
    public static void test2() {

        // You can use this interface to create threads with different behaviors as follows:
        Thread thread1 = new Thread(new Runnable() {

            public void run() {
                System.out.println("Hello world");
            }
        });
        
        thread1.start();
        
        //
        // With a lambda expression it would look like this:
        Thread thread2 = new Thread(() -> System.out.println("Hello world"));
        
        thread2.start();
    }

    public static void main(String[] args) {

    }

}
