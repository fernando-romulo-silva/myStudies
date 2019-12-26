package br.com.fernando.chapter16_jndi_resources.part01_jndi;

public class Jndi01 {

    // JNDI https://docs.jboss.org/jbossweb/2.1.x/jndi-resources-howto.html
    //
    // https://tomcat.apache.org/tomcat-7.0-doc/jndi-resources-howto.html
    
    // The JEE standard provides a standard set of elements in the /WEB-INF/web.xml file to reference resources; 
    // resources referenced in these elements must be defined in an application-server-specific configuration.
    //
    // These entries in per-web-application InitialContext are configured in the <Context> elements that can be specified in either 
    // $CATALINA_HOME/conf/server.xml (Tomcat) or, preferably, the per-web-application context XML file (either META-INF/context.xml).
    //
    // The file context.xml is called from the web.xml through <context-param> tag. 
    // As you web.xml loads first when an application is created and it will create the references for the contexts that are configured in it.
    //
    // So, the purpose of context.xml is adding separation of codes. 
    // You can have separate contexts for different purposes. For example For Database connectivity, using other frameworks etc..,
    //
    // You can do configure only in web.xml.
    //
    // The resources defined in these elements may be referenced by the following elements in the web application deployment 
    // descriptor (/WEB-INF/web.xml) of your web application:
    //
    // * <env-entry> - Environment entry, a single-value parameter that can be used to configure how the application will operate.
    //
    // * <resource-ref> - Resource reference, which is typically to an object factory for resources such as a JDBC DataSource, a 
    // JavaMail Session, or custom object factories configured into Web server. 
    //
    // <resource-env-ref> - Resource environment reference, a new variation of resource-ref added in Servlet 2.4 that is simpler 
    // to configure for resources that do not require authentication information.
}
