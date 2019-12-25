package br.com.fernando.chapter03_jsf.part03_compositeComponents;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class CompositeComponents {

    // ==================================================================================================================================================================
    //
    // JSF defines a composite component as a component that consists of one or more JSF components defined in a Facelets markup file. 
    // This .xhtml file resides inside of a resource library. This allows you to create a reusable component from an arbitrary region of a page.
    // 
    // The composite component is defined in the defining page and used in the using page. 
    //
    // The defining page defines the metadata (or parameters) using <cc:interface> and the implementation using <cc:implementation> , where cc is the prefix for the 
    // http://xmlns.jcp.org/jsf/composite/ namespace.
    //
    // Please look at the chapter03_jsf/part03_compositeComponents/login.xhtml, chapter03_jsf/part03_compositeComponents/index.xhtml
    //
    // Overall, the composite component provides the following benefits:
    //
    // * Follows the Don't Repeat Yourself (DRY) design pattern and allows you to keep code that can be repeated at multiple places in a single file.
    //
    // * Allows developers to author new components without any Java code or XML configuration.
    // 
    // ==================================================================================================================================================================
    @Named
    @SessionScoped
    public static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Named
    @SessionScoped
    public static class UserService implements Serializable {

        private static final long serialVersionUID = 1L;

        @Inject
        User user;

        public void register() {
            System.out.println("Registering " + user.getName() + " with the password \"" + user.getPassword() + "\"");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(User.class, UserService.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part03_compositeComponents/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("status.xhtml", "src/main/resources/chapter03_jsf/part03_compositeComponents/status.xhtml"));

            // webapp/resources/ezcomp (components ez)
            war.addWebResourceFiles(EmbeddedResource.add("login.xhtml", "src/main/resources/chapter03_jsf/part03_compositeComponents/login.xhtml", "resources/ezcomp"));

            // webapp/resources/css
            war.addWebResourceFiles(EmbeddedResource.add("cssLayout.css", "src/main/resources/chapter03_jsf/part03_compositeComponents/cssLayout.css", "resources/css"));
            war.addWebResourceFiles(EmbeddedResource.add("default.css", "src/main/resources/chapter03_jsf/part03_compositeComponents/default.css", "resources/css"));

            // webapp/resources/stylesheets 
            war.addWebResourceFiles(EmbeddedResource.add("main.css", "src/main/resources/chapter03_jsf/part03_compositeComponents/main.css", "resources/stylesheets"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part03_compositeComponents/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part03_compositeComponents/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part03_compositeComponents/faces-config.xml"));
            war.addWebInfFiles(EmbeddedResource.add("template.xhtml", "src/main/resources/chapter03_jsf/part03_compositeComponents/template.xhtml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
