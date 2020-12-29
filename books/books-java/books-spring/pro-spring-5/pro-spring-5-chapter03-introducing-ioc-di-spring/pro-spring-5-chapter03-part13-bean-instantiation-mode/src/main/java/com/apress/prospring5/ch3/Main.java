package com.apress.prospring5.ch3;

public class Main {

    // Basically, they define the next:
    //
    // * Singleton: The default singleton scope. Only one object will be created per Spring IoC container.
    //
    // * Prototype: A new instance will be created by Spring when requested by the application.
    //
    // * Request: For web application use. When using Spring MVC for web applications, beans with request scope will
    // be instantiated for every HTTP request and then destroyed when the request is completed.
    //
    // * Session: For web application use. When using Spring MVC for web applications, beans with session scope will
    // be instantiated for every HTTP session and then destroyed when the session is over.
    //
    // * Global session: For portlet-based web applications. The global session scope beans can be shared among all portlets
    // within the same Spring MVC–powered portal application.
    //
    // * Thread: A new bean instance will be created by Spring when requested by a new thread, while for the same thread,
    // the same bean instance will be returned. Note that this scope is not registered by default.
    //
    // * Websocket: websocket Scopes a single bean definition to the lifecycle of a WebSocket.
    // Only valid in the context of a web-aware Spring ApplicationContext.
    //
    // * Custom: Custom bean scope that can be created by implementing the interface org.springframework.beans.factory.config.Scope
    // and registering the custom scope in Spring’s configuration (for XML, use the class org.springframework.beans.factory.config.CustomScopeConfigurer).
    //
    //
    //
    //
    // In general, singletons should be used in the following scenarios:
    //
    // * Shared object with no state
    //
    // * Shared object with read-only state
    //
    // * Shared object with shared state]
    //
    // * High-throughput objects with writable state
    //
    //
    // You should consider using nonsingletons in the following scenarios:
    //
    // * Objects with writable state
    //
    // * Objects with private state
    //
    // The main positive you gain from Spring’s instantiation management is that your applications can immediately benefit from the lower memory usage associated with singletons

}
