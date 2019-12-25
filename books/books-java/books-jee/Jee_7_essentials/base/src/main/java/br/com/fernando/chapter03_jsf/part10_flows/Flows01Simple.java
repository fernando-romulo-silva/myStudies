package br.com.fernando.chapter03_jsf.part10_flows;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.faces.flow.FlowScoped;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Flows01Simple {

    // ==================================================================================================================================================================
    // Faces Flow provides an encapsulation of related pages and corresponding backing beans as a module.
    //
    // Usually the objects in a faces flow are designed to allow the user to accomplish a task that requires input over a number of different views. 
    // An application thus becomes a collection of flows instead of just views.
    //
    // There are a few issues with this approach:
    //
    // * The application is broken into a series of modular flows that can call one another.
    //
    // * The flow of pages can be packaged as a module that can be reused within the same or an entirely different application
    //
    // * Shared memory scope (for example, flow scope) enables data to be passed between views within the task flow.
    //
    // * A new CDI scope, @FlowScoped , can be specified on a bean. This allows automatic activation/passivation of the bean as the scope is entered/exited.
    //
    // * Business logic can be invoked from anywhere in the page based upon the flow definition.
    @Named
    @FlowScoped("flow1")
    public static class Flow1Bean implements Serializable {

        private static final long serialVersionUID = 1L;

        private String address;

        // In this code, the bean has one flow-scoped variables: address. The bean is defined for the flow flow1 .
        // A new EL object for flow storage, #{flowScope} , is also introduced. 
        // This maps to facesContext.getApplication().getFlowHandler().getCurrentFlowScope() :

        // value: <h:inputText id="input" value="#{flowScope.value}" />

        // In this code, the value entered in the text box is bound to #{flowScope.value} . 
        // This EL expression can be used in other pages in the flow to access the value.

        public Flow1Bean() {
            System.out.println("Flow1Bean.ctor");
        }

        public String getName() {
            return this.getClass().getSimpleName();
        }

        public String getHomeAction() {
            return "/index";
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(final String address) {
            this.address = address;
        }
    }

    // You can define flows declaratively using <flow-definition> , or programmatically using the fluent FlowBuilder API. The two mechanisms are mutually exclusive.

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Flow1Bean.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows01Simple/index.xhtml"));
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows01Simple/flow1", "flow1");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part10_flows/flows01Simple/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part10_flows/beans.xml"));

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
