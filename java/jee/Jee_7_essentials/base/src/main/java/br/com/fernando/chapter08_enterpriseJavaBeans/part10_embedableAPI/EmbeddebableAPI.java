package br.com.fernando.chapter08_enterpriseJavaBeans.part10_embedableAPI;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

public class EmbeddebableAPI {

    // The Embeddable EJB API allows client code and its corresponding enterprise beans to run within the same JVM and class loader.
    //
    // The client uses the bootstrapping API from the javax.ejb package to start the container and identify the set of enterprise bean  components for execution.
    //
    // This provides better support for testing, offline processing, and executing EJB components within a Java SE environment.

    @Stateless
    public static class MyBean {

        public String sayHello(final String name) {
            return "Hello " + name;
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {

        final Map<String, Object> properties = new HashMap<>();

        // The embeddable EJB container uses the JVM classpath to scan for the EJB modules to be loaded.
        //
        // Application name for an EJB module. It corresponds to the <app-name> portion of the Portable Global JNDI Name syntax.
        // properties.put(EJBContainer.APP_NAME, EMBEDDED_JEE_TEST_APP_NAME);
        //
        // The client can override this behavior during setup by specifying an alternative set of target modules:
        properties.put(EJBContainer.MODULES, new File("target/classes/br/com/fernando/chapter08_enterpriseJavaBeans/part10_embedableAPI"));
        //
        // Define the installation of the server
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "C:\\Users\\fernando.romulo\\Downloads\\glassfish-4.1.2\\glassfish\\glassfish");

        // With EJB 3.2, if Java SE 7 is used to run the embeddable container, then the client may close the container implicitly by using the try-with-resources 
        // statement when acquiring the EJBContainer instance:
        try (final EJBContainer container = EJBContainer.createEJBContainer(properties)) {

            final Context context = container.getContext();

            final MyBean instance = (MyBean) context.lookup("java:global/part10_embedableAPI/MyBean");
            final String result = instance.sayHello("Duke");

            System.out.println(result);
        }

    }

}
