package org.nandao.ch01LambdaExpressions.part02TheSyntaxOfLambdaExpressions;

import java.util.Comparator;

// What are first and second? They are both strings. Java is a strongly typed language,
// and we must specify that as well:
// (String first, String second) -> Integer.compare(first.length(), second.length())
//
// λfirst.λsecond.Integer.compare(first.length(), second.length())

public class Test {
    
    public static void doWork(){
        
    }

    public static void main(String[] args) {

        System.out.println();
        
        //  Same as (String first, String second)
        Comparator<String> comp1 = (first, second) -> Integer.compare(first.length(), second.length());
        
        // If a lambda expression has no parameters, you still supply empty parentheses, just as with a parameterless method:
        // () -> { for (int i = 0; i < 1000; i++) System.out.println(); };
        
        // You never specify the result type of a lambda expression. It is always inferred from context. 
        Comparator<String> comp2 = (String first, String second) -> Integer.compare(first.length(), second.length());
        // can be used in a context where a result of type int is expected.

    }
}
