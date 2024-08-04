package org.example;

import static java.lang.System.out;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(final Object input, final Context context) {

        final var message = "Hello from Lambda, Please give review for the course";

        out.println(message);

        out.println(input);

        return message;
    }
}
