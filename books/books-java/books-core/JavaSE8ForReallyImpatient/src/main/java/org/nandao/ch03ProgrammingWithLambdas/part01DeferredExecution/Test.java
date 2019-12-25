package org.nandao.ch03ProgrammingWithLambdas.part01DeferredExecution;

import java.util.logging.Logger;

// The point of all lambdas is deferred execution. After all, if you wanted to execute
// some code right now, you’d do that, without wrapping it inside a lambda.
// There are many reasons for executing code later, such as :
// • Running the code in a separate thread
// • Running the code multiple times
// • Running the code at the right point in an algorithm (for example, the comparison operation in sorting)
// • Running the code when something happens (a button was clicked, data has arrived, and so on)
// • Running the code only when necessary

public class Test {

    public static void main(String[] args) {
        final Logger logger = Logger.getGlobal();
        
        final Integer x = 1;
        
        final Integer y = 2;
        
        logger.info(() -> "x: " + x + ", y:" + y);
    }

}
