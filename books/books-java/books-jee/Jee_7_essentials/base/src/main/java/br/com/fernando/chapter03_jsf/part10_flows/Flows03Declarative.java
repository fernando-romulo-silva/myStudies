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

public class Flows03Declarative {

    // ==================================================================================================================================================================
    @Named
    @FlowScoped("flow1")
    public static class Flow1Bean implements Serializable {

        private static final long serialVersionUID = 1L;

        public String getName() {
            return this.getClass().getSimpleName();
        }

        public String getReturnValue() {
            return "/return";
        }
    }

    @Named
    @FlowScoped("flow2")
    public static class Flow2Bean implements Serializable {

        private static final long serialVersionUID = 1L;

        public String getName() {
            return this.getClass().getSimpleName();
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Flow1Bean.class, Flow2Bean.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows03Declarative/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("return.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows03Declarative/return.xhtml"));

            // Flows packaged in directories use convention-over-configuration. The conventions are:
            // * Every View Declaration Language file, defined by an .xhtml page, in that directory is a view node of that flow.
            // * The start node of the flow is the view whose name is the same as the name of the flow.
            // * Navigation among any of the views in the directory is considered to be within the flow.
            // * Navigation to a view outside of that directory is considered to be an exit of the flow.
            // * An optional <flowname>-flow.xml file represents the flow definition.
            // * flow1.xhtml is the starting node of flow flow1 , and flow1a and flow1b are two other nodes in the same flow.
            //
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows03Declarative/flow1", "flow1");
            // flow1
            // flow1/flow1-flow.xml
            // flow1/flow1.xhtml
            // flow1/flow1a.xhtml
            // flow1/flow1a.xhtml
            //
            // flow2
            // flow2/flow1-flow.xml
            // flow2/flow2.xhtml
            // flow2/flow2a.xhtml
            // flow2/flow2b.xhtml
            //
            // Please look at /flows03Declarative/flow1/flow1-flow.xml
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows03Declarative/flow2", "flow2");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part10_flows/flows03Declarative/web.xml"));
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
