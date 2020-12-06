package com.apress.prospring5.ch3;

public class Main {

    // Setter injection is useful in a variety of cases.
    //
    // If the component is exposing its dependencies to the container but is happy to provide its own defaults, setter injection is usually the best way to accomplish this.
    //
    // Another benefit of setter injection is that it allows dependencies to be declared on an interface, although this is not as useful as you might first think.
    //
    // Unless you are absolutely sure that all implementations of a particular business interface require a particular dependency,
    // let each implementation class define its own dependencies and keep the business interface for business methods.
    //
    //
    // In many environments, Spring cannot automatically wire up all of your application components by using dependency injection, and you must use dependency lookup to access the initial set of components.
    // For example, in stand-alone Java applications, you need to bootstrap Spring’s container in the main() method and obtain the dependencies (via the ApplicationContext interface) for processing programmatically.
    //
    // In some cases, all of this setup is handled automatically (for example, in a web application, Spring’s ApplicationContext will be bootstrapped by the web container during application startup via a Spring-provided ContextLoaderListener class declared in the web.xml descriptor file).
    // But in many cases, you need to code the setup yourself.
    //
    //
    // Although properties are ideal for small, simple applications, they can quickly become cumbersome when you are dealing with a large number of beans.
    // For this reason, it is preferable to use the XML configuration format for all but the most trivial of applications.
    //
    //
    //
    // Originally, Spring supported defining beans through either properties or an XML file.
    // Since the release of JDK 5 and Spring’s support of Java annotations, Spring (starting from Spring 2.5) also supports using Java annotations when configuring

}
