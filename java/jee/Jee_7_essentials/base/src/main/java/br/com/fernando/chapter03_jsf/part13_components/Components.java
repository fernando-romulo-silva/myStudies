package br.com.fernando.chapter03_jsf.part13_components;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Components {

    // ==================================================================================================================================================================
    @Named
    @RequestScoped
    public static class FileUploadBean implements Serializable {

        // h:inputFile is a new component added in JSF 2.2. This component allows a file to be uploaded. 
        // This component can be used inside an h:form and bound to a bean that has a field of the type javax.servlet.http.Part:

        private static final long serialVersionUID = 1L;

        private Part file;

        private String text;

        public Part getFile() {
            return file;
        }

        public void setFile(final Part file) {
            System.out.println("Got file ...");
            this.file = file;

            if (null != file) {
                System.out.println("... and trying to read it ...");

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                    String string = reader.readLine();
                    StringBuilder builder = new StringBuilder();

                    while (string != null) {
                        builder.append(string);
                        string = reader.readLine();
                    }

                    text = builder.toString();
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }

                System.out.println("... completed reading file.");

            } else {
                System.out.println("... but its null.");
            }
        }

        public String getText() {
            System.out.println("Complete text: " + text);
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Person {

        private int id;

        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Named
    @ApplicationScoped
    public static class MyBean implements Serializable {

        private static final long serialVersionUID = 1L;

        String commandLink;

        String commandLinkLabel;

        Person[] list;

        String password;

        String inputText;

        String inputTextarea;

        String selectCheckbox;

        String[] selectManyCheckbox;

        boolean selectBooleanCheckbox;

        @PostConstruct
        public void init() {
            list = Arrays.asList( //
                    new Person(1, "Penny"), // 
                    new Person(2, "Leonard"), // 
                    new Person(3, "Sheldon")) //
                    .toArray(new Person[0]);
        }

        public String getCommandLink() {
            return commandLink;
        }

        public void setCommandLink(String commandLink) {
            this.commandLink = commandLink;
        }

        public String getCommandLinkLabel() {
            return commandLinkLabel;
        }

        public void setCommandLinkLabel(String commandLinkLabel) {
            this.commandLinkLabel = commandLinkLabel;
        }

        public void setList(Person[] list) {
            this.list = list;
        }

        public Person[] getList() {
            return list;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getInputText() {
            return inputText;
        }

        public void setInputText(String inputText) {
            this.inputText = inputText;
        }

        public String getInputTextarea() {
            return inputTextarea;
        }

        public void setInputTextarea(String inputTextarea) {
            this.inputTextarea = inputTextarea;
        }

        public String[] getSelectManyCheckbox() {
            return selectManyCheckbox;
        }

        public void setSelectManyCheckbox(String[] selectManyCheckbox) {
            this.selectManyCheckbox = selectManyCheckbox;
        }

        public boolean isSelectBooleanCheckbox() {
            return selectBooleanCheckbox;
        }

        public void setSelectBooleanCheckbox(boolean selectBooleanCheckbox) {
            this.selectBooleanCheckbox = selectBooleanCheckbox;
        }

        public String getSelectCheckbox() {
            return selectCheckbox;
        }

        public void setSelectCheckbox(String selectCheckbox) {
            this.selectCheckbox = selectCheckbox;
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        // JSF 2 defines a variety of component tags. Each component is backed by a UIComponent class.
        // These component tags are rendered as an HTML element via the HTML RenderKit Renderer.

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Person.class, MyBean.class, FileUploadBean.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part13_components/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("next.xhtml", "src/main/resources/chapter03_jsf/part13_components/next.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("upload.xhtml", "src/main/resources/chapter03_jsf/part13_components/upload.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("ajax-upload.xhtml", "src/main/resources/chapter03_jsf/part13_components/ajax-upload.xhtml"));

            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part13_components/stylesheets", "resources/stylesheets");
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part13_components/scripts", "resources/scripts");
            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part13_components/images", "resources/images");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part13_components/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part13_components/beans.xml"));

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
