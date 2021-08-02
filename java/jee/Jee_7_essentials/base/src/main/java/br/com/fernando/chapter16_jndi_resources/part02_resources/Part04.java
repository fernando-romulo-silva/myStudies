package br.com.fernando.chapter16_jndi_resources.part02_resources;

public class Part04 {

    // Enterprise Naming Context
    //
    // The JEE standard provides a standard set of elements in the /WEB-INF/web.xml file to reference resources;
    // resources referenced in these elements must be defined in an application-server-specific configuration.
    //
    // These entries in per-web-application InitialContext are configured in the <Context> elements that can be specified in either
    // $CATALINA_HOME/conf/server.xml (Tomcat) or, preferably, the per-web-application context XML file (either META-INF/context.xml).
    //
    //
    // ----------------------------------------------------------------------------------------------------------------------------------------
    // The web.xml Configuration
    //
    // The resources defined in these elements may be referenced by the following elements in the web application deployment
    // descriptor (/WEB-INF/web.xml) of your web application:
    //
    // * <env-entry> : Environment entry, a single-value parameter that can be used to configure how the application will operate.
    //
    // * <resource-ref> : Resource reference, which is typically to an object factory for resources such as a JDBC DataSource, a
    // JavaMail Session, or custom object factories configured into Web server.
    //
    // * <resource-env-ref> : Resource environment reference, a new variation of resource-ref added in Servlet 2.4 that is simpler
    // to configure for resources that do not require authentication information.
    //
    //
    // ----------------------------------------------------------------------------------------------------------------------------------------
    // The context.xml Configuration (only for tomcat)
    //
    // The file context.xml (META-INF/context.xml) is called from the web.xml.
    // As you web.xml loads first when an application is created and it will create the references for the contexts that are configured in it.
    //
    // So, the purpose of context.xml is adding separation of codes.
    // The purpose of the Context tag is to configure the context (i.e. your application).
    // You can have separate contexts for different purposes. For example For Database connectivity, using other frameworks etc..,
    //
    // * <Environment> - Configure names and values for scalar environment entries that will be exposed to the web application through the JNDI InitialContext
    // (equivalent to the inclusion of an <env-entry> element in the web application deployment descriptor).
    //
    // * <Resource> - Configure the name and data type of a resource made available to the application (equivalent to the inclusion of a <resource-ref> element
    // in the web application deployment descriptor).
    //
    // * <ResourceLink> - Add a link to a resource defined in the global JNDI context. Use resource links to give a web application access to a resource defined
    // in the <GlobalNamingResources> child element of the <Server> element.
    //
    // * <Transaction> - Add a resource factory for instantiating the UserTransaction object instance that is available at java:comp/UserTransaction.
    //
    //
    //
    // Any number of these elements may be nested inside a <Context> element and will be associated only with that particular web application.
    //
    // Where the same resource name has been defined for a <env-entry> element included in the web application deployment descriptor (/WEB-INF/web.xml) and in an
    // <Environment> element as part of the <Context> element for the web application, the values in the deployment descriptor will take precedence only
    // if allowed by the corresponding <Environment> element (by setting the override attribute to "true")
}
