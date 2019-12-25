package br.com.fernando.chapter09_contextDependencyInjection.part00_intro;

public class Intro {

    // CDI defines a type-safe dependency injection mechanism in the Java EE platform.
    //
    // A bean specifies only the type and semantics of other beans it depends upon, without a string name 
    // and using the type information available in the Java object model.
    //
    // The injection request need not be aware of the actual life cycle, concrete implementation, threading model, or other clients of the bean.
    //
    // Almost any POJO can be injected as a CDI bean. This includes EJBs, JNDI resources, entity classes, and persistence units and contexts.
    //
    // Even the objects that were earlier created by a factory method can now be easily injected.
    //
    // Specifically, CDI allows EJB components to be used as JSF managed beans, thus bridging the gap between the transactional and the web tier.
}
