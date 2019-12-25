package br.com.fernando.chapter01_introduction;

public class Part01_Introduction {

    // -------------------------------------------------------------------------------------------------
    //
    // Java EE 7 consists of the platform specification that defines requirements across the platform.
    // It also consists of the following component specifications:
    //
    // Web technologies
    // * JSR 45: Debugging Support for Other Languages 1.0
    // * JSR 52: Standard Tag Library for JavaServer Pages (JSTL) 1.2
    // * JSR 245: JavaServer Pages (JSP) 2.3
    // * JSR 340: Servlet 3.1
    // * JSR 341: Expression Language 3.0
    // * JSR 344: JavaServer Faces (JSF) 2.2
    // * JSR 353: Java API for JSON Processing (JSON-P) 1.0
    // * JSR 356: Java API for WebSocket 1.0
    //
    // Enterprise technologies
    // * JSR 236: Concurrency Utilities for Java EE 1.0
    // * JSR 250: Common Annotations for the Java Platform 1.2
    // * JSR 316: Managed Beans 1.0
    // * JSR 318: Interceptors 1.2
    // * JSR 322: Java EE Connector Architecture (JCA) 1.7
    // * JSR 330: Dependency Injection for Java 1.0
    // * JSR 338: Java Persistence API (JPA) 2.1
    // * JSR 343: Java Message Service (JMS) 2.0
    // * JSR 345: Enterprise JavaBeans (EJB) 3.2
    // * JSR 346: Contexts and Dependency Injection (CDI) for the Java EE Platform 1.1
    // * JSR 349: Bean Validation 1.1
    // * JSR 352: Batch Applications for Java Platform 1.0
    // * JSR 907: Java Transaction API (JTA) 1.2
    // * JSR 919: JavaMail 1.5
    //
    // Web service technologies
    // * JSR 93: Java API for XML Registries (JAXR) 1.0 (optional for Java EE 7)
    // * JSR 101: Java API for XML-based RPC (JAX-RPC) 1.1 (optional for Java EE 7)
    // * JSR 109: Implementing Enterprise Web Services 1.4
    // * JSR 181: Web Services Metadata for the Java Platform 2.1
    // * JSR 222: Java Architecture for XML Binding (JAXB) 2.2
    // * JSR 224: Java API for XML Web Services (JAX-WS) 2.2
    // * JSR 339: Java API for RESTful Web Services (JAX-RS) 2.0
    //
    // Management and security technologies
    // * JSR 77: J2EE Management API 1.1
    // * JSR 88: Java Platform EE Application Deployment API 1.2 (optional for Java EE 7)
    // * JSR 115: Java Authorization Contract and Containers (JACC) 1.5
    // * JSR 196: Java Authentication Service Provider Inteface for Containers (JASPIC) 1.1
    //
    // -------------------------------------------------------------------------------------------------
    //
    // Java EE 7 architecture
    //
    // * Different components can be logically divided into three tiers: backend tier, middletier, and web tier. 
    // This is only a logical representation, and the components can be restricted to a different tier based upon 
    // the application's requirements.
    //
    // * JPA and JMS provide the basic services such as database access and messaging. JCA allows connection to legacy systems. 
    // Batch is used for performing noninteractive, bulk-oriented tasks.
    // 
    // * Managed Beans and EJB provide a simplified programming model using POJOs to use the basic services.
    //
    // * CDI, Interceptors, and Common Annotations provide concepts that are applicable to a wide variety of components, such 
    // as type-safe dependency injection, addressing cross-cutting concerns using interceptors, and a common set of annotations. 
    // Concurrency Utilities can be used to run tasks in a managed thread. JTA enables Transactional Interceptors that can be 
    // applied to any POJO.
    //
    // * CDI Extensions allow you to extend the platform beyond its existing capabilities in a standard way.
    //
    // * Web Services using JAX-RS and JAX-WS, JSF, JSP, and EL define the programming model for web applications. 
    // Web Fragments allow automatic registration of thirdparty web frameworks in a very natural way. JSON provides a way to parse and
    // generate JSON structures in the web tier. WebSocket allows the setup of a bidirectional, full-duplex communication channel 
    // over a single TCP connection.
    //
    // * Bean Validation provides a standard means to declare constraints and validate them across different technologies
    //
    // ----------------------------------------------------------------------------------------------------
    //
    // java.lang.OutOfMemoryError: PermGen space  = -XX:MaxPermSize=512m
    //
    // You need add jkd7\lib\tools.jar on classpath if you use jdk version >= 8 on your eclipse
}
