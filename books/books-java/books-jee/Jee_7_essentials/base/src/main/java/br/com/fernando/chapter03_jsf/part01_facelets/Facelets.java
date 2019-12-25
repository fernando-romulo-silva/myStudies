package br.com.fernando.chapter03_jsf.part01_facelets;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Facelets {

    private static int count = 0;

    // ==================================================================================================================================================================
    // Facelets is the view declaration language (aka view handler) for JSF. It is the replacement for JSP, which is now retained only for backward compatibility.
    // New features introduced in version 2 of the JSF specification, such as composite components and Ajax, are only exposed to page authors using facelets.
    // Key benefits of facelets include a powerful templating system, reuse and ease of development, better error reporting (including line numbers),
    // and designer-friendliness.
    //
    // Facelets provides Expression Language (EL) integration. This allows two-way data binding between the backing beans and the UI (look at index.xhtml):
    //
    // Hello from Facelets, my name is #{name.value}!
    //
    // In this code, #{name} is an EL that refers to the value field of a request-scoped CDI bean:

    @Named
    @RequestScoped // allways use javax.enterprise.context.RequestScoped
    public static class Name {

        private String value;

        public Name() {
            super();
            value = "XPTO" + count++;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Named
    @ViewScoped // allways use javax.faces.view.ViewScoped
    public static class Product implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;

        public Product() {
            super();
            this.id = "Product 01";
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    // Similarly, an EJB can be injected in an EL expression:
    @Stateless
    @Named
    public static class CustomerSessionBean {

        public List<Name> getCustomerNames() {
            return Arrays.asList(new Name(), new Name(), new Name());
        }
    }

    // In addition, Facelets provides a powerful templating system that allows you to provide a consistent look and feel across multiple pages in a web application. 
    // A base page, called a template, is created via Facelets templating tags.
    //
    // This page defines a default structure for the page, including placeholders for the content that will be defined in the pages using the template.
    //
    // A template client page uses the template and provides actual content for the placeholders defined in the template.
    //
    // Look at template.xhtml and clientTemplate.xhtml at chapter03_jsf/part01_facelets
    //
    // ui:component Inserts a new UI component into the JSF component tree. Any component or content fragment outside this tag is ignored.
    //
    // ui:fragment Similar to ui:component , but does not disregard content outside this tag.
    //
    // ui:include Includes the document pointed to by the src attribute as part of the current Facelets page.
    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Name.class, Product.class, CustomerSessionBean.class);

            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part01_facelets/index.xhtml"));

            war.addWebResourceFiles(EmbeddedResource.add("cssLayout.css", "src/main/resources/chapter03_jsf/part01_facelets/cssLayout.css", "resources/css"));
            war.addWebResourceFiles(EmbeddedResource.add("default.css", "src/main/resources/chapter03_jsf/part01_facelets/default.css", "resources/css"));

            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part01_facelets/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part01_facelets/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part01_facelets/faces-config.xml"));
            war.addWebInfFiles(EmbeddedResource.add("template.xhtml", "src/main/resources/chapter03_jsf/part01_facelets/template.xhtml", "templates"));

            war.addWebResourceFiles(EmbeddedResource.add("clientTemplate.xhtml", "src/main/resources/chapter03_jsf/part01_facelets/clientTemplate.xhtml"));

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
