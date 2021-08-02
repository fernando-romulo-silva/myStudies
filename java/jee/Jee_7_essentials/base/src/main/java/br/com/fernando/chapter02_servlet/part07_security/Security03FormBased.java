package br.com.fernando.chapter02_servlet.part07_security;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmFile.MyEmbeddedJeeRealmFileBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Security03FormBased {

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            //
            war.addWebResourceFiles(EmbeddedResource.add("index.jsp", "src/main/resources/chapter02_servlet/part07_security/indexFormBased.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("login-error.jsp", "src/main/resources/chapter02_servlet/part07_security/login-error.jsp"));
            war.addWebResourceFiles(EmbeddedResource.add("login.jsp", "src/main/resources/chapter02_servlet/part07_security/loginFormBased.jsp"));

            // web inf files
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part07_security/web_Security03FormBased.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web.xml"));
            // export
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            final Path realFilePath = Paths.get(Security05Digest.class.getResource("/chapter02_servlet/part07_security/keyfile").toURI());

            final MyEmbeddedJeeRealmFile realm = new MyEmbeddedJeeRealmFileBuilder("myRealm", realFilePath) //
                    .withDigestAlgorithm("SSHA256") // Default
                    .withEncoding("Hex") // Default
                    .build();

            embeddedJeeServer.addFileRealm(realm);
            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // access the browser
            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
