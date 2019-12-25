package br.com.fernando.chapter03_jsf.part02_resourceHandling;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

import br.com.fernando.chapter03_jsf.part01_facelets.Facelets.CustomerSessionBean;
import br.com.fernando.chapter03_jsf.part01_facelets.Facelets.Name;
import br.com.fernando.chapter03_jsf.part01_facelets.Facelets.Product;

public class ResourceHandling {

    // Such resources can be packaged in the /resources directory in the web application or in /META-INF/resources in the classpath. 
    // The resources may also be localized, versioned, and collected into libraries. A resource can be referenced in EL:
    //
    // <a href="#{resource['wildfly.png']}">click here</a>  (template.xhtml)
    //
    // In this code, wildfly.png is bundled in the standard 'webapp/resources' directory
    //
    // If a resource is bundled in a library corp (a folder at the location where resources are packaged), then you can access it 
    // using the library attribute:
    //
    // <h:graphicImage library="corp" name="sun-logo.png" /> (index.xhtml)

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Name.class, Product.class, CustomerSessionBean.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part02_resourceHandling/index.xhtml"));

            // webapp/resources
            war.addWebResourceFiles(EmbeddedResource.add("wildfly.png", "src/main/resources/chapter03_jsf/part02_resourceHandling/wildfly.png", "resources"));

            // webapp/resources/css
            war.addWebResourceFiles(EmbeddedResource.add("cssLayout.css", "src/main/resources/chapter03_jsf/part02_resourceHandling/cssLayout.css", "resources/css"));
            war.addWebResourceFiles(EmbeddedResource.add("default.css", "src/main/resources/chapter03_jsf/part02_resourceHandling/default.css", "resources/css"));

            // webapp/resources/scripts
            war.addWebResourceFiles(EmbeddedResource.add("myScript.js", "src/main/resources/chapter03_jsf/part02_resourceHandling/myScript.js", "resources/scripts"));

            // webapp/resources/corp
            war.addWebResourceFiles(EmbeddedResource.add("sun-logo.png", "src/main/resources/chapter03_jsf/part02_resourceHandling/sun-logo.png", "resources/corp"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part02_resourceHandling/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part02_resourceHandling/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part02_resourceHandling/faces-config.xml"));
            war.addWebInfFiles(EmbeddedResource.add("template.xhtml", "src/main/resources/chapter03_jsf/part02_resourceHandling/template.xhtml"));

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
