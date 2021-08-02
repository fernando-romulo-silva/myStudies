package br.com.fernando.chapter03_jsf.part06_httpGet;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class HttpGet {

    // JSF provides support for mapping URL parameters in HTTP GET requests to an EL. Italso provides support to generate GET -friendly URLs.
    // View parameters can be used to map URL parameters in GET requests to an EL. 
    // You can do so by adding the following fragment to a Facelets page:

    /**
     * <pre>
     *    <f:metadata>
     *        <f:viewParam name="name" value="#{user.name}" />
     *    </f:metadata>
     * </pre>
     */
    // look at index01.xhtml
    // 
    // Accessing a web application at http://localhost:8080/embeddedJeeContainerTest/index01.xhtml?name=jack :
    //
    // ====================================================================================================================================================================
    //
    @Named
    @ApplicationScoped
    public static class User {

        private String name;

        public String getName() {
            System.out.println("getName: " + name);
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Both process and process2 are valid method signatures
        public void process(ComponentSystemEvent event) throws AbortProcessingException {
            System.out.println("process called");
            name = name.toUpperCase();
        }

        public void process2() {
            System.out.println("process2 called");
            name = name.toUpperCase();
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(User.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index01.xhtml", "src/main/resources/chapter03_jsf/part06_httpGet/index01.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("index02.xhtml", "src/main/resources/chapter03_jsf/part06_httpGet/index02.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("login.xhtml", "src/main/resources/chapter03_jsf/part06_httpGet/login.xhtml"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part06_httpGet/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part06_httpGet/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part06_httpGet/faces-config.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // http://localhost:8080/embeddedJeeContainerTest/index.xhtml

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
