package br.com.fernando.chapter08_enterpriseJavaBeans.part00_intro;

public class Intro {

    // Enterprise JavaBeans are used for the development and deployment of componentbased distributed applications that are scalable, transactional, and secure.
    // An EJB typically contains the business logic that operates on the enterprise's data.
    // The service information, such as transaction and security attributes, may be specified in the form of metadata annotations, or separately in an XML deployment descriptor.
    //
    // A bean instance is managed at runtime by a container. The bean is accessed on the client and is mediated by the container in which it is deployed.
    //
    // This allows the application developer to focus on the business logic and not worry about lowlevel transaction and state management details, remoting, concurrency,
    // multithreading, connection pooling, or other complex low-level APIs.
    //
    // There are two types of enterprise beans:
    //
    // * Session beans
    // * Message-driven beans
    //
    //
    //
    // EJB >= CDI
    //
    // Note that EJBs are CDI beans and therefore have all the benefits of CDI. The reverse is not true (yet).
    // So definitely don't get into the habit of thinking "EJB vs CDI" as that logic really translates
    // to "EJB+CDI vs CDI", which is an odd equation.
    //
    // In future versions of Java EE we'll be continuing to align them. What aligning means is allowing people to do what they already can do, 
    // just without the @Stateful, @Stateless or @Singleton annotation at the top.
    //
    // EJB and CDI in Implementation Terms
    //
    // The difference only comes in how the object to be invoked is resolved. By "resolved" we simply mean, where and how the 
    // container looks for the real instance to invoke.
    //
    // In CDI the container looks in a "scope", which will basically be a hashmap that lives for a specific period of time (per request 
    // @RequestScoped, per HTTP Session @SessionScoped, per application @ApplicationScoped, JSF Conversation @ConversationScoped, or per your custom scope implementation).
    //
    // In EJB the container looks also into a hashmap if the bean is of type @Stateful. An @Stateful bean can also use any of the above scope annotations causing it to 
    // live and die with all the other beans in the scope. In EJB @Stateful is essentially the "any scoped" bean. The @Stateless is basically an instance pool 
    // -- you get an instance from the pool for the duration of one invocation. The @Singleton is essentially @ApplicationScoped
    //
    // Performance Note
    //
    // Disregard any "light" or "heavy" mental images you may have. That's all marketing. They have the same internal design for the most part. 
    // CDI instance resolution is perhaps a bit more complex because it is slightly more dynamic and contextual. EJB instance resolution is fairly static, 
    // dumb and simple by comparison.
    //
    // I can tell you from an implementation perspective in TomEE, there's about zero performance difference between invoking an EJB vs invoking a CDI bean.

}
