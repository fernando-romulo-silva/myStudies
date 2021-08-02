package br.com.fernando.chapter03_jsf.part05_ajax;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Ajax {

    // ==================================================================================================================================================================
    // JSF provides native support for adding Ajax capabilities to web pages.
    // It allows partial view processing, where only some components from the view are used for processing the response.
    //
    // There are two ways this support can be enabled:
    // * Programmatically using JavaScript resources
    // * Declaratively using f:ajax
    //
    // Please look at part05_ajax/index.xhtml
    //
    // The f:ajax tag may be wrapped around multiple components:

    /**
     * <pre>
     *     <f:ajax listener="#{user.checkFormat}">
     *         <h:inputText value="#{user.name}" id="name"/> 
     *         <h:inputText value="#{user.password}" id="password />
     *     </f:ajax>
     * </pre>
     */

    // In this code, f:ajax has a listener attribute and the corresponding Java method:
    public void checkFormat(AjaxBehaviorEvent evt) {
    }

    // ==================================================================================================================================================================
    @Named
    @SessionScoped
    public static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        private String name;

        private String password;

        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void login(ActionEvent evt) {
            if (name.equals(password)) {
                status = "Login successful";
            } else {
                status = "Login failed";
            }
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
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part05_ajax/index.xhtml"));

            // webapp/resources/stylesheets
            war.addWebResourceFiles(EmbeddedResource.add("main.css", "src/main/resources/chapter03_jsf/part05_ajax/main.css", "resources/stylesheets"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part05_ajax/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part05_ajax/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part05_ajax/faces-config.xml"));

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
