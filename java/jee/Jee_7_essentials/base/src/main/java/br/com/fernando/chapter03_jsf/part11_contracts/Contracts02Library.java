package br.com.fernando.chapter03_jsf.part11_contracts;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Contracts02Library {

    // ==================================================================================================================================================================
    @Named
    @SessionScoped
    public static class ContractsBean implements Serializable {

        private static final long serialVersionUID = 1L;

        String contract = "red";

        public String getContract() {
            return contract;
        }

        public void setContract(String contract) {
            this.contract = contract;
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            // The contracts can be packaged in the META-INF/contracts entry of a JAR file.Each contract in the JAR file must have a marker file.
            // The filename is given by the value of the symbolic constant javax.faces.application.ResourceHandler.RESOURCE_CONTRACT_XML

            final EmbeddedJar lib = new EmbeddedJar("libJar");
            lib.addMetaInfFiles("src/main/resources/chapter03_jsf/part11_contracts/contracts01/contracts/blue", "contracts/blue");
            lib.addMetaInfFiles("src/main/resources/chapter03_jsf/part11_contracts/contracts01/contracts/red", "contracts/red");
            lib.addClasses(ContractsBean.class);

            lib.exportToFile(APP_FILE_TARGET);

            // libJar.jar
            // -> META-INF
            // -----> contracts
            // ---------> blue
            // ------------> javax.faces.contract.xml
            // ------------> cssLayout.css
            // ------------> default.css
            // ------------> template.xhtml
            // ---------> red
            // ------------> javax.faces.contract.xml
            // ------------> cssLayout.css
            // ------------> default.css
            // ------------> template.xhtml

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addLibs(APP_FILE_TARGET + "/libJar.jar");

            // The contents of the contracts directory from our application can be packaged in the META-INF/contracts entry of a JAR file, say libJar.jar.
            // This JAR can then be packaged into WEB-INF/lib, and the declared templates can be used in the application:
            // index-blue.xhtml
            // index-red.xhtml
            // WEB-INF/lib/libJar.jar

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index-blue.xhtml", "src/main/resources/chapter03_jsf/part11_contracts/contracts02Library/index-blue.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("index-red.xhtml", "src/main/resources/chapter03_jsf/part11_contracts/contracts02Library/index-red.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part11_contracts/contracts02Library/index.xhtml"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part11_contracts/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part11_contracts/beans.xml"));

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
