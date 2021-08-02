package br.com.fernando.chapter03_jsf.part07_serverAndClientExtension;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ServerAndClientExtension {

    // ==================================================================================================================================================================
    // Converters, validators, and listeners are server-side attached objects that add more functionality to the components on a page.
    // Behaviors are client-side extension points that can enhance a component's rendered content with behavior-defined scripts.
    //
    //
    // * A converter converts the data entered in a component from one format to another.
    //
    // JSF provides several built-in converters such as f:convertNumber and f:convertDateTime.
    // They can be applied to any editable component (look at part07_serverAndClientExtension/index.xhtml):
    //
    // <f:convertNumber integerOnly="true"/>
    //
    //
    //
    // A custom converter can be easily created
    //
    @FacesConverter("myConverter")
    public static class MyConverter implements Converter {

        // In this code, the methods getAsObject and getAsString perform object-to-string and
        // string-to-object conversions between model data objects and a string representation of
        // those objects that is suitable for rendering.

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("Received: " + value);
            try {
                return new UserAge(Integer.parseInt(value.trim()));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(e.toString()), e);
            }
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return String.valueOf(((UserAge) value).getAge());
        }

        // The POJO implements the Converter interface and is also marked with @FacesConverter. 
        // This converter can then be used in a JSF page (look at part07_serverAndClientExtension/index.xhtml):
        // <f:converter converterId="myConverter"/> 
    }

    // A validator is used to validate data that is received from the input components. 
    // JSF  provides several built-in validators such as f:validateLength and f:validateDoubleRange.
    //
    // A custom validator can be easily created:
    @FacesValidator("nameValidator")
    // The value attribute of @FacesValidator must match the value of the id attribute of f:validator here.
    public static class NameValidator implements Validator {

        // In this code, the method validate returns if the value is successfully validated. Otherwise,  a ValidatorException is thrown
        @Override
        public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

            System.out.println("Got: " + value);

            if (((String) value).length() < 3) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect name length", "Name length must >= 3, found only " + value));
            }
        }

    }

    // ==================================================================================================================================================================
    @Named
    @ApplicationScoped
    public static class UserAge {

        int age;

        public UserAge() {
        }

        public UserAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Named
    @ApplicationScoped
    public static class User {

        private int age;

        private String name;

        public User() {
        }

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // A listener listens for events on a component. The event can be a change of value, a click of a button, a click on a link, or something else. 
        // A listener can be a method in a managed bean or a class by itself.
        // A ValueChangeListener can be registered on any editable component:
        // <h:inputText value="#{user.name}" id="name" valueChangeListener="#{user.nameUpdated}">
        public void nameUpdated() {
            System.out.println("Name updated!");
        }

    }
    //  You can create a class-level listener by implementing the ValueChangeListener interface and specify it in the page using the f:valueChangeListener tag

    // You can define custom behaviors by extending ClientBehaviorBase and marking with @FacesBehavior.

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(User.class, UserAge.class, MyConverter.class, NameValidator.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part07_serverAndClientExtension/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("converter.xhtml", "src/main/resources/chapter03_jsf/part07_serverAndClientExtension/converter.xhtml"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part07_serverAndClientExtension/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part07_serverAndClientExtension/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part07_serverAndClientExtension/faces-config.xml"));

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
