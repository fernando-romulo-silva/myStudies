package br.com.fernando.chapter03_jsf.part12_passthroughAttributesHtml5;

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
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class PassthroughAttributesHtml5 {

    // ==================================================================================================================================================================
    @Named
    @SessionScoped
    public static class UserBean implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int age;
        private String email;
        private String blog;
        private String tel;
        private String dob;
        private String color;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBlog() {
            return blog;
        }

        public void setBlog(String blog) {
            this.blog = blog;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    @Named
    @SessionScoped
    public static class LoginBean implements Serializable {

        private static final long serialVersionUID = 1L;

        private String user;

        private String password;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void doLogin() {
            System.out.println("do login! " + user + "  " + password);
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        // HTML5 adds a series of new attributes for existing elements. These attributes include the type attribute for input elements, which supports values such as email, 
        // url, tel, number, range, and date:
        // <input type="email" name="myEmail"/>
        //
        // * JSF 2.2 introduces passthrough attributes, which allow us to list arbitrary name/value pairs in a component that are passed straight through to the user agent 
        // without interpretation by the UIComponent or Renderer . The passthrough attributes can be specified in three different ways:
        // 
        // <h:inputText p:type="email" value="#{user.email}"/>
        // 
        // In this code, p is the shortname for the namespace
        // 
        // * Nesting the <f:passThroughAttribute> tag within a component:
        // 
        // <h:inputText value="#{user.email}">
        //    <f:passThroughAttribute name="type" value="email"/>
        // </h:inputText>
        //
        // In this code, a type attribute of value email is marked as a passthrough attribute 
        //
        // * Nesting the <f:passThroughAttributes> tag within a component:
        // <h:inputText value="#{user.data}">
        //     <f:passThroughAttributes value="#{user.myAttributes}"/>
        // </h:inputText>
        //
        // #{user.myAttributes} must point to a Map<String, Object> where the values can be either literals or a value expression.
        //
        // You can use html pages (with xhtml extension), look at indexHtml.xhtml 

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(UserBean.class, LoginBean.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("index-after.xhtml", "src/main/resources/chapter03_jsf/part12_passthrough/index-after.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("index-before.xhtml", "src/main/resources/chapter03_jsf/part12_passthrough/index-before.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("index.xhtml", "src/main/resources/chapter03_jsf/part12_passthrough/index.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("indexHtml.xhtml", "src/main/resources/chapter03_jsf/part12_passthrough/indexHtml.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("template.xhtml", "src/main/resources/chapter03_jsf/part12_passthrough/template.xhtml"));

            war.addWebResourceFiles("src/main/resources/chapter03_jsf/part12_passthrough/resources/css", "resources/css");

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part12_passthrough/web.xml"));

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
