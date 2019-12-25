package br.com.fernando.chapter03_jsf.part11_contracts;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Contracts01 {

    //  JSF 2.2 defines Resource01 Library Contracts, a library of templates and associated resources that can be applied to an entire
    // application in a reusable and interchangeable manner.
    //
    // A configurable set of views in the application will be able to declare themselves as template-clients of any template in
    // the resource library contract.

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part11_contracts/contracts01/index.xhtml"));

            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part11_contracts/contracts01/contracts/blue", "contracts/blue");
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part11_contracts/contracts01/contracts/red", "contracts/red");

            // Resource01 library contracts reside in the contracts directory of the web application's root:
            //
            // index.xhtml
            // user/index.xhtml
            // WEB-INF/faces-config.xml
            //
            // contracts/blue/layout.css
            // contracts/blue/template.xhtml
            // contracts/blue/footer.png
            //
            // contracts/red
            // contracts/red/layout.css
            // contracts/red/template.xhtml
            // contracts/red/logo.png

            // * The application also has two pages: index.xhtml and user/index.xhtml. These are template client pages.
            //
            // * All contracts reside in the contracts directory of the WAR. All templates and resources for a contract 
            // are in their own directory. For example, the preceding structure has two defined contracts, blue and red.
            //
            // * Each contract has a template.xhtml file, a CSS, and an image. Each template is called as a declared template. 
            // In the template, it is recommended that you refer to the stylesheets using h:outputStylesheet so that they are 
            // resolved appropriately.
            //
            // * The template.xhtml file has <ui:insert> tags called as declared insertion points.
            //
            // * CSS, images, and other resources bundled in the directory are declared resources.
            //
            // * The declared template, declared insertion points, and declared resources together define the resource library contract. 
            // A template client needs to know the value of all three in order to use the contract. 
            //
            // * WEB-INF/faces-config.xml defines the usage of the contract, look at contracts01/faces-config.xml
            //
            // A contract is applied based upon the URL pattern invoked. Based upon the configuration specified here, the red contract will 
            // be applied to /index.xhtml and the blue contract will be applied to /user/index.xhtml.
            // 

            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part11_contracts/contracts01/user", "user");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part11_contracts/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part11_contracts/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part11_contracts/contracts01/faces-config.xml"));

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
