package org.nandao.ch01LambdaExpressions.part01WhyLambda;


import java.util.Arrays;
import java.util.Comparator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


// A “lambda expression” is a block of code that you can pass around so it can be
// executed later, once or multiple times. Before getting into the syntax (or even the
// curious name), let’s step back and see where you have used similar code blocks
// in Java all along.

public class Test {

    // Thread
    static class Worker implements Runnable {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                // doWork();
            }
        }
    }

    static class LengthComparator implements Comparator<String> {
        public int compare(String first, String second) {
            return Integer.compare(first.length(), second.length());
        }
    }

    
    public static void main(String[] args) {
        Arrays.sort(args, new LengthComparator());

        // Annonimous class JavaFX
        Button button = new Button();
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Thanks for clicking!");
            }
        });
        
        // In all three examples, you saw the same approach. A block of code was passed
        // to someone—a thread pool, a sort method, or a button. The code was called at
        // some later time.
    }

}
