package br.com.fernando.chapter09_contextDependencyInjection.part12_lifeCycleCallbacks;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class LifeCycleCallbacks {

    // The standard annotations javax.annotation.PostConstruct and javax.annotation.
    //
    // PreDestroy from JSR 250 can be applied to any methods in a bean to perform resource initialization or cleanup:
    public static class MyBean {

        private String name;

        @PostConstruct
        public void setupResources() {
            // . . .
        }

        @PreDestroy
        public void cleanupResources() {
            // . . .
        }

        public String sayHello() {
            return "Hello " + name;
        }
    }

    // The setupResources method is where any resources required during business method execution can be acquired, and the cleanupResources method is where
    // those resources are closed or released.
    // The life-cycle callback methods are invoked after the no-args constructor.
}
