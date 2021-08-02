package br.com.fernando.chapter16_jndi_resources.part02_resources;

public class Part01 {
    // Configuring Java Resources
    //
    // A resource reference is an element in a deployment descriptor on the server (or aplication) that identifies the component’s coded name for the resource.
    //
    // For Wildfly is standalone.xml (context.xml);
    // For Tomcat is contex.xml (contex.xml);
    // For Glassfish is domain.xml (glassfish-resources.xml);
    //
    //
    // For example, jdbc/SavingsAccountDB. More specifically, the coded name references a connection factory for the resource.
    // The JNDI name of a resource and the resource reference name are not the same.
    //
    // This approach to naming requires that you map the two names before deployment, but it also decouples components from resources.
    // Because of this decoupling, if at a later time the component needs to access a different resource, the name does not need to change.
    //
    // This flexibility makes it easier for you to assemble Java EE applications from preexisting components.
    //
    // Usually they use this format:
    //
    // Environment entry (java:comp/env)
    // EJB home object reference (java:comp/env/ejb)
    // Business interface reference (java:comp/env/ejb)
    // UserTransaction references (java:comp/UserTransaction)
    // JavaMail Session Connection Factories (java:comp/env/mail)
    // JMS (java:comp/env/jms)
    // JDBC data source (java:comp/env/jdbc)
    // JavaMail session (java:comp/env/mail)
    // JavaBeans resource (java:comp/env/bean)
}
