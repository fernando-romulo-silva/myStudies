package br.com.fernando.chapter10_concurrencyUtilities.part00_intro;

public class Intro {

    // Concurrency Utilities for Java EE provides a simple, standardized API for using concurrency from application components without compromising 
    // container integrity while still preserving the Java EE platform’s fundamental benefits.
    //
    // Java EE containers such as the EJB or web container do not allow using common Java SE concurrency APIs such as java.util.concurrent.ThreadPoolExecutor, 
    // java.lang.Thread, or java.util.Timer directly. 
    //
    // This is because all application code is run on a thread managed by the container, and each container typically expects all access to container-supplied 
    // objects to occur on the same thread. 
    // This allows the container to manage the resources and provide centralized administration.
}
