package br.com.fernando.chapter03_jsf.part08_validatingData;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ValidatingData {

    // In addition to using built-in and creating custom JSF validators, you can specify constraints defined on a backing bean using Bean Validation.
    //
    // Consider a simple web application that has one page with several text fields inside of a form:
    /**
     * <pre>
     *  <h:form>
     *   Name: <h:inputText value="#{myBean.name}"/>
     *   Age: <h:inputText value="#{myBean.age}"/>
     *   Zip: <h:inputText value="#{myBean.zip}"/>
     *   <h:commandButton value="Submit"/>
     *  </h:form>
     * </pre>
     */

    @Named
    @SessionScoped
    public static class MyBean implements Serializable {

        private static final long serialVersionUID = 1L;

        @Size(min = 3, message = "At least 3 characters")
        private String name;

        @Min(value = 18, message = "must be greater than or equal to 18")
        @Max(value = 25, message = "must be less than or equal to 25")
        private int age;

        @Pattern(regexp = "[0-9]{5}", message = "must match \"[0-9]{5}\"")
        private String zip;

        @Size(min = 4, message = "At least 4 characters", groups = Page1Group.class)
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    // Every h:inputText element that is backed by a UIInput component has an instance of Validator with id javax.faces.Bean attached to it.
    // The validate method of this Validator is called for the user-specified validation constraints during the process validations phase

    // ==================================================================================================================================================================
    // One or more validation groups can be associated with an input tag:
    // <f:validateBean validationGroups= "br.com.fernando.chapter03_jsf.part08_validatingData.ValidatingData$Page1Group, br.com.fernando.chapter03_jsf.part08_validatingData.ValidatingData$OtherGroup" />
    public static interface Page1Group {

    }

    public static interface OtherGroup {

    }

    // The validation groups can also be associated with a group of input tags:
    /**
     * <pre>
     *    <f:validateBean validationGroups=
    "br.com.fernando.chapter03_jsf.part08_validatingData.ValidatingData$OtherGroup">
     *  	<h:inputText value="#{myBean.name}"/>
     *          <h:inputText value="#{myBean.age}"/>
     * 	  </f:validateBean>
     * </pre>
     */

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(MyBean.class, Page1Group.class, OtherGroup.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part08_validatingData/index.xhtml"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part08_validatingData/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part08_validatingData/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part08_validatingData/faces-config.xml"));

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
