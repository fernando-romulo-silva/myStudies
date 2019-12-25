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

public class Flows02Programmatic {

    // ==================================================================================================================================================================
    public static class Flow1 {

        @Produces
        @FlowDefinition
        public Flow defineFlow(@FlowBuilderParameter final FlowBuilder flowBuilder) {
            final String flowId = "flow1";
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

    @Named
    @FlowScoped("flow1")
    public class Flow1Bean implements Serializable {

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

    // ==================================================================================================================================================================
    // You can define flows declaratively using <flow-definition> , or programmatically using the fluent FlowBuilder API. 
    // The two mechanisms are mutually exclusive.
    // 
    // Flows can be packaged in JAR files or in directories. JAR packaging requires flows to be explicitly declared in META-INF/faces-config.xml in the JAR file. 
    // Flow nodes are packaged in META-INF/flows/<flowname> where <flowname> is a JAR directory entry whose name is identical to that of a flow id in the corresponding 
    // FlowDefinition classes.
    //
    // 
    public static class Flow2 {

        // Flow is defined programmatically via the CDI producer. @FlowDefinition is a
        // class-level annotation that allows the flow to be defined via the fluent Flow
        // Builder API.

        @Produces
        @FlowDefinition
        public Flow defineFlow(@FlowBuilderParameter final FlowBuilder flowBuilder) {

            // A FlowBuilder instance is injected as a parameter via the @FlowBuilderPara meter and is used to define the flow.
            //
            //  flow1 is defined as the flow identifier and flow1.xhtml is marked as the starting node.
            final String flowId = "flow2";
            flowBuilder.id("", flowId);
            flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();

            // The returnNode method is used to define an exit point from the flow. In this case, the flow is directed to /index for the action goHome. 
            // The node value can be specified as an EL expression as well # for example, may be bound to a bean.
            flowBuilder.returnNode("taskFlowReturn1").fromOutcome("#{flow2Bean.returnValue}");
            flowBuilder.returnNode("goHome").fromOutcome("#{flow2Bean.homeValue}");

            // Named inbound parameters are named parameters from another flow and are defined via the inboundParameter method. The methods value 
            // is populated elsewhere with a corresponding outboundParameter element. The value is stored in the flow local storage via #{flowScope} 
            flowBuilder.inboundParameter("param1FromFlow1", "#{flowScope.param1Value}");
            flowBuilder.inboundParameter("param2FromFlow1", "#{flowScope.param2Value}");

            // flowCallNode method is used to define an exit point from the flow. In this case, flow2 flow is called. 
            // A named outbound parameter and its value are set via the outboundParameter method.
            flowBuilder.flowCallNode("call1") //
                    .flowReference("", "flow1") //
                    .outboundParameter("param1FromFlow2", "param1 flow2 value") //
                    .outboundParameter("param2FromFlow2", "param2 flow2 value");

            return flowBuilder.getFlow();
        }
    }

    @Named
    @FlowScoped(value = "flow2")
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

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Flow1Bean.class, Flow1.class, Flow2Bean.class, Flow2.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows02Programmatic/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("return.xhtml", "src/main/resources/chapter03_jsf/part10_flows/flows02Programmatic/return.xhtml"));

            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows02Programmatic/flow1", "flow1");
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part10_flows/flows02Programmatic/flow2", "flow2");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part10_flows/flows02Programmatic/web.xml"));
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
