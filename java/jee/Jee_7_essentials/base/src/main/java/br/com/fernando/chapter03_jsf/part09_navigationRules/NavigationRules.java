package br.com.fernando.chapter03_jsf.part09_navigationRules;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class NavigationRules {

    // JSF defines implicit and explicit navigation rules.
    // Look at navigationRules01.xhtml

    @Named
    @RequestScoped
    public static class Navigation01Bean implements Serializable {

        private static final long serialVersionUID = 1L;

        public String getName() {
            return this.getClass().getSimpleName();
        }

        public String next() {
            return "/navigationRules02.xhtml";
            // or
            // return "navigationRules02";
        }
    }

    // You can specify explicit navigation using <navigation-rule> in faces-config.xml, and
    // you can specify conditional navigation using <if>. Look at faces-config.xml

    @Named
    @RequestScoped
    public static class Navigation02Bean implements Serializable {

        private static final long serialVersionUID = 1L;

        private boolean premium;

        public boolean isPremium() {
            return premium;
        }

        public void setPremium(boolean premium) {
            this.premium = premium;
        }

        public String getName() {
            return this.getClass().getSimpleName();
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(Navigation01Bean.class, Navigation02Bean.class);

            // webapp
            war.addWebResourceFiles(EmbeddedResource.add("navigationRules01.xhtml", "src/main/resources/chapter03_jsf/part09_navigationRules/navigationRules01.xhtml"));
            war.addWebResourceFiles(EmbeddedResource.add("navigationRules02.xhtml", "src/main/resources/chapter03_jsf/part09_navigationRules/navigationRules02.xhtml"));

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter03_jsf/part09_navigationRules/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter03_jsf/part09_navigationRules/beans.xml"));
            war.addWebInfFiles(EmbeddedResource.add("faces-config.xml", "src/main/resources/chapter03_jsf/part09_navigationRules/faces-config.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // http://localhost:8080/embeddedJeeContainerTest/navigationRules01.xhtml

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
