package modular.java.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        LOG.info("hello, the application has started");

        LOG.error(new App().getGreeting());

        LOG.info("the application is now complete");
    }
}
