package org.nandao.ch01LambdaExpressions.part08StaticMethodsInInterface;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// As of Java 8, you are allowed to add static methods to interfaces. There was
// never a technical reason why this should be outlawed. It simply seemed to be
// against the spirit of interfaces as abstract specifications.

public class Test {

    public static void main(String[] args) {
        
        final List<String> list = Arrays.asList("Test", "new");
        
        Collections.shuffle(list);
        
    }
}
