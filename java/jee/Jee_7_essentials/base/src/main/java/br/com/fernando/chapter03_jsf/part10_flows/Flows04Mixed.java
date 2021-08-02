package br.com.fernando.chapter03_jsf.part10_flows;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowScoped;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Flows04Mixed {

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

        public String getHomeValue() {
            return "/index";
        }
    }

    @Named
    @FlowScoped("flow2")
    public static class Flow2Bean implements Serializable {

        private static final long serialVersionUID = 1L;

        public String getName() {
            return this.getClass().getSimpleName();
        }

        public String getReturnValue() {
            return "/return";
        }

        public String getHomeValue() {
            return "/index";
        }
    }

    public static class Flow1 {

        @Produces
        @FlowDefinition
        public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {

            String flowId = "flow1";
            flowBuilder.id("", flowId);
            flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();

            flowBuilder.returnNode("taskFlowReturn1").fromOutcome("#{flow1Bean.returnValue}");
            flowBuilder.returnNode("goHome").fromOutcome("#{flow1Bean.homeValue}");

            flowBuilder.inboundParameter("param1FromFlow2", "#{flowScope.param1Value}");
            flowBuilder.inboundParameter("param2FromFlow2", "#{flowScope.param2Value}");

            flowBuilder.flowCallNode("call2") //
                    .flowReference("", "flow2") //
                    .outboundParameter("param1FromFlow1", "param1 flow1 value") //
                    .outboundParameter("param2FromFlow1", "param2 flow1 value");

            return flowBuilder.getFlow();

        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Flow1Bean.class, Flow2Bean.class, Flow1.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows04Mixed/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("return.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows04Mixed/return.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("nonFlow.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows04Mixed/nonFlow.xhtml"));

            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows04Mixed/flow1", "flow1");
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows04Mixed/flow2", "flow2");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part10_flows/flows04Mixed/web.xml"));
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
