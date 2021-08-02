package org.nandao.ch01LambdaExpressions.part05ConstructorReferences;

import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Constructor references are just like method references, except that the name of
// the method is new. For example, Button::new is a reference to a Button constructor.
// Which constructor? It depends on the context. Suppose you have a list of strings.
// Then you can turn it into an array of buttons, by calling the constructor on each
// of the strings, with the following invocation:
public class Test {

    public static void main(String[] args) {

        List<String> labels = new ArrayList<>();
        
        // There are multiple Button constructors, but the compiler
        // picks the one with a String parameter because it infers from the context that the
        // constructor is called with a string.
        Stream<Button> stream = labels.stream().map(Button::new);
        
        
        List<Button> buttons = stream.collect(Collectors.toList());
        
        // Array constructor references are useful to overcome a limitation of Java. It is not
        // possible to construct an array of a generic type T. The expression new T[n] is an
        // error since it would be erased to new Object[n]. That is a problem for library authors.

        
    }
}
